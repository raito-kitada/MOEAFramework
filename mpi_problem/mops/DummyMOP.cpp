/*
 * DummyMOP.cpp
 *
 */

#include "DummyMOP.h"
#include <iostream>

DummyMOP::DummyMOP()
         :MOP(1, 2, 0)
{
}

DummyMOP::~DummyMOP() 
{
}

void DummyMOP::evaluate(vector<double> const &var,
                        vector<double> &obj,
                        vector<double> &con,
    				         int rank) const
{
	obj[0] = var[0] * var[0];
	obj[1] = (var[0] - 2.0) * (var[0] - 2.0);
	
	//con[0] = rank;
}
