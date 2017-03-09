/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * The LightweightPeer interface marks a component as depending upon
 * a native container so window related events can be routed to the
 * component.  Since this only applies to components and their
 * extensions, this interface extends ComponentPeer.
 * <p>
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 *
 * <p>
 *  LightweightPeer接口将组件标记为取决于本机容器,因此可以将与窗口相关的事件路由到组件。因为这仅适用于组件及其扩展,所以此接口扩展ComponentPeer。
 * <p>
 * 
 * @author Timothy Prinzing
 */
public interface LightweightPeer extends ComponentPeer {

}
