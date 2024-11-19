package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Terence {
    private Body body;
    private Texture texture;

    public Terence(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 500.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Birds/terence.png");
        body.setLinearDamping(0f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 50, 50);
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture.dispose();
    }
}
