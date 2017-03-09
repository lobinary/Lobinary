/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

/**
 * Unchecked exception thrown when an attempt is made to invoke a method on an
 * object created by one file system provider with a parameter created by a
 * different file system provider.
 * <p>
 *  尝试调用由一个文件系统提供程序创建的对象上的方法时,抛出了未检查的异常,该对象具有由不同文件系统提供程序创建的参数。
 * 
 */
public class ProviderMismatchException
    extends java.lang.IllegalArgumentException
{
    static final long serialVersionUID = 4990847485741612530L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     * 
     */
    public ProviderMismatchException() {
    }

    /**
     * Constructs an instance of this class.
     *
     * <p>
     *  构造此类的实例。
     * 
     * @param   msg
     *          the detail message
     */
    public ProviderMismatchException(String msg) {
        super(msg);
    }
}
