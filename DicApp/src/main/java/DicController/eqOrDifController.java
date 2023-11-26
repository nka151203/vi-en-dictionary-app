package DicController;

import DicAPI.ThesaurusAPI;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class eqOrDifController implements Initializable {

    @FXML
    private JFXListView<String> ant;

    @FXML
    private Label antLabel;

    @FXML
    private JFXListView<String> syn;

    @FXML
    private Label synLabel;

    @FXML
    private Label warning;

    @FXML
    private TextField wordBox;

    @FXML
    private Label wordKey;

    @FXML
    private Label wordKey1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warning.setVisible(false);
        syn.setVisible(false);;
        ant.setVisible(false);
        wordKey.setVisible(false);
        wordKey1.setVisible(false);
        synLabel.setVisible(false);
        antLabel.setVisible(false);

    }
    @FXML
    void searchThes(ActionEvent event) {
        List<String> synonyms = new ArrayList<>();
        List<String> antonyms = new ArrayList<>();
        List<String> thesaurusData= new ArrayList<>();
        try{
            thesaurusData = ThesaurusAPI.getThesaurusData(wordBox.getText());
        } catch (Exception e) {
            e.getMessage();
        }

        for (String result : thesaurusData) {
            synonyms = ThesaurusAPI.extractSynonyms(result);
            antonyms = ThesaurusAPI.extractAntonyms(result);
        }
        if(synonyms.isEmpty() && antonyms.isEmpty()) {
            warning.setVisible(true);
        } else {
            warning.setVisible(false);
            wordKey.setText(wordBox.getText());
            wordKey1.setText(wordBox.getText());
            synLabel.setVisible(true);
            antLabel.setVisible(true);
            wordKey.setVisible(true);
            wordKey1.setVisible(true);
            syn.getItems().addAll(synonyms);
            ant.getItems().addAll(antonyms);
            syn.setVisible(true);;
            ant.setVisible(true);
        }
    }


}
