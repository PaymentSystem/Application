package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.ConnectionPool;
import com.epam.lab.paymentsystem.dao.RoleDao;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
  private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);
  private static final String ROLE_SELECT_SQL = "SELECT * FROM roles WHERE role_name = ?";

  @Override
  public int getIdByRole(Roles roleStatus) {

    int roleId = -1;
    Connection connection = null;

    try {
      connection = ConnectionPool.getConnection();

      PreparedStatement ps = connection.prepareStatement(ROLE_SELECT_SQL);
      ps.setString(1, roleStatus.toString().toLowerCase());
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        roleId = rs.getInt(1);
      }

      ps.close();
      rs.close();
    } catch (NamingException | SQLException e) {
      LOGGER.error("Exception in RoleDAOImpl in getIdByRole method", e);
    } finally {
      if (connection != null) {
        ConnectionPool.connectionRelease(connection);
      }
    }
    return roleId;
  }
}
