#!/bin/bash

export PATH=/home/tatsukawa/.p2/pool/plugins/org.eclipse.justj.openjdk.hotspot.jre.full.linux.x86_64_15.0.1.v20201027-0507/jre/bin:${PATH}

# if there is not MOEAFramework-2.13.jar in lib, "../bin" must be added to the class path.
javac -cp "../../bin:../../lib/*:." SchafferProblem.java 
javac -cp "../../bin:../../lib/*:." solveSchafferProblem.java 

# if MOEAFramework-2.13.jar is exported / generated in lib, the following commad is okay.
#javac -cp "../lib/*:." SchafferProblem.java 
#javac -cp "../lib/*:." solveSchafferProblem.java 
