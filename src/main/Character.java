package main;

import main.view.GameFrame;
import java.awt.image.BufferedImage;

public abstract class Character extends Figure{

    protected Tile tile;
    private int destWidth;
    private int destHeight;
    private Tile lastTile;

    public Character(Tile tile){

        super(tile.getSize(),tile.getSize(),tile.getX(),tile.getY());
        this.tile = tile;
        this.lastTile = this.tile;

    }

    public Tile getLastTile() {
        return lastTile;
    }

    public void setLastTile(Tile lastTile) {
        this.lastTile = lastTile;
    }

    public abstract void move(Tile nextTile);

    public void setTile(Tile tile) {
        this.tile = tile;
        if(tile != null) {
            this.changePosition(this.tile.getX(), this.tile.getY());
            centerImage(destWidth, destHeight);
        }
    }

    public Tile getTile() {
        return this.tile;
    }

    public int getWidthImage(){
        return this.destWidth;
    }

    public int getHeightImage(){
        return this.destHeight;
    }

    public abstract BufferedImage getImage();

    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.drawCharacter(this);
    }

    public void setDestHeight(int destHeight) {
        this.destHeight = destHeight;
    }

    public void setDestWidth(int destWidth) {
        this.destWidth = destWidth;
    }
}
