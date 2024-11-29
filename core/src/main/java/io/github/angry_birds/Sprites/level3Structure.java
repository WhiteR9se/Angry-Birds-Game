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
    private Wood wood1, wood2;
    private Stone stone1, stone2;
    private Ice ice1, ice2, ice3, ice4;
    public  Minion minion1;
    public Foreman foreman1;
    public Corporal corporal1, corporal2;
    public level3Structure(World world) {
        stone1 = new Stone(world,1200, 144, "stone1");
        stone2 = new Stone(world, 1360, 144, "stone2");
        wood1 = new Wood(world, 1460, 144, "wood1");
        wood2 = new Wood(world, 1460, 144+60, "wood2");
        ice1 = new Ice(world, 1550, 144, "ice1");
        ice2 = new Ice(world, 1550, 144+60, "ice2");
        ice3 = new Ice(world, 1550, 144+120, "ice3");
        ice4 = new Ice(world, 1550, 144+180, "ice4");
        corporal1 = new Corporal(world, 1460, 144+120, "corporal1");
        foreman1 = new Foreman(world, 1700, 144, "foreman1");
        corporal2 = new Corporal(world, 1280, 144, "corporal2");
        minion1 = new Minion(world, 1400,144, "minion1");
    }
    public void render(SpriteBatch batch){
        wood1.render(batch); wood2.render(batch);
        stone1.render(batch); stone2.render(batch);
        ice1.render(batch); ice2.render(batch); ice3.render(batch); ice4.render(batch);
        minion1.render(batch);
        foreman1.render(batch);
        corporal1.render(batch); corporal2.render(batch);
    }
    public void dispose() {
        // Dispose of all textures
        wood1.dispose(); wood2.dispose();
        ice1.dispose(); ice2.dispose(); ice3.dispose(); ice4.dispose();
        stone1.dispose(); stone2.dispose();
        minion1.dispose();
        foreman1.dispose();
        corporal1.dispose(); corporal2.dispose();
    }
}
