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
package javax.swing.table;

import java.text.Collator;
import java.util.*;
import javax.swing.DefaultRowSorter;
import javax.swing.RowFilter;
import javax.swing.SortOrder;

/**
 * An implementation of <code>RowSorter</code> that provides sorting
 * and filtering using a <code>TableModel</code>.
 * The following example shows adding sorting to a <code>JTable</code>:
 * <pre>
 *   TableModel myModel = createMyTableModel();
 *   JTable table = new JTable(myModel);
 *   table.setRowSorter(new TableRowSorter(myModel));
 * </pre>
 * This will do all the wiring such that when the user does the appropriate
 * gesture, such as clicking on the column header, the table will
 * visually sort.
 * <p>
 * <code>JTable</code>'s row-based methods and <code>JTable</code>'s
 * selection model refer to the view and not the underlying
 * model. Therefore, it is necessary to convert between the two.  For
 * example, to get the selection in terms of <code>myModel</code>
 * you need to convert the indices:
 * <pre>
 *   int[] selection = table.getSelectedRows();
 *   for (int i = 0; i &lt; selection.length; i++) {
 *     selection[i] = table.convertRowIndexToModel(selection[i]);
 *   }
 * </pre>
 * Similarly to select a row in <code>JTable</code> based on
 * a coordinate from the underlying model do the inverse:
 * <pre>
 *   table.setRowSelectionInterval(table.convertRowIndexToView(row),
 *                                 table.convertRowIndexToView(row));
 * </pre>
 * <p>
 * The previous example assumes you have not enabled filtering.  If you
 * have enabled filtering <code>convertRowIndexToView</code> will return
 * -1 for locations that are not visible in the view.
 * <p>
 * <code>TableRowSorter</code> uses <code>Comparator</code>s for doing
 * comparisons. The following defines how a <code>Comparator</code> is
 * chosen for a column:
 * <ol>
 * <li>If a <code>Comparator</code> has been specified for the column by the
 *     <code>setComparator</code> method, use it.
 * <li>If the column class as returned by <code>getColumnClass</code> is
 *     <code>String</code>, use the <code>Comparator</code> returned by
 *     <code>Collator.getInstance()</code>.
 * <li>If the column class implements <code>Comparable</code>, use a
 *     <code>Comparator</code> that invokes the <code>compareTo</code>
 *     method.
 * <li>If a <code>TableStringConverter</code> has been specified, use it
 *     to convert the values to <code>String</code>s and then use the
 *     <code>Comparator</code> returned by <code>Collator.getInstance()</code>.
 * <li>Otherwise use the <code>Comparator</code> returned by
 *     <code>Collator.getInstance()</code> on the results from
 *     calling <code>toString</code> on the objects.
 * </ol>
 * <p>
 * In addition to sorting <code>TableRowSorter</code> provides the ability
 * to filter.  A filter is specified using the <code>setFilter</code>
 * method. The following example will only show rows containing the string
 * "foo":
 * <pre>
 *   TableModel myModel = createMyTableModel();
 *   TableRowSorter sorter = new TableRowSorter(myModel);
 *   sorter.setRowFilter(RowFilter.regexFilter(".*foo.*"));
 *   JTable table = new JTable(myModel);
 *   table.setRowSorter(sorter);
 * </pre>
 * <p>
 * If the underlying model structure changes (the
 * <code>modelStructureChanged</code> method is invoked) the following
 * are reset to their default values: <code>Comparator</code>s by
 * column, current sort order, and whether each column is sortable. The default
 * sort order is natural (the same as the model), and columns are
 * sortable by default.
 * <p>
 * <code>TableRowSorter</code> has one formal type parameter: the type
 * of the model.  Passing in a type that corresponds exactly to your
 * model allows you to filter based on your model without casting.
 * Refer to the documentation of <code>RowFilter</code> for an example
 * of this.
 * <p>
 * <b>WARNING:</b> <code>DefaultTableModel</code> returns a column
 * class of <code>Object</code>.  As such all comparisons will
 * be done using <code>toString</code>.  This may be unnecessarily
 * expensive.  If the column only contains one type of value, such as
 * an <code>Integer</code>, you should override <code>getColumnClass</code> and
 * return the appropriate <code>Class</code>.  This will dramatically
 * increase the performance of this class.
 *
 * <p>
 *  使用<code> TableModel </code>提供排序和过滤的<code> RowSorter </code>的实现。以下示例显示将排序添加到<code> JTable </code>：
 * <pre>
 *  TableModel myModel = createMyTableModel(); JTable table = new JTable(myModel); table.setRowSorter(ne
 * w TableRowSorter(myModel));。
 * </pre>
 *  这将执行所有的布线,使得当用户做出适当的手势(诸如点击列标题)时,表将在视觉上排序。
 * <p>
 *  <code> JTable </code>的基于行的方法和<code> JTable </code>的选择模型指的是视图而不是基础模型。因此,有必要在两者之间进行转换。
 * 例如,要根据<code> myModel </code>获取选择,您需要转换索引：。
 * <pre>
 *  int [] selection = table.getSelectedRows(); for(int i = 0; i <selection.length; i ++){selection [i] = table.convertRowIndexToModel(selection [i]); }}。
 * </pre>
 *  类似地,基于来自基础模型的坐标,在<code> JTable </code>中选择一行：
 * <pre>
 *  table.setRowSelectionInterval(table.convertRowIndexToView(row),table.convertRowIndexToView(row));
 * </pre>
 * <p>
 *  上一个示例假设您尚未启用过滤。如果您已启用过滤<code> convertRowIndexToView </code>将对视图中不可见的位置返回-1。
 * <p>
 * <code> TableRowSorter </code>使用<code> Comparator </code>进行比较。以下定义如何为列选择<code> Comparator </code>：
 * <ol>
 *  <li>如果<code> setComparator </code>方法为列指定了<code> Comparator </code>,请使用它。
 *  <li>如果<code> getColumnClass </code>返回的列类型为<code> String </code>,请使用<code> Collat​​or.getInstance代码>。
 *  <li>如果<code> setComparator </code>方法为列指定了<code> Comparator </code>,请使用它。
 *  <li>如果列类实现<code> Comparable </code>,请使用调用<code> compareTo </code>方法的<code> Comparator </code>。
 *  <li>如果已指定<code> TableStringConverter </code>,则使用它将值转换为<code> String </code> s,然后使用<code>返回的<code> Co
 * mparator </code> Collat​​or.getInstance()</code>。
 *  <li>如果列类实现<code> Comparable </code>,请使用调用<code> compareTo </code>方法的<code> Comparator </code>。
 *  <li>否则,使用<code> Collat​​or.getInstance()</code>返回的<code> Comparator </code>在对象上调用<code> toString </code>
 * 的结果。
 *  <li>如果列类实现<code> Comparable </code>,请使用调用<code> compareTo </code>方法的<code> Comparator </code>。
 * </ol>
 * <p>
 *  除了排序<code> TableRowSorter </code>提供了过滤的能力。使用<code> setFilter </code>方法指定过滤器。以下示例将只显示包含字符串"foo"的行：
 * <pre>
 *  TableModel myModel = createMyTableModel(); TableRowSorter sorter = new TableRowSorter(myModel); sort
 * er.setRowFilter(RowFilter.regexFilter("。
 * * foo。*")); JTable table = new JTable(myModel); table.setRowSorter(sorter);。
 * </pre>
 * <p>
 * 如果底层模型结构发生变化(调用<code> modelStructureChanged </code>方法),以下内容将重置为其默认值：<code>按列的比较器</code>,当前排序顺序,可排序。
 * 默认排序顺序是自然的(与模型相同),默认情况下列是可排序的。
 * <p>
 *  <code> TableRowSorter </code>有一个形式类型参数：模型的类型。传递与您的模型完全对应的类型允许您基于您的模型进行过滤而无需投射。
 * 有关示例,请参阅<code> RowFilter </code>的文档。
 * 
 * @param <M> the type of the model, which must be an implementation of
 *            <code>TableModel</code>
 * @see javax.swing.JTable
 * @see javax.swing.RowFilter
 * @see javax.swing.table.DefaultTableModel
 * @see java.text.Collator
 * @see java.util.Comparator
 * @since 1.6
 */
