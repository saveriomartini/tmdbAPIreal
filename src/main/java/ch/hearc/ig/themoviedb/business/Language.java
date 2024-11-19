package ch.hearc.ig.themoviedb.business;

public class Language implements TmdbItem {
    private int id;
    private String code;

    public Language() {
    }

    public Language(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String getLanguageName(String code) {
        switch (code) {
            case "en": return "English";
            case "fr": return "French";
            case "es": return "Spanish";
            case "de": return "German";
            case "it": return "Italian";
            case "ja": return "Japanese";
            case "ko": return "Korean";
            case "pt": return "Portuguese";
            case "ru": return "Russian";
            case "zh": return "Chinese";

            // Add more mappings as needed
            default: return "Unknown";
        }
    }


    @Override
    public String toString() {
        return getLanguageName(code);
    }
}
