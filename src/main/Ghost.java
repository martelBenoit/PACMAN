package main;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ghost extends Character {

    private boolean isAlive;
    private Tile lastTile;
    protected BufferedImage image = null;

    public Ghost(Tile tile,String nameImage) {
        super(tile);
        this.tile = tile;
        this.lastTile = this.tile;

        try {
            image = ImageIO.read(new File("lib/" + nameImage + ".png"));
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

    public Tile getLastTile() {
        return lastTile;
    }

    public void setLastTile(Tile lastTile) {
        this.lastTile = lastTile;
    }

    public void move(Tile nextTile) {
    	this.setTile(nextTile);
    	this.draw();
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

}
