# CS348C Assignment \#1: Position-Based Fluids
## Introduction
In this first assignment, you will implement a particle-based fluid simulator related to smoothed particle hydrodynamics (SPH).  Your implementation will be based on the recent "Position Based Fluids" (PBF) approach described in [Macklin and Muller 2013](http://blog.mmacklin.com/publications/). You will extend a simple starter-code implementation to support the basic PBF functionality, then extend it to produce a nontrivial animation/simulation of your choosing.

### Groups
Work on your own, or in a group of at most two people. Additional work is expected from a group submission, such as a more elaborate creative artifact or modeled phenomena. PhD students are encouraged to work alone, and pursue a more challenging creative artifact. You can use the Piazza group-finding feature if you are not sure who is also looking for partners.

## Framework Code
We provide a simple framework code in Java to get you started, primarily to support basic OpenGL rendering and a simple Swing GUI. In this assignment, you will modify this package as needed.

### Software Dependency
To compile this starter code, first you will need working [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) to compile Java code and the following libraries:
1. JOGL, gluegen: JOGL version 2.2.4 is tested on both MAC OSX and Linux environment with JDK7/8. You can download it [here](http://jogamp.org/deployment/v2.2.4/archive/). You will want the file `jogamp-all-platforms.7z`. Download and extract it to proper install location. Then, make a library folder and copy over the jar files corresponding to your computer architecture, for example, on Linux, you will do something like this
```
mkdir lib
cp <install_location>/jogamp-all-platform/jar/jogl-all.jar <install_location>/jogamp-all-platform/jar/jogl-all-natives-linux-amd64.jar <install_location>/jogamp-all-platform/jar/gluegen-rt.jar <install_location>/jogamp-all-platform/jar/gluegen-rt-natives-linux-amd64.jar lib
```
In case you have already installed JOGL version higher than 2.3.0, you will want to replace `javax.media.opengl` to be `com.jogamp.opengl` to reflect the interface change. Its recommended you use version 2.2.4.

2. vecmath: vecmath version 1.5.2 is needed. You can get it [here](http://maven.geotoolkit.org/java3d/vecmath/1.5.2/). Once you've downloaded it, copy it to the library path you just made.
3. Mitsuba: Mitsuba is a rendering library created by Wenzel Jakob while he was a student at Cornell. It is the renderer we recommended for the final video that you will hand in for this assignment. More information about how to install and use can be found [here](https://www.mitsuba-renderer.org/). *Mitsuba is not needed for compiling the framework code.*

### Compile and Run
To compile and run the simulation framework, simply do
```
make
make run
```

### Rendering
To render with the Mitsuba template file:

1. In the frames folder, run the script `makeMitsubaFile.py` with
the text frame file
```
python makeMitsubaFile.py cube-drop.txt
```
This will create a file called `cube-drop.xml`, which describes the
particle positions for Mitsuba.

2. Run the mitsuba template file, telling it which particle file to use
```
mitsuba -DpFile=cube-drop.xml -o cube-drop.png particleTemplate.xml
```
You can do this for each frame, just replace cube-drop with the frame file name,
then make them into a movie. You can use mtsgui instead of mitsuba while debugging
to get an interactive viewer to adjust the camera etc.

The `particleTemplate.xml` file currently renders the particles with a blue
diffuse material. It is also possible to use a realistic water material
(currently commented out in the xml file), but it may take some fiddling
to get it to look good. You will need to tweak the camera position and view direction
to make sure it includes the entire simulation. Also feel free to tweak the lighting,
scene, materials, etc., to your heart's content.

Mitsuba will be much slower than just dumping opengl frames. Using the default `particleTemplate.xml`
file, it takes about 30 seconds to render one frame using 4 cores. You only need to render frames at
whatever framerate you want for output, probably 30 fps. By default the particle code
dumps frames at 100fps. On the flip side, if you have more time or access to
more compute power, feel free to increase the number of samples for the path tracer,
or render higher resolution images.
