import java.util.ArrayList;

public class Maze {

    private int powerTime;
    private int fruitValue;
    private int pillValue;
    private int regenerationTime;
    private int ghostSpeed;

    private ArrayList<Tile> tiles;
    private ArrayList<Character> characters;
    private ArrayList<Pill> pills;

    public Maze(int powerTime, int fruitValue, int pillValue, int regenerationTime, int ghostSpeed){

        this.powerTime = powerTime;
        this.fruitValue = fruitValue;
        this.pillValue = pillValue;
        this.regenerationTime = regenerationTime;
        this.ghostSpeed = ghostSpeed;

    }

    public int getFruitValue() {
        return fruitValue;
    }


    public int getPillValue() {
        return pillValue;
    }


    public int getRegenerationTime() {
        return regenerationTime;
    }


    public int getGhostSpeed() {
        return ghostSpeed;
    }


    public ArrayList<Tile> getTiles() {
        return tiles;
    }


    public int getPowerTime() {
        return powerTime;
    }

    public ArrayList<Pill> getPills() {
        return pills;
    }

    public ArrayList<Character> getCharacter() {
        return characters;
    }


}
