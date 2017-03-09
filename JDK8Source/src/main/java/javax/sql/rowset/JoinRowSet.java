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

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.io.*;
import java.math.*;
import java.util.*;

import javax.sql.rowset.*;

/**
 * The <code>JoinRowSet</code> interface provides a mechanism for combining related
 * data from different <code>RowSet</code> objects into one <code>JoinRowSet</code>
 * object, which represents an SQL <code>JOIN</code>.
 * In other words, a <code>JoinRowSet</code> object acts as a
 * container for the data from <code>RowSet</code> objects that form an SQL
 * <code>JOIN</code> relationship.
 * <P>
 * The <code>Joinable</code> interface provides the methods for setting,
 * retrieving, and unsetting a match column, the basis for
 * establishing an SQL <code>JOIN</code> relationship. The match column may
 * alternatively be set by supplying it to the appropriate version of the
 * <code>JointRowSet</code> method <code>addRowSet</code>.
 *
 * <h3>1.0 Overview</h3>
 * Disconnected <code>RowSet</code> objects (<code>CachedRowSet</code> objects
 * and implementations extending the <code>CachedRowSet</code> interface)
 * do not have a standard way to establish an SQL <code>JOIN</code> between
 * <code>RowSet</code> objects without the expensive operation of
 * reconnecting to the data source. The <code>JoinRowSet</code>
 * interface is specifically designed to address this need.
 * <P>
 * Any <code>RowSet</code> object
 * can be added to a <code>JoinRowSet</code> object to become
 * part of an SQL <code>JOIN</code> relationship. This means that both connected
 * and disconnected <code>RowSet</code> objects can be part of a <code>JOIN</code>.
 * <code>RowSet</code> objects operating in a connected environment
 * (<code>JdbcRowSet</code> objects) are
 * encouraged to use the database to which they are already
 * connected to establish SQL <code>JOIN</code> relationships between
 * tables directly. However, it is possible for a
 * <code>JdbcRowSet</code> object to be added to a <code>JoinRowSet</code> object
 * if necessary.
 * <P>
 * Any number of <code>RowSet</code> objects can be added to an
 * instance of <code>JoinRowSet</code> provided that they
 * can be related in an SQL <code>JOIN</code>.
 * By definition, the SQL <code>JOIN</code> statement is used to
 * combine the data contained in two or more relational database tables based
 * upon a common attribute. The <code>Joinable</code> interface provides the methods
 * for establishing a common attribute, which is done by setting a
 * <i>match column</i>. The match column commonly coincides with
 * the primary key, but there is
 * no requirement that the match column be the same as the primary key.
 * By establishing and then enforcing column matches,
 * a <code>JoinRowSet</code> object establishes <code>JOIN</code> relationships
 * between <code>RowSet</code> objects without the assistance of an available
 * relational database.
 * <P>
 * The type of <code>JOIN</code> to be established is determined by setting
 * one of the <code>JoinRowSet</code> constants using the method
 * <code>setJoinType</code>. The following SQL <code>JOIN</code> types can be set:
 * <UL>
 *  <LI><code>CROSS_JOIN</code>
 *  <LI><code>FULL_JOIN</code>
 *  <LI><code>INNER_JOIN</code> - the default if no <code>JOIN</code> type has been set
 *  <LI><code>LEFT_OUTER_JOIN</code>
 *  <LI><code>RIGHT_OUTER_JOIN</code>
 * </UL>
 * Note that if no type is set, the <code>JOIN</code> will automatically be an
 * inner join. The comments for the fields in the
 * <code>JoinRowSet</code> interface explain these <code>JOIN</code> types, which are
 * standard SQL <code>JOIN</code> types.
 *
 * <h3>2.0 Using a <code>JoinRowSet</code> Object for Creating a <code>JOIN</code></h3>
 * When a <code>JoinRowSet</code> object is created, it is empty.
 * The first <code>RowSet</code> object to be added becomes the basis for the
 * <code>JOIN</code> relationship.
 * Applications must determine which column in each of the
 * <code>RowSet</code> objects to be added to the <code>JoinRowSet</code> object
 * should be the match column. All of the
 * <code>RowSet</code> objects must contain a match column, and the values in
 * each match column must be ones that can be compared to values in the other match
 * columns. The columns do not have to have the same name, though they often do,
 * and they do not have to store the exact same data type as long as the data types
 * can be compared.
 * <P>
 * A match column can be be set in two ways:
 * <ul>
 *  <li>By calling the <code>Joinable</code> method <code>setMatchColumn</code><br>
 *  This is the only method that can set the match column before a <code>RowSet</code>
 *  object is added to a <code>JoinRowSet</code> object. The <code>RowSet</code> object
 *  must have implemented the <code>Joinable</code> interface in order to use the method
 *  <code>setMatchColumn</code>. Once the match column value
 *  has been set, this method can be used to reset the match column at any time.
 *  <li>By calling one of the versions of the <code>JoinRowSet</code> method
 *  <code>addRowSet</code> that takes a column name or number (or an array of
 *  column names or numbers)<BR>
 *  Four of the five <code>addRowSet</code> methods take a match column as a parameter.
 *  These four methods set or reset the match column at the time a <code>RowSet</code>
 *  object is being added to a <code>JoinRowSet</code> object.
 * </ul>
 * <h3>3.0 Sample Usage</h3>
 * <p>
 * The following code fragment adds two <code>CachedRowSet</code>
 * objects to a <code>JoinRowSet</code> object. Note that in this example,
 * no SQL <code>JOIN</code> type is set, so the default <code>JOIN</code> type,
 * which is <i>INNER_JOIN</i>, is established.
 * <p>
 * In the following code fragment, the table <code>EMPLOYEES</code>, whose match
 * column is set to the first column (<code>EMP_ID</code>), is added to the
 * <code>JoinRowSet</code> object <i>jrs</i>. Then
 * the table <code>ESSP_BONUS_PLAN</code>, whose match column is likewise
 * the <code>EMP_ID</code> column, is added. When this second
 * table is added to <i>jrs</i>, only the rows in
 * <code>ESSP_BONUS_PLAN</code> whose <code>EMP_ID</code> value matches an
 * <code>EMP_ID</code> value in the <code>EMPLOYEES</code> table are added.
 * In this case, everyone in the bonus plan is an employee, so all of the rows
 * in the table <code>ESSP_BONUS_PLAN</code> are added to the <code>JoinRowSet</code>
 * object.  In this example, both <code>CachedRowSet</code> objects being added
 * have implemented the <code>Joinable</code> interface and can therefore call
 * the <code>Joinable</code> method <code>setMatchColumn</code>.
 * <PRE>
 *     JoinRowSet jrs = new JoinRowSetImpl();
 *
 *     ResultSet rs1 = stmt.executeQuery("SELECT * FROM EMPLOYEES");
 *     CachedRowSet empl = new CachedRowSetImpl();
 *     empl.populate(rs1);
 *     empl.setMatchColumn(1);
 *     jrs.addRowSet(empl);
 *
 *     ResultSet rs2 = stmt.executeQuery("SELECT * FROM ESSP_BONUS_PLAN");
 *     CachedRowSet bonus = new CachedRowSetImpl();
 *     bonus.populate(rs2);
 *     bonus.setMatchColumn(1); // EMP_ID is the first column
 *     jrs.addRowSet(bonus);
 * </PRE>
 * <P>
 * At this point, <i>jrs</i> is an inside JOIN of the two <code>RowSet</code> objects
 * based on their <code>EMP_ID</code> columns. The application can now browse the
 * combined data as if it were browsing one single <code>RowSet</code> object.
 * Because <i>jrs</i> is itself a <code>RowSet</code> object, an application can
 * navigate or modify it using <code>RowSet</code> methods.
 * <PRE>
 *     jrs.first();
 *     int employeeID = jrs.getInt(1);
 *     String employeeName = jrs.getString(2);
 * </PRE>
 * <P>
 * Note that because the SQL <code>JOIN</code> must be enforced when an application
 * adds a second or subsequent <code>RowSet</code> object, there
 * may be an initial degradation in performance while the <code>JOIN</code> is
 * being performed.
 * <P>
 * The following code fragment adds an additional <code>CachedRowSet</code> object.
 * In this case, the match column (<code>EMP_ID</code>) is set when the
 * <code>CachedRowSet</code> object is added to the <code>JoinRowSet</code> object.
 * <PRE>
 *     ResultSet rs3 = stmt.executeQuery("SELECT * FROM 401K_CONTRIB");
 *     CachedRowSet fourO1k = new CachedRowSetImpl();
 *     four01k.populate(rs3);
 *     jrs.addRowSet(four01k, 1);
 * </PRE>
 * <P>
 * The <code>JoinRowSet</code> object <i>jrs</i> now contains values from all three
 * tables. The data in each row in <i>four01k</i> in which the value for the
 * <code>EMP_ID</code> column matches a value for the <code>EMP_ID</code> column
 * in <i>jrs</i> has been added to <i>jrs</i>.
 *
 * <h3>4.0 <code>JoinRowSet</code> Methods</h3>
 * The <code>JoinRowSet</code> interface supplies several methods for adding
 * <code>RowSet</code> objects and for getting information about the
 * <code>JoinRowSet</code> object.
 * <UL>
 *   <LI>Methods for adding one or more <code>RowSet</code> objects<BR>
 *       These methods allow an application to add one <code>RowSet</code> object
 *       at a time or to add multiple <code>RowSet</code> objects at one time. In
 *       either case, the methods may specify the match column for each
 *       <code>RowSet</code> object being added.
 *   <LI>Methods for getting information<BR>
 *       One method retrieves the <code>RowSet</code> objects in the
 *       <code>JoinRowSet</code> object, and another method retrieves the
 *       <code>RowSet</code> names.  A third method retrieves either the SQL
 *       <code>WHERE</code> clause used behind the scenes to form the
 *       <code>JOIN</code> or a text description of what the <code>WHERE</code>
 *       clause does.
 *   <LI>Methods related to the type of <code>JOIN</code><BR>
 *       One method sets the <code>JOIN</code> type, and five methods find out whether
 *       the <code>JoinRowSet</code> object supports a given type.
 *   <LI>A method to make a separate copy of the <code>JoinRowSet</code> object<BR>
 *       This method creates a copy that can be persisted to the data source.
 * </UL>
 *
 * <p>
 *  <code> JoinRowSet </code>接口提供了将来自不同<code> RowSet </code>对象的相关数据组合成一个<code> JoinRowSet </code>对象的机制,代
 * 表一个SQL <code> JOIN <代码>。
 * 换句话说,<code> JoinRowSet </code>对象充当来自形成SQL <code> JOIN </code>关系的<code> RowSet </code>对象的数据的容器。
 * <P>
 *  <code> Joinable </code>接口提供了设置,检索和取消设置匹配列的方法,这是建立SQL <code> JOIN </code>关系的基础。
 * 可替换地,可以通过将匹配列提供给<code> JointRowSet </code>方法<code> addRowSet </code>的适当版本来设置匹配列。
 * 
 *  <h3> 1.0概述</h3>断开<code> RowSet </code>对象(<code> CachedRowSet </code>对象和实现扩展<code> CachedRowSet </code>
 * 接口)没有标准方法在<code> RowSet </code>对象之间建立SQL <code> JOIN </code>,而不需要重新连接到数据源的昂贵操作。
 *  <code> JoinRowSet </code>接口是专门为解决这一需求而设计的。
 * <P>
 * 任何<code> RowSet </code>对象都可以添加到<code> JoinRowSet </code>对象中,成为SQL <code> JOIN </code>关系的一部分。
 * 这意味着连接和断开的<code> RowSet </code>对象可以是<code> JOIN </code>的一部分。
 * 鼓励在连接环境中操作的<code> RowSet </code>对象(<code> JdbcRowSet </code>对象)使用它们已经连接的数据库,以建立SQL <code> JOIN </code>
 * 表直接。
 * 这意味着连接和断开的<code> RowSet </code>对象可以是<code> JOIN </code>的一部分。
 * 但是,如果需要,可以将<code> JdbcRowSet </code>对象添加到<code> JoinRowSet </code>对象。
 * <P>
 *  任何数量的<code> RowSet </code>对象都可以添加到<code> JoinRowSet </code>的实例,只要它们可以在SQL <code> JOIN </code>中相关。
 * 根据定义,SQL <code> JOIN </code>语句用于基于公共属性组合包含在两个或多个关系数据库表中的数据。
 *  <code> Joinable </code>接口提供了建立公共属性的方法,这是通过设置<i>匹配列</i>完成的。匹配列通常与主键重合,但不要求匹配列与主键相同。
 * 通过建立并执行列匹配,<code> JoinRowSet </code>对象在没有可用关系数据库的帮助下建立<code> RowSet </code>对象之间的<code> JOIN </code>关系
 * 。
 *  <code> Joinable </code>接口提供了建立公共属性的方法,这是通过设置<i>匹配列</i>完成的。匹配列通常与主键重合,但不要求匹配列与主键相同。
 * <P>
 * 要建立的<code> JOIN </code>的类型通过使用方法<code> setJoinType </code>设置<code> JoinRowSet </code>常量之一来确定。
 * 可以设置以下SQL <code> JOIN </code>类型：。
 * <UL>
 *  <LI> <code> CROSS_JOIN </code> <LI> <code> FULL_JOIN </code> <LI> <code> INNER_JOIN </code>  - 如果没有设
 * 置<code> JOIN </code> <LI> <code> LEFT_OUTER_JOIN </code> <LI> <code> RIGHT_OUTER_JOIN </code>。
 * </UL>
 *  注意,如果没有设置类型,<code> JOIN </code>将自动成为内部连接。
 *  <code> JoinRowSet </code>接口中的字段的注释解释这些<code> JOIN </code>类型,它们是标准SQL <code> JOIN </code>类型。
 * 
 *  <h3> 2.0使用<code> JoinRowSet </code>对象创建<code> JOIN </code> </h3>当创建一个<code> JoinRowSet </code>对象时,要添
 * 加的第一个<code> RowSet </code>对象成为<code> JOIN </code>关系的基础。
 * 应用程序必须确定要添加到<code> JoinRowSet </code>对象中的每个<code> RowSet </code>对象中的哪个列应该是匹配列。
 * 所有<code> RowSet </code>对象必须包含匹配列,每个匹配列中的值必须是可与其他匹配列中的值进行比较的值。
 * 列不必具有相同的名称,尽管它们经常这样做,并且它们不必存储完全相同的数据类型,只要可以比较数据类型。
 * <P>
 *  匹配列可以通过两种方式设置：
 * <ul>
 * <li>通过调用<code> Joinable </code>方法<code> setMatchColumn </code> <br>这是唯一可以在添加<code> RowSet </code>对象之前
 * 设置匹配列的方法到一个<code> JoinRowSet </code>对象。
 *  <code> RowSet </code>对象必须实现<code> Joinable </code>接口才能使用<code> setMatchColumn </code>方法。
 * 一旦设置了匹配列值,此方法可用于随时重置匹配列。
 *  <li>通过调用<code> JoinRowSet </code>方法<code> addRowSet </code>的某个版本,它接受列名或数字(或列名或数字数组)<BR>四个五个<code> ad
 * dRowSet </code>方法将匹配列作为参数。
 * 一旦设置了匹配列值,此方法可用于随时重置匹配列。这四种方法在将<code> RowSet </code>对象添加到<code> JoinRowSet </code>对象时设置或重置匹配列。
 * </ul>
 *  <h3> 3.0示例用法</h3>
 * <p>
 *  以下代码片段将两个<code> CachedRowSet </code>对象添加到<code> JoinRowSet </code>对象。
 * 注意,在这个例子中,没有设置SQL <code> JOIN </code>类型,因此建立了默认的<code> JOIN </code>类型,即<i> INNER_JOIN </i>。
 * <p>
 * 在以下代码片段中,将其匹配列设置为第一列(<code> EMP_ID </code>)的表<code> EMPLOYEES </code>添加到<code> JoinRowSet </code>对象<i>
 *  jrs </i>。
 * 然后添加其匹配列与<code> EMP_ID </code>列类似的表<code> ESSP_BONUS_PLAN </code>。
 * 将此第二个表添加到<i> jrs </i>时,只有其<code> EMP_ID </code>值与<code> EMP_ID </code>值匹配的<code> ESSP_BONUS_PLAN </code>
 * 添加了<code> EMPLOYEES </code>表。
 * 然后添加其匹配列与<code> EMP_ID </code>列类似的表<code> ESSP_BONUS_PLAN </code>。
 * 在这种情况下,奖励计划中的每个人都是员工,因此表<code> ESSP_BONUS_PLAN </code>中的所有行都会添加到<code> JoinRowSet </code>对象中。
 * 在这个例子中,被添加的<code> CachedRowSet </code>对象实现了<code> Joinable </code>接口,因此可以调用<code> Joinable </code>方法<code>
 *  setMatchColumn </code> 。
 * 在这种情况下,奖励计划中的每个人都是员工,因此表<code> ESSP_BONUS_PLAN </code>中的所有行都会添加到<code> JoinRowSet </code>对象中。
 * <PRE>
 *  JoinRowSet jrs = new JoinRowSetImpl();
 * 
 *  ResultSet rs1 = stmt.executeQuery("SELECT * FROM EMPLOYEES"); CachedRowSet empl = new CachedRowSetIm
 * pl(); empl.populate(rs1); empl.setMatchColumn(1); jrs.addRowSet(empl);。
 * 
 *  ResultSet rs2 = stmt.executeQuery("SELECT * FROM ESSP_BONUS_PLAN"); CachedRowSet bonus = new CachedR
 * owSetImpl(); bonus.populate(rs2); bonus.setMatchColumn(1); // EMP_ID是第一列jrs.addRowSet(bonus);。
 * </PRE>
 * <P>
 * 此时,<i> jrs </i>是基于它们的<code> EMP_ID </code>列的两个<code> RowSet </code>对象的内部JOIN。
 * 应用程序现在可以浏览组合数据,就像浏览单个<code> RowSet </code>对象。
 * 因为<i> jrs </i>本身是一个<code> RowSet </code>对象,应用程序可以使用<code> RowSet </code>方法来导航或修改它。
 * <PRE>
 *  jrs.first(); int employeeID = jrs.getInt(1); String employeeName = jrs.getString(2);
 * </PRE>
 * <P>
 *  注意,因为当应用程序添加第二个或后续的<code> RowSet </code>对象时,必须强制执行SQL <code> JOIN </code>,所以在<code> JOIN <代码>。
 * <P>
 *  以下代码片段添加了一个附加的<code> CachedRowSet </code>对象。
 * 在这种情况下,当<code> CachedRowSet </code>对象添加到<code> JoinRowSet </code>对象时,设置匹配列(<code> EMP_ID </code>)。
 * <PRE>
 *  ResultSet rs3 = stmt.executeQuery("SELECT * FROM 401K_CONTRIB"); CachedRowSet fourO1k = new CachedRo
 * wSetImpl(); four01k.populate(rs3); jrs.addRowSet(four01k,1);。
 * </PRE>
 * <P>
 *  <code> JoinRowSet </code>对象<i> jrs </i>现在包含来自所有三个表的值。
 *  <code> EMP_ID </code>列的值与<i> jrs </code>列中<code> EMP_ID </code>列的值匹配的<i> four01k </i> i>已添加到<i> jrs 
 * </i>。
 *  <code> JoinRowSet </code>对象<i> jrs </i>现在包含来自所有三个表的值。
 * 
 * <h3> 4.0 <code> JoinRowSet </code>方法</h3> <code> JoinRowSet </code>接口提供了几种添加<code> RowSet </code>对象的方
 * 法, JoinRowSet </code>对象。
 * <UL>
 *  <LI>添加一个或多个<code> RowSet </code>对象的方法<BR>这些方法允许应用程序一次添加一个<code> RowSet </code>对象或添加多个<code> RowSet < / code>
 * 对象。
 * 在任一情况下,方法可以为要添加的每个<code> RowSet </code>对象指定匹配列。
 *  <LI>获取信息的方法<BR>一种方法检索<code> JoinRowSet </code>对象中的<code> RowSet </code>对象,另一个方法检索<code> RowSet </code>
 * 名称。
 * 在任一情况下,方法可以为要添加的每个<code> RowSet </code>对象指定匹配列。
 * 第三种方法检索在场景后面使用的SQL <code> WHERE </code>子句以形成<code> JOIN </code>或<code> WHERE </code>子句的文本描述。
 *  <LI> <code> JOIN </code> <BR>类型的方法一个方法设置<code> JOIN </code>类型,五个方法查找<code> JoinRowSet </code>对象支持给定类
 * 型。
 */

