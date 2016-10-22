package forces;

public class Kernels {
	
	static final double POLY6_RADIUS = 0.1;
	
	static double poly6(double r) {
		double h = POLY6_RADIUS;
		if (r <= h) {
			return Math.pow(h * h - r * r, 3) * 315 / (64 * Math.PI * Math.pow(h, 9)); 
		} else {
			return 0;
		}
	}
	
	
}
