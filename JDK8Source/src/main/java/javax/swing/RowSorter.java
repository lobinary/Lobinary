/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import javax.swing.SortOrder;
import javax.swing.event.*;
import java.util.*;

/**
 * <code>RowSorter</code> provides the basis for sorting and filtering.
 * Beyond creating and installing a <code>RowSorter</code>, you very rarely
 * need to interact with one directly.  Refer to
 * {@link javax.swing.table.TableRowSorter TableRowSorter} for a concrete
 * implementation of <code>RowSorter</code> for <code>JTable</code>.
 * <p>
 * <code>RowSorter</code>'s primary role is to provide a mapping between
 * two coordinate systems: that of the view (for example a
 * <code>JTable</code>) and that of the underlying data source, typically a
 * model.
 * <p>
 * The view invokes the following methods on the <code>RowSorter</code>:
 * <ul>
 * <li><code>toggleSortOrder</code> &#151; The view invokes this when the
 *     appropriate user gesture has occurred to trigger a sort.  For example,
 *     the user clicked a column header in a table.
 * <li>One of the model change methods &#151; The view invokes a model
 *     change method when the underlying model
 *     has changed.  There may be order dependencies in how the events are
 *     delivered, so a <code>RowSorter</code> should not update its mapping
 *     until one of these methods is invoked.
 * </ul>
 * Because the view makes extensive use of  the
 * <code>convertRowIndexToModel</code>,
 * <code>convertRowIndexToView</code> and <code>getViewRowCount</code> methods,
 * these methods need to be fast.
 * <p>
 * <code>RowSorter</code> provides notification of changes by way of
 * <code>RowSorterListener</code>.  Two types of notification are sent:
 * <ul>
 * <li><code>RowSorterEvent.Type.SORT_ORDER_CHANGED</code> &#151; notifies
 *     listeners that the sort order has changed.  This is typically followed
 *     by a notification that the sort has changed.
 * <li><code>RowSorterEvent.Type.SORTED</code> &#151; notifies listeners that
 *     the mapping maintained by the <code>RowSorter</code> has changed in
 *     some way.
 * </ul>
 * <code>RowSorter</code> implementations typically don't have a one-to-one
 * mapping with the underlying model, but they can.
 * For example, if a database does the sorting,
 * <code>toggleSortOrder</code> might call through to the database
 * (on a background thread), and override the mapping methods to return the
 * argument that is passed in.
 * <p>
 * Concrete implementations of <code>RowSorter</code>
 * need to reference a model such as <code>TableModel</code> or
 * <code>ListModel</code>.  The view classes, such as
 * <code>JTable</code> and <code>JList</code>, will also have a
 * reference to the model.  To avoid ordering dependencies,
 * <code>RowSorter</code> implementations should not install a
 * listener on the model.  Instead the view class will call into the
 * <code>RowSorter</code> when the model changes.  For
 * example, if a row is updated in a <code>TableModel</code>
 * <code>JTable</code> invokes <code>rowsUpdated</code>.
 * When the model changes, the view may call into any of the following methods:
 * <code>modelStructureChanged</code>, <code>allRowsChanged</code>,
 * <code>rowsInserted</code>, <code>rowsDeleted</code> and
 * <code>rowsUpdated</code>.
 *
 * <p>
 *  <code> RowSorter </code>提供了排序和过滤的基础。除了创建和安装一个<code> RowSorter </code>,你很少需要直接与一个交互。
 * 有关<code> JTable </code>的<code> RowSorter </code>的具体实现,请参阅{@link javax.swing.table.TableRowSorter TableRowSorter}
 * 。
 *  <code> RowSorter </code>提供了排序和过滤的基础。除了创建和安装一个<code> RowSorter </code>,你很少需要直接与一个交互。
 * <p>
 *  <code> RowSorter </code>的主要作用是提供两个坐标系统之间的映射：视图(例如<code> JTable </code>)和底层数据源。
 * <p>
 *  视图在<code> RowSorter </code>上调用以下方法：
 * <ul>
 *  <li> <code> toggleSortOrder </code>  - 当发生适当的用户手势触发排序时,视图将调用此方法。例如,用户单击表中的列标题。
 *  <li>模型更改方法之一 - 当基础模型更改时,视图调用模型更改方法。
 * 可能存在事件如何传递的顺序依赖性,所以一个<code> RowSorter </code>不应该更新其映射,直到这些方法之一被调用。
 * </ul>
 *  因为视图广泛使用<code> convertRowIndexToModel </code>,<code> convertRowIndexToView </code>和<code> getViewRow
 * Count </code>方法,这些方法需要快速。
 * <p>
 * <code> RowSorter </code>通过<code> RowSorterListener </code>提供更改的通知。发送两种类型的通知：
 * <ul>
 *  <li> <code> RowSorterEvent.Type.SORT_ORDER_CHANGED </code>  - 通知侦听器排序顺序已更改。这通常后跟一个通知,排序已更改。
 *  <li> <code> RowSorterEvent.Type.SORTED </code>  - 通知侦听器</code> RowSorter </code>维护的映射以某种方式更改。
 * </ul>
 *  <code> RowSorter </code>实现通常没有与底层模型的一对一映射,但他们可以。
 * 例如,如果数据库进行排序,<code> toggleSortOrder </code>可能调用到数据库(在后台线程上),并重写映射方法以返回传入的参数。
 * <p>
 * <code> RowSorter </code>的具体实现需要引用诸如<code> TableModel </code>或<code> ListModel </code>之类的模型。
 * 视图类,例如<code> JTable </code>和<code> JList </code>,也将引用该模型。
 * 为了避免排序依赖,<code> RowSorter </code>实现不应该在模型上安装一个监听器。相反,当模型更改时,视图类将调用<code> RowSorter </code>。
 * 例如,如果在<code> TableModel </code> <code> JTable </code>中调用一行,则调用<code> rowsUpdated </code>。
 * 当模型更改时,视图可以调用以下任何方法：<code> modelStructureChanged </code>,<code> allRowsChanged </code>,<code> rowsIns
 * erted </code>,<code> rowsDeleted </code >和<code> rowsUpdated </code>。
 * 例如,如果在<code> TableModel </code> <code> JTable </code>中调用一行,则调用<code> rowsUpdated </code>。
 * 
 * 
 * @param <M> the type of the underlying model
 * @see javax.swing.table.TableRowSorter
 * @since 1.6
 */
