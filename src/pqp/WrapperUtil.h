#include "PQP_Internal.h"

class WrapperUtil {
public:
  PQP_REAL* newPoint(double x, double y, double z);

  PQP_REAL** new3x3Mat(double a11, double a12, double a13,
          double a21, double a22, double a23,
          double a31, double a32, double a33);

  /* New Identity matrix of dimension 3x3. */
  PQP_REAL** newI3();

  /* New 3x3 matrix whose column vectors are specified by points (PQP_REAL*). */
  
};

