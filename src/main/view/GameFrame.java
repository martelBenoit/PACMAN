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

    public static final int WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50;
    public static final int HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();


    private static GameFrame instance;

    private JFrame frame;

    private CanvasPane canvas;
    private JPanel panUp = new JPanel();
    private JPanel panDown = new JPanel();

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

    private Graphics2D graphic;
    private Image canvasImage;

    private ArrayList<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private ArrayList<Character> characters;

	private boolean upPressed, downPressed, leftPressed, rightPressed, directionChanged = false;

	private boolean startGame, restartGame = false;


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

        JPanel panStart = new JPanel();
        panStart.setLayout(new BoxLayout(panStart,BoxLayout.Y_AXIS));

        JPanel panGame = new JPanel();

        JPanel panEnd = new JPanel();
        panEnd.setLayout(new BoxLayout(panEnd,BoxLayout.Y_AXIS));

        this.frame.setTitle("Pacman");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        this.frame.setIconImage(new ImageIcon("lib/pacman_open.png").getImage());

        JPanel panMain = new JPanel();
        panMain.setLayout(new BoxLayout(panMain,BoxLayout.Y_AXIS));

        panUp.setLayout(new GridLayout(1,2));
        panDown.setLayout(new GridLayout(1,2));

        // Pour le panGame
        this.level = new JLabel();
        this.score = new JLabel();
        this.highScore = new JLabel();
        this.lives = new JLabel();

        Font fontTitle = null;
        Font fontGameOver;


        try {
            this.font  = Font.createFont(Font.TRUETYPE_FONT, new File("lib/VCR_OSD_MONO_1.001.ttf")).deriveFont(Font.PLAIN, 40);
            fontGameOver = Font.createFont(Font.TRUETYPE_FONT, new File("lib/PAC-FONT.ttf")).deriveFont(Font.PLAIN, 40);
            fontTitle = fontGameOver.deriveFont(Font.PLAIN, 75);
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
        this.level.setHorizontalAlignment(SwingConstants.RIGHT);

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
        title_game_over.setFont(fontTitle.deriveFont(Font.PLAIN,55));

        button_Start =new JButton("START");
        designButtonFirstPanel(button_Start);


        button_Quit =new JButton("QUIT");
        designButtonFirstPanel(button_Quit);


        tableHigh = new JPanel();

        button_restart =new JButton("RESTART");
        designButtonEndPanel(button_restart);

        button_Quit1 =new JButton("QUIT");
        designButtonEndPanel(button_Quit1);


        panStart.setBackground(Color.black);
        panGame.setBackground(Color.black);
        panEnd.setBackground(Color.black);
        this.panUp.setBackground(Color.black);
        this.panDown.setBackground(Color.red);

        this.canvas = new CanvasPane();
        this.canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        panStart.add(title);
        panStart.add(Box.createRigidArea(new Dimension(0,70)));
        panStart.add(button_Start);
        panStart.add(Box.createRigidArea(new Dimension(0,20)));
        panStart.add(button_Quit);


        this.panUp.add(this.score);
        this.panUp.add(this.highScore);

        this.panDown.add(this.lives);
        this.panDown.add(this.level);

        panMain.add(panUp);
        panMain.add(canvas);
        panMain.add(panDown);

        panGame.add(panMain);

        JPanel firstPan = new JPanel();
        firstPan.setBackground(Color.BLACK);

        JPanel lastPan = new JPanel();
        lastPan.setBackground(Color.BLACK);

        JPanel panHighScore = new JPanel();
        panHighScore.setBackground(Color.BLACK);

        panHighScore.add(tableHigh);

        firstPan.setLayout(new GridBagLayout());
        lastPan.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        firstPan.add(panStart,gbc);
        lastPan.add(panEnd,gbc);

        panEnd.add(title_game_over);
        panEnd.add(Box.createRigidArea(new Dimension(0,30)));
        panEnd.add(panHighScore);
        panEnd.add(Box.createRigidArea(new Dimension(0,10)));
        panEnd.add(button_restart);
        panEnd.add(Box.createRigidArea(new Dimension(0,10)));
        panEnd.add(button_Quit1);

        c.add("panStart",firstPan);
        c.add("panGame",panGame);
        c.add("panEnd",lastPan);

        this.frame.pack();
        this.setVisible();

    }

    private void designButtonFirstPanel(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.addActionListener(new ButtonListener());
        button.setFont(font.deriveFont(Font.PLAIN,50));
        button.setPreferredSize(new Dimension(300,80));
        button.setMaximumSize(new Dimension(300,80));
        button.setContentAreaFilled(false);
        button.setBorder(new LineBorder(Color.YELLOW));
        button.setFocusable(false);
    }

    private void designButtonEndPanel(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.addActionListener(new ButtonListener());
        button.setFont(font.deriveFont(Font.PLAIN,40));
        button.setPreferredSize(new Dimension(270,70));
        button.setMaximumSize(new Dimension(270,70));
        button.setContentAreaFilled(false);
        button.setBorder(new LineBorder(Color.YELLOW));
        button.setFocusable(false);
    }

    public void showEndFrame(ArrayList<Integer> highScores,int scoreActual){
        this.tableHigh.removeAll();
        this.tableHigh.setBackground(Color.BLACK);
        this.tableHigh.setLayout(new GridLayout(11,1,2,0));
        JLabel text = new JLabel("HIGH SCORES");
        text.setForeground(Color.YELLOW);
        text.setFont(font.deriveFont(Font.PLAIN,40));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        this.tableHigh.add(text);
        boolean boDone = false;
        for(int score : highScores){
            text = new JLabel(score+"");
            if(score == scoreActual && !boDone){
                text.setForeground(Color.red);
                boDone = true;
            }
            else
                text.setForeground(Color.WHITE);
            text.setFont(font.deriveFont(Font.PLAIN,30));
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
        this.canvas.setMaximumSize(new Dimension(width,height));
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
        SwingUtilities.invokeLater(() -> {
            erase();
            for(Object shape : objects) {
                shapes.get(shape).draw(graphic);
            }

            canvas.repaint();
        });
        wait(120);


    }

    public void eraseCharacter()
    {
        characters = new ArrayList<>();
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

    public boolean isRestartGame(){return restartGame;}

    public void setRestartGame(boolean restartGame){this.restartGame = restartGame;}

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
                System.exit(0);
            }
            else if(e.getSource() == button_Start){
                card.show(c,"panGame");
                canvas.setFocusable(true);
                canvas.requestFocus();
                startGame = true;
            }
            else if(e.getSource() == button_restart){
                card.show(c,"panGame");
                canvas.setFocusable(true);
                canvas.requestFocus();
                restartGame = true;
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

        private ShapeDescription(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        private void draw(Graphics2D graphic) {
            graphic.setColor(color);
            graphic.fill(shape);
        }
    }
}