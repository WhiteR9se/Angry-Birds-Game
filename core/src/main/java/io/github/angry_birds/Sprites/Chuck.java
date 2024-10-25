package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;

public class Chuck extends Sprite{
    private final Core game;
    private final Sprite chuck;

    public Chuck(Core game) {
        this.game = game;
        chuck = new Sprite(new Texture(Gdx.files.internal("Menu/Birds/chuck.png")));
    }
    public void draw(SpriteBatch batch) {
        chuck.draw(batch);
    }
    public void startingPosition() {
        chuck.setPosition(200, 100);
    }
}
