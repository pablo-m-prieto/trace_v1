/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.input;

import trace.data.DataSet;
import trace.data.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class CsvInput extends Input {

    public String fieldSeparator = ";";
    private ArrayList<String> names = new ArrayList<>();
    private Worker worker;
    private InputState state = InputState.STOPPED;
    private InputCommand pendingCommand = null;

    public CsvInput(BufferedReader reader) {
        worker = new Worker(this, reader);
    }

    /**
     * @return the names
     */
    public synchronized ArrayList<String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public synchronized void setNames(ArrayList<String> names) {
        this.names = names;
        fireListener(new InputEvent(InputEvent.EnumEventType.STRUCTURE_CHANGED, names, this));
    }

    @Override
    public synchronized void addListener(InputListener listener) {
        listeners.add(listener);
        listener.newEvent(new InputEvent(InputEvent.EnumEventType.WELCOME_STRUCTURE, names, this));
    }

    @Override
    public synchronized void removeListener(InputListener listener) {
        listeners.remove(this);
    }

    synchronized void newData(DataSet data) {
        fireListener(new InputEvent(InputEvent.EnumEventType.NEW_DATA, data, this));
    }

    /**
     * @return the state
     */
    public InputState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(InputState state) {
        this.state = state;
        switch (state) {
            case RUNNING:
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_RUNNING, this));
            case STARTING:
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_STARTING, this));
            case STOPPED:
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_STOPPED, this));
            case STOPPING:
                fireListener(new InputEvent(InputEvent.EnumEventType.INPUT_STOPPING, this));
        }
    }

    @Override
    public synchronized void setCommand(InputCommand command) {
        pendingCommand = command;
    }

    @Override
    public synchronized void startCommand() {
        setCommand(InputCommand.START);
    }

    @Override
    public synchronized void stopCommand() {
        setCommand(InputCommand.STOP);
    }

    private class Worker implements Runnable {

        public final int DEBUG_LEVEL = 1;

        CsvInput parent;
        BufferedReader reader;
        Thread thread;

        public Worker(CsvInput parent, BufferedReader reader) {
            this.parent = parent;
            this.reader = reader;
            thread = new Thread(this, this.getClass().getCanonicalName());
            thread.start();
        }

        @Override
        public synchronized void run() {
            if (DEBUG_LEVEL > 0) {
                System.out.format("%s>>run\n", this.getClass().getCanonicalName());
            }
            while (parent.pendingCommand != InputCommand.TERMINATE) {
                if (DEBUG_LEVEL > 1) {
                    System.out.format("%s::parent.state%s\n", this.getClass().getCanonicalName(), parent.state);
                }
                switch (parent.state) {
                    case STOPPED:
                        if (parent.pendingCommand == InputCommand.START) {
                            parent.setState(InputState.STARTING);
                            parent.pendingCommand = null;
                        }
                        break;
                    case STOPPING:
                        parent.setState(InputState.STOPPED);
                        break;
                    case RUNNING:
                        runRunning();
                        if (parent.pendingCommand == InputCommand.STOP) {
                            parent.setState(InputState.STOPPING);
                            parent.pendingCommand = null;
                        }
                        break;
                    case STARTING:
                        parent.setState(InputState.RUNNING);
                        break;
                }
            }
            if (DEBUG_LEVEL > 0) {
                System.out.format("%s << run\n", this.getClass().getCanonicalName());
            }
        }

        private synchronized void runRunning() {
            try {
                String line = reader.readLine();
                if (line != null) {
                    Instant instant = Instant.now();
                    String[] parts = line.split(parent.fieldSeparator);

                    if (parts.length != parent.getNames().size()) {
                        //se trata de un encabezado
                        ArrayList names = parent.getNames();
                        names.clear();
                        Collections.addAll(names, parts);
                        parent.setNames(names);
                    } else {
                        DataSet data = new DataSet();
                        for (String part : parts) {
                            data.add(new Data(instant, part));
                        }
                        if (DEBUG_LEVEL > 0) {
                            System.out.format("%s :: data = %s\n", this.getClass().getCanonicalName(), data);
                        }
                        newData(data);
                    }
                } else {
                    parent.fireListener(new InputEvent(InputEvent.EnumEventType.SOURCE_EOF, parent));
                }
                try {
                    thread.sleep(0);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CsvInput.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(CsvInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
