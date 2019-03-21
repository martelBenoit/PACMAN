package main;

import main.view.GameFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class Pill extends Figure{

    protected Tile tile;


    public Pill(Tile tile, Color color,int factor) {
        super(tile.getSize()/factor,tile.getSize()/factor,tile.getX()+(tile.getSize()/2)-(tile.getSize()/factor/2),tile.getY()+(tile.getSize()/2)-(tile.getSize()/factor/2),color);
        this.tile = tile;
    }


    public void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()));

    }



    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
