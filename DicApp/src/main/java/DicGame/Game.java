package DicGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Game {

    static final int EASY_TIME_LIMIT = 120;
    static final int HARD_TIME_LIMIT = 90;
    public static final int E_score = 10;
    public static final int H_score = 20;

    void gameDisplay(String game) {
        System.out.println("Welcome to " + game + "!");
        System.out.println("[0] Exit");
        System.out.println("[1] Play game");
        System.out.println("[2] High Score");
        System.out.print("Enter your choice: ");
    }

    void showPlayGame() {
        System.out.println("Choose the game mode:");
        System.out.println("1. Easy");
        System.out.println("2. Hard");
        System.out.print("Enter your choice: ");
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


    abstract void playGame();
    //abstract Word randomQuestion();
}
