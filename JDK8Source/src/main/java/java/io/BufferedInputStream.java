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
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * A <code>BufferedInputStream</code> adds
 * functionality to another input stream-namely,
 * the ability to buffer the input and to
 * support the <code>mark</code> and <code>reset</code>
 * methods. When  the <code>BufferedInputStream</code>
 * is created, an internal buffer array is
 * created. As bytes  from the stream are read
 * or skipped, the internal buffer is refilled
 * as necessary  from the contained input stream,
 * many bytes at a time. The <code>mark</code>
 * operation  remembers a point in the input
 * stream and the <code>reset</code> operation
 * causes all the  bytes read since the most
 * recent <code>mark</code> operation to be
 * reread before new bytes are  taken from
 * the contained input stream.
 *
 * <p>
 *  <code> BufferedInputStream </code>向另一个输入流添加了功能 - 即缓冲输入并支持<code>标记</code>和<code>重置</code>方法的功能。
 * 当创建<code> BufferedInputStream </code>时,将创建一个内部缓冲区数组。当读取或跳过来自流的字节时,根据需要从包含的输入流中重新填充内部缓冲器,一次多个字节。
 *  <code>标记</code>操作会记住输入流中的一个点,而<code> reset </code>操作会导致自最近的<code>标记</code>操作后读取的所有字节被重读从包含的输入流中获取新的字
 * 节。
 * 当创建<code> BufferedInputStream </code>时,将创建一个内部缓冲区数组。当读取或跳过来自流的字节时,根据需要从包含的输入流中重新填充内部缓冲器,一次多个字节。
 * 
 * 
 * @author  Arthur van Hoff
 * @since   JDK1.0
 */
public
class BufferedInputStream extends FilterInputStream {

    private static int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     * <p>
     *  要分配的数组的最大大小。一些VM在数组中保留一些标题字。尝试分配较大的数组可能会导致OutOfMemoryError：请求的数组大小超过VM限制
     * 
     */
    private static int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    /**
     * The internal buffer array where the data is stored. When necessary,
     * it may be replaced by another array of
     * a different size.
     * <p>
     *  存储数据的内部缓冲区数组。必要时,可以用不同尺寸的另一个阵列替换。
     * 
     */
    protected volatile byte buf[];

    /**
     * Atomic updater to provide compareAndSet for buf. This is
     * necessary because closes can be asynchronous. We use nullness
     * of buf[] as primary indicator that this stream is closed. (The
     * "in" field is also nulled out on close.)
     * <p>
     *  原子更新器为buf提供compareAndSet。这是必要的,因为关闭可以是异步的。我们使用buf []的nullness作为该流被关闭的主要指示符。 ("in"字段在关闭时也为null)。
     * 
     */
    private static final
        AtomicReferenceFieldUpdater<BufferedInputStream, byte[]> bufUpdater =
        AtomicReferenceFieldUpdater.newUpdater
        (BufferedInputStream.class,  byte[].class, "buf");

    /**
     * The index one greater than the index of the last valid byte in
     * the buffer.
     * This value is always
     * in the range <code>0</code> through <code>buf.length</code>;
     * elements <code>buf[0]</code>  through <code>buf[count-1]
     * </code>contain buffered input data obtained
     * from the underlying  input stream.
     * <p>
     * 索引1大于缓冲区中最后一个有效字节的索引。
     * 该值始终在<code> 0 </code>到<code> buf.length </code>的范围内;通过<code> buf [count-1] </code>元素<code> buf [0] </code>
     * 包含从底层输入流获得的缓冲输入数据。
     * 索引1大于缓冲区中最后一个有效字节的索引。
     * 
     */
    protected int count;

    /**
     * The current position in the buffer. This is the index of the next
     * character to be read from the <code>buf</code> array.
     * <p>
     * This value is always in the range <code>0</code>
     * through <code>count</code>. If it is less
     * than <code>count</code>, then  <code>buf[pos]</code>
     * is the next byte to be supplied as input;
     * if it is equal to <code>count</code>, then
     * the  next <code>read</code> or <code>skip</code>
     * operation will require more bytes to be
     * read from the contained  input stream.
     *
     * <p>
     *  缓冲区中的当前位置。这是要从<code> buf </code>数组读取的下一个字符的索引。
     * <p>
     *  该值始终在<code> 0 </code>到<code> count </code>的范围内。
     * 如果它小于<code> count </code>,则<code> buf [pos] </code>是要作为输入提供的下一个字节;如果它等于<code> count </code>,则下一个<code>
     * 读</code>或<code> skip </code>操作将需要从所包含的输入流读取更多的字节。
     *  该值始终在<code> 0 </code>到<code> count </code>的范围内。
     * 
     * 
     * @see     java.io.BufferedInputStream#buf
     */
    protected int pos;

