package main;

import java.awt.Color;

/**
 * <HTML>
 * <BODY>
 * Abstract class Figure. <br>
 * This class is the super class of "Character" but also of "Pill" and "Tile". It allows among other things to
 * define the positions, sizes and colors of the elements of pacman game.
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Character
 * @see Pill
 * @see Tile
 */
public abstract class Figure
{
    /**
     * the figure width in pixels.
     */
    private int width;

    /**
     * the figure height in pixels.
     */
    private int height;

    /**
     * the figure x location in pixels.
     */
    private int x;

    /**
     * the figure y location in pixels.
     */
    private int y;

    /**
     * the color of the figure.
     */
    private Color color;

    /**
     * Constructor of the Figure class. It allows to create an object with as a parameter a height, a width,
     * a position in x and y and a color.
     * @param width the width of the figure.
     * @param height the height of the figure.
     * @param x the x position of the figure.
     * @param y the y position of the figure.
     * @param color the color of the figure.
     */
    protected Figure(int width, int height, int x, int y, Color color)
    {
        assert width >= 0 && height >= 0 : "Wrong dimensions";

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
        invariant();
    }

    /**
     * Constructor of the Figure class. It allows to create an object with as a parameter a height, a width,
     * a position in x and y.
     * @param width the width of the figure.
     * @param height the height of the figure.
     * @param x the x position of the figure.
     * @param y the y position of the figure.
     */
    protected Figure(int width, int height, int x, int y)
    {
        assert width >= 0 && height >= 0 : "Wrong dimensions";

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        invariant();
    }

    /**
     * This method makes it possible to center an image (to change the positions x and y) according to its new size.
     * @param newWidth the new width of the image.
     * @param newHeight the new height of the image.
     */
    protected void centerImage(int newWidth, int newHeight){

        int diffY = (this.height-newHeight)/2;
        int diffX = (this.width-newWidth)/2;

        this.x = this.x+diffX;
        this.y = this.y+diffY;
    }

    /**
     * This method allows to change the position of the figure.
     * @param x the new x position of the figure.
     * @param y the new y position of the figure.
     */
    protected void changePosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * This method allows to get the x position of the figure.
     * @return the x position of the figure
     */
    public int getX() {
    return x;
    }

    /**
     * This method allows to get the y position of the figure.
     * @return the y position of the figure
     */
    public int getY() {
        return y;
    }

    /**
     * This method allows to get the width of the figure.
     * @return the width of the figure.
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method allows to get the height of the figure.
     * @return the height of the figure.
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method allows to get the color of the figure.
     * @return the color of the figure.
     */
    public Color getColor() {
        return color;
    }

    /**
     * This abstract method allows to draw the figure.
     */
    protected abstract void draw();


    /**
     * Invariant of the figure.
     */
    protected void invariant() {
        assert width >= 0 && height >= 0 : "Invariant violated: wrong dimensions";
    }
}