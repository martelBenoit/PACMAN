package view;

public class MazeGenerate {


    private Figure[][] theMaze;

    public MazeGenerate (int x, int y) {

        this.theMaze = new Figure[x][y];
    }

    public void setFigure (int i, int j, Figure f) {

        this.theMaze[i][j] = f;
    }

    public Figure[][] getTheMaze() {

        return this.theMaze;
    }

    public void draw () {

        for(int y = 0; y < theMaze[0].length; y++){
            for(int x = 0; x < theMaze.length; x++){
                if (theMaze[x][y]!=null) {
                    theMaze[x][y].draw();
                }
                System.out.println("x:"+x+", y="+y+", "+ theMaze[x][y]);
            }
        }

    }
}