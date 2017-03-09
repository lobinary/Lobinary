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

import java.awt.Label;

/**
 * The peer interface for {@link Label}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Label}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface LabelPeer extends ComponentPeer {

    /**
     * Sets the text to be displayed on the label.
     *
     * <p>
     *  设置要在标签上显示的文本。
     * 
     * 
     * @param label the text to be displayed on the label
     *
     * @see Label#setText
     */
    void setText(String label);

    /**
     * Sets the alignment of the label text.
     *
     * <p>
     *  设置标签文本的对齐方式。
     * 
     * @param alignment the alignment of the label text
     *
     * @see Label#setAlignment(int)
     * @see Label#CENTER
     * @see Label#RIGHT
     * @see Label#LEFT
     */
    void setAlignment(int alignment);
}
