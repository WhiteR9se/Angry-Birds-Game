package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class WinScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Sprite backToLevel;
    private final Texture backToLevelHover;
    private final Sprite next;
    private final Texture nextHover;
    private final Class<? extends Screen> nextLevelClass;
    private final Class<? extends Screen> currentLevelClass;
    private final Sprite repeatLevel;
    private final Texture repeatLevelHover;
    private final Sprite congratulations;
    private final Texture congratulationsHover;
    private final boolean isLevel3;

    public WinScreen(Core game, Class<? extends Screen> nextLevelClass, Class<? extends Screen> currentLevelClass) {
        this.game = game;
        this.nextLevelClass = nextLevelClass;
        this.currentLevelClass = currentLevelClass;
        this.isLevel3 = currentLevelClass == Level3.class;
        batch = new SpriteBatch();
        background = new Texture("Menu/PostGame/winScreen.png");
        bgSprite = new Sprite(background);
        backToLevel = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/backToLevel.png")));
        backToLevelHover = new Texture(Gdx.files.internal("Menu/PostGame/backToLevelHover.png"));
        next = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/next.png")));
        nextHover = new Texture(Gdx.files.internal("Menu/PostGame/nextHover.png"));
        repeatLevel = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/repeatLevel.png")));
        repeatLevelHover = new Texture(Gdx.files.internal("Menu/PostGame/repeatLevelHover.png"));

        backToLevel.setPosition(10, 900);
        congratulations = new Sprite(new Texture(Gdx.files.internal("Menu/PostGame/congratulations.png")));
        congratulationsHover = new Texture(Gdx.files.internal("Menu/PostGame/congratulations_hover.png"));
        congratulations.setPosition(700, 300);
        congratulations.setSize(500, 180);
        repeatLevel.setPosition(900, 200);
        next.setPosition(790, 390);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (isLevel3) {
                    if (congratulations.getBoundingRectangle().contains(x, y)) {
                        game.setScreen(new HomeScreen(game));
                    }
                } else {
                    if (backToLevel.getBoundingRectangle().contains(x, y)) {
                        game.setScreen(new LevelScreen(game));
                    } else if (nextLevelClass != null && next.getBoundingRectangle().contains(x, y)) {
                        try {
                            game.setScreen(nextLevelClass.getConstructor(Core.class).newInstance(game));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (repeatLevel.getBoundingRectangle().contains(x, y)) {
                        try {
                            game.setScreen(currentLevelClass.getConstructor(Core.class).newInstance(game));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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

        if (isLevel3) {
            if (congratulations.getBoundingRectangle().contains(mouseX, mouseY)) {
                congratulations.setTexture(congratulationsHover);
            } else {
                congratulations.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/congratulations.png")));
            }
        } else {
            if (backToLevel.getBoundingRectangle().contains(mouseX, mouseY)) {
                backToLevel.setTexture(backToLevelHover);
            } else {
                backToLevel.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/backToLevel.png")));
            }

            if (nextLevelClass != null && next.getBoundingRectangle().contains(mouseX, mouseY)) {
                next.setTexture(nextHover);
            } else {
                next.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/next.png")));
            }
            if (repeatLevel.getBoundingRectangle().contains(mouseX, mouseY)) {
                repeatLevel.setTexture(repeatLevelHover);
            } else {
                repeatLevel.setTexture(new Texture(Gdx.files.internal("Menu/PostGame/repeatLevel.png")));
            }
        }

        batch.begin();
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSprite.draw(batch);
        if (isLevel3) {
            congratulations.draw(batch);
        } else {
            repeatLevel.draw(batch);
            backToLevel.draw(batch);
            if (nextLevelClass != null) {
                next.draw(batch);
            }
        }
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
        backToLevel.getTexture().dispose();
        backToLevelHover.dispose();
        if (nextLevelClass != null) {
            next.getTexture().dispose();
            nextHover.dispose();
        }
        congratulations.getTexture().dispose();
        congratulationsHover.dispose();
    }
}