package DicController;
import javafx.scene.control.Alert;

public class notification {
    public notification(){
        returnValue ="";
    }
    Alert alert;
    public String returnValue;
    public void alertInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public String alertConfirm(String title, String content, String ifOK, String ifCancel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                notification child = new notification();
                child.alertInformation(title, ifOK);
                returnValue = "ok";
            } else {
                notification child = new notification();
                child.alertInformation(title, ifCancel);
                returnValue = "cancel";
            }
        });
        return returnValue;
    }
}
