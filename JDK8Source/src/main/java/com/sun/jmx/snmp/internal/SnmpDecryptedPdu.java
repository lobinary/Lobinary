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
/**
 * Class returned by <CODE>SnmpSecuritySubSystem</CODE> and <CODE>SnmpSecurityModel</CODE>. If privacy is applied, the received pdu must be decrypted. This class contains the field of of a decrypted scoped pdu.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  由<CODE> SnmpSecuritySubSystem </CODE>和<CODE> SnmpSecurityModel </CODE>返回的类。如果应用了隐私,则所接收的pdu必须被解密。
 * 此类包含解密的作用域pdu的字段。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */

public class SnmpDecryptedPdu {
    /**
     * Decrypted pdu data.
     * <p>
     *  解密pdu数据。
     * 
     */
    public byte[] data = null;
    /**
     * Decrypted pdu data length.
     * <p>
     *  解密pdu数据长度。
     * 
     */
    public int dataLength = 0;
    /**
     * Decrypted context name.
     * <p>
     *  解密的上下文名称。
     * 
     */
    public byte[] contextName = null;
    /**
     * Decrypted context engine Id.
     * <p>
     *  解密的上下文引擎ID。
     */
    public byte[] contextEngineId = null;
}
