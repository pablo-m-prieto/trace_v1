/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.output;

import trace.data.DataSet;
import trace.input.InputEvent;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import trace.input.Input;
import trace.input.OnlineBuffer;

/**
 *
 * @author usuario
 */
public class TableModelOutput extends Output implements TableModel {

    ArrayList<TableModelListener> listeners = new ArrayList<>();

    OnlineBuffer buffer = new OnlineBuffer();

    public TableModelOutput(Input input) {
        input.addListener(buffer);
        input.addListener(this);
    }

    @Override
    public void newEvent(InputEvent event) {
        switch (event.type) {
            case WELCOME_STRUCTURE:
            case STRUCTURE_CHANGED:
                fireEvent(new TableModelEvent(this, TableModelEvent.ALL_COLUMNS));
                break;
            case WELCOME_DATA:
            case NEW_DATA:
                fireEvent(new TableModelEvent(this, TableModelEvent.UPDATE));
                break;
        }
    }

    private void fireEvent(TableModelEvent event) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(event);
        }
    }

    @Override
    public int getRowCount() {
        return datas.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colNames.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return datas.get(rowIndex).get(columnIndex).value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

}
