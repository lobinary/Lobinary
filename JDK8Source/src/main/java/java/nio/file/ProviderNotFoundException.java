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
 * Runtime exception thrown when a provider of the required type cannot be found.
 * <p>
 *  无法找到所需类型的提供程序时抛出运行时异常。
 * 
 */

public class ProviderNotFoundException
    extends RuntimeException
{
    static final long serialVersionUID = -1880012509822920354L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     * 
     */
    public ProviderNotFoundException() {
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
    public ProviderNotFoundException(String msg) {
        super(msg);
    }
}
