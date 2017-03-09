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

package javax.swing.table;

import java.io.Serializable;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.event.TableModelEvent;


/**
 * This is an implementation of <code>TableModel</code> that
 * uses a <code>Vector</code> of <code>Vectors</code> to store the
 * cell value objects.
 * <p>
 * <strong>Warning:</strong> <code>DefaultTableModel</code> returns a
 * column class of <code>Object</code>.  When
 * <code>DefaultTableModel</code> is used with a
 * <code>TableRowSorter</code> this will result in extensive use of
 * <code>toString</code>, which for non-<code>String</code> data types
 * is expensive.  If you use <code>DefaultTableModel</code> with a
 * <code>TableRowSorter</code> you are strongly encouraged to override
 * <code>getColumnClass</code> to return the appropriate type.
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
 *  这是使用<code> Vectors </code>的<code> Vector </code>来存储单元格值对象的<code> TableModel </code>的实现。
 * <p>
 *  <strong>警告：</strong> <code> DefaultTableModel </code>返回<code> Object </code>的列类。
 * 当<code> DefaultTableModel </code>与<code> TableRowSorter </code>配合使用时,会导致大量使用<code> toString </code>,对
 * 于<code> String </code>类型是昂贵的。
 *  <strong>警告：</strong> <code> DefaultTableModel </code>返回<code> Object </code>的列类。
 * 如果您对<code> TableRowSorter </code>使用<code> DefaultTableModel </code>,强烈建议您覆盖<code> getColumnClass </code>
 * 以返回相应的类型。
 *  <strong>警告：</strong> <code> DefaultTableModel </code>返回<code> Object </code>的列类。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Philip Milne
 *
 * @see TableModel
 * @see #getDataVector
 */
public class DefaultTableModel extends AbstractTableModel implements Serializable {

//
// Instance Variables
//

    /**
     * The <code>Vector</code> of <code>Vectors</code> of
     * <code>Object</code> values.
     * <p>
     *  值</code>的<code>向量</code>的<code>向量</code>。
     * 
     */
    protected Vector    dataVector;

    /** The <code>Vector</code> of column identifiers. */
    protected Vector    columnIdentifiers;

//
// Constructors
//

    /**
     *  Constructs a default <code>DefaultTableModel</code>
     *  which is a table of zero columns and zero rows.
     * <p>
     *  构造一个默认的<code> DefaultTableModel </code>,它是一个零列和零行的表。
     * 
     */
    public DefaultTableModel() {
        this(0, 0);
    }

    private static Vector newVector(int size) {
        Vector v = new Vector(size);
        v.setSize(size);
        return v;
    }

    /**
     *  Constructs a <code>DefaultTableModel</code> with
     *  <code>rowCount</code> and <code>columnCount</code> of
     *  <code>null</code> object values.
     *
     * <p>
     *  使用<code> rowCount </code>和<code> columnCount </code> of <code> null </code>对象值构造<code> DefaultTableM
     * odel </code>。
     * 
     * 
     * @param rowCount           the number of rows the table holds
     * @param columnCount        the number of columns the table holds
     *
     * @see #setValueAt
     */
    public DefaultTableModel(int rowCount, int columnCount) {
        this(newVector(columnCount), rowCount);
    }

