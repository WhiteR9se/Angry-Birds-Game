package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angry_birds.Core;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Sling;
import io.github.angry_birds.Sprites.Blocks.*;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.ContactImpulse;

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
    private Vector2 redInitialPosition, chuckInitialPosition, terenceInitialPosition, woodBlockInitialPosition;
    private Vector2 dragPosition;
    private Texture background;
    private Body groundBody;
    private Sling sling;
    private Wood woodBlock;
    private Rectangle woodBounding;

    public GameScreen(Core game) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.8f), true);

        //world.setVelocityThreshold(1000f); // Increase the velocity threshold (default is usually low)
        debugRenderer = new Box2DDebugRenderer();

        //look for collisionss
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if ((contact.getFixtureA().getBody().getUserData() instanceof Red && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                        (contact.getFixtureB().getBody().getUserData() instanceof Red && contact.getFixtureA().getBody().getUserData() instanceof Wood)) {
                    woodBlock.hit(world);
                }
            }

            @Override
            public void endContact(Contact contact) {
                if(woodBlock.getHit()==2){
                    woodBlock.markForRemoval();

                }
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {
                if(woodBlock.getHit()>1) {
                    if ((contact.getFixtureA().getBody().getUserData() instanceof Red && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                            (contact.getFixtureB().getBody().getUserData() instanceof Red && contact.getFixtureA().getBody().getUserData() instanceof Wood)){
                        contact.setEnabled(false);
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
                if(woodBlock.getHit()==1) {
                    //woodBlock.markForRemoval();
                }
                }


        });



        // Initialize birds
        red = new Red(world, 100, 0); // Position Red on the sling
        chuck = new Chuck(world, 300, 144); // Position Chuck on the ground
        terence = new Terence(world, 500, 144); // Position Terence on the ground

        sling = new Sling(world, 280, 144);
        woodBlock = new Wood(world, 1000, 200);
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
        woodBlockInitialPosition = new Vector2(1000, 200);
        red.getBody().setTransform(redInitialPosition, 0);
        terence.getBody().setTransform(terenceInitialPosition, 0);
        chuck.getBody().setTransform(chuckInitialPosition, 0);
        woodBlock.getBody().setTransform(woodBlockInitialPosition, 0);
        red.getBody().setType(BodyDef.BodyType.StaticBody); // Ensure it's static initially

        // Handle touch input
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos); // Convert screen to world coordinates

                if (isDragging) {
                    dragPosition = new Vector2(touchPos.x, touchPos.y);

                    // Limit drag position within a reasonable range around the sling
                    float maxDragDistance = 200; // Adjust for desired playability
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
                    Vector2 launchVelocity = redInitialPosition.cpy().sub(dragPosition).scl(10);
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
        /*if(woodBlock.markedForRemoval){
            world.destroyBody(woodBlock.getBody());
            woodBlock.setBodyNull();
        }*/

        for(int i = 0; i < 10; i++) {
            world.step(1/60f, 6, 2);
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sling.render(batch);
        red.render(batch);
        chuck.render(batch);
        terence.render(batch);
        woodBlock.render(batch);
        batch.end();
        // Check for collision
        //debugRenderer.render(world, camera.combined);

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
        woodBlock.dispose();
        background.dispose();
    }
}