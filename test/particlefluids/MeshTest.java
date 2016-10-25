package particlefluids;
import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Test;

import particlefluids.Collision;
import particlefluids.Mesh;
import pqp.PQP;

public class MeshTest {
	
	Mesh cubeMesh = Mesh.CubeMesh(new Point3d(0, 0, 0), new Point3d(1, 1, 1));

	@Test
	public void testSegmentIntersectsForCube() {
		Point3d p1 = new Point3d(1.1, 0, 0);
		Point3d p2 = new Point3d(0.8, 0.1, 0.1);
		Point3d p3 = new Point3d(0.1, -0.1, 0);
		Point3d p4 = new Point3d(0.5, 0.01, 0.5);
		Point3d p5 = new Point3d(0.5, -0.001, 0.5);
		
		Point3d p6 = new Point3d(4.757250980194734E-4, 0.23218397488630357, 4.757250980194734E-4);
		Point3d p7 = new Point3d(-1.9174056270835878E-4, 0.23151447386568166, -1.9174056270835878E-4);
		
		
		
		
		assertNotNull(p1 + " and " + p2 + "Intersects", cubeMesh.segmentIntersects(p1, p2));
		assertNull("Does not intersect", cubeMesh.segmentIntersects(p1, p3));
		
		Collision collision = cubeMesh.segmentIntersects(p4, p5);
		assertEquals("Collision direction", new Point3d(0, -1, 0), collision.direction);
		//assertEquals("Collision dist", 0.01, collision.dist, 0.000001);
		
		collision = cubeMesh.segmentIntersects(p6, p7);
		assertNotNull("Collide", collision);
		
		Point3d p8 = new Point3d(0.36561825520760405, 9.896728173931198E-8, 0.365618255208254);
    Point3d p9 = new Point3d(0.36561787063511075, -4.963713995175495E-6, 0.36561787063539775);
		
		collision = cubeMesh.segmentIntersects(p8, p9);
		assertNotNull("Collide", collision);
	}

}
