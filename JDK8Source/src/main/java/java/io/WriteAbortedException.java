/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that one of the ObjectStreamExceptions was thrown during a
 * write operation.  Thrown during a read operation when one of the
 * ObjectStreamExceptions was thrown during a write operation.  The
 * exception that terminated the write can be found in the detail
 * field. The stream is reset to it's initial state and all references
 * to objects already deserialized are discarded.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "exception causing
 * the abort" that is provided at construction time and
 * accessed via the public {@link #detail} field is now known as the
 * <i>cause</i>, and may be accessed via the {@link Throwable#getCause()}
 * method, as well as the aforementioned "legacy field."
 *
 * <p>
 *  表示在写入操作期间抛出了其中一个ObjectStreamExceptions。在读操作期间抛出的一个ObjectStreamExceptions在写操作期间抛出。
 * 终止写入的异常可以在详细信息字段中找到。流被重置为其初始状态,并且对已经反序列化的对象的所有引用被丢弃。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 在构建时提供并通过公共{@link #detail}字段访问的"导致中止的异常"现在称为<i>原因</i>,并且可以通过{@link Throwable# getCause()}方法,以及上述"遗留字段
 * "。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public class WriteAbortedException extends ObjectStreamException {
    private static final long serialVersionUID = -3326426625597282442L;

    /**
     * Exception that was caught while writing the ObjectStream.
     *
     * <p>This field predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  在写ObjectStream时捕获的异常。
     * 
     *  <p>此字段早于通用的例外链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @serial
     */
    public Exception detail;

    /**
     * Constructs a WriteAbortedException with a string describing
     * the exception and the exception causing the abort.
     * <p>
     *  使用描述异常的字符串和导致中止的异常构造WriteAbortedException。
     * 
     * 
     * @param s   String describing the exception.
     * @param ex  Exception causing the abort.
     */
    public WriteAbortedException(String s, Exception ex) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
        detail = ex;
    }

    /**
     * Produce the message and include the message from the nested
     * exception, if there is one.
     * <p>
     *  生成消息并包含来自嵌套异常的消息(如果存在)。
     * 
     */
    public String getMessage() {
        if (detail == null)
            return super.getMessage();
        else
            return super.getMessage() + "; " + detail.toString();
    }

    /**
     * Returns the exception that terminated the operation (the <i>cause</i>).
     *
     * <p>
     *  返回终止操作(<i> cause </i>)的异常。
     * 
     * @return  the exception that terminated the operation (the <i>cause</i>),
     *          which may be null.
     * @since   1.4
     */
    public Throwable getCause() {
        return detail;
    }
}
