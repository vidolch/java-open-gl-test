package math;

import utils.BufferUtils;

import java.nio.FloatBuffer;

public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] elements = new float[SIZE];

    public Matrix4f() { }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();

        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }
        // Diagonally setting the matrix values to 1.0f
        // 1.0f 0.0f 0.0f 0.0f
        // 0.0f 1.0f 0.0f 0.0f
        // 0.0f 0.0f 1.0f 0.0f
        // 0.0f 0.0f 0.0f 1.0f
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    public static Matrix4f orthographic(
            float left, float right,
            float bottom, float top,
            float near, float far
    ) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = 2.0f / (right - left);

        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

        result.elements[2 + 2 * 4] = 2.0f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (far + near) / (far - near);

        return result;
    }

    public static Matrix4f translate(Vector3f vector3f, Matrix4f result) {

        result.elements[0 + 3 * 4] = vector3f.x;
        result.elements[1 + 3 * 4] = vector3f.y;
        result.elements[2 + 3 * 4] = vector3f.z;

        return result;
    }

    public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f result) {
        if (result == null)
            result = new Matrix4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x*axis.y;
        float yz = axis.y*axis.z;
        float xz = axis.x*axis.z;
        float xs = axis.x*s;
        float ys = axis.y*s;
        float zs = axis.z*s;

        float f00 = axis.x*axis.x*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = axis.y*axis.y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = axis.z*axis.z*oneminusc+c;

        float t00 = result.elements[0] * f00 + result.elements[4] * f01 + result.elements[8] * f02;
        float t01 = result.elements[1] * f00 + result.elements[5] * f01 + result.elements[9] * f02;
        float t02 = result.elements[2] * f00 + result.elements[6] * f01 + result.elements[10] * f02;
        float t03 = result.elements[3] * f00 + result.elements[7] * f01 + result.elements[11] * f02;
        float t10 = result.elements[0] * f10 + result.elements[4] * f11 + result.elements[8] * f12;
        float t11 = result.elements[1] * f10 + result.elements[5] * f11 + result.elements[9] * f12;
        float t12 = result.elements[2] * f10 + result.elements[6] * f11 + result.elements[10] * f12;
        float t13 = result.elements[3] * f10 + result.elements[7] * f11 + result.elements[11] * f12;
        result.elements[8] = result.elements[0] * f20 + result.elements[4] * f21 + result.elements[8] * f22;
        result.elements[9] = result.elements[1] * f20 + result.elements[5] * f21 + result.elements[9] * f22;
        result.elements[10] = result.elements[2] * f20 + result.elements[6] * f21 + result.elements[10] * f22;
        result.elements[11] = result.elements[3] * f20 + result.elements[7] * f21 + result.elements[11] * f22;
        result.elements[0] = t00;
        result.elements[1] = t01;
        result.elements[2] = t02;
        result.elements[3] = t03;
        result.elements[4] = t10;
        result.elements[5] = t11;
        result.elements[6] = t12;
        result.elements[7] = t13;
        return result;
    }

    public Matrix4f multiply(Matrix4f matrix, Matrix4f result) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }

    public static Matrix4f scale(Vector3f vec, Matrix4f result) {
        if (result == null)
            result = new Matrix4f();
        result.elements[0] = result.elements[0] * vec.x;
        result.elements[1] = result.elements[1] * vec.x;
        result.elements[2] = result.elements[2] * vec.x;
        result.elements[3] = result.elements[3] * vec.x;
        result.elements[4] = result.elements[4] * vec.y;
        result.elements[5] = result.elements[5] * vec.y;
        result.elements[6] = result.elements[6] * vec.y;
        result.elements[7] = result.elements[7] * vec.y;
        result.elements[8] = result.elements[8] * vec.z;
        result.elements[9] = result.elements[9] * vec.z;
        result.elements[10] = result.elements[10] * vec.z;
        result.elements[11] = result.elements[11] * vec.z;
        return result;
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }
}
