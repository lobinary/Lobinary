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
package javax.swing.text;

import java.awt.*;
import java.util.BitSet;
import java.util.Vector;
import javax.swing.SizeRequirements;
import javax.swing.event.DocumentEvent;

import javax.swing.text.html.HTML;

/**
 * <p>
 * Implements View interface for a table, that is composed of an
 * element structure where the child elements of the element
 * this view is responsible for represent rows and the child
 * elements of the row elements are cells.  The cell elements can
 * have an arbitrary element structure under them, which will
 * be built with the ViewFactory returned by the getViewFactory
 * method.
 * <pre>
 *
 * &nbsp;  TABLE
 * &nbsp;    ROW
 * &nbsp;      CELL
 * &nbsp;      CELL
 * &nbsp;    ROW
 * &nbsp;      CELL
 * &nbsp;      CELL
 *
 * </pre>
 * <p>
 * This is implemented as a hierarchy of boxes, the table itself
 * is a vertical box, the rows are horizontal boxes, and the cells
 * are vertical boxes.  The cells are allowed to span multiple
 * columns and rows.  By default, the table can be thought of as
 * being formed over a grid (i.e. somewhat like one would find in
 * gridbag layout), where table cells can request to span more
 * than one grid cell.  The default horizontal span of table cells
 * will be based upon this grid, but can be changed by reimplementing
 * the requested span of the cell (i.e. table cells can have independant
 * spans if desired).
 *
 * <p>
 * <p>
 *  实现一个表的View接口,它由一个元素结构组成,其中元素的子元素,这个视图负责表示行,行元素的子元素是单元格。
 * 单元格元素可以在它们之下具有任意的元素结构,这将使用由getViewFactory方法返回的ViewFactory构建。
 * <pre>
 * 
 *  &nbsp;表&nbsp; ROW&nbsp; CELL&nbsp; CELL&nbsp; ROW&nbsp; CELL&nbsp;细胞
 * 
 * </pre>
 * <p>
 *  这是作为框的层次实现的,表本身是一个垂直框,行是水平框,单元格是垂直框。允许单元格跨越多个列和行。
 * 默认情况下,表可以被认为是在网格上形成(即,有点像在网格袋布局中发现的),其中表格单元可以请求跨越多于一个网格单元。
 * 表格单元的默认水平跨度将基于该网格,但是可以通过重新实现单元的所请求的跨度来改变(即,如果需要,表格单元可以具有独立的跨度)。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     View
 */
public abstract class TableView extends BoxView {

    /**
     * Constructs a TableView for the given element.
     *
     * <p>
     *  构造给定元素的TableView。
     * 
     * 
     * @param elem the element that this view is responsible for
     */
    public TableView(Element elem) {
        super(elem, View.Y_AXIS);
        rows = new Vector<TableRow>();
        gridValid = false;
    }

    /**
     * Creates a new table row.
     *
     * <p>
     *  创建新表行。
     * 
     * 
     * @param elem an element
     * @return the row
     */
    protected TableRow createTableRow(Element elem) {
        return new TableRow(elem);
    }

    /**
    /* <p>
    /* 
     * @deprecated Table cells can now be any arbitrary
     * View implementation and should be produced by the
     * ViewFactory rather than the table.
     *
     * @param elem an element
     * @return the cell
     */
    @Deprecated
    protected TableCell createTableCell(Element elem) {
        return new TableCell(elem);
    }

    /**
     * The number of columns in the table.
     * <p>
     *  表中的列数。
     * 
     */
    int getColumnCount() {
        return columnSpans.length;
    }

    /**
     * Fetches the span (width) of the given column.
     * This is used by the nested cells to query the
     * sizes of grid locations outside of themselves.
     * <p>
     *  获取给定列的跨度(宽度)。嵌套单元格使用它来查询自己外部的网格位置的大小。
     * 
     */
    int getColumnSpan(int col) {
        return columnSpans[col];
    }

    /**
     * The number of rows in the table.
     * <p>
     *  表中的行数。
     * 
     */
    int getRowCount() {
        return rows.size();
    }

