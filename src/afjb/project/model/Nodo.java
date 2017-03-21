package afjb.project.model;

import afjb.project.event.CambioEvent;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class Nodo {
    
    public static final byte FREE = 0;
    public static final byte OBSTACULO = 1;
    public static final byte ROBOT_SAPIENS = 2;
    public static final byte ROBOT_ENEMIGO = 3;
    public static final byte ITEM = 4;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    
    public byte move_position = FREE;
    
    public int pi, pj, ii, ij;

    public byte puzzle[][];
    public Nodo padre;
    public int operador;
    public int profundidad;
    public int costo;
    public Integer disparos;
    public boolean visitiadoAnchura[];

    public Nodo(byte[][] puzzle, Nodo padre, int operador, int profundidad, int costo, int ii, int ij, int pi, int pj) {
        this.puzzle = puzzle;
        this.padre = padre;
        this.operador = operador;
        this.profundidad = profundidad;
        this.costo = costo;
        this.ii = ii;
        this.pi = pi;
        this.pj = pj;
        this.ij = ij;
    }

    public Nodo mover(int direccion) {
        
        int hpi = pj; 
        int hpj = pi;
        
        byte[][] hijo_puzzle = this.puzzle;
        
        int hcosto = this.costo;
        

        hijo_puzzle[hpi][hpj] = this.move_position;

        int oldPj = hpj;
        int oldPi = hpi;

        switch (direccion) {
            case RIGHT:
                hpj++;
                break;
            case LEFT:
                hpj--;
                break;
            case UP:
                hpi--;
                break;
            case DOWN:
                hpi++;
                break;
        }

        if (hpj == hijo_puzzle[0].length) {
            hpj = 0;
        }

        if (hpj == -1) {
            hpj = hijo_puzzle[0].length - 1;
        }

        if (hpi == getRows()) {
            hpi = 0;
        }

        if (hpi == -1) {
            hpi = getRows() - 1;
        }

        if (this.get(hpi, hpj) == OBSTACULO) {
            hpi = oldPi;
            hpj = oldPj;
        }

        if (this.get(hpi, hpj) == ITEM) {
//            this.notifyChange(new CambioEvent(this));
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
            String msg = "ROBOT SAPIENS A ENCONTRADO LA META ¡FELICITACIONES!";
            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(msg);
            alerta.showAndWait();
        }
        
        this.move_position = hijo_puzzle[hpi][hpj];
        hijo_puzzle[hpi][hpj] = ROBOT_SAPIENS;
        
        Nodo hijo = new Nodo(hijo_puzzle, this, direccion, (this.profundidad++), hcosto, this.ii, this.ij, hpi, hpj);
        return hijo;
    }

    public void profundidad() {
        System.out.println("La profundidad del nodo es " + profundidad);
    }

    public void verPuzzle() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + puzzle[i][j]);
            }

            System.out.println(" ");
        }
    }
    
    
     public int getRows() {
        return this.puzzle.length;
    }

    public int getCols() {
        return this.puzzle.length;
    }
    
    public int get(int row, int col) {
        return this.puzzle[row][col];
    }

//    public ArrayList<Nodo> recorridoAnchura(Nodo nodoI) {
//        ArrayList<Integer> recorridos = new ArrayList<Integer>();
//        visitiadoAnchura[nodoI] = true;
//        ArrayList<Integer> cola = new ArrayList<Integer>();
//        recorridos.add(nodoI);
//        cola.add(nodoI);
//        while (!cola.isEmpty()) {
//            int j = cola.remove(0);
//            for (int i = 0; i < puzzle.length; i++) {
//                if (puzzle[j][i] == 1 && !visitiadoAnchura[i]) {
//                    cola.add(i);
//                    recorridos.add(i);
//                    visitiadoAnchura[i] = true;
//                }
//            }
//        }
//        return recorridos;
//    }

}
