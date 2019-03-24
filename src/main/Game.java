package main;

import java.io.*;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import main.view.GameFrame;

/**
 *
 * @author Benoît & Yoann
 * @version 1.0
 */
class Game {

    /**
     * The score of the game.
     */
    private int score;

    /**
     * The list of highscores.
     */
    private ArrayList<Integer> highScores;

    /**
     * The actual number of lives.
     */
    private int numberOfLives;

    /**
     * The initial number of lives.
     */
    private final int INITIALNUMBEROFLIVES;

    /**
     * The maze of level.
     */
    private Maze maze;

    /**
     * The game frame.
     */
    private GameFrame gameFrame;

    private Pacman pacman;

    public void invariant() {
        assert this.score >= 0 : "The score can't be negative";
        assert this.numberOfLives >= 0 : "The number of lives can't be negative";
    }
    /**
     * The constructor of the class Game.
     * @param lives the initial number of lives.
     */
    Game(int lives) {

        // Load score of previous games
        loadHighScores();

        this.numberOfLives = this.INITIALNUMBEROFLIVES = lives;
        this.gameFrame = GameFrame.getGameFrame();

        this.maze = new Maze(2);

        // Draw the maze
        this.maze.draw();

        // Draw the frame of the game
        this.gameFrame.redraw();

        this.gameFrame.setHighScore(this.highScores.get(0));
        this.gameFrame.setLives(lives);

        this.pacman = this.maze.getPacman();

        invariant();

        // Wait before the game start
        waitStart();

    }

    /**
     * Checks every 0.1 seconds if the user started the game
     */
	private void waitStart(){
	    boolean start = false;
	    while(!start){
	        if(gameFrame.isStartGame()){
	            start = true;
	            startGame();
            }
	        else{
	           gameFrame.wait(100);
            }
        }
    }

