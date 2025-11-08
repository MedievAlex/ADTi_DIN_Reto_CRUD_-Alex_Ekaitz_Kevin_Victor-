package controller;

import exception.OurException;
import exception.ShowAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import model.Profile;
import model.User;

/**
 *
 * @author 2dami
 */
public class LoginWindowController implements Initializable
{
    private Controller controller;

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
    private Button signUpBttn;

    public void setController(Controller controller)
    {
        this.controller = controller;
    }
    
    @FXML
    private void handleLogin()
    {
        String credential = credentialTextField.getText();
        String password = passwordPasswordField.getText();

        if (credential.isEmpty() || password.isEmpty())
        {
            ShowAlert.showAlert("Error", "Please fill all the fields.", Alert.AlertType.ERROR);
            return;
        }

        try
        {
            Profile loggedIn = controller.login(credential, password);

            if (loggedIn != null)
            {
                boolean isUser = loggedIn instanceof User;
                String fxmlPath = isUser ? "/view/UserWindow.fxml" : "/view/AdminWindow.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent window = loader.load();

                if (isUser)
                {
                    ((UserWindowController) loader.getController()).setController(this.controller);
                }
                else
                {
                    ((AdminWindowController) loader.getController()).setController(this.controller);
                }

                Stage currentwindow = (Stage) logInBttn.getScene().getWindow();
                currentwindow.setTitle(isUser ? "User" : "Admin");
                currentwindow.setScene(new Scene(window));
            }
            else
            {
                ShowAlert.showAlert("Error", "Incorrect credentials.", Alert.AlertType.ERROR);
            }

        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error opening window.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void openSignUp()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
            Parent parentWindow = loader.load();

            SignUpWindowController nextController = loader.getController();
            nextController.setController(controller);

            Stage actualWindow = (Stage) signUpBttn.getScene().getWindow();
            actualWindow.setTitle("Sign Up");
            actualWindow.setScene(new Scene(parentWindow));
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error opening Sign Up window.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }
}
