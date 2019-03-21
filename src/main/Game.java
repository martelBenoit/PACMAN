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
        //this.isLost = false;
        this.level = 1;
        this.score = 0;
        Pacman pacman = maze.getPacman();
        // Boucle principale
		while (this.getNumberOfLives() > 0) {
		    while(maze.getPills().size() > 0) {
                pacman = maze.getPacman();
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
                        pacman.move(nextTilePacman);
                        moved = true;
                        pacman.setDirection(pacman.getWantedDirection());
                    }
                }
                if (!moved) {
                    nextTilePacman = maze.getTile(pacman, pacman.getDirection());
                    if (nextTilePacman != null) {
                        // Checks if the tile isn't a wall
                        if (!nextTilePacman.isWall()) {
                            pacman.move(nextTilePacman);
                        }
                    }
                }

                // Eat Pill on the case
                Pill pillToRemove = null;
                for(Tile t: maze.getTiles()) {
                   if (t == nextTilePacman) {
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

                    if (changeDirection) {
                        Direction currentDirection = g.getDirection();
                        Direction newDirection = currentDirection;
                        while (newDirection == currentDirection) {
                            newDirection = getRandomDirection();
                        }
                        nextTile = maze.getTile(g, newDirection);
                        g.move(nextTile);
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

		System.out.println("GAME OVER !");
		this.endGame();
    }


    public Direction inverseDirection(Direction d) {
        Direction ret = null;
        switch(d) {
            case UP:
                ret = Direction.DOWN;
                break;
            case DOWN:
                ret = Direction.UP;
                break;
            case LEFT:
                ret = Direction.RIGHT;
                break;
            case RIGHT:
                ret = Direction.LEFT;
                break;
        }
        return ret;
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

    public void endGame() {
        //this.isLost = true;

        this.highScores.add(score);
        Collections.sort(this.highScores);
        saveHighScores();

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
