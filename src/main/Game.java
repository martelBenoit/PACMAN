package main;

import java.io.*;
import java.util.*;

import main.view.GameFrame;

/**
 *
 * @author Benoît & Yoann
 * @version 1.0
 */
public class Game {

    /**
     * The score of the game.
     */
    private int score;

    /**
     * The level of the game.
     */
    private int level;

    /**
     * The list of highscores.
     */
    private ArrayList<Integer> highScores;

    /**
     * The actual number of lives.
     */
    private int numberOfLives;

    /**
     * The maze of level.
     */
    private Maze maze;

    /**
     * The game frame.
     */
    private GameFrame gameFrame;

    private Pacman pacman;


    /**
     * The constructor of the class Game.
     * @param lives the initial number of lives.
     */
    public Game(int lives) {

        // Load score of previous games
        loadHighScores();

        this.numberOfLives = lives;
        this.gameFrame = GameFrame.getGameFrame();

        this.maze = new Maze(2);

        // Draw the maze
        this.maze.draw();

        // Draw the frame of the game
        this.gameFrame.redraw();

        this.gameFrame.setHighScore(this.highScores.get(0));
        this.gameFrame.setLives(lives);

        this.pacman = this.maze.getPacman();

        // Wait before the game start
        preGame();

    }

    /**
     * Getter number of lives.
     * @return the number of lives.
     */
    public int getNumberOfLives() {
		return this.numberOfLives;
	}

    /**
     * Getter maze.
     * @return the actual maze of the maze.
     */
	public Maze getMaze() {
		return this.maze;
	}

	// TODO : CEST DEGUEULASSE MAIS JE SAIS PAS COMMENT FAIRE AUTREMENT DIS MOI SI TU SAIS
	public void preGame(){
	    Boolean lance = false;
	    while(!lance){
	        if(gameFrame.isStartGame()){
	            lance = true;
	            startGame();
            }
	        else
	            System.out.println();
        }
    }

    // TODO : Créer méthodes pour clarifier celle-ci
    public void startGame() {

        this.level = 1;
        this.score = 0;

        boolean pacmanEaten = false;

        // Wait 1 second before the game start
        gameFrame.wait(1000);

        // Main loop
        while (this.getNumberOfLives() > 0) {

            Maze maze = this.getMaze();
            this.pacman = maze.getPacman();

            while (maze.getPills().size() > 0 && this.getNumberOfLives() > 0) {

                // Change Pacman direction
                if (gameFrame.hasChangedDirection()) {
                    if (gameFrame.isUpPressed()) {
                        this.pacman.setWantedDirection(Direction.UP);
                    } else if (gameFrame.isDownPressed()) {
                        this.pacman.setWantedDirection(Direction.DOWN);
                    } else if (gameFrame.isLeftPressed()) {
                        this.pacman.setWantedDirection(Direction.LEFT);
                    } else if (gameFrame.isRightPressed()) {
                        this.pacman.setWantedDirection(Direction.RIGHT);
                    }
                    gameFrame.resetMove();
                }


                // Move PACMAN
                this.movePacman();

                this.eatPill();

                pacmanEaten = this.moveGhost(pacmanEaten);


                gameFrame.redraw();
                if (pacmanEaten) {
                    gameFrame.wait(2000);
                } else {
                    gameFrame.wait(100);
                }
            }

            if (numberOfLives > 0) {
                this.level += 1;
                gameFrame.setLevel(this.level);
                pacman.erase();
                for (Ghost g : maze.getGhosts()) {
                    g.erase();
                }
                this.maze = new Maze(2);
                this.maze.draw();
                gameFrame.redraw();
            }
        }

		endGame();
    }

    private void movePacman(){


	    Tile nextTilePacman = maze.getTile(this.pacman, this.pacman.getWantedDirection());
        boolean moved = false;
        // Checks if the tile exists (not out of the maze)
        if (nextTilePacman != null) {
            // Checks if the tile isn't a wall
            if (!nextTilePacman.isWall()) {
                this.pacman.setLastTile(this.pacman.getTile());
                this.pacman.move(nextTilePacman);
                moved = true;
                this.pacman.setDirection(this.pacman.getWantedDirection());
            }
        }
        if (!moved) {
            nextTilePacman = maze.getTile(pacman, pacman.getDirection());
            if (nextTilePacman != null) {
                // Checks if the tile isn't a wall
                if (!nextTilePacman.isWall()) {
                    this.pacman.setLastTile(pacman.getTile());
                    this.pacman.move(nextTilePacman);
                }
            }
        }

    }

