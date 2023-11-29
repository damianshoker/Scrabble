package Model;
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

    public void traverseGaddag(GaddagNode currentNode, AnchorSquare anchorSquare, String remainingRack, StringBuilder currentWord, List<String> foundWords, boolean anchorLetterUsed, boolean reversed, int anchorSquareIndex) {

        if (anchorLetterUsed && anchorSquareIndex >= 0 && anchorSquareIndex < currentWord.length() && scrabbleDictionary.isWordInDictionary(currentWord.toString())) {
            foundWords.add(currentWord.toString());
        }

        for (char c : remainingRack.toCharArray()) {
            if (currentNode.hasChild(c)) {
                GaddagNode childNode = currentNode.getChild(c);
                String newRemainingRack = remainingRack.replaceFirst(Character.toString(c), "");
                boolean newAnchorLetterUsed = anchorLetterUsed || c == anchorSquare.letter;
                boolean newReversed = reversed;

                if (!reversed && c == Gaddag.SPLIT) {
                    newReversed = true;
                } else {
                    int newRow = anchorSquare.row;
                    int newCol = anchorSquare.col;

                    Direction direction = board.getWordDirection(newRow, newCol);
                    if (direction == Direction.HORIZONTAL) {
                        newCol = reversed ? newCol - 1 : newCol + 1;
                    } else {
                        newRow = reversed ? newRow - 1 : newRow + 1;
                    }

                    if (board.isValidMove(newRow, newCol, c)) {
                        StringBuilder newCurrentWord = new StringBuilder(currentWord);
                        newCurrentWord.append(c);
                        traverseGaddag(childNode, new AnchorSquare(' ', newRow, newCol), newRemainingRack, newCurrentWord, foundWords, newAnchorLetterUsed, newReversed, reversed ? anchorSquareIndex - 1 : anchorSquareIndex + 1);
                    }
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

        // Traverse GADDAG for words starting from the anchor square
        traverseGaddag(rootNode, anchorSquare, remainingRack, initialWord, foundWords, true, false, 0);

        // Traverse GADDAG for words that end at the anchor square
        for (int i = 0; i < remainingRack.length(); i++) {
            char c = remainingRack.charAt(i);
            if (rootNode.hasChild(c)) {
                StringBuilder newInitialWord = new StringBuilder();
                newInitialWord.append(c).append(anchorSquare.letter);
                String newRemainingRack = remainingRack.substring(0, i) + remainingRack.substring(i + 1);
                traverseGaddag(rootNode, anchorSquare, newRemainingRack, newInitialWord, foundWords, true, true, 1);
            }
        }

        return foundWords;
    }
    
}