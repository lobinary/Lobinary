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

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.SwingPropertyChangeSupport;
import java.lang.Integer;
import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *  A <code>TableColumn</code> represents all the attributes of a column in a
 *  <code>JTable</code>, such as width, resizability, minimum and maximum width.
 *  In addition, the <code>TableColumn</code> provides slots for a renderer and
 *  an editor that can be used to display and edit the values in this column.
 *  <p>
 *  It is also possible to specify renderers and editors on a per type basis
 *  rather than a per column basis - see the
 *  <code>setDefaultRenderer</code> method in the <code>JTable</code> class.
 *  This default mechanism is only used when the renderer (or
 *  editor) in the <code>TableColumn</code> is <code>null</code>.
 * <p>
 *  The <code>TableColumn</code> stores the link between the columns in the
 *  <code>JTable</code> and the columns in the <code>TableModel</code>.
 *  The <code>modelIndex</code> is the column in the
 *  <code>TableModel</code>, which will be queried for the data values for the
 *  cells in this column. As the column moves around in the view this
 *  <code>modelIndex</code> does not change.
 *  <p>
 * <b>Note:</b> Some implementations may assume that all
 *    <code>TableColumnModel</code>s are unique, therefore we would
 *    recommend that the same <code>TableColumn</code> instance
 *    not be added more than once to a <code>TableColumnModel</code>.
 *    To show <code>TableColumn</code>s with the same column of
 *    data from the model, create a new instance with the same
 *    <code>modelIndex</code>.
 *  <p>
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
 *  <code> TableColumn </code>表示<code> JTable </code>中列的所有属性,例如width,resizability,minimum和maximum width。
 * 此外,<code> TableColumn </code>为渲染器和编辑器提供了插槽,可用于显示和编辑此列中的值。
 * <p>
 *  还可以基于每个类型而不是每列来指定渲染器和编辑器 - 请参阅<code> JTable </code>类中的<code> setDefaultRenderer </code>方法。
 * 此默认机制仅在<code> TableColumn </code>中的渲染器(或编辑器)为<code> null </code>时使用。
 * <p>
 *  <code> TableColumn </code>存储<code> JTable </code>中的列与<code> TableModel </code>中的列之间的链接。
 *  <code> modelIndex </code>是<code> TableModel </code>中的列,将查询此列中单元格的数据值。
 * 当列在视图中移动时,此<code> modelIndex </code>不会更改。
 * <p>
 * <b>注意：</b>有些实现可能假设所有<code> TableColumnModel </code>是唯一的,因此我们建议不要将同一<code> TableColumn </code> a <code>
 *  TableColumnModel </code>。
 * 要使用模型中的相同数据列显示<code> TableColumn </code>,请使用相同的<code> modelIndex </code>创建一个新实例。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Alan Chung
 * @author Philip Milne
 * @see javax.swing.table.TableColumnModel
 *
 * @see javax.swing.table.DefaultTableColumnModel
 * @see javax.swing.table.JTableHeader#getDefaultRenderer()
 * @see JTable#getDefaultRenderer(Class)
 * @see JTable#getDefaultEditor(Class)
 * @see JTable#getCellRenderer(int, int)
 * @see JTable#getCellEditor(int, int)
 */
public class TableColumn extends Object implements Serializable {

    /**
     * Obsolete as of Java 2 platform v1.3.  Please use string literals to identify
     * properties.
     * <p>
     *  作为Java 2平台v1.3的已过时。请使用字符串文字来标识属性。
     * 
     */
    /*
     * Warning: The value of this constant, "columWidth" is wrong as the
     * name of the property is "columnWidth".
     * <p>
     *  警告：此常量的值"columWidth"错误,因为属性的名称为"columnWidth"。
     * 
     */
    public final static String COLUMN_WIDTH_PROPERTY = "columWidth";

    /**
     * Obsolete as of Java 2 platform v1.3.  Please use string literals to identify
     * properties.
     * <p>
     *  作为Java 2平台v1.3的已过时。请使用字符串文字来标识属性。
     * 
     */
    public final static String HEADER_VALUE_PROPERTY = "headerValue";

    /**
     * Obsolete as of Java 2 platform v1.3.  Please use string literals to identify
     * properties.
     * <p>
     *  作为Java 2平台v1.3的已过时。请使用字符串文字来标识属性。
     * 
     */
    public final static String HEADER_RENDERER_PROPERTY = "headerRenderer";

