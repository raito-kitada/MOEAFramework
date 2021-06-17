#!/bin/bash

export PATH=/home/tatsukawa/.p2/pool/plugins/org.eclipse.justj.openjdk.hotspot.jre.full.linux.x86_64_15.0.1.v20201027-0507/jre/bin:${PATH}

# if there is not MOEAFramework-2.13.jar in lib, "../bin" must be added to the class path.
java -cp "../../bin:../../lib/*:." lec01/solveSchafferProblem

# if MOEAFramework-2.13.jar is exported / generated in lib, the following commad is okay.
#java -cp ".../lib/*:." lec01/solveSchafferProblem
