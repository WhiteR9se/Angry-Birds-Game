package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class LevelScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite level1, level2, level3;
    private final Sprite backtoHome;
    private final Texture backtoHomeHover;
    private final Texture level1Hover, level2Hover, level3Hover;

    public LevelScreen(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("Menu/Levels/blankBG.png");
        bgSprite = new Sprite(background);
        backtoHome = new Sprite(new Texture(Gdx.files.internal("Menu/Levels/backToHome.png")));
        backtoHomeHover = new Texture(Gdx.files.internal("Menu/Levels/backToHomeHover.png"));

        // Initialize level icons
        level1 = new Sprite(new Texture(Gdx.files.internal("Menu/Levels/level1.png")));
        level2 = new Sprite(new Texture(Gdx.files.internal("Menu/Levels/level2.png")));
        level3 = new Sprite(new Texture(Gdx.files.internal("Menu/Levels/level3.png")));
        level1Hover = new Texture(Gdx.files.internal("Menu/Levels/level1Hover.png"));
        level2Hover = new Texture(Gdx.files.internal("Menu/Levels/level2Hover.png"));
        level3Hover = new Texture(Gdx.files.internal("Menu/Levels/level3Hover.png"));
        backtoHome.setPosition(10, 900);
        level1.setSize(250, 240);
        level2.setSize(250, 240);
        level3.setSize(250, 240);
        level1.setPosition(500, 425);
        level2.setPosition(1100, 425);
        level3.setPosition(800, 150);
    }

    @Override
    public void show() {
        // Set up input processor to handle touch/click events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Convert screen coordinates to world coordinates
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (backtoHome.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeScreen(game));
                } else if (level1.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new Level1(game));
                } else if (level2.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new Level2(game));
                } else if (level3.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new Level3(game));
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
            backtoHome.setTexture(new Texture(Gdx.files.internal("Menu/Levels/backToHome.png")));
        }

        // Update level textures based on hover state
        if (level1.getBoundingRectangle().contains(mouseX, mouseY)) {
            level1.setTexture(level1Hover);
        } else {
            level1.setTexture(new Texture(Gdx.files.internal("Menu/Levels/level1.png")));
        }

        if (level2.getBoundingRectangle().contains(mouseX, mouseY)) {
            level2.setTexture(level2Hover);
        } else {
            level2.setTexture(new Texture(Gdx.files.internal("Menu/Levels/level2.png")));
        }

        if (level3.getBoundingRectangle().contains(mouseX, mouseY)) {
            level3.setTexture(level3Hover);
        } else {
            level3.setTexture(new Texture(Gdx.files.internal("Menu/Levels/level3.png")));
        }

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch); // Draw the background
        backtoHome.draw(batch);
        level1.draw(batch); // Draw level icons
        level2.draw(batch);
        level3.draw(batch);
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
        level1.getTexture().dispose();
        level2.getTexture().dispose();
        level3.getTexture().dispose();
        backtoHome.getTexture().dispose();
        backtoHomeHover.dispose();
    }
}
