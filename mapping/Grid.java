package mapping;
import coordinates.*;

public class Grid {

    static int tileCount = 10;
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

                float height = dotProduct(x,y);
                tiles[id] = new Tile(x+xActual,y+yActual,height,tileSize);
                id++;
            }
        }
    }

    private float dotProduct(int x, int y) {
        Vector2 vector = new Vector2(x, y);
        float height = 0;

        for (int g = 0; g < gradients.length; g++) {
            float xNew = (float)vector.x * gradients[g].x;
            float yNew = (float)vector.y * gradients[g].y;

            float sum = xNew+yNew;
            height += sum;
        }

        return height;
    }

    float lerp(float min, float max, float value) {

        float result = (1-value)*min + value*max;
        return result;
    }

    public static int[] getTileInformation() {
        int[] information = {
            tileSize, tileCount
        };
        return information;
    }
}
