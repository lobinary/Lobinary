/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.legacy.connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.transport.SocketInfo;

/**
 *
 * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p>
 * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p>
 *
 * This interface gives one the ability to plug in their own socket
 * factory class to an ORB. <p>
 *
 * Usage: <p>
 *
 * One specifies a class which implements this interface via the
 *
 *     <code>ORBConstants.SOCKET_FACTORY_CLASS_PROPERTY</code>
 *
 * property. <p>
 *
 * Example: <p>

 * <pre>
 *   -Dcom.sun.CORBA.connection.ORBSocketFactoryClass=MySocketFactory
 * </pre> <p>
 *
 * Typically one would use the same socket factory class on both the
 * server side and the client side (but this is not required). <p>
 *
 * A <code>ORBSocketFactory</code> class should have a public default
 * constructor which is called once per instantiating ORB.init call.
 * That ORB then calls the methods of that <code>ORBSocketFactory</code>
 * to obtain client and server sockets. <p>
 *
 * This interface also supports multiple server end points.  See the
 * documentation on <code>createServerSocket</code> below.
 *
 * <p>
 *  DEPRECATED。 DEPRECATED。 DEPRECATED。 DEPRECATED。 <p> DEPRECATED。 DEPRECATED。 DEPRECATED。 DEPRECATED。
 *  <p>。
 * 
 *  这个接口提供了将自己的套接字工厂类插入ORB的能力。 <p>
 * 
 *  用法：<p>
 * 
 *  一个指定一个类通过实现这个接口
 * 
 *  <code> ORBConstants.SOCKET_FACTORY_CLASS_PROPERTY </code>
 * 
 *  属性。 <p>
 * 
 *  示例：<p>
 * 
 * <pre>
 *  -Dcom.sun.CORBA.connection.ORBSocketFactoryClass = MySocketFactory </pre> <p>
 * 
 *  通常,在服务器端和客户端都使用相同的套接字工厂类(但这不是必需的)。 <p>
 * 
 *  一个<code> ORBSocketFactory </code>类应该有一个公共的默认构造函数,每次实例化ORB.init调用时调用一次。
 * 然后ORB调用<code> ORBSocketFactory </code>的方法来获取客户端和服务器套接字。 <p>。
 * 
 *  此接口还支持多个服务器端点。请参阅下面的<code> createServerSocket </code>文档。
 * 
 */

public interface ORBSocketFactory
{
    /**
     * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p>
     *
     * A server ORB always creates an "IIOP_CLEAR_TEXT" listening port.
     * That port is put into IOP profiles of object references exported
     * by an ORB. <p>
     *
     * If
     *
     *     <code>createServerSocket(String type, int port)</code>
     *
     * is passed <code>IIOP_CLEAR_TEXT</code> as a <code>type</code>
     * argument it should then call and return
     *
     *     <code>new java.net.ServerSocket(int port)</code> <p>
     *
     * If
     *
     *     <code>createSocket(SocketInfo socketInfo)</code>
     *
     * is passed <code>IIOP_CLEAR_TEXT</code> in
     * <code>socketInfo.getType()</code> it should
     * then call and return
     *
     * <pre>
     *     new java.net.Socket(socketInfo.getHost(),
     *                         socketInfo.getPort())
     * </pre>
     *
     * <p>
     *  DEPRECATED。 DEPRECATED。 DEPRECATED。 DEPRECATED。 <p>
     * 
     *  服务器ORB总是创建一个"IIOP_CLEAR_TEXT"监听端口。该端口被放入由ORB导出的对象引用的IOP配置文件中。 <p>
     * 
     *  如果
     * 
     *  <code> createServerSocket(String type,int port)</code>
     * 
     *  将<code> IIOP_CLEAR_TEXT </code>作为<code>类型</code>参数传递,然后它应该调用并返回
     * 
     *  <code> new java.net.ServerSocket(int port)</code> <p>
     * 
     * 如果
     * 
     *  <code> createSocket(SocketInfo socketInfo)</code>
     * 
     *  通过<code> socketInfo.getType()</code>中的<code> IIOP_CLEAR_TEXT </code>,它应该调用并返回
     * 
     * <pre>
     *  new java.net.Socket(socketInfo.getHost(),socketInfo.getPort())
     * </pre>
     * 
     */
    public static final String IIOP_CLEAR_TEXT = "IIOP_CLEAR_TEXT";


