package particlefluids.neighbors;

import java.util.List;

import particlefluids.Particle;

/** Neighbors of a particle */
public class Neighbors {

	public List<Particle> neighbors;
	public List<Double> dists;
	
	public Neighbors(List<Particle> neighbors, List<Double> dists) {
		this.neighbors = neighbors;
		this.dists = dists;
	}
}
