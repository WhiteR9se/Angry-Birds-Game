package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class CurrentBird {
    private Body body;
    private Vector2 initialPosition;

    public CurrentBird(Body body, Vector2 initialPosition) {
        this.body = body;
        this.initialPosition = initialPosition;
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
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