package com.fashion.site.servlet;

import com.fashion.site.dao.UserDAO;
import com.fashion.site.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect("login"); return; }

        req.setAttribute("user", user);
        req.getRequestDispatcher("settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect("login"); return; }

        String major = req.getParameter("major");
        String school = req.getParameter("school");
        String[] formats = req.getParameterValues("preferredFormats");

        user.setMajor(major);
        user.setSchool(school);
        user.setPreferredFormats(formats != null ? Arrays.asList(formats) : null);

        // Update CSDL
        userDAO.insert(user); // hoặc tạo update() nếu muốn

        req.setAttribute("success", "Settings updated!");
        req.setAttribute("user", user);
        req.getRequestDispatcher("settings.jsp").forward(req, resp);
    }
}
