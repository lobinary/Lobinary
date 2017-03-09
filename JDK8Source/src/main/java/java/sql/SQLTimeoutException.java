/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * <P>The subclass of {@link SQLException} thrown when the timeout specified by
 * {@code Statement.setQueryTimeout}, {@code DriverManager.setLoginTimeout},
 * {@code DataSource.setLoginTimeout},{@code XADataSource.setLoginTimeout}
 * has expired.
 * <P> This exception does not correspond to a standard SQLState.
 *
 * <p>
 *  <P> {@code Statement.setQueryTimeout},{@code DriverManager.setLoginTimeout},{@code DataSource.setLoginTimeout}
 * ,{@ code XADataSource.setLoginTimeout}指定的超时已过期时,抛出{@link SQLException}的子类。
 *  。 <P>此异常不对应于标准SQLState。
 * 
 * 
 * @since 1.6
 */

public class SQLTimeoutException extends SQLTransientException {
        /**
         * Constructs a <code>SQLTimeoutException</code> object.
         * The <code>reason</code>, <code>SQLState</code> are initialized
         * to <code>null</code> and the vendor code is initialized to 0.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         *  构造一个<code> SQLTimeoutException </code>对象。
         *  <code> reason </code>,<code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @since 1.6
         */
        public SQLTimeoutException() {
                super();
        }

        /**
         * Constructs a <code>SQLTimeoutException</code> object
         * with a given <code>reason</code>. The <code>SQLState</code>
         * is initialized to <code>null</code> and the vendor code is initialized
         * to 0.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         *  构造具有给定<code>原因</code>的<code> SQLTimeoutException </code>对象。
         *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @param reason a description of the exception
         * @since 1.6
         */
        public SQLTimeoutException(String reason) {
                super(reason);
        }

        /**
         * Constructs a <code>SQLTimeoutException</code> object
         * with a given <code>reason</code> and <code>SQLState</code>.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
         * is initialized to 0.
         * <p>
         * <p>
         *  构造具有给定<code> reason </code>和<code> SQLState </code>的<code> SQLTimeoutException </code>对象。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * 供应商代码初始化为0。
         * <p>
         * 
         * @param reason a description of the exception
         * @param SQLState an XOPEN or SQL:2003 code identifying the exception
         * @since 1.6
         */
        public SQLTimeoutException(String reason, String SQLState) {
                super(reason, SQLState);
        }

        /**
         * Constructs a <code>SQLTimeoutException</code> object
         * with a given <code>reason</code>, <code>SQLState</code>  and
         * <code>vendorCode</code>.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         * 构造具有给定<code>原因</code>,<code> SQLState </code>和<code> vendorCode </code>的<code> SQLTimeoutException </code>
         * 对象。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @param reason a description of the exception
         * @param SQLState an XOPEN or SQL:2003 code identifying the exception
         * @param vendorCode a database vendor specific exception code
         * @since 1.6
         */
        public SQLTimeoutException(String reason, String SQLState, int vendorCode) {
                super(reason, SQLState, vendorCode);
        }

    /**
     * Constructs a <code>SQLTimeoutException</code> object
     * with a given  <code>cause</code>.
     * The <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     * The <code>reason</code>  is initialized to <code>null</code> if
     * <code>cause==null</code> or to <code>cause.toString()</code> if
     * <code>cause!=null</code>.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>的<code> SQLTimeoutException </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0. <code> reason </code>初始化为<code> null </code>
     *  if <code> cause == null </code>或<code> cause.toString()</code>如果<code> cause！= null </code>。
     *  构造具有给定<code>原因</code>的<code> SQLTimeoutException </code>对象。
     * <p>
     * 
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLTimeoutException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a <code>SQLTimeoutException</code> object
     * with a given
     * <code>reason</code> and  <code>cause</code>.
     * The <code>SQLState</code> is  initialized to <code>null</code>
     * and the vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>和<code>原因</code>的<code> SQLTimeoutException </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLTimeoutException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Constructs a <code>SQLTimeoutException</code> object
     *  with a given
     * <code>reason</code>, <code>SQLState</code> and  <code>cause</code>.
     * The vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>,<code> SQLState </code>和<code>原因</code>的<code> SQLTimeoutException </code>对象。
     * 供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLTimeoutException(String reason, String SQLState, Throwable cause) {
        super(reason, SQLState, cause);
    }

    /**
     *  Constructs a <code>SQLTimeoutException</code> object
     * with a given
     * <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
     * and  <code>cause</code>.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code>和<code>原因</code>的<code> SQLTi
     * meoutException </code>对象。
     * 
     * @param reason a description of the exception
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode a database vendor-specific exception code
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLTimeoutException(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
    }

    private static final long serialVersionUID = -4487171280562520262L;
}
