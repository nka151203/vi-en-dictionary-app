package DicGame;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String question;
    private String fullAnswer;
    private String key;

    private List<String> answerList = new ArrayList<>();
    public Quiz() {}

    public Quiz(String q, String f, String k, List<String> quesList) {
        this.question = q;
        this.fullAnswer = f;
        this.key = k;
        this.answerList = quesList;
    }

    public String getQuestion() {
        return question;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }

    public String getKey() {
        return key;
    }
    public List<String> getanswerList() {
        return answerList;
    }

    public void printfAnswer() {
        if (!answerList.isEmpty()) {
            System.out.println(answerList.get(0));
            System.out.println(answerList.get(1));
            System.out.println(answerList.get(2));
            System.out.println(answerList.get(3));
        }
    }

}
