/*
 * main_mop_seq.cpp
 *
 *  Created on: May 31, 2013
 *      Author: antonio
 */

#include <string>
#include <vector>
#include "DummyMOP.h"
#include "readOptions.h"
#include "VectorTools.h"

using namespace std;

const string varsFileName = "pop_vars_eval.txt";
const string objsFileName = "pop_objs_eval.txt";
const string consFileName = "pop_cons_eval.txt";

void evalSolutions(DummyMOP &mop,
                   vector<vector<double> > const &x,
                   vector<vector<double> > &f,
                   vector<vector<double> > &g);

void writeSet(vector<vector<double> > &set, string fileName);

int main(int argc, char **argv) {
   string interDir;

   read_exit_value status = readOptions(argc, argv, interDir);
   if ( status == ERROR || status == HELP)
      return 0;

   // Create MOP
   DummyMOP mop();

   // Read variables file.
   string fileName = interDir + "/" + varsFileName;
   vector<vector<double> > varSet = VectorTools::loadDelimVector(fileName,",");

   int popSize = varSet.size();
   cout << "\npopSize=" << popSize << endl;

   // Evaluate solutions
   vector<vector<double> > objSet(popSize, vector<double>(mop.getNumObjectives()));
   vector<vector<double> > conSet(popSize, vector<double>(mop.getNumConstraints()));
   evalSolutions(mop, varSet, objSet, conSet);

   // Write objectives evaluation
   fileName = interDir + "/" + objsFileName;
   writeSet(objSet, fileName);

   if (mop.getNumConstraints() > 0) {
      // Write constraint evaluation (if any)
      fileName = interDir + "/" + consFileName;
      writeSet(conSet, fileName);
   }

   return 0;
}

void evalSolutions(DummyMOP &mop,
                   vector<vector<double> > const &x,
                   vector<vector<double> > &f,
                   vector<vector<double> > &g)
{
   int popSize = x.size();

   for (int i=0; i < popSize; ++i)
      mop.evaluate(x.at(i), f.at(i), g.at(i), i);
}

void writeSet(vector<vector<double> > &set, string fileName)
{
   ofstream outputFile(fileName.c_str(), ios::out);

   int popSize = set.size();
   for (int i = 0; i < popSize; i++)
      outputFile << VectorTools::vectorToStr(set.at(i), 8, ",", make_pair("", "")) << endl;

   outputFile.close();
}
