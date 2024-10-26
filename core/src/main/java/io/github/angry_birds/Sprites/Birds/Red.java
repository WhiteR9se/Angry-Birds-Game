package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Red extends Sprite {
    private final Core game;

    public Red(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Birds/red.png"))); // Load texture into the inherited Sprite
        this.game = game;
        this.setPosition(290, 136);  // Set initial position
        this.setSize(100, 94);
    }
}
