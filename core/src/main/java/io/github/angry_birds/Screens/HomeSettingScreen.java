package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class HomeSettingScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite backtoHome;
    private final Texture backtoHomeHover;
    private final Sprite sound;
    private final Texture soundOffTexture;
    private final Sprite aboutUsButton;

    public HomeSettingScreen(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("Menu/HomeSetting/homeSetting.png");
        bgSprite = new Sprite(background);
        backtoHome = new Sprite(new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBack.png")));
        backtoHomeHover = new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBackHover.png"));
        sound = new Sprite(new Texture(Gdx.files.internal("Menu/HomeSetting/sound.png")));
        soundOffTexture = new Texture(Gdx.files.internal("Menu/HomeSetting/soundOff.png"));
        aboutUsButton = new Sprite(new Texture(Gdx.files.internal("Menu/AboutUs/aboutUsButton.png")));

        backtoHome.setPosition(750, 630);
        sound.setPosition(1000, 510);
        sound.setSize(130, 130);
        aboutUsButton.setPosition(1000, 360);
        aboutUsButton.setSize(130, 130);

        // Set the initial sound texture based on the sound state
        if (!game.isSoundOn) {
            sound.setTexture(soundOffTexture);
        }

        // Set up input processor to handle touch/click events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Convert screen coordinates to world coordinates
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (backtoHome.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeScreen(game));
                } else if (sound.getBoundingRectangle().contains(x, y)) {
                    toggleSound();
                } else if (aboutUsButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new AboutUS(game));
                }
                return true;
            }
        });
    }

    private void toggleSound() {
        if (game.isSoundOn) {
            game.music.setVolume(0);
            sound.setTexture(soundOffTexture);
        } else {
            game.music.setVolume(20);
            sound.setTexture(new Texture(Gdx.files.internal("Menu/HomeSetting/sound.png")));
        }
        game.isSoundOn = !game.isSoundOn;
    }

    @Override
    public void show() {
        // This method is called when the screen becomes the current screen
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Convert screen coordinates to world coordinates
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (backtoHome.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeScreen(game));
                } else if (sound.getBoundingRectangle().contains(x, y)) {
                    toggleSound();
                } else if (aboutUsButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new AboutUS(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Update backtoHome texture based on hover state
        if (backtoHome.getBoundingRectangle().contains(mouseX, mouseY)) {
            backtoHome.setTexture(backtoHomeHover);
        } else {
            backtoHome.setTexture(new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBack.png")));
        }

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch); // Draw the background
        backtoHome.draw(batch);
        sound.draw(batch);
        aboutUsButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle window resizing if necessary
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        // This method is called when the screen is no longer the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        backtoHome.getTexture().dispose();
        backtoHomeHover.dispose();
        sound.getTexture().dispose();
        soundOffTexture.dispose();
        aboutUsButton.getTexture().dispose();
    }
}
