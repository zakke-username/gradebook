package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static LocaleManager instance;
    private Locale currentLocale = new Locale("fi", "FI");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", currentLocale);

    private LocaleManager() {
    }

    public static LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    public void setLocale(Locale locale) {
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public String getString(String str) {
        return resourceBundle.getString(str);
    }
}
