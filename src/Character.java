public abstract class Character {

    protected Tile tile;

    public Tile getTile(){
        return this.tile;
    }

    public boolean isFacingBlock(){
        return false;
    }

    public abstract void move(Tile nTile);


}
