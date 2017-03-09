/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.nio.channels.FileChannel;
import sun.nio.ch.FileChannelImpl;


/**
 * A file output stream is an output stream for writing data to a
 * <code>File</code> or to a <code>FileDescriptor</code>. Whether or not
 * a file is available or may be created depends upon the underlying
 * platform.  Some platforms, in particular, allow a file to be opened
 * for writing by only one <tt>FileOutputStream</tt> (or other
 * file-writing object) at a time.  In such situations the constructors in
 * this class will fail if the file involved is already open.
 *
 * <p><code>FileOutputStream</code> is meant for writing streams of raw bytes
 * such as image data. For writing streams of characters, consider using
 * <code>FileWriter</code>.
 *
 * <p>
 *  文件输出流是用于将数据写入<code> File </code>或<code> FileDescriptor </code>的输出流。文件是否可用或可以创建取决于底层平台。
 * 一些平台特别允许一次只打开一个<tt> FileOutputStream </tt>(或其他文件写入对象)的文件。在这种情况下,如果涉及的文件已经打开,这个类中的构造函数将失败。
 * 
 *  <p> <code> FileOutputStream </code>用于写入原始字节流,如图像数据。对于写入字符流,请考虑使用<code> FileWriter </code>。
 * 
 * 
 * @author  Arthur van Hoff
 * @see     java.io.File
 * @see     java.io.FileDescriptor
 * @see     java.io.FileInputStream
 * @see     java.nio.file.Files#newOutputStream
 * @since   JDK1.0
 */
public
class FileOutputStream extends OutputStream
{
    /**
     * The system dependent file descriptor.
     * <p>
     *  系统相关文件描述符。
     * 
     */
    private final FileDescriptor fd;

    /**
     * True if the file is opened for append.
     * <p>
     *  如果文件已打开以附加,则为true。
     * 
     */
    private final boolean append;

    /**
     * The associated channel, initialized lazily.
     * <p>
     *  相关通道,懒惰初始化。
     * 
     */
    private FileChannel channel;

    /**
     * The path of the referenced file
     * (null if the stream is created with a file descriptor)
     * <p>
     *  引用文件的路径(如果使用文件描述符创建流,则为null)
     * 
     */
    private final String path;

    private final Object closeLock = new Object();
    private volatile boolean closed = false;

