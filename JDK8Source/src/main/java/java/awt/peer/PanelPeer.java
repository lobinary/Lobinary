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

/**
 * The peer interface for {@link Panel}. This is a subinterface of
 * ContainerPeer and does not declare any additional methods because a Panel
 * is just that, a concrete Container.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Panel}的对等接口。这是ContainerPeer的子接口,不会声明任何其他方法,因为Panel只是一个具体的Container。
 * 
 */
public interface PanelPeer extends ContainerPeer {
}
