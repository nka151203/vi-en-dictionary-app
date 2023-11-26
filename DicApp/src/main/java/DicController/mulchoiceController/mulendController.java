package DicController.mulchoiceController;

import DicGame.Player;
import DicGame.playerManagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import javafx.scene.text.Font;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mulendController implements Initializable {
    playerManagement list = new playerManagement();
    @FXML
    private VBox level;

    @FXML
    private VBox name;

    @FXML
    private VBox point;

    @FXML
    private VBox time;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.writeToFile();
        list.readFromFile();
        System.out.println(list.getPlayers().toArray());
        List<Player> sorted = list.getSortedPlayers();
        for(Player i: sorted){
            String style =" -fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: white";
            // Thiết lập font cho JLabel
            Label nameAdd = new Label(i.getName());
            Label levelAdd = new Label(i.getLevel());
            Label pointAdd = new Label(i.getScore()+"");
            int minutes = (int) ((int)i.getTime() / 60);
            int seconds = (int) ((int)i.getTime() % 60);
            Label timeAdd = new Label(String.format("%02d:%02d", minutes, seconds));
            nameAdd.setStyle(style);
            levelAdd.setStyle(style);
            pointAdd.setStyle(style);
            nameAdd.setStyle(style);
            timeAdd.setStyle(style);
            name.getChildren().add(nameAdd);
            level.getChildren().add(levelAdd);
            point.getChildren().add(pointAdd);
            time.getChildren().add(timeAdd);
        }
    }
}