    /**
     * Creates a file output stream to write to the file with the
     * specified name. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with <code>name</code> as its argument.
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     *  创建文件输出流以写入具有指定名称的文件。创建一个新的<code> FileDescriptor </code>对象以表示此文件连接。
     * <p>
     *  首先,如果有安全管理器,则以<code> name </code>作为其参数来调用其<code> checkWrite </code>方法。
     * <p>
     *  如果文件存在,但是是一个目录而不是一个常规文件,不存在,但不能创建,或无法打开任何其他原因,那么会抛出一个<code> FileNotFoundException </code>。
     * 
     * 
     * @param      name   the system-dependent filename
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     */
    public FileOutputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null, false);
    }

    /**
     * Creates a file output stream to write to the file with the specified
     * name.  If the second argument is <code>true</code>, then
     * bytes will be written to the end of the file rather than the beginning.
     * A new <code>FileDescriptor</code> object is created to represent this
     * file connection.
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with <code>name</code> as its argument.
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     * 创建文件输出流以写入具有指定名称的文件。如果第二个参数是<code> true </code>,那么字节将被写入文件的结尾而不是开始。
     * 创建一个新的<code> FileDescriptor </code>对象以表示此文件连接。
     * <p>
     *  首先,如果有安全管理器,则以<code> name </code>作为其参数来调用其<code> checkWrite </code>方法。
     * <p>
     *  如果文件存在,但是是一个目录而不是一个常规文件,不存在,但不能创建,或无法打开任何其他原因,那么会抛出一个<code> FileNotFoundException </code>。
     * 
     * 
     * @param     name        the system-dependent file name
     * @param     append      if <code>true</code>, then bytes will be written
     *                   to the end of the file rather than the beginning
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason.
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @since     JDK1.1
     */
    public FileOutputStream(String name, boolean append)
        throws FileNotFoundException
    {
        this(name != null ? new File(name) : null, append);
    }

    /**
     * Creates a file output stream to write to the file represented by
     * the specified <code>File</code> object. A new
     * <code>FileDescriptor</code> object is created to represent this
     * file connection.
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the path represented by the <code>file</code>
     * argument as its argument.
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     *  创建文件输出流以写入由指定的<code> File </code>对象表示的文件。创建一个新的<code> FileDescriptor </code>对象以表示此文件连接。
     * <p>
     *  首先,如果有安全管理器,则以<code> file </code>参数表示的路径作为其参数来调用其<code> checkWrite </code>方法。
     * <p>
     *  如果文件存在,但是是一个目录而不是一个常规文件,不存在,但不能创建,或无法打开任何其他原因,那么会抛出一个<code> FileNotFoundException </code>。
     * 
     * 
     * @param      file               the file to be opened for writing.
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     */
    public FileOutputStream(File file) throws FileNotFoundException {
        this(file, false);
    }

    /**
     * Creates a file output stream to write to the file represented by
     * the specified <code>File</code> object. If the second argument is
     * <code>true</code>, then bytes will be written to the end of the file
     * rather than the beginning. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the path represented by the <code>file</code>
     * argument as its argument.
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     *  创建文件输出流以写入由指定的<code> File </code>对象表示的文件。如果第二个参数是<code> true </code>,那么字节将被写入文件的结尾而不是开始。
     * 创建一个新的<code> FileDescriptor </code>对象以表示此文件连接。
     * <p>
     * 首先,如果有安全管理器,则以<code> file </code>参数表示的路径作为其参数来调用其<code> checkWrite </code>方法。
     * <p>
     *  如果文件存在,但是是一个目录而不是一个常规文件,不存在,但不能创建,或无法打开任何其他原因,那么会抛出一个<code> FileNotFoundException </code>。
     * 
     * 
     * @param      file               the file to be opened for writing.
     * @param     append      if <code>true</code>, then bytes will be written
     *                   to the end of the file rather than the beginning
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @since 1.4
     */
    public FileOutputStream(File file, boolean append)
        throws FileNotFoundException
    {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkWrite(name);
        }
        if (name == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        this.fd = new FileDescriptor();
        fd.attach(this);
        this.append = append;
        this.path = name;

        open(name, append);
    }

    /**
     * Creates a file output stream to write to the specified file
     * descriptor, which represents an existing connection to an actual
     * file in the file system.
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the file descriptor <code>fdObj</code>
     * argument as its argument.
     * <p>
     * If <code>fdObj</code> is null then a <code>NullPointerException</code>
     * is thrown.
     * <p>
     * This constructor does not throw an exception if <code>fdObj</code>
     * is {@link java.io.FileDescriptor#valid() invalid}.
     * However, if the methods are invoked on the resulting stream to attempt
     * I/O on the stream, an <code>IOException</code> is thrown.
     *
     * <p>
     *  创建要写入指定文件描述符的文件输出流,该文件描述符表示与文件系统中实际文件的现有连接。
     * <p>
     *  首先,如果有安全管理器,则以文件描述符<code> fdObj </code>参数作为其参数来调用其<code> checkWrite </code>方法。
     * <p>
     *  如果<code> fdObj </code>为null,则抛出<code> NullPointerException </code>。
     * <p>
     *  如果<code> fdObj </code>是{@link java.io.FileDescriptor#valid()invalid},此构造函数不会抛出异常。
     * 但是,如果在生成的流上调用方法以尝试对流上的I / O,则会抛出<code> IOException </code>。
     * 
     * 
     * @param      fdObj   the file descriptor to be opened for writing
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies
     *               write access to the file descriptor
     * @see        java.lang.SecurityManager#checkWrite(java.io.FileDescriptor)
     */
    public FileOutputStream(FileDescriptor fdObj) {
        SecurityManager security = System.getSecurityManager();
        if (fdObj == null) {
            throw new NullPointerException();
        }
        if (security != null) {
            security.checkWrite(fdObj);
        }
        this.fd = fdObj;
        this.append = false;
        this.path = null;

        fd.attach(this);
    }

    /**
     * Opens a file, with the specified name, for overwriting or appending.
     * <p>
     *  打开具有指定名称的文件,以覆盖或附加。
     * 
     * 
     * @param name name of file to be opened
     * @param append whether the file is to be opened in append mode
     */
    private native void open0(String name, boolean append)
        throws FileNotFoundException;

    // wrap native call to allow instrumentation
    /**
     * Opens a file, with the specified name, for overwriting or appending.
     * <p>
     *  打开具有指定名称的文件,以覆盖或附加。
     * 
     * 
     * @param name name of file to be opened
     * @param append whether the file is to be opened in append mode
     */
    private void open(String name, boolean append)
        throws FileNotFoundException {
        open0(name, append);
    }

    /**
     * Writes the specified byte to this file output stream.
     *
     * <p>
     *  将指定的字节写入此文件输出流。
     * 
     * 
     * @param   b   the byte to be written.
     * @param   append   {@code true} if the write operation first
     *     advances the position to the end of file
     */
    private native void write(int b, boolean append) throws IOException;

    /**
     * Writes the specified byte to this file output stream. Implements
     * the <code>write</code> method of <code>OutputStream</code>.
     *
     * <p>
     *  将指定的字节写入此文件输出流。实现<code> OutputStream </code>的<code> write </code>方法。
     * 
     * 
     * @param      b   the byte to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(int b) throws IOException {
        write(b, append);
    }

    /**
     * Writes a sub array as a sequence of bytes.
     * <p>
     *  将子数组写为字节序列。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @param append {@code true} to first advance the position to the
     *     end of file
     * @exception IOException If an I/O error has occurred.
     */
    private native void writeBytes(byte b[], int off, int len, boolean append)
        throws IOException;

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this file output stream.
     *
     * <p>
     *  从指定的字节数组向此文件输出流写入<code> b.length </code>字节。
     * 
     * 
     * @param      b   the data.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[]) throws IOException {
        writeBytes(b, 0, b.length, append);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this file output stream.
     *
     * <p>
     * 从指定的字节数组开始,从<code> off </code>开始,将<code> len </code>字节写入此文件输出流。
     * 
     * 
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[], int off, int len) throws IOException {
        writeBytes(b, off, len, append);
    }

    /**
     * Closes this file output stream and releases any system resources
     * associated with this stream. This file output stream may no longer
     * be used for writing bytes.
     *
     * <p> If this stream has an associated channel then the channel is closed
     * as well.
     *
     * <p>
     *  关闭此文件输出流,并释放与此流相关联的任何系统资源。此文件输出流可能不再用于写入字节。
     * 
     *  <p>如果此流具有关联的频道,则该频道也会关闭。
     * 
     * 
     * @exception  IOException  if an I/O error occurs.
     *
     * @revised 1.4
     * @spec JSR-51
     */
    public void close() throws IOException {
        synchronized (closeLock) {
            if (closed) {
                return;
            }
            closed = true;
        }

        if (channel != null) {
            channel.close();
        }

        fd.closeAll(new Closeable() {
            public void close() throws IOException {
               close0();
           }
        });
    }

    /**
     * Returns the file descriptor associated with this stream.
     *
     * <p>
     *  返回与此流相关联的文件描述符。
     * 
     * 
     * @return  the <code>FileDescriptor</code> object that represents
     *          the connection to the file in the file system being used
     *          by this <code>FileOutputStream</code> object.
     *
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileDescriptor
     */
     public final FileDescriptor getFD()  throws IOException {
        if (fd != null) {
            return fd;
        }
        throw new IOException();
     }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file output stream.
     *
     * <p> The initial {@link java.nio.channels.FileChannel#position()
     * position} of the returned channel will be equal to the
     * number of bytes written to the file so far unless this stream is in
     * append mode, in which case it will be equal to the size of the file.
     * Writing bytes to this stream will increment the channel's position
     * accordingly.  Changing the channel's position, either explicitly or by
     * writing, will change this stream's file position.
     *
     * <p>
     *  返回与此文件输出流相关联的唯一{@link java.nio.channels.FileChannel FileChannel}对象。
     * 
     *  <p>返回的通道的初始{@link java.nio.channels.FileChannel#position()position}将等于写入文件的字节数,除非此流处于附加模式,在这种情况下它将等于
     * 文件的大小。
     * 将字节写入此流将相应地增加通道的位置。通过显式或书写方式更改频道的位置,将会更改此流的文件位置。
     * 
     * 
     * @return  the file channel associated with this file output stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public FileChannel getChannel() {
        synchronized (this) {
            if (channel == null) {
                channel = FileChannelImpl.open(fd, path, false, true, append, this);
            }
            return channel;
        }
    }

    /**
     * Cleans up the connection to the file, and ensures that the
     * <code>close</code> method of this file output stream is
     * called when there are no more references to this stream.
     *
     * <p>
     * 
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileInputStream#close()
     */
    protected void finalize() throws IOException {
        if (fd != null) {
            if (fd == FileDescriptor.out || fd == FileDescriptor.err) {
                flush();
            } else {
                /* if fd is shared, the references in FileDescriptor
                 * will ensure that finalizer is only called when
                 * safe to do so. All references using the fd have
                 * become unreachable. We can call close()
                 * <p>
                 *  清除与文件的连接,并确保在没有更多对此流的引用时调用此文件输出流的<code> close </code>方法。
                 * 
                 */
                close();
            }
        }
    }

    private native void close0() throws IOException;

    private static native void initIDs();

    static {
        initIDs();
    }

}
