package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class CurrentBird implements Serializable {
    private Body body;
    private Vector2 initialPosition;
    private String name;

    public CurrentBird(Body body, Vector2 initialPosition, String name) {
        this.body = body;
        this.initialPosition = initialPosition;
        body.setUserData(this);
        this.name= name;
    }

    public CurrentBird(World world, float x, float y, String name) {
        body = null;
        initialPosition = new Vector2(x, y);
        this.name = name;
    }


    public Body getBody() {
        return body;
    }
    public String getBirdName() {
        return name;
    }

    public Vector2 getInitialPosition() {
        return initialPosition;
    }

    public void resetPosition() {
        body.setTransform(initialPosition, 0);
        body.setLinearVelocity(0, 0);
    }

    public void setDynamic() {
        body.setType(BodyDef.BodyType.DynamicBody);
    }

    public void setStatic() {
        body.setType(BodyDef.BodyType.StaticBody);
    }
    public void render(SpriteBatch batch) {
       /* for (Body birdBody : allBirds) {
            Chuck bird = (Chuck) birdBody.getUserData();
            batch.draw(bird.getTextureRegion(), birdBody.getPosition().x - 25f, birdBody.getPosition().y - 25f, 50, 50);
        }*/
    }

}