    /**
     *  Constructs a <code>DefaultTableModel</code> with as many columns
     *  as there are elements in <code>columnNames</code>
     *  and <code>rowCount</code> of <code>null</code>
     *  object values.  Each column's name will be taken from
     *  the <code>columnNames</code> vector.
     *
     * <p>
     * 构造具有与<code> columnNames </code>和<code> rowCount </code>(<code> null </code>对象值)中的元素一样多的列的<code> Defau
     * ltTableModel </code>。
     * 每个列的名称将从<code> columnNames </code>向量中获取。
     * 
     * 
     * @param columnNames       <code>vector</code> containing the names
     *                          of the new columns; if this is
     *                          <code>null</code> then the model has no columns
     * @param rowCount           the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public DefaultTableModel(Vector columnNames, int rowCount) {
        setDataVector(newVector(rowCount), columnNames);
    }

    /**
     *  Constructs a <code>DefaultTableModel</code> with as many
     *  columns as there are elements in <code>columnNames</code>
     *  and <code>rowCount</code> of <code>null</code>
     *  object values.  Each column's name will be taken from
     *  the <code>columnNames</code> array.
     *
     * <p>
     *  构造具有与<code> columnNames </code>和<code> rowCount </code>(<code> null </code>对象值)中的元素一样多的列的<code> Defa
     * ultTableModel </code>。
     * 每个列的名称将取自<code> columnNames </code>数组。
     * 
     * 
     * @param columnNames       <code>array</code> containing the names
     *                          of the new columns; if this is
     *                          <code>null</code> then the model has no columns
     * @param rowCount           the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public DefaultTableModel(Object[] columnNames, int rowCount) {
        this(convertToVector(columnNames), rowCount);
    }

    /**
     *  Constructs a <code>DefaultTableModel</code> and initializes the table
     *  by passing <code>data</code> and <code>columnNames</code>
     *  to the <code>setDataVector</code> method.
     *
     * <p>
     *  构造一个<code> DefaultTableModel </code>并通过将<code> data </code>和<code> columnNames </code>传递给<code> setD
     * ataVector </code>方法来初始化表。
     * 
     * 
     * @param data              the data of the table, a <code>Vector</code>
     *                          of <code>Vector</code>s of <code>Object</code>
     *                          values
     * @param columnNames       <code>vector</code> containing the names
     *                          of the new columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public DefaultTableModel(Vector data, Vector columnNames) {
        setDataVector(data, columnNames);
    }

    /**
     *  Constructs a <code>DefaultTableModel</code> and initializes the table
     *  by passing <code>data</code> and <code>columnNames</code>
     *  to the <code>setDataVector</code>
     *  method. The first index in the <code>Object[][]</code> array is
     *  the row index and the second is the column index.
     *
     * <p>
     *  构造一个<code> DefaultTableModel </code>并通过将<code> data </code>和<code> columnNames </code>传递给<code> setD
     * ataVector </code>方法来初始化表。
     *  <code> Object [] [] </code>数组中的第一个索引是行索引,第二个是列索引。
     * 
     * 
     * @param data              the data of the table
     * @param columnNames       the names of the columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public DefaultTableModel(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
    }

    /**
     *  Returns the <code>Vector</code> of <code>Vectors</code>
     *  that contains the table's
     *  data values.  The vectors contained in the outer vector are
     *  each a single row of values.  In other words, to get to the cell
     *  at row 1, column 5: <p>
     *
     *  <code>((Vector)getDataVector().elementAt(1)).elementAt(5);</code>
     *
     * <p>
     *  返回包含表的数据值的<code> Vectors </code>的<code> Vector </code>。包含在外向量中的向量各自是单行值。换句话说,要到达第1行第5列的单元格：<p>
     * 
     *  <code>((Vector)getDataVector()。elementAt(1))。elementAt(5); </code>
     * 
     * 
     * @return  the vector of vectors containing the tables data values
     *
     * @see #newDataAvailable
     * @see #newRowsAdded
     * @see #setDataVector
     */
    public Vector getDataVector() {
        return dataVector;
    }

    private static Vector nonNullVector(Vector v) {
        return (v != null) ? v : new Vector();
    }

