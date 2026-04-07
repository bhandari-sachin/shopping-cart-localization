package com.example.shoppingcart.gui.localization.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static volatile RuntimeException initializationError;
    private static final Object LOCK = new Object();
    private static boolean initialized = false;

    private static void initializeIfNeeded() {
        if (initialized) {
            return;
        }

        synchronized (LOCK) {
            if (initialized) {
                return;
            }

            try {
                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                initialized = true;
            } catch (ClassNotFoundException e) {
                initializationError = new RuntimeException("MySQL JDBC Driver not found", e);
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        initializeIfNeeded();

        if (initializationError != null) {
            String message = "Database connection unavailable";
            if (initializationError.getMessage() != null) {
                message += ": " + initializationError.getMessage();
            }
            throw new SQLException(message, initializationError);
        }

        String dbHost = System.getenv().getOrDefault("DB_HOST", "localhost");
        String dbPort = System.getenv().getOrDefault("DB_PORT", "3306");
        String dbName = System.getenv().getOrDefault("DB_NAME", "shopping_cart_localization");
        String dbUser = System.getenv().getOrDefault("DB_USER", "root");
        String dbPassword = System.getenv().getOrDefault("DB_PASSWORD", "postgressuperuser");

        String jdbcUrl = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                dbHost, dbPort, dbName
        );

        try {
            return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    public static boolean isDatabaseAvailable() {
        try {
            initializeIfNeeded();
            if (initializationError != null) {
                return false;
            }
            try (Connection conn = getConnection()) {
                return conn != null && !conn.isClosed();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static String getInitializationErrorMessage() {
        if (initializationError == null || initializationError.getMessage() == null) {
            return "Database connection unavailable";
        }
        return "Database connection unavailable: " + initializationError.getMessage();
    }

    public static void resetConnection() {
        synchronized (LOCK) {
            initialized = false;
            initializationError = null;
        }
    }
}