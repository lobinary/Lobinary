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
package javax.swing;


import java.awt.Component;
import java.awt.FocusTraversalPolicy;

/**
 * A FocusTraversalPolicy which can optionally provide an algorithm for
 * determining a JInternalFrame's initial Component. The initial Component is
 * the first to receive focus when the JInternalFrame is first selected. By
 * default, this is the same as the JInternalFrame's default Component to
 * focus.
 *
 * <p>
 *  一个FocusTraversalPolicy,它可以选择提供一个算法来确定JInternalFrame的初始组件。初始组件是首次选择JInternalFrame时首先接收焦点。
 * 默认情况下,这与JInternalFrame的默认组件相同。
 * 
 * 
 * @author David Mendenhall
 *
 * @since 1.4
 */
public abstract class InternalFrameFocusTraversalPolicy
    extends FocusTraversalPolicy
{

    /**
     * Returns the Component that should receive the focus when a
     * JInternalFrame is selected for the first time. Once the JInternalFrame
     * has been selected by a call to <code>setSelected(true)</code>, the
     * initial Component will not be used again. Instead, if the JInternalFrame
     * loses and subsequently regains selection, or is made invisible or
     * undisplayable and subsequently made visible and displayable, the
     * JInternalFrame's most recently focused Component will become the focus
     * owner. The default implementation of this method returns the
     * JInternalFrame's default Component to focus.
     *
     * <p>
     *  返回当第一次选择JInternalFrame时应该接收焦点的组件。
     * 一旦通过调用<code> setSelected(true)</code>选择了JInternalFrame,初始组件将不会再次使用。
     * 相反,如果JInternalFrame丢失并随后重新获得选择,或被设置为不可见或不可显示,并随后显示和显示,JInternalFrame的最近聚焦的组件将成为焦点所有者。
     * 
     * @param frame the JInternalFrame whose initial Component is to be
     *        returned
     * @return the Component that should receive the focus when frame is
     *         selected for the first time, or null if no suitable Component
     *         can be found
     * @see JInternalFrame#getMostRecentFocusOwner
     * @throws IllegalArgumentException if window is null
     */
    public Component getInitialComponent(JInternalFrame frame) {
        return getDefaultComponent(frame);
    }
}
