#include "WrapperUtil.h"

PQP_REAL* WrapperUtil::newPoint(double x, double y, double z) {
  PQP_REAL* pt = new PQP_REAL[3];
  pt[0] = x;
  pt[1] = y;
  pt[2] = z;
  return pt;
}

PQP_REAL** WrapperUtil::new3x3Mat(
    double a11, double a12, double a13,
    double a21, double a22, double a23,
    double a31, double a32, double a33) {
  PQP_REAL** mat = new PQP_REAL*[3];
  mat[0] = new PQP_REAL[3] {a11, a12, a13};
  mat[1] = new PQP_REAL[3] {a21, a22, a23};
  mat[2] = new PQP_REAL[3] {a31, a32, a33};
  return mat;
}


PQP_REAL** WrapperUtil::newI3() {
  PQP_REAL** mat = new PQP_REAL*[3];
  mat[0] = new PQP_REAL[3] {1, 0, 0};
  mat[1] = new PQP_REAL[3] {0, 1, 0};
  mat[2] = new PQP_REAL[3] {0, 0, 1};
  return mat;
}