    /**
     * The value of the <code>pos</code> field at the time the last
     * <code>mark</code> method was called.
     * <p>
     * This value is always
     * in the range <code>-1</code> through <code>pos</code>.
     * If there is no marked position in  the input
     * stream, this field is <code>-1</code>. If
     * there is a marked position in the input
     * stream,  then <code>buf[markpos]</code>
     * is the first byte to be supplied as input
     * after a <code>reset</code> operation. If
     * <code>markpos</code> is not <code>-1</code>,
     * then all bytes from positions <code>buf[markpos]</code>
     * through  <code>buf[pos-1]</code> must remain
     * in the buffer array (though they may be
     * moved to  another place in the buffer array,
     * with suitable adjustments to the values
     * of <code>count</code>,  <code>pos</code>,
     * and <code>markpos</code>); they may not
     * be discarded unless and until the difference
     * between <code>pos</code> and <code>markpos</code>
     * exceeds <code>marklimit</code>.
     *
     * <p>
     *  在调用最后一个<code>标记</code>方法时<code> pos </code>字段的值。
     * <p>
     * 此值始终在<code> -1 </code>到<code> pos </code>的范围内。如果输入流中没有标记位置,则此字段为<code> -1 </code>。
     * 如果输入流中有标记位置,则<code> buf [markpos] </code>是在<code> reset </code>操作之后作为输入提供的第一个字节。
     * 如果<code> markpos </code>不是<code> -1 </code>,那么从位置<code> buf [markpos] </code>到<code> buf [pos-1] </code >
     * 必须保留在缓冲区数组中(尽管它们可以移动到缓冲区数组中的另一个位置,并对<code> count </code>,<code> pos </code>和<code> markpos </code>);除
     * 非<code> pos </code>和<code> markpos </code>之间的差超过<code> marklimit </code>,否则它们不能被丢弃。
     * 如果输入流中有标记位置,则<code> buf [markpos] </code>是在<code> reset </code>操作之后作为输入提供的第一个字节。
     * 
     * 
     * @see     java.io.BufferedInputStream#mark(int)
     * @see     java.io.BufferedInputStream#pos
     */
    protected int markpos = -1;

    /**
     * The maximum read ahead allowed after a call to the
     * <code>mark</code> method before subsequent calls to the
     * <code>reset</code> method fail.
     * Whenever the difference between <code>pos</code>
     * and <code>markpos</code> exceeds <code>marklimit</code>,
     * then the  mark may be dropped by setting
     * <code>markpos</code> to <code>-1</code>.
     *
     * <p>
     *  在对<code> reset </code>方法的后续调用失败之前,在调用<code>标记</code>方法后允许的最大预读数。
     * 每当<code> pos </code>和<code> markpos </code>之间的差异超过<code> marklimit </code>,则可以通过将<code> markpos </code>
     * 设置为<code > -1 </code>。
     *  在对<code> reset </code>方法的后续调用失败之前,在调用<code>标记</code>方法后允许的最大预读数。
     * 
     * 
     * @see     java.io.BufferedInputStream#mark(int)
     * @see     java.io.BufferedInputStream#reset()
     */
    protected int marklimit;

    /**
     * Check to make sure that underlying input stream has not been
     * nulled out due to close; if not return it;
     * <p>
     *  检查以确保底层输入流没有因为关闭而被清零;如果不返回它;
     * 
     */
    private InputStream getInIfOpen() throws IOException {
        InputStream input = in;
        if (input == null)
            throw new IOException("Stream closed");
        return input;
    }

    /**
     * Check to make sure that buffer has not been nulled out due to
     * close; if not return it;
     * <p>
     *  检查以确保缓冲区没有因为关闭而被清零;如果不返回它;
     * 
     */
    private byte[] getBufIfOpen() throws IOException {
        byte[] buffer = buf;
        if (buffer == null)
            throw new IOException("Stream closed");
        return buffer;
    }

