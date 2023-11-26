package DicController.mulchoiceController;

import DicController.loader;
import DicController.notification;
import DicGame.MultipleChoices;
import DicGame.Player;
import DicGame.Quiz;
import DicGame.playerManagement;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class mulchoiceController implements Initializable {

    @FXML
    private BorderPane mulPlaying;
    @FXML
    private JFXButton currQuizNumber;
    @FXML
    private JFXButton a, b, c, d;
    @FXML
    private Label falseCurr;
    @FXML
    private JFXButton one, two, three, four, five, six, seven, eight, nine, ten;
    @FXML
    private Label question;
    @FXML
    private Label timeLabel;
    @FXML
    private Label trueCurr;

    @FXML
    private Label point;

    private JFXButton [] listQuizNumber;
    private JFXButton [] listAns;


    private int level;
    private String name;
    private long startTime = 0;
    private Timeline timeline;
    private long stopTime;

    public String isSelected = "-fx-border-color: white; -fx-border-width: 3px;";
    public String unSelected = "-fx-border-color: white; -fx-border-width: 1px;";
    public String falseAns = "-fx-background-color:#F14FA1FF;";
    public String trueAns = "-fx-background-color:#029E39FF;";

    public long secondPlaying;

    public boolean dataSetted = false;
    ArrayList<quizManager> listQuiz;
    MultipleChoices runQuiz;
    private void updateTime() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (startTime != 0) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = (currentTime - startTime) / 1000;
                secondPlaying = elapsedTime;
                updateDisplay(elapsedTime);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void updateDisplay(long elapsedTime) {
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        Platform.runLater(() -> {
            timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
    }
    private void stopTime() {
        if (timeline != null) {
            stopTime = System.currentTimeMillis();
            timeline.stop();
        }
    }
    @FXML
    void endGame(ActionEvent event) {
        stopTime();
        Player newPlayer;
        if (level == 1){
            System.out.println(level);
            System.out.println(name);
            newPlayer = new Player(name, "hard", Integer.parseInt(point.getText()), (int)secondPlaying);
        } else {
            newPlayer = new Player(name, "easy", Integer.parseInt(point.getText()), (int)secondPlaying);
        }
        newPlayer.writeToFile();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kết thúc trò chơi");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn dừng lại tại đây không? Khi kết thúc, kết quả sẽ được ghi lại.");

        // Thiết lập các nút trong hộp thoại
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Hiển thị hộp thoại và đợi người dùng phản hồi
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra xem người dùng đã chọn OK hay Cancel
        if (result.isPresent() && result.get() == okButton) {
            // Người dùng chọn OK
            alert.setContentText("Kết thúc thành công");
            Pane view = new Pane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MultipleChoice/mulchoiceendGame.fxml"));
            try{
                view = loader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
            mulPlaying.setCenter(view);
        } else {
            // Người dùng chọn Cancel
            alert.setContentText("Hãy tiếp tục. Keep going !!!");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listQuizNumber = new JFXButton[]{one, two, three, four, five, six, seven, eight, nine, ten};
        listAns = new JFXButton[]{a,b,c,d};
        if(dataSetted){
            startTime = System.currentTimeMillis();
            updateTime();
            playGame(level);
        }
    }
    public void playGame(int level) {
        ArrayList<Integer> infor = new ArrayList<Integer>();
        //0 : yourPoint
        //1:  plusPoint
        //2 : correct sentence number
        //3: incorrect sentence number
        for(int i=0;i<4;i++){
            infor.add(0);
        }
        if(level == 1){
            infor.set(1,2);
        } else {
            infor.set(1,1);
        }
        listQuiz = new ArrayList<>();
        runQuiz = new MultipleChoices();
        runQuiz.insertFromFile();
        ArrayList<Integer> answerSheet = new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            answerSheet.add(0);
        }
        System.out.println(this.level);
        for(int i =0;i<10;i++) {
            Quiz add = runQuiz.randomQuiz(this.level);
            listQuiz.add(new quizManager(add));
        }
        listQuizNumber[0].fire();
        for(int k =0; k<10; k++) {
            int finalK = k;
            //go to each question
            listQuizNumber[k].setOnAction(event -> {
                currQuizNumber.setText((finalK+1)+"");
                //question number is seleted
                listQuizNumber[finalK].setStyle(isSelected);
                for(int o = 0;o<10;o++){
                    if(o != finalK){
                        listQuizNumber[o].setStyle(unSelected);
                    }
                    if(answerSheet.get(o) == 1){
                        listQuizNumber[o].setStyle(trueAns);
                    } else if (answerSheet.get(o) == -1){
                        listQuizNumber[o].setStyle(falseAns);
                    }
                }
                //get question
                question.setText(listQuiz.get(finalK).getQuiz().getQuestion());
                //get answer
                for (int i = 0; i < 4; i++) {
                    listAns[i].setText(listQuiz.get(finalK).getQuiz().getanswerList().get(i));
                }
                // check question which is answered
                if (listQuiz.get(finalK).isAns()) {
                    if(answerSheet.get(finalK) == 1){
                        listQuizNumber[finalK].setStyle(trueAns);
                    } else if (answerSheet.get(finalK) == -1){
                        listQuizNumber[finalK].setStyle(falseAns);
                    }
                    ///see key after answer
                    for (int i = 0; i < 4; i++){
                        if(listAns[i].getText().equals(listQuiz.get(finalK).getQuiz().getFullAnswer())){
                            listAns[i].setStyle(trueAns);
                        } else {
                            listAns[i].setStyle(falseAns);
                        }
                    }
                    ///disable question which is answered
                    for (int i = 0; i < 4; i++) {
                        listAns[i].setDisable(true);
                    }
                } else {
                    for (int i = 0; i < 4; i++){
                        listAns[i].setStyle("");
                    }
                    for (int o = 0; o < 4; o++) {
                        listAns[o].setDisable(false);
                    }
                    AtomicBoolean isWrong = new AtomicBoolean(true);
                    for (int i = 0; i < 4; i++) {
                        int finalI = i;
                        // check every answer
                        listAns[i].setOnAction(event1 -> {
                            //true choice
                            //0 : yourPoint
                            //1:  plusPoint
                            //2 : correct sentence number
                            //3: incorrect sentence number
                            if (listAns[finalI].getText().equals(listQuiz.get(finalK).getQuiz().getFullAnswer())) {
                                listQuizNumber[finalK].setStyle(trueAns);
                                isWrong.set(false);
                                answerSheet.set(finalK, 1);
                                infor.set(2,infor.get(2) + 1);
                                infor.set(0,infor.get(0) + infor.get(1));
                                point.setText(infor.get(0)+"");
                                trueCurr.setText(infor.get(2)+"");
                                for (int o = 0; o < 4; o++) {
                                    listAns[o].setDisable(true);
                                }
                            } else { //false choice
                                listQuizNumber[finalK].setStyle(falseAns);
                                answerSheet.set(finalK, -1);
                                infor.set(3,infor.get(3) + 1);
                                point.setText(infor.get(0)+"");
                                falseCurr.setText(infor.get(3)+"");
                                for (int o = 0; o < 4; o++) {
                                    listAns[o].setDisable(true);
                                }
                            }
                            listQuiz.get(finalK).setAns(true);
                            for (int j = 0; j < 4; j++){
                                if(listAns[j].getText().equals(listQuiz.get(finalK).getQuiz().getFullAnswer())){
                                    listAns[j].setStyle(trueAns);
                                } else {
                                    listAns[j].setStyle(falseAns);
                                }
                            }
                            if(isWrong.get()){
                                listQuizNumber[finalK].setStyle(falseAns);
                            } else {
                                listQuizNumber[finalK].setStyle(trueAns);
                            }
                        });
                    }
                }
            });
        }
    }
    public void setLevel(int level, String name){
        this.level = level;
        this.name = name;
        dataSetted = true;
    }
}
