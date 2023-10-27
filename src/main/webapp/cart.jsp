<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>                              
<body>
    <span><a href="user_home.jsp">Home</a></span>&emsp;<span><a href="BrowseBooksServlet">Browse Books</a></span>&emsp;
    <span><a href="OrderServlet">My Orders</a></span>&emsp;<span><a href="LogoutServlet">Logout</a></span>
    <h1>Your Shopping Cart</h1> 
    <div class="cart-items">
        <%
            // Retrieve the user's cart items from the servlet and iterate through them
            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
            if (cartItems != null) {
                for (CartItem cartItem : cartItems) {
        %>
        <div class="cart-item">
            <h2><%= cartItem.getBookTitle() %></h2>
            <p>Quantity: <%= cartItem.getQuantity() %></p>
            <p>Price per unit: <%= cartItem.getBookPrice() %></p>
            <form action="UpdateCartServlet" method="post">
                <input type="hidden" name="cartItemId" value="<%= cartItem.getCartItemId() %>">
                <label for="quantity">Update Quantity:</label>
                <input type="number" id="quantity" name="quantity" value="<%= cartItem.getQuantity() %>" min="1">
                <input type="submit" value="Update">
                <a href="RemoveCartItemServlet?cartItemId=<%= cartItem.getCartItemId() %>">Remove</a>
            </form>
        </div>
        <%
                }
            }
        %>
    </div>
    <div class="cart-total">
        <p>Total Amount: <%= request.getAttribute("totalAmount") %></p>
        <form action="OrderServlet" method="post">
            <input type="submit" value="Place Order">
        </form>
    </div>
</body>
</html>
