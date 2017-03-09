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

package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.transport.Connection;
import com.sun.corba.se.pept.transport.ContactInfo;

import java.io.IOException;

/**
 * <code>MessageMediator</code> is a central repository for artifacts
 * associated with an individual message.
 *
 * <p>
 *  <code> MessageMediator </code>是与单个消息相关联的工件的中央存储库。
 * 
 * 
 * @author Harold Carr
 */
public interface MessageMediator
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
     * Get the
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * which created this <code>MessageMediator</code>.
     *
     * <p>
     *  获取创建此<code> MessageMediator </code>的{@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     */
    public ContactInfo getContactInfo();

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.Connection Connection}
     * on which this message is sent or received.
     * <p>
     *  获取发送或接收此邮件的{@link com.sun.corba.se.pept.transport.Connection Connection}。
     * 
     */
    public Connection getConnection();

    /**
     * Used to initialize message headers.
     *
     * Note: this should be moved to a <code>RequestDispatcher</code>.
     * <p>
     *  用于初始化邮件标头。
     * 
     *  注意：这应该移动到<code> RequestDispatcher </code>。
     * 
     */
    public void initializeMessage();

    /**
     * Used to send the message (or its last fragment).
     *
     * Note: this should be moved to a <code>RequestDispatcher</code>.
     * <p>
     *  用于发送消息(或其最后一个片段)。
     * 
     *  注意：这应该移动到<code> RequestDispatcher </code>。
     * 
     */
    public void finishSendingRequest();

    /**
     * Used to wait for a response for synchronous messages.
     *
     * <p>
     *  用于等待同步消息的响应。
     * 
     * 
     * @deprecated
     */
    @Deprecated
    public InputObject waitForResponse();

    /**
     * Used to set the
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * used for the message.
     *
     * <p>
     *  用于设置用于消息的{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}。
     * 
     * 
     * @param outputObject
     */
    public void setOutputObject(OutputObject outputObject);

    /**
     * Used to get the
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * used for the message.
     *
     * <p>
     *  用于获取用于消息的{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}。
     * 
     * 
     * @return
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     */
    public OutputObject getOutputObject();

    /**
     * Used to set the
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * used for the message.
     *
     * <p>
     *  用于设置用于消息的{@link com.sun.corba.se.pept.encoding.InputObject InputObject}。
     * 
     * 
     * @param inputObject
     */
    public void setInputObject(InputObject inputObject);

    /**
     * Used to get the
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * used for the message.
     *
     * <p>
     *  用于获取用于消息的{@link com.sun.corba.se.pept.encoding.InputObject InputObject}。
     * 
     * @return
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     */
    public InputObject getInputObject();
}

// End of file.
