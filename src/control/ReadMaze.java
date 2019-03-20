package control;

import view.GameFrame;
import view.Figure;
import view.MazeGenerate;
import view.Pill;
import view.Wall;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadMaze {

    private int nb_x_tiles;
    private int nb_y_tiles;

    private int pacmanX;
    private int pacmanY;

    private MazeGenerate theMaze;

    private String pathMaze;

    private int tile_size = 40;

    public ReadMaze(int mazeNumber) {
        this.pathMaze = "lib/maze2.maze";
        this.createMap();
    }


    private void createMap(){
        try{
            // Ouverture du fichier pour la lecture
            InputStream ips=new FileInputStream(this.pathMaze);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);


            String line = br.readLine();
            String[] param = line.split(",");
            this.nb_x_tiles = Integer.parseInt(param[0]);
            this.nb_y_tiles = Integer.parseInt(param[1]);
            this.theMaze = new MazeGenerate(this.nb_x_tiles,this.nb_y_tiles);
            GameFrame gameFrame = GameFrame.getCanvas();
            this.tile_size = gameFrame.WIDTH/this.nb_x_tiles;
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
                    switch (str) {
                        case "1" :
                            this.theMaze.setFigure(i,j,new Wall(this.tile_size, tmpx, tmpy, Color.blue));
                            break;
                        case "0" :
                            this.theMaze.setFigure(i,j,new Pill(this.tile_size, tmpx, tmpy,Color.white));
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


    public Figure[][] getMaze(){
        return this.theMaze.getTheMaze();
    }

    public void draw () {
        this.theMaze.draw();
    }


    public int getNbXTiles(){ return  this.nb_x_tiles; }

    public int getNbYTiles(){ return  this.nb_y_tiles; }

    public int getTileSize() {
        return this.tile_size;
    }



}