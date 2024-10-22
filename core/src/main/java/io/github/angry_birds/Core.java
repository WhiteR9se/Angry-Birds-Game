package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.angry_birds.Screens.HomeScreen;

import javax.swing.text.View;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game implements ApplicationListener {
    private SpriteBatch batch;
    private Texture image;
    private OrthographicCamera camera;
    private Viewport viewport;
    public Music music;
    public boolean isSoundOn = true;
    // 16:9 aspect ratio
//    private final float WORLD_WIDTH = 1920;
//    private final float WORLD_HEIGHT = 1080;

    @Override
    public void create() {
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//        viewport.apply();
        batch = new SpriteBatch();
        image = new Texture("SplashScreen.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        music.setVolume(20);
        // Schedule a task to switch to HomeScreen after 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setScreen(new HomeScreen(Core.this));
            }
        }, 1); // 3 seconds delay
    }

    @Override
    public void resize(int width, int height){
        //viewport.update(width, height);
    }
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
