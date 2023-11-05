package DicController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;

public class wordController {
    @FXML
    private JFXButton editBut;

    @FXML
    private JFXToggleButton loveBut;

    @FXML
    private JFXButton pronunBut;

    @FXML
    private JFXButton removeBut;

    @FXML
    public Text wordKey;

    @FXML
    public JFXTextArea wordMeaning;

    @FXML
    public Text wordPronun;

    public void initialize(String wk,String wp,String wm) {
        wordMeaning.setEditable(false);
        wordKey.setText(wk);
        if(wk.length()>=14) {
            wordKey.setFont(new Font(55));
        }
        wordPronun.setText(wp);
        wordMeaning.setText(wm);

    }
}
