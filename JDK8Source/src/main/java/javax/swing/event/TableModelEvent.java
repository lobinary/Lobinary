/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

import java.util.EventObject;
import javax.swing.table.*;

/**
 * TableModelEvent is used to notify listeners that a table model
 * has changed. The model event describes changes to a TableModel
 * and all references to rows and columns are in the co-ordinate
 * system of the model.
 * Depending on the parameters used in the constructors, the TableModelevent
 * can be used to specify the following types of changes:
 *
 * <pre>
 * TableModelEvent(source);              //  The data, ie. all rows changed
 * TableModelEvent(source, HEADER_ROW);  //  Structure change, reallocate TableColumns
 * TableModelEvent(source, 1);           //  Row 1 changed
 * TableModelEvent(source, 3, 6);        //  Rows 3 to 6 inclusive changed
 * TableModelEvent(source, 2, 2, 6);     //  Cell at (2, 6) changed
 * TableModelEvent(source, 3, 6, ALL_COLUMNS, INSERT); // Rows (3, 6) were inserted
 * TableModelEvent(source, 3, 6, ALL_COLUMNS, DELETE); // Rows (3, 6) were deleted
 * </pre>
 *
 * It is possible to use other combinations of the parameters, not all of them
 * are meaningful. By subclassing, you can add other information, for example:
 * whether the event WILL happen or DID happen. This makes the specification
 * of rows in DELETE events more useful but has not been included in
 * the swing package as the JTable only needs post-event notification.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  TableModelEvent用于通知侦听器表模型已更改。模型事件描述对TableModel的更改,并且对行和列的所有引用都在模型的坐标系中。
 * 根据构造函数中使用的参数,TableModelevent可用于指定以下类型的更改：。
 * 
 * <pre>
 *  TableModelEvent(source); //数据,即。
 * 所有行更改了TableModelEvent(source,HEADER_ROW); // Structure change,reallocate TableColumns TableModelEvent
 * (source,1); // Row 1 changed TableModelEvent(source,3,6); // Rows 3 to 6 inclusive change TableModelE
 * vent(source,2,2,6); // Cell at(2,6)changed TableModelEvent(source,3,6,ALL_COLUMNS,INSERT); // Rows(3,
 * 6)inserted TableModelEvent(source,3,6,ALL_COLUMNS,DELETE); // Rows(3,6)were deleted。
 *  TableModelEvent(source); //数据,即。
 * </pre>
 * 
 *  可以使用参数的其他组合,不是所有参数都是有意义的。通过子类化,您可以添加其他信息,例如：事件是否发生或DID发生。
 * 这使得在DELETE事件中的行的规范更有用,但是没有包括在swing包中,因为JTable只需要事件后通知。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Alan Chung
 * @author Philip Milne
 * @see TableModel
 */
public class TableModelEvent extends java.util.EventObject
{
    /** Identifies the addition of new rows or columns. */
    public static final int INSERT =  1;
    /** Identifies a change to existing data. */
    public static final int UPDATE =  0;
    /** Identifies the removal of rows or columns. */
    public static final int DELETE = -1;

    /** Identifies the header row. */
    public static final int HEADER_ROW = -1;

    /** Specifies all columns in a row or rows. */
    public static final int ALL_COLUMNS = -1;

//
//  Instance Variables
//

    protected int       type;
    protected int       firstRow;
    protected int       lastRow;
    protected int       column;

//
// Constructors
//

    /**
     *  All row data in the table has changed, listeners should discard any state
     *  that was based on the rows and requery the <code>TableModel</code>
     *  to get the new row count and all the appropriate values.
     *  The <code>JTable</code> will repaint the entire visible region on
     *  receiving this event, querying the model for the cell values that are visible.
     *  The structure of the table ie, the column names, types and order
     *  have not changed.
     * <p>
     *  表中的所有行数据都已更改,侦听器应舍弃基于行的任何状态,并重新请求<code> TableModel </code>以获取新的行计数和所有适当的值。
     * 在接收到此事件时,<code> JTable </code>将重绘整个可见区域,查询模型中可见的单元格值。表的结构即列名,类型和顺序没有改变。
     * 
     */
    public TableModelEvent(TableModel source) {
        // Use Integer.MAX_VALUE instead of getRowCount() in case rows were deleted.
        this(source, 0, Integer.MAX_VALUE, ALL_COLUMNS, UPDATE);
    }

