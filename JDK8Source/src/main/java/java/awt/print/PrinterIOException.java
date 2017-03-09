/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.print;
import java.io.IOException;

/**
 * The <code>PrinterIOException</code> class is a subclass of
 * {@link PrinterException} and is used to indicate that an IO error
 * of some sort has occurred while printing.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The
 * "<code>IOException</code> that terminated the print job"
 * that is provided at construction time and accessed via the
 * {@link #getIOException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method,
 * as well as the aforementioned "legacy method."
 * <p>
 *  <code> PrinterIOException </code>类是{@link PrinterException}的子类,用于表示在打印时发生了某种IO错误。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 在构建时提供并通过{@link #getIOException()}方法访问的"终止打印作业的<code> IOException </code>现在称为<i>原因</i>,可以通过{@link Throwable#getCause()}
 * 方法以及上述"传统方法"来访问。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 */
public class PrinterIOException extends PrinterException {
    static final long serialVersionUID = 5850870712125932846L;

    /**
     * The IO error that terminated the print job.
     * <p>
     *  终止打印作业的IO错误。
     * 
     * 
     * @serial
     */
    private IOException mException;

    /**
     * Constructs a new <code>PrinterIOException</code>
     * with the string representation of the specified
     * {@link IOException}.
     * <p>
     *  使用指定的{@link IOException}的字符串表示构造新的<code> PrinterIOException </code>。
     * 
     * 
     * @param exception the specified <code>IOException</code>
     */
    public PrinterIOException(IOException exception) {
        initCause(null);  // Disallow subsequent initCause
        mException = exception;
    }

    /**
     * Returns the <code>IOException</code> that terminated
     * the print job.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  返回终止打印作业的<code> IOException </code>。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the <code>IOException</code> that terminated
     * the print job.
     * @see IOException
     */
    public IOException getIOException() {
        return mException;
    }

    /**
     * Returns the the cause of this exception (the <code>IOException</code>
     * that terminated the print job).
     *
     * <p>
     * 
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return mException;
    }
}
