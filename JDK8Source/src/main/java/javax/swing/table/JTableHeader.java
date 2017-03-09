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

import sun.swing.table.DefaultTableCellHeaderRenderer;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.beans.PropertyChangeListener;
import java.beans.Transient;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * This is the object which manages the header of the <code>JTable</code>.
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
 *  这是管理<code> JTable </code>标头的对象。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Alan Chung
 * @author Philip Milne
 * @see javax.swing.JTable
 */
public class JTableHeader extends JComponent implements TableColumnModelListener, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "TableHeaderUI";

//
// Instance Variables
//
    /**
     * The table for which this object is the header;
     * the default is <code>null</code>.
     * <p>
     *  这个对象是表头的头;默认为<code> null </code>。
     * 
     */
    protected JTable table;

    /**
     * The <code>TableColumnModel</code> of the table header.
     * <p>
     *  表头的<code> TableColumnModel </code>。
     * 
     */
    protected TableColumnModel  columnModel;

    /**
     * If true, reordering of columns are allowed by the user;
     * the default is true.
     * <p>
     *  如果为真,则用户允许列的重新排序;默认值为true。
     * 
     */
    protected boolean   reorderingAllowed;

    /**
     * If true, resizing of columns are allowed by the user;
     * the default is true.
     * <p>
     *  如果为真,则用户允许列的大小调整;默认值为true。
     * 
     */
    protected boolean   resizingAllowed;

    /**
     * Obsolete as of Java 2 platform v1.3.  Real time repaints, in response
     * to column dragging or resizing, are now unconditional.
     * <p>
     *  作为Java 2平台v1.3的已过时。实时重绘,响应列拖动或调整大小,现在是无条件的。
     * 
     */
    /*
     * If this flag is true, then the header will repaint the table as
     * a column is dragged or resized; the default is true.
     * <p>
     *  如果此标志为true,则当列被拖动或调整大小时,标题将重新绘制表;默认值为true。
     * 
     */
    protected boolean   updateTableInRealTime;

    /** The index of the column being resized. <code>null</code> if not resizing. */
    transient protected TableColumn     resizingColumn;

    /** The index of the column being dragged. <code>null</code> if not dragging. */
    transient protected TableColumn     draggedColumn;

    /** The distance from its original position the column has been dragged. */
    transient protected int     draggedDistance;

    /**
      *  The default renderer to be used when a <code>TableColumn</code>
      *  does not define a <code>headerRenderer</code>.
      * <p>
      *  当<code> TableColumn </code>未定义<code> headerRenderer </code>时,将使用默认渲染器。
      * 
      */
    private TableCellRenderer defaultRenderer;

//
// Constructors
//

    /**
     *  Constructs a <code>JTableHeader</code> with a default
     *  <code>TableColumnModel</code>.
     *
     * <p>
     *  使用默认的<code> TableColumnModel </code>构造一个<code> JTableHeader </code>。
     * 
     * 
     * @see #createDefaultColumnModel
     */
    public JTableHeader() {
        this(null);
    }

    /**
     *  Constructs a <code>JTableHeader</code> which is initialized with
     *  <code>cm</code> as the column model.  If <code>cm</code> is
     *  <code>null</code> this method will initialize the table header
     *  with a default <code>TableColumnModel</code>.
     *
     * <p>
     * 构造一个以<code> cm </code>作为列模型初始化的<code> JTableHeader </code>。
     * 如果<code> cm </code>是<code> null </code>,此方法将使用默认的<code> TableColumnModel </code>初始化表头。
     * 
     * 
     * @param cm        the column model for the table
     * @see #createDefaultColumnModel
     */
    public JTableHeader(TableColumnModel cm) {
        super();

        //setFocusable(false); // for strict win/mac compatibility mode,
                               // this method should be invoked

        if (cm == null)
            cm = createDefaultColumnModel();
        setColumnModel(cm);

        // Initialize local ivars
        initializeLocalVars();

        // Get UI going
        updateUI();
    }

