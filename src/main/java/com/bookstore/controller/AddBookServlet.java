package com.bookstore.controller;


import java.io.IOException;

import com.bookstore.dao.BookDAO;
import com.bookstore.model.Book;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));

        // Create a new book object
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);

        // Establish a database connection (You should use connection pooling in production)
        try (DBUtil dbUtil = new DBUtil()) {
            BookDAO bookDAO = new BookDAO(dbUtil.getConnection());

            // Add the new book to the database
            if (bookDAO.addBook(book)) {
                response.sendRedirect("AdminBooksServlet");
            } else {
                // Handle book addition failure
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

