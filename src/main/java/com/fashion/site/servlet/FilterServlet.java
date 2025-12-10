package com.fashion.site.servlet;

import com.fashion.site.dao.DocumentDAO;
import com.fashion.site.model.Document;
import com.fashion.site.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/filter")
public class FilterServlet extends HttpServlet {
    private DocumentDAO docDAO = new DocumentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect("login"); return; }

        String major = req.getParameter("major");
        String school = req.getParameter("school");
        String format = req.getParameter("format");

        List<Document> filtered = docDAO.findAll().stream()
                .filter(d -> major == null || major.isEmpty() || d.getMajor().equalsIgnoreCase(major))
                .filter(d -> school == null || school.isEmpty() || d.getSchool().equalsIgnoreCase(school))
                .filter(d -> format == null || format.isEmpty() || d.getFormat().equalsIgnoreCase(format))
                .collect(Collectors.toList());

        req.setAttribute("filteredDocs", filtered);

        // Reuse dashboard sections
        List<Document> docs = docDAO.findAll();

        req.setAttribute("newestDocs", docs.stream().sorted((a,b)->b.getId().compareTo(a.getId())).limit(5).collect(Collectors.toList()));
        req.setAttribute("popularDocs", docs.stream().sorted((a,b)->b.getDownloadCount()-a.getDownloadCount()).limit(5).collect(Collectors.toList()));
        req.setAttribute("recommendedDocs", docs.stream().filter(d->d.getMajor()!=null && d.getMajor().equals(user.getMajor())).limit(5).collect(Collectors.toList()));

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
