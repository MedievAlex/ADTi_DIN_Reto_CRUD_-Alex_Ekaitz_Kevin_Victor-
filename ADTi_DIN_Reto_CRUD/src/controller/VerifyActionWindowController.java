package controller;

import exception.ShowAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LoggedProfile;
import model.Profile;

/**
 * FXML Controller class
 *
 * @author 2dami
 */
public class VerifyActionWindowController implements Initializable {
    private Controller controller;
    private Profile profile;
    private int userDelete;
    private Runnable onUserDeletedCallback;

    @FXML
    private Pane rightPane;
    @FXML
    private Button confirmBttn;
    @FXML
    private Button cancelBttn;
    @FXML
    private Label username;
    @FXML
    private Label titleLabel;
    @FXML
    private Label textLabel;
      
    /**
     * Is like a constructor.
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
    
    public void confirmButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyCaptchaWindow.fxml"));
            Parent parentWindow = loader.load();
            
            VerifyCaptchaWindowController nextController = loader.getController();
            nextController.setController(controller, userDelete);
            
            if (onUserDeletedCallback != null) {
                nextController.setOnUserDeletedCallback(onUserDeletedCallback);
            }
            
            Stage actualWindow = (Stage) confirmBttn.getScene().getWindow();
            actualWindow.setScene(new Scene(parentWindow));
        } catch (IOException ex) {
            ShowAlert.showAlert("Error", "Unable to open verification window.", Alert.AlertType.ERROR);
        }
    }
    
    public void cancelButton() {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
