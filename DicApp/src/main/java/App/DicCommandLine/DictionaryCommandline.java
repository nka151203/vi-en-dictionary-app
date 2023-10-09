package App.DicCommandLine;

import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    /**
     * Show all words in dictionary.
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
     * Call function.
     */
    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    /**
     * Look up word.
     */
    public void dictionaryLookup(String word) {
        List<Word> result = dictionaryManagement.dictionaryLookup(word);
        if (result.isEmpty()) {
            System.out.println("No matching words found.");
        } else {
            System.out.println("Matching words:");
            for (Word w : result) {
                System.out.println(w.getWordTarget() + " - " + w.getWordExplain());
            }
        }
    }

    /**
     * Advanced dictionary.
     */
    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.print("Your action: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 0:
                        exit = true;
                        break;
                    case 1:
                        addWord();
                        break;
                    case 2:
                        removeWord();
                        break;
                    case 3:
                        updateWord();
                        break;
                    case 4:
                        showAllWords();
                        break;
                    case 5:
                        System.out.print("Enter a word to lookup: ");
                        String word = scanner.nextLine();
                        dictionaryLookup(word);
                        break;
                    default:
                        System.out.println("Action not supported.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Action not supported.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public void addWord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the English word: ");
        String wordTarget = scanner.nextLine();
        System.out.print("Enter the Vietnamese meaning: ");
        String wordExplain = scanner.nextLine();

        dictionaryManagement.addWord(wordTarget, wordExplain);
        System.out.println("Word added successfully.");
    }

    public void removeWord() {
        Scanner scanner = new Scanner(System.in);
    }

    public void updateWord() {
        Scanner scanner = new Scanner(System.in);
    }

}


