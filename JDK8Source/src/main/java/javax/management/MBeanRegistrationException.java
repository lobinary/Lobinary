/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


/**
 * Wraps exceptions thrown by the preRegister(), preDeregister() methods
 * of the <CODE>MBeanRegistration</CODE> interface.
 *
 * <p>
 *  封装由<CODE> MBeanRegistration </CODE>接口的preRegister(),preDeregister()方法抛出的异常。
 * 
 * 
 * @since 1.5
 */
public class MBeanRegistrationException extends MBeanException   {

    /* Serial version */
    private static final long serialVersionUID = 4482382455277067805L;

    /**
     * Creates an <CODE>MBeanRegistrationException</CODE> that wraps
     * the actual <CODE>java.lang.Exception</CODE>.
     *
     * <p>
     *  创建包装实际<CODE> java.lang.Exception </CODE>的<CODE> MBeanRegistrationException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     */
    public MBeanRegistrationException(java.lang.Exception e) {
        super(e) ;
    }

    /**
     * Creates an <CODE>MBeanRegistrationException</CODE> that wraps
     * the actual <CODE>java.lang.Exception</CODE> with a detailed
     * message.
     *
     * <p>
     *  创建一个包含详细消息的包含实际<CODE> java.lang.Exception </CODE>的<CODE> MBeanRegistrationException </CODE>。
     * 
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public MBeanRegistrationException(java.lang.Exception e, String message) {
        super(e, message) ;
    }
}
