package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import main.view.GameFrame;

public class Game {

    private int score;
    private ArrayList<Integer> highScores;
    private int level;
    private boolean isLost;
    private int initialNumberOfLives;
    private int numberOfLives;
    private Maze maze;


    private GameFrame gameFrame;

    public Game(int lives) {

        this.initialNumberOfLives = lives;
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
        this.isLost = false;
        //this.level = 1;
        this.score = 0;

        Pacman pacman = maze.getPacman();

        // Boucle principale
		//while (this.getNumberOfLives() > 0 && this.getMaze().getPills().size() > 0) { //(this.maps.getNbGom() > 0) && (this.pacman.getLife() > 0)
		while(true) {
			if(gameFrame.hasChangedDirection()) {
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
			Tile nextTilePacman = maze.getTile(pacman,pacman.getWantedDirection());
			boolean moved = false;
            // Checks if the tile exists (not out of the maze)
            if(nextTilePacman != null) {
                // Checks if the tile isn't a wall
                if(!nextTilePacman.isWall()) {
                    pacman.move(nextTilePacman);
                    moved = true;
                    pacman.setDirection(pacman.getWantedDirection());
                }
            }
            if (!moved) {
                nextTilePacman = maze.getTile(pacman,pacman.getDirection());
                if(nextTilePacman != null) {
                    // Checks if the tile isn't a wall
                    if(!nextTilePacman.isWall()) {
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
                       }
                   }
               }
           }
           if(pillToRemove != null) {
               Tile t = pillToRemove.getTile();
               pillToRemove.setTile(null);
               t.draw();
           }

            // Move GHOSTS
			for(Ghost g : maze.getGhosts()) {
                Tile nextTile = maze.getTile(g, g.getDirection());
			    boolean changeDirection = false;
                // Checks if the tile exists (not out of the maze)
                if(nextTile == null) {
                    changeDirection = true;
                }
                else {
                    // Checks if the tile isn't a wall
                    if(nextTile.isWall()) {
                        changeDirection = true;
                    }
                    else {
                        g.move(nextTile);
                        changeDirection = false;
                    }
                }

                if(changeDirection) {
                    Direction currentDirection = g.getDirection();
                    Direction newDirection = currentDirection;
                    while(newDirection == currentDirection) {
                        newDirection = getRandomDirection();
                    }
                    nextTile = maze.getTile(g, newDirection);
                    g.move(nextTile);
                }
            }

            gameFrame.redraw();
			gameFrame.wait(100);
		}
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
        this.isLost = true;

        // If the score is greater than the lowest high score
        if (this.score > highScores.get(0)) {
            // Mettre le score au bon endroit et réarranger la liste des highscores
        }
    }

}
