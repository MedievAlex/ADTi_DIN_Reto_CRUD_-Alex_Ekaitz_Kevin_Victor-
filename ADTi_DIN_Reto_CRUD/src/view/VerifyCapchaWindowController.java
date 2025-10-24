/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.io.IOException;
import static java.lang.Math.random;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
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
import model.User;

/**
 * FXML Controller class
 *
 * @author 2dami
 */
public class VerifyCapchaWindowController implements Initializable {
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
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public int randomCode() {
        Random random = new Random();   
        code = random.nextInt(9999-0+1)+0;
        return code;
    }
    
    public void confirmButton(ActionEvent event) {
         
        if (codeTextField.getText().equals(String.valueOf(code)))
        {
            errorLabel.setText("Correct code.");
        }
        else
        {
            errorLabel.setText("Incorrect code. Try again.");
            codeLabel.setText(String.valueOf(randomCode()));
        }
    }
    
    public void cancellButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codeLabel.setText(String.valueOf(randomCode()));
    }
}
