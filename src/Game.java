import java.util.ArrayList;
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


        
        // Boucle principale
		//while (this.getNumberOfLives() > 0 && this.getMaze().getPills().size() > 0) { //(this.maps.getNbGom() > 0) && (this.pacman.getLife() > 0)
		while(true) {
			if(gameFrame.hasChangedDirection()) {
				if (gameFrame.isUpPressed()) {
					this.getMaze().getPacman().setDirection(Direction.UP);
				} else if (gameFrame.isDownPressed()) {
					this.getMaze().getPacman().setDirection(Direction.DOWN);
				} else if (gameFrame.isLeftPressed()) {
					this.getMaze().getPacman().setDirection(Direction.LEFT);
				} else if (gameFrame.isRightPressed()) {
					this.getMaze().getPacman().setDirection(Direction.RIGHT);
				}
				gameFrame.resetMove();
			}
			
			//this.getMaze().getPacman().move();
		}
    }

    public void endGame() {
        this.isLost = true;

        // If the score is greater than the lowest high score
        if (this.score > highScores.get(0)) {
            // Mettre le score au bon endroit et réarranger la liste des highscores
        }
    }

}
