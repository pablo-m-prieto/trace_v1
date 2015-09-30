/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.data;

import java.time.Instant;
import java.util.TreeSet;

/**
 *
 * @author usuario
 */
public class NamedDataSet extends DataSet {

    public final String name;

    public NamedDataSet(String name) {
        this.name = name;
    }

    public NamedDataSet(String name, DataSet data) {
        this(name);
        this.addAll(data);
    }

    /**
     * @return the set
     */
    public synchronized NamedDataSet getSubSet(Instant from, Instant to) {
        return new NamedDataSet(name, this.getSubSet(from, to));
    }

    /**
     * @return the set
     */
    public synchronized NamedDataSet getSubSet(Instant from, boolean fromInclusive, Instant to, boolean toInclusive) {
        return new NamedDataSet(name, this.getSubSet(from, fromInclusive, to, toInclusive));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(":\t");
        builder.append(super.toString());
        return builder.toString();
    }

}
