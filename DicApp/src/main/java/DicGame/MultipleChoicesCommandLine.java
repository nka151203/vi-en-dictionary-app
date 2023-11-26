package DicGame;

import java.util.*;

public class MultipleChoicesCommandLine extends MultipleChoices {
    private Scanner scanner;
    public MultipleChoicesCommandLine() {
        super();
        scanner = new Scanner(System.in);
    }

    public void displayOverTime() {
        System.out.println("GAME OVER!");
        System.out.println("Time's up! You didn't answer this question in time.");
        System.out.println("The correct answer is: " + yourQuiz.getFullAnswer());
        System.out.println("Your score: " + score);
        exportScore("multiple choices", score);
        score = 0;
    }

    public void display() {
        System.out.println("[1] Do you want to play again?");
        System.out.println("[2] Exit?");
        System.out.println("Enter your choice: ");
    }

    public void StopPlay() {
        System.out.println("Your score: " + score);
        if (score != 0) {
            exportScore("multiple choices", score);
            score = 0;
        }
    }


    @Override
    public void Lose() {
        System.out.println("GAME OVER!");
        System.out.println("The correct answer is: " + yourQuiz.getFullAnswer());
        System.out.println("Your score: " + score);
        if (score != 0) {
            exportScore("multiple choices", score);
            score = 0;
        }
    }

    public boolean checkTime(long t, long tl) {
        if (t / 1000.0 >= tl) {
            return false;
        } else {
            return true;
        }
    }

    public void displayCorrect(int m) {
        System.out.println("You are correct!");
        score += m;
        System.out.println("Your score: " + score);
    }
    public void playGame() {
        boolean exit = false;
        while(!exit) {
            gameDisplay("Multiple Choices");
            boolean continueGame = true;
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 0:
                        System.out.println("Goodbye!");
                        exit = true;
                        break;
                    case 1:
                        while(continueGame) {
                            showPlayGame();
                            int gameMode = scanner.nextInt();
                            scanner.nextLine();
                            long timeLimit = 0;
                            int cntScore = 0;
                            if (gameMode == 1) {
                                yourQuiz = randomQuiz(0);
                                timeLimit = EASY_TIME_LIMIT;
                                cntScore = E_score;
                            } else {
                                yourQuiz = randomQuiz(1);
                                timeLimit = HARD_TIME_LIMIT;
                                cntScore = H_score;
                            }

                            String yourAnswer = "";
                            System.out.println("Your question is:\n");
                            System.out.println(yourQuiz.getQuestion());
                            yourQuiz.printfAnswer();
                            System.out.println("\nEnter your answer: ");
                            long startTime  = System.currentTimeMillis();
                            yourAnswer = scanner.nextLine();
                            long endTime = System.currentTimeMillis();

                            if (checkTime(endTime - startTime, timeLimit)) {
                                if (checkYourAnswer(yourQuiz, yourAnswer)) {
                                    displayCorrect(cntScore);
                                } else {
                                    Lose();
                                }
                            } else {
                                displayOverTime();
                            }
                            display();
                            int tmp = scanner.nextInt();
                            scanner.nextLine();
                            if (tmp == 1) {
                                continue;
                            } else {
                                continueGame = false;
                                checkQuiz.clear();
                            }
                        }
                        break;
                    case 2:
                        importScore("multiple choices");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Action not supported.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public static void  main(String[] args) {
        MultipleChoices mc = new MultipleChoicesCommandLine();
        mc.insertFromFile();
        mc.playGame();
    }

}