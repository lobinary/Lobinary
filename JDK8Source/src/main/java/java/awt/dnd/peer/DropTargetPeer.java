/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.dnd.DropTarget;

/**
 * <p>
 * The DropTargetPeer class is the interface to the platform dependent
 * DnD facilities. Since the DnD system is based on the native platform's
 * facilities, a DropTargetPeer will be associated with a ComponentPeer
 * of the nearsest enclosing native Container (in the case of lightweights)
 * </p>
 *
 * <p>
 * <p>
 *  DropTargetPeer类是与平台相关的DnD设施的接口。
 * 由于DnD系统基于本地平台的设施,DropTargetPeer将与最近的封闭本地容器(在轻量级的情况下)的ComponentPeer相关联,。
 * </p>
 * 
 * 
 * @since 1.2
 *
 */

public interface DropTargetPeer {

    /**
     * Add the DropTarget to the System
     *
     * <p>
     *  将DropTarget添加到系统
     * 
     * 
     * @param dt The DropTarget effected
     */

    void addDropTarget(DropTarget dt);

    /**
     * Remove the DropTarget from the system
     *
     * <p>
     *  从系统中删除DropTarget
     * 
     * @param dt The DropTarget effected
     */

    void removeDropTarget(DropTarget dt);
}
