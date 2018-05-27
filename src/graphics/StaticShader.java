package graphics;

public class StaticShader extends Shader {

    private static final String vertex = "shaders/base.vert";
    private static final String fragment = "shaders/base.frag";

    public StaticShader() {
        super(vertex, fragment);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoord");
    }
}
