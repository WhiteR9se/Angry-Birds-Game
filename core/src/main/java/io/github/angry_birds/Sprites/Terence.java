package io.github.angry_birds.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;

public class Terence extends Sprite{
    private final Core game;
    private final Sprite terence;

    public Terence(Core game) {
        this.game = game;
        terence = new Sprite(new Texture(Gdx.files.internal("Menu/Birds/terence.png")));
    }
    public void draw(SpriteBatch batch) {
        terence.draw(batch);
    }

    public void startingPosition() {
        terence.setPosition(200, 100);
    }
}
