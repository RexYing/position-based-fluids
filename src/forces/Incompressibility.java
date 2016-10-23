package forces;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import com.jogamp.opengl.GL2;

import particlefluids.Constants;
import particlefluids.Particle;
import particlefluids.ParticleSystem;
import particlefluids.neighbors.Neighbors;

public class Incompressibility implements Force {
	
	private static final boolean TENSILE_INSTABILITY = true;

	private ParticleSystem ps;
	
	/** Artificial pressure strength */
	private double k;
	/** Artificial pressure radius */
	private double dq;
	/** Artificial pressure power */
	private double n;
	
	private double rho0;
	
	public Incompressibility(ParticleSystem ps, 
			double restDensity, 
			double artificialPressureStrength,
			double artificialPressureRadius,
			double artificialPressurePower) {
		this.ps = ps;
		this.rho0 = restDensity;
		this.k = artificialPressureStrength;
		this.dq = artificialPressureRadius;
		this.n = artificialPressurePower;
	}
	
	@Override
	public void applyForce() {
		List<Double> lambdas = new ArrayList<>();
		List<Neighbors> neighborsList = new ArrayList<>();
		for (int i = 0; i < ps.P.size(); i++) {
			/* The length is equal to the number of neighbors of the i-th particle */
			List<Vector3d> dWdp = new ArrayList<>();
			Particle p = ps.getParticle(i);
			Neighbors neighbors = ps.findNeighbors(i, Kernels.RADIUS);
			neighborsList.add(neighbors);
			double gradSqrNorm = 0;
			double rhoi = 0;
			Vector3d dWdpi = new Vector3d();
			
			for (int j = 0; j < neighbors.dists.size(); j++) {
				gradSqrNorm += Kernels.poly6dr(neighbors.dists.get(j)) / (rho0 * rho0);
				rhoi += Constants.PARTICLE_MASS * Kernels.poly6(neighbors.dists.get(j));
				Vector3d pij = new Vector3d(p.x);
				pij.sub(neighbors.neighbors.get(j).x);
				Vector3d dWdpj = new Vector3d(Kernels.poly6dp(pij));
				/* Opposite direction for particles i and j */
				dWdpi.add(dWdpj);
				dWdpj.negate();
				dWdp.add(dWdpj);
			}
			
			neighbors.setKernelGrad(dWdp);
			rhoi += Constants.PARTICLE_MASS * Kernels.poly6(0);
			double Ci = rhoi / rho0 - 1;
			dWdpi.scale(1 / rho0);
			gradSqrNorm += dWdpi.lengthSquared();
			lambdas.add(-Ci / (gradSqrNorm + Constants.CFM_EPSILON));
		}
		
		for (int i = 0; i < ps.P.size(); i++) {
			Neighbors neighbors = neighborsList.get(i);
			List<Vector3d> kernGrad = neighbors.getKernelGrad();
			Vector3d dpi = new Vector3d();
			
			for (int j = 0; j < neighbors.dists.size(); j++) {
				Vector3d dpij = new Vector3d(kernGrad.get(j));
				
				int particleIdx = neighbors.neighbors.get(j).getIndex();
				double lambdaij = lambdas.get(i) + lambdas.get(particleIdx);
				// Tensile instability
				double scorr = 0;
				if (TENSILE_INSTABILITY) {
					Vector3d pij = new Vector3d(ps.getParticle(i).x);
					pij.sub(neighbors.neighbors.get(j).x);
					scorr = tensileInstability(pij);
				}
				
				dpij.scale(lambdaij + scorr);
				dpi.add(dpij);
			}
			dpi.scale(1 / rho0);
			
			ps.getParticle(i).changePos(dpi);
		}
		
		ps.applyChanges();
	}

	private double tensileInstability(Vector3d pij) {
		double wp = Kernels.poly6(pij.length());
		double wq = Kernels.poly6(dq);
		double scorr = -k * Math.pow(wp / wq, n);
		return scorr;
	}
	
	@Override
	public void display(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParticleSystem getParticleSystem() {
		return ps;
	}

}
