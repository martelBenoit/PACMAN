package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <BODY>
 * <HTML>
 *     Pacman class. <br>
 *     Pacman class extends the Character class. <br>
 *     This class is used to manage the Pacman character in the game. <br>
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Character
 */
public class Pacman extends Character {

    /**
     * Pacman is powerful.
     */
    private boolean hasPower;

    /**
     * Pacman's mouth is open.
     */
    private boolean isOpenMouth;

    /**
     * The direction that Pacman wants.
     */
    private Direction wantedDirection;

    /**
     * The direction of Pacman.
     */
    private Direction direction;

    /**
     * The basic image of pacman with open mouth.
     */
    private BufferedImage image_op = null;

    /**
     * The basic image of pacman with closed mouth.
     */
    private BufferedImage image_cl = null;

    /**
     * The power image of pacman with open mouth.
     */
    private BufferedImage image_p_op = null;

    /**
     * The power image of pacman with closed mouth.
     */
    private BufferedImage image_p_cl = null;

    /**
     * Modified image of pacman with open mouth.
     */
    private BufferedImage imageActualOp = null;

    /**
     * Modified image of pacman with closed mouth.
     */
    private BufferedImage imageActualCl = null;


    /**
     * <BODY>
     * <HTML>
     *     Constructor of the class Pacman. <br>
     *     Create a Pacman object. <br>
     *     As a parameter of the constructor, the tile on which Pacman is located when it's created.
     * </BODY>
     * </HTML>
     * @param tile the tile on which Pacman is located when it's created.
     */
    public Pacman(Tile tile){

        // Appelle au constructeur de Character
    	super(tile);
        isOpenMouth = true;

        try {
            image_op = ImageIO.read(new File("lib/pacman_open.png"));
            image_cl = ImageIO.read(new File("lib/pacman_close.png"));
            image_p_op = ImageIO.read(new File("lib/pacman_power_open.png"));
            image_p_cl = ImageIO.read(new File("lib/pacman_power_close.png"));
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


    @Override
    public void move(Tile nextTile) {
        this.setTile(nextTile);
    }

    /**
     * This method allows you to change the direction of Pacman's move.
     * Depending on the direction, Pacman's mouth changes direction.
     * @param d the new pacman direction.
     */
    public void setDirection(Direction d) {

        this.direction = d;

        BufferedImage tmp_image_op, tmp_image_cl;

        if(this.hasPower){
            tmp_image_op =image_p_op;
            tmp_image_cl = image_p_cl;
        }
        else{
            tmp_image_op = image_op;
            tmp_image_cl = image_cl;
        }
        if(this.direction == Direction.UP) {
            this.imageActualOp = rotateImage(tmp_image_op, 270);
            this.imageActualCl = rotateImage(tmp_image_cl, 270);
        }
        else if(this.direction == Direction.DOWN) {
            this.imageActualOp = rotateImage(tmp_image_op, 90);
            this.imageActualCl = rotateImage(tmp_image_cl,90);
        }
        else if(this.direction == Direction.LEFT) {
            this.imageActualOp = rotateImage(tmp_image_op, 180);
            this.imageActualCl = rotateImage(tmp_image_cl,180);
        }
        else {
            this.imageActualOp = tmp_image_op;
            this.imageActualCl = tmp_image_cl;
        }
    }

    /**
     * This method retrieves the direction in which is pacman.
     * @return the direction in which is pacman.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * This method allows you to change the wanted direction of Pacman's move.
     * @param wantedDirection the wanted direction
     */
    public void setWantedDirection(Direction wantedDirection) {
        this.wantedDirection = wantedDirection;
    }

    /**
     * This method retrieves the wanted direction in which is pacman.
     * @return the wanted direction.
     */
    public Direction getWantedDirection() {
        return wantedDirection;
    }

    /**
     * This method allows to know if pacman is powerful or not.
     * @return true if pacman is powerful, false otherwise
     */
    public boolean getHasPower(){
        return this.hasPower;
    }

    /**
     * This method allows to put pacman powerful or not.
     * @param power true, pacman becomes powerful, otherwise becomes normal again
     */
    public void setHasPower(Boolean power){
        this.hasPower = power;
    }


    /**
     * This method allows you to retrieve the image of pacman to come later to draw it.
     * At each call of this method, the mouth comes alive. Once the mouth is open, the call to this same method
     * the next time returns the image of pacman with the mouth closed
     * @return pacman's image according to his direction
     */
    public BufferedImage getImage(){
        BufferedImage ret;

        if(isOpenMouth)
            ret = this.imageActualOp;
        else
            ret =  this.imageActualCl;
        isOpenMouth = !isOpenMouth;
        return ret;

    }

    /**
     * This static method makes it possible to rotate the pacman image according to an angle passed in parameter.
     * @param image the image to rotate
     * @param angle the angle in which the image must rotate (degree)
     * @return the new image rotated
     */
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
        at.translate( (newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

}