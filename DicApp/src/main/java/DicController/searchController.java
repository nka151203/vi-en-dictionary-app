package DicController;

import App.DicCommandLine.Word;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchController implements Initializable {

    @FXML
    private BorderPane searchPane;

    @FXML
    private JFXListView<String> listWord;

    @FXML
    private TextField searchBox;
    @FXML
    private Label needWord;

    wordController setWordTemplate = new wordController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //App.dic.insertFromFile();
        listWord.getItems().addAll(App.dic.trie.searchWordsWithPrefix(""));

        //show selected word by type
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != oldValue){
                    listWord.getItems().clear();
                    listWord.getItems().addAll(App.dic.trie.searchWordsWithPrefix(newValue));
                }
            }
        });

        //show selected word in list
        listWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("You choice");
                Word selectedWord = App.dic.dictionaryLookup(newValue);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wordTemplate.fxml"));
                Pane view = null;
                try {
                    view = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                wordController wc = loader.getController();
                wc.initialize(selectedWord.getWordTarget(),selectedWord.getPronunciation(),selectedWord.getWordExplain());
                searchPane.setCenter(view);
            }
        });
    }
}
