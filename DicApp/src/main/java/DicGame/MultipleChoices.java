package DicGame;

import App.DicCommandLine.Word;
import App.DicCommandLine.DictionaryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoices {
    private List<Quiz> quizList;
    private Random random;
    private Scanner scanner;
    private int score;
    private static final int GAME_DURATION = 30000;

    public MultipleChoices() {
        quizList = new ArrayList<>();
    }
}

