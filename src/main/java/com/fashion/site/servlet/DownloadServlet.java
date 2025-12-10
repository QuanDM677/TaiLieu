package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null) { resp.sendRedirect("dashboard"); return; }

        List<Document> docs = docDAO.findAll();
        Document doc = docs.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
        if (doc == null) { resp.sendRedirect("dashboard"); return; }

        File file = new File(getServletContext().getRealPath("") + File.separator + doc.getFilePath());
        if (!file.exists()) { resp.sendRedirect("dashboard"); return; }

        docDAO.increaseDownload(id);

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream())) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
        }
    }
}
