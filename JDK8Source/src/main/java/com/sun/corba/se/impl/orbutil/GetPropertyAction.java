/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil ;

/**
 * A convenience class for retrieving the string value of a system
 * property as a privileged action.  This class is directly copied
 * from sun.security.action.GetPropertyAction in order to avoid
 * depending on the sun.security.action package.
 *
 * <p>An instance of this class can be used as the argument of
 * <code>AccessController.doPrivileged</code>.
 *
 * <p>The following code retrieves the value of the system
 * property named <code>"prop"</code> as a privileged action: <p>
 *
 * <pre>
 * String s = (String) java.security.AccessController.doPrivileged(
 *                         new GetPropertyAction("prop"));
 * </pre>
 *
 * <p>
 *  用于将系统属性的字符串值作为特权操作检索的便利类。这个类直接从sun.security.action.GetPropertyAction复制,以避免依赖于sun.security.action包。
 * 
 *  <p>此类的实例可以用作<code> AccessController.doPrivileged </code>的参数。
 * 
 *  <p>以下代码检索名为<code>"prop"</code>的系统属性的值作为特权操作：<p>
 * 
 * <pre>
 *  String s =(String)java.security.AccessController.doPrivileged(new GetPropertyAction("prop"));
 * </pre>
 * 
 * 
 * @author Roland Schemers
 * @author Ken Cavanaugh
 * @see java.security.PrivilegedAction
 * @see java.security.AccessController
 */

public class GetPropertyAction implements java.security.PrivilegedAction {
    private String theProp;
    private String defaultVal;

    /**
     * Constructor that takes the name of the system property whose
     * string value needs to be determined.
     *
     * <p>
     * 
     * @param theProp the name of the system property.
     */
    public GetPropertyAction(String theProp) {
        this.theProp = theProp;
    }

    /**
     * Constructor that takes the name of the system property and the default
     * value of that property.
     *
     * <p>
     *  构造函数需要需要确定其字符串值的系统属性的名称。
     * 
     * 
     * @param theProp the name of the system property.
     * @param defaulVal the default value.
     */
    public GetPropertyAction(String theProp, String defaultVal) {
        this.theProp = theProp;
        this.defaultVal = defaultVal;
    }

    /**
     * Determines the string value of the system property whose
     * name was specified in the constructor.
     *
     * <p>
     *  构造函数采用系统属性的名称和该属性的默认值。
     * 
     * 
     * @return the string value of the system property,
     *         or the default value if there is no property with that key.
     */
    public Object run() {
        String value = System.getProperty(theProp);
        return (value == null) ? defaultVal : value;
    }
}
