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
 * <h3>1.0 Background</h3>
 * The <code>Joinable</code> interface provides the methods for getting and
 * setting a match column, which is the basis for forming the SQL <code>JOIN</code>
 * formed by adding <code>RowSet</code> objects to a <code>JoinRowSet</code>
 * object.
 * <P>
 * Any standard <code>RowSet</code> implementation <b>may</b> implement
 * the <code>Joinable</code> interface in order to be
 * added to a <code>JoinRowSet</code> object. Implementing this interface gives
 * a <code>RowSet</code> object the ability to use <code>Joinable</code> methods,
 * which set, retrieve, and get information about match columns.  An
 * application may add a
 * <code>RowSet</code> object that has not implemented the <code>Joinable</code>
 * interface to a <code>JoinRowSet</code> object, but to do so it must use one
 * of the <code>JoinRowSet.addRowSet</code> methods that takes both a
 * <code>RowSet</code> object and a match column or an array of <code>RowSet</code>
 * objects and an array of match columns.
 * <P>
 * To get access to the methods in the <code>Joinable</code> interface, a
 * <code>RowSet</code> object implements at least one of the
 * five standard <code>RowSet</code> interfaces and also implements the
 * <code>Joinable</code> interface.  In addition, most <code>RowSet</code>
 * objects extend the <code>BaseRowSet</code> class.  For example:
 * <pre>
 *     class MyRowSetImpl extends BaseRowSet implements CachedRowSet, Joinable {
 *         :
 *         :
 *     }
 * </pre>
 *
 * <h3>2.0 Usage Guidelines</h3>
 * <P>
 * The methods in the <code>Joinable</code> interface allow a <code>RowSet</code> object
 * to set a match column, retrieve a match column, or unset a match column, which is
 * the column upon which an SQL <code>JOIN</code> can be based.
 * An instance of a class that implements these methods can be added to a
 * <code>JoinRowSet</code> object to allow an SQL <code>JOIN</code> relationship to
 *  be established.
 *
 * <pre>
 *     CachedRowSet crs = new MyRowSetImpl();
 *     crs.populate((ResultSet)rs);
 *     (Joinable)crs.setMatchColumnIndex(1);
 *
 *     JoinRowSet jrs = new JoinRowSetImpl();
 *     jrs.addRowSet(crs);
 * </pre>
 * In the previous example, <i>crs</i> is a <code>CachedRowSet</code> object that
 * has implemented the <code>Joinable</code> interface.  In the following example,
 * <i>crs2</i> has not, so it must supply the match column as an argument to the
 * <code>addRowSet</code> method. This example assumes that column 1 is the match
 * column.
 * <PRE>
 *     CachedRowSet crs2 = new MyRowSetImpl();
 *     crs2.populate((ResultSet)rs);
 *
 *     JoinRowSet jrs2 = new JoinRowSetImpl();
 *     jrs2.addRowSet(crs2, 1);
 * </PRE>
 * <p>
 * The <code>JoinRowSet</code> interface makes it possible to get data from one or
 * more <code>RowSet</code> objects consolidated into one table without having to incur
 * the expense of creating a connection to a database. It is therefore ideally suited
 * for use by disconnected <code>RowSet</code> objects. Nevertheless, any
 * <code>RowSet</code> object <b>may</b> implement this interface
 * regardless of whether it is connected or disconnected. Note that a
 * <code>JdbcRowSet</code> object, being always connected to its data source, can
 * become part of an SQL <code>JOIN</code> directly without having to become part
 * of a <code>JoinRowSet</code> object.
 *
 * <h3>3.0 Managing Multiple Match Columns</h3>
 * The index array passed into the <code>setMatchColumn</code> methods indicates
 * how many match columns are being set (the length of the array) in addition to
 * which columns will be used for the match. For example:
 * <pre>
 *     int[] i = {1, 2, 4, 7}; // indicates four match columns, with column
 *                             // indexes 1, 2, 4, 7 participating in the JOIN.
 *     Joinable.setMatchColumn(i);
 * </pre>
 * Subsequent match columns may be added as follows to a different <code>Joinable</code>
 * object (a <code>RowSet</code> object that has implemented the <code>Joinable</code>
 * interface).
 * <pre>
 *     int[] w = {3, 2, 5, 3};
 *     Joinable2.setMatchColumn(w);
 * </pre>
 * When an application adds two or more <code>RowSet</code> objects to a
 * <code>JoinRowSet</code> object, the order of the indexes in the array is
 * particularly important. Each index of
 * the array maps directly to the corresponding index of the previously added
 * <code>RowSet</code> object. If overlap or underlap occurs, the match column
 * data is maintained in the event an additional <code>Joinable</code> RowSet is
 * added and needs to relate to the match column data. Therefore, applications
 * can set multiple match columns in any order, but
 * this order has a direct effect on the outcome of the <code>SQL</code> JOIN.
 * <p>
 * This assertion applies in exactly the same manner when column names are used
 * rather than column indexes to indicate match columns.
 *
 * <p>
 *  <h3> 1.0背景</h3> <code> Joinable </code>接口提供了获取和设置匹配列的方法,这是形成SQL <code> JOIN </code>的基础,代码> RowSet </code>
 * 对象转换为<code> JoinRowSet </code>对象。
 * <P>
 *  任何标准<code> RowSet </code>实现<b>可</b>实现<code> Joinable </code>接口,以便添加到<code> JoinRowSet </code>对象。
 * 实现此接口赋予<code> RowSet </code>对象使用<code> Joinable </code>方法的能力,该方法设置,检索和获取有关匹配列的信息。
 * 应用程序可以向<code> JoinRowSet </code>对象添加未实现<code> Joinable </code>对象的<code> RowSet </code>对象,但为此必须使用<code>
 *  JoinRowSet.addRowSet </code>方法,同时接受<code> RowSet </code>对象和匹配列或<code> RowSet </code>对象和匹配列数组。
 * 实现此接口赋予<code> RowSet </code>对象使用<code> Joinable </code>方法的能力,该方法设置,检索和获取有关匹配列的信息。
 * <P>
 *  为了访问<code> Joinable </code>接口中的方法,<code> RowSet </code>对象实现五个标准<code> RowSet </code>接口中的至少一个,代码>可加入</code>
 * 接口。
 * 此外,大多数<code> RowSet </code>对象扩展了<code> BaseRowSet </code>类。例如：。
 * <pre>
 * class MyRowSetImpl extends BaseRowSet implements CachedRowSet,Joinable {：：}
 * </pre>
 * 
 *  <h3> 2.0使用指南</h3>
 * <P>
 *  <code> Joinable </code>接口中的方法允许<code> RowSet </code>对象设置匹配列,检索匹配列或取消设置匹配列,该列是SQL <代码> JOIN </code>可以
 * 基于。
 * 可以将实现这些方法的类的实例添加到<code> JoinRowSet </code>对象中,以允许建立SQL <code> JOIN </code>关系。
 * 
 * <pre>
 *  CachedRowSet crs = new MyRowSetImpl(); crs.populate((ResultSet)rs); (Joinable)crs.setMatchColumnInde
 * x(1);。
 * 
 *  JoinRowSet jrs = new JoinRowSetImpl(); jrs.addRowSet(crs);
 * </pre>
 *  在前面的示例中,<i> crs </i>是实现了<code> Joinable </code>接口的<code> CachedRowSet </code>对象。
 * 在下面的示例中,<i> crs2 </i>没有,因此必须提供匹配列作为<code> addRowSet </code>方法的参数。此示例假设第1列是匹配列。
 * <PRE>
 *  CachedRowSet crs2 = new MyRowSetImpl(); crs2.populate((ResultSet)rs);
 * 
 *  JoinRowSet jrs2 = new JoinRowSetImpl(); jrs2.addRowSet(crs2,1);
 * </PRE>
 * <p>
 * <code> JoinRowSet </code>接口可以从一个或多个合并到一个表中的<Code> RowSet </code>对象中获取数据,而不必承担创建与数据库的连接的费用。
 * 因此,它非常适合于由断开的<code> RowSet </code>对象使用。然而,任何<code> RowSet </code>对象<b>可以</b>实现这个接口,而不管它是连接还是断开连接。
 * 注意,始终连接到其数据源的<code> JdbcRowSet </code>对象可以直接成为SQL <code> JOIN </code>的一部分,而不必成为<code> JoinRowSet </code >
 * 对象。
 * 因此,它非常适合于由断开的<code> RowSet </code>对象使用。然而,任何<code> RowSet </code>对象<b>可以</b>实现这个接口,而不管它是连接还是断开连接。
 * 
 *  <h3> 3.0管理多个匹配列</h3>传递到<code> setMatchColumn </code>方法中的索引数组表示除了要使用哪些列之外,还要设置多少匹配列(数组的长度)为比赛。例如：
 * <pre>
 *  int [] i = {1,2,4,7}; //表示四个匹配列,索引1,2,4,7参与了JOIN。 Joinable.setMatchColumn(i);
 * </pre>
 *  后续匹配列可以如下添加到不同的<code> Joinable </code>对象(实现<code> Joinable </code>接口的<code> RowSet </code>对象)。
 * <pre>
 *  int [] w = {3,2,5,3}; Joinable2.setMatchColumn(w);
 * </pre>
 * 当应用程序向<code> JoinRowSet </code>对象添加两个或多个<code> RowSet </code>对象时,数组中索引的顺序尤为重要。
 * 数组的每个索引直接映射到先前添加的<code> RowSet </code>对象的相应索引。
 * 如果发生重叠或重叠,则在添加额外的<code>可加入</code>行集合并且需要与匹配列数据相关的情况下维持匹配列数据。
 * 
 * @see JoinRowSet
 * @author  Jonathan Bruce
 */
