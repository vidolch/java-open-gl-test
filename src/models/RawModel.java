package models;

public class RawModel {

    private int vertexCount;
    private int vaoId;

    public RawModel(int vaoID, int vertexCount) {
        this.vaoId = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getVaoId() {
        return vaoId;
    }
}
