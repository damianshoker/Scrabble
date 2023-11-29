public class Tile {
    private char letter;
    private int row;
    private int col;

    public Tile(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
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
}