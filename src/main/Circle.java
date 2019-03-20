package main;

import view.GameFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle extends Figure
{

    public Circle(int size, int x, int y, Color color) {
        super(size, size, x, y, color);
    }

    public int getSize() {
        return getWidth();
    }


    public void setSize(int size) {
        super.setSize(size, size);
    }


    public void setSize(int width, int height) {
        assert width >= 0 && height == width : "Wrong dimensions";
        super.setSize(width, height);
    }

    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()));
    }


    protected void invariant() {
        super.invariant();
        assert getWidth() == getHeight() : "Invariant violated: wrong dimensions";
    }
}