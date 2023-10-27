package com.bookstore.controller;


import java.io.IOException;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the cart item ID and updated quantity from the form
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));

        try (DBUtil dbUtil = new DBUtil()) {
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());

            // Update the quantity of the cart item
            if (cartItemDAO.updateCartItemQuantity(cartItemId, newQuantity)) {
                // Quantity updated successfully
                // You can redirect the user to the cart page or display a success message
                response.sendRedirect("CartServlet");
            } else {
                // Update failed, handle the error
                // You can redirect to an error page or display an error message
                response.sendRedirect("error.jsp");
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

