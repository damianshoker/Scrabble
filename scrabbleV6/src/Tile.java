public class Tile {
    private char letter;
    private int row;
    private int col;
    private boolean permanent;

    public Tile(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.permanent = false;
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

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}