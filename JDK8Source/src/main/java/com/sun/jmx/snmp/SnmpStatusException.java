/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * Reports an error which occurred during a get/set operation on a mib node.
 *
 * This exception includes a status error code as defined in the SNMP protocol.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  报告在mib节点上执行get / set操作期间发生的错误。
 * 
 *  此异常包括SNMP协议中定义的状态错误代码。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpStatusException extends Exception implements SnmpDefinitions {
    private static final long serialVersionUID = 5809485694133115675L;

    /**
     * Error code as defined in RFC 1448 for: <CODE>noSuchName</CODE>.
     * <p>
     *  RFC 1448中定义的错误代码：<CODE> noSuchName </CODE>。
     * 
     */
    public static final int noSuchName         = 2 ;

    /**
     * Error code as defined in RFC 1448 for: <CODE>badValue</CODE>.
     * <p>
     *  RFC 1448中定义的错误代码：<CODE> badValue </CODE>。
     * 
     */
    public static final int badValue           = 3 ;

    /**
     * Error code as defined in RFC 1448 for: <CODE>readOnly</CODE>.
     * <p>
     *  RFC 1448中定义的错误代码：<CODE> readOnly </CODE>。
     * 
     */
    public static final int readOnly           = 4 ;


    /**
     * Error code as defined in RFC 1448 for: <CODE>noAccess</CODE>.
     * <p>
     *  RFC 1448中定义的错误代码：<CODE> noAccess </CODE>。
     * 
     */
    public static final int noAccess           = 6 ;

    /**
     * Error code for reporting a no such instance error.
     * <p>
     *  报告无此类实例错误的错误代码。
     * 
     */
    public static final int noSuchInstance     = 0xE0;

    /**
     * Error code for reporting a no such object error.
     * <p>
     *  用于报告无此类对象错误的错误代码。
     * 
     */
    public static final int noSuchObject     = 0xE1;

    /**
     * Constructs a new <CODE>SnmpStatusException</CODE> with the specified status error.
     * <p>
     *  构造具有指定状态错误的新<CODE> SnmpStatusException </CODE>。
     * 
     * 
     * @param status The error status.
     */
    public SnmpStatusException(int status) {
        errorStatus = status ;
    }

    /**
     * Constructs a new <CODE>SnmpStatusException</CODE> with the specified status error and status index.
     * <p>
     *  构造具有指定状态错误和状态索引的新<CODE> SnmpStatusException </CODE>。
     * 
     * 
     * @param status The error status.
     * @param index The error index.
     */
    public SnmpStatusException(int status, int index) {
        errorStatus = status ;
        errorIndex = index ;
    }

    /**
     * Constructs a new <CODE>SnmpStatusException</CODE> with an error message.
     * The error status is set to 0 (noError) and the index to -1.
     * <p>
     *  构造一个带有错误消息的新<CODE> SnmpStatusException </CODE>。错误状态设置为0(noError),索引设置为-1。
     * 
     * 
     * @param s The error message.
     */
    public SnmpStatusException(String s) {
        super(s);
    }

    /**
     * Constructs a new <CODE>SnmpStatusException</CODE> with an error index.
     * <p>
     *  构造一个带有错误索引的新<CODE> SnmpStatusException </CODE>。
     * 
     * 
     * @param x The original <CODE>SnmpStatusException</CODE>.
     * @param index The error index.
     */
    public SnmpStatusException(SnmpStatusException x, int index) {
        super(x.getMessage());
        errorStatus= x.errorStatus;
        errorIndex= index;
    }

    /**
     * Return the error status.
     * <p>
     *  返回错误状态。
     * 
     * 
     * @return The error status.
     */
    public int getStatus() {
        return errorStatus ;
    }

    /**
     * Returns the index of the error.
     * A value of -1 means that the index is not known/applicable.
     * <p>
     *  返回错误的索引。值为-1表示索引不为已知/适用。
     * 
     * 
     * @return The error index.
     */
    public int getErrorIndex() {
        return errorIndex;
    }


    // PRIVATE VARIABLES
    //--------------------

    /**
     * Status of the error.
     * <p>
     *  错误的状态。
     * 
     * 
     * @serial
     */
    private int errorStatus = 0 ;

    /**
     * Index of the error.
     * If different from -1, indicates the index where the error occurs.
     * <p>
     *  错误的索引。如果与-1不同,则表示发生错误的索引。
     * 
     * @serial
     */
    private int errorIndex= -1;

}
