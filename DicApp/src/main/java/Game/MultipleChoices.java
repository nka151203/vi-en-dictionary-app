package Game;

import App.DicCommandLine.Word;
import App.DicCommandLine.DictionaryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MultipleChoices {
    private DictionaryManagement dictionary;
    private List<Word> words;
    private Random random;
    private Scanner scanner;
    private int score;
    private static final int GAME_DURATION = 120;

    public MultipleChoices() {
        dictionary = new DictionaryManagement();
        dictionary.insertFromFile();
        words = dictionary.getWords();
        random = new Random();
        scanner = new Scanner(System.in);
        score = 0;
    }
}