public interface JoinRowSet extends WebRowSet {

    /**
     * Adds the given <code>RowSet</code> object to this <code>JoinRowSet</code>
     * object. If the <code>RowSet</code> object
     * is the first to be added to this <code>JoinRowSet</code>
     * object, it forms the basis of the <code>JOIN</code> relationship to be
     * established.
     * <P>
     * This method should be used only when the given <code>RowSet</code>
     * object already has a match column that was set with the <code>Joinable</code>
     * method <code>setMatchColumn</code>.
     * <p>
     * Note: A <code>Joinable</code> object is any <code>RowSet</code> object
     * that has implemented the <code>Joinable</code> interface.
     *
     * <p>
     * 第三种方法检索在场景后面使用的SQL <code> WHERE </code>子句以形成<code> JOIN </code>或<code> WHERE </code>子句的文本描述。
     *  <LI>创建<code> JoinRowSet </code>对象的单独副本的方法<BR>此方法创建可以持久保存到数据源的副本。
     * </UL>
     * 
     * 
     * @param rowset the <code>RowSet</code> object that is to be added to this
     *        <code>JoinRowSet</code> object; it must implement the
     *        <code>Joinable</code> interface and have a match column set
     * @throws SQLException if (1) an empty rowset is added to the to this
     *         <code>JoinRowSet</code> object, (2) a match column has not been
     *         set for <i>rowset</i>, or (3) <i>rowset</i>
     *         violates the active <code>JOIN</code>
     * @see Joinable#setMatchColumn
     */
    public void addRowSet(Joinable rowset) throws SQLException;

