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
 * Instances of this class support both reading and writing to a
 * random access file. A random access file behaves like a large
 * array of bytes stored in the file system. There is a kind of cursor,
 * or index into the implied array, called the <em>file pointer</em>;
 * input operations read bytes starting at the file pointer and advance
 * the file pointer past the bytes read. If the random access file is
 * created in read/write mode, then output operations are also available;
 * output operations write bytes starting at the file pointer and advance
 * the file pointer past the bytes written. Output operations that write
 * past the current end of the implied array cause the array to be
 * extended. The file pointer can be read by the
 * {@code getFilePointer} method and set by the {@code seek}
 * method.
 * <p>
 * It is generally true of all the reading routines in this class that
 * if end-of-file is reached before the desired number of bytes has been
 * read, an {@code EOFException} (which is a kind of
 * {@code IOException}) is thrown. If any byte cannot be read for
 * any reason other than end-of-file, an {@code IOException} other
 * than {@code EOFException} is thrown. In particular, an
 * {@code IOException} may be thrown if the stream has been closed.
 *
 * <p>
 *  此类的实例支持读取和写入随机访问文件。随机访问文件的行为类似于存储在文件系统中的大字节数组。
 * 有一种游标,或索引到隐含数组,称为<em>文件指针</em>;输入操作从文件指针开始读取字节,并使文件指针超过读取的字节。
 * 如果随机存取文件在读/写模式下创建,则输出操作也可用;输出操作从文件指针开始写入字节,并使文件指针超过写入的字节。写入超过隐含数组的当前结尾的输出操作导致数组被扩展。
 * 文件指针可以通过{@code getFilePointer}方法读取,并由{@code seek}方法设置。
 * <p>
 *  在这个类中的所有读取例程通常都是这样的,如果在读取所需字节数之前达到文件结尾,则{@code EOFException}(这是一种{@code IOException})是抛出。
 * 如果由于文件结尾之外的任何原因而无法读取任何字节,则会抛出除{@code EOFException}之外的{@code IOException}。
 * 特别是,如果流已经关闭,可能会抛出{@code IOException}。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */

public class RandomAccessFile implements DataOutput, DataInput, Closeable {

    private FileDescriptor fd;
    private FileChannel channel = null;
    private boolean rw;

    /**
     * The path of the referenced file
     * (null if the stream is created with a file descriptor)
     * <p>
     *  引用文件的路径(如果使用文件描述符创建流,则为null)
     * 
     */
    private final String path;

    private Object closeLock = new Object();
    private volatile boolean closed = false;

    private static final int O_RDONLY = 1;
    private static final int O_RDWR =   2;
    private static final int O_SYNC =   4;
    private static final int O_DSYNC =  8;

    /**
     * Creates a random access file stream to read from, and optionally
     * to write to, a file with the specified name. A new
     * {@link FileDescriptor} object is created to represent the
     * connection to the file.
     *
     * <p> The <tt>mode</tt> argument specifies the access mode with which the
     * file is to be opened.  The permitted values and their meanings are as
     * specified for the <a
     * href="#mode"><tt>RandomAccessFile(File,String)</tt></a> constructor.
     *
     * <p>
     * If there is a security manager, its {@code checkRead} method
     * is called with the {@code name} argument
     * as its argument to see if read access to the file is allowed.
     * If the mode allows writing, the security manager's
     * {@code checkWrite} method
     * is also called with the {@code name} argument
     * as its argument to see if write access to the file is allowed.
     *
     * <p>
     * 创建随机访问文件流以从指定名称读取并可选地写入文件。将创建一个新的{@link FileDescriptor}对象以表示与文件的连接。
     * 
     *  <p> <tt> mode </tt>参数指定要打开文件的访问模式。
     * 允许的值及其含义是为<a href="#mode"> <tt> RandomAccessFile(File,String)</tt> </a>构造函数指定的值。
     * 
     * <p>
     *  如果有安全管理器,则会调用其{@code checkRead}方法,并使用{@code name}参数作为其参数,以查看是否允许对文件的读取访问。
     * 如果模式允许写入,则还会调用安全管理器的{@code checkWrite}方法,并使用{@code name}参数作为其参数,以查看是否允许对文件的写访问。
     * 
     * 
     * @param      name   the system-dependent filename
     * @param      mode   the access <a href="#mode">mode</a>
     * @exception  IllegalArgumentException  if the mode argument is not equal
     *               to one of <tt>"r"</tt>, <tt>"rw"</tt>, <tt>"rws"</tt>, or
     *               <tt>"rwd"</tt>
     * @exception FileNotFoundException
     *            if the mode is <tt>"r"</tt> but the given string does not
     *            denote an existing regular file, or if the mode begins with
     *            <tt>"rw"</tt> but the given string does not denote an
     *            existing, writable regular file and a new regular file of
     *            that name cannot be created, or if some other error occurs
     *            while opening or creating the file
     * @exception  SecurityException         if a security manager exists and its
     *               {@code checkRead} method denies read access to the file
     *               or the mode is "rw" and the security manager's
     *               {@code checkWrite} method denies write access to the file
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @revised 1.4
     * @spec JSR-51
     */
    public RandomAccessFile(String name, String mode)
        throws FileNotFoundException
    {
        this(name != null ? new File(name) : null, mode);
    }

