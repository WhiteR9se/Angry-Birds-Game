package io.github.angry_birds.Sprites.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Foreman extends Sprite {
    private final Core game;

    public Foreman(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Pigs/Foreman_pig.png")));
        this.game = game;
        //this.setPosition(290, 136);
        //this.setSize(100, 94);
    }
}
