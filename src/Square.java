import view.GameFrame;

import java.awt.*;


public class Square extends Figure
{


    public Square(int size, int x, int y, Color color) {

        super(size, size, x, y, color);
    }


    public void setSize(int width, int height) {
        assert width >= 0 && height == width : "Wrong dimensions";
        super.setSize(width, height);
    }


    protected void draw() {
        GameFrame gameFrame = GameFrame.getGameFrame();
        System.out.println(gameFrame +" "+getX()+" "+getY()+" "+getHeight()+" "+getWidth());
        gameFrame.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }


    protected void invariant() {
        super.invariant();
        assert getWidth() == getHeight() : "Invariant violated: wrong dimensions";
    }
}
