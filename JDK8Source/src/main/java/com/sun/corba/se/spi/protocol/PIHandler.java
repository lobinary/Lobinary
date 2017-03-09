/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.protocol;

import java.io.Closeable;

import org.omg.PortableInterceptor.ObjectReferenceTemplate ;
import org.omg.PortableInterceptor.Interceptor ;
import org.omg.PortableInterceptor.Current ;
import org.omg.PortableInterceptor.PolicyFactory ;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName ;

import org.omg.CORBA.NVList ;
import org.omg.CORBA.Any ;
import org.omg.CORBA.Policy ;
import org.omg.CORBA.PolicyError ;

import org.omg.CORBA.portable.RemarshalException;

import com.sun.corba.se.spi.oa.ObjectAdapter ;

import com.sun.corba.se.spi.protocol.CorbaMessageMediator ;

import com.sun.corba.se.spi.ior.ObjectKeyTemplate ;

// XXX These need to go away.
import com.sun.corba.se.impl.corba.RequestImpl ;
import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage ;

/** This interface defines the PI interface that is used to interface the rest of the
 * ORB to the PI implementation.
 * <p>
 *  ORB到PI实现。
 * 
 */
public interface PIHandler extends Closeable {
    /** Complete the initialization of the PIHandler.  This will execute the methods
    * on the ORBInitializers, if any are defined.  This must be done here so that
    * the ORB can obtain the PIHandler BEFORE the ORBInitializers run, since they
    * will need access to the PIHandler through the ORB.
    * <p>
    *  在ORBInitializers上,如果有任何定义。这必须在这里完成,以便ORB可以在ORBInitializers运行之前获得PIHandler,因为它们将需要通过ORB访问PIHandler。
    * 
    */
    public void initialize() ;

    public void destroyInterceptors() ;

    /*
     ****************************
     * IOR interceptor PI hooks
     * <p>
     *  ************************** IOR拦截器PI挂钩
     * 
     * 
     ****************************/

    /**
     * Called when a new object adapter is created.
     *
     * <p>
     *  创建新对象适配器时调用。
     * 
     * 
     * @param oa The adapter associated with the interceptors to be
     *   invoked.
     */
    void objectAdapterCreated( ObjectAdapter oa )  ;

    /**
     * Called whenever a state change occurs in an adapter manager.
     *
     * <p>
     *  在适配器管理器中发生状态更改时调用。
     * 
     * 
     * @param managerId managerId The adapter manager id
     * @param newState newState The new state of the adapter manager,
     * and by implication of all object adapters managed by this manager.
     */
    void adapterManagerStateChanged( int managerId,
        short newState ) ;

    /** Called whenever a state change occurs in an object adapter that
    * was not caused by an adapter manager state change.
    *
    * <p>
    *  不是由适配器管理器状态更改引起的。
    * 
    * 
    * @param templates The templates that are changing state.
    * @param newState The new state of the adapters identified by the
    * templates.
    */
    void adapterStateChanged( ObjectReferenceTemplate[] templates,
        short newState ) ;

    /*
     *****************
     * Client PI hooks
     * <p>
     *  ***************客户端PI挂钩
     * 
     * 
     *****************/

    /**
     * Called for pseudo-ops to temporarily disable portable interceptor
     * hooks for calls on this thread.  Keeps track of the number of
     * times this is called and increments the disabledCount.
     * <p>
     *  调用伪操作来临时禁用此线程上的调用的便携式拦截器挂钩。跟踪此次调用的次数,并增加disabledCount。
     * 
     */
    void disableInterceptorsThisThread() ;

    /**
     * Called for pseudo-ops to re-enable portable interceptor
     * hooks for calls on this thread.  Decrements the disabledCount.
     * If disabledCount is 0, interceptors are re-enabled.
     * <p>
     *  调用伪操作为此线程上的调用重新启用便携式拦截器挂钩。减少disabledCount。如果disabledCount为0,则重新启用拦截器。
     * 
     */
    void enableInterceptorsThisThread() ;

    /**
     * Called when the send_request or send_poll portable interception point
     * is to be invoked for all appropriate client-side request interceptors.
     *
     * <p>
     *  当要为所有适当的客户端请求拦截器调用send_request或send_poll可移植拦截点时调用。
     * 
     * 
     * @exception RemarhsalException - Thrown when this request needs to
     *     be retried.
     */
    void invokeClientPIStartingPoint()
        throws RemarshalException ;

