import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrabbleTile {
    private static final int NUM_TILES = 100;
    public static final Map<Character, Integer> letterValues = new HashMap<>();
    private static List<Character> tileBag;
    private static final int NUM_LETTERS = 7;
    static {
        tileBag = new ArrayList<>(NUM_TILES);
        initTileBag();
        shuffleTileBag();
    }
    
     {
        letterValues.put('A', 1);
        letterValues.put('B', 3);
        letterValues.put('C', 3);
        letterValues.put('D', 2);
        letterValues.put('E', 1);
        letterValues.put('F', 4);
        letterValues.put('G', 2);
        letterValues.put('H', 4);
        letterValues.put('I', 1);
        letterValues.put('J', 8);
        letterValues.put('K', 5);
        letterValues.put('L', 1);
        letterValues.put('M', 3);
        letterValues.put('N', 1);
        letterValues.put('O', 1);
        letterValues.put('P', 3);
        letterValues.put('Q', 10);
        letterValues.put('R', 1);
        letterValues.put('S', 1);
        letterValues.put('T', 1);
        letterValues.put('U', 1);
        letterValues.put('V', 4);
        letterValues.put('W', 4);
        letterValues.put('X', 8);
        letterValues.put('Y', 4);
        letterValues.put('Z', 10);
        letterValues.put('*', 0); // Blank tile has a point value of 0
    }

    public ScrabbleTile() {
        tileBag = new ArrayList<>(NUM_TILES);
        initTileBag();
        shuffleTileBag();
    }

    private static void initTileBag() {
        char[] tileDistribution = {
                'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 
                'B', 'B',
                'C', 'C', 
                'D', 'D', 'D', 'D', 
                'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
                'F', 'F', 
                'G', 'G', 'G', 
                'H', 'H', 
                'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I',
                'J', 
                'K',
                'L', 'L', 'L', 'L', 
                'M', 'M', 
                'N', 'N', 'N', 'N', 'N', 'N', 
                'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O',
                'P', 'P', 
                'Q', 
                'R', 'R', 'R', 'R', 'R', 'R', 
                'S', 'S', 'S', 'S', 
                'T', 'T', 'T', 'T', 'T', 'T',
                'U', 'U', 'U', 'U', 
                'V', 'V', 
                'W', 'W', 
                'X', 
                'Y', 'Y', 
                'Z', 
                '*', '*'
        };

        for (char tile : tileDistribution) {
            tileBag.add(tile);
        }
    }

    private static void shuffleTileBag() {
        Collections.shuffle(tileBag);
    }
    
    public static char getNewTile() {
        if (tileBag.isEmpty()) {
            throw new IllegalStateException("Tile bag is empty.");
        }
        return tileBag.remove(0);
    }
    
    public static boolean hasMoreTiles() {
        return !tileBag.isEmpty();
    }

    public List<Character> allocateTiles() {
        List<Character> allocatedTiles = new ArrayList<>();
        for (int i = 0; i < NUM_LETTERS; i++) {
            if (tileBag.size() > 0) {
                int randomIndex = (int) (Math.random() * tileBag.size());
                allocatedTiles.add(tileBag.get(randomIndex));
                tileBag.remove(randomIndex);
            }
        }
        return allocatedTiles;
    }
    
    public static List<Character> getCurrentTileBag() {
        return tileBag;
    }
    
    public static int getTileBagSize() {
        return tileBag.size();
    }
}