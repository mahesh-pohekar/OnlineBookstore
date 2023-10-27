package com.bookstore.controller;


import java.io.IOException;
import java.util.List;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.model.CartItem;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests related to the shopping cart (e.g., displaying the cart)
        try (DBUtil dbUtil = new DBUtil()) {
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());

            // Retrieve the user's cart items
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId"); // Retrieve the user's ID
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(userId);

            // Calculate the total amount and set it as a request attribute
            double totalAmount = calculateTotalAmount(cartItems);
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("totalAmount", totalAmount);

            // Forward the request to the cart.jsp page for displaying the cart
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests related to the shopping cart (e.g., updating cart items)
        try (DBUtil dbUtil = new DBUtil()) {
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());

            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals("update")) {
                    // Update cart item quantities
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(request.getParameter("quantity"));
                    cartItemDAO.updateCartItemQuantity(cartItemId, newQuantity);
                } else if (action.equals("remove")) {
                    // Remove a cart item
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    cartItemDAO.removeCartItem(cartItemId);
                }
            }

            // Redirect back to the cart page
            response.sendRedirect("CartServlet");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    // Helper method to calculate the total amount of items in the cart
    private double calculateTotalAmount(List<CartItem> cartItems) {
        double totalAmount = 0.0;
        for (CartItem cartItem : cartItems) {
            totalAmount += cartItem.getQuantity() * cartItem.getBookPrice();
        }
        return totalAmount;
    }
}

