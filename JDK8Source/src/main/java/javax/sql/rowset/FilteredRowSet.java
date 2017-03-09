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

/**
 * The standard interface that all standard implementations of
 * <code>FilteredRowSet</code> must implement. The <code>FilteredRowSetImpl</code> class
 * provides the reference implementation which may be extended if required.
 * Alternatively, a vendor is free to implement its own version
 * by implementing this interface.
 *
 * <h3>1.0 Background</h3>
 *
 * There are occasions when a <code>RowSet</code> object has a need to provide a degree
 * of filtering to its contents. One possible solution is to provide
 * a query language for all standard <code>RowSet</code> implementations; however,
 * this is an impractical approach for lightweight components such as disconnected
 * <code>RowSet</code>
 * objects. The <code>FilteredRowSet</code> interface seeks to address this need
 * without supplying a heavyweight query language along with the processing that
 * such a query language would require.
 * <p>
 * A JDBC <code>FilteredRowSet</code> standard implementation implements the
 * <code>RowSet</code> interfaces and extends the
 * <code>CachedRowSet</code>&trade; class. The
 * <code>CachedRowSet</code> class provides a set of protected cursor manipulation
 * methods, which a <code>FilteredRowSet</code> implementation can override
 * to supply filtering support.
 *
 * <h3>2.0 Predicate Sharing</h3>
 *
 * If a <code>FilteredRowSet</code> implementation is shared using the
 * inherited <code>createShared</code> method in parent interfaces, the
 * <code>Predicate</code> should be shared without modification by all
 * <code>FilteredRowSet</code> instance clones.
 *
 * <h3>3.0 Usage</h3>
 * <p>
 * By implementing a <code>Predicate</code> (see example in <a href="Predicate.html">Predicate</a>
 * class JavaDoc), a <code>FilteredRowSet</code> could then be used as described
 * below.
 *
 * <pre>
 * {@code
 *     FilteredRowSet frs = new FilteredRowSetImpl();
 *     frs.populate(rs);
 *
 *     Range name = new Range("Alpha", "Bravo", "columnName");
 *     frs.setFilter(name);
 *
 *     frs.next() // only names from "Alpha" to "Bravo" will be returned
 * }
 * </pre>
 * In the example above, we initialize a <code>Range</code> object which
 * implements the <code>Predicate</code> interface. This object expresses
 * the following constraints: All rows outputted or modified from this
 * <code>FilteredRowSet</code> object must fall between the values 'Alpha' and
 * 'Bravo' both values inclusive, in the column 'columnName'. If a filter is
 * applied to a <code>FilteredRowSet</code> object that contains no data that
 * falls within the range of the filter, no rows are returned.
 * <p>
 * This framework allows multiple classes implementing predicates to be
 * used in combination to achieved the required filtering result with
 * out the need for query language processing.
 *
 * <h3>4.0 Updating a <code>FilteredRowSet</code> Object</h3>
 * The predicate set on a <code>FilteredRowSet</code> object
 * applies a criterion on all rows in a
 * <code>RowSet</code> object to manage a subset of rows in a <code>RowSet</code>
 * object. This criterion governs the subset of rows that are visible and also
 * defines which rows can be modified, deleted or inserted.
 * <p>
 * Therefore, the predicate set on a <code>FilteredRowSet</code> object must be
 * considered as bi-directional and the set criterion as the gating mechanism
 * for all views and updates to the <code>FilteredRowSet</code> object. Any attempt
 * to update the <code>FilteredRowSet</code> that violates the criterion will
 * result in a <code>SQLException</code> object being thrown.
 * <p>
 * The <code>FilteredRowSet</code> range criterion can be modified by applying
 * a new <code>Predicate</code> object to the <code>FilteredRowSet</code>
 * instance at any time. This is  possible if no additional references to the
 * <code>FilteredRowSet</code> object are detected. A new filter has has an
 * immediate effect on criterion enforcement within the
 * <code>FilteredRowSet</code> object, and all subsequent views and updates will be
 * subject to similar enforcement.
 *
 * <h3>5.0 Behavior of Rows Outside the Filter</h3>
 * Rows that fall outside of the filter set on a <code>FilteredRowSet</code>
 * object cannot be modified until the filter is removed or a
 * new filter is applied.
 * <p>
 * Furthermore, only rows that fall within the bounds of a filter will be
 * synchronized with the data source.
 *
 * <p>
 *  所有标准实现的<code> FilteredRowSet </code>必须实现的标准接口。 <code> FilteredRowSetImpl </code>类提供了参考实现,如果需要可以扩展。
 * 或者,供应商可以通过实现该接口自由地实现其自己的版本。
 * 
 *  <h3> 1.0背景</h3>
 * 
 *  有时候,一个<code> RowSet </code>对象需要对其内容提供一定程度的过滤。
 * 一种可能的解决方案是为所有标准<code> RowSet </code>实现提供查询语言;然而,对于轻量级组件,例如断开的<code> RowSet </code>对象,这是一种不切实际的方法。
 *  <code> FilteredRowSet </code>接口寻求解决这一需求,而不提供重量级查询语言以及这样的查询语言将需要的处理。
 * <p>
 *  JDBC <code> FilteredRowSet </code>标准实现实现<code> RowSet </code>接口并扩展<code> CachedRowSet </code>&trade;
 * 类。
 *  <code> CachedRowSet </code>类提供了一组受保护的游标操纵方法,<code> FilteredRowSet </code>实现可以覆盖以提供过滤支持。
 * 
 *  <h3> 2.0谓词共享</h3>
 * 
 * 如果在父接口中使用继承的<code> createShared </code>方法共享<code> FilteredRowSet </code>实现,那么<code> Predicate </code>
 * 应该被所有<code> FilteredRowSet < / code>实例克隆。
 * 
 *  <h3> 3.0使用</h3>
 * <p>
 *  通过实施<code>谓词</code>(参见<a href="Predicate.html">谓词</a>类JavaDoc中的示例),然后可以如下所述使用<code> FilteredRowSet </code>
 *  。
 * 
 * <pre>
 *  {@code FilteredRowSet frs = new FilteredRowSetImpl(); frs.populate(rs);
 * 
 *  范围名称=新范围("Alpha","Bravo","columnName"); frs.setFilter(name);
 * 
 *  frs.next()//只返回从"Alpha"到"Bravo"的名称}
 * </pre>
 *  在上面的例子中,我们初始化了一个实现<code> Predicate </code>接口的<code> Range </code>对象。
 * 此对象表示以下约束：从此<code> FilteredRowSet </code>对象输出或修改的所有行必须在列"columnName"中的两个值之间的值'Alpha'和'Bravo'之间。
 * 
 * @author Jonathan Bruce
 */

