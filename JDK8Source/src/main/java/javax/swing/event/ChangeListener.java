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


import java.util.EventListener;


/**
 * Defines an object which listens for ChangeEvents.
 *
 * <p>
 *  定义侦听ChangeEvents的对象。
 * 
 * 
 * @author Jeff Dinkins
 */
public interface ChangeListener extends EventListener {
    /**
     * Invoked when the target of the listener has changed its state.
     *
     * <p>
     *  当侦听器的目标已更改其状态时调用。
     * 
     * @param e  a ChangeEvent object
     */
    void stateChanged(ChangeEvent e);
}
