/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpPduPacket;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpStatusException;

/**
 * This interface is provided to enable fine customization of the SNMP
 * agent behaviour.
 *
 * <p>You will not need to implement this interface except if your agent
 * needs extra customization requiring some contextual information.</p>
 *
 * <p>If an SnmpUserDataFactory is set on the SnmpAdaptorServer, then a new
 * object containing user-data will be allocated through this factory
 * for each incoming request. This object will be passed along to
 * the SnmpMibAgent within SnmpMibRequest objects. By default, no
 * SnmpUserDataFactory is set on the SnmpAdaptorServer, and the contextual
 * object passed to SnmpMibAgent is null.</p>
 *
 * <p>You can use this feature to obtain on contextual information
 * (such as community string etc...) or to implement a caching
 * mechanism, or for whatever purpose might be required by your specific
 * agent implementation.</p>
 *
 * <p>The sequence <code>allocateUserData() / releaseUserData()</code> can
 * also be used to implement a caching mechanism:
 * <ul>
 * <li><code>allocateUserData()</code> could be used to allocate
 *         some cache space,</li>
 * <li>and <code>releaseUserData()</code> could be used to flush it.</li>
 * </ul></p>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  提供此接口以实现SNMP代理行为的精细定制。
 * 
 *  <p>您不需要实现此接口,除非您的代理需要额外的自定义,需要一些上下文信息。</p>
 * 
 *  <p>如果在SnmpAdaptorServer上设置了SnmpUserDataFactory,则包含用户数据的新对象将通过此工厂为每个传入请求分配。
 * 此对象将被传递到SnmpMibRequest对象中的SnmpMibAgent。
 * 默认情况下,SnmpAdaptorServer上没有设置SnmpUserDataFactory,传递给SnmpMibAgent的上下文对象为null。</p>。
 * 
 *  <p>您可以使用此功能获取上下文信息(例如社区字符串等...)或实现缓存机制,或者您的特定代理实现可能需要的任何目的。</p>
 * 
 *  <p>序列<code> allocateUserData()/ releaseUserData()</code>也可以用于实现缓存机制：
 * <ul>
 *  <li> <code> allocateUserData()</code>可用于分配一些缓存空间,</li> <li>和<code> releaseUserData()</code>可用于刷新。
 *  > </ul> </p>。
 * 
 * 
 * @see com.sun.jmx.snmp.agent.SnmpMibRequest
 * @see com.sun.jmx.snmp.agent.SnmpMibAgent
 * @see com.sun.jmx.snmp.daemon.SnmpAdaptorServer
 *
 **/
public interface SnmpUserDataFactory {
    /**
     * Called by the <CODE>SnmpAdaptorServer</CODE> adaptor.
     * Allocate a contextual object containing some user data. This method
     * is called once for each incoming SNMP request. The scope
     * of this object will be the whole request. Since the request can be
     * handled in several threads, the user should make sure that this
     * object can be accessed in a thread-safe manner. The SNMP framework
     * will never access this object directly - it will simply pass
     * it to the <code>SnmpMibAgent</code> within
     * <code>SnmpMibRequest</code> objects - from where it can be retrieved
     * through the {@link com.sun.jmx.snmp.agent.SnmpMibRequest#getUserData() getUserData()} accessor.
     * <code>null</code> is considered to be a valid return value.
     *
     * This method is called just after the SnmpPduPacket has been
     * decoded.
     *
     * <p>
     *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
     * 
     * 
     * @param requestPdu The SnmpPduPacket received from the SNMP manager.
     *        <b>This parameter is owned by the SNMP framework and must be
     *        considered as transient.</b> If you wish to keep some of its
     *        content after this method returns (by storing it in the
     *        returned object for instance) you should clone that
     *        information.
     *
     * @return A newly allocated user-data contextual object, or
     *         <code>null</code>
     * @exception SnmpStatusException If an SnmpStatusException is thrown,
     *            the request will be aborted.
     *
     * @since 1.5
     **/
    public Object allocateUserData(SnmpPdu requestPdu)
        throws SnmpStatusException;

    /**
     * Called by the <CODE>SnmpAdaptorServer</CODE> adaptor.
     * Release a previously allocated contextual object containing user-data.
     * This method is called just before the responsePdu is sent back to the
     * manager. It gives the user a chance to alter the responsePdu packet
     * before it is encoded, and to free any resources that might have
     * been allocated when creating the contextual object.
     *
     * <p>
     * 由<CODE> SnmpAdaptorServer </CODE>适配器调用。分配包含某些用户数据的上下文对象。对于每个传入的SNMP请求,将调用此方法一次。此对象的范围将是整个请求。
     * 由于请求可以在几个线程中处理,用户应该确保可以以线程安全的方式访问此对象。
     *  SNMP框架将永远不会直接访问此对象 - 它将简单地将它传递给<code> SnmpMibRequest </code>对象中的<code> SnmpMibAgent </code>  - 从中​​可
     * 以通过{@link com.sun .jmx.snmp.agent.SnmpMibRequest#getUserData()getUserData()}存取器。
     * 由于请求可以在几个线程中处理,用户应该确保可以以线程安全的方式访问此对象。 <code> null </code>被认为是有效的返回值。
     * 
     * 
     * @param userData The contextual object being released.
     * @param responsePdu The SnmpPduPacket that will be sent back to the
     *        SNMP manager.
     *        <b>This parameter is owned by the SNMP framework and must be
     *        considered as transient.</b> If you wish to keep some of its
     *        content after this method returns you should clone that
     *        information.
     *
     * @exception SnmpStatusException If an SnmpStatusException is thrown,
     *            the responsePdu is dropped and nothing is returned to
     *            to the manager.
     *
     * @since 1.5
     **/
    public void releaseUserData(Object userData, SnmpPdu responsePdu)
        throws SnmpStatusException;
}
