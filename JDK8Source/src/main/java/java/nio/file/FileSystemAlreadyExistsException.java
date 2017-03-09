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
 * Runtime exception thrown when an attempt is made to create a file system that
 * already exists.
 * <p>
 *  尝试创建已存在的文件系统时抛出运行时异常。
 * 
 */

public class FileSystemAlreadyExistsException
    extends RuntimeException
{
    static final long serialVersionUID = -5438419127181131148L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     * 
     */
    public FileSystemAlreadyExistsException() {
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
    public FileSystemAlreadyExistsException(String msg) {
        super(msg);
    }
}
