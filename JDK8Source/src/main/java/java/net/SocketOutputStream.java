/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * This stream extends FileOutputStream to implement a
 * SocketOutputStream. Note that this class should <b>NOT</b> be
 * public.
 *
 * <p>
 *  此流扩展FileOutputStream以实现SocketOutputStream。请注意,此类应<b>不</b>为公开。
 * 
 * 
 * @author      Jonathan Payne
 * @author      Arthur van Hoff
 */
class SocketOutputStream extends FileOutputStream
{
    static {
        init();
    }

    private AbstractPlainSocketImpl impl = null;
    private byte temp[] = new byte[1];
    private Socket socket = null;

    /**
     * Creates a new SocketOutputStream. Can only be called
     * by a Socket. This method needs to hang on to the owner Socket so
     * that the fd will not be closed.
     * <p>
     *  创建一个新的SocketOutputStream。只能通过Socket调用。此方法需要挂起所有者Socket以使fd不会关闭。
     * 
     * 
     * @param impl the socket output stream inplemented
     */
    SocketOutputStream(AbstractPlainSocketImpl impl) throws IOException {
        super(impl.getFileDescriptor());
        this.impl = impl;
        socket = impl.getSocket();
    }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file output stream. </p>
     *
     * The {@code getChannel} method of {@code SocketOutputStream}
     * returns {@code null} since it is a socket based stream.</p>
     *
     * <p>
     *  返回与此文件输出流相关联的唯一{@link java.nio.channels.FileChannel FileChannel}对象。 </p>
     * 
     *  {@code SocketOutputStream}的{@code getChannel}方法返回{@code null},因为它是基于套接字的流。</p>
     * 
     * 
     * @return  the file channel associated with this file output stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public final FileChannel getChannel() {
        return null;
    }

    /**
     * Writes to the socket.
     * <p>
     *  写入套接字。
     * 
     * 
     * @param fd the FileDescriptor
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private native void socketWrite0(FileDescriptor fd, byte[] b, int off,
                                     int len) throws IOException;

    /**
     * Writes to the socket with appropriate locking of the
     * FileDescriptor.
     * <p>
     *  通过适当锁定FileDescriptor写入套接字。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private void socketWrite(byte b[], int off, int len) throws IOException {

        if (len <= 0 || off < 0 || off + len > b.length) {
            if (len == 0) {
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }

        FileDescriptor fd = impl.acquireFD();
        try {
            socketWrite0(fd, b, off, len);
        } catch (SocketException se) {
            if (se instanceof sun.net.ConnectionResetException) {
                impl.setConnectionResetPending();
                se = new SocketException("Connection reset");
            }
            if (impl.isClosedOrPending()) {
                throw new SocketException("Socket closed");
            } else {
                throw se;
            }
        } finally {
            impl.releaseFD();
        }
    }

    /**
     * Writes a byte to the socket.
     * <p>
     *  向套接字写入一个字节。
     * 
     * 
     * @param b the data to be written
     * @exception IOException If an I/O error has occurred.
     */
    public void write(int b) throws IOException {
        temp[0] = (byte)b;
        socketWrite(temp, 0, 1);
    }

    /**
     * Writes the contents of the buffer <i>b</i> to the socket.
     * <p>
     *  将缓冲区<i> b </i>的内容写入套接字。
     * 
     * 
     * @param b the data to be written
     * @exception SocketException If an I/O error has occurred.
     */
    public void write(byte b[]) throws IOException {
        socketWrite(b, 0, b.length);
    }

    /**
     * Writes <i>length</i> bytes from buffer <i>b</i> starting at
     * offset <i>len</i>.
     * <p>
     *  从offset <i> len </i>开始从缓冲区</i>写入<i>长度</i>字节。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception SocketException If an I/O error has occurred.
     */
    public void write(byte b[], int off, int len) throws IOException {
        socketWrite(b, off, len);
    }

    /**
     * Closes the stream.
     * <p>
     *  关闭流。
     * 
     */
    private boolean closing = false;
    public void close() throws IOException {
        // Prevent recursion. See BugId 4484411
        if (closing)
            return;
        closing = true;
        if (socket != null) {
            if (!socket.isClosed())
                socket.close();
        } else
            impl.close();
        closing = false;
    }

    /**
     * Overrides finalize, the fd is closed by the Socket.
     * <p>
     *  覆盖finalize,fd由Socket关闭。
     * 
     */
    protected void finalize() {}

    /**
     * Perform class load-time initializations.
     * <p>
     *  执行类装入时初始化。
     */
    private native static void init();

}
