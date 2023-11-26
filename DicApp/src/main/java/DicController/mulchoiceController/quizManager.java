package DicController.mulchoiceController;
import DicGame.Quiz;
import com.jfoenix.controls.JFXButton;

public class quizManager {
    private Quiz quiz;
    JFXButton a;
    JFXButton b;
    JFXButton c;
    JFXButton d;
    JFXButton [] list;
    private boolean isAns = false;
    public String trueAns = "-fx-background-color:#F14FA1FF;";
    public String falseAns = "-fx-background-color:#029E39FF;";
    public quizManager(){

    }
    public quizManager(Quiz quiz){
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }
    public boolean isAns() {
        return isAns;
    }

    public void setAns(boolean ans) {
        isAns = ans;
    }

}
