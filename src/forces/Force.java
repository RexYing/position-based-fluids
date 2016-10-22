package forces;

import com.jogamp.opengl.*;

import particlefluids.ParticleSystem;

/**
 * Particle system force.
 *
 * @author Doug James, January 2007
 */
public interface Force
{
    /**
     * Causes force to be applied to affected particles.
     */
    public void applyForce();

    /** Display any instructive force information, e.g., direction. */
    public void display(GL2 gl);

    /** Reference to the ParticleSystem this force affects. */
    public ParticleSystem getParticleSystem();
}
