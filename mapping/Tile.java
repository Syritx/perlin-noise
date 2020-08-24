package mapping;

import javax.swing.JPanel;
import java.awt.Color;

public class Tile extends JPanel {
    public Tile(int x, int y, float perlin, int size) {
        setLayout(null);
        setBounds(x*size, y*size, size, size);

        int rgb = (int)perlin*5;
        rgb = 255 - rgb;

        if (rgb > 255) rgb = 255;
        if (rgb < 0) rgb = 0;

        setBackground(new Color(rgb,rgb,rgb));
    }
}
