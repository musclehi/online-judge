compiler: target/classes/libcompiler.so
	mkdir -p target/cpp/ target/classes/
	g++ -Wall -c -std=c++11 -fPIC -I "${JAVA_HOME}/include" -I "${JAVA_HOME}/include/linux" -o target/cpp/compiler.o src/main/cpp/compiler.cpp
	g++ -Wall -std=c++11 -fPIC -shared -o target/classes/libcompiler.so target/cpp/compiler.o

clean:
	rm -rf target/cpp/compiler.o target/classes/libcompiler.so
