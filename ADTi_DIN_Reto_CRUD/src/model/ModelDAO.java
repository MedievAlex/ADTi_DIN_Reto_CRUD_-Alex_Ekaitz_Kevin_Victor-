package model;

import java.util.ArrayList;

/**
 *
 * @author Ekasestao
 */
public interface ModelDAO {
    public Profile login(String username, String password);
    
    public Profile register(User user);
    
    public ArrayList<User> getUsers();
    
    public boolean removeUser(User user);
}