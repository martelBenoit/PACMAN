package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class GameFrame
{
    public static final int WIDTH = 700, HEIGHT = 700;

    private static GameFrame instance;

    public static GameFrame getCanvas()
    {
        if(instance == null) {
            instance = new GameFrame();
        }
        instance.setVisible(true);
        return instance;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;


    private GameFrame()
    {
        JPanel pan = new JPanel();
        JPanel panMaze = new JPanel();
        panMaze.setLayout(new FlowLayout());
        panMaze.setBackground(Color.black);
        JLabel title = new JLabel("PACMAN");
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("MontSerrat", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        pan.setBackground(Color.BLACK);

        BoxLayout box = new BoxLayout(pan, BoxLayout.Y_AXIS);
        pan.setLayout(box);



        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(pan);
        pan.add(title);
        panMaze.add(canvas);
        pan.add(panMaze);

        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(30, 30);
        pan.setPreferredSize(new Dimension(750,900));
        canvas.setPreferredSize(new Dimension(700,700));
        backgroundColor = Color.black;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();

        canvas.setFocusable(true);


    }


    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }


    public void draw(Object referenceObject, Color color, Shape shape)
    {
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape,color));
    }


    public void erase(Object referenceObject)
    {
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
    }


    public void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (Exception e)
        {
            // ignoring exception at the moment
        }
    }

    public void redraw()
    {
        erase();
        for(Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }

        canvas.repaint();
        wait(125);
    }

    private void erase()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    private class ShapeDescription
    {
        private Shape shape;
        private Color color;

        public ShapeDescription(Shape shape, Color color)
        {

            this.shape = shape;
            this.color = color;
        }

        public void draw(Graphics2D graphic)
        {
            graphic.setColor(color);
            graphic.fill(shape);
        }
    }

}