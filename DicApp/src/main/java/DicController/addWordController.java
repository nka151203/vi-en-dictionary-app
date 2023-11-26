package DicController;

import App.DicCommandLine.Word;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TextField pronun;
    @FXML
    private TextField wordTarget;
    @FXML
    private ImageView isFilledMean;
    @FXML
    private ImageView isFilledPronun;
    @FXML
    private ImageView isFilledWord;
    ImageView [] warning;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warning = new ImageView[]{isFilledWord, isFilledPronun, isFilledMean};
        for (int i = 0;i < 3; i++) {
            warning[i].setVisible(false);
        }

        /** Delete 3 warning when you type any textField. */
        wordTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            warning[0].setVisible(false);
            wordTarget.setStyle("-fx-border-color: black");
            warning[1].setVisible(false);
            pronun.setStyle("-fx-border-color: black");
            warning[2].setVisible(false);
        });
        pronun.textProperty().addListener((observable, oldValue, newValue) -> {
            warning[0].setVisible(false);
            wordTarget.setStyle("-fx-border-color: black");
            warning[1].setVisible(false);
            pronun.setStyle("-fx-border-color: black");
            warning[2].setVisible(false);
        });
        meaning.textProperty().addListener((observable, oldValue, newValue) -> {
            warning[0].setVisible(false);
            wordTarget.setStyle("-fx-border-color: black");
            warning[1].setVisible(false);
            pronun.setStyle("-fx-border-color: black");
            warning[2].setVisible(false);
        });
    }
    @FXML
    void addCf(ActionEvent event) {
        /** Show warning . */
        if(wordTarget.getText().isEmpty()) {
            warning[0].setVisible(true);
            wordTarget.setStyle("-fx-border-color: red");
        } else {
            warning[0].setVisible(false);
            wordTarget.setStyle("-fx-border-color: black");
        }
        if(pronun.getText().isEmpty()) {
            warning[1].setVisible(true);
            pronun.setStyle("-fx-border-color: red");
        } else {
            warning[1].setVisible(false);
            pronun.setStyle("-fx-border-color: black");
        }
        if(meaning.getText().isEmpty()) {
            warning[2].setVisible(true);
        } else {
            warning[2].setVisible(false);
        }
        if (!wordTarget.getText().isEmpty() && !pronun.getText().isEmpty()
        && !meaning.getText().isEmpty()) {
            String wt = wordTarget.getText();
            String pr = "/" + pronun.getText() + "/";
            String we = meaning.getText();
            Word isExist = App.dic.dictionaryLookup(wt);
            notification notiAddWord = new notification();
            if(isExist != null) {
                notiAddWord.alertConfirm("Thêm từ vào từ điển","Từ "+wt+" đã có trong từ điển, Bạn có muốn ghi đè không","Bạn đã ghi đè thành công!"
                ,"Ghi đè không thành công");
                if(notiAddWord.returnValue.equals("ok")) {
                    App.dic.removeWord(wt);
                    App.dic.addWord(wt,pr,we,loveBut.isSelected());
                }
            } else {
                notiAddWord.alertConfirm("Thêm từ vào từ điển","Bạn có chắc chắn muốn thêm "+wt+" vào từ điển không ?", "Bạn đã thêm thành công"
                        ,"Thêm từ không thành công");
                if(notiAddWord.returnValue.equals("ok")) {
                    App.dic.removeWord(wt);
                    App.dic.addWord(wt,pr,we,loveBut.isSelected());
                }
            }
        }
    }
    @FXML
    void refreshCf(ActionEvent event) {
        wordTarget.clear();
        pronun.clear();
        meaning.clear();
    }
}
