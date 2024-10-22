package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;

public class HomeScreen implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Music music;
    private final Sprite play, exit, settings;

    public HomeScreen(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("Menu/Home/homeScreen.png");
        bgSprite = new Sprite(background);

        // Play background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        music.setVolume(0);

        // Initialize buttons and other sprites
        play = new Sprite(new Texture(Gdx.files.internal("Menu/Home/homePlay.png")));
        exit = new Sprite(new Texture(Gdx.files.internal("Menu/Home/exit.png")));
        settings = new Sprite(new Texture(Gdx.files.internal("Menu/Home/homeSetting.png")));


        // Set positions for the sprites (example)
        play.setPosition(((Gdx.graphics.getWidth() - play.getWidth()) / 2) , ((Gdx.graphics.getHeight() - play.getHeight()) / 2));
        play.setSize(300, 285);
        exit.setPosition(250, 150);
        exit.setSize(100, 85);
        settings.setPosition(400, 150);
        settings.setSize(100, 85);
    }

    @Override
    public void show() {
        // This method is called when the screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        music.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        music.dispose();
        play.getTexture().dispose();
        exit.getTexture().dispose();
        settings.getTexture().dispose();
    }
}
