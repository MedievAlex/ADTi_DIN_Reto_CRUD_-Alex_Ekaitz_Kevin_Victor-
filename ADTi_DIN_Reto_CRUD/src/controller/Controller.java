package controller;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.DBImplementation;
import dao.ModelDAO;
import exception.ErrorMessages;
import exception.OurException;
import model.Profile;
import model.User;
import javafx.scene.image.Image;

/**
 *
 * @author 2dami
 */
public class Controller
{
    private final ModelDAO dao;
    /**
    *
     * @throws exception.OurException
    */
    public Controller() throws OurException
    {
        try {
            dao = new DBImplementation();
        } catch (Exception ex) {
            throw new OurException(ErrorMessages.DATABASE);
        }
    }

    public void showWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
        Parent root = loader.load();

        LoginWindowController loginController = loader.getController();
        loginController.setController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage.show();
    }

    public User register(User user) throws OurException
    {
        return dao.register(user);
    }

    public Profile login(String credential, String password) throws OurException
    {
        return dao.login(credential, password);
    }

    public ArrayList<User> getUsers() throws OurException
    {
        return dao.getUsers();
    }

    public boolean updateUser(User user) throws OurException
    {
        return dao.updateUser(user);
    }

    public boolean deleteUser(int id) throws OurException
    {
        return dao.deleteUser(id);
    }
}
