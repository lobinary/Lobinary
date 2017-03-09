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

package javax.xml.parsers;

/**
 * Thrown when a problem with configuration with the Parser Factories
 * exists. This error will typically be thrown when the class of a
 * parser factory specified in the system properties cannot be found
 * or instantiated.
 *
 * <p>
 *  当存在使用解析器工厂的配置问题时抛出。当无法找到或实例化系统属性中指定的解析器工厂的类时,通常会抛出此错误。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Revision: 1.7 $, $Date: 2010-11-01 04:36:09 $
 */

public class FactoryConfigurationError extends Error {
    private static final long serialVersionUID = -827108682472263355L;

    /**
     *<code>Exception</code> that represents the error.
     * <p>
     *  代码>异常</code>表示错误。
     * 
     */
    private Exception exception;

    /**
     * Create a new <code>FactoryConfigurationError</code> with no
     * detail mesage.
     * <p>
     *  创建一个没有详细信息的新<code> FactoryConfigurationError </code>。
     * 
     */

    public FactoryConfigurationError() {
        super();
        this.exception = null;
    }

    /**
     * Create a new <code>FactoryConfigurationError</code> with
     * the <code>String </code> specified as an error message.
     *
     * <p>
     *  创建一个新的<code> FactoryConfigurationError </code>,并将<code> String </code>指定为错误消息。
     * 
     * 
     * @param msg The error message for the exception.
     */

    public FactoryConfigurationError(String msg) {
        super(msg);
        this.exception = null;
    }


    /**
     * Create a new <code>FactoryConfigurationError</code> with a
     * given <code>Exception</code> base cause of the error.
     *
     * <p>
     *  使用给定的<code>异常</code>创建一个新的<code> FactoryConfigurationError </code>错误的原因。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * FactoryConfigurationError.
     */

    public FactoryConfigurationError(Exception e) {
        super(e.toString());
        this.exception = e;
    }

    /**
     * Create a new <code>FactoryConfigurationError</code> with the
     * given <code>Exception</code> base cause and detail message.
     *
     * <p>
     *  使用给定的<code>异常</code>基本原因和详细信息创建新的<code> FactoryConfigurationError </code>。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * FactoryConfigurationError
     * @param msg The detail message.
     */

    public FactoryConfigurationError(Exception e, String msg) {
        super(msg);
        this.exception = e;
    }


    /**
     * Return the message (if any) for this error . If there is no
     * message for the exception and there is an encapsulated
     * exception then the message of that exception, if it exists will be
     * returned. Else the name of the encapsulated exception will be
     * returned.
     *
     * <p>
     *  返回此错误的消息(如果有)。如果没有针对异常的消息,并且存在封装的异常,则该异常的消息(如果存在)将被返回。否则将返回封装的异常的名称。
     * 
     * 
     * @return The error message.
     */

    public String getMessage () {
        String message = super.getMessage ();

        if (message == null && exception != null) {
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

    public Exception getException () {
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
