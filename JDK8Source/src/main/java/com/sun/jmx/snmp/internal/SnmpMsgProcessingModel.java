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


import com.sun.jmx.snmp.mpm.SnmpMsgTranslator;

import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpSecurityParameters;

import com.sun.jmx.snmp.SnmpParams;
/**
 * The message processing model interface. Any message processing model must implement this interface in order to be integrated in the engine framework.
 * The model is called by the dispatcher when a call is received or when a call is sent.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  消息处理模型接口。任何消息处理模型必须实现此接口,以便集成到引擎框架中。当接收到呼叫或发送呼叫时,调度器调用模型。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */
public interface SnmpMsgProcessingModel extends SnmpModel {
    /**
     * This method is called when a call is to be sent to the network.
     * <p>
     *  当要将呼叫发送到网络时,将调用此方法。
     * 
     * 
     * @param factory The pdu factory to use to encode and decode pdu.
     * @return The object that will handle every steps of the sending (mainly marshalling and security).
     */
    public SnmpOutgoingRequest getOutgoingRequest(SnmpPduFactory factory);
    /**
     * This method is called when a call is received from the network.
     * <p>
     *  当从网络接收到呼叫时,将调用此方法。
     * 
     * 
     * @param factory The pdu factory to use to encode and decode pdu.
     * @return The object that will handle every steps of the receiving (mainly unmarshalling and security).
     */
    public SnmpIncomingRequest getIncomingRequest(SnmpPduFactory factory);

     /**
     * This method is called when a response is received from the network.
     * <p>
     *  当从网络接收到响应时调用此方法。
     * 
     * 
     * @param factory The pdu factory to use to encode and decode pdu.
     * @return The object that will handle every steps of the receiving (mainly unmarshalling and security).
     */
    public SnmpIncomingResponse getIncomingResponse(SnmpPduFactory factory);
    /**
     * This method is called to instantiate a pdu according to the passed pdu type and parameters.
     * <p>
     *  根据传递的pdu类型和参数,调用此方法来实例化pdu。
     * 
     * 
     * @param p The request parameters.
     * @param type The pdu type.
     * @return The pdu.
     */
    public SnmpPdu getRequestPdu(SnmpParams p, int type) throws SnmpStatusException;

    /**
     * This method is called to encode a full scoped pdu that has not been encrypted. <CODE>contextName</CODE>, <CODE>contextEngineID</CODE> and data are known.
     * <BR>The specified parameters are defined in RFC 2572 (see also the {@link com.sun.jmx.snmp.SnmpV3Message} class).
     * <p>
     *  调用此方法来对尚未加密的完整范围的pdu进行编码。 <CODE> contextName </CODE>,<CODE> contextEngineID </CODE>和数据是已知的。
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
                      byte[] outputBytes) throws SnmpTooBigException;
    /**
     * This method is called to encode a full scoped pdu that as been encrypted. <CODE>contextName</CODE>, <CODE>contextEngineID</CODE> and data are known.
     * <BR>The specified parameters are defined in RFC 2572 (see also the {@link com.sun.jmx.snmp.SnmpV3Message} class).
     * <p>
     *  调用此方法来对已加密的完整范围的pdu进行编码。 <CODE> contextName </CODE>,<CODE> contextEngineID </CODE>和数据是已知的。
     *  <BR>指定的参数在RFC 2572中定义(另请参阅{@link com.sun.jmx.snmp.SnmpV3Message}类)。
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
                          byte[] outputBytes) throws SnmpTooBigException;
     /**
     * This method returns a decoded scoped pdu. This method decodes only the <CODE>contextEngineID</CODE>, <CODE>contextName</CODE> and data. It is needed by the <CODE>SnmpSecurityModel</CODE> after decryption.
     * <p>
     * 此方法返回解码的范围pdu。此方法仅解码<CODE> contextEngineID </CODE>,<CODE> contextName </CODE>和数据。
     * 解密后由<CODE> SnmpSecurityModel </CODE>需要。
     * 
     * 
     * @param pdu The encoded pdu.
     * @return The partialy scoped pdu.
     */
    public SnmpDecryptedPdu decode(byte[] pdu) throws SnmpStatusException;

    /**
     * This method returns an encoded scoped pdu. This method encode only the <CODE>contextEngineID</CODE>, <CODE>contextName</CODE> and data. It is needed by the <CODE>SnmpSecurityModel</CODE> for decryption.
     * <p>
     *  此方法返回编码的作用域pdu。此方法仅编码<CODE> contextEngineID </CODE>,<CODE> contextName </CODE>和数据。
     * 它由<CODE> SnmpSecurityModel </CODE>用于解密。
     * 
     * 
     * @param pdu The pdu to encode.
     * @param outputBytes The partialy scoped pdu.
     * @return The encoded bytes number.
     */
    public int encode(SnmpDecryptedPdu pdu,
                      byte[] outputBytes) throws SnmpTooBigException;

    /**
     * In order to change the behavior of the translator, set it.
     * <p>
     *  为了改变翻译器的行为,设置它。
     * 
     * 
     * @param translator The translator that will be used.
     */
    public void setMsgTranslator(SnmpMsgTranslator translator);

    /**
     * Returns the current translator.
     * <p>
     *  返回当前转换器。
     * 
     * @return The current translator.
     */
    public SnmpMsgTranslator getMsgTranslator();
}
