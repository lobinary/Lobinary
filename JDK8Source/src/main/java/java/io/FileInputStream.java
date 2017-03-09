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
 * A <code>FileInputStream</code> obtains input bytes
 * from a file in a file system. What files
 * are  available depends on the host environment.
 *
 * <p><code>FileInputStream</code> is meant for reading streams of raw bytes
 * such as image data. For reading streams of characters, consider using
 * <code>FileReader</code>.
 *
 * <p>
 *  <code> FileInputStream </code>从文件系统中的文件获取输入字节。可用的文件取决于主机环境。
 * 
 *  <p> <code> FileInputStream </code>用于读取原始字节流,例如图像数据。要读取字符流,请考虑使用<code> FileReader </code>。
 * 
 * 
 * @author  Arthur van Hoff
 * @see     java.io.File
 * @see     java.io.FileDescriptor
 * @see     java.io.FileOutputStream
 * @see     java.nio.file.Files#newInputStream
 * @since   JDK1.0
 */
public
class FileInputStream extends InputStream
{
    /* File Descriptor - handle to the open file */
    private final FileDescriptor fd;

    /**
     * The path of the referenced file
     * (null if the stream is created with a file descriptor)
     * <p>
     *  引用文件的路径(如果使用文件描述符创建流,则为null)
     * 
     */
    private final String path;

    private FileChannel channel = null;

    private final Object closeLock = new Object();
    private volatile boolean closed = false;

