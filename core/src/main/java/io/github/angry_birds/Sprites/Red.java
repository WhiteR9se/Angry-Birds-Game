package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;

public class Red extends Sprite{
    private final Core game;
    private final Sprite redAsset;

    public Red(Core game) {
        this.game = game;
        redAsset = new Sprite(new Texture(Gdx.files.internal("Menu/Birds/chuckSpeed.png"))); //remove the chuckSpeed when red image in available.
    }
    public void drawSprite(SpriteBatch batch) {
        redAsset.draw(batch);
    }
    public void startingPosition() {
        redAsset.setPosition(300, 100);
    }

}