    /**
     * Adds the given <code>RowSet</code> object to this <code>JoinRowSet</code>
     * object and sets the designated column as the match column for
     * the <code>RowSet</code> object. If the <code>RowSet</code> object
     * is the first to be added to this <code>JoinRowSet</code>
     * object, it forms the basis of the <code>JOIN</code> relationship to be
     * established.
     * <P>
     * This method should be used when <i>RowSet</i> does not already have a match
     * column set.
     *
     * <p>
     * 将给定的<code> RowSet </code>对象添加到此<code> JoinRowSet </code>对象。
     * 如果<code> RowSet </code>对象是第一个被添加到这个<code> JoinRowSet </code>对象,它形成了要建立的<code> JOIN </code>关系的基础。
     * <P>
     *  仅当给定的<code> RowSet </code>对象已经具有使用<code> Joinable </code>方法<code> setMatchColumn </code>设置的匹配列时,才应使用
     * 此方法。
     * <p>
     *  注意：<code> Joinable </code>对象是实现了<code> Joinable </code>接口的任何<code> RowSet </code>对象。
     * 
     * 
     * @param rowset the <code>RowSet</code> object that is to be added to this
     *        <code>JoinRowSet</code> object; it may implement the
     *        <code>Joinable</code> interface
     * @param columnIdx an <code>int</code> that identifies the column to become the
     *         match column
     * @throws SQLException if (1) <i>rowset</i> is an empty rowset or
     *         (2) <i>rowset</i> violates the active <code>JOIN</code>
     * @see Joinable#unsetMatchColumn
     */
    public void addRowSet(RowSet rowset, int columnIdx) throws SQLException;

