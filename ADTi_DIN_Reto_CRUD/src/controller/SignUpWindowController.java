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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Gender;
import model.User;

public class SignUpWindowController implements Initializable
{

    private Controller controller;

    @FXML
    private Pane leftPane;
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
    private TextField lastnameTextField;
    @FXML
    private TextField telephoneTextField;
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
    private Button signUpBttn;
    @FXML
    private Button logInBttn;

    private final String ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 2px;";
    private final String NORMAL_STYLE = "-fx-border-color: null;";

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @FXML
    public void handleSignUp()
    {
        if (!validateFields()) {
            ShowAlert.showAlert("Validation Error", 
                "Please fill all required fields correctly:\n\n" +
                "- Telephone must be exactly 9 digits\n" +
                "- Password must be at least 8 characters with uppercase, lowercase and numbers\n" +
                "- Card must be exactly 16 digits",
                Alert.AlertType.ERROR);
            return;
        }

        String username = usernameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordPasswordField.getText().trim();
        String name = nameTextField.getText().trim();
        String lastname = lastnameTextField.getText().trim();
        String telephone = telephoneTextField.getText().trim();

        Gender gender = Gender.OTHER;
        if (maleRadioButton.isSelected())
        {
            gender = Gender.MALE;
        }
        else if (femaleRadioButton.isSelected())
        {
            gender = Gender.FEMALE;
        }

        String cardNumber = cardNumber1TextField.getText()
                + cardNumber2TextField.getText()
                + cardNumber3TextField.getText()
                + cardNumber4TextField.getText();

        try
        {
            User user = new User(email, username, password, name, lastname, telephone, gender, cardNumber);

            if (controller.register(user) != null)
            {
                ShowAlert.showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
                resetFieldStyles();
                openLogin();
            }
            else
            {
                ShowAlert.showAlert("Error", "User registration failed. Please try again.", Alert.AlertType.ERROR);
            }

        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void openLogin()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent parentWindow = loader.load();
            LoginWindowController nextController = loader.getController();
            nextController.setController(controller);

            Stage actualWindow = (Stage) logInBttn.getScene().getWindow();
            actualWindow.setTitle("Logi In");
            actualWindow.setResizable(false);
            actualWindow.setScene(new Scene(parentWindow));
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error opening Login window.", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validateFields()
    {
        boolean isValid = true;
        resetFieldStyles();

        if (usernameTextField.getText().trim().isEmpty())
        {
            usernameTextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        if (emailTextField.getText().trim().isEmpty() || !isValidEmail(emailTextField.getText().trim()))
        {
            emailTextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        if (nameTextField.getText().trim().isEmpty())
        {
            nameTextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        if (lastnameTextField.getText().trim().isEmpty())
        {
            lastnameTextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        if (telephoneTextField.getText().trim().isEmpty() || !isValidTelephone(telephoneTextField.getText().trim()))
        {
            telephoneTextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        if (passwordPasswordField.getText().trim().isEmpty() || !isValidPassword(passwordPasswordField.getText().trim()))
        {
            passwordPasswordField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        String card = cardNumber1TextField.getText() + cardNumber2TextField.getText()
                + cardNumber3TextField.getText() + cardNumber4TextField.getText();

        if (card.isEmpty() || card.length() != 16)
        {
            cardNumber1TextField.setStyle(ERROR_STYLE);
            cardNumber2TextField.setStyle(ERROR_STYLE);
            cardNumber3TextField.setStyle(ERROR_STYLE);
            cardNumber4TextField.setStyle(ERROR_STYLE);
            isValid = false;
        }

        return isValid;
    }
    
    private void resetFieldStyles()
    {
        usernameTextField.setStyle(NORMAL_STYLE);
        emailTextField.setStyle(NORMAL_STYLE);
        nameTextField.setStyle(NORMAL_STYLE);
        lastnameTextField.setStyle(NORMAL_STYLE);
        telephoneTextField.setStyle(NORMAL_STYLE);
        passwordPasswordField.setStyle(NORMAL_STYLE);
        cardNumber1TextField.setStyle(NORMAL_STYLE);
        cardNumber2TextField.setStyle(NORMAL_STYLE);
        cardNumber3TextField.setStyle(NORMAL_STYLE);
        cardNumber4TextField.setStyle(NORMAL_STYLE);
    }
    
    private boolean isValidEmail(String email)
    {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidTelephone(String telephone)
    {
        return telephone.matches("^[0-9]{9}$");
    }

    private boolean isValidPassword(String password)
    {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
    
    private void configureTelephone()
    {
        telephoneTextField.textProperty().addListener((obs, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*"))
            {
                telephoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
                return;
            }

            if (newValue.length() > 9)
            {
                telephoneTextField.setText(oldValue);
            }
        });
    }
    
    private void configureCardNumber()
    {
        TextField[] cardFields =
        {
            cardNumber1TextField, cardNumber2TextField, cardNumber3TextField, cardNumber4TextField
        };

        for (int i = 0; i < cardFields.length; i++)
        {
            final TextField currentField = cardFields[i];
            final TextField prevField = (i > 0) ? cardFields[i - 1] : null;
            final TextField nextField = (i < cardFields.length - 1) ? cardFields[i + 1] : null;

            currentField.textProperty().addListener((obs, oldValue, newValue) ->
            {
                // Filter for only numbers
                if (!newValue.matches("\\d*"))
                {
                    currentField.setText(newValue.replaceAll("[^\\d]", ""));
                    return;
                }

                // Filter for no more than 4 characters
                if (newValue.length() > 4)
                {
                    currentField.setText(oldValue);
                    return;
                }

                // Filter for change TextField when there are 4 characters
                if (newValue.length() == 4 && nextField != null)
                {
                    nextField.requestFocus();
                    nextField.positionCaret(nextField.getText().length()); // When change to the next TextField dont select all the content 
                }

                // Filter to change TextField when you deleted all the characters
                if (newValue.isEmpty() && prevField != null)
                {
                    prevField.requestFocus();
                    prevField.positionCaret(prevField.getText().length()); // When change to the previous TextField dont select all the content
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configureCardNumber();
        configureTelephone();
    }
}
