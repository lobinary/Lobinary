/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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



/**
 * Defines generic behaviour for the server
 * part of a connector or an adaptor. Most connectors or adaptors extend <CODE>CommunicatorServer</CODE>
 * and inherit this behaviour. Connectors or adaptors that do not fit into this model do not extend
 * <CODE>CommunicatorServer</CODE>.
 * <p>
 * An <CODE>CommunicatorServer</CODE> is an active object, it listens for client requests
 * and processes them in its own thread. When necessary, a <CODE>CommunicatorServer</CODE>
 * creates other threads to process multiple requests concurrently.
 * <p>
 * A <CODE>CommunicatorServer</CODE> object can be stopped by calling the <CODE>stop</CODE>
 * method. When it is stopped, the <CODE>CommunicatorServer</CODE> no longer listens to client
 * requests and no longer holds any thread or communication resources.
 * It can be started again by calling the <CODE>start</CODE> method.
 * <p>
 * A <CODE>CommunicatorServer</CODE> has a <CODE>state</CODE> property which reflects its
 * activity.
 * <p>
 * <TABLE>
 * <TR><TH>CommunicatorServer</TH>            <TH>State</TH></TR>
 * <TR><TD><CODE>stopped</CODE></TD>          <TD><CODE>OFFLINE</CODE></TD></TR>
 * <TR><TD><CODE>starting</CODE></TD>         <TD><CODE>STARTING</CODE></TD></TR>
 * <TR><TD><CODE>running</CODE></TD>          <TD><CODE>ONLINE</CODE></TD></TR>
 * <TR><TD><CODE>stopping</CODE></TD>         <TD><CODE>STOPPING</CODE></TD></TR>
 * </TABLE>
 * <p>
 * The <CODE>STARTING</CODE> state marks the transition from <CODE>OFFLINE</CODE> to
 * <CODE>ONLINE</CODE>.
 * <p>
 * The <CODE>STOPPING</CODE> state marks the transition from <CODE>ONLINE</CODE> to
 * <CODE>OFFLINE</CODE>. This occurs when the <CODE>CommunicatorServer</CODE> is
 * finishing or interrupting active requests.
 * <p>
 * A <CODE>CommunicatorServer</CODE> may serve several clients concurrently. The
 * number of concurrent clients can be limited using the property
 * <CODE>maxActiveClientCount</CODE>. The default value of this property is
 * defined by the subclasses.
 * <p>
 * When a <CODE>CommunicatorServer</CODE> is unregistered from the MBeanServer,
 * it is stopped automatically.
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
 *  <CODE> CommunicatorServer </CODE>可以同时为多个客户端提供服务。
 * 可以使用属性<CODE> maxActiveClientCount </CODE>限制并发客户端的数量。此属性的默认值由子类定义。
 * <p>
 *  当<CODE> CommunicatorServer </CODE>从MBeanServer注销时,它会自动停止。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public interface CommunicatorServerMBean {

    /**
     * Starts this <CODE>CommunicatorServer</CODE>.
     * <p>
     * Has no effect if this <CODE>CommunicatorServer</CODE> is <CODE>ONLINE</CODE> or
     * <CODE>STOPPING</CODE>.
     * <p>
     *  启动此<CODE> CommunicatorServer </CODE>。
     * <p>
     *  如果此<CODE> CommunicatorServer </CODE>为<CODE>在线</CODE>或<CODE> STOPPING </CODE>,则不起作用。
     * 
     */
    public void start() ;

    /**
     * Stops this <CODE>CommunicatorServer</CODE>.
     * <p>
     * Has no effect if this <CODE>CommunicatorServer</CODE> is <CODE>OFFLINE</CODE> or
     * <CODE>STOPPING</CODE>.
     * <p>
     *  停止此<CODE> CommunicatorServer </CODE>。
     * <p>
     *  如果此<CODE> CommunicatorServer </CODE>为<CODE> OFFLINE </CODE>或<CODE> STOPPING </CODE>,则不起作用。
     * 
     */
    public void stop() ;

    /**
     * Tests if the <CODE>CommunicatorServer</CODE> is active.
     *
     * <p>
     *  测试<CODE> CommunicatorServer </CODE>是否处于活动状态。
     * 
     * 
     * @return True if connector is <CODE>ONLINE</CODE>; false otherwise.
     */
    public boolean isActive() ;

    /**
     * Waits untill either the State attribute of this MBean equals the specified <VAR>state</VAR> parameter,
     * or the specified  <VAR>timeOut</VAR> has elapsed. The method <CODE>waitState</CODE> returns with a boolean value indicating whether
     * the specified <VAR>state</VAR> parameter equals the value of this MBean's State attribute at the time the method terminates.
     *
     * Two special cases for the <VAR>timeOut</VAR> parameter value are:
     * <UL><LI> if <VAR>timeOut</VAR> is negative then <CODE>waitState</CODE> returns immediately (i.e. does not wait at all),</LI>
     * <LI> if <VAR>timeOut</VAR> equals zero then <CODE>waitState</CODE> waits untill the value of this MBean's State attribute
     * is the same as the <VAR>state</VAR> parameter (i.e. will wait indefinitely if this condition is never met).</LI></UL>
     *
     * <p>
     *  等待,直到此MBean的State属性等于指定的<VAR>状态</VAR>参数,或指定的<VAR> timeOut </VAR>已过。
     * 方法<CODE> waitState </CODE>返回一个布尔值,指示在方法终止时指定的<VAR>状态</VAR>参数是否等于此MBean的State属性的值。
     * 
     * <VAR> timeOut </VAR>参数值的两种特殊情况是：<UL> <LI>如果<VAR> timeOut </VAR>为负,那么<CODE> waitState </CODE>立即返回如果<VAR>
     *  timeOut </VAR>等于零,则<CODE> waitState </CODE>等待,直到此MBean的State属性的值与<VAR>状态< / VAR>参数(即,如果永远不满足此条件,将无限期
     * 地等待)。
     * </LI> </UL>。
     * 
     * 
     * @param state The value of this MBean's State attribute
     *        to wait for. <VAR>state</VAR> can be one of:
     * <ul>
     * <li><CODE>CommunicatorServer.OFFLINE</CODE>,</li>
     * <li><CODE>CommunicatorServer.ONLINE</CODE>,</li>
     * <li><CODE>CommunicatorServer.STARTING</CODE>,</li>
     * <li><CODE>CommunicatorServer.STOPPING</CODE>.</li>
     * </ul>
     * @param timeOut The maximum time to wait for, in
     *        milliseconds, if positive.
     * Infinite time out if 0, or no waiting at all if negative.
     *
     * @return true if the value of this MBean's State attribute is the
     *  same as the <VAR>state</VAR> parameter; false otherwise.
     */
    public boolean waitState(int state , long timeOut) ;

    /**
     * Gets the state of this <CODE>CommunicatorServer</CODE> as an integer.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>的状态为整数。
     * 
     * 
     * @return <CODE>ONLINE</CODE>, <CODE>OFFLINE</CODE>, <CODE>STARTING</CODE> or <CODE>STOPPING</CODE>.
     */
    public int getState() ;

    /**
     * Gets the state of this <CODE>CommunicatorServer</CODE> as a string.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>的状态为字符串。
     * 
     * 
     * @return One of the strings "ONLINE", "OFFLINE", "STARTING" or "STOPPING".
     */
    public String getStateString() ;

    /**
     * Gets the host name used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>使用的主机名。
     * 
     * 
     * @return The host name used by this <CODE>CommunicatorServer</CODE>.
     */
    public String getHost() ;

    /**
     * Gets the port number used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>使用的端口号。
     * 
     * 
     * @return The port number used by this <CODE>CommunicatorServer</CODE>.
     */
    public int getPort() ;

    /**
     * Sets the port number used by this <CODE>CommunicatorServer</CODE>.
     *
     * <p>
     *  设置此<CODE> CommunicatorServer </CODE>使用的端口号。
     * 
     * 
     * @param port The port number used by this <CODE>CommunicatorServer</CODE>.
     *
     * @exception java.lang.IllegalStateException This method has been invoked
     * while the communicator was ONLINE or STARTING.
     */
    public void setPort(int port) throws java.lang.IllegalStateException ;

    /**
     * Gets the protocol being used by this <CODE>CommunicatorServer</CODE>.
     * <p>
     *  获取此<CODE> CommunicatorServer </CODE>正在使用的协议。
     * 
     * @return The protocol as a string.
     */
    public abstract String getProtocol() ;
}
