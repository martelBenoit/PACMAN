package model;

/**
 * Class model.Tile.
 * This class allows to create tile of model.Maze
 */
public class Tile {

    /**
     * The x position of the tile
     */
    private int x;

    /**
     * The y position of the tile
     */
    private int y;

    private boolean isWall = false;

    public Tile(int x, int y, boolean wall){

        assert(x >= 0 && y >= 0) : "x or y < 0";

        this.x = x;
        this.y = y;
        this.isWall = wall;

    }

    public boolean isWall() {
        return isWall;
    }
}