    private void eatPill(){
        // Eat Pill on the tile
        Pill pillToRemove = null;

        // Tile of pacman
        Tile pacmanTile = pacman.getTile();

        for(Pill p : maze.getPills()){
            if(p.getTile() == pacmanTile){
                pillToRemove = p;
                if (p instanceof FruitPill)
                    score += maze.getFruitValue();
                else if (p instanceof PowerPill) {
                    pacman.setHasPower(true);
                    for(Ghost g : maze.getGhosts()){
                        g.setEatable(true);
                    }
                    score += maze.getPillValue();

                    // Schedule the removal of power effect
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            pacman.setHasPower(false);
                            for(Ghost g : maze.getGhosts()){
                                g.setEatable(false);
                            }
                        }
                    }, maze.getPowerTime() * 1000);

                } else
                    score += maze.getPillValue();

                gameFrame.setScore(score);
                if (score >= this.highScores.get(0)) {
                    gameFrame.setHighScore(score);
                }
            }
        }

        if (pillToRemove != null) {

            Tile t = pillToRemove.getTile();
            pillToRemove.removeTile();
            t.draw();
            maze.getPills().remove(pillToRemove);
        }
    }

    private boolean moveGhost(Boolean pacmanEaten){

        for (Ghost g : maze.getGhosts()) {
            if (g.isAlive()) {
                ArrayList<Tile> tilesAround = maze.getTilesAround(g);
                if (g.getLastTile() != null) {
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
        }

        // Check if pacman is eaten by a ghost (or a ghost is eaten by pacman)
        pacmanEaten = false;

        for (Ghost g : maze.getGhosts()) {
            if (g.getTile() == pacman.getTile() || (g.getLastTile() == pacman.getTile() && g.getTile() == pacman.getLastTile())) {
                if (pacman.getHasPower()) {
                    g.setAlive(false);
                    score += 200;
                    Tile ghostSpawnTile = maze.getGhostSpawnTile();
                    g.setTile(ghostSpawnTile == null ? maze.getRandomTile() : ghostSpawnTile);

                    // Schedule the respawn of the ghost
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            g.setAlive(true);
                        }
                    }, maze.getRegenerationTime() * 1000);

                } else {
                    this.loseLife();
                    pacmanEaten = true;
                }
            }
        }

        return pacmanEaten;
    }

    public void endGame() {
        int sizeHighScores = this.highScores.size();

        Collections.sort(this.highScores);
        Collections.reverse(this.highScores);

        // Si il reste de la place dans le tableau
        if(sizeHighScores < 10){
            this.highScores.add(score);
        }
        else if(score > this.highScores.get(sizeHighScores-1)){
            this.highScores.set(sizeHighScores-1,score);
        }

        Collections.sort(this.highScores);
        Collections.reverse(this.highScores);
        saveHighScores();

        gameFrame.showEndFrame(this.highScores,score);

    }

    public void loseLife() {
        this.numberOfLives--;
        gameFrame.setLives(numberOfLives);
        Tile pacmanSpawnTile = this.getMaze().getPacmanSpawnTile();
        Tile ghostSpawnTile = this.getMaze().getGhostSpawnTile();
        if(numberOfLives > 0) {
            this.getMaze().getPacman().setTile(pacmanSpawnTile == null ? maze.getRandomTile() : pacmanSpawnTile);
            this.getMaze().getPacman().setDirection(Direction.LEFT);
            this.getMaze().getPacman().setWantedDirection(Direction.LEFT);
            for(Ghost g : this.getMaze().getGhosts()) {
                g.setTile(ghostSpawnTile == null ? maze.getRandomTile() : ghostSpawnTile);
            }
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
            Collections.reverse(this.highScores);
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
            writer.print(highScores);
            writer.close();

        }
        catch(Exception e){
            System.out.println("High scores not save !");
            System.out.println(e.getMessage());
        }
    }

}