public class TableRowSorter<M extends TableModel> extends DefaultRowSorter<M, Integer> {
    /**
     * Comparator that uses compareTo on the contents.
     * <p>
     * <p>
     *  <b>警告：</b> <code> DefaultTableModel </code>返回<code> Object </code>的列类。
     * 因此,所有的比较将使用<code> toString </code>来完成。这可能是不必要的昂贵。
     * 如果列只包含一种类型的值,例如<code> Integer </code>,则应覆盖<code> getColumnClass </code>并返回相应的<code> Class </code>。
     * 这将大大提高这个类的性能。
     * 
     */
    private static final Comparator COMPARABLE_COMPARATOR =
            new ComparableComparator();

    /**
     * Underlying model.
     * <p>
     *  在内容上使用compareTo的比较器。
     * 
     */
    private M tableModel;

    /**
     * For toString conversions.
     * <p>
     *  基础模型。
     * 
     */
    private TableStringConverter stringConverter;


    /**
     * Creates a <code>TableRowSorter</code> with an empty model.
     * <p>
     *  ForString转化。
     * 
     */
    public TableRowSorter() {
        this(null);
    }

    /**
     * Creates a <code>TableRowSorter</code> using <code>model</code>
     * as the underlying <code>TableModel</code>.
     *
     * <p>
     *  使用空模型创建<code> TableRowSorter </code>。
     * 
     * 
     * @param model the underlying <code>TableModel</code> to use,
     *        <code>null</code> is treated as an empty model
     */
    public TableRowSorter(M model) {
        setModel(model);
    }

