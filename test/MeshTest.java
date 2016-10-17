import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Test;

import particlefluids.Mesh;

public class MeshTest {
	
	Mesh cubeMesh = Mesh.CubeMesh(new Point3d(0, 0, 0), new Point3d(1, 1, 1));

	@Test
	public void testSegmentIntersectsForCube() {
		Point3d p1 = new Point3d(1.1, 0, 0);
		Point3d p2 = new Point3d(0.8, 0.1, 0.1);
		Point3d p3 = new Point3d(0.1, -0.1, 0);
		
		System.out.println(cubeMesh.segmentIntersects(p1, p2));
		assertTrue(p1 + " and " + p2 + "Intersects", cubeMesh.segmentIntersects(p1, p2) > 0);
		System.out.println(cubeMesh.segmentIntersects(p1, p3));
		assertTrue("Does not intersect", cubeMesh.segmentIntersects(p1, p3) == -1);
	}

}
