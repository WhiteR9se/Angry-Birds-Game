package io.github.angry_birds.Sprites.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Wood {
    private Body body;
    private Texture texture;
    private Texture damagedTexture;
    private int hitCount;

    public Wood(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f); // 1x1 meter square

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Blocks/wood1.png");
        damagedTexture = new Texture("Menu/Blocks/wood2.png");
        hitCount = 0;
    }

    public void render(SpriteBatch batch) {
        if (hitCount == 1) {
            batch.draw(damagedTexture, body.getPosition().x - 1f, body.getPosition().y - 1f, 2f, 2f);
        } else {
            batch.draw(texture, body.getPosition().x - 1f, body.getPosition().y - 1f, 2f, 2f);
        }
    }

    public void hit() {
        hitCount++;
        if (hitCount >= 2) {
            // Logic to remove the block from the world
            body.getWorld().destroyBody(body);
        }
    }

    public void dispose() {
        texture.dispose();
        damagedTexture.dispose();
    }
}