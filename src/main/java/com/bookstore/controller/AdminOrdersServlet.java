package com.bookstore.controller;


import java.io.IOException;
import java.util.List;

import com.bookstore.dao.OrderDAO;
import com.bookstore.model.Order;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminOrdersServlet")
public class AdminOrdersServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBUtil dbUtil = new DBUtil()) {
            OrderDAO orderDAO = new OrderDAO(dbUtil.getConnection());

            // Retrieve a list of all orders
            List<Order> orderList = orderDAO.getAllOrdersWithCustomers();

            // Set the order list as a request attribute for the JSP to access
            request.setAttribute("orderList", orderList);

            // Forward the request to the admin_orders.jsp page for displaying orders
            request.getRequestDispatcher("admin_orders.jsp").forward(request, response);
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

