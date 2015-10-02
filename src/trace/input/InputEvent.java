/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trace.input;

/**
 *
 * @author usuario
 */
public class InputEvent {

    public enum EnumEventType {

        /**
         * Cuando un nuevo listener se asocia se envia este evento con datos de
         * estructura
         */
        WELCOME_STRUCTURE,
        /**
         * Cuando un nuevo listener se asocia se envia este evento con los datos
         */
        WELCOME_DATA,
        /**
         * Cuando cambia la estructura de envia este evento con los datos de la
         * estructura
         */
        STRUCTURE_CHANGED,
        /**
         * Cuando hay un nuevo dato se envia este evento con el dato
         */
        NEW_DATA,
        /**
         * Detenido, no adquiere datos de la fuente
         */
        INPUT_STOPPED,
        /**
         * Parando
         */
        INPUT_STOPPING,
        /**
         * En marcha, adquiere datos de la fuente
         */
        INPUT_RUNNING,
        /**
         * Arrancando
         */
        INPUT_STARTING,
        /**
         * EOF
         */
        SOURCE_EOF,
        /**
         * Error en los datos de entrada
         */
        DATA_INPUT_ERROR;
    }

    public final EnumEventType type;
    public final Object data;
    public final Input source;

    public InputEvent(EnumEventType type, Object data, Input source) {
        this.type = type;
        this.data = data;
        this.source = source;
    }

    public InputEvent(EnumEventType type, Input source) {
        this(type, null, source);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(type.name());
        builder.append("; data=");
        builder.append(data);
        builder.append("; source=");
        builder.append(source);
        return builder.toString();
    }

}
