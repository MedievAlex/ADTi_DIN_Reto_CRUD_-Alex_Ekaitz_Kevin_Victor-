package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO {

    private static final int WAITMS = 30000; // 30 seconds

    /**
     * SQL Queries: INSERTS
     */
    final String SQLINSERT_PROFILE = "INSERT INTO db_profile () VALUES ()";
    final String SQLINSERT_USER = "INSERT INTO db_user () VALUES ()";

    /**
     * SQL Queries: SELECTS
     */
    final String SQLSELECT_PASSWORD = "SELECT * FROM db_profile WHERE P_USERNAME = ? AND P_PASSWORD = ?";
    final String SQLSELECT_USERS = "SELECT * FROM db_profile JOIN db_user ON P_ID = U_ID";
    final String SQLSELECT_USER = "SELECT * FROM db_profile JOIN db_user ON P_ID = U_ID WHERE P_EMAIL = ? OR P_USERNAME = ?";
    final String SQLSELECT_ADMIN = "SELECT * FROM db_profile JOIN db_admin ON P_ID = A_ID WHERE P_EMAIL = ? OR P_USERNAME = ?";

    /**
     * SQL Queries: UPDATES
     */
    final String SQLUPDATE_PROFILE = "UPDATE db_profile SET P_PASSWORD = ?, P_NAME = ?, P_LASTNAME = ?, P_TELEPHONE = ? WHERE P_ID = ?";
    final String SQLUPDATE_USER = "UPDATE db_user SET U_GENDER = ?, U_CARD = ? WHERE U_ID = ?";

    /**
     * SQL Queries: DELETES
     */
    final String SQLDELETE_USER = "DELETE FROM db_profile WHERE P_ID = ?";

    public boolean verifyPassword(User user, String password, Connection con) {
        boolean valid = false;
        try (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_PASSWORD);
            ) {
            ResultSet rs;
            stmt.setString(1, user.getUsername());
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            valid = rs.next();
            rs.close();
        } catch (SQLException ex) {

        }
        return valid;
    }

    public boolean insertUser(User user, Connection con) {
        try (
                PreparedStatement stmtProfile = con.prepareStatement(SQLINSERT_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLINSERT_USER)) {

        } catch (SQLException ex) {

        }

        return true;
    }

    public List<User> selectUsers() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);) {
            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }

        return new ArrayList<>();
    }

    public User selectUser() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_USER);) {
            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }

        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }

    public Admin selectAdmin() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_ADMIN);) {
            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }

        return new Admin("", "", "", "", "", 123456789, "");
    }

    public boolean updateUser() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmtProfile = con.prepareStatement(SQLUPDATE_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLUPDATE_USER)) {
            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }

        return true;
    }

    public boolean deleteUser() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLDELETE_USER);) {
            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }

        return true;
    }

    public void login() {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                }
            }

            Thread.sleep(WAITMS);
        } catch (SQLException | InterruptedException ex) {

        }
    }
}
