<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - View All Orders</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<span><a href="admin_home.jsp">Home</a></span>&emsp;<span><a href="LogoutServlet">Logout</a></span>
    <h1>Admin - View All Orders</h1>
    <table>
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
            <th>Order Date</th>
            <th>Total Amount</th>
        </tr>
        <%
            // Retrieve a list of all orders from the servlet and iterate through them
            List<Order> orderList = (List<Order>) request.getAttribute("orderList");
            if (orderList != null) {
                for (Order order : orderList) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getCustomerId() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getTotalAmount() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