//
// Local behavior attributes
//

    /**
     *  Sets the table associated with this header.
     * <p>
     *  设置与此标题关联的表。
     * 
     * 
     *  @param  table   the new table
     *  @beaninfo
     *   bound: true
     *   description: The table associated with this header.
     */
    public void setTable(JTable table) {
        JTable old = this.table;
        this.table = table;
        firePropertyChange("table", old, table);
    }

    /**
      *  Returns the table associated with this header.
      * <p>
      *  返回与此标头关联的表。
      * 
      * 
      *  @return  the <code>table</code> property
      */
    public JTable getTable() {
        return table;
    }

    /**
     *  Sets whether the user can drag column headers to reorder columns.
     *
     * <p>
     *  设置用户是否可以拖动列标题以重新排列列。
     * 
     * 
     * @param   reorderingAllowed       true if the table view should allow
     *                                  reordering; otherwise false
     * @see     #getReorderingAllowed
     * @beaninfo
     *  bound: true
     *  description: Whether the user can drag column headers to reorder columns.
     */
    public void setReorderingAllowed(boolean reorderingAllowed) {
        boolean old = this.reorderingAllowed;
        this.reorderingAllowed = reorderingAllowed;
        firePropertyChange("reorderingAllowed", old, reorderingAllowed);
    }

    /**
     * Returns true if the user is allowed to rearrange columns by
     * dragging their headers, false otherwise. The default is true. You can
     * rearrange columns programmatically regardless of this setting.
     *
     * <p>
     *  如果允许用户通过拖动头来重新排列列,则返回true,否则返回false。默认值为true。无论此设置如何,都可以以编程方式重新排列列。
     * 
     * 
     * @return  the <code>reorderingAllowed</code> property
     * @see     #setReorderingAllowed
     */
    public boolean getReorderingAllowed() {
        return reorderingAllowed;
    }

    /**
     *  Sets whether the user can resize columns by dragging between headers.
     *
     * <p>
     *  设置用户是否可以通过在标题之间拖动来调整列大小。
     * 
     * 
     * @param   resizingAllowed         true if table view should allow
     *                                  resizing
     * @see     #getResizingAllowed
     * @beaninfo
     *  bound: true
     *  description: Whether the user can resize columns by dragging between headers.
     */
    public void setResizingAllowed(boolean resizingAllowed) {
        boolean old = this.resizingAllowed;
        this.resizingAllowed = resizingAllowed;
        firePropertyChange("resizingAllowed", old, resizingAllowed);
    }

    /**
     * Returns true if the user is allowed to resize columns by dragging
     * between their headers, false otherwise. The default is true. You can
     * resize columns programmatically regardless of this setting.
     *
     * <p>
     *  如果允许用户通过在其标题之间拖动来调整列大小,则返回true,否则返回false。默认值为true。无论此设置如何,您都可以通过编程方式调整列大小。
     * 
     * 
     * @return  the <code>resizingAllowed</code> property
     * @see     #setResizingAllowed
     */
    public boolean getResizingAllowed() {
        return resizingAllowed;
    }

    /**
     * Returns the the dragged column, if and only if, a drag is in
     * process, otherwise returns <code>null</code>.
     *
     * <p>
     *  返回拖动的列,如果且仅当,拖动正在进行,否则返回<code> null </code>。
     * 
     * 
     * @return  the dragged column, if a drag is in
     *          process, otherwise returns <code>null</code>
     * @see     #getDraggedDistance
     */
    public TableColumn getDraggedColumn() {
        return draggedColumn;
    }

    /**
     * Returns the column's horizontal distance from its original
     * position, if and only if, a drag is in process. Otherwise, the
     * the return value is meaningless.
     *
     * <p>
     *  当且仅当拖拽正在进行时,返回该列与其原始位置的水平距离。否则,返回值是无意义的。
     * 
     * 
     * @return  the column's horizontal distance from its original
     *          position, if a drag is in process, otherwise the return
     *          value is meaningless
     * @see     #getDraggedColumn
     */
    public int getDraggedDistance() {
        return draggedDistance;
    }

    /**
     * Returns the resizing column.  If no column is being
     * resized this method returns <code>null</code>.
     *
     * <p>
     *  返回调整大小列。如果没有调整列大小,此方法将返回<code> null </code>。
     * 
     * 
     * @return  the resizing column, if a resize is in process, otherwise
     *          returns <code>null</code>
     */
    public TableColumn getResizingColumn() {
        return resizingColumn;
    }

    /**
     * Obsolete as of Java 2 platform v1.3.  Real time repaints, in response to
     * column dragging or resizing, are now unconditional.
     * <p>
     *  作为Java 2平台v1.3的已过时。实时重绘,响应列拖动或调整大小,现在是无条件的。
     * 
     */
    /*
     *  Sets whether the body of the table updates in real time when
     *  a column is resized or dragged.
     *
     * <p>
     *  设置在调整列大小或拖动列时,表的主体是否实时更新。
     * 
     * 
     * @param   flag                    true if tableView should update
     *                                  the body of the table in real time
     * @see #getUpdateTableInRealTime
     */
    public void setUpdateTableInRealTime(boolean flag) {
        updateTableInRealTime = flag;
    }

    /**
     * Obsolete as of Java 2 platform v1.3.  Real time repaints, in response to
     * column dragging or resizing, are now unconditional.
     * <p>
     * 作为Java 2平台v1.3的已过时。实时重绘,响应列拖动或调整大小,现在是无条件的。
     * 
     */
    /*
     * Returns true if the body of the table view updates in real
     * time when a column is resized or dragged.  User can set this flag to
     * false to speed up the table's response to user resize or drag actions.
     * The default is true.
     *
     * <p>
     *  如果在调整列或拖动列时实时更新表视图的主体,则返回true。用户可以将此标志设置为false,以加快表对用户调整大小或拖动操作的响应速度。默认值为true。
     * 
     * 
     * @return  true if the table updates in real time
     * @see #setUpdateTableInRealTime
     */
    public boolean getUpdateTableInRealTime() {
        return updateTableInRealTime;
    }

    /**
     * Sets the default renderer to be used when no <code>headerRenderer</code>
     * is defined by a <code>TableColumn</code>.
     * <p>
     *  设置当<code> TableColumn </code>未定义<code> headerRenderer </code>时要使用的默认渲染器。
     * 
     * 
     * @param  defaultRenderer  the default renderer
     * @since 1.3
     */
    public void setDefaultRenderer(TableCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    /**
     * Returns the default renderer used when no <code>headerRenderer</code>
     * is defined by a <code>TableColumn</code>.
     * <p>
     *  返回当<code> TableColumn </code>未定义<code> headerRenderer </code>时使用的默认渲染器。
     * 
     * 
     * @return the default renderer
     * @since 1.3
     */
    @Transient
    public TableCellRenderer getDefaultRenderer() {
        return defaultRenderer;
    }

    /**
     * Returns the index of the column that <code>point</code> lies in, or -1 if it
     * lies out of bounds.
     *
     * <p>
     *  返回<code> point </code>所在列的索引,如果它超出边界则返回-1。
     * 
     * 
     * @return  the index of the column that <code>point</code> lies in, or -1 if it
     *          lies out of bounds
     */
    public int columnAtPoint(Point point) {
        int x = point.x;
        if (!getComponentOrientation().isLeftToRight()) {
            x = getWidthInRightToLeft() - x - 1;
        }
        return getColumnModel().getColumnIndexAtX(x);
    }

    /**
     * Returns the rectangle containing the header tile at <code>column</code>.
     * When the <code>column</code> parameter is out of bounds this method uses the
     * same conventions as the <code>JTable</code> method <code>getCellRect</code>.
     *
     * <p>
     *  返回包含<code>列</code>的标题标题的矩形。
     * 当<code> column </code>参数超出范围时,此方法使用与<code> JTable </code>方法<code> getCellRect </code>相同的约定。
     * 
     * 
     * @return  the rectangle containing the header tile at <code>column</code>
     * @see JTable#getCellRect
     */
    public Rectangle getHeaderRect(int column) {
        Rectangle r = new Rectangle();
        TableColumnModel cm = getColumnModel();

        r.height = getHeight();

        if (column < 0) {
            // x = width = 0;
            if( !getComponentOrientation().isLeftToRight() ) {
                r.x = getWidthInRightToLeft();
            }
        }
        else if (column >= cm.getColumnCount()) {
            if( getComponentOrientation().isLeftToRight() ) {
                r.x = getWidth();
            }
        }
        else {
            for(int i = 0; i < column; i++) {
                r.x += cm.getColumn(i).getWidth();
            }
            if( !getComponentOrientation().isLeftToRight() ) {
                r.x = getWidthInRightToLeft() - r.x - cm.getColumn(column).getWidth();
            }

            r.width = cm.getColumn(column).getWidth();
        }
        return r;
    }


    /**
     * Allows the renderer's tips to be used if there is text set.
     * <p>
     *  允许使用渲染器的提示,如果有文本集。
     * 
     * 
     * @param  event  the location of the event identifies the proper
     *                          renderer and, therefore, the proper tip
     * @return the tool tip for this component
     */
    public String getToolTipText(MouseEvent event) {
        String tip = null;
        Point p = event.getPoint();
        int column;

        // Locate the renderer under the event location
        if ((column = columnAtPoint(p)) != -1) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null) {
                renderer = defaultRenderer;
            }
            Component component = renderer.getTableCellRendererComponent(
                              getTable(), aColumn.getHeaderValue(), false, false,
                              -1, column);

            // Now have to see if the component is a JComponent before
            // getting the tip
            if (component instanceof JComponent) {
                // Convert the event to the renderer's coordinate system
                MouseEvent newEvent;
                Rectangle cellRect = getHeaderRect(column);

                p.translate(-cellRect.x, -cellRect.y);
                newEvent = new MouseEvent(component, event.getID(),
                                          event.getWhen(), event.getModifiers(),
                                          p.x, p.y, event.getXOnScreen(), event.getYOnScreen(),
                                          event.getClickCount(),
                                          event.isPopupTrigger(), MouseEvent.NOBUTTON);

                tip = ((JComponent)component).getToolTipText(newEvent);
            }
        }

        // No tip from the renderer get our own tip
        if (tip == null)
            tip = getToolTipText();

        return tip;
    }

