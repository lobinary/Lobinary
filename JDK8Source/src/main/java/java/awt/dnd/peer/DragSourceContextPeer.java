/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd.peer;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.InvalidDnDOperationException;


/**
 * <p>
 * This interface is supplied by the underlying window system platform to
 * expose the behaviors of the Drag and Drop system to an originator of
 * the same
 * </p>
 *
 * <p>
 * <p>
 *  该接口由底层窗口系统平台提供,以将拖放系统的行为暴露给其的发起者
 * </p>
 * 
 * 
 * @since 1.2
 *
 */

public interface DragSourceContextPeer {

    /**
     * start a drag
     * <p>
     *  开始拖动
     * 
     */

    void startDrag(DragSourceContext dsc, Cursor c, Image dragImage, Point imageOffset) throws InvalidDnDOperationException;

    /**
     * return the current drag cursor
     * <p>
     *  返回当前拖动光标
     * 
     */

    Cursor getCursor();

    /**
     * set the current drag cursor
     * <p>
     *  设置当前拖动光标
     * 
     */

    void setCursor(Cursor c) throws InvalidDnDOperationException;

    /**
     * notify the peer that the Transferables DataFlavors have changed
     * <p>
     *  通知对等体Transferable DataFlavors已更改
     */

    void transferablesFlavorsChanged();
}
