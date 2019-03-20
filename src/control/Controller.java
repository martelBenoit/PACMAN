package control;

import model.Game;
import view.GameFrame;

public class Controller {

    private Game game;
    private ReadMaze maze;

    private GameFrame gameFrame;

    public Controller(){
        this.maze = new ReadMaze(1);
        this.draw();
        GameFrame gameFrame = GameFrame.getCanvas();
        gameFrame.redraw();



    }

    public Game getGame(){
        return game;
    }

    public void draw () {
        this.maze.draw();

    }

}
