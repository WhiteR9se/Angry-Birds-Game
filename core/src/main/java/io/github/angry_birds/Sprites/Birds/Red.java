package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.io.Serializable;

public class Red implements Renderable, Disposable, Serializable {
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixture;
    private Texture texture;
    private TextureRegion textureRegion;
    private int hitCount;

    public Red(World world, float x, float y) {
        hitCount = 0;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixture = new FixtureDef();
        texture = new Texture("Menu/Birds/red.png");
        textureRegion = new TextureRegion(texture);
        createBody(world, x, y);
    }

    public void createBody(World world, float x, float y) {
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(25f);
        fixture.shape = circle;
        fixture.density = 1f;
        fixture.friction = 0.5f;
        MassData massData = new MassData();
        massData.mass = 50f;
        fixture.restitution = 0.25f;
        body.setAngularDamping(5f);
        body.createFixture(fixture);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setGravityScale(1);
        body.setUserData(this);
        circle.dispose();
    }

    public void hit(World world) {
        hitCount++;
    }

    public int getHit() {
        return hitCount;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, body.getPosition().x - 25f, body.getPosition().y - 25f, 50, 50);
    }

    public Body getBody() {
        return body;
    }
    public Vector2 getXY(){
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}