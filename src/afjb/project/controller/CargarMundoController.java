/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.controller;

import afjb.project.model.Model_Mundo;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 *
 * @author USER
 */
public class CargarMundoController implements Initializable {

    @FXML
    private TextField nombre_archivo;
    
    @FXML
    private Parent contenedor;
    
    @FXML
    private Pane pantalla_scena;
    
    @FXML
    private Label numero_disparos_pan;
    
    @FXML
    private Label no_disparos_label;
    
    @FXML
    private Button btn_jugar;
    
    private Integer disparos;
    
    private byte columnas_number = 10;
    private byte filas_number = 10;

    private byte[][] environment = new byte[10][10];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void jugar_mundo(){
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
//            String msg = "Selecciona el archivo donde esta el mundo que deseas visualizar";
//            alerta.setAlertType(Alert.AlertType.INFORMATION);
//            alerta.setHeaderText(msg);
//            alerta.showAndWait();

            Model_Mundo modelo = new Model_Mundo(0, environment, disparos);

            Map<String, Object> params = new HashMap<>();
            params.put("modelo", modelo);
            NavigatorController navigatorController = (NavigatorController) this.contenedor.getUserData();
            navigatorController.swithUI("jugar_mundo", params);
    }

    public void leer_archivo() throws IOException {
        
        pantalla_scena.getChildren().removeAll();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Mundo");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Fields", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        // Obtener la imagen seleccionada
        NavigatorController navigatorController = (NavigatorController) this.contenedor.getUserData();
        File file = fileChooser.showOpenDialog(navigatorController.getStage());
        this.nombre_archivo.setText(file.getAbsolutePath());
        
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        Line linea_prueba1 = new Line(0,0,0,300);
        Line linea_prueba2 = new Line(0,0,300,0);
       
        String linea = br.readLine();
        disparos = Integer.parseInt(linea);
        String linea2 = br.readLine();
        int i = 0;
        String[] columnas;
        while (linea2 != null) {
            columnas = linea2.split(" ");
            for (int j = 0; j < 10; j++) {
                environment[i][j] = Byte.parseByte(columnas[j]);
            }
            i++;
            linea2 = br.readLine();
        }
        
        for (int j = 0; j < this.filas_number; j++) {
            for (int k = 0; k < this.columnas_number; k++) {
                Rectangle cuadro = new Rectangle(30, 30);
                switch(environment[j][k]){
                    case 0:
                        cuadro.setFill(Color.WHITE);
                        cuadro.setX(k*30);
                        cuadro.setY(j*30);
                        pantalla_scena.getChildren().add(cuadro);
                        break;
                    case 1:
                        cuadro.setFill(Color.GREEN);
                        cuadro.setX(k*30);
                        cuadro.setY(j*30);
                        pantalla_scena.getChildren().add(cuadro);
                        break;
                    case 2:
                        cuadro.setFill(Color.BLUE);
                        cuadro.setX(k*30);
                        cuadro.setY(j*30);
                        pantalla_scena.getChildren().add(cuadro);
                        break;
                    case 3:
                        cuadro.setFill(Color.RED);
                        cuadro.setX(k*30);
                        cuadro.setY(j*30);
                        pantalla_scena.getChildren().add(cuadro);
                        break;
                    case 4:
                        cuadro.setFill(Color.YELLOW);
                        cuadro.setX(k*30);
                        cuadro.setY(j*30);
                        pantalla_scena.getChildren().add(cuadro);
                        break;
                        
                }
            }
        }
        
        pantalla_scena.getChildren().add(linea_prueba1);
        pantalla_scena.getChildren().add(linea_prueba2);
        numero_disparos_pan.setText(Integer.toString(disparos));
        no_disparos_label.setVisible(true);
        btn_jugar.setDisable(false);
        
        
        for (int h = 1; h <= this.columnas_number; h++) {
            Line line_pro_1 = new Line((h*30),0,(h*30),300);
            line_pro_1.setFill(Color.BLACK);
            Line line_pro_2 = new Line(0,(30 * h),300,(30 * h));
            line_pro_2.setFill(Color.BLACK);
            pantalla_scena.getChildren().add(line_pro_1);
            pantalla_scena.getChildren().add(line_pro_2);
        }
        
    }

}
