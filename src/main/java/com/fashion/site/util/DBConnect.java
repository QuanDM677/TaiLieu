package com.fashion.site.util;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnect {

    public static Connection getConnection() {
        try {
            // Lookup DataSource từ Tomcat JNDI
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FashionDB");
            Connection conn = ds.getConnection();
            System.out.println(">>> Kết nối DB qua DataSource thành công!");
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(">>> Lỗi kết nối DB qua DataSource:", e);
        }
    }
}
