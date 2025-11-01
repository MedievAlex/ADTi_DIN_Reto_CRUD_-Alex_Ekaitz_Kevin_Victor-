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

    public ArrayList<User> getUsers() throws SQLException
    {
        return dao.getUsers();
    }
    
    public boolean removeUser(User user)
    {
        return dao.removeUser(user);
    }
   
    public boolean verifyPassword(User user, String password) {
        return dao.verifyPassword(user, password);
    }
}
