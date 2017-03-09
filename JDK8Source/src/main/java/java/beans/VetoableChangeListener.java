/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1997, Oracle and/or its affiliates. All rights reserved.
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
 * A VetoableChange event gets fired whenever a bean changes a "constrained"
 * property.  You can register a VetoableChangeListener with a source bean
 * so as to be notified of any constrained property updates.
 * <p>
 *  当一个bean改变一个"约束"属性时,触发VetoableChange事件。您可以向源bean注册VetoableChangeListener,以便通知任何受限属性更新。
 * 
 */
public interface VetoableChangeListener extends java.util.EventListener {
    /**
     * This method gets called when a constrained property is changed.
     *
     * <p>
     *  当约束属性更改时,将调用此方法。
     * 
     * @param     evt a <code>PropertyChangeEvent</code> object describing the
     *                event source and the property that has changed.
     * @exception PropertyVetoException if the recipient wishes the property
     *              change to be rolled back.
     */
    void vetoableChange(PropertyChangeEvent evt)
                                throws PropertyVetoException;
}
