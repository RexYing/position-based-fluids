package particlefluids;
import javax.vecmath.*;

/**
 * Catch-all utilities (feel free to add on).
 *
 * @author Doug James, January 2007
 */
public class Utils
{
    /**
     * sum += scale*v
     */
    public static void acc(Tuple3d sum, double scale, Tuple3d v)
    {
        sum.x += scale * v.x;
        sum.y += scale * v.y;
        sum.z += scale * v.z;
    }


    /**
     *
     * @param pad Pre-padding char.
     */
    public static String getPaddedNumber(int number, int length, String pad)  {
        return getPaddedString(""+number, length, pad, true);
    }

    /**
     * @param prePad Pre-pads if true, else post pads.
     */
    public static String getPaddedString(String s, int length, String pad, boolean prePad)
    {
        if(pad.length() != 1) throw new IllegalArgumentException("pad must be a single character.");
        String result = s;
        result.trim();
        if(result.length() > length)
            throw new RuntimeException
                ("input string "+s+" is already more than length="+length);

        int nPad = (length - result.length());
        for(int k=0; k<nPad; k++) {
            if(prePad)
                result = pad + result;
            else
                result = result + pad;
        }

        return result;
    }
}
