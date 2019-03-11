package model;

import java.util.ArrayList;
import control.*;
import view.GameFrame;

public class Game {

    private int score;
    private ArrayList<Integer> highScores;
    private int level;
    private boolean isLost;
    private int initialNumberOfLives;
    private int numberOfLives;
    private Maze maze;

    private Controller control;

    private GameFrame gameFrame;

    public Game(int lives) {

        this.control = new Controller(this);
        this.initialNumberOfLives = lives;
    }

    public void generateMaze(int powerTime, int fruitValue, int pillValue, int regenerationTime, int ghostSpeed) {
        this.maze = new Maze(powerTime, fruitValue, pillValue, regenerationTime, ghostSpeed); //TODO
        this.level++; // this.increaseLevel() ?

    }

    // increaseLevel() ?
    // increaseScore() ?

    public void startGame() {
        this.isLost = false;
        //this.level = 1;
        this.score = 0;

        this.gameFrame = new GameFrame(this.control);
        this.gameFrame.showIt();
        // TODO : Boucle principale
    }

    public void endGame() {
        this.isLost = true;

        // If the score is greater than the lowest high score
        if (this.score > highScores.get(0)) {
            // Mettre le score au bon endroit et réarranger la liste des highscores
        }
    }

    public Controller getController(){
        return control;
    }
}
