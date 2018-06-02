#version 400 core

in vec3 position;
in vec2 textCoord;

out vec2 pass_textCoord;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {

    gl_Position = projectionMatrix * viewMatrix * transformMatrix * vec4(position, 1.0);
    pass_textCoord = textCoord;
}