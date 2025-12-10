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
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part filePart = req.getPart("file");
        String title = req.getParameter("title");
        String major = req.getParameter("major");
        String school = req.getParameter("school");

        String originalFileName = filePart.getSubmittedFileName();
        String format = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        filePart.write(uploadPath + File.separator + originalFileName);

        Document doc = new Document(
                UUID.randomUUID().toString(),
                title, major, school,
                format,
                UPLOAD_DIR + "/" + originalFileName
        );

        docDAO.insert(doc);
        resp.sendRedirect("dashboard");
    }
}
