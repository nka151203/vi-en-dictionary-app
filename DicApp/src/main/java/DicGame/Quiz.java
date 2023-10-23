package DicGame;

public class Quiz {
    private String question;
    private String fullAnswer;
    private String key;

    public Quiz(String q, String f, String k) {
        this.question = q;
        this.fullAnswer = f;
        this.key = k;
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
}
