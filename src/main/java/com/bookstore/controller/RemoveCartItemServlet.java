package com.bookstore.controller;


import java.io.IOException;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the cart item ID from the query parameter
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

        try (DBUtil dbUtil = new DBUtil()) {
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());

            // Remove the cart item
            if (cartItemDAO.removeCartItem(cartItemId)) {
                // Cart item removed successfully
                // You can redirect the user to the cart page or display a success message
                response.sendRedirect("CartServlet");
            } else {
                // Removal failed, handle the error
                // You can redirect to an error page or display an error message
                response.sendRedirect("error.jsp");
            }
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

