package com.epam.lab.paymentsystem.dao;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    static List<Connection> connectionPool = new LinkedList<Connection>();
    Connection connect = null;


    public static Connection getConnection() throws SQLException, NamingException {
        Connection connectReturn = null;
        if (connectionPool.size() < 1) {
            addConnect();
            connectReturn = connectionPool.remove(0);
        } else {
            connectReturn = connectionPool.remove(0);
        }
        return connectReturn;
    }

    public static void close(Connection connection) {
        if(connection != null) {
            connectionPool.add(connection);
        }
        else connectionPool.add(new ConnectionPool().connect);
    }

    private static void addConnect() {
        for (int i = 1; i <= 10; i++) {
            connectionPool.add(new ConnectionPool().connect);
        }
    }

    private ConnectionPool() {

        try {
            Class.forName("dao.UserDAO");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");


        try {

            connect = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/payment_system");

        } catch (SQLException e) {

            System.out.println("ConnectionPool Failed!");
            e.printStackTrace();
        }

        if (connect != null) {
            System.out.println("ConnectionPool was set with DB ");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}