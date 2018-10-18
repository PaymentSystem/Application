package com.epam.lab.paymentsystem.dao;

import javax.naming.NamingException;
import java.sql.*;

import com.epam.lab.paymentsystem.entities.User;

public class UserDAO implements UserDAOInterface {

    private static final String INSERT_SQL = "INSERT INTO users (login, password, user_name)" + "VALUES (?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM USERS WHERE login = ?";

    public static User getCopy(User old) {
        User newUser = new User();
        newUser.setLogin(old.getLogin());
        newUser.setName(old.getName());
        newUser.setPassword(old.getPassword());
        return newUser;
    }

    public User createUser(User user) {

        User userToAdd = getCopy(user);
        Connection connect = null;
        try {
            try {
                connect = ConnectionPool.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            PreparedStatement ps = connect.prepareStatement(INSERT_SQL);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());

            ps.executeUpdate();
            ResultSet rs = ps.executeQuery();
            userToAdd.setId(rs.getInt(1));

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                ConnectionPool.connectionRelease(connect);
            }
        }
        return userToAdd;
    }

    public User findByLogin(User user) {

        Connection connection = null;
        User userFind = null;

        try {
            try {
                connection = ConnectionPool.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            }

            PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
            ps.setString(1, user.getLogin());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userFind = getCopy(user);
                userFind.setId(rs.getInt(1));
                System.out.println("it user exist");
                return userFind;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionPool.connectionRelease(connection);
            }
        }
        System.out.println("user is not exist");
        return userFind;
    }
}
