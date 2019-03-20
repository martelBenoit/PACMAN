import java.util.ArrayList;
import java.util.Random;

import view.GameFrame;

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
       // this.generateMaze(10, 50, 20, 10, 5);


    }

    public void generateMaze(int powerTime, int fruitValue, int pillValue, int regenerationTime, int ghostSpeed) {
       // this.maze = new Maze(powerTime, fruitValue, pillValue, regenerationTime, ghostSpeed);
        this.maze = new Maze(2);
        this.gameFrame.redraw();
        //this.level++; // this.increaseLevel() ?

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


        Maze maze = this.getMaze();

        
        // Boucle principale
		//while (this.getNumberOfLives() > 0 && this.getMaze().getPills().size() > 0) { //(this.maps.getNbGom() > 0) && (this.pacman.getLife() > 0)
		while(true) {
			if(gameFrame.hasChangedDirection()) {
				if (gameFrame.isUpPressed()) {
					maze.getPacman().setDirection(Direction.UP);
				} else if (gameFrame.isDownPressed()) {
                    maze.getPacman().setDirection(Direction.DOWN);
				} else if (gameFrame.isLeftPressed()) {
                    maze.getPacman().setDirection(Direction.LEFT);
				} else if (gameFrame.isRightPressed()) {
                    maze.getPacman().setDirection(Direction.RIGHT);
				}
				gameFrame.resetMove();
			}


			// Move PACMAN
            Pacman pacman = maze.getPacman();
			Tile nextTilePacman = maze.getTile(pacman,pacman.getDirection());
            // Checks if the tile exists (not out of the maze)
            if(nextTilePacman != null) {
                // Checks if the tile isn't a wall
                if(!nextTilePacman.isWall()) {
                    pacman.move(nextTilePacman);
                }
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
