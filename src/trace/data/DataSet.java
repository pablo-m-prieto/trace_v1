/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.data;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 *
 * @author usuario
 */
public class DataSet extends TreeSet<Data> {

    public DataSet() {
        super(new TreeSet<>(new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.instant.compareTo(o2.instant);
            }
        }));
    }

    public DataSet(Collection<Data> data) {
        this();
        this.addAll(data);
    }

    public DataSet(Data... data) {
        this();
        Collections.addAll(this, data);
    }

    /**
     * @return the set
     */
    public synchronized DataSet getSubSet(Instant from, Instant to) {
        return new DataSet(this.subSet(new Data(from, null), new Data(to, null)));
    }

    /**
     * @return the set
     */
    public synchronized DataSet getSubSet(Instant from, boolean fromInclusive, Instant to, boolean toInclusive) {
        return new DataSet(this.subSet(new Data(from, null), fromInclusive, new Data(to, null), toInclusive));
    }

    public synchronized boolean addData(DataSet set) {
        return this.addAll(set);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Data data : this) {
            if (builder.length() != 0) {
                builder.append("; ");
            }
            builder.append(data);
        }
        return builder.toString();
    }

}
