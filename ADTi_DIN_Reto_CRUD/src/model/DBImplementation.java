package model;

import java.sql.*;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO
{

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
    final String SQLUPDATE_ADMIN = "UPDATE db_admin SET A_CURRENT_ACCOUNT = ? WHERE A_ID = ?";
    
    /**
     * SQL Queries: DELETES
     */
    final String SQLDELETE_USER = "DELETE FROM db_profile WHERE P_ID = ?";

    public void metodoPrueba()
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

        }
        catch (SQLException ex)
        {

        }
    }
}
