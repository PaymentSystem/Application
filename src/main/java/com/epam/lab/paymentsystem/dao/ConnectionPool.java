package com.epam.lab.paymentsystem.dao;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    private static List<Connection> connectionPool = new LinkedList<Connection>();
    private Connection connect = null;

    private static final int CONNECTION_POOL_SIZE = 10;
    private static final String URL_DATABASE = "jdbc:postgresql://127.0.0.1:5432/payment_system";

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

    public static void connectionRelease(Connection connection) {
        if(connection != null) {
            connectionPool.add(connection);
        }
        else{
            ConnectionPool pool = new ConnectionPool();
            Connection con = null;
            try {
                con = pool.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            connectionPool.add(con);
        }
    }

    private static void addConnect() {
        for (int i = 1; i <= CONNECTION_POOL_SIZE; i++) {
            connectionPool.add(new ConnectionPool().connect);
        }
    }

    private ConnectionPool() {
        try {
            Class.forName(UserDAO.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");

        try {
            connect = DriverManager.getConnection(URL_DATABASE);
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