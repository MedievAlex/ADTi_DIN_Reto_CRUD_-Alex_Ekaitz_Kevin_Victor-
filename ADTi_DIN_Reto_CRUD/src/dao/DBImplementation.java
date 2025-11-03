package dao;

import exception.OurException;
import pool.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Admin;
import model.Gender;
import model.Profile;
import model.User;
import pool.ConnectionThread;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO {

    /**
     * SQL Queries: INSERTS
     */
    final String SQLINSERT_PROFILE = "INSERT INTO db_profile (P_EMAIL, P_USERNAME, P_PASSWORD, P_NAME, P_LASTNAME, P_TELEPHONE) VALUES (?, ?, ?, ?, ?, ?)";
    final String SQLINSERT_USER = "INSERT INTO db_user (U_ID, U_GENDER, U_CARD) VALUES (?, ?, ?)";

    /**
     * SQL Queries: SELECTS
     */
    final String SQLSELECT_USERS = "SELECT * FROM db_profile JOIN db_user ON P_ID = U_ID";
    final String SQLSELECT_PROFILE = "SELECT P_ID, P_EMAIL, P_USERNAME, P_PASSWORD FROM db_profile WHERE P_EMAIL = ? OR P_USERNAME = ?";
    final String SQLSELECT_USER = "SELECT * FROM db_profile p JOIN db_user u ON p.P_ID = u.U_ID WHERE p.P_ID = ?";
    final String SQLSELECT_ADMIN = "SELECT * FROM db_profile p JOIN db_admin a ON p.P_ID = a.A_ID WHERE p.P_ID = ?";
    final String SQLCHECK_CREDENTIALS = "SELECT P_EMAIL, P_USERNAME FROM db_profile WHERE P_EMAIL = ? OR P_USERNAME = ?";

    /**
     * SQL Queries: UPDATES
     */
    final String SQLUPDATE_PROFILE = "UPDATE db_profile SET P_PASSWORD = ?, P_NAME = ?, P_LASTNAME = ?, P_TELEPHONE = ? WHERE P_ID = ?";
    final String SQLUPDATE_USER = "UPDATE db_user SET U_GENDER = ?, U_CARD = ? WHERE U_ID = ?";

    /**
     * SQL Queries: DELETES
     */
    final String SQLDELETE_USER = "DELETE FROM db_profile WHERE P_ID = ?";

    /**
     * SQL Private Methods
     */
    private int insert(Connection con, User user) throws OurException {
        int id = -1;

        try (
                PreparedStatement stmtProfile = con.prepareStatement(SQLINSERT_PROFILE, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtUser = con.prepareStatement(SQLINSERT_USER)) {
            con.setAutoCommit(false);

            stmtProfile.setString(1, user.getEmail());
            stmtProfile.setString(2, user.getUsername());
            stmtProfile.setString(3, user.getPassword());
            stmtProfile.setString(4, user.getName());
            stmtProfile.setString(5, user.getLastname());
            stmtProfile.setString(6, user.getTelephone());
            stmtProfile.executeUpdate();

            try (
                    ResultSet generatedKeys = stmtProfile.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);

                    stmtUser.setInt(1, id);
                    stmtUser.setString(2, user.getGender().name());
                    stmtUser.setString(3, user.getCard());
                    stmtUser.executeUpdate();

                    con.commit();
                }
            }
        } catch (SQLException ex) {
            id = -1;

            try {
                con.rollback();
            } catch (SQLException e) {
            }

            throw new OurException("Error inserting the user: " + ex.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
            }
        }

        return id;
    }

    private ArrayList<User> selectUsers(Connection con) throws OurException {
        ArrayList<User> users = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("P_ID"),
                    rs.getString("P_EMAIL"),
                    rs.getString("P_USERNAME"),
                    rs.getString("P_PASSWORD"),
                    rs.getString("P_NAME"),
                    rs.getString("P_LASTNAME"),
                    rs.getString("P_TELEPHONE"),
                    Gender.valueOf(rs.getString("U_GENDER")),
                    rs.getString("U_CARD")
                );
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new OurException("Error trying to obtain all the users: " + ex.getMessage());
        }

        return users;
    }

    private boolean update(Connection con, User user) throws OurException {
        boolean success = false;

        try (
                PreparedStatement stmtProfile = con.prepareStatement(SQLUPDATE_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLUPDATE_USER)) {
            con.setAutoCommit(false);

            stmtProfile.setString(1, user.getPassword());
            stmtProfile.setString(2, user.getName());
            stmtProfile.setString(3, user.getLastname());
            stmtProfile.setString(4, user.getTelephone());
            stmtProfile.setInt(5, user.getId());

            int profileUpdated = stmtProfile.executeUpdate();

            stmtUser.setString(1, user.getGender().toString());
            stmtUser.setString(2, user.getCard());
            stmtUser.setInt(3, user.getId());

            int userUpdated = stmtUser.executeUpdate();

            if (profileUpdated > 0 && userUpdated > 0) {
                con.commit();
                success = true;
            } else {
                con.rollback();
                success = false;
            }
        } catch (SQLException ex) {
            success = false;

            try {
                con.rollback();
            } catch (SQLException e) {
            }

            throw new OurException("Error trying to update the user: " + ex.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
            }
        }

        return success;
    }

    private boolean delete(Connection con, int userId) throws OurException {
        boolean success;

        try (
                PreparedStatement stmt = con.prepareStatement(SQLDELETE_USER)) {
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException ex) {
            success = false;
            throw new OurException("Error trying to delete the user: " + ex.getMessage());
        }

        return success;
    }

    private HashMap<String, Object> findProfileByCredential(Connection con, String credential) throws OurException {
        try (PreparedStatement stmt = con.prepareStatement(SQLSELECT_PROFILE)) {
            stmt.setString(1, credential);
            stmt.setString(2, credential);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HashMap<String, Object> profile = new HashMap<>();
                    profile.put("P_ID", rs.getInt("P_ID"));
                    profile.put("P_EMAIL", rs.getString("P_EMAIL"));
                    profile.put("P_USERNAME", rs.getString("P_USERNAME"));
                    profile.put("P_PASSWORD", rs.getString("P_PASSWORD"));
                    return profile;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new OurException("Error finding profile: " + ex.getMessage());
        }
    }

    private Profile findProfileByType(Connection con, int profileId) throws OurException {
        try (PreparedStatement stmtUser = con.prepareStatement(SQLSELECT_USER)) {
            stmtUser.setInt(1, profileId);
            try (ResultSet rsUser = stmtUser.executeQuery()) {
                if (rsUser.next()) {
                    User user = User.getInstance();
                    user.setId(rsUser.getInt("P_ID"));
                    user.setEmail(rsUser.getString("P_EMAIL"));
                    user.setUsername(rsUser.getString("P_USERNAME"));
                    user.setPassword(rsUser.getString("P_PASSWORD"));
                    user.setName(rsUser.getString("P_NAME"));
                    user.setLastname(rsUser.getString("P_LASTNAME"));
                    user.setTelephone(rsUser.getString("P_TELEPHONE"));
                    user.setGender(Gender.valueOf(rsUser.getString("U_GENDER")));
                    user.setCard(rsUser.getString("U_CARD"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            throw new OurException("Error finding user profile: " + ex.getMessage());
        }

        try (PreparedStatement stmtAdmin = con.prepareStatement(SQLSELECT_ADMIN)) {
            stmtAdmin.setInt(1, profileId);
            try (ResultSet rsAdmin = stmtAdmin.executeQuery()) {
                if (rsAdmin.next()) {
                    Admin admin = Admin.getInstance();
                    admin.setId(rsAdmin.getInt("P_ID"));
                    admin.setEmail(rsAdmin.getString("P_EMAIL"));
                    admin.setUsername(rsAdmin.getString("P_USERNAME"));
                    admin.setPassword(rsAdmin.getString("P_PASSWORD"));
                    admin.setName(rsAdmin.getString("P_NAME"));
                    admin.setLastname(rsAdmin.getString("P_LASTNAME"));
                    admin.setTelephone(rsAdmin.getString("P_TELEPHONE"));
                    admin.setCurrent_account(rsAdmin.getString("A_CURRENT_ACCOUNT"));
                    return admin;
                }
            }
        } catch (SQLException ex) {
            throw new OurException("Error finding admin profile: " + ex.getMessage());
        }

        throw new OurException("Profile not found");
    }

    private HashMap<String, Boolean> checkCredentialsExistence(Connection con, String email, String username) throws OurException {
        try (PreparedStatement stmt = con.prepareStatement(SQLCHECK_CREDENTIALS)) {
            stmt.setString(1, email);
            stmt.setString(2, username);

            HashMap<String, Boolean> exists = new HashMap<>();
            exists.put("email", false);
            exists.put("username", false);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (email.equals(rs.getString("P_EMAIL"))) {
                        exists.put("email", true);
                    }
                    if (username.equals(rs.getString("P_USERNAME"))) {
                        exists.put("username", true);
                    }
                }
                return exists;
            }
        } catch (SQLException ex) {
            throw new OurException("Error checking credentials: " + ex.getMessage());
        }
    }

    /**
     * SQL Public Methods
     */
    @Override
    public User register(User user) throws OurException {
        try (Connection con = ConnectionPool.getConnection()) {
            Map<String, Boolean> existing = checkCredentialsExistence(con, user.getEmail(), user.getUsername());

            if (existing.get("email") && existing.get("username")) {
                throw new OurException("Both email and username already exist");
            } else if (existing.get("email")) {
                throw new OurException("Email already exists");
            } else if (existing.get("username")) {
                throw new OurException("Username already exists");
            }

            int id = insert(con, user);
            if (id != -1) {
                user.setId(id);
                return user;
            } else {
                throw new OurException("Error creating user");
            }
        } catch (SQLException ex) {
            throw new OurException("Error creating user: " + ex.getMessage());
        }
    }

    @Override
    public Profile login(String credential, String password) throws OurException {
        try (Connection con = ConnectionPool.getConnection()) {
            HashMap<String, Object> profileData = findProfileByCredential(con, credential);
            if (profileData == null) {
                return null;
            }

            String storedPassword = (String) profileData.get("P_PASSWORD");
            if (!storedPassword.equals(password)) {
                return null;
            }

            int profileId = (int) profileData.get("P_ID");
            return findProfileByType(con, profileId);
        } catch (Exception ex) {
            throw new OurException("Login error: " + ex.getMessage());
        }
    }

    @Override
    public ArrayList<User> getUsers() throws OurException {
        try (Connection con = ConnectionPool.getConnection()) {
            return selectUsers(con);
        } catch (SQLException ex) {
            throw new OurException("Error getting users: " + ex.getMessage());
        }
    }

    @Override
    public boolean updateUser(User user) throws OurException {
        ConnectionThread thread = new ConnectionThread(30);
        thread.start();

        try {
            int attempts = 0;

            while (!thread.isReady() && attempts < 50) { // If in 500ms cant get a connection throws timeout exception
                Thread.sleep(10);
                attempts++;
            }

            if (!thread.isReady()) {
                thread.releaseConnection();
                throw new OurException("Timeout waiting the connection");
            }

            Connection con = thread.getConnection();

            boolean result = update(con, user);

            thread.releaseConnection();

            return result;
        } catch (InterruptedException ex) {
            thread.releaseConnection();
            throw new OurException("Error updating the user: " + ex.getMessage());
        }
    }

    @Override
    public boolean deleteUser(User user) throws OurException {
        ConnectionThread thread = new ConnectionThread(30);
        thread.start();

        try {
            int attempts = 0;
            while (!thread.isReady() && attempts < 50) { // If in 500ms cant get a connection throws timeout exception
                Thread.sleep(10);
                attempts++;
            }

            if (!thread.isReady()) {
                thread.releaseConnection();
                throw new OurException("Timeout waiting the connection");
            }

            Connection con = thread.getConnection();

            boolean result = delete(con, user.getId());

            thread.releaseConnection();

            return result;
        } catch (InterruptedException ex) {
            thread.releaseConnection();

            throw new OurException("Error deleting the user: " + ex.getMessage());
        }
    }
}
