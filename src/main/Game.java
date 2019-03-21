package main;

import java.io.*;
import java.util.*;

import main.view.GameFrame;

public class Game {

    private int score;
    private int level;
    private ArrayList<Integer> highScores;
    private int initialNumberOfLives;
    private int numberOfLives;
    private Maze maze;

    private GameFrame gameFrame;

    public Game(int lives) {

        loadHighScores();
        this.initialNumberOfLives = lives;
        this.numberOfLives = initialNumberOfLives;
        this.gameFrame = GameFrame.getGameFrame();
        this.maze = new Maze(1);
        this.maze.draw();
        this.gameFrame.redraw();

    }
    
    public int getNumberOfLives() {
		return this.numberOfLives;
	}
	
	public Maze getMaze() {
		return this.maze;
	}


    public void startGame() {
        this.level = 1;
        this.score = 0;

        // Boucle principale
		while (this.getNumberOfLives() > 0) {

            Maze maze = this.getMaze();
            Pacman pacman = maze.getPacman();

		    while(maze.getPills().size() > 0 && this.getNumberOfLives() > 0) {

		        // Change Pacman direction
                if (gameFrame.hasChangedDirection()) {
                    if (gameFrame.isUpPressed()) {
                        pacman.setWantedDirection(Direction.UP);
                    } else if (gameFrame.isDownPressed()) {
                        pacman.setWantedDirection(Direction.DOWN);
                    } else if (gameFrame.isLeftPressed()) {
                        pacman.setWantedDirection(Direction.LEFT);
                    } else if (gameFrame.isRightPressed()) {
                        pacman.setWantedDirection(Direction.RIGHT);
                    }
                    gameFrame.resetMove();
                }


                // Move PACMAN
                Tile nextTilePacman = maze.getTile(pacman, pacman.getWantedDirection());
                boolean moved = false;
                // Checks if the tile exists (not out of the maze)
                if (nextTilePacman != null) {
                    // Checks if the tile isn't a wall
                    if (!nextTilePacman.isWall()) {
                        pacman.setLastTile(pacman.getTile());
                        pacman.move(nextTilePacman);
                        moved = true;
                        pacman.setDirection(pacman.getWantedDirection());
                    }
                }
                if (!moved) {
                    nextTilePacman = maze.getTile(pacman, pacman.getDirection());
                    if (nextTilePacman != null) {
                        if (!nextTilePacman.isWall()) {
                            pacman.setLastTile(pacman.getTile());
                            pacman.move(nextTilePacman);
                        }
                    }
                }

                // Eat Pill on the case
                Pill pillToRemove = null;
                for(Tile t: maze.getTiles()) {
                   if (t == pacman.getTile()) {
                       for(Pill p: maze.getPills()) {
                           if (p.getTile() == t) {
                               if( p instanceof FruitPill)
                                   score+=maze.getFruitValue();
                               else
                                   score+=maze.getPillValue();
                               pillToRemove = p;
                               score+=maze.getPillValue();
                               gameFrame.setScore(score);
                               if(score >= this.highScores.get(0)) {
                                   gameFrame.setHighScore(score);
                               }
                           }
                       }
                   }
                }
                if(pillToRemove != null) {

                   Tile t = pillToRemove.getTile();
                   pillToRemove.setTile(null);
                   t.draw();
                   if(pillToRemove.getClass().getSimpleName().equals("PowerPill")) {
                        pacman.setHasPower(true);
                   }
                   maze.getPills().remove(pillToRemove);
                }

                // Move GHOSTS
                for (Ghost g : maze.getGhosts()) {
                    ArrayList<Tile> tilesAround = maze.getTilesAround(g);
                    if(g.getLastTile() != null) {
                        if (tilesAround.size() == 1) {
                            g.setLastTile(g.getTile());
                            g.move(tilesAround.get(0));
                        } else {
                            tilesAround.remove(g.getLastTile());
                            Random rd = new Random();
                            int x = rd.nextInt(tilesAround.size());
                            Tile newTile = tilesAround.get(x);
                            g.setLastTile(g.getTile());
                            g.move(newTile);
                        }
                    }
                }

                // Check if pacman got eaten by a ghost
                for(Ghost g: maze.getGhosts()) { // TODO: Créer méthode pour perdre une vie, et factoriser ce code
                    if (g.getTile() == pacman.getTile()) {
                        this.loseLife();
                    }
                    else if(g.getLastTile() == pacman.getTile() && g.getTile() == pacman.getLastTile()) {
                        this.loseLife();
                    }
                }
                gameFrame.redraw();
                gameFrame.wait(100);
            }

		    System.out.println("LEVEL UP !");
            this.level+=1;
            gameFrame.setLevel(this.level);
		    pacman.erase();
		    for(Ghost g: maze.getGhosts()) {
		        g.erase();
            }
		    this.maze = new Maze(1);


            this.maze.draw();
            gameFrame.redraw();
		}
		this.endGame();
    }

    public void endGame() {
        System.out.println("GAME OVER !");
        this.highScores.add(score);
        Collections.sort(this.highScores);
        saveHighScores();

    }

    public void loseLife() {
        this.numberOfLives--;
        if(numberOfLives > 0) {
            this.getMaze().getPacman().setTile(maze.getRandomTile());
        }
        else {
            this.endGame();
        }
    }

    private void loadHighScores(){
        this.highScores = new ArrayList<>();
        try{
            InputStream flux=new FileInputStream("lib/highscores.pac");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String line;
            if((line=buff.readLine())!= null){
                String[] scores = line.split(",");
                for(String score : scores){
                    this.highScores.add(Integer.parseInt(score));
                }
            }
            buff.close();

            if(this.highScores.size()==0){
                this.highScores.add(0);
            }
            Collections.sort(this.highScores);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }



    }

    private void saveHighScores(){
        try{
            PrintWriter writer = new PrintWriter("lib/highscores.pac");

            String highScores = ""+this.highScores.get(0);
            for(int i = 1; i<this.highScores.size(); i++){
                highScores = highScores+","+this.highScores.get(i);
            }

            writer.close();

        }
        catch(Exception e){
            System.out.println("High scores not save !");
            System.out.println(e.getMessage());
        }
    }



}