public abstract class RowSorter<M> {
    private EventListenerList listenerList = new EventListenerList();

    /**
     * Creates a <code>RowSorter</code>.
     * <p>
     *  创建<code> RowSorter </code>。
     * 
     */
    public RowSorter() {
    }

    /**
     * Returns the underlying model.
     *
     * <p>
     *  返回底层模型。
     * 
     * 
     * @return the underlying model
     */
    public abstract M getModel();

    /**
     * Reverses the sort order of the specified column.  It is up to
     * subclasses to provide the exact behavior when invoked.  Typically
     * this will reverse the sort order from ascending to descending (or
     * descending to ascending) if the specified column is already the
     * primary sorted column; otherwise, makes the specified column
     * the primary sorted column, with an ascending sort order.  If
     * the specified column is not sortable, this method has no
     * effect.
     * <p>
     * If this results in changing the sort order and sorting, the
     * appropriate <code>RowSorterListener</code> notification will be
     * sent.
     *
     * <p>
     *  反转指定列的排序顺序。它是由子类提供的确切行为时调用。通常,如果指定的列已经是主要排序列,这将会将排序顺序从升序到降序(或降序到升序)反转;否则,使指定的列为主排序列,并按升序排序。
     * 如果指定的列不可排序,则此方法不起作用。
     * <p>
     *  如果这导致更改排序顺序和排序,将发送相应的<code> RowSorterListener </code>通知。
     * 
     * 
     * @param column the column to toggle the sort ordering of, in
     *        terms of the underlying model
     * @throws IndexOutOfBoundsException if column is outside the range of
     *         the underlying model
     */
    public abstract void toggleSortOrder(int column);

