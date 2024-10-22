package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class HomeScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite play, exit, settings;
    private final Texture playHover, exitHover, settingsHover;

    public HomeScreen(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("Menu/Home/homeScreen.png");
        bgSprite = new Sprite(background);

        // Initialize buttons and hover textures
        play = new Sprite(new Texture(Gdx.files.internal("Menu/Home/homePlay.png")));
        exit = new Sprite(new Texture(Gdx.files.internal("Menu/Home/exit.png")));
        settings = new Sprite(new Texture(Gdx.files.internal("Menu/Home/homeSetting.png")));
        playHover = new Texture(Gdx.files.internal("Menu/Home/homePlayHover.png"));
        exitHover = new Texture(Gdx.files.internal("Menu/Home/exitHover.png"));
        settingsHover = new Texture(Gdx.files.internal("Menu/Home/homeSettingHover.png"));

        // Set positions for the sprites (example)
        play.setPosition(750, 425);
        exit.setPosition(750, 50);
        settings.setPosition(750, 237);

        // Set up input processor to handle touch/click events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Convert screen coordinates to world coordinates
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (play.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new LevelScreen(game));
                } else if (exit.getBoundingRectangle().contains(x, y)) {
                    Gdx.app.exit();
                } else if (settings.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeSettingScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void show() {
        // This method is called when the screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Get mouse position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Update button textures based on hover state
        if (play.getBoundingRectangle().contains(mouseX, mouseY)) {
            play.setTexture(playHover);
        } else {
            play.setTexture(new Texture(Gdx.files.internal("Menu/Home/homePlay.png")));
        }

        if (exit.getBoundingRectangle().contains(mouseX, mouseY)) {
            exit.setTexture(exitHover);
        } else {
            exit.setTexture(new Texture(Gdx.files.internal("Menu/Home/exit.png")));
        }

        if (settings.getBoundingRectangle().contains(mouseX, mouseY)) {
            settings.setTexture(settingsHover);
        } else {
            settings.setTexture(new Texture(Gdx.files.internal("Menu/Home/homeSetting.png")));
        }

        batch.begin();
        // Set the height of the bgSprite to match the window height
        bgSprite.setSize(bgSprite.getHeight() * (Gdx.graphics.getWidth() / bgSprite.getHeight()), Gdx.graphics.getHeight());
        bgSprite.draw(batch); // Draw the background
        play.draw(batch); // Draw buttons (example)
        exit.draw(batch);
        settings.draw(batch);
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
        play.getTexture().dispose();
        exit.getTexture().dispose();
        settings.getTexture().dispose();
        playHover.dispose();
        exitHover.dispose();
        settingsHover.dispose();
    }
}
