#ifndef _LOGGER_HPP_
#define _LOGGER_HPP_

#include <iostream>
#include <string>
#include <sstream>

// 4 default levels: LOG_ERROR, LOG_WARNING, LOG_INFO, LOG_DEBUG
// and devs, e.g.: LOG_BY, LOG_WL, LOG_JC, LOG_PR
enum loglevel_enum {
	LOG_ERROR, LOG_WARNING, LOG_INFO, LOG_DEBUG, LOG_BY, LOG_WL, LOG_JC, LOG_PR
};

extern loglevel_enum loglevel;

class LogSystem {
public:
	LogSystem(loglevel_enum thisLevel) {
		std::string levelString;
		switch (thisLevel) {
			case (LOG_ERROR): levelString = "LOG_ERROR"; break;
			case (LOG_WARNING): levelString = "LOG_WARNING"; break;
			case (LOG_INFO): levelString = "LOG_INFO"; break;
			case (LOG_DEBUG): levelString = "LOG_DEBUG"; break;
			case (LOG_BY): levelString = "LOG_BY"; break;
			case (LOG_WL): levelString = "LOG_WL"; break;
			case (LOG_JC): levelString = "LOG_JC"; break;
			case (LOG_PR): levelString = "LOG_PR"; break;
			default: levelString = "LOG_ERROR";
		}
		_buffer << levelString << ": ";
	}
	~LogSystem() {
        _buffer << std::endl;
        // This is atomic according to the POSIX standard
        // http://www.gnu.org/s/libc/manual/html_node/Streams-and-Threads.html
        std::cerr << _buffer.str();
    }
	template <typename T>
	LogSystem& operator<<(T const& value) {
		_buffer << value;
		return *this;
	}
	static bool shouldLog(const loglevel_enum thislevel, const loglevel_enum loglevel) {
		bool print = false;
		if (thislevel == LOG_ERROR) {
			print = true;
		}
		if (thislevel == LOG_WARNING && loglevel != LOG_ERROR) {
			print = true;
		}
		if (thislevel == LOG_INFO && loglevel != LOG_WARNING && loglevel != LOG_ERROR) {
			print = true;
		}
		if (thislevel == LOG_DEBUG && loglevel != LOG_WARNING && loglevel != LOG_ERROR && loglevel != LOG_INFO) {
			print = true;
		}
		if ((thislevel == LOG_BY || thislevel == LOG_WL || thislevel == LOG_JC || thislevel == LOG_PR) &&
			(loglevel == thislevel)) {
				print = true;
		}
		return print;
	}
	std::ostringstream _buffer;
};

#define log(thisLevel) if (LogSystem::shouldLog(thisLevel, loglevel)) LogSystem(thisLevel)

#endif