    /**
     * Returns the location of <code>index</code> in terms of the
     * underlying model.  That is, for the row <code>index</code> in
     * the coordinates of the view this returns the row index in terms
     * of the underlying model.
     *
     * <p>
     * 根据底层模型返回<code> index </code>的位置。也就是说,对于视图坐标中的行<code> index </code>,这将返回基础模型方面的行索引。
     * 
     * 
     * @param index the row index in terms of the underlying view
     * @return row index in terms of the view
     * @throws IndexOutOfBoundsException if <code>index</code> is outside the
     *         range of the view
     */
    public abstract int convertRowIndexToModel(int index);

    /**
     * Returns the location of <code>index</code> in terms of the
     * view.  That is, for the row <code>index</code> in the
     * coordinates of the underlying model this returns the row index
     * in terms of the view.
     *
     * <p>
     *  根据视图返回<code> index </code>的位置。也就是说,对于底层模型的坐标中的<code> index </code>行,这将返回视图方面的行索引。
     * 
     * 
     * @param index the row index in terms of the underlying model
     * @return row index in terms of the view, or -1 if index has been
     *         filtered out of the view
     * @throws IndexOutOfBoundsException if <code>index</code> is outside
     *         the range of the model
     */
    public abstract int convertRowIndexToView(int index);

    /**
     * Sets the current sort keys.
     *
     * <p>
     *  设置当前排序键。
     * 
     * 
     * @param keys the new <code>SortKeys</code>; <code>null</code>
     *        is a shorthand for specifying an empty list,
     *        indicating that the view should be unsorted
     */
    public abstract void setSortKeys(List<? extends SortKey> keys);

    /**
     * Returns the current sort keys.  This must return a {@code
     * non-null List} and may return an unmodifiable {@code List}. If
     * you need to change the sort keys, make a copy of the returned
     * {@code List}, mutate the copy and invoke {@code setSortKeys}
     * with the new list.
     *
     * <p>
     *  返回当前排序键。这必须返回{@code非空List},并可能返回不可修改的{@code List}。
     * 如果需要更改排序键,请创建一个返回的{@code List}的副本,改变副本并使用新列表调用{@code setSortKeys}。
     * 
     * 
     * @return the current sort order
     */
    public abstract List<? extends SortKey> getSortKeys();

    /**
     * Returns the number of rows in the view.  If the contents have
     * been filtered this might differ from the row count of the
     * underlying model.
     *
     * <p>
     *  返回视图中的行数。如果已过滤内容,则可能与底层模型的行计数不同。
     * 
     * 
     * @return number of rows in the view
     * @see #getModelRowCount
     */
    public abstract int getViewRowCount();

    /**
     * Returns the number of rows in the underlying model.
     *
     * <p>
     *  返回底层模型中的行数。
     * 
     * 
     * @return number of rows in the underlying model
     * @see #getViewRowCount
     */
    public abstract int getModelRowCount();

    /**
     * Invoked when the underlying model structure has completely
     * changed.  For example, if the number of columns in a
     * <code>TableModel</code> changed, this method would be invoked.
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     * <p>
     *  当底层模型结构完全更改时调用。例如,如果<code> TableModel </code>中的列数已更改,则会调用此方法。
     * <p>
     *  通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     */
    public abstract void modelStructureChanged();

    /**
     * Invoked when the contents of the underlying model have
     * completely changed. The structure of the table is the same,
     * only the contents have changed. This is typically sent when it
     * is too expensive to characterize the change in terms of the
     * other methods.
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     * <p>
     *  当底层模型的内容完全更改时调用。表的结构是相同的,只有内容已经改变。这通常在以其它方法表征改变太贵时发送。
     * <p>
     * 通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     */
    public abstract void allRowsChanged();

