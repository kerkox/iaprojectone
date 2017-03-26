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
    public int disparos;
    public boolean visitiadoAnchura[];

    public Nodo(byte[][] puzzle, Nodo padre, int operador, int profundidad, int costo, int ii, int ij, int pi, int pj, int disparos) {
        this.puzzle = puzzle;
        this.padre = padre;
        this.operador = operador;
        this.profundidad = profundidad;
        this.costo = costo;
        this.ii = ii;
        this.pi = pi;
        this.pj = pj;
        this.ij = ij;
        this.disparos = disparos;
    }

    public Nodo mover(int direccion) {
        
        System.out.println("dirreccion de movimien " + direccion);
        
        System.out.println("valor de move posi" + move_position);
        
        if (get(pi, pj) == get(ii, ij)) {
            System.out.println("encontro la meta");
            return this;
        }
        
        System.out.println("valor de pj" + pj);
        
        System.out.println("valor de pi" + pi);

        int hpi = pi;
        int hpj = pj;

        byte[][] hijo_puzzle = this.puzzle;

        int hcosto = this.costo;

        int hdisparos = this.disparos;

        hijo_puzzle[hpi][hpj] = this.move_position;

        int oldPj = hpj;
        int oldPi = hpi;

        switch (direccion) {
            case RIGHT:
                System.out.println("mover a la derecha");
                hpj++;
                break;
            case LEFT:
                System.out.println("mover a la izquierda");
                hpj--;
                break;
            case UP:
                System.out.println("mover arriba");
                hpi--;
                break;
            case DOWN:
                System.out.println("mover abajo");
                hpi++;
                break;
        }
        
        System.out.println("valor de pj movido" + hpj);
        
        System.out.println("valor de pi movido" + hpi);

        if (hpj == this.getCols()) {
            System.out.println("hpj sobre paso las columnas");
            return null;
        }

        if (hpj == -1) {
            System.out.println("hpj es menor que las columas");
            return null;
        }

        if (hpi == this.getRows()) {
            System.out.println("hpi sobre paso las filas");
            return null;
        }

        if (hpi == -1) {
            System.out.println("hpi es menor que las filas ");
            return null;
        }

        if (hijo_puzzle[hpi][hpj] == FREE) {
            hcosto++;
        }

        if (hijo_puzzle[hpi][hpj] == ROBOT_ENEMIGO) {
            if (hdisparos == 0) {
                hcosto += 5;
            } else {
                hdisparos--;
                hcosto++;
            }
        }

        if (hijo_puzzle[hpi][hpj] == OBSTACULO) {
            return null;
        }

//        if (hijo_puzzle[hpi][hpj] == ITEM) {
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
//            String msg = "ROBOT SAPIENS A ENCONTRADO LA META ¡FELICITACIONES!";
//            alerta.setAlertType(Alert.AlertType.INFORMATION);
//            alerta.setHeaderText(msg);
//            alerta.showAndWait();
//        }

        System.out.println("valor nuevo de move position " + hijo_puzzle[hpi][hpj]);
        byte nuevo_move_position = hijo_puzzle[hpi][hpj];
        hijo_puzzle[hpi][hpj] = ROBOT_SAPIENS;

        Nodo hijo = new Nodo(hijo_puzzle, this, direccion, (this.profundidad++), hcosto, this.ii, this.ij, hpi, hpj, hdisparos);
        System.out.println(" ");
        hijo.verPuzzle();
        System.out.println(" ");
        hijo.setMove_position(nuevo_move_position);
        return hijo;
    }

    public int getIi() {
        return ii;
    }

    public int getIj() {
        return ij;
    }
    

    public void profundidad() {
        System.out.println("La profundidad del nodo es " + profundidad);
    }

    public void verPuzzle() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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
//        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
//        visitiadoAnchura[0] = true;
//        ArrayList<Nodo> cola = new ArrayList<Nodo>();
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
    public ArrayList<Nodo> recorridoAnchura() {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        cola.add(this);
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            Nodo j = cola.remove(0);
            for (int i = 0; i < 4; i++) {
                Nodo nh = j.mover(i);
                if (nh != null && nh != j.getPadre()) {
                    if (nh.equals(j)) {
                        solucion_nodo = nh;
                        break;
                    }else{
                        cola.add(nh);
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

    public Nodo getPadre() {
        return padre;
    }

    public int getOperador() {
        return operador;
    }

    public void setMove_position(byte move_position) {
        this.move_position = move_position;
    }

}
