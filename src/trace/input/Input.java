/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.input;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public abstract class Input {

    ArrayList<InputListener> listeners = new ArrayList<>();

    public abstract void addListener(InputListener listener);

    public abstract void removeListener(InputListener listener);

    public synchronized void fireListener(InputEvent event) {
        for (InputListener listener : listeners) {
            listener.newEvent(event);
        }
    }

    public abstract void setCommand(InputCommand command);

    public abstract void startCommand();

    public abstract void stopCommand();
}
