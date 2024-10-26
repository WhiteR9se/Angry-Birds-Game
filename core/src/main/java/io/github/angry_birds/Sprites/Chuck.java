package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Chuck extends Sprite {
    private final Core game;

    public Chuck(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Birds/chuck.png"))); // Load texture into the inherited Sprite
        this.game = game;
        this.setPosition(180, 140);
        this.setSize(100, 100);
    }
}
