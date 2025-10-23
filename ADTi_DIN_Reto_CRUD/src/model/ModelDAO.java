package model;

/**
 *
 * @author Ekasestao
 */
public interface ModelDAO {
    
    public boolean verifyPassword(User user, String password);
    
}