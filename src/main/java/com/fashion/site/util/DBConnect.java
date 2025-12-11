package com.fashion.site.util;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnect {

    public static Connection getConnection() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FashionDB");
            return ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối DB qua DataSource", e);
        }
    }
}
