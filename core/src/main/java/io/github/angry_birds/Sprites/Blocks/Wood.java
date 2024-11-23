package io.github.angry_birds.Sprites.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Wood {
    private Body body;
    private Texture texture;
    private Texture damagedTexture;
    private int hitCount;
    private Rectangle boundingBox;

    public Wood(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40.5f, 40.5f, new Vector2(0.5f, 0.5f), 0); // Set size to 81x81

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;
        MassData massData = new MassData();
        massData.mass = 20f;
        body.setMassData(massData);

        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Blocks/Wood/wood1.png");
        damagedTexture = new Texture("Menu/Blocks/Wood/wood2.png");
        hitCount = 0;

        boundingBox = new Rectangle(x - 40.5f, y - 40.5f, 81f, 81f);
    }

    public void render(SpriteBatch batch) {
        if (hitCount == 1) {
            batch.draw(damagedTexture, body.getPosition().x - 40.5f, body.getPosition().y - 40.5f, 81f, 81f);
        } else {
            batch.draw(texture, body.getPosition().x - 40.5f, body.getPosition().y - 40.5f, 81f, 81f);
        }
        updateBoundingBox();
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

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    public Body getBody() {
        return body;
    }
    private void updateBoundingBox() {
        boundingBox.setPosition(body.getPosition().x - 40.5f, body.getPosition().y - 40.5f);
    }
}