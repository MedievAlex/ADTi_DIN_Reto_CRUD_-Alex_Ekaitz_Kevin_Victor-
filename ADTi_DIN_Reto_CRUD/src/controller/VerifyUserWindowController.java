package controller;

import exception.OurException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

/**
 *
 * @author 2dami
 */
public class VerifyUserWindowController implements Initializable {

    private Controller controller;
    private User user;

    @FXML
    private Pane rightPane;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Label username;
    @FXML
    private Button confirmBttn;
    @FXML
    private Button cancelBttn;
    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLabel;

    /**
     * Asigna el controlador principal.
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    public void confirmButton(ActionEvent event) {
        user = new User();
        String password = passwordPasswordField.getText().trim();

        if (password.isEmpty()) {
            errorLabel.setText("Enter your password.");
        } else {
            try {
                if (verifyPassword(user, password)) {
                    try {
                        Parent parentWindow = FXMLLoader.load(getClass().getResource("/view/VerifyActionWindow.fxml"));
                        Stage actualWindow = (Stage) confirmBttn.getScene().getWindow();
                        actualWindow.setScene(new Scene(parentWindow));
                    } catch (IOException ex) {
                        Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    errorLabel.setText("Incorrect password.");
                }
            } catch (OurException ex) {

            }
        }
    }

    @FXML
    public void cancellButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public boolean verifyPassword(User user, String password) throws OurException
    {
        return user.getPassword().equals(password);
    }
    
    public void setUser() {
        this.user = User.getInstance();
        username.setText(user.getUsername());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUser();
    }
}
