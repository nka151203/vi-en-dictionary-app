package DicGame;

import App.DicCommandLine.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HangmanCommandLine extends Hangman {
    public HangmanCommandLine() {
        super();
        scanner = new Scanner(System.in);
    }

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

    public void playGame() {

        int choice;
        boolean exit = false;

        while (!exit) {
            gameDisplay("Hangman");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

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

                            Word randomWord = RandomWord();
                            String targetWord = randomWord.getWordTarget();
                            StringBuilder hiddenWord = new StringBuilder();

                            showHidden(randomWord,hiddenWord);

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
                                    System.out.println("Game over! The word was: " + getResultWord(randomWord));
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
                                        hintNumber --;
                                        hiddenWord = useHint(randomWord,hiddenWord);
                                        if (!hiddenWord.toString().contains("_")) {
                                            System.out.println("Congratulations! You guessed the word: " + getResultWord(randomWord));
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
                                    hiddenWord = checkChar(randomWord,hiddenWord,guess);
                                    System.out.println(hangmanParts[7 - numAttempts]);

                                    if (checkWinOrLose(hiddenWord) == 1) {
                                        System.out.println("Congratulations! You guessed the word: " + getResultWord(randomWord));
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
                                System.out.println("Game over! The word was: " + getResultWord(randomWord));
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