    /**
     * Creates a random access file stream to read from, and optionally to
     * write to, the file specified by the {@link File} argument.  A new {@link
     * FileDescriptor} object is created to represent this file connection.
     *
     * <p>The <a name="mode"><tt>mode</tt></a> argument specifies the access mode
     * in which the file is to be opened.  The permitted values and their
     * meanings are:
     *
     * <table summary="Access mode permitted values and meanings">
     * <tr><th align="left">Value</th><th align="left">Meaning</th></tr>
     * <tr><td valign="top"><tt>"r"</tt></td>
     *     <td> Open for reading only.  Invoking any of the <tt>write</tt>
     *     methods of the resulting object will cause an {@link
     *     java.io.IOException} to be thrown. </td></tr>
     * <tr><td valign="top"><tt>"rw"</tt></td>
     *     <td> Open for reading and writing.  If the file does not already
     *     exist then an attempt will be made to create it. </td></tr>
     * <tr><td valign="top"><tt>"rws"</tt></td>
     *     <td> Open for reading and writing, as with <tt>"rw"</tt>, and also
     *     require that every update to the file's content or metadata be
     *     written synchronously to the underlying storage device.  </td></tr>
     * <tr><td valign="top"><tt>"rwd"&nbsp;&nbsp;</tt></td>
     *     <td> Open for reading and writing, as with <tt>"rw"</tt>, and also
     *     require that every update to the file's content be written
     *     synchronously to the underlying storage device. </td></tr>
     * </table>
     *
     * The <tt>"rws"</tt> and <tt>"rwd"</tt> modes work much like the {@link
     * java.nio.channels.FileChannel#force(boolean) force(boolean)} method of
     * the {@link java.nio.channels.FileChannel} class, passing arguments of
     * <tt>true</tt> and <tt>false</tt>, respectively, except that they always
     * apply to every I/O operation and are therefore often more efficient.  If
     * the file resides on a local storage device then when an invocation of a
     * method of this class returns it is guaranteed that all changes made to
     * the file by that invocation will have been written to that device.  This
     * is useful for ensuring that critical information is not lost in the
     * event of a system crash.  If the file does not reside on a local device
     * then no such guarantee is made.
     *
     * <p>The <tt>"rwd"</tt> mode can be used to reduce the number of I/O
     * operations performed.  Using <tt>"rwd"</tt> only requires updates to the
     * file's content to be written to storage; using <tt>"rws"</tt> requires
     * updates to both the file's content and its metadata to be written, which
     * generally requires at least one more low-level I/O operation.
     *
     * <p>If there is a security manager, its {@code checkRead} method is
     * called with the pathname of the {@code file} argument as its
     * argument to see if read access to the file is allowed.  If the mode
     * allows writing, the security manager's {@code checkWrite} method is
     * also called with the path argument to see if write access to the file is
     * allowed.
     *
     * <p>
     *  创建一个随机访问文件流,以从{@link File}参数指定的文件读取并可选地写入文件。创建一个新的{@link FileDescriptor}对象以表示此文件连接。
     * 
     *  <p> <a name="mode"> <tt> mode </tt> </a>参数指定要打开文件的访问方式。允许的值及其含义是：
     * 
     * <table summary="Access mode permitted values and meanings">
     * <tr> <th> <td valign ="top"> <tt>"r"</span> </span> </tt> </td> <td>仅供阅读打开。
     * 调用生成对象的任何<tt> write </tt>方法将导致抛出{@link java.io.IOException}。
     *  </td> </td> <td> </td> </td> </td>如果文件不存在,则将尝试创建它。
     *  </td> </td> <td> <tt> <rt> <td> <td> <tt> </tt>,并且还要求对文件内容或元数据的每次更新都与底层存储设备同步写入。
     *  </td> </tt> </td> </tt> </td> <tt> <tt> >"rw"</tt>,并且还要求对文件内容的每次更新都与底层存储设备同步写入。 </td> </tr>。
     * </table>
     * 
     * <tt>"rws"</tt>和<tt>"rwd"</tt>模式与{@link java.nio.channels.FileChannel#force(boolean)force(boolean)}方法{@link java.nio.channels.FileChannel}
     * 类,分别传递<tt> true </tt>和<tt> false </tt>的参数,除了它们始终适用于每个I / O操作,因此往往效率更高。
     * 如果文件驻留在本地存储设备上,那么当调用此类的方法时,将保证该调用对文件所做的所有更改都已写入该设备。这有助于确保关键信息在系统崩溃的情况下不会丢失。如果文件不驻留在本地设备上,则不进行此类保证。
     * 
     *  <p> <tt>"rwd"</tt>模式可用于减少执行的I / O操作数。
     * 使用<tt>"rwd"</tt>只需要更新文件内容以写入存储;使用<tt>"rws"</tt>需要更新文件的内容及其要写入的元数据,这通常需要至少一个低级I / O操作。
     * 
     *  <p>如果有安全管理器,则会调用其{@code checkRead}方法,并使用{@code file}参数的路径名作为其参数,以查看是否允许对文件的读取访问。
     * 如果模式允许写入,则还会使用path参数调用安全管理器的{@code checkWrite}方法,以查看是否允许对文件的写访问。
     * 
     * 
     * @param      file   the file object
     * @param      mode   the access mode, as described
     *                    <a href="#mode">above</a>
     * @exception  IllegalArgumentException  if the mode argument is not equal
     *               to one of <tt>"r"</tt>, <tt>"rw"</tt>, <tt>"rws"</tt>, or
     *               <tt>"rwd"</tt>
     * @exception FileNotFoundException
     *            if the mode is <tt>"r"</tt> but the given file object does
     *            not denote an existing regular file, or if the mode begins
     *            with <tt>"rw"</tt> but the given file object does not denote
     *            an existing, writable regular file and a new regular file of
     *            that name cannot be created, or if some other error occurs
     *            while opening or creating the file
     * @exception  SecurityException         if a security manager exists and its
     *               {@code checkRead} method denies read access to the file
     *               or the mode is "rw" and the security manager's
     *               {@code checkWrite} method denies write access to the file
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @see        java.nio.channels.FileChannel#force(boolean)
     * @revised 1.4
     * @spec JSR-51
     */
    public RandomAccessFile(File file, String mode)
        throws FileNotFoundException
    {
        String name = (file != null ? file.getPath() : null);
        int imode = -1;
        if (mode.equals("r"))
            imode = O_RDONLY;
        else if (mode.startsWith("rw")) {
            imode = O_RDWR;
            rw = true;
            if (mode.length() > 2) {
                if (mode.equals("rws"))
                    imode |= O_SYNC;
                else if (mode.equals("rwd"))
                    imode |= O_DSYNC;
                else
                    imode = -1;
            }
        }
        if (imode < 0)
            throw new IllegalArgumentException("Illegal mode \"" + mode
                                               + "\" must be one of "
                                               + "\"r\", \"rw\", \"rws\","
                                               + " or \"rwd\"");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead(name);
            if (rw) {
                security.checkWrite(name);
            }
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
        open(name, imode);
    }

