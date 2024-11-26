package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
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
import io.github.angry_birds.Sprites.Sling;
import io.github.angry_birds.Sprites.Blocks.Wood;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private Core game;
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
    private Red red;
    private Chuck chuck;
    private Terence terence;

    public GameScreen(Core game) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        red = new Red(world, 300, 300);
        chuck = new Chuck(world, 100, 144);
        terence = new Terence(world, 200, 180);

        // Initialize birds
        birds = new ArrayList<>();
        birds.add(new CurrentBird(red.getBody(), new Vector2(300, 300)));
        birds.add(new CurrentBird(chuck.getBody(), new Vector2(300, 300))); //100, 144
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300))); //200, 180

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

        // Initialize other game objects
        sling = new Sling(world, 280, 144);
        woodBlock = new Wood(world, 1000, 200);

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
                    }, 5);
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
        background.dispose();
    }
}