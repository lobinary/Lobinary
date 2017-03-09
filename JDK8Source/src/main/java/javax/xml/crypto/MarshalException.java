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
 * $Id: MarshalException.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：MarshalException.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;

/**
 * Indicates an exceptional condition that occurred during the XML
 * marshalling or unmarshalling process.
 *
 * <p>A <code>MarshalException</code> can contain a cause: another
 * throwable that caused this <code>MarshalException</code> to get thrown.
 *
 * <p>
 *  表示在XML编组或解组过程中发生的异常情况。
 * 
 *  <p>一个<code> MarshalException </code>可能包含一个原因：另一个throwable导致此<code> MarshalException </code>被抛出。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignature#sign(XMLSignContext)
 * @see XMLSignatureFactory#unmarshalXMLSignature(XMLValidateContext)
 */
public class MarshalException extends Exception {

    private static final long serialVersionUID = -863185580332643547L;

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
     * Constructs a new <code>MarshalException</code> with
     * <code>null</code> as its detail message.
     * <p>
     *  使用<code> null </code>作为其详细消息构造新的<code> MarshalException </code>。
     * 
     */
    public MarshalException() {
        super();
    }

    /**
     * Constructs a new <code>MarshalException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的<code> MarshalException </code>。
     * 
     * 
     * @param message the detail message
     */
    public MarshalException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>MarshalException</code> with the
     * specified detail message and cause.
     * <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的<code> MarshalException </code>。
     *  <p>请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message the detail message
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public MarshalException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new <code>MarshalException</code> with the specified cause
     * and a detail message of <code>(cause==null ? null : cause.toString())
     * </code> (which typically contains the class and detail message of
     * <code>cause</code>).
     *
     * <p>
     *  使用指定的原因和<code>(cause == null?null：cause.toString())</code>的详细消息(通常包含类和详细消息)构造新的<code> MarshalExcepti
     * on </code> of <code> cause </code>)。
     * 
     * 
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public MarshalException(Throwable cause) {
        super(cause==null ? null : cause.toString());
        this.cause = cause;
    }

    /**
     * Returns the cause of this <code>MarshalException</code> or
     * <code>null</code> if the cause is nonexistent or unknown.  (The
     * cause is the throwable that caused this
     * <code>MarshalException</code> to get thrown.)
     *
     * <p>
     *  如果原因不存在或未知,则返回此<code> MarshalException </code>或<code> null </code>的原因。
     *  (原因是throwable导致这个<code> MarshalException </code>被抛出。)。
     * 
     * 
     * @return the cause of this <code>MarshalException</code> or
     *         <code>null</code> if the cause is nonexistent or unknown.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Prints this <code>MarshalException</code>, its backtrace and
     * the cause's backtrace to the standard error stream.
     * <p>
     * 将此<code> MarshalException </code>,其回溯和原因的回溯打印到标准错误流。
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>MarshalException</code>, its backtrace and
     * the cause's backtrace to the specified print stream.
     *
     * <p>
     *  打印此<code> MarshalException </code>,它的backtrace和原因的backtrace到指定的打印流。
     * 
     * 
     * @param s <code>PrintStream</code> to use for output
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>MarshalException</code>, its backtrace and
     * the cause's backtrace to the specified print writer.
     *
     * <p>
     *  打印此<code> MarshalException </code>,它的backtrace和原因的backtrace到指定的打印作者。
     * 
     * @param s <code>PrintWriter</code> to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }
}