    /**
     * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p>
     *
     * This method is used by a server side ORB. <p>
     *
     * When an ORB needs to create a listen socket on which connection
     * requests are accepted it calls
     *
     *     <code>createServerSocket(String type, int port)</code>.
     *
     * The type argument says which type of socket should be created. <p>
     *
     * The interpretation of the type argument is the responsibility of
     * an instance of <code>ORBSocketFactory</code>, except in the case
     * of <code>IIOP_CLEAR_TEXT</code>, in which case a standard server
     * socket should be created. <p>
     *
     *
     * Multiple Server Port API: <p>
     *
     * In addition to the IIOP_CLEAR_TEXT listening port, it is possible
     * to specify that an ORB listen on additional port of specific types. <p>
     *
     * This API allows one to specify that an ORB should create an X,
     * or an X and a Y listen socket. <p>
     *
     * If X, to the user, means SSL, then one just plugs in an SSL
     * socket factory. <p>
     *
     * Or, another example, if X and Y, to the user, means SSL without
     * authentication and SSL with authentication respectively, then they
     * plug in a factory which will either create an X or a Y socket
     * depending on the type given to
     *
     *     <code>createServerSocket(String type, int port)</code>. <p>
     *
     * One specifies multiple listening ports (in addition to the
     * default IIOP_CLEAR_TEXT port) using the
     *
     *     <code>ORBConstants.LISTEN_SOCKET_PROPERTY</code>
     *
     * property. <p>
     *
     * Example usage:<p>
     *
     * <pre>
     *    ... \
     *    -Dcom.sun.CORBA.connection.ORBSocketFactoryClass=com.my.MySockFact \
     *    -Dcom.sun.CORBA.connection.ORBListenSocket=SSL:0,foo:1 \
     *    ...
     * </pre>
     *
     * The meaning of the "type" (SSL and foo above) is controlled
     * by the user. <p>
     *
     * ORBListenSocket is only meaningful for servers. <p>
     *
     * The property value is interpreted as follows.  For each
     * type/number pair: <p>
     *
     * If number is 0 then use an emphemeral port for the listener of
     * the associated type. <p>
     *
     * If number is greater then 0 use that port number. <p>
     *
     * An ORB creates a listener socket for each type
     * specified by the user by calling
     *
     *    <code>createServerSocket(String type, int port)</code>
     *
     * with the type specified by the user. <p>
     *
     * After an ORB is initialized and the RootPOA has been resolved,
     * it is then listening on
     * all the end points which were specified.  It may be necessary
     * to add this additional end point information to object references
     * exported by this ORB.  <p>
     *
     * Each object reference will contain the ORB's default IIOP_CLEAR_TEXT
     * end point in its IOP profile.  To add additional end point information
     * (i.e., an SSL port) to an IOR (i.e., an object reference) one needs
     * to intercept IOR creation using
     * an <code>PortableInterceptor::IORInterceptor</code>. <p>
     *
     * Using PortableInterceptors (with a non-standard extension): <p>
     *
     * Register an <code>IORInterceptor</code>.  Inside its
     * <code>establish_components</code> operation:
     *
     * <pre>
     *
     * com.sun.corba.se.spi.legacy.interceptor.IORInfoExt ext;
     * ext = (com.sun.corba.se.spi.legacy.interceptor.IORInfoExt)info;
     *
     * int port = ext.getServerPort("myType");
     *
     * </pre>
     *
     * Once you have the port you may add information to references
     * created by the associated adapter by calling
     *
     *    <code>IORInfo::add_ior_component</code><p> <p>
     *
     *
     * Note: if one is using a POA and the lifespan policy of that
     * POA is persistent then the port number returned
     * by <code>getServerPort</code> <em>may</em>
     * be the corresponding ORBD port, depending on whether the POA/ORBD
     * protocol is the present port exchange or if, in the future,
     * the protocol is based on object reference template exchange.
     * In either
     * case, the port returned will be correct for the protocol.
     * (In more detail, if the port exchange protocol is used then
     * getServerPort will return the ORBD's port since the port
     * exchange happens before, at ORB initialization.
     * If object reference
     * exchange is used then the server's transient port will be returned
     * since the templates are exchanged after adding components.) <p>
     *
     *
     * Persistent object reference support: <p>
     *
     * When creating persistent object references with alternate
     * type/port info, ones needs to configure the ORBD to also support
     * this alternate info.  This is done as follows: <p>
     *
     * - Give the ORBD the same socket factory you gave to the client
     * and server. <p>
     *
     * - specify ORBListenSocket ports of the same types that your
     * servers support.  You should probably specify explicit port
     * numbers for ORBD if you embed these numbers inside IORs. <p>
     *
     * Note: when using the port exchange protocol
     * the ORBD and servers will exchange port
     * numbers for each given type so they know about each other.
     * When using object reference template exchange the server's
     * transient ports are contained in the template. <p>
     *
     *
     * - specify your <code>BadServerIdHandler</code> (discussed below)
     * using the
     *
     *    <code>ORBConstants.BAD_SERVER_ID_HANDLER_CLASS_PROPERTY</code> <p>
     *
     * Example: <p>
     *
     * <pre>
     *
     * -Dcom.sun.CORBA.POA.ORBBadServerIdHandlerClass=corba.socketPersistent.MyBadServerIdHandler
     *
     * </pre>
     *
     * The <code>BadServerIdHandler</code> ...<p>
     *
     * See <code>com.sun.corba.se.impl.activation.ServerManagerImpl.handle</code>
     * for example code on writing a bad server id handler.  NOTE:  This
     * is an unsupported internal API.  It will not exist in future releases.
     * <p>
     *
     *
     * Secure connections to other services: <p>
     *
     * If one wants secure connections to other services such as
     * Naming then one should configure them with the same
     *
     *     <code>SOCKET_FACTORY_CLASS_PROPERTY</code> and
     *     <code>LISTEN_SOCKET_PROPERTY</code>
     *
     * as used by other clients and servers in your distributed system. <p>
     *
     * <p>
     *  DEPRECATED。 DEPRECATED。 DEPRECATED。 DEPRECATED。 <p>
     * 
     *  此方法由服务器端ORB使用。 <p>
     * 
     *  当ORB需要创建一个侦听套接字时,它会接受连接请求
     * 
     *  <code> createServerSocket(String type,int port)</code>。
     * 
     *  type参数说明应该创建哪种类型的套接字。 <p>
     * 
     *  类型参数的解释是<code> ORBSocketFactory </code>实例的责任,除了<code> IIOP_CLEAR_TEXT </code>的情况,在这种情况下应该创建一个标准的服务器套
     * 接字。
     *  <p>。
     * 
     *  多服务器端口API：<p>
     * 
     *  除了IIOP_CLEAR_TEXT侦听端口之外,还可以指定ORB侦听特定类型的附加端口。 <p>
     * 
     *  此API允许指定ORB应创建X,或X和Y侦听套接字。 <p>
     * 
     *  如果X,给用户,意味着SSL,那么只需插入SSL套接字工厂。 <p>
     * 
     *  或者,另一个例子,如果X和Y,对用户,意味着SSL没有验证和SSL分别与验证,然后他们插入一个工厂,将创建一个X或Y插座,取决于类型给
     * 
     *  <code> createServerSocket(String type,int port)</code>。 <p>
     * 
     * 一个指定多个监听端口(除了默认的IIOP_CLEAR_TEXT端口)使用
     * 
     *  <code> ORBConstants.LISTEN_SOCKET_PROPERTY </code>
     * 
     *  属性。 <p>
     * 
     *  用法示例：<p>
     * 
     * <pre>
     *  ... \ -Dcom.sun.CORBA.connection.ORBSocketFactoryClass = com.my.MySockFact \ -Dcom.sun.CORBA.connect
     * ion.ORBListenSocket = SSL：0,foo：1 \ ...。
     * </pre>
     * 
     *  "类型"(上面的SSL和foo)的含义由用户控制。 <p>
     * 
     *  ORBListenSocket只对服务器有意义。 <p>
     * 
     *  属性值解释如下。对于每个类型/数字对：<p>
     * 
     *  如果number为0,那么对相关类型的侦听器使用一个常用端口。 <p>
     * 
     *  如果number大于0,使用该端口号。 <p>
     * 
     *  ORB为用户通过调用指定的每个类型创建一个监听器套接字
     * 
     *  <code> createServerSocket(String type,int port)</code>
     * 
     *  与用户指定的类型。 <p>
     * 
     *  在ORB初始化并且RootPOA已经解析之后,它然后监听指定的所有端点。可能需要将此附加终结点信息添加到此ORB导出的对象引用。 <p>
     * 
     *  每个对象引用将在其IOP配置文件中包含ORB的缺省IIOP_CLEAR_TEXT结束点。
     * 为了向IOR(即对象引用)添加附加端点信息(即,SSL端口),需要使用<code> PortableInterceptor :: IORInterceptor </code>来拦截IOR创建。
     *  <p>。
     * 
     *  使用PortableInterceptors(使用非标准扩展名)：<p>
     * 
     * 注册<code> IORInterceptor </code>。在其<code> establish_components </code>操作中：
     * 
     * <pre>
     * 
     *  com.sun.corba.se.spi.legacy.interceptor.IORInfoExt ext; ext =(com.sun.corba.se.spi.legacy.intercepto
     * r.IORInfoExt)info;。
     * 
     *  int port = ext.getServerPort("myType");
     * 
     * </pre>
     * 
     *  一旦您有端口,您可以通过调用将信息添加到关联适配器创建的引用
     * 
     *  <code> IORInfo :: add_ior_component </code> <p> <p>
     * 
     *  注意：如果使用POA并且该POA的生命周期策略是持久的,那么<code> getServerPort </code> <em>返回的端口号可以</em>是相应的ORBD端口,取决于POA / ORBD
     * 协议是当前的端口交换,或者如果将来,协议是基于对象引用模板交换的。
     * 在任一情况下,返回的端口将对协议正确。 (更详细地说,如果使用端口交换协议,则在ORB初始化时,由于端口交换发生,getServerPort将返回ORBD的端口。
     * 如果使用对象引用交换,则将返回服务器的瞬时端口,因为模板被交换添加组件。)<p>。
     * 
     *  持久对象引用支持：<p>
     */
    public ServerSocket createServerSocket(String type, int port)
        throws
            IOException;



