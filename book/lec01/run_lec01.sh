#!/bin/bash

# if there is not MOEAFramework-2.13.jar in lib, "../bin" must be added to the class path.
javac -cp "../../bin:../../lib/*:." SchafferProblem.java 
javac -cp "../../bin:../../lib/*:." solveSchafferProblem.java 
java -cp "../../bin:../../lib/*:." solveSchafferProblem

# if MOEAFramework-2.13.jar is exported / generated in lib, the following commad is okay.
#javac -cp "../lib/*:." lec01/SchafferProblem.java 
#javac -cp "../lib/*:." lec01/solveSchafferProblem.java 
#java -cp ".../lib/*:." lec01/solveSchafferProblem