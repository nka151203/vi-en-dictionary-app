package Trie;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insertWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode child = current.getChildren().get(c);
            if (child == null) {
                child = new TrieNode();
                current.getChildren().put(c, child);
            }
            current = child;
        }
        current.setEndOfWord(true);
        current.setWord(word);
    }

    public List<String> searchWordsWithPrefix(String prefix) {
        TrieNode prefixNode = findPrefixNode(prefix);
        List<String> matchedWords = new ArrayList<>();
        if (prefixNode != null) {
            collectWords(prefixNode, matchedWords);
        }
        return matchedWords;
    }

    private TrieNode findPrefixNode(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            TrieNode child = current.getChildren().get(c);
            if (child == null) {
                return null;
            }
            current = child;
        }
        return current;
    }

    private void collectWords(TrieNode node, List<String> matchedWords) {
        if (node.isEndOfWord()) {
            matchedWords.add(node.getWord());
        }
        for (TrieNode child : node.getChildren().values()) {
            collectWords(child, matchedWords);
        }
    }

    private static class TrieNode {
        private boolean endOfWord;
        private String word;
        private final TrieNodeMap children;

        public TrieNode() {
            endOfWord = false;
            word = null;
            children = new TrieNodeMap();
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }

        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public TrieNodeMap getChildren() {
            return children;
        }
    }

    private static class TrieNodeMap extends HashMap<Character, TrieNode> {
    }
}