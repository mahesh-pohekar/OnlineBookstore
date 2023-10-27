<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<span><a href="user_home.jsp">Home</a></span>&emsp;<span><a href="BrowseBooksServlet">Browse Books</a></span>&emsp;
    <span><a href="CartServlet">My Cart</a></span>&emsp;<span><a href="LogoutServlet">Logout</a></span>
    <h1>Your Orders</h1>
    <table>
        <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Total Amount</th>
        </tr>
        <%
            // Retrieve a list of orders from the servlet and iterate through them
            List<Order> orderList = (List<Order>) request.getAttribute("orderList");
            if (orderList != null) {
                for (Order order : orderList) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getTotalAmount() %></td>
            <td> <a href="DeleteOrderServlet?orderId=<%= order.getOrderId() %>">Cancel</a></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
