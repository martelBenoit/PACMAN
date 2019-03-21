package main;


import java.awt.image.BufferedImage;

public class Pacman extends Character {

    private boolean hasPower;
    private boolean isOpenMouth;

    public Pacman(Tile tile, String nameImage){
    	super(tile,nameImage);
        this.tile = tile;
        this.setDirection(Direction.LEFT);
        isOpenMouth = true;
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

    @Override
    public BufferedImage getImage(){
        BufferedImage ret;

        if(isOpenMouth)
            ret = this.imageActual;
        else
            ret =  this.image_cl;
        isOpenMouth = !isOpenMouth;
        return ret;

    }



}
