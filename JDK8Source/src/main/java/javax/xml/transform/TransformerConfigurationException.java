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
 * Indicates a serious configuration error.
 * <p>
 *  表示严重的配置错误。
 * 
 */
public class TransformerConfigurationException extends TransformerException {

    /**
     * Create a new <code>TransformerConfigurationException</code> with no
     * detail mesage.
     * <p>
     *  创建一个没有详细信息的新<code> TransformerConfigurationException </code>。
     * 
     */
    public TransformerConfigurationException() {
        super("Configuration Error");
    }

    /**
     * Create a new <code>TransformerConfigurationException</code> with
     * the <code>String </code> specified as an error message.
     *
     * <p>
     *  使用指定为错误消息的<code> String </code>创建新的<code> TransformerConfigurationException </code>。
     * 
     * 
     * @param msg The error message for the exception.
     */
    public TransformerConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>TransformerConfigurationException</code> with a
     * given <code>Exception</code> base cause of the error.
     *
     * <p>
     *  使用给定的<code>异常</code>创建一个新的<code> TransformerConfigurationException </code>错误的原因。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * TransformerConfigurationException.
     */
    public TransformerConfigurationException(Throwable e) {
        super(e);
    }

    /**
     * Create a new <code>TransformerConfigurationException</code> with the
     * given <code>Exception</code> base cause and detail message.
     *
     * <p>
     *  使用给定的<code>异常</code>原因和详细信息消息创建新的<code> TransformerConfigurationException </code>。
     * 
     * 
     * @param e The exception to be encapsulated in a
     *      TransformerConfigurationException
     * @param msg The detail message.
     */
    public TransformerConfigurationException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * Create a new TransformerConfigurationException from a message and a Locator.
     *
     * <p>This constructor is especially useful when an application is
     * creating its own exception from within a DocumentHandler
     * callback.</p>
     *
     * <p>
     *  从消息和定位器创建一个新的TransformerConfigurationException。
     * 
     *  <p>当应用程序从DocumentHandler回调中创建自己的异常时,此构造函数尤其有用。</p>
     * 
     * 
     * @param message The error or warning message.
     * @param locator The locator object for the error or warning.
     */
    public TransformerConfigurationException(String message,
                                             SourceLocator locator) {
        super(message, locator);
    }

    /**
     * Wrap an existing exception in a TransformerConfigurationException.
     *
     * <p>
     * 
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param locator The locator object for the error or warning.
     * @param e Any exception.
     */
    public TransformerConfigurationException(String message,
                                             SourceLocator locator,
                                             Throwable e) {
        super(message, locator, e);
    }
}
