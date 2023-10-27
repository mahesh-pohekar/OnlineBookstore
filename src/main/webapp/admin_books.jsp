<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.Book" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - View All Books</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	<span><a href="admin_home.jsp">Home</a></span>&emsp;<span><a href="LogoutServlet">Logout</a></span>
    <h1>Admin - View All Books</h1>
    <table>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <%
            // Retrieve a list of all books from the servlet and iterate through them
            List<Book> bookList = (List<Book>) request.getAttribute("bookList");
            if (bookList != null) {
                for (Book book : bookList) {
        %>
        <tr>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getPrice() %></td>
            <td><a href="DeleteBookServlet?bookId=<%= book.getBookId() %>">Delete</a></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
