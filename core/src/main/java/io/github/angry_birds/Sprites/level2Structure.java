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

public class level2Structure {
    private Wood wood1, wood2, wood3;
    private Stone stone1;
    private Ice ice1, ice2, ice3;
    public   Minion minion1;
    public  Foreman foreman1, foreman2;
    public level2Structure(World world) {
        stone1 = new Stone(world,1260, 144, "stone1");
        wood1 = new Wood(world, 1200, 144, "wood1");
        foreman1 = new Foreman(world, 1341, 144, "foreman1");
        ice1 = new Ice(world,1440, 144, "ice1");
        ice2 = new Ice(world, 1440, 144+60, "ice2");
        ice3 = new Ice(world, 1440, 144+120, "ice3");
        minion1 = new Minion(world, 1200,144+240, "minion1");
        foreman2 = new Foreman(world, 1600, 144,"foreman2");
    }
    public void render(SpriteBatch batch){
        wood1.render(batch);
        stone1.render(batch);
        ice1.render(batch); ice2.render(batch); ice3.render(batch);
        minion1.render(batch);
        foreman1.render(batch); foreman2.render(batch);
    }
    public void dispose() {
        // Dispose of all textures
        wood1.dispose();
        ice1.dispose(); ice2.dispose(); ice3.dispose();
        stone1.dispose();
        minion1.dispose();
        foreman1.dispose(); foreman2.dispose();
    }
}
