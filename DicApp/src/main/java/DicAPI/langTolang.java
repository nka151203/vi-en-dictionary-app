package DicAPI;
import java.util.HashMap;
public class langTolang {
    public HashMap<String, String> languageMap = new HashMap<>();
    public HashMap<String, String> relanguageMap = new HashMap<>();

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
        relanguageMap.put("zh", "Chinese (Simplified)");
        relanguageMap.put("zh-Hant", "Chinese (Traditional)");
        relanguageMap.put("nl", "Dutch");
        relanguageMap.put("en", "English");
        relanguageMap.put("fr", "French");
        relanguageMap.put("de", "German");
        relanguageMap.put("it", "Italian");
        relanguageMap.put("ja", "Japanese");
        relanguageMap.put("ko", "Korean");
        relanguageMap.put("pt", "Portuguese (Brazilian & Continental)");
        relanguageMap.put("ru", "Russian");
        relanguageMap.put("es", "Spanish");
        relanguageMap.put("vi", "Tiếng Việt");
    }
    public String getLangValue(String langKey) {
        return languageMap.get(langKey);
    }
    public String getLangKey(String langValue) {return relanguageMap.get(langValue);}
}





