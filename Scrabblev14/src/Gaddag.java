import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Gaddag {
    private final GaddagNode root;

    public Gaddag() {
        root = new GaddagNode();
    }

    public void insertWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            String reversedPrefix = new StringBuilder(word.substring(0, i)).reverse().toString();
            String remainingSuffix = word.substring(i);
            String gaddagWord = reversedPrefix + '>' + remainingSuffix;

            GaddagNode currentNode = root;
            for (char c : gaddagWord.toCharArray()) {
                if (!currentNode.hasChild(c)) {
                    currentNode.addChild(c, new GaddagNode());
                }
                currentNode = currentNode.getChild(c);
            }
            currentNode.setWord(true);
        }
    }

    public GaddagNode getRoot() {
        return root;
    }

    public void loadWordsFromFile(String fileName) {
        try {
            List<String> words = Files.readAllLines(Paths.get(fileName));
            for (String word : words) {
                insertWord(word.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}