    /**
     * Adds <i>rowset</i> to this <code>JoinRowSet</code> object and
     * sets the designated column as the match column. If <i>rowset</i>
     * is the first to be added to this <code>JoinRowSet</code>
     * object, it forms the basis for the <code>JOIN</code> relationship to be
     * established.
     * <P>
     * This method should be used when the given <code>RowSet</code> object
     * does not already have a match column.
     *
     * <p>
     *  将给定的<code> RowSet </code>对象添加到此<code> JoinRowSet </code>对象中,并将指定的列设置为<code> RowSet </code>对象的匹配列。
     * 如果<code> RowSet </code>对象是第一个被添加到这个<code> JoinRowSet </code>对象,它形成了要建立的<code> JOIN </code>关系的基础。
     * <P>
     *  当<i> RowSet </i>尚未设置匹配列时,应使用此方法。
     * 
     * 
     * @param rowset the <code>RowSet</code> object that is to be added to this
     *        <code>JoinRowSet</code> object; it may implement the
     *        <code>Joinable</code> interface
     * @param columnName the <code>String</code> object giving the name of the
     *        column to be set as the match column
     * @throws SQLException if (1) <i>rowset</i> is an empty rowset or
     *         (2) the match column for <i>rowset</i> does not satisfy the
     *         conditions of the <code>JOIN</code>
     */
     public void addRowSet(RowSet rowset,
                           String columnName) throws SQLException;

