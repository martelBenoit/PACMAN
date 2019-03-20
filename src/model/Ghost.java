package model;

public class Ghost extends Character {

    private boolean isAlive;
    
    public Ghost(Tile tile) {
		this.tile = tile;
        this.setDirection(Direction.LEFT);
	}

    public void move() {
		
		Tile nextTile = getTile(this.getDirection());
		
		boolean facingWall = true;
		
		// Checks if the tile exists (not out of the maze)
		if(nextTile) {
			// Checks if the tile isn't a wall
			if(!nextTile.isWall()) {
				this.setTile(nextTile);
				facingWall = false;
			}
		}
		//If the ghost didn't move, change his direction and move it
		if(facingWall) {
			Direction newDirection = this.getDirection();
			while(newDirection == this.getDirection()) {
				newDirection = getRandomDirection();
			}
			this.setDirection(newDirection);
			this.move();
		}
    }
    
    /**
     * Gets a random direction
     * 
     * @return A random direction
     */
    public Direction getRandomDirection() {
		int x = random.nextInt(Direction.length);
        return Direction[x];
	}

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
