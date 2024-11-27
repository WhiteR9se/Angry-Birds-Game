/*
package io.github.angry_birds.Physics;

import com.badlogic.gdx.Game;
import io.github.angry_birds.Screens.GameSettingScreen;
import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Pigs.Minion;
import io.github.angry_birds.Sprites.Sling;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static io.github.angry_birds.Physics.GameState.game;

public class GameState implements Serializable {
    private Red red;
    private Chuck chuck;
    private Terence terence;
    private Sling sling;
    private Minion minion;
    public static Game game;
    // Add other game objects as needed

    // Constructor and getters/setters
}

public class GameScreen {
    private GameState gameState;

    private void saveGameState() {
        try (FileOutputStream fileOut = new FileOutputStream("gameState.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleGameSettingScreen() {
        saveGameState();
        game.setScreen(new GameSettingScreen(game, Level1.class));
    }
}*/
