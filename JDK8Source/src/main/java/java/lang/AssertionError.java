/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown to indicate that an assertion has failed.
 *
 * <p>The seven one-argument public constructors provided by this
 * class ensure that the assertion error returned by the invocation:
 * <pre>
 *     new AssertionError(<i>expression</i>)
 * </pre>
 * has as its detail message the <i>string conversion</i> of
 * <i>expression</i> (as defined in section 15.18.1.1 of
 * <cite>The Java&trade; Language Specification</cite>),
 * regardless of the type of <i>expression</i>.
 *
 * <p>
 *  抛出以指示断言失败。
 * 
 *  <p>此类提供的七个单参数公共构造函数确保调用返回的断言错误：
 * <pre>
 *  新的AssertionError(<i>表达式</i>)
 * </pre>
 *  具有作为其详细消息的<i>表达式</i>(如<cite> Java&trade; Language Specification </cite>的第15.18.1.1节中定义)的<i>字符串转换</i>
 *  <i>表达式</i>的类型。
 * 
 * 
 * @since   1.4
 */
public class AssertionError extends Error {
    private static final long serialVersionUID = -5013299493970297370L;

    /**
     * Constructs an AssertionError with no detail message.
     * <p>
     *  构造一个没有详细消息的AssertionError。
     * 
     */
    public AssertionError() {
    }

    /**
     * This internal constructor does no processing on its string argument,
     * even if it is a null reference.  The public constructors will
     * never call this constructor with a null argument.
     * <p>
     *  这个内部构造函数不处理其字符串参数,即使它是一个空引用。公共构造函数不会使用null参数调用此构造函数。
     * 
     */
    private AssertionError(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified object, which is converted to a string as
     * defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *<p>
     * If the specified object is an instance of {@code Throwable}, it
     * becomes the <i>cause</i> of the newly constructed assertion error.
     *
     * <p>
     *  构造一个AssertionError及其从指定对象派生的详细消息,该消息将转换为<cite> Java&trade;的第15.18.1.1节中定义的字符串。语言规范</cite>。
     * p>
     *  如果指定的对象是{@code Throwable}的实例,它将成为新构造的断言错误的<i>原因</i>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     * @see   Throwable#getCause()
     */
    public AssertionError(Object detailMessage) {
        this(String.valueOf(detailMessage));
        if (detailMessage instanceof Throwable)
            initCause((Throwable) detailMessage);
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>boolean</code>, which is converted to
     * a string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  构造一个AssertionError,其详细消息从指定的<code> boolean </code>派生,它被转换为<cite> Java&trade;的第15.18.1.1节中定义的字符串。
     * 语言规范</cite>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(boolean detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>char</code>, which is converted to a
     * string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     * 构造一个AssertionError,其详细消息来源于指定的<code> char </code>,它转换为<cite> Java&trade;第15.18.1.1节中定义的字符串。
     * 语言规范</cite>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(char detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>int</code>, which is converted to a
     * string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  构造一个AssertionError,其详细消息派生自指定的<code> int </code>,它将转换为<cite> Java&trade;的第15.18.1.1节中定义的字符串;语言规范</cite>
     * 。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(int detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>long</code>, which is converted to a
     * string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  构造一个AssertionError,其详细消息派生自指定的<code> long </code>,它将转换为<cite> Java&trade;第15.18.1.1节中定义的字符串。
     * 语言规范</cite>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(long detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>float</code>, which is converted to a
     * string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  构造一个AssertionError,其详细消息来源于指定的<code> float </code>,它转换为<cite> Java&trade;的第15.18.1.1节中定义的字符串。
     * 语言规范</cite>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(float detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>double</code>, which is converted to a
     * string as defined in section 15.18.1.1 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  构造一个AssertionError,其详细消息来源于指定的<code> double </code>,它转换为<cite> Java&trade;的第15.18.1.1节中定义的字符串。
     * 语言规范</cite>。
     * 
     * 
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(double detailMessage) {
        this(String.valueOf(detailMessage));
    }

    /**
     * Constructs a new {@code AssertionError} with the specified
     * detail message and cause.
     *
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this error's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的{@code AssertionError}。
     * 
     * 
     * @param  message the detail message, may be {@code null}
     * @param  cause the cause, may be {@code null}
     *
     * @since 1.7
     */
    public AssertionError(String message, Throwable cause) {
        super(message, cause);
    }
}
