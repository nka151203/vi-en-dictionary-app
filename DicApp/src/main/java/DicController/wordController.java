package DicController;

import DicAPI.ThesaurusAPI;
import DicAPI.textToSpeechGoogleAPI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class wordController {
    @FXML
    private JFXListView<String> antList;
    @FXML
    private JFXListView<String> synList;
    @FXML
    private JFXToggleButton loveBut;

    @FXML
    private JFXButton pronunBut;

    @FXML
    public Text wordKey;

    @FXML
    private WebView wordMeaning;

    @FXML
    public Text wordPronun;
    public static boolean hasWordDeleted = false;

    public void initialize(String wk,String wp,String wm)  {
        wordKey.setText(wk);
        if(wk.length()>=14) {
            wordKey.setFont(new Font(55));
        }
        wordPronun.setText(wp);
        int meanCount = 1;
        StringBuilder htmlContent = new StringBuilder("<html><body>");
        htmlContent.append("<style>");
        htmlContent.append("body { font-family: 'Segoe UI', sans-serif; font-size: 21px; }");
        htmlContent.append("b { font-family: 'Segoe UI', sans-serif; font-size: 21px; font-weight: bold; }");
        htmlContent.append("</style>");
        htmlContent.append("</head><body>");
        String[] lines = wm.split("\n");
        for (String line : lines) {
            if (line.startsWith("*")) {
                line = line.replace("*", meanCount++ +".");
                htmlContent.append("<b style=\"color: #FF457D;\">").append(line).append("</b><br>");
            } else {
                htmlContent.append(line).append("<br>");
            }
        }
        htmlContent.append("</body></html>");
        WebEngine webEngine = wordMeaning.getEngine();
        webEngine.loadContent(htmlContent.toString());
        pronunBut.setOnAction(event ->{
            try {
                textToSpeechGoogleAPI sound = new textToSpeechGoogleAPI();
                sound.speak(wk);
            } catch (Exception e) {
                System.out.println("The program does not support reading this word");
            }

        });
    }
    private static boolean isChild(Parent parent, Parent child) {
        return parent.getChildrenUnmodifiable().contains(child);
    }
    @FXML
    void editAction(ActionEvent event) {

    }

    @FXML
    void removeAction(ActionEvent event) {
        notification confirm = new notification();
        confirm.alertConfirm("Xóa từ","Bạn có chắc chắn xóa từ này khỏi từ diển không?","Xóa thành công"
                ,"Xóa không thành công");
        if(confirm.returnValue.equals("ok")) {
            App.dic.removeWord(wordKey.getText());
        }
    }
}
