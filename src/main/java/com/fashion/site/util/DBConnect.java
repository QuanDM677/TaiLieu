package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        String host = System.getenv("DB_HOST");
        if (host == null) host = "192.168.1.12";

        String port = System.getenv("DB_PORT");
        if (port == null) port = "1433";

        String dbName = System.getenv("DB_NAME");
        if (dbName == null) dbName = "FashionDB";

        String user = System.getenv("DB_USER");
        if (user == null) user = "sa";

        String pass = System.getenv("DB_PASS");
        if (pass == null) pass = "1234";

        String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbName + ";encrypt=false";

        System.out.println("---- DBConnect Debug ----");
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("Database: " + dbName);
        System.out.println("User: " + user);
        System.out.println("JDBC URL: " + url);
        System.out.println("-------------------------");

        try {
            // Load SQL Server driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(url, user, pass);

            System.out.println("Connection SUCCESS!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found!");
            e.printStackTrace();
            throw new RuntimeException("Driver not found!", e);

        } catch (SQLException e) {
            System.err.println("Connection FAILED!");
            e.printStackTrace();
            throw new RuntimeException("Error connecting to database", e);
        }
    }

    // Test main
    public static void main(String[] args) {
        getConnection();
    }
}
