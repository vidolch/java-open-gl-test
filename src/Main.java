import static org.lwjgl.glfw.GLFW.*;

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
        while (running) {
            DisplayManager.updateDisplay();
            DisplayManager.renderDisplay();

            if (DisplayManager.shouldClose()) {
                running = false;
            }
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
