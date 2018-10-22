package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.ConnectionPool;
import com.epam.lab.paymentsystem.dao.UserDAOInterface;
import com.epam.lab.paymentsystem.entities.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {

    private static final String INSERT_SQL = "INSERT INTO users (login, passwd, user_name)" + "VALUES (?, ?, ?)";
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
            connect = ConnectionPool.getConnection();

            PreparedStatement ps = connect.prepareStatement(INSERT_SQL);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                userToAdd.setId(rs.getInt(1));
            }

            ps.close();
            rs.close();
        } catch (NamingException | SQLException e) {
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
            connection = ConnectionPool.getConnection();

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
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionPool.connectionRelease(connection);
            }
        }
        System.out.println("user is not exist");
        return userFind;
    }

    @Override
    public User getUserByLogin(String login) {
        Connection connection = null;
        User user = new User();

        try {
            connection = ConnectionPool.getConnection();

            PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setLogin(login);
                user.setPassword(rs.getString(3));
                user.setRole_id(rs.getInt(4));
                user.setName(rs.getString(5));

                System.out.println("===USR INFO===");
                System.out.println("login: " + user.getLogin() + "; pwd: " + user.getPassword());
            } else {
                return null;
            }
            ps.close();
            rs.close();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionPool.connectionRelease(connection);
            }
        }
        return user;
    }
}
