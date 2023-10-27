package com.bookstore.controller;


import java.io.IOException;
import java.util.List;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.dao.OrderDAO;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        try (DBUtil dbUtil = new DBUtil()) {
            OrderDAO orderDAO = new OrderDAO(dbUtil.getConnection());

            // Retrieve a list of orders for the user
            List<Order> orderList = orderDAO.getOrdersByCustomerId(userId);

            // Set the order list as a request attribute for the JSP to access
            request.setAttribute("orderList", orderList);

            // Forward the request to the order.jsp page for displaying orders
            request.getRequestDispatcher("order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        try (DBUtil dbUtil = new DBUtil()) {
            OrderDAO orderDAO = new OrderDAO(dbUtil.getConnection());

            // Create a new order
            Order order = new Order();
            order.setCustomerId(userId);
            order.setOrderDate(new java.util.Date()); // Set the current date and time as the order date

            // Calculate and set the total amount based on the user's shopping cart
            // Add code here to calculate the total amount based on the user's shopping cart
            // For example:
            // double totalAmount = calculateTotalAmount(userId);
            // order.setTotalAmount(totalAmount);

            // Add the order to the database
         // Calculate and set the total amount based on the user's shopping cart
            double totalAmount = 0.0; // Initialize the total amount to zero

            // Retrieve the user's cart items
            CartItemDAO cartItemDAO = new CartItemDAO(dbUtil.getConnection());
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(userId);

            // Iterate through the cart items and calculate the total amount
            for (CartItem cartItem : cartItems) {
                double itemTotal = cartItem.getQuantity() * cartItem.getBookPrice();
                totalAmount += itemTotal;
            }

            // Set the calculated total amount in the order object
            order.setTotalAmount(totalAmount);

            boolean orderPlaced = orderDAO.placeOrder(order);

            if (orderPlaced) {
                // If the order is successfully placed, you may want to clear the user's shopping cart
                // Iterate through the cart items and remove each one


                for (CartItem cartItem : cartItems) {
                    cartItemDAO.removeCartItem(cartItem.getCartItemId());
                }

                // Redirect to a success page or provide a success message
                response.sendRedirect("user_home.jsp");
            } else {
                // Handle order placement failure, you may want to redirect to an error page
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

}