public interface Joinable {

    /**
     * Sets the designated column as the match column for this <code>RowSet</code>
     * object. A <code>JoinRowSet</code> object can now add this <code>RowSet</code>
     * object based on the match column.
     * <p>
     * Sub-interfaces such as the <code>CachedRowSet</code>&trade;
     * interface define the method <code>CachedRowSet.setKeyColumns</code>, which allows
     * primary key semantics to be enforced on specific columns.
     * Implementations of the <code>setMatchColumn(int columnIdx)</code> method
     * should ensure that the constraints on the key columns are maintained when
     * a <code>CachedRowSet</code> object sets a primary key column as a match column.
     *
     * <p>
     * 因此,应用程序可以按任何顺序设置多个匹配列,但此顺序对<code> SQL </code> JOIN的结果有直接影响。
     * <p>
     *  当使用列名而不是列索引来指示匹配列时,此断言以完全相同的方式应用。
     * 
     * 
     * @param columnIdx an <code>int</code> identifying the index of the column to be
     *        set as the match column
     * @throws SQLException if an invalid column index is set
     * @see #setMatchColumn(int[])
     * @see #unsetMatchColumn(int)
     *
     */
    public void setMatchColumn(int columnIdx) throws SQLException;

    /**
     * Sets the designated columns as the match column for this <code>RowSet</code>
     * object. A <code>JoinRowSet</code> object can now add this <code>RowSet</code>
     * object based on the match column.
     *
     * <p>
     *  将指定的列设置为<code> RowSet </code>对象的匹配列。
     *  <code> JoinRowSet </code>对象现在可以基于匹配列添加此<code> RowSet </code>对象。
     * <p>
     *  子接口,如<code> CachedRowSet </code>&trade; interface定义方法<code> CachedRowSet.setKeyColumns </code>,它允许在特
     * 定列上强制实施主键语义。
     *  <code> setMatchColumn(int columnIdx)</code>方法的实现应确保在<code> CachedRowSet </code>对象将主键列设置为匹配列时,维护键列的约束
     * 。
     * 
     * 
     * @param columnIdxes an array of <code>int</code> identifying the indexes of the
     *      columns to be set as the match columns
     * @throws SQLException if an invalid column index is set
     * @see #setMatchColumn(int[])
     * @see #unsetMatchColumn(int[])
     */
    public void setMatchColumn(int[] columnIdxes) throws SQLException;

