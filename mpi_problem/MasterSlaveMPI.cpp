/*
 * MasterSlaveMPI.cpp
 *
 *  Created on: Aug 8, 2012
 *      Author: antonio
 */

#include <mpi.h>
#include <algorithm>
#include "MasterSlaveMPI.h"
#include "VectorTools.h"

#include "DummyMOP.h"

MasterSlaveMPI::MasterSlaveMPI(int pSize, MOP *m) {
	mop = m;
	popSize = pSize;
	numVars = mop->getNumVariables();
	numObjs = mop->getNumObjectives();
	numCons = mop->getNumConstraints();

	myRank = MPI::COMM_WORLD.Get_rank();
	numProcesses = MPI::COMM_WORLD.Get_size();

	// If the population size is divisible by the number of processes,
	// then every process will receive the same number of solutions.
	//
	// Otherwise, the first (popSize mod numProcesses) processes will receive
	// popSize/numProcesses + 1 solutions each, while the next ones
	// popSize/numProcesses solutions each.
	int n = popSize/numProcesses;
	int remainder = popSize % numProcesses;

	solsPerSlave.resize(numProcesses);
	groupStart.resize(numProcesses);
	int offset = 0;
	for (int p = 0; p < numProcesses; ++p)
	{
		solsPerSlave.at(p) = (p < remainder) ? n + 1 : n;
		groupStart.at(p) = offset;
		offset += solsPerSlave.at(p);
	}

	xvals_send = createLinear2DArray(popSize, numVars);
	xvals_recv = createLinear2DArray(solsPerSlave.at(myRank), numVars);
	fvals_send = createLinear2DArray(solsPerSlave.at(myRank), (numObjs+numCons));
	fvals_recv = createLinear2DArray(popSize, (numObjs+numCons));

	XVECTOR = MPI::DOUBLE.Create_contiguous(numVars);
	XVECTOR.Commit();
	OBJCONSVECTOR = MPI::DOUBLE.Create_contiguous(numObjs+numCons);
	OBJCONSVECTOR.Commit();
}

MasterSlaveMPI::~MasterSlaveMPI() {
	destroyLinear2DArray(xvals_send);
	destroyLinear2DArray(xvals_recv);
	destroyLinear2DArray(fvals_send);
	destroyLinear2DArray(fvals_recv);
	XVECTOR.Free();
	OBJCONSVECTOR.Free();
}

void MasterSlaveMPI::evaluateSet_master(MOP *m,
                                      vector<vector<double> > const &varSet,
                                      vector<vector<double> > &objSet,
                                      vector<vector<double> > &consSet)
{
	//1. Package the solutions to be sent.
	packParameterValues(varSet); //Only for the MASTER

	//2. Send/Receive the packed solutions.
	scatterParameterValues();
	//cerr << "Master Process " << myRank << ": parameter values unpacked.\n";

	//3. Every process, the master process INCLUDED, compute the objective values,
	evaluatePop(m);
	//cerr << "Master Process " << myRank << ": solutions evaluated.\n";

	//4. Package objective values and send/receive them.
	gatherObjectiveValues();
	//cerr << "Master Process " << myRank << ": objective values received.\n";

	//5. Unpack the received objective and constraint values.
	unpackObjectiveValues(objSet, consSet);  //Only for the MASTER
}

void MasterSlaveMPI::evaluateSet_slave(MOP *m)
{
   //1. Send/Receive the packed solutions.
   scatterParameterValues();
   //cerr << "Process " << myRank << ": parameter values unpacked.\n";

   //2. Compute objective values
   evaluatePop(m);
   //cerr << "Process " << myRank << ": solutions evaluated.\n";

   //3. Package objective values and send/receive them.
   gatherObjectiveValues();
   //cerr << "Process " << myRank << ": objective values received and unpacked.\n";
}

void MasterSlaveMPI::evaluatePop(MOP *m)
{
   DummyMOP *theMOP = dynamic_cast<DummyMOP*>(m);

	for (int i=0; i < solsPerSlave.at(myRank); ++i)
	{
		double *x = &(xvals_recv[i*numVars]);
		double *fx = &(fvals_send[i*(numObjs+numCons)]);
		double *gcons = &(fvals_send[i*(numObjs+numCons) + numObjs]);

		vector<double> xCopy(x, x + numVars);
		vector<double> fxCopy(fx, fx + numObjs);
		vector<double> gconsCopy(gcons, gcons + numCons);

		theMOP->evaluate(xCopy, fxCopy, gconsCopy, groupStart.at(myRank) + i);

		copy(fxCopy.begin(), fxCopy.end(), fx);
		copy(gconsCopy.begin(), gconsCopy.end(), gcons);
	}
}

void MasterSlaveMPI::packParameterValues(vector<vector<double> > const &ind)
{
	for (int i = 0; i < popSize; ++i)
	{
	   std::copy(ind.at(i).begin(), ind.at(i).end(), xvals_send + i*numVars);
	}
}

void MasterSlaveMPI::scatterParameterValues()
{
	// All the processes (master and slaves) do this.
	MPI::COMM_WORLD.Scatterv(xvals_send,
			                      &(*solsPerSlave.begin()),
			                      &(*groupStart.begin()),
			                      XVECTOR,
			                      xvals_recv,
			                      solsPerSlave.at(myRank),
			                      XVECTOR,
			                      MASTER_PROCESS);
}

void MasterSlaveMPI::gatherObjectiveValues()
{
	// All the processes (master and slaves) do this.
	MPI::COMM_WORLD.Gatherv(fvals_send,
			                    solsPerSlave.at(myRank),
			                    OBJCONSVECTOR,
			                    fvals_recv,
			                    &(*solsPerSlave.begin()),
			                    &(*groupStart.begin()),
			                    OBJCONSVECTOR,
			                    MASTER_PROCESS);
}

void MasterSlaveMPI::unpackObjectiveValues(vector<vector<double> > &objSet, vector<vector<double> > &consSet)
{
	if (numCons != 0)
	{
		for (int i = 0; i < popSize; i++)
		{
			//Copy the objective values
			std::copy(&(fvals_recv[i*(numObjs+numCons)]),
				      &(fvals_recv[i*(numObjs+numCons) + numObjs]),
				      objSet.at(i).begin());

			//Copy the constraint values next
			std::copy(&(fvals_recv[i*(numObjs+numCons) + numObjs]),
				      &(fvals_recv[(i+1)*(numObjs+numCons)]),
				      consSet.at(i).begin());
		}
	}
	else
	{
		for (int i = 0; i < popSize; i++)
			std::copy(&(fvals_recv[i*numObjs]),
			 	      &(fvals_recv[(i+1)*numObjs]),
			 	     objSet.at(i).begin());
	}
}

double *MasterSlaveMPI::createLinear2DArray(int rows, int cols)
{
	double *data = new double[rows*cols];

	return data;
}

void MasterSlaveMPI::destroyLinear2DArray(double *array)
{
	delete array;
}

vector<double*> MasterSlaveMPI::get2DView(double *data, int rows, int cols)
{
    vector<double*> array(rows);

    for (unsigned i = 0; i < array.size(); ++i)
        array[i] = data + i*cols;

	return array;
}
