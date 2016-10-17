#version 120

// Standard diffuse shader. Code originally from Edward Angel's OpenGL: A Primer.

varying vec3 L;
varying vec3 N;

void main()
{
  vec3 normal  = normalize(N);
  vec3 light   = normalize(L);
  gl_FragColor = max(dot(normal, light), 0.0) *
          gl_FrontMaterial.diffuse*gl_LightSource[0].diffuse;
}
