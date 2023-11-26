package DicGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class playerManagement {
    private List<Player> players;
    public playerManagement(){
        players = new ArrayList<>();
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\mulScore.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Xử lý từng dòng dữ liệu và tạo đối tượng Player
                String name = line;
                String level = reader.readLine();
                int score = Integer.parseInt(reader.readLine());
                int time = Integer.parseInt(reader.readLine());

                // Thêm Player vào danh sách
                players.add(new Player(name, level, score, time));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\resources\\Database\\mulScore.txt", true))) {
            for (Player player : players) {
                // Ghi thông tin của mỗi player vào file
                player.writeToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Player> getSortedPlayers() {
        // Tạo một bản sao của danh sách để tránh sắp xếp trực tiếp danh sách gốc
        List<Player> sortedPlayers = getPlayers();

        // Sắp xếp danh sách theo điều kiện đã mô tả
        Collections.sort(sortedPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                // So sánh theo score giảm dần
                int scoreComparison = Integer.compare(player2.getScore(), player1.getScore());

                // Nếu score bằng nhau, so sánh theo time tăng dần
                if (scoreComparison == 0) {
                    return Integer.compare(player1.getTime(), player2.getTime());
                }

                return scoreComparison;
            }
        });
        return sortedPlayers;
    }
}
