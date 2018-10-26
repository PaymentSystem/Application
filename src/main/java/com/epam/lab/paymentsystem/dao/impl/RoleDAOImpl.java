package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.ConnectionPool;
import com.epam.lab.paymentsystem.dao.RoleDAO;
import com.epam.lab.paymentsystem.entities.enums.Roles;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private static final String ROLE_SELECT_SQL = "SELECT * FROM roles WHERE role_name = ?";

    @Override
    public int getIdByRole(Roles roleStatus) {

        int role_id = -1;
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();

            PreparedStatement ps = connection.prepareStatement(ROLE_SELECT_SQL);
            ps.setString(1, roleStatus.toString().toLowerCase());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role_id = rs.getInt(1);
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
        return role_id;
    }
}
