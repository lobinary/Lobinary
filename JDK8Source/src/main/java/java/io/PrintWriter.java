/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Objects;
import java.util.Formatter;
import java.util.Locale;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Prints formatted representations of objects to a text-output stream.  This
 * class implements all of the <tt>print</tt> methods found in {@link
 * PrintStream}.  It does not contain methods for writing raw bytes, for which
 * a program should use unencoded byte streams.
 *
 * <p> Unlike the {@link PrintStream} class, if automatic flushing is enabled
 * it will be done only when one of the <tt>println</tt>, <tt>printf</tt>, or
 * <tt>format</tt> methods is invoked, rather than whenever a newline character
 * happens to be output.  These methods use the platform's own notion of line
 * separator rather than the newline character.
 *
 * <p> Methods in this class never throw I/O exceptions, although some of its
 * constructors may.  The client may inquire as to whether any errors have
 * occurred by invoking {@link #checkError checkError()}.
 *
 * <p>
 *  将对象的格式化表示打印到文本输出流。此类实现{@link PrintStream}中所有的<tt>打印</tt>方法。它不包含用于写入原始字节的方法,程序应使用未编码的字节流。
 * 
 *  <p>与{@link PrintStream}类不同,如果启用了自动刷新,则只有当<tt> println </tt>,<tt> printf </tt>或<tt> / tt>方法被调用,而不是每当一
 * 个换行字符碰巧被输出。
 * 这些方法使用平台自己的行分隔符的概念,而不是换行符。
 * 
 *  <p>此类中的方法不会抛出I / O异常,尽管其中的一些构造函数可能。客户端可以通过调用{@link #checkError checkError()}来查询是否发生了任何错误。
 * 
 * 
 * @author      Frank Yellin
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class PrintWriter extends Writer {

    /**
     * The underlying character-output stream of this
     * <code>PrintWriter</code>.
     *
     * <p>
     *  这个<code> PrintWriter </code>的基本字符输出流。
     * 
     * 
     * @since 1.2
     */
    protected Writer out;

    private final boolean autoFlush;
    private boolean trouble = false;
    private Formatter formatter;
    private PrintStream psOut = null;

    /**
     * Line separator string.  This is the value of the line.separator
     * property at the moment that the stream was created.
     * <p>
     *  行分隔符字符串。这是创建流时的line.separator属性的值。
     * 
     */
    private final String lineSeparator;

    /**
     * Returns a charset object for the given charset name.
     * <p>
     *  返回给定字符集名称的charset对象。
     * 
     * 
     * @throws NullPointerException          is csn is null
     * @throws UnsupportedEncodingException  if the charset is not supported
     */
    private static Charset toCharset(String csn)
        throws UnsupportedEncodingException
    {
        Objects.requireNonNull(csn, "charsetName");
        try {
            return Charset.forName(csn);
        } catch (IllegalCharsetNameException|UnsupportedCharsetException unused) {
            // UnsupportedEncodingException should be thrown
            throw new UnsupportedEncodingException(csn);
        }
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing.
     *
     * <p>
     *  创建新的PrintWriter,而不进行自动线刷新。
     * 
     * 
     * @param  out        A character-output stream
     */
    public PrintWriter (Writer out) {
        this(out, false);
    }

    /**
     * Creates a new PrintWriter.
     *
     * <p>
     *  创建新的PrintWriter。
     * 
     * 
     * @param  out        A character-output stream
     * @param  autoFlush  A boolean; if true, the <tt>println</tt>,
     *                    <tt>printf</tt>, or <tt>format</tt> methods will
     *                    flush the output buffer
     */
    public PrintWriter(Writer out,
                       boolean autoFlush) {
        super(out);
        this.out = out;
        this.autoFlush = autoFlush;
        lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing, from an
     * existing OutputStream.  This convenience constructor creates the
     * necessary intermediate OutputStreamWriter, which will convert characters
     * into bytes using the default character encoding.
     *
     * <p>
     * 从现有的OutputStream创建新的PrintWriter,而不自动进行行刷新。这个方便的构造函数创建必要的中间OutputStreamWriter,它将使用默认的字符编码将字符转换为字节。
     * 
     * 
     * @param  out        An output stream
     *
     * @see java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     */
    public PrintWriter(OutputStream out) {
        this(out, false);
    }

    /**
     * Creates a new PrintWriter from an existing OutputStream.  This
     * convenience constructor creates the necessary intermediate
     * OutputStreamWriter, which will convert characters into bytes using the
     * default character encoding.
     *
     * <p>
     *  从现有的OutputStream创建一个新的PrintWriter。这个方便的构造函数创建必要的中间OutputStreamWriter,它将使用默认的字符编码将字符转换为字节。
     * 
     * 
     * @param  out        An output stream
     * @param  autoFlush  A boolean; if true, the <tt>println</tt>,
     *                    <tt>printf</tt>, or <tt>format</tt> methods will
     *                    flush the output buffer
     *
     * @see java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     */
    public PrintWriter(OutputStream out, boolean autoFlush) {
        this(new BufferedWriter(new OutputStreamWriter(out)), autoFlush);

        // save print stream for error propagation
        if (out instanceof java.io.PrintStream) {
            psOut = (PrintStream) out;
        }
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing, with the
     * specified file name.  This convenience constructor creates the necessary
     * intermediate {@link java.io.OutputStreamWriter OutputStreamWriter},
     * which will encode characters using the {@linkplain
     * java.nio.charset.Charset#defaultCharset() default charset} for this
     * instance of the Java virtual machine.
     *
     * <p>
     *  使用指定的文件名创建新的PrintWriter,而不进行自动换行。
     * 这个方便的构造函数创建了必要的中间{@link java.io.OutputStreamWriter OutputStreamWriter},它将使用{@linkplain java.nio.charset.Charset#defaultCharset()default charset}
     * 为Java虚拟机实例编码字符。
     *  使用指定的文件名创建新的PrintWriter,而不进行自动换行。
     * 
     * 
     * @param  fileName
     *         The name of the file to use as the destination of this writer.
     *         If the file exists then it will be truncated to zero size;
     *         otherwise, a new file will be created.  The output will be
     *         written to the file and is buffered.
     *
     * @throws  FileNotFoundException
     *          If the given string does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(fileName)} denies write
     *          access to the file
     *
     * @since  1.5
     */
    public PrintWriter(String fileName) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName))),
             false);
    }

    /* Private constructor */
    private PrintWriter(Charset charset, File file)
        throws FileNotFoundException
    {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset)),
             false);
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing, with the
     * specified file name and charset.  This convenience constructor creates
     * the necessary intermediate {@link java.io.OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the provided
     * charset.
     *
     * <p>
     *  使用指定的文件名和字符集创建新的PrintWriter,而不自动进行行刷新。
     * 这个方便的构造函数创建必要的中间{@link java.io.OutputStreamWriter OutputStreamWriter},它将使用提供的字符集对字符进行编码。
     * 
     * 
     * @param  fileName
     *         The name of the file to use as the destination of this writer.
     *         If the file exists then it will be truncated to zero size;
     *         otherwise, a new file will be created.  The output will be
     *         written to the file and is buffered.
     *
     * @param  csn
     *         The name of a supported {@linkplain java.nio.charset.Charset
     *         charset}
     *
     * @throws  FileNotFoundException
     *          If the given string does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(fileName)} denies write
     *          access to the file
     *
     * @throws  UnsupportedEncodingException
     *          If the named charset is not supported
     *
     * @since  1.5
     */
    public PrintWriter(String fileName, String csn)
        throws FileNotFoundException, UnsupportedEncodingException
    {
        this(toCharset(csn), new File(fileName));
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing, with the
     * specified file.  This convenience constructor creates the necessary
     * intermediate {@link java.io.OutputStreamWriter OutputStreamWriter},
     * which will encode characters using the {@linkplain
     * java.nio.charset.Charset#defaultCharset() default charset} for this
     * instance of the Java virtual machine.
     *
     * <p>
     *  使用指定的文件创建新的PrintWriter,而不自动进行行刷新。
     * 这个方便的构造函数创建了必要的中间{@link java.io.OutputStreamWriter OutputStreamWriter},它将使用{@linkplain java.nio.charset.Charset#defaultCharset()default charset}
     * 为Java虚拟机实例编码字符。
     *  使用指定的文件创建新的PrintWriter,而不自动进行行刷新。
     * 
     * 
     * @param  file
     *         The file to use as the destination of this writer.  If the file
     *         exists then it will be truncated to zero size; otherwise, a new
     *         file will be created.  The output will be written to the file
     *         and is buffered.
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(file.getPath())}
     *          denies write access to the file
     *
     * @since  1.5
     */
    public PrintWriter(File file) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))),
             false);
    }

    /**
     * Creates a new PrintWriter, without automatic line flushing, with the
     * specified file and charset.  This convenience constructor creates the
     * necessary intermediate {@link java.io.OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the provided
     * charset.
     *
     * <p>
     * 使用指定的文件和字符集创建新的PrintWriter,而不进行自动换行。
     * 这个方便的构造函数创建必要的中间{@link java.io.OutputStreamWriter OutputStreamWriter},它将使用提供的字符集对字符进行编码。
     * 
     * 
     * @param  file
     *         The file to use as the destination of this writer.  If the file
     *         exists then it will be truncated to zero size; otherwise, a new
     *         file will be created.  The output will be written to the file
     *         and is buffered.
     *
     * @param  csn
     *         The name of a supported {@linkplain java.nio.charset.Charset
     *         charset}
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(file.getPath())}
     *          denies write access to the file
     *
     * @throws  UnsupportedEncodingException
     *          If the named charset is not supported
     *
     * @since  1.5
     */
    public PrintWriter(File file, String csn)
        throws FileNotFoundException, UnsupportedEncodingException
    {
        this(toCharset(csn), file);
    }

    /** Checks to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed");
    }

    /**
     * Flushes the stream.
     * <p>
     *  刷新流。
     * 
     * 
     * @see #checkError()
     */
    public void flush() {
        try {
            synchronized (lock) {
                ensureOpen();
                out.flush();
            }
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Closes the stream and releases any system resources associated
     * with it. Closing a previously closed stream has no effect.
     *
     * <p>
     *  关闭流并释放与其关联的任何系统资源。关闭先前关闭的流没有任何效果。
     * 
     * 
     * @see #checkError()
     */
    public void close() {
        try {
            synchronized (lock) {
                if (out == null)
                    return;
                out.close();
                out = null;
            }
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Flushes the stream if it's not closed and checks its error state.
     *
     * <p>
     *  如果流未关闭,则刷新流,并检查其错误状态。
     * 
     * 
     * @return <code>true</code> if the print stream has encountered an error,
     *          either on the underlying output stream or during a format
     *          conversion.
     */
    public boolean checkError() {
        if (out != null) {
            flush();
        }
        if (out instanceof java.io.PrintWriter) {
            PrintWriter pw = (PrintWriter) out;
            return pw.checkError();
        } else if (psOut != null) {
            return psOut.checkError();
        }
        return trouble;
    }

    /**
     * Indicates that an error has occurred.
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>true</tt> until {@link
     * #clearError()} is invoked.
     * <p>
     *  表示发生错误。
     * 
     *  <p>此方法将促使{@link #checkError()}的后续调用返回<tt> true </tt>,直到调用{@link #clearError()}。
     * 
     */
    protected void setError() {
        trouble = true;
    }

    /**
     * Clears the error state of this stream.
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>false</tt> until another write
     * operation fails and invokes {@link #setError()}.
     *
     * <p>
     *  清除此流的错误状态。
     * 
     *  <p>此方法将导致{@link #checkError()}的后续调用返回<tt> false </tt>,直到另一个写入操作失败并调用{@link #setError()}。
     * 
     * 
     * @since 1.6
     */
    protected void clearError() {
        trouble = false;
    }

    /*
     * Exception-catching, synchronized output operations,
     * which also implement the write() methods of Writer
     * <p>
     *  异常捕获,同步输出操作,它还实现Writer的write()方法
     * 
     */

    /**
     * Writes a single character.
     * <p>
     *  写入单个字符。
     * 
     * 
     * @param c int specifying a character to be written.
     */
    public void write(int c) {
        try {
            synchronized (lock) {
                ensureOpen();
                out.write(c);
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Writes A Portion of an array of characters.
     * <p>
     *  写入字符数组的一部分。
     * 
     * 
     * @param buf Array of characters
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    public void write(char buf[], int off, int len) {
        try {
            synchronized (lock) {
                ensureOpen();
                out.write(buf, off, len);
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Writes an array of characters.  This method cannot be inherited from the
     * Writer class because it must suppress I/O exceptions.
     * <p>
     *  写入字符数组。此方法不能从Writer类继承,因为它必须抑制I / O异常。
     * 
     * 
     * @param buf Array of characters to be written
     */
    public void write(char buf[]) {
        write(buf, 0, buf.length);
    }

    /**
     * Writes a portion of a string.
     * <p>
     *  写入字符串的一部分。
     * 
     * 
     * @param s A String
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    public void write(String s, int off, int len) {
        try {
            synchronized (lock) {
                ensureOpen();
                out.write(s, off, len);
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Writes a string.  This method cannot be inherited from the Writer class
     * because it must suppress I/O exceptions.
     * <p>
     *  写入字符串。此方法不能从Writer类继承,因为它必须抑制I / O异常。
     * 
     * 
     * @param s String to be written
     */
    public void write(String s) {
        write(s, 0, s.length());
    }

    private void newLine() {
        try {
            synchronized (lock) {
                ensureOpen();
                out.write(lineSeparator);
                if (autoFlush)
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /* Methods that do not terminate lines */

    /**
     * Prints a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * <p>
     * 打印布尔值。
     * 由<code> {@ link java.lang.String#valueOf(boolean)} </code>生成的字符串将根据平台的默认字符编码转换为字节,这些字节的写入方式与<code > {@ link #write(int)}
     *  </code>方法。
     * 打印布尔值。
     * 
     * 
     * @param      b   The <code>boolean</code> to be printed
     */
    public void print(boolean b) {
        write(b ? "true" : "false");
    }

    /**
     * Prints a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * <p>
     *  打印字符。根据平台的默认字符编码将字符转换为一个或多个字节,这些字节的写入方式与<code> {@ link #write(int)} </code>方法完全相同。
     * 
     * 
     * @param      c   The <code>char</code> to be printed
     */
    public void print(char c) {
        write(c);
    }

    /**
     * Prints an integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code> is translated into bytes according
     * to the platform's default character encoding, and these bytes are
     * written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * <p>
     *  打印整数。
     * 由<code> {@ link java.lang.String#valueOf(int)} </code>生成的字符串根据平台的默认字符编码转换为字节,这些字节的写入方式与<code > {@ link #write(int)}
     *  </code>方法。
     *  打印整数。
     * 
     * 
     * @param      i   The <code>int</code> to be printed
     * @see        java.lang.Integer#toString(int)
     */
    public void print(int i) {
        write(String.valueOf(i));
    }

    /**
     * Prints a long integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * <p>
     *  打印长整数。
     * 由<code> {@ link java.lang.String#valueOf(long)} </code>生成的字符串根据平台的默认字符编码转换为字节,这些字节的写入方式与<code > {@ link #write(int)}
     *  </code>方法。
     *  打印长整数。
     * 
     * 
     * @param      l   The <code>long</code> to be printed
     * @see        java.lang.Long#toString(long)
     */
    public void print(long l) {
        write(String.valueOf(l));
    }

    /**
     * Prints a floating-point number.  The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * <p>
     *  打印浮点数。
     * 由<code> {@ link java.lang.String#valueOf(float)} </code>生成的字符串根据平台的默认字符编码转换为字节,这些字节的写入方式与<code > {@ link #write(int)}
     *  </code>方法。
     *  打印浮点数。
     * 
     * 
     * @param      f   The <code>float</code> to be printed
     * @see        java.lang.Float#toString(float)
     */
    public void print(float f) {
        write(String.valueOf(f));
    }

    /**
     * Prints a double-precision floating-point number.  The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * <p>
     * 打印双精度浮点数。
     * 由<code> {@ link java.lang.String#valueOf(double)} </code>生成的字符串将根据平台的默认字符编码转换为字节,这些字节的写入方式与<code > {@ link #write(int)}
     *  </code>方法。
     * 打印双精度浮点数。
     * 
     * 
     * @param      d   The <code>double</code> to be printed
     * @see        java.lang.Double#toString(double)
     */
    public void print(double d) {
        write(String.valueOf(d));
    }

    /**
     * Prints an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * <p>
     *  打印字符数组。根据平台的默认字符编码将字符转换为字节,这些字节的写入方式与<code> {@ link #write(int)} </code>方法完全相同。
     * 
     * 
     * @param      s   The array of chars to be printed
     *
     * @throws  NullPointerException  If <code>s</code> is <code>null</code>
     */
    public void print(char s[]) {
        write(s);
    }

    /**
     * Prints a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * <p>
     *  打印字符串。如果参数是<code> null </code>,则打印字符串<code>"null"</code>。
     * 否则,字符串的字符将根据平台的默认字符编码转换为字节,这些字节的写入方式与<code> {@ link #write(int)} </code>方法完全相同。
     * 
     * 
     * @param      s   The <code>String</code> to be printed
     */
    public void print(String s) {
        if (s == null) {
            s = "null";
        }
        write(s);
    }

    /**
     * Prints an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * <p>
     *  打印对象。
     * 由<code> {@ link java.lang.String#valueOf(Object)} </code>方法生成的字符串将根据平台的默认字符编码转换为字节,这些字节的写入方式与<code> {@ link #write(int)}
     *  </code>方法。
     *  打印对象。
     * 
     * 
     * @param      obj   The <code>Object</code> to be printed
     * @see        java.lang.Object#toString()
     */
    public void print(Object obj) {
        write(String.valueOf(obj));
    }

    /* Methods that do terminate lines */

    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * <p>
     *  通过写入行分隔符字符串来终止当前行。行分隔符字符串由系统属性<code> line.separator </code>定义,不一定是单个换行符(<code>'\ n'</code>)。
     * 
     */
    public void println() {
        newLine();
    }

    /**
     * Prints a boolean value and then terminates the line.  This method behaves
     * as though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     * 打印布尔值,然后终止行。此方法的行为就像调用<code> {@ link #print(boolean)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>boolean</code> value to be printed
     */
    public void println(boolean x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints a character and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then <code>{@link
     * #println()}</code>.
     *
     * <p>
     *  打印一个字符,然后终止该行。此方法的行为就像调用<code> {@ link #print(char)} </code>,然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>char</code> value to be printed
     */
    public void println(char x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints an integer and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then <code>{@link
     * #println()}</code>.
     *
     * <p>
     *  打印整数,然后终止行。此方法的行为就像调用<code> {@ link #print(int)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>int</code> value to be printed
     */
    public void println(int x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints a long integer and then terminates the line.  This method behaves
     * as though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     *  打印一个长整数,然后终止该行。此方法的行为就像调用<code> {@ link #print(long)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>long</code> value to be printed
     */
    public void println(long x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints a floating-point number and then terminates the line.  This method
     * behaves as though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     *  打印浮点数,然后终止行。此方法的行为就像调用<code> {@ link #print(float)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>float</code> value to be printed
     */
    public void println(float x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints a double-precision floating-point number and then terminates the
     * line.  This method behaves as though it invokes <code>{@link
     * #print(double)}</code> and then <code>{@link #println()}</code>.
     *
     * <p>
     *  打印双精度浮点数,然后终止该行。
     * 此方法的行为就像调用<code> {@ link #print(double)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>double</code> value to be printed
     */
    public void println(double x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints an array of characters and then terminates the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     *  打印一个字符数组,然后终止该行。
     * 此方法的行为就像调用<code> {@ link #print(char [])} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the array of <code>char</code> values to be printed
     */
    public void println(char x[]) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints a String and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     *  打印一个字符串,然后终止该行。
     * 此方法的行为就像调用<code> {@ link #print(String)} </code>然后<code> {@ link #println()} </code>。
     * 
     * 
     * @param x the <code>String</code> value to be printed
     */
    public void println(String x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * Prints an Object and then terminates the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * <p>
     * 打印对象,然后终止线。
     * 此方法首先调用String.valueOf(x)获取打印对象的字符串值,然后调用<code> {@ link #print(String)} </code>,然后<code> {@ link# println()}
     *  </code>。
     * 打印对象,然后终止线。
     * 
     * 
     * @param x  The <code>Object</code> to be printed.
     */
    public void println(Object x) {
        String s = String.valueOf(x);
        synchronized (lock) {
            print(s);
            println();
        }
    }

    /**
     * A convenience method to write a formatted string to this writer using
     * the specified format string and arguments.  If automatic flushing is
     * enabled, calls to this method will flush the output buffer.
     *
     * <p> An invocation of this method of the form <tt>out.printf(format,
     * args)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.format(format, args) </pre>
     *
     * <p>
     *  一种方便的方法,使用指定的格式字符串和参数将格式化的字符串写入此写入程序。如果启用了自动刷新,调用此方法将刷新输出缓冲区。
     * 
     *  <p>以<tt> out.printf(format,args)</tt>的形式调用此方法的行为与调用的方式完全相同
     * 
     * <pre>
     *  out.format(format,args)</pre>
     * 
     * 
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public PrintWriter printf(String format, Object ... args) {
        return format(format, args);
    }

    /**
     * A convenience method to write a formatted string to this writer using
     * the specified format string and arguments.  If automatic flushing is
     * enabled, calls to this method will flush the output buffer.
     *
     * <p> An invocation of this method of the form <tt>out.printf(l, format,
     * args)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.format(l, format, args) </pre>
     *
     * <p>
     *  一种方便的方法,使用指定的格式字符串和参数将格式化的字符串写入此写入程序。如果启用了自动刷新,调用此方法将刷新输出缓冲区。
     * 
     *  <p>调用此方法的形式<tt> out.printf(l,format,args)</tt>的行为与调用的方式完全相同
     * 
     * <pre>
     *  out.format(l,format,args)</pre>
     * 
     * 
     * @param  l
     *         The {@linkplain java.util.Locale locale} to apply during
     *         formatting.  If <tt>l</tt> is <tt>null</tt> then no localization
     *         is applied.
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public PrintWriter printf(Locale l, String format, Object ... args) {
        return format(l, format, args);
    }

    /**
     * Writes a formatted string to this writer using the specified format
     * string and arguments.  If automatic flushing is enabled, calls to this
     * method will flush the output buffer.
     *
     * <p> The locale always used is the one returned by {@link
     * java.util.Locale#getDefault() Locale.getDefault()}, regardless of any
     * previous invocations of other formatting methods on this object.
     *
     * <p>
     *  使用指定的格式字符串和参数将格式化的字符串写入此编写器。如果启用了自动刷新,调用此方法将刷新输出缓冲区。
     * 
     *  <p>始终使用的语言环境是{@link java.util.Locale#getDefault()Locale.getDefault()}返回的语言环境,而不管此对象上是否有其他格式化方法的任何调用。
     * 
     * 
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          Formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public PrintWriter format(String format, Object ... args) {
        try {
            synchronized (lock) {
                ensureOpen();
                if ((formatter == null)
                    || (formatter.locale() != Locale.getDefault()))
                    formatter = new Formatter(this);
                formatter.format(Locale.getDefault(), format, args);
                if (autoFlush)
                    out.flush();
            }
        } catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        } catch (IOException x) {
            trouble = true;
        }
        return this;
    }

    /**
     * Writes a formatted string to this writer using the specified format
     * string and arguments.  If automatic flushing is enabled, calls to this
     * method will flush the output buffer.
     *
     * <p>
     * 使用指定的格式字符串和参数将格式化的字符串写入此编写器。如果启用了自动刷新,调用此方法将刷新输出缓冲区。
     * 
     * 
     * @param  l
     *         The {@linkplain java.util.Locale locale} to apply during
     *         formatting.  If <tt>l</tt> is <tt>null</tt> then no localization
     *         is applied.
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public PrintWriter format(Locale l, String format, Object ... args) {
        try {
            synchronized (lock) {
                ensureOpen();
                if ((formatter == null) || (formatter.locale() != l))
                    formatter = new Formatter(this, l);
                formatter.format(l, format, args);
                if (autoFlush)
                    out.flush();
            }
        } catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        } catch (IOException x) {
            trouble = true;
        }
        return this;
    }

    /**
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended. For instance, invoking the <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     *
     * <p>
     *  将指定的字符序列附加到此writer。
     * 
     *  <p>调用此方法的形式<tt> out.append(csq)</tt>的行为与调用的方式完全相同
     * 
     * <pre>
     *  out.write(csq.toString())</pre>
     * 
     *  <p>根据<tt> toString </tt>对字符序列<tt> csq </tt>的规定,整个序列可能不会附加。
     * 例如,调用字符缓冲区的<tt> toString </tt>方法将返回一个子序列,其内容取决于缓冲区的位置和限制。
     * 
     * 
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public PrintWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.subSequence(start, end).toString()) </pre>
     *
     * <p>
     *  将指定字符序列的子序列附加到此writer。
     * 
     *  <p>当<tt> csq </tt>不是<tt> null </tt>时,对<tt> out.append(csq,start,end)</tt>形式的此方法的调用完全与调用相同的方式
     * 
     * <pre>
     *  out.write(csq.subSequence(start,end).toString())</pre>
     * 
     * 
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public PrintWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    /**
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     *
     * <p>
     *  将指定的字符附加到此writer。
     * 
     *  <p>调用此方法的形式<tt> out.append(c)</tt>的行为与调用的方式完全相同
     * 
     * 
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    public PrintWriter append(char c) {
        write(c);
        return this;
    }
}
