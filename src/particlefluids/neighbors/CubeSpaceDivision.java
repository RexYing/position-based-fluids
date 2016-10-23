package particlefluids.neighbors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3i;
import javax.vecmath.Vector3d;

import particlefluids.Particle;

/**
 * Divides a spatial cube into small cubes of equal volume
 * @author rex.ying0923
 *
 */
public class CubeSpaceDivision {

	Tuple3d space;
	
	/** Equals to the kernel radius */
	double sideLen;
	
	Map<Tuple3i, Set<Particle>> cellTable;
	
	public CubeSpaceDivision(Tuple3d space, double sideLen) {
		this.space = space;
		this.sideLen = sideLen;
		cellTable = new HashMap<>();
	}
	
	private Tuple3i computeKey(Point3d p) {
		return new Point3i((int)(p.x / sideLen), (int)(p.y / sideLen), (int)(p.z / sideLen));
	}
	
	public void addParticle(Particle p) {
		Tuple3i key = computeKey(p.x);
		if (!cellTable.containsKey(key)) {
			cellTable.put(key, new HashSet<Particle>());
		}
		cellTable.get(key).add(p);
	}
	
	/**
	 * Remove the particle from prevPos, and add the particle to a cube according to its current pos.
	 */
	public void updateParticle(Point3d prevPos, Particle p) {
		Tuple3i prevKey = computeKey(prevPos);
		if (cellTable.containsKey(prevKey)) {
			boolean result = cellTable.get(prevKey).remove(p);
			if (!result) {
				System.err.println("CubeSpaceDivision: particle NOT in cube!!!");
			}
		}
		addParticle(p);
	}
	
	public Neighbors findNeighbors(Particle p, double radius) {
		
		List<Particle> neighbors = new ArrayList<>();
		List<Double> dists = new ArrayList<>();
		
		int w = (int)(radius / sideLen) + 1;
		Tuple3i key = computeKey(p.x);
		
		for (int keyX = key.x - w; keyX <= key.x + w; keyX++) {
			for (int keyY = key.y - w; keyY <= key.y + w; keyY++) {
				for (int keyZ = key.z - w; keyZ <= key.z+ w; keyZ++) {
					Tuple3i currKey = new Point3i(keyX, keyY, keyZ);
					if (cellTable.containsKey(currKey)) {
						for (Particle particle : cellTable.get(currKey)) {
							Vector3d v = new Vector3d();
							v.sub(p.x, particle.x);
							double dist = v.length();
							if (particle != p && dist < radius) {
								neighbors.add(particle);
								dists.add(dist);
							}
						}
					}
				}
			}
		}
		return new Neighbors(neighbors, dists);
	}
}
