/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import model.User;

/**
 *
 * @author 2dami
 */
public class VerifyUserWindowController implements Initializable {
    private Controller controller;
    private User user;
    
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
     * Asigna el controlador principal.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    @FXML
    public void confirmButton(ActionEvent event) {
        String password = passwordPasswordField.getText().trim();
        
        if (password.isEmpty()) {
            errorLabel.setText("Enter your password.");
        }
        else
        {
            
            try // Opens the next window
                { 
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyActionWindow.fxml"));
                    Parent root = loader.load();   

                    controller.VerifyActionWindowController verifyActionWindow = loader.getController();
                    verifyActionWindow.setUser(user);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);          

                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            
            if (controller.verifyPassword(user, password))
            {
                try // Opens the next window
                { 
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyActionWindow.fxml"));
                    Parent root = loader.load();   

                    controller.VerifyActionWindowController verifyActionWindow = loader.getController();
                    verifyActionWindow.setUser(user);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);          

                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                errorLabel.setText("Incorrect password.");
            }
        }      
    }
    
    @FXML
    public void cancellButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}