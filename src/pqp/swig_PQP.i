%module PQP

%{
#include "PQP.h"
#include "Build.h"
#include "BV.h"
#include "BVTQ.h"
#include "GetTime.h"
#include "MatVec.h"
#include "OBB_Disjoint.h"
#include "PQP_Compile.h"
#include "PQP_Internal.h"
#include "RectDist.h"
#include "Tri.h"
#include "TriDist.h"
#include "WrapperUtil.h"
%}

%include "PQP.h"
%include "Build.h"
%include "BV.h"
%include "BVTQ.h"
%include "GetTime.h"
%include "MatVec.h"
%include "OBB_Disjoint.h"
%include "PQP_Compile.h"
%include "PQP_Internal.h"
%include "RectDist.h"
%include "Tri.h"
%include "TriDist.h"
%include "WrapperUtil.h"
%include "carrays.i"

%array_class(PQP_REAL, DoubleArray);

