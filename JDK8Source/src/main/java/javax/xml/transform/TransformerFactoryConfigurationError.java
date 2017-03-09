/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

/**
 * Thrown when a problem with configuration with the Transformer Factories
 * exists. This error will typically be thrown when the class of a
 * transformation factory specified in the system properties cannot be found
 * or instantiated.
 * <p>
 *  当存在使用Transformer Factories的配置问题时抛出。当无法找到或实例化系统属性中指定的变换工厂的类时,通常会抛出此错误。
 * 
 */
public class TransformerFactoryConfigurationError extends Error {
    private static final long serialVersionUID = -6527718720676281516L;

    /**
     * <code>Exception</code> for the
     *  <code>TransformerFactoryConfigurationError</code>.
     * <p>
     *  <code> TransformerFactoryConfigurationError </code>的<code>异常</code>。
     * 
     */
    private Exception exception;

    /**
     * Create a new <code>TransformerFactoryConfigurationError</code> with no
     * detail mesage.
     * <p>
     *  创建一个没有详细信息的新<code> TransformerFactoryConfigurationError </code>。
     * 
     */
    public TransformerFactoryConfigurationError() {

        super();

        this.exception = null;
    }

    /**
     * Create a new <code>TransformerFactoryConfigurationError</code> with
     * the <code>String</code> specified as an error message.
     *
     * <p>
     *  使用指定为错误消息的<code> String </code>创建新的<code> TransformerFactoryConfigurationError </code>。
     * 
     * 
     * @param msg The error message for the exception.
     */
    public TransformerFactoryConfigurationError(String msg) {

        super(msg);

        this.exception = null;
    }

    /**
     * Create a new <code>TransformerFactoryConfigurationError</code> with a
     * given <code>Exception</code> base cause of the error.
     *
     * <p>
     *  使用给定的<code>异常</code>创建一个新的<code> TransformerFactoryConfigurationError </code>错误的原因。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * TransformerFactoryConfigurationError.
     */
    public TransformerFactoryConfigurationError(Exception e) {

        super(e.toString());

        this.exception = e;
    }

    /**
     * Create a new <code>TransformerFactoryConfigurationError</code> with the
     * given <code>Exception</code> base cause and detail message.
     *
     * <p>
     *  使用给定的<code>异常</code>基本原因和详细信息创建新的<code> TransformerFactoryConfigurationError </code>。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * TransformerFactoryConfigurationError
     * @param msg The detail message.
     */
    public TransformerFactoryConfigurationError(Exception e, String msg) {

        super(msg);

        this.exception = e;
    }

    /**
     * Return the message (if any) for this error . If there is no
     * message for the exception and there is an encapsulated
     * exception then the message of that exception will be returned.
     *
     * <p>
     *  返回此错误的消息(如果有)。如果没有针对异常的消息,并且存在封装的异常,那么将返回该异常的消息。
     * 
     * 
     * @return The error message.
     */
    public String getMessage() {

        String message = super.getMessage();

        if ((message == null) && (exception != null)) {
            return exception.getMessage();
        }

        return message;
    }

    /**
     * Return the actual exception (if any) that caused this exception to
     * be raised.
     *
     * <p>
     *  返回导致引发此异常的实际异常(如果有)。
     * 
     * 
     * @return The encapsulated exception, or null if there is none.
     */
    public Exception getException() {
        return exception;
    }
    /**
     * use the exception chaining mechanism of JDK1.4
     * <p>
     *  使用JDK1.4的异常链接机制
    */
    @Override
    public Throwable getCause() {
        return exception;
    }
}
