package forces;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.jogamp.opengl.GL2;

import particlefluids.Constants;
import particlefluids.Particle;
import particlefluids.ParticleSystem;
import particlefluids.neighbors.Neighbors;

public class VorticityConfinement implements Force {
  
  private ParticleSystem ps;
  
  private static final double DELTA = 0.01;
  private final Vector3d dx = new Vector3d(DELTA, 0, 0);
  private final Vector3d dy = new Vector3d(0, DELTA, 0);
  private final Vector3d dz = new Vector3d(0, 0, DELTA);
  
  private double vorticityEps;
  
  public VorticityConfinement(ParticleSystem ps, double vorticityEps) {
    this.ps = ps;
    this.vorticityEps = vorticityEps;
  }

  @Override
  public void applyForce() {
    for (int i = 0; i < ps.P.size(); i++) {
      Particle pi = ps.getParticle(i);
      Neighbors neighbors = ps.findNeighbors(i, Kernels.RADIUS);
      Vector3d omegai = new Vector3d();
      
      Vector3d omegaiPurturbedX = new Vector3d();
      Vector3d omegaiPurturbedY = new Vector3d();
      Vector3d omegaiPurturbedZ = new Vector3d();
      for (int j = 0; j < neighbors.dists.size(); j++) {
        Particle pj = neighbors.neighbors.get(j);
        Vector3d pij = new Vector3d(pj.x);
        pij.sub(pi.x);
        Vector3d dWdpj = Kernels.poly6dp(pij);
        
        Vector3d vij = new Vector3d(pj.v);
        vij.sub(pi.v);
        
        Vector3d crossij = new Vector3d();  
        crossij.cross(dWdpj, vij);
        
        omegai.add(crossij);
        
        Vector3d pijPurturbedX = new Vector3d(pij);
        Vector3d pijPurturbedY = new Vector3d(pij);
        Vector3d pijPurturbedZ = new Vector3d(pij);
        pijPurturbedX.add(dx);
        pijPurturbedY.add(dy);
        pijPurturbedZ.add(dz);
        crossij.cross(Kernels.poly6dp(pijPurturbedX), vij);
        omegaiPurturbedX.add(crossij);
        crossij.cross(Kernels.poly6dp(pijPurturbedY), vij);
        omegaiPurturbedY.add(crossij);
        crossij.cross(Kernels.poly6dp(pijPurturbedZ), vij);
        omegaiPurturbedZ.add(crossij);
      }
      if (omegai.x == 0 && omegai.y == 0 && omegai.z == 0) {
        return;
      }
      Vector3d eta = new Vector3d(
          (omegaiPurturbedX.length() - omegai.length()) / DELTA,
          (omegaiPurturbedY.length() - omegai.length()) / DELTA,
          (omegaiPurturbedZ.length() - omegai.length()) / DELTA);
      
      eta.normalize();
      
      Vector3d fVorticity = new Vector3d();
      fVorticity.cross(eta, omegai);
      fVorticity.scale(vorticityEps);
      
      pi.changeForce(fVorticity);
      
    }
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
