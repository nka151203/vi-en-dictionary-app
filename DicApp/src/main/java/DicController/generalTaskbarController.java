package DicController;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class generalTaskbarController implements Initializable {

    @FXML
    private BorderPane mainView;
    String isSelected="-fx-border-width: 0 0 0 3; -fx-border-color: #5bf3e8;";

    @FXML
    private JFXButton homebut, infobut, lovebut, searchbut, transbut, addbut, gamebut;
    @FXML
    private JFXButton[] taskGroup;

    @FXML
    void add(ActionEvent event) {
        System.out.println("You choice ad");
        loader obj = new loader();
        Pane view = obj.getPane("homeTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        addbut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @FXML
    void game(ActionEvent event) {
        System.out.println("You choice home");
        loader obj = new loader();
        Pane view = obj.getPane("homeTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        gamebut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @FXML
    void home(ActionEvent event) {
        System.out.println("You choice home");
        loader obj = new loader();
        Pane view = obj.getPane("homeTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        homebut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @FXML
    void info(ActionEvent event) {
        System.out.println("You choice home");
        loader obj = new loader();
        Pane view = obj.getPane("homeTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        infobut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @FXML
    void love(ActionEvent event) {
        System.out.println("You choice home");
        loader obj = new loader();
        Pane view = obj.getPane("homeTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        lovebut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @FXML
    void search(ActionEvent event) {
        System.out.println("You choice search");
        loader obj = new loader();
        Pane view = obj.getPane("searchTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        searchbut.setStyle(isSelected);
        mainView.setCenter(view);
    }
    //homebut, infobut, lovebut, searchbut, transbut, inforbut;
    @FXML
    void translate(ActionEvent event) {
        System.out.println("You choice home");
        loader obj = new loader();
        Pane view = obj.getPane("translateTemplate");
        for(JFXButton i:taskGroup) {
            i.setStyle("");
        }
        transbut.setStyle(isSelected);
        mainView.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskGroup = new JFXButton[]{homebut, infobut, lovebut, searchbut, transbut, addbut, gamebut};
        home(new ActionEvent());
    }
}
