#include "logger.hpp"
#include <iostream>
#include <string>
#include <cstdlib>

const std::string Y = "this should appear";
const std::string N = "this shouldn't appear!";
loglevel_enum loglevel = LOG_ERROR;

int main() {
	// loglevel = LOG_ERROR;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << N;
	log(LOG_INFO) << N;
	log(LOG_DEBUG) << N;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_WARNING;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << N;
	log(LOG_DEBUG) << N;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_INFO;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << N;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_DEBUG;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << Y;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_BY;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << Y;
	log(LOG_BY) << Y;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_WL;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << Y;
	log(LOG_BY) << N;
	log(LOG_WL) << Y;
	log(LOG_JC) << N;
	log(LOG_PR) << N;

	loglevel = LOG_JC;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << Y;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << Y;
	log(LOG_PR) << N;

	loglevel = LOG_PR;
	log(LOG_ERROR) << Y;
	log(LOG_WARNING) << Y;
	log(LOG_INFO) << Y;
	log(LOG_DEBUG) << Y;
	log(LOG_BY) << N;
	log(LOG_WL) << N;
	log(LOG_JC) << N;
	log(LOG_PR) << Y;

	system("pause");
	return 0;
}