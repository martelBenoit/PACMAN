package view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BoardComponent extends JComponent {

    private int n_tiles, tile_size;
    private Image ii;

    public BoardComponent(int n_tiles, int tile_size){
        this.n_tiles = n_tiles;
        this.tile_size = tile_size;
        System.out.println(tile_size);
        setPreferredSize(new Dimension(n_tiles*tile_size,n_tiles*tile_size));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0,0,tile_size*n_tiles,tile_size*n_tiles);
        drawMaze(g2d);


        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawMaze(Graphics2D g2d){
        int x, y;

        try{
            InputStream flux=new FileInputStream("lib/maze1.txt");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String line;
            while ((line=buff.readLine())!=null){
                String[] data = line.split(",");
                x = Integer.parseInt(data[0]);
                y = Integer.parseInt(data[1]);
                if(x >= 0 && x < this.n_tiles){
                    if(y >= 0 && y < this.n_tiles){
                        if(data[2].equals("true")){
                            g2d.setColor(Color.blue);
                            g2d.drawRect(x*tile_size,y*tile_size,this.tile_size,this.tile_size);
                            g2d.fillRect(x*tile_size,y*tile_size,this.tile_size,this.tile_size);
                        }
                    }

                }

            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
}
