/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventListenerProxy;

/**
 * A class which extends the {@code EventListenerProxy}
 * specifically for adding a {@code PropertyChangeListener}
 * with a "bound" property.
 * Instances of this class can be added
 * as {@code PropertyChangeListener}s to a bean
 * which supports firing property change events.
 * <p>
 * If the object has a {@code getPropertyChangeListeners} method
 * then the array returned could be a mixture of {@code PropertyChangeListener}
 * and {@code PropertyChangeListenerProxy} objects.
 *
 * <p>
 *  一个扩展{@code EventListenerProxy}的类,专门用于添加一个带有"bound"属性的{@code PropertyChangeListener}。
 * 这个类的实例可以作为{@code PropertyChangeListener}添加到支持触发属性更改事件的bean。
 * <p>
 *  如果对象有一个{@code getPropertyChangeListeners}方法,那么返回的数组可能是{@code PropertyChangeListener}和{@code PropertyChangeListenerProxy}
 * 对象的混合。
 * 
 * 
 * @see java.util.EventListenerProxy
 * @see PropertyChangeSupport#getPropertyChangeListeners
 * @since 1.4
 */
public class PropertyChangeListenerProxy
        extends EventListenerProxy<PropertyChangeListener>
        implements PropertyChangeListener {

    private final String propertyName;

    /**
     * Constructor which binds the {@code PropertyChangeListener}
     * to a specific property.
     *
     * <p>
     *  将{@code PropertyChangeListener}绑定到特定属性的构造方法。
     * 
     * 
     * @param propertyName  the name of the property to listen on
     * @param listener      the listener object
     */
    public PropertyChangeListenerProxy(String propertyName, PropertyChangeListener listener) {
        super(listener);
        this.propertyName = propertyName;
    }

    /**
     * Forwards the property change event to the listener delegate.
     *
     * <p>
     *  将属性更改事件转发给侦听器委派。
     * 
     * 
     * @param event  the property change event
     */
    public void propertyChange(PropertyChangeEvent event) {
        getListener().propertyChange(event);
    }

    /**
     * Returns the name of the named property associated with the listener.
     *
     * <p>
     *  返回与侦听器关联的命名属性的名称。
     * 
     * @return the name of the named property associated with the listener
     */
    public String getPropertyName() {
        return this.propertyName;
    }
}
