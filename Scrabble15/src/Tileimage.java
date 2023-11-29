import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Tileimage {

    private static final String TILE_IMAGE_PATH = "Scrabllee_thumbnail.png";

    private static final Map<Character, ImageIcon> TILE_IMAGES = new HashMap<>();

    static {
        BufferedImage all = null;
        try {
            all = ImageIO.read(new File(TILE_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int ind = 0;

        for (int y = 0; y < 300; y += 97) {
            for (int x = 0; x < 600; x += 96) {
            	char letter;
                if (ind <= 25) {
                    letter = (char) (ind + 'A');
                } else {
                    letter = (char) ('*' + (ind - 26));
                }

                Image img = all.getSubimage(x, y, 88, 88).getScaledInstance(40, 40, BufferedImage.SCALE_SMOOTH);

                TILE_IMAGES.put(letter, new ImageIcon(img));

                ind++;
            }
        }
    }

    public static ImageIcon getTileImage(char letter) {
        return TILE_IMAGES.get(letter);
    }
    
    

}