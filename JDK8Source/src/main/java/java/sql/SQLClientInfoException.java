/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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
package java.sql;

import java.util.Map;

/**
 * The subclass of {@link SQLException} is thrown when one or more client info properties
 * could not be set on a <code>Connection</code>.  In addition to the information provided
 * by <code>SQLException</code>, a <code>SQLClientInfoException</code> provides a list of client info
 * properties that were not set.
 *
 * Some databases do not allow multiple client info properties to be set
 * atomically.  For those databases, it is possible that some of the client
 * info properties had been set even though the <code>Connection.setClientInfo</code>
 * method threw an exception.  An application can use the <code>getFailedProperties </code>
 * method to retrieve a list of client info properties that were not set.  The
 * properties are identified by passing a
 * <code>Map&lt;String,ClientInfoStatus&gt;</code> to
 * the appropriate <code>SQLClientInfoException</code> constructor.
 * <p>
 * <p>
 *  当无法在<code> Connection </code>上设置一个或多个客户端信息属性时,将抛出{@link SQLException}的子类。
 * 除了由<code> SQLException </code>提供的信息,<code> SQLClientInfoException </code>提供了未设置的客户端信息属性列表。
 * 
 *  某些数据库不允许以原子方式设置多个客户端信息属性。
 * 对于这些数据库,尽管<code> Connection.setClientInfo </code>方法引发了异常,但仍有可能设置了一些客户端信息属性。
 * 应用程序可以使用<code> getFailedProperties </code>方法检索未设置的客户端信息属性列表。
 * 通过将<code> Map&lt; String,ClientInfoStatus&gt; </code>传递到适当的<code> SQLClientInfoException </code>构造函数来
 * 识别属性。
 * 应用程序可以使用<code> getFailedProperties </code>方法检索未设置的客户端信息属性列表。
 * <p>
 * 
 * @see ClientInfoStatus
 * @see Connection#setClientInfo
 * @since 1.6
 */
public class SQLClientInfoException extends SQLException {




        private Map<String, ClientInfoStatus>   failedProperties;

