package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import io.github.angry_birds.Core;

public class AboutUS implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture aboutTexture;
    private final Sprite aboutSprite;
    private final Sprite backToSetting;
    private final Texture backToSettingHover;

    public AboutUS(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        aboutTexture = new Texture(Gdx.files.internal("Menu/AboutUs/About.png"));
        aboutSprite = new Sprite(aboutTexture);
        backToSetting = new Sprite(new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBack.png")));
        backToSettingHover = new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBackHover.png"));

        backToSetting.setPosition(50, 900); // Set the position as needed

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = screenX;
                float y = Gdx.graphics.getHeight() - screenY;

                if (backToSetting.getBoundingRectangle().contains(x, y)) {
                    game.setScreen(new HomeSettingScreen(game));
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

        if (backToSetting.getBoundingRectangle().contains(mouseX, mouseY)) {
            backToSetting.setTexture(backToSettingHover);
        } else {
            backToSetting.setTexture(new Texture(Gdx.files.internal("Menu/HomeSetting/homeSettingBack.png")));
        }

        batch.begin();
        aboutSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        aboutSprite.draw(batch);
        backToSetting.draw(batch);
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
        aboutTexture.dispose();
        backToSetting.getTexture().dispose();
        backToSettingHover.dispose();
    }
}
