package control;

import model.Game;

public class Controller{

    private Game game;

    public Controller(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

}
