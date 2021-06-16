/*
 * main_mop_mpi.cpp
 *
 *  Created on: May 31, 2013
 *      Author: antonio
 */

#include <mpi.h>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include "MasterSlaveMPI.h"
#include "DummyMOP.h"
#include "readOptions.h"
#include "VectorTools.h"

using namespace std;

typedef VectorTools VT;
const string varsFileName = "pop_vars_eval.txt";
const string objsFileName = "pop_objs_eval.txt";
const string consFileName = "pop_cons_eval.txt";

// Write a set of vectors to a file with the given name.
// Each line of the file is a vector of the set.
void writeSet(vector<vector<double> > const &set, string const &fileName);

int main(int argc, char **argv)
{
   string interDir;   /* Interface directory for MOEA/Evaluator communication. */

   MPI::Init(argc, argv);
   int myRank = MPI::COMM_WORLD.Get_rank();

/// 0. All the processes read the configuration file.
   read_exit_value status = readOptions(argc, argv, interDir);
   if ( status == ERROR || status == HELP) {
      MPI::Finalize();
      return 0;
   }

/// 1. Create DummyMOP to evaluate trajectories. 
	DummyMOP mop;

/// 2. Read design parameters file.
   vector<vector<double> > varSet = VT::loadDelimVector(interDir + "/" + varsFileName, ",");
   int popSize = varSet.size();

/// 3. Evaluate solutions (All the processes)
///    The MASTER evaluates and gather results, SLAVEs only evaluate solutions.
   MasterSlaveMPI *evaluator = new MasterSlaveMPI(popSize, (MOP *)&mop);

   if (myRank == MasterSlaveMPI::MASTER_PROCESS)
   {
      vector<vector<double> > objSet(popSize, vector<double>(mop.getNumObjectives()));
      vector<vector<double> > conSet(popSize, vector<double>(mop.getNumConstraints()));

      evaluator->evaluateSet_master((MOP *)&mop, varSet, objSet, conSet);

      /// 4. Write objectives evaluation and constraints (IF any)
      writeSet(objSet, interDir + "/" + objsFileName);

      if (mop.getNumConstraints() > 0)
         writeSet(conSet, interDir + "/" + consFileName);
   }
   else {
      evaluator->evaluateSet_slave((MOP *)&mop);
   }

   delete evaluator;
   MPI::Finalize();

   return 0;
}


void writeSet(vector<vector<double> > const &set, string const &fileName)
{
   ofstream outputFile(fileName.c_str(), ios::out);

   int popSize = set.size();
   for (int i = 0; i < popSize; i++)
      outputFile << VectorTools::vectorToStr(set.at(i), 8, ",", make_pair("", "")) << endl;

   outputFile.close();
}