    /**
     *  Replaces the current <code>dataVector</code> instance variable
     *  with the new <code>Vector</code> of rows, <code>dataVector</code>.
     *  Each row is represented in <code>dataVector</code> as a
     *  <code>Vector</code> of <code>Object</code> values.
     *  <code>columnIdentifiers</code> are the names of the new
     *  columns.  The first name in <code>columnIdentifiers</code> is
     *  mapped to column 0 in <code>dataVector</code>. Each row in
     *  <code>dataVector</code> is adjusted to match the number of
     *  columns in <code>columnIdentifiers</code>
     *  either by truncating the <code>Vector</code> if it is too long,
     *  or adding <code>null</code> values if it is too short.
     *  <p>Note that passing in a <code>null</code> value for
     *  <code>dataVector</code> results in unspecified behavior,
     *  an possibly an exception.
     *
     * <p>
     * 将当前<code> dataVector </code>实例变量替换为新的<code> Vector </code>行<code> dataVector </code>。
     * 每行在<code> dataVector </code>中表示为<code> Object </code>值的<code>向量</code>。
     *  <code> columnIdentifiers </code>是新列的名称。
     *  <code> columnIdentifiers </code>中的第一个名称映射到<code> dataVector </code>中的第0列。
     *  <code> dataVector </code>中的每一行都被调整为与<code> columnIdentifiers </code>中的列数匹配,方法是通过截断<code> Vector </code>
     * (如果太长)或添加<code > null </code>值,如果它太短。
     *  <code> columnIdentifiers </code>中的第一个名称映射到<code> dataVector </code>中的第0列。
     *  <p>请注意,传递<code> dataVector </code>的<code> null </code>值会导致未指定的行为,可能是一个异常。
     * 
     * 
     * @param   dataVector         the new data vector
     * @param   columnIdentifiers     the names of the columns
     * @see #getDataVector
     */
    public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
        this.dataVector = nonNullVector(dataVector);
        this.columnIdentifiers = nonNullVector(columnIdentifiers);
        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    /**
     *  Replaces the value in the <code>dataVector</code> instance
     *  variable with the values in the array <code>dataVector</code>.
     *  The first index in the <code>Object[][]</code>
     *  array is the row index and the second is the column index.
     *  <code>columnIdentifiers</code> are the names of the new columns.
     *
     * <p>
     *  将<code> dataVector </code>实例变量中的值替换为数组<code> dataVector </code>中的值。
     *  <code> Object [] [] </code>数组中的第一个索引是行索引,第二个是列索引。 <code> columnIdentifiers </code>是新列的名称。
     * 
     * 
     * @param dataVector                the new data vector
     * @param columnIdentifiers the names of the columns
     * @see #setDataVector(Vector, Vector)
     */
    public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
        setDataVector(convertToVector(dataVector), convertToVector(columnIdentifiers));
    }

    /**
     *  Equivalent to <code>fireTableChanged</code>.
     *
     * <p>
     *  等同于<code> fireTableChanged </code>。
     * 
     * 
     * @param event  the change event
     *
     */
    public void newDataAvailable(TableModelEvent event) {
        fireTableChanged(event);
    }

