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

/**
 * This abstract class is the superclass of all classes representing
 * an input stream of bytes.
 *
 * <p> Applications that need to define a subclass of <code>InputStream</code>
 * must always provide a method that returns the next byte of input.
 *
 * <p>
 *  这个抽象类是表示字节输入流的所有类的超类
 * 
 *  <p>需要定义<code> InputStream </code>子类的应用程序必须始终提供返回输入的下一个字节的方法
 * 
 * 
 * @author  Arthur van Hoff
 * @see     java.io.BufferedInputStream
 * @see     java.io.ByteArrayInputStream
 * @see     java.io.DataInputStream
 * @see     java.io.FilterInputStream
 * @see     java.io.InputStream#read()
 * @see     java.io.OutputStream
 * @see     java.io.PushbackInputStream
 * @since   JDK1.0
 */
public abstract class InputStream implements Closeable {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * <p> A subclass must provide an implementation of this method.
     *
     * <p>
     *  从输入流读取数据的下一个字节值字节作为<code> int </code>在<code> 0 </code>到<code> 255 </code>范围内返回如果没有字节可用因为已经到达流的结尾,返回值
     * <code> -1 </code>此方法阻塞,直到输入数据可用,流的结束被检测到,或抛出异常。
     * 
     * <p>子类必须提供此方法的实现
     * 
     * 
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public abstract int read() throws IOException;

    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     *
     * <p> If the length of <code>b</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at the
     * end of the file, the value <code>-1</code> is returned; otherwise, at
     * least one byte is read and stored into <code>b</code>.
     *
     * <p> The first byte read is stored into element <code>b[0]</code>, the
     * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     * at most, equal to the length of <code>b</code>. Let <i>k</i> be the
     * number of bytes actually read; these bytes will be stored in elements
     * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[</code><i>k</i><code>]</code> through
     * <code>b[b.length-1]</code> unaffected.
     *
     * <p> The <code>read(b)</code> method for class <code>InputStream</code>
     * has the same effect as: <pre><code> read(b, 0, b.length) </code></pre>
     *
     * <p>
     *  从输入流读取一些字节数并将它们存储到缓冲区数组<code> b </code>实际读取的字节数作为整数返回此方法阻塞,直到输入数据可用,检测到文件结束,或抛出异常
     * 
     *  <p>如果<code> b </code>的长度为零,则不读取任何字节,并返回<code> 0 </code>否则,尝试读取至少一个字节如果没有字节可用,因为流位于文件的末尾,则返回值<code> -
     * 1 </code>;否则,至少读取一个字节并存储到<code> b </code>中。
     * 
     * <p>读取的第一个字节存储在<code> b [0] </code>中,下一个读入<code> b [1] </code>,等等。
     * 大多数,等于<code> b </code>的长度让<k>是实际读取的字节数;这些字节将存储在元素<code> b [0] </code>到<code> b [</code> <i> k </i> <code>
     *  -1] </code>代码> b </code> <i> k </i> <code>] </code>通过<code> b [blength-1] </code>。
     * <p>读取的第一个字节存储在<code> b [0] </code>中,下一个读入<code> b [1] </code>,等等。
     * 
     *  <p>类<code> InputStream </code>的<code> read(b)</code>方法具有相同的效果：<pre> <code> read(b,0,blength)</code> 
     * </pre>。
     * 
     * 
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  If the first byte cannot be read for any reason
     * other than the end of the file, if the input stream has been closed, or
     * if some other I/O error occurs.
     * @exception  NullPointerException  if <code>b</code> is <code>null</code>.
     * @see        java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * <code>len</code> bytes, but a smaller number may be read.
     * The number of bytes actually read is returned as an integer.
     *
     * <p> This method blocks until input data is available, end of file is
     * detected, or an exception is thrown.
     *
     * <p> If <code>len</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at end of
     * file, the value <code>-1</code> is returned; otherwise, at least one
     * byte is read and stored into <code>b</code>.
     *
     * <p> The first byte read is stored into element <code>b[off]</code>, the
     * next one into <code>b[off+1]</code>, and so on. The number of bytes read
     * is, at most, equal to <code>len</code>. Let <i>k</i> be the number of
     * bytes actually read; these bytes will be stored in elements
     * <code>b[off]</code> through <code>b[off+</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[off+</code><i>k</i><code>]</code> through
     * <code>b[off+len-1]</code> unaffected.
     *
     * <p> In every case, elements <code>b[0]</code> through
     * <code>b[off]</code> and elements <code>b[off+len]</code> through
     * <code>b[b.length-1]</code> are unaffected.
     *
     * <p> The <code>read(b,</code> <code>off,</code> <code>len)</code> method
     * for class <code>InputStream</code> simply calls the method
     * <code>read()</code> repeatedly. If the first such call results in an
     * <code>IOException</code>, that exception is returned from the call to
     * the <code>read(b,</code> <code>off,</code> <code>len)</code> method.  If
     * any subsequent call to <code>read()</code> results in a
     * <code>IOException</code>, the exception is caught and treated as if it
     * were end of file; the bytes read up to that point are stored into
     * <code>b</code> and the number of bytes read before the exception
     * occurred is returned. The default implementation of this method blocks
     * until the requested amount of input data <code>len</code> has been read,
     * end of file is detected, or an exception is thrown. Subclasses are encouraged
     * to provide a more efficient implementation of this method.
     *
     * <p>
     * 从输入流中读取<code> len </code>字节的数据到字节数组中尝试读取多达<code> len </code>字节,但是可以读取较小的数字实际读取的字节数作为整数返回
     * 
     *  <p>此方法阻止,直到输入数据可用,文件结束被检测到,或抛出异常
     * 
     *  <p>如果<code> len </code>为零,则不读取任何字节,并返回<code> 0 </code>否则,尝试读取至少一个字节如果没有字节可用,因为流在​​文件的结尾,则返回值<code> -
     * 1 </code>;否则,至少读取一个字节并存储到<code> b </code>中。
     * 
     * <p>读取的第一个字节存储在<code> b [off] </code>中,下一个字节存储在<code> b [off + 1] </code> ,最多等于<code> len </code>让<k>是
     * 实际读取的字节数;这些字节将通过<code> b [off + </code> <i> k </i> <code> -1] </code>存储在元素<code> b [off] </code> <code>
     *  b [off + </code> <i> k </i> <code>] </code>通过<code> b [off + len-1] </code>。
     * 
     *  <p>在每种情况下,元素<code> b [0] </code>通过<code> b [off] </code> b [blength-1] </code>不受影响
     * 
     * <p> <code> read(b,</code> <code> off,</code> <code> len)</code>方法用于类<code> InputStream </code>只是调用方法<code >
     *  read()</code> repeated如果第一个这样的调用导致<code> IOException </code>,那么从调用<code> read(b,</code> <code> ,</code>
     *  <code> len)</code>方法如果对<code> read()</code>的任何后续调用导致<code> IOException </code>,则异常被捕获,它是文件的结束;到此为止读取
     * 的字节存储到<code> b </code>中,并返回在发生异常之前读取的字节数。
     * 此方法的默认实现阻塞,直到请求的输入数据量<code> len <代码>已被读取,文件结束被检测到,或抛出异常鼓励子类提供这种方法的更有效的实现。
     * 
     * 
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written.
     * @param      len   the maximum number of bytes to read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the input stream has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @see        java.io.InputStream#read()
     */
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from this input
     * stream. The <code>skip</code> method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly <code>0</code>.
     * This may result from any of a number of conditions; reaching end of file
     * before <code>n</code> bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned. If {@code n} is
     * negative, the {@code skip} method for class {@code InputStream} always
     * returns 0, and no bytes are skipped. Subclasses may handle the negative
     * value differently.
     *
     * <p> The <code>skip</code> method of this class creates a
     * byte array and then repeatedly reads into it until <code>n</code> bytes
     * have been read or the end of the stream has been reached. Subclasses are
     * encouraged to provide a more efficient implementation of this method.
     * For instance, the implementation may depend on the ability to seek.
     *
     * <p>
     * 跳过并丢弃来自该输入流的<code> n </code>字节的数据<code> skip </code>方法可能由于各种原因跳过一些较小数量的字节,可能<code> > 0 </code>这可能是由多个
     * 条件中的任何一个引起的;在跳过<code> n </code>字节之前到达文件结束只有一种可能性返回实际跳过的字节数如果{@code n}为负数,则{@code { InputStream}总是返回0,
     * 并且没有字节被跳过子类可以不同地处理负值。
     * 
     * <p>此类的<code> skip </code>方法创建一个字节数组,然后重复读取,直到<code> n </code>字节已读取或流的结尾已经达到子类鼓励提供这种方法的更有效的实现。
     * 例如,实现可能取决于寻找的能力。
     * 
     * 
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if the stream does not support seek,
     *                          or if some other I/O error occurs.
     */
    public long skip(long n) throws IOException {

        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int)Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int)Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation
     * might be the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     *
     * <p> Note that while some implementations of {@code InputStream} will return
     * the total number of bytes in the stream, many will not.  It is
     * never correct to use the return value of this method to allocate
     * a buffer intended to hold all data in this stream.
     *
     * <p> A subclass' implementation of this method may choose to throw an
     * {@link IOException} if this input stream has been closed by
     * invoking the {@link #close()} method.
     *
     * <p> The {@code available} method for class {@code InputStream} always
     * returns {@code 0}.
     *
     * <p> This method should be overridden by subclasses.
     *
     * <p>
     *  返回可以从此输入流读取(或跳过)的字节数的估计值,而不会通过下一次调用此输入流的方法进行阻止下一次调用可能是同一个线程或另一个线程单个读取或跳过的这么多字节不会阻塞,但可能会读取或跳过更少的字节
     * 
     * <p>请注意,虽然{@code InputStream}的某些实现将返回流中的总字节数,但很多不会使用此方法的返回值来分配一个缓冲区用于保存所有数据这个流
     * 
     *  <p>如果此输入流已通过调用{@link #close()}方法关闭,则此方法的子类实现可以选择抛出{@link IOException}
     * 
     *  <p> {@code InputStream}类的{@code available}方法总是返回{@code 0}
     * 
     *  <p>此方法应该被子类覆盖
     * 
     * 
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking or {@code 0} when
     *             it reaches the end of the input stream.
     * @exception  IOException if an I/O error occurs.
     */
    public int available() throws IOException {
        return 0;
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     *
     * <p> The <code>close</code> method of <code>InputStream</code> does
     * nothing.
     *
     * <p>
     *  关闭此输入流,并释放与流相关联的任何系统资源
     * 
     *  <p> <code> InputStream </code>的<code> close </code>方法什么都不做
     * 
     * 
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {}

    /**
     * Marks the current position in this input stream. A subsequent call to
     * the <code>reset</code> method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     *
     * <p> The <code>readlimit</code> arguments tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     *
     * <p> The general contract of <code>mark</code> is that, if the method
     * <code>markSupported</code> returns <code>true</code>, the stream somehow
     * remembers all the bytes read after the call to <code>mark</code> and
     * stands ready to supply those same bytes again if and whenever the method
     * <code>reset</code> is called.  However, the stream is not required to
     * remember any data at all if more than <code>readlimit</code> bytes are
     * read from the stream before <code>reset</code> is called.
     *
     * <p> Marking a closed stream should not have any effect on the stream.
     *
     * <p> The <code>mark</code> method of <code>InputStream</code> does
     * nothing.
     *
     * <p>
     * 标记此输入流中的当前位置随后调用<code> reset </code>方法会在最后一个标记位置重新定位此流,以便后续读取重新读取相同的字节
     * 
     *  <p> <code> readlimit </code>参数告诉此输入流允许在标记位置无效之前读取许多字节
     * 
     * <p> <code> mark </code>的一般合同是,如果<code> markSupported </code>方法返回<code> true </code>,流会以某种方式记住调用后读取的所有
     * 字节到<code>标记</code>,并且随时准备好在方法<code> reset </code>被调用时再次提供这些相同的字节。
     * 但是,如果超过<代码> readlimit </code>字节在调用<code> reset </code>之前从流中读取。
     * 
     *  <p>标记封闭的串流不应对串流产生任何影响
     * 
     *  <p> <code> InputStream </code>的<code>标记</code>方法什么也不做
     * 
     * 
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {}

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     *
     * <p> The general contract of <code>reset</code> is:
     *
     * <ul>
     * <li> If the method <code>markSupported</code> returns
     * <code>true</code>, then:
     *
     *     <ul><li> If the method <code>mark</code> has not been called since
     *     the stream was created, or the number of bytes read from the stream
     *     since <code>mark</code> was last called is larger than the argument
     *     to <code>mark</code> at that last call, then an
     *     <code>IOException</code> might be thrown.
     *
     *     <li> If such an <code>IOException</code> is not thrown, then the
     *     stream is reset to a state such that all the bytes read since the
     *     most recent call to <code>mark</code> (or since the start of the
     *     file, if <code>mark</code> has not been called) will be resupplied
     *     to subsequent callers of the <code>read</code> method, followed by
     *     any bytes that otherwise would have been the next input data as of
     *     the time of the call to <code>reset</code>. </ul>
     *
     * <li> If the method <code>markSupported</code> returns
     * <code>false</code>, then:
     *
     *     <ul><li> The call to <code>reset</code> may throw an
     *     <code>IOException</code>.
     *
     *     <li> If an <code>IOException</code> is not thrown, then the stream
     *     is reset to a fixed state that depends on the particular type of the
     *     input stream and how it was created. The bytes that will be supplied
     *     to subsequent callers of the <code>read</code> method depend on the
     *     particular type of the input stream. </ul></ul>
     *
     * <p>The method <code>reset</code> for class <code>InputStream</code>
     * does nothing except throw an <code>IOException</code>.
     *
     * <p>
     *  将此流重新定位到在此输入流上最后调用<code>标记</code>方法时的位置
     * 
     * <p> <code> reset </code>的一般合同是：
     * 
     * <ul>
     *  <li>如果方法<code> markSupported </code>返回<code> true </code>,则：
     * 
     *  <ul> <li>如果自创建流后尚未调用<code>标记</code>方法,或自上次调用<code>标记</code>后从流中读取的字节数较大比在最后调用</code>的<code>标记</code>
     * 的参数,则可能抛出<code> IOException </code>。
     * 
     * <li>如果未抛出此类<code> IOException </code>,则会将流重置为一种状态,以便自最近调用<code>标记</code>以来读取的所有字节(或自从如果<code>标记</code>
     * 未被调用)将被重新提供给<code> read </code>方法的后续调用者,随后是任何字节,否则将是下一个输入数据截至调用<code> reset </code> </ul>的时间。
     * 
     * 
     * @exception  IOException  if this stream has not been marked or if the
     *               mark has been invalidated.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. Whether or not <code>mark</code> and
     * <code>reset</code> are supported is an invariant property of a
     * particular input stream instance. The <code>markSupported</code> method
     * of <code>InputStream</code> returns <code>false</code>.
     *
     * <p>
     *  <li>如果方法<code> markSupported </code>返回<code> false </code>,则：
     * 
     *  <ul> <li>调用<code> reset </code>可能会抛出<code> IOException </code>
     * 
     * <li>如果未抛出<code> IOException </code>,则流会重置为固定状态,此状态取决于输入流的特定类型及其创建方式将提供给后续调用者的字节的<code> read </code>方法
     * 取决于输入流</ul> </ul>的特定类型。
     * 
     * 
     * @return  <code>true</code> if this stream instance supports the mark
     *          and reset methods; <code>false</code> otherwise.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

}
