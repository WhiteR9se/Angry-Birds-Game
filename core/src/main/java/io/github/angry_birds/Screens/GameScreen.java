package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import io.github.angry_birds.Core;
import io.github.angry_birds.Sprites.Birds.CurrentBird;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Blocks.Ice;
import io.github.angry_birds.Sprites.Blocks.Stone;
import io.github.angry_birds.Sprites.Sling;
import io.github.angry_birds.Sprites.Blocks.Wood;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private List<CurrentBird> birds;
    private CurrentBird currentBird;
    private boolean isDragging;
    private Vector2 dragPosition;
    private Texture background;
    private Body groundBody;
    private Sling sling;
    private Wood woodBlock;
    private Stone stoneStick;
    private Ice iceBlock;
    private Red red;
    private Chuck chuck;
    private Terence terence;

    public GameScreen(Core game) {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        red = new Red(world, 300, 300);
        chuck = new Chuck(world, 90, 144);
        terence = new Terence(world, 150, 180);

        world.setContactListener(new ContactListener() {
    @Override
    public void beginContact(Contact contact) {
    Object userDataA = contact.getFixtureA().getBody().getUserData();
    Object userDataB = contact.getFixtureB().getBody().getUserData();

    if (userDataA instanceof CurrentBird || userDataB instanceof CurrentBird) {
        CurrentBird bird = (userDataA instanceof CurrentBird) ? (CurrentBird) userDataA : (CurrentBird) userDataB;
        Object other = (userDataA instanceof CurrentBird) ? userDataB : userDataA;

        if (other instanceof Wood) {
            Wood wood = (Wood) other;
            if (bird.getBody().getUserData() instanceof Terence) {
                wood.hit(3);
            } else {
                wood.hit(1);
            }
        }
        if (other instanceof Stone) {
            Stone stone = (Stone) other;
            if (bird.getBody().getUserData() instanceof Terence) {
                stone.hit(3);
            } else {
                stone.hit(1);
            }
        }
        if (other instanceof Ice) {
            Ice ice = (Ice) other;
            if (bird.getBody().getUserData() instanceof Terence) {
                ice.hit(3);
            } else {
                ice.hit(1);
            }
        }
    }
}

@Override
public void endContact(Contact contact) {
    if (woodBlock.getHit() == 2) {
        woodBlock.markForRemoval();
    }
    if (stoneStick.getHit() == 2) {
        stoneStick.markForRemoval();
    }
    if (iceBlock.getHit() == 1) {
        iceBlock.markForRemoval();
    }
}

@Override
public void preSolve(Contact contact, Manifold manifold) {
    if (woodBlock.getHit() > 2) {
        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Wood) ||
                (contact.getFixtureA().getBody().getUserData() instanceof Stone && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                (contact.getFixtureB().getBody().getUserData() instanceof Wood && contact.getFixtureA().getBody().getUserData() instanceof Stone)) {
            contact.setEnabled(false);
        }
    }
    if (stoneStick.getHit() > 2) {
        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Stone) ||
                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Stone) ||
                (contact.getFixtureA().getBody().getUserData() instanceof Wood && contact.getFixtureB().getBody().getUserData() instanceof Stone) ||
                (contact.getFixtureB().getBody().getUserData() instanceof Stone && contact.getFixtureA().getBody().getUserData() instanceof Wood)) {
            contact.setEnabled(false);
        }
    }
    if (iceBlock.getHit() > 1) {
        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Ice) ||
                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Ice) ||
                (contact.getFixtureA().getBody().getUserData() instanceof Wood && contact.getFixtureB().getBody().getUserData() instanceof Ice) ||
                (contact.getFixtureB().getBody().getUserData() instanceof Ice && contact.getFixtureA().getBody().getUserData() instanceof Wood) ||
                (contact.getFixtureA().getBody().getUserData() instanceof Stone && contact.getFixtureB().getBody().getUserData() instanceof Ice) ||
                (contact.getFixtureB().getBody().getUserData() instanceof Ice && contact.getFixtureA().getBody().getUserData() instanceof Stone)) {
            contact.setEnabled(false);
        }
    }
}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
    });
        // Initialize birds

        birds = new ArrayList<>();
        birds.add(new CurrentBird(red.getBody(), new Vector2(300, 300)));
        birds.add(new CurrentBird(chuck.getBody(), new Vector2(300, 300))); //100, 144
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300))); //200, 180
        sling = new Sling(world, 250, 144);
        woodBlock = new Wood(world, 1000, 200);
        stoneStick = new Stone(world, 700, 600);
        iceBlock = new Ice(world, 1500, 200);

        // Set the first bird as the current bird
        setCurrentBird(0);

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

        // Initialize other game objects


        // Handle touch input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (currentBird == null) return false;

                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

                if (isDragging) {
                    dragPosition = new Vector2(touchPos.x, touchPos.y);
                    float maxDragDistance = 100;
                    if (dragPosition.dst(currentBird.getInitialPosition()) > maxDragDistance) {
                        dragPosition = currentBird.getInitialPosition().cpy().lerp(dragPosition, maxDragDistance / dragPosition.dst(currentBird.getInitialPosition()));
                    }
                    currentBird.getBody().setTransform(dragPosition, 0);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (currentBird == null) return false;

                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);

                float grabRadius = 30f;
                if (currentBird.getBody().getPosition().dst(new Vector2(touchPos.x, touchPos.y)) < grabRadius) {
                    isDragging = true;
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    currentBird.setDynamic();

                    Vector2 launchVelocity = currentBird.getInitialPosition().cpy().sub(currentBird.getBody().getPosition()).scl(10);
                    currentBird.getBody().setLinearVelocity(launchVelocity);

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            setCurrentBird((birds.indexOf(currentBird) + 1) % birds.size());
                        }
                    }, 3);
                }
                return true;
            }
        });
    }

    private void setCurrentBird(int index) {
        if (currentBird != null && currentBird.getBody().getType() == BodyDef.BodyType.StaticBody) {
            currentBird.setStatic();
        }
        currentBird = birds.get(index);
        currentBird.resetPosition();
        currentBird.getBody().setTransform(300, 300, 0);
        currentBird.setStatic();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for(int i = 0; i<10; i++) {
            world.step(1 / 60f, 6, 2);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sling.render(batch);
       // for (CurrentBird bird : birds) {
       //     ((Renderable) currentBird.getBody().getUserData()).render(batch);
       // }
        red.render(batch);
        chuck.render(batch);
        terence.render(batch);
        currentBird.render(batch);
        woodBlock.render(batch);
        stoneStick.render(batch);
        iceBlock.render(batch);
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
        shapeRenderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        for (CurrentBird bird : birds) {
            ((Disposable)bird.getBody().getUserData()).dispose();
        }
        woodBlock.dispose();
        stoneStick.dispose();
        iceBlock.dispose();
        background.dispose();
    }
}