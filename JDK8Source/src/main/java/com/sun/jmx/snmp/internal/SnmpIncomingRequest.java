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

import java.net.InetAddress;

import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpMsg;

import com.sun.jmx.snmp.SnmpUnknownSecModelException;
import com.sun.jmx.snmp.SnmpBadSecurityLevelException;

/**
<P> An <CODE>SnmpIncomingRequest</CODE> handles both sides of an incoming SNMP request:
<ul>
<li> The request. Unmarshalling of the received message. </li>
<li> The response. Marshalling of the message to send. </li>
</ul>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  <P> <CODE> SnmpIncomingRequest </CODE>处理传入SNMP请求的两端：
 * <ul>
 *  <li>请求。解组接收到的消息。 </li> <li>回应。编排要发送的消息。 </li>
 * </ul>
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpIncomingRequest {
    /**
     * Once the incoming request decoded, returns the decoded security parameters.
     * <p>
     *  一旦传入请求被解码,则返回解码的安全参数。
     * 
     * 
     * @return The decoded security parameters.
     */
    public SnmpSecurityParameters getSecurityParameters();
     /**
     * Tests if a report is expected.
     * <p>
     *  测试是否需要报告。
     * 
     * 
     * @return boolean indicating if a report is to be sent.
     */
    public boolean isReport();
    /**
     * Tests if a response is expected.
     * <p>
     *  测试是否期望响应。
     * 
     * 
     * @return boolean indicating if a response is to be sent.
     */
    public boolean isResponse();

    /**
     * Tells this request that no response will be sent.
     * <p>
     *  告诉此请求,将不会发送任何响应。
     * 
     */
    public void noResponse();
    /**
     * Gets the incoming request principal.
     * <p>
     *  获取传入请求主体。
     * 
     * 
     * @return The request principal.
     **/
    public String getPrincipal();
    /**
     * Gets the incoming request security level. This level is defined in {@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}.
     * <p>
     *  获取传入请求安全级别。此级别在{@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}中定义。
     * 
     * 
     * @return The security level.
     */
    public int getSecurityLevel();
    /**
     * Gets the incoming request security model.
     * <p>
     *  获取传入请求安全模型。
     * 
     * 
     * @return The security model.
     */
    public int getSecurityModel();
    /**
     * Gets the incoming request context name.
     * <p>
     *  获取传入请求上下文名称。
     * 
     * 
     * @return The context name.
     */
    public byte[] getContextName();
    /**
     * Gets the incoming request context engine Id.
     * <p>
     *  获取传入请求上下文引擎标识。
     * 
     * 
     * @return The context engine Id.
     */
    public byte[] getContextEngineId();
    /**
     * Gets the incoming request context name used by Access Control Model in order to allow or deny the access to OIDs.
     * <p>
     *  获取访问控制模型使用的传入请求上下文名称,以允许或拒绝对OID的访问。
     * 
     */
    public byte[] getAccessContext();
    /**
     * Encodes the response message to send and puts the result in the specified byte array.
     *
     * <p>
     *  对响应消息进行编码以发送并将结果放入指定的字节数组中。
     * 
     * 
     * @param outputBytes An array to receive the resulting encoding.
     *
     * @exception ArrayIndexOutOfBoundsException If the result does not fit
     *                                           into the specified array.
     */
    public int encodeMessage(byte[] outputBytes)
        throws SnmpTooBigException;

    /**
     * Decodes the specified bytes and initializes the request with the incoming message.
     *
     * <p>
     *  解码指定的字节并使用传入消息初始化请求。
     * 
     * 
     * @param inputBytes The bytes to be decoded.
     *
     * @exception SnmpStatusException If the specified bytes are not a valid encoding or if the security applied to this request failed and no report is to be sent (typically trap PDU).
     */
    public void decodeMessage(byte[] inputBytes,
                              int byteCount,
                              InetAddress address,
                              int port)
        throws SnmpStatusException, SnmpUnknownSecModelException,
               SnmpBadSecurityLevelException;

     /**
     * Initializes the response to send with the passed Pdu.
     * <P>
     * If the encoding length exceeds <CODE>maxDataLength</CODE>,
     * the method throws an exception.
     *
     * <p>
     *  初始化响应以使用传递的Pdu进行发送。
     * <P>
     *  如果编码长度超过<CODE> maxDataLength </CODE>,则该方法将抛出异常。
     * 
     * 
     * @param p The PDU to be encoded.
     * @param maxDataLength The maximum length permitted for the data field.
     *
     * @exception SnmpStatusException If the specified <CODE>pdu</CODE>
     *     is not valid.
     * @exception SnmpTooBigException If the resulting encoding does not fit
     * into <CODE>maxDataLength</CODE> bytes.
     * @exception ArrayIndexOutOfBoundsException If the encoding exceeds
     *   <CODE>maxDataLength</CODE>.
     */
    public SnmpMsg encodeSnmpPdu(SnmpPdu p,
                                 int maxDataLength)
        throws SnmpStatusException, SnmpTooBigException;

    /**
     * Gets the request PDU encoded in the received message.
     * <P>
     * This method decodes the data field and returns the resulting PDU.
     *
     * <p>
     *  获取在接收到的消息中编码的请求PDU。
     * <P>
     *  此方法解码数据字段并返回生成的PDU。
     * 
     * 
     * @return The resulting PDU.
     * @exception SnmpStatusException If the encoding is not valid.
     */
    public SnmpPdu decodeSnmpPdu()
        throws SnmpStatusException;

    /**
     * Returns a stringified form of the received message.
     * <p>
     * 返回接收到的消息的字符串形式。
     * 
     * 
     * @return The message state string.
     */
    public String printRequestMessage();
    /**
     * Returns a stringified form of the message to send.
     * <p>
     *  返回要发送的消息的字符串形式。
     * 
     * @return The message state string.
     */
    public String printResponseMessage();
}
