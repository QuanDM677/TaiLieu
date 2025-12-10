package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        try {
            // Load driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Lấy thông tin DB từ environment variables
            String host = System.getenv("DB_HOST");
            String dbName = System.getenv("DB_NAME");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");
            String port = System.getenv("DB_PORT");          // thêm biến port
            String instance = System.getenv("DB_INSTANCE");  // thêm biến instance nếu có

            // Default nếu chưa set env
            if (host == null) host = "localhost";
            if (dbName == null) dbName = "FashionSite";
            if (user == null) user = "sa";
            if (pass == null) pass = "1234";
            if (port == null) port = "1433";

            String url;

            if (instance != null && !instance.isEmpty()) {
                // Named instance
                url = "jdbc:sqlserver://" + host + "\\" + instance + ":" + port
                        + ";databaseName=" + dbName + ";encrypt=false";
            } else {
                // Default instance
                url = "jdbc:sqlserver://" + host + ":" + port
                        + ";databaseName=" + dbName + ";encrypt=false";
            }

            // Log debug
            System.out.println("Connecting to DB: " + url + " with user: " + user);

            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server Driver not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database! Check host, port, instance, username, password.", e);
        }
    }

    // Test nhanh kết nối
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
