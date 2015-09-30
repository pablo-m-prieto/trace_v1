/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.input;

/**
 *
 * @author usuario
 */
public enum InputState {

    /**
     * Detenido, no adquiere datos de la fuente
     */
    STOPPED,
    /**
     * Parando
     */
    STOPPING,
    /**
     * En marcha, adquiere datos de la fuente
     */
    RUNNING,
    /**
     * Arrancando
     */
    STARTING;
}
