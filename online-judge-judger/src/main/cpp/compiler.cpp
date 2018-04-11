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


#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
#include <iterator>
#include <sys/wait.h>
#include <sys/resource.h>
#include <unistd.h>

#include "compiler.h"

#define STD_MB 1048576LL

enum LanguageEnum {
    C    = 1,
    CPP  = 2,
    JAVA = 3
};

char** split(const char* command, const char* delimiter);

JNIEXPORT jint JNICALL Java_cn_idealismxxm_onlinejudge_judger_core_Compiler_compile
    (JNIEnv * jniEnv, jobject selfReference, jint language, jstring compilationCommand, jstring compilationInfoFilePath) {
	int pid = fork();
	// 如果是子进程运行，则进行编译操作
	if(pid == 0) {
	    // 设置资源限制
	    struct rlimit LIM;
		LIM.rlim_max = 6;
		LIM.rlim_cur = 6;
		setrlimit(RLIMIT_CPU, &LIM);
		alarm(6);
		LIM.rlim_max = 10 * STD_MB;
		LIM.rlim_cur = 10 * STD_MB;
		setrlimit(RLIMIT_FSIZE, &LIM);

		if(language == JAVA) {
		   LIM.rlim_max = STD_MB << 11;
		   LIM.rlim_cur = STD_MB << 11;
        } else{
		   LIM.rlim_max = STD_MB << 9;
		   LIM.rlim_cur = STD_MB << 9;
		}
		setrlimit(RLIMIT_AS, &LIM);

        // 将编译信息重定向到 工作目录的指定文件
        freopen(jniEnv -> GetStringUTFChars(compilationInfoFilePath, 0), "w", stderr);

		while(setgid(1000) != 0) {
		    sleep(1);
        }
        while(setuid(1000) != 0) {
            sleep(1);
        }
        while(setresuid(1000, 1000, 1000) != 0) {
            sleep(1);
        }

        char** argv = split(jniEnv -> GetStringUTFChars(compilationCommand, 0), " ");
        execvp(argv[0], argv);

		exit(0);
	} else {
		int status = 0;
		waitpid(pid, &status, 0);

		return status;
	}
}

/**
 * 将字符串分割成字符串数组
 * @param  command  命令
 * @return 字符串数组列表
 */
char** split(const char* command, const char* delimiter) {
    char* commandCopy = new char[strlen(command) + 1];
    strcpy(commandCopy, command);

    // 分割字符串，将指针放入 vector
    std::vector<char*> parts;
    char* tmp = strtok(commandCopy, delimiter);

    while(tmp != NULL) {
        char* part = new char[strlen(tmp) + 1];
        strcpy(part, tmp);
        parts.push_back(part);

        tmp = strtok(NULL, delimiter);
    }

    delete commandCopy;

    // vector<char*> 转成 char**
    char** result = new char*[parts.size() + 1];
    for(int i = parts.size() - 1; i >= 0; --i) {
        result[i] = parts[i];
    }
    result[parts.size()] = NULL;

    return result;
}