    private void startGame() {

        int level = 1;
        this.score = 0;

        boolean pacmanEaten;

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("lib/start.wav")));
            clip.setMicrosecondPosition(10_000);
            clip.start();
            while (!clip.isRunning())
                gameFrame.wait(10);
            while (clip.isRunning())
                gameFrame.wait(10);
            clip.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        // Main loop
        while (this.numberOfLives > 0) {

            Clip clip = null;

            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("lib/chomp.wav")));
                clip.setLoopPoints(1_700, 14_700);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

            this.pacman = maze.getPacman();

            while (maze.getPills().size() > 0 && this.numberOfLives > 0) {

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


                // Move Pacman
                this.movePacman();

                // Eat pill on the tile (if there is a pill)
                this.eatPill();

                // Move every ghosts
                this.moveGhosts();

                // Check if pacman eat a ghost, or a ghost eat pacman
                pacmanEaten = this.checkEaten();


                gameFrame.redraw();
                // If pacman was eaten, wait 2 seconds before resume game
                if (pacmanEaten) {
                    try {
                        clip.stop();
                        Clip death = AudioSystem.getClip();
                        death.open(AudioSystem.getAudioInputStream(new File("lib/death.wav")));
                        death.start();
                        while (!death.isRunning())
                            gameFrame.wait(10);
                        while (death.isRunning())
                            gameFrame.wait(10);
                        death.close();
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    gameFrame.wait(1000);

                    clip.setMicrosecondPosition(1_000);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                } else {
                    gameFrame.wait(100);
                }


                invariant();

            }

            if (numberOfLives > 0) {
                int prevNbLives = this.numberOfLives;
                int prevScore = this.score;

                clip.stop();
                level += 1;
                gameFrame.setLevel(level);
                gameFrame.eraseCharacter();
                this.maze = new Maze(2);
                this.maze.draw();
                gameFrame.redraw();
                gameFrame.wait(2000);
                clip.start();

                assert(this.score == prevScore) : "Le score a été modifié durant le changement de niveau";
                assert(this.numberOfLives == prevNbLives) : "Le nombre de vies a été modifié durant le changement de niveau";
            }


            clip.stop();
        }

		endGame();
    }

    private void movePacman(){


	    Tile nextTilePacman = this.maze.getTile(this.pacman, this.pacman.getWantedDirection());
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
            nextTilePacman = this.maze.getTile(pacman, pacman.getDirection());
            if (nextTilePacman != null) {
                // Checks if the tile isn't a wall
                if (!nextTilePacman.isWall()) {
                    this.pacman.setLastTile(pacman.getTile());
                    this.pacman.move(nextTilePacman);
                }
            }
        }

        invariant();

    }

    private void eatPill(){

	    int prevScore = this.score;

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

            assert(pillToRemove.getTile() != maze.getPacman().getTile()) : "Pacman doit manger les pills se trouvant sur la même case que lui";
            assert(pillToRemove instanceof FruitPill ? score == prevScore + maze.getFruitValue() : score == prevScore + maze.getPillValue()) : "Le score n'a pas été correctement augmenté";
        }

        invariant();
    }

    private void moveGhosts() {

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

        invariant();
    }

    // Check if pacman is eaten by a ghost (or a ghost is eaten by pacman)
    private boolean checkEaten() {
        boolean pacmanEaten = false;

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
                    int prevNbLives = this.numberOfLives;
                    this.loseLife();
                    pacmanEaten = true;
                    int newNbLives = this.numberOfLives;

                    assert (newNbLives == prevNbLives-1) : "Pacman doit perdre une vie s'il est sur la même case qu'un fantôme et qu'il na pas de pouvoir.";
                }
            }
        }


        invariant();
        return pacmanEaten;
    }

    private void endGame() {
        int sizeHighScores = this.highScores.size();

        Collections.sort(this.highScores);
        Collections.reverse(this.highScores);

        // S'il reste de la place dans le tableau
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


        invariant();

        waitRestart();


    }

    private void waitRestart(){

        boolean restart = false;
        while(!restart){
            if(gameFrame.isRestartGame()){
                restart = true;
                this.numberOfLives = this.INITIALNUMBEROFLIVES;
                gameFrame.eraseCharacter();

                this.maze = new Maze(2);
                // Draw the maze
                this.maze.draw();

                // Draw the frame of the game
                this.gameFrame.redraw();
                this.gameFrame.setRestartGame(false);


                this.gameFrame.setHighScore(this.highScores.get(0));
                this.gameFrame.setLives(this.numberOfLives);



                startGame();
            }
            else
                gameFrame.wait(100);
        }

    }

    private void loseLife() {
        this.numberOfLives--;
        gameFrame.setLives(numberOfLives);
        Tile pacmanSpawnTile = this.maze.getPacmanSpawnTile();
        Tile ghostSpawnTile = this.maze.getGhostSpawnTile();
        if(numberOfLives > 0) {
            this.maze.getPacman().setTile(pacmanSpawnTile == null ? maze.getRandomTile() : pacmanSpawnTile);
            this.maze.getPacman().setDirection(Direction.LEFT);
            this.maze.getPacman().setWantedDirection(Direction.LEFT);
            for(Ghost g : this.maze.getGhosts()) {
                g.setTile(ghostSpawnTile == null ? maze.getRandomTile() : ghostSpawnTile);
            }
        }

        invariant();
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

        invariant();

    }

    private void saveHighScores(){
        try{
            PrintWriter writer = new PrintWriter("lib/highscores.pac");

            StringBuffer highScores = new StringBuffer(110);
            highScores.append(this.highScores.get(0));

            for(int i = 1; i<this.highScores.size(); i++){
                highScores.append(",").append(this.highScores.get(i));
            }
            writer.print(highScores);
            writer.close();

        }
        catch(Exception e){
            System.out.println("High scores not save !");
            System.out.println(e.getMessage());
        }

        invariant();
    }

}