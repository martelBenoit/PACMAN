package main;

import main.view.GameFrame;

import java.awt.*;

/**
 * <HTML>
 * <BODY>
 *     Class Tile. <br>
 *     This class allows to create tile of Maze. <br>
 *     This class extends the Figure class.
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Figure
 */
public class Tile extends Figure {

    /**
     * The size of the tile.
     */
    private int size;

    /**
     * The color of the tile.
     */
    private Color color;

    /**
     * Tile pacman spawn.
     */
    private boolean pacmanSpawn;

    /**
     * Tile pacman ghost.
     */
    private boolean ghostSpawn;

    /**
     * Tile is wall.
     */
    private boolean isWall;

    /**
     * Constructor of the Tile class. This constructor allows you to create a tile
     * by specifying its x and y positions, size and defining it as a wall or not.
     * @param size the size of the tile.
     * @param x x position of the tile.
     * @param y y position of the tile.
     * @param wall tile is wall or not.
     */
    public Tile(int size, int x, int y, boolean wall){

        super(size,size,x,y);

        this.size = size;
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
     * The method allows to get if this tile is the spawn of ghost
     * @return true if the tile where ghost spawn
     */
    boolean isGhostSpawn() {
        return ghostSpawn;
    }

    /**
     * The method allows to get if this tile is the spawn of pacman
     * @return true if the tile where pacman spawn
     */
    boolean isPacmanSpawn() {
        return pacmanSpawn;
    }

    /**
     * The method allows to define if this tile is the spawn of ghost
     */
    public void setGhostSpawn() {
        this.ghostSpawn = true;
    }

    /**
     * The method allows to define if this tile is the spawn of pacman
     */
    public void setPacmanSpawn() {
        this.pacmanSpawn = true;
    }

    /**
     * Getter isWall.
     * @return true if the tile is a wall else false.
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * Method that draws the tile on the game frame.
     */
    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * Getter color.
     * @return the color of the tile.
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Getter size.
     * @return the size of the tile.
     */
    public int getSize(){
        return this.size;
    }
}