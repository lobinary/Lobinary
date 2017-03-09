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

package javax.sql.rowset.spi;

import javax.sql.RowSet;
import java.sql.SQLException;

/**
 * Defines a framework that allows applications to use a manual decision tree
 * to decide what should be done when a synchronization conflict occurs.
 * Although it is not mandatory for
 * applications to resolve synchronization conflicts manually, this
 * framework provides the means to delegate to the application when conflicts
 * arise.
 * <p>
 * Note that a conflict is a situation where the <code>RowSet</code> object's original
 * values for a row do not match the values in the data source, which indicates that
 * the data source row has been modified since the last synchronization. Note also that
 * a <code>RowSet</code> object's original values are the values it had just prior to the
 * the last synchronization, which are not necessarily its initial values.
 *
 *
 * <H2>Description of a <code>SyncResolver</code> Object</H2>
 *
 * A <code>SyncResolver</code> object is a specialized <code>RowSet</code> object
 * that implements the <code>SyncResolver</code> interface.
 * It <b>may</b> operate as either a connected <code>RowSet</code> object (an
 * implementation of the <code>JdbcRowSet</code> interface) or a connected
 * <code>RowSet</code> object (an implementation of the
 * <code>CachedRowSet</code> interface or one of its subinterfaces). For information
 * on the subinterfaces, see the
 * <a href="../package-summary.html"><code>javax.sql.rowset</code></a> package
 * description. The reference implementation for <code>SyncResolver</code> implements
 * the <code>CachedRowSet</code> interface, but other implementations
 * may choose to implement the <code>JdbcRowSet</code> interface to satisfy
 * particular needs.
 * <P>
 * After an application has attempted to synchronize a <code>RowSet</code> object with
 * the data source (by calling the <code>CachedRowSet</code>
 * method <code>acceptChanges</code>), and one or more conflicts have been found,
 * a rowset's <code>SyncProvider</code> object creates an instance of
 * <code>SyncResolver</code>. This new <code>SyncResolver</code> object has
 * the same number of rows and columns as the
 * <code>RowSet</code> object that was attempting the synchronization. The
 * <code>SyncResolver</code> object contains the values from the data source that caused
 * the conflict(s) and <code>null</code> for all other values.
 * In addition, it contains information about each conflict.
 *
 *
 * <H2>Getting and Using a <code>SyncResolver</code> Object</H2>
 *
 * When the method <code>acceptChanges</code> encounters conflicts, the
 * <code>SyncProvider</code> object creates a <code>SyncProviderException</code>
 * object and sets it with the new <code>SyncResolver</code> object. The method
 * <code>acceptChanges</code> will throw this exception, which
 * the application can then catch and use to retrieve the
 * <code>SyncResolver</code> object it contains. The following code snippet uses the
 * <code>SyncProviderException</code> method <code>getSyncResolver</code> to get
 * the <code>SyncResolver</code> object <i>resolver</i>.
 * <PRE>
 * {@code
 *     } catch (SyncProviderException spe) {
 *         SyncResolver resolver = spe.getSyncResolver();
 *     ...
 *     }
 *
 * }
 * </PRE>
 * <P>
 * With <i>resolver</i> in hand, an application can use it to get the information
 * it contains about the conflict or conflicts.  A <code>SyncResolver</code> object
 * such as <i>resolver</i> keeps
 * track of the conflicts for each row in which there is a conflict.  It also places a
 * lock on the table or tables affected by the rowset's command so that no more
 * conflicts can occur while the current conflicts are being resolved.
 * <P>
 * The following kinds of information can be obtained from a <code>SyncResolver</code>
 * object:
 *
 *    <h3>What operation was being attempted when a conflict occurred</h3>
 * The <code>SyncProvider</code> interface defines four constants
 * describing states that may occur. Three
 * constants describe the type of operation (update, delete, or insert) that a
 * <code>RowSet</code> object was attempting to perform when a conflict was discovered,
 * and the fourth indicates that there is no conflict.
 * These constants are the possible return values when a <code>SyncResolver</code> object
 * calls the method <code>getStatus</code>.
 * <PRE>
 *     {@code int operation = resolver.getStatus(); }
 * </PRE>
 *
 *    <h3>The value in the data source that caused a conflict</h3>
 * A conflict exists when a value that a <code>RowSet</code> object has changed
 * and is attempting to write to the data source
 * has also been changed in the data source since the last synchronization.  An
 * application can call the <code>SyncResolver</code> method
 * <code>getConflictValue</code > to retrieve the
 * value in the data source that is the cause of the conflict because the values in a
 * <code>SyncResolver</code> object are the conflict values from the data source.
 * <PRE>
 *     java.lang.Object conflictValue = resolver.getConflictValue(2);
 * </PRE>
 * Note that the column in <i>resolver</i> can be designated by the column number,
 * as is done in the preceding line of code, or by the column name.
 * <P>
 * With the information retrieved from the methods <code>getStatus</code> and
 * <code>getConflictValue</code>, the application may make a determination as to
 * which value should be persisted in the data source. The application then calls the
 * <code>SyncResolver</code> method <code>setResolvedValue</code>, which sets the value
 * to be persisted in the <code>RowSet</code> object and also in the data source.
 * <PRE>
 *     resolver.setResolvedValue("DEPT", 8390426);
 * </PRE>
 * In the preceding line of code,
 * the column name designates the column in the <code>RowSet</code> object
 * that is to be set with the given value. The column number can also be used to
 * designate the column.
 * <P>
 * An application calls the method <code>setResolvedValue</code> after it has
 * resolved all of the conflicts in the current conflict row and repeats this process
 * for each conflict row in the <code>SyncResolver</code> object.
 *
 *
 * <H2>Navigating a <code>SyncResolver</code> Object</H2>
 *
 * Because a <code>SyncResolver</code> object is a <code>RowSet</code> object, an
 * application can use all of the <code>RowSet</code> methods for moving the cursor
 * to navigate a <code>SyncResolver</code> object. For example, an application can
 * use the <code>RowSet</code> method <code>next</code> to get to each row and then
 * call the <code>SyncResolver</code> method <code>getStatus</code> to see if the row
 * contains a conflict.  In a row with one or more conflicts, the application can
 * iterate through the columns to find any non-null values, which will be the values
 * from the data source that are in conflict.
 * <P>
 * To make it easier to navigate a <code>SyncResolver</code> object, especially when
 * there are large numbers of rows with no conflicts, the <code>SyncResolver</code>
 * interface defines the methods <code>nextConflict</code> and
 * <code>previousConflict</code>, which move only to rows
 * that contain at least one conflict value. Then an application can call the
 * <code>SyncResolver</code> method <code>getConflictValue</code>, supplying it
 * with the column number, to get the conflict value itself. The code fragment in the
 * next section gives an example.
 *
 * <H2>Code Example</H2>
 *
 * The following code fragment demonstrates how a disconnected <code>RowSet</code>
 * object <i>crs</i> might attempt to synchronize itself with the
 * underlying data source and then resolve the conflicts. In the <code>try</code>
 * block, <i>crs</i> calls the method <code>acceptChanges</code>, passing it the
 * <code>Connection</code> object <i>con</i>.  If there are no conflicts, the
 * changes in <i>crs</i> are simply written to the data source.  However, if there
 * is a conflict, the method <code>acceptChanges</code> throws a
 * <code>SyncProviderException</code> object, and the
 * <code>catch</code> block takes effect.  In this example, which
 * illustrates one of the many ways a <code>SyncResolver</code> object can be used,
 * the <code>SyncResolver</code> method <code>nextConflict</code> is used in a
 * <code>while</code> loop. The loop will end when <code>nextConflict</code> returns
 * <code>false</code>, which will occur when there are no more conflict rows in the
 * <code>SyncResolver</code> object <i>resolver</i>. In This particular code fragment,
 * <i>resolver</i> looks for rows that have update conflicts (rows with the status
 * <code>SyncResolver.UPDATE_ROW_CONFLICT</code>), and the rest of this code fragment
 * executes only for rows where conflicts occurred because <i>crs</i> was attempting an
 * update.
 * <P>
 * After the cursor for <i>resolver</i> has moved to the next conflict row that
 * has an update conflict, the method <code>getRow</code> indicates the number of the
 * current row, and
 * the cursor for the <code>CachedRowSet</code> object <i>crs</i> is moved to
 * the comparable row in <i>crs</i>. By iterating
 * through the columns of that row in both <i>resolver</i> and <i>crs</i>, the conflicting
 * values can be retrieved and compared to decide which one should be persisted. In this
 * code fragment, the value in <i>crs</i> is the one set as the resolved value, which means
 * that it will be used to overwrite the conflict value in the data source.
 *
 * <PRE>
 * {@code
 *     try {
 *
 *         crs.acceptChanges(con);
 *
 *     } catch (SyncProviderException spe) {
 *
 *         SyncResolver resolver = spe.getSyncResolver();
 *
 *         Object crsValue;  // value in the RowSet object
 *         Object resolverValue:  // value in the SyncResolver object
 *         Object resolvedValue:  // value to be persisted
 *
 *         while(resolver.nextConflict())  {
 *             if(resolver.getStatus() == SyncResolver.UPDATE_ROW_CONFLICT)  {
 *                 int row = resolver.getRow();
 *                 crs.absolute(row);
 *
 *                 int colCount = crs.getMetaData().getColumnCount();
 *                 for(int j = 1; j <= colCount; j++) {
 *                     if (resolver.getConflictValue(j) != null)  {
 *                         crsValue = crs.getObject(j);
 *                         resolverValue = resolver.getConflictValue(j);
 *                         . . .
 *                         // compare crsValue and resolverValue to determine
 *                         // which should be the resolved value (the value to persist)
 *                         resolvedValue = crsValue;
 *
 *                         resolver.setResolvedValue(j, resolvedValue);
 *                      }
 *                  }
 *              }
 *          }
 *      }
 * }</PRE>
 * <p>
 *  定义一个框架,允许应用程序使用手动决策树来决定在发生同步冲突时应该做什么。虽然应用程序不是手动解决同步冲突的强制性要求,但该框架提供了在发生冲突时委派给应用程序的方法。
 * <p>
 *  请注意,冲突是指某行的<code> RowSet </code>对象的原始值与数据源中的值不匹配的情况,表示数据源行自上次同步后已被修改。
 * 还要注意,一个<code> RowSet </code>对象的原始值是它刚刚在最后一次同步之前的值,它们不一定是它的初始值。
 * 
 *  <H2> <code> SyncResolver </code>对象</H2>的说明
 * 
 * <code> SyncResolver </code>对象是实现<code> SyncResolver </code>接口的专用<code> RowSet </code>对象。
 * 它<b>可</b>作为连接的<code> RowSet </code>对象(<code> JdbcRowSet </code>接口的实现)或连接的<code> RowSet </code>对象(<code>
 *  CachedRowSet </code>接口或其子接口之一的实现)。
 * <code> SyncResolver </code>对象是实现<code> SyncResolver </code>接口的专用<code> RowSet </code>对象。
 * 有关子接口的信息,请参见<a href="../package-summary.html"> <code> javax.sql.rowset </code> </a>软件包描述。
 *  <code> SyncResolver </code>的参考实现实现了<code> CachedRowSet </code>接口,但是其他实现可以选择实现<code> JdbcRowSet </code>
 * 接口以满足特定需求。
 * 有关子接口的信息,请参见<a href="../package-summary.html"> <code> javax.sql.rowset </code> </a>软件包描述。
 * <P>
 *  在应用程序试图使<code> RowSet </code>对象与数据源同步(通过调用<code> CachedRowSet </code>方法<code> acceptChanges </code>)
 * 之后,一个或多个冲突找到后,行集的<code> SyncProvider </code>对象创建一个<code> SyncResolver </code>的实例。
 * 这个新的<code> SyncResolver </code>对象具有与尝试同步的<code> RowSet </code>对象相同数量的行和列。
 *  <code> SyncResolver </code>对象包含导致冲突的数据源的值和所有其他值的<code> null </code>。此外,它包含有关每个冲突的信息。
 * 
 * <H2>获取和使用<code> SyncResolver </code>对象</H2>
 * 
 *  当方法<code> acceptChanges </code>遇到冲突时,<code> SyncProvider </code>对象创建一个<code> SyncProviderException </code>
 * 对象,并使用新的<code> SyncResolver </code>对象。
 * 方法<code> acceptChanges </code>会抛出此异常,然后应用程序可以捕获并使用它来检索其包含的<code> SyncResolver </code>对象。
 * 以下代码片段使用<code> SyncProviderException </code>方法<code> getSyncResolver </code>获取<code> SyncResolver </code>
 * 对象<i>解析器</i>。
 * 方法<code> acceptChanges </code>会抛出此异常,然后应用程序可以捕获并使用它来检索其包含的<code> SyncResolver </code>对象。
 * <PRE>
 *  {@code} catch(SyncProviderException spe){SyncResolver resolver = spe.getSyncResolver(); ...}
 * 
 *  }}
 * </PRE>
 * <P>
 *  使用<i> resolver </i>,应用程序可以使用它来获取其包含的有关冲突或冲突的信息。
 * 诸如<i> resolver </i>之类的<code> SyncResolver </code>对象会跟踪存在冲突的每一行的冲突。
 * 它还对受行集的命令影响的表设置锁定,以便在解决当前冲突时不会发生更多冲突。
 * <P>
 *  可以从<code> SyncResolver </code>对象获取以下类型的信息：
 * 
 * <h3>发生冲突时尝试执行什么操作</h3> <code> SyncProvider </code>接口定义四个常量,描述可能发生的状态。
 * 三个常量描述一个<code> RowSet </code>对象在发现冲突时尝试执行的操作类型(更新,删除或插入),第四个表示没有冲突。
 * 这些常量是当<code> SyncResolver </code>对象调用<code> getStatus </code>方法时可能的返回值。
 * <PRE>
 *  {@code int operation = resolver.getStatus(); }}
 * </PRE>
 * 
 *  <h3>导致冲突的数据源中的值</h3>当<code> RowSet </code>对象已更改并试图写入数据源的值已存在时,自上次同步以来的数据源。
 * 应用程序可以调用<code> SyncResolver </code>方法<code> getConflictValue </code>来检索数据源中导致冲突的值,因为<code> SyncResolv
 * er </code>对象是来自数据源的冲突值。
 *  <h3>导致冲突的数据源中的值</h3>当<code> RowSet </code>对象已更改并试图写入数据源的值已存在时,自上次同步以来的数据源。
 * <PRE>
 *  java.lang.Object conflictValue = resolver.getConflictValue(2);
 * </PRE>
 *  请注意,<i> resolver </i>中的列可以由列号指定,如在前一行代码中完成的那样,或者由列名指定。
 * <P>
 * 利用从方法<code> getStatus </code>和<code> getConflictValue </code>检索的信息,应用可以确定应当在数据源中保持哪个值。
 * 应用程序然后调用<code> SyncResolver </code>方法<code> setResolvedValue </code>,它设置要保存在<code> RowSet </code>对象和数
 * 据源中的值。
 * 利用从方法<code> getStatus </code>和<code> getConflictValue </code>检索的信息,应用可以确定应当在数据源中保持哪个值。
 * <PRE>
 *  resolver.setResolvedValue("DEPT",8390426);
 * </PRE>
 *  在上一行代码中,列名指定要使用给定值设置的<code> RowSet </code>对象中的列。列号也可用于指定列。
 * <P>
 *  应用程序在解决当前冲突行中的所有冲突后调用<code> setResolvedValue </code>方法,并对<code> SyncResolver </code>对象中的每个冲突行重复此过程。
 * 
 *  <H2>导航<code> SyncResolver </code>对象</H2>
 * 
 * 因为<code> SyncResolver </code>对象是一个<code> RowSet </code>对象,应用程序可以使用所有<code> RowSet </code>方法来移动光标来导航<code>
 *  SyncResolver </code>对象。
 * 例如,应用程序可以使用<code> RowSet </code>方法<code> next </code>获取每行,然后调用<code> SyncResolver </code>方法<code> get
 * Status </code >查看行是否包含冲突。
 * 在具有一个或多个冲突的行中,应用程序可以遍历列以查找任何非空值,这些值将是来自数据源中冲突的值。
 * <P>
 *  为了更容易导航一个<code> SyncResolver </code>对象,特别是当有大量没有冲突的行时,<code> SyncResolver </code>接口定义方法<code> nextCo
 * nflict </code>和<code> previousConflict </code>,它们只移动到包含至少一个冲突值的行。
 * 然后应用程序可以调用<code> SyncResolver </code>方法<code> getConflictValue </code>,为其提供列号,以获取冲突值本身。
 * 下一节中的代码片段给出了一个示例。
 * 
 *  <H2>代码示例</H2>
 * 
 * @author  Jonathan Bruce
 */

