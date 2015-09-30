/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace;

import trace.input.CsvInput;
import trace.output.PrintStreamOutput;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import trace.input.InputCommand;
import trace.input.InputEvent;
import trace.input.InputListener;

/**
 *
 * @author usuario
 */
public class Graficar_V1 implements InputListener {

    String filename = "C:\\Documents and Settings\\usuario\\Escritorio\\OEEDesignSpeedViewer.csv";

    boolean onOff = false;
    PrintStreamOutput output;
    CsvInput input;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        Graficar_V1 gv = new Graficar_V1();

    }

    public Graficar_V1() throws FileNotFoundException {
        output = new PrintStreamOutput();
        input = new CsvInput(new BufferedReader(new FileReader(filename)));
        input.addListener(output);
        input.addListener(this);
        input.startCommand();
    }

    @Override
    public void newEvent(InputEvent event) {
        switch (event.type) {
            case INPUT_RUNNING:
                onOff = true;
                break;
            case SOURCE_EOF:
                if (onOff) {
                    input.setCommand(InputCommand.TERMINATE);
                }
        }
    }

}
