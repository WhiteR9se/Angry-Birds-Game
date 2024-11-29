package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import io.github.angry_birds.Sprites.Blocks.*;
import io.github.angry_birds.Sprites.Pigs.Corporal;
import io.github.angry_birds.Sprites.Pigs.Foreman;
import io.github.angry_birds.Sprites.Pigs.Minion;
import io.github.angry_birds.Sprites.Sling;
import io.github.angry_birds.Sprites.level3Structure;
import java.util.ArrayList;
import java.util.List;

public class Level3 implements Screen {
    private final Sprite gameSetting;
    private final Texture gameSettingHover;
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
    private Minion minion;
    private Foreman foreman;
    private Corporal corporal;
    level3Structure L3;
    private boolean isGameSettingScreenVisible;
    public static ArrayList<Body> destroyedBodies = new ArrayList<>() ;
    private int currentLevel;
    private boolean isWin, isLose = false;
    private Core game;


    public Level3(Core game) {
        this.game =game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        gameSetting = new Sprite(new Texture(Gdx.files.internal("Menu/Game/gameSetting.png")));
        gameSetting.setPosition(50, 950);
        gameSetting.setSize(100, 100);
        gameSettingHover = new Texture(Gdx.files.internal("Menu/Game/gameSettingHover.png"));


        red = new Red(world, 300, 300);
        chuck = new Chuck(world, 90, 144);
        terence = new Terence(world, 150, 180);

        isGameSettingScreenVisible = false;
//        gameState = new GameState(red, chuck, terence, sling, minion, Level3.class);
        currentLevel = 3;

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
                        wood.hit();
                    }
                    if (other instanceof Stone) {
                        Stone stone = (Stone) other;
                        stone.hit();
                    }
                    if (other instanceof Ice) {
                        Ice ice = (Ice) other;
                        ice.hit();
                    }
                    if (other instanceof Minion) {
                        Minion minion = (Minion) other;
                        minion.hit();
                    }
                    if (other instanceof Corporal) {
                        Corporal corporal = (Corporal) other;
                        corporal.hit();
                    }
                    if (other instanceof Foreman) {
                        Foreman foreman = (Foreman) other;
                        foreman.hit();
                    }

                }
            }

            @Override
            public void endContact(Contact contact) {
                for (Wood wood : Wood.woods) {
                    if (wood.getHit() >1) {
                        wood.markForRemoval();
                        destroyedBodies.add(wood.getBody());
//            timeDelay.add(0.2f);
                    }
                }
                for (Stone stone : Stone.stones) {
                    if (stone.getHit() >2) {
                        stone.markForRemoval();
                        destroyedBodies.add(stone.getBody());
//                timeDelay.add(0.2f);
                    }
                }
                for(Ice ice : Ice.ices){
                    if(ice.getHit()>0){
                        ice.markForRemoval();
                        destroyedBodies.add(ice.getBody());
//                timeDelay.add(0.2f);
                    }
                }
                for(Minion minion : Minion.minions){
                    if(minion.getHit()>0){
                        minion.markForRemoval();
                        destroyedBodies.add(minion.getBody());
//                timeDelay.add(0.2f);
                    }
                }
                for(Foreman foreman : Foreman.foremans){
                    if(foreman.getHit()>1){
                        foreman.markForRemoval();
                        destroyedBodies.add(foreman.getBody());
//                timeDelay.add(0.2f);
                    }
                }
                for(Corporal corporal : Corporal.corporals) {
                    if (corporal.getHit() >2) {
                        corporal.markForRemoval();
                        destroyedBodies.add(corporal.getBody());
//                timeDelay.add(0.2f);
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {
                for (int i = 0; i<destroyedBodies.size();i++) {
                    Body destroyedBody= destroyedBodies.get(i);
                    if (contact.getFixtureA().getBody() == destroyedBody || contact.getFixtureB().getBody() == destroyedBody) {
                        contact.setEnabled(false);

                    }
                }
                for (Wood wood : Wood.woods) {
                    if (wood.getHit() > 3) {
                        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Wood) ||
                                (contact.getFixtureA().getBody().getUserData() instanceof Stone && contact.getFixtureB().getBody().getUserData() instanceof Wood) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof Wood && contact.getFixtureA().getBody().getUserData() instanceof Stone)) {
                            contact.setEnabled(false);
                        }
                    }
                }
                for (Stone stone : Stone.stones) {
                    if (stone.getHit() > 4) {
                        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Stone) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Stone) ||
                                (contact.getFixtureA().getBody().getUserData() instanceof Wood && contact.getFixtureB().getBody().getUserData() instanceof Stone) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof Stone && contact.getFixtureA().getBody().getUserData() instanceof Wood)) {
                            contact.setEnabled(false);
                        }
                    }
                }
                for (Ice ice : Ice.ices) {
                    if (ice.getHit() > 0) {
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
                for (Minion minion : Minion.minions) {
                    if (minion.getHit() > 1) {
                        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Minion) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Minion)) {
                            contact.setEnabled(false);
                        }
                    }
                }
                for (Foreman foreman : Foreman.foremans) {
                    if (foreman.getHit() > 3) {
                        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Foreman) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Foreman)) {
                            contact.setEnabled(false);
                        }
                    }
                }
                for (Corporal corporal : Corporal.corporals) {
                    if (corporal.getHit() > 4) {
                        if ((contact.getFixtureA().getBody().getUserData() instanceof CurrentBird && contact.getFixtureB().getBody().getUserData() instanceof Corporal) ||
                                (contact.getFixtureB().getBody().getUserData() instanceof CurrentBird && contact.getFixtureA().getBody().getUserData() instanceof Corporal)) {
                            contact.setEnabled(false);
                        }
                    }
                }
            }



            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) { }
        });










        // Initialize base birds

        //------------------------------------------
        //--------------------------------------------
        birds = new ArrayList<>();
        birds.add(new CurrentBird(red.getBody(), new Vector2(300, 300),"red"));
        birds.add(new CurrentBird(chuck.getBody(), new Vector2(300, 300),"chuck")); //100, 144
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence")); //200, 180
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence")); //200, 180
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence")); //200, 180
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence")); //200, 180
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence"));
        birds.add(new CurrentBird(terence.getBody(), new Vector2(300, 300), "terence"));

        setCurrentBird(0);





        sling = new Sling(world, 250, 144);

        // level structure
        L3 = new level3Structure(world);



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



        // Handle touch input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    toggleGameSettingScreen(game);
                }
                return true;
            }
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
                float xc = screenX;
                float yc = Gdx.graphics.getHeight() - screenY;
                if (gameSetting.getBoundingRectangle().contains(xc, yc)) {
                    game.setScreen(new GameSettingScreen(game, Level3.class));
                }

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

                    Vector2 launchVelocity = currentBird.getInitialPosition().cpy().sub(currentBird.getBody().getPosition()).scl(1000000);
                    currentBird.getBody().setLinearVelocity(launchVelocity);

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            birds.remove(currentBird);
                            if(!birds.isEmpty()){
                                setCurrentBird(0);
                            }
                            else{
                                currentBird= null;
                                Timer.schedule(new Timer.Task(){
                                    @Override
                                    public void run(){
                                        isLose = true;
                                    }
                                }, 5);
                            }
                        }
                    }, 5);
                }
                return true;
            }
        });
    }

    public void makeWinYes(){
        if(L3.foreman1.markedForRemoval && L3.minion1.markedForRemoval && L3.corporal1.markedForRemoval && L3.corporal2.markedForRemoval){
            isWin = true;
        }
    }
    private void toggleWinScreen(Core game){
        makeWinYes();
        if(isWin){
            game.setScreen(new WinScreen(game, Level3.class, Level3.class));
        }
    }
    public void toggleLoseScreen(Core game){
        if(isLose){
            game.setScreen(new LoseScreen(game, Level3.class));
        }
    }

    /*private void saveGameState(){
        GameState.saveState(gameState, currentLevel);
    }
    private void loadGameState() {
        gameState = GameState.loadState(currentLevel);
        // Set the game objects from the loaded game state
        gameState.getRedBody().applyToBody(red.getBody());
        gameState.getChuckBody().applyToBody(chuck.getBody());
        gameState.getTerenceBody().applyToBody(terence.getBody());
        gameState.getSlingBody().applyToBody(sling.getBody());
        gameState.getMinionBody().applyToBody(minion.getBody());
        // Set other game objects as needed
    }*/

    private void toggleGameSettingScreen(Core game) {
        if (isGameSettingScreenVisible) {
            game.setScreen(this);
        } else {
            game.setScreen(new GameSettingScreen(game, Level3.class));
        }
        isGameSettingScreenVisible = !isGameSettingScreenVisible;
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
        /*for(int i= 0;i<timeDelay.size();i++){
            timeDelay.set(i, timeDelay.get(i)-delta);
        }*/
        for(int i = 0; i<10; i++) {
            world.step(1 / 60f, 6, 2);
        }
        //world.step(1/60f, 6, 2);
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (gameSetting.getBoundingRectangle().contains(mouseX, mouseY)) {
            gameSetting.setTexture(gameSettingHover);
        } else {
            gameSetting.setTexture(new Texture(Gdx.files.internal("Menu/Game/gameSetting.png")));
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameSetting.draw(batch);
        sling.render(batch);
        red.render(batch);
        chuck.render(batch);
        terence.render(batch);
        if (currentBird != null) currentBird.render(batch);
        L3.render(batch);
        toggleWinScreen(game);
        toggleLoseScreen(game);
        batch.end();

        //debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    public ArrayList<Body> getDestroyedBodies(){
        return destroyedBodies;
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
        L3.dispose();
        background.dispose();
    }
}