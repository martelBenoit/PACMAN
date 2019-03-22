package main.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import main.Character;

public class GameFrame{

    public static final int WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Image canvasImage;
    private ArrayList<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private ArrayList<Character> characters;

    private JPanel pan = new JPanel();
    private JPanel panUp = new JPanel();
    private JPanel panDown = new JPanel();

    private JLabel level;
    private JLabel score;
    private JLabel highScore;
    private JLabel lives;

    private Font font;
    private Font fontGameOver;

    private Boolean gameOver = false;

    private static GameFrame instance;
	private boolean upPressed, downPressed, leftPressed, rightPressed, directionChanged = false;


    private GameFrame(){

        frame = new JFrame();
        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setContentPane(pan);
        frame.setIconImage(new ImageIcon("lib/pacman_open.png").getImage());

        JPanel panMain = new JPanel();
        panMain.setLayout(new BoxLayout(panMain,BoxLayout.Y_AXIS));

        panUp.setLayout(new GridLayout(1,2));
        panDown.setLayout(new GridLayout(1,2));

        // Pour le panMain
        this.level = new JLabel();
        this.score = new JLabel();
        this.highScore = new JLabel();
        this.lives = new JLabel();


        try {
           this.font  = Font.createFont(Font.TRUETYPE_FONT, new File("lib/VCR_OSD_MONO_1.001.ttf")).deriveFont(Font.PLAIN, 40);
           this.fontGameOver = Font.createFont(Font.TRUETYPE_FONT, new File("lib/PAC-FONT.ttf")).deriveFont(Font.PLAIN, 40);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.level.setFont(this.font);
        this.score.setFont(this.font);
        this.highScore.setFont(this.font);
        this.lives.setFont(this.font);

        this.level.setText("LEVEL 1");
        this.level.setForeground(Color.WHITE);

        this.score.setText("0");
        this.score.setForeground(Color.WHITE);
        this.score.setHorizontalAlignment(SwingConstants.LEFT);

        this.highScore.setText("HIGH 0");
        this.highScore.setForeground(Color.WHITE);
        this.highScore.setHorizontalAlignment(SwingConstants.RIGHT);

        this.lives.setText("LIVES");
        this.lives.setForeground(Color.WHITE);
        this.lives.setHorizontalAlignment(SwingConstants.LEFT);

        pan.setBackground(Color.black);
        panMain.setBackground(Color.black);
        panUp.setBackground(Color.black);
        panDown.setBackground(Color.black);
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        panUp.add(this.score);
        panUp.add(this.highScore);
        panDown.add(this.lives);
        panMain.add(panUp);
        panMain.add(canvas);
        panMain.add(panDown);
        pan.add(panMain);
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
        this.level.validate();
    }

    public void setScore(int score){
        this.score.setText(""+score);
        this.score.validate();
    }

    public void setHighScore(int highScore){
        this.highScore.setText("HIGH "+highScore);
        this.highScore.validate();
    }

    public void setLives(int lives){
        if (lives > 1)
            this.lives.setText("LIVES "+lives);
        else
            this.lives.setText("LIVE "+lives);
        this.lives.validate();
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

    public void setDimensionPan(int width, int height){
        this.canvas.setPreferredSize(new Dimension(width,height));
        this.canvas.revalidate();
        this.panUp.setPreferredSize(new Dimension(width,40));
        this.panUp.revalidate();
        this.panDown.setPreferredSize(new Dimension(width,40));
        this.panDown.revalidate();
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setPreferredSize(new Dimension((int)(width*1.2),(int)((height+80)*1.1)));
        frame.pack();
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

    public void drawGameOver(){
        this.gameOver = true;
        redraw();
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

            if(gameOver){
                g.setColor(Color.RED);
                g.setFont(fontGameOver.deriveFont(Font.PLAIN,70));
                int x = canvas.getWidth()/2 - (int) g.getFontMetrics().getStringBounds("game over",g).getWidth()/2;
                g.drawString("game over",x,(canvas.getHeight()/2));
                g.setColor(Color.WHITE);
                g.drawString("GAME OVER",x,(canvas.getHeight()/2));
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
