package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <BODY>
 * <HTML>
 *     Ghost class. <br>
 *     Ghost class extends the Character class. <br>
 *     This class is used to manage the Ghost character in the game. <br>
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Character
 */
public class Ghost extends Character {

    /**
     * Determine if ghost is alive.
     */
    private boolean isAlive;

    /**
     * Determine if ghost is eatable.
     */
    private boolean isEatable;

    /**
     * The image of the ghost.
     */
    private BufferedImage image = null;

    /**
     * The image of the eatable ghost.
     */
    private static final BufferedImage IMAGE_EATABLE;
    static {
        BufferedImage tmp = null;
        try{
            tmp = ImageIO.read(new File("lib/ghost_eatable.png"));
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        IMAGE_EATABLE = tmp;
    }

    /**
     * Constructor of the Ghost class.
     * @param tile the tile where the ghost is.
     * @param nbGhost the number of the ghost that we create.
     */
    Ghost(Tile tile, int nbGhost) {
        super(tile);
        this.tile = tile;
        this.isAlive = true;

        try {
           image = ImageIO.read(new File("lib/ghost"+nbGhost+".png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.setDestWidth((int) (image.getWidth() * tile.getSize() / image.getWidth() * 0.75));
        this.setDestHeight((int) (image.getHeight() * tile.getSize() / image.getHeight() * 0.75));
        centerImage(getWidthImage(), getHeightImage());
	}

    /**
     * Get the image of the ghost. If the ghost is edible then we return a different image.
     * @return the image of the ghost.
     */
    public BufferedImage getImage(){
        if(isEatable)
            return IMAGE_EATABLE;
        else
            return this.image;

    }

    /**
     * Move the ghost to the next tile
     * @param nextTile the tile in which the ghost has to move
     */
    @Override
    public void move(Tile nextTile) {
    	this.setTile(nextTile);
    }

    /**
     * Set if the ghost is alive or not.
     * @param alive tre if is alive, false otherwise.
     */
    void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Get if the ghost is alive or not.
     * @return true if is alive, false otherwise.
     */
    boolean isAlive() {
        return isAlive;
    }

    /**
     * Set if the ghost is eatable or not.
     * @param eatable true if the ghost is eatable, false otherwise.
     */
    void setEatable(boolean eatable) {
        isEatable = eatable;
    }

}