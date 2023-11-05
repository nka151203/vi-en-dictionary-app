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

    private boolean interetedWord;

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
        interetedWord = false;
    }

    public Word(String wordTarget, String pronunciation, String wordExplain, boolean interetedWord) {
        this.wordTarget = wordTarget;
        this.pronunciation = pronunciation;
        this.wordExplain = wordExplain;
        this.interetedWord = interetedWord;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        if (wordTarget != null) {
            return wordTarget;
        } else return "";
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

    public boolean getInteretedWord() {
        return interetedWord;
    }

    public void setInteretedWord(boolean interetedWord) {
        this.interetedWord = interetedWord;
    }

    public String getWord() {
        return getWordTarget() + " - " + getWordExplain();
    }
}