    /**
     * Obsolete as of Java 2 platform v1.3.  Please use string literals to identify
     * properties.
     * <p>
     *  作为Java 2平台v1.3的已过时。请使用字符串文字来标识属性。
     * 
     */
    public final static String CELL_RENDERER_PROPERTY = "cellRenderer";

//
//  Instance Variables
//

    /**
      * The index of the column in the model which is to be displayed by
      * this <code>TableColumn</code>. As columns are moved around in the
      * view <code>modelIndex</code> remains constant.
      * <p>
      *  要由此<code> TableColumn </code>显示的模型中的列的索引。当列在视图中移动时,<code> modelIndex </code>保持不变。
      * 
      */
    protected int       modelIndex;

    /**
     *  This object is not used internally by the drawing machinery of
     *  the <code>JTable</code>; identifiers may be set in the
     *  <code>TableColumn</code> as as an
     *  optional way to tag and locate table columns. The table package does
     *  not modify or invoke any methods in these identifier objects other
     *  than the <code>equals</code> method which is used in the
     *  <code>getColumnIndex()</code> method in the
     *  <code>DefaultTableColumnModel</code>.
     * <p>
     * 这个对象不是由<code> JTable </code>的绘图机器内部使用的;可以在<code> TableColumn </code>中设置标识符作为标记和定位表列的可选方法。
     * 表包不修改或调用这些标识符对象中除了<code> equals </code>方法中的<code> getColumnIndex()</code>方法中的任何方法,<code> DefaultTable
     * ColumnModel <代码>。
     * 这个对象不是由<code> JTable </code>的绘图机器内部使用的;可以在<code> TableColumn </code>中设置标识符作为标记和定位表列的可选方法。
     * 
     */
    protected Object    identifier;

    /** The width of the column. */
    protected int       width;

    /** The minimum width of the column. */
    protected int       minWidth;

    /** The preferred width of the column. */
    private int         preferredWidth;

    /** The maximum width of the column. */
    protected int       maxWidth;

    /** The renderer used to draw the header of the column. */
    protected TableCellRenderer headerRenderer;

    /** The header value of the column. */
    protected Object            headerValue;

    /** The renderer used to draw the data cells of the column. */
    protected TableCellRenderer cellRenderer;

    /** The editor used to edit the data cells of the column. */
    protected TableCellEditor   cellEditor;

    /** If true, the user is allowed to resize the column; the default is true. */
    protected boolean   isResizable;

    /**
     * This field was not used in previous releases and there are
     * currently no plans to support it in the future.
     *
     * <p>
     *  此字段在以前的版本中未使用,并且目前没有计划在将来支持它。
     * 
     * 
     * @deprecated as of Java 2 platform v1.3
     */
    /*
     *  Counter used to disable posting of resizing notifications until the
     *  end of the resize.
     * <p>
     *  用于禁用发布调整大小通知的计数器,直到调整大小结束。
     * 
     */
    @Deprecated
    transient protected int     resizedPostingDisableCount;

    /**
     * If any <code>PropertyChangeListeners</code> have been registered, the
     * <code>changeSupport</code> field describes them.
     * <p>
     *  如果已经注册了任何<code> PropertyChangeListeners </code>,则<code> changeSupport </code>字段描述它们。
     * 
     */
    private SwingPropertyChangeSupport changeSupport;

//
// Constructors
//

