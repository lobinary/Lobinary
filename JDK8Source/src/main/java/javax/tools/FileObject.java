/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

/**
 * File abstraction for tools.  In this context, <em>file</em> means
 * an abstraction of regular files and other sources of data.  For
 * example, a file object can be used to represent regular files,
 * memory cache, or data in databases.
 *
 * <p>All methods in this interface might throw a SecurityException if
 * a security exception occurs.
 *
 * <p>Unless explicitly allowed, all methods in this interface might
 * throw a NullPointerException if given a {@code null} argument.
 *
 * <p>
 *  工具的文件抽象。在此上下文中,<em> </em>文件意味着常规文件和其他数据源的抽象。例如,文件对象可以用于表示常规文件,内存缓存或数据库中的数据。
 * 
 *  <p>如果发生安全性异常,此接口中的所有方法都可能抛出SecurityException。
 * 
 *  <p>除非明确允许,否则如果给定{@code null}参数,此接口中的所有方法都可能抛出NullPointerException。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface FileObject {

    /**
     * Returns a URI identifying this file object.
     * <p>
     *  返回标识此文件对象的URI。
     * 
     * 
     * @return a URI
     */
    URI toUri();

    /**
     * Gets a user-friendly name for this file object.  The exact
     * value returned is not specified but implementations should take
     * care to preserve names as given by the user.  For example, if
     * the user writes the filename {@code "BobsApp\Test.java"} on
     * the command line, this method should return {@code
     * "BobsApp\Test.java"} whereas the {@linkplain #toUri toUri}
     * method might return {@code
     * file:///C:/Documents%20and%20Settings/UncleBob/BobsApp/Test.java}.
     *
     * <p>
     *  为此文件对象获取一个用户友好的名称。返回的确切值未指定,但实现应注意保留用户提供的名称。
     * 例如,如果用户在命令行上输入文件名{@code"BobsApp \ Test.java"},则此方法应返回{@code"BobsApp \ Test.java"},而{@linkplain #toUri toUri}
     * 可能会返回{@code file：/// C：/Documents%20and%20Settings/UncleBob/BobsApp/Test.java}。
     *  为此文件对象获取一个用户友好的名称。返回的确切值未指定,但实现应注意保留用户提供的名称。
     * 
     * 
     * @return a user-friendly name
     */
    String getName();

    /**
     * Gets an InputStream for this file object.
     *
     * <p>
     *  获取此文件对象的InputStream。
     * 
     * 
     * @return an InputStream
     * @throws IllegalStateException if this file object was
     * opened for writing and does not support reading
     * @throws UnsupportedOperationException if this kind of file
     * object does not support byte access
     * @throws IOException if an I/O error occurred
     */
    InputStream openInputStream() throws IOException;

    /**
     * Gets an OutputStream for this file object.
     *
     * <p>
     *  获取此文件对象的OutputStream。
     * 
     * 
     * @return an OutputStream
     * @throws IllegalStateException if this file object was
     * opened for reading and does not support writing
     * @throws UnsupportedOperationException if this kind of
     * file object does not support byte access
     * @throws IOException if an I/O error occurred
     */
    OutputStream openOutputStream() throws IOException;

    /**
     * Gets a reader for this object.  The returned reader will
     * replace bytes that cannot be decoded with the default
     * translation character.  In addition, the reader may report a
     * diagnostic unless {@code ignoreEncodingErrors} is true.
     *
     * <p>
     *  获取此对象的阅读器。返回的读取器将替换不能使用默认翻译字符解码的字节。此外,除非{@code ignoreEncodingErrors}为真,否则读者可以报告诊断。
     * 
     * 
     * @param ignoreEncodingErrors ignore encoding errors if true
     * @return a Reader
     * @throws IllegalStateException if this file object was
     * opened for writing and does not support reading
     * @throws UnsupportedOperationException if this kind of
     * file object does not support character access
     * @throws IOException if an I/O error occurred
     */
    Reader openReader(boolean ignoreEncodingErrors) throws IOException;

    /**
     * Gets the character content of this file object, if available.
     * Any byte that cannot be decoded will be replaced by the default
     * translation character.  In addition, a diagnostic may be
     * reported unless {@code ignoreEncodingErrors} is true.
     *
     * <p>
     * 获取此文件对象的字符内容(如果可用)。任何无法解码的字节将被默认的翻译字符替换。此外,除非{@code ignoreEncodingErrors}为真,否则可能会报告诊断。
     * 
     * 
     * @param ignoreEncodingErrors ignore encoding errors if true
     * @return a CharSequence if available; {@code null} otherwise
     * @throws IllegalStateException if this file object was
     * opened for writing and does not support reading
     * @throws UnsupportedOperationException if this kind of
     * file object does not support character access
     * @throws IOException if an I/O error occurred
     */
    CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException;

    /**
     * Gets a Writer for this file object.
     *
     * <p>
     *  获取此文件对象的Writer。
     * 
     * 
     * @return a Writer
     * @throws IllegalStateException if this file object was
     * opened for reading and does not support writing
     * @throws UnsupportedOperationException if this kind of
     * file object does not support character access
     * @throws IOException if an I/O error occurred
     */
    Writer openWriter() throws IOException;

    /**
     * Gets the time this file object was last modified.  The time is
     * measured in milliseconds since the epoch (00:00:00 GMT, January
     * 1, 1970).
     *
     * <p>
     *  获取此文件对象上次修改的时间。时间以自纪元(1970年1月1日,格林尼治时间00:00:00)之后的毫秒计量。
     * 
     * 
     * @return the time this file object was last modified; or 0 if
     * the file object does not exist, if an I/O error occurred, or if
     * the operation is not supported
     */
    long getLastModified();

    /**
     * Deletes this file object.  In case of errors, returns false.
     * <p>
     *  删除此文件对象。如果出现错误,返回false。
     * 
     * @return true if and only if this file object is successfully
     * deleted; false otherwise
     */
    boolean delete();

}