    /**
     * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p>
     *
     * This method is used by a client side ORB. <p>
     *
     * Each time a client invokes on an object reference, the reference's
     * associated ORB will call
     *
     * <pre>
     *    getEndPointInfo(ORB orb,
     *                    IOR ior,
     *                    SocketInfo socketInfo)
     * </pre>
     *
     * NOTE: The type of the <code>ior</code> argument is an internal
     * representation for efficiency.  If the <code>ORBSocketFactory</code>
     * interface ever becomes standardized then the <code>ior</code> will
     * most likely change to a standard type (e.g., a stringified ior,
     * an <code>org.omg.IOP.IOR</code>, or ...). <p>
     *
     * Typically, this method will look at tagged components in the
     * given <code>ior</code> to determine what type of socket to create. <p>
     *
     * Typically, the <code>ior</code> will contain a tagged component
     * specifying an alternate port type and number.  <p>
     *
     * This method should return an <code>SocketInfo</code> object
     * containing the type/host/port to be used for the connection.
     *
     * If there are no appropriate tagged components then this method
     * should return an <code>SocketInfo</code> object with the type
     * <code>IIOP_CLEAR_TEXT</code> and host/port from the ior's IOP
     * profile. <p>
     *
     * If the ORB already has an existing connection to the returned
     * type/host/port, then that connection is used.  Otherwise the ORB calls
     *
     *    <code>createSocket(SocketInfo socketInfo)</code> <p>
     *
     * The <code>orb</code> argument is useful for handling
     * the <code>ior</code> argument. <p>
     *
     * The <code>SocketInfo</code> given to <code>getEndPointInfo</code>
     * is either null or an object obtained
     * from <code>GetEndPointInfoAgainException</code> <p>
     *
     * <p>
     * 
     *  当创建具有替代类型/端口信息的持久对象引用时,需要配置ORBD以支持此备用信息。这是这样做的：<p>
     * 
     *   - 给ORBD与您给客户端和服务器相同的套接字工厂。 <p>
     * 
     *  - 指定与您的服务器支持的相同类型的ORBListenSocket端口。如果将这些数字嵌入到IOR中,您应该为ORBD指定显式端口号。 <p>
     * 
     *  注意：当使用端口交换协议时,ORBD和服务器将交换每个给定类型的端口号,以便他们彼此了解。当使用对象引用模板交换时,服务器的瞬态端口包含在模板中。 <p>
     * 
     *   - 使用。指定<code> BadServerIdHandler </code>(下面讨论)
     * 
     *  <code> ORBConstants.BAD_SERVER_ID_HANDLER_CLASS_PROPERTY </code> <p>
     * 
     *  示例：<p>
     * 
     * <pre>
     * 
     *  -Dcom.sun.CORBA.POA.ORBBadServerIdHandlerClass = corba.socketPersistent.MyBadServerIdHandler
     * 
     * </pre>
     * 
     *  <code> BadServerIdHandler </code> ... <p>
     * 
     *  请参阅<code> com.sun.corba.se.impl.activation.ServerManagerImpl.handle </code>例如关于编写错误的服务器id处理程序的代码。
     * 注意：这是不受支持的内部API。它在将来的版本中不会存在。
     * <p>
     * 
     *  与其他服务的安全连接：<p>
     * 
     *  如果想要安全连接到其他服务,如命名,那么应该使用相同的配置
     * 
     *  <code> SOCKET_FACTORY_CLASS_PROPERTY </code>和<code> LISTEN_SOCKET_PROPERTY </code>
     * 
     *  由分布式系统中的其他客户端和服务器使用。 <p>
     * 
     */
    public SocketInfo getEndPointInfo(org.omg.CORBA.ORB orb,
                                        IOR ior,
                                        SocketInfo socketInfo);


