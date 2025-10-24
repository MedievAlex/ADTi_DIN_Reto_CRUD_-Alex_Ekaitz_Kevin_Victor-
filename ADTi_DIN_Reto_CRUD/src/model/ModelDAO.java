package model;

import java.sql.Connection;

/**
 *
 * @author Ekasestao
 */
public interface ModelDAO {
    
    public boolean verifyPassword(User user, String password);
    
}