    /**
     * Adds one or more <code>RowSet</code> objects contained in the given
     * array of <code>RowSet</code> objects to this <code>JoinRowSet</code>
     * object and sets the match column for
     * each of the <code>RowSet</code> objects to the match columns
     * in the given array of column indexes. The first element in
     * <i>columnIdx</i> is set as the match column for the first
     * <code>RowSet</code> object in <i>rowset</i>, the second element of
     * <i>columnIdx</i> is set as the match column for the second element
     * in <i>rowset</i>, and so on.
     * <P>
     * The first <code>RowSet</code> object added to this <code>JoinRowSet</code>
     * object forms the basis for the <code>JOIN</code> relationship.
     * <P>
     * This method should be used when the given <code>RowSet</code> object
     * does not already have a match column.
     *
     * <p>
     *  将<i>行集</i>添加到此<code> JoinRowSet </code>对象,并将指定的列设置为匹配列。
     * 如果<i> rowset </i>是第一个被添加到这个<code> JoinRowSet </code>对象,它形成了要建立的<code> JOIN </code>关系的基础。
     * <P>
     *  当给定的<code> RowSet </code>对象尚未具有匹配列时,应使用此方法。
     * 
     * 
     * @param rowset an array of one or more <code>RowSet</code> objects
     *        to be added to the <code>JOIN</code>; it may implement the
     *        <code>Joinable</code> interface
     * @param columnIdx an array of <code>int</code> values indicating the index(es)
     *        of the columns to be set as the match columns for the <code>RowSet</code>
     *        objects in <i>rowset</i>
     * @throws SQLException if (1) an empty rowset is added to this
     *         <code>JoinRowSet</code> object, (2) a match column is not set
     *         for a <code>RowSet</code> object in <i>rowset</i>, or (3)
     *         a <code>RowSet</code> object being added violates the active
     *         <code>JOIN</code>
     */
    public void addRowSet(RowSet[] rowset,
                          int[] columnIdx) throws SQLException;

