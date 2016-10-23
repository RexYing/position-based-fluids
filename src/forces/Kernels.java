package forces;

import javax.vecmath.Vector3d;

public class Kernels {
	
	public static final double RADIUS = 0.1;
	static final double POLY6_COEF = 315 / (64 * Math.PI * Math.pow(RADIUS, 9));
	
	static double poly6(double r) {
		double h = RADIUS;
		if (r <= h) {
			return Math.pow(h * h - r * r, 3) * POLY6_COEF; 
		} else {
			return 0;
		}
	}
	
	static double poly6dr(double r) {
		double h = RADIUS;
		if (r < h) {
			double sqrDiff = h * h - r * r;
			return -6 * sqrDiff * sqrDiff * r * POLY6_COEF; 
		} else {
			return 0;
		}
	}
	
	static Vector3d poly6dp(Vector3d dp) {
		double h = RADIUS;
		double r = dp.length();
		if (r < h) {
			double sqrDiff = h * h - r * r;
			double dWdr = -6 * sqrDiff * sqrDiff * r * POLY6_COEF; 
			Vector3d grad = new Vector3d(dp);
			grad.normalize();
			grad.scale(dWdr);
			return grad;
		} else {
			return new Vector3d();
		}
	}
	
	
}
