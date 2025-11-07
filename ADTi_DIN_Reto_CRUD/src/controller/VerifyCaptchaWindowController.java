package controller;

import exception.OurException;
import exception.ShowAlert;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LoggedProfile;
import model.Profile;

/**
 * FXML Controller class
 *
 * @author 2dami
 */
public class VerifyCaptchaWindowController implements Initializable
{

    private Controller controller;
    private Profile profile;
    private int code, userDelete;
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
    private Label codeLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField codeTextField;

    /**
     * Is like a constructor.
     *
     * @param controller
     * @param userDelete
     */
    public void setController(Controller controller, int userDelete)
    {
        this.controller = controller;
        this.userDelete = userDelete;
        
        profile = LoggedProfile.getInstance().getProfile();
        username.setText(profile.getUsername());

        generateAndDisplayCode();
    }

    public void setOnUserDeletedCallback(Runnable callback) {
        onUserDeletedCallback = callback;
    }

    private void generateAndDisplayCode() {
        code = new Random().nextInt(10000);
        codeLabel.setText(String.valueOf(code));
        errorLabel.setText("");
        codeTextField.clear();
    }

    @FXML
    private void confirmButton()
    {
        String inputCode = codeTextField.getText().trim();

        if (inputCode.isEmpty())
        {
            errorLabel.setText("Please enter the code.");
            return;
        }

        if (!inputCode.equals(String.valueOf(code)))
        {
            errorLabel.setText("Incorrect code. Try again.");
            generateAndDisplayCode();
            return;
        }

        try
        {
            boolean success = controller.deleteUser(userDelete != -1 ? userDelete : profile.getId());

            if (success)
            {
                if (onUserDeletedCallback != null)
                {
                    onUserDeletedCallback.run();
                }

                ShowAlert.showAlert("Success", "User deleted successfully.", Alert.AlertType.INFORMATION);

                ((Stage) confirmBttn.getScene().getWindow()).close();
            }
            else
            {
                ShowAlert.showAlert("Error", "User could not be deleted.", Alert.AlertType.ERROR);
            }
        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cancelButton()
    {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }
}
