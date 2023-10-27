package com.bookstore.controller;

import java.io.IOException;

import com.bookstore.dao.OrderDAO;
import com.bookstore.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try (DBUtil dbUtil = new DBUtil()) {
            OrderDAO orderDAO = new OrderDAO(dbUtil.getConnection());

            // Attempt to delete the order
            boolean deleted = orderDAO.deleteOrder(orderId);

            if (deleted) {
                // Successfully deleted, redirect back to the order.jsp page
                response.sendRedirect("OrderServlet");
            } else {
                // Handle deletion failure, you can redirect to an error page or handle as needed
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}

