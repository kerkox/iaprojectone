/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.controller;

import afjb.project.model.Model_Mundo;
import static afjb.project.util.Utilities.LOGGER;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigatorController {

    private final Stage stage;
//    private final Map<String, Scene> fxmlMap = new HashMap<>();
//    private Scene currentUI;

    public NavigatorController(Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage(){
        return this.stage;
    }

    public void swithUI(String fxmlName, Map params) {
//        Scene ui = this.fxmlMap.get(fxmlName);
//        if (ui == null) {
        try {
            Parent parent = FXMLLoader.load(this.getClass().getResource("/afjb/project/views/" + fxmlName + ".fxml"));
            parent.setUserData(this);
            parent.getProperties().put("params", params);
            Scene ui = new Scene(parent);
            this.stage.setScene(ui);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Error ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
