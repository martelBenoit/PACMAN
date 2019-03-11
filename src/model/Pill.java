package model;

public abstract class Pill {


    // private boolean isEaten;

    private Tile tile;

    /*
    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public boolean getIsEaten() {
        return this.isEaten;
    }
    */

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
