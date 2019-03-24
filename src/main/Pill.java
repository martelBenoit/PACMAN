package main;

import main.view.GameFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Abstract class Pill. <br>
 * Pill makes it possible to create pills on the maze. Each pills is placed on a tiles belonging to the maze.
 * @author Benoît Martel
 * @author Yoann Le Dréan
 * @version 1.0
 * @see PowerPill
 * @see NormalPill
 * @see FruitPill
 */
public abstract class Pill extends Figure{

    /**
     * The tile on which the pill is placed.
     */
    protected Tile tile;

    /**
     * Constructor of the Pill class. <br>
     * The class being abstract it is not possible to create a "Pill" object, it's necessary to go through the
     * sub objects "NormalPill, FruitPill, PowerPill". <br>
     * This constructor is called from the subclasses. It allows
     * to select on which tile is the pill, the color of the pill and its size factor on the maze.
     * @param tile the tile on which is the Pill.
     * @param color the color of the Pill.
     * @param factor the size factor of the Pill.
     */
    Pill(Tile tile, Color color,int factor) {
        super(tile.getSize()/factor,tile.getSize()/factor,tile.getX()+(tile.getSize()/2)-(tile.getSize()/factor/2),tile.getY()+(tile.getSize()/2)-(tile.getSize()/factor/2),color);
        this.tile = tile;
    }

    /**
     * Method that draws the Pill on the game frame.
     */

    public void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()));

    }

    /**
     * A method that removes the link from the pill to a tile. This method is used especially when the pill is eaten.
     */
    void removeTile(){
        this.tile = null;
    }

    /**
     * Getter tiles.
     * This method retrieves the tile on which the pill is located.
     * @return the tile on which the pill is located.
     */
    public Tile getTile() {
        return tile;
    }
}