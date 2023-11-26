package App.DicCommandLine;

import DicGame.HangmanCommandLine;
import DicGame.MultipleChoicesCommandLine;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {

    /**
     * Show all words in dictionary.
     */

    public void showAllWords() {
        List<Word> words = getWords();

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
                scanner.nextLine();
                switch (choice) {
                    case 0:
                        dictionaryExportToFile();
                        System.out.println("Goodbye!");
                        exit = true;
                        break;
                    case 1:
                        System.out.print("Enter English word: ");
                        String wordTarget = scanner.nextLine();
                        System.out.print("Enter pronunciation: ");
                        String pronunciation = scanner.nextLine();
                        System.out.print("Enter Vietnamese meaning: ");
                        String wordExplain = scanner.nextLine();
                        System.out.println("Do you want add to interested list?");
                        String check = scanner.nextLine();
                        boolean interested = false;
                        if (check == "yes")  interested = true;
                        addWord(wordTarget,pronunciation,wordExplain, interested);
                        break;
                    case 2:
                        System.out.print("Enter the word to remove: ");
                        String wordRemove = scanner.nextLine();

                        removeWord(wordRemove);
                        break;
                    case 3:
                        System.out.print("Enter the word to update: ");
                        String wordUpdate = scanner.nextLine();

                        System.out.print("Enter new English word: ");
                        String newWordTarget = scanner.nextLine();
                        System.out.print("Enter new pronunciation: ");
                        String newPronunciation = scanner.nextLine();
                        System.out.print("Enter new Vietnamese meaning: ");
                        String newWordExplain = scanner.nextLine();

                        updateWord(wordUpdate, newWordTarget, newPronunciation, newWordExplain);
                        break;
                    case 4:
                        displayAllWords();
                        break;
                    case 5:
                        System.out.print("Enter a word to look up: ");
                        String wordLookup = scanner.nextLine();

                        dictionaryLookup(wordLookup);
                        break;
                    case 6:
                        System.out.print("Enter a prefix to search: ");
                        String prefix = scanner.nextLine().toLowerCase();

                        dictionarySearcher(prefix);
                        break;
                    case 7:
                        boolean release = false;
                        while (!release) {
                            System.out.println("Welcome to Game!");
                            System.out.println("[0] Exit");
                            System.out.println("[1] Hangman");
                            System.out.println("[2] MultipleChoices");

                            try {
                                int choose = scanner.nextInt();
                                scanner.nextLine();
                                switch (choose) {
                                    case 0 -> {
                                        System.out.println("Goodbye!");
                                        release = true;
                                    }
                                    case 1 -> {
                                        HangmanCommandLine hangman = new HangmanCommandLine();
                                        hangman.playGame();
                                    }
                                    case 2 -> {
                                        MultipleChoicesCommandLine mc = new MultipleChoicesCommandLine();
                                        mc.playGame();
                                    }

                                    default -> throw new IllegalStateException("Unexpected value: " + choose);
                                }
                            }
                            catch (Exception e) {
                                System.out.println("Action not supported.");
                                scanner.nextLine();
                            }
                        }
                        break;
                    case 8:
                        break;
                    case 9:
                        dictionaryExportToFile();
                        System.out.println("Dictionary exported successfully.");
                        break;
                    default:
                        System.out.println("Action not supported.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Action not supported.");
                scanner.nextLine();
            }
        }
    }

}
