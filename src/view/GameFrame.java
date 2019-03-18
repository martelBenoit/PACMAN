package view;

import javax.swing.*;

import control.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameFrame extends JFrame {

    private Controller control;
    private JPanel pan;
    private JButton[][] tiles;

    public GameFrame(Controller control){
        this.control = control;

        this.setTitle("Pacman");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // this.setResizable(false);
        pan = new JPanel();
        pan.setBackground(Color.BLACK);


//        int sizeGame = (int)Math.sqrt(control.getGame().getMaze().getTiles().size());
        int sizeGame = 15;
        tiles = new JButton[sizeGame][sizeGame];
        pan.setLayout(new GridLayout(sizeGame,sizeGame,0,0));
        pan.setPreferredSize(new Dimension(600,600));

        for(int x = 0; x < sizeGame; x++){
            for(int y = 0; y < sizeGame; y++){
                tiles[x][y] = new JButton();
                tiles[x][y].setBorderPainted(false);
                tiles[x][y].setFocusPainted(false);
                tiles[x][y].setBackground(Color.BLACK);
                tiles[x][y].setIcon(new ImageIcon("lib.pill.png" ));
                pan.add(tiles[x][y]);
            }
        }

        try{
            InputStream flux=new FileInputStream("lib/maze1.txt");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String line;
            while ((line=buff.readLine())!=null){
                String[] data = line.split(",");

                if(Integer.parseInt(data[0]) >= 0 && Integer.parseInt(data[0]) < sizeGame){
                    if(Integer.parseInt(data[1]) >= 0 && Integer.parseInt(data[1]) < sizeGame){
                        if(data[2].equals("true")){
                            tiles[Integer.parseInt(data[1])][Integer.parseInt(data[0])].setBackground(Color.blue);
                            tiles[Integer.parseInt(data[1])][Integer.parseInt(data[0])].setIcon(null);
                        }
                    }

                }

            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }



       this.add(pan);



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

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }



}
