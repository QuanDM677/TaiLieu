package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;
import com.fashion.site.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        List<Document> docs = docDAO.findAll();

        List<Document> newest = docs.stream()
                .sorted(Comparator.comparing(Document::getId).reversed())
                .limit(5).collect(Collectors.toList());

        List<Document> popular = docs.stream()
                .sorted(Comparator.comparing(Document::getDownloadCount).reversed())
                .limit(5).collect(Collectors.toList());

        List<Document> recommended = docs.stream()
                .filter(d -> d.getMajor() != null && d.getMajor().equals(user.getMajor()))
                .limit(5).collect(Collectors.toList());

        req.setAttribute("newestDocs", newest);
        req.setAttribute("popularDocs", popular);
        req.setAttribute("recommendedDocs", recommended);

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
