package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.DBImplementation;
import dao.ModelDAO;
import exception.OurException;
import model.Profile;
import model.User;

/**
 *
 * @author 2dami
 */
public class Controller
{
    private final ModelDAO dao;
    
    /**
    * Constructor del Controller
     * @throws java.sql.SQLException
    */
    public Controller() throws SQLException
    {
        dao = new DBImplementation();
    }
    
    public void showWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
        Parent root = loader.load();

        LoginWindowController loginController = loader.getController();
        loginController.setController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public User register(User user) throws OurException
    {
        return dao.register(user);
    }
    
    public Profile login(String username, String password) throws OurException
    {
        return dao.login(username, password);
    }

    public ArrayList<User> getUsers() throws OurException
    {
        return dao.getUsers();
    }
    
    public boolean updateUser(User user) throws OurException
    {
        return dao.updateUser(user);
    }
    
    public boolean removeUser(User user) throws OurException
    {
        return dao.deleteUser(user);
    }
   
    public boolean verifyPassword(User user, String password) throws OurException {
        return dao.verifyPassword(user, password);
    }
}
