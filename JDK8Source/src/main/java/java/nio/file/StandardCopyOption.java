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
 * Defines the standard copy options.
 *
 * <p>
 *  定义标准复制选项。
 * 
 * 
 * @since 1.7
 */

public enum StandardCopyOption implements CopyOption {
    /**
     * Replace an existing file if it exists.
     * <p>
     *  替换现有文件(如果存在)。
     * 
     */
    REPLACE_EXISTING,
    /**
     * Copy attributes to the new file.
     * <p>
     *  将属性复制到新文件。
     * 
     */
    COPY_ATTRIBUTES,
    /**
     * Move the file as an atomic file system operation.
     * <p>
     *  将文件作为原子文件系统操作移动。
     */
    ATOMIC_MOVE;
}
