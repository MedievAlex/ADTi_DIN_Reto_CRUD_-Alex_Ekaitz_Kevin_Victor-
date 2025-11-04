/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author 2dami
 */
public class SignUpWindowController implements Initializable {
    private Controller controller;
    
    private Label label;
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
    private Button signUpBttn;
    @FXML
    private Button logInBttn;
    
    
    /**
     * Asigna el controlador principal.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    /*
    public void automaticChange(java.awt.event.KeyEvent evt,TextField cardNumber1, TextField cardNumber2, TextField cardNumber3, TextField cardNumber4){ 
        int limite=4;
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            return;
        }
        if (cardNumber1.getText().length() >= limite) {
            evt.consume();
            cardNumber2.requestFocus();
        } else if (cardNumber2.getText().length() >= limite) {
            evt.consume();
            cardNumber3.requestFocus();
        } else if (cardNumber3.getText().length() >= limite) {
            evt.consume();
            cardNumber4.requestFocus();
        }
    }*/
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    /**
     * Configura los campos de la tarjeta con:
     * - Límite de 4 caracteres por campo
     * - Cambio automático al siguiente campo al alcanzar 4 caracteres
     */
    /**
     * Configura los campos de la tarjeta con:
     * - Límite de 4 caracteres por campo
     * - Cambio automático al siguiente campo al alcanzar 4 caracteres
     * - Validación para permitir solo números
     */
    private void setupCardNumberFields() {
        // Array de los campos de tarjeta en orden
        TextField[] cardFields = {
            cardNumber1TextField,
            cardNumber2TextField,
            cardNumber3TextField,
            cardNumber4TextField
        };
        
        // Configurar cada campo
        for (int i = 0; i < cardFields.length; i++) {
            final TextField currentField = cardFields[i];
            final TextField nextField = (i < cardFields.length - 1) ? cardFields[i + 1] : null;
            
            // Listener para cambio automático y validaciones
            currentField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Si está vacío, permitir
                if (newValue.isEmpty()) {
                    return;
                }
                
                // Validar que solo sean números
                if (!newValue.matches("\\d*")) {
                    currentField.setText(oldValue);
                    return;
                }
                
                // Si excede 4 caracteres, revertir al valor anterior
                if (newValue.length() > 4) {
                    currentField.setText(oldValue);
                    return;
                }
                
                // Si alcanza 4 caracteres y hay siguiente campo, cambiar el focus
                if (newValue.length() >= 4 && nextField != null) {
                    nextField.requestFocus();
                }
            });
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar los campos de la tarjeta
        setupCardNumberFields();
    }
}