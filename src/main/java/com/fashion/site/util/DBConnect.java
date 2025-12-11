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

            // Debug Render log
            System.out.println("DB_URL = " + dbUrl);
            System.out.println("DB_USERNAME = " + dbUser);

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException(
                        "Thiếu biến môi trường: DB_URL, DB_USERNAME, DB_PASSWORD"
                );
            }

            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");

            // Connect
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println(">>> Kết nối PostgreSQL thành công!");

            // -------------------------------
            // TỰ ĐỘNG TẠO BẢNG (Auto migrate)
            // -------------------------------
            try (Statement st = conn.createStatement()) {

                String createUsersTable =
                        "CREATE TABLE IF NOT EXISTS Users ("
                                + "username VARCHAR(100) PRIMARY KEY, "
                                + "password VARCHAR(200), "
                                + "major VARCHAR(200), "
                                + "school VARCHAR(200)"
                                + ");";

                st.execute(createUsersTable);

                String createDocumentsTable =
                        "CREATE TABLE IF NOT EXISTS Documents ("
                                + "id VARCHAR(100) PRIMARY KEY, "
                                + "title VARCHAR(255), "
                                + "major VARCHAR(200), "
                                + "school VARCHAR(200), "
                                + "format VARCHAR(50), "
                                + "filePath VARCHAR(500), "
                                + "downloadCount INT DEFAULT 0"
                                + ");";

                st.execute(createDocumentsTable);

                System.out.println(">>> Kiểm tra & tạo bảng thành công!");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver PostgreSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Không thể kết nối PostgreSQL hoặc tạo bảng");
            e.printStackTrace();
        }

        return conn;
    }
}
