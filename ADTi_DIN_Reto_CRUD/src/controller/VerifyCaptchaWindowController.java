/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exception.OurException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Random;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Admin;
import model.User;

/**
 * FXML Controller class
 *
 * @author 2dami
 */
public class VerifyCaptchaWindowController implements Initializable {

    private Controller controller;
    private User user;
    private int code;

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
     * Asigna el controlador principal.
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int randomCode() {
        Random random = new Random();
        code = random.nextInt(9999 - 0 + 1) + 0;
        return code;
    }

    public void confirmButton(ActionEvent event) {

        if (codeTextField.getText().equals(String.valueOf(code))) {
            try {
                controller.removeUser(user);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
                if(user != user){ // Compare with the static user
                    try {
                        Parent parentWindow = FXMLLoader.load(getClass().getResource("/view/AdminWindow.fxml"));
                        Stage actualWindow = (Stage) confirmBttn.getScene().getWindow();
                        actualWindow.setScene(new Scene(parentWindow));
                    } catch (IOException ex) {
                        Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (OurException ex) {
                Logger.getLogger(VerifyCaptchaWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            errorLabel.setText("Incorrect code. Try again.");
            codeLabel.setText(String.valueOf(randomCode()));
        }
    }

    public void cancellButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
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
