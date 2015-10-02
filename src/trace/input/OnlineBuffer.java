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
    InputState state = InputState.RUNNING;

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
        if (state == InputState.RUNNING) {
            switch (event.type) {
                case WELCOME_DATA:
                    if ((event.data instanceof Data) || (event.data instanceof DataSet)) {
                        fireListener(new InputEvent(InputEvent.EnumEventType.DATA_INPUT_ERROR, event.data, this));
                    }
                    if (event.data instanceof NamedDataSet) {
                        NamedDataSet set = data.getData(((NamedDataSet) event.data).name);
                        if (set == null) {
                            data.add(set);
                            fireListener(new InputEvent(InputEvent.EnumEventType.STRUCTURE_CHANGED, data, this));
                        } else {
                            set.addData((NamedDataSet) event.data);
                            fireListener(new InputEvent(InputEvent.EnumEventType.NEW_DATA, event.data, this));
                        }
                    }
                    if (event.data instanceof DataMap) {
                        boolean newSet = false;
                        boolean newData = false;
                        for (NamedDataSet nds : (DataMap) event.data) {
                            NamedDataSet set = data.getData(nds.name);
                            if (set == null) {
                                data.add(set);
                                newSet = true;
                                newData = true;
                            } else {
                                set.addData(nds);
                                newData = true;
                            }
                        }
                        if (newSet) {
                            fireListener(new InputEvent(InputEvent.EnumEventType.STRUCTURE_CHANGED, data, this));
                        } else {
                            if (newData) {
                                fireListener(new InputEvent(InputEvent.EnumEventType.NEW_DATA, event.data, this));
                            }
                        }
                    }
                    break;
                default:
                    fireListener(event);
                    break;
            }
        }
    }

    @Override
    public void setCommand(InputCommand command) {
        switch (command) {
            case START:
                state = InputState.RUNNING;
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_RUNNING, this));
                break;
            case STOP:
                state = InputState.STOPPED;
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_STOPPED, this));
                break;
        }
    }

    @Override
    public void startCommand() {
        this.setCommand(InputCommand.START);
    }

    @Override
    public void stopCommand() {
        this.setCommand(InputCommand.STOP);
    }

}
