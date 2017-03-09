/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file.attribute;

/**
 * File attributes associated with a file in a file system that supports
 * legacy "DOS" attributes.
 *
 * <p> <b>Usage Example:</b>
 * <pre>
 *    Path file = ...
 *    DosFileAttributes attrs = Files.readAttributes(file, DosFileAttributes.class);
 * </pre>
 *
 * <p>
 *  与支持传统"DOS"属性的文件系统中的文件相关联的文件属性。
 * 
 *  <p> <b>使用示例：</b>
 * <pre>
 *  Path file = ... DosFileAttributes attrs = Files.readAttributes(file,DosFileAttributes.class);
 * </pre>
 * 
 * 
 * @since 1.7
 */

public interface DosFileAttributes
    extends BasicFileAttributes
{
    /**
     * Returns the value of the read-only attribute.
     *
     * <p> This attribute is often used as a simple access control mechanism
     * to prevent files from being deleted or updated. Whether the file system
     * or platform does any enforcement to prevent <em>read-only</em> files
     * from being updated is implementation specific.
     *
     * <p>
     *  返回只读属性的值。
     * 
     *  <p>此属性通常用作简单的访问控制机制,以防止文件被删除或更新。文件系统或平台是否实施以防止更新只读</em>文件是实施特定的。
     * 
     * 
     * @return  the value of the read-only attribute
     */
    boolean isReadOnly();

    /**
     * Returns the value of the hidden attribute.
     *
     * <p> This attribute is often used to indicate if the file is visible to
     * users.
     *
     * <p>
     *  返回隐藏属性的值。
     * 
     *  <p>此属性通常用于指示文件是否对用户可见。
     * 
     * 
     * @return  the value of the hidden attribute
     */
    boolean isHidden();

    /**
     * Returns the value of the archive attribute.
     *
     * <p> This attribute is typically used by backup programs.
     *
     * <p>
     *  返回归档属性的值。
     * 
     *  <p>此属性通常由备份程序使用。
     * 
     * 
     * @return  the value of the archive attribute
     */
    boolean isArchive();

    /**
     * Returns the value of the system attribute.
     *
     * <p> This attribute is often used to indicate that the file is a component
     * of the operating system.
     *
     * <p>
     *  返回系统属性的值。
     * 
     * 
     * @return  the value of the system attribute
     */
    boolean isSystem();
}
