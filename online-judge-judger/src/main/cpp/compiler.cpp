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


#include <stdlib.h>
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

JNIEXPORT jint JNICALL Java_cn_idealismxxm_onlinejudge_judger_core_Compiler_compile
    (JNIEnv * jniEnv, jobject selfReference, jint language, jstring compilationCommand) {
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

        // TODO 编译信息输出目录需要重新配置
        // 将编译信息重定向到 ce.txt
        freopen("ce.txt", "w", stderr);

		while(setgid(1536) != 0) {
		    sleep(1);
        }
        while(setuid(1536) != 0) {
            sleep(1);
        }
        while(setresuid(1536, 1536, 1536) != 0) {
            sleep(1);
        }

        system(jniEnv->GetStringUTFChars(compilationCommand, 0));
		exit(0);
	} else {
		int status = 0;
		waitpid(pid, &status, 0);

		return status;
	}
}
