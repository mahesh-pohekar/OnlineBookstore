package com.bookstore.controller;

import java.io.IOException;

import com.bookstore.dao.BookDAO;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteBookServlet")
public class DeleteBookServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the bookId parameter from the request
        String bookId = request.getParameter("bookId");

        // Check if the bookId parameter is not null
        if (bookId != null && !bookId.isEmpty()) {
            try {
                // Convert the bookId to an integer (you may want to handle exceptions here)
                int bookIdInt = Integer.parseInt(bookId);

                // Assuming you have a method to delete a book by its ID, call that method here
                try (DBUtil dbUtil = new DBUtil()) {
                     BookDAO bookDAO = new BookDAO(dbUtil.getConnection());
                     bookDAO.deleteBook(bookIdInt);

                 } catch (Exception e) {
                     e.printStackTrace();
                     // Handle exceptions
                 }

                // Redirect the user to a relevant page after deletion
                response.sendRedirect("AdminBooksServlet"); // Replace with your actual page
            } catch (NumberFormatException e) {
                // Handle the case where the bookId is not a valid integer
                response.sendRedirect("error.jsp"); // Redirect to an error page
            }
        } else {
            // Handle the case where bookId is missing or empty
            response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }

    // Other methods if needed
}

