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
    public boolean move(Tile nextTile) {
        switch(this.getDirection()) {
			case UP:
				// Checks if the tile exists (not out of the maze)
				if(nextTile != null) {
					// Checks if the tile isn't a wall
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
						return true;
					}
				}
				break;
			case DOWN:
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
						return true;
					}
				}
				break;
			case LEFT:
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
						return true;
					}
				}
				break;
			case RIGHT:
				if(nextTile != null) {
					if(!nextTile.isWall()) {
						this.setTile(nextTile);
						return true;
					}
				}
				break;
		}
		return false;

    }


    public void setHasPower(Boolean power){
        power = this.hasPower;
    }

    public boolean getHasPower(){
        return this.hasPower;
    }
}
