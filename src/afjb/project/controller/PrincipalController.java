/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import com.jfoenix.controls.JFXButton;

/**
 *
 * @author USER
 */
import javafx.event.ActionEvent;
public class PrincipalController implements Initializable{
    
    private final Alert alerta = new Alert(Alert.AlertType.ERROR);
    
     @FXML
    private Parent contenedor;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
    }
    
    public void cambiar_view(){
        String msg = "Selecciona el archivo donde esta el mundo que deseas visualizar";

            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(msg);
            alerta.showAndWait();

            Map<String, Object> params = new HashMap<>();
            params.put("cargar_mundo", true);
            NavigatorController navigatorController = (NavigatorController) this.contenedor.getUserData();
            navigatorController.swithUI("cargar_mundo", params);
    }
    
    
    
    
    
    
}
