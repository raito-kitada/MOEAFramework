#include <iostream>
#include "mop.h"

using std::string;

MOP::MOP(int numVariables, int numObjectives, int numConstraints)
{
   this->nobj = numObjectives;
   this->nvar = numVariables;
   this->ncon = numConstraints;
}

MOP::~MOP() {}

void MOP::evaluate(vector<double> const &var,
		                 vector<double> &obj,
		                 vector<double> &con) const
{
   // do nothing
}

void MOP::setNumObjectives(int nobj) {
   this->nobj = nobj;
}

void MOP::setNumVariables(int nvar) {
   this->nvar = nvar;
}

void MOP::setNumConstraints(int ncon) {
   this->ncon = ncon;
}

const int MOP::getNumVariables() const {
   return nvar;
}

const int MOP::getNumObjectives() const {
   return nobj;
}

const int MOP::getNumConstraints() const {
   return ncon;
}
