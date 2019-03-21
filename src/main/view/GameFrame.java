package main.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import main.Character;

public class GameFrame{

    public static final int WIDTH = 700, HEIGHT = 700;
    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Image canvasImage;
    private ArrayList<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private ArrayList<Character> characters;

    private JLabel level;
    private JLabel score;

    private Font font;

    private static GameFrame instance;
	private boolean upPressed, downPressed, leftPressed, rightPressed, directionChanged = false;

    private GameFrame(){

        frame = new JFrame();
        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(750,900));

        JPanel pan = new JPanel();
        this.level = new JLabel();

        try {
           this.font  = Font.createFont(Font.TRUETYPE_FONT, new File("lib/VCR_OSD_MONO_1.001.ttf")).deriveFont(Font.PLAIN, 40);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.level.setFont(this.font);

        this.level.setText("LEVEL 1");
        this.level.setForeground(Color.WHITE);

        pan.setBackground(Color.black);
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(700,700));
        frame.setContentPane(pan);
        pan.add(level);
        pan.add(canvas);
        frame.pack();

        objects = new ArrayList<>();
        shapes = new HashMap<>();
        characters = new ArrayList<>();

        canvas.setFocusable(true);
        this.setVisible(true);

        canvas.addKeyListener(new KeyboardListener());
    }

    public void setLevel(int level){
        this.level.setText("LEVEL "+level);
    }


    
    public static GameFrame getGameFrame()
    {
        if(instance == null) {
            instance = new GameFrame();
        }
        instance.setVisible(true);
        return instance;
    }

    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    public void drawCharacter(Character c){
        characters.remove(c);
        characters.add(c);
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
        characters.remove(referenceObject);
    }


    public void wait(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        }
        catch (Exception e) {
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
        graphic.setColor(Color.black);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }
    
    /**
     * Check whether the UP key is currently pressed
     * @return true if the UP key is currently pressed
     */
    public boolean isUpPressed()
    {
        return upPressed;
    }

    /**
     * Check whether the DOWN key is currently pressed
     * @return true if the DOWN key is currently pressed
     */
    public boolean isDownPressed()
    {
        return downPressed;
    }
    
    /**
     * Check whether a key has been pressed
     * @return true a key has been pressed
     */
    public boolean hasChangedDirection()
    {
        return directionChanged;
    }

    /**
     * Check whether the LEFT key is currently pressed
     * @return true if the LEFT key is currently pressed
     */
    public boolean isLeftPressed()
    {
		return leftPressed;
    }

    /**
     * Check whether the RIGHT key is currently pressed
     * @return true if the RIGHT key is currently pressed
     */
    public boolean isRightPressed()
    {
		return rightPressed;
    }

    public void resetMove(){
		rightPressed = false;
		leftPressed = false;
		upPressed = false;
		downPressed = false;
		directionChanged = false;
	}
    
	private class KeyboardListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
        	    case KeyEvent.VK_UP:
					upPressed = true;
					directionChanged = true;
					break;
        	    case KeyEvent.VK_DOWN:
					downPressed = true;
					directionChanged = true;
					break;
        	    case KeyEvent.VK_LEFT:
					leftPressed = true;
					directionChanged = true;
					break;
        	    case KeyEvent.VK_RIGHT:
					rightPressed = true;
					directionChanged = true;
					break;
            }
        }
	}


    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
            for(Character c: characters) {
                int x = c.getX();
                int y = c.getY();
                int width = c.getWidthImage();
                int height = c.getHeightImage();
                g.drawImage(c.getImage(), x, y, width, height,null);

            }
        }
    }

    private class ShapeDescription
    {
        private Shape shape;
        private Color color;

        public ShapeDescription(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public void draw(Graphics2D graphic) {
            graphic.setColor(color);
            graphic.fill(shape);
        }
    }


}
