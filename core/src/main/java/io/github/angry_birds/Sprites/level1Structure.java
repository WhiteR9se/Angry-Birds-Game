package io.github.angry_birds.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.angry_birds.Core;

public class level1Structure extends Actor {
    private Sprite wood1, wood2, woodHollow, woodRectangle, woodStick1, woodStick2, woodStick3,woodStick4, woodStick5, woodStick6, woodStick7, woodStick8, woodStick9, woodStick10;
    private Sprite stone1, stoneHollow, stoneRectangle, stoneStick;
    private Sprite ice1, iceHollow, iceRectangle, iceStick1, iceStick2, iceStick3;

    public level1Structure(Core game) {
        wood1 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood1.png")));
        woodHollow = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_hollow1.png")));
        woodRectangle = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_rectangle1.png")));
        woodStick1 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick2 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick3 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick4 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick5 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick6 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick7 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick8 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick9 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));
        woodStick10 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Wood/wood_stick1.png")));

        stone1 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Stone/stone1.png")));
        stoneHollow = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Stone/stone_hollow1.png")));
        stoneRectangle = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Stone/stone_rectangle1.png")));
        stoneStick = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Stone/stone_stick1.png")));

        ice1 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice1.png")));
        iceHollow = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice_hollow1.png")));
        iceRectangle = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice_rectangle1.png")));
        iceStick1 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice_stick1.png")));
        iceStick2 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice_stick1.png")));
        iceStick3 = new Sprite(new Texture(Gdx.files.internal("Menu/Blocks/Ice/ice_stick1.png")));

    }

    public void drawSprites(SpriteBatch batch) {
        woodStick1.draw(batch);
        woodStick2.draw(batch);
        woodStick3.draw(batch);
        woodStick4.draw(batch);
        woodStick5.draw(batch);
        iceStick1.draw(batch);
        iceStick2.draw(batch);
        iceStick3.draw(batch);
        stone1.draw(batch);
    }
    public void setRotations() {
        woodStick1.setRotation(90);
        woodStick2.setRotation(90);
        woodStick3.setRotation(0);
        woodStick4.setRotation(0);
        woodStick5.setRotation(45);
        iceStick1.setRotation(90);
        iceStick2.setRotation(90);
        iceStick3.setRotation(0);
        stone1.setRotation(0);
    }


    public void setPositions() {
        woodStick1.setPosition(1200, 260);
        woodStick2.setPosition(1000, 260);
        woodStick3.setPosition(1100, 385);
        woodStick4.setPosition(1300, 145);
        woodStick5.setPosition(1400, 245);
        iceStick1.setPosition(1500, 260);
        iceStick2.setPosition(1700, 260);
        iceStick3.setPosition(1600, 385);
        stone1.setPosition(1350, 150);
    }


    public void dispose() {
        woodStick1.getTexture().dispose();
        woodStick2.getTexture().dispose();
        woodStick3.getTexture().dispose();
        woodStick4.getTexture().dispose();
        woodStick5.getTexture().dispose();
        iceStick1.getTexture().dispose();
        iceStick2.getTexture().dispose();
        iceStick3.getTexture().dispose();
        stone1.getTexture().dispose();
    }
}
