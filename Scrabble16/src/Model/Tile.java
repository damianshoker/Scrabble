package Model;
import java.awt.Image;

import javax.swing.*;

public class Tile {
    private char letter;
    private int row;
    private int col;
    private boolean permanent;

    private int initialPositionX;
    private int initialPositionY;
    
    private Image tileImage;

    // label
    private final JLabel label;

    public Tile(char letter, int row, int col, int initialPositionX, int initialPositionY) {
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.permanent = false;
        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;
        this.label = new JLabel(Tileimage.getTileImage(letter));

        this.label.setBounds(initialPositionX, 670, 40, 40);

    }

    public static TileType getTileType(int row, int col) {
        if ((row == 0 && col == 0)
                || ((row == 7 && col == 0)) || ((row == 0 && col == 7))
                || ((row == 14 && col == 14)) || ((row == 14 && col == 0))
                || ((row == 0 && col == 14)) || ((row == 14 && col == 7))
                || ((row == 7 && col == 14))
                ) {
            return TileType.TRIPLE_WORD;
        } else if ((row == 1 && col == 1) || ((row == 2 && col == 2))
                || ((row == 3 && col == 3)) || ((row == 4 && col == 4))
                || ((row == 10 && col == 10)) || ((row == 13 && col == 13))
                || ((row == 12 && col == 12)) || ((row == 11 && col == 11))
                || ((row == 13 && col == 1)) || ((row == 12 && col == 2))
                || ((row == 11 && col == 3)) || ((row == 10 && col == 4))
                || ((col == 13 && row == 1)) || ((col == 12 && row == 2))
                || ((col == 11 && row == 3)) || ((col == 10 && row == 4))
                ) {
            return TileType.DOUBLE_WORD;
        } else if ((row == 1 && col == 5) || ((row == 1 && col == 9))
                || ((row == 5 && col == 1)) || ((row == 5 && col == 5))
                || ((row == 5 && col == 9)) || ((row == 5 && col == 13))
                || ((row == 9 && col == 1)) || ((row == 9 && col == 5))
                || ((row == 9 && col == 9)) || ((row == 9 && col == 13))
                || ((row == 13 && col == 5)) || ((row == 13 && col == 9))
                ) {
            return TileType.TRIPLE_LETTER;
        } else if ((row == 0 && col == 3) || ((row == 0 && col == 11))
                || ((row == 2 && col == 6)) || ((row == 2 && col == 8))
                || ((row == 3 && col == 0)) || ((row == 3 && col == 7))
                || ((row == 3 && col == 14)) || ((row == 6 && col == 2))
                || ((row == 6 && col == 6)) || ((row == 6 && col == 8))
                || ((row == 6 && col == 12)) || ((row == 7 && col == 3))
                || ((row == 7 && col == 11)) || ((row == 8 && col == 2))
                || ((row == 8 && col == 6)) || ((row == 8 && col == 8))
                || ((row == 8 && col == 12)) || ((row == 11 && col == 0))
                || ((row == 11 && col == 7)) || ((row == 11 && col == 14))
                || ((row == 12 && col == 6)) || ((row == 12 && col == 8))
                || ((row == 14 && col == 3)) || ((row == 14 && col == 11))
                ) {
            return TileType.DOUBLE_LETTER;
        } else if ((row == 7 && col == 7)) {
            return TileType.STAR_SPIN;
        } else {
            return TileType.NORMAL;
        }
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
        // also change the label
        this.label.setIcon(Tileimage.getTileImage(letter));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }


    public int getInitialPositionX() {
        return initialPositionX;
    }

    public int getInitialPositionY() {
        return initialPositionY;
    }

    public void setInitialPositionX(int initialPositionX) {
        this.initialPositionX = initialPositionX;
    }

    public void setInitialPositionY(int initialPositionY) {
        this.initialPositionY = initialPositionY;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setRowCol(int row, int col) {
        setRow(row);
        setCol(col);
    }

    public void resetLabelLocation() {
        label.setLocation(initialPositionX, initialPositionY);
    }

    public TileType getTileType() {
        return getTileType(row, col);
    }
    
    public void setTileImage(char letter, Image image) {
        this.tileImage = image;
    }
    
}