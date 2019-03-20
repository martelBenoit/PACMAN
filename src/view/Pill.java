package view;

import java.awt.*;

public class Pill extends Figure {

    private Figure[] figures;


    public Pill(int size, int x, int y, Color color) {
        super(size, size, x, y, color);
        this.figures = new Figure[2];
        this.figures[0] = new Square(size, x, y, Color.black);
        int sg = size/4;
        int xg = x+(size/2)-(sg/2);
        int yg = y+(size/2)-(sg/2);
        this.figures[1] = new Circle(sg,xg,yg,Color.WHITE);
    }


    public void draw() {
        for (Figure f : this.figures) {
            if (f != null) {
                f.draw();
            }
        }
    }
}