public interface SyncResolver extends RowSet {
    /**
     * Indicates that a conflict occurred while the <code>RowSet</code> object was
     * attempting to update a row in the data source.
     * The values in the data source row to be updated differ from the
     * <code>RowSet</code> object's original values for that row, which means that
     * the row in the data source has been updated or deleted since the last
     * synchronization.
     * <p>
     * 
     * 以下代码片段演示了断开的<code> RowSet </code>对象<i> crs </i>如何尝试将其本身与底层数据源同步,然后解决冲突。
     * 在<code> try </code>块中,<i> crs </i>调用方法<code> acceptChanges </code>,将<code> Connection </code> i>。
     * 如果没有冲突,则将<i> crs </i>中的更改写入数据源。
     * 但是,如果存在冲突,方法<code> acceptChanges </code>会抛出一个<code> SyncProviderException </code>对象,<code> catch </code>
     * 在本示例中,其示出了可以使用<code> SyncResolver </code>对象的许多方式之一,<code> SyncResolver </code>方法<code> nextConflict </code>
     *  while </code>循环。
     * 如果没有冲突,则将<i> crs </i>中的更改写入数据源。
     * 当<code> SyncResolver </code>对象<i> resolver </code>中没有更多冲突行时,循环将结束<code> nextConflict </code> i>。
     * 在此特定代码片段中,<i>解析器</i>查找具有更新冲突的行(状态为<code> SyncResolver.UPDATE_ROW_CONFLICT </code>的行),此代码片段的其余部分仅对发生冲突
     * ,因为<i> crs </i>尝试更新。
     * 当<code> SyncResolver </code>对象<i> resolver </code>中没有更多冲突行时,循环将结束<code> nextConflict </code> i>。
     * <P>
     * 在<i> resolver </i>的游标移动到具有更新冲突的下一个冲突行后,方法<code> getRow </code>表示当前行的编号,<code> > CachedRowSet </code>对
     * 象<i> crs </i>移动到<i> crs </i>中可比较的行。
     * 通过在<i> resolver </i>和<i> crs </i>中遍历该行的列,可以检索和比较冲突的值,以决定哪个值应该被持久化。
     * 在此代码片段中,<i> crs </i>中的值是设置为解析值的值,这意味着它将用于覆盖数据源中的冲突值。
     * 
     * <PRE>
     *  {@code try {
     * 
     *  crs.acceptChanges(con);
     * 
     *  } catch(SyncProviderException spe){
     * 
     *  SyncResolver resolver = spe.getSyncResolver();
     * 
     *  对象crsValue; // RowSet对象中的值对象resolverValue：// SyncResolver对象中的值对象resolvedValue：//要持久化的值
     * 
     *  while(resolver.nextConflict()){if(resolver.getStatus()== SyncResolver.UPDATE_ROW_CONFLICT){int row = resolver.getRow(); crs.absolute(row);。
     * 
     *  int colCount = crs.getMetaData()。
     * getColumnCount(); for(int j = 1; j <= colCount; j ++){if(resolver.getConflictValue(j)！= null){crsValue = crs.getObject(j); resolverValue = resolver.getConflictValue(j); 。
     *  int colCount = crs.getMetaData()。 。 。
     *  //比较crsValue和resolverValue以确定//应该是已解析的值(要保留的值)resolvedValue = crsValue;。
     * 
     *  resolver.setResolvedValue(j,resolvedValue); }}}}}} </PRE>
     * 
     */
     public static int UPDATE_ROW_CONFLICT = 0;

