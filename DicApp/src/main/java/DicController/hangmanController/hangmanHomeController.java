package DicController.hangmanController;

import DicController.loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class hangmanHomeController {

    @FXML
    private BorderPane hangmanHome;

    @FXML
    void startHangman(ActionEvent event) {
        loader obj = new loader();
        Pane view = obj.getPane("Hangman/hangman");
    }

}
