package com.fashion.site.util;

import java.sql.*;

public class DBConnect {

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Lấy thông tin DB từ environment variables
            String host = System.getenv("DB_HOST");
            String dbName = System.getenv("DB_NAME");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");

            // Nếu chạy local mà chưa set env vars, dùng default
            if (host == null) host = "localhost";
            if (dbName == null) dbName = "FashionSite";
            if (user == null) user = "sa";
            if (pass == null) pass = "1234";

            String url = "jdbc:sqlserver://" + host + ":1433;databaseName=" + dbName + ";encrypt=false";

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