    /**
     * Indicates that a conflict occurred while the <code>RowSet</code> object was
     * attempting to delete a row in the data source.
     * The values in the data source row to be updated differ from the
     * <code>RowSet</code> object's original values for that row, which means that
     * the row in the data source has been updated or deleted since the last
     * synchronization.
     * <p>
     * 表示当<code> RowSet </code>对象尝试更新数据源中的行时发生冲突。
     * 要更新的数据源行中的值与该行的<code> RowSet </code>对象的原始值不同,这意味着数据源中的行自上次同步后已更新或删除。
     * 
     */
    public static int DELETE_ROW_CONFLICT = 1;

   /**
    * Indicates that a conflict occurred while the <code>RowSet</code> object was
    * attempting to insert a row into the data source.  This means that a
    * row with the same primary key as the row to be inserted has been inserted
    * into the data source since the last synchronization.
    * <p>
    *  表示当<code> RowSet </code>对象尝试删除数据源中的行时发生冲突。
    * 要更新的数据源行中的值与该行的<code> RowSet </code>对象的原始值不同,这意味着数据源中的行自上次同步后已更新或删除。
    * 
    */
    public static int INSERT_ROW_CONFLICT = 2;

    /**
     * Indicates that <b>no</b> conflict occurred while the <code>RowSet</code> object
     * was attempting to update, delete or insert a row in the data source. The values in
     * the <code>SyncResolver</code> will contain <code>null</code> values only as an indication
     * that no information in pertinent to the conflict resolution in this row.
     * <p>
     *  表示当<code> RowSet </code>对象尝试向数据源插入行时发生冲突。这意味着自上次同步以来,与要插入的行具有相同主键的行已插入数据源。
     * 
     */
    public static int NO_ROW_CONFLICT = 3;

