package model;

import java.sql.*;

/**
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class DBImplementation implements ModelDAO {

    /**
     * SQL Queries: INSERTS
     */
    final String SQLINSERT_TEACHINGUNIT = "INSERT INTO TeachingUnit (ACRONIM, TITLE, EVALUATION, DESCRIPTION) VALUES (?, ?, ?, ?)";

    /**
     * SQL Queries: SELECTS
     */
    final String SQLSELECT_TEACHINGUNIT = "SELECT * FROM TeachingUnit WHERE acronim = ?";

    public void metodoPrueba()
    {
        try (Connection con = ConnectionPool.getConnection(); PreparedStatement stmt = con.prepareStatement(SQLSELECT_TEACHINGUNIT))
        {

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                }
            }

        }
        catch (SQLException ex)
        {
            
        }
    }
}