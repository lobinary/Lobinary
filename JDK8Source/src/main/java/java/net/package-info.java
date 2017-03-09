/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Provides the classes for implementing networking applications.
 *
 * <p> The java.net package can be roughly divided in two sections:</p>
 * <ul>
 *     <li><p><i>A Low Level API</i>, which deals with the
 *               following abstractions:</p>
 *     <ul>
 *       <li><p><i>Addresses</i>, which are networking identifiers,
 *              like IP addresses.</p></li>
 *       <li><p><i>Sockets</i>, which are basic bidirectional data communication
 *              mechanisms.</p></li>
 *       <li><p><i>Interfaces</i>, which describe network interfaces. </p></li>
 *     </ul></li>
 *     <li> <p><i>A High Level API</i>, which deals with the following
 *          abstractions:</p>
 *     <ul>
 *       <li><p><i>URIs</i>, which represent
 *               Universal Resource Identifiers.</p></li>
 *       <li><p><i>URLs</i>, which represent
 *               Universal Resource Locators.</p></li>
 *       <li><p><i>Connections</i>, which represents connections to the resource
 *               pointed to by <i>URLs</i>.</p></li>
 *       </ul></li>
 * </ul>
 * <h2>Addresses</h2>
 * <p>Addresses are used throughout the java.net APIs as either host
 *    identifiers, or socket endpoint identifiers.</p>
 * <p>The {@link java.net.InetAddress} class is the abstraction representing an
 *    IP (Internet Protocol) address.  It has two subclasses:
 * <ul>
 *       <li>{@link java.net.Inet4Address} for IPv4 addresses.</li>
 *       <li>{@link java.net.Inet6Address} for IPv6 addresses.</li>
 * </ul>
 * <p>But, in most cases, there is no need to deal directly with the subclasses,
 *    as the InetAddress abstraction should cover most of the needed
 *    functionality.</p>
 * <h3><b>About IPv6</b></h3>
 * <p>Not all systems have support for the IPv6 protocol, and while the Java
 *    networking stack will attempt to detect it and use it transparently when
 *    available, it is also possible to disable its use with a system property.
 *    In the case where IPv6 is not available, or explicitly disabled,
 *    Inet6Address are not valid arguments for most networking operations any
 *    more. While methods like {@link java.net.InetAddress#getByName} are
 *    guaranteed not to return an Inet6Address when looking up host names, it
 *    is possible, by passing literals, to create such an object. In which
 *    case, most methods, when called with an Inet6Address will throw an
 *    Exception.</p>
 * <h2>Sockets</h2>
 * <p>Sockets are means to establish a communication link between machines over
 *    the network. The java.net package provides 4 kinds of Sockets:</p>
 * <ul>
 *       <li>{@link java.net.Socket} is a TCP client API, and will typically
 *            be used to {@linkplain java.net.Socket#connect(SocketAddress)
 *            connect} to a remote host.</li>
 *       <li>{@link java.net.ServerSocket} is a TCP server API, and will
 *            typically {@linkplain java.net.ServerSocket#accept accept}
 *            connections from client sockets.</li>
 *       <li>{@link java.net.DatagramSocket} is a UDP endpoint API and is used
 *            to {@linkplain java.net.DatagramSocket#send send} and
 *            {@linkplain java.net.DatagramSocket#receive receive}
 *            {@linkplain java.net.DatagramPacket datagram packets}.</li>
 *       <li>{@link java.net.MulticastSocket} is a subclass of
 *            {@code DatagramSocket} used when dealing with multicast
 *            groups.</li>
 * </ul>
 * <p>Sending and receiving with TCP sockets is done through InputStreams and
 *    OutputStreams which can be obtained via the
 *    {@link java.net.Socket#getInputStream} and
 *    {@link java.net.Socket#getOutputStream} methods.</p>
 * <h2>Interfaces</h2>
 * <p>The {@link java.net.NetworkInterface} class provides APIs to browse and
 *    query all the networking interfaces (e.g. ethernet connection or PPP
 *    endpoint) of the local machine. It is through that class that you can
 *    check if any of the local interfaces is configured to support IPv6.</p>
 * <p>Note, all conforming implementations must support at least one
 *    {@code NetworkInterface} object, which must either be connected to a
 *    network, or be a "loopback" interface that can only communicate with
 *    entities on the same machine.</p>
 *
 * <h2>High level API</h2>
 * <p>A number of classes in the java.net package do provide for a much higher
 *    level of abstraction and allow for easy access to resources on the
 *    network. The classes are:
 * <ul>
 *       <li>{@link java.net.URI} is the class representing a
 *            Universal Resource Identifier, as specified in RFC 2396.
 *            As the name indicates, this is just an Identifier and doesn't
 *            provide directly the means to access the resource.</li>
 *       <li>{@link java.net.URL} is the class representing a
 *            Universal Resource Locator, which is both an older concept for
 *            URIs and a means to access the resources.</li>
 *       <li>{@link java.net.URLConnection} is created from a URL and is the
 *            communication link used to access the resource pointed by the
 *            URL. This abstract class will delegate most of the work to the
 *            underlying protocol handlers like http or https.</li>
 *       <li>{@link java.net.HttpURLConnection} is a subclass of URLConnection
 *            and provides some additional functionalities specific to the
 *            HTTP protocol.</li>
 * </ul>
 * <p>The recommended usage is to use {@link java.net.URI} to identify
 *    resources, then convert it into a {@link java.net.URL} when it is time to
 *    access the resource. From that URL, you can either get the
 *    {@link java.net.URLConnection} for fine control, or get directly the
 *    InputStream.
 * <p>Here is an example:</p>
 * <pre>
 * URI uri = new URI("http://java.sun.com/");
 * URL url = uri.toURL();
 * InputStream in = url.openStream();
 * </pre>
 * <h2>Protocol Handlers</h2>
 * As mentioned, URL and URLConnection rely on protocol handlers which must be
 * present, otherwise an Exception is thrown. This is the major difference with
 * URIs which only identify resources, and therefore don't need to have access
 * to the protocol handler. So, while it is possible to create an URI with any
 * kind of protocol scheme (e.g. {@code myproto://myhost.mydomain/resource/}),
 * a similar URL will try to instantiate the handler for the specified protocol;
 * if it doesn't exist an exception will be thrown.
 * <p>By default the protocol handlers are loaded dynamically from the default
 *    location. It is, however, possible to add to the search path by setting
 *    the {@code java.protocol.handler.pkgs} system property. For instance if
 *    it is set to {@code myapp.protocols}, then the URL code will try, in the
 *    case of http, first to load {@code myapp.protocols.http.Handler}, then,
 *    if this fails, {@code http.Handler} from the default location.
 * <p>Note that the Handler class <b>has to</b> be a subclass of the abstract
 *    class {@link java.net.URLStreamHandler}.</p>
 * <h2>Additional Specification</h2>
 * <ul>
 *       <li><a href="doc-files/net-properties.html">
 *            Networking System Properties</a></li>
 * </ul>
 *
 * <p>
 *  提供用于实现网络应用程序的类。
 * 
 *  <p> java.net包大致可分为两部分：</p>
 * <ul>
 *  <li> <p> <i>低级API </i>,其处理以下抽象概念：</p>
 * <ul>
 *  </p> </li> <li> <p> <i>套接字</i>,这些是基本的网路识别码双向数据通信机制。
 * </p> </li> <li> <p> <i>接口</i> </p> </li> </ul> </li> <li> <p> <i>高级API </i>。
 * <ul>
 *  </p> </li> <li> <p> <i> URL </i>,代表通用资源定位器。 </p> </li> </li> </p> </li> </li> / ul> </li>
 * </ul>
 *  <h2>地址</h2> <p>地址在整个java.net API中用作主机标识符或套接字端点标识符。
 * </p> <p> {@link java.net.InetAddress}类是抽象表示IP(因特网协议)地址。它有两个子类：。
 * <ul>
 *  </li> {@ link java.net.Inet4Address} for IPv6 addresses。</li> <li> {@ link java.net.Inet4Address}
 * </ul>
 * <p>但是,在大多数情况下,不需要直接处理子类,因为InetAddress抽象应该涵盖大部分所需的功能。
 * </p> <h3> <b>关于IPv6 </b> h3> <p>并非所有系统都支持IPv6协议,并且尽管Java网络协议栈会尝试检测它并在可用时透明地使用它,但也可以通过系统属性禁用它。
 * 在IPv6不可用或显式禁用的情况下,Inet6Address不再是大多数网络操作的有效参数。
 * 虽然像{@link java.net.InetAddress#getByName}这样的方法保证在查找主机名时不返回Inet6Address,但是可以通过传递文字来创建这样的对象。
 * 在这种情况下,当使用Inet6Address调用时,大多数方法将抛出异常。</p> <h2>套接字是通过网络在机器之间建立通信链路的手段。 java.net包提供了4种类型的套接字：</p>。
 * <ul>
 * <li> {@ link java.net.Socket}是一个TCP客户端API,通常用于将{@linkplain java.net.Socket#connect(SocketAddress)connect}
 * 连接到远程主机。
 * </li> <li > {@ link java.net.ServerSocket}是一个TCP服务器API,通常会从客户端套接字{@linkplain java.net.ServerSocket#accept accept}
 * 连接。
 * </li> <li> {@ link java.net。
 *  DatagramSocket}是一个UDP端点API,用于{@linkplain java.net.DatagramSocket#send send}和{@linkplain java.net.DatagramSocket#receive receive}
 *  {@linkplain java.net.DatagramPacket datagram packets}。
 * </li> <li> {@ link java.net。
 * < li> <li> {@ link java.net.MulticastSocket}是处理多播组时使用的{@code DatagramSocket}的子类。</li>。
 * </ul>
 *  <p>使用TCP套接字发送和接收通过InputStreams和OutputStreams完成,可以通过{@link java.net.Socket#getInputStream}和{@link java.net.Socket#getOutputStream}
 * 
 * @since JDK1.0
 */
package java.net;
