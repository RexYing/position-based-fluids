CC = g++

UNAME := $(shell uname)

ifeq ($(UNAME), Linux)
  SWIG_JAVA_INCLUDE = -I/usr/local/java/jdk/include -I/usr/local/java/jdk/include/linux

  LIB = libPQP.so
else
  JAVA_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include"
  JAVA_WIN_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include/win32"
  JAVA_WIN_BRIDGE_INCLUDE = "-I/cygdrive/c/Program Files/Java/jdk/include/win32/bridge"
  
  # need to include dir that contains jni.h and jni_md.h
  SWIG_JAVA_INCLUDE = $(JAVA_INCLUDE) $(JAVA_WIN_INCLUDE) 
  
  LIB = PQP.dll
endif


CFLAGS		= -O2 -I. -fno-strict-aliasing -fPIC -std=c++11
WIN_LIB_FLAGS   = -Wl,--add-stdcall-alias # might need for linking dll

.SUFFIXES: .C .cpp

OBJECTS		= lib/BV.o \
		  lib/Build.o \
		  lib/TriDist.o \
		  lib/WrapperUtil.o \
		  lib/PQP.o 
			
WRAP_OBJ  = lib/swig_PQP_wrap.o


CLEAN		= $(OBJECTS) $(WRAP_OBJ) lib/libPQP.a include/*.h

library: $(OBJECTS)
	/bin/rm -f lib/libPQP.a
	ar ruv lib/libPQP.a $(OBJECTS)

lib/BV.o: BV.cpp
	$(CC) $(CFLAGS) -c BV.cpp -o lib/BV.o
lib/PQP.o: PQP.cpp
	$(CC) $(CFLAGS) -c PQP.cpp -o lib/PQP.o
lib/Build.o: Build.cpp
	$(CC) $(CFLAGS) -c Build.cpp -o lib/Build.o
lib/TriDist.o: TriDist.cpp
	$(CC) $(CFLAGS) -c TriDist.cpp -o lib/TriDist.o
lib/WrapperUtil.o: WrapperUtil.cpp
	$(CC) $(CFLAGS) -c WrapperUtil.cpp -o lib/WrapperUtil.o
	
java: $(OBJECTS)
	swig -java -c++ -package pqp -outdir . -o swig_PQP_wrap.cpp swig_PQP.i
	$(CC) $(CFLAGS) -c swig_PQP_wrap.cpp $(SWIG_JAVA_INCLUDE) -o $(WRAP_OBJ)
	$(CC) -shared -fPIC $(OBJECTS) $(WRAP_OBJ) -o $(LIB)

clean:
	/bin/rm -f $(CLEAN)

