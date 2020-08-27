package mapping;
import java.util.Random;

import coordinates.*;

public class Grid {

    static int tileCount = 20;
    static int tileSize = 5;
    int xActual, yActual;

    public Tile[] tiles = new Tile[tileCount*tileCount];
    Vector2[] gradients = new Vector2[4];

    public Grid(int x, int y, Vector2[] gradients) {
        xActual = x*tileCount;
        yActual = y*tileCount;

        this.gradients = gradients;
        createTiles();
    }

    void createTiles() {

        int id = 0;
        for (int x = 0; x < tileCount; x++) {
            for (int y = 0; y < tileCount; y++) {

                // INTERPOLATION
                float yFrac = (float)y/tileCount, xFrac = (float)x/tileCount;
                float[] products = dotProduct(xFrac,yFrac);

                float AB = products[0] + xFrac * (products[1]-products[0]);
                float CD = products[2] + xFrac * (products[3]-products[2]);

                float xOffset = fade((float)products[0] - x);
                float yOffset = fade((float)products[1] - y);
                float value = AB+yFrac*(CD-AB);

                tiles[id] = new Tile(x+xActual,y+yActual,value*10,tileSize);
                id++;
            }
        }
    }   

    float dotProduct(Vector2 a, Vector2 b) {
        return (float)a.x * b.x + a.y * b.y;
    }

    private float[] dotProduct(float x, float y) {
        Vector2 vector = new Vector2(x, y);
        float[] products = new float[gradients.length];

        for (int g = 0; g < gradients.length; g++) {
            float xNew = (float)vector.x * gradients[g].x;
            float yNew = (float)vector.y * gradients[g].y;

            float sum = xNew+yNew;
            products[g] = sum;
        }
        return products;
    }

    float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static int[] getTileInformation() {
        int[] information = {
            tileSize, tileCount
        };

        return information;
    }
}
