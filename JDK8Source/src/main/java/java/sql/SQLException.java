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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * <P>An exception that provides information on a database access
 * error or other errors.
 *
 * <P>Each <code>SQLException</code> provides several kinds of information:
 * <UL>
 *   <LI> a string describing the error.  This is used as the Java Exception
 *       message, available via the method <code>getMesasge</code>.
 *   <LI> a "SQLstate" string, which follows either the XOPEN SQLstate conventions
 *        or the SQL:2003 conventions.
 *       The values of the SQLState string are described in the appropriate spec.
 *       The <code>DatabaseMetaData</code> method <code>getSQLStateType</code>
 *       can be used to discover whether the driver returns the XOPEN type or
 *       the SQL:2003 type.
 *   <LI> an integer error code that is specific to each vendor.  Normally this will
 *       be the actual error code returned by the underlying database.
 *   <LI> a chain to a next Exception.  This can be used to provide additional
 *       error information.
 *   <LI> the causal relationship, if any for this <code>SQLException</code>.
 * </UL>
 * <p>
 *  <P>提供有关数据库访问错误或其他错误的信息的异常
 * 
 *  <P>每个<code> SQLException </code>提供了几种信息：
 * <UL>
 * <LI>描述错误的字符串这用作Java异常消息,可通过方法<code> getMesasge </code> <LI>提供"SQLstate"字符串,该字符串遵循XOPEN SQLstate约定或SQL
 * ：2003约定SQLState字符串的值在适当的规范中描述。
 * <code> DatabaseMetaData </code>方法<code> getSQLStateType </code>可用于发现驱动程序是否返回XOPEN类型或SQL：2003类型< LI>每个
 * 供应商特定的整数错误代码通常这将是基础数据库返回的实际错误代码<LI>链接到下一个异常这可以用于提供额外的错误信息<LI>因果关系,如果对此<code> SQLException </code>有任何影
 * 响。
 * </UL>
 */
