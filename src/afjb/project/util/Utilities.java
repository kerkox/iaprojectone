/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.util;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Sistemas
 */
public class Utilities {

    public static Logger LOGGER = null;

    static {
        LOGGER = Logger.getLogger("logger");
        try {
            Handler handler = new FileHandler("logger.log");
//            Logger.getLogger("").addHandler(handler);
            LOGGER.addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Alert alert = null;

    public static void showAlert(Alert.AlertType type, String message) {
        showAlert(type, null, message);
    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        if (alert == null) {
            Utilities.alert = new Alert(Alert.AlertType.NONE);
        }
        Utilities.alert.setAlertType(type);
        Utilities.alert.setHeaderText(message);
        Utilities.alert.setTitle(title == null ? "" : title);
        Utilities.alert.showAndWait();
    }

}
