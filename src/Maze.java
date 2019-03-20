import view.GameFrame;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private String pathMaze;
    private int nbXTiles, nbYTiles;
    private int tile_size = 40;

    private int powerTime;
    private int fruitValue;
    private int pillValue;
    private int regenerationTime;
    private int ghostSpeed;
    
    private Pacman pacman;

    private ArrayList<Tile> tiles;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Pill> pills;

    public Maze(int mazeNumber){
        this.pathMaze = "lib/maze"+mazeNumber+".maze";
        this.tiles = new ArrayList<Tile>();
        this.pills = new ArrayList<Pill>();
        this.ghosts = new ArrayList<Ghost>();
        this.createMaze();
        this.pacman = new Pacman(getRandomTile());
    }

    private void createMaze(){
        try{
            // Ouverture du fichier pour la lecture
            InputStream ips=new FileInputStream(this.pathMaze);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);


            String line = br.readLine();
            String[] param = line.split(",");
            this.nbXTiles = Integer.parseInt(param[0]);
            this.nbYTiles = Integer.parseInt(param[1]);

            GameFrame gameFrame = GameFrame.getGameFrame();
            this.tile_size = gameFrame.WIDTH/this.nbXTiles;
            int i = 0;

            // On lit toute les lignes du fichier
            while ((line=br.readLine())!=null){

                int j = 0;                   // La colonne de la map
                int tmpx = 0;                // Variable utilisée pour stocké la position en x d'une figure
                int tmpy = 0;                // Variable utilisée pour stocké la position en y d'une figure

                param = line.split(",");

                for (String str : param) {

                    tmpx = j*this.tile_size;
                    tmpy = i*this.tile_size;
                    Tile newTile;
                    Pill newPill;
                    switch (str) {
                        case "2" :
                            newTile = new Tile(this.tile_size, tmpx, tmpy, false);
                            newPill = new PowerPill(newTile, Color.WHITE);
                            this.tiles.add(newTile);
                            this.pills.add(newPill);
                            break;
                        case "1" :
                            newTile = new Tile(this.tile_size, tmpx, tmpy, true);
                            this.tiles.add(newTile);
                            break;
                        case "0" :
                            newTile = new Tile(this.tile_size, tmpx, tmpy, false);
                            newPill = new NormalPill(newTile);
                            this.tiles.add(newTile);
                            this.pills.add(newPill);
                            break;

                    }
                    j++;
                }
                i++;

            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    /**
     * Get a random tile which isn't a wall in the maze
     *
     * @return A random tile, which isn't a wall
     */
    public Tile getRandomTile() {
        ArrayList<Tile> tilesAvailable = new ArrayList<Tile>();
        ArrayList<PowerPill> powerPills = new ArrayList<PowerPill>();

        for(Pill p : this.pills) {
            if (p.getClass().getSimpleName().equals("PowerPill")) {
               powerPills.add((PowerPill) (p));
            }
        }

        for(Tile t : this.tiles) {
            if (!t.isWall()) {
                boolean valide = true;
                for (PowerPill p : powerPills) {
                    if (p.getTile() == t) {
                        valide = false;
                    }
                }
                if (valide) {
                    tilesAvailable.add(t);
                }
            }
        }

        Random rd = new Random();
        int x = rd.nextInt(tilesAvailable.size());
        return tilesAvailable.get(x);
    }


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

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }
    
    public Pacman getPacman() {
		return this.pacman;
	}

    // TODO
    public Tile getTile(Character c, Direction direction){
        switch(direction) {
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            default:
                break;
        }
        return null;
    }

    public void draw () {

        for(Tile t: this.tiles) {
            t.draw();
        }

        for(Pill p: this.pills) {
            p.draw();
        }

    }


}
