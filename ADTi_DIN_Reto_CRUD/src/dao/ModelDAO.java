package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Profile;
import model.User;

/**
 *
 * @author Ekasestao
 */
public interface ModelDAO {
    public Profile login(String username, String password);
    
    public Profile register(User user);
    
    public ArrayList<User> getUsers() throws SQLException;
    
    public boolean removeUser(User user);
    
    public boolean verifyPassword(User user, String password);
}