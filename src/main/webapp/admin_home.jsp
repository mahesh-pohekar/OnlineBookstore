<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bookstore.model.User" %>
<%@ page import="com.bookstore.model.UserRole" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<span><a href="LogoutServlet">Logout</a></span>
    <h1>Welcome, Admin!</h1>
    <ul>
        <li><a href="AdminOrdersServlet">View All Orders</a></li>
        <li><a href="admin_add_book.jsp">Add a Book</a></li>
        <li><a href="AdminBooksServlet">View All Books</a></li>
        <li><a href="admin_signup.jsp">Add Admin Account</a></li>
        <%
        User user = (User) session.getAttribute("user");
        int userId = (user != null) ? user.getUserId() : -1; // Default to -1 if not found
    	%>
		<li><a href="DeleteAccountServlet?userId=<%= userId %>">Delete My Account</a></li>
    </ul>
    
</body>
</html>