    /**
     * Creates a <code>FileInputStream</code> by
     * opening a connection to an actual file,
     * the file named by the path name <code>name</code>
     * in the file system.  A new <code>FileDescriptor</code>
     * object is created to represent this file
     * connection.
     * <p>
     * First, if there is a security
     * manager, its <code>checkRead</code> method
     * is called with the <code>name</code> argument
     * as its argument.
     * <p>
     * If the named file does not exist, is a directory rather than a regular
     * file, or for some other reason cannot be opened for reading then a
     * <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     *  通过打开与实际文件的连接来创建<code> FileInputStream </code>,该文件由文件系统中的路径名<code> name </code>命名。
     * 创建一个新的<code> FileDescriptor </code>对象以表示此文件连接。
     * <p>
     *  首先,如果有安全管理器,则以<code> name </code>参数作为其参数来调用其<code> checkRead </code>方法。
     * <p>
     *  如果指定的文件不存在,是一个目录而不是一个常规文件,或者由于某些其他原因不能打开阅读然后一个<code> FileNotFoundException </code>被抛出。
     * 
     * 
     * @param      name   the system-dependent file name.
     * @exception  FileNotFoundException  if the file does not exist,
     *                   is a directory rather than a regular file,
     *                   or for some other reason cannot be opened for
     *                   reading.
     * @exception  SecurityException      if a security manager exists and its
     *               <code>checkRead</code> method denies read access
     *               to the file.
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     */
    public FileInputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null);
    }

    /**
     * Creates a <code>FileInputStream</code> by
     * opening a connection to an actual file,
     * the file named by the <code>File</code>
     * object <code>file</code> in the file system.
     * A new <code>FileDescriptor</code> object
     * is created to represent this file connection.
     * <p>
     * First, if there is a security manager,
     * its <code>checkRead</code> method  is called
     * with the path represented by the <code>file</code>
     * argument as its argument.
     * <p>
     * If the named file does not exist, is a directory rather than a regular
     * file, or for some other reason cannot be opened for reading then a
     * <code>FileNotFoundException</code> is thrown.
     *
     * <p>
     *  通过打开与文件系统中的<code> File </code>对象<code>文件</code>命名的实际文件的连接来创建<code> FileInputStream </code>创建一个新的<code>
     *  FileDescriptor </code>对象以表示此文件连接。
     * <p>
     * 首先,如果有安全管理器,则以<code> file </code>参数表示的路径作为其参数来调用其<code> checkRead </code>方法。
     * <p>
     *  如果指定的文件不存在,是一个目录而不是一个常规文件,或者由于某些其他原因不能打开阅读然后一个<code> FileNotFoundException </code>被抛出。
     * 
     * 
     * @param      file   the file to be opened for reading.
     * @exception  FileNotFoundException  if the file does not exist,
     *                   is a directory rather than a regular file,
     *                   or for some other reason cannot be opened for
     *                   reading.
     * @exception  SecurityException      if a security manager exists and its
     *               <code>checkRead</code> method denies read access to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     */
    public FileInputStream(File file) throws FileNotFoundException {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead(name);
        }
        if (name == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        fd = new FileDescriptor();
        fd.attach(this);
        path = name;
        open(name);
    }

    /**
     * Creates a <code>FileInputStream</code> by using the file descriptor
     * <code>fdObj</code>, which represents an existing connection to an
     * actual file in the file system.
     * <p>
     * If there is a security manager, its <code>checkRead</code> method is
     * called with the file descriptor <code>fdObj</code> as its argument to
     * see if it's ok to read the file descriptor. If read access is denied
     * to the file descriptor a <code>SecurityException</code> is thrown.
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
     *  使用文件描述符<code> fdObj </code>创建<code> FileInputStream </code>,它代表与文件系统中实际文件的现有连接。
     * <p>
     *  如果有安全管理器,则调用其<code> checkRead </code>方法,以文件描述符<code> fdObj </code>作为其参数,看看是否可以读取文件描述符。
     * 如果对文件描述符的读访问被拒绝,则抛出<code> SecurityException </code>。
     * <p>
     *  如果<code> fdObj </code>为null,则抛出<code> NullPointerException </code>。
     * <p>
     *  如果<code> fdObj </code>是{@link java.io.FileDescriptor#valid()invalid},此构造函数不会抛出异常。
     * 但是,如果在生成的流上调用方法以尝试对流上的I / O,则会抛出<code> IOException </code>。
     * 
     * 
     * @param      fdObj   the file descriptor to be opened for reading.
     * @throws     SecurityException      if a security manager exists and its
     *                 <code>checkRead</code> method denies read access to the
     *                 file descriptor.
     * @see        SecurityManager#checkRead(java.io.FileDescriptor)
     */
    public FileInputStream(FileDescriptor fdObj) {
        SecurityManager security = System.getSecurityManager();
        if (fdObj == null) {
            throw new NullPointerException();
        }
        if (security != null) {
            security.checkRead(fdObj);
        }
        fd = fdObj;
        path = null;

        /*
         * FileDescriptor is being shared by streams.
         * Register this stream with FileDescriptor tracker.
         * <p>
         *  FileDescriptor正在由流共享。使用FileDescriptor跟踪器注册此流。
         * 
         */
        fd.attach(this);
    }

    /**
     * Opens the specified file for reading.
     * <p>
     *  打开指定的文件进行读取。
     * 
     * 
     * @param name the name of the file
     */
    private native void open0(String name) throws FileNotFoundException;

    // wrap native call to allow instrumentation
    /**
     * Opens the specified file for reading.
     * <p>
     *  打开指定的文件进行读取。
     * 
     * 
     * @param name the name of the file
     */
    private void open(String name) throws FileNotFoundException {
        open0(name);
    }

    /**
     * Reads a byte of data from this input stream. This method blocks
     * if no input is yet available.
     *
     * <p>
     *  从此输入流读取一个字节的数据。如果没有输入可用,此方法将阻止。
     * 
     * 
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             file is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public int read() throws IOException {
        return read0();
    }

    private native int read0() throws IOException;

    /**
     * Reads a subarray as a sequence of bytes.
     * <p>
     *  将子阵列读为字节序列。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private native int readBytes(byte b[], int off, int len) throws IOException;

    /**
     * Reads up to <code>b.length</code> bytes of data from this input
     * stream into an array of bytes. This method blocks until some input
     * is available.
     *
     * <p>
     * 从此输入流中读取最多<code> b.length </code>字节的数据为字节数组。此方法阻塞,直到某些输入可用。
     * 
     * 
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the file has been reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public int read(byte b[]) throws IOException {
        return readBytes(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream
     * into an array of bytes. If <code>len</code> is not zero, the method
     * blocks until some input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     *
     * <p>
     *  从此输入流中读取最多<code> len </code>字节的数据为字节数组。
     * 如果<code> len </code>不为零,则该方法阻塞,直到某些输入可用;否则,不读取任何字节,并返回<code> 0 </code>。
     * 
     * 
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in the destination array <code>b</code>
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the file has been reached.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception  IOException  if an I/O error occurs.
     */
    public int read(byte b[], int off, int len) throws IOException {
        return readBytes(b, off, len);
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from the
     * input stream.
     *
     * <p>The <code>skip</code> method may, for a variety of
     * reasons, end up skipping over some smaller number of bytes,
     * possibly <code>0</code>. If <code>n</code> is negative, the method
     * will try to skip backwards. In case the backing file does not support
     * backward skip at its current position, an <code>IOException</code> is
     * thrown. The actual number of bytes skipped is returned. If it skips
     * forwards, it returns a positive value. If it skips backwards, it
     * returns a negative value.
     *
     * <p>This method may skip more bytes than what are remaining in the
     * backing file. This produces no exception and the number of bytes skipped
     * may include some number of bytes that were beyond the EOF of the
     * backing file. Attempting to read from the stream after skipping past
     * the end will result in -1 indicating the end of the file.
     *
     * <p>
     *  跳过并丢弃来自输入流的<code> n </code>字节数据。
     * 
     *  <p>由于各种原因,<code> skip </code>方法可能跳过一些较小数量的字节,可能是<code> 0 </code>。如果<code> n </code>为负,则该方法将尝试向后跳过。
     * 如果备份文件不支持在当前位置的向后跳过,则会抛出<code> IOException </code>。将返回实际跳过的字节数。如果它向前跳跃,它返回一个正值。如果它向后跳,它返回一个负值。
     * 
     *  <p>此方法可能会跳过比备份文件中剩余的字节多的字节。这不产生异常,并且跳过的字节数可以包括超出后备文件的EOF的一些数量的字节。跳过结束后尝试从流中读取将导致-1表示文件的结尾。
     * 
     * 
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if n is negative, if the stream does not
     *             support seek, or if an I/O error occurs.
     */
    public native long skip(long n) throws IOException;

    /**
     * Returns an estimate of the number of remaining bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. Returns 0 when the file
     * position is beyond EOF. The next invocation might be the same thread
     * or another thread. A single read or skip of this many bytes will not
     * block, but may read or skip fewer bytes.
     *
     * <p> In some cases, a non-blocking read (or skip) may appear to be
     * blocked when it is merely slow, for example when reading large
     * files over slow networks.
     *
     * <p>
     * 返回此输入流中可以读取(或跳过)的剩余字节数的估计值,而不会通过下次调用此输入流的方法进行阻止。当文件位置超出EOF时,返回0。下一次调用可能是同一个线程或另一个线程。
     * 单个读取或跳过这么多字节不会阻塞,但可以读取或跳过更少的字节。
     * 
     *  <p>在某些情况下,非阻塞读取(或跳过)可能似乎被阻止,当它只是缓慢,例如当通过慢网络读取大文件。
     * 
     * 
     * @return     an estimate of the number of remaining bytes that can be read
     *             (or skipped over) from this input stream without blocking.
     * @exception  IOException  if this file input stream has been closed by calling
     *             {@code close} or an I/O error occurs.
     */
    public native int available() throws IOException;

    /**
     * Closes this file input stream and releases any system resources
     * associated with the stream.
     *
     * <p> If this stream has an associated channel then the channel is closed
     * as well.
     *
     * <p>
     *  关闭此文件输入流,并释放与流相关联的任何系统资源。
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
     * Returns the <code>FileDescriptor</code>
     * object  that represents the connection to
     * the actual file in the file system being
     * used by this <code>FileInputStream</code>.
     *
     * <p>
     *  返回代表与此<code> FileInputStream </code>正在使用的文件系统中实际文件的连接的<code> FileDescriptor </code>对象。
     * 
     * 
     * @return     the file descriptor object associated with this stream.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileDescriptor
     */
    public final FileDescriptor getFD() throws IOException {
        if (fd != null) {
            return fd;
        }
        throw new IOException();
    }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file input stream.
     *
     * <p> The initial {@link java.nio.channels.FileChannel#position()
     * position} of the returned channel will be equal to the
     * number of bytes read from the file so far.  Reading bytes from this
     * stream will increment the channel's position.  Changing the channel's
     * position, either explicitly or by reading, will change this stream's
     * file position.
     *
     * <p>
     *  返回与此文件输入流相关联的唯一{@link java.nio.channels.FileChannel FileChannel}对象。
     * 
     *  <p>返回的通道的初始{@link java.nio.channels.FileChannel#position()position}将等于从文件读取的字节数。从此流读取字节将增加通道的位置。
     * 显式更改或通过读取更改频道的位置,将更改此流的文件位置。
     * 
     * 
     * @return  the file channel associated with this file input stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public FileChannel getChannel() {
        synchronized (this) {
            if (channel == null) {
                channel = FileChannelImpl.open(fd, path, true, false, this);
            }
            return channel;
        }
    }

    private static native void initIDs();

    private native void close0() throws IOException;

    static {
        initIDs();
    }

    /**
     * Ensures that the <code>close</code> method of this file input stream is
     * called when there are no more references to it.
     *
     * <p>
     *  确保当没有更多的引用时,调用此文件输入流的<code> close </code>方法。
     * 
     * 
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileInputStream#close()
     */
    protected void finalize() throws IOException {
        if ((fd != null) &&  (fd != FileDescriptor.in)) {
            /* if fd is shared, the references in FileDescriptor
             * will ensure that finalizer is only called when
             * safe to do so. All references using the fd have
             * become unreachable. We can call close()
             * <p>
             * 将确保仅在安全的情况下调用终结器。使用fd的所有引用都变得无法访问。我们可以调用close()
             */
            close();
        }
    }
}
