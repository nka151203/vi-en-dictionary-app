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

    public Word(String wordTarget, String wordExplain, String pronunciation) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.pronunciation = pronunciation;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getWord() {
        return getWordTarget() + " - " + getWordExplain();
    }
}