    /**
     * Invoked when rows have been inserted into the underlying model
     * in the specified range (inclusive).
     * <p>
     * The arguments give the indices of the effected range.
     * The first argument is in terms of the model before the change, and
     * must be less than or equal to the size of the model before the change.
     * The second argument is in terms of the model after the change and must
     * be less than the size of the model after the change. For example,
     * if you have a 5-row model and add 3 items to the end of the model
     * the indices are 5, 7.
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     *
     * <p>
     *  当行已插入到指定范围(包括)中的底层模型中时调用。
     * <p>
     *  参数给出了受影响范围的索引。第一个参数是根据改变之前的模型,并且必须小于或等于改变之前的模型大小。第二个参数是根据改变后的模型,并且必须小于改变后的模型的大小。
     * 例如,如果您有一个5行模型,并在模型的末尾添加3个项目,索引为5,7。
     * <p>
     *  通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     * 
     * @param firstRow the first row
     * @param endRow the last row
     * @throws IndexOutOfBoundsException if either argument is invalid, or
     *         <code>firstRow</code> &gt; <code>endRow</code>
     */
    public abstract void rowsInserted(int firstRow, int endRow);

    /**
     * Invoked when rows have been deleted from the underlying model
     * in the specified range (inclusive).
     * <p>
     * The arguments give the indices of the effected range and
     * are in terms of the model <b>before</b> the change.
     * For example, if you have a 5-row model and delete 3 items from the end
     * of the model the indices are 2, 4.
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     *
     * <p>
     *  在已从指定范围(包括)中的底层模型中删除行时调用。
     * <p>
     *  参数给出了受影响范围的索引,并且根据<b>之前</b>的模型。例如,如果您有一个5行模型,并从模型末尾删除3个项目,索引为2,4。
     * <p>
     *  通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     * 
     * @param firstRow the first row
     * @param endRow the last row
     * @throws IndexOutOfBoundsException if either argument is outside
     *         the range of the model before the change, or
     *         <code>firstRow</code> &gt; <code>endRow</code>
     */
    public abstract void rowsDeleted(int firstRow, int endRow);

    /**
     * Invoked when rows have been changed in the underlying model
     * between the specified range (inclusive).
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     *
     * <p>
     *  在底层模型中在指定范围(包括)之间更改行时调用。
     * <p>
     *  通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     * 
     * @param firstRow the first row, in terms of the underlying model
     * @param endRow the last row, in terms of the underlying model
     * @throws IndexOutOfBoundsException if either argument is outside
     *         the range of the underlying model, or
     *         <code>firstRow</code> &gt; <code>endRow</code>
     */
    public abstract void rowsUpdated(int firstRow, int endRow);

    /**
     * Invoked when the column in the rows have been updated in
     * the underlying model between the specified range.
     * <p>
     * You normally do not call this method.  This method is public
     * to allow view classes to call it.
     *
     * <p>
     *  当底层模型中指定范围内的行中的列已更新时调用。
     * <p>
     * 通常不调用此方法。这个方法是public的,允许视图类调用它。
     * 
     * 
     * @param firstRow the first row, in terms of the underlying model
     * @param endRow the last row, in terms of the underlying model
     * @param column the column that has changed, in terms of the underlying
     *        model
     * @throws IndexOutOfBoundsException if either argument is outside
     *         the range of the underlying model after the change,
     *         <code>firstRow</code> &gt; <code>endRow</code>, or
     *         <code>column</code> is outside the range of the underlying
     *          model
     */
    public abstract void rowsUpdated(int firstRow, int endRow, int column);

    /**
     * Adds a <code>RowSorterListener</code> to receive notification
     * about this <code>RowSorter</code>.  If the same
     * listener is added more than once it will receive multiple
     * notifications.  If <code>l</code> is <code>null</code> nothing
     * is done.
     *
     * <p>
     *  添加<code> RowSorterListener </code>以接收有关此<code> RowSorter </code>的通知。如果同一个监听器被多次添加,它将接收多个通知。
     * 如果<code> l </code>是<code> null </code>,则不做任何操作。
     * 
     * 
     * @param l the <code>RowSorterListener</code>
     */
    public void addRowSorterListener(RowSorterListener l) {
        listenerList.add(RowSorterListener.class, l);
    }

