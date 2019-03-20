package main;


import main.view.GameFrame;

public class Pacman extends Character {

    private boolean hasPower;

    public Pacman(Tile tile, String pathImage){
    	super(tile,pathImage);
        this.tile = tile;
        this.setDirection(Direction.LEFT);
    }

    public boolean eatPill(){
        return false;
    }

    public boolean eatGhost(){
        return false;
    }

    @Override
    public void move(Tile nextTile) {
		this.setTile(nextTile);
	}


    public void setHasPower(Boolean power){
        power = this.hasPower;
    }

    public boolean getHasPower(){
        return this.hasPower;
    }




}
