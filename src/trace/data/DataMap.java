/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.data;

import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author usuario
 */
public class DataMap implements Set<NamedDataSet> {

    private TreeMap<String, NamedDataSet> map;

    public void addData(NamedDataSet data) {
        if (map.containsKey(data.name)) {
            map.get(data.name).addAll(data);
        } else {
            add(data);
        }
    }

    public synchronized NamedDataSet getData(String name) {
        return map.get(name);
    }

    public synchronized NamedDataSet getData(String name, Instant from, Instant to) {
        return map.get(name).getSubSet(from, to);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        try {
            return map.containsKey(((NamedDataSet) o).name);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Iterator<NamedDataSet> iterator() {
        return map.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return map.values().toArray(a);
    }

    @Override
    public boolean add(NamedDataSet e) {
        try {
            map.put(e.name, e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            map.remove(((NamedDataSet) o).name);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return map.values().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends NamedDataSet> c) {
        try {
            for (NamedDataSet set : c) {
                add(set);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return map.values().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return map.values().removeAll(c);
    }

    @Override
    public void clear() {
        map.values().clear();
    }

}
