package model;

import java.util.ArrayList;

public class Maze {

    private int powerTime;
    private int fruitValue;
    private int pillValue;
    private int regenerationTime;
    private int ghostSpeed;
    
    private Pacman pacman;

    private ArrayList<Tile> tiles;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Pill> pills;

    public Maze(int powerTime, int fruitValue, int pillValue, int regenerationTime, int ghostSpeed){

        this.powerTime = powerTime;
        this.fruitValue = fruitValue;
        this.pillValue = pillValue;
        this.regenerationTime = regenerationTime;
        this.ghostSpeed = ghostSpeed;
        
        
        // Pour test
        Tile t = new Tile(0,0, false);
        this.pacman = new Pacman(t);

    }

    public int getFruitValue() {
        return fruitValue;
    }


    public int getPillValue() {
        return pillValue;
    }


    public int getRegenerationTime() {
        return regenerationTime;
    }


    public int getGhostSpeed() {
        return ghostSpeed;
    }


    public ArrayList<Tile> getTiles() {
        return tiles;
    }


    public int getPowerTime() {
        return powerTime;
    }

    public ArrayList<Pill> getPills() {
        return pills;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }
    
    public Pacman getPacman() {
		return this.pacman;
	}
}
