package io.github.angry_birds.Sprites.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wood implements Serializable {
    private Body body;
    private Texture texture;
    private Texture damagedTexture;
    private int hitCount;
    public boolean markedForRemoval;
    public static List<Wood> woods = new ArrayList<>();
    private String name;

    public Wood(World world, float x, float y, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        this.name = name;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30f, 30f, new Vector2(0.5f, 0.5f), 0); // Set size to 60x60

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;
        MassData massData = new MassData();
        massData.mass = 2;
        body.setMassData(massData);
        body.setUserData(this);
        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Blocks/Wood/wood1.png");
        damagedTexture = new Texture("Menu/Blocks/Wood/wood2.png");
        hitCount = 0;
        body.setFixedRotation(true);
        markedForRemoval = false;
        woods.add(this);
    }

    public void render(SpriteBatch batch) {
        if (body == null) { return; }
        if (hitCount == 0) {
            batch.draw(texture, body.getPosition().x - 30f, body.getPosition().y - 30f, 60f, 60f);
        } else if (hitCount == 1) {
            batch.draw(damagedTexture, body.getPosition().x - 30f, body.getPosition().y - 30f, 60f, 60f);
        }
    }

    public void hit() {
        hitCount ++;
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

    public Vector2 getXY(){
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public void dispose() {
        texture.dispose();
        damagedTexture.dispose();
    }

    public void setHit(int hitCount) {
        this.hitCount = hitCount;
    }

    public String getName() {
        return name;
    }
}