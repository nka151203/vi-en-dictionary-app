package DicController;

import DicAPI.TranslateGoogleAPI;
import DicAPI.langTolang;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class translateController implements Initializable {

    @FXML
    private ChoiceBox<String> langFrom;

    @FXML
    private ChoiceBox<String> langTo;

    @FXML
    private TextArea textFrom;

    @FXML
    private TextArea textTo;

    @FXML
    private JFXButton transButton;

    String [] languages  = {"Chinese (Simplified)", "Chinese (Traditional)", "Dutch", "English", "French",
        "German", "Italian", "Japanese", "Korean", "Portuguese (Brazilian & Continental)", "Russian", "Spanish","Tiếng Việt"};

    String choiceFrom;
    String choiceTo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langTolang langtolang = new langTolang();
        choiceFrom = "vi";
        choiceTo = "en";
        langFrom.getItems().addAll(languages);
        langTo.getItems().addAll(languages);
        langFrom.setValue("Tiếng Việt");
        langTo.setValue("English");
        textTo.setEditable(false);
        System.out.println(choiceFrom+" "+choiceTo);
        langFrom.setOnAction(event -> {
            textTo.clear();
            choiceFrom = langtolang.getLangValue(langFrom.getValue());
        });
        langTo.setOnAction(event -> {
            textTo.clear();
            choiceTo = langtolang.getLangValue(langTo.getValue());
            System.out.println(choiceTo);
        });
        transButton.setOnAction(event -> {
            textTo.setPromptText("Đang dịch...");
            try {
                textTo.setText(TranslateGoogleAPI.translate(choiceFrom,choiceTo,textFrom.getText()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//        textTo.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                try {
//                    textTo.setText(TranslateGoogleAPI.translate(choiceTo,choiceTo,textFrom.getText()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
