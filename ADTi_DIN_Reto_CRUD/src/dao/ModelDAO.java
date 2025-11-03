package dao;

import exception.OurException;
import java.util.ArrayList;
import model.Profile;
import model.User;

/**
 *
 * @author Ekasestao
 */
public interface ModelDAO {
    public ArrayList<User> getUsers() throws OurException;
    
    public boolean updateUser(User user) throws OurException;
    
    public boolean deleteUser(User user) throws OurException;
    
    public Profile login(String username, String password) throws OurException;
    
    public User register(User user) throws OurException;
}