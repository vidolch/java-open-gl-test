package graphics;

import base.DisplayManager;
import entities.Entity;
import math.Matrix4f;
import models.RawModel;
import models.TexturedModel;
import utils.MatrixUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        createProjectionMatrix();
        shader.enable();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.disable();
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(1, 0, 0, 1);
    }

    public void render(Entity entity, StaticShader shader) {
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getModel();
        glBindVertexArray(model.getVaoId());

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = MatrixUtils.createTransformationMatrix(
                entity.getPosition(),
                entity.getRx(), entity.getRy(), entity.getRz(),
                entity.getScale()
        );

        shader.loadTransformationMatrix(transformationMatrix);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float aspectRation = (float) DisplayManager.getWindowWidth() / (float) DisplayManager.getWindowHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRation);
        float x_scale = y_scale / aspectRation;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.elements[0] = x_scale;
        projectionMatrix.elements[5] = y_scale;
        projectionMatrix.elements[10] = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.elements[11] = -1;
        projectionMatrix.elements[14] = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.elements[15] = 0;
    }
}
