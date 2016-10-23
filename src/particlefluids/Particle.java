package particlefluids;

import javax.vecmath.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

/**
 * Simple particle implementation, with miscellaneous adornments.
 *
 * @author Doug James, January 2007
 * @author Eston Schweickart, February 2014
 */
public class Particle
{
	
	public static final float[] CYAN = {0f, 1f, 1f, 1f};//default: cyan
	public static final float[] BLUE = {0f, 0f, 1f, 1f};
	
    /** Radius of particle's sphere graphic. */
    public static final double PARTICLE_RADIUS = 0.010;

    /** Display list index. */
    private static int PARTICLE_DISPLAY_LIST = -1;

    /** Highlighted appearance if true, otherwise white. */
    private boolean highlight = false;
    
    /** The index of this particle in its particle system. */
    private final int index;

    /** Default mass. */
    double   m = Constants.PARTICLE_MASS;

    /** Deformed Position. */
    public final Point3d  x = new Point3d();
    
    /** Change in position to be applied */
    private Vector3d dx = new Vector3d();

    /** Undeformed/material Position. */
    Point3d  x0 = new Point3d();

    /** Velocity. */
    public final Vector3d v = new Vector3d();

    /** Force accumulator. */
    Vector3d f = new Vector3d();
    
    float[] color = CYAN;

    /**
     * Constructs particle with the specified material/undeformed
     * coordinate, x0.
     */
    public Particle(Point3d x0, int index)
    {
        this.x0.set(x0);
        x.set(x0);
        this.index = index;
    }
    
    public Particle(Point3d x0)
    {
        this(x0, 0);
    }

    /** Draws spherical particle using a display list. */
    public void display(GL2 gl)
    {
        if(PARTICLE_DISPLAY_LIST < 0) {// MAKE DISPLAY LIST:
            int displayListIndex = gl.glGenLists(1);
            GLU glu = GLU.createGLU();
            GLUquadric quadric = glu.gluNewQuadric();
            gl.glNewList(displayListIndex, GL2.GL_COMPILE);
            glu.gluSphere(quadric, PARTICLE_RADIUS, 16, 8);
            gl.glEndList();
            glu.gluDeleteQuadric(quadric);

            // For older versions of JOGL
            //glu.destroy();

            System.out.println("MADE DISPLAY LIST "+displayListIndex+" : "+gl.glIsList(displayListIndex));
            PARTICLE_DISPLAY_LIST = displayListIndex;
        }
        
        updateColor();

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, color, 0); // Color used by shader

        /// DRAW ORIGIN-CIRCLE TRANSLATED TO "p":
        gl.glPushMatrix();
        gl.glTranslated(x.x, x.y, x.z);
        gl.glCallList(PARTICLE_DISPLAY_LIST); // Draw the particle
        gl.glPopMatrix();
    }
    
    private void updateColor() {
      if(highlight) {
      	color = BLUE;
      } else {
      	color = CYAN;
      }
    	color[0] = (float)x.x;
    }

    /** Specifies whether particle should be drawn highlighted. */
    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
    /** True if particle should be drawn highlighted. */
    public boolean getHighlight() {
        return highlight;
    }
    
    public void changePos(Vector3d dx) {
    	this.dx = dx;
    }
    
    public void applyChanges() {
    	x.add(dx);
    	
    	dx = new Vector3d();
    }
    
    
    /**
     * @return the index of the particle in its particle system.
     */
    public int getIndex() {
    	return index;
    }
}
