package DicController;

import DicGame.Hangman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class hangman implements Initializable {

    @FXML
    private TextField ans;

    @FXML
    private Label time;

    @FXML
    private Label word;
    Hangman startGame = new Hangman();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void okChar(ActionEvent event) {

    }

}
