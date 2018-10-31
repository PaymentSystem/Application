package com.epam.lab.paymentsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class ConnectionPool {
  private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
  private static final int CONNECTION_POOL_SIZE = 10;
  private static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/payment_system";
  private static final String DRIVER_DATABASE_CLASS = "org.postgresql.Driver";
  private static final String USER_NAME = "postgres";
  private static final String USER_PASSWORD = "postgres";
  private static List<Connection> connectionPool = new LinkedList<Connection>();
  private Connection connect = null;

  /**
   * Create connection pool.
   */
  public ConnectionPool() {
    try {
      Class.forName(DRIVER_DATABASE_CLASS);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Exception, PostgreSQL JDBC Driver is not registered", e);
      System.exit(1);
    }

    try {
      connect = DriverManager.getConnection(URL_DATABASE, USER_NAME, USER_PASSWORD);
    } catch (SQLException e) {
      LOGGER.error("Connection failed", e);
    }
  }

  /**
   * Give connection from connection pool.
   *
   * @return connection
   * @throws SQLException    if wrong SQL request
   * @throws NamingException ?
   */
  public static Connection getConnection() throws SQLException, NamingException {
    Connection connectReturn;
    if (connectionPool.size() < 1) {
      addConnect();
      connectReturn = connectionPool.remove(0);
    } else {
      connectReturn = connectionPool.remove(0);
    }
    return connectReturn;
  }

  /**
   * Return connection to the connection pool.
   *
   * @param connection connection
   */
  public static void connectionRelease(Connection connection) {
    if (connection != null) {
      connectionPool.add(connection);
    } else {
      Connection con = null;
      try {
        con = getConnection();
      } catch (NamingException | SQLException e) {
        LOGGER.error("Connection release failed", e);

      }
      connectionPool.add(con);
      LOGGER.info("Connection returned back to the database");
    }
  }

  private static void addConnect() {
    for (int i = 1; i <= CONNECTION_POOL_SIZE; i++) {
      connectionPool.add(new ConnectionPool().connect);
    }
    LOGGER.info("Connection pool created, amount connections: " + connectionPool.size());
  }
}