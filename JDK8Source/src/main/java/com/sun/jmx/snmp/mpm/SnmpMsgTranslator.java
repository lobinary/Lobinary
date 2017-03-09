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
package com.sun.jmx.snmp.mpm;

import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpMsg;
/**
 * The translator interface is implemented by classes dealing with a specific SNMP protocol version. SnmpMsgTranslator are used in conjonction with SnmpMsgProcessingModel implementations.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  翻译器接口由处理特定SNMP协议版本的类实现。 SnmpMsgTranslator与SnmpMsgProcessingModel实现结合使用。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */
public interface SnmpMsgTranslator {
    /**
     * Returns the request or message Id contained in the passed message. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回包含在传递的消息中的请求或消息ID。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public int getMsgId(SnmpMsg msg);
    /**
     * Returns the response max message size. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回响应最大消息大小。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public int getMsgMaxSize(SnmpMsg msg);
    /**
     * Returns the message flags. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息标志。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte getMsgFlags(SnmpMsg msg);
    /**
     * Returns the message security model. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息安全模型。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public int getMsgSecurityModel(SnmpMsg msg);
    /**
     * Returns the message security level. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息安全级别。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public int getSecurityLevel(SnmpMsg msg);
     /**
     * Returns an encoded representation of security parameters contained in the passed msg. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回包含在传递的msg中的安全参数的编码表示。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte[] getFlatSecurityParameters(SnmpMsg msg);
    /**
     * Returns the message security parameters. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息安全参数。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public SnmpSecurityParameters getSecurityParameters(SnmpMsg msg);
    /**
     * Returns the message context Engine Id. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     * 返回消息上下文引擎标识。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte[] getContextEngineId(SnmpMsg msg);
    /**
     * Returns the message context name. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息上下文名称。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte[] getContextName(SnmpMsg msg);
    /**
     * Returns the raw message context name. Raw mean as it is received from the network, without translation. It can be useful when some data are piggy backed in the context name.The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回原始消息上下文名称。原始平均值,因为它是从网络接收的,没有翻译。当在上下文名称中捎带一些数据时,这可能很有用。消息是一个通用的,在实现此接口的对象中缩小。
     * 
     */
    public byte[] getRawContextName(SnmpMsg msg);
    /**
     * Returns the message accesscontext name. This access context name is used when dealing with access rights (eg: community for V1/V2 or context name for V3).The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息accesscontext名称。此访问上下文名称在处理访问权限时使用(例如：V1 / V2的社区或V3的上下文名称)。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte[] getAccessContext(SnmpMsg msg);
    /**
     * Returns the message encrypted pdu or null if no encryption. The message is a generic one that is narrowed in the object implementing this interface.
     * <p>
     *  返回消息encrypted pdu或null(如果没有加密)。消息是在实现此接口的对象中缩小的通用消息。
     * 
     */
    public byte[] getEncryptedPdu(SnmpMsg msg);
    /**
     * Set the context name of the passed message.
     * <p>
     *  设置传递消息的上下文名称。
     * 
     */
    public void setContextName(SnmpMsg req, byte[] contextName);
     /**
     * Set the context engine Id of the passed message.
     * <p>
     *  设置传递消息的上下文引擎ID。
     */
    public void setContextEngineId(SnmpMsg req, byte[] contextEngineId);
}
