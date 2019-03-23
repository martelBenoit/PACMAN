package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ghost extends Character {

    private boolean isAlive;
    private boolean isEatable;
    private BufferedImage image = null;
    private BufferedImage image_eatable = null;

    Ghost(Tile tile, int nbGhost) {
        super(tile);
        this.tile = tile;
        this.isAlive = true;

        try {
           image = ImageIO.read(new File("lib/ghost"+nbGhost+".png"));
           image_eatable = ImageIO.read(new File("lib/ghost_eatable.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.setDestWidth((int) (image.getWidth() * tile.getSize() / image.getWidth() * 0.75));
        this.setDestHeight((int) (image.getHeight() * tile.getSize() / image.getHeight() * 0.75));
        centerImage(getWidthImage(), getHeightImage());
	}

    public BufferedImage getImage(){
        if(isEatable)
            return this.image_eatable;
        else
            return this.image;

    }

    public void move(Tile nextTile) {
    	this.setTile(nextTile);
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    boolean isAlive() {
        return isAlive;
    }

    void setEatable(boolean eatable) {
        isEatable = eatable;
    }


}
