package main;

import main.view.GameFrame;

import java.awt.*;

/**
 * Class main.Tile.
 * This class allows to create tile of main.Maze
 */
public class Tile extends Figure {

    /**
     * The x position of the tile
     */
    private int x;

    /**
     * The y position of the tile
     */
    private int y;

    private int size;

    private Color color;

    private boolean isWall;

    public Tile(int size, int x, int y, boolean wall){

        super(size,size,x,y);

        this.size = size;
        this.x = x;
        this.y = y;
        this.isWall = wall;

        if(isWall)
            color = Color.BLUE;
        else
            color = Color.BLACK;


    }

    public boolean isWall() {
        return isWall;
    }

    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    public Color getColor(){
        return this.color;
    }

    public int getSize(){
        return this.size;
    }


}