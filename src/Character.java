public abstract class Character {

    private Tile tile;

    public Tile getTile(){
        return this.tile;
    }

    public boolean isFacingBlock(){
        return false;
    }

    public abstract Tile move(Tile nTile);


}
