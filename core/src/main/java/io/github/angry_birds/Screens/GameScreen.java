package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angry_birds.Core;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Sling;

public class GameScreen implements Screen {
    private Core game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Red red;
    private Chuck chuck;
    private Terence terence;
    private boolean isDragging = false;
    private Vector2 redInitialPosition, chuckInitialPosition, terenceInitialPosition;
    private Vector2 dragPosition;
    private Texture background;
    private Body groundBody;
    private Sling sling;

    public GameScreen(Core game) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.8f), true);
        //world.setVelocityThreshold(1000f); // Increase the velocity threshold (default is usually low)
        debugRenderer = new Box2DDebugRenderer();

        // Initialize birds
        red = new Red(world, 100, 0); // Position Red on the sling
        chuck = new Chuck(world, 300, 144); // Position Chuck on the ground
        terence = new Terence(world, 500, 144); // Position Terence on the ground

        sling = new Sling(world, 280, 144);

        // Load background texture
        background = new Texture("Menu/Game/background.png");

        // Create ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 0));
        groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-Gdx.graphics.getWidth(), 144), new Vector2(Gdx.graphics.getWidth(), 144));

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;
        groundFixtureDef.restitution = 0.5f;

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
        EdgeShape rightWallShape = new EdgeShape();
        rightWallShape.set(new Vector2(1920, 1080), new Vector2(1920, 0));

        FixtureDef rightWallFixtureDef = new FixtureDef();
        rightWallFixtureDef.shape = rightWallShape;
        rightWallFixtureDef.friction = 0.5f;
        rightWallFixtureDef.restitution = 0.5f;

        groundBody.createFixture(rightWallFixtureDef);
        rightWallShape.dispose();

        EdgeShape leftWallShape = new EdgeShape();
        leftWallShape.set(new Vector2(0, 0), new Vector2(0, 1080));

        FixtureDef leftWallFixtureDef = new FixtureDef();
        leftWallFixtureDef.shape = leftWallShape;
        leftWallFixtureDef.friction = 0.5f;
        leftWallFixtureDef.restitution = 0.5f;

        groundBody.createFixture(leftWallFixtureDef);
        leftWallShape.dispose();

        EdgeShape upperWallShape = new EdgeShape();
        upperWallShape.set(new Vector2(0, 1080), new Vector2(1920, 1080));

        FixtureDef upperWallFixtureDef = new FixtureDef();
        upperWallFixtureDef.shape = upperWallShape;
        upperWallFixtureDef.friction = 0.5f;
        upperWallFixtureDef.restitution = 0.5f;

        groundBody.createFixture(upperWallFixtureDef);
        upperWallShape.dispose();
        // Set initial position of the bird on the sling
        redInitialPosition = new Vector2(300, 300);
        chuckInitialPosition = new Vector2(100, 144);
        terenceInitialPosition = new Vector2(200, 144);
        red.getBody().setTransform(redInitialPosition, 0);
        terence.getBody().setTransform(terenceInitialPosition, 0);
        chuck.getBody().setTransform(chuckInitialPosition, 0);
        red.getBody().setType(BodyDef.BodyType.StaticBody); // Ensure it's static initially
        Body birdBody = red.getBody();
        birdBody.setMassData(new MassData() {{ mass = 1f; }}); // Reasonable mass

        // Handle touch input
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos); // Convert screen to world coordinates

                if (isDragging) {
                    dragPosition = new Vector2(touchPos.x, touchPos.y);

                    // Limit drag position within a reasonable range around the sling
                    float maxDragDistance = 500; // Adjust for desired playability
                    if (dragPosition.dst(redInitialPosition) > maxDragDistance) {
                        dragPosition = redInitialPosition.cpy().lerp(dragPosition, maxDragDistance / dragPosition.dst(redInitialPosition));
                    }

                    red.getBody().setTransform(dragPosition, 0);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

                // Increase the threshold for grabbing the bird
                float grabRadius = 30f; // Adjust as needed
                if (red.getBody().getPosition().dst(new Vector2(touchPos.x, touchPos.y)) < grabRadius) {
                    isDragging = true;
                    dragPosition = red.getBody().getPosition();
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;

                    // Change the bird's body type to dynamic
                    red.getBody().setType(BodyDef.BodyType.DynamicBody);

                    // Calculate launch velocity as the vector difference between positions
                   Vector2 launchVelocity = redInitialPosition.cpy().sub(dragPosition).scl(10000);
                    red.getBody().setLinearVelocity(launchVelocity);

                    // Reset bird to initial position if it falls too low (optional fail-safe)
                    if (red.getBody().getPosition().y < 0) {
                        red.getBody().setTransform(redInitialPosition, 0);
                        red.getBody().setLinearVelocity(0, 0);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/60f, 6, 6);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sling.render(batch);
        red.render(batch);
        chuck.render(batch);
        terence.render(batch);
        batch.end();

        // Render hitbox using ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(red.getBody().getPosition().x, red.getBody().getPosition().y, red.getRadius());
        shapeRenderer.end();

        // debugRenderer.render(world, camera.combined);
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
        shapeRenderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        red.dispose();
        chuck.dispose();
        terence.dispose();
        background.dispose();
    }
}