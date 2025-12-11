package com.fashion.site;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            String dbUrl = "jdbc:postgresql://dpg-d4t8bnkhg0os73co9e20-a.singapore-postgres.render.com:5432/fashion_site_kf06?sslmode=require";
            String dbUser = "sa";
            String dbPass = "iIa4u2h3Njefzd781AdfNngyDbvxCGny";
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("Kết nối thành công: " + c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
