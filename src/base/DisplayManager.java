package base;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {
    private static final int FPS = 100;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Not an engine";
    private static long window = 0;
    private static long variableYieldTime, lastTime;

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
        sync(FPS);
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private static void sync(int fps) {
        if (fps <= 0) return;

        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
        long overSleep = 0; // time the sync goes over by

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                }else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                }else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
            }
            else if (overSleep < variableYieldTime - 200*1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
            }
        }
    }
}