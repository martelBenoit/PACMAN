package view;

import java.awt.*;

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


    public boolean isInside() {
        return x >= 0 && x+width < GameFrame.WIDTH && y >= 0 && y+height < GameFrame.HEIGHT;
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


    public void move() {
        invariant();
    }


    public void move(int dx, int dy) {
        erase();
        x += dx;
        y += dy;
        draw();
        invariant();
    }

    public void setSize(int width, int height) {
        assert width >= 0 && height >= 0 : "Wrong dimensions";
        erase();
        this.width = width;
        this.height = height;
        draw();
        invariant();
    }


    public void setColor(Color color)
    {
        this.color = color;
        draw();
        invariant();
    }


    protected abstract void draw();


    protected void erase()
    {
        GameFrame gameFrame = GameFrame.getCanvas();
        gameFrame.erase(this);
    }


    protected void invariant() {
        assert width >= 0 && height >= 0 : "Invariant violated: wrong dimensions";
    }
}