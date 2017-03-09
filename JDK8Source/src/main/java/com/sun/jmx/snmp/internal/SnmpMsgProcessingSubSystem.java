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
package com.sun.jmx.snmp.internal;

import java.util.Vector;
import com.sun.jmx.snmp.SnmpMsg;
import com.sun.jmx.snmp.SnmpParams;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpVarBind;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpSecurityParameters;

import com.sun.jmx.snmp.SnmpUnknownMsgProcModelException;

/**
 * Message processing sub system interface. To allow engine integration, a message processing sub system must implement this interface. This sub system is called by the dispatcher when receiving or sending calls.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  消息处理子系统接口。为了允许引擎集成,消息处理子系统必须实现此接口。此子系统在接收或发送呼叫时由分派器调用。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */
public interface SnmpMsgProcessingSubSystem extends SnmpSubSystem {

    /**
     * Attaches the security sub system to this sub system. Message processing model are making usage of various security sub systems. This direct attachement avoid the need of accessing the engine to retrieve the Security sub system.
     * <p>
     *  将安全子系统连接到此子系统。消息处理模型正在使用各种安全子系统。此直接附件避免访问引擎以检索安全子系统的需要。
     * 
     * 
     * @param security The security sub system.
     */
    public void setSecuritySubSystem(SnmpSecuritySubSystem security);
    /** Gets the attached security sub system.
    /* <p>
    /* 
     * @return The security sub system.
     */
    public SnmpSecuritySubSystem getSecuritySubSystem();

    /**
     * This method is called when a call is received from the network.
     * <p>
     *  当从网络接收到呼叫时,将调用此方法。
     * 
     * 
     * @param model The model ID.
     * @param factory The pdu factory to use to encode and decode pdu.
     * @return The object that will handle every steps of the receiving (mainly unmarshalling and security).
     */
    public SnmpIncomingRequest getIncomingRequest(int model,
                                                  SnmpPduFactory factory)
        throws SnmpUnknownMsgProcModelException;
    /**
     * This method is called when a call is to be sent to the network. The sub system routes the call to the dedicated model according to the model ID.
     * <p>
     *  当要将呼叫发送到网络时,将调用此方法。子系统根据型号ID将呼叫路由到专用模型。
     * 
     * 
     * @param model The model ID.
     * @param factory The pdu factory to use to encode and decode pdu.
     * @return The object that will handle every steps of the sending (mainly marshalling and security).
     */
    public SnmpOutgoingRequest getOutgoingRequest(int model,
                                                  SnmpPduFactory factory) throws SnmpUnknownMsgProcModelException ;
    /**
     * This method is called to instantiate a pdu according to the passed pdu type and parameters. The sub system routes the call to the dedicated model according to the model ID.
     * <p>
     *  根据传递的pdu类型和参数,调用此方法来实例化pdu。子系统根据型号ID将呼叫路由到专用模型。
     * 
     * 
     * @param model The model ID.
     * @param p The request parameters.
     * @param type The pdu type.
     * @return The pdu.
     */
    public SnmpPdu getRequestPdu(int model, SnmpParams p, int type) throws SnmpUnknownMsgProcModelException, SnmpStatusException ;
     /**
     * This method is called when a call is received from the network. The sub system routes the call to the dedicated model according to the model ID.
     * <p>
     *  当从网络接收到呼叫时,将调用此方法。子系统根据型号ID将呼叫路由到专用模型。
     * 
     * 
     * @param model The model ID.
     * @param factory The pdu factory to use to decode pdu.
     * @return The object that will handle every steps of the receiving (mainly marshalling and security).
     */
    public SnmpIncomingResponse getIncomingResponse(int model,
                                                    SnmpPduFactory factory) throws SnmpUnknownMsgProcModelException;
    /**
     * This method is called to encode a full scoped pdu that as not been encrypted. <CODE>contextName</CODE>, <CODE>contextEngineID</CODE> and data are known. It will be routed to the dedicated model according to the version value.
     * <BR>The specified parameters are defined in RFC 2572 (see also the {@link com.sun.jmx.snmp.SnmpV3Message} class).
     * <p>
     * 调用此方法来编码未加密的全范围pdu。 <CODE> contextName </CODE>,<CODE> contextEngineID </CODE>和数据是已知的。它将根据版本值路由到专用模型。
     *  <BR>指定的参数在RFC 2572中定义(另请参阅{@link com.sun.jmx.snmp.SnmpV3Message}类)。
     * 
     * 
     * @param version The SNMP protocol version.
     * @param msgID The SNMP message ID.
     * @param msgMaxSize The max message size.
     * @param msgFlags The message flags.
     * @param msgSecurityModel The message security model.
     * @param params The security parameters.
     * @param contextEngineID The context engine ID.
     * @param contextName The context name.
     * @param data The encoded data.
     * @param dataLength The encoded data length.
     * @param outputBytes The buffer containing the encoded message.
     * @return The encoded bytes number.
     */
    public int encode(int version,
                      int msgID,
                      int msgMaxSize,
                      byte msgFlags,
                      int msgSecurityModel,
                      SnmpSecurityParameters params,
                      byte[] contextEngineID,
                      byte[] contextName,
                      byte[] data,
                      int dataLength,
                      byte[] outputBytes)
        throws SnmpTooBigException,
               SnmpUnknownMsgProcModelException ;
    /**
     * This method is called to encode a full scoped pdu that as been encrypted. <CODE>contextName</CODE>, <CODE>contextEngineID</CODE> and data are not known. It will be routed to the dedicated model according to the version value.
     * <BR>The specified parameters are defined in RFC 2572 (see also the {@link com.sun.jmx.snmp.SnmpV3Message} class).
     * <p>
     *  调用此方法来对已加密的完整范围的pdu进行编码。 <CODE> contextName </CODE>,<CODE> contextEngineID </CODE>和数据未知。
     * 它将根据版本值路由到专用模型。 <BR>指定的参数在RFC 2572中定义(另请参阅{@link com.sun.jmx.snmp.SnmpV3Message}类)。
     * 
     * 
     * @param version The SNMP protocol version.
     * @param msgID The SNMP message ID.
     * @param msgMaxSize The max message size.
     * @param msgFlags The message flags.
     * @param msgSecurityModel The message security model.
     * @param params The security parameters.
     * @param encryptedPdu The encrypted pdu.
     * @param outputBytes The buffer containing the encoded message.
     * @return The encoded bytes number.
     */
    public int encodePriv(int version,
                          int msgID,
                          int msgMaxSize,
                          byte msgFlags,
                          int msgSecurityModel,
                          SnmpSecurityParameters params,
                          byte[] encryptedPdu,
                          byte[] outputBytes) throws SnmpTooBigException, SnmpUnknownMsgProcModelException;

