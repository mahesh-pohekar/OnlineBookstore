package com.bookstore.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.CartItem;

public class CartItemDAO {
    private Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a book to the user's shopping cart
    public boolean addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart_items (user_id, book_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cartItem.getUserId());
            preparedStatement.setInt(2, cartItem.getBookId());
            preparedStatement.setInt(3, cartItem.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update the quantity of a cart item
    public boolean updateCartItemQuantity(int cartItemId, int newQuantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, cartItemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to remove a cart item
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cartItemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all cart items for a user
    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItemList = new ArrayList<>();
        String sql = "SELECT ci.cart_item_id, ci.user_id, ci.book_id, ci.quantity, b.title, b.price " +
                     "FROM cart_items ci " +
                     "JOIN books b ON ci.book_id = b.book_id " +
                     "WHERE ci.user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
                cartItem.setUserId(resultSet.getInt("user_id"));
                cartItem.setBookId(resultSet.getInt("book_id"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItem.setBookTitle(resultSet.getString("title"));
                cartItem.setBookPrice(resultSet.getDouble("price"));
                cartItemList.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItemList;
    }
}

