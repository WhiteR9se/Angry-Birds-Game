package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.angry_birds.Core;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Sling;

public class GameScreen implements Screen {
    private Core game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Red red;
    private Chuck chuck;
    private Terence terence;
    private boolean isDragging = false;
    private Vector2 initialPosition;
    private Vector2 slingPosition;
    private Texture background;
    private Body groundBody;
    private Sling sling;

    public GameScreen(Core game) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -200f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Initialize birds
        red = new Red(world, 300, 500);
        chuck = new Chuck(world, 300, 1000);
        terence = new Terence(world, 300, 1000);

        // Set birds to kinematic initially
        red.getBody().setType(BodyDef.BodyType.KinematicBody);
        chuck.getBody().setType(BodyDef.BodyType.KinematicBody);
        terence.getBody().setType(BodyDef.BodyType.KinematicBody);

        sling = new Sling(world, 100, 144);
        slingPosition = new Vector2(100, 144);

        // Load background texture
        background = new Texture("Menu/Game/background.jpg");

        // Create ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 0));
        groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-Gdx.graphics.getWidth(), 144), new Vector2(Gdx.graphics.getWidth(), 144));

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    isDragging = true;
                    initialPosition = screenToWorld(screenX, screenY);
                    red.getBody().setGravityScale(0); // Disable gravity
                    red.getBody().setLinearVelocity(0, 0); // Stop the bird from falling
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    Vector2 currentPosition = screenToWorld(screenX, screenY);
                    Vector2 dragVector = slingPosition.cpy().sub(currentPosition).limit(1); // Limit the drag distance
                    red.getBody().setTransform(slingPosition.cpy().sub(dragVector), 0);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT && isDragging) {
                    isDragging = false;
                    Vector2 releasePosition = screenToWorld(screenX, screenY);
                    red.getBody().setGravityScale(1); // Enable gravity
                    red.getBody().setType(BodyDef.BodyType.DynamicBody); // Change to dynamic body

                    Vector2 launchVelocity = slingPosition.cpy().sub(releasePosition).scl(10f); // Adjust the scale factor as needed
                    red.getBody().setLinearVelocity(launchVelocity);
                }
                return true;
            }
        });
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        return new Vector2(screenX, Gdx.graphics.getHeight() - screenY).scl(1 / 32f); // Adjust the scale factor as needed
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sling.render(batch);
        red.render(batch);
        chuck.render(batch);
        terence.render(batch);
        sling.render(batch);
        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
        red.dispose();
        chuck.dispose();
        terence.dispose();
        background.dispose();
    }
}
