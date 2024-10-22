package io.github.angry_birds.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;

public class Settings implements Screen {
    private final Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite bgSprite;
    private final Music music;
    private final Sprite play, exit, settings;

    public Settings(Core game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("Menu/Settings/settingsScreen.png");
        bgSprite = new Sprite(background);

        // Play background music
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        music.setVolume(0);

        // Initialize buttons and other sprites
        play = new Sprite(new Texture(Gdx.files.internal("Menu/Settings/settingsPlay.png")));
        exit = new Sprite(new Texture(Gdx.files.internal("Menu/Settings/exit.png")));
        settings = new Sprite(new Texture(Gdx.files.internal("Menu/Settings/settingsSetting.png")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