    /**
     * Adds one or more <code>RowSet</code> objects contained in the given
     * array of <code>RowSet</code> objects to this <code>JoinRowSet</code>
     * object and sets the match column for
     * each of the <code>RowSet</code> objects to the match columns
     * in the given array of column names. The first element in
     * <i>columnName</i> is set as the match column for the first
     * <code>RowSet</code> object in <i>rowset</i>, the second element of
     * <i>columnName</i> is set as the match column for the second element
     * in <i>rowset</i>, and so on.
     * <P>
     * The first <code>RowSet</code> object added to this <code>JoinRowSet</code>
     * object forms the basis for the <code>JOIN</code> relationship.
     * <P>
     * This method should be used when the given <code>RowSet</code> object(s)
     * does not already have a match column.
     *
     * <p>
     * 将<code> RowSet </code>对象的给定数组中包含的一个或多个<code> RowSet </code>对象添加到此<code> JoinRowSet </code>对象中,并为每个<code>
     *  > RowSet </code>对象映射到给定的列索引数组中的匹配列。
     *  <i> columnIdx </i>中的第一个元素设置为<i> rowset </i>中第一个<code> RowSet </code>对象的匹配列, i>设置为<i> rowset </i>中的第二
     * 个元素的匹配列,依此类推。
     * <P>
     *  添加到此<code> JoinRowSet </code>对象中的第一个<code> RowSet </code>对象构成了<code> JOIN </code>关系的基础。
     * <P>
     *  当给定的<code> RowSet </code>对象尚未具有匹配列时,应使用此方法。
     * 
     * 
     * @param rowset an array of one or more <code>RowSet</code> objects
     *        to be added to the <code>JOIN</code>; it may implement the
     *        <code>Joinable</code> interface
     * @param columnName an array of <code>String</code> values indicating the
     *        names of the columns to be set as the match columns for the
     *        <code>RowSet</code> objects in <i>rowset</i>
     * @throws SQLException if (1) an empty rowset is added to this
     *         <code>JoinRowSet</code> object, (2) a match column is not set
     *         for a <code>RowSet</code> object in <i>rowset</i>, or (3)
     *         a <code>RowSet</code> object being added violates the active
     *         <code>JOIN</code>
     */
    public void addRowSet(RowSet[] rowset,
                          String[] columnName) throws SQLException;

    /**
     * Returns a <code>Collection</code> object containing the
     * <code>RowSet</code> objects that have been added to this
     * <code>JoinRowSet</code> object.
     * This should return the 'n' number of RowSet contained
     * within the <code>JOIN</code> and maintain any updates that have occurred while in
     * this union.
     *
     * <p>
     *  将<code> RowSet </code>对象的给定数组中包含的一个或多个<code> RowSet </code>对象添加到此<code> JoinRowSet </code>对象中,并为每个<code>
     *  > RowSet </code>对象添加到给定的列名数组中的匹配列。
     *  <i> columnName </i>中的第一个元素设置为<i> rowset </i>中第一个<code> RowSet </code>对象的匹配列, i>设置为<i> rowset </i>中的第
     * 二个元素的匹配列,依此类推。
     * <P>
     *  添加到此<code> JoinRowSet </code>对象中的第一个<code> RowSet </code>对象构成了<code> JOIN </code>关系的基础。
     * <P>
     * 当给定的<code> RowSet </code>对象尚未具有匹配列时,应使用此方法。
     * 
     * 
     * @return a <code>Collection</code> object consisting of the
     *        <code>RowSet</code> objects added to this <code>JoinRowSet</code>
     *        object
     * @throws SQLException if an error occurs generating the
     *         <code>Collection</code> object to be returned
     */
    public Collection<?> getRowSets() throws java.sql.SQLException;

