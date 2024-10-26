package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class Level2 implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite gameSetting;
    private final Texture gameSettingHover;
    private final Sprite winDummyButton;
    private final Sprite loseDummyButton;
    private boolean isGameSettingScreenVisible;

    public Level2(Core game) {
        this.game = game;
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
                if (keycode == com.badlogic.gdx.Input.Keys.ESCAPE) {
                    toggleGameSettingScreen();
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (gameSetting.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new GameSettingScreen(game, Level2.class));
                } else if (winDummyButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new WinScreen(game, Level3.class, Level2.class));
                } else if (loseDummyButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new LoseScreen(game, Level2.class));
                }
                return true;
            }
        });
    }

    private void toggleGameSettingScreen() {
        if (isGameSettingScreenVisible) {
            game.setScreen(this);
        } else {
            game.setScreen(new GameSettingScreen(game, Level2.class));
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
