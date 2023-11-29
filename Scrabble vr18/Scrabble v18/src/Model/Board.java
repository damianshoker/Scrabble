package Model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Tile[][] tiles;


    private Color[] colors = {
            new Color(255, 255, 255),          // normal
            new Color(173, 216, 230, 120),  // double letter
            new Color(0, 0, 255, 120),      // triple letter
            new Color(255, 192, 203, 120),  // double word
            new Color(255, 0, 0, 120),      // triple word
            new Color(255, 255, 0, 120)     // star spin
    };


    public Board() {
        setPreferredSize(new Dimension(602, 602));
        tiles = new Tile[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                tiles[row][col] = null;
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        int tileSize = 40;


        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int x = col * tileSize;
                int y = row * tileSize;

                g.setColor(Color.WHITE);
                g.fillRect(x, y, tileSize, tileSize);
                g.setColor(Color.GRAY);
                g.drawRect(x, y, tileSize, tileSize);

                g.setColor(colors[Tile.getTileType(row, col).ordinal()]);
                g.fillRect(x + 1, y + 1, tileSize, tileSize);
            }
        }


    }

    public Collection<Tile> getTiles() {
        List<Tile> result = new ArrayList<>();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = tiles[row][col];
                if (tile != null) {
                    result.add(tile);
                }
            }
        }
        return result;
    }

    public void placeTile(Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        if (tiles[row][col] != null) {
            System.out.println("Tile already exists at " + row + ", " + col);
        }
        tiles[row][col] = tile;
    }

    public Tile getTileAt(int row, int col) {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            return tiles[row][col];
        } else {
            return null;
        }
    }

    public void removeTile(Tile tile) {
        removeTile(tile.getRow(), tile.getCol());
    }

    public void removeTile(int row, int col) {
        if (row == -1 || col == -1) {
            return;
        }
        tiles[row][col] = null;
    }

    public boolean hasTileAbove(int row, int col) {
        return getTileAt(row, col - 1) != null;
    }

    public boolean hasTileBelow(int row, int col) {
        return getTileAt(row, col + 1) != null;
    }

    public boolean hasTileToLeft(int row, int col) {
        return getTileAt(row - 1, col) != null;
    }

    public boolean hasTileToRight(int row, int col) {
        return getTileAt(row + 1, col) != null;
    }
    public boolean isValidMove(int row, int col, char letter) {
        // Check if the position is within the board's boundaries
        if (row < 0 || row >= 15 || col < 0 || col >= 15) {
            return false;
        }

        // Check if the position is empty
        Tile tile = getTileAt(row, col);
        if (tile != null && tile.getLetter() != ' ') {
            return false;
        }

        return true;
    }
    
    
    public boolean isValidPosition(int row, int col) {
    	 if (row < 0 || row >= 15 || col < 0 || col >= 15) {
             return false;
         }
		return true;
    }
    
    public Direction getWordDirection(int row, int col) {
        boolean horizontal = false;
        boolean vertical = false;

        if (col > 0 && getTileAt(row, col - 1) != null) {
            horizontal = true;
        }
        if (row > 0 && getTileAt(row - 1, col) != null) {
            vertical = true;
        }
        if (col < 14 && getTileAt(row, col + 1) != null) {
            horizontal = true;
        }
        if (row < 14 && getTileAt(row + 1, col) != null) {
            vertical = true;
        }

        if (horizontal && !vertical) {
            return Direction.HORIZONTAL;
        } else if (vertical && !horizontal) {
            return Direction.VERTICAL;
        } else {
            // Default case: use horizontal if direction is ambiguous
            return Direction.HORIZONTAL;
        }
    }
    
    public boolean placeWord(String word, int row, int col) {
        Direction direction = getWordDirection(row, col);

        if (direction == Direction.HORIZONTAL) {
            for (int i = 0; i < word.length(); i++) {
                if (!isValidMove(row, col + i, word.charAt(i))) {
                    return false;
                }
            }
            for (int i = 0; i < word.length(); i++) {
                Tile tile = new Tile(word.charAt(i), row, col + i, 0, 0);
                placeTile(tile);
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (!isValidMove(row + i, col, word.charAt(i))) {
                    return false;
                }
            }
            for (int i = 0; i < word.length(); i++) {
                Tile tile = new Tile(word.charAt(i), row + i, col, 0, 0);
                placeTile(tile);
            }
        }
        return true;
    }
    

    public boolean isEmpty(int row, int col) {
    	return getTileAt(row, col) == null;
    }

    public boolean hasAdjacentTiles(int row, int col) {
    	return hasTileAbove(row, col) || hasTileBelow(row, col) || hasTileToLeft(row, col) || hasTileToRight(row, col);
    }

    public boolean isWordOnBoard(String word) {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = getTileAt(row, col);
                if (tile != null && tile.getLetter() == word.charAt(0)) {
                    Direction[] directions = {Direction.HORIZONTAL, Direction.VERTICAL};
                    for (Direction direction : directions) {
                        boolean wordMatches = true;
                        for (int i = 0; i < word.length(); i++) {
                            int newRow = row + (direction == Direction.VERTICAL ? i : 0);
                            int newCol = col + (direction == Direction.HORIZONTAL ? i : 0);

                            if (newRow >= 0 && newRow < 15 && newCol >= 0 && newCol < 15) {
                                Tile currentTile = getTileAt(newRow, newCol);
                                if (currentTile == null || currentTile.getLetter() != word.charAt(i)) {
                                    wordMatches = false;
                                    break;
                                }
                            } else {
                                wordMatches = false;
                                break;
                            }
                        }
                        if (wordMatches) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    

    public void printBoard() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = getTileAt(row, col);
                if (tile != null) {
                    System.out.print(tile.getLetter() + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
    
    
    
    
    
    


}