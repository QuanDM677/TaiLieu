package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect("dashboard");
            return;
        }

        Document doc = docDAO.findById(id);
        if (doc == null) {
            System.out.println("DownloadServlet: Document not found for id = " + id);
            resp.sendRedirect("dashboard");
            return;
        }

        File file = new File(doc.getFilePath());
        if (!file.exists() || !file.canRead()) {
            System.out.println("DownloadServlet: File does not exist or cannot be read: " + doc.getFilePath());
            resp.sendRedirect("dashboard");
            return;
        }

        // Tăng lượt tải
        docDAO.increaseDownload(id);

        // Set content type chung
        resp.setContentType("application/octet-stream");

        // Encode tên file để hỗ trợ tiếng Việt và ký tự đặc biệt
        String encodedName = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");
        resp.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedName);

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream())) {

            byte[] buffer = new byte[8192]; // buffer lớn hơn để tối ưu
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.println("DownloadServlet: Error streaming file: " + e.getMessage());
            resp.sendRedirect("dashboard");
        }
    }
}
