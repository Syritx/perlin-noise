import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import coordinates.*;
import mapping.*;

public class Launcher {

    static int chunkLengthOfMap = 5;
    static Grid[] grids = new Grid[chunkLengthOfMap*chunkLengthOfMap];
    static Vector2[] gradients;

    public static void main(String[] args) {
        
        gradients = new Vector2[chunkLengthOfMap*chunkLengthOfMap];
        int gridId = 0;
        int offsetReach = 2;

        int gradientId = 0;

        //---------------------------------------------//
        // CREATING THE VECTORS FOR THE GRADIENTS //
        //---------------------------------------------//

        for (int x = 0; x < chunkLengthOfMap; x++) {
            for (int y = 0; y < chunkLengthOfMap; y++) {
                Random rx = new Random(), 
                       ry = new Random();

                int gx = rx.nextInt((offsetReach*2)+1)-offsetReach;
                int gy = ry.nextInt((offsetReach*2)+1)-offsetReach;

                gradients[gradientId] = new Vector2(gx,gy);
                gradientId++;
            }
        }

        int gradientCollectionId = 0;

        //---------------------------------------------//
        // CREATING GRIDS //
        //---------------------------------------------//

        for (int x = 0; x < chunkLengthOfMap; x++) {
            for (int y = 0; y < chunkLengthOfMap; y++) {

                // creating a gradient array
                Vector2[] gradient = new Vector2[4];

                // setting a default vector of {0,0}
                for (int i = 0; i < gradient.length; i++) {
                    gradient[i] = new Vector2(0, 0);
                }

                // applying 4 vectors to an array
                gradient[0] = gradients[gradientCollectionId];
                try {   
                    gradient[1] = gradients[gradientCollectionId+1];
                    gradient[2] = gradients[gradientCollectionId+chunkLengthOfMap];
                    gradient[3] = gradients[gradientCollectionId+chunkLengthOfMap+1];
                }
                catch(Exception exception) {
                    // if the gradients don't exist we'll create some more
                    Random rx = new Random(), 
                           ry = new Random();

                    int gx = rx.nextInt((offsetReach*2)+1)-offsetReach;
                    int gy = ry.nextInt((offsetReach*2)+1)-offsetReach;

                    gradient[1] = new Vector2(gx, gy);

                    gx = rx.nextInt((offsetReach*2)+1)-offsetReach;
                    gy = ry.nextInt((offsetReach*2)+1)-offsetReach;
                    gradient[2] = new Vector2(gx, gy);
                }

                gradientCollectionId++;
                grids[gridId] = new Grid(x,y,gradient);
                gridId++;

                // printing the vectors
                for (Vector2 gradientVector : gradient) {
                    System.out.println(gradientVector.x + " " + gradientVector.y);
                }
                System.out.println("----------");
            }
        }

        // id 0 => count | id 1 => size
        int[] information = Grid.getTileInformation();
        new Game(grids,information);
    }
}

//---------------------------------------------//
// WINDOW DISPLAY //
//---------------------------------------------//

class Game extends JFrame {

    private static final long serialVersionUID = 1L;
    JPanel background;
    
    public Game(Grid[] chunks, int[] information) {

        int gridLength = (int)Math.sqrt(chunks.length);
        int size = information[1]*information[0]*gridLength;

        setSize(size,size);
        setResizable(false);
        setTitle("Noise");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        background = new JPanel();
        background.setLayout(null);

        for (Grid chunk : chunks) {
            for (Tile tile : chunk.tiles) {
                background.add(tile);
        }  }
        add(background);
        setVisible(true);
    }
}
