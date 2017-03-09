/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset;

import java.sql.SQLException;

/**
 * An extension of <code>SQLException</code> that provides information
 * about database warnings set on <code>RowSet</code> objects.
 * Warnings are silently chained to the object whose method call
 * caused it to be reported.
 * This class complements the <code>SQLWarning</code> class.
 * <P>
 * Rowset warnings may be retrieved from <code>JdbcRowSet</code>,
 * <code>CachedRowSet</code>&trade;,
 * <code>WebRowSet</code>, <code>FilteredRowSet</code>, or <code>JoinRowSet</code>
 * implementations. To retrieve the first warning reported on any
 * <code>RowSet</code>
 * implementation,  use the method <code>getRowSetWarnings</code> defined
 * in the <code>JdbcRowSet</code> interface or the <code>CachedRowSet</code>
 * interface. To retrieve a warning chained to the first warning, use the
 * <code>RowSetWarning</code> method
 * <code>getNextWarning</code>. To retrieve subsequent warnings, call
 * <code>getNextWarning</code> on each <code>RowSetWarning</code> object that is
 * returned.
 * <P>
 * The inherited methods <code>getMessage</code>, <code>getSQLState</code>,
 * and <code>getErrorCode</code> retrieve information contained in a
 * <code>RowSetWarning</code> object.
 * <p>
 *  <code> SQLException </code>的扩展,提供有关<code> RowSet </code>对象上设置的数据库警告的信息。警告被静默链接到方法调用导致它被报告的对象。
 * 此类补充了<code> SQLWarning </code>类。
 * <P>
 *  可以从<code> JdbcRowSet </code>,<code> CachedRowSet </code>&trade;,<code> WebRowSet </code>,<code> Filt
 * eredRowSet </code>或<code> JoinRowSet < / code>实现。
 * 要检索在任何<code> RowSet </code>实现上报告的第一个警告,请使用<code> JdbcRowSet </code>接口或<code> CachedRowSet </code中定义的<code>
 *  getRowSetWarnings </code> >接口。
 * 要检索链接到第一个警告的警告,请使用<code> RowSetWarning </code>方法<code> getNextWarning </code>。
 * 要检索后续警告,请在返回的每个<code> RowSetWarning </code>对象上调用<code> getNextWarning </code>。
 * <P>
 *  继承的方法<code> getMessage </code>,<code> getSQLState </code>和<code> getErrorCode </code>检索包含在<code> Row
 * SetWarning </code>对象中的信息。
 * 
 */
public class RowSetWarning extends SQLException {

    /**
     * RowSetWarning object handle.
     * <p>
     *  RowSetWarning对象句柄。
     * 
     */
     private RowSetWarning rwarning;

    /**
     * Constructs a <code>RowSetWarning</code> object
     * with the given value for the reason; SQLState defaults to null,
     * and vendorCode defaults to 0.
     *
     * <p>
     *  构造具有给定值的<code> RowSetWarning </code>对象的原因; SQLState默认为null,vendorCode默认为0。
     * 
     * 
     * @param reason a <code>String</code> object giving a description
     *        of the warning; if the <code>String</code> is <code>null</code>,
     *        this constructor behaves like the default (zero parameter)
     *        <code>RowSetWarning</code> constructor
     */
    public RowSetWarning(String reason) {
        super(reason);
    }

    /**
     * Constructs a default <code>RowSetWarning</code> object. The reason
     * defaults to <code>null</code>, SQLState defaults to null and vendorCode
     * defaults to 0.
     * <p>
     * 构造一个默认的<code> RowSetWarning </code>对象。原因默认为<code> null </code>,SQLState默认为null,vendorCode默认为0。
     * 
     */
    public RowSetWarning() {
        super();
    }

    /**
     * Constructs a <code>RowSetWarning</code> object initialized with the
     * given values for the reason and SQLState. The vendor code defaults to 0.
     *
     * If the <code>reason</code> or <code>SQLState</code> parameters are <code>null</code>,
     * this constructor behaves like the default (zero parameter)
     * <code>RowSetWarning</code> constructor.
     *
     * <p>
     *  构造使用给定值为原因和SQLState初始化的<code> RowSetWarning </code>对象。供应商代码默认为0。
     * 
     *  如果<code> reason </code>或<code> SQLState </code>参数是<code> null </code>,此构造函数的行为类似于默认(零参数)<code> RowSe
     * tWarning </code>构造函数。
     * 
     * 
     * @param reason a <code>String</code> giving a description of the
     *        warning;
     * @param SQLState an XOPEN code identifying the warning; if a non standard
     *        XOPEN <i>SQLState</i> is supplied, no exception is thrown.
     */
    public RowSetWarning(java.lang.String reason, java.lang.String SQLState) {
        super(reason, SQLState);
    }

    /**
     * Constructs a fully specified <code>RowSetWarning</code> object initialized
     * with the given values for the reason, SQLState and vendorCode.
     *
     * If the <code>reason</code>, or the  <code>SQLState</code>
     * parameters are <code>null</code>, this constructor behaves like the default
     * (zero parameter) <code>RowSetWarning</code> constructor.
     *
     * <p>
     *  构造完全指定的<code> RowSetWarning </code>对象,该对象由给定值(原因为SQLState和vendorCode)初始化。
     * 
     *  如果<code> reason </code>或<code> SQLState </code>参数是<code> null </code>,此构造函数的行为类似于默认(零参数)<code> RowSe
     * tWarning </code>构造函数。
     * 
     * 
     * @param reason a <code>String</code> giving a description of the
     *        warning;
     * @param SQLState an XOPEN code identifying the warning; if a non standard
     *        XPOEN <i>SQLState</i> is supplied, no exception is thrown.
     * @param vendorCode a database vendor-specific warning code
     */
    public RowSetWarning(java.lang.String reason, java.lang.String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    /**
     * Retrieves the warning chained to this <code>RowSetWarning</code>
     * object.
     *
     * <p>
     *  检索链接到此<code> RowSetWarning </code>对象的警告。
     * 
     * 
     * @return the <code>RowSetWarning</code> object chained to this one; if no
     *         <code>RowSetWarning</code> object is chained to this one,
     *         <code>null</code> is returned (default value)
     * @see #setNextWarning
     */
    public RowSetWarning getNextWarning() {
        return rwarning;
    }

    /**
     * Sets <i>warning</i> as the next warning, that is, the warning chained
     * to this <code>RowSetWarning</code> object.
     *
     * <p>
     *  将<i>警告</i>设置为下一个警告,即链接到此<code> RowSetWarning </code>对象的警告。
     * 
     * @param warning the <code>RowSetWarning</code> object to be set as the
     *     next warning; if the <code>RowSetWarning</code> is null, this
     *     represents the finish point in the warning chain
     * @see #getNextWarning
     */
    public void setNextWarning(RowSetWarning warning) {
        rwarning = warning;
    }

    static final long serialVersionUID = 6678332766434564774L;
}
