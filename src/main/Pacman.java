package main;


public class Pacman extends Character {

    private boolean hasPower;

    private boolean isOpenMouth;


    public Pacman(Tile tile, String nameImage){
    	super(tile,nameImage);
        this.tile = tile;
        this.setDirection(Direction.LEFT);
        this.isOpenMouth = true;
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
        if(isOpenMouth)
            imageActual = image_op;
        else
            imageActual = image_cl;
        isOpenMouth = !isOpenMouth;
		this.draw();
	}


    public void setHasPower(Boolean power){
        power = this.hasPower;
    }

    public boolean getHasPower(){
        return this.hasPower;
    }




}
