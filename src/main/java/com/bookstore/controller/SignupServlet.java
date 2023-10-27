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

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try (DBUtil dbUtil = new DBUtil()) {
            UserDAO userDAO = new UserDAO(dbUtil.getConnection());

            // Check if the username is already taken
            if (userDAO.getUserByUsername(username) != null) {
                request.setAttribute("signupError", "Username is already taken. Please choose another.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }

            // Create a new user with the role "USER"
            User user = new User(username, password, name, email, UserRole.USER);

            // Add the user to the database
            if (userDAO.addUser(user)) {
                response.sendRedirect("login.jsp"); // Redirect to the login page after successful registration
            } else {
                // Registration failed, show an error message
                request.setAttribute("signupError", "Registration failed. Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error. Please try again later.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests if needed
    }
}
