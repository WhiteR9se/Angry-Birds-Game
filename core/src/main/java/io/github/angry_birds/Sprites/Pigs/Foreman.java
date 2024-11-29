package io.github.angry_birds.Sprites.Pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angry_birds.Sprites.Birds.CurrentBird;
import io.github.angry_birds.Sprites.Blocks.Wood;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Foreman implements Serializable {
    private Body body;
    private Texture texture;
    private Texture damagedTexture;
    private int hitCount;
    public boolean markedForRemoval;
    public static List<Foreman> foremans = new ArrayList<>();
    private String name;


    public Foreman(World world, float x, float y, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        this.name = name;

        CircleShape shape = new CircleShape();
        shape.setRadius(30f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;
        MassData massData = new MassData();
        massData.mass = 3;
        body.setMassData(massData);
        body.setUserData(this);
        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Pigs/Foreman_pig.png");
        damagedTexture = new Texture("Menu/Pigs/Foreman_Pig_hurt2.png");
        hitCount = 0;
        body.setFixedRotation(true);
        markedForRemoval = false;
        foremans.add(this);
    }

    public void render(SpriteBatch batch) {
        if (body ==null) {return;}
        if (hitCount == 0) {
            batch.draw(texture, body.getPosition().x - 30f, body.getPosition().y - 30f, 60f, 60f);
        } else if (hitCount == 1) {
            batch.draw(damagedTexture, body.getPosition().x - 30f, body.getPosition().y - 30f, 60f, 60f);
        }
    }

    public void hit() {
        hitCount ++;

    }
    public void markForRemoval(){
        markedForRemoval = true;
    }

    public int getHit(){
        return hitCount;
    }

    public Body getBody() {
        return body;
    }
    public Vector2 getXY(){
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public void setBodyNull(){
        body = null;
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