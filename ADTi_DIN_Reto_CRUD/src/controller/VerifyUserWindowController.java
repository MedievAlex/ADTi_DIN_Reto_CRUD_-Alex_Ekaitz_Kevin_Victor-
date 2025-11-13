package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import model.LoggedProfile;
import model.Profile;

/**
 *
 * @author 2dami
 */
public class VerifyUserWindowController implements Initializable {

    private Controller controller;
    private Profile profile;
    private int userDelete;
    private Runnable onUserDeletedCallback;

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
     * Is like a constructor.
     *
     * @param controller
     * @param userDelete
     */
    public void setController(Controller controller, int userDelete) {
        this.controller = controller;
        this.userDelete = userDelete;
        
        profile = LoggedProfile.getInstance().getProfile();
        username.setText(profile.getUsername());
    }

    public void setOnUserDeletedCallback(Runnable callback) {
        onUserDeletedCallback = callback;
    }

    @FXML
    public void confirmButton() {
        String password = passwordPasswordField.getText().trim();

        if (password.isEmpty()) {
            errorLabel.setText("Enter your password.");
            return;
        }
        
        if (profile.getPassword().equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyActionWindow.fxml"));
                Parent parentWindow = loader.load();
                
                VerifyActionWindowController nextController = loader.getController();
                nextController.setController(controller, userDelete);
                
                if (onUserDeletedCallback != null) {
                    nextController.setOnUserDeletedCallback(onUserDeletedCallback);
                }
                
                Stage actualWindow = (Stage) confirmBttn.getScene().getWindow();
                actualWindow.setTitle("Verify your Action");
                actualWindow.setResizable(false);
                actualWindow.setScene(new Scene(parentWindow));
            } catch (IOException ex) {
                errorLabel.setText("Error loading window.");
            }
        } else {
            errorLabel.setText("Incorrect password.");
        }
    }

    @FXML
    public void cancelButton() {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
