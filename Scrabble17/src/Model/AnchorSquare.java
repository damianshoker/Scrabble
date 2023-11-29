package Model;
public class AnchorSquare {
    char letter;
    int row;
    int col;

    public AnchorSquare(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
    }


    public char getLetter() {
        return letter;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    
    @Override
    public String toString() {
        return "AnchorSquare{" +
                "letter=" + letter +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
    
    
    
    
    
}