package utils;

import entities.Camera;
import math.Matrix4f;
import math.Vector3f;

public class MatrixUtils {

    public static Matrix4f createTransformationMatrix(
            Vector3f translation, float rx, float ry, float rz, float scale
    ) {
        Matrix4f matrix = Matrix4f.identity();
        Matrix4f.translate(translation, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0) ,matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0) ,matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1) ,matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix);

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Matrix4f.identity();

        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(0, 1, 0), viewMatrix);

        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix);
        return viewMatrix;

    }
}
