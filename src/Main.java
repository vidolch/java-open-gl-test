import base.DisplayManager;
import entities.Camera;
import entities.Entity;
import graphics.Renderer;
import graphics.StaticShader;
import graphics.Texture;
import math.Vector3f;
import models.Loader;
import models.RawModel;
import models.TexturedModel;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
    private Thread thread;
    private boolean running;

    private void start() {
        running = true;
        thread = new Thread(this, "notGameEngine");
        thread.start();
    }

    private void init() {
        if (!glfwInit()) {
            System.out.println("Error occurred while initializing the GLFW library");
            return;
        }

        DisplayManager.createDisplay();
    }

    public void run() {
        init();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        float[] vertices = {
                -0.5f,0.5f,0,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,0.5f,0,

                -0.5f,0.5f,1,
                -0.5f,-0.5f,1,
                0.5f,-0.5f,1,
                0.5f,0.5f,1,

                0.5f,0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1,
                0.5f,0.5f,1,

                -0.5f,0.5f,0,
                -0.5f,-0.5f,0,
                -0.5f,-0.5f,1,
                -0.5f,0.5f,1,

                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,

                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        RawModel model = loader.loadToVAO(vertices, indices, textureCoords);
        Texture texture = new Texture("res/bg.png");
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1f), 0, 0, 0, 1);

        Camera camera = new Camera();

        while (running) {
            entity.increaseRotation(0, 0, -0.01f);
            camera.move();
            renderer.prepare();

            shader.enable();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.disable();

            DisplayManager.updateDisplay();

            int i = glGetError();
            if (i != GL_NO_ERROR ) {
                System.err.println("OpenGl error code: " + i);
            }

            if (DisplayManager.shouldClose()) {
                running = false;
            }
        }

        // TODO: Shader cleanup goes here
        // TODO: Textures cleanup goes here
        loader.cleanUp();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
