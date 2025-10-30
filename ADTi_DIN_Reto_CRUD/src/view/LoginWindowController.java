package view;

import controller.Controller;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author 2dami
 */
public class LoginWindowController implements Initializable {
    private Controller controller;
    private Connection con;

    private Label label;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField credentialTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button logInBttn;
    @FXML
    private Button signInBttn;


    /**
     * Asigna el controlador principal.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}