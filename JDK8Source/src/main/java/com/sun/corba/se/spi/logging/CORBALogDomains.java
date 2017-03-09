/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.logging ;

/** Defines constants for all of the logging domains used in the ORB.
 * Note that this is the suffix to the log domain.  The full domain is given by
 * <code>javax.enterprise.resource.corba.{ORBId}.{Log domain}</code>
 * where {ORBId} is the ORB ID of the ORB instance doing the logging.
 * <P>
 * The ORB implementation packages are mapped into these domains as follows:
 * <ul>
 * <li>activation: orbd.*
 * <li>corba: rpc.presentation (CORBA API, typecode/any), oa.invocation (DII)
 * <li>core: service context code in rpc.protocol (will eventually move to its own package)
 * <li>dynamicany: rpc.presentation
 * <li>encoding: rpc.encoding
 * <li>iiop: rmiiop.delegate (ShutdownUtilDelegate needs to move somewhere)
 * <li>interceptors: rpc.protocol
 * <li>io: rpc.encoding
 * <li>ior: oa.ior
 * <li>javax: rmiiiop.delegate
 * <li>logging: logging does not have a domain
 * <li>naming: naming
 * <li>oa: oa
 * <li>orb: orb.lifecycle
 * <li>orbutil: util
 * <li>protocol: rpc.protocol
 * <li>resolver: orb.resolver
 * <li>transport: rpc.transport
 * <li>txpoa: this will be removed in the future.
 * <li>util: util
 * </ul>
 * <p>
 *  注意,这是日志域的后缀。完整的域由<code> javax.enterprise.resource.corba。{ORBId}。
 * {Log domain} </code>给出,其中{ORBId}是执行日志记录的ORB实例的ORB ID。
 * <P>
 *  ORB实现包映射到这些域如下：
 * <ul>
 *  <li> activation：orbd。
 * * <li> corba：rpc.presentation(CORBA API,typecode / any),oa.invocation(DII)<li> core：rpc.protocol中的服务上
 * 下文代码package)<li> dynamicany：rpc.presentation <li> encoding：rpc.encoding <li> iiop：rmiiop.delegate(Shu
 * tdownUtilDelegate需要移动到某处)<li>拦截器：rpc.protocol <li> io：rpc.encoding < li> ior：oa.ior <li> javax：rmiiio
 * p.delegate <li> logging：logging没有域<li> naming：naming <li> oa：oa <li> orb：orb.lifecycle <li> ：util <li>
 *  protocol：rpc.protocol <li>解析器：orb.resolver <li> transport：rpc.transport <li> txpoa：这将在以后删除。
 *  <li> activation：orbd。 <li> util：util。
 * </ul>
 */
public abstract class CORBALogDomains {
    private CORBALogDomains() {}

    // Top level log domain for CORBA
    public static final String TOP_LEVEL_DOMAIN  = "javax.enterprise.resource.corba";

    public static final String RPC              = "rpc" ;

    /** Log domain for code directly implementing the CORBA API and
     * the typecode/any machinery.
     * <p>
     *  类型代码/任何机器。
     * 
     */
    public static final String RPC_PRESENTATION = "rpc.presentation" ;

    /** Log domain for any sort of wire encoding used in marshalling
    /* <p>
     */
    public static final String RPC_ENCODING = "rpc.encoding" ;

    /** Log domain for the code used to handle any kind of invocation
     * protocol.  This includes client and server delegates, client and
     * server request dispatchers, service contexts, portable interceptors,
     * and the GIOP protocol (but not CDR representation of data).
     * <p>
     *  协议。这包括客户端和服务器代理,客户端和服务器请求调度程序,服务上下文,便携式拦截器和GIOP协议(但不包括数据的CDR表示)。
     * 
     */
    public static final String RPC_PROTOCOL = "rpc.protocol" ;

    /** Log domain for low-level transport details, which are
     * independent of encoding and presentation details.  This
     * includes selectors, acceptors, connections, connection management,
     * and any other transport management functions.
     * <p>
     *  独立于编码和演示的细节。这包括选择器,接受器,连接,连接管理和任何其他传输管理功能。
     * 
     */
    public static final String RPC_TRANSPORT = "rpc.transport" ;

    public static final String NAMING          = "naming" ;

    /** Log domain for naming context creation and destruction.
    /* <p>
     */
    public static final String NAMING_LIFECYCLE = "naming.lifecycle" ;

    /** Log domain for name service lookup.
    /* <p>
     */
    public static final String NAMING_READ = "naming.read" ;

    /** Log domain for name service bind, rebind, destroy, and other state
     * change operations.
     * <p>
     * 更改操作。
     * 
     */
    public static final String NAMING_UPDATE = "naming.update" ;

    public static final String ORBD         = "orbd" ;

    /** Log domain for the ORBD locator function, which forwards
     * client requests to their current server incarnation.
     * <p>
     *  客户端请求到其当前服务器角色。
     * 
     */
    public static final String ORBD_LOCATOR = "orbd.locator" ;

    /** Log domain for the ORBD activator function, which starts
     * server instances on demand.
     * <p>
     *  服务器实例。
     * 
     */
    public static final String ORBD_ACTIVATOR = "orbd.activator" ;

    /** Log domain for the Implementation Repository.
    /* <p>
     */
    public static final String ORBD_REPOSITORY = "orbd.repository" ;

    /** Log domain for the servertool utilitiy used to update the
     * implementation repository.
     * <p>
     *  实现存储库。
     * 
     */
    public static final String ORBD_SERVERTOOL = "orbd.servertool" ;

    public static final String ORB          = "orb" ;

    /** Log domain for ORB initialization, configuration, startup,
     * and shutdown.
     * <p>
     *  和关机。
     * 
     */
    public static final String ORB_LIFECYCLE = "orb.lifecycle" ;

    /** Log domain for ORB client side name resolution and supporting
     * functions such as INS.
     * <p>
     *  功能如INS。
     * 
     */
    public static final String ORB_RESOLVER = "orb.resolver" ;

    public static final String OA           = "oa" ;

    /** Log domain for creation, destruction, and state change of
     * Object Adapters and related classes (e.g. POAManager).
     * <p>
     *  对象适配器和相关类(例如,POAManager)。
     * 
     */
    public static final String OA_LIFECYCLE = "oa.lifecycle" ;

    /** Log domain for all IOR related code.
    /* <p>
     */
    public static final String OA_IOR = "oa.ior" ;

    /** Log domain for object adapter request dispatch.
    /* <p>
     */
    public static final String OA_INVOCATION = "oa.invocation" ;

    public static final String RMIIIOP          = "rmiiiop" ;

    /** Log domain for the RMI-IIOP implementation in the Stub, Util, and
     * PortableRemoteObject delegates.
     * <p>
     *  PortableRemoteObject委托。
     * 
     */
    public static final String RMIIIOP_DELEGATE = "rmiiiop.delegate" ;

    /** Log domain for utility classes.
    /* <p>
     */
    public static final String UTIL = "util" ;
}
