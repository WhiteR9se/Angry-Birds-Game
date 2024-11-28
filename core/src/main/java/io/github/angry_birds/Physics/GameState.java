
package io.github.angry_birds.Physics;

import io.github.angry_birds.Sprites.Birds.Chuck;
import io.github.angry_birds.Sprites.Birds.Red;
import io.github.angry_birds.Sprites.Birds.Terence;
import io.github.angry_birds.Sprites.Pigs.Minion;
import io.github.angry_birds.Sprites.Sling;

import java.io.*;

public class GameState implements Serializable {
    private Red red;
    private Chuck chuck;
    private Terence terence;
    private Sling sling;
    private Minion minion;
    // Add other game objects as needed

    public GameState(Red red, Chuck chuck, Terence terence, Sling sling, Minion minion) {
        this.red = red;
        this.chuck = chuck;
        this.terence = terence;
        this.sling = sling;
        this.minion = minion;
    }

    public Red getRed() {
        return red;
    }

    public Chuck getChuck() {
        return chuck;
    }

    public Terence getTerence() {
        return terence;
    }

    public Sling getSling() {
        return sling;
    }

    public Minion getMinion() {
        return minion;
    }

    // Add getters for other game objects as needed

    public static void saveState(GameState gameState, int level) {
        String filePath = "gameStatelvl" + level + ".ser";
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadState(int level) {
        String filePath = "gameStatelvl" + level + ".ser";
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}