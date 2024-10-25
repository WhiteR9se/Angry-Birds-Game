package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class Sling extends Sprite{
    private final Core game;
    private final Sprite sling;

    public Sling(Core game) {
        this.game = game;
        sling = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/sling.png")));
    }
    public void draw(SpriteBatch batch) {
        sling.draw(batch);
    }
    public void startingPosition() {
        sling.setPosition(500, 100);
    }
}
