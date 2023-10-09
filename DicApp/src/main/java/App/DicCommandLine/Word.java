package App.DicCommandLine;

public class Word {

    /**
     * English vocab.
     */
    private String wordTarget;

    /**
     * Vietnamese interpretation.
     */
    private String wordExplain;

    public Word() {
        this.wordExplain = "";
        this.wordTarget = "";
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        return wordTarget;
    }
}
