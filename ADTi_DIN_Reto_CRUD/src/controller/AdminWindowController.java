package controller;

import exception.OurException;
import exception.ShowAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Admin;
import model.User;

/**
 *
 * @author 2dami
 */
public class AdminWindowController implements Initializable {
    private Controller controller;
    private Admin admin;
    private ArrayList<User> users;

    private Label label;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private RadioButton otherRadioButton;
    @FXML
    private TextField cardNumber1TextField;
    @FXML
    private TextField cardNumber2TextField;
    @FXML
    private TextField cardNumber3TextField;
    @FXML
    private TextField cardNumber4TextField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private ComboBox<String> usersComboBox;
    @FXML
    private Button deleteUserBttn;
    @FXML
    private Button saveChangesBttn;
    @FXML
    private Label username;
    @FXML
    private Button logOutBttn;

    /**
     * Asigna el controlador principal.
     * @param controller
     */
    public void setController(Controller controller)
    {
        this.controller = controller;
        admin = Admin.getInstance();
        username.setText(admin.getUsername());
        getUsers();
    }

    public void getUsers()
    {
        try
        {
            users = controller.getUsers();
        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", "Error getting users: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
        
        users.forEach(user -> usersComboBox.getItems().add(user.getUsername()));
    }
    
    public void logOut()
    {
        this.admin = null;
        User.clearInstance();

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent window = loader.load();
            LoginWindowController loginController = loader.getController();
            loginController.setController(this.controller);
            Stage currentwindow = (Stage) logOutBttn.getScene().getWindow();
            currentwindow.setScene(new Scene(window));
            
            ShowAlert.showAlert("Ok", "Logout successfully", Alert.AlertType.INFORMATION);
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error trying to logout: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }
}