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

/**
 *
 * @author 2dami
 */
public class AdminWindowController implements Initializable
{

    private Controller controller;
    private Admin admin;
    private ArrayList<User> users;
    private User selectedUser;
    
    @FXML
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
    private TextField lastnameTextField;
    @FXML
    private TextField telephoneTextField;
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
    private ComboBox<User> usersComboBox;
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
     *
     * @param controller
     */
    public void setController(Controller controller)
    {
        this.controller = controller;

        admin = (Admin) LoggedProfile.getInstance().getProfile();
        username.setText(admin.getUsername());
        getUsers();
    }

    public void getUsers()
    {
        try
        {
            users = controller.getUsers();
            usersComboBox.getItems().clear();
            usersComboBox.getItems().addAll(users);
        }
        catch (OurException ex)
        {
            ShowAlert.showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearUserFields()
    {
        usernameTextField.clear();
        emailTextField.clear();
        nameTextField.clear();
        lastnameTextField.clear();
        telephoneTextField.clear();
        passwordPasswordField.clear();
        cardNumber1TextField.clear();
        cardNumber2TextField.clear();
        cardNumber3TextField.clear();
        cardNumber4TextField.clear();
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
        if (selectedUser != null)
        {
            usernameTextField.setText(selectedUser.getUsername());
            emailTextField.setText(selectedUser.getEmail());
            nameTextField.setText(selectedUser.getName());
            lastnameTextField.setText(selectedUser.getLastname());
            telephoneTextField.setText(selectedUser.getTelephone());
            passwordPasswordField.setText(selectedUser.getPassword());

            switch (selectedUser.getGender())
            {
                case MALE: maleRadioButton.setSelected(true);
                    break;
                case FEMALE: femaleRadioButton.setSelected(true);
                    break;
                case OTHER: otherRadioButton.setSelected(true);
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
    }

    @FXML
    public void saveChanges()
    {
        if (selectedUser == null)
        {
            ShowAlert.showAlert("Error", "Please select a user to update.", Alert.AlertType.ERROR);
            return;
        }

        String username = usernameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String lastname = lastnameTextField.getText().trim();
        String phone = telephoneTextField.getText().trim();
        String password = passwordPasswordField.getText().trim();

        Gender gender = Gender.OTHER;
        if (maleRadioButton.isSelected())
        {
            gender = Gender.MALE;
        }
        else if (femaleRadioButton.isSelected())
        {
            gender = Gender.FEMALE;
        }

        String card = cardNumber1TextField.getText()
                + cardNumber2TextField.getText()
                + cardNumber3TextField.getText()
                + cardNumber4TextField.getText();

        selectedUser.setUsername(username);
        selectedUser.setEmail(email);
        selectedUser.setName(name);
        selectedUser.setLastname(lastname);
        selectedUser.setTelephone(phone);
        selectedUser.setPassword(password);
        selectedUser.setGender(gender);
        selectedUser.setCard(card);

        try
        {
            boolean success = controller.updateUser(selectedUser);

            if (success)
            {
                ShowAlert.showAlert("Success", "User updated successfully.", Alert.AlertType.INFORMATION);
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

            verifyController.setOnUserDeletedCallback(() ->
            {
                refreshUserList();
            });

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
            ShowAlert.showAlert("Error", "Error opening Confim window.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void logOut()
    {
        LoggedProfile.getInstance().clear();
        this.admin = null;
        this.selectedUser = null;

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent window = loader.load();
            LoginWindowController loginController = loader.getController();
            loginController.setController(controller);
            Stage currentwindow = (Stage) logOutBttn.getScene().getWindow();
            currentwindow.setScene(new Scene(window));
        }
        catch (IOException ex)
        {
            ShowAlert.showAlert("Error", "Error trying to logout.", Alert.AlertType.ERROR);
        }
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
            if (selectedUser != null)
            {
                loadUserData();
            }
        });
        
        configureCardNumber();
    }
}
