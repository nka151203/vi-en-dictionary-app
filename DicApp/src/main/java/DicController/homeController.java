package DicController;

import App.DicCommandLine.Word;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class homeController implements Initializable {

    @FXML
    private Pane quoteOfDay;
    @FXML
    private Label quoteLine;
    @FXML
    private Pane wordOfDay;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label helloView;
    @FXML
    private ImageView helloIMG;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Random random = new Random();
        int randomIndex = random.nextInt(0,App.dic.getWords().size());
        System.out.println(randomIndex);
        Word selectedWord = App.dic.getWords().get(randomIndex);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wordTemplate.fxml"));
        Pane view = null;
        try {
            view = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        wordController wc = loader.getController();
        wc.initialize(selectedWord.getWordTarget(),selectedWord.getPronunciation(),selectedWord.getWordExplain());
        Rectangle clip = new Rectangle (920, 500); // tạo một hình chữ nhật để làm clip
        view.setClip (clip); //cắt theo clip
        Scale scale = new Scale (0.7,0.7); // tạo một Scale transform với tỷ lệ 0.5
        scale.setPivotX (0); // thiết lập pivot point là (0, 0)
        scale.setPivotY (0);
        view.getTransforms ().add (scale);
        wordOfDay.getChildren().add(view);
        view.relocate (25, 0);

        // Random quote
        quoteLine.setText(App.dic.randomQuote());

        // Định dạng thời gian và ngày
        Date curr = new Date();
        SimpleDateFormat currHour = new SimpleDateFormat("HH");

        if(Integer.parseInt(currHour.format(curr)) >= 3 && Integer.parseInt(currHour.format(curr)) <12){
            try{
                helloView.setText("CHÀO BUỔI SÁNG !");
                Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/morning.png")));
                helloIMG.setImage(img);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        if(Integer.parseInt(currHour.format(curr)) >= 12 && Integer.parseInt(currHour.format(curr)) <18){
            try{
                helloView.setText("CHÀO BUỔI CHIỀU !");
                Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/afternoon.png")));
                helloIMG.setImage(img);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        if(Integer.parseInt(currHour.format(curr)) >= 18 || Integer.parseInt(currHour.format(curr)) <3){
            helloView.setText("CHÀO BUỔI TỐI !");
            try{
                Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon/night.png")));
                helloIMG.setImage(img);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Lấy thời gian hiện tại
            Date now = new Date();
            // Định dạng thời gian và ngày
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // Đặt giá trị cho hai label
            Platform.runLater(() -> {
                timeLabel.setText(timeFormat.format(now)+"");
                dateLabel.setText(dateFormat.format(now)+"");
            });
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
}
