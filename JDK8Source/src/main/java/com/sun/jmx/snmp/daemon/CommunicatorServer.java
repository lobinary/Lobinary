/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp.daemon;



// java import
//
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.Vector;
import java.util.NoSuchElementException;

// jmx import
//
import javax.management.MBeanServer;
import javax.management.MBeanRegistration;
import javax.management.ObjectName;
import javax.management.NotificationListener;
import javax.management.NotificationFilter;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationBroadcasterSupport;
import javax.management.MBeanNotificationInfo;
import javax.management.AttributeChangeNotification;
import javax.management.ListenerNotFoundException;

import static com.sun.jmx.defaults.JmxProperties.SNMP_ADAPTOR_LOGGER;

// JSR 160 import
//
// XXX Revisit:
//   used to import com.sun.jmx.snmp.MBeanServerForwarder
// Now using JSR 160 instead. => this is an additional
// dependency to JSR 160.
//
import javax.management.remote.MBeanServerForwarder;

/**
 * Defines generic behavior for the server part of a connector or an adaptor.
 * Most connectors or adaptors extend <CODE>CommunicatorServer</CODE>
 * and inherit this behavior. Connectors or adaptors that do not fit into
 * this model do not extend <CODE>CommunicatorServer</CODE>.
 * <p>
 * A <CODE>CommunicatorServer</CODE> is an active object, it listens for
 * client requests  and processes them in its own thread. When necessary, a
 * <CODE>CommunicatorServer</CODE> creates other threads to process multiple
 * requests concurrently.
 * <p>
 * A <CODE>CommunicatorServer</CODE> object can be stopped by calling the
 * <CODE>stop</CODE> method. When it is stopped, the
 * <CODE>CommunicatorServer</CODE> no longer listens to client requests and
 * no longer holds any thread or communication resources.
 * It can be started again by calling the <CODE>start</CODE> method.
 * <p>
 * A <CODE>CommunicatorServer</CODE> has a <CODE>State</CODE> attribute
 * which reflects its  activity.
 * <p>
 * <TABLE>
 * <TR><TH>CommunicatorServer</TH>      <TH>State</TH></TR>
 * <TR><TD><CODE>stopped</CODE></TD>    <TD><CODE>OFFLINE</CODE></TD></TR>
 * <TR><TD><CODE>starting</CODE></TD>    <TD><CODE>STARTING</CODE></TD></TR>
 * <TR><TD><CODE>running</CODE></TD>     <TD><CODE>ONLINE</CODE></TD></TR>
 * <TR><TD><CODE>stopping</CODE></TD>     <TD><CODE>STOPPING</CODE></TD></TR>
 * </TABLE>
 * <p>
 * The <CODE>STARTING</CODE> state marks the transition
 * from <CODE>OFFLINE</CODE> to <CODE>ONLINE</CODE>.
 * <p>
 * The <CODE>STOPPING</CODE> state marks the transition from
 * <CODE>ONLINE</CODE> to <CODE>OFFLINE</CODE>. This occurs when the
 * <CODE>CommunicatorServer</CODE> is finishing or interrupting active
 * requests.
 * <p>
 * When a <CODE>CommunicatorServer</CODE> is unregistered from the MBeanServer,
 * it is stopped automatically.
 * <p>
 * When the value of the <CODE>State</CODE> attribute changes the
 * <CODE>CommunicatorServer</CODE> sends a
 * <tt>{@link javax.management.AttributeChangeNotification}</tt> to the
 * registered listeners, if any.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  定义连接器或适配器的服务器部分的通用行为。大多数连接器或适配器扩展<CODE> CommunicatorServer </CODE>并继承此行为。
 * 不适合此型号的连接器或适配器不扩展<CODE> CommunicatorServer </CODE>。
 * <p>
 *  <CODE> CommunicatorServer </CODE>是一个活动对象,它侦听客户端请求并在自己的线程中处理它们。
 * 必要时,<CODE> CommunicatorServer </CODE>创建其他线程以并发处理多个请求。
 * <p>
 *  可以通过调用<CODE> stop </CODE>方法停止<CODE> CommunicatorServer </CODE>对象。
 * 当它停止时,<CODE> CommunicatorServer </CODE>不再侦听客户端请求,不再持有任何线程或通信资源。可以通过调用<CODE> start </CODE>方法重新启动。
 * <p>
 *  <CODE> CommunicatorServer </CODE>具有反映其活动的<CODE>状态</CODE>属性。
 * <p>
 * <TABLE>
 *  <TR> <TH> CommunicatorServer </TH> <TH>状态</TH> </TR> <TR> <TD> <CODE>已停止</CODE> </TD> <TD> <CODE> CO
 * DE>开始</CODE> </TD> </TR> <TR> </TD> </TD> <TD> <CODE>运行</CODE> </TD> <TD> <CODE>在线</CODE> </TD> </TR>
 *  <TR> <TD> <CODE> TD> <TD> <CODE> STOPPING </CODE> </TD> </TR>。
 * </TABLE>
 * <p>
 * <CODE> STARTING </CODE>状态标记从<CODE> OFFLINE </CODE>到<CODE> ONLINE </CODE>的转换。
 * <p>
 *  <CODE> STOPPING </CODE>状态标记从<CODE>在线</CODE>到<CODE>离线</CODE>的转换。
 * 当<CODE> CommunicatorServer </CODE>正在完成或中断活动请求时,会发生这种情况。
 * <p>
 *  当<CODE> CommunicatorServer </CODE>从MBeanServer注销时,它会自动停止。
 * <p>
 *  当<CODE> State </CODE>属性的值更改时,<CODE> CommunicatorServer </CODE>向注册的侦听器发送<tt> {@ link javax.management.AttributeChangeNotification}
 *  </tt>。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public abstract class CommunicatorServer
    implements Runnable, MBeanRegistration, NotificationBroadcaster,
               CommunicatorServerMBean {

    //
    // States of a CommunicatorServer
    //

    /**
     * Represents an <CODE>ONLINE</CODE> state.
     * <p>
     *  表示<CODE>在线</CODE>状态。
     * 
     */
    public static final int ONLINE = 0 ;

    /**
     * Represents an <CODE>OFFLINE</CODE> state.
     * <p>
     *  表示<CODE>离线</CODE>状态。
     * 
     */
    public static final int OFFLINE = 1 ;

    /**
     * Represents a <CODE>STOPPING</CODE> state.
     * <p>
     *  表示<CODE> STOPPING </CODE>状态。
     * 
     */
    public static final int STOPPING = 2 ;

    /**
     * Represents a <CODE>STARTING</CODE> state.
     * <p>
     *  表示<CODE>开始</CODE>状态。
     * 
     */
    public static final int STARTING = 3 ;

    //
    // Types of connectors.
    //

    /**
     * Indicates that it is an RMI connector type.
     * <p>
     *  表示它是RMI连接器类型。
     * 
     */
    //public static final int RMI_TYPE = 1 ;

    /**
     * Indicates that it is an HTTP connector type.
     * <p>
     *  表示它是HTTP连接器类型。
     * 
     */
    //public static final int HTTP_TYPE = 2 ;

    /**
     * Indicates that it is an HTML connector type.
     * <p>
     *  表示它是HTML连接器类型。
     * 
     */
    //public static final int HTML_TYPE = 3 ;

    /**
     * Indicates that it is an SNMP connector type.
     * <p>
     *  表示它是SNMP连接器类型。
     * 
     */
    public static final int SNMP_TYPE = 4 ;

    /**
     * Indicates that it is an HTTPS connector type.
     * <p>
     *  表示它是HTTPS连接器类型。
     * 
     */
    //public static final int HTTPS_TYPE = 5 ;

    //
    // Package variables
    //

    /**
     * The state of the connector server.
     * <p>
     *  连接器服务器的状态。
     * 
     */
     transient volatile int state = OFFLINE ;

    /**
     * The object name of the connector server.
     * <p>
     *  连接器服务器的对象名称。
     * 
     * 
     * @serial
     */
    ObjectName objectName ;

    MBeanServer topMBS;
    MBeanServer bottomMBS;

    /**
    /* <p>
     */
    transient String dbgTag = null ;

    /**
     * The maximum number of clients that the CommunicatorServer can
     * process concurrently.
     * <p>
     *  CommunicatorServer可以并发处理的最大客户端数。
     * 
     * 
     * @serial
     */
    int maxActiveClientCount = 1 ;

    /**
    /* <p>
     */
    transient int servedClientCount = 0 ;

    /**
     * The host name used by this CommunicatorServer.
     * <p>
     *  此CommunicatorServer使用的主机名。
     * 
     * 
     * @serial
     */
    String host = null ;

    /**
     * The port number used by this CommunicatorServer.
     * <p>
     *  此CommunicatorServer使用的端口号。
     * 
     * 
     * @serial
     */
    int port = -1 ;


    //
    // Private fields
    //

    /* This object controls access to the "state" and "interrupted" variables.
       If held at the same time as the lock on "this", the "this" lock must
    /* <p>
    /*  如果在锁定的同时保持"this","this"锁必须
    /* 
    /* 
       be taken first.  */
    private transient Object stateLock = new Object();

    private transient Vector<ClientHandler>
            clientHandlerVector = new Vector<>() ;

    private transient Thread mainThread = null ;

    private volatile boolean stopRequested = false ;
    private boolean interrupted = false;
    private transient Exception startException = null;

    // Notifs count, broadcaster and info
    private transient long notifCount = 0;
    private transient NotificationBroadcasterSupport notifBroadcaster =
        new NotificationBroadcasterSupport();
    private transient MBeanNotificationInfo[] notifInfos = null;


    /**
     * Instantiates a <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  实例化<CODE> CommunicatorServer </CODE>。
     * 
     * 
     * @param connectorType Indicates the connector type. Possible values are:
     * SNMP_TYPE.
     *
     * @exception <CODE>java.lang.IllegalArgumentException</CODE>
     *            This connector type is not correct.
     */
    public CommunicatorServer(int connectorType)
        throws IllegalArgumentException {
        switch (connectorType) {
        case SNMP_TYPE :
            //No op. int Type deciding debugging removed.
            break;
        default:
            throw new IllegalArgumentException("Invalid connector Type") ;
        }
        dbgTag = makeDebugTag() ;
    }

    protected Thread createMainThread() {
        return new Thread (this, makeThreadName());
    }

    /**
     * Starts this <CODE>CommunicatorServer</CODE>.
     * <p>
     * Has no effect if this <CODE>CommunicatorServer</CODE> is
     * <CODE>ONLINE</CODE> or <CODE>STOPPING</CODE>.
     * <p>
     * 启动此<CODE> CommunicatorServer </CODE>。
     * <p>
     *  如果此<CODE> CommunicatorServer </CODE>为<CODE>在线</CODE>或<CODE> STOPPING </CODE>,则不起作用。
     * 
     * 
     * @param timeout Time in ms to wait for the connector to start.
     *        If <code>timeout</code> is positive, wait for at most
     *        the specified time. An infinite timeout can be specified
     *        by passing a <code>timeout</code> value equals
     *        <code>Long.MAX_VALUE</code>. In that case the method
     *        will wait until the connector starts or fails to start.
     *        If timeout is negative or zero, returns as soon as possible
     *        without waiting.
     * @exception CommunicationException if the connectors fails to start.
     * @exception InterruptedException if the thread is interrupted or the
     *            timeout expires.
     */
    public void start(long timeout)
        throws CommunicationException, InterruptedException {
        boolean start;

        synchronized (stateLock) {
            if (state == STOPPING) {
                // Fix for bug 4352451:
                //     "java.net.BindException: Address in use".
                waitState(OFFLINE, 60000);
            }
            start = (state == OFFLINE);
            if (start) {
                changeState(STARTING);
                stopRequested = false;
                interrupted = false;
                startException = null;
            }
        }

        if (!start) {
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "start","Connector is not OFFLINE");
            }
            return;
        }

        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                "start","--> Start connector ");
        }

        mainThread = createMainThread();

        mainThread.start() ;

        if (timeout > 0) waitForStart(timeout);
    }

    /**
     * Starts this <CODE>CommunicatorServer</CODE>.
     * <p>
     * Has no effect if this <CODE>CommunicatorServer</CODE> is
     * <CODE>ONLINE</CODE> or <CODE>STOPPING</CODE>.
     * <p>
     *  启动此<CODE> CommunicatorServer </CODE>。
     * <p>
     *  如果此<CODE> CommunicatorServer </CODE>为<CODE>在线</CODE>或<CODE> STOPPING </CODE>,则不起作用。
     * 
     */
    @Override
    public void start() {
        try {
            start(0);
        } catch (InterruptedException x) {
            // cannot happen because of `0'
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "start","interrupted", x);
            }
        }
    }

    /**
     * Stops this <CODE>CommunicatorServer</CODE>.
     * <p>
     * Has no effect if this <CODE>CommunicatorServer</CODE> is
     * <CODE>OFFLINE</CODE> or  <CODE>STOPPING</CODE>.
     * <p>
     *  停止此<CODE> CommunicatorServer </CODE>。
     * <p>
     *  如果此<CODE> CommunicatorServer </CODE>为<CODE> OFFLINE </CODE>或<CODE> STOPPING </CODE>,则不起作用。
     * 
     */
    @Override
    public void stop() {
        synchronized (stateLock) {
            if (state == OFFLINE || state == STOPPING) {
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                        "stop","Connector is not ONLINE");
                }
                return;
            }
            changeState(STOPPING);
            //
            // Stop the connector thread
            //
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "stop","Interrupt main thread");
            }
            stopRequested = true ;
            if (!interrupted) {
                interrupted = true;
                mainThread.interrupt();
            }
        }

        //
        // Call terminate on each active client handler
        //
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                "stop","terminateAllClient");
        }
        terminateAllClient() ;

        // ----------------------
        // changeState
        // ----------------------
        synchronized (stateLock) {
            if (state == STARTING)
                changeState(OFFLINE);
        }
    }

    /**
     * Tests whether the <CODE>CommunicatorServer</CODE> is active.
     *
     * <p>
     *  测试<CODE> CommunicatorServer </CODE>是否处于活动状态。
     * 
     * 
     * @return True if connector is <CODE>ONLINE</CODE>; false otherwise.
     */
    @Override
    public boolean isActive() {
        synchronized (stateLock) {
            return (state == ONLINE);
        }
    }

    /**
     * <p>Waits until either the State attribute of this MBean equals the
     * specified <VAR>wantedState</VAR> parameter,
     * or the specified  <VAR>timeOut</VAR> has elapsed.
     * The method <CODE>waitState</CODE> returns with a boolean value
     * indicating whether the specified <VAR>wantedState</VAR> parameter
     * equals the value of this MBean's State attribute at the time the method
     * terminates.</p>
     *
     * <p>Two special cases for the <VAR>timeOut</VAR> parameter value are:</p>
     * <UL><LI> if <VAR>timeOut</VAR> is negative then <CODE>waitState</CODE>
     *     returns immediately (i.e. does not wait at all),</LI>
     * <LI> if <VAR>timeOut</VAR> equals zero then <CODE>waitState</CODE>
     *     waits untill the value of this MBean's State attribute
     *     is the same as the <VAR>wantedState</VAR> parameter (i.e. will wait
     *     indefinitely if this condition is never met).</LI></UL>
     *
     * <p>
     *  <p>等待,直到此MBean的State属性等于指定的<VAR> wantedState </VAR>参数,或指定的<VAR> timeOut </VAR>已过。
     * 方法<CODE> waitState </CODE>返回一个布尔值,指示方法终止时指定的<VAR> wantedState </VAR>参数是否等于此MBean的State属性的值。</p>。
     * 
     *  <p> <VAR> timeOut </VAR>参数值的两种特殊情况是：</p> <UL> <LI>如果<VAR> timeOut </VAR>为负,那么<CODE> waitState </CODE>
     * 立即返回(即完全不等待),</LI> <LI>如果<VAR> timeOut </VAR>等于零,那么<CODE> waitState </CODE>等待,直到此MBean的State属性的值相同作为<VAR>
     *  wantedState </VAR>参数(即,如果永远不满足该条件,将无限期地等待)。
     * </LI> </UL>。
     * 
     * 
     * @param wantedState The value of this MBean's State attribute to wait
     *        for. <VAR>wantedState</VAR> can be one of:
     * <ul>
     * <li><CODE>CommunicatorServer.OFFLINE</CODE>,</li>
     * <li><CODE>CommunicatorServer.ONLINE</CODE>,</li>
     * <li><CODE>CommunicatorServer.STARTING</CODE>,</li>
     * <li><CODE>CommunicatorServer.STOPPING</CODE>.</li>
     * </ul>
     * @param timeOut The maximum time to wait for, in milliseconds,
     *        if positive.
     * Infinite time out if 0, or no waiting at all if negative.
     *
     * @return true if the value of this MBean's State attribute is the
     *      same as the <VAR>wantedState</VAR> parameter; false otherwise.
     */
    @Override
    public boolean waitState(int wantedState, long timeOut) {
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                "waitState", wantedState + "(0on,1off,2st) TO=" + timeOut +
                  " ; current state = " + getStateString());
        }

        long endTime = 0;
        if (timeOut > 0)
            endTime = System.currentTimeMillis() + timeOut;

        synchronized (stateLock) {
            while (state != wantedState) {
                if (timeOut < 0) {
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                            "waitState", "timeOut < 0, return without wait");
                    }
                    return false;
                } else {
                    try {
                        if (timeOut > 0) {
                            long toWait = endTime - System.currentTimeMillis();
                            if (toWait <= 0) {
                                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                                    SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                                        "waitState", "timed out");
                                }
                                return false;
                            }
                            stateLock.wait(toWait);
                        } else {  // timeOut == 0
                            stateLock.wait();
                        }
                    } catch (InterruptedException e) {
                        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                                "waitState", "wait interrupted");
                        }
                        return (state == wantedState);
                    }
                }
            }
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "waitState","returning in desired state");
            }
            return true;
        }
    }

    /**
     * <p>Waits until the communicator is started or timeout expires.
     *
     * <p>
     *  <p>等待直到通信器启动或超时到期。
     * 
     * 
     * @param timeout Time in ms to wait for the connector to start.
     *        If <code>timeout</code> is positive, wait for at most
     *        the specified time. An infinite timeout can be specified
     *        by passing a <code>timeout</code> value equals
     *        <code>Long.MAX_VALUE</code>. In that case the method
     *        will wait until the connector starts or fails to start.
     *        If timeout is negative or zero, returns as soon as possible
     *        without waiting.
     *
     * @exception CommunicationException if the connectors fails to start.
     * @exception InterruptedException if the thread is interrupted or the
     *            timeout expires.
     *
     */
    private void waitForStart(long timeout)
        throws CommunicationException, InterruptedException {
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                "waitForStart", "Timeout=" + timeout +
                 " ; current state = " + getStateString());
        }

        final long startTime = System.currentTimeMillis();

        synchronized (stateLock) {
            while (state == STARTING) {
                // Time elapsed since startTime...
                //
                final long elapsed = System.currentTimeMillis() - startTime;

                // wait for timeout - elapsed.
                // A timeout of Long.MAX_VALUE is equivalent to something
                // like 292271023 years - which is pretty close to
                // forever as far as we are concerned ;-)
                //
                final long remainingTime = timeout-elapsed;

                // If remainingTime is negative, the timeout has elapsed.
                //
                if (remainingTime < 0) {
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                            "waitForStart", "timeout < 0, return without wait");
                    }
                    throw new InterruptedException("Timeout expired");
                }

                // We're going to wait until someone notifies on the
                // the stateLock object, or until the timeout expires,
                // or until the thread is interrupted.
                //
                try {
                    stateLock.wait(remainingTime);
                } catch (InterruptedException e) {
                    if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                        SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                            "waitForStart", "wait interrupted");
                    }

                    // If we are now ONLINE, then no need to rethrow the
                    // exception... we're simply going to exit the while
                    // loop. Otherwise, throw the InterruptedException.
                    //
                    if (state != ONLINE) throw e;
                }
            }

            // We're no longer in STARTING state
            //
            if (state == ONLINE) {
                // OK, we're started, everything went fine, just return
                //
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                        "waitForStart", "started");
                }
                return;
            } else if (startException instanceof CommunicationException) {
                // There was some exception during the starting phase.
                // Cast and throw...
                //
                throw (CommunicationException)startException;
            } else if (startException instanceof InterruptedException) {
                // There was some exception during the starting phase.
                // Cast and throw...
                //
                throw (InterruptedException)startException;
            } else if (startException != null) {
                // There was some exception during the starting phase.
                // Wrap and throw...
                //
                throw new CommunicationException(startException,
                                                 "Failed to start: "+
                                                 startException);
            } else {
                // We're not ONLINE, and there's no exception...
                // Something went wrong but we don't know what...
                //
                throw new CommunicationException("Failed to start: state is "+
                                                 getStringForState(state));
            }
        }
    }

    /**
     * Gets the state of this <CODE>CommunicatorServer</CODE> as an integer.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>的状态为整数。
     * 
     * 
     * @return <CODE>ONLINE</CODE>, <CODE>OFFLINE</CODE>,
     *         <CODE>STARTING</CODE> or <CODE>STOPPING</CODE>.
     */
    @Override
    public int getState() {
        synchronized (stateLock) {
            return state ;
        }
    }

    /**
     * Gets the state of this <CODE>CommunicatorServer</CODE> as a string.
     *
     * <p>
     * 获取此<CODE> CommunicatorServer </CODE>的状态为字符串。
     * 
     * 
     * @return One of the strings "ONLINE", "OFFLINE", "STARTING" or
     *         "STOPPING".
     */
    @Override
    public String getStateString() {
        return getStringForState(state) ;
    }

    /**
     * Gets the host name used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>使用的主机名。
     * 
     * 
     * @return The host name used by this <CODE>CommunicatorServer</CODE>.
     */
    @Override
    public String getHost() {
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            host = "Unknown host";
        }
        return host ;
    }

    /**
     * Gets the port number used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>使用的端口号。
     * 
     * 
     * @return The port number used by this <CODE>CommunicatorServer</CODE>.
     */
    @Override
    public int getPort() {
        synchronized (stateLock) {
            return port ;
        }
    }

    /**
     * Sets the port number used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  设置此<CODE> CommunicatorServer </CODE>使用的端口号。
     * 
     * 
     * @param port The port number used by this
     *             <CODE>CommunicatorServer</CODE>.
     *
     * @exception java.lang.IllegalStateException This method has been invoked
     * while the communicator was ONLINE or STARTING.
     */
    @Override
    public void setPort(int port) throws java.lang.IllegalStateException {
        synchronized (stateLock) {
            if ((state == ONLINE) || (state == STARTING))
                throw new IllegalStateException("Stop server before " +
                                                "carrying out this operation");
            this.port = port;
            dbgTag = makeDebugTag();
        }
    }

    /**
     * Gets the protocol being used by this <CODE>CommunicatorServer</CODE>.
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>正在使用的协议。
     * 
     * 
     * @return The protocol as a string.
     */
    @Override
    public abstract String getProtocol();

    /**
     * Gets the number of clients that have been processed by this
     * <CODE>CommunicatorServer</CODE>  since its creation.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>创建后处理的客户端数。
     * 
     * 
     * @return The number of clients handled by this
     *         <CODE>CommunicatorServer</CODE>
     *         since its creation. This counter is not reset by the
     *         <CODE>stop</CODE> method.
     */
    int getServedClientCount() {
        return servedClientCount ;
    }

    /**
     * Gets the number of clients currently being processed by this
     * <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>正在处理的客户端数。
     * 
     * 
     * @return The number of clients currently being processed by this
     *         <CODE>CommunicatorServer</CODE>.
     */
    int getActiveClientCount() {
        int result = clientHandlerVector.size() ;
        return result ;
    }

    /**
     * Gets the maximum number of clients that this
     * <CODE>CommunicatorServer</CODE> can  process concurrently.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>可同时处理的客户端的最大数量。
     * 
     * 
     * @return The maximum number of clients that this
     *         <CODE>CommunicatorServer</CODE> can
     *         process concurrently.
     */
    int getMaxActiveClientCount() {
        return maxActiveClientCount ;
    }

    /**
     * Sets the maximum number of clients this
     * <CODE>CommunicatorServer</CODE> can process concurrently.
     *
     * <p>
     *  设置此<CODE> CommunicatorServer </CODE>可并发处理的客户端的最大数量。
     * 
     * 
     * @param c The number of clients.
     *
     * @exception java.lang.IllegalStateException This method has been invoked
     * while the communicator was ONLINE or STARTING.
     */
    void setMaxActiveClientCount(int c)
        throws java.lang.IllegalStateException {
        synchronized (stateLock) {
            if ((state == ONLINE) || (state == STARTING)) {
                throw new IllegalStateException(
                          "Stop server before carrying out this operation");
            }
            maxActiveClientCount = c ;
        }
    }

    /**
     * For SNMP Runtime internal use only.
     * <p>
     *  仅供SNMP Runtime内部使用。
     * 
     */
    void notifyClientHandlerCreated(ClientHandler h) {
        clientHandlerVector.addElement(h) ;
    }

    /**
     * For SNMP Runtime internal use only.
     * <p>
     *  仅供SNMP Runtime内部使用。
     * 
     */
    synchronized void notifyClientHandlerDeleted(ClientHandler h) {
        clientHandlerVector.removeElement(h);
        notifyAll();
    }

    /**
     * The number of times the communicator server will attempt
     * to bind before giving up.
     * <p>
     *  通信器服务器在放弃之前将尝试绑定的次数。
     * 
     * 
     **/
    protected int getBindTries() {
        return 50;
    }

    /**
     * The delay, in ms, during which the communicator server will sleep before
     * attempting to bind again.
     * <p>
     *  延迟(以毫秒为单位),通信器服务器在尝试再次绑定之前将休眠。
     * 
     * 
     **/
    protected long getBindSleepTime() {
        return 100;
    }

    /**
     * For SNMP Runtime internal use only.
     * <p>
     * The <CODE>run</CODE> method executed by this connector's main thread.
     * <p>
     *  仅供SNMP Runtime内部使用。
     * <p>
     *  由此连接器的主线程执行的<CODE>运行</CODE>方法。
     * 
     */
    @Override
    public void run() {

        // Fix jaw.00667.B
        // It seems that the init of "i" and "success"
        // need to be done outside the "try" clause...
        // A bug in Java 2 production release ?
        //
        int i = 0;
        boolean success = false;

        // ----------------------
        // Bind
        // ----------------------
        try {
            // Fix for bug 4352451: "java.net.BindException: Address in use".
            //
            final int  bindRetries = getBindTries();
            final long sleepTime   = getBindSleepTime();
            while (i < bindRetries && !success) {
                try {
                    // Try socket connection.
                    //
                    doBind();
                    success = true;
                } catch (CommunicationException ce) {
                    i++;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ie) {
                        throw ie;
                    }
                }
            }
            // Retry last time to get correct exception.
            //
            if (!success) {
                // Try socket connection.
                //
                doBind();
            }

        } catch(Exception x) {
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                    "run", "Got unexpected exception", x);
            }
            synchronized(stateLock) {
                startException = x;
                changeState(OFFLINE);
            }
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "run","State is OFFLINE");
            }
            doError(x);
            return;
        }

        try {
            // ----------------------
            // State change
            // ----------------------
            changeState(ONLINE) ;
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "run","State is ONLINE");
            }

            // ----------------------
            // Main loop
            // ----------------------
            while (!stopRequested) {
                servedClientCount++;
                doReceive() ;
                waitIfTooManyClients() ;
                doProcess() ;
            }
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "run","Stop has been requested");
            }

        } catch(InterruptedException x) {
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                    "run","Interrupt caught");
            }
            changeState(STOPPING);
        } catch(Exception x) {
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                    "run","Got unexpected exception", x);
            }
            changeState(STOPPING);
        } finally {
            synchronized (stateLock) {
                interrupted = true;
                Thread.interrupted();
            }

            // ----------------------
            // unBind
            // ----------------------
            try {
                doUnbind() ;
                waitClientTermination() ;
                changeState(OFFLINE);
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                        "run","State is OFFLINE");
                }
            } catch(Exception x) {
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                        "run","Got unexpected exception", x);
                }
                changeState(OFFLINE);
            }

        }
    }

    /**
    /* <p>
     */
    protected abstract void doError(Exception e) throws CommunicationException;

    //
    // To be defined by the subclass.
    //
    // Each method below is called by run() and must be subclassed.
    // If the method sends an exception (Communication or Interrupt), this
    // will end up the run() method and switch the connector offline.
    //
    // If it is a CommunicationException, run() will call
    //       Debug.printException().
    //
    // All these methods should propagate the InterruptedException to inform
    // run() that the connector must be switch OFFLINE.
    //
    //
    //
    // doBind() should do all what is needed before calling doReceive().
    // If doBind() throws an exception, doUnbind() is not to be called
    // and run() ends up.
    //

    /**
    /* <p>
     */
    protected abstract void doBind()
        throws CommunicationException, InterruptedException ;

    /**
     * <CODE>doReceive()</CODE> should block until a client is available.
     * If this method throws an exception, <CODE>doProcess()</CODE> is not
     * called but <CODE>doUnbind()</CODE> is called then <CODE>run()</CODE>
     * stops.
     * <p>
     *  <CODE> doReceive()</CODE>应阻止,直到客户端可用。
     * 如果此方法抛出异常,则不调用<CODE> doProcess()</CODE>,而是调用<CODE> doUnbind()</CODE>,然后调用<CODE> run()</CODE>。
     * 
     */
    protected abstract void doReceive()
        throws CommunicationException, InterruptedException ;

    /**
     * <CODE>doProcess()</CODE> is called after <CODE>doReceive()</CODE>:
     * it should process the requests of the incoming client.
     * If it throws an exception, <CODE>doUnbind()</CODE> is called and
     * <CODE>run()</CODE> stops.
     * <p>
     * <CODE> doProcess()</CODE>在<CODE> doReceive()</CODE>之后调用：它应该处理传入客户端的请求。
     * 如果它抛出异常,则调用<CODE> doUnbind()</CODE>并停止<CODE> run()</CODE>。
     * 
     */
    protected abstract void doProcess()
        throws CommunicationException, InterruptedException ;

    /**
     * <CODE>doUnbind()</CODE> is called whenever the connector goes
     * <CODE>OFFLINE</CODE>, except if <CODE>doBind()</CODE> has thrown an
     * exception.
     * <p>
     *  <CODE> doUnbind()</CODE>在连接器变为<CODE> OFFLINE </CODE>时调用,除非<CODE> doBind()</CODE>引发异常。
     * 
     */
    protected abstract void doUnbind()
        throws CommunicationException, InterruptedException ;

    /**
     * Get the <code>MBeanServer</code> object to which incoming requests are
     * sent.  This is either the MBean server in which this connector is
     * registered, or an <code>MBeanServerForwarder</code> leading to that
     * server.
     * <p>
     *  获取发送传入请求的<code> MBeanServer </code>对象。这是注册此连接器的MBean服务器或导向该服务器的<code> MBeanServerForwarder </code>。
     * 
     */
    public synchronized MBeanServer getMBeanServer() {
        return topMBS;
    }

    /**
     * Set the <code>MBeanServer</code> object to which incoming
     * requests are sent.  This must be either the MBean server in
     * which this connector is registered, or an
     * <code>MBeanServerForwarder</code> leading to that server.  An
     * <code>MBeanServerForwarder</code> <code>mbsf</code> leads to an
     * MBean server <code>mbs</code> if
     * <code>mbsf.getMBeanServer()</code> is either <code>mbs</code>
     * or an <code>MBeanServerForwarder</code> leading to
     * <code>mbs</code>.
     *
     * <p>
     *  设置发送传入请求的<code> MBeanServer </code>对象。
     * 它必须是注册此连接器的MBean服务器或导向该服务器的<code> MBeanServerForwarder </code>。
     * 如果<code> mbsf.getMBeanServer()</code>是<code> mbs </code>,则<code> MBeanServerForwarder </code> <code> 
     * mbsf </code>会导向MBean服务器< / code>或导致<code> mbs </code>的<code> MBeanServerForwarder </code>。
     * 它必须是注册此连接器的MBean服务器或导向该服务器的<code> MBeanServerForwarder </code>。
     * 
     * 
     * @exception IllegalArgumentException if <code>newMBS</code> is neither
     * the MBean server in which this connector is registered nor an
     * <code>MBeanServerForwarder</code> leading to that server.
     *
     * @exception IllegalStateException This method has been invoked
     * while the communicator was ONLINE or STARTING.
     */
    public synchronized void setMBeanServer(MBeanServer newMBS)
            throws IllegalArgumentException, IllegalStateException {
        synchronized (stateLock) {
            if (state == ONLINE || state == STARTING)
                throw new IllegalStateException("Stop server before " +
                                                "carrying out this operation");
        }
        final String error =
            "MBeanServer argument must be MBean server where this " +
            "server is registered, or an MBeanServerForwarder " +
            "leading to that server";
        Vector<MBeanServer> seenMBS = new Vector<>();
        for (MBeanServer mbs = newMBS;
             mbs != bottomMBS;
             mbs = ((MBeanServerForwarder) mbs).getMBeanServer()) {
            if (!(mbs instanceof MBeanServerForwarder))
                throw new IllegalArgumentException(error);
            if (seenMBS.contains(mbs))
                throw new IllegalArgumentException("MBeanServerForwarder " +
                                                   "loop");
            seenMBS.addElement(mbs);
        }
        topMBS = newMBS;
    }

    //
    // To be called by the subclass if needed
    //
    /**
     * For internal use only.
     * <p>
     *  仅限内部使用。
     * 
     */
    ObjectName getObjectName() {
        return objectName ;
    }

    /**
     * For internal use only.
     * <p>
     *  仅限内部使用。
     * 
     */
    void changeState(int newState) {
        int oldState;
        synchronized (stateLock) {
            if (state == newState)
                return;
            oldState = state;
            state = newState;
            stateLock.notifyAll();
        }
        sendStateChangeNotification(oldState, newState);
    }

    /**
     * Returns the string used in debug traces.
     * <p>
     *  返回调试跟踪中使用的字符串。
     * 
     */
    String makeDebugTag() {
        return "CommunicatorServer["+ getProtocol() + ":" + getPort() + "]" ;
    }

    /**
     * Returns the string used to name the connector thread.
     * <p>
     *  返回用于命名连接器线程的字符串。
     * 
     */
    String makeThreadName() {
        String result ;

        if (objectName == null)
            result = "CommunicatorServer" ;
        else
            result = objectName.toString() ;

        return result ;
    }

    /**
     * This method blocks if there are too many active clients.
     * Call to <CODE>wait()</CODE> is terminated when a client handler
     * thread calls <CODE>notifyClientHandlerDeleted(this)</CODE> ;
     * <p>
     *  此方法阻止是否有太多活动客户端。当客户端处理程序线程调用<CODE> notifyClientHandlerDeleted(this)</CODE>时,调用<CODE> wait()</CODE>
     * 
     */
    private synchronized void waitIfTooManyClients()
        throws InterruptedException {
        while (getActiveClientCount() >= maxActiveClientCount) {
            if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "waitIfTooManyClients","Waiting for a client to terminate");
            }
            wait();
        }
    }

    /**
     * This method blocks until there is no more active client.
     * <p>
     *  此方法阻塞,直到没有更多的活动客户端。
     * 
     */
    private void waitClientTermination() {
        int s = clientHandlerVector.size() ;
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            if (s >= 1) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                "waitClientTermination","waiting for " +
                      s + " clients to terminate");
            }
        }

        // The ClientHandler will remove themselves from the
        // clientHandlerVector at the end of their run() method, by
        // calling notifyClientHandlerDeleted().
        // Since the clientHandlerVector is modified by the ClientHandler
        // threads we must avoid using Enumeration or Iterator to loop
        // over this array. We must also take care of NoSuchElementException
        // which could be thrown if the last ClientHandler removes itself
        // between the call to clientHandlerVector.isEmpty() and the call
        // to clientHandlerVector.firstElement().
        // What we *MUST NOT DO* is locking the clientHandlerVector, because
        // this would most probably cause a deadlock.
        //
        while (! clientHandlerVector.isEmpty()) {
            try {
                clientHandlerVector.firstElement().join();
            } catch (NoSuchElementException x) {
                if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                    SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                        "waitClientTermination","No elements left",  x);
                }
            }
        }

        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            if (s >= 1) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "waitClientTermination","Ok, let's go...");
            }
        }
    }

    /**
     * Call <CODE>interrupt()</CODE> on each pending client.
     * <p>
     *  在每个挂起的客户端上调用<CODE> interrupt()</CODE>。
     * 
     */
    private void terminateAllClient() {
        final int s = clientHandlerVector.size() ;
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
            if (s >= 1) {
                SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                    "terminateAllClient","Interrupting " + s + " clients");
            }
        }

        // The ClientHandler will remove themselves from the
        // clientHandlerVector at the end of their run() method, by
        // calling notifyClientHandlerDeleted().
        // Since the clientHandlerVector is modified by the ClientHandler
        // threads we must avoid using Enumeration or Iterator to loop
        // over this array.
        // We cannot use the same logic here than in waitClientTermination()
        // because there is no guarantee that calling interrupt() on the
        // ClientHandler will actually terminate the ClientHandler.
        // Since we do not want to wait for the actual ClientHandler
        // termination, we cannot simply loop over the array until it is
        // empty (this might result in calling interrupt() endlessly on
        // the same client handler. So what we do is simply take a snapshot
        // copy of the vector and loop over the copy.
        // What we *MUST NOT DO* is locking the clientHandlerVector, because
        // this would most probably cause a deadlock.
        //
        final  ClientHandler[] handlers =
                clientHandlerVector.toArray(new ClientHandler[0]);
         for (ClientHandler h : handlers) {
             try {
                 h.interrupt() ;
             } catch (Exception x) {
                 if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
                     SNMP_ADAPTOR_LOGGER.logp(Level.FINER, dbgTag,
                             "terminateAllClient",
                             "Failed to interrupt pending request. " +
                             "Ignore the exception.", x);
                 }
            }
        }
    }

    /**
     * Controls the way the CommunicatorServer service is deserialized.
     * <p>
     * 控制CommunicatorServer服务反序列化的方式。
     * 
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        // Call the default deserialization of the object.
        //
        stream.defaultReadObject();

        // Call the specific initialization for the CommunicatorServer service.
        // This is for transient structures to be initialized to specific
        // default values.
        //
        stateLock = new Object();
        state = OFFLINE;
        stopRequested = false;
        servedClientCount = 0;
        clientHandlerVector = new Vector<>();
        mainThread = null;
        notifCount = 0;
        notifInfos = null;
        notifBroadcaster = new NotificationBroadcasterSupport();
        dbgTag = makeDebugTag();
    }


    //
    // NotificationBroadcaster
    //

    /**
     * Adds a listener for the notifications emitted by this
     * CommunicatorServer.
     * There is only one type of notifications sent by the CommunicatorServer:
     * they are <tt>{@link javax.management.AttributeChangeNotification}</tt>,
     * sent when the <tt>State</tt> attribute of this CommunicatorServer
     * changes.
     *
     * <p>
     *  为此CommunicatorServer发出的通知添加一个侦听器。
     *  CommunicatorServer只发送一种类型的通知：它们是<CommunicatorServer <tt> State </tt>属性更改时发送的<tt> {@ link javax.management.AttributeChangeNotification}
     *  </tt>。
     *  为此CommunicatorServer发出的通知添加一个侦听器。
     * 
     * 
     * @param listener The listener object which will handle the emitted
     *        notifications.
     * @param filter The filter object. If filter is null, no filtering
     *        will be performed before handling notifications.
     * @param handback An object which will be sent back unchanged to the
     *        listener when a notification is emitted.
     *
     * @exception IllegalArgumentException Listener parameter is null.
     */
    @Override
    public void addNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback)
        throws java.lang.IllegalArgumentException {

        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                "addNotificationListener","Adding listener "+ listener +
                  " with filter "+ filter + " and handback "+ handback);
        }
        notifBroadcaster.addNotificationListener(listener, filter, handback);
    }

    /**
     * Removes the specified listener from this CommunicatorServer.
     * Note that if the listener has been registered with different
     * handback objects or notification filters, all entries corresponding
     * to the listener will be removed.
     *
     * <p>
     *  从此CommunicatorServer中删除指定的侦听器。请注意,如果侦听器已注册了不同的手动对象或通知过滤器,则与侦听器相对应的所有条目都将被删除。
     * 
     * 
     * @param listener The listener object to be removed.
     *
     * @exception ListenerNotFoundException The listener is not registered.
     */
    @Override
    public void removeNotificationListener(NotificationListener listener)
        throws ListenerNotFoundException {

        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                "removeNotificationListener","Removing listener "+ listener);
        }
        notifBroadcaster.removeNotificationListener(listener);
    }

    /**
     * Returns an array of MBeanNotificationInfo objects describing
     * the notification types sent by this CommunicatorServer.
     * There is only one type of notifications sent by the CommunicatorServer:
     * it is <tt>{@link javax.management.AttributeChangeNotification}</tt>,
     * sent when the <tt>State</tt> attribute of this CommunicatorServer
     * changes.
     * <p>
     *  返回描述此CommunicatorServer发送的通知类型的MBeanNotificationInfo对象的数组。
     *  CommunicatorServer只发送一种类型的通知：<CommunicatorServer <tt> State </tt>属性更改时发送<tt> {@ link javax.management.AttributeChangeNotification}
     *  </tt>。
     *  返回描述此CommunicatorServer发送的通知类型的MBeanNotificationInfo对象的数组。
     * 
     */
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {

        // Initialize notifInfos on first call to getNotificationInfo()
        //
        if (notifInfos == null) {
            notifInfos = new MBeanNotificationInfo[1];
            String[] notifTypes = {
                AttributeChangeNotification.ATTRIBUTE_CHANGE};
            notifInfos[0] = new MBeanNotificationInfo( notifTypes,
                     AttributeChangeNotification.class.getName(),
                     "Sent to notify that the value of the State attribute "+
                     "of this CommunicatorServer instance has changed.");
        }

        return notifInfos.clone();
    }

    /**
     *
     * <p>
     */
    private void sendStateChangeNotification(int oldState, int newState) {

        String oldStateString = getStringForState(oldState);
        String newStateString = getStringForState(newState);
        String message = new StringBuffer().append(dbgTag)
            .append(" The value of attribute State has changed from ")
            .append(oldState).append(" (").append(oldStateString)
            .append(") to ").append(newState).append(" (")
            .append(newStateString).append(").").toString();

        notifCount++;
        AttributeChangeNotification notif =
            new AttributeChangeNotification(this,    // source
                         notifCount,                 // sequence number
                         System.currentTimeMillis(), // time stamp
                         message,                    // message
                         "State",                    // attribute name
                         "int",                      // attribute type
                         new Integer(oldState),      // old value
                         new Integer(newState) );    // new value
        if (SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
            SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, dbgTag,
                "sendStateChangeNotification","Sending AttributeChangeNotification #"
                    + notifCount + " with message: "+ message);
        }
        notifBroadcaster.sendNotification(notif);
    }

    /**
     *
     * <p>
     */
    private static String getStringForState(int s) {
        switch (s) {
        case ONLINE:   return "ONLINE";
        case STARTING: return "STARTING";
        case OFFLINE:  return "OFFLINE";
        case STOPPING: return "STOPPING";
        default:       return "UNDEFINED";
        }
    }


    //
    // MBeanRegistration
    //

    /**
     * Preregister method of connector.
     *
     * <p>
     *  连接器的预注册方法。
     * 
     * 
     *@param server The <CODE>MBeanServer</CODE> in which the MBean will
     *       be registered.
     *@param name The object name of the MBean.
     *
     *@return  The name of the MBean registered.
     *
     *@exception java.langException This exception should be caught by
     *           the <CODE>MBeanServer</CODE> and re-thrown
     *           as an <CODE>MBeanRegistrationException</CODE>.
     */
    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name)
            throws java.lang.Exception {
        objectName = name;
        synchronized (this) {
            if (bottomMBS != null) {
                throw new IllegalArgumentException("connector already " +
                                                   "registered in an MBean " +
                                                   "server");
            }
            topMBS = bottomMBS = server;
        }
        dbgTag = makeDebugTag();
        return name;
    }

    /**
     *
     * <p>
     * 
     *@param registrationDone Indicates whether or not the MBean has been
     *       successfully registered in the <CODE>MBeanServer</CODE>.
     *       The value false means that the registration phase has failed.
     */
    @Override
    public void postRegister(Boolean registrationDone) {
        if (!registrationDone.booleanValue()) {
            synchronized (this) {
                topMBS = bottomMBS = null;
            }
        }
    }

    /**
     * Stop the connector.
     *
     * <p>
     *  停止连接器。
     * 
     * 
     * @exception java.langException This exception should be caught by
     *            the <CODE>MBeanServer</CODE> and re-thrown
     *            as an <CODE>MBeanRegistrationException</CODE>.
     */
    @Override
    public void preDeregister() throws java.lang.Exception {
        synchronized (this) {
            topMBS = bottomMBS = null;
        }
        objectName = null ;
        final int cstate = getState();
        if ((cstate == ONLINE) || ( cstate == STARTING)) {
            stop() ;
        }
    }

    /**
     * Do nothing.
     * <p>
     *  没做什么。
     */
    @Override
    public void postDeregister(){
    }

}
