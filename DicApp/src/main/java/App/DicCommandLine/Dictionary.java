package App.DicCommandLine;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> words;
    public Dictionary() {
        words = new ArrayList<>();
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public void removeWord(Word word) {
        words.remove(word);
    }

    public List<Word> getWords() {
        return words;
    }
}
