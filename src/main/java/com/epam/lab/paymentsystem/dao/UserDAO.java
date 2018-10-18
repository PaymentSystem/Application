package com.epam.lab.paymentsystem.dao;

import javax.naming.NamingException;
import java.sql.*;
import com.epam.lab.paymentsystem.entities.User;


public class UserDAO implements UserDAOInterface {


    public User createUser(User user) {

        String sql = "INSERT INTO users (login, password, user_name)" + "VALUES (?, ?, ?)";
        Connection connect = null;


        try {
            try {
                connect = ConnectionPool.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());

            ps.executeUpdate();
            ResultSet rs = ps.executeQuery();
            user.setId(rs.getInt(1));

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                ConnectionPool.close(connect);
            }
        }

        return user;
    }


    public User findByLogin(User user) {

        String sql = "SELECT * FROM USERS WHERE login = ?";
        Connection connection = null;
        User userFind = null;

        try {
            try {
                connection = ConnectionPool.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            }


            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userFind = user;
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
                ConnectionPool.close(connection);
            }
        }
        System.out.println("user is not exist");
        return userFind;
    }

}
