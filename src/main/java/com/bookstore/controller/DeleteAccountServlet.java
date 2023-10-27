package com.bookstore.controller;

import java.io.IOException;

import com.bookstore.dao.UserDAO;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve user_id from the request parameter
        int userIdToDelete = Integer.parseInt(request.getParameter("userId"));

        try (DBUtil dbUtil = new DBUtil()) {
            UserDAO userDAO = new UserDAO(dbUtil.getConnection());

            // Delete the user by ID
            boolean success = userDAO.deleteUserById(userIdToDelete);

            if (success) {
                // User deleted successfully
                // Invalidate the user's session and redirect to a confirmation page
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("signup.jsp");
            } else {
                // User deletion failed
                // You can redirect to an error page or display an error message
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
            response.sendRedirect("error.jsp");
        }
    }
}

