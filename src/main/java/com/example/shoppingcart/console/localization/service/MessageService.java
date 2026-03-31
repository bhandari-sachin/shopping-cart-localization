package com.example.shoppingcart.console.localization.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageService {

    private static ResourceBundle bundle;

    public static void init(Locale locale) {
        // Use UTF8Control to read properties files in UTF-8
        bundle = ResourceBundle.getBundle("MessagesBundle", locale, new UTF8Control());
    }

    public static String get(String key) {
        if (bundle == null) {
            throw new IllegalStateException("MessageService not initialized. Call init(locale) first.");
        }
        return bundle.getString(key);
    }
}