/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.datatype;

/**
 * <p>Indicates a serious configuration error.</p>
 *
 * <p>
 *  <p>表示严重的配置错误。</p>
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @since 1.5
 */

public class DatatypeConfigurationException extends Exception {

    /**
     * <p>Create a new <code>DatatypeConfigurationException</code> with
     * no specified detail mesage and cause.</p>
     * <p>
     *  <p>创建一个新的<code> DatatypeConfigurationException </code>,没有指定的详细信息和原因。</p>
     * 
     */

    public DatatypeConfigurationException() {
        super();
    }

    /**
     * <p>Create a new <code>DatatypeConfigurationException</code> with
         * the specified detail message.</p>
     *
     * <p>
     *  <p>使用指定的详细信息创建新的<code> DatatypeConfigurationException </code>。</p>
     * 
     * 
         * @param message The detail message.
     */

    public DatatypeConfigurationException(String message) {
        super(message);
    }

        /**
         * <p>Create a new <code>DatatypeConfigurationException</code> with
         * the specified detail message and cause.</p>
         *
         * <p>
         *  <p>使用指定的详细消息和原因创建新的<code> DatatypeConfigurationException </code>。</p>
         * 
         * 
         * @param message The detail message.
         * @param cause The cause.  A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
         */

        public DatatypeConfigurationException(String message, Throwable cause) {
                super(message, cause);
        }

        /**
         * <p>Create a new <code>DatatypeConfigurationException</code> with
         * the specified cause.</p>
         *
         * <p>
         *  <p>使用指定的原因创建新的<code> DatatypeConfigurationException </code>。</p>
         * 
         * @param cause The cause.  A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
         */

        public DatatypeConfigurationException(Throwable cause) {
                super(cause);
        }
}
