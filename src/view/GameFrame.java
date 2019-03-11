package view;

import javax.swing.*;
import control.*;

public class GameFrame extends JFrame {

    private Controller control;

    public GameFrame(Controller control){
        this.control = control;

        this.setTitle("Pacman");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);
        this.pack();
    }


}
