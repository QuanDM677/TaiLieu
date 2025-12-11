package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) return conn;
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi kiểm tra kết nối", e);
            }
        }

        try {
            // Lấy thông tin database từ biến môi trường (Render)
            String dbUrl = System.getenv("DB_URL");        // ví dụ: jdbc:postgresql://host:port/dbname
            String dbUser = System.getenv("DB_USERNAME");
            String dbPass = System.getenv("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException("Chưa thiết lập biến môi trường DB_URL, DB_USERNAME, DB_PASSWORD");
            }

            // Load driver (PostgreSQL example)
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("Kết nối DB thành công!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy driver DB", e);
        } catch (SQLException e) {
            throw new RuntimeException("Không thể kết nối CSDL", e);
        }

        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Đóng kết nối DB thành công!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Test kết nối
    public static void main(String[] args) {
        Connection c = getConnection();
        if (c != null) {
            System.out.println("Test kết nối OK!");
            closeConnection();
        }
    }
}
