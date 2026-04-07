package com.example.shoppingcart.gui.localization.db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    public Map<String, String> getStrings(String language) {
        // Fallback strings in case database is unavailable
        Map<String, String> fallbackStrings = getFallbackStrings(language);

        if (!DatabaseConnection.isDatabaseAvailable()) {
            System.out.println("Database unavailable, using fallback strings for: " + language);
            return fallbackStrings;
        }

        Map<String, String> map = new HashMap<>();
        String sql = """
            SELECT k.`key`, v.value
            FROM localization_keys k
            JOIN localization_values v ON v.key_id = k.id
            WHERE v.language_code = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, language);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("key"), rs.getString("value"));
            }

            // If database returned nothing, use fallback
            if (map.isEmpty()) {
                System.out.println("No translations found for language: " + language + ", using fallback");
                return fallbackStrings;
            }

            return map;

        } catch (SQLException e) {
            System.err.println("LocalizationService error: " + e.getMessage());
            System.out.println("Using fallback strings due to database error");
            return fallbackStrings;
        }
    }

    private Map<String, String> getFallbackStrings(String language) {
        Map<String, String> map = new HashMap<>();

        switch (language) {
            case "fi":
                map.put("select.language", "Valitse kieli:");
                map.put("prompt.num.items", "Syötä ostettavien tuotteiden määrä:");
                map.put("btn.generate.items", "Luo tuotteet");
                map.put("btn.calculate.total", "Laske yhteensä");
                map.put("total.cost", "Kokonaishinta:");
                map.put("prompt.price", "Hinta:");
                map.put("prompt.quantity", "Määrä:");
                map.put("item.prompt", "Tuote");
                map.put("error.invalid.number", "Virheellinen numero");
                map.put("error.positive.number", "Syötä positiivinen luku");
                break;
            case "sv":
                map.put("select.language", "Välj språk:");
                map.put("prompt.num.items", "Ange antal artiklar:");
                map.put("btn.generate.items", "Generera artiklar");
                map.put("btn.calculate.total", "Beräkna totalt");
                map.put("total.cost", "Totalkostnad:");
                map.put("prompt.price", "Pris:");
                map.put("prompt.quantity", "Antal:");
                map.put("item.prompt", "Artikel");
                map.put("error.invalid.number", "Ogiltigt nummer");
                map.put("error.positive.number", "Ange ett positivt tal");
                break;
            case "ja":
                map.put("select.language", "言語を選択:");
                map.put("prompt.num.items", "アイテム数を入力:");
                map.put("btn.generate.items", "アイテム生成");
                map.put("btn.calculate.total", "合計計算");
                map.put("total.cost", "合計金額:");
                map.put("prompt.price", "価格:");
                map.put("prompt.quantity", "数量:");
                map.put("item.prompt", "アイテム");
                map.put("error.invalid.number", "無効な数値");
                map.put("error.positive.number", "正の数を入力");
                break;
            case "ar":
                map.put("select.language", "اختر اللغة:");
                map.put("prompt.num.items", "أدخل عدد العناصر:");
                map.put("btn.generate.items", "إنشاء عناصر");
                map.put("btn.calculate.total", "حساب المجموع");
                map.put("total.cost", "التكلفة الإجمالية:");
                map.put("prompt.price", "السعر:");
                map.put("prompt.quantity", "الكمية:");
                map.put("item.prompt", "عنصر");
                map.put("error.invalid.number", "رقم غير صالح");
                map.put("error.positive.number", "يرجى إدخال رقم موجب");
                break;
            default: // English
                map.put("select.language", "Select Language:");
                map.put("prompt.num.items", "Enter number of items:");
                map.put("btn.generate.items", "Generate Items");
                map.put("btn.calculate.total", "Calculate Total");
                map.put("total.cost", "Total cost:");
                map.put("prompt.price", "Price:");
                map.put("prompt.quantity", "Quantity:");
                map.put("item.prompt", "Item");
                map.put("error.invalid.number", "Invalid number format");
                map.put("error.positive.number", "Please enter a positive number");
                break;
        }

        return map;
    }
}