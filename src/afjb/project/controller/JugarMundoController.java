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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
        try {
            dibujar();
        } catch (InterruptedException ex) {
            Logger.getLogger(JugarMundoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void animation_test(KeyEvent evt) {
    }

    public void dibujar() throws InterruptedException {

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
                        continue;
                    case 1:
                        cuadro.setFill(Color.GREEN);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        continue;
                    case 2:
                        cuadro.setFill(Color.BLUE);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        continue;
                    case 3:
                        cuadro.setFill(Color.RED);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        continue;
                    case 4:
                        cuadro.setFill(Color.YELLOW);
                        cuadro.setX(k * 30);
                        cuadro.setY(j * 30);
                        mundo_juego_pan.getChildren().add(cuadro);
                        continue;

                }
            }
        }

        Line linea_prueba1 = new Line(0, 0, 0, 300);
        Line linea_prueba2 = new Line(0, 0, 300, 0);

        mundo_juego_pan.getChildren().add(linea_prueba1);
        mundo_juego_pan.getChildren().add(linea_prueba2);

        for (int h = 1; h <= 10; h++) {
            Line line_pro_1 = new Line((h * 30), 0, (h * 30), 300);
            line_pro_1.setFill(Color.BLACK);
            Line line_pro_2 = new Line(0, (30 * h), 300, (30 * h));
            line_pro_2.setFill(Color.BLACK);
            mundo_juego_pan.getChildren().add(line_pro_1);
            mundo_juego_pan.getChildren().add(line_pro_2);
        }
    }

    public void solucion_dibujo(ArrayList<Nodo> soluciones) {
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                Integer mn = 1;
                for (int i = (soluciones.size() - 1); i > -1; i--) {
                    Thread.sleep(1000);
                    int operador = soluciones.get(i).getOperador();
                    System.out.println("operador " + operador);
                    if (operador != -1) {
                        modelo.move(operador);
                    }
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                dibujar();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(JugarMundoController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
                return mn;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    public void aplicar_busqueda() throws CloneNotSupportedException, InterruptedException {
        Object seleccion = this.option_selected_algorithm.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            Nodo nodo_raiz = new Nodo(modelo.getEnvironment(), null, -1, 0, 0, modelo.getIi(), modelo.getIj(), modelo.getPi(), modelo.getPj(), modelo.getDisparos());
            if (seleccion.equals("Amplitud")) {
                ArrayList<Nodo> soluciones = recorridoAnchura(nodo_raiz);
                solucion_dibujo(soluciones);
            }
            if (seleccion.equals("Costo Uniforme")) {
                ArrayList<Nodo> soluciones = recorridoCosto(nodo_raiz);
                solucion_dibujo(soluciones);
            }
            if (seleccion.equals("Profundidad Evitando Ciclos")) {
                ArrayList<Nodo> soluciones = recorridoProfundidad(nodo_raiz);
                solucion_dibujo(soluciones);
            }
            if (seleccion.equals("Avara")) {
                ArrayList<Nodo> soluciones = recorridoAvara(nodo_raiz);
                solucion_dibujo(soluciones);
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
            alerta.setHeaderText("por favor seleccione un algoritmo de busqueda");
            alerta.showAndWait();
        }
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

    public int nodo_menor(ArrayList<Nodo> nodos) {
        int posicion = 0;
        Nodo menor = nodos.get(0);
        for (int i = 1; i < nodos.size(); i++) {
            Nodo nd_comparar = nodos.get(i);
            if (menor.getCosto() > nd_comparar.getCosto()) {
                menor = nd_comparar;
                posicion = i;
            }
        }
        return posicion;
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
    
    public int nodo_menor_hn(ArrayList<Nodo> nodos){
        int posicion = 0;
        Nodo menor = nodos.get(0);
        for (int i = 1; i < nodos.size(); i++) {
            Nodo nd_comparar = nodos.get(i);
            if (menor.getHn() > nd_comparar.getHn()) {
                menor = nd_comparar;
                posicion = i;
            }
        }
        return posicion;
    }

    public ArrayList<Nodo> recorridoCosto(Nodo nodo_padre) throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        ArrayList<Nodo> expandidos = new ArrayList<>();
        cola.add((Nodo) nodo_padre.clone());
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            int posicion_nodo = nodo_menor(cola);
            Nodo nodo_j = cola.remove(posicion_nodo);
            for (int i = 0; i < 4; i++) {
                expandidos.add(nodo_j);
                Nodo nh = create_nodo(i, (Nodo) nodo_j.clone());
                if (nh != null) {
                    if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
                        System.out.println("solucion encontrada 1");
                        solucion_nodo = nh;
                        cola.clear();
                        break;
                    }
//                    if (nh.equals(nh.getPadre())) {
//                        System.out.println("encontro la solucion");
//                        break;
//                    }
//                    if (!expandidos.contains(nh)) {
                    if (esta_en_arbol(nh) == false) {
//                            if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
//                                System.out.println("solucion encontrada");
//                                solucion_nodo = nh;
//                                break;
//                            } else {
                        cola.add(nh);
//                            }
                    }
//                    }
                }
            }
        }
        System.out.println("costo de la solucion: " + solucion_nodo.getCosto());
        boolean termino = false;
        recorridos.add(solucion_nodo);
        while (termino == false) {
            solucion_nodo = solucion_nodo.getPadre();
            if (solucion_nodo == null) {
                termino = true;
            } else {
                recorridos.add(solucion_nodo);
            }
        }
        return recorridos;
    }

    public ArrayList<Nodo> combinar_lista(ArrayList<Nodo> cola_m, ArrayList<Nodo> cola_n) {
        ArrayList<Nodo> cola_retorno = new ArrayList<>();
        for (int i = (cola_m.size() - 1); i > -1; i--) {
            cola_retorno.add(cola_m.get(i));
        }
        cola_retorno.addAll(cola_n);
        return cola_retorno;
    }

    public ArrayList<Nodo> recorridoProfundidad(Nodo nodo_padre) throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        ArrayList<Nodo> expandidos = new ArrayList<>();
        cola.add((Nodo) nodo_padre.clone());
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            int posicion_nodo = nodo_menor(cola);
            Nodo nodo_j = cola.remove(posicion_nodo);
            ArrayList<Nodo> nodos_profundidad = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                expandidos.add(nodo_j);
                Nodo nh = create_nodo(i, (Nodo) nodo_j.clone());
                if (nh != null) {
                    if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
                        System.out.println("solucion encontrada 1");
                        solucion_nodo = nh;
                        cola.clear();
                        nodos_profundidad.clear();
                        break;
                    }
//                    if (!expandidos.contains(nh)) {
                    if (esta_en_arbol(nh) == false) {
//                            if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
//                                System.out.println("solucion encontrada");
//                                solucion_nodo = nh;
//                                break;
//                            } else {
                        nodos_profundidad.add(nh);
//                            }
                    }
//                    }
                }
            }
            cola = combinar_lista(nodos_profundidad, cola);
        }
        System.out.println("costo de la solucion: " + solucion_nodo.getCosto());
        boolean termino = false;
        recorridos.add(solucion_nodo);
        while (termino == false) {
            solucion_nodo = solucion_nodo.getPadre();
            if (solucion_nodo == null) {
                termino = true;
            } else {
                recorridos.add(solucion_nodo);
            }
        }
        return recorridos;
    }

    public ArrayList<Nodo> recorridoAvara(Nodo nodo_padre) throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        ArrayList<Nodo> expandidos = new ArrayList<>();
        cola.add((Nodo) nodo_padre.clone());
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            int posicion_nodo = nodo_menor_hn(cola);
            Nodo nodo_j = cola.remove(posicion_nodo);
            for (int i = 0; i < 4; i++) {
                expandidos.add(nodo_j);
                Nodo nh = create_nodo(i, (Nodo) nodo_j.clone());
                if (nh != null) {
                    if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
                        System.out.println("solucion encontrada 1");
                        solucion_nodo = nh;
                        cola.clear();
                        break;
                    }
//                    if (nh.equals(nh.getPadre())) {
//                        System.out.println("encontro la solucion");
//                        break;
//                    }
//                    if (!expandidos.contains(nh)) {
                    if (esta_en_arbol(nh) == false) {
//                            if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
//                                System.out.println("solucion encontrada");
//                                solucion_nodo = nh;
//                                break;
//                            } else {
                        cola.add(nh);
//                            }
                    }
//                    }
                }
            }
        }
        System.out.println("costo de la solucion: " + solucion_nodo.getCosto());
        boolean termino = false;
        recorridos.add(solucion_nodo);
        while (termino == false) {
            solucion_nodo = solucion_nodo.getPadre();
            if (solucion_nodo == null) {
                termino = true;
            } else {
                recorridos.add(solucion_nodo);
            }
        }
        return recorridos;
    }

    public ArrayList<Nodo> recorridoAnchura(Nodo nodo_padre) throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        ArrayList<Nodo> expandidos = new ArrayList<>();
        cola.add((Nodo) nodo_padre.clone());
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            Nodo nodo_j = cola.remove(0);
            for (int i = 0; i < 4; i++) {
                expandidos.add(nodo_j);
                Nodo nh = create_nodo(i, (Nodo) nodo_j.clone());
                if (nh != null) {
                    if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
                        System.out.println("solucion encontrada 1");
                        solucion_nodo = nh;
                        cola.clear();
                        break;
                    }
//                    if (nh.equals(nh.getPadre())) {
//                        System.out.println("encontro la solucion");
//                        break;
//                    }
//                    if (!expandidos.contains(nh)) {
                    if (esta_en_arbol(nh) == false) {
//                            if (nh.getPi() == nh.getIi() && nh.getIj() == nh.getPj()) {
//                                System.out.println("solucion encontrada");
//                                solucion_nodo = nh;
//                                break;
//                            } else {
                        cola.add(nh);
//                            }
                    }
//                    }
                }
            }
        }
        System.out.println("costo de la solucion: " + solucion_nodo.getCosto());
        boolean termino = false;
        recorridos.add(solucion_nodo);
        while (termino == false) {
            solucion_nodo = solucion_nodo.getPadre();
            if (solucion_nodo == null) {
                termino = true;
            } else {
                recorridos.add(solucion_nodo);
            }
        }
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
