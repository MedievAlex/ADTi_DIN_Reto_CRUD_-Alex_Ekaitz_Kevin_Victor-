/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginWindowController;

/**
 *
 * @author 2dami
 */
public class Controller {
    /**
     * Crea y muestra la ventana de login.
     *
     * @param stage
     * @throws IOException
     */
    public void showWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowLogin.fxml"));
        Parent root = loader.load();

        LoginWindowController loginController = loader.getController();
        loginController.setController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}