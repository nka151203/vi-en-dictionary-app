package DicController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class gameListController {
    @FXML
    private BorderPane gameHome;
    @FXML
    void hm(ActionEvent event)  {
        loader obj = new loader();
        Pane view = obj.getPane("Hangman/hangman");
        gameHome.setCenter(view);
    }
    @FXML
    void mul(ActionEvent event) {
        loader obj = new loader();
        Pane view = obj.getPane("MultipleChoice/mulchoiceHome");
        gameHome.setCenter(view);
    }
}
