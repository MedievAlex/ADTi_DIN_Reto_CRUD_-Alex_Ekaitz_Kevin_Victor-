/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author 2dami
 */
public class VerifyActionWindowController implements Initializable {
    private Controller controller;
    private User user;

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
     * Asigna el controlador principal.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void confirmButton(ActionEvent event) {
         
        
        try { // Opens the next window
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyCapchaWindow.fxml"));
            Parent root = loader.load();   
            
            view.VerifyCapchaWindowController verifyCapchaWindow = loader.getController();
            verifyCapchaWindow.setUser(user);

            Scene scene = new Scene(root);
            stage.setScene(scene);          

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cancellButton(ActionEvent event) {

    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
