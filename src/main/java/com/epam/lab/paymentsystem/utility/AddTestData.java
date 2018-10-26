package com.epam.lab.paymentsystem.utility;

import com.epam.lab.paymentsystem.dao.ConnectionPool;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class AddTestData {

    private final static List<String> USERS = new LinkedList<>();
    private final static List<String> ACCOUNT = new LinkedList<>();
    private final static List<Boolean> CARDS = new LinkedList<>();
    private final static String SQL_USERS
            = "INSERT INTO users (login, passwd, id_role, user_name)" + " VALUES (?,?,?,?)";
    private final static String SQL_ACCOUNTS
            = "INSERT INTO accounts (id_user, amount, is_active)" + " VALUES (?,?,?)";
    private final static String SQL_CARDS
            = "INSERT INTO cards (id_user, id_account, is_active)" + " VALUES (?,?,?)";
    private final static String SQL_ID_USER = "SELECT * FROM USERS WHERE login = ?";
    private final static String SQL_ID_ACCOUNT = "SELECT * FROM ACCOUNTS WHERE id_user = ?";
    private final static String SQL_ID_ROLE_USER = "SELECT * FROM USERS WHERE id_role = ?";
    private final static int DATA_COUNT = 5;
    private static Connection connection;

    private static void createData() {

        USERS.addAll(
                Arrays.asList(
                        "zorita,1234,1,Zoy",
                        "chaim,qwer,2,Chester",
                        "lee,4321,2,Lee",
                        "amir,poiu,2,Amir",
                        "habib,12ha,3,Habib"
                ));

        ACCOUNT.addAll(
                Arrays.asList(
                        "4125,true",
                        "7436,true",
                        "3462,true",
                        "9874,true",
                        "5679,false"
                ));

        CARDS.addAll(
                Arrays.asList(
                        true,
                        true,
                        false,
                        true,
                        true
                ));

    }

    private static void generationData() throws SQLException, NamingException {
        connection = ConnectionPool.getConnection();
        int rand = (int) (Math.random() * USERS.size());

        String[] userArray = (USERS.remove(rand)).split(",");
        String loginUser = userArray[0];
        String passwordUser = userArray[1];
        int idRoleUser = Integer.parseInt(userArray[2]);
        String nameUser = userArray[3];

        PreparedStatement psUsers = connection.prepareStatement(SQL_USERS);
        psUsers.setString(1, loginUser);
        psUsers.setString(2, passwordUser);
        psUsers.setInt(3, idRoleUser);
        psUsers.setString(4, nameUser);
        psUsers.executeUpdate();

        String[] accountArray = ACCOUNT.remove(rand).split(",");
        PreparedStatement psIdUser = connection.prepareStatement(SQL_ID_USER);
        psIdUser.setString(1, loginUser);

        ResultSet rsUsers = psIdUser.executeQuery();
        rsUsers.next();
        int idUsers = rsUsers.getInt(1);
        int amountAccounts = Integer.parseInt(accountArray[0]);
        boolean isActiveAccounts = Boolean.parseBoolean(accountArray[1]);

        PreparedStatement psAccount = connection.prepareStatement(SQL_ACCOUNTS);
        psAccount.setInt(1, idUsers);
        psAccount.setInt(2, amountAccounts);
        psAccount.setBoolean(3, isActiveAccounts);
        psAccount.executeUpdate();

        PreparedStatement psIdAccount = connection.prepareStatement(SQL_ID_ACCOUNT);
        psIdAccount.setInt(1, idUsers);

        ResultSet rsAccount = psIdAccount.executeQuery();
        rsAccount.next();
        int idAccount = rsAccount.getInt(1);
        boolean isActive = CARDS.remove(rand);

        PreparedStatement psCards = connection.prepareStatement(SQL_CARDS);
        psCards.setInt(1, idUsers);
        psCards.setInt(2, idAccount);
        psCards.setBoolean(3, isActive);
        psCards.executeUpdate();

        rsAccount.close();
        rsUsers.close();
        ConnectionPool.connectionRelease(connection);
    }

    private static boolean checkRoleAdmin() throws SQLException, NamingException {
        connection = ConnectionPool.getConnection();
        int idRoleAdmin = 1;

        PreparedStatement psIdRoleUser = connection.prepareStatement(SQL_ID_ROLE_USER);
        psIdRoleUser.setInt(1, idRoleAdmin);
        ResultSet rsIdRoleUser = psIdRoleUser.executeQuery();

        if (rsIdRoleUser.next()) {
            return true;
        }
        rsIdRoleUser.close();
        ConnectionPool.connectionRelease(connection);
        return false;
    }

    public static boolean addTestData() {
        try {
            try {
                if (checkRoleAdmin()) {
                    return true;
                }
                createData();
                for (int i = 0; i < DATA_COUNT; i++) {
                    generationData();
                }
                System.out.println("Generation is over");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return false;
    }
}