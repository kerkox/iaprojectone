/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.controller;

import afjb.project.model.Model_Mundo;
import afjb.project.model.Nodo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class JugarMundoController implements Initializable {

    @FXML
    private JFXComboBox option_algotihm;

    @FXML
    private JFXComboBox option_selected_algorithm;

    @FXML
    private Parent contenedor;

    @FXML
    private Pane mundo_juego_pan;

    @FXML
    private JFXButton btn_aplica_algorithm;

    public Model_Mundo modelo;

    public byte[][] envivorement;

    private NavigatorController navigatorController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.option_algotihm.getItems().add(new Label("Busqueda Informada"));
        this.option_algotihm.getItems().add(new Label("Busqueda no Informada"));
        this.modelo = Model_Mundo.getInstance();
        this.envivorement = modelo.getEnvironment();
        this.mundo_juego_pan.setFocusTraversable(true);
        dibujar();
    }

    @FXML
    void animation_test(KeyEvent evt) {
//        if(evt.getCode().equals(evt.getCode().UP)){
//            this.modelo.move(this.modelo.UP);
//        }
//        if(evt.getCode().equals(evt.getCode().DOWN)){
//            this.modelo.move(this.modelo.DOWN);
//        }
//        if(evt.getCode().equals(evt.getCode().RIGHT)){
//            this.modelo.move(this.modelo.RIGHT);
//        }
//        if(evt.getCode().equals(evt.getCode().LEFT)){
//            this.modelo.move(this.modelo.LEFT);
//        }
//        dibujar();
    }

    public void dibujar() {

        mundo_juego_pan.getChildren().removeAll(mundo_juego_pan.getChildren());

        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                Rectangle cuadro = new Rectangle(30, 30);
                switch (this.modelo.getEnvironment()[j][k]) {
                    case 0:
                        cuadro.setFill(Color.WHITE);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        break;
                    case 1:
                        cuadro.setFill(Color.GREEN);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        break;
                    case 2:
                        cuadro.setFill(Color.BLUE);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        break;
                    case 3:
                        cuadro.setFill(Color.RED);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        break;
                    case 4:
                        cuadro.setFill(Color.YELLOW);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        break;

                }
            }
        }

        Line linea_prueba1 = new Line(0, 0, 0, 300);
        Line linea_prueba2 = new Line(0, 0, 300, 0);

        mundo_juego_pan.getChildren().add(linea_prueba1);
        mundo_juego_pan.getChildren().add(linea_prueba2);
//        numero_disparos_pan.setText(Integer.toString(disparos));
//        no_disparos_label.setVisible(true);
//        btn_jugar.setDisable(false);

        for (int h = 1; h <= 10; h++) {
            Line line_pro_1 = new Line((h * 30), 0, (h * 30), 300);
            line_pro_1.setFill(Color.BLACK);
            Line line_pro_2 = new Line(0, (30 * h), 300, (30 * h));
            line_pro_2.setFill(Color.BLACK);
            mundo_juego_pan.getChildren().add(line_pro_1);
            mundo_juego_pan.getChildren().add(line_pro_2);
        }
    }

    @FXML
    public void aplicar_busqueda() throws CloneNotSupportedException {
        Nodo nodo_raiz = new Nodo(modelo.getEnvironment(), null, -1, 0, 0, modelo.getIi(), modelo.getIj(), modelo.getPi(), modelo.getPj(), modelo.getDisparos());
        recorridoAnchura(nodo_raiz);
//        ArrayList<Nodo> recorridos = nodo_raiz.recorridoAnchura(nodo_raiz);
//        for (Nodo recorrido : recorridos) {
//            recorrido.verPuzzle();
//        }
    }

    public Nodo create_nodo(int i, Nodo nodo_expandir) throws CloneNotSupportedException {
        Nodo nodo_i = (Nodo) nodo_expandir.clone();
        Nodo hijo = new Nodo(nodo_i.getPuzzle(), nodo_i, i, (nodo_i.getProfundidad() + 1), nodo_i.getCosto(), nodo_i.getIi(), nodo_i.getIj(), nodo_i.getPi(), nodo_i.getPj(), nodo_i.getDisparos());
        Integer verificar = hijo.mover(i);
        if (verificar == 0) {
            return hijo;
        }
        if (verificar == 1) {
            return null;
        }
        System.out.println("nodo padre");
        nodo_expandir.verPuzzle();
        System.out.println(" ");
        System.out.println("puzzle hijo movido");
        hijo.verPuzzle();
        System.out.println(" ");
        return hijo;
    }

    public boolean esta_en_arbol(Nodo ng) {
        if (ng.getPadre() == null) {
            return false;
        }
        Nodo nd = ng.getPadre();
        while (nd != null) {
            if (ng.equals(nd)) {
                return true;
            }
            nd = nd.getPadre();
        }
        return false;
    }

    public ArrayList<Nodo> recorridoAnchura(Nodo nodo_padre) throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        cola.add((Nodo) nodo_padre.clone());
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            Nodo nodo_j = cola.remove(0);
            for (int i = 0; i < 4; i++) {
                Nodo nh = create_nodo(i, (Nodo) nodo_j.clone());
                if (nh != null) {
                    if (esta_en_arbol(nh) == false) {
//                        System.out.println(" ");
//                        System.out.println("puzzle nh");
//                        nodo_j.verPuzzle();
//                        System.out.println(" ");
//                        System.out.println(" ");
//                        System.out.println("nodo padre");
//                        System.out.println(" ");
//                        nh.getPadre().verPuzzle();
                        if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
                            solucion_nodo = nh;
                            break;
                        } else {
                            cola.add(nh);
                        }
                    }
                }
            }
        }
//        boolean termino = false;
        recorridos.add(solucion_nodo);
//        while (termino == false) {
//            solucion_nodo = solucion_nodo.getPadre();
//            if (solucion_nodo == null) {
//                termino = true;
//            } else {
//                recorridos.add(solucion_nodo);
//            }
//        }
        return recorridos;
    }

    public void select_algorithm_action() {
        Label seleccion = (Label) this.option_algotihm.getSelectionModel().getSelectedItem();
        if (seleccion.getText().equals("Busqueda Informada")) {
            this.option_selected_algorithm.getItems().removeAll(this.option_selected_algorithm.getItems());
            this.option_selected_algorithm.setPromptText("Seleccione una " + seleccion.getText());
            this.option_selected_algorithm.getItems().add("Avara");
            this.option_selected_algorithm.getItems().add("A*");
            this.option_selected_algorithm.setDisable(false);
            this.mundo_juego_pan.requestFocus();
            this.option_selected_algorithm.requestFocus();
        }
        if (seleccion.getText().equals("Busqueda no Informada")) {
            this.option_selected_algorithm.getItems().removeAll(this.option_selected_algorithm.getItems());
            this.option_selected_algorithm.setPromptText("Seleccione una " + seleccion.getText());
            this.option_selected_algorithm.getItems().add("Amplitud");
            this.option_selected_algorithm.getItems().add("Costo Uniforme");
            this.option_selected_algorithm.getItems().add("Profundidad Evitando Ciclos");
            this.option_selected_algorithm.setDisable(false);
            this.mundo_juego_pan.requestFocus();
            this.option_selected_algorithm.requestFocus();
        }
    }
}
