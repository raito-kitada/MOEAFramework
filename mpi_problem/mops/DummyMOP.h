/*
 * DummyMOP.h
 *
 */

#ifndef DUMMYMOP_H_
#define DUMMYMOP_H_

#include <string>
#include <mop.h>

using namespace std;

class DummyMOP : public MOP {
public:
	DummyMOP();
	virtual ~DummyMOP();

	void evaluate(vector<double> const &var,
			      vector<double> &obj,
			      vector<double> &con,
				  int rank) const;
};

#endif /* DUMMYMOP_H_ */
