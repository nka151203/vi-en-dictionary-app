package App.DicCommandLine;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryManagement {
    private Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    /**
     * Add new words from cmd.
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numWords; i++) {
            System.out.println("Word #" + (i + 1));
            System.out.print("Enter the English word: ");
            String englishWord = scanner.nextLine();
            System.out.print("Enter the Vietnamese meaning: ");
            String vietnameseMeaning = scanner.nextLine();

            Word word = new Word(englishWord, vietnameseMeaning);
            dictionary.add(word);
        }
    }

    /**
     * Add new words from file.
     */
    public void insertFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String wordTarget = parts[0];
                    String wordExplain = parts[1];
                    Word word = new Word(wordTarget, wordExplain);
                    dictionary.addWord(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find English word.
     */
    public List<Word> dictionaryLookup(String word) {
        List<Word> result = new ArrayList<>();
        for (Word w : dictionary.getWords()) {
            if (w.getWordTarget().startsWith(word)) {
                result.add(w);
            }
        }
        return result;
    }

    /**
     * Remove word.
     */
    public void removeWord(int index) {
        List<Word> words = dictionary.getWords();
        if (index >= 0 && index < words.size()) {
            Word word = words.get(index);
            dictionary.removeWord(word);
        }
    }

    /**
     * Update word.
     */
    public void updateWord(int index, String wordTarget, String wordExplain) {
        List<Word> words = dictionary.getWords();
        if (index >= 0 && index < words.size()) {
            Word word = words.get(index);
            word = new Word(wordTarget, wordExplain);
        }
    }

    /**
     * Export current dictionary data to file.
     */
    public void dictionaryExportToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            List<Word> words = dictionary.getWords();
            for (Word word : words) {
                writer.write(word.getWordTarget() + "\t" + word.getWordExplain());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new word.
     */
    public void addWord(String wordTarget, String wordExplain) {
        Word word = new Word(wordTarget, wordExplain);
        dictionary.addWord(word);
    }

    /**
     * Get the word list.
     */
    public List<Word> getDictionary() {
        return dictionary;
    }
}

