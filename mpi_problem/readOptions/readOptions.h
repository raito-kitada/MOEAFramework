/*
 * readOptions.h
 *
 *  Created on: Apr 18, 2013
 *      Author: antonio
 */

#ifndef READOPTIONS_H_
#define READOPTIONS_H_

#include <string>
#include <boost/program_options.hpp>

using namespace std;
namespace po = boost::program_options;

// Possible exit values after trying to read the program options.
enum read_exit_value {SUCCESS, ERROR, HELP};

const char* const WORK_DIR_ID = "inter-dir";

read_exit_value readOptions(int argc, char **argv,
                            string &workingDir);

po::options_description createGeneralOptions();

read_exit_value parseOptions(int argc, char **argv,
                             po::options_description const &options,
                             po::variables_map &vm,
                             string const &topMsg);

read_exit_value readMOPValues(po::options_description &desc,
                          po::variables_map &vm,
                          string &workingDir);

void writeValuesRead(string &workingDir,
                     ostream &output);

string getUsageMessage(po::options_description const &options);

void exceptionMessage(string exceptionName, string FunctionName, int line);

void unknownOptionMessage(string elementName);

void wrongFileMessage(string fileName, string purposeOfFile);

void missingValueMessage(string elementName);

#endif