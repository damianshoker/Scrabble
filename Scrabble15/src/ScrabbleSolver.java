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

    public void traverseGaddag(GaddagNode currentNode, AnchorSquare anchorSquare, String remainingRack, StringBuilder currentWord, List<String> foundWords, boolean anchorLetterUsed) {

        if (anchorLetterUsed && scrabbleDictionary.isWordInDictionary(currentWord.toString())) {
            foundWords.add(currentWord.toString());
        }

        for (char c : remainingRack.toCharArray()) {
            if (currentNode.hasChild(c)) {
                GaddagNode childNode = currentNode.getChild(c);
                String newRemainingRack = remainingRack.replaceFirst(Character.toString(c), "");
                boolean newAnchorLetterUsed = anchorLetterUsed || c == anchorSquare.letter;

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
                    traverseGaddag(childNode, new AnchorSquare(' ', newRow, newCol), newRemainingRack, newCurrentWord, foundWords, newAnchorLetterUsed);
                }
            }
        }
    }
    public List<String> findWordsFromAnchorSquare(AnchorSquare anchorSquare, String rack) {
        List<String> foundWords = new ArrayList<>();
        GaddagNode rootNode = gaddag.getRoot();
        StringBuilder initialWord = new StringBuilder();
        initialWord.append(anchorSquare.letter);

        String remainingRack = rack.replaceFirst(Character.toString(anchorSquare.letter), "");

        traverseGaddag(rootNode, anchorSquare, remainingRack, initialWord, foundWords, true);

        return foundWords;
    }
    
}