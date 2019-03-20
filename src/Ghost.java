import java.util.Random;

public class Ghost extends Character {

    private boolean isAlive;
    
    public Ghost(Tile tile) {
		this.tile = tile;
        this.setDirection(Direction.LEFT);
	}

    public void move(Tile nextTile) {
    	this.setTile(nextTile);
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
