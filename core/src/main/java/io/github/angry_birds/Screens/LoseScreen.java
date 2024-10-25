package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class LoseScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite retryButton;
    private final Sprite backToLevelButton;
    private final Texture retryButtonHoverTexture;
    private final Texture backToLevelButtonHoverTexture;
    private final Class<? extends Screen> currentLevel;

    public LoseScreen(Core game, Class<? extends Screen> currentLevel) {
        this.game = game;
        this.currentLevel = currentLevel;
        batch = new SpriteBatch();
        background = new Texture("Menu/PostGame/loseScreen.png");
        bgSprite = new Sprite(background);
        retryButton = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/retry.png")));
        backToLevelButton = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/backToLevel.png")));
        retryButtonHoverTexture = new Texture(Gdx.files.internal("Menu/PostGame/retryHover.png"));
        backToLevelButtonHoverTexture = new Texture(Gdx.files.internal("Menu/PostGame/backToLevelHover.png"));


        backToLevelButton.setPosition(10, 900);
        retryButton.setPosition(750, 700);
        // Set up input processor to handle touch/click events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (retryButton.getBoundingRectangle().contains(x, y)) {
                    try {
                        game.setScreen(currentLevel.getConstructor(Core.class).newInstance(game));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (backToLevelButton.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new LevelScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (retryButton.getBoundingRectangle().contains(mouseX, mouseY)) {
            retryButton.setTexture(retryButtonHoverTexture);
        } else {
            retryButton.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/retry.png")));
        }

        if (backToLevelButton.getBoundingRectangle().contains(mouseX, mouseY)) {
            backToLevelButton.setTexture(backToLevelButtonHoverTexture);
        } else {
            backToLevelButton.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/backToLevel.png")));
        }

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch);
        retryButton.draw(batch);
        backToLevelButton.draw(batch);
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
        retryButton.getTexture().dispose();
        backToLevelButton.getTexture().dispose();
        retryButtonHoverTexture.dispose();
        backToLevelButtonHoverTexture.dispose();
    }
}
