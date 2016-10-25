package pqp;

import static org.junit.Assert.*;

import org.junit.Test;

public class PQPModelTest {
	static {
		/*
		 * Need the library (libPQP.so in Linux, PQP.dll in Windows) to be put
		 * into java library path. In Linux, it is the LD_LIBRARY_PATH; in
		 * Windows, it is the PATH variable. Check using:
		 * System.getProperty("java.library.path");
		 */
		System.loadLibrary("PQP");
	}

	PQP_Model m1 = new PQP_Model();
	PQP_Model m2 = new PQP_Model();
	PQP_Model m3 = new PQP_Model();
	PQP_Model m4 = new PQP_Model();


	@Test
	public void testModelCollision() {
		DoubleArray p1 = new DoubleArray(3);
		p1.setitem(0, 0);
		p1.setitem(1, 0);
		p1.setitem(2, 0);

		DoubleArray p2 = new DoubleArray(3);
		p2.setitem(0, 0);
		p2.setitem(1, 1);
		p2.setitem(2, 0);

		DoubleArray p3 = new DoubleArray(3);
		p3.setitem(0, 0);
		p3.setitem(1, 0);
		p3.setitem(2, 1);

		DoubleArray p4 = new DoubleArray(3);
		p4.setitem(0, 1);
		p4.setitem(1, 0);
		p4.setitem(2, 0);
		
		WrapperUtil util = new WrapperUtil();
		SWIGTYPE_p_double p5 = util.newPoint(1, 1, 1);
		SWIGTYPE_p_double p6 = util.newPoint(0.01, 0.5, 0.5);
		

		SWIGTYPE_p_p_double r = util.newI3();
		SWIGTYPE_p_double t = util.newPoint(0, 0, 0);
		
		m1.BeginModel();
		m2.BeginModel();
		m3.BeginModel();

		int res = m1.AddTri(p1.cast(), p2.cast(), p3.cast(), 0);
		assertEquals(0, res);
		
		m2.AddTri(p1.cast(), p4.cast(), p4.cast(), 0);
		m3.AddTri(p4.cast(), p5, p6, 0);

		m1.EndModel();
		m2.EndModel();
		m3.EndModel();
		
		PQP_CollideResult collideResult = new PQP_CollideResult();
		PQP.PQP_Collide(collideResult, r, t, m1, r, t, m2, PQP.getPQP_FIRST_CONTACT());
		assertEquals("Segment and triangle collide", 1, collideResult.Colliding());
		
		collideResult = new PQP_CollideResult();
    PQP.PQP_Collide(collideResult, r, t, m1, r, t, m3, PQP.getPQP_FIRST_CONTACT());
    assertEquals("Two triangles do not collide", 0, collideResult.Colliding());
    
    p6 = util.newPoint(-0.01, 0.5, 0.5);
    m4.BeginModel();
    m4.AddTri(p4.cast(), p5, p6, 0);
    m4.EndModel();
    
    collideResult = new PQP_CollideResult();
    PQP.PQP_Collide(collideResult, r, t, m1, r, t, m4, PQP.getPQP_FIRST_CONTACT());
    assertEquals("Two triangles collide", 1, collideResult.Colliding());
	}

}
