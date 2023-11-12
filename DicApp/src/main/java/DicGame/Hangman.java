package DicGame;

import App.DicCommandLine.Word;
import App.DicCommandLine.DictionaryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Hangman extends Game {
    private DictionaryManagement dictionary;
    private List<Word> words;

    private List<String> usedWord = new ArrayList<>();
    private Scanner scanner;
    private int score;

    @Override
    Word randomQuestion() {
        Random random = new Random();
        Word randomWord = words.get(random.nextInt(words.size()));
        String targetWord = randomWord.getWordTarget().toLowerCase();

        while (!isValidWord(targetWord) || isDuplicateWord(targetWord)) {
            randomWord = words.get(random.nextInt(words.size()));
            targetWord = randomWord.getWordTarget().toLowerCase();
        }
        return randomWord;
    }

    private boolean isValidWord(String word) {
        if (word.matches(".*[^a-zA-Z].*")) {
            return false;
        }

        if (word.contains(" ")) {
            return false;
        }

        return true;
    }

    private boolean isDuplicateWord(String word) {
        for (String w : usedWord) {
            if (w.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private String[] hangmanParts = {
            "   -------------    \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |                \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |           |    \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |          /|    \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |          /|\\  \n"
                    + "   |                \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |          /|\\  \n"
                    + "   |          /     \n"
                    + "   |     \n"
                    + " -----   \n",
            "   -------------    \n"
                    + "   |           |    \n"
                    + "   |           O    \n"
                    + "   |          /|\\  \n"
                    + "   |          / \\  \n"
                    + "   |     \n"
                    + " -----   \n"
    };

    public Hangman() {
        dictionary = new DictionaryManagement();
        dictionary.insertFromFile();
        words = dictionary.getWords();

        if (words.isEmpty()) {
            System.out.println("No words in the dictionary.");
            return;
        }

        scanner = new Scanner(System.in);
        score = 0;
    }

    private char getHint(String targetWord, String hiddenWord) {
        for (int i = 0; i < targetWord.length(); i++) {
            if (hiddenWord.charAt(i) == '_') {
                return targetWord.charAt(i);
            }
        }
        return '\0';
    }

    public void playGame() {

        int choice;
        boolean exit = false;

        while (!exit) {
            gameDisplay("Hangman");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 0:
                        System.out.println("Goodbye!");
                        exit = true;
                        break;
                    case 1:
                        showPlayGame();

                        int gameMode = scanner.nextInt();
                        scanner.nextLine();

                        int hintNumber = NUM_HINTS;
                        boolean continueGame = true;

                        while (continueGame) {

                            Word randomWord = randomQuestion();
                            String targetWord = randomWord.getWordTarget();

                            StringBuilder hiddenWord = new StringBuilder();

                            for (int i = 0; i < targetWord.length(); i++) {
                                hiddenWord.append("_");
                            }
                            int timeLimit = (gameMode == 1) ? EASY_TIME_LIMIT : HARD_TIME_LIMIT;
                            int numAttempts = 7;
                            System.out.println("Let's Start!");
                            System.out.println(hangmanParts[0]);
                            List<Character> guessedLetters = new ArrayList<>();
                            long startTime = System.currentTimeMillis();
                            long time = 0;
                            while (numAttempts > 0) {
                                long currentTime = System.currentTimeMillis();
                                long elapsedTime = (currentTime - startTime) / 1000;
                                if (elapsedTime >= timeLimit) {
                                    System.out.println("Time's up! You didn't guess the word in time.");
                                    System.out.println("Game over! The word was: " + targetWord);
                                    System.out.println(randomWord.getPronunciation());
                                    System.out.println(randomWord.getWordExplain());
                                    break;
                                }
                                System.out.println("Word: " + hiddenWord.toString());
                                System.out.println("Attempts left: " + numAttempts);
                                System.out.println("Time left: " + (timeLimit - elapsedTime) + " seconds");
                                System.out.println("Hints left: " + hintNumber);
                                System.out.print("Enter a letter or 'hint' for a hint: ");
                                String input = scanner.nextLine();
                                System.out.println();
                                char guess = input.toLowerCase().charAt(0);
                                if (guessedLetters.contains(guess)) {
                                    System.out.println("You already guessed that letter. Try again.");
                                    System.out.println(hangmanParts[7 - numAttempts]);
                                    continue;
                                }
                                if (input.equals("hint")) {
                                    if (hintNumber > 0) {
                                        hintNumber--;
                                        char hint = getHint(targetWord, hiddenWord.toString());
                                        for (int i = 0; i < targetWord.length(); i++) {
                                            if (targetWord.charAt(i) == hint) {
                                                hiddenWord.setCharAt(i, hint);
                                            }
                                        }
                                        if (!hiddenWord.toString().contains("_")) {
                                            System.out.println("Congratulations! You guessed the word: " + targetWord);
                                            System.out.println(randomWord.getPronunciation());
                                            System.out.println(randomWord.getWordExplain());
                                            score++;
                                            break;
                                        } else {
                                            System.out.println(hangmanParts[7 - numAttempts]);
                                        }
                                    } else {
                                        System.out.println("No hints left.");
                                    }
                                    continue;
                                }
                                guessedLetters.add(guess);
                                if (targetWord.contains(String.valueOf(guess))) {
                                    for (int i = 0; i < targetWord.length(); i++) {
                                        if (targetWord.charAt(i) == guess) {
                                            hiddenWord.setCharAt(i, guess);
                                        }
                                    }
                                    System.out.println(hangmanParts[7 - numAttempts]);
                                    if (!hiddenWord.toString().contains("_")) {
                                        System.out.println("Congratulations! You guessed the word: " + targetWord);
                                        System.out.println(randomWord.getPronunciation());
                                        System.out.println(randomWord.getWordExplain());
                                        score++;
                                        break;
                                    }
                                } else {
                                    numAttempts--;
                                    System.out.println("Incorrect guess. You have " + numAttempts + " attempts remaining.");
                                    System.out.println(hangmanParts[7 - numAttempts]);
                                }

                                time = timeLimit - elapsedTime;
                            }
                            if (numAttempts == 0) {
                                System.out.println("Game over! The word was: " + targetWord);
                                System.out.println(randomWord.getPronunciation());
                                System.out.println(randomWord.getWordExplain());
                            }
                            score += numAttempts * (time);
                            System.out.println("Score: " + score);
                            System.out.println("Do you want to play again? (Y/N): ");
                            String playAgain = scanner.nextLine();
                            if (playAgain.equalsIgnoreCase("N")) {
                                continueGame = false;
                            }
                        }
                        System.out.println("Your Score: " + score);
                        exportScore("hangman", score);
                        break;
                    case 2:
                        importScore("hangman");
                        break;
                    case 3:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Action not supported.");
                scanner.nextLine();
            }
        }
    }

}