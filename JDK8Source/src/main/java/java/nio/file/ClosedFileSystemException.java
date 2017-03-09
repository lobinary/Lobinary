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
 * Unchecked exception thrown when an attempt is made to invoke an operation on
 * a file and the file system is closed.
 * <p>
 *  尝试调用对文件的操作并关闭文件系统时抛出未经检查的异常。
 * 
 */

public class ClosedFileSystemException
    extends IllegalStateException
{
    static final long serialVersionUID = -8158336077256193488L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     */
    public ClosedFileSystemException() {
    }
}