public class SQLException extends java.lang.Exception
                          implements Iterable<Throwable> {

    /**
     *  Constructs a <code>SQLException</code> object with a given
     * <code>reason</code>, <code>SQLState</code>  and
     * <code>vendorCode</code>.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     * <p>
     * 构造具有给定<code>原因</code>,<code> SQLState </code>和<code> vendorCode </code>的<code> SQLException </code>
     * 
     *  <code> cause </code>未初始化,并且随后可以通过调用{@link Throwable#initCause(javalangThrowable)}方法来初始化
     * <p>
     * 
     * @param reason a description of the exception
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode a database vendor-specific exception code
     */
    public SQLException(String reason, String SQLState, int vendorCode) {
        super(reason);
        this.SQLState = SQLState;
        this.vendorCode = vendorCode;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                DriverManager.println("SQLState(" + SQLState +
                                                ") vendor code(" + vendorCode + ")");
                printStackTrace(DriverManager.getLogWriter());
            }
        }
    }


    /**
     * Constructs a <code>SQLException</code> object with a given
     * <code>reason</code> and <code>SQLState</code>.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
     * is initialized to 0.
     * <p>
     * <p>
     *  用给定的<code> reason </code>和<code> SQLState </code>构造一个<code> SQLException </code>
     * 
     *  <code> cause </code>没有初始化,可以通过调用{@link Throwable#initCause(javalangThrowable)}方法初始化。供应商代码初始化为0
     * <p>
     * 
     * @param reason a description of the exception
     * @param SQLState an XOPEN or SQL:2003 code identifying the exception
     */
    public SQLException(String reason, String SQLState) {
        super(reason);
        this.SQLState = SQLState;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                printStackTrace(DriverManager.getLogWriter());
                DriverManager.println("SQLException: SQLState(" + SQLState + ")");
            }
        }
    }

    /**
     *  Constructs a <code>SQLException</code> object with a given
     * <code>reason</code>. The  <code>SQLState</code>  is initialized to
     * <code>null</code> and the vendor code is initialized to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     * <p>
     *  使用给定的<code>原因构造<code> SQLException </code>对象</code> <code> SQLState </code>初始化为<code> null </code>,并
     * 且供应商代码初始化为0。
     * 
     * <code> cause </code>未初始化,并且随后可以通过调用{@link Throwable#initCause(javalangThrowable)}方法来初始化
     * <p>
     * 
     * @param reason a description of the exception
     */
    public SQLException(String reason) {
        super(reason);
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                printStackTrace(DriverManager.getLogWriter());
            }
        }
    }

    /**
     * Constructs a <code>SQLException</code> object.
     * The <code>reason</code>, <code>SQLState</code> are initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     *
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     *
     * <p>
     *  构造<code> SQLException </code>对象<code> reason </code>,<code> SQLState </code>初始化为<code> null </code>,
     * 供应商代码初始化为0。
     * 
     *  <code> cause </code>未初始化,并且随后可以通过调用{@link Throwable#initCause(javalangThrowable)}方法来初始化
     * 
     */
    public SQLException() {
        super();
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                printStackTrace(DriverManager.getLogWriter());
            }
        }
    }

    /**
     *  Constructs a <code>SQLException</code> object with a given
     * <code>cause</code>.
     * The <code>SQLState</code> is initialized
     * to <code>null</code> and the vendor code is initialized to 0.
     * The <code>reason</code>  is initialized to <code>null</code> if
     * <code>cause==null</code> or to <code>cause.toString()</code> if
     * <code>cause!=null</code>.
     * <p>
     * <p>
     * 使用给定的<code>原因构造<code> SQLException </code>对象</code> <code> SQLState </code>初始化为<code> null </code>,并且
     * 供应商代码初始化为0如果<code> cause == null </code>或<code> causetoString()</code>如果<code>原因,<code> reason </code>
     * 被初始化为<code> null < = null </code>。
     * <p>
     * 
     * @param cause the underlying reason for this <code>SQLException</code>
     * (which is saved for later retrieval by the <code>getCause()</code> method);
     * may be null indicating the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLException(Throwable cause) {
        super(cause);

        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                printStackTrace(DriverManager.getLogWriter());
            }
        }
    }

    /**
     * Constructs a <code>SQLException</code> object with a given
     * <code>reason</code> and  <code>cause</code>.
     * The <code>SQLState</code> is  initialized to <code>null</code>
     * and the vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>和<code>原因的<code> SQLException </code>对象</code> <code> SQLState </code>初始化为<code>
     *  null </code >,并将供应商代码初始化为0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param cause the underlying reason for this <code>SQLException</code>
     * (which is saved for later retrieval by the <code>getCause()</code> method);
     * may be null indicating the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLException(String reason, Throwable cause) {
        super(reason,cause);

        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                    printStackTrace(DriverManager.getLogWriter());
            }
        }
    }

    /**
     * Constructs a <code>SQLException</code> object with a given
     * <code>reason</code>, <code>SQLState</code> and  <code>cause</code>.
     * The vendor code is initialized to 0.
     * <p>
     * <p>
     *  构造具有给定<code>原因</code>,<code> SQLState </code>和<code>原因</code>的<code> SQLException </code>对象供应商代码初始化为
     * 0。
     * <p>
     * 
     * @param reason a description of the exception.
     * @param sqlState an XOPEN or SQL:2003 code identifying the exception
     * @param cause the underlying reason for this <code>SQLException</code>
     * (which is saved for later retrieval by the
     * <code>getCause()</code> method); may be null indicating
     *     the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLException(String reason, String sqlState, Throwable cause) {
        super(reason,cause);

        this.SQLState = sqlState;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                printStackTrace(DriverManager.getLogWriter());
                DriverManager.println("SQLState(" + SQLState + ")");
            }
        }
    }

    /**
     * Constructs a <code>SQLException</code> object with a given
     * <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
     * and  <code>cause</code>.
     * <p>
     * <p>
     * 构造具有给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code>和<code>原因</code>的<code> SQLExc
     * eption </code>。
     * <p>
     * 
     * @param reason a description of the exception
     * @param sqlState an XOPEN or SQL:2003 code identifying the exception
     * @param vendorCode a database vendor-specific exception code
     * @param cause the underlying reason for this <code>SQLException</code>
     * (which is saved for later retrieval by the <code>getCause()</code> method);
     * may be null indicating the cause is non-existent or unknown.
     * @since 1.6
     */
    public SQLException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason,cause);

        this.SQLState = sqlState;
        this.vendorCode = vendorCode;
        if (!(this instanceof SQLWarning)) {
            if (DriverManager.getLogWriter() != null) {
                DriverManager.println("SQLState(" + SQLState +
                                                ") vendor code(" + vendorCode + ")");
                printStackTrace(DriverManager.getLogWriter());
            }
        }
    }

    /**
     * Retrieves the SQLState for this <code>SQLException</code> object.
     *
     * <p>
     *  检索此<code> SQLException </code>对象的SQLState
     * 
     * 
     * @return the SQLState value
     */
    public String getSQLState() {
        return (SQLState);
    }

    /**
     * Retrieves the vendor-specific exception code
     * for this <code>SQLException</code> object.
     *
     * <p>
     *  检索此<code> SQLException </code>对象的供应商特定的异常代码
     * 
     * 
     * @return the vendor's error code
     */
    public int getErrorCode() {
        return (vendorCode);
    }

    /**
     * Retrieves the exception chained to this
     * <code>SQLException</code> object by setNextException(SQLException ex).
     *
     * <p>
     *  通过setNextException(SQLException ex)检索链接到此<code> SQLException </code>对象的异常。
     * 
     * 
     * @return the next <code>SQLException</code> object in the chain;
     *         <code>null</code> if there are none
     * @see #setNextException
     */
    public SQLException getNextException() {
        return (next);
    }

    /**
     * Adds an <code>SQLException</code> object to the end of the chain.
     *
     * <p>
     *  在链的末尾添加一个<code> SQLException </code>对象
     * 
     * 
     * @param ex the new exception that will be added to the end of
     *            the <code>SQLException</code> chain
     * @see #getNextException
     */
    public void setNextException(SQLException ex) {

        SQLException current = this;
        for(;;) {
            SQLException next=current.next;
            if (next != null) {
                current = next;
                continue;
            }

            if (nextUpdater.compareAndSet(current,null,ex)) {
                return;
            }
            current=current.next;
        }
    }

    /**
     * Returns an iterator over the chained SQLExceptions.  The iterator will
     * be used to iterate over each SQLException and its underlying cause
     * (if any).
     *
     * <p>
     *  返回对链接的SQLExceptions的迭代器迭代器将用于遍历每个SQLException及其根本原因(如果有)
     * 
     * 
     * @return an iterator over the chained SQLExceptions and causes in the proper
     * order
     *
     * @since 1.6
     */
    public Iterator<Throwable> iterator() {

       return new Iterator<Throwable>() {

           SQLException firstException = SQLException.this;
           SQLException nextException = firstException.getNextException();
           Throwable cause = firstException.getCause();

           public boolean hasNext() {
               if(firstException != null || nextException != null || cause != null)
                   return true;
               return false;
           }

           public Throwable next() {
               Throwable throwable = null;
               if(firstException != null){
                   throwable = firstException;
                   firstException = null;
               }
               else if(cause != null){
                   throwable = cause;
                   cause = cause.getCause();
               }
               else if(nextException != null){
                   throwable = nextException;
                   cause = nextException.getCause();
                   nextException = nextException.getNextException();
               }
               else
                   throw new NoSuchElementException();
               return throwable;
           }

           public void remove() {
               throw new UnsupportedOperationException();
           }

       };

    }

    /**
    /* <p>
    /* 
         * @serial
         */
    private String SQLState;

        /**
        /* <p>
        /* 
         * @serial
         */
    private int vendorCode;

        /**
        /* <p>
        /* 
         * @serial
         */
    private volatile SQLException next;

    private static final AtomicReferenceFieldUpdater<SQLException,SQLException> nextUpdater =
            AtomicReferenceFieldUpdater.newUpdater(SQLException.class,SQLException.class,"next");

    private static final long serialVersionUID = 2135244094396331484L;
}
