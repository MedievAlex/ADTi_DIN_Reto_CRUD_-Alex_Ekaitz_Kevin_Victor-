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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Admin;
import model.Gender;
import model.LoggedProfile;
import model.User;

public class AdminWindowController implements Initializable
{

    private Controller controller;
    private Admin admin;
    private ArrayList<User> users;
    private User selectedUser;

    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;
    @FXML
    private Label label;
    @FXML
    private TextField usernameTextField, emailTextField, nameTextField, lastnameTextField, telephoneTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    @FXML
    private TextField cardNumber1TextField, cardNumber2TextField, cardNumber3TextField, cardNumber4TextField;
    @FXML
    private Label usernameLabel, passwordLabel, nameLabel, telephoneLabel, genderLabel, emailLabel, cardNumberLabel;
    @FXML
    private ComboBox<User> usersComboBox;
    @FXML
    private Button deleteUserBttn, saveChangesBttn, logOutBttn;
    @FXML
    private Label username;

    private final String ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 2px;";
    private final String NORMAL_STYLE = "-fx-border-color: null;";

    public void setController(Controller controller)
    {
        this.controller = controller;
        admin = (Admin) LoggedProfile.getInstance().getProfile();
        username.setText(admin.getUsername());
        getUsers();
    }

    private void getUsers()
    {
        try
        {
            users = controller.getUsers();
            usersComboBox.getItems().setAll(users);
        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearUserFields()
    {
        TextField[] textFields =
        {
            usernameTextField, emailTextField, nameTextField, lastnameTextField, telephoneTextField,
            passwordPasswordField, cardNumber1TextField, cardNumber2TextField, cardNumber3TextField, cardNumber4TextField
        };
        for (TextField tf : textFields)
        {
            tf.clear();
        }

        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        otherRadioButton.setSelected(false);

        selectedUser = null;
        usersComboBox.getSelectionModel().clearSelection();
    }

    private void refreshUserList()
    {
        getUsers();
        clearUserFields();
    }

    private void loadUserData()
    {
        if (selectedUser == null)
        {
            return;
        }

        usernameTextField.setText(selectedUser.getUsername());
        emailTextField.setText(selectedUser.getEmail());
        nameTextField.setText(selectedUser.getName());
        lastnameTextField.setText(selectedUser.getLastname());
        telephoneTextField.setText(selectedUser.getTelephone());
        passwordPasswordField.setText(selectedUser.getPassword());

        switch (selectedUser.getGender())
        {
            case MALE:
                maleRadioButton.setSelected(true);
                break;
            case FEMALE:
                femaleRadioButton.setSelected(true);
                break;
            case OTHER:
                otherRadioButton.setSelected(true);
                break;
        }

        if (selectedUser.getCard() != null && selectedUser.getCard().length() == 16)
        {
            cardNumber1TextField.setText(selectedUser.getCard().substring(0, 4));
            cardNumber2TextField.setText(selectedUser.getCard().substring(4, 8));
            cardNumber3TextField.setText(selectedUser.getCard().substring(8, 12));
            cardNumber4TextField.setText(selectedUser.getCard().substring(12, 16));
        }
    }

    @FXML
    public void saveChanges()
    {
        if (selectedUser == null)
        {
            ShowAlert.showAlert("Error", "Please select a user to update.", Alert.AlertType.ERROR);
            return;
        }

        if (!validateFields())
        {
            ShowAlert.showAlert("Validation Error",
                    "Please fill all required fields correctly:\n\n"
                    + "- Telephone must be exactly 9 digits\n"
                    + "- Password must be at least 8 characters with uppercase, lowercase and numbers\n"
                    + "- Card must be exactly 16 digits",
                    Alert.AlertType.ERROR);
            return;
        }

        selectedUser.setName(nameTextField.getText().trim());
        selectedUser.setLastname(lastnameTextField.getText().trim());
        selectedUser.setTelephone(telephoneTextField.getText().trim());
        selectedUser.setPassword(passwordPasswordField.getText().trim());
        selectedUser.setGender(maleRadioButton.isSelected() ? Gender.MALE : femaleRadioButton.isSelected() ? Gender.FEMALE : Gender.OTHER);
        selectedUser.setCard(cardNumber1TextField.getText() + cardNumber2TextField.getText()
                + cardNumber3TextField.getText() + cardNumber4TextField.getText());

        try
        {
            boolean success = controller.updateUser(selectedUser);

            if (success)
            {
                ShowAlert.showAlert("Success", "User updated successfully.", Alert.AlertType.INFORMATION);
                resetFieldStyles();
            }
            else
            {
                ShowAlert.showAlert("Error", "Could not update user.", Alert.AlertType.ERROR);
            }
        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deleteUser()
    {
        if (selectedUser == null)
        {
            ShowAlert.showAlert("Error", "Please select a user to delete.", Alert.AlertType.ERROR);
            return;
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyUserWindow.fxml"));
            Parent root = loader.load();
            VerifyUserWindowController verifyController = loader.getController();
            verifyController.setController(controller, selectedUser.getId());
            verifyController.setOnUserDeletedCallback(this::refreshUserList);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Confirm");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(deleteUserBttn.getScene().getWindow());
            stage.show();
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error opening Confirm window.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void logOut()
    {
        LoggedProfile.getInstance().clear();
        admin = null;
        selectedUser = null;

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent window = loader.load();
            LoginWindowController loginController = loader.getController();
            loginController.setController(controller);
            Stage currentwindow = (Stage) logOutBttn.getScene().getWindow();
            currentwindow.setTitle("Logi In");
            currentwindow.setResizable(false);
            currentwindow.setScene(new Scene(window));
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error trying to logout.", Alert.AlertType.ERROR);
        }
    }

    private boolean validateFields()
    {
        boolean isValid = true;
        resetFieldStyles();
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
        usersComboBox.setOnAction(e ->
        {
            selectedUser = usersComboBox.getValue();
            loadUserData();
        });
        configureCardNumber();
        configureTelephone();
    }
}
