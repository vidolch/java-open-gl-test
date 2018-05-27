
import graphics.*;
import math.*;
import level.Level;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 640;
    private static final String WINDOW_TITLE = "Not an engine";
    private static long window = 0;
    private static Level level;

    public static void createDisplay() {
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, 0, 0);
        if (window == 0) {
            // TODO: Handle
            System.out.println("Error occurred while creating the window");
            return;
        }

        glfwSetWindowPos(window, 100, 100); // TODO: Fix Set window pos

        glfwSetKeyCallback(window, new Input());

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        Shader.loadAll();

        Shader.Background.enable();
        Matrix4f pr_matrix = Matrix4f.orthographic(
                -10.0f, 10.0f,
                -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f,
                -1.0f, 1.0f);
        Shader.Background.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.Background.disable();

        level = new Level();
    }

    public static void updateDisplay() {
        glfwPollEvents();
    }

    public static void renderDisplay() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        level.render();
        int i = glGetError();
        if (i != GL_NO_ERROR ) {
            System.err.println("OpenGl error code: " + i);
        }
        glfwSwapBuffers(window);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}