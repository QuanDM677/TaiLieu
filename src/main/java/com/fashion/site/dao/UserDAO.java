package com.fashion.site.dao;

import com.fashion.site.model.User;
import com.fashion.site.util.DBConnect;
import java.sql.*;

public class UserDAO {

    public User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username=?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User(
                        rs.getString("username"),
                        rs.getString("password")
                );
                u.setMajor(rs.getString("major"));
                u.setSchool(rs.getString("school"));
                return u;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(User user) {
        String sql = "INSERT INTO Users(username,password,major,school) VALUES(?,?,?,?)";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getMajor());
            ps.setString(4, user.getSchool());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
