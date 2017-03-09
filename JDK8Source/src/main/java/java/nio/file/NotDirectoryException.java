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
 * Checked exception thrown when a file system operation, intended for a
 * directory, fails because the file is not a directory.
 *
 * <p>
 *  当针对目录的文件系统操作失败,因为文件不是目录时抛出的检查异常。
 * 
 * 
 * @since 1.7
 */

public class NotDirectoryException
    extends FileSystemException
{
    private static final long serialVersionUID = -9011457427178200199L;

    /**
     * Constructs an instance of this class.
     *
     * <p>
     *  构造此类的实例。
     * 
     * @param   file
     *          a string identifying the file or {@code null} if not known
     */
    public NotDirectoryException(String file) {
        super(file);
    }
}
