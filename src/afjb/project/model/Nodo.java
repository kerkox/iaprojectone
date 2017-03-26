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

    public byte[][] puzzle;
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

    public Integer mover(int direccion) {
        
        System.out.println("puzzle original");
        this.verPuzzle();
        System.out.println(" ");
        
        System.out.println("dirreccion de movimien " + direccion);
        
        System.out.println("valor de move posi" + move_position);
        
        if (get(pi, pj) == get(ii, ij)) {
            System.out.println("encontro la meta");
            return 0;
        }
        
        System.out.println("valor de pj" + this.pj);
        
        System.out.println("valor de pi" + this.pi);
        
        System.out.println("puzle hijo");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + this.puzzle[i][j]);
            }

            System.out.println(" ");
        }
        System.out.println(" ");

        int oldPj = this.pj;
        int oldPi = this.pi;

        switch (direccion) {
            case RIGHT:
                System.out.println("mover a la derecha");
                this.pj++;
                break;
            case LEFT:
                System.out.println("mover a la izquierda");
                this.pj--;
                break;
            case UP:
                System.out.println("mover arriba");
                this.pi--;
                break;
            case DOWN:
                System.out.println("mover abajo");
                this.pi++;
                break;
        }
        
        System.out.println("valor de pj movido" + this.pj);
        
        System.out.println("valor de pi movido" + this.pi);

        if (this.pj == this.getCols()) {
            System.out.println("hpj sobre paso las columnas");
            return 1;
        }

        if (this.pj == -1) {
            System.out.println("hpj es menor que las columas");
            return 1;
        }

        if (this.pi == this.getRows()) {
            System.out.println("hpi sobre paso las filas");
            return 1;
        }

        if (this.pi == -1) {
            System.out.println("hpi es menor que las filas ");
            return 1;
        }

        if (puzzle[this.pi][this.pj] == FREE) {
            this.costo++;
        }

        if (puzzle[this.pi][this.pj] == ROBOT_ENEMIGO) {
            if (this.disparos == 0) {
                this.costo += 5;
            } else {
                this.disparos--;
                this.costo++;
            }
        }

        if (puzzle[this.pi][this.pj] == OBSTACULO) {
            return 1;
        }

//        if (hijo_puzzle[hpi][hpj] == ITEM) {
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
//            String msg = "ROBOT SAPIENS A ENCONTRADO LA META ¡FELICITACIONES!";
//            alerta.setAlertType(Alert.AlertType.INFORMATION);
//            alerta.setHeaderText(msg);
//            alerta.showAndWait();
//        }
        
        puzzle[oldPi][oldPj] = this.move_position;
        
        this.move_position = this.puzzle[pi][pj];

        System.out.println("valor nuevo de move position " + puzzle[this.pi][this.pj]);
        
        byte nuevo_move_position = puzzle[this.pi][this.pj];
        
        puzzle[this.pi][this.pj] = ROBOT_SAPIENS;
        
        return 2;

    }
    
    public Nodo create_nodo(int i){
        Nodo hijo = new Nodo(this.puzzle, this, i, (this.profundidad++), this.costo, this.ii, this.ij, this.pi, this.pj, this.disparos);
        Integer verificar = hijo.mover(i);
        if(verificar == 0){
            return hijo;
        }
        if(verificar == 1){
            return null;
        }
        System.out.println(" ");
        hijo.verPuzzle();
        System.out.println(" ");
        System.out.println("puzzle original movido");
        this.verPuzzle();
        System.out.println(" ");
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
        return this.puzzle[0].length;
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
    public ArrayList<Nodo> recorridoAnchura() throws CloneNotSupportedException {
        ArrayList<Nodo> recorridos = new ArrayList<Nodo>();
        ArrayList<Nodo> cola = new ArrayList<Nodo>();
        cola.add(this);
        Nodo solucion_nodo = null;
        while (!cola.isEmpty()) {
            Nodo nodo_j = cola.remove(0);
            for (int i = 0; i < 4; i++) {
                Nodo nh = nodo_j.create_nodo(i);
                if (nh != null && nh != nodo_j.getPadre()) {
                    System.out.println(" ");
                    System.out.println("puzzle nh");
                    nh.verPuzzle();
                    System.out.println(" ");
                    if (nh.equals(nodo_j)) {
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

    public byte[][] getPuzzle() {
        return puzzle;
    }

    public int getOperador() {
        return operador;
    }

    public void setMove_position(byte move_position) {
        this.move_position = move_position;
    }

}
