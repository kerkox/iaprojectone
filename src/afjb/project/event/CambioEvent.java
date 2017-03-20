/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afjb.project.event;

import afjb.project.model.Model_Mundo;

/**
 *
 * @author Cindy Caicedo
 */
public class CambioEvent {

    private Model_Mundo source;

    public CambioEvent(Model_Mundo source) {
        this.source = source;
    }

    public Model_Mundo getSource() {
        return source;
    }
}
