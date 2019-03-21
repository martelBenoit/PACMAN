package main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pacman extends Character {

    private boolean hasPower;
    private boolean isOpenMouth;
    private Direction wantedDirection;
    private Direction direction;

    private BufferedImage image_op = null;
    private BufferedImage image_cl = null;
    private BufferedImage imageActualOp = null;
    private BufferedImage imageActualCl = null;

    public Pacman(Tile tile){
    	super(tile);
        this.tile = tile;
        isOpenMouth = true;

        try {
            image_op = ImageIO.read(new File("lib/pacman_open.png"));
            image_cl = ImageIO.read(new File("lib/pacman_close.png"));
            imageActualOp = image_op;
            imageActualCl = image_cl;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.setDestWidth((int) (image_op.getWidth() * tile.getSize() / image_op.getWidth() * 0.75));
        this.setDestHeight((int) (image_op.getHeight() * tile.getSize() / image_op.getHeight() * 0.75));
        centerImage(getWidthImage(), getHeightImage());


        this.setDirection(Direction.LEFT);
        this.setWantedDirection(Direction.LEFT);

    }

    public void setDirection(Direction d) {
        this.direction = d;
        if(this.direction == Direction.UP) {
            this.imageActualOp = rotateImage(image_op, 270);
            this.imageActualCl = rotateImage(image_cl, 270);
        }
        else if(this.direction == Direction.DOWN) {
            this.imageActualOp = rotateImage(image_op, 90);
            this.imageActualCl = rotateImage(image_cl,90);
        }
        else if(this.direction == Direction.LEFT) {
            this.imageActualOp = rotateImage(image_op, 180);
            this.imageActualCl = rotateImage(image_cl,180);
        }
        else {
            this.imageActualOp = image_op;
            this.imageActualCl = image_cl;
        }
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

    public Direction getDirection() {
        return this.direction;
    }

    public Direction getWantedDirection() {
        return wantedDirection;
    }

    public void setWantedDirection(Direction wantedDirection) {
        this.wantedDirection = wantedDirection;
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

    public BufferedImage getImage(){
        BufferedImage ret;

        if(isOpenMouth)
            ret = this.imageActualOp;
        else
            ret =  this.imageActualCl;
        isOpenMouth = !isOpenMouth;
        return ret;

    }



}
