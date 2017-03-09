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

import java.nio.ByteBuffer;
import java.util.List;
import java.io.IOException;

/**
 * A file attribute view that provides a view of a file's user-defined
 * attributes, sometimes known as <em>extended attributes</em>. User-defined
 * file attributes are used to store metadata with a file that is not meaningful
 * to the file system. It is primarily intended for file system implementations
 * that support such a capability directly but may be emulated. The details of
 * such emulation are highly implementation specific and therefore not specified.
 *
 * <p> This {@code FileAttributeView} provides a view of a file's user-defined
 * attributes as a set of name/value pairs, where the attribute name is
 * represented by a {@code String}. An implementation may require to encode and
 * decode from the platform or file system representation when accessing the
 * attribute. The value has opaque content. This attribute view defines the
 * {@link #read read} and {@link #write write} methods to read the value into
 * or write from a {@link ByteBuffer}. This {@code FileAttributeView} is not
 * intended for use where the size of an attribute value is larger than {@link
 * Integer#MAX_VALUE}.
 *
 * <p> User-defined attributes may be used in some implementations to store
 * security related attributes so consequently, in the case of the default
 * provider at least, all methods that access user-defined attributes require the
 * {@code RuntimePermission("accessUserDefinedAttributes")} permission when a
 * security manager is installed.
 *
 * <p> The {@link java.nio.file.FileStore#supportsFileAttributeView
 * supportsFileAttributeView} method may be used to test if a specific {@link
 * java.nio.file.FileStore FileStore} supports the storage of user-defined
 * attributes.
 *
 * <p> Where dynamic access to file attributes is required, the {@link
 * java.nio.file.Files#getAttribute getAttribute} method may be used to read
 * the attribute value. The attribute value is returned as a byte array (byte[]).
 * The {@link java.nio.file.Files#setAttribute setAttribute} method may be used
 * to write the value of a user-defined attribute from a buffer (as if by
 * invoking the {@link #write write} method), or byte array (byte[]).
 *
 * <p>
 *  文件属性视图,提供文件的用户定义属性视图,有时称为<em>扩展属性</em>。用户定义的文件属性用于使用对文件系统无意义的文件存储元数据。它主要用于直接支持这种能力但可以被仿真的文件系统实现。
 * 这种仿真的细节高度实现特定并且因此没有指定。
 * 
 *  <p>此{@code FileAttributeView}提供文件的用户定义属性的视图,作为一组名称/值对,其中属性名称由{@code String}表示。
 * 实现可能需要在访问属性时从平台或文件系统表示进行编码和解码。该值具有不透明内容。
 * 此属性视图定义{@link #read read}和{@link #write write}方法来将值读入或写入{@link ByteBuffer}。
 * 此{@code FileAttributeView}不适用于属性值的大小大于{@link Integer#MAX_VALUE}的情况。
 * 
 * 在一些实现中可以使用用户定义的属性来存储安全相关的属性,因此,在至少是默认提供者的情况下,访问用户定义的属性的所有方法都需要{@code RuntimePermission("accessUserDefinedAttributes") }
 * 权限。
 * 
 *  <p> {@link java.nio.file.FileStore#supportsFileAttributeView supportsFileAttributeView}方法可用于测试特定{@link java.nio.file.FileStore FileStore}
 * 是否支持用户定义属性的存储。
 * 
 *  <p>在需要对文件属性进行动态访问时,可以使用{@link java.nio.file.Files#getAttribute getAttribute}方法来读取属性值。
 * 属性值作为字节数组返回(byte [])。
 *  {@link java.nio.file.Files#setAttribute setAttribute}方法可用于从缓冲区写入用户定义属性的值(如通过调用{@link #write write}方法
 * )或字节array(byte [])。
 * 属性值作为字节数组返回(byte [])。
 * 
 * 
 * @since 1.7
 */

