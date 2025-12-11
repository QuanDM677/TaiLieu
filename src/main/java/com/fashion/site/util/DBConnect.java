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
                throw new RuntimeException("Thiếu biến môi trường: DB_URL, DB_USERNAME, DB_PASSWORD");
            }

            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println(">>> Kết nối PostgreSQL OK");

            // Tạo bảng
            try (Statement st = conn.createStatement()) {

                String createUsers =
                        "CREATE TABLE IF NOT EXISTS users ("
                                + "username VARCHAR(100) PRIMARY KEY, "
                                + "password VARCHAR(200), "
                                + "major VARCHAR(200), "
                                + "school VARCHAR(200)"
                                + ");";

                System.out.println(">>> RUN SQL = " + createUsers);
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

                System.out.println(">>> RUN SQL = " + createDocuments);
                st.execute(createDocuments);

                System.out.println(">>> Bảng users + documents đã đảm bảo tồn tại !");
            }

        } catch (Exception e) {
            System.err.println(">>> LỖI KẾT NỐI HOẶC TẠO BẢNG PostgreSQL <<<");
            e.printStackTrace();
            throw new RuntimeException(e); // ép log hiển thị trên Tomcat
        }

        return conn;
    }
}