    /**
     * Returns the opaque file descriptor object associated with this
     * stream.
     *
     * <p>
     * 返回与此流相关联的不透明文件描述符对象。
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
     * object associated with this file.
     *
     * <p> The {@link java.nio.channels.FileChannel#position()
     * position} of the returned channel will always be equal to
     * this object's file-pointer offset as returned by the {@link
     * #getFilePointer getFilePointer} method.  Changing this object's
     * file-pointer offset, whether explicitly or by reading or writing bytes,
     * will change the position of the channel, and vice versa.  Changing the
     * file's length via this object will change the length seen via the file
     * channel, and vice versa.
     *
     * <p>
     *  返回与此文件关联的唯一{@link java.nio.channels.FileChannel FileChannel}对象。
     * 
     *  <p>返回的通道的{@link java.nio.channels.FileChannel#position()position}将始终等于此对象的{@link #getFilePointer getFilePointer}
     * 方法返回的文件指针偏移量。
     * 更改此对象的文件指针偏移量,无论是明确地还是通过读取或写入字节,都将更改通道的位置,反之亦然。通过此对象更改文件的长度将更改通过文件通道看到的长度,反之亦然。
     * 
     * 
     * @return  the file channel associated with this file
     *
     * @since 1.4
     * @spec JSR-51
     */
    public final FileChannel getChannel() {
        synchronized (this) {
            if (channel == null) {
                channel = FileChannelImpl.open(fd, path, true, rw, this);
            }
            return channel;
        }
    }

    /**
     * Opens a file and returns the file descriptor.  The file is
     * opened in read-write mode if the O_RDWR bit in {@code mode}
     * is true, else the file is opened as read-only.
     * If the {@code name} refers to a directory, an IOException
     * is thrown.
     *
     * <p>
     *  打开文件并返回文件描述符。如果{@code mode}中的O_RDWR位为真,则该文件以读写模式打开,否则文件以只读方式打开。如果{@code name}引用一个目录,则抛出IOException。
     * 
     * 
     * @param name the name of the file
     * @param mode the mode flags, a combination of the O_ constants
     *             defined above
     */
    private native void open0(String name, int mode)
        throws FileNotFoundException;

    // wrap native call to allow instrumentation
    /**
     * Opens a file and returns the file descriptor.  The file is
     * opened in read-write mode if the O_RDWR bit in {@code mode}
     * is true, else the file is opened as read-only.
     * If the {@code name} refers to a directory, an IOException
     * is thrown.
     *
     * <p>
     *  打开文件并返回文件描述符。如果{@code mode}中的O_RDWR位为真,则该文件以读写模式打开,否则文件以只读方式打开。如果{@code name}引用一个目录,则抛出IOException。
     * 
     * 
     * @param name the name of the file
     * @param mode the mode flags, a combination of the O_ constants
     *             defined above
     */
    private void open(String name, int mode)
        throws FileNotFoundException {
        open0(name, mode);
    }

    // 'Read' primitives

    /**
     * Reads a byte of data from this file. The byte is returned as an
     * integer in the range 0 to 255 ({@code 0x00-0x0ff}). This
     * method blocks if no input is yet available.
     * <p>
     * Although {@code RandomAccessFile} is not a subclass of
     * {@code InputStream}, this method behaves in exactly the same
     * way as the {@link InputStream#read()} method of
     * {@code InputStream}.
     *
     * <p>
     *  从此文件读取一个字节的数据。该字节以0到255范围内的整数返回({@code 0x00-0x0ff})。如果没有输入可用,此方法将阻止。
     * <p>
     *  虽然{@code RandomAccessFile}不是{@code InputStream}的子类,但此方法的行为方式与{@code InputStream}的{@link InputStream#read()}
     * 方法完全相同。
     * 
     * 
     * @return     the next byte of data, or {@code -1} if the end of the
     *             file has been reached.
     * @exception  IOException  if an I/O error occurs. Not thrown if
     *                          end-of-file has been reached.
     */
    public int read() throws IOException {
        return read0();
    }

    private native int read0() throws IOException;

    /**
     * Reads a sub array as a sequence of bytes.
     * <p>
     * 作为字节序列读取子数组。
     * 
     * 
     * @param b the buffer into which the data is read.
     * @param off the start offset of the data.
     * @param len the number of bytes to read.
     * @exception IOException If an I/O error has occurred.
     */
    private native int readBytes(byte b[], int off, int len) throws IOException;

    /**
     * Reads up to {@code len} bytes of data from this file into an
     * array of bytes. This method blocks until at least one byte of input
     * is available.
     * <p>
     * Although {@code RandomAccessFile} is not a subclass of
     * {@code InputStream}, this method behaves in exactly the
     * same way as the {@link InputStream#read(byte[], int, int)} method of
     * {@code InputStream}.
     *
     * <p>
     *  从此文件中读取最多{@code len}字节的数据为字节数组。此方法阻塞,直到输入的至少一个字节可用。
     * <p>
     *  虽然{@code RandomAccessFile}不是{@code InputStream}的子类,但该方法的行为方式与{@code InputStream}的{@link InputStream#read(byte [],int,int) 。
     * 
     * 
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array {@code b}
     *                   at which the data is written.
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             {@code -1} if there is no more data because the end of
     *             the file has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the random access file has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If {@code b} is {@code null}.
     * @exception  IndexOutOfBoundsException If {@code off} is negative,
     * {@code len} is negative, or {@code len} is greater than
     * {@code b.length - off}
     */
    public int read(byte b[], int off, int len) throws IOException {
        return readBytes(b, off, len);
    }

