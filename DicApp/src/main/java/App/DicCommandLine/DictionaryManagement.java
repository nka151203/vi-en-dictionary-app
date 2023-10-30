package App.DicCommandLine;

import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import Trie.Trie;

public class DictionaryManagement extends Dictionary {
    public Trie trie;

    public DictionaryManagement() {
        trie = new Trie();
    }

    private final int indexSepate = 33;
    //private final int indexSepate = 58169;

    /**
     * Add new words from cmd.
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWords = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numWords; i++) {
            System.out.println("Word #" + (i + 1));
            System.out.print("Enter the English word: ");
            String englishWord = scanner.nextLine();
            System.out.print("Enter the Vietnamese meaning: ");
            String vietnameseMeaning = scanner.nextLine();

            Word word = new Word(englishWord, vietnameseMeaning);
            addWord(word);
        }
    }

    /**
     * Add new words from file.
     */
    public void insertFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\dictionary.txt"))) {
        //try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\dictionary.txt"))) {
            String line;
            String word = "" ;
            String pronunciation= "" ;
            String meaning= "" ;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@") && line.contains("/")) {
                    word = line.substring(1,line.indexOf('/')-1);
                    pronunciation = line.substring(line.indexOf('/'));
                } else if (!word.isEmpty()) {
                    meaning += line;
                    meaning += '\n';
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        meaning += line;
                        meaning += '\n';
                    }

                    addWord(new Word(word, pronunciation, meaning));
                    trie.insertWord(word);
                    meaning = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Export current dictionary data to file.
     */
    public void dictionaryExportToFile() {
        Scanner sc = new Scanner(System.in);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\java\\App\\DicCommandLine\\dictionaries.txt",false))) {
            List<Word> words = getWords();
            writer.write("-----" + "\n");
            for (Word word : words)
                writer.write("@" + word.getWordTarget() + " " + word.getPronunciation() + "\n" + word.getWordExplain() + "\n");

        } catch (Exception e) {
            System.out.println("Error exporting dictionary.");
            e.printStackTrace();
        }
    }

    /**
     * Add new word.
     */
    public void addWord(String wordTarget, String pronunciation, String wordExplain) {

        Word word = new Word(wordTarget, pronunciation, wordExplain);
        addWord(word);
        trie.insertWord(wordTarget);

        dictionaryExportToFile();
        System.out.println("Word added successfully.");
    }

    /**
     * Remove word.
     */
    public void removeWord(String wordTarget) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words.subList(0, indexSepate), wordTarget);

        if (neededWord == null) {
            neededWord = binarySearchWord(words.subList(indexSepate, words.size()), wordTarget);
        }

        if (neededWord != null) {
            removeWord(neededWord);
            trie.deleteWord(wordTarget);

            dictionaryExportToFile();
            System.out.println("Word removed successfully.");
            return;
        }

        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");

    }

    /**
     * Update word.
     */
    public void updateWord(String wordTarget, String newWordTarget, String newPronunciation, String newWordExplain) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words.subList(0, indexSepate), wordTarget);

        if (neededWord == null) {
            neededWord = binarySearchWord(words.subList(indexSepate, words.size()), wordTarget);
        }

        if (neededWord != null) {

            neededWord.setWordTarget(newWordTarget);
            neededWord.setPronunciation(newPronunciation);
            neededWord.setWordExplain(newWordExplain);

            trie.insertWord(newWordTarget);
            trie.deleteWord(wordTarget);

            dictionaryExportToFile();
            System.out.println("Word updated successfully.");
            return;
        }

        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");

    }

    /**
     * Display a list of words.
     */
    public void displayAllWords() {
        List<Word> words = getWords();
        int index = 1;
        for (Word word : words) {
            System.out.println(index + ". " + word.getWordTarget());
            System.out.println(word.getPronunciation());
            System.out.println(word.getWordExplain());
            index ++;
        }
    }

    /**
     * Find English word.
     */
    public void dictionaryLookup(String wordTarget) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words.subList(0, indexSepate), wordTarget);

        if (neededWord == null) {
            neededWord = binarySearchWord(words.subList(indexSepate, words.size()), wordTarget);
        }

        if (neededWord != null) {
                System.out.println("Word: " + neededWord.getWordTarget());
                System.out.println("Pronunciation: " + neededWord.getPronunciation());
                System.out.println("Meaning: " + neededWord.getWordExplain());
                return;
        }

        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");
    }

    /**
     * Search words with prefix.
     */
    public void dictionarySearcher(String prefix) {
        List<String> matchedWords = trie.searchWordsWithPrefix(prefix);
        Collections.sort(matchedWords);

        int count = 0;

        if (matchedWords.isEmpty()) {
            System.out.println("No words found.");
        } else {
            System.out.println("Words starting with \"" + prefix + "\":");
            for (String word : matchedWords) {
                System.out.println(count + ". " + word);
                count ++;
                if (count == 20 || count > matchedWords.size()) break;
            }
        }
    }

    private Word binarySearchWord(List<Word> words, String target) {
        int left = 0;
        int right = words.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Word word = words.get(mid);
            String wordTarget = word.getWordTarget();

            int compareResult = wordTarget.compareToIgnoreCase(target);
            if (compareResult == 0) {
                return word;
            } else if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

}


