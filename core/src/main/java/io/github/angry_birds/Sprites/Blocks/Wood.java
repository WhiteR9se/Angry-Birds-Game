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
    private boolean markedForRemoval;

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
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.1f;
        MassData massData = new MassData();
        massData.mass = 10f;
        body.setMassData(massData);

        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Blocks/Wood/wood1.png");
        damagedTexture = new Texture("Menu/Blocks/Wood/wood2.png");
        hitCount = 0;
        markedForRemoval = false;
    }

    public void render(SpriteBatch batch) {
        if (!markedForRemoval) {
            if (hitCount == 0) {
                batch.draw(texture, body.getPosition().x - 40.5f, body.getPosition().y - 40.5f, 81f, 81f);
            } else if (hitCount == 1) {
                batch.draw(damagedTexture, body.getPosition().x - 40.5f, body.getPosition().y - 40.5f, 81f, 81f);
            }
        }
    }

    public void hit(World world) {
        hitCount++;
        if (hitCount >= 1) {
            markedForRemoval = true;
            System.out.println("Marked for removal");
            cleanUp(world);
        }
    }

    public void cleanUp(World world) {
        world.destroyBody(body);
    }


    public Body getBody() {
        return body;
    }


    public void dispose() {
        texture.dispose();
        damagedTexture.dispose();
    }
}