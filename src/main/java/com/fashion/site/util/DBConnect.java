package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USERNAME");
            String dbPass = System.getenv("DB_PASSWORD");

            // Debug cho Render
            System.out.println("DB_URL = " + dbUrl);
            System.out.println("DB_USERNAME = " + dbUser);

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException(
                        "Thiếu biến môi trường: DB_URL, DB_USERNAME, DB_PASSWORD"
                );
            }

            // PostgreSQL Driver
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println(">>> Kết nối PostgreSQL thành công!");

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver PostgreSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Không thể kết nối PostgreSQL");
            e.printStackTrace();
        }

        return conn;
    }
}
