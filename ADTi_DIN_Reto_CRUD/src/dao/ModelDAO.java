package dao;

import exception.OurException;
import java.util.ArrayList;
import model.Profile;
import model.User;

/**
 *
 * @author Alex, Ekaitz, Kevin, Victor
 */
public interface ModelDAO {
    public ArrayList<User> getUsers() throws OurException;

    public boolean updateUser(User user) throws OurException;

    public boolean deleteUser(int id) throws OurException;

    public Profile login(String credential, String password) throws OurException;

    public User register(User user) throws OurException;
}