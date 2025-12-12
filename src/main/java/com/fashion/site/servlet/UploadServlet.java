package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 2*1024*1024, maxFileSize = 10*1024*1024)
public class UploadServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();
    private static final String UPLOAD_DIR = "C:\\fashion_uploads";

    private static final List<String> ALLOWED_FORMATS = Arrays.asList("pdf","docx","pptx","xlsx","txt","odt");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part filePart = req.getPart("file");
        String title = req.getParameter("title");

        if(filePart == null || filePart.getSize() == 0){
            req.getSession().setAttribute("error", "Chưa chọn file!");
            resp.sendRedirect("dashboard");
            return;
        }

        // Lấy extension
        String originalFileName = new File(filePart.getSubmittedFileName()).getName();
        String format = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        if(!ALLOWED_FORMATS.contains(format)){
            req.getSession().setAttribute("error", "Chỉ cho phép upload file: " + ALLOWED_FORMATS);
            resp.sendRedirect("dashboard");
            return;
        }

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if(!uploadDir.exists()) uploadDir.mkdirs();

        // Tạo tên file duy nhất
        String newFileName = UUID.randomUUID() + "_" + originalFileName;
        File file = new File(uploadDir, newFileName);

        // Lưu file
        filePart.write(file.getAbsolutePath());

        // Lưu vào database
        Document doc = new Document(UUID.randomUUID().toString(), title, "", "", format, file.getAbsolutePath());
        docDAO.insert(doc);

        req.getSession().setAttribute("message", "Upload thành công!");
        resp.sendRedirect("dashboard");
    }
}

