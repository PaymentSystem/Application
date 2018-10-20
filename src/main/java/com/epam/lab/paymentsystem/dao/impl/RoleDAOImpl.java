package com.epam.lab.paymentsystem.dao.impl;

import com.epam.lab.paymentsystem.dao.ConnectionPool;
import com.epam.lab.paymentsystem.dao.RoleDAO;
import com.epam.lab.paymentsystem.entities.enums.Roles;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAOImpl implements RoleDAO {

    private static final String ROLE_SELECT_SQL = "SELECT * FROM roles WHERE role_name = ?";

    @Override
    public int getIdByRole(Roles roleName) {
        int role_id = -1;
        try {
            Connection connection = ConnectionPool.getConnection();

            PreparedStatement ps = connection.prepareStatement(ROLE_SELECT_SQL);
            ps.setString(1, roleName.toString().toLowerCase());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role_id = rs.getInt(1);
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return role_id;
    }
}