//
// Managing TableHeaderUI
//

    /**
     * Returns the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @return the <code>TableHeaderUI</code> object that renders this component
     */
    public TableHeaderUI getUI() {
        return (TableHeaderUI)ui;
    }

    /**
     * Sets the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  设置呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @param ui  the <code>TableHeaderUI</code> L&amp;F object
     * @see UIDefaults#getUI
     */
    public void setUI(TableHeaderUI ui){
        if (this.ui != ui) {
            super.setUI(ui);
            repaint();
        }
    }

    /**
     * Notification from the <code>UIManager</code> that the look and feel
     * (L&amp;F) has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * <p>
     *  来自<code> UIManager </code>的通知,外观和感觉(L&amp; F)已更改。使用<code> UIManager </code>中的最新版本替换当前的UI对象。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI(){
        setUI((TableHeaderUI)UIManager.getUI(this));

        TableCellRenderer renderer = getDefaultRenderer();
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component)renderer);
        }
    }


    /**
     * Returns the suffix used to construct the name of the look and feel
     * (L&amp;F) class used to render this component.
     * <p>
     *  返回用于构造用于渲染此组件的外观和感觉(L&amp; F)类的名称的后缀。
     * 
     * 
     * @return the string "TableHeaderUI"
     *
     * @return "TableHeaderUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


//
// Managing models
//


    /**
     *  Sets the column model for this table to <code>newModel</code> and registers
     *  for listener notifications from the new column model.
     *
     * <p>
     * 将此表的列模型设置为<code> newModel </code>,并注册来自新列模型的侦听器通知。
     * 
     * 
     * @param   columnModel     the new data source for this table
     * @exception IllegalArgumentException
     *                          if <code>newModel</code> is <code>null</code>
     * @see     #getColumnModel
     * @beaninfo
     *  bound: true
     *  description: The object governing the way columns appear in the view.
     */
    public void setColumnModel(TableColumnModel columnModel) {
        if (columnModel == null) {
            throw new IllegalArgumentException("Cannot set a null ColumnModel");
        }
        TableColumnModel old = this.columnModel;
        if (columnModel != old) {
            if (old != null) {
                old.removeColumnModelListener(this);
            }
            this.columnModel = columnModel;
            columnModel.addColumnModelListener(this);

            firePropertyChange("columnModel", old, columnModel);
            resizeAndRepaint();
        }
    }

    /**
     * Returns the <code>TableColumnModel</code> that contains all column information
     * of this table header.
     *
     * <p>
     *  返回包含此表标题的所有列信息的<code> TableColumnModel </code>。
     * 
     * 
     * @return  the <code>columnModel</code> property
     * @see     #setColumnModel
     */
    public TableColumnModel getColumnModel() {
        return columnModel;
    }

