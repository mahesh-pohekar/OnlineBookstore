package com.bookstore.controller;


import java.io.IOException;
import java.util.List;

import com.bookstore.dao.BookDAO;
import com.bookstore.model.Book;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BrowseBooksServlet")
public class BrowseBooksServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBUtil dbUtil = new DBUtil()) {
            BookDAO bookDAO = new BookDAO(dbUtil.getConnection());

            // Retrieve a list of all books
            List<Book> bookList = bookDAO.getAllBooks();

            // Set the book list as a request attribute for the JSP to access
            request.setAttribute("bookList", bookList);

            // Forward the request to the home.jsp page for displaying books
            request.getRequestDispatcher("all_books.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests if needed
    }
}

