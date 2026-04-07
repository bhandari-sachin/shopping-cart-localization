package com.example.shoppingcart.gui.localization.db;

import com.example.shoppingcart.gui.localization.model.CartItem;

import java.sql.*;
import java.util.List;

public class CartService {

    public void saveCart(int totalItems, double totalCost,
                         String language, List<CartItem> items) {

        if (!DatabaseConnection.isDatabaseAvailable()) {
            System.out.println("=== CART SUMMARY (Database Unavailable - Not Saved) ===");
            printCartSummary(totalItems, totalCost, language, items);
            return;
        }

        String cartSql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(cartSql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, totalItems);
            ps.setDouble(2, totalCost);
            ps.setString(3, language);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int cartId = keys.getInt(1);
                saveCartItems(conn, cartId, items);
                System.out.println("✓ Cart saved to database with ID: " + cartId);
            }

        } catch (SQLException e) {
            System.err.println("CartService error: " + e.getMessage());
            System.out.println("Failed to save to database, printing summary instead:");
            printCartSummary(totalItems, totalCost, language, items);
        }
    }

    private void saveCartItems(Connection conn, int cartId, List<CartItem> items) throws SQLException {
        String sql = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                double subtotal = item.getPrice() * item.getQuantity();

                ps.setInt(1, cartId);
                ps.setInt(2, i + 1);
                ps.setDouble(3, item.getPrice());
                ps.setInt(4, item.getQuantity());
                ps.setDouble(5, subtotal);
                ps.executeUpdate();
            }
        }
    }

    private void printCartSummary(int totalItems, double totalCost, String language, List<CartItem> items) {
        System.out.println("========================================");
        System.out.println("SHOPPING CART SUMMARY");
        System.out.println("========================================");
        System.out.println("Language: " + language);
        System.out.println("Total Items Count: " + totalItems);
        System.out.printf("Total Cost: %.2f%n", totalCost);
        System.out.println("----------------------------------------");
        System.out.println("Item Details:");
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            System.out.printf("  Item %d: Price=%.2f, Quantity=%d, Subtotal=%.2f%n",
                    i + 1, item.getPrice(), item.getQuantity(),
                    item.getPrice() * item.getQuantity());
        }
        System.out.println("========================================");
    }
}