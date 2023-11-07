package DicController;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class addWordController implements Initializable {
    @FXML
    private JFXToggleButton loveBut;

    @FXML
    private TextArea meaning;

    @FXML
    private Line pLine;

    @FXML
    private TextField pronun;

    @FXML
    private Line wLine;

    @FXML
    private TextField wordTarget;
    @FXML
    private ImageView isFilledMean;

    @FXML
    private ImageView isFilledPronun;

    @FXML
    private ImageView isFilledWord;
    boolean wordFocus =false;
    boolean pronFocus =false;
    boolean meanFocus =false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView warningFill[]={isFilledWord, isFilledPronun, isFilledMean};
        for(int i=0;i<3;i++){
            warningFill[i].setVisible(false);
        }
        wordTarget.setText("");
        pronun.setText("");
        meaning.setText("");

        wordTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ok");
            if(oldValue.isEmpty()){
                warningFill[0].setVisible(true);
                wLine.setStroke(Color.RED);
            }
            if(!newValue.isEmpty()){
                warningFill[0].setVisible(false);
                wLine.setStroke(Color.BLACK);
            } else{
                warningFill[0].setVisible(true);
                wLine.setStroke(Color.RED);
            }
        });


        if(meanFocus && meaning.getText().isEmpty()){
            warningFill[1].setVisible(true);
            meaning.setStyle("-fx-background-color: rgba(255,0,0,0.44);");
        }
    }

    @FXML
    void addCf(ActionEvent event) {

    }

    @FXML
    void refreshCf(ActionEvent event) {

    }
}
