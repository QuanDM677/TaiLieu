package com.fashion.site.servlet;

import com.fashion.site.dao.UserDAO;
import com.fashion.site.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember"); // checkbox "Remember Me"

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername(username);

        if(user != null && user.getPassword().equals(password)){
            // Lưu session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // Nếu check "Remember Me", tạo cookie
            Cookie cookie;
            if("on".equals(remember)){
                cookie = new Cookie("username", username);
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                cookie.setHttpOnly(true);
            } else {
                // Xóa cookie nếu không check
                cookie = new Cookie("username", "");
                cookie.setMaxAge(0);
            }
            resp.addCookie(cookie);

            resp.sendRedirect("dashboard");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    // Nếu muốn auto-login bằng cookie
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra cookie
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                if("username".equals(c.getName())){
                    String username = c.getValue();
                    if(username != null && !username.isEmpty()){
                        UserDAO userDAO = new UserDAO();
                        User user = userDAO.findByUsername(username);
                        if(user != null){
                            req.getSession().setAttribute("user", user);
                            resp.sendRedirect("dashboard");
                            return;
                        }
                    }
                }
            }
        }
        // Nếu không có cookie, hiển thị trang login
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}