    /**
     * Returns a <code>String</code> array containing the names of the
     *         <code>RowSet</code> objects added to this <code>JoinRowSet</code>
     *         object.
     *
     * <p>
     *  返回包含已添加到此<code> JoinRowSet </code>对象的<code> RowSet </code>对象的<code> Collection </code>对象。
     * 这应该返回包含在<code> JOIN </code>中的"n"个RowSet,并保持在此联合中发生的任何更新。
     * 
     * 
     * @return a <code>String</code> array of the names of the
     *         <code>RowSet</code> objects in this <code>JoinRowSet</code>
     *         object
     * @throws SQLException if an error occurs retrieving the names of
     *         the <code>RowSet</code> objects
     * @see CachedRowSet#setTableName
     */
    public String[] getRowSetNames() throws java.sql.SQLException;

    /**
     * Creates a new <code>CachedRowSet</code> object containing the
     * data in this <code>JoinRowSet</code> object, which can be saved
     * to a data source using the <code>SyncProvider</code> object for
     * the <code>CachedRowSet</code> object.
     * <P>
     * If any updates or modifications have been applied to the JoinRowSet
     * the CachedRowSet returned by the method will not be able to persist
     * it's changes back to the originating rows and tables in the
     * in the datasource. The CachedRowSet instance returned should not
     * contain modification data and it should clear all properties of
     * it's originating SQL statement. An application should reset the
     * SQL statement using the <code>RowSet.setCommand</code> method.
     * <p>
     * In order to allow changes to be persisted back to the datasource
     * to the originating tables, the <code>acceptChanges</code> method
     * should be used and called on a JoinRowSet object instance. Implementations
     * can leverage the internal data and update tracking in their
     * implementations to interact with the SyncProvider to persist any
     * changes.
     *
     * <p>
     *  返回一个<code> String </code>数组,其中包含添加到此<code> JoinRowSet </code>对象的<code> RowSet </code>对象的名称。
     * 
     * 
     * @return a CachedRowSet containing the contents of the JoinRowSet
     * @throws SQLException if an error occurs assembling the CachedRowSet
     * object
     * @see javax.sql.RowSet
     * @see javax.sql.rowset.CachedRowSet
     * @see javax.sql.rowset.spi.SyncProvider
     */
    public CachedRowSet toCachedRowSet() throws java.sql.SQLException;

    /**
     * Indicates if CROSS_JOIN is supported by a JoinRowSet
     * implementation
     *
     * <p>
     *  创建一个包含此<code> JoinRowSet </code>对象中的数据的新的<code> CachedRowSet </code>对象,可以使用<code> SyncProvider </code>
     *  > CachedRowSet </code>对象。
     * <P>
     *  如果对JoinRowSet应用了任何更新或修改,该方法返回的CachedRowSet将不能将更改保存回数据源中的原始行和表。
     * 返回的CachedRowSet实例不应包含修改数据,应清除其原始SQL语句的所有属性。应用程序应使用<code> RowSet.setCommand </code>方法重置SQL语句。
     * <p>
     * 为了允许将更改持久化回原始表的数据源,应在JoinRowSet对象实例上使用和调用<code> acceptChanges </code>方法。
     * 实现可以利用内部数据并在其实现中更新跟踪,以与SyncProvider进行交互,以保持任何更改。
     * 
     * 
     * @return true if the CROSS_JOIN is supported; false otherwise
     */
    public boolean supportsCrossJoin();

    /**
     * Indicates if INNER_JOIN is supported by a JoinRowSet
     * implementation
     *
     * <p>
     *  指示JoinRowSet实现是否支持CROSS_JOIN
     * 
     * 
     * @return true is the INNER_JOIN is supported; false otherwise
     */
    public boolean supportsInnerJoin();

    /**
     * Indicates if LEFT_OUTER_JOIN is supported by a JoinRowSet
     * implementation
     *
     * <p>
     *  指示JoinRowSet实现是否支持INNER_JOIN
     * 
     * 
     * @return true is the LEFT_OUTER_JOIN is supported; false otherwise
     */
    public boolean supportsLeftOuterJoin();

    /**
     * Indicates if RIGHT_OUTER_JOIN is supported by a JoinRowSet
     * implementation
     *
     * <p>
     *  指示LEFT_OUTER_JOIN是否受JoinRowSet实现支持
     * 
     * 
     * @return true is the RIGHT_OUTER_JOIN is supported; false otherwise
     */
    public boolean supportsRightOuterJoin();

    /**
     * Indicates if FULL_JOIN is supported by a JoinRowSet
     * implementation
     *
     * <p>
     *  指示JoinRowSet实现是否支持RIGHT_OUTER_JOIN
     * 
     * 
     * @return true is the FULL_JOIN is supported; false otherwise
     */
    public boolean supportsFullJoin();

