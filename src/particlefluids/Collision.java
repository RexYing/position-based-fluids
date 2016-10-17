package particlefluids;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Information about a collision
 * @author rex.ying0923
 *
 */
public class Collision {

	public Vector3d direction = new Vector3d();
	Vector3d reflectedDirection = new Vector3d();
	public double dist;
	
	public Collision(Point3d p1, Point3d p2, double dist, Vector3d normal) {
		this.dist = dist;
		direction.sub(p2, p1);
		direction.normalize();
		
		Vector3d v = new Vector3d(normal);
		v.scale(direction.dot(normal) * 2);
		reflectedDirection.sub(direction, v);
		reflectedDirection.normalize();
	}
}
