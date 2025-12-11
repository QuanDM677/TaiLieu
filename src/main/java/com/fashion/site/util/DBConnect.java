package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            // Nếu chưa có kết nối hoặc bị đóng
            if (conn == null || conn.isClosed()) {
                // Load driver SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                // Cấu hình kết nối
                String dbUser = "sa";
                String dbPass = "1234";

                // Nếu dùng SQL Server Express, chú ý instance SQLEXPRESS
                String dbUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                        "databaseName=fashion_site;" +
                        "encrypt=true;trustServerCertificate=true;";

                // Kết nối
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                System.out.println("Kết nối SQL Server thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver SQL Server: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối SQL: " + e.getMessage());
            throw new RuntimeException("Không thể kết nối CSDL", e);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Đóng kết nối SQL Server thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
