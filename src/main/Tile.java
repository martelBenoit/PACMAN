package main;

import main.view.GameFrame;

import java.awt.*;

/**
 * Class Tile.
 * This class allows to create tile of Maze
 */
public class Tile extends Figure {

    /**
     * The x position of the tile
     */
    private int x;

    /**
     * The y position of the tile
     */
    private int y;

    private int size;
    private Color color;
    private boolean pacmanSpawn;
    private boolean ghostSpawn;

    private boolean isWall;

    public Tile(int size, int x, int y, boolean wall){

        super(size,size,x,y);

        this.size = size;
        this.x = x;
        this.y = y;
        this.isWall = wall;
        this.pacmanSpawn = false;
        this.ghostSpawn = false;

        if(isWall)
            color = Color.BLUE;
        else
            color = Color.BLACK;
    }

    public boolean isGhostSpawn() {
        return ghostSpawn;
    }

    public boolean isPacmanSpawn() {
        return pacmanSpawn;
    }

    public void setGhostSpawn(boolean ghostSpawn) {
        this.ghostSpawn = ghostSpawn;
    }

    public void setPacmanSpawn(boolean pacmanSpawn) {
        this.pacmanSpawn = pacmanSpawn;
    }

    public boolean isWall() {
        return isWall;
    }

    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    public Color getColor(){
        return this.color;
    }

    public int getSize(){
        return this.size;
    }


}