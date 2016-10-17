LIBPATH=lib
CLASSPATH=.:${LIBPATH}/jogl-all.jar:${LIBPATH}/gluegen-rt.jar:${LIBPATH}/vecmath-1.5.2.jar

build:
	javac -classpath $(CLASSPATH) cs348c/particles/*.java

run:
	java -Xmx1000m -classpath $(CLASSPATH) cs348c.particles.ParticleSystemBuilder

doc:
	javadoc -d doc -classpath $(CLASSPATH) cs348c.particles

clean:
	rm -f *~ cs348c/particles/*~ cs348c/particles/*.class
