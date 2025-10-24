/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DBImplementation;
import model.ModelDAO;
import model.User;
import view.LoginWindowController;

/**
 *
 * @author 2dami
 */
public class Controller {
    private ModelDAO dao;

    /**
     * Constructor del Controller
     */
    public Controller() {
        dao = new DBImplementation();
    }
    
    public void showWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
        Parent root = loader.load();

        LoginWindowController loginController = loader.getController();
        loginController.setController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public boolean verifyPassword(User user, String password) {
        return dao.verifyPassword(user, password);
    }
}