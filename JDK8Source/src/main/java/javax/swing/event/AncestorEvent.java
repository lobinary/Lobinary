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
package javax.swing.event;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * An event reported to a child component that originated from an
 * ancestor in the component hierarchy.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  报告给子组件的事件,源自组件层次结构中的祖先。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Dave Moore
 */
@SuppressWarnings("serial")
public class AncestorEvent extends AWTEvent {
    /**
     * An ancestor-component was added to the hierarchy of
     * visible objects (made visible), and is currently being displayed.
     * <p>
     *  将祖先组件添加到可见对象的层次结构中(使其可见),并且当前正在显示。
     * 
     */
    public static final int ANCESTOR_ADDED = 1;
    /**
     * An ancestor-component was removed from the hierarchy
     * of visible objects (hidden) and is no longer being displayed.
     * <p>
     *  祖代组件从可见对象的层次结构中删除(隐藏),不再显示。
     * 
     */
    public static final int ANCESTOR_REMOVED = 2;
    /** An ancestor-component changed its position on the screen. */
    public static final int ANCESTOR_MOVED = 3;

    Container ancestor;
    Container ancestorParent;

    /**
     * Constructs an AncestorEvent object to identify a change
     * in an ancestor-component's display-status.
     *
     * <p>
     *  构造AncestorEvent对象以标识祖先组件的显示状态中的更改。
     * 
     * 
     * @param source          the JComponent that originated the event
     *                        (typically <code>this</code>)
     * @param id              an int specifying {@link #ANCESTOR_ADDED},
     *                        {@link #ANCESTOR_REMOVED} or {@link #ANCESTOR_MOVED}
     * @param ancestor        a Container object specifying the ancestor-component
     *                        whose display-status changed
     * @param ancestorParent  a Container object specifying the ancestor's parent
     */
    public AncestorEvent(JComponent source, int id, Container ancestor, Container ancestorParent) {
        super(source, id);
        this.ancestor = ancestor;
        this.ancestorParent = ancestorParent;
    }

    /**
     * Returns the ancestor that the event actually occurred on.
     * <p>
     *  返回事件实际发生的祖先。
     * 
     */
    public Container getAncestor() {
        return ancestor;
    }

    /**
     * Returns the parent of the ancestor the event actually occurred on.
     * This is most interesting in an ANCESTOR_REMOVED event, as
     * the ancestor may no longer be in the component hierarchy.
     * <p>
     *  返回事件实际发生的祖先的父级。这在ANCESTOR_REMOVED事件中最有趣,因为祖先可能不再在组件层次结构中。
     * 
     */
    public Container getAncestorParent() {
        return ancestorParent;
    }

    /**
     * Returns the component that the listener was added to.
     * <p>
     *  返回侦听器添加到的组件。
     */
    public JComponent getComponent() {
        return (JComponent)getSource();
    }
}
