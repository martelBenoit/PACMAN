package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ghost extends Character {

    private boolean isAlive;
    protected BufferedImage image = null;

    public Ghost(Tile tile, int nbGhost) {
        super(tile);
        this.tile = tile;

        try {
           image = ImageIO.read(new File("lib/ghost"+nbGhost+".png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.setDestWidth((int) (image.getWidth() * tile.getSize() / image.getWidth() * 0.75));
        this.setDestHeight((int) (image.getHeight() * tile.getSize() / image.getHeight() * 0.75));
        centerImage(getWidthImage(), getHeightImage());
	}

    public BufferedImage getImage(){
        return this.image;

    }

    public void move(Tile nextTile) {
    	this.setTile(nextTile);
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

}
