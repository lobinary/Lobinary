/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2006, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This exception is thrown when an error occurs in an <CODE> SnmpSecurityModel </CODE>.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  当在<CODE> SnmpSecurityModel </CODE>中发生错误时抛出此异常。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。
 * </b> </p>。
 * 
 * 
 * @since 1.5
 */
public class SnmpSecurityException extends Exception {
    private static final long serialVersionUID = 5574448147432833480L;

    /**
     * The current request varbind list.
     * <p>
     *  当前请求varbind列表。
     * 
     */
    public SnmpVarBind[] list = null;
    /**
     * The status of the exception. See {@link com.sun.jmx.snmp.SnmpDefinitions} for possible values.
     * <p>
     *  异常的状态。有关可能的值,请参阅{@link com.sun.jmx.snmp.SnmpDefinitions}。
     * 
     */
    public int status = SnmpDefinitions.snmpReqUnknownError;
    /**
     * The current security model related security parameters.
     * <p>
     *  当前安全模型相关的安全参数。
     * 
     */
    public SnmpSecurityParameters params = null;
    /**
     * The current context engine Id.
     * <p>
     *  当前上下文引擎Id。
     * 
     */
    public byte[] contextEngineId = null;
     /**
     * The current context name.
     * <p>
     *  当前上下文名称。
     * 
     */
    public byte[] contextName = null;
     /**
     * The current flags.
     * <p>
     *  当前标志。
     * 
     */
    public byte flags = (byte) SnmpDefinitions.noAuthNoPriv;
    /**
     * Constructor.
     * <p>
     *  构造函数。
     * 
     * @param msg The exception msg to display.
     */
    public SnmpSecurityException(String msg) {
        super(msg);
    }
}
