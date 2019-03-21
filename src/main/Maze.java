package main;

import main.view.GameFrame;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private String pathMaze;
    private int nbXTiles, nbYTiles;
    private int tile_size;

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

        this.pathMaze = "lib/"+mazeNumber+".maze";
        this.tiles = new ArrayList<>();
        this.pills = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.createMaze();
        Tile tile = getRandomTile();
        this.pacman = new Pacman(getRandomTile(),"pacman");
        for (int i = 0; i < 4 ; i++) {
            Ghost g = new Ghost(getRandomTile(), "ghost");
            this.ghosts.add(g);
        }
    }

    private void createMaze(){
        try{
            // Ouverture du fichier pour la lecture
            InputStream ips=new FileInputStream(this.pathMaze);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);

            // Lecture de la dimension du plateau
            String line = br.readLine();
            String[] param = line.split(",");
            this.nbXTiles = Integer.parseInt(param[0]);
            this.nbYTiles = Integer.parseInt(param[1]);

            // Lecture des données propre au plateau
            line = br.readLine();
            param = line.split(",");
            this.pillValue = Integer.parseInt(param[0]);
            this.fruitValue = Integer.parseInt(param[1]);
            this.powerTime = Integer.parseInt(param[2]);
            this.regenerationTime = Integer.parseInt(param[3]);
            this.ghostSpeed = Integer.parseInt(param[4]);

            // On récupère la taille de la fenêtre pour adapter la taille des cases du tableau
            GameFrame gameFrame = GameFrame.getGameFrame();
            this.tile_size = (int)((gameFrame.WIDTH-gameFrame.HEIGHT)/1.1/this.nbXTiles);

            int deltaX = (gameFrame.WIDTH-this.nbXTiles*this.tile_size)/2;
            gameFrame.setDimensionPan(new Dimension(this.nbXTiles*this.tile_size,60));


            int i = 0;
            // On lit toute les lignes du fichier
            while ((line=br.readLine())!=null){

                int j = 0;                   // La colonne de la map
                int tmpx = 0;               // Variable utilisée pour stocké la position en x d'une figure
                int tmpy = 0;                // Variable utilisée pour stocké la position en y d'une figure

                param = line.split(",");

                for (String str : param) {

                    tmpx = deltaX+j*this.tile_size;
                    tmpy = i*this.tile_size;
                    Tile newTile;
                    Pill newPill;
                    switch (str) {
                        case "P" : // Si c'est une PowerPill
                            newTile = new Tile(this.tile_size, tmpx, tmpy, false);
                            newPill = new PowerPill(newTile, Color.WHITE);
                            this.tiles.add(newTile);
                            this.pills.add(newPill);
                            break;
                        case "F" :  // Si c'est une case avec un fruit dessus
                            newTile = new Tile(this.tile_size, tmpx, tmpy, false);
                            newPill = new FruitPill(newTile,Color.RED);
                            this.tiles.add(newTile);
                            this.pills.add(newPill);
                            break;
                        case "E" :  // Si c'est une case avec rien
                            newTile = new Tile(this.tile_size, tmpx, tmpy, false);
                            this.tiles.add(newTile);
                            break;
                        case "#" :  // Si c'est un mur
                            newTile = new Tile(this.tile_size, tmpx, tmpy, true);
                            this.tiles.add(newTile);
                            break;
                        case "_" :  // Si c'est une case avec une gomme dessus
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
     * Get a random tile which isn't a wall in the maze and doesn't contain a powerPill
     * @return A random tile, which isn't a wall and doesn't contain a powerPill
     */
    public Tile getRandomTile() {
        ArrayList<Tile> tilesAvailable = new ArrayList<>();
        ArrayList<Pill> pills = new ArrayList<>();
        for(Pill p : this.pills) {
            if (p instanceof PowerPill || p instanceof FruitPill) {
               pills.add(p);
            }
        }

        for(Tile t : this.tiles) {
            if (!t.isWall()) {
                boolean valide = true;
                for (Pill p : pills) {
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

    public Tile getTile(Character c, Direction direction){
        Tile characterTile = c.getTile();
        switch(direction) {
            case UP:
                for(Tile t: tiles) {
                    if(t.getX() == characterTile.getX() && t.getY() == characterTile.getY()-this.tile_size) {
                        return t;
                    }
                }
                break;
            case DOWN:
                for(Tile t: tiles) {
                    if(t.getX() == characterTile.getX() && t.getY() == characterTile.getY()+this.tile_size) {
                        return t;
                    }
                }
                break;
            case LEFT:
                for(Tile t: tiles) {
                    if(t.getX() == characterTile.getX()-this.tile_size && t.getY() == characterTile.getY()) {
                        return t;
                    }
                }
                break;
            case RIGHT:
                for(Tile t: tiles) {
                    if(t.getX() == characterTile.getX()+this.tile_size && t.getY() == characterTile.getY()) {
                        return t;
                    }
                }
                break;
            default:
                return null;
        }
        return null;
    }

    public ArrayList<Tile> getTilesAround(Character c) {

        Tile characterTile = c.getTile();
        ArrayList<Tile> ret = new ArrayList<Tile>();
        for(Tile t: tiles) {
            if(!t.isWall()) {
                if (t.getX() >= characterTile.getX() - this.tile_size && t.getX() <= characterTile.getX() + this.tile_size) {
                    if(t.getX() == characterTile.getX()) {
                        if(t.getY() >= characterTile.getY()-this.tile_size && t.getY() <= characterTile.getY()+this.tile_size) {
                            if(t.getY() != characterTile.getY()) {
                                ret.add(t);
                            }
                        }
                    }
                    else {
                        if(t.getY() == characterTile.getY()) {
                            ret.add(t);
                        }
                    }
                }
            }
        }
        return ret;
    }
    public void draw () {

        for(Tile t: this.tiles) {
            t.draw();
        }

        for(Pill p: this.pills) {
            p.draw();
        }

        this.getPacman().draw();

    }

}