    /**
     *  Cover method, using a default model index of 0,
     *  default width of 75, a <code>null</code> renderer and a
     *  <code>null</code> editor.
     *  This method is intended for serialization.
     * <p>
     *  Cover方法,使用默认模型索引0,默认宽度75,一个<code> null </code> renderer和一个<code> null </code>编辑器。此方法用于序列化。
     * 
     * 
     *  @see #TableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public TableColumn() {
        this(0);
    }

    /**
     *  Cover method, using a default width of 75, a <code>null</code>
     *  renderer and a <code>null</code> editor.
     * <p>
     *  Cover方法,使用默认宽度75,<code> null </code>渲染器和<code> null </code>编辑器。
     * 
     * 
     *  @see #TableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public TableColumn(int modelIndex) {
        this(modelIndex, 75, null, null);
    }

    /**
     *  Cover method, using a <code>null</code> renderer and a
     *  <code>null</code> editor.
     * <p>
     *  Cover方法,使用<code> null </code>渲染器和<code> null </code>编辑器。
     * 
     * 
     *  @see #TableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public TableColumn(int modelIndex, int width) {
        this(modelIndex, width, null, null);
    }

    /**
     *  Creates and initializes an instance of
     *  <code>TableColumn</code> with the specified model index,
     *  width, cell renderer, and cell editor;
     *  all <code>TableColumn</code> constructors delegate to this one.
     *  The value of <code>width</code> is used
     *  for both the initial and preferred width;
     *  if <code>width</code> is negative,
     *  they're set to 0.
     *  The minimum width is set to 15 unless the initial width is less,
     *  in which case the minimum width is set to
     *  the initial width.
     *
     *  <p>
     *  When the <code>cellRenderer</code>
     *  or <code>cellEditor</code> parameter is <code>null</code>,
     *  a default value provided by the <code>JTable</code>
     *  <code>getDefaultRenderer</code>
     *  or <code>getDefaultEditor</code> method, respectively,
     *  is used to
     *  provide defaults based on the type of the data in this column.
     *  This column-centric rendering strategy can be circumvented by overriding
     *  the <code>getCellRenderer</code> methods in <code>JTable</code>.
     *
     * <p>
     * 使用指定的模型索引,宽度,单元格渲染器和单元格编辑器创建和初始化<code> TableColumn </code>的实例;所有<code> TableColumn </code>构造函数委托给这一个。
     *  <code> width </code>的值用于初始宽度和首选宽度;如果<code> width </code>为负,则它们设置为0.最小宽度设置为15,除非初始宽度较小,在这种情况下,最小宽度设置为
     * 初始宽度。
     * 
     * <p>
     *  当<code> cellRenderer </code>或<code> cellEditor </code>参数为<code> null </code>时,<code> JTable </code> 
     * <code> getDefaultRenderer <代码>或<code> getDefaultEditor </code>方法分别用于根据此列中数据的类型提供默认值。
     * 这种以列为中心的呈现策略可以通过覆盖<code> JTable </code>中的<code> getCellRenderer </code>方法来规避。
     * 
     * 
     * @param modelIndex the index of the column
     *  in the model that supplies the data for this column in the table;
     *  the model index remains the same
     *  even when columns are reordered in the view
     * @param width this column's preferred width and initial width
     * @param cellRenderer the object used to render values in this column
     * @param cellEditor the object used to edit values in this column
     * @see #getMinWidth()
     * @see JTable#getDefaultRenderer(Class)
     * @see JTable#getDefaultEditor(Class)
     * @see JTable#getCellRenderer(int, int)
     * @see JTable#getCellEditor(int, int)
     */
    public TableColumn(int modelIndex, int width,
                                 TableCellRenderer cellRenderer,
                                 TableCellEditor cellEditor) {
        super();
        this.modelIndex = modelIndex;
        preferredWidth = this.width = Math.max(width, 0);

        this.cellRenderer = cellRenderer;
        this.cellEditor = cellEditor;

        // Set other instance variables to default values.
        minWidth = Math.min(15, this.width);
        maxWidth = Integer.MAX_VALUE;
        isResizable = true;
        resizedPostingDisableCount = 0;
        headerValue = null;
    }

//
// Modifying and Querying attributes
//

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (changeSupport != null) {
            changeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    private void firePropertyChange(String propertyName, int oldValue, int newValue) {
        if (oldValue != newValue) {
            firePropertyChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
        }
    }

    private void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        if (oldValue != newValue) {
            firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Sets the model index for this column. The model index is the
     * index of the column in the model that will be displayed by this
     * <code>TableColumn</code>. As the <code>TableColumn</code>
     * is moved around in the view the model index remains constant.
     * <p>
     *  设置此列的模型索引。模型索引是模型中将由此<code> TableColumn </code>显示的列的索引。随着<code> TableColumn </code>在视图中移动,模型索引保持不变。
     * 
     * 
     * @param  modelIndex  the new modelIndex
     * @beaninfo
     *  bound: true
     *  description: The model index.
     */
    public void setModelIndex(int modelIndex) {
        int old = this.modelIndex;
        this.modelIndex = modelIndex;
        firePropertyChange("modelIndex", old, modelIndex);
    }

    /**
     * Returns the model index for this column.
     * <p>
     *  返回此列的模型索引。
     * 
     * 
     * @return the <code>modelIndex</code> property
     */
    public int getModelIndex() {
        return modelIndex;
    }

    /**
     * Sets the <code>TableColumn</code>'s identifier to
     * <code>anIdentifier</code>. <p>
     * Note: identifiers are not used by the <code>JTable</code>,
     * they are purely a
     * convenience for the external tagging and location of columns.
     *
     * <p>
     *  将<code> TableColumn </code>的标识符设置为<code> anIdentifier </code>。
     *  <p>注意：<code> JTable </code>不使用标识符,它们只是为了方便外部标记和列的位置。
     * 
     * 
     * @param      identifier           an identifier for this column
     * @see        #getIdentifier
     * @beaninfo
     *  bound: true
     *  description: A unique identifier for this column.
     */
    public void setIdentifier(Object identifier) {
        Object old = this.identifier;
        this.identifier = identifier;
        firePropertyChange("identifier", old, identifier);
    }


    /**
     *  Returns the <code>identifier</code> object for this column.
     *  Note identifiers are not used by <code>JTable</code>,
     *  they are purely a convenience for external use.
     *  If the <code>identifier</code> is <code>null</code>,
     *  <code>getIdentifier()</code> returns <code>getHeaderValue</code>
     *  as a default.
     *
     * <p>
     * 返回此列的<code> identifier </code>对象。注释标识符不由<code> JTable </code>使用,它们只是为了方便外部使用。
     * 如果<code>标识符</code>是<code> null </code>,则<code> getIdentifier()</code>返回<code> getHeaderValue </code>作
     * 为默认值。
     * 返回此列的<code> identifier </code>对象。注释标识符不由<code> JTable </code>使用,它们只是为了方便外部使用。
     * 
     * 
     * @return  the <code>identifier</code> property
     * @see     #setIdentifier
     */
    public Object getIdentifier() {
        return (identifier != null) ? identifier : getHeaderValue();

    }

    /**
     * Sets the <code>Object</code> whose string representation will be
     * used as the value for the <code>headerRenderer</code>.  When the
     * <code>TableColumn</code> is created, the default <code>headerValue</code>
     * is <code>null</code>.
     * <p>
     *  设置<code> Object </code>,其字符串表示形式将用作<code> headerRenderer </code>的值。
     * 当创建<code> TableColumn </code>时,默认的<code> headerValue </code>是<code> null </code>。
     * 
     * 
     * @param headerValue  the new headerValue
     * @see       #getHeaderValue
     * @beaninfo
     *  bound: true
     *  description: The text to be used by the header renderer.
     */
    public void setHeaderValue(Object headerValue) {
        Object old = this.headerValue;
        this.headerValue = headerValue;
        firePropertyChange("headerValue", old, headerValue);
    }

    /**
     * Returns the <code>Object</code> used as the value for the header
     * renderer.
     *
     * <p>
     *  返回用作头部渲染器的值的<code> Object </code>。
     * 
     * 
     * @return  the <code>headerValue</code> property
     * @see     #setHeaderValue
     */
    public Object getHeaderValue() {
        return headerValue;
    }

    //
    // Renderers and Editors
    //

    /**
     * Sets the <code>TableCellRenderer</code> used to draw the
     * <code>TableColumn</code>'s header to <code>headerRenderer</code>.
     * <p>
     * It is the header renderers responsibility to render the sorting
     * indicator.  If you are using sorting and specify a renderer your
     * renderer must render the sorting indication.
     *
     * <p>
     *  设置用于将<code> TableColumn </code>的头描述为<code> headerRenderer </code>的<code> TableCellRenderer </code>。
     * <p>
     *  头渲染器负责渲染排序指示符。如果您正在使用排序并指定渲染器,则渲染器必须呈现排序指示。
     * 
     * 
     * @param headerRenderer  the new headerRenderer
     *
     * @see       #getHeaderRenderer
     * @beaninfo
     *  bound: true
     *  description: The header renderer.
     */
    public void setHeaderRenderer(TableCellRenderer headerRenderer) {
        TableCellRenderer old = this.headerRenderer;
        this.headerRenderer = headerRenderer;
        firePropertyChange("headerRenderer", old, headerRenderer);
    }

    /**
     * Returns the <code>TableCellRenderer</code> used to draw the header of the
     * <code>TableColumn</code>. When the <code>headerRenderer</code> is
     * <code>null</code>, the <code>JTableHeader</code>
     * uses its <code>defaultRenderer</code>. The default value for a
     * <code>headerRenderer</code> is <code>null</code>.
     *
     * <p>
     *  返回用于绘制<code> TableColumn </code>标题的<code> TableCellRenderer </code>。
     * 当<code> headerRenderer </code>是<code> null </code>时,<code> JTableHeader </code>使用其<code> defaultRende
     * rer </code>。
     *  返回用于绘制<code> TableColumn </code>标题的<code> TableCellRenderer </code>。
     *  <code> headerRenderer </code>的默认值为<code> null </code>。
     * 
     * 
     * @return  the <code>headerRenderer</code> property
     * @see     #setHeaderRenderer
     * @see     #setHeaderValue
     * @see     javax.swing.table.JTableHeader#getDefaultRenderer()
     */
    public TableCellRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    /**
     * Sets the <code>TableCellRenderer</code> used by <code>JTable</code>
     * to draw individual values for this column.
     *
     * <p>
     *  设置<code> JTable </code>使用的<code> TableCellRenderer </code>为此列绘制单个值。
     * 
     * 
     * @param cellRenderer  the new cellRenderer
     * @see     #getCellRenderer
     * @beaninfo
     *  bound: true
     *  description: The renderer to use for cell values.
     */
    public void setCellRenderer(TableCellRenderer cellRenderer) {
        TableCellRenderer old = this.cellRenderer;
        this.cellRenderer = cellRenderer;
        firePropertyChange("cellRenderer", old, cellRenderer);
    }

    /**
     * Returns the <code>TableCellRenderer</code> used by the
     * <code>JTable</code> to draw
     * values for this column.  The <code>cellRenderer</code> of the column
     * not only controls the visual look for the column, but is also used to
     * interpret the value object supplied by the <code>TableModel</code>.
     * When the <code>cellRenderer</code> is <code>null</code>,
     * the <code>JTable</code> uses a default renderer based on the
     * class of the cells in that column. The default value for a
     * <code>cellRenderer</code> is <code>null</code>.
     *
     * <p>
     * 返回由<code> JTable </code>使用的<code> TableCellRenderer </code>来为此列绘制值。
     * 列的<code> cellRenderer </code>不仅控制列的视觉外观,而且还用于解释由<code> TableModel </code>提供的值对象。
     * 当<code> cellRenderer </code>是<code> null </code>时,<code> JTable </code>使用基于该列中单元格类的默认渲染器。
     *  <code> cellRenderer </code>的默认值为<code> null </code>。
     * 
     * 
     * @return  the <code>cellRenderer</code> property
     * @see     #setCellRenderer
     * @see     JTable#setDefaultRenderer
     */
    public TableCellRenderer getCellRenderer() {
        return cellRenderer;
    }

    /**
     * Sets the editor to used by when a cell in this column is edited.
     *
     * <p>
     *  设置编辑此列中的单元格时要使用的编辑器。
     * 
     * 
     * @param cellEditor  the new cellEditor
     * @see     #getCellEditor
     * @beaninfo
     *  bound: true
     *  description: The editor to use for cell values.
     */
    public void setCellEditor(TableCellEditor cellEditor){
        TableCellEditor old = this.cellEditor;
        this.cellEditor = cellEditor;
        firePropertyChange("cellEditor", old, cellEditor);
    }

    /**
     * Returns the <code>TableCellEditor</code> used by the
     * <code>JTable</code> to edit values for this column.  When the
     * <code>cellEditor</code> is <code>null</code>, the <code>JTable</code>
     * uses a default editor based on the
     * class of the cells in that column. The default value for a
     * <code>cellEditor</code> is <code>null</code>.
     *
     * <p>
     *  返回由<code> JTable </code>使用的<code> TableCellEditor </code>以编辑此列的值。
     * 当<code> cellEditor </code>是<code> null </code>时,<code> JTable </code>使用基于该列中单元格类的默认编辑器。
     *  <code> cellEditor </code>的默认值为<code> null </code>。
     * 
     * 
     * @return  the <code>cellEditor</code> property
     * @see     #setCellEditor
     * @see     JTable#setDefaultEditor
     */
    public TableCellEditor getCellEditor() {
        return cellEditor;
    }

    /**
     * This method should not be used to set the widths of columns in the
     * <code>JTable</code>, use <code>setPreferredWidth</code> instead.
     * Like a layout manager in the
     * AWT, the <code>JTable</code> adjusts a column's width automatically
     * whenever the
     * table itself changes size, or a column's preferred width is changed.
     * Setting widths programmatically therefore has no long term effect.
     * <p>
     * This method sets this column's width to <code>width</code>.
     * If <code>width</code> exceeds the minimum or maximum width,
     * it is adjusted to the appropriate limiting value.
     * <p>
     *  此方法不应用于设置<code> JTable </code>中列的宽度,而应使用<code> setPreferredWidth </code>。
     * 与AWT中的布局管理器一样,每当表本身更改大小或列的首选宽度更改时,<code> JTable </code>会自动调整列宽度。因此,以编程方式设置宽度没有长期效果。
     * <p>
     *  此方法将此列的宽度设置为<code> width </code>。如果<code> width </code>超过最小或最大宽度,则将其调整为适当的极限值。
     * 
     * 
     * @param  width  the new width
     * @see     #getWidth
     * @see     #setMinWidth
     * @see     #setMaxWidth
     * @see     #setPreferredWidth
     * @see     JTable#doLayout()
     * @beaninfo
     *  bound: true
     *  description: The width of the column.
     */
    public void setWidth(int width) {
        int old = this.width;
        this.width = Math.min(Math.max(width, minWidth), maxWidth);
        firePropertyChange("width", old, this.width);
    }

    /**
     * Returns the width of the <code>TableColumn</code>. The default width is
     * 75.
     *
     * <p>
     * 返回<code> TableColumn </code>的宽度。默认宽度为75。
     * 
     * 
     * @return  the <code>width</code> property
     * @see     #setWidth
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets this column's preferred width to <code>preferredWidth</code>.
     * If <code>preferredWidth</code> exceeds the minimum or maximum width,
     * it is adjusted to the appropriate limiting value.
     * <p>
     * For details on how the widths of columns in the <code>JTable</code>
     * (and <code>JTableHeader</code>) are calculated from the
     * <code>preferredWidth</code>,
     * see the <code>doLayout</code> method in <code>JTable</code>.
     *
     * <p>
     *  将此列的首选宽度设置为<code> preferredWidth </code>。如果<code> preferredWidth </code>超过最小或最大宽度,则将其调整为适当的限制值。
     * <p>
     *  有关如何从<code> preferredWidth </code>计算<code> JTable </code>(和<code> JTableHeader </code>)中的列宽度的详细信息,请参
     * 阅<code> doLayout <代码>方法在<code> JTable </code>中。
     * 
     * 
     * @param  preferredWidth the new preferred width
     * @see     #getPreferredWidth
     * @see     JTable#doLayout()
     * @beaninfo
     *  bound: true
     *  description: The preferred width of the column.
     */
    public void setPreferredWidth(int preferredWidth) {
        int old = this.preferredWidth;
        this.preferredWidth = Math.min(Math.max(preferredWidth, minWidth), maxWidth);
        firePropertyChange("preferredWidth", old, this.preferredWidth);
    }

    /**
     * Returns the preferred width of the <code>TableColumn</code>.
     * The default preferred width is 75.
     *
     * <p>
     *  返回<code> TableColumn </code>的首选宽度。默认首选宽度为75。
     * 
     * 
     * @return  the <code>preferredWidth</code> property
     * @see     #setPreferredWidth
     */
    public int getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * Sets the <code>TableColumn</code>'s minimum width to
     * <code>minWidth</code>,
     * adjusting the new minimum width if necessary to ensure that
     * 0 &lt;= <code>minWidth</code> &lt;= <code>maxWidth</code>.
     * For example, if the <code>minWidth</code> argument is negative,
     * this method sets the <code>minWidth</code> property to 0.
     *
     * <p>
     * If the value of the
     * <code>width</code> or <code>preferredWidth</code> property
     * is less than the new minimum width,
     * this method sets that property to the new minimum width.
     *
     * <p>
     *  将<code> TableColumn </code>的最小宽度设置为<code> minWidth </code>,如果需要,调整新的最小宽度以确保0 <= <code> minWidth </code>
     * &lt; = < code> maxWidth </code>。
     * 例如,如果<code> minWidth </code>参数为负,则此方法将<code> minWidth </code>属性设置为0。
     * 
     * <p>
     *  如果<code> width </code>或<code> preferredWidth </code>属性的值小于新的最小宽度,则此方法将该属性设置为新的最小宽度。
     * 
     * 
     * @param minWidth  the new minimum width
     * @see     #getMinWidth
     * @see     #setPreferredWidth
     * @see     #setMaxWidth
     * @beaninfo
     *  bound: true
     *  description: The minimum width of the column.
     */
    public void setMinWidth(int minWidth) {
        int old = this.minWidth;
        this.minWidth = Math.max(Math.min(minWidth, maxWidth), 0);
        if (width < this.minWidth) {
            setWidth(this.minWidth);
        }
        if (preferredWidth < this.minWidth) {
            setPreferredWidth(this.minWidth);
        }
        firePropertyChange("minWidth", old, this.minWidth);
    }

    /**
     * Returns the minimum width for the <code>TableColumn</code>. The
     * <code>TableColumn</code>'s width can't be made less than this either
     * by the user or programmatically.
     *
     * <p>
     *  返回<code> TableColumn </code>的最小宽度。 <code> TableColumn </code>的宽度不能小于用户或编程方式。
     * 
     * 
     * @return  the <code>minWidth</code> property
     * @see     #setMinWidth
     * @see     #TableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * Sets the <code>TableColumn</code>'s maximum width to
     * <code>maxWidth</code> or,
     * if <code>maxWidth</code> is less than the minimum width,
     * to the minimum width.
     *
     * <p>
     * If the value of the
     * <code>width</code> or <code>preferredWidth</code> property
     * is more than the new maximum width,
     * this method sets that property to the new maximum width.
     *
     * <p>
     *  将<code> TableColumn </code>的最大宽度设置为<code> maxWidth </code>,或者如果<code> maxWidth </code>小于最小宽度,则设置为最小宽
     * 度。
     * 
     * <p>
     * 如果<code> width </code>或<code> preferredWidth </code>属性的值大于新的最大宽度,则此方法将该属性设置为新的最大宽度。
     * 
     * 
     * @param maxWidth  the new maximum width
     * @see     #getMaxWidth
     * @see     #setPreferredWidth
     * @see     #setMinWidth
     * @beaninfo
     *  bound: true
     *  description: The maximum width of the column.
     */
    public void setMaxWidth(int maxWidth) {
        int old = this.maxWidth;
        this.maxWidth = Math.max(minWidth, maxWidth);
        if (width > this.maxWidth) {
            setWidth(this.maxWidth);
        }
        if (preferredWidth > this.maxWidth) {
            setPreferredWidth(this.maxWidth);
        }
        firePropertyChange("maxWidth", old, this.maxWidth);
    }

    /**
     * Returns the maximum width for the <code>TableColumn</code>. The
     * <code>TableColumn</code>'s width can't be made larger than this
     * either by the user or programmatically.  The default maxWidth
     * is Integer.MAX_VALUE.
     *
     * <p>
     *  返回<code> TableColumn </code>的最大宽度。 <code> TableColumn </code>的宽度不能由用户或编程方式大于此值。
     * 默认maxWidth为Integer.MAX_VALUE。
     * 
     * 
     * @return  the <code>maxWidth</code> property
     * @see     #setMaxWidth
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Sets whether this column can be resized.
     *
     * <p>
     *  设置是否可以调整此列。
     * 
     * 
     * @param isResizable  if true, resizing is allowed; otherwise false
     * @see     #getResizable
     * @beaninfo
     *  bound: true
     *  description: Whether or not this column can be resized.
     */
    public void setResizable(boolean isResizable) {
        boolean old = this.isResizable;
        this.isResizable = isResizable;
        firePropertyChange("isResizable", old, this.isResizable);
    }

    /**
     * Returns true if the user is allowed to resize the
     * <code>TableColumn</code>'s
     * width, false otherwise. You can change the width programmatically
     * regardless of this setting.  The default is true.
     *
     * <p>
     *  如果允许用户调整<code> TableColumn </code>的宽度,则返回true,否则返回false。无论此设置如何,都可以以编程方式更改宽度。默认值为true。
     * 
     * 
     * @return  the <code>isResizable</code> property
     * @see     #setResizable
     */
    public boolean getResizable() {
        return isResizable;
    }

    /**
     * Resizes the <code>TableColumn</code> to fit the width of its header cell.
     * This method does nothing if the header renderer is <code>null</code>
     * (the default case). Otherwise, it sets the minimum, maximum and preferred
     * widths of this column to the widths of the minimum, maximum and preferred
     * sizes of the Component delivered by the header renderer.
     * The transient "width" property of this TableColumn is also set to the
     * preferred width. Note this method is not used internally by the table
     * package.
     *
     * <p>
     *  调整<code> TableColumn </code>的大小以适应其标题单元格的宽度。如果头部渲染器是<code> null </code>(默认情况),此方法不执行任何操作。
     * 否则,它将此列的最小,最大和首选宽度设置为由头部渲染器传送的组件的最小,最大和首选大小的宽度。此TableColumn的临时"width"属性也设置为首选宽度。请注意,此方法不由表包在内部使用。
     * 
     * 
     * @see     #setPreferredWidth
     */
    public void sizeWidthToFit() {
        if (headerRenderer == null) {
            return;
        }
        Component c = headerRenderer.getTableCellRendererComponent(null,
                                getHeaderValue(), false, false, 0, 0);

        setMinWidth(c.getMinimumSize().width);
        setMaxWidth(c.getMaximumSize().width);
        setPreferredWidth(c.getPreferredSize().width);

        setWidth(getPreferredWidth());
    }

    /**
     * This field was not used in previous releases and there are
     * currently no plans to support it in the future.
     *
     * <p>
     *  此字段在以前的版本中未使用,并且目前没有计划在将来支持它。
     * 
     * 
     * @deprecated as of Java 2 platform v1.3
     */
    @Deprecated
    public void disableResizedPosting() {
        resizedPostingDisableCount++;
    }

    /**
     * This field was not used in previous releases and there are
     * currently no plans to support it in the future.
     *
     * <p>
     *  此字段在以前的版本中未使用,并且目前没有计划在将来支持它。
     * 
     * 
     * @deprecated as of Java 2 platform v1.3
     */
    @Deprecated
    public void enableResizedPosting() {
        resizedPostingDisableCount--;
    }

//
// Property Change Support
//

    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A <code>PropertyChangeEvent</code> will get fired in response to an
     * explicit call to <code>setFont</code>, <code>setBackground</code>,
     * or <code>setForeground</code> on the
     * current component.  Note that if the current component is
     * inheriting its foreground, background, or font from its
     * container, then no event will be fired in response to a
     * change in the inherited property.
     *
     * <p>
     *  向侦听器列表中添加<code> PropertyChangeListener </code>。侦听器为所有属性注册。
     * <p>
     * 响应对当前组件的<code> setFont </code>,<code> setBackground </code>或<code> setForeground </code>的显式调用,将触发<code>
     *  PropertyChangeEvent </code> 。
     * 请注意,如果当前组件从其容器继承其前景,背景或字体,则不会触发任何事件以响应继承的属性中的更改。
     * 
     * 
     * @param listener  the listener to be added
     *
     */
    public synchronized void addPropertyChangeListener(
                                PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * The <code>PropertyChangeListener</code> to be removed was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除<code> PropertyChangeListener </code>。要删除的<code> PropertyChangeListener </code>已为所有属性注册。
     * 
     * 
     * @param listener  the listener to be removed
     *
     */

    public synchronized void removePropertyChangeListener(
                                PropertyChangeListener listener) {
        if (changeSupport != null) {
            changeSupport.removePropertyChangeListener(listener);
        }
    }

    /**
     * Returns an array of all the <code>PropertyChangeListener</code>s added
     * to this TableColumn with addPropertyChangeListener().
     *
     * <p>
     *  返回通过addPropertyChangeListener()添加到此TableColumn的所有<code> PropertyChangeListener </code>数组。
     * 
     * 
     * @return all of the <code>PropertyChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners();
    }

//
// Protected Methods
//

    /**
     * As of Java 2 platform v1.3, this method is not called by the <code>TableColumn</code>
     * constructor.  Previously this method was used by the
     * <code>TableColumn</code> to create a default header renderer.
     * As of Java 2 platform v1.3, the default header renderer is <code>null</code>.
     * <code>JTableHeader</code> now provides its own shared default
     * renderer, just as the <code>JTable</code> does for its cell renderers.
     *
     * <p>
     *  从Java 2平台v1.3开始,此方法不是由<code> TableColumn </code>构造函数调用的。
     * 以前,此方法由<code> TableColumn </code>用来创建默认头渲染器。从Java 2平台v1.3开始,默认头渲染器是<code> null </code>。
     * 
     * @return the default header renderer
     * @see javax.swing.table.JTableHeader#createDefaultRenderer()
     */
    protected TableCellRenderer createDefaultHeaderRenderer() {
        DefaultTableCellRenderer label = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                         boolean isSelected, boolean hasFocus, int row, int column) {
                if (table != null) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                }

                setText((value == null) ? "" : value.toString());
                setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                return this;
            }
        };
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

} // End of class TableColumn
