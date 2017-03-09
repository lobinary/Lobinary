/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels.spi;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;
import sun.security.action.GetPropertyAction;


/**
 * Service-provider class for selectors and selectable channels.
 *
 * <p> A selector provider is a concrete subclass of this class that has a
 * zero-argument constructor and implements the abstract methods specified
 * below.  A given invocation of the Java virtual machine maintains a single
 * system-wide default provider instance, which is returned by the {@link
 * #provider() provider} method.  The first invocation of that method will locate
 * the default provider as specified below.
 *
 * <p> The system-wide default provider is used by the static <tt>open</tt>
 * methods of the {@link java.nio.channels.DatagramChannel#open
 * DatagramChannel}, {@link java.nio.channels.Pipe#open Pipe}, {@link
 * java.nio.channels.Selector#open Selector}, {@link
 * java.nio.channels.ServerSocketChannel#open ServerSocketChannel}, and {@link
 * java.nio.channels.SocketChannel#open SocketChannel} classes.  It is also
 * used by the {@link java.lang.System#inheritedChannel System.inheritedChannel()}
 * method. A program may make use of a provider other than the default provider
 * by instantiating that provider and then directly invoking the <tt>open</tt>
 * methods defined in this class.
 *
 * <p> All of the methods in this class are safe for use by multiple concurrent
 * threads.  </p>
 *
 *
 * <p>
 *  选择器和可选通道的服务提供程序类。
 * 
 *  <p>选择器提供程序是此类的一个具体子类,它具有零参数构造函数,并实现下面指定的抽象方法。
 *  Java虚拟机的给定调用维护单个系统范围的默认提供程序实例,该实例由{@link #provider()provider}方法返回。该方法的第一次调用将定位下面指定的默认提供程序。
 * 
 *  <p>系统范围的默认提供程序由{@link java.nio.channels.DatagramChannel#open DatagramChannel}的静态<tt> open </t>方法使用,{@link java.nio.channels.Pipe #open Pipe}
 * ,{@link java.nio.channels.Selector#open Selector},{@link java.nio.channels.ServerSocketChannel#open ServerSocketChannel}
 * 和{@link java.nio.channels.SocketChannel#open SocketChannel}类。
 * 它也被{@link java.lang.System#inheritedChannel System.inheritedChannel()}方法使用。
 * 程序可以通过实例化该提供者然后直接调用该类中定义的<tt>打开</tt>方法来使用除默认提供者之外的提供者。
 * 
 *  <p>此类中的所有方法都可安全地用于多个并发线程。 </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class SelectorProvider {

    private static final Object lock = new Object();
    private static SelectorProvider provider = null;

    /**
     * Initializes a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     *          {@link RuntimePermission}<tt>("selectorProvider")</tt>
     */
    protected SelectorProvider() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new RuntimePermission("selectorProvider"));
    }

    private static boolean loadProviderFromProperty() {
        String cn = System.getProperty("java.nio.channels.spi.SelectorProvider");
        if (cn == null)
            return false;
        try {
            Class<?> c = Class.forName(cn, true,
                                       ClassLoader.getSystemClassLoader());
            provider = (SelectorProvider)c.newInstance();
            return true;
        } catch (ClassNotFoundException x) {
            throw new ServiceConfigurationError(null, x);
        } catch (IllegalAccessException x) {
            throw new ServiceConfigurationError(null, x);
        } catch (InstantiationException x) {
            throw new ServiceConfigurationError(null, x);
        } catch (SecurityException x) {
            throw new ServiceConfigurationError(null, x);
        }
    }

    private static boolean loadProviderAsService() {

        ServiceLoader<SelectorProvider> sl =
            ServiceLoader.load(SelectorProvider.class,
                               ClassLoader.getSystemClassLoader());
        Iterator<SelectorProvider> i = sl.iterator();
        for (;;) {
            try {
                if (!i.hasNext())
                    return false;
                provider = i.next();
                return true;
            } catch (ServiceConfigurationError sce) {
                if (sce.getCause() instanceof SecurityException) {
                    // Ignore the security exception, try the next provider
                    continue;
                }
                throw sce;
            }
        }
    }

    /**
     * Returns the system-wide default selector provider for this invocation of
     * the Java virtual machine.
     *
     * <p> The first invocation of this method locates the default provider
     * object as follows: </p>
     *
     * <ol>
     *
     *   <li><p> If the system property
     *   <tt>java.nio.channels.spi.SelectorProvider</tt> is defined then it is
     *   taken to be the fully-qualified name of a concrete provider class.
     *   The class is loaded and instantiated; if this process fails then an
     *   unspecified error is thrown.  </p></li>
     *
     *   <li><p> If a provider class has been installed in a jar file that is
     *   visible to the system class loader, and that jar file contains a
     *   provider-configuration file named
     *   <tt>java.nio.channels.spi.SelectorProvider</tt> in the resource
     *   directory <tt>META-INF/services</tt>, then the first class name
     *   specified in that file is taken.  The class is loaded and
     *   instantiated; if this process fails then an unspecified error is
     *   thrown.  </p></li>
     *
     *   <li><p> Finally, if no provider has been specified by any of the above
     *   means then the system-default provider class is instantiated and the
     *   result is returned.  </p></li>
     *
     * </ol>
     *
     * <p> Subsequent invocations of this method return the provider that was
     * returned by the first invocation.  </p>
     *
     * <p>
     * 返回此虚拟机调用的系统级默认选择器提供程序。
     * 
     *  <p>此方法的第一个调用如下所示定位默认提供程序对象：</p>
     * 
     * <ol>
     * 
     *  <li> <p>如果定义了系统属性<tt> java.nio.channels.spi.SelectorProvider </tt>,那么它将被视为具体提供程序类的完全限定名称。
     * 类被加载和实例化;如果此过程失败,则抛出未指定的错误。 </p> </li>。
     * 
     *  <li> <p>如果提供程序类已安装在系统类加载器可见的jar文件中,并且该jar文件包含名为<tt> java.nio.channels.spi.SelectorProvider < / tt>在资
     * 源目录<tt> META-INF / services </tt>中,那么将获取该文件中指定的第一个类名。
     * 类被加载和实例化;如果此过程失败,则抛出未指定的错误。 </p> </li>。
     * 
     *  <li> <p>最后,如果没有通过上述任何方法指定提供程序,则系统默认提供程序类将被实例化,并返回结果。 </p> </li>
     * 
     * </ol>
     * 
     *  <p>此方法的后续调用返回由第一次调用返回的提供程序。 </p>
     * 
     * 
     * @return  The system-wide default selector provider
     */
    public static SelectorProvider provider() {
        synchronized (lock) {
            if (provider != null)
                return provider;
            return AccessController.doPrivileged(
                new PrivilegedAction<SelectorProvider>() {
                    public SelectorProvider run() {
                            if (loadProviderFromProperty())
                                return provider;
                            if (loadProviderAsService())
                                return provider;
                            provider = sun.nio.ch.DefaultSelectorProvider.create();
                            return provider;
                        }
                    });
        }
    }

    /**
     * Opens a datagram channel.
     *
     * <p>
     *  打开数据报通道。
     * 
     * 
     * @return  The new channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract DatagramChannel openDatagramChannel()
        throws IOException;

    /**
     * Opens a datagram channel.
     *
     * <p>
     *  打开数据报通道。
     * 
     * 
     * @param   family
     *          The protocol family
     *
     * @return  A new datagram channel
     *
     * @throws  UnsupportedOperationException
     *          If the specified protocol family is not supported
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @since 1.7
     */
    public abstract DatagramChannel openDatagramChannel(ProtocolFamily family)
        throws IOException;

    /**
     * Opens a pipe.
     *
     * <p>
     *  打开管道。
     * 
     * 
     * @return  The new pipe
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract Pipe openPipe()
        throws IOException;

    /**
     * Opens a selector.
     *
     * <p>
     *  打开选择器。
     * 
     * 
     * @return  The new selector
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract AbstractSelector openSelector()
        throws IOException;

    /**
     * Opens a server-socket channel.
     *
     * <p>
     *  打开服务器套接字通道。
     * 
     * 
     * @return  The new channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract ServerSocketChannel openServerSocketChannel()
        throws IOException;

    /**
     * Opens a socket channel.
     *
     * <p>
     *  打开套接字通道。
     * 
     * 
     * @return  The new channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract SocketChannel openSocketChannel()
        throws IOException;

    /**
     * Returns the channel inherited from the entity that created this
     * Java virtual machine.
     *
     * <p> On many operating systems a process, such as a Java virtual
     * machine, can be started in a manner that allows the process to
     * inherit a channel from the entity that created the process. The
     * manner in which this is done is system dependent, as are the
     * possible entities to which the channel may be connected. For example,
     * on UNIX systems, the Internet services daemon (<i>inetd</i>) is used to
     * start programs to service requests when a request arrives on an
     * associated network port. In this example, the process that is started,
     * inherits a channel representing a network socket.
     *
     * <p> In cases where the inherited channel represents a network socket
     * then the {@link java.nio.channels.Channel Channel} type returned
     * by this method is determined as follows:
     *
     * <ul>
     *
     *  <li><p> If the inherited channel represents a stream-oriented connected
     *  socket then a {@link java.nio.channels.SocketChannel SocketChannel} is
     *  returned. The socket channel is, at least initially, in blocking
     *  mode, bound to a socket address, and connected to a peer.
     *  </p></li>
     *
     *  <li><p> If the inherited channel represents a stream-oriented listening
     *  socket then a {@link java.nio.channels.ServerSocketChannel
     *  ServerSocketChannel} is returned. The server-socket channel is, at
     *  least initially, in blocking mode, and bound to a socket address.
     *  </p></li>
     *
     *  <li><p> If the inherited channel is a datagram-oriented socket
     *  then a {@link java.nio.channels.DatagramChannel DatagramChannel} is
     *  returned. The datagram channel is, at least initially, in blocking
     *  mode, and bound to a socket address.
     *  </p></li>
     *
     * </ul>
     *
     * <p> In addition to the network-oriented channels described, this method
     * may return other kinds of channels in the future.
     *
     * <p> The first invocation of this method creates the channel that is
     * returned. Subsequent invocations of this method return the same
     * channel. </p>
     *
     * <p>
     *  返回从创建此Java虚拟机的实体继承的通道。
     * 
     * <p>在许多操作系统上,可以以允许进程从创建进程的实体继承通道的方式启动进程(如Java虚拟机)。这样做的方式是系统相关的,信道可以连接到的可能实体也是这样。
     * 例如,在UNIX系统上,Internet服务守护程序(<i> inetd </i>)用于在请求在关联的网络端口上到达时启动程序以服务请求。在此示例中,启动的进程继承了表示网络套接字的通道。
     * 
     *  <p>如果继承的频道代表网络套接字,则由此方法返回的{@link java.nio.channels.Channel Channel}类型确定如下：
     * 
     * <ul>
     * 
     *  <li> <p>如果继承的通道代表面向流的连接套接字,则返回{@link java.nio.channels.SocketChannel SocketChannel}。
     * 套接字通道至少最初是处于阻塞模式,绑定到套接字地址,并且连接到对等体。 </p> </li>。
     * 
     * 
     * @return  The inherited channel, if any, otherwise <tt>null</tt>.
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     *          {@link RuntimePermission}<tt>("inheritedChannel")</tt>
     *
     * @since 1.5
     */
   public Channel inheritedChannel() throws IOException {
        return null;
   }

}
