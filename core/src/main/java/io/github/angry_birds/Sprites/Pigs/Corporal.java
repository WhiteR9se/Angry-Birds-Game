package io.github.angry_birds.Sprites.Pigs;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Corporal {
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixture;
    private Texture texture;
    private TextureRegion textureRegion;
    private int health;

    public Corporal(World world, float x, float y) {
        health = 100;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixture = new FixtureDef();
        texture = new Texture("Menu/Pigs/Corporal_pig.png");
        textureRegion = new TextureRegion(texture);
        createBody(world, x, y);
    }

    public void createBody(World world, float x, float y) {
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(30f);
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

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, body.getPosition().x - 30f, body.getPosition().y - 30f, 60, 60);
    }
    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture.dispose();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void hit(World world) {
        world.destroyBody(body);
    }
}