    /**
     * Retrieves the conflict status of the current row of this <code>SyncResolver</code>,
     * which indicates the operation
     * the <code>RowSet</code> object was attempting when the conflict occurred.
     *
     * <p>
     *  表示<code> RowSet </code>对象尝试更新,删除或插入数据源中的行时发生<b>无</b>冲突。
     *  <code> SyncResolver </code>中的值将包含<code> null </code>值,仅作为指示没有与此行中的冲突解决相关的信息。
     * 
     * 
     * @return one of the following constants:
     *         <code>SyncResolver.UPDATE_ROW_CONFLICT</code>,
     *         <code>SyncResolver.DELETE_ROW_CONFLICT</code>,
     *         <code>SyncResolver.INSERT_ROW_CONFLICT</code>, or
     *         <code>SyncResolver.NO_ROW_CONFLICT</code>
     */
    public int getStatus();

    /**
     * Retrieves the value in the designated column in the current row of this
     * <code>SyncResolver</code> object, which is the value in the data source
     * that caused a conflict.
     *
     * <p>
     *  检索此<code> SyncResolver </code>当前行的冲突状态,该冲突状态指示发生冲突时<code> RowSet </code>对象尝试的操作。
     * 
     * 
     * @param index an <code>int</code> designating the column in this row of this
     *        <code>SyncResolver</code> object from which to retrieve the value
     *        causing a conflict
     * @return the value of the designated column in the current row of this
     *         <code>SyncResolver</code> object
     * @throws SQLException if a database access error occurs
     */
    public Object getConflictValue(int index) throws SQLException;

