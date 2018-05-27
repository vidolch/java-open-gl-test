import graphics.Renderer;
import graphics.StaticShader;
import graphics.Texture;
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
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] positions = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] texturedCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        RawModel model = loader.loadToVAO(positions, indices, texturedCoords);
        Texture texture = new Texture("res/bg.png");
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (running) {
            renderer.prepare();

            shader.enable();
            renderer.render(texturedModel);
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
