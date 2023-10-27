<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bookstore.model.User" %>
<%@ page import="com.bookstore.model.UserRole" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Home</title>
    <link rel="stylesheet" type="text/css" href="css/user_home.css">
</head>
<body>
	<span><a href="LogoutServlet">Logout</a></span>
    <%
        User user = (User) session.getAttribute("user");
        int userId = (user != null) ? user.getUserId() : -1; // Default to -1 if not found
    %>
    <h1>Welcome, <%= user.getName() %> !</h1>
    
    <ul>
        <li><a href="BrowseBooksServlet">Browse Books</a></li>
        <li><a href="CartServlet">My Cart</a></li>
        <li><a href="OrderServlet">My Orders</a></li>
        
    </ul>
     <!-- Retrieve user_id from session -->

    <!-- Create a link to DeleteAccountServlet with user_id parameter -->
    <a href="DeleteAccountServlet?userId=<%= userId %>">Delete My Account</a>
    <img alt="" src="css/bookstore.png">
</body>
</html>
