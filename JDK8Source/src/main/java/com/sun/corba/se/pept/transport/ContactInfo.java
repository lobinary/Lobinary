/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.pept.transport;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
import com.sun.corba.se.pept.transport.ConnectionCache;

/**
 * <p>The <b><em>primary</em></b> PEPt client-side plug-in point and enabler
 * for <b><em>altenate encodings, protocols and transports</em></b>.</p>
 *
 * <p><code>ContactInfo</code> is a <em>factory</em> for client-side
 * artifacts used
 * to construct and send a message (and possibly receive and process a
 * response).</p>
 *
 * <p>
 *  <p> <b> <em> </em> </b> </b> </b>的</em>主要插件点和启用器<b> <em> 。</p>
 * 
 *  <p> <code> ContactInfo </em>是用于构造和发送消息(以及可能接收和处理响应)的客户端工件的<em>工厂</em>。</p>
 * 
 * 
 * @author Harold Carr
 */
public interface ContactInfo
{
    /**
     * The {@link com.sun.corba.se.pept.broker.Broker Broker} associated
     * with an invocation.
     *
     * <p>
     *  与调用相关联的{@link com.sun.corba.se.pept.broker.Broker Broker}。
     * 
     * 
     * @return {@link com.sun.corba.se.pept.broker.Broker Broker}
     */
    public Broker getBroker();

