import level.Level;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Not an engine";
    private static long window = 0;
    private static Level level;

    public static void createDisplay() {
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, 0, 0);

        if (window == 0) {
            // TODO: Handle
            System.out.println("Error occurred while creating the window");
            return;
        }

        glfwSetWindowPos(window, 100, 100); // TODO: Fix Set window pos

        glfwSetKeyCallback(window, new Input());
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwShowWindow(window);

        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public static void updateDisplay() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}