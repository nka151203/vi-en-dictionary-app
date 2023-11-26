package DicAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThesaurusAPI {
    private static final String API_KEY = "Q0jzDwxiL3ulw/S8A2P94A==hHfjkN0GV2BBdcYb";
    private static final String API_URL = "https://api.api-ninjas.com/v1/thesaurus?word=";

    public static List<String> getThesaurusData(String word) throws IOException {
        List<String> resultList = new ArrayList<>();

        String apiUrl = API_URL + word;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", API_KEY);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            resultList.add(response.toString());
        } else {
            System.out.println("Error: " + connection.getResponseCode() + connection.getResponseMessage());
        }

        return resultList;
    }

    /**
     * Tách từ đồng nghĩa
     */
    public static List<String> extractSynonyms(String thesaurusResult) {
        List<String> synonymsList = new ArrayList<>();

        String startTag = "\"synonyms\":";
        String endTag = "\"antonyms\":";
        int startIndex = thesaurusResult.indexOf(startTag);
        int endIndex = thesaurusResult.indexOf(endTag);

        if (startIndex != -1 && endIndex != -1) {
            String synonymsSubstring = thesaurusResult.substring(startIndex + startTag.length(), endIndex);
            addWordsToList(synonymsList, synonymsSubstring);
        }

        return synonymsList;
    }

    /**
     * Tách từ trái nghĩa
     */
    public static List<String> extractAntonyms(String thesaurusResult) {
        List<String> antonymsList = new ArrayList<>();

        String startTag = "\"antonyms\":";
        int startIndex = thesaurusResult.indexOf(startTag);

        if (startIndex != -1) {
            String antonymsSubstring = thesaurusResult.substring(startIndex + startTag.length());
            addWordsToList(antonymsList, antonymsSubstring);
        }

        return antonymsList;
    }

    public static void addWordsToList(List<String> wordList, String words) {
        Pattern pattern = Pattern.compile("\"(\\w+)\"");
        Matcher matcher = pattern.matcher(words);

        while (matcher.find()) {
            wordList.add(matcher.group(1));
        }
    }

    public static void main(String[] args) throws IOException {
        String word = "envy";
        List<String> thesaurusData = getThesaurusData(word);

        for (String result : thesaurusData) {
            List<String> synonyms = extractSynonyms(result);
            System.out.println("Synonyms:");
            for (String synonym : synonyms) {
                System.out.println(synonym);
            }

            List<String> antonyms = extractAntonyms(result);
            System.out.println("Antonyms:");
            for (String antonym : antonyms) {
                System.out.println(antonym);
            }
        }
    }
}
