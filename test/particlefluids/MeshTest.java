package particlefluids;
import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Test;

import particlefluids.Collision;
import particlefluids.Mesh;

public class MeshTest {
	
	Mesh cubeMesh = Mesh.CubeMesh(new Point3d(0, 0, 0), new Point3d(1, 1, 1));

	@Test
	public void testSegmentIntersectsForCube() {
		Point3d p1 = new Point3d(1.1, 0, 0);
		Point3d p2 = new Point3d(0.8, 0.1, 0.1);
		Point3d p3 = new Point3d(0.1, -0.1, 0);
		Point3d p4 = new Point3d(0.5, 0.01, 0.5);
		Point3d p5 = new Point3d(0.5, -0.00, 0.5);
		
		
		assertNotNull(p1 + " and " + p2 + "Intersects", cubeMesh.segmentIntersects(p1, p2));
		assertNull("Does not intersect", cubeMesh.segmentIntersects(p1, p3));
		
		Collision collision = cubeMesh.segmentIntersects(p4, p5);
		assertEquals("Collision direction", new Point3d(0, -1, 0), collision.direction);
		assertEquals("Collision dist", 0.01, collision.dist, 0.0001);
	}

}
