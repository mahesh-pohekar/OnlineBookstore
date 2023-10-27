package com.bookstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil implements AutoCloseable {
    private Connection connection;

    public DBUtil() throws SQLException {
        // Modify the database URL, username, and password as needed
        String dbUrl = "jdbc:mysql://localhost:3306/online_bookstore";
        String dbUser = "root";
        String dbPassword = "5mysqlpassword!";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection failed.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
