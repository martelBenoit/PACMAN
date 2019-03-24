package main;

import main.view.GameFrame;
import java.awt.image.BufferedImage;

/**
 * Abstract class Character
 * This abstract class will eventually create "pacman" objects and "ghosts".
 * This class extends the Figure class.
 * @author Benoît & Yoann
 * @version 1.0
 * @see Figure
 * @see Pacman
 * @see Ghost
 */
public abstract class Character extends Figure{

    /**
     * The tile where the character is.
     */
    protected Tile tile;

    /**
     * The last tile where the character was.
     */
    private Tile lastTile;

    /**
     * Actual width of the character (not the width of the tile).
     */
    private int destWidth;

    /**
     * Actual height of the character (not the height of the tile).
     */
    private int destHeight;

    /**
     * Constructor of the class Character.
     * This constructor is called when creating a "pacman" or "ghost" object. It allows to define on which tile is the
     * character.
     * @param tile the tile where the character is.
     */
    public Character(Tile tile){
        super(tile.getSize(),tile.getSize(),tile.getX(),tile.getY());
        this.tile = tile;
        this.lastTile = this.tile;

        invariant();
    }


    /**
     * Abstract method move. This method allows to move the character in another tile.
     * @param nextTile the tile in which the character has to move
     */
    public abstract void move(Tile nextTile);

    /**
     * This method allows to draw the character on the game frame.
     */
    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.drawCharacter(this);
    }

    /**
     * This method allows to get the tile where the character is.
     * @return tile where the character is.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * This method allows to define the tile where the character is.
     * @param tile the tile where the character is.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
        if(tile != null) {
            this.changePosition(this.tile.getX(), this.tile.getY());
            centerImage(destWidth, destHeight);
        }
    }

    /**
     * This method allows to get the last tile where the character was.
     * @return the last tile where the character was.
     */
    Tile getLastTile() {
        return lastTile;
    }

    /**
     * This method allows to define the last tile where the character was.
     * @param lastTile the last tile where the character was.
     */
    void setLastTile(Tile lastTile) {
        this.lastTile = lastTile;
    }

    /**
     * This abstract method getImage allows to get the image of the character.
     * @return image of the character.
     */
    public abstract BufferedImage getImage();

    /**
     * This method allows to get the width of the image.
     * @return the width of the image.
     */
    public int getWidthImage(){
        return this.destWidth;
    }

    /**
     * This method allows to get the height of the image.
     * @return the height of the image.
     */
    public int getHeightImage(){
        return this.destHeight;
    }

    /**
     * This method allows to set the height of the image character.
     * @param destHeight the height that the image must have.
     */
    void setDestHeight(int destHeight) {
        this.destHeight = destHeight;
    }

    /**
     * This method allows to set the width of the image character.
     * @param destWidth the width that the image must have.
     */
    void setDestWidth(int destWidth) {
        this.destWidth = destWidth;
    }

    public void invariant() {
        if(this.tile != null) {
            assert !this.tile.isWall() : "The character can't be in a wall";
        }
    }
}