package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.angry_birds.Sprites.Blocks.*;
import io.github.angry_birds.Sprites.Pigs.*;
import io.github.angry_birds.Core;

public class level3Structure {
    private Wood wood1, wood2, wood3;
    private Stone stone1, stone2, stone3;
    private Ice ice1, ice2, ice3, ice4;
    private Minion minion1, minion2, minion3;
    private Foreman foreman1;
    private Corporal corporal1;
    public level3Structure(World world) {
        stone1 = new Stone(world,1800, 144);
        wood1 = new Wood(world, 980, 144);
        wood2 = new Wood(world, 860, 144);
        ice1 = new Ice(world, 800, 144);
        ice2 = new Ice(world, 800, 144+60);
        corporal1 = new Corporal(world,920, 144);
        foreman1 = new Foreman(world, 980, 144+60);
        minion1 = new Minion(world, 860,144+60);
    }
    public void render(SpriteBatch batch){
        wood1.render(batch); wood2.render(batch);
        stone1.render(batch);
        ice1.render(batch); ice2.render(batch);
        minion1.render(batch);
        foreman1.render(batch);
        corporal1.render(batch);
    }
    public void dispose() {
        // Dispose of all textures
        wood1.dispose(); wood2.dispose();
        ice1.dispose(); ice2.dispose();
        stone1.dispose();
        minion1.dispose();
        foreman1.dispose();
        corporal1.dispose();
    }
}
