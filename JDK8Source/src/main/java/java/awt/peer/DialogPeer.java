/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.peer;

import java.awt.*;

/**
 * The peer interface for {@link Dialog}. This adds a couple of dialog specific
 * features to the {@link WindowPeer} interface.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Dialog}的对等接口。这会为{@link WindowPeer}界面添加几个对话框特定功能。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface DialogPeer extends WindowPeer {

    /**
     * Sets the title on the dialog window.
     *
     * <p>
     *  设置对话框窗口上的标题。
     * 
     * 
     * @param title the title to set
     *
     * @see Dialog#setTitle(String)
     */
    void setTitle(String title);

    /**
     * Sets if the dialog should be resizable or not.
     *
     * <p>
     *  设置是否应该调整对话框的大小。
     * 
     * 
     * @param resizeable {@code true} when the dialog should be resizable,
     *        {@code false} if not
     *
     * @see Dialog#setResizable(boolean)
     */
    void setResizable(boolean resizeable);

    /**
     * Block the specified windows. This is used for modal dialogs.
     *
     * <p>
     *  阻止指定的窗口。这用于模态对话框。
     * 
     * @param windows the windows to block
     *
     * @see Dialog#modalShow()
     * @see Dialog#blockWindows()
     */
    void blockWindows(java.util.List<Window> windows);
}
