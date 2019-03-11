public abstract class Character {

    private Tile tile;

    public Tile getTile(){
        return this.tile;
    }

    public abstract void move(Tile nTile);

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