    /**
     *  This row of data has been updated.
     *  To denote the arrival of a completely new table with a different structure
     *  use <code>HEADER_ROW</code> as the value for the <code>row</code>.
     *  When the <code>JTable</code> receives this event and its
     *  <code>autoCreateColumnsFromModel</code>
     *  flag is set it discards any TableColumns that it had and reallocates
     *  default ones in the order they appear in the model. This is the
     *  same as calling <code>setModel(TableModel)</code> on the <code>JTable</code>.
     * <p>
     *  此行数据已更新。为了表示具有不同结构的全新表的到达,使用<code> HEADER_ROW </code>作为<code> row </code>的值。
     * 当<code> JTable </code>收到此事件并设置其<code> autoCreateColumnsFromModel </code>标志时,它将丢弃它所拥有的任何TableColumns,并
     * 按它们在模型中显示的顺序重新分配默认值。
     *  此行数据已更新。为了表示具有不同结构的全新表的到达,使用<code> HEADER_ROW </code>作为<code> row </code>的值。
     * 这与在<code> JTable </code>上调用<code> setModel(TableModel)</code>相同。
     * 
     */
    public TableModelEvent(TableModel source, int row) {
        this(source, row, row, ALL_COLUMNS, UPDATE);
    }

    /**
     *  The data in rows [<I>firstRow</I>, <I>lastRow</I>] have been updated.
     * <p>
     *  行[<I> firstRow </I>,<I> lastRow </I>]中的数据已更新。
     * 
     */
    public TableModelEvent(TableModel source, int firstRow, int lastRow) {
        this(source, firstRow, lastRow, ALL_COLUMNS, UPDATE);
    }

    /**
     *  The cells in column <I>column</I> in the range
     *  [<I>firstRow</I>, <I>lastRow</I>] have been updated.
     * <p>
     *  已经更新在范围[<firstRow </I>,<I> lastRow </i>]中的第<I>列</I>中的单元格。
     * 
     */
    public TableModelEvent(TableModel source, int firstRow, int lastRow, int column) {
        this(source, firstRow, lastRow, column, UPDATE);
    }

    /**
     *  The cells from (firstRow, column) to (lastRow, column) have been changed.
     *  The <I>column</I> refers to the column index of the cell in the model's
     *  co-ordinate system. When <I>column</I> is ALL_COLUMNS, all cells in the
     *  specified range of rows are considered changed.
     *  <p>
     *  The <I>type</I> should be one of: INSERT, UPDATE and DELETE.
     * <p>
     * 从(firstRow,column)到(lastRow,column)的单元格已更改。 <I>列</I>是指模型坐标系中单元格的列索引。
     * 当<I>列</I>为ALL_COLUMNS时,指定行范围内的所有单元格都将被视为已更改。
     * <p>
     *  <I>类型</I>应该是以下之一：INSERT,UPDATE和DELETE。
     * 
     */
    public TableModelEvent(TableModel source, int firstRow, int lastRow, int column, int type) {
        super(source);
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.column = column;
        this.type = type;
    }

//
// Querying Methods
//

   /** Returns the first row that changed.  HEADER_ROW means the meta data,
     * ie. names, types and order of the columns.
     * <p>
     *  即。名称,类型和列的顺序。
     * 
     */
    public int getFirstRow() { return firstRow; };

    /** Returns the last row that changed. */
    public int getLastRow() { return lastRow; };

    /**
     *  Returns the column for the event.  If the return
     *  value is ALL_COLUMNS; it means every column in the specified
     *  rows changed.
     * <p>
     *  返回事件的列。如果返回值为ALL_COLUMNS;它意味着指定行中的每一列都更改。
     * 
     */
    public int getColumn() { return column; };

    /**
     *  Returns the type of event - one of: INSERT, UPDATE and DELETE.
     * <p>
     *  返回事件的类型 - 以下之一：INSERT,UPDATE和DELETE。
     */
    public int getType() { return type; }
}