    /**
     * Sets the <code>TableModel</code> to use as the underlying model
     * for this <code>TableRowSorter</code>.  A value of <code>null</code>
     * can be used to set an empty model.
     *
     * <p>
     *  使用<code> model </code>作为底层的<code> TableModel </code>创建一个<code> TableRowSorter </code>。
     * 
     * 
     * @param model the underlying model to use, or <code>null</code>
     */
    public void setModel(M model) {
        tableModel = model;
        setModelWrapper(new TableRowSorterModelWrapper());
    }

    /**
     * Sets the object responsible for converting values from the
     * model to strings.  If non-<code>null</code> this
     * is used to convert any object values, that do not have a
     * registered <code>Comparator</code>, to strings.
     *
     * <p>
     *  设置<code> TableModel </code>作为此<code> TableRowSorter </code>的底层模型。 <code> null </code>的值可用于设置空模型。
     * 
     * 
     * @param stringConverter the object responsible for converting values
     *        from the model to strings
     */
    public void setStringConverter(TableStringConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    /**
     * Returns the object responsible for converting values from the
     * model to strings.
     *
     * <p>
     * 设置负责将值从模型转换为字符串的对象。如果非<code> null </code>,则用于将没有注册的<code> Comparator </code>的任何对象值转换为字符串。
     * 
     * 
     * @return object responsible for converting values to strings.
     */
    public TableStringConverter getStringConverter() {
        return stringConverter;
    }

    /**
     * Returns the <code>Comparator</code> for the specified
     * column.  If a <code>Comparator</code> has not been specified using
     * the <code>setComparator</code> method a <code>Comparator</code>
     * will be returned based on the column class
     * (<code>TableModel.getColumnClass</code>) of the specified column.
     * If the column class is <code>String</code>,
     * <code>Collator.getInstance</code> is returned.  If the
     * column class implements <code>Comparable</code> a private
     * <code>Comparator</code> is returned that invokes the
     * <code>compareTo</code> method.  Otherwise
     * <code>Collator.getInstance</code> is returned.
     *
     * <p>
     *  返回负责将值从模型转换为字符串的对象。
     * 
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public Comparator<?> getComparator(int column) {
        Comparator comparator = super.getComparator(column);
        if (comparator != null) {
            return comparator;
        }
        Class columnClass = getModel().getColumnClass(column);
        if (columnClass == String.class) {
            return Collator.getInstance();
        }
        if (Comparable.class.isAssignableFrom(columnClass)) {
            return COMPARABLE_COMPARATOR;
        }
        return Collator.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  返回指定列的<code> Comparator </code>。
     * 如果未使用<code> setComparator </code>方法指定<code> Comparator </code>,则将基于列类返回<code> Comparator </code>(<code>
     *  TableModel.getColumnClass < / code>)。
     *  返回指定列的<code> Comparator </code>。
     * 如果列类是<code> String </code>,则返回<code> Collat​​or.getInstance </code>。
     * 如果列类实现<code> Comparable </code>,则返回调用<code> compareTo </code>方法的私有<code> Comparator </code>。
     * 否则返回<code> Collat​​or.getInstance </code>。
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    protected boolean useToString(int column) {
        Comparator comparator = super.getComparator(column);
        if (comparator != null) {
            return false;
        }
        Class columnClass = getModel().getColumnClass(column);
        if (columnClass == String.class) {
            return false;
        }
        if (Comparable.class.isAssignableFrom(columnClass)) {
            return false;
        }
        return true;
    }

    /**
     * Implementation of DefaultRowSorter.ModelWrapper that delegates to a
     * TableModel.
     * <p>
     * 
     */
    private class TableRowSorterModelWrapper extends ModelWrapper<M,Integer> {
        public M getModel() {
            return tableModel;
        }

        public int getColumnCount() {
            return (tableModel == null) ? 0 : tableModel.getColumnCount();
        }

        public int getRowCount() {
            return (tableModel == null) ? 0 : tableModel.getRowCount();
        }

        public Object getValueAt(int row, int column) {
            return tableModel.getValueAt(row, column);
        }

        public String getStringValueAt(int row, int column) {
            TableStringConverter converter = getStringConverter();
            if (converter != null) {
                // Use the converter
                String value = converter.toString(
                        tableModel, row, column);
                if (value != null) {
                    return value;
                }
                return "";
            }

            // No converter, use getValueAt followed by toString
            Object o = getValueAt(row, column);
            if (o == null) {
                return "";
            }
            String string = o.toString();
            if (string == null) {
                return "";
            }
            return string;
        }

        public Integer getIdentifier(int index) {
            return index;
        }
    }


    private static class ComparableComparator implements Comparator {
        @SuppressWarnings("unchecked")
        public int compare(Object o1, Object o2) {
            return ((Comparable)o1).compareTo(o2);
        }
    }
}
