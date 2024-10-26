package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Sling extends Sprite {
    private final Core game;

    public Sling(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Blocks/sling.png")));
        this.game = game;
        this.setPosition(400, 145);
    }
}
