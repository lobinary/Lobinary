/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.validation;

/**
 * Thrown when a problem with configuration with the Schema Factories
 * exists. This error will typically be thrown when the class of a
 * schema factory specified in the system properties cannot be found
 * or instantiated.
 * <p>
 *  当使用模式工厂的配置存在问题时抛出。当无法找到或实例化系统属性中指定的模式工厂的类时,通常会抛出此错误。
 * 
 * 
 * @since 1.8
 */
public final class SchemaFactoryConfigurationError extends Error {

    static final long serialVersionUID = 3531438703147750126L;

    /**
     * Create a new <code>SchemaFactoryConfigurationError</code> with no
     * detail message.
     * <p>
     *  创建一个没有详细消息的新<> SchemaFactoryConfigurationError </code>。
     * 
     */
    public SchemaFactoryConfigurationError() {
    }


    /**
     * Create a new <code>SchemaFactoryConfigurationError</code> with
     * the <code>String</code> specified as an error message.
     *
     * <p>
     *  使用指定为错误消息的<code> String </code>创建新的<code> SchemaFactoryConfigurationError </code>。
     * 
     * 
     * @param message The error message for the exception.
     */
    public SchemaFactoryConfigurationError(String message) {
        super(message);
    }

    /**
     * Create a new <code>SchemaFactoryConfigurationError</code> with the
     * given <code>Throwable</code> base cause.
     *
     * <p>
     *  使用给定的<code> Throwable </code>基本原因创建一个新的<code> SchemaFactoryConfigurationError </code>。
     * 
     * 
     * @param cause The exception or error to be encapsulated in a
     * SchemaFactoryConfigurationError.
     */
    public SchemaFactoryConfigurationError(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new <code>SchemaFactoryConfigurationError</code> with the
     * given <code>Throwable</code> base cause and detail message.
     *
     * <p>
     *  使用给定的<code> Throwable </code>基本原因和详细信息消息创建新的<code> SchemaFactoryConfigurationError </code>。
     * 
     * @param cause The exception or error to be encapsulated in a
     * SchemaFactoryConfigurationError.
     * @param message The detail message.
     */
    public SchemaFactoryConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

}
