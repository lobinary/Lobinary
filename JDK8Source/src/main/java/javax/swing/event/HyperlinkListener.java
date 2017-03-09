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
 * HyperlinkListener
 *
 * <p>
 *  超链接监听器
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface HyperlinkListener extends EventListener {

    /**
     * Called when a hypertext link is updated.
     *
     * <p>
     *  当超文本链接更新时调用。
     * 
     * @param e the event responsible for the update
     */
    void hyperlinkUpdate(HyperlinkEvent e);
}
