package com.fashion.site;

import com.fashion.site.util.DBConnect;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection c = DBConnect.getConnection()) {
            System.out.println("Connection: " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}