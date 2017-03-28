/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.logic;

/**
 *
 * @author root
 */

import afjb.project.controller.NavigatorController;
import static afjb.project.util.Utilities.LOGGER;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    
    @FXML private VBox borderPane;

    @Override
    public void start(Stage stage) {
//          Parent root = new VBox(new Button());
//        root.autosize();
//        Scene scene = new Scene(root);
        try {
            NavigatorController navigatorController = new NavigatorController(stage);
            navigatorController.swithUI("principal_view", null);

            stage.setResizable(false);
//        stage.setAlwaysOnTop(true);


            stage.setTitle("Proyecto Inteligencia Atificial - 2017");
            stage.show();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