    /**
     * Allow the application to adjust the type of <code>JOIN</code> imposed
     * on tables contained within the JoinRowSet object instance.
     * Implementations should throw a SQLException if they do
     * not support a given <code>JOIN</code> type.
     *
     * <p>
     *  指示JoinRowSet实现是否支持FULL_JOIN
     * 
     * 
     * @param joinType the standard JoinRowSet.XXX static field definition
     * of a SQL <code>JOIN</code> to re-configure a JoinRowSet instance on
     * the fly.
     * @throws SQLException if an unsupported <code>JOIN</code> type is set
     * @see #getJoinType
     */
    public void setJoinType(int joinType) throws SQLException;

    /**
     * Return a SQL-like description of the WHERE clause being used
     * in a JoinRowSet object. An implementation can describe
     * the WHERE clause of the SQL <code>JOIN</code> by supplying a SQL
     * strings description of <code>JOIN</code> or provide a textual
     * description to assist applications using a <code>JoinRowSet</code>
     *
     * <p>
     *  允许应用程序调整施加在JoinRowSet对象实例中包含的表上的<code> JOIN </code>的类型。
     * 如果实现不支持给定的<code> JOIN </code>类型,则应抛出SQLException。
     * 
     * 
     * @return whereClause a textual or SQL description of the logical
     * WHERE clause used in the JoinRowSet instance
     * @throws SQLException if an error occurs in generating a representation
     * of the WHERE clause.
     */
    public String getWhereClause() throws SQLException;

    /**
     * Returns a <code>int</code> describing the set SQL <code>JOIN</code> type
     * governing this JoinRowSet instance. The returned type will be one of
     * standard JoinRowSet types: <code>CROSS_JOIN</code>, <code>INNER_JOIN</code>,
     * <code>LEFT_OUTER_JOIN</code>, <code>RIGHT_OUTER_JOIN</code> or
     * <code>FULL_JOIN</code>.
     *
     * <p>
     *  返回在JoinRowSet对象中使用的WHERE子句的类似SQL的描述。
     * 实现可以通过提供<code> JOIN </code>的SQL字符串描述来描述SQL <code> JOIN </code>的WHERE子句,或者提供文本描述以帮助应用程序使用<code> JoinRo
     * wSet </code >。
     *  返回在JoinRowSet对象中使用的WHERE子句的类似SQL的描述。
     * 
     * 
     * @return joinType one of the standard JoinRowSet static field
     *     definitions of a SQL <code>JOIN</code>. <code>JoinRowSet.INNER_JOIN</code>
     *     is returned as the default <code>JOIN</code> type is no type has been
     *     explicitly set.
     * @throws SQLException if an error occurs determining the SQL <code>JOIN</code>
     *     type supported by the JoinRowSet instance.
     * @see #setJoinType
     */
    public int getJoinType() throws SQLException;

    /**
     * An ANSI-style <code>JOIN</code> providing a cross product of two tables
     * <p>
     * 返回描述用于管理此JoinRowSet实例的集合SQL <code> JOIN </code>类型的<code> int </code>。
     * 返回的类型将是标准JoinRowSet类型之一：<code> CROSS_JOIN </code>,<code> INNER_JOIN </code>,<code> LEFT_OUTER_JOIN </code>
     * ,<code> RIGHT_OUTER_JOIN </code> FULL_JOIN </code>。
     * 返回描述用于管理此JoinRowSet实例的集合SQL <code> JOIN </code>类型的<code> int </code>。
     * 
     */
    public static int CROSS_JOIN = 0;

    /**
     * An ANSI-style <code>JOIN</code> providing a inner join between two tables. Any
     * unmatched rows in either table of the join should be discarded.
     * <p>
     *  一个ANSI风格的<code> JOIN </code>提供两个表的叉积
     * 
     */
    public static int INNER_JOIN = 1;

    /**
     * An ANSI-style <code>JOIN</code> providing a left outer join between two
     * tables. In SQL, this is described where all records should be
     * returned from the left side of the JOIN statement.
     * <p>
     *  提供两个表之间的内部联接的ANSI样式<code> JOIN </code>。任何连接表中的任何未匹配的行都应该被丢弃。
     * 
     */
    public static int LEFT_OUTER_JOIN = 2;

    /**
     * An ANSI-style <code>JOIN</code> providing a right outer join between
     * two tables. In SQL, this is described where all records from the
     * table on the right side of the JOIN statement even if the table
     * on the left has no matching record.
     * <p>
     *  在两个表之间提供左外连接的ANSI样式<code> JOIN </code>。在SQL中,描述了所有记录应从JOIN语句的左侧返回的情况。
     * 
     */
    public static int RIGHT_OUTER_JOIN = 3;

    /**
     * An ANSI-style <code>JOIN</code> providing a a full JOIN. Specifies that all
     * rows from either table be returned regardless of matching
     * records on the other table.
     * <p>
     *  在两个表之间提供一个右外连接的ANSI风格的<code> JOIN </code>。在SQL中,描述了来自JOIN语句右侧的表中的所有记录,即使左侧的表没有匹配的记录。
     * 
     */
    public static int FULL_JOIN = 4;


}
