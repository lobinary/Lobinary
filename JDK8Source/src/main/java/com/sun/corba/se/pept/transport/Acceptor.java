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
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.transport.Connection;
import com.sun.corba.se.pept.transport.EventHandler;

/**
 * <p>The <b><em>primary</em></b> PEPt server-side plug-in point and enabler
 * for <b><em>altenate encodings, protocols and transports</em></b>.</p>
 *
 * <p><code>Acceptor</code> is a <em>factory</em> for client-side
 * artifacts used to receive a message (and possibly send a response).</p>
 *
 * <p>
 *  <p> <b> <em> </em> </em> </b> </b> PEPt服务器端插件点和启动器<b> <em> 。</p>
 * 
 *  <p> <code> Acceptor </em>是用于接收邮件(可能发送响应)的客户端工件的<em>工厂</em>。</p>
 * 
 * 
 * @author Harold Carr
 */
public interface Acceptor
{
    /**
     * Used to initialize an <code>Acceptor</code>.
     *
     * For example, initialization may mean to create a
     * {@link java.nio.channels.ServerSocketChannel ServerSocketChannel}.
     *
     * Note: this must be prepared to be be called multiple times.
     *
     * <p>
     *  用于初始化<code> Acceptor </code>。
     * 
     *  例如,初始化可能意味着创建一个{@link java.nio.channels.ServerSocketChannel ServerSocketChannel}。
     * 
     *  注意：这必须准备好多次调用。
     * 
     * 
     * @return <code>true</code> when it performs initializatin
     * actions (typically the first call.
     */
    public boolean initialize();

    /**
     * Used to determine if an <code>Acceptor</code> has been initialized.
     *
     * <p>
     *  用于确定<code> Acceptor </code>是否已初始化。
     * 
     * 
     * @return <code>true</code. if the <code>Acceptor</code> has been
     * initialized.
     */
    public boolean initialized();

    /**
     * PEPt uses separate caches for each type of <code>Acceptor</code>
     * as given by <code>getConnectionCacheType</code>.
     *
     * <p>
     *  PEPt对<code> getConnectionCacheType </code>给出的每种类型的<code> Acceptor </code>使用单独的缓存。
     * 
     * 
     * @return {@link java.lang.String}
     */
    public String getConnectionCacheType();

