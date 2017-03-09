/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;

import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.protocol.MessageMediator;
import com.sun.corba.se.pept.transport.EventHandler;


/**
 * <p><code>Connection</code> represents a <em>transport</em> in the
 * PEPt architecture.</p>
 *
 * <p>
 *  <p> <code>连接</code>代表PEPt架构中的<em>传输</em>。</p>
 * 
 * 
 * @author Harold Carr
*/
public interface Connection
{
    /**
     * Used to determine if the <code>Connection</code> should register
     * with the
     * {@link com.sun.corba.se.pept.transport.TransportManager
     * TransportManager}
     * {@link com.sun.corba.se.pept.transport.Selector Selector}
     * to handle read events.
     *
     * For example, an HTTP transport would not register since the requesting
     * thread would just block on read when waiting for the reply.
     *
     * <p>
     *  用于确定<code> Connection </code>是否应该注册到{@link com.sun.corba.se.pept.transport.TransportManager TransportManager}
     *  {@link com.sun.corba.se.pept.transport。
     * 选择器选择器}来处理读事件。
     * 
     *  例如,HTTP传输不会注册,因为请求线程在等待回复时只会在读取时阻塞。
     * 
     * 
     * @return <code>true</code> if it should be registered.
     */
    public boolean shouldRegisterReadEvent();

    /**
     * Used to determine if the <code>Connection</code> should register
     * with the
     * {@link com.sun.corba.se.pept.transport.TransportManager
     * TransportManager}
     * {@link com.sun.corba.se.pept.transport.Selector Selector}
     * to handle read events.
     *
     * For example, an HTTP transport would not register since the requesting
     * thread would just block on read when waiting for the reply.
     *
     * <p>
     *  用于确定<code> Connection </code>是否应该注册到{@link com.sun.corba.se.pept.transport.TransportManager TransportManager}
     *  {@link com.sun.corba.se.pept.transport。
     * 选择器选择器}来处理读事件。
     * 
     *  例如,HTTP传输不会注册,因为请求线程在等待回复时只会在读取时阻塞。
     * 
     * 
     * @return <code>true</code> if it should be registered.
     */
    public boolean shouldRegisterServerReadEvent(); // REVISIT - why special?

    /**
     * Called to read incoming messages.
     *
     * <p>
     *  呼叫读取传入邮件。
     * 
     * 
     * @return <code>true</code> if the thread calling read can be released.
     */
    public boolean read();

    /**
     * Close the <code>Connection</code>.
     *
     * <p>
     *  关闭<code>连接</code>。
     * 
     */
    public void close();

    // REVISIT: replace next two with PlugInFactory (implemented by ContactInfo
    // and Acceptor).

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.Acceptor Acceptor}
     * that created this <code>Connection</code>.
     *
     * <p>
     *  获取创建此<code> Connection </code>的{@link com.sun.corba.se.pept.transport.Acceptor Acceptor}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.Acceptor Acceptor}
     */
    public Acceptor getAcceptor();

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * that created this <code>Connection</code>.
     *
     * <p>
     *  获取创建此<code> Connection </code>的{@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     */
    public ContactInfo getContactInfo();

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

    /**
     * Indicates whether a
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * or a
     * {@link com.sun.corba.se.pept.transport.Acceptor Acceptor}
     * created the
     * <code>Connection</code>.
     *
     * <p>
     * 指示{@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}或{@link com.sun.corba.se.pept.transport.Acceptor Acceptor}
     * 是否创建了<code> Connection </code >。
     * 
     * 
     * @return <code>true</code> if <code>Connection</code> an
     * {@link com.sun.corba.se.pept.transport.Acceptor Acceptor}
     * created the <code>Connection</code>.
     */
    public boolean isServer();

    /**
     * Indicates if the <code>Connection</code> is in the process of
     * sending or receiving a message.
     *
     * <p>
     *  指示<code>连接</code>是否正在发送或接收消息。
     * 
     * 
     * @return <code>true</code> if the <code>Connection</code> is busy.
     */
    public boolean isBusy();

    /**
     * Timestamps are used for connection management, in particular, for
     * reclaiming idle <code>Connection</code>s.
     *
     * <p>
     *  时间戳用于连接管理,特别是用于回收空闲<code>连接</code>。
     * 
     * 
     * @return the "time" the <code>Connection</code> was last used.
     */
    public long getTimeStamp();

    /**
     * Timestamps are used for connection management, in particular, for
     * reclaiming idle <code>Connection</code>s.
     *
     * <p>
     *  时间戳用于连接管理,特别是用于回收空闲<code>连接</code>。
     * 
     * 
     * @param time - the "time" the <code>Connection</code> was last used.
     */
    public void setTimeStamp(long time);

    /**
     * The "state" of the <code>Connection</code>.
     *
     * param state
     * <p>
     *  <code> Connection </code>的"状态"。
     * 
     *  param状态
     * 
     */
    public void setState(String state);

    /**
     * Grab a write lock on the <code>Connection</code>.
     *
     * If another thread already has a write lock then the calling
     * thread will block until the lock is released.  The calling
     * thread must call
     * {@link #writeUnlock}
     * when it is done.
     * <p>
     *  获取<code> Connection </code>上的写锁定。
     * 
     *  如果另一个线程已经有写锁定,那么调用线程将阻塞,直到锁被释放。调用线程必须在完成后调用{@link #writeUnlock}。
     * 
     */
    public void writeLock();

    /**
     * Release a write lock on the <code>Connection</code>.
     * <p>
     *  释放<code> Connection </code>上的写锁定。
     * 
     */
    public void writeUnlock();

    /*
     * Send the data encoded in
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * on the <code>Connection</code>.
     *
     * <p>
     *  发送在<code> Connection </code>上的{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}中编码的数据
     * 。
     * 
     * 
     * @param outputObject
     */
    public void sendWithoutLock(OutputObject outputObject);

    /**
     * Register an invocation's
     * {@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     * with the <code>Connection</code>.
     *
     * This is useful in protocols which support fragmentation.
     *
     * <p>
     *  使用<code> Connection </code>注册调用的{@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     * 。
     * 
     *  这在支持碎片的协议中很有用。
     * 
     * 
     * @param messageMediator
     */
    public void registerWaiter(MessageMediator messageMediator);

    /**
     * If a message expect's a response then this method is called.
     *
     * This method might block on a read (e.g., HTTP), put the calling
     * thread to sleep while another thread read's the response (e.g., GIOP),
     * or it may use the calling thread to perform the server-side work
     * (e.g., Solaris Doors).
     *
     * <p>
     *  如果消息期望有响应,则调用此方法。
     * 
     *  该方法可以在读取(例如,HTTP)时阻塞,使调用线程休眠,而另一个线程读取响应(例如,GIOP),或者它可以使用调用线程来执行服务器端工作(例如,Solaris门)。
     * 
     * 
     * @param messageMediator
     */
    public InputObject waitForResponse(MessageMediator messageMediator);

    /**
     * Unregister an invocation's
     * {@link com.sun.corba.se.pept.protocol.MessageMediator MessageMediator}
     * with the <code>Connection</code>.
     *
     * <p>
     * 
     * @param messageMediator
     */
    public void unregisterWaiter(MessageMediator messageMediator);

    public void setConnectionCache(ConnectionCache connectionCache);

    public ConnectionCache getConnectionCache();
}

// End of file.
