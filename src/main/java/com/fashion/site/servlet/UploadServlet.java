package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10)
public class UploadServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    // Thư mục upload ngoài webapp
    private static final String UPLOAD_DIR = "C:\\fashion_uploads"; // chỉnh theo máy server

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part filePart = req.getPart("file");
        String title = req.getParameter("title");
        String major = req.getParameter("major");
        String school = req.getParameter("school");

        if (filePart == null || filePart.getSize() == 0) {
            resp.sendRedirect("dashboard");
            return;
        }

        String originalFileName = new File(filePart.getSubmittedFileName()).getName();
        String format = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Tạo tên file duy nhất
        String newFileName = UUID.randomUUID() + "_" + originalFileName;
        File file = new File(uploadDir, newFileName);

        // Lưu file
        filePart.write(file.getAbsolutePath());

        // Lưu thông tin vào database (đường dẫn tuyệt đối)
        Document doc = new Document(
                UUID.randomUUID().toString(),
                title, major, school,
                format,
                file.getAbsolutePath()
        );
        docDAO.insert(doc);

        resp.sendRedirect("dashboard");
    }
}
