package DicAPI;
import java.util.HashMap;
public class langTolang {
    public HashMap<String, String> languageMap = new HashMap<>();

    public langTolang() {
        languageMap.put("Chinese (Simplified)", "zh");
        languageMap.put("Chinese (Traditional)", "zh-Hant");
        languageMap.put("Dutch", "nl");
        languageMap.put("English", "en");
        languageMap.put("French", "fr");
        languageMap.put("German", "de");
        languageMap.put("Italian", "it");
        languageMap.put("Japanese", "ja");
        languageMap.put("Korean", "ko");
        languageMap.put("Portuguese (Brazilian & Continental)", "pt");
        languageMap.put("Russian", "ru");
        languageMap.put("Spanish", "es");
        languageMap.put("Tiếng Việt", "vi");
    }

    public String getLangValue(String langKey) {
        return languageMap.get(langKey);
    }
}





