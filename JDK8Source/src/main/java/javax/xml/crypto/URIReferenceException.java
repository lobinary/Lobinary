/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: URIReferenceException.java,v 1.4 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：URIReferenceException.java,v 1.4 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

/**
 * Indicates an exceptional condition thrown while dereferencing a
 * {@link URIReference}.
 *
 * <p>A <code>URIReferenceException</code> can contain a cause: another
 * throwable that caused this <code>URIReferenceException</code> to get thrown.
 *
 * <p>
 *  表示在解除引用{@link URIReference}时引发的异常条件。
 * 
 *  <p>一个<code> URIReferenceException </code>可能包含一个原因：另一个throwable引起这个<code> URIReferenceException </code>
 * 被抛出。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see URIDereferencer#dereference(URIReference, XMLCryptoContext)
 * @see RetrievalMethod#dereference(XMLCryptoContext)
 */
public class URIReferenceException extends Exception {

    private static final long serialVersionUID = 7173469703932561419L;

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

    private URIReference uriReference;

    /**
     * Constructs a new <code>URIReferenceException</code> with
     * <code>null</code> as its detail message.
     * <p>
     *  使用<code> null </code>作为其详细消息构造新的<code> URIReferenceException </code>。
     * 
     */
    public URIReferenceException() {
        super();
    }

    /**
     * Constructs a new <code>URIReferenceException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的<code> URIReferenceException </code>。
     * 
     * 
     * @param message the detail message
     */
    public URIReferenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>URIReferenceException</code> with the
     * specified detail message and cause.
     * <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的<code> URIReferenceException </code>。
     *  <p>请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message the detail message
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public URIReferenceException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new <code>URIReferenceException</code> with the
     * specified detail message, cause and <code>URIReference</code>.
     * <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  使用指定的详细消息,cause和<code> URIReference </code>构造新的<code> URIReferenceException </code>。
     *  <p>请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message the detail message
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     * @param uriReference the <code>URIReference</code> that was being
     *    dereferenced when the error was encountered
     * @throws NullPointerException if <code>uriReference</code> is
     *    <code>null</code>
     */
    public URIReferenceException(String message, Throwable cause,
        URIReference uriReference) {
        this(message, cause);
        if (uriReference == null) {
            throw new NullPointerException("uriReference cannot be null");
        }
        this.uriReference = uriReference;
    }

    /**
     * Constructs a new <code>URIReferenceException</code> with the specified
     * cause and a detail message of <code>(cause==null ? null :
     * cause.toString())</code> (which typically contains the class and detail
     * message of <code>cause</code>).
     *
     * <p>
     * 使用指定的原因和<code>(cause == null?null：cause.toString())</code>的详细消息构造新的<code> URIReferenceException </code>
     * (通常包含类和详细消息of <code> cause </code>)。
     * 
     * 
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public URIReferenceException(Throwable cause) {
        super(cause==null ? null : cause.toString());
        this.cause = cause;
    }

    /**
     * Returns the <code>URIReference</code> that was being dereferenced
     * when the exception was thrown.
     *
     * <p>
     *  返回在抛出异常时被解除引用的<code> URIReference </code>。
     * 
     * 
     * @return the <code>URIReference</code> that was being dereferenced
     * when the exception was thrown, or <code>null</code> if not specified
     */
    public URIReference getURIReference() {
        return uriReference;
    }

    /**
     * Returns the cause of this <code>URIReferenceException</code> or
     * <code>null</code> if the cause is nonexistent or unknown.  (The
     * cause is the throwable that caused this
     * <code>URIReferenceException</code> to get thrown.)
     *
     * <p>
     *  如果原因不存在或未知,则返回此<<code> URIReferenceException </code>或<code> null </code>的原因。
     *  (原因是throwable导致这个<code> URIReferenceException </code>被抛出。)。
     * 
     * 
     * @return the cause of this <code>URIReferenceException</code> or
     *    <code>null</code> if the cause is nonexistent or unknown.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Prints this <code>URIReferenceException</code>, its backtrace and
     * the cause's backtrace to the standard error stream.
     * <p>
     *  将此<code> URIReferenceException </code>,它的backtrace和原因的backtrace打印到标准错误流。
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>URIReferenceException</code>, its backtrace and
     * the cause's backtrace to the specified print stream.
     *
     * <p>
     *  打印此<code> URIReferenceException </code>,它的backtrace和原因的backtrace到指定的打印流。
     * 
     * 
     * @param s <code>PrintStream</code> to use for output
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>URIReferenceException</code>, its backtrace and
     * the cause's backtrace to the specified print writer.
     *
     * <p>
     *  将此<code> URIReferenceException </code>,它的backtrace和原因的backtrace打印到指定的打印作者。
     * 
     * @param s <code>PrintWriter</code> to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }
}
