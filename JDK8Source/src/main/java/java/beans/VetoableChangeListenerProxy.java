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
 * specifically for adding a {@code VetoableChangeListener}
 * with a "constrained" property.
 * Instances of this class can be added
 * as {@code VetoableChangeListener}s to a bean
 * which supports firing vetoable change events.
 * <p>
 * If the object has a {@code getVetoableChangeListeners} method
 * then the array returned could be a mixture of {@code VetoableChangeListener}
 * and {@code VetoableChangeListenerProxy} objects.
 *
 * <p>
 *  一个扩展{@code EventListenerProxy}的类,专门用于添加一个带有"constrained"属性的{@code VetoableChangeListener}。
 * 这个类的实例可以作为{@code VetoableChangeListener}添加到支持触发更改事件的bean。
 * <p>
 *  如果对象有一个{@code getVetoableChangeListeners}方法,那么返回的数组可能是{@code VetoableChangeListener}和{@code VetoableChangeListenerProxy}
 * 对象的混合体。
 * 
 * 
 * @see java.util.EventListenerProxy
 * @see VetoableChangeSupport#getVetoableChangeListeners
 * @since 1.4
 */
public class VetoableChangeListenerProxy
        extends EventListenerProxy<VetoableChangeListener>
        implements VetoableChangeListener {

    private final String propertyName;

    /**
     * Constructor which binds the {@code VetoableChangeListener}
     * to a specific property.
     *
     * <p>
     *  将{@code VetoableChangeListener}绑定到特定属性的构造方法。
     * 
     * 
     * @param propertyName  the name of the property to listen on
     * @param listener      the listener object
     */
    public VetoableChangeListenerProxy(String propertyName, VetoableChangeListener listener) {
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
    *
    * @exception PropertyVetoException if the recipient wishes the property
    *                                  change to be rolled back
    */
    public void vetoableChange(PropertyChangeEvent event) throws PropertyVetoException{
        getListener().vetoableChange(event);
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