     /**
     * This method returns a decoded scoped pdu. This method decodes only the <CODE>contextEngineID</CODE>, <CODE>contextName</CODE> and data. It is needed by the <CODE>SnmpSecurityModel</CODE> after decryption. It will be routed to the dedicated model according to the version value.
     * <p>
     *  此方法返回解码的范围pdu。此方法仅解码<CODE> contextEngineID </CODE>,<CODE> contextName </CODE>和数据。
     * 解密后由<CODE> SnmpSecurityModel </CODE>需要。它将根据版本值路由到专用模型。
     * 
     * 
     * @param version The SNMP protocol version.
     * @param pdu The encoded pdu.
     * @return the partialy scoped pdu.
     */
    public SnmpDecryptedPdu decode(int version,
                                   byte[] pdu)
        throws SnmpStatusException, SnmpUnknownMsgProcModelException;

      /**
     * This method returns an encoded scoped pdu. This method encodes only the <CODE>contextEngineID</CODE>, <CODE>contextName</CODE> and data. It is needed by the <CODE>SnmpSecurityModel</CODE> for decryption. It will be routed to the dedicated model according to the version value.
     * <p>
     *  此方法返回编码的作用域pdu。此方法仅编码<CODE> contextEngineID </CODE>,<CODE> contextName </CODE>和数据。
     * 它由<CODE> SnmpSecurityModel </CODE>用于解密。它将根据版本值路由到专用模型。
     * 
     * @param version The SNMP protocol version.
     * @param pdu The pdu to encode.
     * @param outputBytes The partialy scoped pdu.
     * @return The encoded bytes number.
     */
    public int encode(int version,
                      SnmpDecryptedPdu pdu,
                      byte[] outputBytes)
        throws SnmpTooBigException, SnmpUnknownMsgProcModelException;
}