    /**
     * Removes a <code>RowSorterListener</code>.  If
     * <code>l</code> is <code>null</code> nothing is done.
     *
     * <p>
     *  删除<code> RowSorterListener </code>。如果<code> l </code>是<code> null </code>,则不做任何操作。
     * 
     * 
     * @param l the <code>RowSorterListener</code>
     */
    public void removeRowSorterListener(RowSorterListener l) {
        listenerList.remove(RowSorterListener.class, l);
    }

    /**
     * Notifies listener that the sort order has changed.
     * <p>
     *  通知侦听器排序顺序已更改。
     * 
     */
    protected void fireSortOrderChanged() {
        fireRowSorterChanged(new RowSorterEvent(this));
    }

    /**
     * Notifies listener that the mapping has changed.
     *
     * <p>
     *  通知侦听器映射已更改。
     * 
     * 
     * @param lastRowIndexToModel the mapping from model indices to
     *        view indices prior to the sort, may be <code>null</code>
     */
    protected void fireRowSorterChanged(int[] lastRowIndexToModel) {
        fireRowSorterChanged(new RowSorterEvent(this,
                RowSorterEvent.Type.SORTED, lastRowIndexToModel));
    }

    void fireRowSorterChanged(RowSorterEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == RowSorterListener.class) {
                ((RowSorterListener)listeners[i + 1]).
                        sorterChanged(event);
            }
        }
    }

    /**
     * SortKey describes the sort order for a particular column.  The
     * column index is in terms of the underlying model, which may differ
     * from that of the view.
     *
     * <p>
     *  SortKey描述特定列的排序顺序。列索引是根据底层模型,它可能与视图的不同。
     * 
     * 
     * @since 1.6
     */
    public static class SortKey {
        private int column;
        private SortOrder sortOrder;

        /**
         * Creates a <code>SortKey</code> for the specified column with
         * the specified sort order.
         *
         * <p>
         *  为指定的具有指定排序顺序的列创建<code> SortKey </code>。
         * 
         * 
         * @param column index of the column, in terms of the model
         * @param sortOrder the sorter order
         * @throws IllegalArgumentException if <code>sortOrder</code> is
         *         <code>null</code>
         */
        public SortKey(int column, SortOrder sortOrder) {
            if (sortOrder == null) {
                throw new IllegalArgumentException(
                        "sort order must be non-null");
            }
            this.column = column;
            this.sortOrder = sortOrder;
        }

        /**
         * Returns the index of the column.
         *
         * <p>
         *  返回列的索引。
         * 
         * 
         * @return index of column
         */
        public final int getColumn() {
            return column;
        }

        /**
         * Returns the sort order of the column.
         *
         * <p>
         *  返回列的排序顺序。
         * 
         * 
         * @return the sort order of the column
         */
        public final SortOrder getSortOrder() {
            return sortOrder;
        }

        /**
         * Returns the hash code for this <code>SortKey</code>.
         *
         * <p>
         *  返回此<code> SortKey </code>的哈希码。
         * 
         * 
         * @return hash code
         */
        public int hashCode() {
            int result = 17;
            result = 37 * result + column;
            result = 37 * result + sortOrder.hashCode();
            return result;
        }

        /**
         * Returns true if this object equals the specified object.
         * If the specified object is a <code>SortKey</code> and
         * references the same column and sort order, the two objects
         * are equal.
         *
         * <p>
         *  如果此对象等于指定的对象,则返回true。如果指定的对象是<code> SortKey </code>并引用相同的列和排序顺序,则这两个对象是相等的。
         * 
         * @param o the object to compare to
         * @return true if <code>o</code> is equal to this <code>SortKey</code>
         */
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof SortKey) {
                return (((SortKey)o).column == column &&
                        ((SortKey)o).sortOrder == sortOrder);
            }
            return false;
        }
    }
}