//
// Implementing TableColumnModelListener interface
//

    /**
     * Invoked when a column is added to the table column model.
     * <p>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     *
     * <p>
     *  将列添加到表列模型时调用。
     * <p>
     *  应用程序代码不会明确使用这些方法,它们由<code> JTable </code>在内部使用。
     * 
     * 
     * @param e  the event received
     * @see TableColumnModelListener
     */
    public void columnAdded(TableColumnModelEvent e) { resizeAndRepaint(); }


    /**
     * Invoked when a column is removed from the table column model.
     * <p>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     *
     * <p>
     *  在从表列模型中删除列时调用。
     * <p>
     *  应用程序代码不会明确使用这些方法,它们由<code> JTable </code>在内部使用。
     * 
     * 
     * @param e  the event received
     * @see TableColumnModelListener
     */
    public void columnRemoved(TableColumnModelEvent e) { resizeAndRepaint(); }


    /**
     * Invoked when a column is repositioned.
     * <p>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     *
     * <p>
     *  在重新定位列时调用。
     * <p>
     *  应用程序代码不会明确使用这些方法,它们由<code> JTable </code>在内部使用。
     * 
     * 
     * @param e the event received
     * @see TableColumnModelListener
     */
    public void columnMoved(TableColumnModelEvent e) { repaint(); }


    /**
     * Invoked when a column is moved due to a margin change.
     * <p>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     *
     * <p>
     *  在由于保证金更改而移动列时调用。
     * <p>
     *  应用程序代码不会明确使用这些方法,它们由<code> JTable </code>在内部使用。
     * 
     * 
     * @param e the event received
     * @see TableColumnModelListener
     */
    public void columnMarginChanged(ChangeEvent e) { resizeAndRepaint(); }


    // --Redrawing the header is slow in cell selection mode.
    // --Since header selection is ugly and it is always clear from the
    // --view which columns are selected, don't redraw the header.
    /**
     * Invoked when the selection model of the <code>TableColumnModel</code>
     * is changed.  This method currently has no effect (the header is not
     * redrawn).
     * <p>
     * Application code will not use these methods explicitly, they
     * are used internally by <code>JTable</code>.
     *
     * <p>
     *  当<code> TableColumnModel </code>的选择模型更改时调用。此方法目前没有效果(标题不重新绘制)。
     * <p>
     *  应用程序代码不会明确使用这些方法,它们由<code> JTable </code>在内部使用。
     * 
     * 
     * @param e the event received
     * @see TableColumnModelListener
     */
    public void columnSelectionChanged(ListSelectionEvent e) { } // repaint(); }

