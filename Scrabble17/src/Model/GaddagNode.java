package Model;
import java.util.HashMap;
import java.util.Map;

public class GaddagNode {
    private final Map<Character, GaddagNode> children;
    private boolean isWord;

    public GaddagNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void addChild(char c, GaddagNode node) {
        children.put(c, node);
    }

    public GaddagNode getChild(char c) {
        return children.get(c);
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean isWord) {
        this.isWord = isWord;
    }
}