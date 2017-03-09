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
package com.sun.jmx.snmp;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpTooBigException;

/**
 * Security parameters are security model dependent. Every security parameters class wishing to be passed to a security model must implement this marker interface.
 * This interface has to be implemented when developing customized security models.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  安全参数是安全模型相关的。希望传递到安全模型的每个安全参数类都必须实现此标记接口。当开发定制的安全模型时,必须实现此接口。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */
public interface SnmpSecurityParameters {
    /**
     * BER encoding of security parameters.
     * <p>
     *  安全参数的BER编码。
     * 
     * 
     * @param outputBytes Array to fill.
     * @return Encoded parameters length.
     */
    int encode(byte[] outputBytes) throws SnmpTooBigException;
    /**
     * BER decoding of security parameters.
     * <p>
     *  BER解码安全参数。
     * 
     * 
     * @param params Encoded parameters.
     */
    void decode(byte[] params) throws SnmpStatusException;

    /**
     * Principal coded inside the security parameters.
     * <p>
     *  主体编码内部的安全参数。
     * 
     * @return The security principal.
     */
    String getPrincipal();
}
