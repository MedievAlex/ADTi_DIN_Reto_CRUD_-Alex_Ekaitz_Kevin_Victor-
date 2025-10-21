package model;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO
{
    private static  final int WAITMS = 30000; // 30 seconds

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

    private boolean insertUser(Connection con, User user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLINSERT_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLINSERT_USER)
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return true;
    }
    
    private ArrayList<User> selectUsers(Connection con)
    {
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new ArrayList<>();
    }
    
    private User selectUser(Connection con, String user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_USER);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    private Admin selectAdmin(Connection con, String admin)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLSELECT_ADMIN);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new Admin("", "", "", "", "", 123456789, "");
    }
    
    private boolean updateUser(Connection con, User user)
    {
        try
            (
                PreparedStatement stmtProfile = con.prepareStatement(SQLUPDATE_PROFILE);
                PreparedStatement stmtUser = con.prepareStatement(SQLUPDATE_USER)
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return true;
    }
    
    private boolean deleteUser(Connection con, String user)
    {
        try
            (
                PreparedStatement stmt = con.prepareStatement(SQLDELETE_USER);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return true;
    }
    
    @Override
    public Profile login(String username, String password)
    {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS))
        {

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                }
            }
            
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    @Override
    public Profile register(User user)
    {
        try (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS))
        {

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                }
            }
            
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new User("", "", "", "", "", 123456789, Gender.MALE, "");
    }
    
    @Override
    public ArrayList<User> getUsers()
    {
        try
            (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return new ArrayList<>();
    }
    
    @Override
    public boolean removeUser(User user)
    {
        try
            (
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQLSELECT_USERS);
            )
        {           
            Thread.sleep(WAITMS);
        }
        catch (SQLException | InterruptedException ex)
        {

        }
        
        return true;
    }
}
