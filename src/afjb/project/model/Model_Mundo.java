/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.model;

import afjb.project.event.CambioEvent;
import afjb.project.event.NewInterface;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Model_Mundo {
    
    public static final byte FREE = 0;
    public static final byte OBSTACULO = 1;
    public static final byte ROBOT_SAPIENS = 2;
    public static final byte ROBOT_ENEMIGO = 3;
    public static final byte ITEM = 4;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    
    public Integer disparos;
    
    public byte cant_item, cant_ini;

    private byte[][] environment; // entorno - matriz que representa el juego

    // posicion inicial
    private int pi, pj, ii, ij;

    private int level;

    private int rows;
    private int cols;
    
    public byte move_position = FREE;
    
    private static Model_Mundo m_md;

    private Model_Mundo() {
        
    }
    
    public void modifi_attr(int lve, byte[][] matriz, Integer shoot){
        try {
            this.disparos = shoot;
            this.environment = matriz;
            this.level = lve;
            this.initGame(level, environment);
        } catch (Exception ex) {
            Logger.getLogger(Model_Mundo.class.getName()).log(Level.SEVERE, null, ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(ex.getMessage());
            alerta.showAndWait();
        }
    }
    
    public static Model_Mundo getInstance(){
         if (m_md == null){
            m_md = new Model_Mundo();
        }
        return m_md;
    }

//    private ResourceBundle bundle = ResourceBundle.getBundle("afjb/project/levels/levels");

    public void initGame(int level, byte[][] mundo) throws Exception {
        
        if(mundo != null){
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if(mundo[i][j] == ROBOT_SAPIENS){
                        this.pi = i;
                        this.pj = j;
                        cant_ini ++;
                    }
                    if(mundo[i][j] == ITEM){
                        this.ii = i;
                        this.ij = j;
                        cant_item ++;
                    }
                }
            }
            
            if(cant_ini > 1){
                throw new Exception("no puede haber mas de una posicion de inicio");
            }
            if(cant_item > 1){
                throw new Exception("no puede haber mas de una meta");
            }
            
        }else {
            
//             String str = bundle.getString("level" + level);
//            //str.split = le quita los espacios a la cadena y se convierte en una matriz quitandole los espacios.
//            String[] strEnv = str.split(" ");
//            /*System.out.println(str);
//             System.out.println(Arrays.toString(strEnv));*/
//
//            rows = Integer.parseInt(strEnv[0]);
//            cols = Integer.parseInt(strEnv[1]);
//
//            pi = Integer.parseInt(strEnv[2]);
//            pj = Integer.parseInt(strEnv[3]);
//
//            cc = Integer.parseInt(strEnv[4]);
//
//            int k = 5;
//
//            this.environment = new byte[rows][cols];
//
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < cols; j++) {
//                    environment[i][j] = Byte.parseByte(strEnv[k]);
//                    k++;
//                }
//            }
//
//            this.environment[pi][pj] = PACMAN;
            
        }
    }

    public int get(int row, int col) {
        return this.environment[row][col];
    }

    public byte[][] getEnvironment() {
        return environment;
    }
    

    public Integer getDisparos() {
        return disparos;
    }

    public void move(int direction) {
        this.environment[pi][pj] = this.move_position;

        int oldPj = pj;
        int oldPi = pi;

        switch (direction) {
            case RIGHT:
                this.pj++;
                break;
            case LEFT:
                this.pj--;
                break;
            case UP:
                this.pi--;
                break;
            case DOWN:
                this.pi++;
                break;
        }

        if (this.pj == this.environment[0].length) {
            this.pj = 0;
        }

        if (this.pj == -1) {
            this.pj = this.environment[0].length - 1;
        }

        if (this.pi == getRows()) {
            this.pi = 0;
        }

        if (this.pi == -1) {
            this.pi = getRows() - 1;
        }

        if (this.get(pi, pj) == OBSTACULO) {
            this.pi = oldPi;
            this.pj = oldPj;
        }

        if (this.get(pi, pj) == ITEM) {
//            this.notifyChange(new CambioEvent(this));
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setTitle("Proyecto Inteteligencia Artificial 2017");
//            String msg = "ROBOT SAPIENS A ENCONTRADO LA META ¡FELICITACIONES!";
//            alerta.setAlertType(Alert.AlertType.INFORMATION);
//            alerta.setHeaderText(msg);
//            alerta.showAndWait();
        }
        
        this.move_position = this.environment[pi][pj];
        this.environment[pi][pj] = ROBOT_SAPIENS;

    }

//    public ResourceBundle getBundle() {
//        return bundle;
//    }

    public int getRows() {
        return this.environment.length;
    }

    public int getCols() {
        return this.environment[0].length;
    }

    private List<NewInterface> listeners
            = new LinkedList<>();

    public void addNewInterface(NewInterface l) {
        this.listeners.add(l);
    }

    public int getPi() {
        return pi;
    }

    public int getPj() {
        return pj;
    }

    public int getIi() {
        return ii;
    }

    public int getIj() {
        return ij;
    }
    
    

    public void removeNewInterface(NewInterface l) {
        this.listeners.remove(l);
    }

    private void notifyChange(CambioEvent Event) {
        for (NewInterface listener : listeners) {
            listener.change(Event);
        }
    }
    
    
}
