/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package pqp;

public class WrapperUtil {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected WrapperUtil(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(WrapperUtil obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        PQPJNI.delete_WrapperUtil(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public SWIGTYPE_p_double newPoint(double x, double y, double z) {
    long cPtr = PQPJNI.WrapperUtil_newPoint(swigCPtr, this, x, y, z);
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public SWIGTYPE_p_p_double new3x3Mat(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
    long cPtr = PQPJNI.WrapperUtil_new3x3Mat(swigCPtr, this, a11, a12, a13, a21, a22, a23, a31, a32, a33);
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_double(cPtr, false);
  }

  public SWIGTYPE_p_p_double newI3() {
    long cPtr = PQPJNI.WrapperUtil_newI3(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_double(cPtr, false);
  }

  public WrapperUtil() {
    this(PQPJNI.new_WrapperUtil(), true);
  }

}
