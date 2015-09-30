/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.output;

import trace.data.Data;
import trace.input.InputEvent;
import java.io.PrintStream;

/**
 *
 * @author usuario
 */
public class PrintStreamOutput extends Output {

    public final int DEBUG_LEVEL = 0;

    public PrintStream stream;

    public PrintStreamOutput(PrintStream stream) {
        this.stream = stream;
    }

    public PrintStreamOutput() {
        this(System.out);
    }

    @Override
    public void newEvent(InputEvent event) {
        switch (event.type) {
            case WELCOME_STRUCTURE:
            case WELCOME_DATA:
            case NEW_DATA:
            case STRUCTURE_CHANGED:
                stream.println(event);
        }
    }

}
