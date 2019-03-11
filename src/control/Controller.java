package control;

import model.Game;
import view.GameFrame;

public class Controller {

    private Game game;

    private GameFrame gameFrame;

    public Controller(Game game){
        this.game = game;
        this.gameFrame = new GameFrame(this);
    }

    public Game getGame(){
        return game;
    }

}
