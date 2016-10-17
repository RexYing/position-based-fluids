package particlefluids.neighbors;

import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Test;

import particlefluids.Particle;

public class CubeSpaceDivisionTest {
	
	CubeSpaceDivision neighborFinder = new CubeSpaceDivision(new Point3d(1, 1, 1), 0.101); 

	@Test
	public void testFindNeighbors() {
		neighborFinder.addParticle(new Particle(new Point3d(0.5, 0.5, 0.5)));
		neighborFinder.addParticle(new Particle(new Point3d(0.51, 0.51, 0.51)));
		neighborFinder.addParticle(new Particle(new Point3d(0.52, 0.52, 0.52)));
		neighborFinder.addParticle(new Particle(new Point3d(0.53, 0.53, 0.53)));
		
		Particle p1 = new Particle(new Point3d(0.54, 0.54, 0.54));
		neighborFinder.addParticle(p1);
		
		Particle p = new Particle(new Point3d(0.57, 0.57, 0.57));
		Neighbors neighbors = neighborFinder.findNeighbors(p, 0.1);
		assertEquals("Number of neighbors", 3, neighbors.neighbors.size());
		
		p = new Particle(new Point3d(0.49, 0.49, 0.49));
		neighbors = neighborFinder.findNeighbors(p, 0.1);
		assertEquals("Number of neighbors", 5, neighbors.neighbors.size());
		
		
		p = new Particle(new Point3d(0.66, 0.66, 0.66));
		neighbors = neighborFinder.findNeighbors(p, 0.1);
		assertEquals("Number of neighbors", 0, neighbors.neighbors.size());
		
		Point3d p1Prev = new Point3d(p1.x);
		p1.x.x = 0.8;
		neighborFinder.updateParticle(p1Prev, p);
		
		p = new Particle(new Point3d(0.49, 0.49, 0.49));
		neighbors = neighborFinder.findNeighbors(p, 0.1);
		assertEquals("Number of neighbors", 4, neighbors.neighbors.size());
	}

}
