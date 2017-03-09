/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.util.EventListener;
import java.awt.AWTEvent;

/**
 * The listener interface for receiving notification of events
 * dispatched to objects that are instances of Component or
 * MenuComponent or their subclasses.  Unlike the other EventListeners
 * in this package, AWTEventListeners passively observe events
 * being dispatched in the AWT, system-wide.  Most applications
 * should never use this class; applications which might use
 * AWTEventListeners include event recorders for automated testing,
 * and facilities such as the Java Accessibility package.
 * <p>
 * The class that is interested in monitoring AWT events
 * implements this interface, and the object created with that
 * class is registered with the Toolkit, using the Toolkit's
 * <code>addAWTEventListener</code> method.  When an event is
 * dispatched anywhere in the AWT, that object's
 * <code>eventDispatched</code> method is invoked.
 *
 * <p>
 *  侦听器接口,用于接收分派到作为Component或MenuComponent或其子类的实例的对象的事件的通知。
 * 与此包中的其他EventListeners不同,AWTEventListeners被动地观察在AWT(系统范围)中分派的事件。
 * 大多数应用程序不应该使用这个类;可能使用AWTEventListeners的应用程序包括用于自动测试的事件记录器以及诸如Java辅助功能包之类的工具。
 * <p>
 *  对监视AWT事件感兴趣的类实现此接口,使用Toolkit的<code> addAWTEventListener </code>方法向Toolkit注册使用该类创建的对象。
 * 
 * @see java.awt.AWTEvent
 * @see java.awt.Toolkit#addAWTEventListener
 * @see java.awt.Toolkit#removeAWTEventListener
 *
 * @author Fred Ecks
 * @since 1.2
 */
public interface AWTEventListener extends EventListener {

    /**
     * Invoked when an event is dispatched in the AWT.
     * <p>
     * 当在AWT中的任何地方分派事件时,将调用该对象的<code> eventDispatched </code>方法。
     * 
     */
    public void eventDispatched(AWTEvent event);

}
