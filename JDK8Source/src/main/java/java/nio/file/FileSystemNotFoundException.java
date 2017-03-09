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
 * Runtime exception thrown when a file system cannot be found.
 * <p>
 *  无法找到文件系统时抛出运行时异常。
 * 
 */

public class FileSystemNotFoundException
    extends RuntimeException
{
    static final long serialVersionUID = 7999581764446402397L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     * 
     */
    public FileSystemNotFoundException() {
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
    public FileSystemNotFoundException(String msg) {
        super(msg);
    }
}
