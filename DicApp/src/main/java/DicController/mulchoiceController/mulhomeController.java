package DicController.mulchoiceController;

import DicController.loader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class mulhomeController {

    @FXML
    private JFXButton cf;

    @FXML
    private ToggleGroup level;

    @FXML
    private BorderPane mulHome;

    @FXML
    private TextField playerName;

    String selectedOption ="";
    String selectedName ="";
    boolean flagLevel=false;
    boolean flagName=false;
    @FXML
    void initialize() {
        cf.setVisible(false);
        level.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (level.getSelectedToggle() != null) {
                JFXRadioButton selectedRadioButton = (JFXRadioButton) level.getSelectedToggle();
                selectedOption = selectedRadioButton.getText();
                flagLevel = true;
            }
            if(!selectedName.isEmpty()) {
                flagName = true;
            } else {
                flagName = false;
            }
            if(flagName && flagLevel) {
                cf.setVisible(true);
            } else {
                cf.setVisible(false);
            }
        });
        playerName.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedName = newValue;
            if(!newValue.isEmpty()) {
                flagName = true;
            } else {
                flagName = false;
            }
            if(flagName && flagLevel) {
                cf.setVisible(true);
            }else {
                cf.setVisible(false);
            }
        });
    }
    @FXML
    void start(ActionEvent event) {
        Pane view = new Pane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MultipleChoice/mulchoice.fxml"));
        try{
            view = loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
        mulchoiceController controller = loader.getController();
        if(controller == null) {
            System.out.println("controller null");
        } else {
            if(selectedOption.equals("Hard")) {
                controller.setLevel(1, selectedName);
                System.out.println("Kho");
            } else {
                controller.setLevel(0,selectedName);
                System.out.println("De");
            }
        }
        if(controller.dataSetted) {
            controller.initialize(null, null);
        }
        mulHome.setCenter(view);
    }
}
