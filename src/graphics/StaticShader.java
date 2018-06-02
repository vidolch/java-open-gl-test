package graphics;

import entities.Camera;
import math.Matrix4f;
import utils.MatrixUtils;

public class StaticShader extends Shader {

    private static final String vertex = "shaders/base.vert";
    private static final String fragment = "shaders/base.frag";
    private int transformation_matrix;
    private int projection_matrix;
    private int view_matrix;

    public StaticShader() {
        super(vertex, fragment);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        transformation_matrix = super.getUniform("transformMatrix");
        projection_matrix = super.getUniform("projectionMatrix");
        view_matrix = super.getUniform("viewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        super.setUniformMat4f(transformation_matrix, matrix4f);
    }
    public void loadProjectionMatrix(Matrix4f matrix4f) {
        super.setUniformMat4f(projection_matrix, matrix4f);
    }
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = MatrixUtils.createViewMatrix(camera);

        super.setUniformMat4f(view_matrix, viewMatrix);
    }
}
