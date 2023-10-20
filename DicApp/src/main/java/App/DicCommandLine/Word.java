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

    /**
     * Pronunciation of word.
     */
    private String pronunciation;

    public Word() {
        this.wordExplain = "";
        this.wordTarget = "";
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public Word(String wordTarget, String pronunciation, String wordExplain) {
        this.wordTarget = wordTarget;
        this.pronunciation = pronunciation;
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getWordExplain() {
        return wordExplain;
    }


    public String getWord() {
        return getWordTarget() + " - " + getWordExplain();
    }
}
