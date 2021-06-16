#ifndef MOP_H
#define MOP_H

#include <string>
#include <vector>
#include <utility>

using namespace std;

/**
 * Abstract base class MOP defines a general Multiobjective Optimization Problem (MOP).
 * The abstract class MOP should be implemented (by inheritance) by any class
 * intended to define a concrete MOP. The child class must define the
 * evaluate() method, and, if needed, the evalConstraints() method .
 */
class MOP {
public:
   MOP(int numVariables, int numObjectives, int numConstraints = 0);
   virtual ~MOP();
   
   virtual void evaluate(vector<double> const &var,
		                 vector<double> &obj,
		                 vector<double> &con) const;

   virtual void setNumObjectives(int nobj);
   virtual void setNumVariables(int nvar);
   virtual void setNumConstraints(int ncon);

   const int getNumVariables() const;
   const int getNumObjectives() const;
   const int getNumConstraints() const;
   
protected:

   int nvar;        // Number of decision variables
   int nobj;       // Number of objectives
   int ncon;      // Number of constraints

}; // end abstract class MOP

#endif
