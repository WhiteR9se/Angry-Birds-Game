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
    private Stone stone1, stone2, stone3;
    private Ice ice1, ice2, ice3, ice4;
    private Minion minion1, minion2, minion3;
    private Foreman foreman1;
    public level2Structure(World world) {
        stone1 = new Stone(world,1000, 144);
        stone2 = new Stone(world, 1022, 144);
        stone3 = new Stone(world, 1044, 144);
        wood1 = new Wood(world, 940, 144);
        ice1 = new Ice(world, 860, 144);
        foreman1 = new Foreman(world, 1022, 144+248);
        minion1 = new Minion(world, 940,144+240);
    }
    public void render(SpriteBatch batch){
        wood1.render(batch);
        stone1.render(batch);stone2.render(batch);stone3.render(batch);
        ice1.render(batch);
        minion1.render(batch);
        foreman1.render(batch);
    }
    public void dispose() {
        // Dispose of all textures
        wood1.dispose();
        ice1.dispose();
        stone1.dispose();stone2.dispose();stone3.dispose();
        minion1.dispose();
        foreman1.dispose();
    }
}
