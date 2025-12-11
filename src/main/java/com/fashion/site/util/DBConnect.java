package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        try {
            // Load SQL Server driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Lấy từ environment variables hoặc hardcode (test)
            String host = System.getenv("DB_HOST");
            if (host == null) host = "192.168.1.12"; // IP SQL Server

            String port = System.getenv("DB_PORT");
            if (port == null) port = "1433";

            String dbName = System.getenv("DB_NAME");
            if (dbName == null) dbName = "FashionDB";

            String user = System.getenv("DB_USER");
            if (user == null) user = "sa";

            String pass = System.getenv("DB_PASS");
            if (pass == null) pass = "1234";

            String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbName + ";encrypt=false";

            System.out.println("Connecting to SQL Server: " + url);

            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