    /**
     * Sets the designated column as the match column for this <code>RowSet</code>
     * object. A <code>JoinRowSet</code> object can now add this <code>RowSet</code>
     * object based on the match column.
     * <p>
     * Subinterfaces such as the <code>CachedRowSet</code> interface define
     * the method <code>CachedRowSet.setKeyColumns</code>, which allows
     * primary key semantics to be enforced on specific columns.
     * Implementations of the <code>setMatchColumn(String columnIdx)</code> method
     * should ensure that the constraints on the key columns are maintained when
     * a <code>CachedRowSet</code> object sets a primary key column as a match column.
     *
     * <p>
     * 将指定的列设置为<code> RowSet </code>对象的匹配列。 <code> JoinRowSet </code>对象现在可以基于匹配列添加此<code> RowSet </code>对象。
     * 
     * 
     * @param columnName a <code>String</code> object giving the name of the column
     *      to be set as the match column
     * @throws SQLException if an invalid column name is set, the column name
     *      is a null, or the column name is an empty string
     * @see #unsetMatchColumn
     * @see #setMatchColumn(int[])
     */
    public void setMatchColumn(String columnName) throws SQLException;

    /**
     * Sets the designated columns as the match column for this <code>RowSet</code>
     * object. A <code>JoinRowSet</code> object can now add this <code>RowSet</code>
     * object based on the match column.
     *
     * <p>
     *  将指定的列设置为<code> RowSet </code>对象的匹配列。
     *  <code> JoinRowSet </code>对象现在可以基于匹配列添加此<code> RowSet </code>对象。
     * <p>
     *  诸如<code> CachedRowSet </code>接口的子接口定义方法<code> CachedRowSet.setKeyColumns </code>,允许在特定列上强制实施主键语义。
     *  <code> setMatchColumn(String columnIdx)</code>方法的实现应确保在<code> CachedRowSet </code>对象将主键列设置为匹配列时,维护键列
     * 上的约束。
     *  诸如<code> CachedRowSet </code>接口的子接口定义方法<code> CachedRowSet.setKeyColumns </code>,允许在特定列上强制实施主键语义。
     * 
     * 
     * @param columnNames an array of <code>String</code> objects giving the names
     *     of the column to be set as the match columns
     * @throws SQLException if an invalid column name is set, the column name
     *      is a null, or the column name is an empty string
     * @see #unsetMatchColumn
     * @see #setMatchColumn(int[])
     */
    public void setMatchColumn(String[] columnNames) throws SQLException;

