package main;

import java.awt.*;

public abstract class Pill extends Circle{

    protected Tile tile;

    protected Figure[] figures;


    public Pill(Tile tile, Color color) {
        super(tile.getSize(),tile.getX(),tile.getY(),color);
        this.figures = new Figure[2];
        this.figures[0] = new Square(tile.getSize(), tile.getX(), tile.getY(), Color.black);
        int sg = tile.getSize()/5;
        int xg = tile.getX()+(tile.getSize()/2)-(sg/2);
        int yg = tile.getY()+(tile.getSize()/2)-(sg/2);
        this.figures[1] = new Circle(sg,xg,yg,color);
        this.tile = tile;
    }


    public void draw() {
        for (Figure f : this.figures) {
            if (f != null) {
                f.draw();
            }
        }
    }



    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {

        return tile;
    }
}