    /**
     * Reads up to {@code b.length} bytes of data from this file
     * into an array of bytes. This method blocks until at least one byte
     * of input is available.
     * <p>
     * Although {@code RandomAccessFile} is not a subclass of
     * {@code InputStream}, this method behaves in exactly the
     * same way as the {@link InputStream#read(byte[])} method of
     * {@code InputStream}.
     *
     * <p>
     *  从此文件中读取到{@code b.length}字节的数据为字节数组。此方法阻塞,直到输入的至少一个字节可用。
     * <p>
     *  虽然{@code RandomAccessFile}不是{@code InputStream}的子类,但此方法的行为与{@code InputStream}的{@link InputStream#read(byte [])}
     * 方法完全相同。
     * 
     * 
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             {@code -1} if there is no more data because the end of
     *             this file has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the random access file has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If {@code b} is {@code null}.
     */
    public int read(byte b[]) throws IOException {
        return readBytes(b, 0, b.length);
    }

    /**
     * Reads {@code b.length} bytes from this file into the byte
     * array, starting at the current file pointer. This method reads
     * repeatedly from the file until the requested number of bytes are
     * read. This method blocks until the requested number of bytes are
     * read, the end of the stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取{@code b.length}字节为字节数组,从当前文件指针开始。此方法从文件中重复读取,直到读取所请求的字节数。此方法阻塞直到读取所请求的字节数,检测到流的结尾或抛出异常。
     * 
     * 
     * @param      b   the buffer into which the data is read.
     * @exception  EOFException  if this file reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final void readFully(byte b[]) throws IOException {
        readFully(b, 0, b.length);
    }

    /**
     * Reads exactly {@code len} bytes from this file into the byte
     * array, starting at the current file pointer. This method reads
     * repeatedly from the file until the requested number of bytes are
     * read. This method blocks until the requested number of bytes are
     * read, the end of the stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件中将{@code len}字节精确地读入字节数组,从当前文件指针开始。此方法从文件中重复读取,直到读取所请求的字节数。此方法阻塞直到读取所请求的字节数,检测到流的结尾或抛出异常。
     * 
     * 
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset of the data.
     * @param      len   the number of bytes to read.
     * @exception  EOFException  if this file reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final void readFully(byte b[], int off, int len) throws IOException {
        int n = 0;
        do {
            int count = this.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        } while (n < len);
    }

    /**
     * Attempts to skip over {@code n} bytes of input discarding the
     * skipped bytes.
     * <p>
     *
     * This method may skip over some smaller number of bytes, possibly zero.
     * This may result from any of a number of conditions; reaching end of
     * file before {@code n} bytes have been skipped is only one
     * possibility. This method never throws an {@code EOFException}.
     * The actual number of bytes skipped is returned.  If {@code n}
     * is negative, no bytes are skipped.
     *
     * <p>
     *  尝试跳过{@code n}个字节的输入,丢弃跳过的字节。
     * <p>
     * 
     * 该方法可以跳过一些较小数量的字节,可能为零。这可能是由许多条件中的任何一个引起的;在{@code n}字节被跳过之前到达文件结束只有一种可能性。
     * 这个方法不会抛出{@code EOFException}。将返回实际跳过的字节数。如果{@code n}为负,则不跳过任何字节。
     * 
     * 
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if an I/O error occurs.
     */
    public int skipBytes(int n) throws IOException {
        long pos;
        long len;
        long newpos;

        if (n <= 0) {
            return 0;
        }
        pos = getFilePointer();
        len = length();
        newpos = pos + n;
        if (newpos > len) {
            newpos = len;
        }
        seek(newpos);

        /* return the actual number of bytes skipped */
        return (int) (newpos - pos);
    }

    // 'Write' primitives

    /**
     * Writes the specified byte to this file. The write starts at
     * the current file pointer.
     *
     * <p>
     *  将指定的字节写入此文件。写入从当前文件指针开始。
     * 
     * 
     * @param      b   the {@code byte} to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(int b) throws IOException {
        write0(b);
    }

    private native void write0(int b) throws IOException;

    /**
     * Writes a sub array as a sequence of bytes.
     * <p>
     *  将子数组写为字节序列。
     * 
     * 
     * @param b the data to be written

     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private native void writeBytes(byte b[], int off, int len) throws IOException;

    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this file, starting at the current file pointer.
     *
     * <p>
     *  从指定的字节数组写入{@code b.length}字节到此文件,从当前文件指针开始。
     * 
     * 
     * @param      b   the data.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[]) throws IOException {
        writeBytes(b, 0, b.length);
    }

    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this file.
     *
     * <p>
     *  从指定的字节数组开始,从offset {@code off}开始,将{@code len}字节写入此文件。
     * 
     * 
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[], int off, int len) throws IOException {
        writeBytes(b, off, len);
    }

    // 'Random access' stuff

    /**
     * Returns the current offset in this file.
     *
     * <p>
     *  返回此文件中的当前偏移量。
     * 
     * 
     * @return     the offset from the beginning of the file, in bytes,
     *             at which the next read or write occurs.
     * @exception  IOException  if an I/O error occurs.
     */
    public native long getFilePointer() throws IOException;

    /**
     * Sets the file-pointer offset, measured from the beginning of this
     * file, at which the next read or write occurs.  The offset may be
     * set beyond the end of the file. Setting the offset beyond the end
     * of the file does not change the file length.  The file length will
     * change only by writing after the offset has been set beyond the end
     * of the file.
     *
     * <p>
     *  设置从文件开头测量的文件指针偏移量,在此处进行下一个读或写操作。偏移量可以设置超出文件的末尾。将偏移设置为超出文件末尾不会更改文件长度。在偏移量设置超出文件末尾后,文件长度将仅通过写入更改。
     * 
     * 
     * @param      pos   the offset position, measured in bytes from the
     *                   beginning of the file, at which to set the file
     *                   pointer.
     * @exception  IOException  if {@code pos} is less than
     *                          {@code 0} or if an I/O error occurs.
     */
    public void seek(long pos) throws IOException {
        if (pos < 0) {
            throw new IOException("Negative seek offset");
        } else {
            seek0(pos);
        }
    }

    private native void seek0(long pos) throws IOException;

    /**
     * Returns the length of this file.
     *
     * <p>
     *  返回此文件的长度。
     * 
     * 
     * @return     the length of this file, measured in bytes.
     * @exception  IOException  if an I/O error occurs.
     */
    public native long length() throws IOException;

