package io.github.angry_birds.Sprites.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.angry_birds.Core;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Minion extends Sprite {
    private final Core game;
    private final Sprite minion1, minion2;
    private final SpriteBatch batch;

    public Minion(Core game) {
        minion1 = new Sprite(new Texture(Gdx.files.internal("Menu/Pigs/Minion_pig.png")));
        minion2 = new Sprite(new Texture(Gdx.files.internal("Menu/Pigs/Minion_pig.png")));
        this.game = game;
        this.batch = new SpriteBatch();
    }
    public void level1(){
        minion1.setPosition(1200, 145);
        minion1.setSize(100, 100);
        minion2.setPosition(1650, 145);
        minion2.setSize(100, 100);
        batch.begin();
        minion1.draw(batch);
        minion2.draw(batch);
        batch.end();
    }
}

