/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * <p>
 * This interface is intended to be implemented by, or delegated from, instances
 * of java.beans.beancontext.BeanContext, in order to propagate to its nested hierarchy
 * of java.beans.beancontext.BeanContextChild instances, the current "designTime" property.
 * <p>
 * The JavaBeans&trade; specification defines the notion of design time as is a
 * mode in which JavaBeans instances should function during their composition
 * and customization in a interactive design, composition or construction tool,
 * as opposed to runtime when the JavaBean is part of an applet, application,
 * or other live Java executable abstraction.
 *
 * <p>
 * <p>
 *  此接口旨在由java.beans.beancontext.BeanContext的实例实现或委派,以便传播到其java.beans.beancontext.BeanContextChild实例的嵌套层
 * 次结构,即当前的"designTime"属性。
 * <p>
 *  JavaBeans&trade;规范定义了设计时间的概念,作为在交互式设计,组合或构造工具中的组合和定制期间JavaBeans实例应当运行的模式,而不是当JavaBean是小应用程序,应用或其他实况的
 * 一部分时的运行时Java可执行文件抽象。
 * 
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 *
 * @see java.beans.beancontext.BeanContext
 * @see java.beans.beancontext.BeanContextChild
 * @see java.beans.beancontext.BeanContextMembershipListener
 * @see java.beans.PropertyChangeEvent
 */

public interface DesignMode {

    /**
     * The standard value of the propertyName as fired from a BeanContext or
     * other source of PropertyChangeEvents.
     * <p>
     *  从BeanContext或其他PropertyChangeEvents来源触发的propertyName的标准值。
     * 
     */

    static String PROPERTYNAME = "designTime";

    /**
     * Sets the "value" of the "designTime" property.
     * <p>
     * If the implementing object is an instance of java.beans.beancontext.BeanContext,
     * or a subinterface thereof, then that BeanContext should fire a
     * PropertyChangeEvent, to its registered BeanContextMembershipListeners, with
     * parameters:
     * <ul>
     *    <li><code>propertyName</code> - <code>java.beans.DesignMode.PROPERTYNAME</code>
     *    <li><code>oldValue</code> - previous value of "designTime"
     *    <li><code>newValue</code> - current value of "designTime"
     * </ul>
     * Note it is illegal for a BeanContextChild to invoke this method
     * associated with a BeanContext that it is nested within.
     *
     * <p>
     *  设置"designTime"属性的"值"。
     * <p>
     *  如果实现对象是java.beans.beancontext.BeanContext的实例或其子接口,那么该BeanContext应该使用参数将PropertyChangeEvent触发到其注册的Bea
     * nContextMembershipListeners：。
     * <ul>
     *  <li> <code> propertyName </code>  -  <code> java.beans.DesignMode.PROPERTYNAME </code> <li> <code> o
     * ldValue </code> newValue </code>  - 当前值"designTime"。
     * 
     * @param designTime  the current "value" of the "designTime" property
     * @see java.beans.beancontext.BeanContext
     * @see java.beans.beancontext.BeanContextMembershipListener
     * @see java.beans.PropertyChangeEvent
     */

    void setDesignTime(boolean designTime);

    /**
     * A value of true denotes that JavaBeans should behave in design time
     * mode, a value of false denotes runtime behavior.
     *
     * <p>
     * </ul>
     *  注意,BeanContextChild调用与嵌套在其中的BeanContext相关联的此方法是非法的。
     * 
     * 
     * @return the current "value" of the "designTime" property.
     */

    boolean isDesignTime();
}
