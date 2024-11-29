// GameSettingScreen.java
package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class GameSettingScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite soundButton;
    private final Sprite quitToHomeButton;
    private final Sprite restartButton;
    private final Sprite resumeButton;
    private final Sprite backToLevelsButton;
    private final Sprite musicButton;
    private final Class<? extends Screen> currentLevel;
    private boolean isMusicOn;
    private final Texture soundOffTexture;

    public GameSettingScreen(Core game, Class<? extends Screen> currentLevel) {
        this.game = game;
        this.currentLevel = currentLevel;
        batch = new SpriteBatch();
        background = new Texture("Menu/GameSetting/gameSetting.png");
        bgSprite = new Sprite(background);

        musicButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/music.png")));
        soundButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/sound.png")));
        soundOffTexture = (new Texture(Gdx.files.internal("Menu/GameSetting/soundOff.png")));
        quitToHomeButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/quitToHome.png")));
        restartButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/restart.png")));
        resumeButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/resume.png")));
        backToLevelsButton = new Sprite(new Texture(Gdx.files.internal("Menu/GameSetting/backToLevels.png")));

        soundButton.setPosition(1050, 360);
        soundButton.setSize(200, 200);
        quitToHomeButton.setPosition(650, 200);
        restartButton.setPosition(990, 800);
        resumeButton.setPosition(650, 800);
        backToLevelsButton.setPosition(650, 600);
        musicButton.setPosition(650, 400);

        if (!game.isSoundOn) {
            soundButton.setTexture(soundOffTexture);
        }
        isMusicOn = true; // Assume music is on initially

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (soundButton.getBoundingRectangle().contains(x, y)) {
                    toggleSound();
                } else if (quitToHomeButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeScreen(game));
                } else if (restartButton.getBoundingRectangle().contains(x, y)) {
                    try {
                        if (currentLevel == Level1.class) {
                            Level1 l1 = new Level1(game);
                            l1.gameObjects.clear();
                            l1.destroyedBodies.clear();
                            game.setScreen(l1);
                        }
                        else if (currentLevel == Level2.class) {
                            Level2 l2 = new Level2(game);
                            l2.destroyedBodies.clear();
                            game.setScreen(l2);
                        }
                        else if (currentLevel == Level3.class) {
                            Level3 l3 = new Level3(game);
                            l3.destroyedBodies.clear();
                            game.setScreen(l3);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resumeButton.getBoundingRectangle().contains(x, y)) {
                    try {
                        if(currentLevel == Level1.class) {
                            Level1 l1 = new Level1(game);
                            game.setScreen(l1);
                        } else if (currentLevel == Level2.class) {
                            game.setScreen(new Level2(game));
                        } else if (currentLevel == Level3.class) {
                            game.setScreen(new Level3(game));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (backToLevelsButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new LevelScreen(game));
                }
                return true;
            }
        });
    }

    private void toggleSound() {
        if (game.isSoundOn) {
            game.music.setVolume(0);
            soundButton.setTexture(soundOffTexture);
        } else {
            game.music.setVolume(20);
            soundButton.setTexture(new Texture(Gdx.files.internal("Menu/GameSetting/sound.png")));
        }
        game.isSoundOn = !game.isSoundOn;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch);
        soundButton.draw(batch);
        quitToHomeButton.draw(batch);
        musicButton.draw(batch);
        restartButton.draw(batch);
        resumeButton.draw(batch);
        backToLevelsButton.draw(batch);
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
        soundButton.getTexture().dispose();
        quitToHomeButton.getTexture().dispose();
        restartButton.getTexture().dispose();
        resumeButton.getTexture().dispose();
        backToLevelsButton.getTexture().dispose();
    }
}