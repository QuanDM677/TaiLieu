package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USERNAME");
            String dbPass = System.getenv("DB_PASSWORD");
            System.out.println(">>> DB_URL = " + dbUrl);
            System.out.println(">>> DB_USERNAME = " + dbUser);
            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException("Thiếu biến môi trường DATABASE!");
            }
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println(">>> Kết nối PostgreSQL thành công!");
        } catch (Exception e) {
            System.err.println(">>> Lỗi kết nối DB:");
            e.printStackTrace();
        }
        return conn;
    }
}
