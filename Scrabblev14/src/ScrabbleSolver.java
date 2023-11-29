import java.util.ArrayList;
import java.util.List;

public class ScrabbleSolver {
    private Gaddag gaddag;
    private Board board;
    private ScrabbleDictionary scrabbleDictionary;
    

    public ScrabbleSolver(Gaddag gaddag, Board board) {
        this.gaddag = gaddag;
        this.board = board;
        String filePath = "src/Scrabble.txt";
        scrabbleDictionary = new ScrabbleDictionary(filePath);
    }

    public void traverseGaddag(GaddagNode currentNode, AnchorSquare anchorSquare, String remainingRack, StringBuilder currentWord, List<String> foundWords) {
        System.out.println("Entering traverseGaddag with currentNode: " + currentNode + ", remainingRack: " + remainingRack + ", currentWord: " + currentWord);
        if (currentNode.isWord() && currentWord.indexOf(Character.toString(anchorSquare.letter)) != -1) {
            System.out.println("Found word: " + currentWord);
            foundWords.add(currentWord.toString());
        }

        for (char c : remainingRack.toCharArray()) {
            if (currentNode.hasChild(c)) {
                GaddagNode childNode = currentNode.getChild(c);
                String newRemainingRack = remainingRack.replaceFirst(Character.toString(c), "");

                int newRow = anchorSquare.row;
                int newCol = anchorSquare.col;
                // Update newRow and newCol based on the direction of the word (horizontal or vertical)
                // and the current position on the board.
                Direction direction = board.getWordDirection(newRow, newCol);
                if (direction == Direction.HORIZONTAL) {
                    newCol++;
                } else {
                    newRow++;
                }

                if (board.isValidMove(newRow, newCol, c)) {
                    StringBuilder newCurrentWord = new StringBuilder(currentWord);
                    newCurrentWord.append(c);
                    traverseGaddag(childNode, new AnchorSquare(' ', newRow, newCol), newRemainingRack, newCurrentWord, foundWords);
                }
            }
        }
    }

    public List<String> findWordsFromAnchorSquare(AnchorSquare anchorSquare, String rack) {
        List<String> foundWords = new ArrayList<>();
        GaddagNode rootNode = gaddag.getRoot();
        StringBuilder emptyWord = new StringBuilder();

        // Include the anchor letter in the search
        String newRack = rack + anchorSquare.letter;
        traverseGaddag(rootNode, anchorSquare, newRack, emptyWord, foundWords);

        return foundWords;
    }
}