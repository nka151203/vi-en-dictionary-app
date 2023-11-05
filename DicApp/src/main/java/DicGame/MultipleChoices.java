package DicGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MultipleChoices {
    private List<Quiz> quizList;
    private Random random;
    private Scanner scanner;
    private Quiz yourQuiz;
    private int score;
    private int countQuiz;
    private static final int GAME_DURATION = 30000;

    public MultipleChoices() {
        quizList = new ArrayList<>();
        random = new Random();
        scanner = new Scanner(System.in);
        yourQuiz = new Quiz();
        score = 0;
        countQuiz = 0;
    }

    public void insertFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\Questions.txt"))) {
            String line;
            String question = "";
            String answer = "";
            String key = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@")) {
                    question += line.substring(line.indexOf('@') + 2);
                } else if (line.startsWith("|")) {
                    answer = line.substring(line.indexOf('|') + 2);
                    key = line.substring(line.indexOf('|') + 1, line.indexOf('|') + 2);
                    Quiz quiz = new Quiz(question, answer, key);
                    quizList.add(quiz);
                    question = "";
                    answer = "";
                    key = "";
                } else {
                    question += line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMultipleChoices() {
        insertFromFile();
        boolean exit = false;
        int choice;
        while (!exit) {
            System.out.println("Welcome to Multiple Choices!");
            System.out.println("[0] Exit");
            System.out.println("[1] Play game");
            System.out.println("[2] High Score");
            System.out.println("[3] Help");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        System.out.println("Goodbye!");
                        exit = true;
                        break;
                    case 1:
                        System.out.println("Choose the game mode:");
                        System.out.println("1. Easy");
                        System.out.println("2. Hard");
                        System.out.print("Enter your choice: ");

                        int gameMode = scanner.nextInt();
                        // scanner.nextLine();
                        List<Quiz> quizMode;
                        boolean continueGame = true;
                        Set<String> RQ_E = new HashSet<>();
                        Set<String> RQ_H = new HashSet<>();
                        while (continueGame) {
                            int countScore;
                            int timeLimit;
                            if (gameMode == 1) {
                                countScore = 10;
                                timeLimit = GAME_DURATION * 3;
                                quizMode = new ArrayList<>(quizList.subList(quizList.size() - 300, quizList.size()));
                            } else {
                                countScore = 30;
                                timeLimit = GAME_DURATION * 2;
                                quizMode = new ArrayList<>(quizList.subList(0, 300));
                            }

                            TimerThread timerThread = new TimerThread(timeLimit);

                            Quiz randomQuiz = quizMode.get(random.nextInt(quizMode.size()));
                            int len = 0;

                            if (gameMode == 1) {
                                if (!RQ_E.isEmpty()) {
                                    len = RQ_E.size();
                                }
                                RQ_E.add(randomQuiz.getQuestion());
                                while (len == RQ_E.size()) {
                                    randomQuiz = quizMode.get(random.nextInt(quizMode.size()));
                                    RQ_E.add(randomQuiz.getQuestion());
                                }
                            } else {
                                if (!RQ_H.isEmpty()) {
                                    len = RQ_E.size();
                                }
                                RQ_H.add(randomQuiz.getQuestion());
                                while (len == RQ_E.size()) {
                                    randomQuiz = quizMode.get(random.nextInt(quizMode.size()));
                                    RQ_H.add(randomQuiz.getQuestion());
                                }
                            }
                            yourQuiz = randomQuiz;

                            String yourAnswer = "";
                            System.out.println("Your question is:\n");
                            System.out.println(yourQuiz.getQuestion());
                            System.out.println("\nEnter your answer: ");
                            timerThread.start();
                            yourAnswer = scanner.nextLine();

                            if (timerThread.getTimeLeft() <= 0) {
                                System.out.println("GAME OVER!");
                                System.out.println("Time's up! You didn't answer this question in time.");
                                System.out.println("The correct answer is: " + yourQuiz.getFullAnswer());
                                System.out.println("Your score: " + score);
                                yourQuiz.exportScore("multiple choices", score);
                                score = 0;
                                timerThread.stopTimer();
                            } else if (yourAnswer.equals(yourQuiz.getKey())) {
                                System.out.println("You are correct!");
                                score += countScore;
                                System.out.println("Your score: " + score);
                            } else {
                                System.out.println("You are incorrect, GAME OVER!");
                                System.out.println("The correct answer is: " + yourQuiz.getFullAnswer());
                                System.out.println("Your score: " + score);
                                yourQuiz.exportScore("multiple choices", score);
                                score = 0;
                            }
                            timerThread.stopTimer();

                            if (gameMode == 1) {
                                if (RQ_E.size() == quizMode.size()) {
                                    System.out.println("[1] Do you want to play the easy level again?");
                                    System.out.println("[2] Do you want to play harder?");
                                    System.out.println("[3] Exit?");
                                    System.out.println("Enter your choice: ");
                                    int tmp = scanner.nextInt();
                                    if (tmp == 1) {
                                        gameMode = 1;
                                        RQ_E = null;
                                        if (score != 0) {
                                            yourQuiz.exportScore("multiple choices", score);
                                            score = 0;
                                        }
                                    } else if (tmp == 2) {
                                        if (RQ_H.size() < quizList.size() - quizMode.size()) {
                                            gameMode = 2;
                                        } else {
                                            if (score != 0) {
                                                yourQuiz.exportScore("multiple choices", score);
                                                score = 0;
                                            }
                                            System.out.println("You have answered all the questions of this game");
                                            System.out.println("Do you want to play again?");
                                            System.out.println("[1] Easy");
                                            System.out.println("[2] Hard");
                                            System.out.println("[3] Exit?");
                                            System.out.print("Enter your choice: ");
                                            int yourChoice = scanner.nextInt();
                                            if (yourChoice != 3) {
                                                gameMode = yourChoice;
                                            } else {
                                                continueGame = false;
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("[1] Do you want to continue playing?");
                                    System.out.println("[2] Do you want to play harder?");
                                    System.out.println("[3] Exit?");
                                    System.out.println("Enter your choice: ");
                                    int tmp = scanner.nextInt();
                                    if (tmp != 3) {
                                        gameMode = tmp;
                                    } else {
                                        continueGame = false;
                                        if (score != 0) {
                                            yourQuiz.exportScore("multiple choices", score);
                                            score = 0;
                                        }
                                    }
                                }
                            } else {
                                if (RQ_H.size() == quizMode.size()) {
                                    System.out.println("[1] Do you want to play easier?");
                                    System.out.println("[2] Do you want to play the hard level again?");
                                    System.out.println("[3] Exit?");
                                    System.out.println("Enter your choice: ");
                                    int tmp = scanner.nextInt();
                                    if (tmp == 2) {
                                        gameMode = 2;
                                        RQ_H = null;
                                        if (score != 0) {
                                            yourQuiz.exportScore("multiple choices", score);
                                            score = 0;
                                        }
                                    } else if (tmp == 1) {
                                        if (RQ_E.size() < quizList.size() - quizMode.size()) {
                                            gameMode = 1;
                                        } else {
                                            if (score != 0) {
                                                yourQuiz.exportScore("multiple choices", score);
                                                score = 0;
                                            }
                                            System.out.println("You have answered all the questions of this game");
                                            System.out.println("Do you want to play again?");
                                            System.out.println("[1] Easy");
                                            System.out.println("[2] Hard");
                                            System.out.println("[3] Exit?");
                                            System.out.print("Enter your choice: ");
                                            int yourChoice = scanner.nextInt();
                                            if (yourChoice != 3) {
                                                gameMode = yourChoice;
                                            } else {
                                                continueGame = false;
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("[1] Do you want to play easier?");
                                    System.out.println("[2] Do you want to continue playing?");
                                    System.out.println("[3] Exit?");
                                    System.out.println("Enter your choice: ");
                                    int tmp = scanner.nextInt();
                                    if (tmp != 3) {
                                        gameMode = tmp;
                                    } else {
                                        continueGame = false;
                                        if (score != 0) {
                                            yourQuiz.exportScore("multiple choices", score);
                                            score = 0;
                                        }
                                    }
                                }
                            }
                            quizMode = null;
                        }
                        break;
                    case 2:
                        yourQuiz.importScore("multiple choices");
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


    public void Show() {
        for (int i = 0; i < 10;i++) {
            System.out.println(i+1 + "." + quizList.get(i).getQuestion() + "\n");

        }
    }

    public static void main(String[] args) {
        MultipleChoices test = new MultipleChoices();
        test.insertFromFile();
        test.playMultipleChoices();
    }

}