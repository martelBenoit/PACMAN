package model;

import java.util.ArrayList;

public class Game {

    private int score;
    private ArrayList<Integer> highScores;
    private int level;
    private boolean isLost;
    private int initialNumberOfLives;
    private int numberOfLives;
    private Maze maze;

    public Game(int lives) {
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

        // TODO : Boucle principale
    }

    public void endGame() {
        this.isLost = true;

        // If the score is greater than the lowest high score
        if (this.score > highScores.get(0)) {
            // Mettre le score au bon endroit et réarranger la liste des highscores
        }
    }
}
