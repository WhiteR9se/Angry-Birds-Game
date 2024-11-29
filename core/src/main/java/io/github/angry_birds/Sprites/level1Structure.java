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

public class level1Structure {
    private Wood wood1, wood2, wood3, wood4;
    private Ice ice1, ice2;
    public  Minion minion1, minion2, minion3;
    public level1Structure(World world) {
        wood1 = new Wood(world, 1200, 144,"wood1");
        wood2 = new Wood(world, 1200, 144+60, "wood2");
        wood3 = new Wood(world,1200, 144+120, "wood3");
        wood4 = new Wood(world, 1200, 144+180, "wood4");
        ice1 = new Ice(world, 1460, 144, "ice1");
        ice2 = new Ice(world, 1460, 144+60, "ice2");
        minion2 = new Minion(world, 1200, 144+240, "minion2");
        minion1 = new Minion(world, 1000,144, "minion1");
        minion3 = new Minion(world, 1460, 144+120, "minion3");
    }
    public void render(SpriteBatch batch){
        wood1.render(batch);
        wood2.render(batch);
        wood3.render(batch);
        wood4.render(batch);
        ice1.render(batch); ice2.render(batch);
        minion1.render(batch); minion2.render(batch); minion3.render(batch);
    }
    public void dispose() {
        // Dispose of all textures
        wood1.dispose(); wood2.dispose(); wood3.dispose(); wood4.dispose();
        Wood.woods.remove(wood1); Wood.woods.remove(wood2); Wood.woods.remove(wood3); Wood.woods.remove(wood4);
        ice1.dispose();ice2.dispose();
        Ice.ices.remove(ice1); Ice.ices.remove(ice2);
        minion1.dispose();minion2.dispose();minion3.dispose();
        Minion.minions.remove(minion1); Minion.minions.remove(minion2); Minion.minions.remove(minion3);
    }
}
