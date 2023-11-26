package App.DicCommandLine;

import Trie.Trie;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public Trie trie;

    public DictionaryManagement() {
        trie = new Trie();
    }

    public List<String> listInterestedWord = new ArrayList<>();

    public static int lookupedWord=0;
    public static int contributedWord=0;

    private final int indexSepate = 58105;

    /**
     * Add new words from cmd.
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWords = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numWords; i++) {
            System.out.println("Word #" + (i + 1));
            System.out.print("Enter the English word: ");
            String englishWord = scanner.nextLine();
            System.out.print("Enter the Vietnamese meaning: ");
            String vietnameseMeaning = scanner.nextLine();

            Word word = new Word(englishWord, vietnameseMeaning);
            addWord(word);
        }
    }

    /**
     * Add new words from file.
     */
    public void insertFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\dictionary.txt"))) {
            String line;
            String word = "" ;
            String pronunciation = "" ;
            boolean interested = false;
            String meaning = "" ;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@") && line.contains("/")) {

                    String[] parts = line.split("/");
                    String wordInfo = parts[0];
                    interested = parts.length > 2 && parts[2].trim().equals("1");

                    word = parts[0].substring(1,line.indexOf('/')-1);
                    pronunciation = "/" + parts[1] + "/";

                } else if (!word.isEmpty()) {
                    meaning += line;
                    meaning += '\n';
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        meaning += line;
                        meaning += '\n';
                    }

                    addWord(new Word(word, pronunciation, meaning, interested));
                    if (interested) listInterestedWord.add(word);
                    trie.insertWord(word);
                    meaning = "";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Export current dictionary data to file.
     */
    public void dictionaryExportToFile() {
        Scanner sc = new Scanner(System.in);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\resources\\Database\\dictionary.txt",false))) {
            List<Word> words = getWords();
            writer.write("-----" + "\n");
            for (Word word : words) {
                int check = 0;
                if (word.getInteretedWord()) {
                    check = 1;
                }
                writer.write("@" + word.getWordTarget() + " " + word.getPronunciation() + ' ' + 0 + "\n" + word.getWordExplain() + "\n");
            }

        } catch (Exception e) {
            System.out.println("Error exporting dictionary.");
            e.printStackTrace();
        }
    }

    /**
     * add new word
     * @String 3 attribute of word.
     */
    public void addWord(String wordTarget, String pronunciation, String wordExplain, boolean interested) {

        Word word = new Word(wordTarget, pronunciation, wordExplain, interested);
        addWord(word);
        trie.insertWord(wordTarget);
        contributedWord ++;

        System.out.println("Word added successfully.");
    }

    /**
     * Remove word.
     * @String key word
     */
    public void removeWord(String wordTarget) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words, wordTarget);

        if (neededWord != null) {
            removeWord(neededWord);
            trie.deleteWord(wordTarget);

            System.out.println("Word removed successfully.");
            return;
        }

        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");

    }

    /**
     * Update word.
     * @String key word
     */
    public void updateWord(String wordTarget, String newWordTarget, String newPronunciation, String newWordExplain) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words, wordTarget);

        if (neededWord != null) {

            neededWord.setWordTarget(newWordTarget);
            neededWord.setPronunciation(newPronunciation);
            neededWord.setWordExplain(newWordExplain);

            trie.insertWord(newWordTarget);
            trie.deleteWord(wordTarget);

            System.out.println("Word updated successfully.");
            return;
        }

        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");

    }

    /**
     * Display a list of words.
     * @String key word
     */
    public void displayAllWords() {
        List<Word> words = getWords();
        int index = 1;
        for (Word word : words) {
            System.out.println(index + ". " + word.getWordTarget());
            System.out.println(word.getPronunciation());
            System.out.println(word.getWordExplain());
            index ++;
        }
    }

    /**
     * Find English word.
     * @String key word.
     * @return object word.
     */
    public Word dictionaryLookup(String wordTarget) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words, wordTarget);

        if (neededWord != null) {
            System.out.println("Word: " + neededWord.getWordTarget());
            System.out.println("Pronunciation: " + neededWord.getPronunciation());
            System.out.println("Meaning: " + neededWord.getWordExplain());

            lookupedWord ++;

            return new Word(neededWord.getWordTarget(),neededWord.getPronunciation(),neededWord.getWordExplain());
        }
        System.out.println("Word \"" + wordTarget + "\" not found in the dictionary.");
        return null;
    }

    /**
     * Search words with prefix.
     */
    public void dictionarySearcher(String prefix) {
        List<String> matchedWords = trie.searchWordsWithPrefix(prefix);
        Collections.sort(matchedWords);

        int count = 0;

        if (matchedWords.isEmpty()) {
            System.out.println("No words found.");
        } else {
            System.out.println("Words starting with \"" + prefix + "\":");
            for (String word : matchedWords) {
                count ++;
                System.out.println(count + ". " + word);
                if (count == 20 || count > matchedWords.size()) break;
            }
        }
    }

    /**
     * change status interestedWord.
     */
    public void checkInterestedWord(String wordTarget, boolean interested) {
        List<Word> words = getWords();

        Word neededWord = binarySearchWord(words, wordTarget);

        if (neededWord != null) {
            if (neededWord.getInteretedWord() != interested) {
                neededWord.setInteretedWord(interested);

                if (interested) {
                    listInterestedWord.add(wordTarget);
                } else listInterestedWord.remove(wordTarget);
            }
        }
    }

    /**
     * show my list interested word.
     */
    public void showListInterestedWord() {
        int index = 1;
        for (String word : listInterestedWord) {
            System.out.println(index + ". " + word);
            index ++;
        }
    }

    /**
     * Search word with String.
     */
    private Word binarySearchWord(List<Word> words, String target) {
        int left = 0;
        int right = indexSepate - 1;

        if (target == null) return null;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Word word = words.get(mid);
            String wordTarget = word.getWordTarget();

            int compareResult = wordTarget.compareToIgnoreCase(target);
            if (compareResult == 0) {
                return word;
            } else if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        for (int i = indexSepate; i < words.size(); i++) {
            System.out.println("Word doesn't exist in primary dictionary");
            Word word = words.get(i);
            if (word.getWordTarget().equals(target)) {
                return word;
            }
        }

        return null;
    }

    /**
     * read lookupedWord and contributedWord from file
     */
    public void readNumberWord() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\numberWord.txt"))) {
            String line;

            if ((line = reader.readLine()) != null) {
                try {
                    String[] numbers = line.trim().split("\\s+");
                    if (numbers.length == 2) {
                        lookupedWord = Integer.parseInt(numbers[0]);
                        contributedWord = Integer.parseInt(numbers[1]);
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write lookupedWord and contributedWord to file
     */
    public void writeNumberWord() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DicApp\\src\\main\\resources\\Database\\numberWord.txt"))) {
            writer.write(lookupedWord + " " + contributedWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Random Quote from file
     */
    public String randomQuote() {
        List<String> quoteList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("DicApp\\src\\main\\resources\\Database\\Quote.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                quoteList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!quoteList.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(quoteList.size());
            return quoteList.get(index);
        } else {
            return "No quotes available.";
        }
    }
}


