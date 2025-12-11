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

            // Debug trên Render
            System.out.println("DB_URL = " + dbUrl);
            System.out.println("DB_USERNAME = " + dbUser);

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new RuntimeException(
                        "Thiếu biến môi trường: DB_URL, DB_USERNAME, DB_PASSWORD"
                );
            }

            // Driver PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Kết nối
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println(">>> Kết nối PostgreSQL thành công!");

            // -------------------------------
            // AUTO CREATE TABLE (Chữ thường)
            // -------------------------------
            try (Statement st = conn.createStatement()) {

                String createUsers =
                        "CREATE TABLE IF NOT EXISTS users ("
                                + "username VARCHAR(100) PRIMARY KEY, "
                                + "password VARCHAR(200), "
                                + "major VARCHAR(200), "
                                + "school VARCHAR(200)"
                                + ");";

                st.execute(createUsers);

                String createDocuments =
                        "CREATE TABLE IF NOT EXISTS documents ("
                                + "id VARCHAR(100) PRIMARY KEY, "
                                + "title VARCHAR(255), "
                                + "major VARCHAR(200), "
                                + "school VARCHAR(200), "
                                + "format VARCHAR(50), "
                                + "filePath VARCHAR(500), "
                                + "downloadCount INT DEFAULT 0"
                                + ");";

                st.execute(createDocuments);

                System.out.println(">>> Tạo bảng users & documents thành công!");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver PostgreSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Không thể kết nối PostgreSQL hoặc lỗi tạo bảng!");
            e.printStackTrace();
        }

        return conn;
    }
}
