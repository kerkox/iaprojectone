package afjb.project.model;

import afjb.project.event.CambioEvent;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class Nodo implements Cloneable {

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
        this.puzzle = new byte[puzzle.length][];
        for (int i = 0; i < 10; i++) {
            this.puzzle[i] = puzzle[i].clone();
        }
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

        if (get(pi, pj) == get(ii, ij)) {
            System.out.println("encontro la meta");
            return 0;
        }

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

        if (this.pj == this.getCols()) {
            return 1;
        }

        if (this.pj == -1) {
            return 1;
        }

        if (this.pi == this.getRows()) {
            return 1;
        }

        if (this.pi == -1) {
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

        this.move_position = this.puzzle[pi][pj];
        
        puzzle[oldPi][oldPj] = this.move_position;

        byte nuevo_move_position = puzzle[this.pi][this.pj];

        puzzle[this.pi][this.pj] = ROBOT_SAPIENS;

        return 2;

    }

    public Nodo create_nodo(int i) {
        Nodo hijo = new Nodo(this.puzzle, this, i, (this.profundidad++), this.costo, this.ii, this.ij, this.pi, this.pj, this.disparos);
        Integer verificar = hijo.mover(i);
        if (verificar == 0) {
            return hijo;
        }
        if (verificar == 1) {
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

    public byte getMove_position() {
        return move_position;
    }

    public int getPi() {
        return pi;
    }

    public int getPj() {
        return pj;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public int getCosto() {
        return costo;
    }

    public int getDisparos() {
        return disparos;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Nodo clon = new Nodo(this.puzzle, this.padre, this.operador, this.profundidad, this.costo, this.ii, this.ij, this.pi, this.pj, this.disparos);
        return clon;
    }

    @Override
    public boolean equals(Object obj) {
        Nodo compr = (Nodo) obj;
        if(this.getPuzzle().equals(compr.getPuzzle())){
            return true;
        }
        return false;
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
