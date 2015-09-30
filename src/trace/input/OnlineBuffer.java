/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.input;

import trace.data.Data;
import trace.data.DataMap;
import trace.data.DataSet;
import trace.data.NamedDataSet;

/**
 *
 * @author usuario
 */
public class OnlineBuffer extends Input implements InputListener {

    DataMap data = new DataMap();

    @Override
    public synchronized void addListener(InputListener listener) {
        listeners.add(listener);
        listener.newEvent(new InputEvent(InputEvent.EnumEventType.WELCOME_DATA, data, this));
    }

    @Override
    public void removeListener(InputListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void newEvent(InputEvent event) {
        switch (event.type) {
            case WELCOME_DATA:
                if (event.data instanceof Data) {
                    data.add(new NamedDataSet(null, new DataSet(new Data[](event.data))));
                }
                data.add(event.data);
                fireListener(event);
                break;
            default:
                fireListener(event);
                break;
        }
    }

    @Override
    public void setCommand(InputCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
