package com.fashion.site.servlet;

import com.fashion.site.dao.UserDAO;
import com.fashion.site.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userDAO.findByUsername(username) != null) {
            req.setAttribute("error", "Username already exists!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        User user = new User(username, password);
        userDAO.insert(user);

        resp.sendRedirect("login");
    }
}
