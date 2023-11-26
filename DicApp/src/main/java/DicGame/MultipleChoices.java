package DicGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MultipleChoices extends Game {
    public static int score = 0;
    public static List<Quiz> quizList;
    public static Set<String> checkQuiz;
    public Random random;
    public Quiz yourQuiz;

    public MultipleChoices() {
        quizList = new ArrayList<>();
        random = new Random();
        yourQuiz = new Quiz();
        checkQuiz = new HashSet<>();
    }

    /**
     * insert question data from file to quizList
     */
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

    /**
     * Random Quiz
     * @int 0 is Ez, 1 is Hard
     * @return randomQuiz
     */
    public Quiz randomQuiz(int mode) {
        List<Quiz> listQuiz =  new ArrayList<>();
        if (mode == 0) {
            listQuiz = quizList.subList(quizList.size() - 300, quizList.size());
        } else {
            listQuiz = quizList.subList(0, 300);
        }
        //if (checkEasyQuiz.size() == EasyQuiz.size()){
          //  return null;
        //}
        //int len = 0;

        Quiz randomQiz = listQuiz.get(random.nextInt(listQuiz.size()));

        //if (!checkQuiz.isEmpty()) {
            int len = checkQuiz.size();
        //}
        checkQuiz.add(randomQiz.getQuestion());
        while (len == checkQuiz.size()) {
            randomQiz = listQuiz.get(random.nextInt(listQuiz.size()));
            checkQuiz.add(randomQiz.getQuestion());
        }
        return randomQiz;
    }

    /**
     * check answer true or false
     * @Quiz currentQuiz
     * @String answer
     * @return status
     */
    public boolean checkYourAnswer(Quiz newQuiz, String answer) {
        return answer.equalsIgnoreCase(newQuiz.getKey());
    }

    /**
     * Lose or reset your turn.
     */
    public void Lose() {
        if (score != 0) {
            exportScore("multiple choices", score);
            score = 0;
        }
        checkQuiz.clear();
    }

    @Override
    void playGame() {

    }
}
