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
 * Checked exception thrown when a file system operation fails because a
 * directory is not empty.
 *
 * <p>
 *  当文件系统操作失败,因为目录不为空时抛出检查异常。
 * 
 * 
 * @since 1.7
 */

public class DirectoryNotEmptyException
    extends FileSystemException
{
    static final long serialVersionUID = 3056667871802779003L;

    /**
     * Constructs an instance of this class.
     *
     * <p>
     *  构造此类的实例。
     * 
     * @param   dir
     *          a string identifying the directory or {@code null} if not known
     */
    public DirectoryNotEmptyException(String dir) {
        super(dir);
    }
}
