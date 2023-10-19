package Game;

import App.DicCommandLine.Word;
import App.DicCommandLine.DictionaryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Hangman {
    private DictionaryManagement dictionary;
    private List<Word> words;
    private Random random;
    private Scanner scanner;
    private int score;
    private static final int GAME_DURATION = 120;

    public Hangman() {
        dictionary = new DictionaryManagement();
        dictionary.insertFromFile();
        words = dictionary.getWords();
        random = new Random();
        scanner = new Scanner(System.in);
        score = 0;
    }

    public void playHangmanGame() {
        if (words.isEmpty()) {
            System.out.println("No words in the dictionary.");
            return;
        }

        Word randomWord = words.get(random.nextInt(words.size()));
        String targetWord = randomWord.getWordTarget().toLowerCase();

        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < targetWord.length(); i++) {
            hiddenWord.append("_");
        }

        int numAttempts = 7;
        List<Character> guessedLetters = new ArrayList<>();

        String[] hangmanParts = {
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

        System.out.println("Welcome to Hangman!");
        System.out.println("Guess the word by entering one letter at a time.");
        System.out.println("You have " + GAME_DURATION + " seconds for each word.");
        System.out.println("You have " + numAttempts + " attempts.");
        System.out.println(hangmanParts[0]);

        int hangmanIndex = 0;
        long startTime = System.currentTimeMillis();

        while (numAttempts > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime);

            if (elapsedTime >= GAME_DURATION) {
                System.out.println("Time's up! You didn't guess the word in time.");
                break;
            }

            System.out.println("Word: " + hiddenWord.toString());
            System.out.print("Enter a letter: ");
            String input = scanner.nextLine();
            System.out.println();

            if (input.length() != 1) {
                System.out.println("Invalid input. Please enter a single letter.");
                System.out.println(hangmanParts[7-numAttempts]);
                continue;
            }

            char guess = input.toLowerCase().charAt(0);

            if (!Character.isLetter(guess)) {
                System.out.println("Invalid input. Please enter a letter.");
                System.out.println(hangmanParts[7-numAttempts]);

                continue;
            }

            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter. Try again.");
                System.out.println(hangmanParts[7-numAttempts]);
                continue;
            }

            guessedLetters.add(guess);

            if (targetWord.contains(String.valueOf(guess))) {
                for (int i = 0; i < targetWord.length(); i++) {
                    if (targetWord.charAt(i) == guess) {
                        hiddenWord.setCharAt(i, guess);
                    }
                }

                System.out.println(hangmanParts[7-numAttempts]);

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
                System.out.println(hangmanParts[7-numAttempts]);

            }
        }

        if (numAttempts == 0) {
            System.out.println("Game over! The word was: " + targetWord);
            System.out.println(randomWord.getPronunciation());
            System.out.println(randomWord.getWordExplain());
            System.out.println(hangmanParts[7-numAttempts]);
        }

        System.out.println("Score: " + score);

        System.out.print("Do you want to play again? (Y/N): ");
        String playAgain = scanner.nextLine();

        if (playAgain.equalsIgnoreCase("Y")) {
            playHangmanGame();
        }
    }

}