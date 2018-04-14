// modified from https://github.com/zhblue/hustoj/blob/master/trunk/core/judge_client/judge_client.cc
#define IGNORE_ESOL
// File:   main.cc
// Author: sempr
// refacted by zhblue
/*
 * Copyright 2008 sempr <iamsempr@gmail.com>
 *
 * Refacted and modified by zhblue<newsclan@gmail.com>
 * Bug report email newsclan@gmail.com
 *
 *
 * This file is part of HUSTOJ.
 *
 * HUSTOJ is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * HUSTOJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HUSTOJ. if not, see <http://www.gnu.org/licenses/>.
 */

#include <cstdio>
#include <cstring>
#include <string>
#include <sys/ptrace.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/user.h>
#include <sys/wait.h>
#include <sys/resource.h>
#include <unistd.h>

#include "okcalls.h"
#include "executor.h"

#define STD_MB 1048576
#define STD_T_LIM 2
#define STD_F_LIM (STD_MB<<5)
#define STD_M_LIM (STD_MB<<7)
#define BUFFER_SIZE 512

/*copy from ZOJ
 http://code.google.com/p/zoj/source/browse/trunk/judge_client/client/tracer.cc?spec=svn367&r=367#39
 */
#ifdef __i386
#define REG_SYSCALL orig_eax
#define REG_RET eax
#define REG_ARG0 ebx
#define REG_ARG1 ecx
#else
#define REG_SYSCALL orig_rax
#define REG_RET rax
#define REG_ARG0 rdi
#define REG_ARG1 rsi
#endif

enum ResultEnum {
    ACCEPTED            = 3,
    TIME_LIMIT_EXCEED   = 5,
    MEMORY_LIMIT_EXCEED = 6,
    OUTPUT_LIMIT_EXCEED = 7,
    RUNTIME_ERROR       = 8
};

enum LanguageEnum {
    C    = 1,
    CPP  = 2,
    JAVA = 3
};

const int call_array_size = 512;
unsigned int call_id = 0;
unsigned int call_counter[call_array_size] = { 0 };

const static int use_ptrace = 1;
const static double cpu_compensation = 1.0;
const static int DEBUG = 0;

void execute(const int& language, const char* workspacePath,
            const char* inputFilePath, const char* outputFilePath,
            const char* userOutputFilePath, const char* errorFilePath,
            const int& timeLimit, int& usedTime, const int& memoryLimit, int& topMemory);

JNIEXPORT jstring JNICALL Java_cn_idealismxxm_onlinejudge_judger_core_Executor_execute
    (JNIEnv* jniEnv, jobject selfReference, jint jlanguage, jstring jworkspacePath, jstring jinputFilePath, jstring joutputFilePath, jstring juserOutputFilePath, jstring jerrorFilePath, jint jtimeLimit, jint jmemoryLimit) {
    int language = jlanguage;
    const char* workspacePath = jniEnv -> GetStringUTFChars(jworkspacePath, 0);
    const char* inputFilePath = jniEnv -> GetStringUTFChars(jinputFilePath, 0);
    const char* outputFilePath = jniEnv -> GetStringUTFChars(joutputFilePath, 0);
    const char* userOutputFilePath = jniEnv -> GetStringUTFChars(juserOutputFilePath, 0);
    const char* errorFilePath = jniEnv -> GetStringUTFChars(jerrorFilePath, 0);
    int timeLimit = jtimeLimit;
    int memoryLimit = jmemoryLimit;

    int result = ACCEPTED;
    int usedTime = 0;
    int topMemory = 0;

    execute(language, workspacePath, inputFilePath, outputFilePath,
            userOutputFilePath, errorFilePath, timeLimit,
            usedTime, memoryLimit, topMemory);

    std::string resultJson = "{";
    resultJson += "\"result\":" + result;
    resultJson += ",\"usedTime\":" + usedTime;
    resultJson += ",\"topMemory\":" + topMemory;
    resultJson += "}";

    return jniEnv -> NewStringUTF(resultJson.c_str());
}

