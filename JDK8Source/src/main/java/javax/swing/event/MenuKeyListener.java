/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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
 * MenuKeyListener
 *
 * <p>
 *  MenuKeyListener
 * 
 * 
 * @author Georges Saab
 */
public interface MenuKeyListener extends EventListener {
    /**
     * Invoked when a key has been typed.
     * This event occurs when a key press is followed by a key release.
     * <p>
     *  在键入键时调用。当按键后按键释放时,会发生此事件。
     * 
     */
    void menuKeyTyped(MenuKeyEvent e);

    /**
     * Invoked when a key has been pressed.
     * <p>
     *  在按下键时调用。
     * 
     */
    void menuKeyPressed(MenuKeyEvent e);

    /**
     * Invoked when a key has been released.
     * <p>
     *  在释放键时调用。
     */
    void menuKeyReleased(MenuKeyEvent e);
}
