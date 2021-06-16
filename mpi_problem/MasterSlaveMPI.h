/*
 * MasterSlaveMPI.h
 *
 *  Created on: Aug 8, 2012
 *      Author: antonio
 */

#ifndef MASTERSLAVEMPI_H_
#define MASTERSLAVEMPI_H_

#include <mpi.h>
#include <vector>
#include "mop.h"

using namespace std;

class MasterSlaveMPI {
public:
   MasterSlaveMPI(int popSize, MOP *mop);
	virtual ~MasterSlaveMPI();
	static const int MASTER_PROCESS = 0;

	void evaluateSet_master(MOP *mop,
	                        vector<vector<double> > const &varSet,
	                        vector<vector<double> > &objSet,
	                        vector<vector<double> > &consSet);

	void evaluateSet_slave(MOP *mop);

private:
   void evaluatePop(MOP *mop);
   void packParameterValues(vector<vector<double> > const &varSet);
   void unpackParameterValues();
   void scatterParameterValues();
   void gatherObjectiveValues();
   void packObjectiveValues();

   void unpackObjectiveValues(vector<vector<double> > &objSet,
                              vector<vector<double> > &consSet);

   double *createLinear2DArray(int rows, int cols);
   void destroyLinear2DArray(double *array);
   vector<double*> get2DView(double *data, int rows, int cols);

   enum message_tag {XVALUES_TAG, FVALUES_TAG};

   int myRank;             //Identifier for each process.
   int numProcesses;       //Number of processes available.
   int popSize;            //Total number of individuals to evaluate.

   MOP *mop;     //MOP to solve
   int numVars;  //Number of real-encoded variables.
   int numObjs;  //Number of objectives of the problem.
   int numCons;  //Number of constraints of the problem.

	vector<int> solsPerSlave; //Number of solutions assigned for each slave.
	vector<int> groupStart;   //Index at which each group of solutions starts wrt all the population.

   // Vectors to store variable, objective and constraint values during computation.
	double *xvals_send;
	double *xvals_recv;
	double *fvals_send;
	double *fvals_recv;

	MPI::Datatype XVECTOR;
	MPI::Datatype OBJCONSVECTOR;
};

#endif /* MASTERSLAVEMPI_H_ */
