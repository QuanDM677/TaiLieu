package com.fashion.site.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        try {
            // Load Postgres driver
            Class.forName("org.postgresql.Driver");

            // Lấy từ environment variables hoặc hardcode (test)
            String host = System.getenv("DB_HOST"); // dpg-d4t0jvfpm1nc73ecg52g-a
            if (host == null) host = "dpg-d4t0jvfpm1nc73ecg52g-a";

            String port = System.getenv("DB_PORT"); // 5432
            if (port == null) port = "5432";

            String dbName = System.getenv("DB_NAME"); // fashion_site
            if (dbName == null) dbName = "fashion_site";

            String user = System.getenv("DB_USER"); // fashion_site
            if (user == null) user = "fashion_site";

            String pass = System.getenv("DB_PASS"); // mật khẩu của bạn
            if (pass == null) pass = "<password>";

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

            System.out.println("Connecting to DB: " + url);

            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Cannot connect to database!", e);
        }
    }
}
