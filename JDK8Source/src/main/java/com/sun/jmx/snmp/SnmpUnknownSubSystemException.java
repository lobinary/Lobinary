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
 * This exception is thrown when the handled <CODE> SnmpSubSystem </CODE> is unknown.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  当处理的<CODE> SnmpSubSystem </CODE>未知时,将抛出此异常。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。
 * </b> </p>。
 * 
 * 
 * @since 1.5
 */
public class SnmpUnknownSubSystemException extends Exception {
    private static final long serialVersionUID = 4463202140045245052L;

    /**
     * Constructor.
     * <p>
     * 
     * @param msg The exception msg to display.
     */
    public SnmpUnknownSubSystemException(String msg) {
        super(msg);
    }
}
