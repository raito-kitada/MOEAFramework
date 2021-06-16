/*
 * userReadInputParameters.cpp
 *
 *  Created on: Apr 18, 2013
 *      Author: antonio
 */

#include "readOptions.h"
#include "VectorTools.h"
#include <fstream>
#include <exception>

using namespace std;
typedef VectorTools vt;

read_exit_value readOptions(int argc, char **argv, string &workingDir)
{
   read_exit_value exitVal;

 //** 1) Register the options/parameters that the program will provide.
   po::options_description optionsList = createGeneralOptions();

 //*** 2) Parse values given in the command line.
   po::variables_map vm;
   const string headerMsg = "\ndummy optimization problem.";
   exitVal = parseOptions(argc, argv, optionsList, vm, headerMsg);
   if (exitVal != SUCCESS)
      return exitVal;

 //*** 3) Read the values parsed and stored in the variables map: vm
   exitVal = readMOPValues(optionsList, vm, workingDir);

   return exitVal;
}

po::options_description createGeneralOptions()
{
   po::options_description mainOptionsList("Options for MOP", 100, 50);

   try {
      //Informational Options. These are not included in the input configuration file.
      po::options_description info(100, 50);
      info.add_options()
         ("help,h", "Use --help or -h to display this help.");

      // Options available for MOP.
      po::options_description mop_mandatory("Mandatory parameters", 100, 50);
      mop_mandatory.add_options()
         (WORK_DIR_ID, po::value<string>(), "Interface directory for MOEA/Evaluator")
         ;

      //po::options_description mop_optional("Optional parameters", 100, 50);
      //mainOptionsList.add(info).add(mop_mandatory).add(mop_optional);
      
      mainOptionsList.add(info).add(mop_mandatory);
   }
   catch(...) {
      exceptionMessage("Unknown", __FUNCTION__, __LINE__);
      throw std::exception();
   }

   return mainOptionsList;
}

read_exit_value parseOptions(int argc, char **argv,
                             po::options_description const &options,
                             po::variables_map &vm,
                             string const &topMsg)
{
   try {
      store(po::parse_command_line(argc, argv, options), vm);

      if (vm.count("help")) {
         cout << topMsg << getUsageMessage(options) << options << "\n";
         return HELP;
      }

      notify(vm);
   }
   catch(po::unknown_option& e) {
      unknownOptionMessage(e.get_option_name());
      return ERROR;
   }
   catch(exception& e) {
      exceptionMessage(e.what(), __FUNCTION__, __LINE__);
      throw std::exception();
   }
   catch(...) {
      exceptionMessage("Unknown", __FUNCTION__, __LINE__);
      throw std::exception();
   }

   return SUCCESS;
}

read_exit_value readMOPValues(po::options_description &desc,
                              po::variables_map &vm,
                              string &workingDir)
{
   read_exit_value exitVal = SUCCESS;

   if (vm.count(WORK_DIR_ID))
   {
      workingDir = vm[WORK_DIR_ID].as<string>();
   }
   else {
      missingValueMessage(desc.find(WORK_DIR_ID, true).description());
      exitVal = ERROR;
   }

   return exitVal;
}

void writeValuesRead(string &workingDir, ostream &output)
{
   output << "\nInterface directory: " << workingDir;
   output << endl;
}

string getUsageMessage(po::options_description const &options)
{
   string usageMsg =
         string("\n\nUsage:\n./mop.out") +
         " --" + WORK_DIR_ID  + "=<file>\n\n";
   return usageMsg;
}

void exceptionMessage(string exceptionName, string FunctionName, int line) {
   cerr << "\nException: " << exceptionName
        << " (raised at " << FunctionName << " in line " << line << ").\n" << endl;
}

void wrongFileMessage(string fileName, string purposeOfFile)
{
   cout << "\nFile " << fileName << " for " << purposeOfFile
        << " can't be opened, hence exiting.\n" << endl;
}

void missingValueMessage(string elementName)
{
   cout << "\nMissing value: A value for " << elementName << " must be provided.\n" << endl;
}

void unknownOptionMessage(string elementName)
{
   cout << "Unknown option '" << elementName << "'" << endl;
}
