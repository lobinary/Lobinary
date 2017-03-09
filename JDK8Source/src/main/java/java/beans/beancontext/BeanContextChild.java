/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.beans.PropertyVetoException;

import java.beans.beancontext.BeanContext;

/**
 * <p>
 * JavaBeans wishing to be nested within, and obtain a reference to their
 * execution environment, or context, as defined by the BeanContext
 * sub-interface shall implement this interface.
 * </p>
 * <p>
 * Conformant BeanContexts shall as a side effect of adding a BeanContextChild
 * object shall pass a reference to itself via the setBeanContext() method of
 * this interface.
 * </p>
 * <p>
 * Note that a BeanContextChild may refuse a change in state by throwing
 * PropertyVetoedException in response.
 * </p>
 * <p>
 * In order for persistence mechanisms to function properly on BeanContextChild
 * instances across a broad variety of scenarios, implementing classes of this
 * interface are required to define as transient, any or all fields, or
 * instance variables, that may contain, or represent, references to the
 * nesting BeanContext instance or other resources obtained
 * from the BeanContext via any unspecified mechanisms.
 * </p>
 *
 * <p>
 * <p>
 *  希望嵌套在其中的JavaBeans,并且获得对它们的执行环境或上下文的引用,如BeanContext子接口所定义的那样将实现该接口。
 * </p>
 * <p>
 *  符合BeanContext要作为添加一个BeanContextChild对象的副作用,应该通过此接口的setBeanContext()方法向其自身传递一个引用。
 * </p>
 * <p>
 *  注意,BeanContextChild可以通过抛出PropertyVetoedException来拒绝状态的改变。
 * </p>
 * <p>
 *  为了使持久化机制在BeanContextChild实例上跨越各种各样的场景正确地运行,实现这个接口的类需要定义为瞬态,任何或所有字段或实例变量,可以包含或表示对嵌套的引用BeanContext实例或通
 * 过任何未指定的机制从BeanContext获取的其他资源。
 * </p>
 * 
 * 
 * @author      Laurence P. G. Cable
 * @since       1.2
 *
 * @see java.beans.beancontext.BeanContext
 * @see java.beans.PropertyChangeEvent
 * @see java.beans.PropertyChangeListener
 * @see java.beans.PropertyVetoException
 * @see java.beans.VetoableChangeListener
 */

public interface BeanContextChild {

    /**
     * <p>
     * Objects that implement this interface,
     * shall fire a java.beans.PropertyChangeEvent, with parameters:
     *
     * propertyName "beanContext", oldValue (the previous nesting
     * <code>BeanContext</code> instance, or <code>null</code>),
     * newValue (the current nesting
     * <code>BeanContext</code> instance, or <code>null</code>).
     * <p>
     * A change in the value of the nesting BeanContext property of this
     * BeanContextChild may be vetoed by throwing the appropriate exception.
     * </p>
     * <p>
     * <p>
     *  实现此接口的对象将使用参数来触发java.beans.PropertyChangeEvent：
     * 
     *  propertyName"beanContext",oldValue(先前的嵌套<code> BeanContext </code>实例或<code> null </code>),newValue(当
     * 前嵌套<code> BeanContext </code> null </code>)。
     * <p>
     *  此BeanContextChild的嵌套BeanContext属性的值的更改可能会被抛出相应的异常。
     * </p>
     * 
     * @param bc The <code>BeanContext</code> with which
     * to associate this <code>BeanContextChild</code>.
     * @throws PropertyVetoException if the
     * addition of the specified <code>BeanContext</code> is refused.
     */
    void setBeanContext(BeanContext bc) throws PropertyVetoException;

    /**
     * Gets the <code>BeanContext</code> associated
     * with this <code>BeanContextChild</code>.
     * <p>
     * 获取与此<code> BeanContextChild </code>关联的<code> BeanContext </code>。
     * 
     * 
     * @return the <code>BeanContext</code> associated
     * with this <code>BeanContextChild</code>.
     */
    BeanContext getBeanContext();

    /**
     * Adds a <code>PropertyChangeListener</code>
     * to this <code>BeanContextChild</code>
     * in order to receive a <code>PropertyChangeEvent</code>
     * whenever the specified property has changed.
     * <p>
     *  向此<code> BeanContextChild </code>中添加<code> PropertyChangeListener </code>,以便在指定的属性更改时接收<code> Proper
     * tyChangeEvent </code>。
     * 
     * 
     * @param name the name of the property to listen on
     * @param pcl the <code>PropertyChangeListener</code> to add
     */
    void addPropertyChangeListener(String name, PropertyChangeListener pcl);

    /**
     * Removes a <code>PropertyChangeListener</code> from this
     * <code>BeanContextChild</code>  so that it no longer
     * receives <code>PropertyChangeEvents</code> when the
     * specified property is changed.
     *
     * <p>
     *  从此<code> BeanContextChild </code>中移除<code> PropertyChangeListener </code>,以便在指定的属性更改时不再接收<code> Prop
     * ertyChangeEvents </code>。
     * 
     * 
     * @param name the name of the property that was listened on
     * @param pcl the <code>PropertyChangeListener</code> to remove
     */
    void removePropertyChangeListener(String name, PropertyChangeListener pcl);

    /**
     * Adds a <code>VetoableChangeListener</code> to
     * this <code>BeanContextChild</code>
     * to receive events whenever the specified property changes.
     * <p>
     *  向此<code> BeanContextChild </code>中添加<code> VetoableChangeListener </code>以在指定的属性更改时接收事件。
     * 
     * 
     * @param name the name of the property to listen on
     * @param vcl the <code>VetoableChangeListener</code> to add
     */
    void addVetoableChangeListener(String name, VetoableChangeListener vcl);

    /**
     * Removes a <code>VetoableChangeListener</code> from this
     * <code>BeanContextChild</code> so that it no longer receives
     * events when the specified property changes.
     * <p>
     *  从此<code> BeanContextChild </code>中移除<code> VetoableChangeListener </code>,以使其在指定的属性更改时不再接收事件。
     * 
     * @param name the name of the property that was listened on.
     * @param vcl the <code>VetoableChangeListener</code> to remove.
     */
    void removeVetoableChangeListener(String name, VetoableChangeListener vcl);

}
