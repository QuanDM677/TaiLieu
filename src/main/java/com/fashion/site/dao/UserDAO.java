package com.fashion.site.dao;

import com.fashion.site.model.User;
import com.fashion.site.util.DBConnect;
import java.sql.*;

import static com.fashion.site.util.DBConnect.getConnection;

public class UserDAO {

    public User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username=?";
        try (Connection c = getConnection();
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
        try (Connection c = getConnection();
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

    public void update(User user) {
        String sql = "UPDATE users SET major = ?, school = ?, preferred_formats = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getMajor());
            stmt.setString(2, user.getSchool());
            stmt.setString(3, user.getPreferredFormats());
            stmt.setString(4, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
