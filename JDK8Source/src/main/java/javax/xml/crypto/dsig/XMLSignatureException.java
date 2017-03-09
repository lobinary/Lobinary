/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: XMLSignatureException.java,v 1.5 2005/05/10 16:03:48 mullan Exp $
 * <p>
 *  $ Id：XMLSignatureException.java,v 1.5 2005/05/10 16:03:48 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Indicates an exceptional condition that occurred during the XML
 * signature generation or validation process.
 *
 * <p>An <code>XMLSignatureException</code> can contain a cause: another
 * throwable that caused this <code>XMLSignatureException</code> to get thrown.
 *
 * <p>
 *  表示在XML签名生成或验证过程中发生的异常情况。
 * 
 *  <p>一个<code> XMLSignatureException </code>可能包含一个原因：另一个throwable导致此<code> XMLSignatureException </code>
 * 被抛出。
 * 
 * 
 * @since 1.6
 */
public class XMLSignatureException extends Exception {

    private static final long serialVersionUID = -3438102491013869995L;

    /**
     * The throwable that caused this exception to get thrown, or null if this
     * exception was not caused by another throwable or if the causative
     * throwable is unknown.
     *
     * <p>
     *  throwable引起此异常被抛出,或null如果此异常不是由另一个throwable引起的,或者如果原因throwable是未知的。
     * 
     * 
     * @serial
     */
    private Throwable cause;

    /**
     * Constructs a new <code>XMLSignatureException</code> with
     * <code>null</code> as its detail message.
     * <p>
     *  使用<code> null </code>作为其详细消息构造新的<code> XMLSignatureException </code>。
     * 
     */
    public XMLSignatureException() {
        super();
    }

    /**
     * Constructs a new <code>XMLSignatureException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的<code> XMLSignatureException </code>。
     * 
     * 
     * @param message the detail message
     */
    public XMLSignatureException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>XMLSignatureException</code> with the
     * specified detail message and cause.
     * <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的<code> XMLSignatureException </code>。
     *  <p>请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message the detail message
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public XMLSignatureException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new <code>XMLSignatureException</code> with the specified
     * cause and a detail message of
     * <code>(cause==null ? null : cause.toString())</code>
     * (which typically contains the class and detail message of
     * <code>cause</code>).
     *
     * <p>
     *  构造具有指定原因和<code>(cause == null?null：cause.toString())</code>的详细消息的新的<code> XMLSignatureException </code>
     * (通常包含类和详细消息of <code> cause </code>)。
     * 
     * 
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public XMLSignatureException(Throwable cause) {
        super(cause==null ? null : cause.toString());
        this.cause = cause;
    }

    /**
     * Returns the cause of this <code>XMLSignatureException</code> or
     * <code>null</code> if the cause is nonexistent or unknown.  (The
     * cause is the throwable that caused this
     * <code>XMLSignatureException</code> to get thrown.)
     *
     * <p>
     * 如果原因不存在或未知,则返回此<code> XMLSignatureException </code>或<code> null </code>的原因。
     *  (原因是throwable导致这个<code> XMLSignatureException </code>被抛出。)。
     * 
     * 
     * @return the cause of this <code>XMLSignatureException</code> or
     *         <code>null</code> if the cause is nonexistent or unknown.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Prints this <code>XMLSignatureException</code>, its backtrace and
     * the cause's backtrace to the standard error stream.
     * <p>
     *  打印此<code> XMLSignatureException </code>,它的backtrace和原因的backtrace到标准错误流。
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    /**
     * Prints this <code>XMLSignatureException</code>, its backtrace and
     * the cause's backtrace to the specified print stream.
     *
     * <p>
     *  打印此<code> XMLSignatureException </code>,它的backtrace和原因的backtrace到指定的打印流。
     * 
     * 
     * @param s <code>PrintStream</code> to use for output
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (cause != null) {
            cause.printStackTrace(s);
        }
    }

    /**
     * Prints this <code>XMLSignatureException</code>, its backtrace and
     * the cause's backtrace to the specified print writer.
     *
     * <p>
     *  打印此<code> XMLSignatureException </code>,它的backtrace和原因的backtrace到指定的打印作者。
     * 
     * @param s <code>PrintWriter</code> to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (cause != null) {
            cause.printStackTrace(s);
        }
    }
}
