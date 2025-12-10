package com.fashion.site.dao;

import com.fashion.site.model.Document;
import com.fashion.site.util.DBConnect;
import java.sql.*;
import java.util.*;

public class DocumentDAO {

    public List<Document> findAll() {
        List<Document> list = new ArrayList<>();
        String sql = "SELECT * FROM Documents";

        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Document d = new Document(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("major"),
                        rs.getString("school"),
                        rs.getString("format"),
                        rs.getString("filePath")
                );
                while (rs.getInt("downloadCount") > d.getDownloadCount()) {
                    d.increaseDownloadCount();
                }
                list.add(d);
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Document d) {
        String sql = "INSERT INTO Documents(id,title,major,school,format,filePath,downloadCount) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, d.getId());
            ps.setString(2, d.getTitle());
            ps.setString(3, d.getMajor());
            ps.setString(4, d.getSchool());
            ps.setString(5, d.getFormat());
            ps.setString(6, d.getFilePath());
            ps.setInt(7, d.getDownloadCount());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseDownload(String id) {
        String sql = "UPDATE Documents SET downloadCount = downloadCount + 1 WHERE id=?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM Documents WHERE id=?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