    /**
     * Sets the length of this file.
     *
     * <p> If the present length of the file as returned by the
     * {@code length} method is greater than the {@code newLength}
     * argument then the file will be truncated.  In this case, if the file
     * offset as returned by the {@code getFilePointer} method is greater
     * than {@code newLength} then after this method returns the offset
     * will be equal to {@code newLength}.
     *
     * <p> If the present length of the file as returned by the
     * {@code length} method is smaller than the {@code newLength}
     * argument then the file will be extended.  In this case, the contents of
     * the extended portion of the file are not defined.
     *
     * <p>
     *  设置此文件的长度。
     * 
     * <p>如果{@code length}方法返回的文件的当前长度大于{@code newLength}参数,那么该文件将被截断。
     * 在这种情况下,如果{@code getFilePointer}方法返回的文件偏移量大于{@code newLength},那么在此方法返回之后,偏移量将等于{@code newLength}。
     * 
     *  <p>如果{@code length}方法返回的文件的当前长度小于{@code newLength}参数,则文件将被扩展。在这种情况下,未定义文件的扩展部分的内容。
     * 
     * 
     * @param      newLength    The desired length of the file
     * @exception  IOException  If an I/O error occurs
     * @since      1.2
     */
    public native void setLength(long newLength) throws IOException;

