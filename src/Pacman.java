public class Pacman extends Character {

    private boolean hasPower;

    public Pacman(Tile tile){
        this.tile = tile;
        this.setDirection(Direction.LEFT);
    }

    public boolean eatPill(){
        return false;
    }

    public boolean eatGhost(){
        return false;
    }

    @Override
    public void move() {
		Tile nextTile;
        switch(this.getDirection()) {
			case UP:
				nextTile = getTile(Direction.UP);
				// Checks if the tile exists (not out of the maze)
				if(nextTile != null) {
					// Checks if the tile isn't a wall
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
					}
				}
				break;
			case DOWN:
				nextTile = getTile(Direction.DOWN);
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
					}
				}
				break;
			case LEFT:
				nextTile = getTile(Direction.LEFT);
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
					}
				}
				break;
			case RIGHT:
				nextTile = getTile(Direction.RIGHT);
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
					}
				}
				break;
		}
    }


    public void setHasPower(Boolean power){
        power = this.hasPower;
    }

    public boolean getHasPower(){
        return this.hasPower;
    }
}
