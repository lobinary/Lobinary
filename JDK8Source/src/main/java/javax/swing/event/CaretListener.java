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
 * Listener for changes in the caret position of a text
 * component.
 *
 * <p>
 *  用于文本组件的插入符位置更改的侦听器。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface CaretListener extends EventListener {

    /**
     * Called when the caret position is updated.
     *
     * <p>
     *  更新插入符位置时调用。
     * 
     * @param e the caret event
     */
    void caretUpdate(CaretEvent e);
}
