import view.GameFrame;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    private Figure[][] figures;

    private ArrayList<Tile> tiles;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Pill> pills;

    public Maze(int mazeNumber){
        this.pathMaze = "lib/maze"+mazeNumber+".maze";
        this.createMaze();
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
            figures = new Figure[nbXTiles][nbYTiles];

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
                    Tile tile;
                    switch (str) {
                        case "1" :
                            setFigure(i,j,new Tile(this.tile_size, tmpx, tmpy, true));
                            break;
                        case "0" :
                            tile = new Tile(this.tile_size, tmpx, tmpy,false);
                            setFigure(i,j,new NormalPill(tile));
                            break;
                        case "2" :
                            tile = new Tile(this.tile_size, tmpx, tmpy,false);
                            setFigure(i,j,new PowerPill(tile,Color.WHITE));
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

    // A FAIRE
    public Tile getTile(Direction direction){
        return null;
    }

    public void setFigure (int i, int j, Figure f) {

        this.figures[i][j] = f;
    }

    public void draw () {

        for(int y = 0; y < figures[0].length; y++){
            for(int x = 0; x < figures.length; x++){
                if (figures[x][y]!=null) {
                    figures[x][y].draw();
                }
            }
        }

    }


}
