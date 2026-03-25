package com.sport_events.api.presentation.util;

public class LanguageUtils {

    private LanguageUtils() {
    }

    public static String normalizeLanguage(String acceptLanguage) {
        return acceptLanguage.split(",")[0].split(";")[0].split("-")[0].trim().toLowerCase();
    }
}
