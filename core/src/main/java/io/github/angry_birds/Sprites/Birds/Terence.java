package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Terence extends Sprite {
    private final Core game;

    public Terence(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Birds/terence.png"))); // Load texture into the inherited Sprite
        this.game = game;
        this.setPosition(0, 130);
        this.setSize(150, 150);
    }
}
