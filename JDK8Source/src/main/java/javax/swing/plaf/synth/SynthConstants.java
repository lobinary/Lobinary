/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.synth;

import javax.swing.*;

/**
 * Constants used by Synth. Not all Components support all states. A
 * Component will at least be in one of the primary states. That is, the
 * return value from <code>SynthContext.getComponentState()</code> will at
 * least be one of <code>ENABLED</code>, <code>MOUSE_OVER</code>,
 * <code>PRESSED</code> or <code>DISABLED</code>, and may also contain
 * <code>FOCUSED</code>, <code>SELECTED</code> or <code>DEFAULT</code>.
 *
 * <p>
 *  Synth使用的常数。并非所有组件都支持所有状态。组件将至少处于主要状态之一。
 * 也就是说,<code> SynthContext.getComponentState()</code>的返回值至少是<code> ENABLED </code>,<code> MOUSE_OVER </code>
 * ,<code> PRESSED </code >或<code> DISABLED </code>,并且还可以包含<code> FOCUSED </code>,<code> SELECTED </code>
 * 或<code> DEFAULT </code>。
 *  Synth使用的常数。并非所有组件都支持所有状态。组件将至少处于主要状态之一。
 * 
 * 
 * @since 1.5
 */
public interface SynthConstants {
    /**
     * Primary state indicating the component is enabled.
     * <p>
     *  指示组件已启用的主状态。
     * 
     */
    public static final int ENABLED = 1 << 0;
    /**
     * Primary state indicating the mouse is over the region.
     * <p>
     *  指示鼠标在区域上的主状态。
     * 
     */
    public static final int MOUSE_OVER = 1 << 1;
    /**
     * Primary state indicating the region is in a pressed state. Pressed
     * does not necessarily mean the user has pressed the mouse button.
     * <p>
     *  指示该区域处于按压状态的主状态。按下并不一定意味着用户已经按下了鼠标按钮。
     * 
     */
    public static final int PRESSED = 1 << 2;
    /**
     * Primary state indicating the region is not enabled.
     * <p>
     *  指示该区域的主状态未启用。
     * 
     */
    public static final int DISABLED = 1 << 3;

    /**
     * Indicates the region has focus.
     * <p>
     *  表示区域具有焦点。
     * 
     */
    public static final int FOCUSED = 1 << 8;
    /**
     * Indicates the region is selected.
     * <p>
     *  表示选择了区域。
     * 
     */
    public static final int SELECTED = 1 << 9;
    /**
     * Indicates the region is the default. This is typically used for buttons
     * to indicate this button is somehow special.
     * <p>
     *  表示区域是默认区域。这通常用于按钮以指示这个按钮是特别的。
     */
    public static final int DEFAULT = 1 << 10;
}
