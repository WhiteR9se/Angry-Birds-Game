package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Red {
    private Body body;
    private Texture texture;
    private Rectangle boundingBox;

    public Red(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0.5f, 0.5f));
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50f;
        fixtureDef.friction = 6f;
        fixtureDef.restitution = 0.25f;
        body.setMassData(new MassData() {
            {mass = 50f;}
        });
        body.createFixture(fixtureDef);
        shape.dispose();

        texture = new Texture("Menu/Birds/red.png");
        body.setLinearDamping(0f);

        boundingBox = new Rectangle(x - 0.5f, y - 0.5f, 1f, 1f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 50, 50);
        updateBoundingBox();
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture.dispose();
    }

    public float getRadius() {
        return 0.5f;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    private void updateBoundingBox() {
        boundingBox.setPosition(body.getPosition().x - 0.5f, body.getPosition().y - 0.5f);
    }
}