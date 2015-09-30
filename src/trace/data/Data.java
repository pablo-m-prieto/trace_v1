/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.data;

import java.time.Instant;

/**
 *
 * @author usuario
 */
public class Data {

    public final Object value;
    public final Instant instant;

    public Data(Instant instant, Object value) {
        this.instant = instant;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (value != null) {
            builder.append(value);
        } else {
            builder.append("null");
        }
        if (instant != null) {
            builder.append("(");
            builder.append(instant);
            builder.append(")");
        }
        return builder.toString();
    }

}
