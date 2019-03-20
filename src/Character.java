public abstract class Character{

    protected Tile tile;
    private Direction direction;

    public Tile getTile(){
        return this.tile;
    }

    public abstract boolean move(Tile nextTile);

    public void setTile(Tile tile) {
        this.tile = tile;
    }
    
    public void setDirection(Direction d) {
		this.direction = d;
		System.out.println("Changement de direction : " + d);
	}
	
	public Direction getDirection() {
		return this.direction;
	}


}
