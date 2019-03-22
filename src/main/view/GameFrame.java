package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import main.Character;

public class GameFrame{

    public static final int WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();


    private static GameFrame instance;

    private JFrame frame;

    private JPanel panStart;
    private JPanel panGame;
    private JPanel panMain;
    private CanvasPane canvas;
    private JPanel panUp = new JPanel();
    private JPanel panDown = new JPanel();

    private JPanel panEnd;

    private CardLayout card;
    private Container c;

    private JLabel level;
    private JLabel score;
    private JLabel highScore;
    private JLabel lives;

    private JButton button_Start;
    private JButton button_Quit;

    private JPanel tableHigh;
    private JButton button_restart;
    private JButton button_Quit1;

    private Font font;
    private Font fontTitle;
    private Font fontGameOver;

    private Graphics2D graphic;
    private Image canvasImage;

    private ArrayList<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private ArrayList<Character> characters;

    private Boolean gameOver = false;

	private boolean upPressed, downPressed, leftPressed, rightPressed, directionChanged = false;

	private boolean startGame = false;


    private GameFrame(){

        createAndShowHUI();

        objects = new ArrayList<>();
        shapes = new HashMap<>();
        characters = new ArrayList<>();

        canvas.addKeyListener(new KeyboardListener());
        canvas.setFocusable(true);
    }