    /**
     * Called when the appropriate client ending interception point is
     * to be invoked for all apporpriate client-side request interceptors.
     *
     * <p>
     *  当适当的客户端结束拦截点将被调用为所有apporpriate客户端请求拦截器时调用。
     * 
     * 
     * @param replyStatus One of the constants in iiop.messages.ReplyMessage
     *     indicating which reply status to set.
     * @param exception The exception before ending interception points have
     *     been invoked, or null if no exception at the moment.
     * @return The exception to be thrown, after having gone through
     *     all ending points, or null if there is no exception to be
     *     thrown.  Note that this exception can be either the same or
     *     different from the exception set using setClientPIException.
     *     There are four possible return types: null (no exception),
     *     SystemException, UserException, or RemarshalException.
     */
    Exception invokeClientPIEndingPoint(
        int replyStatus, Exception exception ) ;

    /**
     * Called when a retry is needed after initiateClientPIRequest but
     * before invokeClientPIRequest.  In this case, we need to properly
     * balance initiateClientPIRequest/cleanupClientPIRequest calls,
     * but WITHOUT extraneous calls to invokeClientPIEndingPoint
     * (see bug 6763340).
     *
     * <p>
     * 当在initiateClientPIRequest之后但在invokeClientPIRequest之前需要重试时调用。
     * 在这种情况下,我们需要适当地平衡initiateClientPIRequest / cleanupClientPIRequest调用,但不要调用invokeClientPIEndingPoint(参见错
     * 误6763340)。
     * 当在initiateClientPIRequest之后但在invokeClientPIRequest之前需要重试时调用。
     * 
     * 
     * @param replyStatus One of the constants in iiop.messages.ReplyMessage
     *     indicating which reply status to set.
     * @param exception The exception before ending interception points have
     *     been invoked, or null if no exception at the moment.
     * @return The exception to be thrown, after having gone through
     *     all ending points, or null if there is no exception to be
     *     thrown.  Note that this exception can be either the same or
     *     different from the exception set using setClientPIException.
     *     There are four possible return types: null (no exception),
     *     SystemException, UserException, or RemarshalException.
     */
    Exception makeCompletedClientRequest(
        int replyStatus, Exception exception ) ;

    /**
     * Invoked when a request is about to be created.  Must be called before
     * any of the setClientPI* methods so that a new info object can be
     * prepared for information collection.
     *
     * <p>
     *  在即将创建请求时调用。必须在任何setClientPI *方法之前调用,以便可以为信息收集准备新的信息对象。
     * 
     * 
     * @param diiRequest True if this is to be a DII request, or false if it
     *     is a "normal" request.  In the DII case, initiateClientPIRequest
     *     is called twice and we need to ignore the second one.
     */
    void initiateClientPIRequest( boolean diiRequest ) ;

    /**
     * Invoked when a request is about to be cleaned up.  Must be called
     * after ending points are called so that the info object on the stack
     * can be deinitialized and popped from the stack at the appropriate
     * time.
     * <p>
     *  在请求即将清除时调用。必须在调用结束点之后调用,以便堆栈上的info对象可以在适当的时间取消初始化并从堆栈弹出。
     * 
     */
    void cleanupClientPIRequest() ;

    /**
     * Notifies PI of additional information for client-side interceptors.
     * PI will use this information as a source of information for the
     * ClientRequestInfo object.
     * <p>
     *  通知PI有关客户端拦截器的附加信息。 PI将使用此信息作为ClientRequestInfo对象的信息源。
     * 
     */
    void setClientPIInfo( RequestImpl requestImpl ) ;

    /**
     * Notify PI of the MessageMediator for the request.
     * <p>
     *  通知MessageMediator的PI请求。
     * 
     */
    void setClientPIInfo(CorbaMessageMediator messageMediator) ;

    /*
     *****************
     * Server PI hooks
     * <p>
     *  ***************服务器PI挂钩
     * 
     * 
     *****************/

    /**
     * Called when the appropriate server starting interception point is
     * to be invoked for all appropriate server-side request interceptors.
     *
     * <p>
     *  当适当的服务器启动拦截点被调用为所有适当的服务器端请求拦截器时调用。
     * 
     * 
     * @throws ForwardException Thrown if an interceptor raises
     *     ForwardRequest.  This is an unchecked exception so that we need
     *     not modify the entire execution path to declare throwing
     *     ForwardException.
     */
    void invokeServerPIStartingPoint() ;

