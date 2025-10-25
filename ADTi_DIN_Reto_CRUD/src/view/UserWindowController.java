package view;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

/**
 *
 * @author 2dami
 */
public class UserWindowController implements Initializable
{
    private Controller controller;
    private Connection con;
    private User user;

    private Label label;
    @FXML
    private Pane leftPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Button deleteUserBttn;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneTextField;
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
    private Button saveChangesBttn;
    @FXML
    private Label username;
    @FXML
    private Button logOutBttn;

    /**
     * Asigna el controlador principal.
     *
     * @param controller
     */
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setData()
    {
        username.setText(user.getUsername());
        usernameTextField.setText(user.getUsername());
        emailTextField.setText(user.getEmail());
        passwordPasswordField.setText(user.getPassword());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getLastname());
        phoneTextField.setText(String.valueOf(user.getTelephone()));

    }

    public void logOut()
    {
        this.user = null;
        
        try
        {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/view/LoginWindow.fxml"));
            Stage currentStage = (Stage) logOutBttn.getScene().getWindow();
            currentStage.setScene(new Scene(loginRoot));
        }
        catch (IOException e)
        {
            System.err.println("Error loading login window: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setData();
    }
}
