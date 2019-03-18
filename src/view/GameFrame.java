package view;

import javax.swing.*;

import control.*;

import java.awt.*;

public class GameFrame extends JFrame {

    private Controller control;
    private JPanel pan;
    private JPanel panGame;

    private int tile_size = 50;
    private int n_tiles;
    private int maze_size;



    public GameFrame(Controller control){
        this.control = control;

        this.setTitle("Pacman");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // this.setResizable(false);

        pan = new JPanel();
        pan.setBackground(Color.BLACK);
        pan.setLayout(new FlowLayout());
        pan.setPreferredSize(new Dimension(700,900));
        panGame = new JPanel();
        panGame.setBackground(Color.BLACK);


//        int sizeGame = (int)Math.sqrt(control.getGame().getMaze().getTiles().size());
        this.n_tiles = 15;
        this.maze_size = n_tiles*tile_size;

       // panGame.setLayout(new GridLayout(sizeGame,sizeGame,0,0));
        //panGame.setPreferredSize(new Dimension(600,600));






       // pan.add(title);
       // pan.add(panGame);
        this.add(new BoardComponent(n_tiles,tile_size));



        //this.setLocationRelativeTo(null);
        this.pack();
        showIt();
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







}