    /**
     * Retrieves the indexes of the match columns that were set for this
     * <code>RowSet</code> object with the method
     * <code>setMatchColumn(int[] columnIdxes)</code>.
     *
     * <p>
     *  将指定的列设置为<code> RowSet </code>对象的匹配列。
     *  <code> JoinRowSet </code>对象现在可以基于匹配列添加此<code> RowSet </code>对象。
     * 
     * 
     * @return an <code>int</code> array identifying the indexes of the columns
     *         that were set as the match columns for this <code>RowSet</code> object
     * @throws SQLException if no match column has been set
     * @see #setMatchColumn
     * @see #unsetMatchColumn
     */
    public int[] getMatchColumnIndexes() throws SQLException;

    /**
     * Retrieves the names of the match columns that were set for this
     * <code>RowSet</code> object with the method
     * <code>setMatchColumn(String [] columnNames)</code>.
     *
     * <p>
     *  使用方法<code> setMatchColumn(int [] columnIdxes)</code>检索为此<code> RowSet </code>对象设置的匹配列的索引。
     * 
     * 
     * @return an array of <code>String</code> objects giving the names of the columns
     *         set as the match columns for this <code>RowSet</code> object
     * @throws SQLException if no match column has been set
     * @see #setMatchColumn
     * @see #unsetMatchColumn
     *
     */
    public String[] getMatchColumnNames() throws SQLException;

    /**
     * Unsets the designated column as the match column for this <code>RowSet</code>
     * object.
     * <P>
     * <code>RowSet</code> objects that implement the <code>Joinable</code> interface
     * must ensure that a key-like constraint continues to be enforced until the
     * method <code>CachedRowSet.unsetKeyColumns</code> has been called on the
     * designated column.
     *
     * <p>
     *  使用方法<code> setMatchColumn(String [] columnNames)</code>检索为此<code> RowSet </code>对象设置的匹配列的名称。
     * 
     * 
     * @param columnIdx an <code>int</code> that identifies the index of the column
     *          that is to be unset as a match column
     * @throws SQLException if an invalid column index is designated or if
     *          the designated column was not previously set as a match
     *          column
     * @see #setMatchColumn
     */
    public void unsetMatchColumn(int columnIdx) throws SQLException;

    /**
     * Unsets the designated columns as the match column for this <code>RowSet</code>
     * object.
     *
     * <p>
     *  将指定的列取消设置为<code> RowSet </code>对象的匹配列。
     * <P>
     * 实现<code> Joinable </code>接口的<code> RowSet </code>对象必须确保一个类似键的约束继续执行,直到方法<code> CachedRowSet.unsetKeyC
     * olumns </code>指定列。
     * 
     * 
     * @param columnIdxes an array of <code>int</code> that identifies the indexes
     *     of the columns that are to be unset as match columns
     * @throws SQLException if an invalid column index is designated or if
     *          the designated column was not previously set as a match
     *          column
     * @see #setMatchColumn
     */
    public void unsetMatchColumn(int[] columnIdxes) throws SQLException;

    /**
     * Unsets the designated column as the match column for this <code>RowSet</code>
     * object.
     * <P>
     * <code>RowSet</code> objects that implement the <code>Joinable</code> interface
     * must ensure that a key-like constraint continues to be enforced until the
     * method <code>CachedRowSet.unsetKeyColumns</code> has been called on the
     * designated column.
     *
     * <p>
     *  将指定的列取消设置为<code> RowSet </code>对象的匹配列。
     * 
     * 
     * @param columnName a <code>String</code> object giving the name of the column
     *          that is to be unset as a match column
     * @throws SQLException if an invalid column name is designated or
     *          the designated column was not previously set as a match
     *          column
     * @see #setMatchColumn
     */
    public void unsetMatchColumn(String columnName) throws SQLException;

    /**
     * Unsets the designated columns as the match columns for this <code>RowSet</code>
     * object.
     *
     * <p>
     *  将指定的列取消设置为<code> RowSet </code>对象的匹配列。
     * <P>
     *  实现<code> Joinable </code>接口的<code> RowSet </code>对象必须确保一个类似键的约束继续执行,直到方法<code> CachedRowSet.unsetKey
     * Columns </code>指定列。
     * 
     * @param columnName an array of <code>String</code> objects giving the names of
     *     the columns that are to be unset as the match columns
     * @throws SQLException if an invalid column name is designated or the
     *     designated column was not previously set as a match column
     * @see #setMatchColumn
     */
    public void unsetMatchColumn(String[] columnName) throws SQLException;
}
