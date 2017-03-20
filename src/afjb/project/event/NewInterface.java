package afjb.project.event;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cindy Caicedo
 */
public interface NewInterface extends Serializable{
    
    public void change(CambioEvent e);
    
}
