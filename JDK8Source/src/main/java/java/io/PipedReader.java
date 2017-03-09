/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Piped character-input streams.
 *
 * <p>
 *  流水线字符输入流。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class PipedReader extends Reader {
    boolean closedByWriter = false;
    boolean closedByReader = false;
    boolean connected = false;

    /* REMIND: identification of the read and write sides needs to be
       more sophisticated.  Either using thread groups (but what about
       pipes within a thread?) or using finalization (but it may be a
    /* <p>
    /*  更复杂。使用线程组(但是线程内的管道呢?)或使用finalization(但它可能是一个
    /* 
    /* 
       long time until the next GC). */
    Thread readSide;
    Thread writeSide;

   /**
    * The size of the pipe's circular input buffer.
    * <p>
    *  管道的圆形输入缓冲区的大小。
    * 
    */
    private static final int DEFAULT_PIPE_SIZE = 1024;

    /**
     * The circular buffer into which incoming data is placed.
     * <p>
     *  输入数据所在的循环缓冲区。
     * 
     */
    char buffer[];

    /**
     * The index of the position in the circular buffer at which the
     * next character of data will be stored when received from the connected
     * piped writer. <code>in&lt;0</code> implies the buffer is empty,
     * <code>in==out</code> implies the buffer is full
     * <p>
     *  循环缓冲器中从连接的管道写入器接收到下一个字符数据时的位置索引。 <code>在<0 </code>中表示缓冲区为空,<code> in == out </code>表示缓冲区已满
     * 
     */
    int in = -1;

    /**
     * The index of the position in the circular buffer at which the next
     * character of data will be read by this piped reader.
     * <p>
     *  在循环缓冲器中的位置的索引,在该位置下一个字符的数据将被该管道读取器读取。
     * 
     */
    int out = 0;

    /**
     * Creates a <code>PipedReader</code> so
     * that it is connected to the piped writer
     * <code>src</code>. Data written to <code>src</code>
     * will then be available as input from this stream.
     *
     * <p>
     *  创建一个<code> PipedReader </code>,以便它连接到管道编写器<code> src </code>。写入<code> src </code>的数据将作为该流的输入。
     * 
     * 
     * @param      src   the stream to connect to.
     * @exception  IOException  if an I/O error occurs.
     */
    public PipedReader(PipedWriter src) throws IOException {
        this(src, DEFAULT_PIPE_SIZE);
    }

    /**
     * Creates a <code>PipedReader</code> so that it is connected
     * to the piped writer <code>src</code> and uses the specified
     * pipe size for the pipe's buffer. Data written to <code>src</code>
     * will then be  available as input from this stream.

     * <p>
     *  创建一个<code> PipedReader </code>,以便它连接到管道编写器<code> src </code>,并使用指定的管道大小作为管道的缓冲区。
     * 写入<code> src </code>的数据将作为该流的输入。
     * 
     * 
     * @param      src       the stream to connect to.
     * @param      pipeSize  the size of the pipe's buffer.
     * @exception  IOException  if an I/O error occurs.
     * @exception  IllegalArgumentException if {@code pipeSize <= 0}.
     * @since      1.6
     */
    public PipedReader(PipedWriter src, int pipeSize) throws IOException {
        initPipe(pipeSize);
        connect(src);
    }


    /**
     * Creates a <code>PipedReader</code> so
     * that it is not yet {@linkplain #connect(java.io.PipedWriter)
     * connected}. It must be {@linkplain java.io.PipedWriter#connect(
     * java.io.PipedReader) connected} to a <code>PipedWriter</code>
     * before being used.
     * <p>
     *  创建<code> PipedReader </code>,以使其尚未连接{@linkplain #connect(java.io.PipedWriter)}。
     * 它必须是{@linkplain java.io.PipedWriter#connect(java.io.PipedReader)connected}到<code> PipedWriter </code>
     * 才能使用。
     *  创建<code> PipedReader </code>,以使其尚未连接{@linkplain #connect(java.io.PipedWriter)}。
     * 
     */
    public PipedReader() {
        initPipe(DEFAULT_PIPE_SIZE);
    }

    /**
     * Creates a <code>PipedReader</code> so that it is not yet
     * {@link #connect(java.io.PipedWriter) connected} and uses
     * the specified pipe size for the pipe's buffer.
     * It must be  {@linkplain java.io.PipedWriter#connect(
     * java.io.PipedReader) connected} to a <code>PipedWriter</code>
     * before being used.
     *
     * <p>
     * 创建一个<code> PipedReader </code>,使其尚未连接{@link #connect(java.io.PipedWriter)},并使用指定的管道大小作为管道的缓冲区。
     * 它必须是{@linkplain java.io.PipedWriter#connect(java.io.PipedReader)connected}到<code> PipedWriter </code>
     * 才能使用。
     * 创建一个<code> PipedReader </code>,使其尚未连接{@link #connect(java.io.PipedWriter)},并使用指定的管道大小作为管道的缓冲区。
     * 
     * 
     * @param   pipeSize the size of the pipe's buffer.
     * @exception  IllegalArgumentException if {@code pipeSize <= 0}.
     * @since      1.6
     */
    public PipedReader(int pipeSize) {
        initPipe(pipeSize);
    }

    private void initPipe(int pipeSize) {
        if (pipeSize <= 0) {
            throw new IllegalArgumentException("Pipe size <= 0");
        }
        buffer = new char[pipeSize];
    }

    /**
     * Causes this piped reader to be connected
     * to the piped  writer <code>src</code>.
     * If this object is already connected to some
     * other piped writer, an <code>IOException</code>
     * is thrown.
     * <p>
     * If <code>src</code> is an
     * unconnected piped writer and <code>snk</code>
     * is an unconnected piped reader, they
     * may be connected by either the call:
     *
     * <pre><code>snk.connect(src)</code> </pre>
     * <p>
     * or the call:
     *
     * <pre><code>src.connect(snk)</code> </pre>
     * <p>
     * The two calls have the same effect.
     *
     * <p>
     *  使此管道式读取器连接到管道式写入程序<code> src </code>。如果此对象已经连接到其他管道式写入程序,则会抛出<code> IOException </code>。
     * <p>
     *  如果<code> src </code>是未连接的管道式写入程序,而<code> snk </code>是未连接的管道式读取程序,
     * 
     *  <pre> <code> snk.connect(src)</code> </pre>
     * <p>
     *  或呼叫：
     * 
     *  <pre> <code> src.connect(snk)</code> </pre>
     * <p>
     *  这两个调用具有相同的效果。
     * 
     * 
     * @param      src   The piped writer to connect to.
     * @exception  IOException  if an I/O error occurs.
     */
    public void connect(PipedWriter src) throws IOException {
        src.connect(this);
    }

    /**
     * Receives a char of data. This method will block if no input is
     * available.
     * <p>
     *  接收数据的字符。如果没有可用的输入,此方法将阻塞。
     * 
     */
    synchronized void receive(int c) throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByWriter || closedByReader) {
            throw new IOException("Pipe closed");
        } else if (readSide != null && !readSide.isAlive()) {
            throw new IOException("Read end dead");
        }

        writeSide = Thread.currentThread();
        while (in == out) {
            if ((readSide != null) && !readSide.isAlive()) {
                throw new IOException("Pipe broken");
            }
            /* full: kick any waiting readers */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
        if (in < 0) {
            in = 0;
            out = 0;
        }
        buffer[in++] = (char) c;
        if (in >= buffer.length) {
            in = 0;
        }
    }

    /**
     * Receives data into an array of characters.  This method will
     * block until some input is available.
     * <p>
     *  将数据接收到字符数组中。此方法将阻塞,直到一些输入可用。
     * 
     */
    synchronized void receive(char c[], int off, int len)  throws IOException {
        while (--len >= 0) {
            receive(c[off++]);
        }
    }

    /**
     * Notifies all waiting threads that the last character of data has been
     * received.
     * <p>
     *  通知所有等待线程已接收到数据的最后一个字符。
     * 
     */
    synchronized void receivedLast() {
        closedByWriter = true;
        notifyAll();
    }

    /**
     * Reads the next character of data from this piped stream.
     * If no character is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned.
     * This method blocks until input data is available, the end of
     * the stream is detected, or an exception is thrown.
     *
     * <p>
     *  从此管道流读取数据的下一个字符。如果没有字符可用,因为已经到达流的结尾,则返回值<code> -1 </code>。此方法阻塞,直到输入数据可用,检测到流的结尾,或抛出异常。
     * 
     * 
     * @return     the next character of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if the pipe is
     *          <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *          {@link #connect(java.io.PipedWriter) unconnected}, closed,
     *          or an I/O error occurs.
     */
    public synchronized int read()  throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }

        readSide = Thread.currentThread();
        int trials = 2;
        while (in < 0) {
            if (closedByWriter) {
                /* closed by writer, return EOF */
                return -1;
            }
            if ((writeSide != null) && (!writeSide.isAlive()) && (--trials < 0)) {
                throw new IOException("Pipe broken");
            }
            /* might be a writer waiting */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
        int ret = buffer[out++];
        if (out >= buffer.length) {
            out = 0;
        }
        if (in == out) {
            /* now empty */
            in = -1;
        }
        return ret;
    }

    /**
     * Reads up to <code>len</code> characters of data from this piped
     * stream into an array of characters. Less than <code>len</code> characters
     * will be read if the end of the data stream is reached or if
     * <code>len</code> exceeds the pipe's buffer size. This method
     * blocks until at least one character of input is available.
     *
     * <p>
     * 从此管道流读取数据的<code> len </code>字符为一个字符数组。
     * 如果到达数据流的末尾或者如果<code> len </code>超过管道的缓冲区大小,则会读取<code> len </code>字符。此方法阻塞,直到输入的至少一个字符可用。
     * 
     * 
     * @param      cbuf     the buffer into which the data is read.
     * @param      off   the start offset of the data.
     * @param      len   the maximum number of characters read.
     * @return     the total number of characters read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  if the pipe is
     *                  <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *                  {@link #connect(java.io.PipedWriter) unconnected}, closed,
     *                  or an I/O error occurs.
     */
    public synchronized int read(char cbuf[], int off, int len)  throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }

        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        /* possibly wait on the first character */
        int c = read();
        if (c < 0) {
            return -1;
        }
        cbuf[off] =  (char)c;
        int rlen = 1;
        while ((in >= 0) && (--len > 0)) {
            cbuf[off + rlen] = buffer[out++];
            rlen++;
            if (out >= buffer.length) {
                out = 0;
            }
            if (in == out) {
                /* now empty */
                in = -1;
            }
        }
        return rlen;
    }

    /**
     * Tell whether this stream is ready to be read.  A piped character
     * stream is ready if the circular buffer is not empty.
     *
     * <p>
     *  告诉这个流是否准备好被读取。如果循环缓冲区不为空,则管道字符流就绪。
     * 
     * 
     * @exception  IOException  if the pipe is
     *                  <a href=PipedInputStream.html#BROKEN> <code>broken</code></a>,
     *                  {@link #connect(java.io.PipedWriter) unconnected}, or closed.
     */
    public synchronized boolean ready() throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }
        if (in < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Closes this piped stream and releases any system resources
     * associated with the stream.
     *
     * <p>
     *  关闭此管道流,并释放与流相关联的任何系统资源。
     * 
     * @exception  IOException  if an I/O error occurs.
     */
    public void close()  throws IOException {
        in = -1;
        closedByReader = true;
    }
}
