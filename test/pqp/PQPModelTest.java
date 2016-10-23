package pqp;

import static org.junit.Assert.*;

import javax.media.j3d.J3DBuffer;

import org.junit.Test;

import junit.framework.TestCase;

public class PQPModelTest {
	static {
		/* Need the library (libPQP.so in Linux, PQP.dll in Windows) to be put into java library path.
		 * In Linux, it is the LD_LIBRARY_PATH; in Windows, it is the PATH variable.
		 * Check using:
		 * System.getProperty("java.library.path");
		 */
		System.loadLibrary("PQP");
	}
	
	PQP_Model m1 = new PQP_Model();
	PQP_Model m2 = new PQP_Model();
	
	@Test
	public void test() {

		assertEquals(0, 0);
	}

	@Test
	public void testModelCreation() {
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
		
		int res = m1.AddTri(p1.cast(), p2.cast(), p3.cast(), 0);
		assertEquals(0, res);

		System.out.println(0);
	}

}
