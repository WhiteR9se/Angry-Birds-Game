package io.github.angry_birds.Sprites.Pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Corporal {
    private Body body;
    private Texture texture;
    private Texture damagedTexture2, damagedTexture;
    private int hitCount;
    public boolean markedForRemoval;

    public Corporal(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(11f, 124f, new Vector2(0.5f, 0.5f), 0); // Set size to 22x248

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.9f;
        MassData massData = new MassData();
        massData.mass = 2;
        body.setMassData(massData);
        body.setUserData(this);
        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Pigs/Corporal_pig.png");
        damagedTexture = new Texture("Menu/Pigs/Corporal_pig_hurt.png");
        damagedTexture2 = new Texture("Menu/Pigs/Corporal_Pig_hurt2.png");
        hitCount = 0;
        body.setFixedRotation(true);
        markedForRemoval = false;
    }

    public void render(SpriteBatch batch) {
        if (body == null) { return; }
        if (hitCount == 0) {
            batch.draw(texture, body.getPosition().x - 11f, body.getPosition().y - 124f, 22f, 248f);
        } else if (hitCount == 1) {
            batch.draw(damagedTexture, body.getPosition().x - 11f, body.getPosition().y - 124f, 22f, 248f);
        } else if (hitCount == 2) {
            batch.draw(damagedTexture2, body.getPosition().x - 11f, body.getPosition().y - 124f, 22f, 248f);
        }
    }

    public void hit(int increment) {
        hitCount += increment;

    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public int getHit() {
        return hitCount;
    }

    public Body getBody() {
        return body;
    }

    public void setBodyNull() {
        body = null;
    }

    public void dispose() {
        texture.dispose();
        damagedTexture.dispose();
    }
}