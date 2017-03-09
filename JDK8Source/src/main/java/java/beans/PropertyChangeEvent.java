/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.beans;

import java.util.EventObject;

/**
 * A "PropertyChange" event gets delivered whenever a bean changes a "bound"
 * or "constrained" property.  A PropertyChangeEvent object is sent as an
 * argument to the PropertyChangeListener and VetoableChangeListener methods.
 * <P>
 * Normally PropertyChangeEvents are accompanied by the name and the old
 * and new value of the changed property.  If the new value is a primitive
 * type (such as int or boolean) it must be wrapped as the
 * corresponding java.lang.* Object type (such as Integer or Boolean).
 * <P>
 * Null values may be provided for the old and the new values if their
 * true values are not known.
 * <P>
 * An event source may send a null object as the name to indicate that an
 * arbitrary set of if its properties have changed.  In this case the
 * old and new values should also be null.
 * <p>
 *  每当bean更改"绑定"或"约束"属性时,将传递"PropertyChange"事件。
 *  PropertyChangeEvent对象作为参数发送到PropertyChangeListener和VetoableChangeListener方法。
 * <P>
 *  通常PropertyChangeEvents伴随着名称以及changed属性的旧值和新值。如果新值是一个基本类型(例如int或boolean),它必须被包装为相应的java.lang。
 * * Object类型(如Integer或Boolean)。
 * <P>
 *  如果旧值和新值的真值未知,则可以为旧值和新值提供空值。
 * <P>
 *  事件源可以发送空对象作为名称,以指示其属性已更改的任意集合。在这种情况下,旧值和新值也应为null。
 * 
 */
public class PropertyChangeEvent extends EventObject {
    private static final long serialVersionUID = 7042693688939648123L;

    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * <p>
     *  构造一个新的{@code PropertyChangeEvent}。
     * 
     * 
     * @param source        the bean that fired the event
     * @param propertyName  the programmatic name of the property that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    public PropertyChangeEvent(Object source, String propertyName,
                               Object oldValue, Object newValue) {
        super(source);
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    /**
     * Gets the programmatic name of the property that was changed.
     *
     * <p>
     *  获取已更改的属性的编程名称。
     * 
     * 
     * @return  The programmatic name of the property that was changed.
     *          May be null if multiple properties have changed.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Gets the new value for the property, expressed as an Object.
     *
     * <p>
     *  获取属性的新值,表示为Object。
     * 
     * 
     * @return  The new value for the property, expressed as an Object.
     *          May be null if multiple properties have changed.
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * Gets the old value for the property, expressed as an Object.
     *
     * <p>
     *  获取属性的旧值,表示为Object。
     * 
     * 
     * @return  The old value for the property, expressed as an Object.
     *          May be null if multiple properties have changed.
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Sets the propagationId object for the event.
     *
     * <p>
     *  设置事件的propagationId对象。
     * 
     * 
     * @param propagationId  The propagationId object for the event.
     */
    public void setPropagationId(Object propagationId) {
        this.propagationId = propagationId;
    }

    /**
     * The "propagationId" field is reserved for future use.  In Beans 1.0
     * the sole requirement is that if a listener catches a PropertyChangeEvent
     * and then fires a PropertyChangeEvent of its own, then it should
     * make sure that it propagates the propagationId field from its
     * incoming event to its outgoing event.
     *
     * <p>
     *  "propagationId"字段保留供将来使用。
     * 在Beans 1.0中,唯一的要求是,如果侦听器捕获到一个PropertyChangeEvent,然后触发它自己的PropertyChangeEvent,那么它应该确保它将propagationId字段
     * 从其传入事件传播到其传出事件。
     *  "propagationId"字段保留供将来使用。
     * 
     * 
     * @return the propagationId object associated with a bound/constrained
     *          property update.
     */
    public Object getPropagationId() {
        return propagationId;
    }

    /**
     * name of the property that changed.  May be null, if not known.
     * <p>
     * 更改的属性的名称。可能为null,如果不知道。
     * 
     * 
     * @serial
     */
    private String propertyName;

    /**
     * New value for property.  May be null if not known.
     * <p>
     *  属性的新值。如果不知道可以为null。
     * 
     * 
     * @serial
     */
    private Object newValue;

    /**
     * Previous value for property.  May be null if not known.
     * <p>
     *  属性的上一个值。如果不知道可以为null。
     * 
     * 
     * @serial
     */
    private Object oldValue;

    /**
     * Propagation ID.  May be null.
     * <p>
     *  传播ID。可能为null。
     * 
     * 
     * @serial
     * @see #getPropagationId
     */
    private Object propagationId;

    /**
     * Returns a string representation of the object.
     *
     * <p>
     *  返回对象的字符串表示形式。
     * 
     * @return a string representation of the object
     *
     * @since 1.7
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append("[propertyName=").append(getPropertyName());
        appendTo(sb);
        sb.append("; oldValue=").append(getOldValue());
        sb.append("; newValue=").append(getNewValue());
        sb.append("; propagationId=").append(getPropagationId());
        sb.append("; source=").append(getSource());
        return sb.append("]").toString();
    }

    void appendTo(StringBuilder sb) {
    }
}
