/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi;

/**
 * The <code>Remote</code> interface serves to identify interfaces whose
 * methods may be invoked from a non-local virtual machine.  Any object that
 * is a remote object must directly or indirectly implement this interface.
 * Only those methods specified in a "remote interface", an interface that
 * extends <code>java.rmi.Remote</code> are available remotely.
 *
 * <p>Implementation classes can implement any number of remote interfaces and
 * can extend other remote implementation classes.  RMI provides some
 * convenience classes that remote object implementations can extend which
 * facilitate remote object creation.  These classes are
 * <code>java.rmi.server.UnicastRemoteObject</code> and
 * <code>java.rmi.activation.Activatable</code>.
 *
 * <p>For complete details on RMI, see the <a
 href=../../../platform/rmi/spec/rmiTOC.html>RMI Specification</a> which describes the RMI API and system.
 *
 * <p>
 *  <code> Remote </code>接口用于标识可以从非本地虚拟机调用方法的接口。任何作为远程对象的对象必须直接或间接实现此接口。
 * 只有在"远程接口"中指定的那些方法,扩展<code> java.rmi.Remote </code>的接口可远程使用。
 * 
 *  <p>实现类可以实现任何数量的远程接口,并可以扩展其他远程实现类。 RMI提供了一些方便的类,远程对象实现可以扩展,便于远程对象创建。
 * 这些类是<code> java.rmi.server.UnicastRemoteObject </code>和<code> java.rmi.activation.Activatable </code>
 * 
 * @since   JDK1.1
 * @author  Ann Wollrath
 * @see     java.rmi.server.UnicastRemoteObject
 * @see     java.rmi.activation.Activatable
 */
public interface Remote {}
