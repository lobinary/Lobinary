/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import java.security.PrivilegedAction;

/**
 * Utility class to be used by the method <tt>AccessControler.doPrivileged</tt>
 * to get a system property.
 *
 * <p>
 *  要通过方法<tt> AccessControler.doPrivileged </tt>使用的实用程序类来获取系统属性。
 * 
 * @since 1.5
 */
public class GetPropertyAction implements PrivilegedAction<String> {
    private final String key;

    public GetPropertyAction(String key) {
        this.key = key;
    }

    public String run() {
        return System.getProperty(key);
    }
}