public interface UserDefinedFileAttributeView
    extends FileAttributeView
{
    /**
     * Returns the name of this attribute view. Attribute views of this type
     * have the name {@code "user"}.
     * <p>
     *  返回此属性视图的名称。此类型的属性视图名称为{@code"user"}。
     * 
     */
    @Override
    String name();

    /**
     * Returns a list containing the names of the user-defined attributes.
     *
     * <p>
     *  返回包含用户定义属性名称的列表。
     * 
     * 
     * @return  An unmodifiable list containing the names of the file's
     *          user-defined
     *
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserDefinedAttributes")</tt>
     *          or its {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     */
    List<String> list() throws IOException;

    /**
     * Returns the size of the value of a user-defined attribute.
     *
     * <p>
     *  返回用户定义属性的值的大小。
     * 
     * 
     * @param   name
     *          The attribute name
     *
     * @return  The size of the attribute value, in bytes.
     *
     * @throws  ArithmeticException
     *          If the size of the attribute is larger than {@link Integer#MAX_VALUE}
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserDefinedAttributes")</tt>
     *          or its {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     */
    int size(String name) throws IOException;

    /**
     * Read the value of a user-defined attribute into a buffer.
     *
     * <p> This method reads the value of the attribute into the given buffer
     * as a sequence of bytes, failing if the number of bytes remaining in
     * the buffer is insufficient to read the complete attribute value. The
     * number of bytes transferred into the buffer is {@code n}, where {@code n}
     * is the size of the attribute value. The first byte in the sequence is at
     * index {@code p} and the last byte is at index {@code p + n - 1}, where
     * {@code p} is the buffer's position. Upon return the buffer's position
     * will be equal to {@code p + n}; its limit will not have changed.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to read a file's MIME type that is stored as a user-defined
     * attribute with the name "{@code user.mimetype}".
     * <pre>
     *    UserDefinedFileAttributeView view =
     *        Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
     *    String name = "user.mimetype";
     *    ByteBuffer buf = ByteBuffer.allocate(view.size(name));
     *    view.read(name, buf);
     *    buf.flip();
     *    String value = Charset.defaultCharset().decode(buf).toString();
     * </pre>
     *
     * <p>
     *  将用户定义属性的值读入缓冲区。
     * 
     * <p>此方法将属性的值作为字节序列读入给定缓冲区,如果缓冲区中剩余的字节数不足以读取完整属性值,则会失败。传输到缓冲区的字节数为{@code n},其中{@code n}是属性值的大小。
     * 序列中的第一个字节位于索引{@code p},最后一个字节位于索引{@code p + n  -  1},其中{@code p}是缓冲区的位置。
     * 返回时,缓冲区的位置将等于{@code p + n};其极限不会改变。
     * 
     *  <p> <b>用法示例：</b>假设我们要读取存储为用户定义属性的文件的MIME类型,名称为"{@code user.mimetype}"。
     * <pre>
     *  UserDefinedFileAttributeView view = Files.getFileAttributeView(path,UserDefinedFileAttributeView.cla
     * ss); String name ="user.mimetype"; ByteBuffer buf = ByteBuffer.allocate(view.size(name)); view.read(n
     * ame,buf); buf.flip(); String value = Charset.defaultCharset()。
     * decode(buf).toString();。
     * </pre>
     * 
     * 
     * @param   name
     *          The attribute name
     * @param   dst
     *          The destination buffer
     *
     * @return  The number of bytes read, possibly zero
     *
     * @throws  IllegalArgumentException
     *          If the destination buffer is read-only
     * @throws  IOException
     *          If an I/O error occurs or there is insufficient space in the
     *          destination buffer for the attribute value
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserDefinedAttributes")</tt>
     *          or its {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     *
     * @see #size
     */
    int read(String name, ByteBuffer dst) throws IOException;

    /**
     * Writes the value of a user-defined attribute from a buffer.
     *
     * <p> This method writes the value of the attribute from a given buffer as
     * a sequence of bytes. The size of the value to transfer is {@code r},
     * where {@code r} is the number of bytes remaining in the buffer, that is
     * {@code src.remaining()}. The sequence of bytes is transferred from the
     * buffer starting at index {@code p}, where {@code p} is the buffer's
     * position. Upon return, the buffer's position will be equal to {@code
     * p + n}, where {@code n} is the number of bytes transferred; its limit
     * will not have changed.
     *
     * <p> If an attribute of the given name already exists then its value is
     * replaced. If the attribute does not exist then it is created. If it
     * implementation specific if a test to check for the existence of the
     * attribute and the creation of attribute are atomic with respect to other
     * file system activities.
     *
     * <p> Where there is insufficient space to store the attribute, or the
     * attribute name or value exceed an implementation specific maximum size
     * then an {@code IOException} is thrown.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to write a file's MIME type as a user-defined attribute:
     * <pre>
     *    UserDefinedFileAttributeView view =
     *        FIles.getFileAttributeView(path, UserDefinedFileAttributeView.class);
     *    view.write("user.mimetype", Charset.defaultCharset().encode("text/html"));
     * </pre>
     *
     * <p>
     *  从缓冲区写入用户定义属性的值。
     * 
     * <p>此方法将来自给定缓冲区的属性值作为字节序列写入。要传输的值的大小是{@code r}​​,其中{@code r}​​是缓冲区中剩余的字节数,即{@code src.remaining()}。
     * 字节序列从缓冲区开始从索引{@code p}传输,其中{@code p}是缓冲区的位置。返回时,缓冲区的位置将等于{@code p + n},其中{@code n}是传输的字节数;其极限不会改变。
     * 
     *  <p>如果给定名称的属性已存在,则替换其值。如果属性不存在,则创建它。如果它的实现特定如果检查属性的存在的测试和属性的创建相对于其他文件系统活动是原子的。
     * 
     *  <p>如果没有足够的空间来存储属性,或属性名称或值超过实现特定的最大大小,则会抛出{@code IOException}。
     * 
     *  <p> <b>用法示例：</b>假设我们要将文件的MIME类型写为用户定义的属性：
     * 
     * @param   name
     *          The attribute name
     * @param   src
     *          The buffer containing the attribute value
     *
     * @return  The number of bytes written, possibly zero
     *
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserDefinedAttributes")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file.
     */
    int write(String name, ByteBuffer src) throws IOException;

    /**
     * Deletes a user-defined attribute.
     *
     * <p>
     * <pre>
     *  UserDefinedFileAttributeView view = FIles.getFileAttributeView(path,UserDefinedFileAttributeView.cla
     * ss); view.write("user.mimetype",Charset.defaultCharset()。
     * encode("text / html"));。
     * </pre>
     * 
     * @param   name
     *          The attribute name
     *
     * @throws  IOException
     *          If an I/O error occurs or the attribute does not exist
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserDefinedAttributes")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file.
     */
    void delete(String name) throws IOException;
}
