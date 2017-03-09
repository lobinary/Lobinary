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
import com.sun.jmx.snmp.SnmpPduFactory;
import com.sun.jmx.snmp.SnmpSecurityParameters;
import com.sun.jmx.snmp.SnmpSecurityException;
import com.sun.jmx.snmp.SnmpTooBigException;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpMsg;

import com.sun.jmx.snmp.internal.SnmpSecurityCache;
import com.sun.jmx.snmp.SnmpBadSecurityLevelException;
/**
 * <P> An <CODE>SnmpIncomingResponse</CODE> handles the unmarshalling of the received response.</P>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  </p> </p> </p> </p> </p> </p> </p> </p> </p> </p> </b> </p>
 * 
 * 
 * @since 1.5
 */

public interface SnmpIncomingResponse {
    /**
     * Returns the source address.
     * <p>
     *  返回源地址。
     * 
     * 
     * @return The source address.
     */
    public InetAddress getAddress();

    /**
     * Returns the source port.
     * <p>
     *  返回源端口。
     * 
     * 
     * @return The source port.
     */
    public int getPort();

    /**
     * Gets the incoming response security parameters.
     * <p>
     *  获取传入响应安全参数。
     * 
     * 
     * @return The security parameters.
     **/
    public SnmpSecurityParameters getSecurityParameters();
    /**
     * Call this method in order to reuse <CODE>SnmpOutgoingRequest</CODE> cache.
     * <p>
     *  调用此方法以便重用<CODE> SnmpOutgoingRequest </CODE>缓存。
     * 
     * 
     * @param cache The security cache.
     */
    public void setSecurityCache(SnmpSecurityCache cache);
    /**
     * Gets the incoming response security level. This level is defined in
     * {@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}.
     * <p>
     *  获取传入响应安全级别。此级别在{@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}中定义。
     * 
     * 
     * @return The security level.
     */
    public int getSecurityLevel();
    /**
     * Gets the incoming response security model.
     * <p>
     *  获取传入响应安全模型。
     * 
     * 
     * @return The security model.
     */
    public int getSecurityModel();
    /**
     * Gets the incoming response context name.
     * <p>
     *  获取传入响应上下文名称。
     * 
     * 
     * @return The context name.
     */
    public byte[] getContextName();

    /**
     * Decodes the specified bytes and initializes itself with the received
     * response.
     *
     * <p>
     *  解码指定的字节,并用接收到的响应初始化自身。
     * 
     * 
     * @param inputBytes The bytes to be decoded.
     *
     * @exception SnmpStatusException If the specified bytes are not a valid encoding.
     */
    public SnmpMsg decodeMessage(byte[] inputBytes,
                                 int byteCount,
                                 InetAddress address,
                                 int port)
        throws SnmpStatusException, SnmpSecurityException;

    /**
     * Gets the request PDU encoded in the received response.
     * <P>
     * This method decodes the data field and returns the resulting PDU.
     *
     * <p>
     *  获取在接收到的响应中编码的请求PDU。
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
     * Returns the response request Id.
     * <p>
     *  返回响应请求标识。
     * 
     * 
     * @param data The flat message.
     * @return The request Id.
     */
    public int getRequestId(byte[] data) throws SnmpStatusException;

    /**
     * Returns a stringified form of the message to send.
     * <p>
     *  返回要发送的消息的字符串形式。
     * 
     * @return The message state string.
     */
    public String printMessage();
}
