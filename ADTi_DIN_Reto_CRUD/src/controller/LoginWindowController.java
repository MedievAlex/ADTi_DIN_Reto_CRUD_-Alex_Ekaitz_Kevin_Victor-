package controller;

import exception.OurException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import model.Profile;
import model.User;
import pool.ConnectionPool;

/**
 *
 * @author 2dami
 */
public class LoginWindowController implements Initializable
{

    private Controller controller;
    private Connection con;

    private Label label;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField credentialTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button logInBttn;
    @FXML
    private Button signInBttn;

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    private void handleButtonAction(ActionEvent event) throws SQLException
    {
        con = ConnectionPool.getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    public void test(ActionEvent event)
    {
        try
        {
            Parent ventana = FXMLLoader.load(getClass().getResource("/view/VerifyUserWindow.fxml"));
            Stage currentventana = (Stage) logInBttn.getScene().getWindow();
            currentventana.setScene(new Scene(ventana));
        }
        catch (IOException ex)
        {
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String credential = credentialTextField.getText();
        String password = passwordPasswordField.getText();

        if (credential.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }

        try {
            Profile loggedIn = controller.login(credential, password);

            if (loggedIn != null) {
                String windowPath;
                showAlert("Ok", "Correct login!", Alert.AlertType.INFORMATION);
                if (loggedIn instanceof User)
                {
                   windowPath = "/view/UserWindow.fxml";
                }
                else
                {
                    windowPath = "/view/AdminWindow.fxml";
                }
                
                Parent window;
                try {
                    window = FXMLLoader.load(getClass().getResource(windowPath));
                    Stage currentventana = (Stage) logInBttn.getScene().getWindow();
                    currentventana.setScene(new Scene(window));
                }
                catch (IOException ex) {
                    showAlert("Error", "Error trying to open the window: " + ex.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Error", "Incorrect credentials", Alert.AlertType.ERROR);
            }

        } catch (OurException ex) {
            showAlert("Error", "Error in login: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