    /**
     * Fetches the span (height) of the given row.
     * <p>
     * 获取给定行的跨度(高度)。
     * 
     */
    int getRowSpan(int row) {
        View rv = getRow(row);
        if (rv != null) {
            return (int) rv.getPreferredSpan(Y_AXIS);
        }
        return 0;
    }

    TableRow getRow(int row) {
        if (row < rows.size()) {
            return rows.elementAt(row);
        }
        return null;
    }

    /**
     * Determines the number of columns occupied by
     * the table cell represented by given element.
     * <p>
     *  确定由给定元素表示的表单元占据的列数。
     * 
     */
    /*protected*/ int getColumnsOccupied(View v) {
        // PENDING(prinz) this code should be in the html
        // paragraph, but we can't add api to enable it.
        AttributeSet a = v.getElement().getAttributes();
        String s = (String) a.getAttribute(HTML.Attribute.COLSPAN);
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                // fall through to one column
            }
        }

        return 1;
    }

    /**
     * Determines the number of rows occupied by
     * the table cell represented by given element.
     * <p>
     *  // PENDING(prinz)这段代码应该在html //段落,但是我们不能添加api来启用它。 AttributeSet a = v.getElement()。
     * getAttributes(); String s =(String)a.getAttribute(HTML.Attribute.COLSPAN); if(s！= null){try {return Integer.parseInt(s); }
     *  catch(NumberFormatException nfe){//通过一列}}。
     *  // PENDING(prinz)这段代码应该在html //段落,但是我们不能添加api来启用它。 AttributeSet a = v.getElement()。
     * 
     *  return 1; }}
     * 
     *  / **确定由给定元素表示的表单元占用的行数。
     * 
     */
    /*protected*/ int getRowsOccupied(View v) {
        // PENDING(prinz) this code should be in the html
        // paragraph, but we can't add api to enable it.
        AttributeSet a = v.getElement().getAttributes();
        String s = (String) a.getAttribute(HTML.Attribute.ROWSPAN);
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                // fall through to one row
            }
        }

        return 1;
    }

    /* <p>
    /*  // PENDING(prinz)这段代码应该在html //段落,但是我们不能添加api来启用它。 AttributeSet a = v.getElement()。
    /* getAttributes(); String s =(String)a.getAttribute(HTML.Attribute.ROWSPAN); if(s！= null){try {return Integer.parseInt(s); }
    /*  catch(NumberFormatException nfe){//下降到一行}}。
    /*  // PENDING(prinz)这段代码应该在html //段落,但是我们不能添加api来启用它。 AttributeSet a = v.getElement()。
    /* 
    /*  return 1; }}
    /* 
    /* 
    /*protected*/ void invalidateGrid() {
        gridValid = false;
    }

    protected void forwardUpdate(DocumentEvent.ElementChange ec,
                                     DocumentEvent e, Shape a, ViewFactory f) {
        super.forwardUpdate(ec, e, a, f);
        // A change in any of the table cells usually effects the whole table,
        // so redraw it all!
        if (a != null) {
            Component c = getContainer();
            if (c != null) {
                Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a :
                                   a.getBounds();
                c.repaint(alloc.x, alloc.y, alloc.width, alloc.height);
            }
        }
    }

    /**
     * Change the child views.  This is implemented to
     * provide the superclass behavior and invalidate the
     * grid so that rows and columns will be recalculated.
     * <p>
     *  gridValid = false; }}
     * 
     *  protected void forwardUpdate(DocumentEvent.ElementChange ec,DocumentEvent e,Shape a,ViewFactory f){super.forwardUpdate(ec,e,a,f); //任何表格单元格的改变通常影响整个表格,//所以重绘所有的表格！ if(a！= null){Component c = getContainer(); if(c！= null){Rectangle alloc =(a instanceof Rectangle)? (Rectangle)a：a.getBounds(); c.repaint(alloc.x,alloc.y,alloc.width,alloc.height); }
     * }}。
     * 
     * / **更改子视图。这被实现以提供超类行为并且使网格无效,使得行和列将被重新计算。
     * 
     */
    public void replace(int offset, int length, View[] views) {
        super.replace(offset, length, views);
        invalidateGrid();
    }

    /**
     * Fill in the grid locations that are placeholders
     * for multi-column, multi-row, and missing grid
     * locations.
     * <p>
     *  填写多列,多行和缺少网格位置的占位符的网格位置。
     * 
     */
    void updateGrid() {
        if (! gridValid) {
            // determine which views are table rows and clear out
            // grid points marked filled.
            rows.removeAllElements();
            int n = getViewCount();
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                if (v instanceof TableRow) {
                    rows.addElement((TableRow) v);
                    TableRow rv = (TableRow) v;
                    rv.clearFilledColumns();
                    rv.setRow(i);
                }
            }

            int maxColumns = 0;
            int nrows = rows.size();
            for (int row = 0; row < nrows; row++) {
                TableRow rv = getRow(row);
                int col = 0;
                for (int cell = 0; cell < rv.getViewCount(); cell++, col++) {
                    View cv = rv.getView(cell);
                    // advance to a free column
                    for (; rv.isFilled(col); col++);
                    int rowSpan = getRowsOccupied(cv);
                    int colSpan = getColumnsOccupied(cv);
                    if ((colSpan > 1) || (rowSpan > 1)) {
                        // fill in the overflow entries for this cell
                        int rowLimit = row + rowSpan;
                        int colLimit = col + colSpan;
                        for (int i = row; i < rowLimit; i++) {
                            for (int j = col; j < colLimit; j++) {
                                if (i != row || j != col) {
                                    addFill(i, j);
                                }
                            }
                        }
                        if (colSpan > 1) {
                            col += colSpan - 1;
                        }
                    }
                }
                maxColumns = Math.max(maxColumns, col);
            }

            // setup the column layout/requirements
            columnSpans = new int[maxColumns];
            columnOffsets = new int[maxColumns];
            columnRequirements = new SizeRequirements[maxColumns];
            for (int i = 0; i < maxColumns; i++) {
                columnRequirements[i] = new SizeRequirements();
            }
            gridValid = true;
        }
    }

    /**
     * Mark a grid location as filled in for a cells overflow.
     * <p>
     *  将网格位置标记为填充以用于单元格溢出。
     * 
     */
    void addFill(int row, int col) {
        TableRow rv = getRow(row);
        if (rv != null) {
            rv.fillColumn(col);
        }
    }

    /**
     * Lays out the columns to fit within the given target span.
     * Returns the results through {@code offsets} and {@code spans}.
     *
     * <p>
     *  放出列以适合给定的目标范围。通过{@code offsets}和{@code span}返回结果。
     * 
     * 
     * @param targetSpan the given span for total of all the table
     *  columns
     * @param reqs the requirements desired for each column.  This
     *  is the column maximum of the cells minimum, preferred, and
     *  maximum requested span
     * @param spans the return value of how much to allocated to
     *  each column
     * @param offsets the return value of the offset from the
     *  origin for each column
     */
    protected void layoutColumns(int targetSpan, int[] offsets, int[] spans,
                                 SizeRequirements[] reqs) {
        // allocate using the convenience method on SizeRequirements
        SizeRequirements.calculateTiledPositions(targetSpan, null, reqs,
                                                 offsets, spans);
    }

    /**
     * Perform layout for the minor axis of the box (i.e. the
     * axis orthogonal to the axis that it represents).  The results
     * of the layout should be placed in the given arrays which represent
     * the allocations to the children along the minor axis.  This
     * is called by the superclass whenever the layout needs to be
     * updated along the minor axis.
     * <p>
     * This is implemented to call the
     * {@link #layoutColumns layoutColumns} method, and then
     * forward to the superclass to actually carry out the layout
     * of the tables rows.
     *
     * <p>
     *  执行箱子短轴(即与它代表的轴正交的轴)的布局。布局的结果应该放在给定的数组中,这些数组表示沿着短轴的子项分配。每当布局需要沿着短轴被更新时,这被超类调用。
     * <p>
     *  这是实现调用{@link #layoutColumns layoutColumns}方法,然后转发到超类,以实际执行表行的布局。
     * 
     * 
     * @param targetSpan the total span given to the view, which
     *  would be used to layout the children.
     * @param axis the axis being layed out.
     * @param offsets the offsets from the origin of the view for
     *  each of the child views.  This is a return value and is
     *  filled in by the implementation of this method.
     * @param spans the span of each child view.  This is a return
     *  value and is filled in by the implementation of this method.
     */
    protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        // make grid is properly represented
        updateGrid();

        // all of the row layouts are invalid, so mark them that way
        int n = getRowCount();
        for (int i = 0; i < n; i++) {
            TableRow row = getRow(i);
            row.layoutChanged(axis);
        }

        // calculate column spans
        layoutColumns(targetSpan, columnOffsets, columnSpans, columnRequirements);

        // continue normal layout
        super.layoutMinorAxis(targetSpan, axis, offsets, spans);
    }

    /**
     * Calculate the requirements for the minor axis.  This is called by
     * the superclass whenever the requirements need to be updated (i.e.
     * a preferenceChanged was messaged through this view).
     * <p>
     * This is implemented to calculate the requirements as the sum of the
     * requirements of the columns.
     * <p>
     *  计算短轴的要求。每当需要被更新时(即,通过这个视图传送preferenceChanged),这被超类调用。
     * <p>
     *  这被实现以将需求计算为列的需求的总和。
     * 
     */
    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
        updateGrid();

        // calculate column requirements for each column
        calculateColumnRequirements(axis);


        // the requirements are the sum of the columns.
        if (r == null) {
            r = new SizeRequirements();
        }
        long min = 0;
        long pref = 0;
        long max = 0;
        for (SizeRequirements req : columnRequirements) {
            min += req.minimum;
            pref += req.preferred;
            max += req.maximum;
        }
        r.minimum = (int) min;
        r.preferred = (int) pref;
        r.maximum = (int) max;
        r.alignment = 0;
        return r;
    }

    /*
    boolean shouldTrace() {
        AttributeSet a = getElement().getAttributes();
        Object o = a.getAttribute(HTML.Attribute.ID);
        if ((o != null) && o.equals("debug")) {
            return true;
        }
        return false;
    }
    /* <p>
    /*  boolean shouldTrace(){AttributeSet a = getElement()。
    /* getAttributes(); Object o = a.getAttribute(HTML.Attribute.ID); if((o！= null)&& o.equals("debug")){return true; }
    /*  return false; }}。
    /*  boolean shouldTrace(){AttributeSet a = getElement()。
    /* 
    */

    /**
     * Calculate the requirements for each column.  The calculation
     * is done as two passes over the table.  The table cells that
     * occupy a single column are scanned first to determine the
     * maximum of minimum, preferred, and maximum spans along the
     * give axis.  Table cells that span multiple columns are excluded
     * from the first pass.  A second pass is made to determine if
     * the cells that span multiple columns are satisfied.  If the
     * column requirements are not satisified, the needs of the
     * multi-column cell is mixed into the existing column requirements.
     * The calculation of the multi-column distribution is based upon
     * the proportions of the existing column requirements and taking
     * into consideration any constraining maximums.
     * <p>
     * 计算每个列的要求。计算以两次通过表的形式完成。首先扫描占用单个列的表格单元格,以确定沿给定轴的最小,最佳和最大跨度的最大值。跨越多个列的表单元格从第一遍排除。进行第二遍以确定是否满足跨越多个列的单元。
     * 如果不满足列要求,则将多列单元格的需求混合到现有列需求中。多列分布的计算基于现有列要求的比例,并考虑任何约束最大值。
     * 
     */
    void calculateColumnRequirements(int axis) {
        // pass 1 - single column cells
        boolean hasMultiColumn = false;
        int nrows = getRowCount();
        for (int i = 0; i < nrows; i++) {
            TableRow row = getRow(i);
            int col = 0;
            int ncells = row.getViewCount();
            for (int cell = 0; cell < ncells; cell++, col++) {
                View cv = row.getView(cell);
                for (; row.isFilled(col); col++); // advance to a free column
                int rowSpan = getRowsOccupied(cv);
                int colSpan = getColumnsOccupied(cv);
                if (colSpan == 1) {
                    checkSingleColumnCell(axis, col, cv);
                } else {
                    hasMultiColumn = true;
                    col += colSpan - 1;
                }
            }
        }

        // pass 2 - multi-column cells
        if (hasMultiColumn) {
            for (int i = 0; i < nrows; i++) {
                TableRow row = getRow(i);
                int col = 0;
                int ncells = row.getViewCount();
                for (int cell = 0; cell < ncells; cell++, col++) {
                    View cv = row.getView(cell);
                    for (; row.isFilled(col); col++); // advance to a free column
                    int colSpan = getColumnsOccupied(cv);
                    if (colSpan > 1) {
                        checkMultiColumnCell(axis, col, colSpan, cv);
                        col += colSpan - 1;
                    }
                }
            }
        }

        /*
        if (shouldTrace()) {
            System.err.println("calc:");
            for (int i = 0; i < columnRequirements.length; i++) {
                System.err.println(" " + i + ": " + columnRequirements[i]);
            }
        }
        /* <p>
        /*  if(shouldTrace()){System.err.println("calc："); for(int i = 0; i <columnRequirements.length; i ++){System.err.println(""+ i +"："+ columnRequirements [i]); }}。
        /* 
        */
    }

    /**
     * check the requirements of a table cell that spans a single column.
     * <p>
     *  请检查跨单个列的表单元格的要求。
     * 
     */
    void checkSingleColumnCell(int axis, int col, View v) {
        SizeRequirements req = columnRequirements[col];
        req.minimum = Math.max((int) v.getMinimumSpan(axis), req.minimum);
        req.preferred = Math.max((int) v.getPreferredSpan(axis), req.preferred);
        req.maximum = Math.max((int) v.getMaximumSpan(axis), req.maximum);
    }

    /**
     * check the requirements of a table cell that spans multiple
     * columns.
     * <p>
     *  请检查跨多个列的表单元格的要求。
     * 
     */
    void checkMultiColumnCell(int axis, int col, int ncols, View v) {
        // calculate the totals
        long min = 0;
        long pref = 0;
        long max = 0;
        for (int i = 0; i < ncols; i++) {
            SizeRequirements req = columnRequirements[col + i];
            min += req.minimum;
            pref += req.preferred;
            max += req.maximum;
        }

        // check if the minimum size needs adjustment.
        int cmin = (int) v.getMinimumSpan(axis);
        if (cmin > min) {
            /*
             * the columns that this cell spans need adjustment to fit
             * this table cell.... calculate the adjustments.  The
             * maximum for each cell is the maximum of the existing
             * maximum or the amount needed by the cell.
             * <p>
             *  该单元格跨越的列需要调整以适合此表格单元格....计算调整。每个单元格的最大值是现有最大值或单元所需的最大值。
             * 
             */
            SizeRequirements[] reqs = new SizeRequirements[ncols];
            for (int i = 0; i < ncols; i++) {
                SizeRequirements r = reqs[i] = columnRequirements[col + i];
                r.maximum = Math.max(r.maximum, (int) v.getMaximumSpan(axis));
            }
            int[] spans = new int[ncols];
            int[] offsets = new int[ncols];
            SizeRequirements.calculateTiledPositions(cmin, null, reqs,
                                                     offsets, spans);
            // apply the adjustments
            for (int i = 0; i < ncols; i++) {
                SizeRequirements req = reqs[i];
                req.minimum = Math.max(spans[i], req.minimum);
                req.preferred = Math.max(req.minimum, req.preferred);
                req.maximum = Math.max(req.preferred, req.maximum);
            }
        }

        // check if the preferred size needs adjustment.
        int cpref = (int) v.getPreferredSpan(axis);
        if (cpref > pref) {
            /*
             * the columns that this cell spans need adjustment to fit
             * this table cell.... calculate the adjustments.  The
             * maximum for each cell is the maximum of the existing
             * maximum or the amount needed by the cell.
             * <p>
             *  该单元格跨越的列需要调整以适合此表格单元格....计算调整。每个单元格的最大值是现有最大值或单元所需的最大值。
             * 
             */
            SizeRequirements[] reqs = new SizeRequirements[ncols];
            for (int i = 0; i < ncols; i++) {
                SizeRequirements r = reqs[i] = columnRequirements[col + i];
            }
            int[] spans = new int[ncols];
            int[] offsets = new int[ncols];
            SizeRequirements.calculateTiledPositions(cpref, null, reqs,
                                                     offsets, spans);
            // apply the adjustments
            for (int i = 0; i < ncols; i++) {
                SizeRequirements req = reqs[i];
                req.preferred = Math.max(spans[i], req.preferred);
                req.maximum = Math.max(req.preferred, req.maximum);
            }
        }

    }

    /**
     * Fetches the child view that represents the given position in
     * the model.  This is implemented to walk through the children
     * looking for a range that contains the given position.  In this
     * view the children do not necessarily have a one to one mapping
     * with the child elements.
     *
     * <p>
     * 获取表示模型中给定位置的子视图。这被实现为遍历寻找包含给定位置的范围的孩子。在这个视图中,子元素不必具有与子元素的一对一映射。
     * 
     * 
     * @param pos  the search position &gt;= 0
     * @param a  the allocation to the table on entry, and the
     *   allocation of the view containing the position on exit
     * @return  the view representing the given position, or
     *   <code>null</code> if there isn't one
     */
    protected View getViewAtPosition(int pos, Rectangle a) {
        int n = getViewCount();
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            int p0 = v.getStartOffset();
            int p1 = v.getEndOffset();
            if ((pos >= p0) && (pos < p1)) {
                // it's in this view.
                if (a != null) {
                    childAllocation(i, a);
                }
                return v;
            }
        }
        if (pos == getEndOffset()) {
            View v = getView(n - 1);
            if (a != null) {
                this.childAllocation(n - 1, a);
            }
            return v;
        }
        return null;
    }

    // ---- variables ----------------------------------------------------

    int[] columnSpans;
    int[] columnOffsets;
    SizeRequirements[] columnRequirements;
    Vector<TableRow> rows;
    boolean gridValid;
    static final private BitSet EMPTY = new BitSet();

    /**
     * View of a row in a row-centric table.
     * <p>
     *  一行在行为中心的表中的视图。
     * 
     */
    public class TableRow extends BoxView {

        /**
         * Constructs a TableView for the given element.
         *
         * <p>
         *  构造给定元素的TableView。
         * 
         * 
         * @param elem the element that this view is responsible for
         * @since 1.4
         */
        public TableRow(Element elem) {
            super(elem, View.X_AXIS);
            fillColumns = new BitSet();
        }

        void clearFilledColumns() {
            fillColumns.and(EMPTY);
        }

        void fillColumn(int col) {
            fillColumns.set(col);
        }

        boolean isFilled(int col) {
            return fillColumns.get(col);
        }

        /** get location in the overall set of rows */
        int getRow() {
            return row;
        }

        /**
         * set location in the overall set of rows, this is
         * set by the TableView.updateGrid() method.
         * <p>
         *  在整个行集中设置位置,这是由TableView.updateGrid()方法设置的。
         * 
         */
        void setRow(int row) {
            this.row = row;
        }

        /**
         * The number of columns present in this row.
         * <p>
         *  此行中显示的列数。
         * 
         */
        int getColumnCount() {
            int nfill = 0;
            int n = fillColumns.size();
            for (int i = 0; i < n; i++) {
                if (fillColumns.get(i)) {
                    nfill ++;
                }
            }
            return getViewCount() + nfill;
        }

        /**
         * Change the child views.  This is implemented to
         * provide the superclass behavior and invalidate the
         * grid so that rows and columns will be recalculated.
         * <p>
         *  更改子视图。这被实现以提供超类行为并且使网格无效,使得行和列将被重新计算。
         * 
         */
        public void replace(int offset, int length, View[] views) {
            super.replace(offset, length, views);
            invalidateGrid();
        }

        /**
         * Perform layout for the major axis of the box (i.e. the
         * axis that it represents).  The results of the layout should
         * be placed in the given arrays which represent the allocations
         * to the children along the major axis.
         * <p>
         * This is re-implemented to give each child the span of the column
         * width for the table, and to give cells that span multiple columns
         * the multi-column span.
         *
         * <p>
         *  执行箱子长轴(即它代表的轴)的布局。布局的结果应该放在给定的数组中,这些数组表示沿着长轴的子项的分配。
         * <p>
         *  这被重新实现,以给每个子表达表的列宽度的跨度,并且给予跨多个列的单元格多列跨度。
         * 
         * 
         * @param targetSpan the total span given to the view, which
         *  would be used to layout the children.
         * @param axis the axis being layed out.
         * @param offsets the offsets from the origin of the view for
         *  each of the child views.  This is a return value and is
         *  filled in by the implementation of this method.
         * @param spans the span of each child view.  This is a return
         *  value and is filled in by the implementation of this method.
         */
        protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
            int col = 0;
            int ncells = getViewCount();
            for (int cell = 0; cell < ncells; cell++, col++) {
                View cv = getView(cell);
                for (; isFilled(col); col++); // advance to a free column
                int colSpan = getColumnsOccupied(cv);
                spans[cell] = columnSpans[col];
                offsets[cell] = columnOffsets[col];
                if (colSpan > 1) {
                    int n = columnSpans.length;
                    for (int j = 1; j < colSpan; j++) {
                        // Because the table may be only partially formed, some
                        // of the columns may not yet exist.  Therefore we check
                        // the bounds.
                        if ((col+j) < n) {
                            spans[cell] += columnSpans[col+j];
                        }
                    }
                    col += colSpan - 1;
                }
            }
        }

        /**
         * Perform layout for the minor axis of the box (i.e. the
         * axis orthogonal to the axis that it represents).  The results
         * of the layout should be placed in the given arrays which represent
         * the allocations to the children along the minor axis.  This
         * is called by the superclass whenever the layout needs to be
         * updated along the minor axis.
         * <p>
         * This is implemented to delegate to the superclass, then adjust
         * the span for any cell that spans multiple rows.
         *
         * <p>
         *  执行箱子短轴(即与它代表的轴正交的轴)的布局。布局的结果应该放在给定的数组中,这些数组表示沿着短轴的子项分配。每当布局需要沿着短轴被更新时,这被超类调用。
         * <p>
         *  这是实现委托到超类,然后调整跨越多行的任何单元格的跨度。
         * 
         * 
         * @param targetSpan the total span given to the view, which
         *  would be used to layout the children.
         * @param axis the axis being layed out.
         * @param offsets the offsets from the origin of the view for
         *  each of the child views.  This is a return value and is
         *  filled in by the implementation of this method.
         * @param spans the span of each child view.  This is a return
         *  value and is filled in by the implementation of this method.
         */
        protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
            super.layoutMinorAxis(targetSpan, axis, offsets, spans);
            int col = 0;
            int ncells = getViewCount();
            for (int cell = 0; cell < ncells; cell++, col++) {
                View cv = getView(cell);
                for (; isFilled(col); col++); // advance to a free column
                int colSpan = getColumnsOccupied(cv);
                int rowSpan = getRowsOccupied(cv);
                if (rowSpan > 1) {
                    for (int j = 1; j < rowSpan; j++) {
                        // test bounds of each row because it may not exist
                        // either because of error or because the table isn't
                        // fully loaded yet.
                        int row = getRow() + j;
                        if (row < TableView.this.getViewCount()) {
                            int span = TableView.this.getSpan(Y_AXIS, getRow()+j);
                            spans[cell] += span;
                        }
                    }
                }
                if (colSpan > 1) {
                    col += colSpan - 1;
                }
            }
        }

        /**
         * Determines the resizability of the view along the
         * given axis.  A value of 0 or less is not resizable.
         *
         * <p>
         * 确定沿给定轴的视图的可重新调整性。值为0或更小不可调整大小。
         * 
         * 
         * @param axis may be either View.X_AXIS or View.Y_AXIS
         * @return the resize weight
         * @exception IllegalArgumentException for an invalid axis
         */
        public int getResizeWeight(int axis) {
            return 1;
        }

        /**
         * Fetches the child view that represents the given position in
         * the model.  This is implemented to walk through the children
         * looking for a range that contains the given position.  In this
         * view the children do not necessarily have a one to one mapping
         * with the child elements.
         *
         * <p>
         *  获取表示模型中给定位置的子视图。这被实现为遍历寻找包含给定位置的范围的孩子。在这个视图中,子元素不必具有与子元素的一对一映射。
         * 
         * 
         * @param pos  the search position &gt;= 0
         * @param a  the allocation to the table on entry, and the
         *   allocation of the view containing the position on exit
         * @return  the view representing the given position, or
         *   <code>null</code> if there isn't one
         */
        protected View getViewAtPosition(int pos, Rectangle a) {
            int n = getViewCount();
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                int p0 = v.getStartOffset();
                int p1 = v.getEndOffset();
                if ((pos >= p0) && (pos < p1)) {
                    // it's in this view.
                    if (a != null) {
                        childAllocation(i, a);
                    }
                    return v;
                }
            }
            if (pos == getEndOffset()) {
                View v = getView(n - 1);
                if (a != null) {
                    this.childAllocation(n - 1, a);
                }
                return v;
            }
            return null;
        }

        /** columns filled by multi-column or multi-row cells */
        BitSet fillColumns;
        /** the row within the overall grid */
        int row;
    }

    /**
    /* <p>
    /* 
     * @deprecated  A table cell can now be any View implementation.
     */
    @Deprecated
    public class TableCell extends BoxView implements GridCell {

        /**
         * Constructs a TableCell for the given element.
         *
         * <p>
         *  构造给定元素的TableCell。
         * 
         * 
         * @param elem the element that this view is responsible for
         * @since 1.4
         */
        public TableCell(Element elem) {
            super(elem, View.Y_AXIS);
        }

        // --- GridCell methods -------------------------------------

        /**
         * Gets the number of columns this cell spans (e.g. the
         * grid width).
         *
         * <p>
         *  获取此单元格跨越的列数(例如,网格宽度)。
         * 
         * 
         * @return the number of columns
         */
        public int getColumnCount() {
            return 1;
        }

        /**
         * Gets the number of rows this cell spans (that is, the
         * grid height).
         *
         * <p>
         *  获取此单元格跨越的行数(即网格高度)。
         * 
         * 
         * @return the number of rows
         */
        public int getRowCount() {
            return 1;
        }


        /**
         * Sets the grid location.
         *
         * <p>
         *  设置网格位置。
         * 
         * 
         * @param row the row &gt;= 0
         * @param col the column &gt;= 0
         */
        public void setGridLocation(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Gets the row of the grid location
         * <p>
         *  获取网格位置的行
         * 
         */
        public int getGridRow() {
            return row;
        }

        /**
         * Gets the column of the grid location
         * <p>
         *  获取网格位置的列
         * 
         */
        public int getGridColumn() {
            return col;
        }

        int row;
        int col;
    }

    /**
     * <em>
     * THIS IS NO LONGER USED, AND WILL BE REMOVED IN THE
     * NEXT RELEASE.  THE JCK SIGNATURE TEST THINKS THIS INTERFACE
     * SHOULD EXIST
     * </em>
     * <p>
     * <em>
     *  这不是长期使用,并将在下一次发布中删除。 JCK签名测试认为这个接口应该存在
     * </em>
     */
    interface GridCell {

        /**
         * Sets the grid location.
         *
         * <p>
         *  设置网格位置。
         * 
         * 
         * @param row the row &gt;= 0
         * @param col the column &gt;= 0
         */
        public void setGridLocation(int row, int col);

        /**
         * Gets the row of the grid location
         * <p>
         *  获取网格位置的行
         * 
         */
        public int getGridRow();

        /**
         * Gets the column of the grid location
         * <p>
         *  获取网格位置的列
         * 
         */
        public int getGridColumn();

        /**
         * Gets the number of columns this cell spans (e.g. the
         * grid width).
         *
         * <p>
         *  获取此单元格跨越的列数(例如,网格宽度)。
         * 
         * 
         * @return the number of columns
         */
        public int getColumnCount();

        /**
         * Gets the number of rows this cell spans (that is, the
         * grid height).
         *
         * <p>
         *  获取此单元格跨越的行数(即网格高度)。
         * 
         * @return the number of rows
         */
        public int getRowCount();

    }

}
