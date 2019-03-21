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

    /**
     * The size of the tile
     */
    private int size;

    /**
     * The color of the tile
     */
    private Color color;

    /**
     *
     */
    private boolean pacmanSpawn;

    /**
     *
     */
    private boolean ghostSpawn;

    /**
     * Tile is wall
     */
    private boolean isWall;

    /**
     * Constructor of the Tile class. This constructor allows you to create a tile
     * by specifying its x and y positions, size and defining it as a wall or not.
     * @param size the size of the tile
     * @param x x position of the tile
     * @param y y position of the tile
     * @param wall tile is wall or not
     */
    public Tile(int size, int x, int y, boolean wall){

        super(size,size,x,y);

        this.size = size;
        this.x = x;
        this.y = y;
        this.isWall = wall;
        this.pacmanSpawn = false;
        this.ghostSpawn = false;

        // Si c'est un mur alors on définit la couleur bleue sinon la couleur noire
        if(isWall)
            color = Color.BLUE;
        else
            color = Color.BLACK;
    }

    /**
     *
     * @return
     */
    public boolean isGhostSpawn() {
        return ghostSpawn;
    }

    /**
     *
     * @return
     */
    public boolean isPacmanSpawn() {
        return pacmanSpawn;
    }

    /**
     *
     * @param ghostSpawn
     */
    public void setGhostSpawn(boolean ghostSpawn) {
        this.ghostSpawn = ghostSpawn;
    }

    /**
     *
     * @param pacmanSpawn
     */
    public void setPacmanSpawn(boolean pacmanSpawn) {
        this.pacmanSpawn = pacmanSpawn;
    }

    /**
     * Getter isWall
     * @return true if the tile is a wall else false
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * Method that draws the tile on the game frame
     */
    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * Getter color
     * @return the color of the tile
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Getter size
     * @return the size of the tile
     */
    public int getSize(){
        return this.size;
    }


}