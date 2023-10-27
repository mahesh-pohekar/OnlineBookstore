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

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (DBUtil dbUtil = new DBUtil()) {
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());

            // Get user ID from the session
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId");

            // Get book ID and quantity from the form submission
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            // Retrieve the user's cart items
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(userId);

            boolean itemExists = false;

            // Check if the item already exists in the user's cart
            for (CartItem cartItem : cartItems) {
                if (cartItem.getBookId() == bookId) {
                    // If the item already exists, update the quantity
                    int newQuantity = cartItem.getQuantity() + 1;
                    cartItemDAO.updateCartItemQuantity(cartItem.getCartItemId(), newQuantity);
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                // If the item does not exist, add it to the cart
                CartItem newCartItem = new CartItem();
                newCartItem.setUserId(userId);
                newCartItem.setBookId(bookId);
                newCartItem.setQuantity(1);
                cartItemDAO.addCartItem(newCartItem);
            }

            // Redirect back to the main page for users
            response.sendRedirect("CartServlet");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}
