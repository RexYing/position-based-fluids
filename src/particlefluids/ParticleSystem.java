package particlefluids;

import java.util.*;
import javax.vecmath.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.glsl.*;

import particlefluids.neighbors.CubeSpaceDivision;
import particlefluids.neighbors.Neighbors;

/**
 * Maintains dynamic lists of Particle and Force objects, and provides access to
 * their state for numerical integration of dynamics.
 *
 * @author Doug James, January 2007
 * @author Eston Schweickart, February 2014
 */
public class ParticleSystem // implements Serializable
{
	private static final double CUBE_DIVISION_SIDE_LEN = 0.101;
	
	/** Current simulation time. */
	public double time = 0;

	/** List of Particle objects. */
	public ArrayList<Particle> P = new ArrayList<Particle>();
	private CubeSpaceDivision cubeDivision;

	/** List of Force objects. */
	public ArrayList<Force> F = new ArrayList<Force>();

	/**
	 * true iff prog has been initialized. This cannot be done in the constructor
	 * because it requires a GL2 reference.
	 */
	private boolean init = false;

	/** Filename of vertex shader source. */
	public static final String[] VERT_SOURCE = { "vert.glsl" };

	/** Filename of fragment shader source. */
	public static final String[] FRAG_SOURCE = { "frag.glsl" };

	/** The shader program used by the particles. */
	ShaderProgram prog;

	/** A list of meshes to collide with. */
	private List<Mesh> meshes = new ArrayList<>();

	/** Basic constructor. */
	public ParticleSystem() {
	}
	
	/** Bounded */
	public ParticleSystem(Tuple3d boundary) {
		cubeDivision = new CubeSpaceDivision(boundary, CUBE_DIVISION_SIDE_LEN);
	}

	/**
	 * Set up the GLSL program. This requires that the current directory (i.e. the
	 * package in which this class resides) has a vertex and fragment shader.
	 */
	public synchronized void init(GL2 gl) {
		if (init)
			return;

		prog = new ShaderProgram();
		ShaderCode vert_code = ShaderCode.create(gl, GL2ES2.GL_VERTEX_SHADER, 1, this.getClass(), VERT_SOURCE, false);
		ShaderCode frag_code = ShaderCode.create(gl, GL2ES2.GL_FRAGMENT_SHADER, 1, this.getClass(), FRAG_SOURCE, false);
		if (!prog.add(gl, vert_code, System.err) || !prog.add(gl, frag_code, System.err)) {
			System.err.println("WARNING: shader did not compile");
			prog.init(gl); // Initialize empty program
		} else {
			prog.link(gl, System.err);
		}

		init = true;
	}

	public synchronized void addMesh(Mesh m) {
		meshes.add(m);
	}

	/** Adds a force object (until removed) */
	public synchronized void addForce(Force f) {
		F.add(f);
	}

	/**
	 * Useful for removing temporary forces, such as user-interaction spring
	 * forces.
	 */
	public synchronized void removeForce(Force f) {
		F.remove(f);
	}

	/**
	 * Creates particle and adds it to the particle system.
	 * 
	 * @param p0
	 *          Undeformed/material position.
	 * @return Reference to new Particle.
	 */
	public synchronized Particle createParticle(Point3d p0) {
		Particle newP = new Particle(p0);
		P.add(newP);
		cubeDivision.addParticle(newP);
		return newP;
	}

	/**
	 * Helper-function that computes the nearest particle to the specified
	 * (deformed) position.
	 * 
	 * @return Nearest particle, or null if no particles.
	 */
	public synchronized Particle getNearestParticle(Point3d x) {
		Particle minP = null;
		double minDistSq = Double.MAX_VALUE;
		for (Particle particle : P) {
			double distSq = x.distanceSquared(particle.x);
			if (distSq < minDistSq) {
				minDistSq = distSq;
				minP = particle;
			}
		}
		return minP;
	}

	/**
	 * Moves all particles to undeformed/materials positions, and sets all
	 * velocities to zero. Synchronized to avoid problems with simultaneous calls
	 * to advanceTime().
	 */
	public synchronized void reset() {
		for (Particle p : P) {
			p.x.set(p.x0);
			p.v.set(0, 0, 0);
			p.f.set(0, 0, 0);
			p.setHighlight(false);
		}
		time = 0;
	}

	private void clearForceExceptG(Particle p) {
		p.f.set(0, -p.m * 10.f, 0);
	}

	/**
	 * Simple implementation of a first-order time step. TODO: Implement the
	 * "Position Based Fluids" integrator here
	 */
	public synchronized void advanceTime(double dt) {
		/// Clear force accumulators:
		for (Particle p : P) {
			/// Gather forces: (TODO)
			clearForceExceptG(p);
			for (Force force : F) {
				force.applyForce();
			}
		}

		/// TIME-STEP: (Symplectic Euler for now):
		for (Particle p : P) {
			p.v.scaleAdd(dt, p.f, p.v); // p.v += dt * p.f;
			
			Point3d pPrev = new Point3d(p.x);
			p.x.scaleAdd(dt, p.v, p.x); // p.x += dt * p.v;
			
			meshCollisionDetection(pPrev, p.x, p);
			cubeDivision.updateParticle(pPrev, p);
		}
		

		time += dt;
	}

	private synchronized void meshCollisionDetection(Point3d pPrev, Point3d p, Particle particle) {
		for (Mesh mesh : meshes) {
			Collision collision = mesh.segmentIntersects(pPrev, p);
			if (collision != null) {
				particle.v.scale(particle.v.length() * Constants.WALL_DAMP, collision.reflectedDirection);
				particle.x.scaleAdd(collision.dist - Constants.WALL_MARGIN, collision.direction, pPrev);
			}
		}
	}
	
	/**
	 * Displays Particle and Force objects. Modify how you like.
	 */
	public synchronized void display(GL2 gl) {
		for (Force force : F) {
			force.display(gl);
		}

		if (!init)
			init(gl);

		prog.useProgram(gl, true);

		for (Particle particle : P) {
			particle.display(gl);
		}

		prog.useProgram(gl, false);
	}
}
