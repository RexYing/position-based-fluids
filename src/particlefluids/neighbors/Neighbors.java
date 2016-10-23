package particlefluids.neighbors;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import particlefluids.Particle;

/** Neighbors of a particle */
public class Neighbors {

	public List<Particle> neighbors;
	public List<Double> dists;
	
	private List<Vector3d> kernelGrad = new ArrayList<>();

	public Neighbors(List<Particle> neighbors, List<Double> dists) {
		this.neighbors = neighbors;
		this.dists = dists;
	}
	
	public List<Vector3d> getKernelGrad() {
		return kernelGrad;
	}

	public void setKernelGrad(List<Vector3d> kernelGrad) {
		if (kernelGrad.size() != neighbors.size()) {
			throw new RuntimeException("Gradient size does not match the number of neighbors.");
		}
		this.kernelGrad = kernelGrad;
	}
}
