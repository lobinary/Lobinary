/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
import java.util.*;

import javax.swing.*;

/**
 * AncestorListener
 *
 * Interface to support notification when changes occur to a JComponent or one
 * of its ancestors.  These include movement and when the component becomes
 * visible or invisible, either by the setVisible() method or by being added
 * or removed from the component hierarchy.
 *
 * <p>
 *  AncestorListener
 * 
 *  用于在JComponent或其某个祖先发生更改时支持通知的界面。这些包括运动以及组件何时变为可见或不可见(通过setVisible()方法或通过添加或从组件层次结构中删除)。
 * 
 * 
 * @author Dave Moore
 */
public interface AncestorListener extends EventListener {
    /**
     * Called when the source or one of its ancestors is made visible
     * either by setVisible(true) being called or by its being
     * added to the component hierarchy.  The method is only called
     * if the source has actually become visible.  For this to be true
     * all its parents must be visible and it must be in a hierarchy
     * rooted at a Window
     * <p>
     *  当源或其祖先之一通过被调用的setVisible(true)或者被添加到组件层次结构而变为可见时调用。仅当源实际上变为可见时才调用该方法。
     * 为了这是真的所有的父亲必须是可见的,它必须在一个层次根植于窗口。
     * 
     */
    public void ancestorAdded(AncestorEvent event);

    /**
     * Called when the source or one of its ancestors is made invisible
     * either by setVisible(false) being called or by its being
     * remove from the component hierarchy.  The method is only called
     * if the source has actually become invisible.  For this to be true
     * at least one of its parents must by invisible or it is not in
     * a hierarchy rooted at a Window
     * <p>
     *  当通过调用setVisible(false)或者从组件层次结构中删除源或其祖先中的一个时,调用此方法。仅当源实际上变为不可见时才调用该方法。
     * 为了这是真实的,它的父亲中的至少一个必须是不可见的或者它不是在根源于窗口的层次结构中。
     * 
     */
    public void ancestorRemoved(AncestorEvent event);

    /**
     * Called when either the source or one of its ancestors is moved.
     * <p>
     */
    public void ancestorMoved(AncestorEvent event);

}
