#! /bin/env python

import sys
import os

particleSize = 0.02

if len(sys.argv) != 2:
    print("Usage: " + sys.argv[0] + " <scene txt file>")
else:
    # Read the scene file

  for filename in os.listdir(sys.argv[1]):

    #scene = open(sys.argv[1]).readlines()[1:]
    scene = open(os.path.join(sys.argv[1], filename)).readlines()[1:]

    particleString = ''

    for l in scene:
        loc = [float(x) for x in l.split()]

        particleString += """
    <shape type="sphere">
        <transform name="toWorld">
            <scale value="%f"/>
            <translate x="%f" y="%f" z="%f"/>
        </transform>
        <ref id="water"/>
    </shape>
""" % (particleSize, loc[0], loc[1], loc[2])

    # Now write the file
    #fName = sys.argv[1][:-3]
    fName = filename[:-3]
    fName += 'xml'

    of = open(fName, 'w')
    of.write('<scene version="0.5.0">\n')
    of.write(particleString)
    of.write('</scene>')

