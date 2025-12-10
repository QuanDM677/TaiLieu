package com.fashion.site.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class I18nFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Locale locale = (Locale) session.getAttribute("locale");
        if(locale == null) {
            locale = Locale.ENGLISH; // mặc định
        }

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        req.setAttribute("bundle", bundle);

        chain.doFilter(request, response);
    }
}