    /**
     * Set the
     * {@link com.sun.corba.se.pept.transport.Inbound.ConnectionCache InboundConnectionCache}
     * to be used by this <code>Acceptor</code>.
     *
     * PEPt uses separate caches for each type of <code>Acceptor</code>
     * as given by {@link #getConnectionCacheType}.
     * {@link #setConnectionCache} and {@link #getConnectionCache} support
     * an optimzation to avoid hashing to find that cache.
     *
     * <p>
     *  设置要由此<code> Acceptor </code>使用的{@link com.sun.corba.se.pept.transport.Inbound.ConnectionCache InboundConnectionCache}
     * 。
     * 
     *  PEPt对由{@link #getConnectionCacheType}给出的每种类型的<code> Acceptor </code>使用单独的缓存。
     *  {@link #setConnectionCache}和{@link #getConnectionCache}支持优化,以避免散列找到该缓存。
     * 
     * 
     * @param connectionCache.
     */
    public void setConnectionCache(InboundConnectionCache connectionCache);

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.Inbound.ConnectionCache InboundConnectionCache}
     * used by this <code>Acceptor</code>
     *
     * PEPt uses separate caches for each type of <code>Acceptor</code>
     * as given by {@link #getConnectionCacheType}.
     * {@link #setConnectionCache} and {@link #getConnectionCache} support
     * an optimzation to avoid hashing to find that cache.
     *
     * <p>
     *  获取此<code> Acceptor </code>使用的{@link com.sun.corba.se.pept.transport.Inbound.ConnectionCache InboundConnectionCache}
     * 。
     * 
     * PEPt对由{@link #getConnectionCacheType}给出的每种类型的<code> Acceptor </code>使用单独的缓存。
     *  {@link #setConnectionCache}和{@link #getConnectionCache}支持优化,以避免散列找到该缓存。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.ConnectionCache ConnectionCache}
     */
    public InboundConnectionCache getConnectionCache();

    /**
     * Used to determine if the <code>Acceptor</code> should register
     * with
     * {@link com.sun.corba.se.pept.transport.Selector Selector}
     * to handle accept events.
     *
     * For example, this may be <em>false</em> in the case of Solaris Doors
     * which do not actively listen.
     *
     * <p>
     *  用于确定<code> Acceptor </code>是否应该向{@link com.sun.corba.se.pept.transport.Selector Selector}注册以处理接受事件。
     * 
     *  例如,在没有主动侦听的Solaris Doors的情况下,这可能是<em> false </em>。
     * 
     * 
     * @return <code>true</code> if the <code>Acceptor</code> should be
     * registered with
     * {@link com.sun.corba.se.pept.transport.Selector Selector}
     */
    public boolean shouldRegisterAcceptEvent();

    /**
     * Accept a connection request.
     *
     * This is called either when the selector gets an accept event
     * for this <code>Acceptor</code> or by a
     * {@link com.sun.corba.se.pept.transport.ListenerThread ListenerThread}.
     *
     * It results in a
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * being created.
     * <p>
     *  接受连接请求。
     * 
     *  当选择器获得此<code> Acceptor </code>的accept事件或由{@link com.sun.corba.se.pept.transport.ListenerThread ListenerThread}
     * 获取此事件时,将调用此方法。
     * 
     *  它将创建一个{@link com.sun.corba.se.pept.transport.Connection Connection}。
     * 
     */
    public void accept();

    /**
     * Close the <code>Acceptor</code>.
     * <p>
     *  关闭<code> Acceptor </code>。
     * 
     */
    public void close();

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.EventHandler EventHandler}
     * associated with this <code>Acceptor</code>.
     *
     * <p>
     *  获取与此<code> Acceptor </code>关联的{@link com.sun.corba.se.pept.transport.EventHandler EventHandler}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.EventHandler EventHandler}
     */
    public EventHandler getEventHandler();

    //
    // Factory methods
    //

    // REVISIT: Identical to ContactInfo method.  Refactor into base interface.

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     * to hold internal data for a message received using the specific
     * encoding, protocol, transport combination represented by this
     * <code>Acceptor</code>.
     *
     * <p>
     *  用于获取{@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}以保存使用由此<code> Acceptor </code>
     * 表示的特定编码,协议,传输组合接收的消息的内部数据。
     *  。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     */
    public MessageMediator createMessageMediator(Broker xbroker,
                                                 Connection xconnection);

    // REVISIT: Identical to ContactInfo method.  Refactor into base interface.

    /**
     * Used to finish creating a
     * {@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}
     * to with internal data for a message received using the specific
     * encoding, protocol, transport combination represented by this
     * <code>Acceptor</code>.
     *
     * <p>
     *  用于完成创建{@link com.sun.corba.se.pept.protocol.MessageMeidator MessageMediator}以使用由此<code> Acceptor </code表示的特定编码,协议,传输组合接收的消息的内部数据>
     * 。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     */

    public MessageMediator finishCreatingMessageMediator(Broker broker,
                                                         Connection xconnection,
                                                         MessageMediator messageMediator);

    /**
     * Used to get a
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * for the specific <em>encoding</em> represented by this
     * <code>Acceptor</code>.
     *
     * <p>
     * 用于获取由此<code> Acceptor </code>表示的特定<em>编码</em>的{@link com.sun.corba.se.pept.encoding.InputObject InputObject}
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
     * <code>Acceptor</code>.
     *
     * <p>
     *  用于获取由此<code> Acceptor </code>表示的特定<em>编码</em>的{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * 。
     * 
     * @return
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     */
    public OutputObject createOutputObject(Broker broker,
                                           MessageMediator messageMediator);

    //
    // Usage dictates implementation equals and hashCode.
    //
}

// End of file.
