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
import java.util.Vector;
import java.io.IOException;
import java.net.InetAddress;

// jmx imports
//
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpVarBindList;
import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpTimeticks;
import com.sun.jmx.snmp.SnmpIpAddress;
import com.sun.jmx.snmp.SnmpPduPacket;
import com.sun.jmx.snmp.InetAddressAcl;
import com.sun.jmx.snmp.SnmpPeer;

// SNMP Runtime imports
//
import com.sun.jmx.snmp.agent.SnmpMibAgent;
import com.sun.jmx.snmp.agent.SnmpMibHandler;
import com.sun.jmx.snmp.agent.SnmpUserDataFactory;

/**
 * Exposes the remote management interface of the {@link SnmpAdaptorServer} MBean.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  显示{@link SnmpAdaptorServer} MBean的远程管理接口。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public interface SnmpAdaptorServerMBean extends CommunicatorServerMBean {

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Returns the Ip address based ACL used by this SNMP protocol adaptor.
     * <p>
     *  返回此SNMP协议适配器使用的基于IP地址的ACL。
     * 
     * 
     * @return The <CODE>InetAddressAcl</CODE> implementation.
     *
     * @since 1.5
     */
    public InetAddressAcl getInetAddressAcl();
    /**
     * Returns the port used by this SNMP protocol adaptor for sending traps.
     * By default, port 162 is used.
     *
     * <p>
     *  返回此SNMP协议适配器用于发送陷阱的端口。默认情况下,使用端口162。
     * 
     * 
     * @return The port number for sending SNMP traps.
     */
    public Integer getTrapPort();

    /**
     * Sets the port used by this SNMP protocol adaptor for sending traps.
     *
     * <p>
     *  设置此SNMP协议适配器用于发送陷阱的端口。
     * 
     * 
     * @param port The port number for sending SNMP traps.
     */
    public void setTrapPort(Integer port);

    /**
     * Returns the port used by this SNMP protocol adaptor for sending inform requests.
     * By default, port 162 is used.
     *
     * <p>
     *  返回此SNMP协议适配器用于发送通知请求的端口。默认情况下,使用端口162。
     * 
     * 
     * @return The port number for sending SNMP inform requests.
     */
    public int getInformPort();

    /**
     * Sets the port used by this SNMP protocol adaptor for sending inform requests.
     *
     * <p>
     *  设置此SNMP协议适配器用于发送通知请求的端口。
     * 
     * 
     * @param port The port number for sending SNMP inform requests.
     */
    public void setInformPort(int port);

    /**
     * Gets the number of managers that have been processed by this SNMP protocol adaptor
     * since its creation.
     *
     * <p>
     *  获取此SNMP协议适配器自创建以来已处理的管理器数。
     * 
     * 
     * @return The number of managers handled by this SNMP protocol adaptor
     * since its creation. This counter is not reset by the <CODE>stop</CODE> method.
     */
    public int getServedClientCount();

    /**
     * Gets the number of managers currently being processed by this
     * SNMP protocol adaptor.
     *
     * <p>
     *  获取此SNMP协议适配器当前正在处理的管理器数。
     * 
     * 
     * @return The number of managers currently being processed by this
     * SNMP protocol adaptor.
     */
    public int getActiveClientCount();

    /**
     * Gets the maximum number of managers that this SNMP protocol adaptor can
     * process concurrently.
     *
     * <p>
     *  获取此SNMP协议适配器可以并发处理的最大管理器数。
     * 
     * 
     * @return The maximum number of managers that this SNMP protocol adaptor can
     * process concurrently.
     */
    public int getMaxActiveClientCount();

    /**
     * Sets the maximum number of managers this SNMP protocol adaptor can
     * process concurrently.
     *
     * <p>
     *  设置此SNMP协议适配器可以并发处理的最大管理器数。
     * 
     * 
     * @param c The number of managers.
     *
     * @exception java.lang.IllegalStateException This method has been invoked
     * while the communicator was <CODE>ONLINE</CODE> or <CODE>STARTING</CODE>.
     */
    public void setMaxActiveClientCount(int c) throws java.lang.IllegalStateException;

    /**
     * Returns the protocol of this SNMP protocol adaptor.
     *
     * <p>
     *  返回此SNMP协议适配器的协议。
     * 
     * 
     * @return The string "snmp".
     */
    @Override
    public String getProtocol();

    /**
     * Returns the buffer size of this SNMP protocol adaptor.
     * By default, buffer size 1024 is used.
     *
     * <p>
     *  返回此SNMP协议适配器的缓冲区大小。默认情况下,使用缓冲区大小1024。
     * 
     * 
     * @return The buffer size.
     */
    public Integer getBufferSize();

    /**
     * Sets the buffer size of this SNMP protocol adaptor.
     *
     * <p>
     *  设置此SNMP协议适配器的缓冲区大小。
     * 
     * 
     * @param s The buffer size.
     *
     * @exception java.lang.IllegalStateException This method has been invoked
     * while the communicator was <CODE>ONLINE</CODE> or <CODE>STARTING</CODE>.
     */
    public void setBufferSize(Integer s) throws java.lang.IllegalStateException;

    /**
     * Gets the number of times to try sending an inform request before giving up.
     * <p>
     *  获取在放弃之前尝试发送通知请求的次数。
     * 
     * 
     * @return The maximun number of tries.
     */
    public int getMaxTries();

    /**
     * Changes the maximun number of times to try sending an inform request before giving up.
     * <p>
     *  更改最大尝试次数,尝试在放弃之前发送通知请求。
     * 
     * 
     * @param newMaxTries The maximun number of tries.
     */
    public void setMaxTries(int newMaxTries);

    /**
     * Gets the timeout to wait for an inform response from the manager.
     * <p>
     * 获取超时,等待管理器发出通知响应。
     * 
     * 
     * @return The value of the timeout property.
     */
    public int getTimeout();

    /**
     * Changes the timeout to wait for an inform response from the manager.
     * <p>
     *  更改超时以等待管理器发出通知响应。
     * 
     * 
     * @param newTimeout The timeout (in milliseconds).
     */
    public void setTimeout(int newTimeout);

    /**
     * Returns the message factory of this SNMP protocol adaptor.
     *
     * <p>
     *  返回此SNMP协议适配器的消息工厂。
     * 
     * 
     * @return The factory object.
     */
    public SnmpPduFactory getPduFactory();

    /**
     * Sets the message factory of this SNMP protocol adaptor.
     *
     * <p>
     *  设置此SNMP协议适配器的消息出厂。
     * 
     * 
     * @param factory The factory object (null means the default factory).
     */
    public void setPduFactory(SnmpPduFactory factory);


    /**
     * Set the user-data factory of this SNMP protocol adaptor.
     *
     * <p>
     *  设置此SNMP协议适配器的用户数据出厂设置。
     * 
     * 
     * @param factory The factory object (null means no factory).
     * @see com.sun.jmx.snmp.agent.SnmpUserDataFactory
     */
    public void setUserDataFactory(SnmpUserDataFactory factory);

    /**
     * Get the user-data factory associated with this SNMP protocol adaptor.
     *
     * <p>
     *  获取与此SNMP协议适配器关联的用户数据工厂。
     * 
     * 
     * @return The factory object (null means no factory).
     * @see com.sun.jmx.snmp.agent.SnmpUserDataFactory
     */
    public SnmpUserDataFactory getUserDataFactory();

    /**
     * Returns <CODE>true</CODE> if authentication traps are enabled.
     * <P>
     * When this feature is enabled, the SNMP protocol adaptor sends
     * an <CODE>authenticationFailure</CODE> trap each time an authentication fails.
     * <P>
     * The default behaviour is to send authentication traps.
     *
     * <p>
     *  如果启用了身份验证陷阱,则返回<CODE> true </CODE>。
     * <P>
     *  启用此功能时,SNMP协议适配器会在每次认证失败时发送<CODE> authenticationFailure </CODE>陷阱。
     * <P>
     *  默认行为是发送身份验证陷阱。
     * 
     * 
     * @return <CODE>true</CODE> if authentication traps are enabled, <CODE>false</CODE> otherwise.
     */
    public boolean getAuthTrapEnabled();

    /**
     * Sets the flag indicating if traps need to be sent in case of authentication failure.
     *
     * <p>
     *  设置标志,指​​示在认证失败的情况下是否需要发送陷阱。
     * 
     * 
     * @param enabled Flag indicating if traps need to be sent.
     */
    public void setAuthTrapEnabled(boolean enabled);

    /**
     * Returns <code>true</code> if this SNMP protocol adaptor sends a response in case
     * of authentication failure.
     * <P>
     * When this feature is enabled, the SNMP protocol adaptor sends a response with <CODE>noSuchName</CODE>
     * or <CODE>readOnly</CODE> when the authentication failed. If the flag is disabled, the
     * SNMP protocol adaptor trashes the PDU silently.
     * <P>
     * The default behavior is to send responses.
     *
     * <p>
     *  如果此SNMP协议适配器在认证失败的情况下发送响应,则返回<code> true </code>。
     * <P>
     *  启用此功能时,当认证失败时,SNMP协议适配器会发送一个带有<CODE> noSuchName </CODE>或<CODE> readOnly </CODE>的响应。
     * 如果禁用该标志,SNMP协议适配器将静默地丢弃PDU。
     * <P>
     *  默认行为是发送响应。
     * 
     * 
     * @return <code>true</code> if responses are sent.
     */
    public boolean getAuthRespEnabled();

    /**
     * Sets the flag indicating if responses need to be sent in case of authentication failure.
     *
     * <p>
     *  设置指示在认证失败的情况下是否需要发送响应的标志。
     * 
     * 
     * @param enabled Flag indicating if responses need to be sent.
     */
    public void setAuthRespEnabled(boolean enabled);

    /**
     * Returns the enterprise OID. It is used by {@link #snmpV1Trap snmpV1Trap} to fill
     * the 'enterprise' field of the trap request.
     *
     * <p>
     *  返回企业OID。它由{@link#snmpV1Trap snmpV1Trap}用于填充陷阱请求的"enterprise"字段。
     * 
     * 
     * @return The OID in string format "x.x.x.x".
     */
    public String getEnterpriseOid();

    /**
     * Sets the enterprise OID.
     *
     * <p>
     *  设置企业OID。
     * 
     * 
     * @param oid The OID in string format "x.x.x.x".
     *
     * @exception IllegalArgumentException The string format is incorrect
     */
    public void setEnterpriseOid(String oid) throws IllegalArgumentException;

    /**
     * Returns the names of the MIBs available in this SNMP protocol adaptor.
     *
     * <p>
     *  返回此SNMP协议适配器中可用的MIB的名称。
     * 
     * 
     * @return An array of MIB names.
     */
    public String[] getMibs();

    // GETTERS FOR SNMP GROUP (MIBII)
    //-------------------------------

    /**
     * Returns the <CODE>snmpOutTraps</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutTraps </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutTraps</CODE> value.
     */
    public Long getSnmpOutTraps();

    /**
     * Returns the <CODE>snmpOutGetResponses</CODE> value defined in MIB-II.
     *
     * <p>
     * 返回在MIB-II中定义的<CODE> snmpOutGetResponses </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutGetResponses</CODE> value.
     */
    public Long getSnmpOutGetResponses();

    /**
     * Returns the <CODE>snmpOutGenErrs</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutGenErrs </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutGenErrs</CODE> value.
     */
    public Long getSnmpOutGenErrs();

    /**
     * Returns the <CODE>snmpOutBadValues</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutBadValues </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutBadValues</CODE> value.
     */
    public Long getSnmpOutBadValues();

    /**
     * Returns the <CODE>snmpOutNoSuchNames</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutNoSuchNames </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutNoSuchNames</CODE> value.
     */
    public Long getSnmpOutNoSuchNames();

    /**
     * Returns the <CODE>snmpOutTooBigs</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutTooBigs </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutTooBigs</CODE> value.
     */
    public Long getSnmpOutTooBigs();

    /**
     * Returns the <CODE>snmpInASNParseErrs</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInASNParseErrs </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInASNParseErrs</CODE> value.
     */
    public Long getSnmpInASNParseErrs();

    /**
     * Returns the <CODE>snmpInBadCommunityUses</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInBadCommunityUses </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInBadCommunityUses</CODE> value.
     */
    public Long getSnmpInBadCommunityUses();

    /**
     * Returns the <CODE>snmpInBadCommunityNames</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInBadCommunityNames </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInBadCommunityNames</CODE> value.
     */
    public Long getSnmpInBadCommunityNames();

    /**
     * Returns the <CODE>snmpInBadVersions</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInBadVersions </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInBadVersions</CODE> value.
     */
    public Long getSnmpInBadVersions();

    /**
     * Returns the <CODE>snmpOutPkts</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpOutPkts </CODE>值。
     * 
     * 
     * @return The <CODE>snmpOutPkts</CODE> value.
     */
    public Long getSnmpOutPkts();

    /**
     * Returns the <CODE>snmpInPkts</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInPkts </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInPkts</CODE> value.
     */
    public Long getSnmpInPkts();

    /**
     * Returns the <CODE>snmpInGetRequests</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInGetRequests </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInGetRequests</CODE> value.
     */
    public Long getSnmpInGetRequests();

    /**
     * Returns the <CODE>snmpInGetNexts</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInGetNexts </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInGetNexts</CODE> value.
     */
    public Long getSnmpInGetNexts();

    /**
     * Returns the <CODE>snmpInSetRequests</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInSetRequests </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInSetRequests</CODE> value.
     */
    public Long getSnmpInSetRequests();

    /**
     * Returns the <CODE>snmpInTotalSetVars</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInTotalSetVars </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInTotalSetVars</CODE> value.
     */
    public Long getSnmpInTotalSetVars();

    /**
     * Returns the <CODE>snmpInTotalReqVars</CODE> value defined in MIB-II.
     *
     * <p>
     *  返回在MIB-II中定义的<CODE> snmpInTotalReqVars </CODE>值。
     * 
     * 
     * @return The <CODE>snmpInTotalReqVars</CODE> value.
     */
    public Long getSnmpInTotalReqVars();

    /**
     * Returns the <CODE>snmpSilentDrops</CODE> value defined in rfc 1907 NMPv2-MIB .
     *
     * <p>
     *  返回在rfc 1907 NMPv2-MIB中定义的<CODE> snmpSilentDrops </CODE>值。
     * 
     * 
     * @return The <CODE>snmpSilentDrops</CODE> value.
     *
     * @since 1.5
     */
    public Long getSnmpSilentDrops();

    /**
     * Returns the <CODE>snmpProxyDrops</CODE> value defined in rfc 1907 NMPv2-MIB .
     *
     * <p>
     *  返回在rfc 1907 NMPv2-MIB中定义的<CODE> snmpProxyDrops </CODE>值。
     * 
     * 
     * @return The <CODE>snmpProxyDrops</CODE> value.
     *
     * @since 1.5
     */
    public Long getSnmpProxyDrops();

    // PUBLIC METHODS
    //---------------

    /**
     * Adds a new MIB in the SNMP MIB handler.
     * This method is called automatically by {@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptor(SnmpMibHandler)}
     * and {@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptorName(ObjectName)}
     * and should not be called directly.
     *
     * <p>
     * 在SNMP MIB处理程序中添加一个新的MIB。
     * 此方法由{@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptor(SnmpMibHandler)}和{@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptorName(ObjectName)}
     * 自动调用,且不应直接调用。
     * 在SNMP MIB处理程序中添加一个新的MIB。
     * 
     * 
     * @param mib The MIB to add.
     *
     * @return A reference to the SNMP MIB handler.
     *
     * @exception IllegalArgumentException If the parameter is null.
     */
    public SnmpMibHandler addMib(SnmpMibAgent mib) throws IllegalArgumentException;

    /**
     * Adds a new MIB in the SNMP MIB handler.
     *
     * <p>
     *  在SNMP MIB处理程序中添加一个新的MIB。
     * 
     * 
     * @param mib The MIB to add.
     * @param oids The set of OIDs this agent implements.
     *
     * @return A reference to the SNMP MIB handler.
     *
     * @exception IllegalArgumentException If the parameter is null.
     *
     * @since 1.5
     */
    public SnmpMibHandler addMib(SnmpMibAgent mib, SnmpOid[] oids) throws IllegalArgumentException;

    /**
     * Removes the specified MIB from the SNMP protocol adaptor.
     * This method is called automatically by {@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptor(SnmpMibHandler)}
     * and {@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptorName(ObjectName)}
     * and should not be called directly.
     *
     * <p>
     *  从SNMP协议适配器中删除指定的MIB。
     * 此方法由{@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptor(SnmpMibHandler)}和{@link com.sun.jmx.snmp.agent.SnmpMibAgent#setSnmpAdaptorName(ObjectName)}
     * 自动调用,且不应直接调用。
     *  从SNMP协议适配器中删除指定的MIB。
     * 
     * 
     * @param mib The MIB to be removed.
     *
     * @return <code>true</code> if the specified <CODE>mib</CODE> was a MIB included in the SNMP MIB handler,
     * <code>false</code> otherwise.
     */
    public boolean removeMib(SnmpMibAgent mib);

    /**
     * Sends a trap using SNMP V1 trap format.
     * <BR>The trap is sent to each destination defined in the ACL file (if available).
     * If no ACL file or no destinations are available, the trap is sent to the local host.
     *
     * <p>
     *  使用SNMP V1陷阱格式发送陷阱。 <BR>陷阱发送到ACL文件中定义的每个目标(如果可用)。如果没有ACL文件或没有目标可用,则将陷阱发送到本地主机。
     * 
     * 
     * @param generic The generic number of the trap.
     * @param specific The specific number of the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public void snmpV1Trap(int generic, int specific, SnmpVarBindList varBindList) throws IOException, SnmpStatusException;


    /**
     * Sends a trap using SNMP V1 trap format.
     * <BR>The trap is sent to the specified <CODE>InetAddress</CODE> destination
     * using the specified community string (and the ACL file is not used).
     *
     * <p>
     *  使用SNMP V1陷阱格式发送陷阱。 <BR>使用指定的社区字符串将陷阱发送到指定的<CODE> InetAddress </CODE>目标(并且不使用ACL文件)。
     * 
     * 
     * @param address The <CODE>InetAddress</CODE> destination of the trap.
     * @param cs The community string to be used for the trap.
     * @param generic The generic number of the trap.
     * @param specific The specific number of the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public void snmpV1Trap(InetAddress address, String cs, int generic, int specific, SnmpVarBindList varBindList)
        throws IOException, SnmpStatusException;


    /**
     * Sends a trap using SNMP V1 trap format.
     * <BR>The trap is sent to the specified <CODE>SnmpPeer</CODE> destination.
     * The community string used is the one located in the <CODE>SnmpPeer</CODE> parameters (<CODE>SnmpParameters.getRdCommunity() </CODE>).
     *
     * <p>
     *  使用SNMP V1陷阱格式发送陷阱。 <BR>陷阱发送到指定的<CODE> SnmpPeer </CODE>目标。
     * 使用的团体字符串是位于<CODE> SnmpPeer </CODE>参数(<CODE> SnmpParameters.getRdCommunity()</CODE>)中的团体字符串。
     * 
     * 
     * @param peer The <CODE>SnmpPeer</CODE> destination of the trap.
     * @param agentAddr The agent address to be used for the trap.
     * @param enterpOid The enterprise OID to be used for the trap.
     * @param generic The generic number of the trap.
     * @param specific The specific number of the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     * @param time The time stamp (overwrite the current time).
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     *
     * @since 1.5
     */
    public void snmpV1Trap(SnmpPeer peer,
                           SnmpIpAddress agentAddr,
                           SnmpOid enterpOid,
                           int generic,
                           int specific,
                           SnmpVarBindList varBindList,
                           SnmpTimeticks time) throws IOException, SnmpStatusException;

    /**
     * Sends a trap using SNMP V2 trap format.
     * <BR>The trap is sent to the specified <CODE>SnmpPeer</CODE> destination.
     * <BR>The community string used is the one located in the <CODE>SnmpPeer</CODE> parameters (<CODE>SnmpParameters.getRdCommunity() </CODE>).
     * <BR>The variable list included in the outgoing trap is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with the value specified by <CODE>time</CODE>
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     *
     * <p>
     * 使用SNMP V2陷阱格式发送陷阱。 <BR>陷阱发送到指定的<CODE> SnmpPeer </CODE>目标。
     *  <BR>所使用的社区字符串是位于<CODE> SnmpPeer </CODE>参数(<CODE> SnmpParameters.getRdCommunity()</CODE>)中的字符串。
     *  <BR>包含在输出陷阱中的变量列表由以下项目组成：。
     * <UL>
     *  <CODE>与<CODE>指定的值</CODE> <LI> <CODE> sysUpTime.0 </CODE> CODE> varBindList </CODE>中的所有(oid,值)</CODE>
     * 。
     * </UL>
     * 
     * 
     * @param peer The <CODE>SnmpPeer</CODE> destination of the trap.
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     * @param time The time stamp (overwrite the current time).
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     *
     * @since 1.5
     */
    public void snmpV2Trap(SnmpPeer peer,
                           SnmpOid trapOid,
                           SnmpVarBindList varBindList,
                           SnmpTimeticks time) throws IOException, SnmpStatusException;

    /**
     * Sends a trap using SNMP V2 trap format.
     * <BR>The trap is sent to each destination defined in the ACL file (if available).
     * If no ACL file or no destinations are available, the trap is sent to the local host.
     * <BR>The variable list included in the outgoing trap is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with its current value
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     *
     * <p>
     *  使用SNMP V2陷阱格式发送陷阱。 <BR>陷阱发送到ACL文件中定义的每个目标(如果可用)。如果没有ACL文件或没有目标可用,则将陷阱发送到本地主机。
     *  <BR>包含在输出陷阱中的变量列表由以下项目组成：。
     * <UL>
     *  <CODE>与<CODE> trapOid </CODE> <LI> <CODE> all指定的值的<LI> <CODE>及其当前值<LI> <CODE> selenpTrapOid.0 </CODE>
     * 来自指定的<CODE> varBindList </CODE>的(oid,values)</CODE>。
     * </UL>
     * 
     * 
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public void snmpV2Trap(SnmpOid trapOid, SnmpVarBindList varBindList) throws IOException, SnmpStatusException;


    /**
     * Sends a trap using SNMP V2 trap format.
     * <BR>The trap is sent to the specified <CODE>InetAddress</CODE> destination
     * using the specified community string (and the ACL file is not used).
     * <BR>The variable list included in the outgoing trap is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with its current value
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     *
     * <p>
     *  使用SNMP V2陷阱格式发送陷阱。 <BR>使用指定的社区字符串将陷阱发送到指定的<CODE> InetAddress </CODE>目标(并且不使用ACL文件)。
     *  <BR>包含在输出陷阱中的变量列表由以下项目组成：。
     * <UL>
     * <CODE>与<CODE> trapOid </CODE> <LI> <CODE> all指定的值的<LI> <CODE>及其当前值<LI> <CODE> selenpTrapOid.0 </CODE>
     * 来自指定的<CODE> varBindList </CODE>的(oid,values)</CODE>。
     * </UL>
     * 
     * 
     * @param address The <CODE>InetAddress</CODE> destination of the trap.
     * @param cs The community string to be used for the trap.
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public void snmpV2Trap(InetAddress address, String cs, SnmpOid trapOid, SnmpVarBindList varBindList)
        throws IOException, SnmpStatusException;

    /**
     * Send the specified trap PDU to the passed <CODE>InetAddress</CODE>.
     * <p>
     *  将指定的陷阱PDU发送到传递的<CODE> InetAddress </CODE>。
     * 
     * 
     * @param address The destination address.
     * @param pdu The pdu to send.
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     *
     * @since 1.5
     */
    public void snmpPduTrap(InetAddress address, SnmpPduPacket pdu)
        throws IOException, SnmpStatusException;
    /**
     * Send the specified trap PDU to the passed <CODE>SnmpPeer</CODE>.
     * <p>
     *  将指定的陷阱PDU发送到传递的<CODE> SnmpPeer </CODE>。
     * 
     * 
     * @param peer The destination peer. The Read community string is used of <CODE>SnmpParameters</CODE> is used as the trap community string.
     * @param pdu The pdu to send.
     * @exception IOException An I/O error occurred while sending the trap.
     * @exception SnmpStatusException If the trap exceeds the limit defined by <CODE>bufferSize</CODE>.
     * @since 1.5
     */
    public void snmpPduTrap(SnmpPeer peer,
                            SnmpPduPacket pdu)
        throws IOException, SnmpStatusException;

    /**
     * Sends an inform using SNMP V2 inform request format.
     * <BR>The inform request is sent to each destination defined in the ACL file (if available).
     * If no ACL file or no destinations are available, the inform request is sent to the local host.
     * <BR>The variable list included in the outgoing inform request is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with its current value
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     * To send an inform request, the SNMP adaptor server must be active.
     *
     * <p>
     *  使用SNMP V2通知请求格式发送通知。 <BR>通知请求发送到ACL文件中定义的每个目标(如果可用)。如果没有ACL文件或没有目标可用,则通知请求将发送到本地主机。
     *  <BR>传出通知请求中包含的变量列表由以下项目组成：。
     * <UL>
     *  <CODE>与<CODE> trapOid </CODE> <LI> <CODE> all指定的值的<LI> <CODE>及其当前值<LI> <CODE> selenpTrapOid.0 </CODE>
     * 来自指定的<CODE> varBindList </CODE>的(oid,values)</CODE>。
     * </UL>
     *  要发送通知请求,SNMP适配器服务器必须处于活动状态。
     * 
     * 
     * @param cb The callback that is invoked when a request is complete.
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @return A vector of {@link com.sun.jmx.snmp.daemon.SnmpInformRequest} objects.
     * <P>If there is no destination host for this inform request, the returned vector will be empty.
     *
     * @exception IllegalStateException  This method has been invoked while the SNMP adaptor server was not active.
     * @exception IOException An I/O error occurred while sending the inform request.
     * @exception SnmpStatusException If the inform request exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public Vector<?> snmpInformRequest(SnmpInformHandler cb, SnmpOid trapOid,
            SnmpVarBindList varBindList)
        throws IllegalStateException, IOException, SnmpStatusException;

    /**
     * Sends an inform using SNMP V2 inform request format.
     * <BR>The inform is sent to the specified <CODE>InetAddress</CODE> destination
     * using the specified community string.
     * <BR>The variable list included in the outgoing inform request is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with its current value
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     * To send an inform request, the SNMP adaptor server must be active.
     *
     * <p>
     *  使用SNMP V2通知请求格式发送通知。 <BR>通知使用指定的社区字符串发送到指定的<CODE> InetAddress </CODE>目标。
     *  <BR>传出通知请求中包含的变量列表由以下项目组成：。
     * <UL>
     *  <CODE>与<CODE> trapOid </CODE> <LI> <CODE> all指定的值的<LI> <CODE>及其当前值<LI> <CODE> selenpTrapOid.0 </CODE>
     * 来自指定的<CODE> varBindList </CODE>的(oid,values)</CODE>。
     * </UL>
     * 要发送通知请求,SNMP适配器服务器必须处于活动状态。
     * 
     * 
     * @param address The <CODE>InetAddress</CODE> destination for this inform request.
     * @param cs The community string to be used for the inform request.
     * @param cb The callback that is invoked when a request is complete.
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @return The inform request object.
     *
     * @exception IllegalStateException  This method has been invoked while the SNMP adaptor server was not active.
     * @exception IOException An I/O error occurred while sending the inform request.
     * @exception SnmpStatusException If the inform request exceeds the limit defined by <CODE>bufferSize</CODE>.
     */
    public SnmpInformRequest snmpInformRequest(InetAddress address, String cs, SnmpInformHandler cb,
                                               SnmpOid trapOid, SnmpVarBindList varBindList)
        throws IllegalStateException, IOException, SnmpStatusException;


    /**
     * Sends an inform using SNMP V2 inform request format.
     * <BR>The inform is sent to the specified <CODE>SnmpPeer</CODE> destination.
     * <BR> The community string used is the one located in the <CODE>SnmpPeer</CODE> parameters (<CODE>SnmpParameters.getInformCommunity() </CODE>).
     * <BR>The variable list included in the outgoing inform is composed of the following items:
     * <UL>
     * <LI><CODE>sysUpTime.0</CODE> with its current value
     * <LI><CODE>snmpTrapOid.0</CODE> with the value specified by <CODE>trapOid</CODE>
     * <LI><CODE>all the (oid,values)</CODE> from the specified <CODE>varBindList</CODE>
     * </UL>
     * To send an inform request, the SNMP adaptor server must be active.
     *
     * <p>
     *  使用SNMP V2通知请求格式发送通知。 <BR>通知发送到指定的<CODE> SnmpPeer </CODE>目标。
     *  <BR>使用的社区字符串是位于<CODE> SnmpPeer </CODE>参数(<CODE> SnmpParameters.getInformCommunity()</CODE>)中的字符串。
     *  <BR>传出通知中包含的变量列表由以下项目组成：。
     * <UL>
     *  <CODE>与<CODE> trapOid </CODE> <LI> <CODE> all指定的值的<LI> <CODE>及其当前值<LI> <CODE> snmpTrapOid.0 </CODE>来
     * 
     * @param peer The <CODE>SnmpPeer</CODE> destination for this inform request.
     * @param cb The callback that is invoked when a request is complete.
     * @param trapOid The OID identifying the trap.
     * @param varBindList A list of <CODE>SnmpVarBind</CODE> instances or null.
     *
     * @return The inform request object.
     *
     * @exception IllegalStateException  This method has been invoked while the SNMP adaptor server was not active.
     * @exception IOException An I/O error occurred while sending the inform request.
     * @exception SnmpStatusException If the inform request exceeds the limit defined by <CODE>bufferSize</CODE>.
     *
     * @since 1.5
     */
    public SnmpInformRequest snmpInformRequest(SnmpPeer peer,
                                               SnmpInformHandler cb,
                                               SnmpOid trapOid,
                                               SnmpVarBindList varBindList) throws IllegalStateException, IOException, SnmpStatusException;
}
