package io.github.angry_birds.Sprites.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;

public class Corporal extends Sprite {
    private final Core game;

    public Corporal(Core game) {
        super(new Texture(Gdx.files.internal("Menu/Pigs/Corporal_pig.png")));
        this.game = game;
        //this.setPosition(290, 136);
        //this.setSize(100, 94);
    }
}
