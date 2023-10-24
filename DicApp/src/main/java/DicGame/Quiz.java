package DicGame;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Quiz {
    private String question;
    private String fullAnswer;
    private String key;

    public Quiz() {}

    public Quiz(String q, String f, String k) {
        this.question = q;
        this.fullAnswer = f;
        this.key = k;
    }

    public String getQuestion() {
        return question;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }

    public String getKey() {
        return key;
    }

    public void importScore(String key) {
        List<Integer> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\score.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    String word = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    if (word.equals(key)) {
                        scores.add(score);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(scores, Comparator.reverseOrder());

        int cnt = 0;

        System.out.println("List of score:");
        for (int score : scores) {
            cnt ++;
            System.out.println(cnt + ". " + score);
        }

        return;
    }

    public void exportScore(String key, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\resources\\Database\\score.txt", true))) {
            String scoreEntry = key + " - " + score;
            writer.write(scoreEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