        /**
     * Constructs a <code>SQLClientInfoException</code>  Object.
     * The <code>reason</code>,
     * <code>SQLState</code>, and failedProperties list are initialized to
     * <code> null</code> and the vendor code is initialized to 0.
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     *
     * <p>
     *  构造一个<code> SQLClientInfoException </code>对象。
     *  <code>原因</code>,<code> SQLState </code>和failedProperties列表初始化为<code> null </code>,供应商代码初始化为0. <code>
     *  cause </code >未初始化,并且可以随后通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     *  构造一个<code> SQLClientInfoException </code>对象。
     * <p>
     * 
     * 
     * @since 1.6
     */
        public SQLClientInfoException() {

                this.failedProperties = null;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given <code>failedProperties</code>.
     * The <code>reason</code> and <code>SQLState</code> are initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     *
     * <p>
     * 构造使用给定<code> failedProperties </code>初始化的<code> SQLClientInfoException </code>对象。
     *  <code> reason </code>和<code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * <p>
     * 
     * 
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(Map<String, ClientInfoStatus> failedProperties) {

                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with
     * a given <code>cause</code> and <code>failedProperties</code>.
     *
     * The <code>reason</code>  is initialized to <code>null</code> if
     * <code>cause==null</code> or to <code>cause.toString()</code> if
     * <code>cause!=null</code> and the vendor code is initialized to 0.
     *
     * <p>
     *
     * <p>
     *  构造使用给定的<code> cause </code>和<code> failedProperties </code>初始化的<code> SQLClientInfoException </code>
     * 对象。
     * 
     *  如果<code> cause == null </code>或<code> cause.toString()</code>如果<code>,<code>原因</code>被初始化为< cause！= null </code>
     * ,并将供应商代码初始化为0。
     * 
     * <p>
     * 
     * 
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * @param cause                                     the (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(Map<String, ClientInfoStatus> failedProperties,
                                                           Throwable cause) {

                super(cause != null?cause.toString():null);
                initCause(cause);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given <code>reason</code> and <code>failedProperties</code>.
     * The <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     *
     * <p>
     *  构造使用给定的<code> reason </code>和<code> failedProperties </code>初始化的<code> SQLClientInfoException </code>
     * 对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                Map<String, ClientInfoStatus> failedProperties) {

                super(reason);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given <code>reason</code>, <code>cause</code> and
     * <code>failedProperties</code>.
     * The  <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     * <p>
     *
     * <p>
     *  构造使用给定的<code>原因</code>,<code>原因</code>和<code> failedProperties </code>初始化的<code> SQLClientInfoExcept
     * ion </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * @param cause                                     the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                                                           Map<String, ClientInfoStatus> failedProperties,
                                                           Throwable cause) {

                super(reason);
                initCause(cause);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given  <code>reason</code>, <code>SQLState</code>  and
     * <code>failedProperties</code>.
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
     * is initialized to 0.
     * <p>
     *
     * <p>
     * 构造使用给定的<code> reason </code>,<code> SQLState </code>和<code> failedProperties </code>初始化的<code> SQLCli
     * entInfoException </code>对象。
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * 供应商代码初始化为0。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param SQLState                          an XOPEN or SQL:2003 code identifying the exception
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                                                           String SQLState,
                                                           Map<String, ClientInfoStatus> failedProperties) {

                super(reason, SQLState);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given  <code>reason</code>, <code>SQLState</code>, <code>cause</code>
     * and <code>failedProperties</code>.  The vendor code is initialized to 0.
     * <p>
     *
     * <p>
     *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code>原因</code>和<code> failedProperties </code>初始化的<code>
     *  SQLClientInfoException < 。
     * 供应商代码初始化为0。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param SQLState                          an XOPEN or SQL:2003 code identifying the exception
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * @param cause                                     the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                                                           String SQLState,
                                                           Map<String, ClientInfoStatus> failedProperties,
                                                           Throwable cause) {

                super(reason, SQLState);
                initCause(cause);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given  <code>reason</code>, <code>SQLState</code>,
     * <code>vendorCode</code>  and <code>failedProperties</code>.
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     *
     * <p>
     *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code>和<code> failedProperties </code>
     * 初始化的<code> SQLClientInfoException < 。
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param SQLState                          an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode                        a database vendor-specific exception code
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                                                           String SQLState,
                                                           int vendorCode,
                                                           Map<String, ClientInfoStatus> failedProperties) {

                super(reason, SQLState, vendorCode);
                this.failedProperties = failedProperties;
        }

        /**
     * Constructs a <code>SQLClientInfoException</code> object initialized with a
     * given  <code>reason</code>, <code>SQLState</code>,
     * <code>cause</code>, <code>vendorCode</code> and
     * <code>failedProperties</code>.
     * <p>
     *
     * <p>
     *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code>原因</code>,<code> vendorCode </code>初始化的<code> SQ
     * LClientInfoException <和<code> failedProperties </code>。
     * <p>
     * 
     * 
     * @param reason                            a description of the exception
     * @param SQLState                          an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode                        a database vendor-specific exception code
     * @param failedProperties          A Map containing the property values that could not
     *                                  be set.  The keys in the Map
     *                                  contain the names of the client info
     *                                  properties that could not be set and
     *                                  the values contain one of the reason codes
     *                                  defined in <code>ClientInfoStatus</code>
     * @param cause                     the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * <p>
     * @since 1.6
     */
        public SQLClientInfoException(String reason,
                                                           String SQLState,
                                                           int vendorCode,
                                                           Map<String, ClientInfoStatus> failedProperties,
                                                           Throwable cause) {

                super(reason, SQLState, vendorCode);
                initCause(cause);
                this.failedProperties = failedProperties;
        }

    /**
     * Returns the list of client info properties that could not be set.  The
     * keys in the Map  contain the names of the client info
     * properties that could not be set and the values contain one of the
     * reason codes defined in <code>ClientInfoStatus</code>
     * <p>
     *
     * <p>
     * 
     * @return Map list containing the client info properties that could
     * not be set
     * <p>
     * @since 1.6
     */
        public Map<String, ClientInfoStatus> getFailedProperties() {

                return this.failedProperties;
        }

    private static final long serialVersionUID = -4319604256824655880L;
}