    /**
     * Closes this random access file stream and releases any system
     * resources associated with the stream. A closed random access
     * file cannot perform input or output operations and cannot be
     * reopened.
     *
     * <p> If this file has an associated channel then the channel is closed
     * as well.
     *
     * <p>
     *  关闭此随机访问文件流,并释放与流相关联的任何系统资源。封闭随机存取文件无法执行输入或输出操作,无法重新打开。
     * 
     *  <p>如果此文件具有关联的频道,则该频道也会关闭。
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

    //
    //  Some "reading/writing Java data types" methods stolen from
    //  DataInputStream and DataOutputStream.
    //

    /**
     * Reads a {@code boolean} from this file. This method reads a
     * single byte from the file, starting at the current file pointer.
     * A value of {@code 0} represents
     * {@code false}. Any other value represents {@code true}.
     * This method blocks until the byte is read, the end of the stream
     * is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取{@code boolean}。此方法从文件读取单个字节,从当前文件指针开始。值为{@code 0}表示{@code false}。任何其他值表示{@code true}。
     * 此方法阻塞直到读取字节,检测到流的结束,或抛出异常。
     * 
     * 
     * @return     the {@code boolean} value read.
     * @exception  EOFException  if this file has reached the end.
     * @exception  IOException   if an I/O error occurs.
     */
    public final boolean readBoolean() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    /**
     * Reads a signed eight-bit value from this file. This method reads a
     * byte from the file, starting from the current file pointer.
     * If the byte read is {@code b}, where
     * <code>0&nbsp;&lt;=&nbsp;b&nbsp;&lt;=&nbsp;255</code>,
     * then the result is:
     * <blockquote><pre>
     *     (byte)(b)
     * </pre></blockquote>
     * <p>
     * This method blocks until the byte is read, the end of the stream
     * is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取带符号的8位值。此方法从当前文件指针开始从文件读取一个字节。
     * 如果读取的字节是{@code b},其中<code> 0&nbsp;&lt; =&nbsp; b&nbsp;&lt; =&nbsp; 255 </code>,则结果是：<blockquote> <pre>
     *  b)</pre> </blockquote>。
     *  从此文件读取带符号的8位值。此方法从当前文件指针开始从文件读取一个字节。
     * <p>
     * 此方法阻塞直到读取字节,检测到流的结束,或抛出异常。
     * 
     * 
     * @return     the next byte of this file as a signed eight-bit
     *             {@code byte}.
     * @exception  EOFException  if this file has reached the end.
     * @exception  IOException   if an I/O error occurs.
     */
    public final byte readByte() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return (byte)(ch);
    }

    /**
     * Reads an unsigned eight-bit number from this file. This method reads
     * a byte from this file, starting at the current file pointer,
     * and returns that byte.
     * <p>
     * This method blocks until the byte is read, the end of the stream
     * is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取一个无符号的8位数。此方法从此文件读取一个字节,从当前文件指针开始,并返回该字节。
     * <p>
     *  此方法阻塞直到读取字节,检测到流的结束,或抛出异常。
     * 
     * 
     * @return     the next byte of this file, interpreted as an unsigned
     *             eight-bit number.
     * @exception  EOFException  if this file has reached the end.
     * @exception  IOException   if an I/O error occurs.
     */
    public final int readUnsignedByte() throws IOException {
        int ch = this.read();
        if (ch < 0)
            throw new EOFException();
        return ch;
    }

    /**
     * Reads a signed 16-bit number from this file. The method reads two
     * bytes from this file, starting at the current file pointer.
     * If the two bytes read, in order, are
     * {@code b1} and {@code b2}, where each of the two values is
     * between {@code 0} and {@code 255}, inclusive, then the
     * result is equal to:
     * <blockquote><pre>
     *     (short)((b1 &lt;&lt; 8) | b2)
     * </pre></blockquote>
     * <p>
     * This method blocks until the two bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取带符号的16位数字。该方法从该文件读取两个字节,从当前文件指针开始。
     * 如果按顺序读取的两个字节是{@code b1}和{@code b2},其中两个值中的每一个都在{@code 0}和{@code 255}之间(含),那么结果等于：<blockquote> <pre>(s
     * hort)((b1 <8)| b2)</pre> </blockquote>。
     *  从此文件读取带符号的16位数字。该方法从该文件读取两个字节,从当前文件指针开始。
     * <p>
     *  此方法阻塞直到读取两个字节,检测到流的结尾,或抛出异常。
     * 
     * 
     * @return     the next two bytes of this file, interpreted as a signed
     *             16-bit number.
     * @exception  EOFException  if this file reaches the end before reading
     *               two bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final short readShort() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    /**
     * Reads an unsigned 16-bit number from this file. This method reads
     * two bytes from the file, starting at the current file pointer.
     * If the bytes read, in order, are
     * {@code b1} and {@code b2}, where
     * <code>0&nbsp;&lt;=&nbsp;b1, b2&nbsp;&lt;=&nbsp;255</code>,
     * then the result is equal to:
     * <blockquote><pre>
     *     (b1 &lt;&lt; 8) | b2
     * </pre></blockquote>
     * <p>
     * This method blocks until the two bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取一个无符号的16位数字。此方法从文件读取两个字节,从当前文件指针开始。
     * 如果按顺序读取的字节是{@code b1}和{@code b2},其中<code> 0&nbsp; <=&nbsp; b1,b2&nbsp;&lt; =&nbsp; 255 </code>等于：<blockquote>
     *  <pre>(b1 <8)| b2 </pre> </blockquote>。
     *  从此文件读取一个无符号的16位数字。此方法从文件读取两个字节,从当前文件指针开始。
     * <p>
     *  此方法阻塞直到读取两个字节,检测到流的结尾,或抛出异常。
     * 
     * 
     * @return     the next two bytes of this file, interpreted as an unsigned
     *             16-bit integer.
     * @exception  EOFException  if this file reaches the end before reading
     *               two bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final int readUnsignedShort() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch1 << 8) + (ch2 << 0);
    }

    /**
     * Reads a character from this file. This method reads two
     * bytes from the file, starting at the current file pointer.
     * If the bytes read, in order, are
     * {@code b1} and {@code b2}, where
     * <code>0&nbsp;&lt;=&nbsp;b1,&nbsp;b2&nbsp;&lt;=&nbsp;255</code>,
     * then the result is equal to:
     * <blockquote><pre>
     *     (char)((b1 &lt;&lt; 8) | b2)
     * </pre></blockquote>
     * <p>
     * This method blocks until the two bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     * 从此文件读取字符。此方法从文件读取两个字节,从当前文件指针开始。
     * 如果按顺序读取的字节是{@code b1}和{@code b2},其中<code> 0&nbsp;&lt; = b1,&nbsp; b2&nbsp;&lt; =&nbsp; 255 </code>结果等
     * 于：<blockquote> <pre>(char)((b1 <8)| b2)</pre> </blockquote>。
     * 从此文件读取字符。此方法从文件读取两个字节,从当前文件指针开始。
     * <p>
     *  此方法阻塞直到读取两个字节,检测到流的结尾,或抛出异常。
     * 
     * 
     * @return     the next two bytes of this file, interpreted as a
     *                  {@code char}.
     * @exception  EOFException  if this file reaches the end before reading
     *               two bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final char readChar() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (char)((ch1 << 8) + (ch2 << 0));
    }

    /**
     * Reads a signed 32-bit integer from this file. This method reads 4
     * bytes from the file, starting at the current file pointer.
     * If the bytes read, in order, are {@code b1},
     * {@code b2}, {@code b3}, and {@code b4}, where
     * <code>0&nbsp;&lt;=&nbsp;b1, b2, b3, b4&nbsp;&lt;=&nbsp;255</code>,
     * then the result is equal to:
     * <blockquote><pre>
     *     (b1 &lt;&lt; 24) | (b2 &lt;&lt; 16) + (b3 &lt;&lt; 8) + b4
     * </pre></blockquote>
     * <p>
     * This method blocks until the four bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取带符号的32位整数。此方法从文件读取4个字节,从当前文件指针开始。
     * 如果按顺序读取的字节是{@code b1},{@code b2},{@code b3}和{@code b4},其中<code> 0&nbsp;&lt; = b1,b2,b3 ,b4&lt; =&nbsp
     * ; 255 </code>,则结果等于：<blockquote> <pre>(b1 <24)| (b2 <16)+(b3 <8)+ b4 </pre> </blockquote>。
     *  从此文件读取带符号的32位整数。此方法从文件读取4个字节,从当前文件指针开始。
     * <p>
     *  此方法阻塞直到读取四个字节,检测到流的结束,或抛出异常。
     * 
     * 
     * @return     the next four bytes of this file, interpreted as an
     *             {@code int}.
     * @exception  EOFException  if this file reaches the end before reading
     *               four bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final int readInt() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        int ch4 = this.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    /**
     * Reads a signed 64-bit integer from this file. This method reads eight
     * bytes from the file, starting at the current file pointer.
     * If the bytes read, in order, are
     * {@code b1}, {@code b2}, {@code b3},
     * {@code b4}, {@code b5}, {@code b6},
     * {@code b7}, and {@code b8,} where:
     * <blockquote><pre>
     *     0 &lt;= b1, b2, b3, b4, b5, b6, b7, b8 &lt;=255,
     * </pre></blockquote>
     * <p>
     * then the result is equal to:
     * <blockquote><pre>
     *     ((long)b1 &lt;&lt; 56) + ((long)b2 &lt;&lt; 48)
     *     + ((long)b3 &lt;&lt; 40) + ((long)b4 &lt;&lt; 32)
     *     + ((long)b5 &lt;&lt; 24) + ((long)b6 &lt;&lt; 16)
     *     + ((long)b7 &lt;&lt; 8) + b8
     * </pre></blockquote>
     * <p>
     * This method blocks until the eight bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取带符号的64位整数。此方法从文件读取八个字节,从当前文件指针开始。
     * 如果按顺序读取的字节为{@code b1},{@code b2},{@code b3},{@code b4},{@code b5},{@code b6},{@code b7}和{@code b8,}其中
     * ：<blockquote> <pre> 0 <= b1,b2,b3,b4,b5,b6,b7,b8 <= 255,</。
     *  从此文件读取带符号的64位整数。此方法从文件读取八个字节,从当前文件指针开始。
     * <p>
     * 那么结果等于：<BLOCKQUOTE> <前>((长)B1&LT;&LT; 56)+((长)B2&LT;&LT; 48)+((长)B3&LT;&LT; 40)+( (长)B4&LT;&LT; 32)+(
     * (长)B5&LT;&LT; 24)+((长)B6&LT;&LT; 16)+((长)B7&LT;&LT; 8)+ B8 </预> </块引用>。
     * <p>
     *  此方法一直阻塞八个读取字节,检测到流的末尾或者抛出异常。
     * 
     * 
     * @return     the next eight bytes of this file, interpreted as a
     *             {@code long}.
     * @exception  EOFException  if this file reaches the end before reading
     *               eight bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    public final long readLong() throws IOException {
        return ((long)(readInt()) << 32) + (readInt() & 0xFFFFFFFFL);
    }

    /**
     * Reads a {@code float} from this file. This method reads an
     * {@code int} value, starting at the current file pointer,
     * as if by the {@code readInt} method
     * and then converts that {@code int} to a {@code float}
     * using the {@code intBitsToFloat} method in class
     * {@code Float}.
     * <p>
     * This method blocks until the four bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取一个{@code浮}。
     * 此方法读取一个@code {} INT价值,并从当前文件指针,仿佛被@code {}的readInt方法,然后转换那个@code {} INT到{@code浮}使用{@code intBitsToFloat }
     * 类{@code浮法}。
     *  从此文件读取一个{@code浮}。
     * <p>
     *  此方法一直阻塞四个读取字节,检测到流的末尾或者抛出异常。
     * 
     * 
     * @return     the next four bytes of this file, interpreted as a
     *             {@code float}.
     * @exception  EOFException  if this file reaches the end before reading
     *             four bytes.
     * @exception  IOException   if an I/O error occurs.
     * @see        java.io.RandomAccessFile#readInt()
     * @see        java.lang.Float#intBitsToFloat(int)
     */
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    /**
     * Reads a {@code double} from this file. This method reads a
     * {@code long} value, starting at the current file pointer,
     * as if by the {@code readLong} method
     * and then converts that {@code long} to a {@code double}
     * using the {@code longBitsToDouble} method in
     * class {@code Double}.
     * <p>
     * This method blocks until the eight bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取一个{@code双}。
     * 这种方法可以读取{@code长}值,并从当前文件指针,仿佛被{@code readLong}方法,然后,使用将转换{@code长}到{@code双} {在@code longBitsToDouble }
     * 方法类{@code双}。
     *  从此文件读取一个{@code双}。
     * <p>
     *  此方法一直阻塞八个读取字节,检测到流的末尾或者抛出异常。
     * 
     * 
     * @return     the next eight bytes of this file, interpreted as a
     *             {@code double}.
     * @exception  EOFException  if this file reaches the end before reading
     *             eight bytes.
     * @exception  IOException   if an I/O error occurs.
     * @see        java.io.RandomAccessFile#readLong()
     * @see        java.lang.Double#longBitsToDouble(long)
     */
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * Reads the next line of text from this file.  This method successively
     * reads bytes from the file, starting at the current file pointer,
     * until it reaches a line terminator or the end
     * of the file.  Each byte is converted into a character by taking the
     * byte's value for the lower eight bits of the character and setting the
     * high eight bits of the character to zero.  This method does not,
     * therefore, support the full Unicode character set.
     *
     * <p> A line of text is terminated by a carriage-return character
     * ({@code '\u005Cr'}), a newline character ({@code '\u005Cn'}), a
     * carriage-return character immediately followed by a newline character,
     * or the end of the file.  Line-terminating characters are discarded and
     * are not included as part of the string returned.
     *
     * <p> This method blocks until a newline character is read, a carriage
     * return and the byte following it are read (to see if it is a newline),
     * the end of the file is reached, or an exception is thrown.
     *
     * <p>
     * 从此文件读取下一行文本。此方法从文件中连续读取字节,从当前文件指针开始,直到到达行终止符或文件结尾。每个字节通过获取字符的低8位的字节值并将字符的高8位设置为零来转换为字符。
     * 因此,此方法不支持完整的Unicode字符集。
     * 
     *  <p>文本行由回车字符({@code'\ u005Cr'}),换行符({@code'\ u005Cn'}),回车符后紧跟换行符字符或文件的结尾。行终止字符被丢弃,不作为返回的字符串的一部分包含。
     * 
     *  <p>此方法阻塞,直到读取换行符,回车并读取其后的字节(以查看它是否为换行符),到达文件结尾或抛出异常。
     * 
     * 
     * @return     the next line of text from this file, or null if end
     *             of file is encountered before even one byte is read.
     * @exception  IOException  if an I/O error occurs.
     */

    public final String readLine() throws IOException {
        StringBuffer input = new StringBuffer();
        int c = -1;
        boolean eol = false;

        while (!eol) {
            switch (c = read()) {
            case -1:
            case '\n':
                eol = true;
                break;
            case '\r':
                eol = true;
                long cur = getFilePointer();
                if ((read()) != '\n') {
                    seek(cur);
                }
                break;
            default:
                input.append((char)c);
                break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }
        return input.toString();
    }

    /**
     * Reads in a string from this file. The string has been encoded
     * using a
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * format.
     * <p>
     * The first two bytes are read, starting from the current file
     * pointer, as if by
     * {@code readUnsignedShort}. This value gives the number of
     * following bytes that are in the encoded string, not
     * the length of the resulting string. The following bytes are then
     * interpreted as bytes encoding characters in the modified UTF-8 format
     * and are converted into characters.
     * <p>
     * This method blocks until all the bytes are read, the end of the
     * stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此文件读取字符串。该字符串已使用<a href="DataInput.html#modified-utf-8">修改的UTF-8 </a>格式进行编码。
     * <p>
     *  前两个字节从当前文件指针开始读取,如同通过{@code readUnsignedShort}。此值给出了编码字符串中后面的字节数,而不是结果字符串的长度。
     * 随后的字节将被解释为以修改的UTF-8格式编码字符的字节,并转换为字符。
     * <p>
     * 此方法阻塞直到读取所有字节,检测到流的结束,或抛出异常。
     * 
     * 
     * @return     a Unicode string.
     * @exception  EOFException            if this file reaches the end before
     *               reading all the bytes.
     * @exception  IOException             if an I/O error occurs.
     * @exception  UTFDataFormatException  if the bytes do not represent
     *               valid modified UTF-8 encoding of a Unicode string.
     * @see        java.io.RandomAccessFile#readUnsignedShort()
     */
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    /**
     * Writes a {@code boolean} to the file as a one-byte value. The
     * value {@code true} is written out as the value
     * {@code (byte)1}; the value {@code false} is written out
     * as the value {@code (byte)0}. The write starts at
     * the current position of the file pointer.
     *
     * <p>
     *  将{@code boolean}作为一个字节的值写入文件。值{@code true}被写为值{@code(byte)1};值{@code false}被写为值{@code(byte)0}。
     * 写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code boolean} value to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
        //written++;
    }

    /**
     * Writes a {@code byte} to the file as a one-byte value. The
     * write starts at the current position of the file pointer.
     *
     * <p>
     *  将{@code byte}作为一个字节的值写入文件。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code byte} value to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeByte(int v) throws IOException {
        write(v);
        //written++;
    }

    /**
     * Writes a {@code short} to the file as two bytes, high byte first.
     * The write starts at the current position of the file pointer.
     *
     * <p>
     *  将{@code short}写入文件为两个字节,高字节优先。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code short} to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeShort(int v) throws IOException {
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
        //written += 2;
    }

    /**
     * Writes a {@code char} to the file as a two-byte value, high
     * byte first. The write starts at the current position of the
     * file pointer.
     *
     * <p>
     *  将{@code char}以两个字节的值(高字节优先)写入文件。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code char} value to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeChar(int v) throws IOException {
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
        //written += 2;
    }

    /**
     * Writes an {@code int} to the file as four bytes, high byte first.
     * The write starts at the current position of the file pointer.
     *
     * <p>
     *  将{@code int}作为四个字节写入文件,高字节在前。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   an {@code int} to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeInt(int v) throws IOException {
        write((v >>> 24) & 0xFF);
        write((v >>> 16) & 0xFF);
        write((v >>>  8) & 0xFF);
        write((v >>>  0) & 0xFF);
        //written += 4;
    }

    /**
     * Writes a {@code long} to the file as eight bytes, high byte first.
     * The write starts at the current position of the file pointer.
     *
     * <p>
     *  将一个{@code long}写入文件作为八个字节,高字节在前。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code long} to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeLong(long v) throws IOException {
        write((int)(v >>> 56) & 0xFF);
        write((int)(v >>> 48) & 0xFF);
        write((int)(v >>> 40) & 0xFF);
        write((int)(v >>> 32) & 0xFF);
        write((int)(v >>> 24) & 0xFF);
        write((int)(v >>> 16) & 0xFF);
        write((int)(v >>>  8) & 0xFF);
        write((int)(v >>>  0) & 0xFF);
        //written += 8;
    }

    /**
     * Converts the float argument to an {@code int} using the
     * {@code floatToIntBits} method in class {@code Float},
     * and then writes that {@code int} value to the file as a
     * four-byte quantity, high byte first. The write starts at the
     * current position of the file pointer.
     *
     * <p>
     *  使用{@code Float}类中的{@code floatToIntBits}方法将float参数转换为{@code int},然后将该{@code int}值作为四字节数量,高字节先写入文件。
     * 写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code float} value to be written.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.lang.Float#floatToIntBits(float)
     */
    public final void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    /**
     * Converts the double argument to a {@code long} using the
     * {@code doubleToLongBits} method in class {@code Double},
     * and then writes that {@code long} value to the file as an
     * eight-byte quantity, high byte first. The write starts at the current
     * position of the file pointer.
     *
     * <p>
     * 使用类{@code Double}中的{@code doubleToLongBits}方法将double参数转换为{@code long},然后将该{@code long}值作为8字节数量,高字节先写入
     * 文件。
     * 写入从文件指针的当前位置开始。
     * 
     * 
     * @param      v   a {@code double} value to be written.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.lang.Double#doubleToLongBits(double)
     */
    public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    /**
     * Writes the string to the file as a sequence of bytes. Each
     * character in the string is written out, in sequence, by discarding
     * its high eight bits. The write starts at the current position of
     * the file pointer.
     *
     * <p>
     *  将字符串作为字节序列写入文件。字符串中的每个字符按顺序通过丢弃其高8位而被写出。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      s   a string of bytes to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    @SuppressWarnings("deprecation")
    public final void writeBytes(String s) throws IOException {
        int len = s.length();
        byte[] b = new byte[len];
        s.getBytes(0, len, b, 0);
        writeBytes(b, 0, len);
    }

    /**
     * Writes a string to the file as a sequence of characters. Each
     * character is written to the data output stream as if by the
     * {@code writeChar} method. The write starts at the current
     * position of the file pointer.
     *
     * <p>
     *  将字符串作为字符序列写入文件。每个字符都被写入数据输出流,就像使用{@code writeChar}方法。写入从文件指针的当前位置开始。
     * 
     * 
     * @param      s   a {@code String} value to be written.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.RandomAccessFile#writeChar(int)
     */
    public final void writeChars(String s) throws IOException {
        int clen = s.length();
        int blen = 2*clen;
        byte[] b = new byte[blen];
        char[] c = new char[clen];
        s.getChars(0, clen, c, 0);
        for (int i = 0, j = 0; i < clen; i++) {
            b[j++] = (byte)(c[i] >>> 8);
            b[j++] = (byte)(c[i] >>> 0);
        }
        writeBytes(b, 0, blen);
    }

    /**
     * Writes a string to the file using
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * encoding in a machine-independent manner.
     * <p>
     * First, two bytes are written to the file, starting at the
     * current file pointer, as if by the
     * {@code writeShort} method giving the number of bytes to
     * follow. This value is the number of bytes actually written out,
     * not the length of the string. Following the length, each character
     * of the string is output, in sequence, using the modified UTF-8 encoding
     * for each character.
     *
     * <p>
     *  使用<a href="DataInput.html#modified-utf-8">修改的UTF-8 </a>编码以机器无关的方式将字符串写入文件。
     * <p>
     *  首先,从当前文件指针开始,将两个字节写入文件,就像通过{@code writeShort}方法给出跟随的字节数。这个值是实际写出的字节数,而不是字符串的长度。
     * 
     * @param      str   a string to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeUTF(String str) throws IOException {
        DataOutputStream.writeUTF(str, this);
    }

    private static native void initIDs();

    private native void close0() throws IOException;

    static {
        initIDs();
    }
}
