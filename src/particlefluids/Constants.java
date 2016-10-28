package particlefluids;

/**
 * Default constants. Add your own as necessary.
 *
 * @author Doug James, January 2007
 * @author Eston Schweickart, February 2014
 */
public interface Constants
{
    /** Mass of a particle. */
    public static final double PARTICLE_MASS     = 1.0;

    /** Camera rotation speed constants. */
    public static final double CAM_SIN_THETA     = Math.sin(0.2);
    public static final double CAM_COS_THETA     = Math.cos(0.2);
    
    public static final double WALL_MARGIN = 0.01;
    public static final double WALL_DAMP = 0.5;
    
    public static final double KERNEL_RADIUS = 0.1;
    
    //public static final double REST_DENSITY = 6378; // kg m^-2
    public static final double REST_DENSITY = 68378; 
    /** Constraint Force Mixing */
    public static final double CFM_EPSILON = 5000;
    
    /** Tensile stability */
    public static final double ARTIFICIAL_PRESSURE_STRENGTH = 0.0002;
    public static final double ARTIFICIAL_PRESSURE_RADIUS = 0.03; // meter
    public static final double ARTIFICIAL_PRESSURE_POWER = 4;
    
    public static final double VORTICITY_EPS = 0.00004;
}
