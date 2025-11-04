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
import model.Admin;
import model.Profile;
import model.User;

/**
 *
 * @author 2dami
 */
public class VerifyUserWindowController implements Initializable {

    private Controller controller;
    private Profile profile;

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
        if (profile instanceof User)
        {
            profile = User.getInstance();
        }
        else
        {
            profile = Admin.getInstance();
        }

        String password = passwordPasswordField.getText().trim();

        if (password.isEmpty()) {
            errorLabel.setText("Enter your password.");
        } else {
            System.out.println(profile.getPassword());
            System.out.println(profile.getPassword().equals(password));
            if (profile.getPassword().equals(password)) {
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
        }
    }

    @FXML
    public void cancellButton() {
        Stage stage = (Stage) (cancelBttn.getScene().getWindow());
        stage.close();
    }
    
    public void setUser() {
        this.profile = User.getInstance();
        username.setText(this.profile.getUsername());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUser();
    }
}
