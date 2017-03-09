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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.InvalidDnDOperationException;

/**
 * <p>
 * This interface is exposed by the underlying window system platform to
 * enable control of platform DnD operations
 * </p>
 *
 * <p>
 * <p>
 *  该接口由底层窗口系统平台暴露以实现对平台DnD操作的控制
 * </p>
 * 
 * 
 * @since 1.2
 *
 */

public interface DropTargetContextPeer {

    /**
     * update the peer's notion of the Target's actions
     * <p>
     *  更新对等体对目标的行为的概念
     * 
     */

    void setTargetActions(int actions);

    /**
     * get the current Target actions
     * <p>
     *  获取当前的目标操作
     * 
     */

    int getTargetActions();

    /**
     * get the DropTarget associated with this peer
     * <p>
     *  获取与此对等关联的DropTarget
     * 
     */

    DropTarget getDropTarget();

    /**
     * get the (remote) DataFlavors from the peer
     * <p>
     *  从对等体获取(远程)DataFlavors
     * 
     */

    DataFlavor[] getTransferDataFlavors();

    /**
     * get an input stream to the remote data
     * <p>
     *  获取到远程数据的输入流
     * 
     */

    Transferable getTransferable() throws InvalidDnDOperationException;

    /**
    /* <p>
    /* 
     * @return if the DragSource Transferable is in the same JVM as the Target
     */

    boolean isTransferableJVMLocal();

    /**
     * accept the Drag
     * <p>
     *  接受拖动
     * 
     */

    void acceptDrag(int dragAction);

    /**
     * reject the Drag
     * <p>
     *  拒绝拖动
     * 
     */

    void rejectDrag();

    /**
     * accept the Drop
     * <p>
     *  接受掉落
     * 
     */

    void acceptDrop(int dropAction);

    /**
     * reject the Drop
     * <p>
     *  拒绝掉落
     * 
     */

    void rejectDrop();

    /**
     * signal complete
     * <p>
     *  信号完成
     */

    void dropComplete(boolean success);

}
