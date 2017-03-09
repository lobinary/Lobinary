/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp.daemon;

// java import
//
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Represents exceptions raised due to communications problems,
 * for example when a managed object server is out of reach.<p>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示由于通信问题而引发的异常,例如,当受管对象服务器无法访问时。<p>
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class CommunicationException extends javax.management.JMRuntimeException {

    /* Serial version */
    private static final long serialVersionUID = -2499186113233316177L;

    /**
     * Constructs a CommunicationException with a target exception.
     * <p>
     *  构造具有目标异常的CommunicationException。
     * 
     */
    public CommunicationException(Throwable target) {
        super(target.getMessage());
        initCause(target);
    }

    /**
     * Constructs a CommunicationException with a target exception
     * and a detail message.
     * <p>
     *  构造具有目标异常和详细消息的CommunicationException。
     * 
     */
    public CommunicationException(Throwable target, String msg) {
        super(msg);
        initCause(target);
    }

    /**
     * Constructs a CommunicationException with a detail message.
     * <p>
     *  构造具有详细消息的CommunicationException。
     * 
     */
    public CommunicationException(String msg) {
        super(msg);
    }

    /**
     * Get the thrown target exception.
     * <p>
     *  获取抛出的目标异常。
     */
    public Throwable getTargetException() {
        return getCause();
    }

}