public interface FilteredRowSet extends WebRowSet {

   /**
    * Applies the given <code>Predicate</code> object to this
    * <code>FilteredRowSet</code>
    * object. The filter applies controls both to inbound and outbound views,
    * constraining which rows are visible and which
    * rows can be manipulated.
    * <p>
    * A new <code>Predicate</code> object may be set at any time. This has the
    * effect of changing constraints on the <code>RowSet</code> object's data.
    * In addition, modifying the filter at runtime presents issues whereby
    * multiple components may be operating on one <code>FilteredRowSet</code> object.
    * Application developers must take responsibility for managing multiple handles
    * to <code>FilteredRowSet</code> objects when their underling <code>Predicate</code>
    * objects change.
    *
    * <p>
    * 如果将过滤器应用于不包含落入过滤器范围内的数据的<code> FilteredRowSet </code>对象,则不返回任何行。
    * <p>
    *  此框架允许组合使用实现谓词的多个类来实现所需的过滤结果,而不需要查询语言处理。
    * 
    * <h3> 4.0更新<code> FilteredRowSet </code>对象</h3>在<code> FilteredRowSet </code>对象上设置的谓词对<code> RowSet </code>
    * 对象以管理<code> RowSet </code>对象中的行的子集。
    * 此标准管理可见的行的子集,并且还定义可以修改,删除或插入哪些行。
    * <p>
    *  因此,在<code> FilteredRowSet </code>对象上设置的谓词必须被认为是双向的,并且设置标准作为对<code> FilteredRowSet </code>对象的所有视图和更新的
    * 门控机制。
    * 任何更新违反标准的<code> FilteredRowSet </code>的尝试都会导致抛出<code> SQLException </code>对象。
    * <p>
    *  通过在任何时候将<code> Predicate </code>对象应用于<code> FilteredRowSet </code>实例,可以修改<code> FilteredRowSet </code>
    * 范围标准。
    * 如果没有检测到对<code> FilteredRowSet </code>对象的额外引用,这是可能的。
    * 新的过滤器对<code> FilteredRowSet </code>对象内的标准执行有即时影响,并且所有后续视图和更新将受到类似的强制执行。
    * 
    * @param p a <code>Predicate</code> object defining the filter for this
    * <code>FilteredRowSet</code> object. Setting a <b>null</b> value
    * will clear the predicate, allowing all rows to become visible.
    *
    * @throws SQLException if an error occurs when setting the
    *     <code>Predicate</code> object
    */
    public void setFilter(Predicate p) throws SQLException;

   /**
    * Retrieves the active filter for this <code>FilteredRowSet</code> object.
    *
    * <p>
    * 
    *  <h3> 5.0过滤器外的行的行为</h3>在除去过滤器或应用新过滤器之前,不能修改落在<code> FilteredRowSet </code>对象上的过滤器集之外的行。
    * <p>
    * 此外,只有落在过滤器边界内的行将与数据源同步。
    * 
    * 
    * @return p the <code>Predicate</code> for this <code>FilteredRowSet</code>
    * object; <code>null</code> if no filter has been set.
    */
    public Predicate getFilter() ;
}
