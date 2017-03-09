/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The subclass of {@link SQLException} thrown when the SQLState class value
 * is '<i>42</i>', or under vendor-specified conditions. This indicates that the
 * in-progress query has violated SQL syntax rules.
 * <p>
 * Please consult your driver vendor documentation for the vendor-specified
 * conditions for which this <code>Exception</code> may be thrown.
 * <p>
 *  当SQLState类值为"<i> 42 </i>"或在供应商指定的条件下时抛出的{@link SQLException}子类。这表示进行中的查询违反了SQL语法规则。
 * <p>
 *  请查阅您的驱动程序供应商文档,了解可能抛出该<code>异常</code>的供应商指定条件。
 * 
 * 
 * @since 1.6
 */
public class SQLSyntaxErrorException extends SQLNonTransientException {

        /**
         * Constructs a <code>SQLSyntaxErrorException</code> object.
         *  The <code>reason</code>, <code>SQLState</code> are initialized
         * to <code>null</code> and the vendor code is initialized to 0.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         *  构造一个<code> SQLSyntaxErrorException </code>对象。
         *  <code> reason </code>,<code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @since 1.6
         */
        public SQLSyntaxErrorException() {
                super();
        }

        /**
         * Constructs a <code>SQLSyntaxErrorException</code> object
         * with a given <code>reason</code>. The <code>SQLState</code>
         * is initialized to <code>null</code> and the vendor code is initialized
         * to 0.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         *  构造具有给定<code> reason </code>的<code> SQLSyntaxErrorException </code>对象。
         *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @param reason a description of the exception
         * @since 1.6
         */
        public SQLSyntaxErrorException(String reason) {
                super(reason);
        }

        /**
         * Constructs a <code>SQLSyntaxErrorException</code> object
         * with a given <code>reason</code> and <code>SQLState</code>.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
         * is initialized to 0.
         * <p>
         * <p>
         *  用给定的<code> reason </code>和<code> SQLState </code>构造一个<code> SQLSyntaxErrorException </code>对象。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * 供应商代码初始化为0。
         * <p>
         * 
         * @param reason a description of the exception
         * @param SQLState an XOPEN or SQL:2003 code identifying the exception
         * @since 1.6
         */
        public SQLSyntaxErrorException(String reason, String SQLState) {
                super(reason, SQLState);
        }

        /**
         * Constructs a <code>SQLSyntaxErrorException</code> object
         *  with a given <code>reason</code>, <code>SQLState</code>  and
         * <code>vendorCode</code>.
         *
         * The <code>cause</code> is not initialized, and may subsequently be
         * initialized by a call to the
         * {@link Throwable#initCause(java.lang.Throwable)} method.
         * <p>
         * <p>
         * 使用给定的<code> reason </code>,<code> SQLState </code>和<code> vendorCode </code>构造<code> SQLSyntaxErrorEx
         * ception </code>对象。
         * 
         *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
         * <p>
         * 
         * @param reason a description of the exception
         * @param SQLState an XOPEN or SQL:2003 code identifying the exception
         * @param vendorCode a database vendor specific exception code
         * @since 1.6
         */
        public SQLSyntaxErrorException(String reason, String SQLState, int vendorCode) {
                super(reason, SQLState, vendorCode);
        }

    /**
     * Constructs a <code>SQLSyntaxErrorException</code> object
     *   with a given  <code>cause</code>.
     * The <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     * The <code>reason</code>  is initialized to <code>null</code> if
     * <code>cause==null</code> or to <code>cause.toString()</code> if
     * <code>cause!=null</code>.
     * <p>
     * <p>
     *  用给定的<code> cause </code>构造一个<code> SQLSyntaxErrorException </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0. <code> reason </code>初始化为<code> null </code>
     *  if <code> cause == null </code>或<code> cause.toString()</code>如果<code> cause！= null </code>。
     *  用给定的<code> cause </code>构造一个<code> SQLSyntaxErrorException </code>对象。
     * <p>
     * 
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLSyntaxErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a <code>SQLSyntaxErrorException</code> object
     * with a given
     * <code>reason</code> and  <code>cause</code>.
     * The <code>SQLState</code> is  initialized to <code>null</code>
     * and the vendor code is initialized to 0.
     * <p>
     * <p>
     *  用给定的<code> reason </code>和<code> cause </code>构造一个<code> SQLSyntaxErrorException </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLSyntaxErrorException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Constructs a <code>SQLSyntaxErrorException</code> object
     * with a given
     * <code>reason</code>, <code>SQLState</code> and  <code>cause</code>.
     * The vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>,<code> SQLState </code>和<code>原因</code>的<code> SQLSyntaxErrorException </code>
     * 对象。
     * 供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     * @param cause the (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLSyntaxErrorException(String reason, String SQLState, Throwable cause) {
        super(reason, SQLState, cause);
    }

    /**
     *  Constructs a <code>SQLSyntaxErrorException</code> object
     * with a given
     * <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
     * and  <code>cause</code>.
     * <p>
     * <p>
     * 
     * @param reason a description of the exception
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode a database vendor-specific exception code
     * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLSyntaxErrorException(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
    }

    private static final long serialVersionUID = -1843832610477496053L;
}
