import java.util.Random;

public class Ghost extends Character {

    private boolean isAlive;
    
    public Ghost(Tile tile) {
		this.tile = tile;
        this.setDirection(Direction.LEFT);
	}

    public boolean move(Tile nextTile) {
		
		boolean facingWall = true;
		
		// Checks if the tile exists (not out of the maze)
		if(nextTile != null) {
			// Checks if the tile isn't a wall
			if(!nextTile.isWall()) {
				this.setTile(nextTile);
				facingWall = false;
				return true;
			}
			return false;
		}
		//If the ghost didn't move, change his direction and move it
		if(facingWall) {
			return false;
		}
		return false;
    }
    
    /**
     * Gets a random direction
     * 
     * @return A random direction
     */
    public Direction getRandomDirection() {
    	Random rd = new Random();
		int x = rd.nextInt(Direction.values().length);
        return Direction.values()[x];
	}

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
