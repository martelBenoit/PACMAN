public class Ghost extends Character {

    private boolean isAlive;

    public void move(Tile newTile) {
        this.setTile(newTile);
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
