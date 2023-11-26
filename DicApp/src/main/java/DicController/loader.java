package DicController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class loader {
    private Pane view;
    public Pane getPane(String path) {
        try{
            URL fileUrl = App.class.getResource("/fxml/"+path+".fxml");
            if(fileUrl==null){
                throw new java.io.FileNotFoundException("FXML not found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
