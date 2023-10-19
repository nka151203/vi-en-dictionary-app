package App.DicCommandLine;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class DictionaryManagement extends Dictionary {

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
            addWord(word);
        }
    }

    /**
     * Add new words from file.
     */
    public void insertFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\java\\App\\DicCommandLine\\dictionaries.txt"))) {
            String line;
            String word = "" ;
            String pronunciation= "" ;
            String meaning= "" ;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@") && line.contains("/")) {
                    word = line.substring(1,line.indexOf('/'));
                    pronunciation = line.substring(line.indexOf('/'));
                } else if (!word.isEmpty()) {
                    meaning += line;
                    meaning += '\n';
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        meaning += line;
                        meaning += '\n';
                    }
                    addWord(new Word(word, pronunciation, meaning));
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

        //listWord myList = new listWord();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\java\\App\\DicCommandLine\\dictionaries.txt",true))) {
            writer.write("\n");

            System.out.print("Enter English word: ");
            String wordTarget = sc.nextLine();

            System.out.print("Enter pronunciation: ");
            String pronun = sc.nextLine();

            System.out.print("Enter Vietnamese meaning: ");
            String meaning = sc.nextLine();
            writer.write("@" + wordTarget + " /" + pronun + "/\n");
            writer.write(meaning + "\n");

            Word word = new Word(wordTarget, pronun, meaning);
            addWord(word);

            System.out.println("Dictionary exported successfully.");
        } catch (Exception e) {
            System.out.println("Error exporting dictionary.");
            e.printStackTrace();
        }
    }

    /**
     * Add new word.
     */
    public void addWord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter English word: ");
        String wordTarget = scanner.nextLine();
        System.out.print("Enter pronunciation: ");
        String pronunciation = scanner.nextLine();
        System.out.print("Enter Vietnamese meaning: ");
        String wordExplain = scanner.nextLine();

        Word word = new Word(wordTarget, pronunciation, wordExplain);
        addWord(word);

        System.out.println("Word added successfully.");
    }

    /**
     * Remove word.
     */
    public void removeWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to remove: ");
        String wordTarget = scanner.nextLine();

        List<Word> words = getWords();

        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                removeWord(word);
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

        List<Word> words = getWords();

        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                System.out.print("Enter new English word: ");
                String newWordTarget = scanner.nextLine();
                System.out.print("Enter new Vietnamese meaning: ");
                String newWordExplain = scanner.nextLine();
                System.out.print("Enter new pronunciation: ");
                String newPronunciation = scanner.nextLine();

                word = new Word(newWordTarget, newPronunciation, newWordExplain);
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
        List<Word> words = getWords();
        int index = 1;
        for (Word word : words) {
            System.out.println(index + ". " + word.getWordTarget());
            System.out.println(word.getPronunciation());
            System.out.println(word.getWordExplain());
            System.out.println();
            index ++;
        }
    }

    /**
     * Find English word.
     */
    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to lookup: ");
        String lookupWord = scanner.nextLine();

        List<Word> words = getWords();

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

        List<Word> words = getWords();
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
}