    /**
     * DEPRECATED.  DEPRECATED. DEPRECATED. DEPRECATED. <p
     *
     * This method is used by a client side ORB. <p>
     *
     * This method should return a client socket of the given
     * type/host/port. <p>
     *
     * Note: the <code>SocketInfo</code> is the same instance as was
     * returned by <code>getSocketInfo</code> so extra cookie info may
     * be attached. <p>
     *
     * If this method throws GetEndPointInfoAgainException then the
     * ORB calls <code>getEndPointInfo</code> again, passing it the
     * <code>SocketInfo</code> object contained in the exception. <p>
     *
     * <p>
     *  DEPRECATED。 DEPRECATED。 DEPRECATED。 DEPRECATED。 <p>
     * 
     *  此方法由客户端ORB使用。 <p>
     * 
     *  每次客户端调用对象引用时,引用的关联ORB将调用
     * 
     * <pre>
     * getEndPointInfo(ORB orb,IOR ior,SocketInfo socketInfo)
     * </pre>
     * 
     *  注意：<code> ior </code>参数的类型是效率的内部表示。
     * 如果<code> ORBSocketFactory </code>接口变得标准化,那么<code> ior </code>将很可能改变为标准类型(例如,一个字符串化的ior,一个<code> org.o
     * mg.IOP.IOR </code>或...)。
     *  注意：<code> ior </code>参数的类型是效率的内部表示。 <p>。
     * 
     *  通常,此方法将查看给定的<code> ior </code>中的已标记组件,以确定要创建哪种类型的套接字。 <p>
     * 
     *  通常,<code> ior </code>将包含一个标记的组件,指定备用端口类型和编号。 <p>
     * 
     *  此方法应返回一个<code> SocketInfo </code>对象,其中包含用于连接的类型/ host / port。
     * 
     *  如果没有适当的标记组件,那么这个方法应该从ior的IOP配置文件中返回一个类型为<code> IIOP_CLEAR_TEXT </code>和host / port的<code> SocketInfo
     *  </code>对象。
     *  <p>。
     */
    public Socket createSocket(SocketInfo socketInfo)
        throws
            IOException,
            GetEndPointInfoAgainException;
}

// End of file.
