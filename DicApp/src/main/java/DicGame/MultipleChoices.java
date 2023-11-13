package DicGame;

import App.DicCommandLine.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MultipleChoices extends Game {
    private List<Quiz> quizList;
    private Random random;
    private Scanner scanner;
    private Quiz yourQuiz;
    private int score;
    private int countQuiz;

    public MultipleChoices() {
        quizList = new ArrayList<>();
        random = new Random();
        scanner = new Scanner(System.in);
        yourQuiz = new Quiz();
        score = 0;
        countQuiz = 0;
    }

    public void insertFromFile() {
        Path filePath = Paths.get("DicApp", "src", "main", "resources", "Database", "Questions.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            String question = "";
            String answer = "";
            String key = "";
            List<String> ql = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@")) {
                    question = line.substring(line.indexOf('@') + 2);
                } else if (line.matches("[a-dA-D]\\..*")) {
                    ql.add(line);
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        if (line.matches("[a-dA-D]\\..*")) {
                            ql.add(line);
                        } else {
                            if (line.startsWith("|")) {
                                answer = line.substring(line.indexOf('|') + 2);
                                key = line.substring(line.indexOf('|') + 2, line.indexOf('|') + 3);
                                if (!question.isEmpty()) {
                                    Quiz quiz = new Quiz(question, answer, key, new ArrayList<>(ql));
                                    quizList.add(quiz);
                                    question = "";
                                    answer = "";
                                    key = "";
                                    ql.clear();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playGame() {
        insertFromFile();
        boolean exit = false;
        int choice;
        while (!exit) {
            gameDisplay("Multiple Choices");
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
                        List<Quiz> quizMode;
                        boolean continueGame = true;
                        Set<String> RQ_E = new HashSet<>();
                        Set<String> RQ_H = new HashSet<>();
                        while (continueGame) {
                            int countScore;
                            int timeLimit;
                            if (gameMode == 1) {
                                countScore = 10;
                                timeLimit = EASY_TIME_LIMIT;
                                quizMode = new ArrayList<>(quizList.subList(quizList.size() - 300, quizList.size()));
                            } else {
                                countScore = 30;
                                timeLimit = HARD_TIME_LIMIT;
                                quizMode = new ArrayList<>(quizList.subList(0, 300));
                            }
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
                            yourQuiz.printfAnswer();
                            System.out.println("\nEnter your answer: ");
                            long startTime  = System.currentTimeMillis();
                            yourAnswer = scanner.nextLine();
                            long endTime = System.currentTimeMillis();
                            if ((endTime - startTime) / 1000.0 >= timeLimit) {
                                System.out.println("GAME OVER!");
                                System.out.println("Time's up! You didn't answer this question in time.");
                                System.out.println("The correct answer is: " + yourQuiz.getFullAnswer());
                                System.out.println("Your score: " + score);
                                yourQuiz.exportScore("multiple choices", score);
                                score = 0;
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

                            if (gameMode == 1) {
                                if (RQ_E.size() == quizMode.size()) {
                                    System.out.println("[1] Do you want to play the easy level again?");
                                    System.out.println("[2] Do you want to play harder?");
                                    System.out.println("[3] Exit?");
                                    System.out.println("Enter your choice: ");
                                    int tmp = scanner.nextInt();
                                    scanner.nextLine();
                                    if (tmp == 1) {
                                        gameMode = 1;
                                        RQ_E.clear();
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
                                            scanner.nextLine();
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
                                    scanner.nextLine();
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
                                    scanner.nextLine();
                                    if (tmp == 2) {
                                        gameMode = 2;
                                        RQ_H.clear();
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
                                            scanner.nextLine();
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
                                    scanner.nextLine();
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
                            quizMode.clear();
                        }
                        break;
                    case 2:
                        yourQuiz.importScore("multiple choices");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Action not supported.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    @Override
    Word randomQuestion() {
        return null;
    }

    private void showQuizList() {
        List<Quiz> quizs = quizList;
        int cnt = 1;
        for (Quiz i :  quizs) {
            System.out.print(cnt + ". ");
            System.out.println(i.getQuestion());
            i.printfAnswer();
            i.getFullAnswer();
            i.getKey();
            cnt++;
        }
        System.out.println(quizs.size());
    }
    public static void main(String []args) {
        MultipleChoices mc = new MultipleChoices();
        mc.insertFromFile();
        //mc.showQuizList();
        mc.playGame();
    }
}