    /**
     * The parent
     * {@link com.sun.corba.se.pept.broker.ContactInfoList ContactInfoList}
     * for this <code>ContactInfo</code>.
     *
     * <p>
     *  这个<code> ContactInfo </code>的父级{@link com.sun.corba.se.pept.broker.ContactInfoList ContactInfoList}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.broker.ContactInfoList ContactInfoList}
     */
    public ContactInfoList getContactInfoList();

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.protocol.ClientRequestDispatcher
     * ClientRequestDispatcher}
     * used to handle the specific <em>protocol</em> represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     *  用于获取用于处理由此<code> ContactInfo </code>表示的特定<em>协议</em>的{@link com.sun.corba.se.pept.protocol.ClientRequestDispatcher ClientRequestDispatcher}
     * 。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.ClientRequestDispatcher
     * ClientRequestDispatcher} */
    public ClientRequestDispatcher getClientRequestDispatcher();

    /**
     * Used to determine if a
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * will be present in an invocation.
     *
     * For example, it may be
     * <code>false</code> in the case of shared-memory
     * <code>Input/OutputObjects</code>.
     *
     * <p>
     *  用于确定是否在调用中存在{@link com.sun.corba.se.pept.transport.Connection Connection}。
     * 
     *  例如,在共享存储器<code> Input / OutputObjects </code>的情况下,可以是<code> false </code>。
     * 
     * 
     * @return <code>true</code> if a
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * will be used for an invocation.
     */
    public boolean isConnectionBased();

    /**
     * Used to determine if the
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * used for a request should be cached.
     *
     * If <code>true</code> then PEPt will attempt to reuse an existing
     * {@link com.sun.corba.se.pept.transport.Connection Connection}. If
     * one is not found it will create a new one and cache it for future use.
     *
     *
     * <p>
     *  用于确定是否应缓存用于请求的{@link com.sun.corba.se.pept.transport.Connection Connection}。
     * 
     *  如果<code> true </code>,PEPt将尝试重用现有的{@link com.sun.corba.se.pept.transport.Connection Connection}。
     * 如果没有找到它,它会创建一个新的缓存,以备将来使用。
     * 
     * 
     * @return <code>true</code> if
     * {@link com.sun.corba.se.pept.transport.Connection Connection}s
     * created by this <code>ContactInfo</code> should be cached.
     */
    public boolean shouldCacheConnection();

    /**
     * PEPt uses separate caches for each type of <code>ContactInfo</code>
     * as given by <code>getConnectionCacheType</code>.
     *
     * <p>
     * PEPt对<code> getConnectionCacheType </code>给出的每个类型的<code> ContactInfo </code>使用单独的缓存。
     * 
     * 
     * @return {@link java.lang.String}
     */
    public String getConnectionCacheType();

    /**
     * Set the
     * {@link com.sun.corba.se.pept.transport.Outbound.ConnectionCache OutboundConnectionCache}
     * to be used by this <code>ContactInfo</code>.
     *
     * PEPt uses separate caches for each type of <code>ContactInfo</code>
     * as given by {@link #getConnectionCacheType}.
     * {@link #setConnectionCache} and {@link #getConnectionCache} support
     * an optimzation to avoid hashing to find that cache.
     *
     * <p>
     *  设置此<code> ContactInfo </code>使用的{@link com.sun.corba.se.pept.transport.Outbound.ConnectionCache OutboundConnectionCache}
     * 。
     * 
     *  PEPt对由{@link #getConnectionCacheType}给出的每个类型的<code> ContactInfo </code>使用单独的缓存。
     *  {@link #setConnectionCache}和{@link #getConnectionCache}支持优化,以避免散列找到该缓存。
     * 
     * 
     * @param connectionCache.
     */
    public void setConnectionCache(OutboundConnectionCache connectionCache);

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.Outbound.ConnectionCache OutboundConnectionCache}
     * used by this <code>ContactInfo</code>
     *
     * PEPt uses separate caches for each type of <code>ContactInfo</code>
     * as given by {@link #getConnectionCacheType}.
     * {@link #setConnectionCache} and {@link #getConnectionCache} support
     * an optimzation to avoid hashing to find that cache.
     *
     * <p>
     *  获取此<code> ContactInfo </code>使用的{@link com.sun.corba.se.pept.transport.Outbound.ConnectionCache OutboundConnectionCache}
     * 。
     * 
     *  PEPt对由{@link #getConnectionCacheType}给出的每个类型的<code> ContactInfo </code>使用单独的缓存。
     *  {@link #setConnectionCache}和{@link #getConnectionCache}支持优化,以避免散列找到该缓存。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.ConnectionCache ConnectionCache}
     */
    public OutboundConnectionCache getConnectionCache();

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * to send and receive messages on the specific <em>transport</em>
     * represented by this <code>ContactInfo</code>.
     *
     * <p>
     *  用于获取{@link com.sun.corba.se.pept.transport.Connection Connection}在此<code> ContactInfo </code>表示的特定<em>
     * 传输</em>上发送和接收邮件。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     */
    public Connection createConnection();

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     * to hold internal data for a message to be sent using the specific
     * encoding, protocol, transport combination represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     *  用于获取一个{@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}来保存要使用特定编码,协议,传输组合发送的消息的
     * 内部数据,由此<code> ContactInfo <代码>。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     */
    public MessageMediator createMessageMediator(Broker broker,
                                                 ContactInfo contactInfo,
                                                 Connection connection,
                                                 String methodName,
                                                 boolean isOneWay);

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     * to hold internal data for a message received using the specific
     * encoding, protocol, transport combination represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     * 用于获取{@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}以保存使用由此<code> ContactInfo </code>
     * 表示的特定编码,协议,传输组合接收的消息的内部数据。
     *  。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     */
    public MessageMediator createMessageMediator(Broker broker,
                                                 Connection connection);

    /**
     * Used to finish creating a
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     * with internal data for a message received using the specific
     * encoding, protocol, transport combination represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     *  用于完成使用由此<code> ContactInfo </code>表示的特定编码,协议,传输组合接收的消息的内部数据创建{@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     *  。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     */
    public MessageMediator finishCreatingMessageMediator(Broker broker,
                                                         Connection connection,
                                                         MessageMediator messageMediator);

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * for the specific <em>encoding</em> represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     *  用于为此<code> ContactInfo </code>表示的特定<em>编码</em>获取{@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * 。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     */
    public InputObject createInputObject(Broker broker,
                                         MessageMediator messageMediator);

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * for the specific <em>encoding</em> represented by this
     * <code>ContactInfo</code>.
     *
     * <p>
     *  用于为由此<code> ContactInfo </code>表示的特定<em>编码</em>获取{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * 。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     */
    public OutputObject createOutputObject(MessageMediator messageMediator);

    /**
     * Used to lookup artifacts associated with this <code>ContactInfo</code>.
     *
     * <p>
     * 
     * @return the hash value.
     */
    public int hashCode();
}

// End of file.
