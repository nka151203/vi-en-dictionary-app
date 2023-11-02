package DicController;

import App.DicCommandLine.DictionaryCommandline;
import App.DicCommandLine.DictionaryManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    protected static DictionaryManagement dic = new DictionaryManagement();

    @Override
    public void start(Stage stage) throws IOException {
        try{
            dic.insertFromFile();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/generalTaskbar.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
            stage.setTitle("TDA Dictionary");
            stage.setScene(scene);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/logo.png")));
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}