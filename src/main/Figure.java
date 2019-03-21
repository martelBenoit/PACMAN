package main;

import main.view.GameFrame;

import java.awt.Color;

public abstract class Figure
{
    private int width; // the figure width in pixels
    private int height; // the figure height in pixels
    private int x; // the figure x location in pixels
    private int y; // the figure y location in pixels
    private Color color;

    public Figure(int width, int height, int x, int y, Color color)
    {
        assert width >= 0 && height >= 0 : "Wrong dimensions";

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
        invariant();
    }

    public Figure(int width, int height, int x, int y)
    {
        assert width >= 0 && height >= 0 : "Wrong dimensions";

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        invariant();
    }

    protected void centerImage(int newWidth, int newHeight){

        int diffY = (this.height-newHeight)/2;
        int diffX = (this.width-newWidth)/2;

        this.x = this.x+diffX;
        this.y = this.y+diffY;
    }

    protected void changePosition(int x, int y){
        this.x = x;
        this.y = y;
    }


    public int getX() {
    return x;
}


    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public Color getColor() {
        return color;
    }


    public void setSize(int width, int height) {
        assert width >= 0 && height >= 0 : "Wrong dimensions";
        erase();
        this.width = width;
        this.height = height;
        draw();
        invariant();
    }


    protected abstract void draw();


    protected void erase()
    {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.erase(this);
    }


    protected void invariant() {
        assert width >= 0 && height >= 0 : "Invariant violated: wrong dimensions";
    }
}