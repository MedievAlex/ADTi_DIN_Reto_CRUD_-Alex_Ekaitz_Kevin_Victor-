package model;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO
{
    Connection con;
    
    public DBImplementation() throws SQLException
    {
        this.con = ConnectionPool.getConnection();
    }
    
    /**
     * SQL Queries: INSERTS
     */
    final String SQLINSERT_PROFILE = "INSERT INTO db_profile () VALUES ()";
    final String SQLINSERT_USER = "INSERT INTO db_user () VALUES ()";

    /**
     * SQL Queries: SELECTS
     */
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

    private boolean insertUser(User user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLINSERT_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLINSERT_USER)
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return true;
    }
    
    private ArrayList<User> selectUsers()
    {
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return new ArrayList<>();
    }
    
    private User selectUser(String user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_USER);
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    private Admin selectAdmin(String admin)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_ADMIN);
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return new Admin("", "", "", "", "", 123456789, "");
    }
    
    private boolean updateUser(User user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLUPDATE_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLUPDATE_USER)
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return true;
    }
    
    private boolean deleteUser(String user)
    {
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLDELETE_USER);
            )
        {           
        }
        catch (SQLException ex)
        {

        }
        
        return true;
    }
    
    @Override
    public Profile login(String username, String password)
    {
        try (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS))
        {

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                }
            }
            
        }
        catch (SQLException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    @Override
    public Profile register(User user)
    {
        try (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS))
        {

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                }
            }
            
        }
        catch (SQLException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    @Override
    public ArrayList<User> getUsers() throws SQLException
    {
        ArrayList<User> users = new ArrayList<>();
        
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
                ResultSet rs = stmt.executeQuery();
            )
        {
            while (rs.next()) {
                User user = new User(rs.getInt("P_ID"), rs.getString("U_EMAIL"), rs.getString("U_USERNAME"), rs.getString("PASSWORD"), rs.getString("U_NAME"), rs.getString("U_LASTNAME"), rs.getInt("U_TELEPHONE"), Gender.valueOf(rs.getString("U_GENDER")), rs.getString("U_CARD"));
                users.add(user);
            }
        }
        
        return users;
    }
    
    @Override
    public boolean removeUser(User user)
    {
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
            )
        {
        }
        catch (SQLException ex)
        {

        }
        
        return true;
    }
}
