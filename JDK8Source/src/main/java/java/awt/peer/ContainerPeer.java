/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * The peer interface for {@link Container}. This is the parent interface
 * for all container like widgets.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Container}的对等接口。这是所有容器的窗口小部件的父接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface ContainerPeer extends ComponentPeer {

    /**
     * Returns the insets of this container. Insets usually is the space that
     * is occupied by things like borders.
     *
     * <p>
     *  返回此容器的插入。插图通常是被像边框这样的东西占据的空间。
     * 
     * 
     * @return the insets of this container
     */
    Insets getInsets();

    /**
     * Notifies the peer that validation of the component tree is about to
     * begin.
     *
     * <p>
     *  通知对等体组件树的验证即将开始。
     * 
     * 
     * @see Container#validate()
     */
    void beginValidate();

    /**
     * Notifies the peer that validation of the component tree is finished.
     *
     * <p>
     *  通知对等体组件树的验证已完成。
     * 
     * 
     * @see Container#validate()
     */
    void endValidate();

    /**
     * Notifies the peer that layout is about to begin. This is called
     * before the container itself and its children are laid out.
     *
     * <p>
     *  通知对等体布局即将开始。这是在容器本身和它的孩子布局之前调用的。
     * 
     * 
     * @see Container#validateTree()
     */
    void beginLayout();

    /**
     * Notifies the peer that layout is finished. This is called after the
     * container and its children have been laid out.
     *
     * <p>
     *  通知对等体布局完成。这是在容器和它的孩子被布置之后调用的。
     * 
     * @see Container#validateTree()
     */
    void endLayout();
}