    private void createAndShowHUI(){

        this.frame = new JFrame();

        c = frame.getContentPane();
        card = new CardLayout();
        c.setLayout(card);

        this.panStart = new JPanel();
        this.panStart.setLayout(new BoxLayout(this.panStart,BoxLayout.Y_AXIS));
        this.panGame = new JPanel();
        this.panEnd = new JPanel();
        this.panEnd.setLayout(new BoxLayout(this.panEnd,BoxLayout.Y_AXIS));



        this.frame.setTitle("Pacman");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

      // this.frame.setContentPane(this.panGame);
        this.frame.setIconImage(new ImageIcon("lib/pacman_open.png").getImage());



        this.panMain = new JPanel();
        panMain.setLayout(new BoxLayout(panMain,BoxLayout.Y_AXIS));

        panUp.setLayout(new GridLayout(1,2));
        panDown.setLayout(new GridLayout(1,2));

        // Pour le panGame
        this.level = new JLabel();
        this.score = new JLabel();
        this.highScore = new JLabel();
        this.lives = new JLabel();


        try {
            this.font  = Font.createFont(Font.TRUETYPE_FONT, new File("lib/VCR_OSD_MONO_1.001.ttf")).deriveFont(Font.PLAIN, 40);
            this.fontGameOver = Font.createFont(Font.TRUETYPE_FONT, new File("lib/PAC-FONT.ttf")).deriveFont(Font.PLAIN, 40);
            this.fontTitle = fontGameOver.deriveFont(Font.PLAIN, 80);
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

        this.highScore.setText("HIGH");
        this.highScore.setForeground(Color.WHITE);
        this.highScore.setHorizontalAlignment(SwingConstants.RIGHT);

        this.lives.setText("LIVES");
        this.lives.setForeground(Color.WHITE);
        this.lives.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel title = new JLabel();
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setText("PACMAN");
        title.setForeground(Color.YELLOW);
        title.setFont(fontTitle);

        JLabel title_game_over = new JLabel();
        title_game_over.setAlignmentX(Component.CENTER_ALIGNMENT);
        title_game_over.setText("game over");
        title_game_over.setForeground(Color.RED);
        title_game_over.setFont(fontTitle);

        button_Start =new JButton("START");
        button_Start.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_Start.setForeground(Color.WHITE);
        button_Start.addActionListener(new ButtonListener());
        button_Start.setFont(font.deriveFont(Font.PLAIN,50));
        button_Start.setPreferredSize(new Dimension(300,80));
        button_Start.setMaximumSize(new Dimension(300,80));
        button_Start.setContentAreaFilled(false);
        button_Start.setBorder(new LineBorder(Color.YELLOW));
        button_Start.setFocusable(false);


        button_Quit =new JButton("QUIT");
        button_Quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_Quit.setForeground(Color.WHITE);
        button_Quit.addActionListener(new ButtonListener());
        button_Quit.setFont(font.deriveFont(Font.PLAIN,50));
        button_Quit.setPreferredSize(new Dimension(300,80));
        button_Quit.setMaximumSize(new Dimension(300,80));
        button_Quit.setContentAreaFilled(false);
        button_Quit.setBorder(new LineBorder(Color.YELLOW));
        button_Quit.setFocusable(false);


        tableHigh = new JPanel();

        button_restart =new JButton("RESTART");
        button_restart.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_restart.setForeground(Color.WHITE);
        button_restart.addActionListener(new ButtonListener());
        button_restart.setFont(font.deriveFont(Font.PLAIN,50));
        button_restart.setPreferredSize(new Dimension(300,80));
        button_restart.setMaximumSize(new Dimension(300,80));
        button_restart.setContentAreaFilled(false);
        button_restart.setBorder(new LineBorder(Color.YELLOW));
        button_restart.setFocusable(false);

        button_Quit1 =new JButton("QUIT");
        button_Quit1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_Quit1.setForeground(Color.WHITE);
        button_Quit1.addActionListener(new ButtonListener());
        button_Quit1.setFont(font.deriveFont(Font.PLAIN,50));
        button_Quit1.setPreferredSize(new Dimension(300,80));
        button_Quit1.setMaximumSize(new Dimension(300,80));
        button_Quit1.setContentAreaFilled(false);
        button_Quit1.setBorder(new LineBorder(Color.YELLOW));
        button_Quit1.setFocusable(false);


        this.panStart.setBackground(Color.black);
        this.panGame.setBackground(Color.black);
        this.panEnd.setBackground(Color.black);
        this.panMain.setBackground(Color.black);
        this.panUp.setBackground(Color.black);
        this.panDown.setBackground(Color.black);
        this.canvas = new CanvasPane();
        this.canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        panStart.add(title);
        panStart.add(Box.createRigidArea(new Dimension(0,80)));
        panStart.add(button_Start);
        panStart.add(Box.createRigidArea(new Dimension(0,20)));
        panStart.add(button_Quit);


        this.panUp.add(this.score);
        this.panUp.add(this.highScore);

        this.panDown.add(this.lives);

        this.panMain.add(panUp);
        this.panMain.add(canvas);
        this.panMain.add(panDown);

        this.panGame.add(panMain);

        JPanel firstPan = new JPanel();
        firstPan.setBackground(Color.BLACK);

        JPanel lastPan = new JPanel();
        lastPan.setBackground(Color.BLACK);


        firstPan.setLayout(new GridBagLayout());
        lastPan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        firstPan.add(panStart,gbc);
        lastPan.add(panEnd,gbc);

        this.panEnd.add(title_game_over);
        this.panEnd.add(Box.createRigidArea(new Dimension(0,60)));
        this.panEnd.add(tableHigh);
        this.panEnd.add(Box.createRigidArea(new Dimension(0,20)));
        this.panEnd.add(button_restart);
        this.panEnd.add(Box.createRigidArea(new Dimension(0,20)));
        this.panEnd.add(button_Quit1);

        c.add("panStart",firstPan);
        c.add("panGame",panGame);
        c.add("panEnd",lastPan);

        this.frame.pack();

        //canvas.setFocusable(true);
        this.setVisible();

    }

    public void showEndFrame(ArrayList<Integer> highScores){
        this.tableHigh.removeAll();
        this.tableHigh.setBackground(Color.BLACK);
        this.tableHigh.setLayout(new BoxLayout(tableHigh,BoxLayout.Y_AXIS));
        JLabel text = new JLabel("HIGH SCORES");
        text.setForeground(Color.YELLOW);
        text.setFont(font.deriveFont(Font.PLAIN,30));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        this.tableHigh.add(text);
        for(int i = 0; i < highScores.size(); i++){
            String score = highScores.get(i)+"";
            text = new JLabel(score);
            text.setForeground(Color.WHITE);
            text.setFont(font.deriveFont(Font.PLAIN,20));
            text.setHorizontalAlignment(SwingConstants.CENTER);
            this.tableHigh.add(text);
        }
        card.show(c,"panEnd");
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
        instance.setVisible();
        return instance;
    }

    private void setVisible()
    {
        if(graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(true);
    }

    public void setDimensionPan(int width, int height){
        this.canvas.setPreferredSize(new Dimension(width,height));
        this.canvas.revalidate();
        this.panUp.setPreferredSize(new Dimension(width,40));
        this.panUp.revalidate();
        this.panDown.setPreferredSize(new Dimension(width,40));
        this.panDown.revalidate();
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

    public boolean isStartGame(){return startGame;}

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

	private class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == button_Quit || e.getSource() == button_Quit1){
                frame.dispose();
            }
            else if(e.getSource() == button_Start){
                card.show(c,"panGame");
                canvas.setFocusable(true);
                canvas.requestFocus();
                startGame = true;

            }
            else if(e.getSource() == button_restart){
                card.show(c,"panGame");
                // TODO : Trouver une manière pour relancer une partie
            }

        }

    }

    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(canvasImage, 0, 0, null);

            for(Character c: characters) {
                int x = c.getX();
                int y = c.getY();
                int width = c.getWidthImage();
                int height = c.getHeightImage();
                g2d.drawImage(c.getImage(), x, y, width, height,null);

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