    /**
     * Retrieves the value in the designated column in the current row of this
     * <code>SyncResolver</code> object, which is the value in the data source
     * that caused a conflict.
     *
     * <p>
     * 检索此<Sync> SyncResolver </code>对象的当前行中指定列中的值,这是导致冲突的数据源中的值。
     * 
     * 
     * @param columnName a <code>String</code> object designating the column in this row of this
     *        <code>SyncResolver</code> object from which to retrieve the value
     *        causing a conflict
     * @return the value of the designated column in the current row of this
     *         <code>SyncResolver</code> object
     * @throws SQLException if a database access error occurs
     */
    public Object getConflictValue(String columnName) throws SQLException;

    /**
     * Sets <i>obj</i> as the value in column <i>index</i> in the current row of the
     * <code>RowSet</code> object that is being synchronized. <i>obj</i>
     * is set as the value in the data source internally.
     *
     * <p>
     *  检索此<Sync> SyncResolver </code>对象的当前行中指定列中的值,这是导致冲突的数据源中的值。
     * 
     * 
     * @param index an <code>int</code> giving the number of the column into which to
     *        set the value to be persisted
     * @param obj an <code>Object</code> that is the value to be set in the
     *        <code>RowSet</code> object and persisted in the data source
     * @throws SQLException if a database access error occurs
     */
    public void setResolvedValue(int index, Object obj) throws SQLException;

