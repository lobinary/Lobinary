/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

/**
 * An exception that signals that a SOAP exception has occurred. A
 * <code>SOAPException</code> object may contain a <code>String</code>
 * that gives the reason for the exception, an embedded
 * <code>Throwable</code> object, or both. This class provides methods
 * for retrieving reason messages and for retrieving the embedded
 * <code>Throwable</code> object.
 *
 * <P> Typical reasons for throwing a <code>SOAPException</code>
 * object are problems such as difficulty setting a header, not being
 * able to send a message, and not being able to get a connection with
 * the provider.  Reasons for embedding a <code>Throwable</code>
 * object include problems such as input/output errors or a parsing
 * problem, such as an error in parsing a header.
 * <p>
 *  表示发生SOAP异常的异常。
 *  <code> SOAPException </code>对象可以包含给出异常原因的<code> String </code>,嵌入的<code> Throwable </code>对象或两者。
 * 此类提供了用于检索原因消息和检索嵌入的<code> Throwable </code>对象的方法。
 * 
 *  <p>抛出<code> SOAPException </code>对象的典型原因是诸如设置标头,无法发送消息,以及无法获取与提供程序的连接等问题。
 * 嵌入<code> Throwable </code>对象的原因包括诸如输入/输出错误或解析问题(例如解析标头时的错误)等问题。
 * 
 */
public class SOAPException extends Exception {
    private Throwable cause;

    /**
     * Constructs a <code>SOAPException</code> object with no
     * reason or embedded <code>Throwable</code> object.
     * <p>
     *  构造一个没有原因或嵌入的<code> Throwable </code>对象的<code> SOAPException </code>对象。
     * 
     */
    public SOAPException() {
        super();
        this.cause = null;
    }

    /**
     * Constructs a <code>SOAPException</code> object with the given
     * <code>String</code> as the reason for the exception being thrown.
     *
     * <p>
     *  构造具有给定<code> String </code>的<code> SOAPException </code>对象作为抛出异常的原因。
     * 
     * 
     * @param reason a description of what caused the exception
     */
    public SOAPException(String reason) {
        super(reason);
        this.cause = null;
    }

    /**
     * Constructs a <code>SOAPException</code> object with the given
     * <code>String</code> as the reason for the exception being thrown
     * and the given <code>Throwable</code> object as an embedded
     * exception.
     *
     * <p>
     *  使用给定的<code> String </code>构造一个<code> SOAPException </code>对象作为抛出异常的原因,并将给定的<code> Throwable </code>对
     * 象作为嵌入式异常。
     * 
     * 
     * @param reason a description of what caused the exception
     * @param cause a <code>Throwable</code> object that is to
     *        be embedded in this <code>SOAPException</code> object
     */
    public SOAPException(String reason, Throwable cause) {
        super(reason);
        initCause(cause);
    }

    /**
     * Constructs a <code>SOAPException</code> object initialized
     * with the given <code>Throwable</code> object.
     * <p>
     *  构造使用给定的<code> Throwable </code>对象初始化的<code> SOAPException </code>对象。
     * 
     */
    public SOAPException(Throwable cause) {
        super(cause.toString());
        initCause(cause);
    }

    /**
     * Returns the detail message for this <code>SOAPException</code>
     * object.
     * <P>
     * If there is an embedded <code>Throwable</code> object, and if the
     * <code>SOAPException</code> object has no detail message of its
     * own, this method will return the detail message from the embedded
     * <code>Throwable</code> object.
     *
     * <p>
     *  返回此<> SOAPException </code>对象的详细信息。
     * <P>
     * 如果有一个嵌入的<code> Throwable </code>对象,并且<code> SOAPException </code>对象没有自己的详细消息,这个方法将返回嵌入的<code> Throwab
     * le < / code>对象。
     * 
     * 
     * @return the error or warning message for this
     *         <code>SOAPException</code> or, if it has none, the
     *         message of the embedded <code>Throwable</code> object,
     *         if there is one
     */
    public String getMessage() {
        String message = super.getMessage();
        if (message == null && cause != null) {
            return cause.getMessage();
        } else {
            return message;
        }
    }

    /**
     * Returns the <code>Throwable</code> object embedded in this
     * <code>SOAPException</code> if there is one. Otherwise, this method
     * returns <code>null</code>.
     *
     * <p>
     *  返回嵌入在此<code> SOAPException </code>中的<code> Throwable </code>对象(如果有)。否则,此方法返回<code> null </code>。
     * 
     * 
     * @return the embedded <code>Throwable</code> object or <code>null</code>
     *         if there is none
     */

    public Throwable getCause() {
        return cause;
    }

    /**
     * Initializes the <code>cause</code> field of this <code>SOAPException</code>
     * object with the given <code>Throwable</code> object.
     * <P>
     * This method can be called at most once.  It is generally called from
     * within the constructor or immediately after the constructor has
     * returned a new <code>SOAPException</code> object.
     * If this <code>SOAPException</code> object was created with the
     * constructor {@link #SOAPException(Throwable)} or
     * {@link #SOAPException(String,Throwable)}, meaning that its
     * <code>cause</code> field already has a value, this method cannot be
     * called even once.
     *
     * <p>
     *  使用给定的<code> Throwable </code>对象初始化此<code> SOAPException </code>对象的<code> cause </code>字段。
     * <P>
     *  此方法最多可以调用一次。它通常在构造函数内部或在构造函数返回一个新的<code> SOAPException </code>对象之后立即调用。
     * 如果使用构造函数{@link #SOAPException(Throwable)}或{@link #SOAPException(String,Throwable)}创建此<code> SOAPExcep
     * 
     * @param  cause the <code>Throwable</code> object that caused this
     *         <code>SOAPException</code> object to be thrown.  The value of this
     *         parameter is saved for later retrieval by the
     *         {@link #getCause()} method.  A <tt>null</tt> value is
     *         permitted and indicates that the cause is nonexistent or
     *         unknown.
     * @return  a reference to this <code>SOAPException</code> instance
     * @throws IllegalArgumentException if <code>cause</code> is this
     *         <code>Throwable</code> object.  (A <code>Throwable</code> object
     *         cannot be its own cause.)
     * @throws IllegalStateException if the cause for this <code>SOAPException</code> object
     *         has already been initialized
     */
    public synchronized Throwable initCause(Throwable cause) {
        if (this.cause != null) {
            throw new IllegalStateException("Can't override cause");
        }
        if (cause == this) {
            throw new IllegalArgumentException("Self-causation not permitted");
        }
        this.cause = cause;

        return this;
    }
}
