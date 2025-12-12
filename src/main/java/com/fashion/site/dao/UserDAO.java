package com.fashion.site.dao;

import com.fashion.site.model.User;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static com.fashion.site.util.DBConnect.getConnection;

public class UserDAO {

    // Tìm user theo username
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

                // preferred_formats lưu dưới dạng CSV
                String formatsCsv = rs.getString("preferred_formats");
                if (formatsCsv != null && !formatsCsv.isEmpty()) {
                    String[] arr = formatsCsv.split(",");
                    // Java 8 safe: dùng Arrays.asList thay vì List.of
                    u.setPreferredFormats(Arrays.asList(arr));
                }

                return u;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Insert chỉ dùng khi Register, kiểm tra username trùng
    public void insert(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        String sql = "INSERT INTO Users(username,password,major,school,preferred_formats) VALUES(?,?,?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // nên hash trước khi insert
            ps.setString(3, user.getMajor());
            ps.setString(4, user.getSchool());
            ps.setString(5, user.getPreferredFormats() != null ? String.join(",", user.getPreferredFormats()) : null);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Update thông tin user
    public void update(User user) {
        String sql = "UPDATE Users SET major = ?, school = ?, preferred_formats = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getMajor());
            stmt.setString(2, user.getSchool());
            stmt.setString(3, user.getPreferredFormats() != null ? String.join(",", user.getPreferredFormats()) : null);
            stmt.setString(4, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