//
// Manipulating rows
//

    private void justifyRows(int from, int to) {
        // Sometimes the DefaultTableModel is subclassed
        // instead of the AbstractTableModel by mistake.
        // Set the number of rows for the case when getRowCount
        // is overridden.
        dataVector.setSize(getRowCount());

        for (int i = from; i < to; i++) {
            if (dataVector.elementAt(i) == null) {
                dataVector.setElementAt(new Vector(), i);
            }
            ((Vector)dataVector.elementAt(i)).setSize(getColumnCount());
        }
    }

    /**
     *  Ensures that the new rows have the correct number of columns.
     *  This is accomplished by  using the <code>setSize</code> method in
     *  <code>Vector</code> which truncates vectors
     *  which are too long, and appends <code>null</code>s if they
     *  are too short.
     *  This method also sends out a <code>tableChanged</code>
     *  notification message to all the listeners.
     *
     * <p>
     *  确保新行具有正确的列数。
     * 这是通过使用<code> Vector </code>中的<code> setSize </code>方法实现的,该方法截断太长的向量,并且如果它们太短,则追加<code> null </code>。
     * 此方法还会向所有侦听器发送一个<code> tableChanged </code>通知消息。
     * 
     * 
     * @param e         this <code>TableModelEvent</code> describes
     *                           where the rows were added.
     *                           If <code>null</code> it assumes
     *                           all the rows were newly added
     * @see #getDataVector
     */
    public void newRowsAdded(TableModelEvent e) {
        justifyRows(e.getFirstRow(), e.getLastRow() + 1);
        fireTableChanged(e);
    }

    /**
     *  Equivalent to <code>fireTableChanged</code>.
     *
     * <p>
     * 等同于<code> fireTableChanged </code>。
     * 
     * 
     *  @param event the change event
     *
     */
    public void rowsRemoved(TableModelEvent event) {
        fireTableChanged(event);
    }

    /**
     * Obsolete as of Java 2 platform v1.3.  Please use <code>setRowCount</code> instead.
     * <p>
     *  作为Java 2平台v1.3的已过时。请改用<code> setRowCount </code>。
     * 
     */
    /*
     *  Sets the number of rows in the model.  If the new size is greater
     *  than the current size, new rows are added to the end of the model
     *  If the new size is less than the current size, all
     *  rows at index <code>rowCount</code> and greater are discarded.
     *
     * <p>
     *  在模型中设置行数。如果新大小大于当前大小,则将新行添加到模型的末尾如果新大小小于当前大小,则索引<code> rowCount </code>和更大值处的所有行将被丢弃。
     * 
     * 
     * @param   rowCount   the new number of rows
     * @see #setRowCount
     */
    public void setNumRows(int rowCount) {
        int old = getRowCount();
        if (old == rowCount) {
            return;
        }
        dataVector.setSize(rowCount);
        if (rowCount <= old) {
            fireTableRowsDeleted(rowCount, old-1);
        }
        else {
            justifyRows(old, rowCount);
            fireTableRowsInserted(old, rowCount-1);
        }
    }

    /**
     *  Sets the number of rows in the model.  If the new size is greater
     *  than the current size, new rows are added to the end of the model
     *  If the new size is less than the current size, all
     *  rows at index <code>rowCount</code> and greater are discarded.
     *
     * <p>
     *  设置模型中的行数。如果新大小大于当前大小,则将新行添加到模型的末尾如果新大小小于当前大小,则索引<code> rowCount </code>和更大值处的所有行将被丢弃。
     * 
     * 
     *  @see #setColumnCount
     * @since 1.3
     */
    public void setRowCount(int rowCount) {
        setNumRows(rowCount);
    }

    /**
     *  Adds a row to the end of the model.  The new row will contain
     *  <code>null</code> values unless <code>rowData</code> is specified.
     *  Notification of the row being added will be generated.
     *
     * <p>
     *  在模型的末尾添加一行。除非指定<code> rowData </code>,否则新行将包含<code> null </code>值。将生成要添加的行的通知。
     * 
     * 
     * @param   rowData          optional data of the row being added
     */
    public void addRow(Vector rowData) {
        insertRow(getRowCount(), rowData);
    }

    /**
     *  Adds a row to the end of the model.  The new row will contain
     *  <code>null</code> values unless <code>rowData</code> is specified.
     *  Notification of the row being added will be generated.
     *
     * <p>
     *  在模型的末尾添加一行。除非指定<code> rowData </code>,否则新行将包含<code> null </code>值。将生成要添加的行的通知。
     * 
     * 
     * @param   rowData          optional data of the row being added
     */
    public void addRow(Object[] rowData) {
        addRow(convertToVector(rowData));
    }

    /**
     *  Inserts a row at <code>row</code> in the model.  The new row
     *  will contain <code>null</code> values unless <code>rowData</code>
     *  is specified.  Notification of the row being added will be generated.
     *
     * <p>
     *  在模型中的<code> row </code>插入一行。除非指定<code> rowData </code>,否则新行将包含<code> null </code>值。将生成要添加的行的通知。
     * 
     * 
     * @param   row             the row index of the row to be inserted
     * @param   rowData         optional data of the row being added
     * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
     */
    public void insertRow(int row, Vector rowData) {
        dataVector.insertElementAt(rowData, row);
        justifyRows(row, row+1);
        fireTableRowsInserted(row, row);
    }

    /**
     *  Inserts a row at <code>row</code> in the model.  The new row
     *  will contain <code>null</code> values unless <code>rowData</code>
     *  is specified.  Notification of the row being added will be generated.
     *
     * <p>
     *  在模型中的<code> row </code>插入一行。除非指定<code> rowData </code>,否则新行将包含<code> null </code>值。将生成要添加的行的通知。
     * 
     * 
     * @param   row      the row index of the row to be inserted
     * @param   rowData          optional data of the row being added
     * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
     */
    public void insertRow(int row, Object[] rowData) {
        insertRow(row, convertToVector(rowData));
    }

    private static int gcd(int i, int j) {
        return (j == 0) ? i : gcd(j, i%j);
    }

    private static void rotate(Vector v, int a, int b, int shift) {
        int size = b - a;
        int r = size - shift;
        int g = gcd(size, r);
        for(int i = 0; i < g; i++) {
            int to = i;
            Object tmp = v.elementAt(a + to);
            for(int from = (to + r) % size; from != i; from = (to + r) % size) {
                v.setElementAt(v.elementAt(a + from), a + to);
                to = from;
            }
            v.setElementAt(tmp, a + to);
        }
    }

    /**
     *  Moves one or more rows from the inclusive range <code>start</code> to
     *  <code>end</code> to the <code>to</code> position in the model.
     *  After the move, the row that was at index <code>start</code>
     *  will be at index <code>to</code>.
     *  This method will send a <code>tableChanged</code> notification
       message to all the listeners.
     *
     *  <pre>
     *  Examples of moves:
     *
     *  1. moveRow(1,3,5);
     *          a|B|C|D|e|f|g|h|i|j|k   - before
     *          a|e|f|g|h|B|C|D|i|j|k   - after
     *
     *  2. moveRow(6,7,1);
     *          a|b|c|d|e|f|G|H|i|j|k   - before
     *          a|G|H|b|c|d|e|f|i|j|k   - after
     *  </pre>
     *
     * <p>
     * 将一个或多个行从包含范围<code> start </code>移动到<code> end </code>到模型中的<code>到</code>位置。
     * 移动之后,位于索引<code> start </code>的行将位于索引<code>到</code>。此方法将向所有侦听器发送<code> tableChanged </code>通知消息。
     * 
     * <pre>
     *  移动示例：
     * 
     *  moveRow(1,3,5); a | B | C | D | e | f | g | h | i | j | k-之前a | e | f | g | h | B | C | D | i |
     * 
     *  moveRow(6,7,1); a | b | c | d | e | f | G | H | i | j | k-之前a | G | H | b | c | d | e | f |
     * </pre>
     * 
     * 
     * @param   start       the starting row index to be moved
     * @param   end         the ending row index to be moved
     * @param   to          the destination of the rows to be moved
     * @exception  ArrayIndexOutOfBoundsException  if any of the elements
     * would be moved out of the table's range
     *
     */
    public void moveRow(int start, int end, int to) {
        int shift = to - start;
        int first, last;
        if (shift < 0) {
            first = to;
            last = end;
        }
        else {
            first = start;
            last = to + end - start;
        }
        rotate(dataVector, first, last + 1, shift);

        fireTableRowsUpdated(first, last);
    }

    /**
     *  Removes the row at <code>row</code> from the model.  Notification
     *  of the row being removed will be sent to all the listeners.
     *
     * <p>
     *  从模型中删除<code> row </code>的行。要删除的行的通知将发送给所有侦听器。
     * 
     * 
     * @param   row      the row index of the row to be removed
     * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
     */
    public void removeRow(int row) {
        dataVector.removeElementAt(row);
        fireTableRowsDeleted(row, row);
    }

