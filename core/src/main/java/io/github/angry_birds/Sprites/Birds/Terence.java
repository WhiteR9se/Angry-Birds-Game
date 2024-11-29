package io.github.angry_birds.Sprites.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.io.Serializable;

public class Terence implements Serializable {
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixture;
    private Texture texture;
    private TextureRegion textureRegion;
    private int hitCount;
    private int maxHits = 3; //Terence can take 3 hits/collisions

    public Terence(World world, float x, float y) {
        hitCount = 0;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixture = new FixtureDef();
        texture = new Texture("Menu/Birds/terence.png");
        textureRegion = new TextureRegion(texture);
        createBody(world, x, y);
    }

    public void createBody(World world, float x, float y) {
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(37.5f);
        fixture.shape = circle;
        fixture.density = 1f;
        fixture.friction = 0.5f;
        MassData massData = new MassData();
        massData.mass = 150f;
        fixture.restitution = 0.25f;
        body.setAngularDamping(5f);
        body.createFixture(fixture);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setGravityScale(1);
        body.setUserData(this);
        circle.dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, body.getPosition().x - 40f, body.getPosition().y - 40f, 80, 80);
    }
    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture.dispose();
    }
    public void hit(World world) {
        hitCount++;
    }
    public Vector2 getXY(){
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public int getHit() {
        return hitCount;
    }
}