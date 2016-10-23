package forces;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import com.jogamp.opengl.GL2;

import particlefluids.Particle;
import particlefluids.ParticleSystem;
import particlefluids.neighbors.Neighbors;

public class ViscosityXSPH implements Force {
	
	/** The larger C is, the larger the effect of viscosity */ 
	static final double C = 0.0000005;
	
	private ParticleSystem ps;
	
	public ViscosityXSPH(ParticleSystem ps) {
		this.ps = ps;
	}

	@Override
	public void applyForce() {
		List<Vector3d> dvList = new ArrayList<>();
		for (int j = 0; j < ps.getParticles().size(); j++) {
			Particle p = ps.getParticle(j);
			Neighbors neighbors = ps.findNeighbors(j, Kernels.RADIUS);
			Vector3d dv = new Vector3d();
			for (int i = 0; i < neighbors.dists.size(); i++) {
				Vector3d vRel = new Vector3d(); 
				vRel.sub(neighbors.neighbors.get(i).v, p.v);
				Vector3d pRel = new Vector3d();
				pRel.sub(neighbors.neighbors.get(i).x, p.x);
				dv.scaleAdd(Kernels.poly6(neighbors.dists.get(i)), vRel, dv);
			}
			dv.scale(C);
			dvList.add(dv);
		}
		
		for (int i = 0; i < dvList.size(); i++) {
			ps.getParticle(i).v.add(dvList.get(i));
		}
		//System.out.println(ps.getParticle(1).v);
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