void initSyscallsLimits(int language) {
 	memset(call_counter, 0, sizeof(call_counter));
 	if (language == C || language == CPP) { // C & C++
 		for (int i = 0; i == 0 || LANG_CV[i] != 0; ++i) {
 			call_counter[LANG_CV[i]] = HOJ_MAX_LIMIT;
 		}
 	} else if (language == JAVA) { // Java
 		for (int i = 0; i == 0 || LANG_JV[i] != 0; ++i) {
 			call_counter[LANG_JV[i]] = HOJ_MAX_LIMIT;
 		}
 	}
 }


void runSolution(const int& language, const char* workspacePath, const char* inputFilePath, const char* userOutputFilePath, const char* errorFilePath, const int& timeLimit, int& usedTime, const int& memoryLimit) {
	nice(19);

    // now the user is "judger"
    chdir(workspacePath);

	// 输入输出重定向
	freopen(inputFilePath, "r", stdin);
	freopen(userOutputFilePath, "w", stdout);
	freopen(errorFilePath, "a+", stderr);
	// trace me
	if(use_ptrace) {
	    ptrace(PTRACE_TRACEME, 0, NULL, NULL);
	}
	// run me
	if (language != JAVA) {
		chroot(workspacePath);
    }

	while (setgid(1000) != 0) {
		sleep(1);
	}
	while (setuid(1000) != 0) {
		sleep(1);
    }
	while (setresuid(1000, 1000, 1000) != 0) {
		sleep(1);
    }

	// child
	// set the limit
	struct rlimit LIM; // time limit, file limit& memory limit
	// time limit
    LIM.rlim_cur = (timeLimit / cpu_compensation - usedTime) + 1000;
	LIM.rlim_max = LIM.rlim_cur;

	setrlimit(RLIMIT_CPU, &LIM);
	alarm(0);
	alarm(timeLimit * 5 / cpu_compensation);

	// file limit
	LIM.rlim_max = STD_F_LIM + STD_MB;
	LIM.rlim_cur = STD_F_LIM;
	setrlimit(RLIMIT_FSIZE, &LIM);
	// proc limit
	switch (language) {
	case JAVA:  //java
		LIM.rlim_cur = LIM.rlim_max = 80;
		break;
	default: // C & C++
		LIM.rlim_cur = LIM.rlim_max = 1;
	}

	setrlimit(RLIMIT_NPROC, &LIM);

	// set the stack
	LIM.rlim_cur = STD_MB << 6;
	LIM.rlim_max = STD_MB << 6;
	setrlimit(RLIMIT_STACK, &LIM);
	// set the memory
	LIM.rlim_cur = STD_MB * memoryLimit / 2 * 3;
	LIM.rlim_max = STD_MB * memoryLimit * 2;
	if (language == C || language == CPP) {
		setrlimit(RLIMIT_AS, &LIM);
    }

	switch (language) {
	case C:
	case CPP:
		execl("./Main", "./Main", (char *) NULL);
		break;
	case JAVA:
	    char* java_xmx = new char[BUFFER_SIZE];
        sprintf(java_xmx, "-Xmx%dM", memoryLimit);
		execl("java", "java", java_xmx,
		        "-Djava.security.manager",
				"-Djava.security.policy=./java.policy", "Main", (char *) NULL);
		break;
	}
	fflush(stderr);
}

int get_proc_status(int pid, const char* mark) {
	FILE * pf;
	char fn[BUFFER_SIZE], buf[BUFFER_SIZE];
	int ret = 0;
	sprintf(fn, "/proc/%d/status", pid);
	pf = fopen(fn, "re");
	int m = strlen(mark);
	while (pf && fgets(buf, BUFFER_SIZE - 1, pf)) {
		buf[strlen(buf) - 1] = 0;
		if (strncmp(buf, mark, m) == 0) {
			sscanf(buf + m + 1, "%d", &ret);
		}
	}
	if (pf) {
		fclose(pf);
	}
	return ret;
}

