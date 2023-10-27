<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.Book" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Bookstore</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<span><a href="user_home.jsp">Home</a></span>&emsp;<span><a href="CartServlet">My Cart</a></span>&emsp;
    <span><a href="OrderServlet">My Orders</a></span>&emsp;<span><a href="LogoutServlet">Logout</a></span>
    <h1>Welcome to the Online Bookstore</h1>
    <div class="book-list">
        <%
            // Retrieve a list of books from the servlet and iterate through them
            List<Book> bookList = (List<Book>) request.getAttribute("bookList");
            if (bookList != null) {
                for (Book book : bookList) {
        %>
        <div class="book-item">
            <h2><%= book.getTitle() %></h2>
            <p>Author: <%= book.getAuthor() %></p>
            <p>Price: <%= book.getPrice() %></p>
            <form action="AddToCartServlet" method="post">
                <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                <input type="submit" value="Add to Cart">
            </form>
        </div>
        <%
                }
            }
        %>
    </div>
</body>
</html>
