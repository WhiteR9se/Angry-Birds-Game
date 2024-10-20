package io.github.angry_birds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angry_birds.Core;
import org.w3c.dom.Text;

public class HomeScreen implements Screen{
    private Core game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Sprite sprite;
    private final Music music;
    private Sprite play, exit, settings, sound, sound_off;

    public HomeScreen() {
        //this.game = game;
        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        sprite = new Sprite(background);
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        music.setVolume(50);

        // #sprites
        play = new Sprite(new Texture(Gdx.files.internal("Menu/Home/homePlay.png")));
        exit = new Sprite(new Texture(Gdx.files.internal("Menu/Home/exit.png")));
        settings = new Sprite(new Texture(Gdx.files.internal("Menu/Home/exit.png")));
        sound = new Sprite(new Texture(Gdx.files.internal("Menu/Home/sound.png")));
        sound_off = new Sprite(new Texture(Gdx.files.internal("Menu/Home/soundOff.png")));

        // give them positions

    }

    public void show(){}
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public void render(float x){};
    public void resize(int a, int b){};
    public void pause(){};
    public void resume(){};
    public void hide(){};
    public void dispose() {
        batch.dispose();
        background.dispose();
        music.dispose();
    }
}
