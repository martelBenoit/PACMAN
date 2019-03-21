package main;

import main.view.GameFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Character extends Figure{

    protected Tile tile;
    protected BufferedImage image = null;
    protected BufferedImage imageActual = null;
    private Direction direction;
    private Direction wantedDirection;

    private int destWidth;
    private int destHeight;

    public Character(Tile tile, String pathImage){

        super(tile.getSize(),tile.getSize(),tile.getX(),tile.getY());

        try{
            image = ImageIO.read(new File(pathImage));
            imageActual = image;
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        destWidth =  (int)(image.getWidth() * tile.getSize()/image.getWidth()*0.75);
        destHeight = (int)(image.getHeight() * tile.getSize()/image.getHeight()*0.75);
        centerImage(destWidth,destHeight);
        this.direction = Direction.LEFT;
        this.wantedDirection = Direction.LEFT;

    }

    public abstract void move(Tile nextTile);

    public void setTile(Tile tile) {
        this.tile = tile;
        this.changePosition(this.tile.getX(),this.tile.getY());
        centerImage(destWidth,destHeight);
    }

    public Tile getTile() {
        return tile;
    }

    public void setWantedDirection(Direction wantedDirection) {
        this.wantedDirection = wantedDirection;
    }

    public void setDirection(Direction d) {
		this.direction = d;
		if(this.direction == Direction.UP)
		    imageActual = rotateImage(image,270);
        else if(this.direction == Direction.DOWN)
            imageActual = rotateImage(image,90);
        else if(this.direction == Direction.LEFT)
            imageActual = rotateImage(image,180);
        else
            imageActual = image;
	}

    public Direction getWantedDirection() {
        return wantedDirection;
    }

    public Direction getDirection() {
		return this.direction;
	}

    public BufferedImage getImage(){
        return this.imageActual;
    }

    public int getWidthImage(){
        return this.destWidth;
    }

    public int getHeightImage(){
        return this.destHeight;
    }

    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        gameFrame.drawCharacter(this);
    }


    private static BufferedImage rotateImage(BufferedImage image, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = image.getWidth();
        int h = image.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }


}