    /**
     * Sets <i>obj</i> as the value in column <i>columnName</i> in the current row of the
     * <code>RowSet</code> object that is being synchronized. <i>obj</i>
     * is set as the value in the data source internally.
     *
     * <p>
     *  将<i> obj </i>设置为正在同步的<code> RowSet </code>对象的当前行中的<i> index </i>列中的值。 <i> obj </i>在内部设置为数据源中的值。
     * 
     * 
     * @param columnName a <code>String</code> object giving the name of the column
     *        into which to set the value to be persisted
     * @param obj an <code>Object</code> that is the value to be set in the
     *        <code>RowSet</code> object and persisted in the data source
     * @throws SQLException if a database access error occurs
     */
    public void setResolvedValue(String columnName, Object obj) throws SQLException;

    /**
     * Moves the cursor down from its current position to the next row that contains
     * a conflict value. A <code>SyncResolver</code> object's
     * cursor is initially positioned before the first conflict row; the first call to the
     * method <code>nextConflict</code> makes the first conflict row the current row;
     * the second call makes the second conflict row the current row, and so on.
     * <p>
     * A call to the method <code>nextConflict</code> will implicitly close
     * an input stream if one is open and will clear the <code>SyncResolver</code>
     * object's warning chain.
     *
     * <p>
     *  将<i> obj </i>设置为正在同步的<code> RowSet </code>对象的当前行中的列<i> columnName </i>中的值。
     *  <i> obj </i>在内部设置为数据源中的值。
     * 
     * 
     * @return <code>true</code> if the new current row is valid; <code>false</code>
     *         if there are no more rows
     * @throws SQLException if a database access error occurs or the result set type
     *     is <code>TYPE_FORWARD_ONLY</code>
     *
     */
    public boolean nextConflict() throws SQLException;

    /**
     * Moves the cursor up from its current position to the previous conflict
     * row in this <code>SyncResolver</code> object.
     * <p>
     * A call to the method <code>previousConflict</code> will implicitly close
     * an input stream if one is open and will clear the <code>SyncResolver</code>
     * object's warning chain.
     *
     * <p>
     *  将光标从当前位置向下移动到包含冲突值的下一行。
     *  <code> SyncResolver </code>对象的游标最初位于第一个冲突行之前;第一次调用方法<code> nextConflict </code>使第一个冲突行成为当前行;第二个调用使第二
     * 个冲突行成为当前行,依此类推。
     *  将光标从当前位置向下移动到包含冲突值的下一行。
     * <p>
     *  对方法<code> nextConflict </code>的调用将隐式关闭输入流(如果已打开),并将清除<code> SyncResolver </code>对象的警告链。
     * 
     * @return <code>true</code> if the cursor is on a valid row; <code>false</code>
     *     if it is off the result set
     * @throws SQLException if a database access error occurs or the result set type
     *     is <code>TYPE_FORWARD_ONLY</code>
     */
    public boolean previousConflict() throws SQLException;

}
