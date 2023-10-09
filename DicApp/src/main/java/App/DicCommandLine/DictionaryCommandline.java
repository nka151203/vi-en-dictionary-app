package App.DicCommandLine;

import java.util.Comparator;
import java.util.Collections;
import java.util.List;
public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    /**
     * hiển thị danh sách các từ trong từ điển.
     */

    public void showAllWords() {
        List<Word> dictionary = dictionaryManagement.getDictionary();

        // Sort the dictionary alphabetically by English word
        Collections.sort(dictionary, Comparator.comparing(Word::getWordTarget));

        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < dictionary.size(); i++) {
            Word word = dictionary.get(i);
            System.out.printf("%-3d| %-7s| %s%n", i + 1, word.getWordTarget(), word.getWordExplain());
        }
    }

    /**
     * gọi hàm.
     */
    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }
}


