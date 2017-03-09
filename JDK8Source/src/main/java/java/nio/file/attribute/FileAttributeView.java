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
 * An attribute view that is a read-only or updatable view of non-opaque
 * values associated with a file in a filesystem. This interface is extended or
 * implemented by specific file attribute views that define methods to read
 * and/or update the attributes of a file.
 *
 * <p>
 *  属性视图,它是与文件系统中的文件相关联的非不透明值的只读或可更新视图。此接口由特定文件属性视图扩展或实现,该视图定义读取和/或更新文件属性的方法。
 * 
 * @since 1.7
 *
 * @see java.nio.file.Files#getFileAttributeView(Path,Class,java.nio.file.LinkOption[])
 */

public interface FileAttributeView
    extends AttributeView
{
}
