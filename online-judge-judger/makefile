compiler: src/main/cpp/compiler.cpp
	mkdir -p target/cpp/ target/classes/
	g++ -Wall -c -std=c++11 -fPIC -I "${JAVA_HOME}/include" -I "${JAVA_HOME}/include/linux" -o target/cpp/compiler.o src/main/cpp/compiler.cpp
	g++ -Wall -std=c++11 -fPIC -shared -o target/classes/libcompiler.so target/cpp/compiler.o

executor: src/main/cpp/executor.cpp
	mkdir -p target/cpp/ target/classes/
	g++ -Wall -c -std=c++11 -fPIC -I "${JAVA_HOME}/include" -I "${JAVA_HOME}/include/linux" -o target/cpp/executor.o src/main/cpp/executor.cpp
	g++ -Wall -std=c++11 -fPIC -shared -o target/classes/libexecutor.so target/cpp/executor.o

clean:
	rm -rf target/cpp/compiler.o target/classes/libcompiler.so target/cpp/executor.o target/classes/libexecutor.so