//
// Manipulating columns
//

    /**
     * Replaces the column identifiers in the model.  If the number of
     * <code>newIdentifier</code>s is greater than the current number
     * of columns, new columns are added to the end of each row in the model.
     * If the number of <code>newIdentifier</code>s is less than the current
     * number of columns, all the extra columns at the end of a row are
     * discarded.
     *
     * <p>
     *  替换模型中的列标识符。如果<code> newIdentifier </code> s的数量大于当前列数,则会将新列添加到模型中每行的末尾。
     * 如果<code> newIdentifier </code> s的数量小于当前列数,则行末尾的所有额外列都将被丢弃。
     * 
     * 
     * @param   columnIdentifiers  vector of column identifiers.  If
     *                          <code>null</code>, set the model
     *                          to zero columns
     * @see #setNumRows
     */
    public void setColumnIdentifiers(Vector columnIdentifiers) {
        setDataVector(dataVector, columnIdentifiers);
    }

    /**
     * Replaces the column identifiers in the model.  If the number of
     * <code>newIdentifier</code>s is greater than the current number
     * of columns, new columns are added to the end of each row in the model.
     * If the number of <code>newIdentifier</code>s is less than the current
     * number of columns, all the extra columns at the end of a row are
     * discarded.
     *
     * <p>
     *  替换模型中的列标识符。如果<code> newIdentifier </code> s的数量大于当前列数,则会将新列添加到模型中每行的末尾。
     * 如果<code> newIdentifier </code> s的数量小于当前列数,则行末尾的所有额外列都将被丢弃。
     * 
     * 
     * @param   newIdentifiers  array of column identifiers.
     *                          If <code>null</code>, set
     *                          the model to zero columns
     * @see #setNumRows
     */
    public void setColumnIdentifiers(Object[] newIdentifiers) {
        setColumnIdentifiers(convertToVector(newIdentifiers));
    }

    /**
     *  Sets the number of columns in the model.  If the new size is greater
     *  than the current size, new columns are added to the end of the model
     *  with <code>null</code> cell values.
     *  If the new size is less than the current size, all columns at index
     *  <code>columnCount</code> and greater are discarded.
     *
     * <p>
     * 设置模型中的列数。如果新大小大于当前大小,则使用<code> null </code>单元格值将新列添加到模型的末尾。
     * 如果新大小小于当前大小,则索引<code> columnCount </code>和更大值处的所有列都将被丢弃。
     * 
     * 
     *  @param columnCount  the new number of columns in the model
     *
     *  @see #setColumnCount
     * @since 1.3
     */
    public void setColumnCount(int columnCount) {
        columnIdentifiers.setSize(columnCount);
        justifyRows(0, getRowCount());
        fireTableStructureChanged();
    }

    /**
     *  Adds a column to the model.  The new column will have the
     *  identifier <code>columnName</code>, which may be null.  This method
     *  will send a
     *  <code>tableChanged</code> notification message to all the listeners.
     *  This method is a cover for <code>addColumn(Object, Vector)</code> which
     *  uses <code>null</code> as the data vector.
     *
     * <p>
     *  向模型中添加列。新列将具有标识符<code> columnName </code>,其可以为null。此方法将向所有侦听器发送<code> tableChanged </code>通知消息。
     * 这个方法是<code> addColumn(Object,Vector)</code>的封面,它使用<code> null </code>作为数据向量。
     * 
     * 
     * @param   columnName the identifier of the column being added
     */
    public void addColumn(Object columnName) {
        addColumn(columnName, (Vector)null);
    }

    /**
     *  Adds a column to the model.  The new column will have the
     *  identifier <code>columnName</code>, which may be null.
     *  <code>columnData</code> is the
     *  optional vector of data for the column.  If it is <code>null</code>
     *  the column is filled with <code>null</code> values.  Otherwise,
     *  the new data will be added to model starting with the first
     *  element going to row 0, etc.  This method will send a
     *  <code>tableChanged</code> notification message to all the listeners.
     *
     * <p>
     *  向模型中添加列。新列将具有标识符<code> columnName </code>,其可以为null。 <code> columnData </code>是列的数据的可选向量。
     * 如果它是<code> null </code>,则该列用<code> null </code>值填充。否则,新数据将被添加到模型,从第一个元素开始到第0行,等等。
     * 这个方法将发送一个<code> tableChanged </code>通知消息给所有的监听器。
     * 
     * 
     * @param   columnName the identifier of the column being added
     * @param   columnData       optional data of the column being added
     */
    public void addColumn(Object columnName, Vector columnData) {
        columnIdentifiers.addElement(columnName);
        if (columnData != null) {
            int columnSize = columnData.size();
            if (columnSize > getRowCount()) {
                dataVector.setSize(columnSize);
            }
            justifyRows(0, getRowCount());
            int newColumn = getColumnCount() - 1;
            for(int i = 0; i < columnSize; i++) {
                  Vector row = (Vector)dataVector.elementAt(i);
                  row.setElementAt(columnData.elementAt(i), newColumn);
            }
        }
        else {
            justifyRows(0, getRowCount());
        }

        fireTableStructureChanged();
    }

    /**
     *  Adds a column to the model.  The new column will have the
     *  identifier <code>columnName</code>.  <code>columnData</code> is the
     *  optional array of data for the column.  If it is <code>null</code>
     *  the column is filled with <code>null</code> values.  Otherwise,
     *  the new data will be added to model starting with the first
     *  element going to row 0, etc.  This method will send a
     *  <code>tableChanged</code> notification message to all the listeners.
     *
     * <p>
     * 向模型中添加列。新列将具有标识符<code> columnName </code>。 <code> columnData </code>是列的可选数据数组。
     * 如果它是<code> null </code>,则该列用<code> null </code>值填充。否则,新数据将被添加到模型,从第一个元素开始到第0行,等等。
     * 这个方法将发送一个<code> tableChanged </code>通知消息给所有的监听器。
     * 
     * 
     * @see #addColumn(Object, Vector)
     */
    public void addColumn(Object columnName, Object[] columnData) {
        addColumn(columnName, convertToVector(columnData));
    }

