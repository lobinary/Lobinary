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
 * <P>An exception that provides information on  database access
 * warnings. Warnings are silently chained to the object whose method
 * caused it to be reported.
 * <P>
 * Warnings may be retrieved from <code>Connection</code>, <code>Statement</code>,
 * and <code>ResultSet</code> objects.  Trying to retrieve a warning on a
 * connection after it has been closed will cause an exception to be thrown.
 * Similarly, trying to retrieve a warning on a statement after it has been
 * closed or on a result set after it has been closed will cause
 * an exception to be thrown. Note that closing a statement also
 * closes a result set that it might have produced.
 *
 * <p>
 *  <P>提供有关数据库访问警告的信息的异常。警告被静默链接到其方法导致其被报告的对象。
 * <P>
 *  警告可以从<code> Connection </code>,<code> Statement </code>和<code> ResultSet </code>对象中检索。
 * 尝试在关闭连接后检索警告将导致抛出异常。同样,试图在语句关闭后或在结果集关闭后检索语句的警告将导致抛出异常。注意,关闭语句也会关闭它可能产生的结果集。
 * 
 * 
 * @see Connection#getWarnings
 * @see Statement#getWarnings
 * @see ResultSet#getWarnings
 */
public class SQLWarning extends SQLException {

    /**
     * Constructs a  <code>SQLWarning</code> object
     *  with a given <code>reason</code>, <code>SQLState</code>  and
     * <code>vendorCode</code>.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     * <p>
     *  使用给定的<code> reason </code>,<code> SQLState </code>和<code> vendorCode </code>构造<code> SQLWarning </code>
     * 对象。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * <p>
     * 
     * @param reason a description of the warning
     * @param SQLState an XOPEN or SQL:2003 code identifying the warning
     * @param vendorCode a database vendor-specific warning code
     */
     public SQLWarning(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
        DriverManager.println("SQLWarning: reason(" + reason +
                              ") SQLState(" + SQLState +
                              ") vendor code(" + vendorCode + ")");
    }


    /**
     * Constructs a <code>SQLWarning</code> object
     * with a given <code>reason</code> and <code>SQLState</code>.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
     * is initialized to 0.
     * <p>
     * <p>
     *  用给定的<code> reason </code>和<code> SQLState </code>构造一个<code> SQLWarning </code>对象。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * 供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the warning
     * @param SQLState an XOPEN or SQL:2003 code identifying the warning
     */
    public SQLWarning(String reason, String SQLState) {
        super(reason, SQLState);
        DriverManager.println("SQLWarning: reason(" + reason +
                                  ") SQLState(" + SQLState + ")");
    }

    /**
     * Constructs a <code>SQLWarning</code> object
     * with a given <code>reason</code>. The <code>SQLState</code>
     * is initialized to <code>null</code> and the vendor code is initialized
     * to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     * <p>
     * 使用给定的<code> reason </code>构造一个<code> SQLWarning </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * <p>
     * 
     * @param reason a description of the warning
     */
    public SQLWarning(String reason) {
        super(reason);
        DriverManager.println("SQLWarning: reason(" + reason + ")");
    }

    /**
     * Constructs a  <code>SQLWarning</code> object.
     * The <code>reason</code>, <code>SQLState</code> are initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     *
     * <p>
     *  构造一个<code> SQLWarning </code>对象。
     *  <code> reason </code>,<code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * 
     *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
     * 
     */
    public SQLWarning() {
        super();
        DriverManager.println("SQLWarning: ");
    }

    /**
     * Constructs a <code>SQLWarning</code> object
     * with a given  <code>cause</code>.
     * The <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     * The <code>reason</code>  is initialized to <code>null</code> if
     * <code>cause==null</code> or to <code>cause.toString()</code> if
     * <code>cause!=null</code>.
     * <p>
     * <p>
     *  用给定的<code> cause </code>构造一个<code> SQLWarning </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0. <code> reason </code>初始化为<code> null </code>
     *  if <code> cause == null </code>或<code> cause.toString()</code>如果<code> cause！= null </code>。
     *  用给定的<code> cause </code>构造一个<code> SQLWarning </code>对象。
     * <p>
     * 
     * @param cause the underlying reason for this <code>SQLWarning</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     */
    public SQLWarning(Throwable cause) {
        super(cause);
        DriverManager.println("SQLWarning");
    }

    /**
     * Constructs a <code>SQLWarning</code> object
     * with a given
     * <code>reason</code> and  <code>cause</code>.
     * The <code>SQLState</code> is  initialized to <code>null</code>
     * and the vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>和<code>原因</code>的<code> SQLWarning </code>对象。
     *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the warning
     * @param cause  the underlying reason for this <code>SQLWarning</code>
     * (which is saved for later retrieval by the <code>getCause()</code> method);
     * may be null indicating the cause is non-existent or unknown.
     */
    public SQLWarning(String reason, Throwable cause) {
        super(reason,cause);
        DriverManager.println("SQLWarning : reason("+ reason + ")");
    }

    /**
     * Constructs a <code>SQLWarning</code> object
     * with a given
     * <code>reason</code>, <code>SQLState</code> and  <code>cause</code>.
     * The vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>,<code> SQLState </code>和<code>原因</code>的<code> SQLWarning </code>对象。
     * 供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the warning
     * @param SQLState an XOPEN or SQL:2003 code identifying the warning
     * @param cause the underlying reason for this <code>SQLWarning</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     */
    public SQLWarning(String reason, String SQLState, Throwable cause) {
        super(reason,SQLState,cause);
        DriverManager.println("SQLWarning: reason(" + reason +
                                  ") SQLState(" + SQLState + ")");
    }

    /**
     * Constructs a<code>SQLWarning</code> object
     * with a given
     * <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
     * and  <code>cause</code>.
     * <p>
     * <p>
     * 构造具有给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code>和<code>原因</code>的<code> SQLWar
     * ning </code>对象。
     * <p>
     * 
     * @param reason a description of the warning
     * @param SQLState an XOPEN or SQL:2003 code identifying the warning
     * @param vendorCode a database vendor-specific warning code
     * @param cause the underlying reason for this <code>SQLWarning</code> (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     */
    public SQLWarning(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason,SQLState,vendorCode,cause);
        DriverManager.println("SQLWarning: reason(" + reason +
                              ") SQLState(" + SQLState +
                              ") vendor code(" + vendorCode + ")");

    }
    /**
     * Retrieves the warning chained to this <code>SQLWarning</code> object by
     * <code>setNextWarning</code>.
     *
     * <p>
     *  通过<code> setNextWarning </code>检索链接到此<code> SQLWarning </code>对象的警告。
     * 
     * 
     * @return the next <code>SQLException</code> in the chain; <code>null</code> if none
     * @see #setNextWarning
     */
    public SQLWarning getNextWarning() {
        try {
            return ((SQLWarning)getNextException());
        } catch (ClassCastException ex) {
            // The chained value isn't a SQLWarning.
            // This is a programming error by whoever added it to
            // the SQLWarning chain.  We throw a Java "Error".
            throw new Error("SQLWarning chain holds value that is not a SQLWarning");
        }
    }

    /**
     * Adds a <code>SQLWarning</code> object to the end of the chain.
     *
     * <p>
     *  在链的末尾添加<code> SQLWarning </code>对象。
     * 
     * @param w the new end of the <code>SQLException</code> chain
     * @see #getNextWarning
     */
    public void setNextWarning(SQLWarning w) {
        setNextException(w);
    }

    private static final long serialVersionUID = 3917336774604784856L;
}
