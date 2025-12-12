package com.fashion.site.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();

        Locale locale;

        // Chọn locale đúng chuẩn
        if ("vi".equalsIgnoreCase(lang)) {
            locale = new Locale("vi", "VN");
        } else {
            locale = new Locale("en", "US");
        }

        // Lưu vào session
        session.setAttribute("locale", locale);

        // Quay lại trang trước
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/locale")) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("dashboard");
        }
    }
}