int get_page_fault_mem(struct rusage& ruse, pid_t& pidApp) {
	//java use pagefault
	int m_vmpeak, m_vmdata, m_minflt;
	m_minflt = ruse.ru_minflt * getpagesize();
	if (0 && DEBUG) {
		m_vmpeak = get_proc_status(pidApp, "VmPeak:");
		m_vmdata = get_proc_status(pidApp, "VmData:");
		printf("VmPeak:%d KB VmData:%d KB minflt:%d KB\n", m_vmpeak, m_vmdata,
				m_minflt >> 10);
	}
	return m_minflt;
}

long get_file_size(const char* filename) {
	struct stat f_stat;

	if (stat(filename, &f_stat) == -1) {
		return 0;
	}

	return (long) f_stat.st_size;
}

void watchSolution(int pidApp, int& ACflag, const char* userOutputFilePath,
                   const char* outputFilePath, const char* errorFilePath,
                   const int& language, int& topMemory, int memoryLimit,
                   int& usedTime, int timeLimit, const char* workspacePath) {
	// parent
	int tempMemory=0;

	int status, sig, exitcode;
	struct user_regs_struct reg;
	struct rusage ruse;
	bool first = true;
	while (true) {
		// check the usage
		wait4(pidApp, &status, __WALL, &ruse);
		if(first){ //
			ptrace(PTRACE_SETOPTIONS, pidApp, NULL,
                    PTRACE_O_TRACESYSGOOD
                    | PTRACE_O_TRACEEXIT
                    | PTRACE_O_EXITKILL
			);
		}

        //jvm gc ask VM before need,so used kernel page fault times and page size
		if (language == JAVA) {
			tempMemory = get_page_fault_mem(ruse, pidApp);
		} else {        //other use VmPeak
			tempMemory = get_proc_status(pidApp, "VmPeak:") << 10;
		}
		if (tempMemory > topMemory) {
			topMemory = tempMemory;
        }
		if (topMemory > memoryLimit * STD_MB) {
			if (DEBUG) {
				printf("out of memory %d\n", topMemory);
            }
			if (ACflag == ACCEPTED) {
				ACflag = MEMORY_LIMIT_EXCEED;
			}
			ptrace(PTRACE_KILL, pidApp, NULL, NULL);
			break;
		}
		//sig = status >> 8;/*status >> 8 Ã¥Â·Â®Ã¤Â¸ÂÃ¥Â¤Å¡Ã¦ËÂ¯EXITCODE*/

		if (WIFEXITED(status)) {
			break;
		}
		if (get_file_size("error.out")) {
			ACflag = RUNTIME_ERROR;
			ptrace(PTRACE_KILL, pidApp, NULL, NULL);
			break;
		}

		if (get_file_size(userOutputFilePath) > get_file_size(outputFilePath) * 2 + 1024) {
			ACflag = OUTPUT_LIMIT_EXCEED;
			ptrace(PTRACE_KILL, pidApp, NULL, NULL);
			break;
		}

		exitcode = WEXITSTATUS(status);
		/*exitcode == 5 waiting for next CPU allocation          * ruby using system to run,exit 17 ok
		 *  Runtime Error:Unknown signal xxx need be added here
                 */
		if ((language == JAVA && exitcode == 17) || exitcode == 0x05 || exitcode == 0 || exitcode == 133 ) {
			//go on and on
			;
        }
		else {
			if (DEBUG) {
				printf("status>>8=%d\n", exitcode);
			}

			if (ACflag == ACCEPTED) {
				switch (exitcode) {
				case SIGCHLD:
				case SIGALRM:
					alarm(0);
					if(DEBUG) {
                        printf("alarm:%d\n",timeLimit);
					}
				case SIGKILL:
				case SIGXCPU:
					ACflag = TIME_LIMIT_EXCEED;
					usedTime = timeLimit;
					if(DEBUG) {
                        printf("TLE:%d\n",usedTime);
					}
					break;
				case SIGXFSZ:
					ACflag = OUTPUT_LIMIT_EXCEED;
					break;
				default:
					ACflag = RUNTIME_ERROR;
				}
				fprintf(stderr, strsignal(exitcode));
			}
			ptrace(PTRACE_KILL, pidApp, NULL, NULL);
			break;
		}
		if (WIFSIGNALED(status)) {
			/*  WIFSIGNALED: if the process is terminated by signal
			 *
			 *  psignal(int sig, char *s)，like perror(char *s)，print out s, with error msg from system of sig
			 * sig = 5 means Trace/breakpoint trap
			 * sig = 11 means Segmentation fault
			 * sig = 25 means File size limit exceeded
			 */
			sig = WTERMSIG(status);

			if (DEBUG) {
				printf("WTERMSIG=%d\n", sig);
				psignal(sig, NULL);
			}
			if (ACflag == ACCEPTED) {
				switch (sig) {
				case SIGCHLD:
				case SIGALRM:
					alarm(0);
				case SIGKILL:
				case SIGXCPU:
					ACflag = TIME_LIMIT_EXCEED;
					break;
				case SIGXFSZ:
					ACflag = OUTPUT_LIMIT_EXCEED;
					break;
				default:
					ACflag = RUNTIME_ERROR;
				}
				fprintf(stderr, strsignal(sig));
			}
			break;
		}
		/*     comment from http://www.felix021.com/blog/read.php?1662
		 WIFSTOPPED: return true if the process is paused or stopped while ptrace is watching on it
		 WSTOPSIG: get the signal if it was stopped by signal
		 */

		// check the system calls
		ptrace(PTRACE_GETREGS, pidApp, NULL, &reg);
		call_id = (unsigned int) reg.REG_SYSCALL % call_array_size;
		if (call_counter[call_id] ){
			//call_counter[reg.REG_SYSCALL]--;
		} //do not limit JVM syscall for using different JVM
		else {
			ACflag = RUNTIME_ERROR;
			char error[BUFFER_SIZE];
			sprintf(error,
                    "[ERROR] A Not allowed system call: submissionId: %s, CALLID:%u\n"
                    "TO FIX THIS , ask admin to add the CALLID into corresponding LANG_XXV[] located at okcalls32/64.h ,\n"
                    "and recompile judge_client. \n"
                    "if you are admin and you don't know what to do ,\n"
                    "chinese explaination can be found on https://zhuanlan.zhihu.com/p/24498599\n",
                    "%d", call_id);
			fprintf(stderr, error);
			ptrace(PTRACE_KILL, pidApp, NULL, NULL);
		}


		ptrace(PTRACE_SYSCALL, pidApp, NULL, NULL);
		first = false;
	}
	usedTime += (ruse.ru_utime.tv_sec * 1000 + ruse.ru_utime.tv_usec / 1000) * cpu_compensation;
	usedTime += (ruse.ru_stime.tv_sec * 1000 + ruse.ru_stime.tv_usec / 1000) * cpu_compensation;
}

void execute(const int& language, const char* workspacePath,
            const char* inputFilePath, const char* outputFilePath,
            const char* userOutputFilePath, const char* errorFilePath,
            const int& timeLimit, int& usedTime, const int& memoryLimit, int& topMemory) {
    initSyscallsLimits(language);

    int pidApp = fork();
    int ACflag = ACCEPTED;

    // 如果是子进程，则运行用户程序
    if (pidApp == 0) {
        runSolution(language, workspacePath, inputFilePath, userOutputFilePath, errorFilePath, timeLimit, usedTime, memoryLimit);
    } else {
        // 父进程监控子进程运行
        watchSolution(pidApp, ACflag, userOutputFilePath, outputFilePath, errorFilePath,
                language, topMemory, memoryLimit, usedTime, timeLimit, workspacePath);
    }
}
