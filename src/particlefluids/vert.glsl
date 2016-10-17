#version 120

// Standard diffuse shader. Code originally from Edward Angel's OpenGL: A Primer.

varying vec3 N;
varying vec3 L;

void main()
{
  gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
  vec4 eyePosition = gl_ModelViewMatrix * gl_Vertex;
  vec4 eyeLightPos = gl_LightSource[0].position;
  
  N = normalize(gl_NormalMatrix * gl_Normal);
  L = normalize(eyeLightPos.xyz - eyePosition.xyz);
}