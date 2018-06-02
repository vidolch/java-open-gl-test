package graphics;

import utils.ShaderUtils;
import math.*;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    public static final int VERTEX = 0;
    public static final int TEXTURE_COORDINATE = 1;

    private final int ID;
    private boolean enabled = false;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader(String vertex, String fragment) {
        ID = ShaderUtils.load(vertex, fragment);
        bindAttributes();
        getAllUniformLocations();
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name)) {
            return locationCache.get(name);
        }

        int result = glGetUniformLocation(ID, name);

        if (result == -1) {
            System.err.println("Could not find uniform variable " + name);
        } else {
            locationCache.put(name, result);
        }

        return result;
    }

    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1i(int location, int value) {
        if (!enabled) enable();
        glUniform1i(location, value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform1f(int location, float value) {
        if (!enabled) enable();
        glUniform1f(location, value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform2f(int location, float x, float y) {
        if (!enabled) enable();
        glUniform2f(location, x, y);
    }


    public void setUniform3f(String name, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z  );
    }

    public void setUniform3f(int location, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(location, vector.x, vector.y, vector.z  );
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void setUniformMat4f(int location, Matrix4f matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(location, false, matrix.toFloatBuffer());
    }

    public void setUniformBool(String name, boolean value) {
        glUniform1f(getUniform(name), value ? 1 : 0);
    }

    public void setUniformBool(int location, boolean value) {
        glUniform1f(location, value ? 1 : 0);
    }

    public void enable() {
        glUseProgram(ID);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

    // TODO: think about cleanUp method

    protected abstract void bindAttributes();
    protected abstract void getAllUniformLocations();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(ID, attribute,variableName);
    }
}
