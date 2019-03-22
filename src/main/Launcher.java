package main;

/**
 * Launcher class of game Pacman
 */
public class Launcher {

    /**
     * Method main
     * This method start a new Game
     * @param args nothing to put
     */
    public static void main(String[] args){
        Game game = new Game(3);
        game.startGame();

    }
}
