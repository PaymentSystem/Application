package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.ConnectionPool;
import com.epam.lab.paymentsystem.dao.UserDao;
import com.epam.lab.paymentsystem.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

@Deprecated
public class UserDaoImpl implements UserDao {

  private static final String INSERT_SQL
      = "INSERT INTO users (login, passwd, user_name, id_role)" + "VALUES (?, ?, ?, ?)";
  private static final String SELECT_SQL = "SELECT * FROM USERS WHERE login = ?";

  /**
   * Copy user from service layer.
   *
   * @param old user from service
   * @return new user
   */
  public static User getCopy(User old) {
    User newUser = new User();
    newUser.setLogin(old.getLogin());
    newUser.setName(old.getName());
    newUser.setPassword(old.getPassword());
    newUser.setRole(old.getRole());
    return newUser;
  }

  /**
   * Insert new user into database.
   *
   * @param user user entity
   * @return user
   */
  public User createUser(User user) {

    User userToAdd = getCopy(user);
    Connection connect = null;
    try {
      connect = ConnectionPool.getConnection();

      PreparedStatement ps = connect.prepareStatement(INSERT_SQL);
      ps.setString(1, user.getLogin());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getName());
      ps.setInt(4, user.getRole().getId());

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
    return getUserByLogin(user.getLogin());
  }

  @Override
  public User getUserByLogin(String login) {
    throw new UnsupportedOperationException("this method is not supported");
//    Connection connection = null;
//    User user = new User();
//
//    try {
//      connection = ConnectionPool.getConnection();
//
//      PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
//      ps.setString(1, login);
//      ResultSet rs = ps.executeQuery();
//
//      if (rs.next()) {
//        user.setId(rs.getInt(1));
//        user.setLogin(login);
//        user.setPassword(rs.getString(3));
//        user.setRole(rs.getInt(4));
//        user.setName(rs.getString(5));
//      } else {
//        return null;
//      }
//      ps.close();
//      rs.close();
//    } catch (NamingException | SQLException e) {
//      e.printStackTrace();
//    } finally {
//      if (connection != null) {
//        ConnectionPool.connectionRelease(connection);
//      }
//    }
//    return user;
  }
}
