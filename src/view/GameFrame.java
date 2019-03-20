package view;

import javax.swing.*;
import control.*;
import java.awt.event.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private Controller control;
    private static GameFrame GameFrameSingleton;
	private boolean upPressed, downPressed, leftPressed, rightPressed, directionChanged = false;

    public GameFrame(Controller control){
        this.control = control;

        this.setTitle("Pacman");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300,300));
        this.addKeyListener(new KeyboardListener());
        //this.setLocationRelativeTo(null);
        this.pack();
    }
    
    public static GameFrame getGameFrame(Controller control)
    {
        if(GameFrameSingleton == null) {
            GameFrameSingleton = new GameFrame(control);
        }
        GameFrameSingleton.setVisible(true);
        return GameFrameSingleton;
}

    /**
     * Method that displays the window
     */
    public void showIt(){
        this.setVisible(true);
    }

    /**
     * A method to hide the window
     */
    public void hideIt(){
        this.setVisible(false);
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


}
