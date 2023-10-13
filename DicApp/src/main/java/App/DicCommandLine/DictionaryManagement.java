package App.DicCommandLine;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
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
    public void insertFromFile() {
        try {
            File file = new File("src/main/dictionaries.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("@")) {
                    String[] parts = line.split("/");
                    if (parts.length >= 3) {
                        String wordTarget = parts[0].substring(1).trim();
                        String pronunciation = parts[1].trim();
                        String wordExplain = line.substring(line.indexOf(parts[2])).trim();

                        Word word = new Word(wordTarget, wordExplain, pronunciation);
                        dictionary.addWord(word);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    /**
     * Export current dictionary data to file.
     */
    public void dictionaryExportToFile() {
        try {
            File file = new File("output.txt");
            FileWriter writer = new FileWriter(file);

            List<Word> words = dictionary.getWords();

            for (Word word : words) {
                writer.write("@ " + word.getWordTarget() + " /" + word.getPronunciation() + "/\n");
                writer.write(word.getWordExplain() + "\n");
            }

            writer.close();
            System.out.println("Dictionary exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting dictionary.");
        }
    }

    /**
     * Add new word.
     */
    public void addWord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter English word: ");
        String wordTarget = scanner.nextLine();
        System.out.print("Enter Vietnamese meaning: ");
        String wordExplain = scanner.nextLine();
        System.out.print("Enter pronunciation: ");
        String pronunciation = scanner.nextLine();

        Word word = new Word(wordTarget, wordExplain, pronunciation);
        dictionary.addWord(word);

        System.out.println("Word added successfully.");
    }

    /**
     * Remove word.
     */
    public void removeWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to remove: ");
        String wordTarget = scanner.nextLine();

        List<Word> words = dictionary.getWords();

        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                dictionary.removeWord(word);
                System.out.println("Word removed successfully.");
                return;
            }
        }

        System.out.println("Word not found in the dictionary.");
    }

    /**
     * Update word.
     */
    public void updateWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to update: ");
        String wordTarget = scanner.nextLine();

        List<Word> words = dictionary.getWords();

        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                System.out.print("Enter new English word: ");
                String newWordTarget = scanner.nextLine();
                System.out.print("Enter new Vietnamese meaning: ");
                String newWordExplain = scanner.nextLine();
                System.out.print("Enter new pronunciation: ");
                String newPronunciation = scanner.nextLine();

                word = new Word(newWordTarget, newWordExplain, newPronunciation);
                System.out.println("Word updated successfully.");
                return;
            }
        }

        System.out.println("Word not found in the dictionary.");
    }

    /**
     * Display a list of words.
     */
    public void displayAllWords() {
        List<Word> words = dictionary.getWords();
        for (Word word : words) {
            System.out.println("Word: " + word.getWordTarget());
            System.out.println("Pronunciation: " + word.getPronunciation());
            System.out.println("Meaning: " + word.getWordExplain());
            System.out.println();
        }
    }

    /**
     * Find English word.
     */
    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to lookup: ");
        String lookupWord = scanner.nextLine();

        List<Word> words = dictionary.getWords();

        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(lookupWord)) {
                System.out.println("Word: " + word.getWordTarget());
                System.out.println("Pronunciation: " + word.getPronunciation());
                System.out.println("Meaning: " + word.getWordExplain());
                return;
            }
        }

        System.out.println("Word not found in the dictionary.");
    }

    /**
     * Search words with prefix.
     */
    public void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a prefix to search: ");
        String prefix = scanner.nextLine();

        List<Word> words = dictionary.getWords();
        List<String> matchedWords = new ArrayList<>();

        for (Word word : words) {
            if (word.getWordTarget().toLowerCase().startsWith(prefix.toLowerCase())) {
                matchedWords.add(word.getWordTarget());
            }
        }

        if (matchedWords.isEmpty()) {
            System.out.println("No words found.");
        } else {
            System.out.println("Words starting with \"" + prefix + "\":");
            for (String word : matchedWords) {
                System.out.println(word);
            }
        }
    }

    /**
     * Get the word list.
     */
    public List<Word> getDictionary() {
        return dictionary;
    }
}


