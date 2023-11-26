package DicGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Player {
    private String name;
    private String level;
    private int score;
    private int time;

    public Player(String n, String lv, int score, int time){
        this.name = n;
        this.level = lv;
        this.score = score;
        this.time = time;
    }
    public String toFormattedString() {
        return name + "\n" + level + "\n" + score + "\n" + time +"\n";
    }
    public void writeToFile() {
        System.out.println(this.toFormattedString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\resources\\Database\\mulScore.txt", true))) {
            // Ghi thông tin của player vào file
            writer.write(this.toFormattedString());
            System.out.println("Ghi ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