//
// Implementing the TableModel interface
//

    /**
     * Returns the number of rows in this data table.
     * <p>
     *  返回此数据表中的行数。
     * 
     * 
     * @return the number of rows in the model
     */
    public int getRowCount() {
        return dataVector.size();
    }

    /**
     * Returns the number of columns in this data table.
     * <p>
     *  返回此数据表中的列数。
     * 
     * 
     * @return the number of columns in the model
     */
    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    /**
     * Returns the column name.
     *
     * <p>
     *  返回列名称。
     * 
     * 
     * @return a name for this column using the string value of the
     * appropriate member in <code>columnIdentifiers</code>.
     * If <code>columnIdentifiers</code> does not have an entry
     * for this index, returns the default
     * name provided by the superclass.
     */
    public String getColumnName(int column) {
        Object id = null;
        // This test is to cover the case when
        // getColumnCount has been subclassed by mistake ...
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
        return (id == null) ? super.getColumnName(column)
                            : id.toString();
    }

    /**
     * Returns true regardless of parameter values.
     *
     * <p>
     *  无论参数值如何,都返回true。
     * 
     * 
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  true
     * @see #setValueAt
     */
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    /**
     * Returns an attribute value for the cell at <code>row</code>
     * and <code>column</code>.
     *
     * <p>
     *  返回<code> row </code>和<code> column </code>的单元格的属性值。
     * 
     * 
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  the value Object at the specified cell
     * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
     *               column was given
     */
    public Object getValueAt(int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        return rowVector.elementAt(column);
    }

    /**
     * Sets the object value for the cell at <code>column</code> and
     * <code>row</code>.  <code>aValue</code> is the new value.  This method
     * will generate a <code>tableChanged</code> notification.
     *
     * <p>
     *  为<code> column </code>和<code> row </code>设置单元格的对象值。 <code> aValue </code>是新值。
     * 此方法将生成<code> tableChanged </code>通知。
     * 
     * 
     * @param   aValue          the new value; this can be null
     * @param   row             the row whose value is to be changed
     * @param   column          the column whose value is to be changed
     * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
     *               column was given
     */
    public void setValueAt(Object aValue, int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        rowVector.setElementAt(aValue, column);
        fireTableCellUpdated(row, column);
    }

//
// Protected Methods
//

    /**
     * Returns a vector that contains the same objects as the array.
     * <p>
     *  返回包含与数组相同的对象的向量。
     * 
     * 
     * @param anArray  the array to be converted
     * @return  the new vector; if <code>anArray</code> is <code>null</code>,
     *                          returns <code>null</code>
     */
    protected static Vector convertToVector(Object[] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Object> v = new Vector<Object>(anArray.length);
        for (Object o : anArray) {
            v.addElement(o);
        }
        return v;
    }

    /**
     * Returns a vector of vectors that contains the same objects as the array.
     * <p>
     *  返回包含与数组相同的对象的向量的向量。
     * 
     * @param anArray  the double array to be converted
     * @return the new vector of vectors; if <code>anArray</code> is
     *                          <code>null</code>, returns <code>null</code>
     */
    protected static Vector convertToVector(Object[][] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Vector> v = new Vector<Vector>(anArray.length);
        for (Object[] o : anArray) {
            v.addElement(convertToVector(o));
        }
        return v;
    }

} // End of class DefaultTableModel
