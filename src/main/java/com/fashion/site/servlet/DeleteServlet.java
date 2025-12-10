package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null) { resp.sendRedirect("dashboard"); return; }

        List<Document> docs = docDAO.findAll();
        Document doc = docs.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
        if (doc != null) {
            File file = new File(getServletContext().getRealPath("") + File.separator + doc.getFilePath());
            if (file.exists()) file.delete();
            docDAO.delete(id);
        }

        resp.sendRedirect("dashboard");
    }
}
