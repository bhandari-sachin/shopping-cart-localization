package com.example.shoppingcart.console.localization.factory;

import java.util.Locale;

public class LocaleFactory {

    public static Locale getLocale(String lang) {
        return switch (lang) {
            case "fi" -> Locale.forLanguageTag("fi-FI");
            case "sv" -> Locale.forLanguageTag("sv-SE");
            case "ja" -> Locale.forLanguageTag("ja-JP");
            default -> Locale.forLanguageTag("en-US");
        };
    }
}