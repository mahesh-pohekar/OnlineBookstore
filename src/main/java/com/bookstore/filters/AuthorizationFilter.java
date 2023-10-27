package com.bookstore.filters;

import java.io.IOException;

import com.bookstore.model.User;
import com.bookstore.model.UserRole;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the user is logged in
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Get the user's role
            UserRole userRole = ((User) session.getAttribute("user")).getRole();
            // Define which roles can access specific URLs
            String requestURI = httpRequest.getRequestURI();

            if (userRole == UserRole.ADMIN && isAdminPage(requestURI)) {
                // Admin is allowed to access admin-specific pages
                chain.doFilter(request, response);
            } else if (userRole == UserRole.USER && isUserPage(requestURI)) {
                // Authenticated user can access general user pages
                chain.doFilter(request, response);
            } else {
                // Unauthorized access, redirect to an error page or handle as needed
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
            }
        } else if (isLoginPage(httpRequest.getRequestURI())) {
            // Allow access to the login page without being logged in
            chain.doFilter(request, response);
        }else {
            // User is not logged in and not on the login page, redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    private boolean isAdminPage(String url) {
        // Implement logic to check if the URL is an admin page
        // Example: return url.contains("/admin/");
        return  !(url.contains("BrowseBooksServlet")||url.contains("OrderServlet")||url.contains("CartServlet")|| url.contains("user"));
    }

    private boolean isUserPage(String url) {
        // Implement logic to check if the URL is a user page
        // Example: return url.contains("/user/");
        return !(url.contains("admin")||url.contains("Admin")||url.contains("DeleteBookServlet")||url.contains("AddBookServlet"));
    }

    private boolean isLoginPage(String url) {
        // Implement logic to check if the URL is the login page
        // Example: return url.contains("/login.jsp");
        return url.contains("signup")|| url.contains("login")|| url.contains("LoginServlet")||url.contains("LogOutServlet")||
        		url.contains("SignupServlet");
    }

    // Other filter methods (init, destroy) can be empty

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
