package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;
import io.github.angry_birds.Sprites.*;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Pigs.Minion;

public class Level1 implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite gameSetting;
    private final Texture gameSettingHover;
    private final Sprite winDummyButton;
    private final Sprite loseDummyButton;
    private boolean isGameSettingScreenVisible;
    private Red red;
    private Chuck chuck;
    private Terence terence;
    private Sling sling;
    private level1Structure level1Structure;
    private Minion minion;

    public Level1(Core game) {
        this.game = game;
        this.red = new Red(game);
        this.chuck = new Chuck(game);
        this.terence = new Terence(game);
        this.sling = new Sling(game);
        this.level1Structure = new level1Structure(game);
        this.minion = new Minion(game);
        batch = new SpriteBatch();
        background = new Texture("Menu/Game/background.jpg");
        bgSprite = new Sprite(background);
        gameSetting = new Sprite(new Texture(Gdx.files.internal("Menu/Game/gameSetting.png")));
        gameSettingHover = new Texture(Gdx.files.internal("Menu/Game/gameSettingHover.png"));
        winDummyButton = new Sprite(new Texture(Gdx.files.internal("Menu/Game/winDummyButton.png")));
        loseDummyButton = new Sprite(new Texture(Gdx.files.internal("Menu/Game/loseDummyButton.png")));
        gameSetting.setPosition(50, 850);
        gameSetting.setSize(200, 200);

        winDummyButton.setPosition(1500, 50);
        loseDummyButton.setPosition(1700, 50);

        isGameSettingScreenVisible = false;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    toggleGameSettingScreen();
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (gameSetting.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new GameSettingScreen(game, Level1.class));
                } else if (winDummyButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new WinScreen(game, Level2.class, Level1.class));
                } else if (loseDummyButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new LoseScreen(game, Level1.class));
                }
                return true;
            }
        });
    }

    private void toggleGameSettingScreen() {
        if (isGameSettingScreenVisible) {
            game.setScreen(this);
        } else {
            game.setScreen(new GameSettingScreen(game, Level1.class));
        }
        isGameSettingScreenVisible = !isGameSettingScreenVisible;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (gameSetting.getBoundingRectangle().contains(mouseX, mouseY)) {
            gameSetting.setTexture(gameSettingHover);
        } else {
            gameSetting.setTexture(new Texture(Gdx.files.internal("Menu/Game/gameSetting.png")));
        }

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch);
        red.draw(batch);
        chuck.draw(batch);
        terence.draw(batch);
        sling.draw(batch);
        level1Structure.setRotations();
        level1Structure.setPositions();
        level1Structure.drawSprites(batch);
        minion.level1();
        gameSetting.draw(batch);
        winDummyButton.draw(batch);
        loseDummyButton.draw(batch);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        gameSetting.getTexture().dispose();
        gameSettingHover.dispose();
        winDummyButton.getTexture().dispose();
        loseDummyButton.getTexture().dispose();
    }
}