    /**
     * Called when the appropriate server intermediate interception point is
     * to be invoked for all appropriate server-side request interceptors.
     *
     * <p>
     *  在为所有适当的服务器端请求拦截器调用适当的服务器中间拦截点时调用。
     * 
     * 
     * @throws ForwardException Thrown if an interceptor raises
     *     ForwardRequest.  This is an unchecked exception so that we need
     *     not modify the entire execution path to declare throwing
     *     ForwardException.
     */
    void invokeServerPIIntermediatePoint() ;

    /**
     * Called when the appropriate server ending interception point is
     * to be invoked for all appropriate server-side request interceptors.
     *
     * <p>
     *  当适当的服务器结束拦截点被调用为所有适当的服务器端请求拦截器时调用。
     * 
     * 
     * @param replyMessage The iiop.messages.ReplyMessage containing the
     *     reply status.
     * @throws ForwardException Thrown if an interceptor raises
     *     ForwardRequest.  This is an unchecked exception so that we need
     *     not modify the entire execution path to declare throwing
     *     ForwardException.
     */
    void invokeServerPIEndingPoint( ReplyMessage replyMessage ) ;

    /**
     * Notifies PI to start a new server request and set initial
     * information for server-side interceptors.
     * PI will use this information as a source of information for the
     * ServerRequestInfo object.  poaimpl is declared as an Object so that
     * we need not introduce a dependency on the POA package.
     * <p>
     * 通知PI启动新的服务器请求,并为服务器端拦截器设置初始信息。 PI将使用此信息作为ServerRequestInfo对象的信息源。 poaimpl被声明为一个对象,所以我们不需要在POA包上引入依赖。
     * 
     */
    void initializeServerPIInfo( CorbaMessageMediator request,
        ObjectAdapter oa, byte[] objectId, ObjectKeyTemplate oktemp ) ;

    /**
     * Notifies PI of additional information reqired for ServerRequestInfo.
     *
     * <p>
     *  通知PI ServerRequestInfo所需的附加信息。
     * 
     * 
     * @param servant The servant.  This is java.lang.Object because in the
     *     POA case, this will be a org.omg.PortableServer.Servant whereas
     *     in the ServerRequestDispatcher case this will be an ObjectImpl.
     * @param targetMostDerivedInterface.  The most derived interface.  This
     *     is passed in instead of calculated when needed because it requires
     *     extra information in the POA case that we didn't want to bother
     *     creating extra methods for to pass in.
     */
    void setServerPIInfo( java.lang.Object servant,
                                    String targetMostDerivedInterface ) ;

    /**
     * Notifies PI of additional information required for ServerRequestInfo.
     * <p>
     *  通知PI ServerRequestInfo所需的附加信息。
     * 
     */
    void setServerPIInfo( Exception exception ) ;

    /**
     * Notifies PI of additional information for server-side interceptors.
     * PI will use this information as a source of information for the
     * ServerRequestInfo object.  These are the arguments for a DSI request.
     * <p>
     *  通知PI有关服务器端拦截器的附加信息。 PI将使用此信息作为ServerRequestInfo对象的信息源。这些是DSI请求的参数。
     * 
     */
    void setServerPIInfo( NVList arguments ) ;

    /**
     * Notifies PI of additional information for server-side interceptors.
     * PI will use this information as a source of information for the
     * ServerRequestInfo object.  This is the exception of a DSI request.
     * <p>
     *  通知PI有关服务器端拦截器的附加信息。 PI将使用此信息作为ServerRequestInfo对象的信息源。这是DSI请求的例外。
     * 
     */
    void setServerPIExceptionInfo( Any exception ) ;

    /**
     * Notifies PI of additional information for server-side interceptors.
     * PI will use this information as a source of information for the
     * ServerRequestInfo object.  This is the result of a DSI request.
     * <p>
     *  通知PI有关服务器端拦截器的附加信息。 PI将使用此信息作为ServerRequestInfo对象的信息源。这是DSI请求的结果。
     * 
     */
    void setServerPIInfo( Any result ) ;

    /**
     * Invoked when a request is about to be cleaned up.  Must be called
     * after ending points are called so that the info object on the stack
     * can be deinitialized and popped from the stack at the appropriate
     * time.
     * <p>
     *  在请求即将清除时调用。必须在调用结束点之后调用,以便堆栈上的info对象可以在适当的时间取消初始化并从堆栈弹出。
     */
    void cleanupServerPIRequest() ;

    Policy create_policy( int type, Any val ) throws PolicyError ;

    void register_interceptor( Interceptor interceptor, int type )
        throws DuplicateName ;

    Current getPICurrent() ;

    void registerPolicyFactory( int type, PolicyFactory factory ) ;

    int allocateServerRequestId() ;
}
