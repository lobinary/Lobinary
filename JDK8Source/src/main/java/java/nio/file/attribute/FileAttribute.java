/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * An object that encapsulates the value of a file attribute that can be set
 * atomically when creating a new file or directory by invoking the {@link
 * java.nio.file.Files#createFile createFile} or {@link
 * java.nio.file.Files#createDirectory createDirectory} methods.
 *
 * <p>
 *  一个对象,它通过调用{@link java.nio.file.Files#createFile createFile}或{@link java.nio.file.Files}来封装一个文件属性的值,该
 * 属性可以在创建新文件或目录时以原子方式设置#createDirectory createDirectory}方法。
 * 
 * 
 * @param <T> The type of the file attribute value
 *
 * @since 1.7
 * @see PosixFilePermissions#asFileAttribute
 */

public interface FileAttribute<T> {
    /**
     * Returns the attribute name.
     *
     * <p>
     *  返回属性名称。
     * 
     * 
     * @return The attribute name
     */
    String name();

    /**
     * Returns the attribute value.
     *
     * <p>
     *  返回属性值。
     * 
     * @return The attribute value
     */
    T value();
}
