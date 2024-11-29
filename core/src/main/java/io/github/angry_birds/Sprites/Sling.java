package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.angry_birds.Core;

import java.io.Serializable;

public class Sling extends Sprite implements Serializable{
    private final Body slingBody;
    private final Texture slingTexture;

    public Sling(World world, float x, float y) {
        super(new Texture(Gdx.files.internal("Menu/Blocks/sling.png")));
        this.setPosition(x, y);

        // Create sling body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        slingBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 500f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f;

        slingBody.createFixture(fixtureDef);
        shape.dispose();

        slingTexture = new Texture("Menu/Blocks/sling.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(slingTexture, slingBody.getPosition().x - 0.5f, slingBody.getPosition().y - 0.5f, 81, 197);
    }

    public void dispose() {
        slingTexture.dispose();
    }

    public Body getBody() {
        return slingBody;
    }
}