    /**
     * Creates a <code>BufferedInputStream</code>
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. An internal
     * buffer array is created and  stored in <code>buf</code>.
     *
     * <p>
     * 创建一个<code> BufferedInputStream </code>并保存其参数,输入流<code>在</code>中,供以后使用。
     * 创建内部缓冲区数组并存储在<code> buf </code>中。
     * 
     * 
     * @param   in   the underlying input stream.
     */
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Creates a <code>BufferedInputStream</code>
     * with the specified buffer size,
     * and saves its  argument, the input stream
     * <code>in</code>, for later use.  An internal
     * buffer array of length  <code>size</code>
     * is created and stored in <code>buf</code>.
     *
     * <p>
     *  创建一个具有指定缓冲区大小的<code> BufferedInputStream </code>,并保存其参数,输入流<code>在</code>中,供以后使用。
     * 创建长度为<code> size </code>的内部缓冲区数组并存储在<code> buf </code>中。
     * 
     * 
     * @param   in     the underlying input stream.
     * @param   size   the buffer size.
     * @exception IllegalArgumentException if {@code size <= 0}.
     */
    public BufferedInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        buf = new byte[size];
    }

    /**
     * Fills the buffer with more data, taking into account
     * shuffling and other tricks for dealing with marks.
     * Assumes that it is being called by a synchronized method.
     * This method also assumes that all data has already been read in,
     * hence pos > count.
     * <p>
     *  用更多的数据填充缓冲区,考虑洗牌和其他处理标记的技巧。假设它正在被一个同步方法调用。此方法还假定所有数据都已读入,因此pos> count。
     * 
     */
    private void fill() throws IOException {
        byte[] buffer = getBufIfOpen();
        if (markpos < 0)
            pos = 0;            /* no mark: throw away the buffer */
        else if (pos >= buffer.length)  /* no room left in buffer */
            if (markpos > 0) {  /* can throw away early part of the buffer */
                int sz = pos - markpos;
                System.arraycopy(buffer, markpos, buffer, 0, sz);
                pos = sz;
                markpos = 0;
            } else if (buffer.length >= marklimit) {
                markpos = -1;   /* buffer got too big, invalidate mark */
                pos = 0;        /* drop buffer contents */
            } else if (buffer.length >= MAX_BUFFER_SIZE) {
                throw new OutOfMemoryError("Required array size too large");
            } else {            /* grow buffer */
                int nsz = (pos <= MAX_BUFFER_SIZE - pos) ?
                        pos * 2 : MAX_BUFFER_SIZE;
                if (nsz > marklimit)
                    nsz = marklimit;
                byte nbuf[] = new byte[nsz];
                System.arraycopy(buffer, 0, nbuf, 0, pos);
                if (!bufUpdater.compareAndSet(this, buffer, nbuf)) {
                    // Can't replace buf if there was an async close.
                    // Note: This would need to be changed if fill()
                    // is ever made accessible to multiple threads.
                    // But for now, the only way CAS can fail is via close.
                    // assert buf == null;
                    throw new IOException("Stream closed");
                }
                buffer = nbuf;
            }
        count = pos;
        int n = getInIfOpen().read(buffer, pos, buffer.length - pos);
        if (n > 0)
            count = n + pos;
    }

    /**
     * See
     * the general contract of the <code>read</code>
     * method of <code>InputStream</code>.
     *
     * <p>
     *  请参阅<code> InputStream </code>的<code> read </code>方法的一般合同。
     * 
     * 
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if this input stream has been closed by
     *                          invoking its {@link #close()} method,
     *                          or an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     */
    public synchronized int read() throws IOException {
        if (pos >= count) {
            fill();
            if (pos >= count)
                return -1;
        }
        return getBufIfOpen()[pos++] & 0xff;
    }

    /**
     * Read characters into a portion of an array, reading from the underlying
     * stream at most once if necessary.
     * <p>
     *  将字符读入数组的一部分,如果需要,从底层流读取最多一次。
     * 
     */
    private int read1(byte[] b, int off, int len) throws IOException {
        int avail = count - pos;
        if (avail <= 0) {
            /* If the requested length is at least as large as the buffer, and
               if there is no mark/reset activity, do not bother to copy the
               bytes into the local buffer.  In this way buffered streams will
            /* <p>
            /*  如果没有标记/复位活动,不要麻烦地将字节复制到本地缓冲区。这样缓冲流就会
            /* 
            /* 
               cascade harmlessly. */
            if (len >= getBufIfOpen().length && markpos < 0) {
                return getInIfOpen().read(b, off, len);
            }
            fill();
            avail = count - pos;
            if (avail <= 0) return -1;
        }
        int cnt = (avail < len) ? avail : len;
        System.arraycopy(getBufIfOpen(), pos, b, off, cnt);
        pos += cnt;
        return cnt;
    }

    /**
     * Reads bytes from this byte-input stream into the specified byte array,
     * starting at the given offset.
     *
     * <p> This method implements the general contract of the corresponding
     * <code>{@link InputStream#read(byte[], int, int) read}</code> method of
     * the <code>{@link InputStream}</code> class.  As an additional
     * convenience, it attempts to read as many bytes as possible by repeatedly
     * invoking the <code>read</code> method of the underlying stream.  This
     * iterated <code>read</code> continues until one of the following
     * conditions becomes true: <ul>
     *
     *   <li> The specified number of bytes have been read,
     *
     *   <li> The <code>read</code> method of the underlying stream returns
     *   <code>-1</code>, indicating end-of-file, or
     *
     *   <li> The <code>available</code> method of the underlying stream
     *   returns zero, indicating that further input requests would block.
     *
     * </ul> If the first <code>read</code> on the underlying stream returns
     * <code>-1</code> to indicate end-of-file then this method returns
     * <code>-1</code>.  Otherwise this method returns the number of bytes
     * actually read.
     *
     * <p> Subclasses of this class are encouraged, but not required, to
     * attempt to read as many bytes as possible in the same fashion.
     *
     * <p>
     *  从该字节输入流读取字节到指定的字节数组,从给定的偏移量开始。
     * 
     * <p>此方法实施<code> {@ link InputStream} </code的相应<code> {@ link InputStream#read(byte [],int,int)read} </code>
     *  >类。
     * 作为额外的方便,它尝试通过重复调用底层流的<code> read </code>方法来读取尽可能多的字节。这个迭代的<code> read </code>继续,直到下列条件之一变为true：<ul>。
     * 
     *  <li>已读取指定的字节数,
     * 
     *  <li>基础流的<code> read </code>方法返回<code> -1 </code>,表示文件结束,或
     * 
     *  <li>基本流的<code> available </code>方法返回零,表示进一步的输入请求将被阻止。
     * 
     *  </ul>如果底层流上的第一个<code> read </code>返回<code> -1 </code>以指示文件结束,则此方法返回<code> -1 </code>。
     * 否则,此方法返回实际读取的字节数。
     * 
     *  <p>鼓励这个类的子类,但不是必需的,尝试以同样的方式读取尽可能多的字节。
     * 
     * 
     * @param      b     destination buffer.
     * @param      off   offset at which to start storing bytes.
     * @param      len   maximum number of bytes to read.
     * @return     the number of bytes read, or <code>-1</code> if the end of
     *             the stream has been reached.
     * @exception  IOException  if this input stream has been closed by
     *                          invoking its {@link #close()} method,
     *                          or an I/O error occurs.
     */
    public synchronized int read(byte b[], int off, int len)
        throws IOException
    {
        getBufIfOpen(); // Check for closed stream
        if ((off | len | (off + len) | (b.length - (off + len))) < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int n = 0;
        for (;;) {
            int nread = read1(b, off + n, len - n);
            if (nread <= 0)
                return (n == 0) ? nread : n;
            n += nread;
            if (n >= len)
                return n;
            // if not closed but no bytes available, return
            InputStream input = in;
            if (input != null && input.available() <= 0)
                return n;
        }
    }

    /**
     * See the general contract of the <code>skip</code>
     * method of <code>InputStream</code>.
     *
     * <p>
     *  请参阅<code> InputStream </code>的<code> skip </code>方法的一般合同。
     * 
     * 
     * @exception  IOException  if the stream does not support seek,
     *                          or if this input stream has been closed by
     *                          invoking its {@link #close()} method, or an
     *                          I/O error occurs.
     */
    public synchronized long skip(long n) throws IOException {
        getBufIfOpen(); // Check for closed stream
        if (n <= 0) {
            return 0;
        }
        long avail = count - pos;

        if (avail <= 0) {
            // If no mark position set then don't keep in buffer
            if (markpos <0)
                return getInIfOpen().skip(n);

            // Fill in buffer to save bytes for reset
            fill();
            avail = count - pos;
            if (avail <= 0)
                return 0;
        }

        long skipped = (avail < n) ? avail : n;
        pos += skipped;
        return skipped;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation might be
     * the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * <p>
     * This method returns the sum of the number of bytes remaining to be read in
     * the buffer (<code>count&nbsp;- pos</code>) and the result of calling the
     * {@link java.io.FilterInputStream#in in}.available().
     *
     * <p>
     * 返回此输入流中可以读取(或跳过)的字节数的估计值,而不会通过下一次调用此输入流的方法进行阻止。下一次调用可能是同一个线程或另一个线程。单个读取或跳过这么多字节不会阻塞,但可以读取或跳过更少的字节。
     * <p>
     *  此方法返回缓冲区中要读取的剩余字节数(<code> count&nbsp;  -  pos </code>)和调用{@link java.io.FilterInputStream#in in。
     *  ()。
     * 
     * 
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking.
     * @exception  IOException  if this input stream has been closed by
     *                          invoking its {@link #close()} method,
     *                          or an I/O error occurs.
     */
    public synchronized int available() throws IOException {
        int n = count - pos;
        int avail = getInIfOpen().available();
        return n > (Integer.MAX_VALUE - avail)
                    ? Integer.MAX_VALUE
                    : n + avail;
    }

    /**
     * See the general contract of the <code>mark</code>
     * method of <code>InputStream</code>.
     *
     * <p>
     *  请参阅<code> InputStream </code>的<code> mark </code>方法的一般合同。
     * 
     * 
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.BufferedInputStream#reset()
     */
    public synchronized void mark(int readlimit) {
        marklimit = readlimit;
        markpos = pos;
    }

    /**
     * See the general contract of the <code>reset</code>
     * method of <code>InputStream</code>.
     * <p>
     * If <code>markpos</code> is <code>-1</code>
     * (no mark has been set or the mark has been
     * invalidated), an <code>IOException</code>
     * is thrown. Otherwise, <code>pos</code> is
     * set equal to <code>markpos</code>.
     *
     * <p>
     *  请参阅<code> InputStream </code>的<code> reset </code>方法的一般合同。
     * <p>
     *  如果<code> markpos </code>是<code> -1 </code>(未设置标记或标记已失效),则抛出<code> IOException </code>。
     * 否则,<code> pos </code>设置为等于<code> markpos </code>。
     * 
     * 
     * @exception  IOException  if this stream has not been marked or,
     *                  if the mark has been invalidated, or the stream
     *                  has been closed by invoking its {@link #close()}
     *                  method, or an I/O error occurs.
     * @see        java.io.BufferedInputStream#mark(int)
     */
    public synchronized void reset() throws IOException {
        getBufIfOpen(); // Cause exception if closed
        if (markpos < 0)
            throw new IOException("Resetting to invalid mark");
        pos = markpos;
    }

    /**
     * Tests if this input stream supports the <code>mark</code>
     * and <code>reset</code> methods. The <code>markSupported</code>
     * method of <code>BufferedInputStream</code> returns
     * <code>true</code>.
     *
     * <p>
     *  测试此输入流是否支持<code>标记</code>和<code>重置</code>方法。
     *  <code> BufferedInputStream </code>的<code> markSupported </code>方法返回<code> true </code>。
     * 
     * 
     * @return  a <code>boolean</code> indicating if this stream type supports
     *          the <code>mark</code> and <code>reset</code> methods.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return true;
    }

    /**
     * Closes this input stream and releases any system resources
     * associated with the stream.
     * Once the stream has been closed, further read(), available(), reset(),
     * or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * <p>
     *  关闭此输入流,并释放与流相关联的任何系统资源。一旦流被关闭,进一步的read(),available(),reset()或skip()调用将抛出一个IOException异常。
     * 关闭先前关闭的流没有任何效果。
     * 
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {
        byte[] buffer;
        while ( (buffer = buf) != null) {
            if (bufUpdater.compareAndSet(this, buffer, null)) {
                InputStream input = in;
                in = null;
                if (input != null)
                    input.close();
                return;
            }
            // Else retry in case a new buf was CASed in fill()
        }
    }
}
