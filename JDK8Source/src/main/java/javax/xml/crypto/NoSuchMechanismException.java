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
 * $Id: NoSuchMechanismException.java,v 1.4 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：NoSuchMechanismException.java,v 1.4 2005/05/10 15:47:42 mullan Exp $
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
 * This exception is thrown when a particular XML mechanism is requested but
 * is not available in the environment.
 *
 * <p>A <code>NoSuchMechanismException</code> can contain a cause: another
 * throwable that caused this <code>NoSuchMechanismException</code> to get
 * thrown.
 *
 * <p>
 *  当请求特定的XML机制但在环境中不可用时,抛出此异常。
 * 
 *  <p>一个<code> NoSuchMechanismException </code>可以包含一个原因：另一个throwable引起这个<code> NoSuchMechanismException
 *  </code>被抛出。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#getInstance XMLSignatureFactory.getInstance
 * @see KeyInfoFactory#getInstance KeyInfoFactory.getInstance
 */
public class NoSuchMechanismException extends RuntimeException {

    private static final long serialVersionUID = 4189669069570660166L;

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
     * Constructs a new <code>NoSuchMechanismException</code> with
     * <code>null</code> as its detail message.
     * <p>
     *  使用<code> null </code>作为其详细消息构造新的<code> NoSuchMechanismException </code>。
     * 
     */
    public NoSuchMechanismException() {
        super();
    }

    /**
     * Constructs a new <code>NoSuchMechanismException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的<code> NoSuchMechanismException </code>。
     * 
     * 
     * @param message the detail message
     */
    public NoSuchMechanismException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>NoSuchMechanismException</code> with the
     * specified detail message and cause.
     * <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的<code> NoSuchMechanismException </code>。
     *  <p>请注意,与<code> cause </code>关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message the detail message
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public NoSuchMechanismException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new <code>NoSuchMechanismException</code> with the
     * specified cause and a detail message of
     * <code>(cause==null ? null : cause.toString())</code> (which typically
     * contains the class and detail message of <code>cause</code>).
     *
     * <p>
     *  使用指定的原因和<code>(cause == null?null：cause.toString())</code>的详细消息(通常包含类和详细消息)构造一个新的<code> NoSuchMechan
     * ismException </code> of <code> cause </code>)。
     * 
     * 
     * @param cause the cause (A <tt>null</tt> value is permitted, and
     *        indicates that the cause is nonexistent or unknown.)
     */
    public NoSuchMechanismException(Throwable cause) {
        super(cause==null ? null : cause.toString());
        this.cause = cause;
    }

    /**
     * Returns the cause of this <code>NoSuchMechanismException</code> or
     * <code>null</code> if the cause is nonexistent or unknown.  (The
     * cause is the throwable that caused this
     * <code>NoSuchMechanismException</code> to get thrown.)
     *
     * <p>
     * 如果原因不存在或未知,则返回此<code> NoSuchMechanismException </code>或<code> null </code>的原因。
     *  (原因是throwable导致这个<code> NoSuchMechanismException </code>被抛出。)。
     * 
     * 
     * @return the cause of this <code>NoSuchMechanismException</code> or
     *         <code>null</code> if the cause is nonexistent or unknown.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Prints this <code>NoSuchMechanismException</code>, its backtrace and
     * the cause's backtrace to the standard error stream.
     * <p>
     *  打印此<code> NoSuchMechanismException </code>,它的backtrace和原因的回溯到标准错误流。
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>NoSuchMechanismException</code>, its backtrace and
     * the cause's backtrace to the specified print stream.
     *
     * <p>
     *  打印此<code> NoSuchMechanismException </code>,它的backtrace和原因的backtrace到指定的打印流。
     * 
     * 
     * @param s <code>PrintStream</code> to use for output
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }

    /**
     * Prints this <code>NoSuchMechanismException</code>, its backtrace and
     * the cause's backtrace to the specified print writer.
     *
     * <p>
     *  打印此<code> NoSuchMechanismException </code>,它的backtrace和原因的backtrace到指定的打印作者。
     * 
     * @param s <code>PrintWriter</code> to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        //XXX print backtrace of cause
    }
}
