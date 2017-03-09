/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An "IndexedPropertyChange" event gets delivered whenever a component that
 * conforms to the JavaBeans&trade; specification (a "bean") changes a bound
 * indexed property. This class is an extension of <code>PropertyChangeEvent</code>
 * but contains the index of the property that has changed.
 * <P>
 * Null values may be provided for the old and the new values if their
 * true values are not known.
 * <P>
 * An event source may send a null object as the name to indicate that an
 * arbitrary set of if its properties have changed.  In this case the
 * old and new values should also be null.
 *
 * <p>
 *  每当符合JavaBeans&trade;的组件时,将传递"IndexedPropertyChange"事件。规范("bean")更改绑定的索引属性。
 * 此类是<code> PropertyChangeEvent </code>的扩展,但包含已更改的属性的索引。
 * <P>
 *  如果旧值和新值的真值未知,则可以为旧值和新值提供空值。
 * <P>
 *  事件源可以发送空对象作为名称,以指示其属性已更改的任意集合。在这种情况下,旧值和新值也应为null。
 * 
 * 
 * @since 1.5
 * @author Mark Davidson
 */
public class IndexedPropertyChangeEvent extends PropertyChangeEvent {
    private static final long serialVersionUID = -320227448495806870L;

    private int index;

    /**
     * Constructs a new <code>IndexedPropertyChangeEvent</code> object.
     *
     * <p>
     * 
     * @param source  The bean that fired the event.
     * @param propertyName  The programmatic name of the property that
     *             was changed.
     * @param oldValue      The old value of the property.
     * @param newValue      The new value of the property.
     * @param index index of the property element that was changed.
     */
    public IndexedPropertyChangeEvent(Object source, String propertyName,
                                      Object oldValue, Object newValue,
                                      int index) {
        super (source, propertyName, oldValue, newValue);
        this.index = index;
    }

    /**
     * Gets the index of the property that was changed.
     *
     * <p>
     *  构造一个新的<code> IndexedPropertyChangeEvent </code>对象。
     * 
     * 
     * @return The index specifying the property element that was
     *         changed.
     */
    public int getIndex() {
        return index;
    }

    void appendTo(StringBuilder sb) {
        sb.append("; index=").append(getIndex());
    }
}
