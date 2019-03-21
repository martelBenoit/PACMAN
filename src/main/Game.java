package main;


import java.util.ArrayList;
import java.util.Random;

import main.view.GameFrame;

public class Game {

    private int score;
    private ArrayList<Integer> highScores;
    private int initialNumberOfLives;
    private int numberOfLives;
    private Maze maze;


    private GameFrame gameFrame;

    public Game(int lives) {

        this.initialNumberOfLives = lives;
        this.numberOfLives = initialNumberOfLives;
        this.gameFrame = GameFrame.getGameFrame();
        this.maze = new Maze(2);
        this.maze.draw();
        this.gameFrame.redraw();

    }

    
    public int getNumberOfLives() {
		return this.numberOfLives;
	}
	
	public Maze getMaze() {
		return this.maze;
	}

    // increaseLevel() ?
    // increaseScore() ?

    public void startGame() {
        //this.isLost = false;
        //this.level = 1;
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
                           pillToRemove = p;
                           score+=10;
                           gameFrame.setScore(score);
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
                    Tile nextTile = maze.getTile(g, g.getDirection());
                    boolean changeDirection = false;
                    // Checks if the tile exists (not out of the maze)
                    if (nextTile == null) {
                        changeDirection = true;
                    } else {
                        // Checks if the tile isn't a wall
                        if (nextTile.isWall()) {
                            changeDirection = true;
                        } else {
                            g.move(nextTile);
                            changeDirection = false;
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

		    System.out.println("GG !");
		    pacman.erase();
		    this.maze = new Maze(2);

            this.maze.draw();
            this.gameFrame.redraw();
		}

		System.out.println("GAME OVER !");
		this.endGame();
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

        // If the score is greater than the lowest high score
        if (this.score > highScores.get(0)) {
            // Mettre le score au bon endroit et réarranger la liste des highscores
        }
    }

}
