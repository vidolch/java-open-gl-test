package models;

import graphics.Texture;

public class TexturedModel {

    private final RawModel model;
    private final Texture texture;

    public TexturedModel(RawModel model, Texture textured) {
        this.model = model;
        this.texture = textured;
    }

    public RawModel getModel() {
        return model;
    }

    public Texture getTexture() {
        return texture;
    }
}
