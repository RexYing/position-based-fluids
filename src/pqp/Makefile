CC = g++

JAVA_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include"
JAVA_WIN_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include/win32"
JAVA_WIN_BRIDGE_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include/win32/bridge"

# need to include dir that contains jni.h and jni_md.h
SWIG_JAVA_INCLUDE = $(JAVA_INCLUDE) $(JAVA_WIN_INCLUDE) 

CFLAGS		= -O2 -I. -fno-strict-aliasing

.SUFFIXES: .C .cpp

OBJECTS		= lib/PQP.o \
		  lib/BV.o \
		  lib/Build.o \
		  lib/TriDist.o
			
WRAP_OBJ  = lib/swig_PQP_wrap.o

WIN_LIB = PQP.dll
LINUX_LIB = libPQP.so

CLEAN		= $(OBJECTS) lib/libPQP.a include/*.h

library: $(OBJECTS)
	/bin/rm -f lib/libPQP.a
	ar ruv lib/libPQP.a $(OBJECTS)
	cp PQP.h include/
	cp PQP_Compile.h include/
	cp PQP_Internal.h include/
	cp BV.h include/
	cp Tri.h include/

lib/BV.o: BV.cpp
	$(CC) $(CFLAGS) -c BV.cpp -o lib/BV.o
lib/PQP.o: PQP.cpp
	$(CC) $(CFLAGS) -c PQP.cpp -o lib/PQP.o
lib/Build.o: Build.cpp
	$(CC) $(CFLAGS) -c Build.cpp -o lib/Build.o
lib/TriDist.o: TriDist.cpp
	$(CC) $(CFLAGS) -c TriDist.cpp -o lib/TriDist.o
	
java: $(OBJECTS)
	swig -java -c++ -package pqp -outdir . -o swig_PQP_wrap.cpp swig_PQP.i
	$(CC) $(CFLAGS) -c -fpic swig_PQP_wrap.cpp $(SWIG_JAVA_INCLUDE) -o $(WRAP_OBJ)
	$(CC) -shared $(OBJECTS) $(WRAP_OBJ) -Wl,--add-stdcall-alias -o $(WIN_LIB)

clean:
	/bin/rm -f $(CLEAN)
