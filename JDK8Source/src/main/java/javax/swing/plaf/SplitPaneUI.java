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

package javax.swing.plaf;

import javax.swing.JSplitPane;
import java.awt.Graphics;

/**
 * Pluggable look and feel interface for JSplitPane.
 *
 * <p>
 *  JSplitPane的可插拔外观界面。
 * 
 * 
 * @author Scott Violet
 */
public abstract class SplitPaneUI extends ComponentUI
{
    /**
     * Messaged to relayout the JSplitPane based on the preferred size
     * of the children components.
     * <p>
     *  消息传递到JSplitPane基于孩子组件的首选大小。
     * 
     */
    public abstract void resetToPreferredSizes(JSplitPane jc);

    /**
     * Sets the location of the divider to location.
     * <p>
     *  将分隔符的位置设置为位置。
     * 
     */
    public abstract void setDividerLocation(JSplitPane jc, int location);

    /**
     * Returns the location of the divider.
     * <p>
     *  返回分隔符的位置。
     * 
     */
    public abstract int getDividerLocation(JSplitPane jc);

    /**
     * Returns the minimum possible location of the divider.
     * <p>
     *  返回分隔符的最小可能位置。
     * 
     */
    public abstract int getMinimumDividerLocation(JSplitPane jc);

    /**
     * Returns the maximum possible location of the divider.
     * <p>
     *  返回分隔符的最大可能位置。
     * 
     */
    public abstract int getMaximumDividerLocation(JSplitPane jc);

    /**
     * Messaged after the JSplitPane the receiver is providing the look
     * and feel for paints its children.
     * <p>
     *  JSplitPane之后的消息接收器提供了它的孩子的油漆的外观和感觉。
     */
    public abstract void finishedPaintingChildren(JSplitPane jc, Graphics g);
}
