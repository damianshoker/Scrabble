package Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ScrabbleDictionary {
    private Set<String> dictionary;

    public ScrabbleDictionary(String filePath) {
        dictionary = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWordInDictionary(String word) {
        return dictionary.contains(word.toLowerCase());
    }
}