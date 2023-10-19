package App.DicCommandLine;

import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import Game.Hangman;

public class DictionaryCommandline extends DictionaryManagement {

    /**
     * Show all words in dictionary.
     */

    public void showAllWords() {
        List<Word> words = getWords();

        // Sort the dictionary alphabetically by English word
        Collections.sort(words, Comparator.comparing(Word::getWordTarget));

        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.printf("%-3d| %-7s| %s%n", i + 1, word.getWordTarget(), word.getWordExplain());
        }
    }

    /**
     * Call function.
     */
    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    /**
     * Advanced dictionary.
     */
    public void dictionaryAdvanced() {
        //dictionaryManagement.insertFromFile();
        Scanner scanner = new Scanner(System.in);
        DictionaryManagement dictionary = new DictionaryManagement();
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
                        System.out.println("Goodbye!");
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
                        displayAllWords();
                        break;
                    case 5:
                        dictionaryLookup();
                        break;
                    case 6:
                        dictionarySearcher();
                        break;
                    case 7:
                        Hangman hangman = new Hangman();
                        hangman.playHangmanGame();
                        break;
                    case 8:
                        break;
                    case 9:
                        dictionaryExportToFile();
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

}
