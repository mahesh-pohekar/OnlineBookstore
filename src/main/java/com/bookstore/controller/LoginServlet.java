package com.bookstore.controller;

import java.io.IOException;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;
import com.bookstore.model.UserRole;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (DBUtil dbUtil = new DBUtil()) {
            UserDAO userDAO = new UserDAO(dbUtil.getConnection());

            // Check if the username and password are valid
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                // Authentication successful
            	// Check user's role
                if (user.getRole() == UserRole.ADMIN) {
                    // If user is an admin, redirect to admin dashboard
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getUserId());
                    response.sendRedirect("admin_home.jsp");
                } else {
                    // If user is a regular user, redirect to the home page
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getUserId());
                    response.sendRedirect("user_home.jsp");
                }
            } else {
            	response.sendRedirect("signup.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests if needed
    }
}