//
//  Package Methods
//

    /**
     *  Returns the default column model object which is
     *  a <code>DefaultTableColumnModel</code>.  A subclass can override this
     *  method to return a different column model object
     *
     * <p>
     *  返回默认的列模型对象,它是一个<code> DefaultTableColumnModel </code>。子类可以覆盖此方法以返回不同的列模型对象
     * 
     * 
     * @return the default column model object
     */
    protected TableColumnModel createDefaultColumnModel() {
        return new DefaultTableColumnModel();
    }

    /**
     *  Returns a default renderer to be used when no header renderer
     *  is defined by a <code>TableColumn</code>.
     *
     * <p>
     *  返回当没有头文件渲染器由<code> TableColumn </code>定义时使用的默认渲染器。
     * 
     * 
     *  @return the default table column renderer
     * @since 1.3
     */
    protected TableCellRenderer createDefaultRenderer() {
        return new DefaultTableCellHeaderRenderer();
    }


    /**
     * Initializes the local variables and properties with default values.
     * Used by the constructor methods.
     * <p>
     * 使用默认值初始化局部变量和属性。由构造函数方法使用。
     * 
     */
    protected void initializeLocalVars() {
        setOpaque(true);
        table = null;
        reorderingAllowed = true;
        resizingAllowed = true;
        draggedColumn = null;
        draggedDistance = 0;
        resizingColumn = null;
        updateTableInRealTime = true;

        // I'm registered to do tool tips so we can draw tips for the
        // renderers
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.registerComponent(this);
        setDefaultRenderer(createDefaultRenderer());
    }

    /**
     * Sizes the header and marks it as needing display.  Equivalent
     * to <code>revalidate</code> followed by <code>repaint</code>.
     * <p>
     *  调整标题大小并将其标记为需要显示。等同于<code> revalidate </code>后跟<code> repaint </code>。
     * 
     */
    public void resizeAndRepaint() {
        revalidate();
        repaint();
    }

    /**
      *  Sets the header's <code>draggedColumn</code> to <code>aColumn</code>.
      *  <p>
      *  Application code will not use this method explicitly, it is used
      *  internally by the column dragging mechanism.
      *
      * <p>
      *  将标头的<code> draggedColumn </code>设置为<code> aColumn </code>。
      * <p>
      *  应用程序代码不会明确地使用此方法,它由列拖动机制在内部使用。
      * 
      * 
      *  @param  aColumn  the column being dragged, or <code>null</code> if
      *                 no column is being dragged
      */
    public void setDraggedColumn(TableColumn aColumn) {
        draggedColumn = aColumn;
    }

    /**
      *  Sets the header's <code>draggedDistance</code> to <code>distance</code>.
      * <p>
      *  将标头的<code> draggedDistance </code>设置为<code> distance </code>。
      * 
      * 
      *  @param distance  the distance dragged
      */
    public void setDraggedDistance(int distance) {
        draggedDistance = distance;
    }

    /**
      *  Sets the header's <code>resizingColumn</code> to <code>aColumn</code>.
      *  <p>
      *  Application code will not use this method explicitly, it
      *  is used internally by the column sizing mechanism.
      *
      * <p>
      *  将标头的<code> resizingColumn </code>设置为<code> aColumn </code>。
      * <p>
      *  应用程序代码不会明确地使用此方法,它由列大小调整机制内部使用。
      * 
      * 
      *  @param  aColumn  the column being resized, or <code>null</code> if
      *                 no column is being resized
      */
    public void setResizingColumn(TableColumn aColumn) {
        resizingColumn = aColumn;
    }

    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if ((ui != null) && (getUIClassID().equals(uiClassID))) {
            ui.installUI(this);
        }
    }

    private int getWidthInRightToLeft() {
        if ((table != null) &&
            (table.getAutoResizeMode() != JTable.AUTO_RESIZE_OFF)) {
            return table.getWidth();
        }
        return super.getWidth();
    }

    /**
     * Returns a string representation of this <code>JTableHeader</code>. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     * <P>
     * Overriding <code>paramString</code> to provide information about the
     * specific new aspects of the JFC components.
     *
     * <p>
     *  返回此<code> JTableHeader </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * <P>
     *  覆盖<code> paramString </code>以提供有关JFC组件的特定新方面的信息。
     * 
     * 
     * @return  a string representation of this <code>JTableHeader</code>
     */
    protected String paramString() {
        String reorderingAllowedString = (reorderingAllowed ?
                                          "true" : "false");
        String resizingAllowedString = (resizingAllowed ?
                                        "true" : "false");
        String updateTableInRealTimeString = (updateTableInRealTime ?
                                              "true" : "false");

        return super.paramString() +
        ",draggedDistance=" + draggedDistance +
        ",reorderingAllowed=" + reorderingAllowedString +
        ",resizingAllowed=" + resizingAllowedString +
        ",updateTableInRealTime=" + updateTableInRealTimeString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JTableHeader.
     * For JTableHeaders, the AccessibleContext takes the form of an
     * AccessibleJTableHeader.
     * A new AccessibleJTableHeader instance is created if necessary.
     *
     * <p>
     *  获取与此JTableHeader相关联的AccessibleContext。对于JTableHeaders,AccessibleContext采用AccessibleJTableHeader的形式。
     * 如果需要,将创建一个新的AccessibleJTableHeader实例。
     * 
     * 
     * @return an AccessibleJTableHeader that serves as the
     *         AccessibleContext of this JTableHeader
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJTableHeader();
        }
        return accessibleContext;
    }

    //
    // *** should also implement AccessibleSelection?
    // *** and what's up with keyboard navigation/manipulation?
    //
    /**
     * This class implements accessibility support for the
     * <code>JTableHeader</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to table header user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     * 这个类实现了对<code> JTableHeader </code>类的辅助功能支持。它提供了适用于表头用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJTableHeader extends AccessibleJComponent {

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PANEL;
        }

        /**
         * Returns the Accessible child, if one exists, contained at the local
         * coordinate Point.
         *
         * <p>
         *  返回Accessible child(如果存在)包含在本地坐标Point。
         * 
         * 
         * @param p The point defining the top-left corner of the Accessible,
         * given in the coordinate space of the object's parent.
         * @return the Accessible, if it exists, at the specified location;
         * else null
         */
        public Accessible getAccessibleAt(Point p) {
            int column;

            // Locate the renderer under the Point
            if ((column = JTableHeader.this.columnAtPoint(p)) != -1) {
                TableColumn aColumn = JTableHeader.this.columnModel.getColumn(column);
                TableCellRenderer renderer = aColumn.getHeaderRenderer();
                if (renderer == null) {
                    if (defaultRenderer != null) {
                        renderer = defaultRenderer;
                    } else {
                        return null;
                    }
                }
                Component component = renderer.getTableCellRendererComponent(
                                  JTableHeader.this.getTable(),
                                  aColumn.getHeaderValue(), false, false,
                                  -1, column);

                return new AccessibleJTableHeaderEntry(column, JTableHeader.this, JTableHeader.this.table);
            } else {
                return null;
            }
        }

        /**
         * Returns the number of accessible children in the object.  If all
         * of the children of this object implement Accessible, than this
         * method should return the number of children of this object.
         *
         * <p>
         *  返回对象中可访问的子项数。如果这个对象的所有子对象实现Accessible,那么这个方法应该返回这个对象的子对象数。
         * 
         * 
         * @return the number of accessible children in the object.
         */
        public int getAccessibleChildrenCount() {
            return JTableHeader.this.columnModel.getColumnCount();
        }

        /**
         * Return the nth Accessible child of the object.
         *
         * <p>
         *  返回对象的第n个Accessible子项。
         * 
         * 
         * @param i zero-based index of child
         * @return the nth Accessible child of the object
         */
        public Accessible getAccessibleChild(int i) {
            if (i < 0 || i >= getAccessibleChildrenCount()) {
                return null;
            } else {
                TableColumn aColumn = JTableHeader.this.columnModel.getColumn(i)
;
                TableCellRenderer renderer = aColumn.getHeaderRenderer();
                if (renderer == null) {
                    if (defaultRenderer != null) {
                        renderer = defaultRenderer;
                    } else {
                        return null;
                    }
                }
                Component component = renderer.getTableCellRendererComponent(
                                  JTableHeader.this.getTable(),
                                  aColumn.getHeaderValue(), false, false,
                                  -1, i);

                return new AccessibleJTableHeaderEntry(i, JTableHeader.this, JTableHeader.this.table);
            }
        }

      /**
       * This class provides an implementation of the Java Accessibility
       * API appropriate for JTableHeader entries.
       * <p>
       *  此类提供了适用于JTableHeader条目的Java辅助功能API的实现。
       * 
       */
        protected class AccessibleJTableHeaderEntry extends AccessibleContext
            implements Accessible, AccessibleComponent  {

            private JTableHeader parent;
            private int column;
            private JTable table;

            /**
             *  Constructs an AccessiblJTableHeaaderEntry
             * <p>
             *  构造AccessiblJTableHeaaderEntry
             * 
             * 
             * @since 1.4
             */
            public AccessibleJTableHeaderEntry(int c, JTableHeader p, JTable t) {
                parent = p;
                column = c;
                table = t;
                this.setAccessibleParent(parent);
            }

            /**
             * Get the AccessibleContext associated with this object.
             * In the implementation of the Java Accessibility API
             * for this class, returns this object, which serves as
             * its own AccessibleContext.
             *
             * <p>
             *  获取与此对象关联的AccessibleContext。在为此类实现Java辅助功能API时,返回此对象,该对象充当其自己的AccessibleContext。
             * 
             * 
             * @return this object
             */
            public AccessibleContext getAccessibleContext() {
                return this;
            }

            private AccessibleContext getCurrentAccessibleContext() {
                TableColumnModel tcm = table.getColumnModel();
                if (tcm != null) {
                    // Fixes 4772355 - ArrayOutOfBoundsException in
                    // JTableHeader
                    if (column < 0 || column >= tcm.getColumnCount()) {
                        return null;
                    }
                    TableColumn aColumn = tcm.getColumn(column);
                    TableCellRenderer renderer = aColumn.getHeaderRenderer();
                    if (renderer == null) {
                        if (defaultRenderer != null) {
                            renderer = defaultRenderer;
                        } else {
                            return null;
                        }
                    }
                    Component c = renderer.getTableCellRendererComponent(
                                      JTableHeader.this.getTable(),
                                      aColumn.getHeaderValue(), false, false,
                                      -1, column);
                    if (c instanceof Accessible) {
                        return ((Accessible) c).getAccessibleContext();
                    }
                }
                return null;
            }

            private Component getCurrentComponent() {
                TableColumnModel tcm = table.getColumnModel();
                if (tcm != null) {
                    // Fixes 4772355 - ArrayOutOfBoundsException in
                    // JTableHeader
                    if (column < 0 || column >= tcm.getColumnCount()) {
                        return null;
                    }
                    TableColumn aColumn = tcm.getColumn(column);
                    TableCellRenderer renderer = aColumn.getHeaderRenderer();
                    if (renderer == null) {
                        if (defaultRenderer != null) {
                            renderer = defaultRenderer;
                        } else {
                            return null;
                        }
                    }
                    return renderer.getTableCellRendererComponent(
                                      JTableHeader.this.getTable(),
                                      aColumn.getHeaderValue(), false, false,
                                      -1, column);
                } else {
                    return null;
                }
            }

        // AccessibleContext methods

            public String getAccessibleName() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    String name = ac.getAccessibleName();
                    if ((name != null) && (name != "")) {
                        // return the cell renderer's AccessibleName
                        return name;
                    }
                }
                if ((accessibleName != null) && (accessibleName != "")) {
                    return accessibleName;
                } else {
                    // fall back to the client property
                    String name = (String)getClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY);
                    if (name != null) {
                        return name;
                    } else {
                        return table.getColumnName(column);
                    }
                }
            }

            public void setAccessibleName(String s) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.setAccessibleName(s);
                } else {
                    super.setAccessibleName(s);
                }
            }

            //
            // *** should check toolTip text for desc. (needs MouseEvent)
            //
            public String getAccessibleDescription() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleDescription();
                } else {
                    return super.getAccessibleDescription();
                }
            }

            public void setAccessibleDescription(String s) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.setAccessibleDescription(s);
                } else {
                    super.setAccessibleDescription(s);
                }
            }

            public AccessibleRole getAccessibleRole() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleRole();
                } else {
                    return AccessibleRole.COLUMN_HEADER;
                }
            }

            public AccessibleStateSet getAccessibleStateSet() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    AccessibleStateSet states = ac.getAccessibleStateSet();
                    if (isShowing()) {
                        states.add(AccessibleState.SHOWING);
                    }
                    return states;
                } else {
                    return new AccessibleStateSet();  // must be non null?
                }
            }

            public int getAccessibleIndexInParent() {
                return column;
            }

            public int getAccessibleChildrenCount() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleChildrenCount();
                } else {
                    return 0;
                }
            }

            public Accessible getAccessibleChild(int i) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    Accessible accessibleChild = ac.getAccessibleChild(i);
                    ac.setAccessibleParent(this);
                    return accessibleChild;
                } else {
                    return null;
                }
            }

            public Locale getLocale() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getLocale();
                } else {
                    return null;
                }
            }

            public void addPropertyChangeListener(PropertyChangeListener l) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.addPropertyChangeListener(l);
                } else {
                    super.addPropertyChangeListener(l);
                }
            }

            public void removePropertyChangeListener(PropertyChangeListener l) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.removePropertyChangeListener(l);
                } else {
                    super.removePropertyChangeListener(l);
                }
            }

            public AccessibleAction getAccessibleAction() {
                return getCurrentAccessibleContext().getAccessibleAction();
            }

           /**
            * Get the AccessibleComponent associated with this object.  In the
            * implementation of the Java Accessibility API for this class,
            * return this object, which is responsible for implementing the
            * AccessibleComponent interface on behalf of itself.
            *
            * <p>
            * 获取与此对象关联的AccessibleComponent。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleComponent接口。
            * 
            * @return this object
            */
            public AccessibleComponent getAccessibleComponent() {
                return this; // to override getBounds()
            }

            public AccessibleSelection getAccessibleSelection() {
                return getCurrentAccessibleContext().getAccessibleSelection();
            }

            public AccessibleText getAccessibleText() {
                return getCurrentAccessibleContext().getAccessibleText();
            }

            public AccessibleValue getAccessibleValue() {
                return getCurrentAccessibleContext().getAccessibleValue();
            }


        // AccessibleComponent methods

            public Color getBackground() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getBackground();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.getBackground();
                    } else {
                        return null;
                    }
                }
            }

            public void setBackground(Color c) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setBackground(c);
                } else {
                    Component cp = getCurrentComponent();
                    if (cp != null) {
                        cp.setBackground(c);
                    }
                }
            }

            public Color getForeground() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getForeground();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.getForeground();
                    } else {
                        return null;
                    }
                }
            }

            public void setForeground(Color c) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setForeground(c);
                } else {
                    Component cp = getCurrentComponent();
                    if (cp != null) {
                        cp.setForeground(c);
                    }
                }
            }

            public Cursor getCursor() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getCursor();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.getCursor();
                    } else {
                        Accessible ap = getAccessibleParent();
                        if (ap instanceof AccessibleComponent) {
                            return ((AccessibleComponent) ap).getCursor();
                        } else {
                            return null;
                        }
                    }
                }
            }

            public void setCursor(Cursor c) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setCursor(c);
                } else {
                    Component cp = getCurrentComponent();
                    if (cp != null) {
                        cp.setCursor(c);
                    }
                }
            }

            public Font getFont() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getFont();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.getFont();
                    } else {
                        return null;
                    }
                }
            }

            public void setFont(Font f) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setFont(f);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.setFont(f);
                    }
                }
            }

            public FontMetrics getFontMetrics(Font f) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getFontMetrics(f);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.getFontMetrics(f);
                    } else {
                        return null;
                    }
                }
            }

            public boolean isEnabled() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).isEnabled();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.isEnabled();
                    } else {
                        return false;
                    }
                }
            }

            public void setEnabled(boolean b) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setEnabled(b);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.setEnabled(b);
                    }
                }
            }

            public boolean isVisible() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).isVisible();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.isVisible();
                    } else {
                        return false;
                    }
                }
            }

            public void setVisible(boolean b) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setVisible(b);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.setVisible(b);
                    }
                }
            }

            public boolean isShowing() {
                if (isVisible() && JTableHeader.this.isShowing()) {
                    return true;
                } else {
                    return false;
                }
            }

            public boolean contains(Point p) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    Rectangle r = ((AccessibleComponent) ac).getBounds();
                    return r.contains(p);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        Rectangle r = c.getBounds();
                        return r.contains(p);
                    } else {
                        return getBounds().contains(p);
                    }
                }
            }

            public Point getLocationOnScreen() {
                if (parent != null) {
                    Point parentLocation = parent.getLocationOnScreen();
                    Point componentLocation = getLocation();
                    componentLocation.translate(parentLocation.x, parentLocation.y);
                    return componentLocation;
                } else {
                    return null;
                }
            }

            public Point getLocation() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    Rectangle r = ((AccessibleComponent) ac).getBounds();
                    return r.getLocation();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        Rectangle r = c.getBounds();
                        return r.getLocation();
                    } else {
                        return getBounds().getLocation();
                    }
                }
            }

            public void setLocation(Point p) {
//                if ((parent != null)  && (parent.contains(p))) {
//                    ensureIndexIsVisible(indexInParent);
//                }
            }

            public Rectangle getBounds() {
                  Rectangle r = table.getCellRect(-1, column, false);
                  r.y = 0;
                  return r;

//                AccessibleContext ac = getCurrentAccessibleContext();
//                if (ac instanceof AccessibleComponent) {
//                    return ((AccessibleComponent) ac).getBounds();
//                } else {
//                  Component c = getCurrentComponent();
//                  if (c != null) {
//                      return c.getBounds();
//                  } else {
//                      Rectangle r = table.getCellRect(-1, column, false);
//                      r.y = 0;
//                      return r;
//                  }
//              }
            }

            public void setBounds(Rectangle r) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setBounds(r);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.setBounds(r);
                    }
                }
            }

            public Dimension getSize() {
                return getBounds().getSize();
//                AccessibleContext ac = getCurrentAccessibleContext();
//                if (ac instanceof AccessibleComponent) {
//                    Rectangle r = ((AccessibleComponent) ac).getBounds();
//                    return r.getSize();
//                } else {
//                    Component c = getCurrentComponent();
//                    if (c != null) {
//                        Rectangle r = c.getBounds();
//                        return r.getSize();
//                    } else {
//                        return getBounds().getSize();
//                    }
//                }
            }

            public void setSize (Dimension d) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setSize(d);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.setSize(d);
                    }
                }
            }

            public Accessible getAccessibleAt(Point p) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).getAccessibleAt(p);
                } else {
                    return null;
                }
            }

            public boolean isFocusTraversable() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    return ((AccessibleComponent) ac).isFocusTraversable();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        return c.isFocusTraversable();
                    } else {
                        return false;
                    }
                }
            }

            public void requestFocus() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).requestFocus();
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.requestFocus();
                    }
                }
            }

            public void addFocusListener(FocusListener l) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).addFocusListener(l);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.addFocusListener(l);
                    }
                }
            }

            public void removeFocusListener(FocusListener l) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).removeFocusListener(l);
                } else {
                    Component c = getCurrentComponent();
                    if (c != null) {
                        c.removeFocusListener(l);
                    }
                }
            }

        } // inner class AccessibleJTableHeaderElement

    }  // inner class AccessibleJTableHeader

}  // End of Class JTableHeader
