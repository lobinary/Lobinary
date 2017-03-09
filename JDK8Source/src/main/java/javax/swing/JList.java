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

package javax.swing;

import java.awt.*;
import java.awt.event.*;

import java.util.Vector;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;

import javax.swing.event.*;
import javax.accessibility.*;
import javax.swing.plaf.*;
import javax.swing.text.Position;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

import sun.swing.SwingUtilities2;
import sun.swing.SwingUtilities2.Section;
import static sun.swing.SwingUtilities2.Section.*;


/**
 * A component that displays a list of objects and allows the user to select
 * one or more items. A separate model, {@code ListModel}, maintains the
 * contents of the list.
 * <p>
 * It's easy to display an array or Vector of objects, using the {@code JList}
 * constructor that automatically builds a read-only {@code ListModel} instance
 * for you:
 * <pre>
 * {@code
 * // Create a JList that displays strings from an array
 *
 * String[] data = {"one", "two", "three", "four"};
 * JList<String> myList = new JList<String>(data);
 *
 * // Create a JList that displays the superclasses of JList.class, by
 * // creating it with a Vector populated with this data
 *
 * Vector<Class<?>> superClasses = new Vector<Class<?>>();
 * Class<JList> rootClass = javax.swing.JList.class;
 * for(Class<?> cls = rootClass; cls != null; cls = cls.getSuperclass()) {
 *     superClasses.addElement(cls);
 * }
 * JList<Class<?>> myList = new JList<Class<?>>(superClasses);
 *
 * // The automatically created model is stored in JList's "model"
 * // property, which you can retrieve
 *
 * ListModel<Class<?>> model = myList.getModel();
 * for(int i = 0; i < model.getSize(); i++) {
 *     System.out.println(model.getElementAt(i));
 * }
 * }
 * </pre>
 * <p>
 * A {@code ListModel} can be supplied directly to a {@code JList} by way of a
 * constructor or the {@code setModel} method. The contents need not be static -
 * the number of items, and the values of items can change over time. A correct
 * {@code ListModel} implementation notifies the set of
 * {@code javax.swing.event.ListDataListener}s that have been added to it, each
 * time a change occurs. These changes are characterized by a
 * {@code javax.swing.event.ListDataEvent}, which identifies the range of list
 * indices that have been modified, added, or removed. {@code JList}'s
 * {@code ListUI} is responsible for keeping the visual representation up to
 * date with changes, by listening to the model.
 * <p>
 * Simple, dynamic-content, {@code JList} applications can use the
 * {@code DefaultListModel} class to maintain list elements. This class
 * implements the {@code ListModel} interface and also provides a
 * <code>java.util.Vector</code>-like API. Applications that need a more
 * custom <code>ListModel</code> implementation may instead wish to subclass
 * {@code AbstractListModel}, which provides basic support for managing and
 * notifying listeners. For example, a read-only implementation of
 * {@code AbstractListModel}:
 * <pre>
 * {@code
 * // This list model has about 2^16 elements.  Enjoy scrolling.
 *
 * ListModel<String> bigData = new AbstractListModel<String>() {
 *     public int getSize() { return Short.MAX_VALUE; }
 *     public String getElementAt(int index) { return "Index " + index; }
 * };
 * }
 * </pre>
 * <p>
 * The selection state of a {@code JList} is managed by another separate
 * model, an instance of {@code ListSelectionModel}. {@code JList} is
 * initialized with a selection model on construction, and also contains
 * methods to query or set this selection model. Additionally, {@code JList}
 * provides convenient methods for easily managing the selection. These methods,
 * such as {@code setSelectedIndex} and {@code getSelectedValue}, are cover
 * methods that take care of the details of interacting with the selection
 * model. By default, {@code JList}'s selection model is configured to allow any
 * combination of items to be selected at a time; selection mode
 * {@code MULTIPLE_INTERVAL_SELECTION}. The selection mode can be changed
 * on the selection model directly, or via {@code JList}'s cover method.
 * Responsibility for updating the selection model in response to user gestures
 * lies with the list's {@code ListUI}.
 * <p>
 * A correct {@code ListSelectionModel} implementation notifies the set of
 * {@code javax.swing.event.ListSelectionListener}s that have been added to it
 * each time a change to the selection occurs. These changes are characterized
 * by a {@code javax.swing.event.ListSelectionEvent}, which identifies the range
 * of the selection change.
 * <p>
 * The preferred way to listen for changes in list selection is to add
 * {@code ListSelectionListener}s directly to the {@code JList}. {@code JList}
 * then takes care of listening to the the selection model and notifying your
 * listeners of change.
 * <p>
 * Responsibility for listening to selection changes in order to keep the list's
 * visual representation up to date lies with the list's {@code ListUI}.
 * <p>
 * <a name="renderer"></a>
 * Painting of cells in a {@code JList} is handled by a delegate called a
 * cell renderer, installed on the list as the {@code cellRenderer} property.
 * The renderer provides a {@code java.awt.Component} that is used
 * like a "rubber stamp" to paint the cells. Each time a cell needs to be
 * painted, the list's {@code ListUI} asks the cell renderer for the component,
 * moves it into place, and has it paint the contents of the cell by way of its
 * {@code paint} method. A default cell renderer, which uses a {@code JLabel}
 * component to render, is installed by the lists's {@code ListUI}. You can
 * substitute your own renderer using code like this:
 * <pre>
 * {@code
 *  // Display an icon and a string for each object in the list.
 *
 * class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
 *     final static ImageIcon longIcon = new ImageIcon("long.gif");
 *     final static ImageIcon shortIcon = new ImageIcon("short.gif");
 *
 *     // This is the only method defined by ListCellRenderer.
 *     // We just reconfigure the JLabel each time we're called.
 *
 *     public Component getListCellRendererComponent(
 *       JList<?> list,           // the list
 *       Object value,            // value to display
 *       int index,               // cell index
 *       boolean isSelected,      // is the cell selected
 *       boolean cellHasFocus)    // does the cell have focus
 *     {
 *         String s = value.toString();
 *         setText(s);
 *         setIcon((s.length() > 10) ? longIcon : shortIcon);
 *         if (isSelected) {
 *             setBackground(list.getSelectionBackground());
 *             setForeground(list.getSelectionForeground());
 *         } else {
 *             setBackground(list.getBackground());
 *             setForeground(list.getForeground());
 *         }
 *         setEnabled(list.isEnabled());
 *         setFont(list.getFont());
 *         setOpaque(true);
 *         return this;
 *     }
 * }
 *
 * myList.setCellRenderer(new MyCellRenderer());
 * }
 * </pre>
 * <p>
 * Another job for the cell renderer is in helping to determine sizing
 * information for the list. By default, the list's {@code ListUI} determines
 * the size of cells by asking the cell renderer for its preferred
 * size for each list item. This can be expensive for large lists of items.
 * To avoid these calculations, you can set a {@code fixedCellWidth} and
 * {@code fixedCellHeight} on the list, or have these values calculated
 * automatically based on a single prototype value:
 * <a name="prototype_example"></a>
 * <pre>
 * {@code
 * JList<String> bigDataList = new JList<String>(bigData);
 *
 * // We don't want the JList implementation to compute the width
 * // or height of all of the list cells, so we give it a string
 * // that's as big as we'll need for any cell.  It uses this to
 * // compute values for the fixedCellWidth and fixedCellHeight
 * // properties.
 *
 * bigDataList.setPrototypeCellValue("Index 1234567890");
 * }
 * </pre>
 * <p>
 * {@code JList} doesn't implement scrolling directly. To create a list that
 * scrolls, make it the viewport view of a {@code JScrollPane}. For example:
 * <pre>
 * JScrollPane scrollPane = new JScrollPane(myList);
 *
 * // Or in two steps:
 * JScrollPane scrollPane = new JScrollPane();
 * scrollPane.getViewport().setView(myList);
 * </pre>
 * <p>
 * {@code JList} doesn't provide any special handling of double or triple
 * (or N) mouse clicks, but it's easy to add a {@code MouseListener} if you
 * wish to take action on these events. Use the {@code locationToIndex}
 * method to determine what cell was clicked. For example:
 * <pre>
 * MouseListener mouseListener = new MouseAdapter() {
 *     public void mouseClicked(MouseEvent e) {
 *         if (e.getClickCount() == 2) {
 *             int index = list.locationToIndex(e.getPoint());
 *             System.out.println("Double clicked on Item " + index);
 *          }
 *     }
 * };
 * list.addMouseListener(mouseListener);
 * </pre>
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
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
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/list.html">How to Use Lists</a>
 * in <a href="https://docs.oracle.com/javase/tutorial/"><em>The Java Tutorial</em></a>
 * for further documentation.
 * <p>
 * <p>
 *  显示对象列表并允许用户选择一个或多个项的组件。单独的模型{@code ListModel}维护列表的内容。
 * <p>
 *  使用{@code JList}构造函数可以轻松地显示对象的数组或Vector,该构造函数会为您自动构建只读{@code ListModel}实例：
 * <pre>
 *  {@code //创建一个显示字符串数组的JList
 * 
 *  String [] data = {"one","two","three","four"}; JList <String> myList = new JList <String>(data);
 * 
 *  //创建一个JList,显示JList.class的超类,//创建一个填充了这些数据的Vector
 * 
 *  向量<Class <?>> superClasses = new Vector <Class <?>>(); class <JList> rootClass = javax.swing.JList.c
 * lass; for(Class <?> cls = rootClass; cls！= null; cls = cls.getSuperclass()){superClasses.addElement(cls); }
 *  JList <Class <?>> myList = new JList <Class <?>>(superClasses);。
 * 
 *  //自动创建的模型存储在JList的"model"//属性中,您可以检索它
 * 
 *  ListModel <Class <?>> model = myList.getModel(); for(int i = 0; i <model.getSize(); i ++){System.out.println(model.getElementAt(i)); }}。
 * </pre>
 * <p>
 * 可以通过构造函数或{@code setModel}方法将{@code ListModel}直接提供给{@code JList}。内容不需要是静态的 - 项目的数量,并且项目的值可以随时间改变。
 * 正确的{@code ListModel}实现通知每次发生更改时已添加到其中的{@code javax.swing.event.ListDataListener}的集合。
 * 这些更改的特点是{@code javax.swing.event.ListDataEvent},它标识已修改,添加或删除的列表索引的范围。
 *  {@code JList}的{@code ListUI}负责通过监听模型来保持视觉表示的最新更改。
 * <p>
 *  简单的动态内容{@code JList}应用程序可以使用{@code DefaultListModel}类来维护列表元素。
 * 这个类实现了{@code ListModel}接口,并且还提供了一个<code> java.util.Vector </code>类API。
 * 需要更多自定义<code> ListModel </code>实现的应用程序可能希望将{@code AbstractListModel}子类化,这为管理和通知侦听器提供了基本支持。
 * 例如,{@code AbstractListModel}的只读实现：。
 * <pre>
 *  {@code //这个列表模型有大约2 ^ 16个元素。享受滚动。
 * 
 *  ListModel <String> bigData = new AbstractListModel <String>(){public int getSize(){return Short.MAX_VALUE; }
 *  public String getElementAt(int index){return"Index"+ index; }}; }}。
 * </pre>
 * <p>
 * {@code JList}的选择状态由另一个单独的模型({@code ListSelectionModel})的实例管理。
 *  {@code JList}使用构造上的选择模型初始化,并且还包含查询或设置此选择模型的方法。此外,{@code JList}提供了方便的方法来轻松管理选择。
 * 这些方法(例如{@code setSelectedIndex}和{@code getSelectedValue})是处理与选择模型交互的详细信息的覆盖方法。
 * 默认情况下,{@code JList}的选择模型配置为允许一次选择项目的任意组合;选择模式{@code MULTIPLE_INTERVAL_SELECTION}。
 * 可以直接在选择模型上或通过{@code JList}的覆盖方法更改选择模式。响应用户手势更新选择模型的责任在于列表的{@code ListUI}。
 * <p>
 *  正确的{@code ListSelectionModel}实现通知每次对选择进行更改时添加到其中的{@code javax.swing.event.ListSelectionListener}的集合。
 * 这些更改的特点是{@code javax.swing.event.ListSelectionEvent},它标识选择更改的范围。
 * <p>
 *  监听列表选择中的更改的首选方法是将{@code ListSelectionListener}直接添加到{@code JList}。
 *  {@code JList}然后负责监听选择模型并通知你的监听器有变化。
 * <p>
 * 负责收听选择更改,以保持列表的视觉表示更新列表的{@code ListUI}。
 * <p>
 *  <a name="renderer"> </a> {@code JList}中的单元格绘画由称为单元格渲染器的委托处理,作为{@code cellRenderer}属性安装在列表中。
 * 渲染器提供了一个{@code java.awt.Component},它像"橡皮图章"一样用于绘制单元格。
 * 每次需要绘制单元格时,列表的{@code ListUI}会询问单元格渲染器的组件,将其移动到位,并通过其{@code paint}方法绘制单元格的内容。
 * 默认单元格渲染器使用{@code JLabel}组件进行渲染,由列表的{@code ListUI}安装。你可以使用如下代码替换自己的渲染器：。
 * <pre>
 *  {@code //显示列表中每个对象的图标和字符串。
 * 
 *  class MyCellRenderer extends JLabel implements ListCellRenderer <Object> {final static ImageIcon longIcon = new ImageIcon("long.gif"); final static ImageIcon shortIcon = new ImageIcon("short.gif");。
 * 
 *  //这是由ListCellRenderer定义的唯一方法。 //我们每次调用时都重新配置JLabel。
 * 
 * public Component getListCellRendererComponent(JList <list> list,//列表Object值,//显示的值int index,// cell i
 * ndex boolean isSelected,// is cell selected boolean cellHasFocus)//单元格有焦点{String s = value.toString(); setText(s); setIcon((s.length()> 10)?longIcon：shortIcon); if(isSelected){setBackground(list.getSelectionBackground()); setForeground(list.getSelectionForeground()); }
 *  else {setBackground(list.getBackground()); setForeground(list.getForeground()); } setEnabled(list.is
 * Enabled()); setFont(list.getFont()); setOpaque(true);返回这个; }}。
 * 
 *  myList.setCellRenderer(new MyCellRenderer()); }}
 * </pre>
 * <p>
 *  单元格渲染器的另一个工作是帮助确定列表的大小信息。默认情况下,列表的{@code ListUI}通过询问单元格渲染器的每个列表项的首选大小来确定单元格的大小。这对于大的项目列表可能是昂贵的。
 * 要避免这些计算,您可以在列表上设置{@code fixedCellWidth}和{@code fixedCellHeight},或根据单个原型值自动计算这些值：<a name="prototype_example">
 *  </a>。
 *  单元格渲染器的另一个工作是帮助确定列表的大小信息。默认情况下,列表的{@code ListUI}通过询问单元格渲染器的每个列表项的首选大小来确定单元格的大小。这对于大的项目列表可能是昂贵的。
 * <pre>
 *  {@code JList <String> bigDataList = new JList <String>(bigData);
 * 
 * //我们不希望JList实现计算所有列表单元格的宽度//或高度,因此我们给它一个字符串//,它和我们需要的任何单元格一样大。
 * 它使用这个来计算fixedCellWidth和fixedCellHeight //属性的值。
 * 
 *  bigDataList.setPrototypeCellValue("Index 1234567890"); }}
 * </pre>
 * <p>
 *  {@code JList}不实现直接滚动。要创建滚动的列表,请将其视为{@code JScrollPane}的视口视图。例如：
 * <pre>
 *  JScrollPane scrollPane = new JScrollPane(myList);
 * 
 *  //或在两个步骤：JScrollPane scrollPane = new JScrollPane(); scrollPane.getViewport()。setView(myList);
 * </pre>
 * <p>
 *  {@code JList}不提供任何特殊的双重或三重(或N)鼠标点击处理,但如果您希望对这些事件采取行动,则可以轻松添加{@code MouseListener}。
 * 使用{@code locationToIndex}方法来确定单击哪个单元格。例如：。
 * <pre>
 *  MouseListener mouseListener = new MouseAdapter(){public void mouseClicked(MouseEvent e){if(e.getClickCount()== 2){int index = list.locationToIndex(e.getPoint()); System.out.println("双击项目"+索引); }
 * }}; list.addMouseListener(mouseListener);。
 * </pre>
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * <p>
 *  请参阅<a href ="https://docs.oracle中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/list.html">
 * 如何使用列表</a> .com / javase / tutorial /"> <em> Java教程</em> </a>。
 * <p>
 * 
 * @see ListModel
 * @see AbstractListModel
 * @see DefaultListModel
 * @see ListSelectionModel
 * @see DefaultListSelectionModel
 * @see ListCellRenderer
 * @see DefaultListCellRenderer
 *
 * @param <E> the type of the elements of this list
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: A component which allows for the selection of one or more objects from a list.
 *
 * @author Hans Muller
 */
public class JList<E> extends JComponent implements Scrollable, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ListUI";

    /**
     * Indicates a vertical layout of cells, in a single column;
     * the default layout.
     * <p>
     *  表示单个列的垂直布局;默认布局。
     * 
     * 
     * @see #setLayoutOrientation
     * @since 1.4
     */
    public static final int VERTICAL = 0;

    /**
     * Indicates a "newspaper style" layout with cells flowing vertically
     * then horizontally.
     * <p>
     *  表示"报纸样式"布局,单元格垂直然后水平。
     * 
     * 
     * @see #setLayoutOrientation
     * @since 1.4
     */
    public static final int VERTICAL_WRAP = 1;

    /**
     * Indicates a "newspaper style" layout with cells flowing horizontally
     * then vertically.
     * <p>
     *  表示"报纸样式"布局,单元格水平然后垂直。
     * 
     * 
     * @see #setLayoutOrientation
     * @since 1.4
     */
    public static final int HORIZONTAL_WRAP = 2;

    private int fixedCellWidth = -1;
    private int fixedCellHeight = -1;
    private int horizontalScrollIncrement = -1;
    private E prototypeCellValue;
    private int visibleRowCount = 8;
    private Color selectionForeground;
    private Color selectionBackground;
    private boolean dragEnabled;

    private ListSelectionModel selectionModel;
    private ListModel<E> dataModel;
    private ListCellRenderer<? super E> cellRenderer;
    private ListSelectionListener selectionListener;

    /**
     * How to lay out the cells; defaults to <code>VERTICAL</code>.
     * <p>
     *  如何铺设细胞;默认为<code> VERTICAL </code>。
     * 
     */
    private int layoutOrientation;

    /**
     * The drop mode for this component.
     * <p>
     *  此组件的放置模式。
     * 
     */
    private DropMode dropMode = DropMode.USE_SELECTION;

    /**
     * The drop location.
     * <p>
     *  放置位置。
     * 
     */
    private transient DropLocation dropLocation;

    /**
     * A subclass of <code>TransferHandler.DropLocation</code> representing
     * a drop location for a <code>JList</code>.
     *
     * <p>
     *  <code> TransferHandler.DropLocation </code>的子类,表示<code> JList </code>的放置位置。
     * 
     * 
     * @see #getDropLocation
     * @since 1.6
     */
    public static final class DropLocation extends TransferHandler.DropLocation {
        private final int index;
        private final boolean isInsert;

        private DropLocation(Point p, int index, boolean isInsert) {
            super(p);
            this.index = index;
            this.isInsert = isInsert;
        }

        /**
         * Returns the index where dropped data should be placed in the
         * list. Interpretation of the value depends on the drop mode set on
         * the associated component. If the drop mode is either
         * <code>DropMode.USE_SELECTION</code> or <code>DropMode.ON</code>,
         * the return value is an index of a row in the list. If the drop mode is
         * <code>DropMode.INSERT</code>, the return value refers to the index
         * where the data should be inserted. If the drop mode is
         * <code>DropMode.ON_OR_INSERT</code>, the value of
         * <code>isInsert()</code> indicates whether the index is an index
         * of a row, or an insert index.
         * <p>
         * <code>-1</code> indicates that the drop occurred over empty space,
         * and no index could be calculated.
         *
         * <p>
         * 返回应将丢弃数据放在列表中的索引。值的解释取决于在相关组件上设置的下降模式。
         * 如果删除模式是<code> DropMode.USE_SELECTION </code>或<code> DropMode.ON </code>,则返回值是列表中某一行的索引。
         * 如果删除模式为<code> DropMode.INSERT </code>,则返回值指的是应插入数据的索引。
         * 如果删除模式是<code> DropMode.ON_OR_INSERT </code>,则<code> isInsert()</code>的值指示索引是行的索引还是插入索引。
         * <p>
         *  <code> -1 </code>表示删除发生在空白空间上,并且不能计算索引。
         * 
         * 
         * @return the drop index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Returns whether or not this location represents an insert
         * location.
         *
         * <p>
         *  返回此位置是否表示插入位置。
         * 
         * 
         * @return whether or not this is an insert location
         */
        public boolean isInsert() {
            return isInsert;
        }

        /**
         * Returns a string representation of this drop location.
         * This method is intended to be used for debugging purposes,
         * and the content and format of the returned string may vary
         * between implementations.
         *
         * <p>
         *  返回此放置位置的字符串表示形式。此方法旨在用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
         * 
         * 
         * @return a string representation of this drop location
         */
        public String toString() {
            return getClass().getName()
                   + "[dropPoint=" + getDropPoint() + ","
                   + "index=" + index + ","
                   + "insert=" + isInsert + "]";
        }
    }

    /**
     * Constructs a {@code JList} that displays elements from the specified,
     * {@code non-null}, model. All {@code JList} constructors delegate to
     * this one.
     * <p>
     * This constructor registers the list with the {@code ToolTipManager},
     * allowing for tooltips to be provided by the cell renderers.
     *
     * <p>
     *  构造{@code JList},显示指定的{@code非空}模型中的元素。所有{@code JList}构造函数委托给这一个。
     * <p>
     *  这个构造函数使用{@code ToolTipManager}注册列表,允许单元格渲染器提供工具提示。
     * 
     * 
     * @param dataModel the model for the list
     * @exception IllegalArgumentException if the model is {@code null}
     */
    public JList(ListModel<E> dataModel)
    {
        if (dataModel == null) {
            throw new IllegalArgumentException("dataModel must be non null");
        }

        // Register with the ToolTipManager so that tooltips from the
        // renderer show through.
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.registerComponent(this);

        layoutOrientation = VERTICAL;

        this.dataModel = dataModel;
        selectionModel = createSelectionModel();
        setAutoscrolls(true);
        setOpaque(true);
        updateUI();
    }


    /**
     * Constructs a <code>JList</code> that displays the elements in
     * the specified array. This constructor creates a read-only model
     * for the given array, and then delegates to the constructor that
     * takes a {@code ListModel}.
     * <p>
     * Attempts to pass a {@code null} value to this method results in
     * undefined behavior and, most likely, exceptions. The created model
     * references the given array directly. Attempts to modify the array
     * after constructing the list results in undefined behavior.
     *
     * <p>
     *  构造一个显示指定数组中的元素的<code> JList </code>。这个构造函数为给定的数组创建一个只读模型,然后委托给一个{@code ListModel}的构造函数。
     * <p>
     * 尝试向此方法传递{@code null}值会导致未定义的行为,并且很可能会出现异常。创建的模型直接引用给定的数组。在构造列表之后尝试修改数组会导致未定义的行为。
     * 
     * 
     * @param  listData  the array of Objects to be loaded into the data model,
     *                   {@code non-null}
     */
    public JList(final E[] listData)
    {
        this (
            new AbstractListModel<E>() {
                public int getSize() { return listData.length; }
                public E getElementAt(int i) { return listData[i]; }
            }
        );
    }


    /**
     * Constructs a <code>JList</code> that displays the elements in
     * the specified <code>Vector</code>. This constructor creates a read-only
     * model for the given {@code Vector}, and then delegates to the constructor
     * that takes a {@code ListModel}.
     * <p>
     * Attempts to pass a {@code null} value to this method results in
     * undefined behavior and, most likely, exceptions. The created model
     * references the given {@code Vector} directly. Attempts to modify the
     * {@code Vector} after constructing the list results in undefined behavior.
     *
     * <p>
     *  构造一个显示指定的<code> Vector </code>中的元素的<code> JList </code>。
     * 这个构造函数为给定的{@code Vector}创建一个只读模型,然后委托给一个{@code ListModel}的构造函数。
     * <p>
     *  尝试向此方法传递{@code null}值会导致未定义的行为,并且很可能会出现异常。创建的模型直接引用给定的{@code Vector}。
     * 尝试在构造列表后修改{@code Vector}会导致未定义的行为。
     * 
     * 
     * @param  listData  the <code>Vector</code> to be loaded into the
     *                   data model, {@code non-null}
     */
    public JList(final Vector<? extends E> listData) {
        this (
            new AbstractListModel<E>() {
                public int getSize() { return listData.size(); }
                public E getElementAt(int i) { return listData.elementAt(i); }
            }
        );
    }


    /**
     * Constructs a <code>JList</code> with an empty, read-only, model.
     * <p>
     *  用一个空的,只读的模型构造一个<code> JList </code>。
     * 
     */
    public JList() {
        this (
            new AbstractListModel<E>() {
              public int getSize() { return 0; }
              public E getElementAt(int i) { throw new IndexOutOfBoundsException("No Data Model"); }
            }
        );
    }


    /**
     * Returns the {@code ListUI}, the look and feel object that
     * renders this component.
     *
     * <p>
     *  返回{@code ListUI},呈现此组件的外观对象。
     * 
     * 
     * @return the <code>ListUI</code> object that renders this component
     */
    public ListUI getUI() {
        return (ListUI)ui;
    }


    /**
     * Sets the {@code ListUI}, the look and feel object that
     * renders this component.
     *
     * <p>
     *  设置{@code ListUI},呈现此组件的外观对象。
     * 
     * 
     * @param ui  the <code>ListUI</code> object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(ListUI ui) {
        super.setUI(ui);
    }


    /**
     * Resets the {@code ListUI} property by setting it to the value provided
     * by the current look and feel. If the current cell renderer was installed
     * by the developer (rather than the look and feel itself), this also causes
     * the cell renderer and its children to be updated, by calling
     * {@code SwingUtilities.updateComponentTreeUI} on it.
     *
     * <p>
     *  通过将{@code ListUI}属性设置为当前外观提供的值来重置该属性。
     * 如果当前单元格渲染器是由开发人员安装的(而不是外观和感觉本身),这也通过调用{@code SwingUtilities.updateComponentTreeUI}来更新单元格渲染器及其子节点。
     * 
     * 
     * @see UIManager#getUI
     * @see SwingUtilities#updateComponentTreeUI
     */
    public void updateUI() {
        setUI((ListUI)UIManager.getUI(this));

        ListCellRenderer<? super E> renderer = getCellRenderer();
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component)renderer);
        }
    }


    /**
     * Returns {@code "ListUI"}, the <code>UIDefaults</code> key used to look
     * up the name of the {@code javax.swing.plaf.ListUI} class that defines
     * the look and feel for this component.
     *
     * <p>
     * 返回{@code"ListUI"},用于查找定义此组件的外观的{@code javax.swing.plaf.ListUI}类的名称的<code> UIDefaults </code>键。
     * 
     * 
     * @return the string "ListUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /* -----private-----
     * This method is called by setPrototypeCellValue and setCellRenderer
     * to update the fixedCellWidth and fixedCellHeight properties from the
     * current value of prototypeCellValue (if it's non null).
     * <p>
     * This method sets fixedCellWidth and fixedCellHeight but does <b>not</b>
     * generate PropertyChangeEvents for them.
     *
     * <p>
     *  此方法由setPrototypeCellValue和setCellRenderer调用,以从prototypeCellValue的当前值(如果它不为null)更新fixedCellWidth和fixe
     * dCellHeight属性。
     * <p>
     *  此方法设置fixedCellWidth和fixedCellHeight,但<b>不</b>为它们生成PropertyChangeEvents。
     * 
     * 
     * @see #setPrototypeCellValue
     * @see #setCellRenderer
     */
    private void updateFixedCellSize()
    {
        ListCellRenderer<? super E> cr = getCellRenderer();
        E value = getPrototypeCellValue();

        if ((cr != null) && (value != null)) {
            Component c = cr.getListCellRendererComponent(this, value, 0, false, false);

            /* The ListUI implementation will add Component c to its private
             * CellRendererPane however we can't assume that's already
             * been done here.  So we temporarily set the one "inherited"
             * property that may affect the renderer components preferred size:
             * its font.
             * <p>
             *  CellRendererPane然而我们不能假设已经在这里完成。所以我们临时设置一个"继承"属性,可能影响渲染器组件首选大小：它的字体。
             * 
             */
            Font f = c.getFont();
            c.setFont(getFont());

            Dimension d = c.getPreferredSize();
            fixedCellWidth = d.width;
            fixedCellHeight = d.height;

            c.setFont(f);
        }
    }


    /**
     * Returns the "prototypical" cell value -- a value used to calculate a
     * fixed width and height for cells. This can be {@code null} if there
     * is no such value.
     *
     * <p>
     *  返回"原型"单元格值 - 用于计算单元格的固定宽度和高度的值。如果没有这样的值,这可以是{@code null}。
     * 
     * 
     * @return the value of the {@code prototypeCellValue} property
     * @see #setPrototypeCellValue
     */
    public E getPrototypeCellValue() {
        return prototypeCellValue;
    }

    /**
     * Sets the {@code prototypeCellValue} property, and then (if the new value
     * is {@code non-null}), computes the {@code fixedCellWidth} and
     * {@code fixedCellHeight} properties by requesting the cell renderer
     * component for the given value (and index 0) from the cell renderer, and
     * using that component's preferred size.
     * <p>
     * This method is useful when the list is too long to allow the
     * {@code ListUI} to compute the width/height of each cell, and there is a
     * single cell value that is known to occupy as much space as any of the
     * others, a so-called prototype.
     * <p>
     * While all three of the {@code prototypeCellValue},
     * {@code fixedCellHeight}, and {@code fixedCellWidth} properties may be
     * modified by this method, {@code PropertyChangeEvent} notifications are
     * only sent when the {@code prototypeCellValue} property changes.
     * <p>
     * To see an example which sets this property, see the
     * <a href="#prototype_example">class description</a> above.
     * <p>
     * The default value of this property is <code>null</code>.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置{@code prototypeCellValue}属性,然后(如果新值为{@code non-null}),通过请求单元格渲染器组件为给定值计算{@code fixedCellWidth}和{@code fixedCellHeight}
     * 属性(和索引0)从单元格渲染器,并使用该组件的首选大小。
     * <p>
     *  当列表太长而不允许{@code ListUI}计算每个单元格的宽度/高度,并且已知单个单元格值占用与其他任何单元格相同的空间时,此方法很有用。所谓的原型。
     * <p>
     * 虽然可以通过此方法修改{@code prototypeCellValue},{@code fixedCellHeight}和{@code fixedCellWidth}属性中的所有三个,但仅当{@code prototypeCellValue}
     * 属性更改时才会发送{@code PropertyChangeEvent}通知。
     * <p>
     *  要查看设置此属性的示例,请参见上面的<a href="#prototype_example">类说明</a>。
     * <p>
     *  此属性的默认值为<code> null </code>。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param prototypeCellValue  the value on which to base
     *                          <code>fixedCellWidth</code> and
     *                          <code>fixedCellHeight</code>
     * @see #getPrototypeCellValue
     * @see #setFixedCellWidth
     * @see #setFixedCellHeight
     * @see JComponent#addPropertyChangeListener
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The cell prototype value, used to compute cell width and height.
     */
    public void setPrototypeCellValue(E prototypeCellValue) {
        E oldValue = this.prototypeCellValue;
        this.prototypeCellValue = prototypeCellValue;

        /* If the prototypeCellValue has changed and is non-null,
         * then recompute fixedCellWidth and fixedCellHeight.
         * <p>
         *  然后重新计算fixedCellWidth和fixedCellHeight。
         * 
         */

        if ((prototypeCellValue != null) && !prototypeCellValue.equals(oldValue)) {
            updateFixedCellSize();
        }

        firePropertyChange("prototypeCellValue", oldValue, prototypeCellValue);
    }


    /**
     * Returns the value of the {@code fixedCellWidth} property.
     *
     * <p>
     *  返回{@code fixedCellWidth}属性的值。
     * 
     * 
     * @return the fixed cell width
     * @see #setFixedCellWidth
     */
    public int getFixedCellWidth() {
        return fixedCellWidth;
    }

    /**
     * Sets a fixed value to be used for the width of every cell in the list.
     * If {@code width} is -1, cell widths are computed in the {@code ListUI}
     * by applying <code>getPreferredSize</code> to the cell renderer component
     * for each list element.
     * <p>
     * The default value of this property is {@code -1}.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置用于列表中每个单元格宽度的固定值。
     * 如果{@code width}为-1,则通过将<code> getPreferredSize </code>应用于每个列表元素的单元格渲染器组件,在{@code ListUI}中计算单元格宽度。
     * <p>
     *  此属性的默认值为{@code -1}。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param width the width to be used for all cells in the list
     * @see #setPrototypeCellValue
     * @see #setFixedCellWidth
     * @see JComponent#addPropertyChangeListener
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: Defines a fixed cell width when greater than zero.
     */
    public void setFixedCellWidth(int width) {
        int oldValue = fixedCellWidth;
        fixedCellWidth = width;
        firePropertyChange("fixedCellWidth", oldValue, fixedCellWidth);
    }


    /**
     * Returns the value of the {@code fixedCellHeight} property.
     *
     * <p>
     *  返回{@code fixedCellHeight}属性的值。
     * 
     * 
     * @return the fixed cell height
     * @see #setFixedCellHeight
     */
    public int getFixedCellHeight() {
        return fixedCellHeight;
    }

    /**
     * Sets a fixed value to be used for the height of every cell in the list.
     * If {@code height} is -1, cell heights are computed in the {@code ListUI}
     * by applying <code>getPreferredSize</code> to the cell renderer component
     * for each list element.
     * <p>
     * The default value of this property is {@code -1}.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置要用于列表中每个单元格的高度的固定值。
     * 如果{@code height}为-1,则通过对每个列表元素应用<code> getPreferredSize </code>到单元格渲染器组件,在{@code ListUI}中计算单元格高度。
     * <p>
     *  此属性的默认值为{@code -1}。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param height the height to be used for for all cells in the list
     * @see #setPrototypeCellValue
     * @see #setFixedCellWidth
     * @see JComponent#addPropertyChangeListener
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: Defines a fixed cell height when greater than zero.
     */
    public void setFixedCellHeight(int height) {
        int oldValue = fixedCellHeight;
        fixedCellHeight = height;
        firePropertyChange("fixedCellHeight", oldValue, fixedCellHeight);
    }


    /**
     * Returns the object responsible for painting list items.
     *
     * <p>
     *  返回负责绘制列表项的对象。
     * 
     * 
     * @return the value of the {@code cellRenderer} property
     * @see #setCellRenderer
     */
    @Transient
    public ListCellRenderer<? super E> getCellRenderer() {
        return cellRenderer;
    }

    /**
     * Sets the delegate that is used to paint each cell in the list.
     * The job of a cell renderer is discussed in detail in the
     * <a href="#renderer">class level documentation</a>.
     * <p>
     * If the {@code prototypeCellValue} property is {@code non-null},
     * setting the cell renderer also causes the {@code fixedCellWidth} and
     * {@code fixedCellHeight} properties to be re-calculated. Only one
     * <code>PropertyChangeEvent</code> is generated however -
     * for the <code>cellRenderer</code> property.
     * <p>
     * The default value of this property is provided by the {@code ListUI}
     * delegate, i.e. by the look and feel implementation.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 设置用于绘制列表中每个单元格的委托。 <a href="#renderer">类级文档</a>详细讨论了单元格渲染器的作业。
     * <p>
     *  如果{@code prototypeCellValue}属性是{@code non-null},设置单元格渲染器也会导致{@code fixedCellWidth}和{@code fixedCellHeight}
     * 属性被重新计算。
     * 但是,对于<code> cellRenderer </code>属性只生成一个<code> PropertyChangeEvent </code>。
     * <p>
     *  此属性的默认值由{@code ListUI}委托提供,即通过外观实现。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param cellRenderer the <code>ListCellRenderer</code>
     *                          that paints list cells
     * @see #getCellRenderer
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The component used to draw the cells.
     */
    public void setCellRenderer(ListCellRenderer<? super E> cellRenderer) {
        ListCellRenderer<? super E> oldValue = this.cellRenderer;
        this.cellRenderer = cellRenderer;

        /* If the cellRenderer has changed and prototypeCellValue
         * was set, then recompute fixedCellWidth and fixedCellHeight.
         * <p>
         *  设置,然后重新计算fixedCellWidth和fixedCellHeight。
         * 
         */
        if ((cellRenderer != null) && !cellRenderer.equals(oldValue)) {
            updateFixedCellSize();
        }

        firePropertyChange("cellRenderer", oldValue, cellRenderer);
    }


    /**
     * Returns the color used to draw the foreground of selected items.
     * {@code DefaultListCellRenderer} uses this color to draw the foreground
     * of items in the selected state, as do the renderers installed by most
     * {@code ListUI} implementations.
     *
     * <p>
     *  返回用于绘制所选项目的前景的颜色。
     *  {@code DefaultListCellRenderer}使用此颜色来绘制处于选定状态的项目的前景,大多数{@code ListUI}实现安装的渲染器也是如此。
     * 
     * 
     * @return the color to draw the foreground of selected items
     * @see #setSelectionForeground
     * @see DefaultListCellRenderer
     */
    public Color getSelectionForeground() {
        return selectionForeground;
    }


    /**
     * Sets the color used to draw the foreground of selected items, which
     * cell renderers can use to render text and graphics.
     * {@code DefaultListCellRenderer} uses this color to draw the foreground
     * of items in the selected state, as do the renderers installed by most
     * {@code ListUI} implementations.
     * <p>
     * The default value of this property is defined by the look and feel
     * implementation.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置用于绘制所选项目的前景的颜色,哪些单元格渲染器可用于渲染文本和图形。
     *  {@code DefaultListCellRenderer}使用此颜色来绘制处于选定状态的项目的前景,大多数{@code ListUI}实现安装的渲染器也是如此。
     * <p>
     *  此属性的默认值由外观实现定义。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param selectionForeground  the {@code Color} to use in the foreground
     *                             for selected list items
     * @see #getSelectionForeground
     * @see #setSelectionBackground
     * @see #setForeground
     * @see #setBackground
     * @see #setFont
     * @see DefaultListCellRenderer
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The foreground color of selected cells.
     */
    public void setSelectionForeground(Color selectionForeground) {
        Color oldValue = this.selectionForeground;
        this.selectionForeground = selectionForeground;
        firePropertyChange("selectionForeground", oldValue, selectionForeground);
    }


    /**
     * Returns the color used to draw the background of selected items.
     * {@code DefaultListCellRenderer} uses this color to draw the background
     * of items in the selected state, as do the renderers installed by most
     * {@code ListUI} implementations.
     *
     * <p>
     * 返回用于绘制所选项目背景的颜色。 {@code DefaultListCellRenderer}使用此颜色来绘制处于选定状态的项目的背景,大多数{@code ListUI}实现安装的渲染器也是如此。
     * 
     * 
     * @return the color to draw the background of selected items
     * @see #setSelectionBackground
     * @see DefaultListCellRenderer
     */
    public Color getSelectionBackground() {
        return selectionBackground;
    }


    /**
     * Sets the color used to draw the background of selected items, which
     * cell renderers can use fill selected cells.
     * {@code DefaultListCellRenderer} uses this color to fill the background
     * of items in the selected state, as do the renderers installed by most
     * {@code ListUI} implementations.
     * <p>
     * The default value of this property is defined by the look
     * and feel implementation.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置用于绘制所选项目背景的颜色,哪些单元格渲染器可以使用填充所选单元格。
     *  {@code DefaultListCellRenderer}使用这种颜色来填充处于选定状态的项目的背景,大多数{@code ListUI}实现安装的渲染器也是如此。
     * <p>
     *  此属性的默认值由外观实现定义。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param selectionBackground  the {@code Color} to use for the
     *                             background of selected cells
     * @see #getSelectionBackground
     * @see #setSelectionForeground
     * @see #setForeground
     * @see #setBackground
     * @see #setFont
     * @see DefaultListCellRenderer
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The background color of selected cells.
     */
    public void setSelectionBackground(Color selectionBackground) {
        Color oldValue = this.selectionBackground;
        this.selectionBackground = selectionBackground;
        firePropertyChange("selectionBackground", oldValue, selectionBackground);
    }


    /**
     * Returns the value of the {@code visibleRowCount} property. See the
     * documentation for {@link #setVisibleRowCount} for details on how to
     * interpret this value.
     *
     * <p>
     *  返回{@code visibleRowCount}属性的值。有关如何解释此值的详细信息,请参阅{@link #setVisibleRowCount}的文档。
     * 
     * 
     * @return the value of the {@code visibleRowCount} property.
     * @see #setVisibleRowCount
     */
    public int getVisibleRowCount() {
        return visibleRowCount;
    }

    /**
     * Sets the {@code visibleRowCount} property, which has different meanings
     * depending on the layout orientation: For a {@code VERTICAL} layout
     * orientation, this sets the preferred number of rows to display without
     * requiring scrolling; for other orientations, it affects the wrapping of
     * cells.
     * <p>
     * In {@code VERTICAL} orientation:<br>
     * Setting this property affects the return value of the
     * {@link #getPreferredScrollableViewportSize} method, which is used to
     * calculate the preferred size of an enclosing viewport. See that method's
     * documentation for more details.
     * <p>
     * In {@code HORIZONTAL_WRAP} and {@code VERTICAL_WRAP} orientations:<br>
     * This affects how cells are wrapped. See the documentation of
     * {@link #setLayoutOrientation} for more details.
     * <p>
     * The default value of this property is {@code 8}.
     * <p>
     * Calling this method with a negative value results in the property
     * being set to {@code 0}.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  设置{@code visibleRowCount}属性,它根据布局方向有不同的含义：对于{@code VERTICAL}布局方向,这将设置首选的行数,而不需要滚动;对于其它取向,其影响细胞的包裹。
     * <p>
     *  在{@code VERTICAL}方向中：<br>设置此属性会影响{@link #getPreferredScrollableViewportSize}方法的返回值,该方法用于计算封闭视口的首选大小。
     * 有关更多详细信息,请参阅该方法的文档。
     * <p>
     * 在{@code HORIZONTAL_WRAP}和{@code VERTICAL_WRAP}方向中：<br>这会影响单元格的包装方式。
     * 有关详细信息,请参阅{@link #setLayoutOrientation}的文档。
     * <p>
     *  此属性的默认值为{@code 8}。
     * <p>
     *  使用负值调用此方法会导致将属性设置为{@code 0}。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param visibleRowCount  an integer specifying the preferred number of
     *                         rows to display without requiring scrolling
     * @see #getVisibleRowCount
     * @see #getPreferredScrollableViewportSize
     * @see #setLayoutOrientation
     * @see JComponent#getVisibleRect
     * @see JViewport
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The preferred number of rows to display without
     *              requiring scrolling
     */
    public void setVisibleRowCount(int visibleRowCount) {
        int oldValue = this.visibleRowCount;
        this.visibleRowCount = Math.max(0, visibleRowCount);
        firePropertyChange("visibleRowCount", oldValue, visibleRowCount);
    }


    /**
     * Returns the layout orientation property for the list: {@code VERTICAL}
     * if the layout is a single column of cells, {@code VERTICAL_WRAP} if the
     * layout is "newspaper style" with the content flowing vertically then
     * horizontally, or {@code HORIZONTAL_WRAP} if the layout is "newspaper
     * style" with the content flowing horizontally then vertically.
     *
     * <p>
     *  返回列表的布局方向属性：{@code VERTICAL}(如果布局是单列单元格),{@code VERTICAL_WRAP}(如果布局是"报纸样式",内容垂直然后水平),或{@code HORIZONTAL_WRAP }
     * 如果布局是"报纸样式",内容水平然后垂直。
     * 
     * 
     * @return the value of the {@code layoutOrientation} property
     * @see #setLayoutOrientation
     * @since 1.4
     */
    public int getLayoutOrientation() {
        return layoutOrientation;
    }


    /**
     * Defines the way list cells are layed out. Consider a {@code JList}
     * with five cells. Cells can be layed out in one of the following ways:
     *
     * <pre>
     * VERTICAL:          0
     *                    1
     *                    2
     *                    3
     *                    4
     *
     * HORIZONTAL_WRAP:   0  1  2
     *                    3  4
     *
     * VERTICAL_WRAP:     0  3
     *                    1  4
     *                    2
     * </pre>
     * <p>
     * A description of these layouts follows:
     *
     * <table border="1"
     *  summary="Describes layouts VERTICAL, HORIZONTAL_WRAP, and VERTICAL_WRAP">
     *   <tr><th><p style="text-align:left">Value</p></th><th><p style="text-align:left">Description</p></th></tr>
     *   <tr><td><code>VERTICAL</code>
     *       <td>Cells are layed out vertically in a single column.
     *   <tr><td><code>HORIZONTAL_WRAP</code>
     *       <td>Cells are layed out horizontally, wrapping to a new row as
     *           necessary. If the {@code visibleRowCount} property is less than
     *           or equal to zero, wrapping is determined by the width of the
     *           list; otherwise wrapping is done in such a way as to ensure
     *           {@code visibleRowCount} rows in the list.
     *   <tr><td><code>VERTICAL_WRAP</code>
     *       <td>Cells are layed out vertically, wrapping to a new column as
     *           necessary. If the {@code visibleRowCount} property is less than
     *           or equal to zero, wrapping is determined by the height of the
     *           list; otherwise wrapping is done at {@code visibleRowCount} rows.
     *  </table>
     * <p>
     * The default value of this property is <code>VERTICAL</code>.
     *
     * <p>
     *  定义列表单元格的布局方式。考虑一个带有五个单元格的{@code JList}。单元格可以以下列方式之一进行布局：
     * 
     * <pre>
     *  垂直：0 1 2 3 4
     * 
     *  HORIZONTAL_WRAP：0 1 2 3 4
     * 
     *  VERTICAL_WRAP：0 3 1 4 2
     * </pre>
     * <p>
     *  这些布局的描述如下：
     * 
     *  <table border ="1"
     * summary="Describes layouts VERTICAL, HORIZONTAL_WRAP, and VERTICAL_WRAP">
     * <tr> <th> <p style ="text-align：left">值</p> </th> <th> <p style ="text-align：left">说明</p> </th > </tr>
     *  <tr> <td> <code> VERTICAL </code> <td>单元格垂直放置在单个列中。
     *  <tr> <td> <code> HORIZONTAL_WRAP </code> <td>单元格被水平排列,根据需要包装到新行。
     * 如果{@code visibleRowCount}属性小于或等于零,则换行由列表的宽度决定;否则包装是以确保列表中的{@code visibleRowCount}行的方式完成的。
     *  <tr> <td> <code> VERTICAL_WRAP </code> <td>单元格被垂直布局,必要时包装到新列。
     * 如果{@code visibleRowCount}属性小于或等于零,则包装由列表的高度决定;否则换行在{@code visibleRowCount}行完成。
     * </table>
     * <p>
     *  此属性的默认值为<code> VERTICAL </code>。
     * 
     * 
     * @param layoutOrientation the new layout orientation, one of:
     *        {@code VERTICAL}, {@code HORIZONTAL_WRAP} or {@code VERTICAL_WRAP}
     * @see #getLayoutOrientation
     * @see #setVisibleRowCount
     * @see #getScrollableTracksViewportHeight
     * @see #getScrollableTracksViewportWidth
     * @throws IllegalArgumentException if {@code layoutOrientation} isn't one of the
     *         allowable values
     * @since 1.4
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: Defines the way list cells are layed out.
     *        enum: VERTICAL JList.VERTICAL
     *              HORIZONTAL_WRAP JList.HORIZONTAL_WRAP
     *              VERTICAL_WRAP JList.VERTICAL_WRAP
     */
    public void setLayoutOrientation(int layoutOrientation) {
        int oldValue = this.layoutOrientation;
        switch (layoutOrientation) {
        case VERTICAL:
        case VERTICAL_WRAP:
        case HORIZONTAL_WRAP:
            this.layoutOrientation = layoutOrientation;
            firePropertyChange("layoutOrientation", oldValue, layoutOrientation);
            break;
        default:
            throw new IllegalArgumentException("layoutOrientation must be one of: VERTICAL, HORIZONTAL_WRAP or VERTICAL_WRAP");
        }
    }


    /**
     * Returns the smallest list index that is currently visible.
     * In a left-to-right {@code componentOrientation}, the first visible
     * cell is found closest to the list's upper-left corner. In right-to-left
     * orientation, it is found closest to the upper-right corner.
     * If nothing is visible or the list is empty, {@code -1} is returned.
     * Note that the returned cell may only be partially visible.
     *
     * <p>
     *  返回当前可见的最小列表索引。在从左到右的{@code componentOrientation}中,第一个可见单元格最靠近列表的左上角。在从右到左的方向,它被发现最接近右上角。
     * 如果没有可见或列表为空,则返回{@code -1}。请注意,返回的单元格只能部分可见。
     * 
     * 
     * @return the index of the first visible cell
     * @see #getLastVisibleIndex
     * @see JComponent#getVisibleRect
     */
    public int getFirstVisibleIndex() {
        Rectangle r = getVisibleRect();
        int first;
        if (this.getComponentOrientation().isLeftToRight()) {
            first = locationToIndex(r.getLocation());
        } else {
            first = locationToIndex(new Point((r.x + r.width) - 1, r.y));
        }
        if (first != -1) {
            Rectangle bounds = getCellBounds(first, first);
            if (bounds != null) {
                SwingUtilities.computeIntersection(r.x, r.y, r.width, r.height, bounds);
                if (bounds.width == 0 || bounds.height == 0) {
                    first = -1;
                }
            }
        }
        return first;
    }


    /**
     * Returns the largest list index that is currently visible.
     * If nothing is visible or the list is empty, {@code -1} is returned.
     * Note that the returned cell may only be partially visible.
     *
     * <p>
     *  返回当前可见的最大列表索引。如果没有可见或列表为空,则返回{@code -1}。请注意,返回的单元格只能部分可见。
     * 
     * 
     * @return the index of the last visible cell
     * @see #getFirstVisibleIndex
     * @see JComponent#getVisibleRect
     */
    public int getLastVisibleIndex() {
        boolean leftToRight = this.getComponentOrientation().isLeftToRight();
        Rectangle r = getVisibleRect();
        Point lastPoint;
        if (leftToRight) {
            lastPoint = new Point((r.x + r.width) - 1, (r.y + r.height) - 1);
        } else {
            lastPoint = new Point(r.x, (r.y + r.height) - 1);
        }
        int location = locationToIndex(lastPoint);

        if (location != -1) {
            Rectangle bounds = getCellBounds(location, location);

            if (bounds != null) {
                SwingUtilities.computeIntersection(r.x, r.y, r.width, r.height, bounds);
                if (bounds.width == 0 || bounds.height == 0) {
                    // Try the top left(LTR) or top right(RTL) corner, and
                    // then go across checking each cell for HORIZONTAL_WRAP.
                    // Try the lower left corner, and then go across checking
                    // each cell for other list layout orientation.
                    boolean isHorizontalWrap =
                        (getLayoutOrientation() == HORIZONTAL_WRAP);
                    Point visibleLocation = isHorizontalWrap ?
                        new Point(lastPoint.x, r.y) :
                        new Point(r.x, lastPoint.y);
                    int last;
                    int visIndex = -1;
                    int lIndex = location;
                    location = -1;

                    do {
                        last = visIndex;
                        visIndex = locationToIndex(visibleLocation);

                        if (visIndex != -1) {
                            bounds = getCellBounds(visIndex, visIndex);
                            if (visIndex != lIndex && bounds != null &&
                                bounds.contains(visibleLocation)) {
                                location = visIndex;
                                if (isHorizontalWrap) {
                                    visibleLocation.y = bounds.y + bounds.height;
                                    if (visibleLocation.y >= lastPoint.y) {
                                        // Past visible region, bail.
                                        last = visIndex;
                                    }
                                }
                                else {
                                    visibleLocation.x = bounds.x + bounds.width;
                                    if (visibleLocation.x >= lastPoint.x) {
                                        // Past visible region, bail.
                                        last = visIndex;
                                    }
                                }

                            }
                            else {
                                last = visIndex;
                            }
                        }
                    } while (visIndex != -1 && last != visIndex);
                }
            }
        }
        return location;
    }


    /**
     * Scrolls the list within an enclosing viewport to make the specified
     * cell completely visible. This calls {@code scrollRectToVisible} with
     * the bounds of the specified cell. For this method to work, the
     * {@code JList} must be within a <code>JViewport</code>.
     * <p>
     * If the given index is outside the list's range of cells, this method
     * results in nothing.
     *
     * <p>
     * 在封闭视口中滚动列表,使指定的单元格完全可见。这将调用{@code scrollRectToVisible}与指定单元格的边界。
     * 要使此方法起作用,{@code JList}必须位于<code> JViewport </code>中。
     * <p>
     *  如果给定的索引在列表的单元格范围之外,此方法不会产生任何结果。
     * 
     * 
     * @param index  the index of the cell to make visible
     * @see JComponent#scrollRectToVisible
     * @see #getVisibleRect
     */
    public void ensureIndexIsVisible(int index) {
        Rectangle cellBounds = getCellBounds(index, index);
        if (cellBounds != null) {
            scrollRectToVisible(cellBounds);
        }
    }

    /**
     * Turns on or off automatic drag handling. In order to enable automatic
     * drag handling, this property should be set to {@code true}, and the
     * list's {@code TransferHandler} needs to be {@code non-null}.
     * The default value of the {@code dragEnabled} property is {@code false}.
     * <p>
     * The job of honoring this property, and recognizing a user drag gesture,
     * lies with the look and feel implementation, and in particular, the list's
     * {@code ListUI}. When automatic drag handling is enabled, most look and
     * feels (including those that subclass {@code BasicLookAndFeel}) begin a
     * drag and drop operation whenever the user presses the mouse button over
     * an item and then moves the mouse a few pixels. Setting this property to
     * {@code true} can therefore have a subtle effect on how selections behave.
     * <p>
     * If a look and feel is used that ignores this property, you can still
     * begin a drag and drop operation by calling {@code exportAsDrag} on the
     * list's {@code TransferHandler}.
     *
     * <p>
     *  打开或关闭自动拖动处理。为了启用自动拖动处理,此属性应设置为{@code true},并且列表的{@code TransferHandler}需要为{@code non-null}。
     *  {@code dragEnabled}属性的默认值为{@code false}。
     * <p>
     *  尊重此属性并识别用户拖动手势的工作在于外观和感觉实现,特别是列表的{@code ListUI}。
     * 当启用自动拖动处理时,每当用户在项目上按下鼠标按钮,然后将鼠标移动几个像素时,大多数外观和感觉(包括子类{@code BasicLookAndFeel})开始拖放操作。
     * 因此,将此属性设置为{@code true}可能会对选择行为产生微妙的影响。
     * <p>
     *  如果使用忽略此属性的外观,您仍然可以通过在列表的{@code TransferHandler}上调用{@code exportAsDrag}来开始拖放操作。
     * 
     * 
     * @param b whether or not to enable automatic drag handling
     * @exception HeadlessException if
     *            <code>b</code> is <code>true</code> and
     *            <code>GraphicsEnvironment.isHeadless()</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     *
     * @beaninfo
     *  description: determines whether automatic drag handling is enabled
     *        bound: false
     */
    public void setDragEnabled(boolean b) {
        if (b && GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
        dragEnabled = b;
    }

    /**
     * Returns whether or not automatic drag handling is enabled.
     *
     * <p>
     *  返回是否启用自动拖动处理。
     * 
     * 
     * @return the value of the {@code dragEnabled} property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled() {
        return dragEnabled;
    }

    /**
     * Sets the drop mode for this component. For backward compatibility,
     * the default for this property is <code>DropMode.USE_SELECTION</code>.
     * Usage of one of the other modes is recommended, however, for an
     * improved user experience. <code>DropMode.ON</code>, for instance,
     * offers similar behavior of showing items as selected, but does so without
     * affecting the actual selection in the list.
     * <p>
     * <code>JList</code> supports the following drop modes:
     * <ul>
     *    <li><code>DropMode.USE_SELECTION</code></li>
     *    <li><code>DropMode.ON</code></li>
     *    <li><code>DropMode.INSERT</code></li>
     *    <li><code>DropMode.ON_OR_INSERT</code></li>
     * </ul>
     * The drop mode is only meaningful if this component has a
     * <code>TransferHandler</code> that accepts drops.
     *
     * <p>
     * 设置此组件的放置模式。为了向后兼容,此属性的默认值为<code> DropMode.USE_SELECTION </code>。然而,为了改进的用户体验,推荐使用其它模式之一。
     * 例如,<code> DropMode.ON </code>提供了类似的行为来显示所选项目,但这样做不会影响列表中的实际选择。
     * <p>
     *  <code> JList </code>支持以下放置模式：
     * <ul>
     *  <li> <code> DropMode.USE_SELECTION </code> </li> <li> <code> DropMode.ON </code> </li> <li> <code> D
     * ropMode.INSERT </code> > <li> <code> DropMode.ON_OR_INSERT </code> </li>。
     * </ul>
     *  drop模式只有在这个组件有一个接受drop的<code> TransferHandler </code>时才有意义。
     * 
     * 
     * @param dropMode the drop mode to use
     * @throws IllegalArgumentException if the drop mode is unsupported
     *         or <code>null</code>
     * @see #getDropMode
     * @see #getDropLocation
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.6
     */
    public final void setDropMode(DropMode dropMode) {
        if (dropMode != null) {
            switch (dropMode) {
                case USE_SELECTION:
                case ON:
                case INSERT:
                case ON_OR_INSERT:
                    this.dropMode = dropMode;
                    return;
            }
        }

        throw new IllegalArgumentException(dropMode + ": Unsupported drop mode for list");
    }

    /**
     * Returns the drop mode for this component.
     *
     * <p>
     *  返回此组件的放置模式。
     * 
     * 
     * @return the drop mode for this component
     * @see #setDropMode
     * @since 1.6
     */
    public final DropMode getDropMode() {
        return dropMode;
    }

    /**
     * Calculates a drop location in this component, representing where a
     * drop at the given point should insert data.
     *
     * <p>
     *  计算此组件中的放置位置,表示给定点的放置应插入数据的位置。
     * 
     * 
     * @param p the point to calculate a drop location for
     * @return the drop location, or <code>null</code>
     */
    DropLocation dropLocationForPoint(Point p) {
        DropLocation location = null;
        Rectangle rect = null;

        int index = locationToIndex(p);
        if (index != -1) {
            rect = getCellBounds(index, index);
        }

        switch(dropMode) {
            case USE_SELECTION:
            case ON:
                location = new DropLocation(p,
                    (rect != null && rect.contains(p)) ? index : -1,
                    false);

                break;
            case INSERT:
                if (index == -1) {
                    location = new DropLocation(p, getModel().getSize(), true);
                    break;
                }

                if (layoutOrientation == HORIZONTAL_WRAP) {
                    boolean ltr = getComponentOrientation().isLeftToRight();

                    if (SwingUtilities2.liesInHorizontal(rect, p, ltr, false) == TRAILING) {
                        index++;
                    // special case for below all cells
                    } else if (index == getModel().getSize() - 1 && p.y >= rect.y + rect.height) {
                        index++;
                    }
                } else {
                    if (SwingUtilities2.liesInVertical(rect, p, false) == TRAILING) {
                        index++;
                    }
                }

                location = new DropLocation(p, index, true);

                break;
            case ON_OR_INSERT:
                if (index == -1) {
                    location = new DropLocation(p, getModel().getSize(), true);
                    break;
                }

                boolean between = false;

                if (layoutOrientation == HORIZONTAL_WRAP) {
                    boolean ltr = getComponentOrientation().isLeftToRight();

                    Section section = SwingUtilities2.liesInHorizontal(rect, p, ltr, true);
                    if (section == TRAILING) {
                        index++;
                        between = true;
                    // special case for below all cells
                    } else if (index == getModel().getSize() - 1 && p.y >= rect.y + rect.height) {
                        index++;
                        between = true;
                    } else if (section == LEADING) {
                        between = true;
                    }
                } else {
                    Section section = SwingUtilities2.liesInVertical(rect, p, true);
                    if (section == LEADING) {
                        between = true;
                    } else if (section == TRAILING) {
                        index++;
                        between = true;
                    }
                }

                location = new DropLocation(p, index, between);

                break;
            default:
                assert false : "Unexpected drop mode";
        }

        return location;
    }

    /**
     * Called to set or clear the drop location during a DnD operation.
     * In some cases, the component may need to use it's internal selection
     * temporarily to indicate the drop location. To help facilitate this,
     * this method returns and accepts as a parameter a state object.
     * This state object can be used to store, and later restore, the selection
     * state. Whatever this method returns will be passed back to it in
     * future calls, as the state parameter. If it wants the DnD system to
     * continue storing the same state, it must pass it back every time.
     * Here's how this is used:
     * <p>
     * Let's say that on the first call to this method the component decides
     * to save some state (because it is about to use the selection to show
     * a drop index). It can return a state object to the caller encapsulating
     * any saved selection state. On a second call, let's say the drop location
     * is being changed to something else. The component doesn't need to
     * restore anything yet, so it simply passes back the same state object
     * to have the DnD system continue storing it. Finally, let's say this
     * method is messaged with <code>null</code>. This means DnD
     * is finished with this component for now, meaning it should restore
     * state. At this point, it can use the state parameter to restore
     * said state, and of course return <code>null</code> since there's
     * no longer anything to store.
     *
     * <p>
     *  在DnD操作期间调用以设置或清除丢弃位置。在某些情况下,组件可能需要暂时使用它的内部选择来指示丢弃位置。为了帮助实现这一点,该方法返回并接受状态对象作为参数。该状态对象可用于存储并稍后恢复选择状态。
     * 无论此方法返回将作为状态参数传递回它在未来的调用。如果它希望DnD系统继续存储相同的状态,它必须每次都通过它。以下是使用方法：。
     * <p>
     * 让我们说,在第一次调用这个方法时,组件决定保存一些状态(因为它将使用选择来显示drop索引)。它可以返回一个状态对象给调用者封装任何保存的选择状态。在第二次调用时,我们假定放置位置正在更改为其他值。
     * 该组件不需要恢复任何东西,所以它只是传回相同的状态对象,让DnD系统继续存储它。最后,让我们说这个方法是用<code> null </code>。这意味着DnD现在完成这个组件,意味着它应该恢复状态。
     * 在这一点上,它可以使用状态参数来恢复所述状态,当然返回<code> null </code>,因为不再存储任何东西。
     * 
     * 
     * @param location the drop location (as calculated by
     *        <code>dropLocationForPoint</code>) or <code>null</code>
     *        if there's no longer a valid drop location
     * @param state the state object saved earlier for this component,
     *        or <code>null</code>
     * @param forDrop whether or not the method is being called because an
     *        actual drop occurred
     * @return any saved state for this component, or <code>null</code> if none
     */
    Object setDropLocation(TransferHandler.DropLocation location,
                           Object state,
                           boolean forDrop) {

        Object retVal = null;
        DropLocation listLocation = (DropLocation)location;

        if (dropMode == DropMode.USE_SELECTION) {
            if (listLocation == null) {
                if (!forDrop && state != null) {
                    setSelectedIndices(((int[][])state)[0]);

                    int anchor = ((int[][])state)[1][0];
                    int lead = ((int[][])state)[1][1];

                    SwingUtilities2.setLeadAnchorWithoutSelection(
                            getSelectionModel(), lead, anchor);
                }
            } else {
                if (dropLocation == null) {
                    int[] inds = getSelectedIndices();
                    retVal = new int[][] {inds, {getAnchorSelectionIndex(),
                                                 getLeadSelectionIndex()}};
                } else {
                    retVal = state;
                }

                int index = listLocation.getIndex();
                if (index == -1) {
                    clearSelection();
                    getSelectionModel().setAnchorSelectionIndex(-1);
                    getSelectionModel().setLeadSelectionIndex(-1);
                } else {
                    setSelectionInterval(index, index);
                }
            }
        }

        DropLocation old = dropLocation;
        dropLocation = listLocation;
        firePropertyChange("dropLocation", old, dropLocation);

        return retVal;
    }

    /**
     * Returns the location that this component should visually indicate
     * as the drop location during a DnD operation over the component,
     * or {@code null} if no location is to currently be shown.
     * <p>
     * This method is not meant for querying the drop location
     * from a {@code TransferHandler}, as the drop location is only
     * set after the {@code TransferHandler}'s <code>canImport</code>
     * has returned and has allowed for the location to be shown.
     * <p>
     * When this property changes, a property change event with
     * name "dropLocation" is fired by the component.
     * <p>
     * By default, responsibility for listening for changes to this property
     * and indicating the drop location visually lies with the list's
     * {@code ListUI}, which may paint it directly and/or install a cell
     * renderer to do so. Developers wishing to implement custom drop location
     * painting and/or replace the default cell renderer, may need to honor
     * this property.
     *
     * <p>
     *  返回此组件在组件上的DnD操作期间可视地指示为放置位置的位置,或{@code null}(如果当前未显示位置)。
     * <p>
     *  此方法不是用于从{@code TransferHandler}查询丢弃位置,因为丢弃位置仅在{@code TransferHandler}的<code> canImport </code>返回并允许位
     * 置之后设置显示。
     * <p>
     *  当此属性更改时,组件会触发名为"dropLocation"的属性更改事件。
     * <p>
     * 默认情况下,侦听对此属性的更改和指示删除位置的责任在视觉上在列表的{@code ListUI},它可以直接绘制它和/或安装单元格渲染器这样做。
     * 希望实现自定义拖放位置绘制和/或替换默认单元格渲染器的开发人员可能需要尊重此属性。
     * 
     * 
     * @return the drop location
     * @see #setDropMode
     * @see TransferHandler#canImport(TransferHandler.TransferSupport)
     * @since 1.6
     */
    public final DropLocation getDropLocation() {
        return dropLocation;
    }

    /**
     * Returns the next list element whose {@code toString} value
     * starts with the given prefix.
     *
     * <p>
     *  返回下一个列表元素,其{@code toString}值以给定前缀开头。
     * 
     * 
     * @param prefix the string to test for a match
     * @param startIndex the index for starting the search
     * @param bias the search direction, either
     * Position.Bias.Forward or Position.Bias.Backward.
     * @return the index of the next list element that
     * starts with the prefix; otherwise {@code -1}
     * @exception IllegalArgumentException if prefix is {@code null}
     * or startIndex is out of bounds
     * @since 1.4
     */
    public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
        ListModel<E> model = getModel();
        int max = model.getSize();
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        if (startIndex < 0 || startIndex >= max) {
            throw new IllegalArgumentException();
        }
        prefix = prefix.toUpperCase();

        // start search from the next element after the selected element
        int increment = (bias == Position.Bias.Forward) ? 1 : -1;
        int index = startIndex;
        do {
            E element = model.getElementAt(index);

            if (element != null) {
                String string;

                if (element instanceof String) {
                    string = ((String)element).toUpperCase();
                }
                else {
                    string = element.toString();
                    if (string != null) {
                        string = string.toUpperCase();
                    }
                }

                if (string != null && string.startsWith(prefix)) {
                    return index;
                }
            }
            index = (index + increment + max) % max;
        } while (index != startIndex);
        return -1;
    }

    /**
     * Returns the tooltip text to be used for the given event. This overrides
     * {@code JComponent}'s {@code getToolTipText} to first check the cell
     * renderer component for the cell over which the event occurred, returning
     * its tooltip text, if any. This implementation allows you to specify
     * tooltip text on the cell level, by using {@code setToolTipText} on your
     * cell renderer component.
     * <p>
     * <strong>Note:</strong> For <code>JList</code> to properly display the
     * tooltips of its renderers in this manner, <code>JList</code> must be a
     * registered component with the <code>ToolTipManager</code>. This registration
     * is done automatically in the constructor. However, if at a later point
     * <code>JList</code> is unregistered, by way of a call to
     * {@code setToolTipText(null)}, tips from the renderers will no longer display.
     *
     * <p>
     *  返回要用于给定事件的工具提示文本。
     * 这将覆盖{@code JComponent}的{@code getToolTipText},首先检查事件发生的单元格的单元格渲染器组件,返回其工具提示文本(如果有)。
     * 此实现允许您在单元格级别上通过在单元格渲染器组件上使用{@code setToolTipText}指定工具提示文本。
     * <p>
     *  <strong>注意</strong>：对于<code> JList </code>以这种方式正确显示其渲染器的工具提示,<code> JList </code>必须是注册的组件与<code> Too
     * lTipManager < / code>。
     * 此注册在构造函数中自动完成。但是,如果稍后<code> JList </code>未注册,通过调用{@code setToolTipText(null)},来自渲染器的提示将不再显示。
     * 
     * 
     * @param event the {@code MouseEvent} to fetch the tooltip text for
     * @see JComponent#setToolTipText
     * @see JComponent#getToolTipText
     */
    public String getToolTipText(MouseEvent event) {
        if(event != null) {
            Point p = event.getPoint();
            int index = locationToIndex(p);
            ListCellRenderer<? super E> r = getCellRenderer();
            Rectangle cellBounds;

            if (index != -1 && r != null && (cellBounds =
                               getCellBounds(index, index)) != null &&
                               cellBounds.contains(p.x, p.y)) {
                ListSelectionModel lsm = getSelectionModel();
                Component rComponent = r.getListCellRendererComponent(
                           this, getModel().getElementAt(index), index,
                           lsm.isSelectedIndex(index),
                           (hasFocus() && (lsm.getLeadSelectionIndex() ==
                                           index)));

                if(rComponent instanceof JComponent) {
                    MouseEvent      newEvent;

                    p.translate(-cellBounds.x, -cellBounds.y);
                    newEvent = new MouseEvent(rComponent, event.getID(),
                                              event.getWhen(),
                                              event.getModifiers(),
                                              p.x, p.y,
                                              event.getXOnScreen(),
                                              event.getYOnScreen(),
                                              event.getClickCount(),
                                              event.isPopupTrigger(),
                                              MouseEvent.NOBUTTON);

                    String tip = ((JComponent)rComponent).getToolTipText(
                                              newEvent);

                    if (tip != null) {
                        return tip;
                    }
                }
            }
        }
        return super.getToolTipText();
    }

    /**
     * --- ListUI Delegations ---
     * <p>
     *  --- ListUI代理---
     * 
     */


    /**
     * Returns the cell index closest to the given location in the list's
     * coordinate system. To determine if the cell actually contains the
     * specified location, compare the point against the cell's bounds,
     * as provided by {@code getCellBounds}. This method returns {@code -1}
     * if the model is empty
     * <p>
     * This is a cover method that delegates to the method of the same name
     * in the list's {@code ListUI}. It returns {@code -1} if the list has
     * no {@code ListUI}.
     *
     * <p>
     * 返回最接近列表坐标系中给定位置的单元格索引。要确定单元格是否实际包含指定的位置,请将该点与单元格的边界(由{@code getCellBounds}提供)进行比较。
     * 如果模型为空,此方法返回{@code -1}。
     * <p>
     *  这是一个覆盖方法,在列表的{@code ListUI}中委托同名方法。如果列表没有{@code ListUI},则返回{@code -1}。
     * 
     * 
     * @param location the coordinates of the point
     * @return the cell index closest to the given location, or {@code -1}
     */
    public int locationToIndex(Point location) {
        ListUI ui = getUI();
        return (ui != null) ? ui.locationToIndex(this, location) : -1;
    }


    /**
     * Returns the origin of the specified item in the list's coordinate
     * system. This method returns {@code null} if the index isn't valid.
     * <p>
     * This is a cover method that delegates to the method of the same name
     * in the list's {@code ListUI}. It returns {@code null} if the list has
     * no {@code ListUI}.
     *
     * <p>
     *  返回列表坐标系中指定项目的原点。如果索引无效,此方法返回{@code null}。
     * <p>
     *  这是一个覆盖方法,在列表的{@code ListUI}中委托同名方法。如果列表没有{@code ListUI},则返回{@code null}。
     * 
     * 
     * @param index the cell index
     * @return the origin of the cell, or {@code null}
     */
    public Point indexToLocation(int index) {
        ListUI ui = getUI();
        return (ui != null) ? ui.indexToLocation(this, index) : null;
    }


    /**
     * Returns the bounding rectangle, in the list's coordinate system,
     * for the range of cells specified by the two indices.
     * These indices can be supplied in any order.
     * <p>
     * If the smaller index is outside the list's range of cells, this method
     * returns {@code null}. If the smaller index is valid, but the larger
     * index is outside the list's range, the bounds of just the first index
     * is returned. Otherwise, the bounds of the valid range is returned.
     * <p>
     * This is a cover method that delegates to the method of the same name
     * in the list's {@code ListUI}. It returns {@code null} if the list has
     * no {@code ListUI}.
     *
     * <p>
     *  在列表的坐标系中返回由两个索引指定的单元格范围的边界矩形。这些索引可以按任何顺序提供。
     * <p>
     *  如果较小的索引在列表的单元格范围之外,则此方法返回{@code null}。如果较小的索引有效,但较大的索引在列表的范围之外,则只返回第一个索引的边界。否则,返回有效范围的边界。
     * <p>
     *  这是一个覆盖方法,在列表的{@code ListUI}中委托同名方法。如果列表没有{@code ListUI},则返回{@code null}。
     * 
     * 
     * @param index0 the first index in the range
     * @param index1 the second index in the range
     * @return the bounding rectangle for the range of cells, or {@code null}
     */
    public Rectangle getCellBounds(int index0, int index1) {
        ListUI ui = getUI();
        return (ui != null) ? ui.getCellBounds(this, index0, index1) : null;
    }


    /**
     * --- ListModel Support ---
     * <p>
     *  --- ListModel支持---
     * 
     */


    /**
     * Returns the data model that holds the list of items displayed
     * by the <code>JList</code> component.
     *
     * <p>
     *  返回保存由<code> JList </code>组件显示的项目列表的数据模型。
     * 
     * 
     * @return the <code>ListModel</code> that provides the displayed
     *                          list of items
     * @see #setModel
     */
    public ListModel<E> getModel() {
        return dataModel;
    }

    /**
     * Sets the model that represents the contents or "value" of the
     * list, notifies property change listeners, and then clears the
     * list's selection.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 设置表示列表的内容或"值"的模型,通知属性更改侦听器,然后清除列表的选择。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param model  the <code>ListModel</code> that provides the
     *                                          list of items for display
     * @exception IllegalArgumentException  if <code>model</code> is
     *                                          <code>null</code>
     * @see #getModel
     * @see #clearSelection
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: The object that contains the data to be drawn by this JList.
     */
    public void setModel(ListModel<E> model) {
        if (model == null) {
            throw new IllegalArgumentException("model must be non null");
        }
        ListModel<E> oldValue = dataModel;
        dataModel = model;
        firePropertyChange("model", oldValue, dataModel);
        clearSelection();
    }


    /**
     * Constructs a read-only <code>ListModel</code> from an array of items,
     * and calls {@code setModel} with this model.
     * <p>
     * Attempts to pass a {@code null} value to this method results in
     * undefined behavior and, most likely, exceptions. The created model
     * references the given array directly. Attempts to modify the array
     * after invoking this method results in undefined behavior.
     *
     * <p>
     *  从项目数组构造只读<code> ListModel </code>,并使用此模型调用{@code setModel}。
     * <p>
     *  尝试向此方法传递{@code null}值会导致未定义的行为,并且很可能会出现异常。创建的模型直接引用给定的数组。尝试在调用此方法后修改数组会导致未定义的行为。
     * 
     * 
     * @param listData an array of {@code E} containing the items to
     *        display in the list
     * @see #setModel
     */
    public void setListData(final E[] listData) {
        setModel (
            new AbstractListModel<E>() {
                public int getSize() { return listData.length; }
                public E getElementAt(int i) { return listData[i]; }
            }
        );
    }


    /**
     * Constructs a read-only <code>ListModel</code> from a <code>Vector</code>
     * and calls {@code setModel} with this model.
     * <p>
     * Attempts to pass a {@code null} value to this method results in
     * undefined behavior and, most likely, exceptions. The created model
     * references the given {@code Vector} directly. Attempts to modify the
     * {@code Vector} after invoking this method results in undefined behavior.
     *
     * <p>
     *  从<code> Vector </code>构造只读<code> ListModel </code>,并使用此模型调用{@code setModel}。
     * <p>
     *  尝试向此方法传递{@code null}值会导致未定义的行为,并且很可能会出现异常。创建的模型直接引用给定的{@code Vector}。
     * 尝试在调用此方法后修改{@code Vector}会导致未定义的行为。
     * 
     * 
     * @param listData a <code>Vector</code> containing the items to
     *                                          display in the list
     * @see #setModel
     */
    public void setListData(final Vector<? extends E> listData) {
        setModel (
            new AbstractListModel<E>() {
                public int getSize() { return listData.size(); }
                public E getElementAt(int i) { return listData.elementAt(i); }
            }
        );
    }


    /**
     * --- ListSelectionModel delegations and extensions ---
     * <p>
     *  --- ListSelectionModel委托和扩展---
     * 
     */


    /**
     * Returns an instance of {@code DefaultListSelectionModel}; called
     * during construction to initialize the list's selection model
     * property.
     *
     * <p>
     *  返回{@code DefaultListSelectionModel}的实例;在构造期间调用以初始化列表的选择模型属性。
     * 
     * 
     * @return a {@code DefaultListSelecitonModel}, used to initialize
     *         the list's selection model property during construction
     * @see #setSelectionModel
     * @see DefaultListSelectionModel
     */
    protected ListSelectionModel createSelectionModel() {
        return new DefaultListSelectionModel();
    }


    /**
     * Returns the current selection model. The selection model maintains the
     * selection state of the list. See the class level documentation for more
     * details.
     *
     * <p>
     *  返回当前选择模型。选择模型维护列表的选择状态。有关更多详细信息,请参阅类级文档。
     * 
     * 
     * @return the <code>ListSelectionModel</code> that maintains the
     *         list's selections
     *
     * @see #setSelectionModel
     * @see ListSelectionModel
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }


    /**
     * Notifies {@code ListSelectionListener}s added directly to the list
     * of selection changes made to the selection model. {@code JList}
     * listens for changes made to the selection in the selection model,
     * and forwards notification to listeners added to the list directly,
     * by calling this method.
     * <p>
     * This method constructs a {@code ListSelectionEvent} with this list
     * as the source, and the specified arguments, and sends it to the
     * registered {@code ListSelectionListeners}.
     *
     * <p>
     * 通知{@code ListSelectionListener}直接添加到对选择模型所做的选择更改列表中。
     *  {@code JList}侦听对选择模型中的选择所做的更改,并通过调用此方法将通知直接转发到添加到列表中的侦听器。
     * <p>
     *  此方法构造一个{@code ListSelectionEvent},此列表作为源和指定的参数,并将其发送到已注册的{@code ListSelectionListeners}。
     * 
     * 
     * @param firstIndex the first index in the range, {@code <= lastIndex}
     * @param lastIndex the last index in the range, {@code >= firstIndex}
     * @param isAdjusting whether or not this is one in a series of
     *        multiple events, where changes are still being made
     *
     * @see #addListSelectionListener
     * @see #removeListSelectionListener
     * @see javax.swing.event.ListSelectionEvent
     * @see EventListenerList
     */
    protected void fireSelectionValueChanged(int firstIndex, int lastIndex,
                                             boolean isAdjusting)
    {
        Object[] listeners = listenerList.getListenerList();
        ListSelectionEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListSelectionListener.class) {
                if (e == null) {
                    e = new ListSelectionEvent(this, firstIndex, lastIndex,
                                               isAdjusting);
                }
                ((ListSelectionListener)listeners[i+1]).valueChanged(e);
            }
        }
    }


    /* A ListSelectionListener that forwards ListSelectionEvents from
     * the selectionModel to the JList ListSelectionListeners.  The
     * forwarded events only differ from the originals in that their
     * source is the JList instead of the selectionModel itself.
     * <p>
     *  selectionModel到JList ListSelectionListeners。转发的事件只不同于原始的,因为它们的源是JList而不是selectionModel本身。
     * 
     */
    private class ListSelectionHandler implements ListSelectionListener, Serializable
    {
        public void valueChanged(ListSelectionEvent e) {
            fireSelectionValueChanged(e.getFirstIndex(),
                                      e.getLastIndex(),
                                      e.getValueIsAdjusting());
        }
    }


    /**
     * Adds a listener to the list, to be notified each time a change to the
     * selection occurs; the preferred way of listening for selection state
     * changes. {@code JList} takes care of listening for selection state
     * changes in the selection model, and notifies the given listener of
     * each change. {@code ListSelectionEvent}s sent to the listener have a
     * {@code source} property set to this list.
     *
     * <p>
     *  向列表添加侦听器,每当对选择进行更改时通知侦听器;监听选择状态变化的首选方式。 {@code JList}负责监听选择模型中的选择状态变化,并通知给定的监听器每个变化。
     *  {@code ListSelectionEvent}发送到侦听器时,会将{@code source}属性设置为此列表。
     * 
     * 
     * @param listener the {@code ListSelectionListener} to add
     * @see #getSelectionModel
     * @see #getListSelectionListeners
     */
    public void addListSelectionListener(ListSelectionListener listener)
    {
        if (selectionListener == null) {
            selectionListener = new ListSelectionHandler();
            getSelectionModel().addListSelectionListener(selectionListener);
        }

        listenerList.add(ListSelectionListener.class, listener);
    }


    /**
     * Removes a selection listener from the list.
     *
     * <p>
     *  从列表中删除选择侦听器。
     * 
     * 
     * @param listener the {@code ListSelectionListener} to remove
     * @see #addListSelectionListener
     * @see #getSelectionModel
     */
    public void removeListSelectionListener(ListSelectionListener listener) {
        listenerList.remove(ListSelectionListener.class, listener);
    }


    /**
     * Returns an array of all the {@code ListSelectionListener}s added
     * to this {@code JList} by way of {@code addListSelectionListener}.
     *
     * <p>
     *  通过{@code addListSelectionListener}返回添加到此{@code JList}的所有{@code ListSelectionListener}的数组。
     * 
     * 
     * @return all of the {@code ListSelectionListener}s on this list, or
     *         an empty array if no listeners have been added
     * @see #addListSelectionListener
     * @since 1.4
     */
    public ListSelectionListener[] getListSelectionListeners() {
        return listenerList.getListeners(ListSelectionListener.class);
    }


    /**
     * Sets the <code>selectionModel</code> for the list to a
     * non-<code>null</code> <code>ListSelectionModel</code>
     * implementation. The selection model handles the task of making single
     * selections, selections of contiguous ranges, and non-contiguous
     * selections.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     *  将列表的<code> selectionModel </code>设置为非<code> null </code> <code> ListSelectionModel </code>实现。
     * 选择模型处理单个选择,连续范围选择和不连续选择的任务。
     * <p>
     *  这是一个JavaBeans绑定属性。
     * 
     * 
     * @param selectionModel  the <code>ListSelectionModel</code> that
     *                          implements the selections
     * @exception IllegalArgumentException   if <code>selectionModel</code>
     *                                          is <code>null</code>
     * @see #getSelectionModel
     * @beaninfo
     *       bound: true
     * description: The selection model, recording which cells are selected.
     */
    public void setSelectionModel(ListSelectionModel selectionModel) {
        if (selectionModel == null) {
            throw new IllegalArgumentException("selectionModel must be non null");
        }

        /* Remove the forwarding ListSelectionListener from the old
         * selectionModel, and add it to the new one, if necessary.
         * <p>
         * selectionModel,并将其添加到新的,如果需要。
         * 
         */
        if (selectionListener != null) {
            this.selectionModel.removeListSelectionListener(selectionListener);
            selectionModel.addListSelectionListener(selectionListener);
        }

        ListSelectionModel oldValue = this.selectionModel;
        this.selectionModel = selectionModel;
        firePropertyChange("selectionModel", oldValue, selectionModel);
    }


    /**
     * Sets the selection mode for the list. This is a cover method that sets
     * the selection mode directly on the selection model.
     * <p>
     * The following list describes the accepted selection modes:
     * <ul>
     * <li>{@code ListSelectionModel.SINGLE_SELECTION} -
     *   Only one list index can be selected at a time. In this mode,
     *   {@code setSelectionInterval} and {@code addSelectionInterval} are
     *   equivalent, both replacing the current selection with the index
     *   represented by the second argument (the "lead").
     * <li>{@code ListSelectionModel.SINGLE_INTERVAL_SELECTION} -
     *   Only one contiguous interval can be selected at a time.
     *   In this mode, {@code addSelectionInterval} behaves like
     *   {@code setSelectionInterval} (replacing the current selection},
     *   unless the given interval is immediately adjacent to or overlaps
     *   the existing selection, and can be used to grow the selection.
     * <li>{@code ListSelectionModel.MULTIPLE_INTERVAL_SELECTION} -
     *   In this mode, there's no restriction on what can be selected.
     *   This mode is the default.
     * </ul>
     *
     * <p>
     *  设置列表的选择模式。这是一种直接在选择模型上设置选择模式的覆盖方法。
     * <p>
     *  以下列表描述了接受的选择模式：
     * <ul>
     *  <li> {@ code ListSelectionModel.SINGLE_SELECTION}  - 一次只能选择一个列表索引。
     * 在这种模式下,{@code setSelectionInterval}和{@code addSelectionInterval}是等效的,都将当前选择替换为由第二个参数("lead")表示的索引。
     *  <li> {@ code ListSelectionModel.SINGLE_INTERVAL_SELECTION}  - 一次只能选择一个连续的时间间隔。
     * 在这种模式下,{@code addSelectionInterval}的行为类似于{@code setSelectionInterval}(替换当前选择),除非给定的间隔与现有选择直接相邻或重叠,并可用
     * 于增大选择。
     *  <li> {@ code ListSelectionModel.SINGLE_INTERVAL_SELECTION}  - 一次只能选择一个连续的时间间隔。
     * <li> @code ListSelectionModel.MULTIPLE_INTERVAL_SELECTION}  - 在此模式下,对可选择的内容没有限制,此模式是默认模式。
     * </ul>
     * 
     * 
     * @param selectionMode the selection mode
     * @see #getSelectionMode
     * @throws IllegalArgumentException if the selection mode isn't
     *         one of those allowed
     * @beaninfo
     * description: The selection mode.
     *        enum: SINGLE_SELECTION            ListSelectionModel.SINGLE_SELECTION
     *              SINGLE_INTERVAL_SELECTION   ListSelectionModel.SINGLE_INTERVAL_SELECTION
     *              MULTIPLE_INTERVAL_SELECTION ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
     */
    public void setSelectionMode(int selectionMode) {
        getSelectionModel().setSelectionMode(selectionMode);
    }

    /**
     * Returns the current selection mode for the list. This is a cover
     * method that delegates to the method of the same name on the
     * list's selection model.
     *
     * <p>
     *  返回列表的当前选择模式。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the current selection mode
     * @see #setSelectionMode
     */
    public int getSelectionMode() {
        return getSelectionModel().getSelectionMode();
    }


    /**
     * Returns the anchor selection index. This is a cover method that
     * delegates to the method of the same name on the list's selection model.
     *
     * <p>
     *  返回锚选择索引。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the anchor selection index
     * @see ListSelectionModel#getAnchorSelectionIndex
     */
    public int getAnchorSelectionIndex() {
        return getSelectionModel().getAnchorSelectionIndex();
    }


    /**
     * Returns the lead selection index. This is a cover method that
     * delegates to the method of the same name on the list's selection model.
     *
     * <p>
     *  返回潜在客户选择索引。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the lead selection index
     * @see ListSelectionModel#getLeadSelectionIndex
     * @beaninfo
     * description: The lead selection index.
     */
    public int getLeadSelectionIndex() {
        return getSelectionModel().getLeadSelectionIndex();
    }


    /**
     * Returns the smallest selected cell index, or {@code -1} if the selection
     * is empty. This is a cover method that delegates to the method of the same
     * name on the list's selection model.
     *
     * <p>
     * 返回最小的选定单元格索引,如果选择为空,则返回{@code -1}。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the smallest selected cell index, or {@code -1}
     * @see ListSelectionModel#getMinSelectionIndex
     */
    public int getMinSelectionIndex() {
        return getSelectionModel().getMinSelectionIndex();
    }


    /**
     * Returns the largest selected cell index, or {@code -1} if the selection
     * is empty. This is a cover method that delegates to the method of the same
     * name on the list's selection model.
     *
     * <p>
     *  返回最大的选定单元格索引,如果选择为空,则返回{@code -1}。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the largest selected cell index
     * @see ListSelectionModel#getMaxSelectionIndex
     */
    public int getMaxSelectionIndex() {
        return getSelectionModel().getMaxSelectionIndex();
    }


    /**
     * Returns {@code true} if the specified index is selected,
     * else {@code false}. This is a cover method that delegates to the method
     * of the same name on the list's selection model.
     *
     * <p>
     *  如果选择了指定的索引,则返回{@code true},否则返回{@code false}。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @param index index to be queried for selection state
     * @return {@code true} if the specified index is selected,
     *         else {@code false}
     * @see ListSelectionModel#isSelectedIndex
     * @see #setSelectedIndex
     */
    public boolean isSelectedIndex(int index) {
        return getSelectionModel().isSelectedIndex(index);
    }


    /**
     * Returns {@code true} if nothing is selected, else {@code false}.
     * This is a cover method that delegates to the method of the same
     * name on the list's selection model.
     *
     * <p>
     *  如果没有选择,返回{@code true},否则返回{@code false}。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return {@code true} if nothing is selected, else {@code false}
     * @see ListSelectionModel#isSelectionEmpty
     * @see #clearSelection
     */
    public boolean isSelectionEmpty() {
        return getSelectionModel().isSelectionEmpty();
    }


    /**
     * Clears the selection; after calling this method, {@code isSelectionEmpty}
     * will return {@code true}. This is a cover method that delegates to the
     * method of the same name on the list's selection model.
     *
     * <p>
     *  清除选择;调用此方法后,{@code isSelectionEmpty}将返回{@code true}。这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @see ListSelectionModel#clearSelection
     * @see #isSelectionEmpty
     */
    public void clearSelection() {
        getSelectionModel().clearSelection();
    }


    /**
     * Selects the specified interval. Both {@code anchor} and {@code lead}
     * indices are included. {@code anchor} doesn't have to be less than or
     * equal to {@code lead}. This is a cover method that delegates to the
     * method of the same name on the list's selection model.
     * <p>
     * Refer to the documentation of the selection model class being used
     * for details on how values less than {@code 0} are handled.
     *
     * <p>
     *  选择指定的间隔。包括{@code anchor}和{@code lead}索引。 {@code anchor}不必小于或等于{@code lead}。
     * 这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * <p>
     *  有关如何处理小于{@code 0}的值的详细信息,请参阅用于选择模型类的文档。
     * 
     * 
     * @param anchor the first index to select
     * @param lead the last index to select
     * @see ListSelectionModel#setSelectionInterval
     * @see DefaultListSelectionModel#setSelectionInterval
     * @see #createSelectionModel
     * @see #addSelectionInterval
     * @see #removeSelectionInterval
     */
    public void setSelectionInterval(int anchor, int lead) {
        getSelectionModel().setSelectionInterval(anchor, lead);
    }


    /**
     * Sets the selection to be the union of the specified interval with current
     * selection. Both the {@code anchor} and {@code lead} indices are
     * included. {@code anchor} doesn't have to be less than or
     * equal to {@code lead}. This is a cover method that delegates to the
     * method of the same name on the list's selection model.
     * <p>
     * Refer to the documentation of the selection model class being used
     * for details on how values less than {@code 0} are handled.
     *
     * <p>
     * 将选择设置为指定时间间隔与当前选择的并集。包括{@code anchor}和{@code lead}索引。 {@code anchor}不必小于或等于{@code lead}。
     * 这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * <p>
     *  有关如何处理小于{@code 0}的值的详细信息,请参阅用于选择模型类的文档。
     * 
     * 
     * @param anchor the first index to add to the selection
     * @param lead the last index to add to the selection
     * @see ListSelectionModel#addSelectionInterval
     * @see DefaultListSelectionModel#addSelectionInterval
     * @see #createSelectionModel
     * @see #setSelectionInterval
     * @see #removeSelectionInterval
     */
    public void addSelectionInterval(int anchor, int lead) {
        getSelectionModel().addSelectionInterval(anchor, lead);
    }


    /**
     * Sets the selection to be the set difference of the specified interval
     * and the current selection. Both the {@code index0} and {@code index1}
     * indices are removed. {@code index0} doesn't have to be less than or
     * equal to {@code index1}. This is a cover method that delegates to the
     * method of the same name on the list's selection model.
     * <p>
     * Refer to the documentation of the selection model class being used
     * for details on how values less than {@code 0} are handled.
     *
     * <p>
     *  将选择设置为指定间隔和当前选择的设置差值。将删除{@code index0}和{@code index1}索引。 {@code index0}不必小于或等于{@code index1}。
     * 这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * <p>
     *  有关如何处理小于{@code 0}的值的详细信息,请参阅用于选择模型类的文档。
     * 
     * 
     * @param index0 the first index to remove from the selection
     * @param index1 the last index to remove from the selection
     * @see ListSelectionModel#removeSelectionInterval
     * @see DefaultListSelectionModel#removeSelectionInterval
     * @see #createSelectionModel
     * @see #setSelectionInterval
     * @see #addSelectionInterval
     */
    public void removeSelectionInterval(int index0, int index1) {
        getSelectionModel().removeSelectionInterval(index0, index1);
    }


    /**
     * Sets the selection model's {@code valueIsAdjusting} property. When
     * {@code true}, upcoming changes to selection should be considered part
     * of a single change. This property is used internally and developers
     * typically need not call this method. For example, when the model is being
     * updated in response to a user drag, the value of the property is set
     * to {@code true} when the drag is initiated and set to {@code false}
     * when the drag is finished. This allows listeners to update only
     * when a change has been finalized, rather than handling all of the
     * intermediate values.
     * <p>
     * You may want to use this directly if making a series of changes
     * that should be considered part of a single change.
     * <p>
     * This is a cover method that delegates to the method of the same name on
     * the list's selection model. See the documentation for
     * {@link javax.swing.ListSelectionModel#setValueIsAdjusting} for
     * more details.
     *
     * <p>
     *  设置选择模型的{@code valueIsAdjusting}属性。当{@code true}时,即将发生的选择更改应视为单次更改的一部分。此属性在内部使用,开发人员通常不需要调用此方法。
     * 例如,当模型响应用户拖动而更新时,属性的值在启动拖动时设置为{@code true},并在拖动完成时设置为{@code false}。这允许侦听器仅在更改已完成时更新,而不是处理所有中间值。
     * <p>
     * 如果进行一系列应该被视为单个更改的一部分的更改,您可能需要直接使用它。
     * <p>
     *  这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 有关更多详细信息,请参阅{@link javax.swing.ListSelectionModel#setValueIsAdjusting}的文档。
     * 
     * 
     * @param b the new value for the property
     * @see ListSelectionModel#setValueIsAdjusting
     * @see javax.swing.event.ListSelectionEvent#getValueIsAdjusting
     * @see #getValueIsAdjusting
     */
    public void setValueIsAdjusting(boolean b) {
        getSelectionModel().setValueIsAdjusting(b);
    }


    /**
     * Returns the value of the selection model's {@code isAdjusting} property.
     * <p>
     * This is a cover method that delegates to the method of the same name on
     * the list's selection model.
     *
     * <p>
     *  返回选择模型的{@code isAdjusting}属性的值。
     * <p>
     *  这是一个覆盖方法,它在列表的选择模型上委托同名的方法。
     * 
     * 
     * @return the value of the selection model's {@code isAdjusting} property.
     *
     * @see #setValueIsAdjusting
     * @see ListSelectionModel#getValueIsAdjusting
     */
    public boolean getValueIsAdjusting() {
        return getSelectionModel().getValueIsAdjusting();
    }


    /**
     * Returns an array of all of the selected indices, in increasing
     * order.
     *
     * <p>
     *  以递增的顺序返回所有所选索引的数组。
     * 
     * 
     * @return all of the selected indices, in increasing order,
     *         or an empty array if nothing is selected
     * @see #removeSelectionInterval
     * @see #addListSelectionListener
     */
    @Transient
    public int[] getSelectedIndices() {
        ListSelectionModel sm = getSelectionModel();
        int iMin = sm.getMinSelectionIndex();
        int iMax = sm.getMaxSelectionIndex();

        if ((iMin < 0) || (iMax < 0)) {
            return new int[0];
        }

        int[] rvTmp = new int[1+ (iMax - iMin)];
        int n = 0;
        for(int i = iMin; i <= iMax; i++) {
            if (sm.isSelectedIndex(i)) {
                rvTmp[n++] = i;
            }
        }
        int[] rv = new int[n];
        System.arraycopy(rvTmp, 0, rv, 0, n);
        return rv;
    }


    /**
     * Selects a single cell. Does nothing if the given index is greater
     * than or equal to the model size. This is a convenience method that uses
     * {@code setSelectionInterval} on the selection model. Refer to the
     * documentation for the selection model class being used for details on
     * how values less than {@code 0} are handled.
     *
     * <p>
     *  选择单个单元格。如果给定的索引大于或等于模型大小,则不执行任何操作。这是一个方便的方法,在选择模型上使用{@code setSelectionInterval}。
     * 有关如何处理小于{@code 0}的值的详细信息,请参阅用于选择模型类的文档。
     * 
     * 
     * @param index the index of the cell to select
     * @see ListSelectionModel#setSelectionInterval
     * @see #isSelectedIndex
     * @see #addListSelectionListener
     * @beaninfo
     * description: The index of the selected cell.
     */
    public void setSelectedIndex(int index) {
        if (index >= getModel().getSize()) {
            return;
        }
        getSelectionModel().setSelectionInterval(index, index);
    }


    /**
     * Changes the selection to be the set of indices specified by the given
     * array. Indices greater than or equal to the model size are ignored.
     * This is a convenience method that clears the selection and then uses
     * {@code addSelectionInterval} on the selection model to add the indices.
     * Refer to the documentation of the selection model class being used for
     * details on how values less than {@code 0} are handled.
     *
     * <p>
     *  将选择更改为由给定数组指定的索引集。大于或等于模型大小的指数将被忽略。这是一个方便的方法,清除选择,然后在选择模型上使用{@code addSelectionInterval}添加索引。
     * 有关如何处理小于{@code 0}的值的详细信息,请参阅用于选择模型类的文档。
     * 
     * 
     * @param indices an array of the indices of the cells to select,
     *                {@code non-null}
     * @see ListSelectionModel#addSelectionInterval
     * @see #isSelectedIndex
     * @see #addListSelectionListener
     * @throws NullPointerException if the given array is {@code null}
     */
    public void setSelectedIndices(int[] indices) {
        ListSelectionModel sm = getSelectionModel();
        sm.clearSelection();
        int size = getModel().getSize();
        for (int i : indices) {
            if (i < size) {
                sm.addSelectionInterval(i, i);
            }
        }
    }


    /**
     * Returns an array of all the selected values, in increasing order based
     * on their indices in the list.
     *
     * <p>
     *  返回所有选定值的数组,按照列表中的索引按升序排列。
     * 
     * 
     * @return the selected values, or an empty array if nothing is selected
     * @see #isSelectedIndex
     * @see #getModel
     * @see #addListSelectionListener
     *
     * @deprecated As of JDK 1.7, replaced by {@link #getSelectedValuesList()}
     */
    @Deprecated
    public Object[] getSelectedValues() {
        ListSelectionModel sm = getSelectionModel();
        ListModel<E> dm = getModel();

        int iMin = sm.getMinSelectionIndex();
        int iMax = sm.getMaxSelectionIndex();

        if ((iMin < 0) || (iMax < 0)) {
            return new Object[0];
        }

        Object[] rvTmp = new Object[1+ (iMax - iMin)];
        int n = 0;
        for(int i = iMin; i <= iMax; i++) {
            if (sm.isSelectedIndex(i)) {
                rvTmp[n++] = dm.getElementAt(i);
            }
        }
        Object[] rv = new Object[n];
        System.arraycopy(rvTmp, 0, rv, 0, n);
        return rv;
    }

    /**
     * Returns a list of all the selected items, in increasing order based
     * on their indices in the list.
     *
     * <p>
     *  根据列表中的索引以递增顺序返回所有选定项目的列表。
     * 
     * 
     * @return the selected items, or an empty list if nothing is selected
     * @see #isSelectedIndex
     * @see #getModel
     * @see #addListSelectionListener
     *
     * @since 1.7
     */
    public List<E> getSelectedValuesList() {
        ListSelectionModel sm = getSelectionModel();
        ListModel<E> dm = getModel();

        int iMin = sm.getMinSelectionIndex();
        int iMax = sm.getMaxSelectionIndex();

        if ((iMin < 0) || (iMax < 0)) {
            return Collections.emptyList();
        }

        List<E> selectedItems = new ArrayList<E>();
        for(int i = iMin; i <= iMax; i++) {
            if (sm.isSelectedIndex(i)) {
                selectedItems.add(dm.getElementAt(i));
            }
        }
        return selectedItems;
    }


    /**
     * Returns the smallest selected cell index; <i>the selection</i> when only
     * a single item is selected in the list. When multiple items are selected,
     * it is simply the smallest selected index. Returns {@code -1} if there is
     * no selection.
     * <p>
     * This method is a cover that delegates to {@code getMinSelectionIndex}.
     *
     * <p>
     * 返回最小的选定单元格索引; <i>选择</i>时,在列表中只选择一个项目。当选择多个项目时,它只是最小的所选索引。如果没有选择,则返回{@code -1}。
     * <p>
     *  此方法是委托{@code getMinSelectionIndex}的封面。
     * 
     * 
     * @return the smallest selected cell index
     * @see #getMinSelectionIndex
     * @see #addListSelectionListener
     */
    public int getSelectedIndex() {
        return getMinSelectionIndex();
    }


    /**
     * Returns the value for the smallest selected cell index;
     * <i>the selected value</i> when only a single item is selected in the
     * list. When multiple items are selected, it is simply the value for the
     * smallest selected index. Returns {@code null} if there is no selection.
     * <p>
     * This is a convenience method that simply returns the model value for
     * {@code getMinSelectionIndex}.
     *
     * <p>
     *  返回最小选定单元格索引的值; <i>所选值</i>,当列表中只选择一个项目时。当选择多个项目时,它只是所选最小索引的值。如果没有选择,则返回{@code null}。
     * <p>
     *  这是一个方便的方法,只返回{@code getMinSelectionIndex}的模型值。
     * 
     * 
     * @return the first selected value
     * @see #getMinSelectionIndex
     * @see #getModel
     * @see #addListSelectionListener
     */
    public E getSelectedValue() {
        int i = getMinSelectionIndex();
        return (i == -1) ? null : getModel().getElementAt(i);
    }


    /**
     * Selects the specified object from the list.
     *
     * <p>
     *  从列表中选择指定的对象。
     * 
     * 
     * @param anObject      the object to select
     * @param shouldScroll  {@code true} if the list should scroll to display
     *                      the selected object, if one exists; otherwise {@code false}
     */
    public void setSelectedValue(Object anObject,boolean shouldScroll) {
        if(anObject == null)
            setSelectedIndex(-1);
        else if(!anObject.equals(getSelectedValue())) {
            int i,c;
            ListModel<E> dm = getModel();
            for(i=0,c=dm.getSize();i<c;i++)
                if(anObject.equals(dm.getElementAt(i))){
                    setSelectedIndex(i);
                    if(shouldScroll)
                        ensureIndexIsVisible(i);
                    repaint();  /** FIX-ME setSelectedIndex does not redraw all the time with the basic l&f**/
                    return;
                }
            setSelectedIndex(-1);
        }
        repaint(); /** FIX-ME setSelectedIndex does not redraw all the time with the basic l&f**/
    }



    /**
     * --- The Scrollable Implementation ---
     * <p>
     *  ---滚动实现---
     * 
     */

    private void checkScrollableParameters(Rectangle visibleRect, int orientation) {
        if (visibleRect == null) {
            throw new IllegalArgumentException("visibleRect must be non-null");
        }
        switch (orientation) {
        case SwingConstants.VERTICAL:
        case SwingConstants.HORIZONTAL:
            break;
        default:
            throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
        }
    }


    /**
     * Computes the size of viewport needed to display {@code visibleRowCount}
     * rows. The value returned by this method depends on the layout
     * orientation:
     * <p>
     * <b>{@code VERTICAL}:</b>
     * <br>
     * This is trivial if both {@code fixedCellWidth} and {@code fixedCellHeight}
     * have been set (either explicitly or by specifying a prototype cell value).
     * The width is simply the {@code fixedCellWidth} plus the list's horizontal
     * insets. The height is the {@code fixedCellHeight} multiplied by the
     * {@code visibleRowCount}, plus the list's vertical insets.
     * <p>
     * If either {@code fixedCellWidth} or {@code fixedCellHeight} haven't been
     * specified, heuristics are used. If the model is empty, the width is
     * the {@code fixedCellWidth}, if greater than {@code 0}, or a hard-coded
     * value of {@code 256}. The height is the {@code fixedCellHeight} multiplied
     * by {@code visibleRowCount}, if {@code fixedCellHeight} is greater than
     * {@code 0}, otherwise it is a hard-coded value of {@code 16} multiplied by
     * {@code visibleRowCount}.
     * <p>
     * If the model isn't empty, the width is the preferred size's width,
     * typically the width of the widest list element. The height is the
     * {@code fixedCellHeight} multiplied by the {@code visibleRowCount},
     * plus the list's vertical insets.
     * <p>
     * <b>{@code VERTICAL_WRAP} or {@code HORIZONTAL_WRAP}:</b>
     * <br>
     * This method simply returns the value from {@code getPreferredSize}.
     * The list's {@code ListUI} is expected to override {@code getPreferredSize}
     * to return an appropriate value.
     *
     * <p>
     *  计算显示{@code visibleRowCount}行所需的视口大小。此方法返回的值取决于布局方向：
     * <p>
     *  <b> {@ code VERTICAL}：</b>
     * <br>
     *  如果已经设置了{@code fixedCellWidth}和{@code fixedCellHeight}(显式地或通过指定原型单元格值),这是微不足道的。
     * 宽度只是{@code fixedCellWidth}加上列表的水平插图。高度是{@code fixedCellHeight}乘以{@code visibleRowCount},加上列表的垂直插入。
     * <p>
     * 如果没有指定{@code fixedCellWidth}或{@code fixedCellHeight},则使用启发式。
     * 如果模型为空,则宽度为{@code fixedCellWidth}(如果大于{@code 0})或硬编码值{@code 256}。
     * 如果{@code fixedCellHeight}大于{@code 0},则高度为{@code fixedCellHeight}乘以{@code visibleRowCount},否则为{@code 16}
     * 的硬编码值乘以{@ code visibleRowCount}。
     * 如果模型为空,则宽度为{@code fixedCellWidth}(如果大于{@code 0})或硬编码值{@code 256}。
     * <p>
     *  如果模型不为空,则宽度为首选大小的宽度,通常为最宽列表元素的宽度。高度是{@code fixedCellHeight}乘以{@code visibleRowCount},加上列表的垂直插入。
     * <p>
     *  <b> {@ code VERTICAL_WRAP}或{@code HORIZONTAL_WRAP}：</b>
     * <br>
     *  此方法简单地从{@code getPreferredSize}返回值。列表的{@code ListUI}应覆盖{@code getPreferredSize}以返回适当的值。
     * 
     * 
     * @return a dimension containing the size of the viewport needed
     *          to display {@code visibleRowCount} rows
     * @see #getPreferredScrollableViewportSize
     * @see #setPrototypeCellValue
     */
    public Dimension getPreferredScrollableViewportSize()
    {
        if (getLayoutOrientation() != VERTICAL) {
            return getPreferredSize();
        }
        Insets insets = getInsets();
        int dx = insets.left + insets.right;
        int dy = insets.top + insets.bottom;

        int visibleRowCount = getVisibleRowCount();
        int fixedCellWidth = getFixedCellWidth();
        int fixedCellHeight = getFixedCellHeight();

        if ((fixedCellWidth > 0) && (fixedCellHeight > 0)) {
            int width = fixedCellWidth + dx;
            int height = (visibleRowCount * fixedCellHeight) + dy;
            return new Dimension(width, height);
        }
        else if (getModel().getSize() > 0) {
            int width = getPreferredSize().width;
            int height;
            Rectangle r = getCellBounds(0, 0);
            if (r != null) {
                height = (visibleRowCount * r.height) + dy;
            }
            else {
                // Will only happen if UI null, shouldn't matter what we return
                height = 1;
            }
            return new Dimension(width, height);
        }
        else {
            fixedCellWidth = (fixedCellWidth > 0) ? fixedCellWidth : 256;
            fixedCellHeight = (fixedCellHeight > 0) ? fixedCellHeight : 16;
            return new Dimension(fixedCellWidth, fixedCellHeight * visibleRowCount);
        }
    }


    /**
     * Returns the distance to scroll to expose the next or previous
     * row (for vertical scrolling) or column (for horizontal scrolling).
     * <p>
     * For horizontal scrolling, if the layout orientation is {@code VERTICAL},
     * then the list's font size is returned (or {@code 1} if the font is
     * {@code null}).
     *
     * <p>
     *  返回滚动到显示下一行或上一行(用于垂直滚动)或列(用于水平滚动)的距离。
     * <p>
     *  对于水平滚动,如果布局方向为{@code VERTICAL},则返回列表的字体大小(如果字体为{@code null},则为{@code 1})。
     * 
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation {@code SwingConstants.HORIZONTAL} or
     *                    {@code SwingConstants.VERTICAL}
     * @param direction less or equal to zero to scroll up/back,
     *                  greater than zero for down/forward
     * @return the "unit" increment for scrolling in the specified direction;
     *         always positive
     * @see #getScrollableBlockIncrement
     * @see Scrollable#getScrollableUnitIncrement
     * @throws IllegalArgumentException if {@code visibleRect} is {@code null}, or
     *         {@code orientation} isn't one of {@code SwingConstants.VERTICAL} or
     *         {@code SwingConstants.HORIZONTAL}
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
    {
        checkScrollableParameters(visibleRect, orientation);

        if (orientation == SwingConstants.VERTICAL) {
            int row = locationToIndex(visibleRect.getLocation());

            if (row == -1) {
                return 0;
            }
            else {
                /* Scroll Down */
                if (direction > 0) {
                    Rectangle r = getCellBounds(row, row);
                    return (r == null) ? 0 : r.height - (visibleRect.y - r.y);
                }
                /* Scroll Up */
                else {
                    Rectangle r = getCellBounds(row, row);

                    /* The first row is completely visible and it's row 0.
                     * We're done.
                     * <p>
                     *  我们完成了。
                     * 
                     */
                    if ((r.y == visibleRect.y) && (row == 0))  {
                        return 0;
                    }
                    /* The first row is completely visible, return the
                     * height of the previous row or 0 if the first row
                     * is the top row of the list.
                     * <p>
                     *  如果第一行是列表的第一行,则为上一行的高度或0。
                     * 
                     */
                    else if (r.y == visibleRect.y) {
                        Point loc = r.getLocation();
                        loc.y--;
                        int prevIndex = locationToIndex(loc);
                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);

                        if (prevR == null || prevR.y >= r.y) {
                            return 0;
                        }
                        return prevR.height;
                    }
                    /* The first row is partially visible, return the
                     * height of hidden part.
                     * <p>
                     *  隐藏部分的高度。
                     * 
                     */
                    else {
                        return visibleRect.y - r.y;
                    }
                }
            }
        } else if (orientation == SwingConstants.HORIZONTAL &&
                           getLayoutOrientation() != JList.VERTICAL) {
            boolean leftToRight = getComponentOrientation().isLeftToRight();
            int index;
            Point leadingPoint;

            if (leftToRight) {
                leadingPoint = visibleRect.getLocation();
            }
            else {
                leadingPoint = new Point(visibleRect.x + visibleRect.width -1,
                                         visibleRect.y);
            }
            index = locationToIndex(leadingPoint);

            if (index != -1) {
                Rectangle cellBounds = getCellBounds(index, index);
                if (cellBounds != null && cellBounds.contains(leadingPoint)) {
                    int leadingVisibleEdge;
                    int leadingCellEdge;

                    if (leftToRight) {
                        leadingVisibleEdge = visibleRect.x;
                        leadingCellEdge = cellBounds.x;
                    }
                    else {
                        leadingVisibleEdge = visibleRect.x + visibleRect.width;
                        leadingCellEdge = cellBounds.x + cellBounds.width;
                    }

                    if (leadingCellEdge != leadingVisibleEdge) {
                        if (direction < 0) {
                            // Show remainder of leading cell
                            return Math.abs(leadingVisibleEdge - leadingCellEdge);

                        }
                        else if (leftToRight) {
                            // Hide rest of leading cell
                            return leadingCellEdge + cellBounds.width - leadingVisibleEdge;
                        }
                        else {
                            // Hide rest of leading cell
                            return leadingVisibleEdge - cellBounds.x;
                        }
                    }
                    // ASSUME: All cells are the same width
                    return cellBounds.width;
                }
            }
        }
        Font f = getFont();
        return (f != null) ? f.getSize() : 1;
    }


    /**
     * Returns the distance to scroll to expose the next or previous block.
     * <p>
     * For vertical scrolling, the following rules are used:
     * <ul>
     * <li>if scrolling down, returns the distance to scroll so that the last
     * visible element becomes the first completely visible element
     * <li>if scrolling up, returns the distance to scroll so that the first
     * visible element becomes the last completely visible element
     * <li>returns {@code visibleRect.height} if the list is empty
     * </ul>
     * <p>
     * For horizontal scrolling, when the layout orientation is either
     * {@code VERTICAL_WRAP} or {@code HORIZONTAL_WRAP}:
     * <ul>
     * <li>if scrolling right, returns the distance to scroll so that the
     * last visible element becomes
     * the first completely visible element
     * <li>if scrolling left, returns the distance to scroll so that the first
     * visible element becomes the last completely visible element
     * <li>returns {@code visibleRect.width} if the list is empty
     * </ul>
     * <p>
     * For horizontal scrolling and {@code VERTICAL} orientation,
     * returns {@code visibleRect.width}.
     * <p>
     * Note that the value of {@code visibleRect} must be the equal to
     * {@code this.getVisibleRect()}.
     *
     * <p>
     *  返回滚动到显示下一个或上一个块的距离。
     * <p>
     *  对于垂直滚动,使用以下规则：
     * <ul>
     * <li>如果向下滚动,则返回滚动的距离,以便最后一个可见元素成为第一个完全可见的元素<li>如果向上滚动,则返回滚动的距离,以便第一个可见元素成为最后一个完全可见的元素<li >如果列表为空,则返回{@code visibleRect.height}
     * 。
     * </ul>
     * <p>
     *  对于水平滚动,当布局方向为{@code VERTICAL_WRAP}或{@code HORIZONTAL_WRAP}时：
     * <ul>
     *  <li>如果向右滚动,则返回滚动距离,以便最后一个可见元素成为第一个完全可见元素<li>如果向左滚动,则返回滚动距离,以使第一个可见元素成为最后一个完全可见元素<li >如果列表为空,则返回{@code visibleRect.width}
     * 。
     * </ul>
     * <p>
     *  对于水平滚动和{@code VERTICAL}方向,返回{@code visibleRect.width}。
     * <p>
     *  注意,{@code visibleRect}的值必须等于{@code this.getVisibleRect()}。
     * 
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation {@code SwingConstants.HORIZONTAL} or
     *                    {@code SwingConstants.VERTICAL}
     * @param direction less or equal to zero to scroll up/back,
     *                  greater than zero for down/forward
     * @return the "block" increment for scrolling in the specified direction;
     *         always positive
     * @see #getScrollableUnitIncrement
     * @see Scrollable#getScrollableBlockIncrement
     * @throws IllegalArgumentException if {@code visibleRect} is {@code null}, or
     *         {@code orientation} isn't one of {@code SwingConstants.VERTICAL} or
     *         {@code SwingConstants.HORIZONTAL}
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        checkScrollableParameters(visibleRect, orientation);
        if (orientation == SwingConstants.VERTICAL) {
            int inc = visibleRect.height;
            /* Scroll Down */
            if (direction > 0) {
                // last cell is the lowest left cell
                int last = locationToIndex(new Point(visibleRect.x, visibleRect.y+visibleRect.height-1));
                if (last != -1) {
                    Rectangle lastRect = getCellBounds(last,last);
                    if (lastRect != null) {
                        inc = lastRect.y - visibleRect.y;
                        if ( (inc == 0) && (last < getModel().getSize()-1) ) {
                            inc = lastRect.height;
                        }
                    }
                }
            }
            /* Scroll Up */
            else {
                int newFirst = locationToIndex(new Point(visibleRect.x, visibleRect.y-visibleRect.height));
                int first = getFirstVisibleIndex();
                if (newFirst != -1) {
                    if (first == -1) {
                        first = locationToIndex(visibleRect.getLocation());
                    }
                    Rectangle newFirstRect = getCellBounds(newFirst,newFirst);
                    Rectangle firstRect = getCellBounds(first,first);
                    if ((newFirstRect != null) && (firstRect!=null)) {
                        while ( (newFirstRect.y + visibleRect.height <
                                 firstRect.y + firstRect.height) &&
                                (newFirstRect.y < firstRect.y) ) {
                            newFirst++;
                            newFirstRect = getCellBounds(newFirst,newFirst);
                        }
                        inc = visibleRect.y - newFirstRect.y;
                        if ( (inc <= 0) && (newFirstRect.y > 0)) {
                            newFirst--;
                            newFirstRect = getCellBounds(newFirst,newFirst);
                            if (newFirstRect != null) {
                                inc = visibleRect.y - newFirstRect.y;
                            }
                        }
                    }
                }
            }
            return inc;
        }
        else if (orientation == SwingConstants.HORIZONTAL &&
                 getLayoutOrientation() != JList.VERTICAL) {
            boolean leftToRight = getComponentOrientation().isLeftToRight();
            int inc = visibleRect.width;
            /* Scroll Right (in ltr mode) or Scroll Left (in rtl mode) */
            if (direction > 0) {
                // position is upper right if ltr, or upper left otherwise
                int x = visibleRect.x + (leftToRight ? (visibleRect.width - 1) : 0);
                int last = locationToIndex(new Point(x, visibleRect.y));

                if (last != -1) {
                    Rectangle lastRect = getCellBounds(last,last);
                    if (lastRect != null) {
                        if (leftToRight) {
                            inc = lastRect.x - visibleRect.x;
                        } else {
                            inc = visibleRect.x + visibleRect.width
                                      - (lastRect.x + lastRect.width);
                        }
                        if (inc < 0) {
                            inc += lastRect.width;
                        } else if ( (inc == 0) && (last < getModel().getSize()-1) ) {
                            inc = lastRect.width;
                        }
                    }
                }
            }
            /* Scroll Left (in ltr mode) or Scroll Right (in rtl mode) */
            else {
                // position is upper left corner of the visibleRect shifted
                // left by the visibleRect.width if ltr, or upper right shifted
                // right by the visibleRect.width otherwise
                int x = visibleRect.x + (leftToRight
                                         ? -visibleRect.width
                                         : visibleRect.width - 1 + visibleRect.width);
                int first = locationToIndex(new Point(x, visibleRect.y));

                if (first != -1) {
                    Rectangle firstRect = getCellBounds(first,first);
                    if (firstRect != null) {
                        // the right of the first cell
                        int firstRight = firstRect.x + firstRect.width;

                        if (leftToRight) {
                            if ((firstRect.x < visibleRect.x - visibleRect.width)
                                    && (firstRight < visibleRect.x)) {
                                inc = visibleRect.x - firstRight;
                            } else {
                                inc = visibleRect.x - firstRect.x;
                            }
                        } else {
                            int visibleRight = visibleRect.x + visibleRect.width;

                            if ((firstRight > visibleRight + visibleRect.width)
                                    && (firstRect.x > visibleRight)) {
                                inc = firstRect.x - visibleRight;
                            } else {
                                inc = firstRight - visibleRight;
                            }
                        }
                    }
                }
            }
            return inc;
        }
        return visibleRect.width;
    }


    /**
     * Returns {@code true} if this {@code JList} is displayed in a
     * {@code JViewport} and the viewport is wider than the list's
     * preferred width, or if the layout orientation is {@code HORIZONTAL_WRAP}
     * and {@code visibleRowCount <= 0}; otherwise returns {@code false}.
     * <p>
     * If {@code false}, then don't track the viewport's width. This allows
     * horizontal scrolling if the {@code JViewport} is itself embedded in a
     * {@code JScrollPane}.
     *
     * <p>
     *  如果{@code JList}显示在{@code JViewport}中且视口比列表的首选宽度宽,或者布局方向为{@code HORIZONTAL_WRAP}和{@code visibleRowCount < = 0};否则返回{@code false}。
     * <p>
     *  如果{@code false},那么不跟踪视口的宽度。如果{@code JViewport}本身嵌入在{@code JScrollPane}中,这允许水平滚动。
     * 
     * 
     * @return whether or not an enclosing viewport should force the list's
     *         width to match its own
     * @see Scrollable#getScrollableTracksViewportWidth
     */
    public boolean getScrollableTracksViewportWidth() {
        if (getLayoutOrientation() == HORIZONTAL_WRAP &&
                                      getVisibleRowCount() <= 0) {
            return true;
        }
        Container parent = SwingUtilities.getUnwrappedParent(this);
        if (parent instanceof JViewport) {
            return parent.getWidth() > getPreferredSize().width;
        }
        return false;
    }

    /**
     * Returns {@code true} if this {@code JList} is displayed in a
     * {@code JViewport} and the viewport is taller than the list's
     * preferred height, or if the layout orientation is {@code VERTICAL_WRAP}
     * and {@code visibleRowCount <= 0}; otherwise returns {@code false}.
     * <p>
     * If {@code false}, then don't track the viewport's height. This allows
     * vertical scrolling if the {@code JViewport} is itself embedded in a
     * {@code JScrollPane}.
     *
     * <p>
     * 如果{@code JList}显示在{@code JViewport}中,且视口高于列表的首选高度,或者布局方向为{@code VERTICAL_WRAP}和{@code visibleRowCount < = 0};否则返回{@code false}。
     * <p>
     *  如果{@code false},那么不跟踪视口的高度。这允许垂直滚动,如果{@code JViewport}本身嵌入在{@code JScrollPane}。
     * 
     * 
     * @return whether or not an enclosing viewport should force the list's
     *         height to match its own
     * @see Scrollable#getScrollableTracksViewportHeight
     */
    public boolean getScrollableTracksViewportHeight() {
        if (getLayoutOrientation() == VERTICAL_WRAP &&
                     getVisibleRowCount() <= 0) {
            return true;
        }
        Container parent = SwingUtilities.getUnwrappedParent(this);
        if (parent instanceof JViewport) {
            return parent.getHeight() > getPreferredSize().height;
        }
        return false;
    }


    /*
     * See {@code readObject} and {@code writeObject} in {@code JComponent}
     * for more information about serialization in Swing.
     * <p>
     *  有关在Swing中序列化的更多信息,请参阅{@code readComponent}中的{@code readObject}和{@code writeObject}。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * Returns a {@code String} representation of this {@code JList}.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned {@code String} may vary
     * between implementations. The returned {@code String} may be empty,
     * but may not be {@code null}.
     *
     * <p>
     *  返回此{@code JList}的{@code String}表示形式。此方法仅用于调试目的,并且返回的{@code String}的内容和格式可能因实现而异。
     * 返回的{@code String}可能为空,但可能不是{@code null}。
     * 
     * 
     * @return  a {@code String} representation of this {@code JList}.
     */
    protected String paramString() {
        String selectionForegroundString = (selectionForeground != null ?
                                            selectionForeground.toString() :
                                            "");
        String selectionBackgroundString = (selectionBackground != null ?
                                            selectionBackground.toString() :
                                            "");

        return super.paramString() +
        ",fixedCellHeight=" + fixedCellHeight +
        ",fixedCellWidth=" + fixedCellWidth +
        ",horizontalScrollIncrement=" + horizontalScrollIncrement +
        ",selectionBackground=" + selectionBackgroundString +
        ",selectionForeground=" + selectionForegroundString +
        ",visibleRowCount=" + visibleRowCount +
        ",layoutOrientation=" + layoutOrientation;
    }


    /**
     * --- Accessibility Support ---
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Gets the {@code AccessibleContext} associated with this {@code JList}.
     * For {@code JList}, the {@code AccessibleContext} takes the form of an
     * {@code AccessibleJList}.
     * <p>
     * A new {@code AccessibleJList} instance is created if necessary.
     *
     * <p>
     *  获取与此{@code JList}相关联的{@code AccessibleContext}。
     * 对于{@code JList},{@code AccessibleContext}采用{@code AccessibleJList}的形式。
     * <p>
     *  如有必要,将创建一个新的{@code AccessibleJList}实例。
     * 
     * 
     * @return an {@code AccessibleJList} that serves as the
     *         {@code AccessibleContext} of this {@code JList}
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJList();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * {@code JList} class. It provides an implementation of the
     * Java Accessibility API appropriate to list user-interface
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
     *  此类实现{@code JList}类的辅助功能支持。它提供了适用于列出用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJList extends AccessibleJComponent
        implements AccessibleSelection, PropertyChangeListener,
        ListSelectionListener, ListDataListener {

        int leadSelectionIndex;

        public AccessibleJList() {
            super();
            JList.this.addPropertyChangeListener(this);
            JList.this.getSelectionModel().addListSelectionListener(this);
            JList.this.getModel().addListDataListener(this);
            leadSelectionIndex = JList.this.getLeadSelectionIndex();
        }

        /**
         * Property Change Listener change method. Used to track changes
         * to the DataModel and ListSelectionModel, in order to re-set
         * listeners to those for reporting changes there via the Accessibility
         * PropertyChange mechanism.
         *
         * <p>
         *  属性更改侦听器更改方法。用于跟踪对DataModel和ListSelectionModel的更改,以便将监听器重新设置为通过辅助功能属性更改机制报告更改的监听器。
         * 
         * 
         * @param e PropertyChangeEvent
         */
        public void propertyChange(PropertyChangeEvent e) {
            String name = e.getPropertyName();
            Object oldValue = e.getOldValue();
            Object newValue = e.getNewValue();

                // re-set listData listeners
            if (name.compareTo("model") == 0) {

                if (oldValue != null && oldValue instanceof ListModel) {
                    ((ListModel) oldValue).removeListDataListener(this);
                }
                if (newValue != null && newValue instanceof ListModel) {
                    ((ListModel) newValue).addListDataListener(this);
                }

                // re-set listSelectionModel listeners
            } else if (name.compareTo("selectionModel") == 0) {

                if (oldValue != null && oldValue instanceof ListSelectionModel) {
                    ((ListSelectionModel) oldValue).removeListSelectionListener(this);
                }
                if (newValue != null && newValue instanceof ListSelectionModel) {
                    ((ListSelectionModel) newValue).addListSelectionListener(this);
                }

                firePropertyChange(
                    AccessibleContext.ACCESSIBLE_SELECTION_PROPERTY,
                    Boolean.valueOf(false), Boolean.valueOf(true));
            }
        }

        /**
         * List Selection Listener value change method. Used to fire
         * the property change
         *
         * <p>
         *  列表选择监听器值更改方法。用于触发属性更改
         * 
         * 
         * @param e ListSelectionEvent
         *
         */
        public void valueChanged(ListSelectionEvent e) {
            int oldLeadSelectionIndex = leadSelectionIndex;
            leadSelectionIndex = JList.this.getLeadSelectionIndex();
            if (oldLeadSelectionIndex != leadSelectionIndex) {
                Accessible oldLS, newLS;
                oldLS = (oldLeadSelectionIndex >= 0)
                        ? getAccessibleChild(oldLeadSelectionIndex)
                        : null;
                newLS = (leadSelectionIndex >= 0)
                        ? getAccessibleChild(leadSelectionIndex)
                        : null;
                firePropertyChange(AccessibleContext.ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY,
                                   oldLS, newLS);
            }

            firePropertyChange(AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                               Boolean.valueOf(false), Boolean.valueOf(true));
            firePropertyChange(AccessibleContext.ACCESSIBLE_SELECTION_PROPERTY,
                               Boolean.valueOf(false), Boolean.valueOf(true));

            // Process the State changes for Multiselectable
            AccessibleStateSet s = getAccessibleStateSet();
            ListSelectionModel lsm = JList.this.getSelectionModel();
            if (lsm.getSelectionMode() != ListSelectionModel.SINGLE_SELECTION) {
                if (!s.contains(AccessibleState.MULTISELECTABLE)) {
                    s.add(AccessibleState.MULTISELECTABLE);
                    firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                       null, AccessibleState.MULTISELECTABLE);
                }
            } else {
                if (s.contains(AccessibleState.MULTISELECTABLE)) {
                    s.remove(AccessibleState.MULTISELECTABLE);
                    firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                       AccessibleState.MULTISELECTABLE, null);
                }
            }
        }

        /**
         * List Data Listener interval added method. Used to fire the visible data property change
         *
         * <p>
         *  列表数据侦听器间隔添加方法。用于触发可见数据属性更改
         * 
         * 
         * @param e ListDataEvent
         *
         */
        public void intervalAdded(ListDataEvent e) {
            firePropertyChange(AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                               Boolean.valueOf(false), Boolean.valueOf(true));
        }

        /**
         * List Data Listener interval removed method. Used to fire the visible data property change
         *
         * <p>
         *  列表数据侦听器间隔删除方法。用于触发可见数据属性更改
         * 
         * 
         * @param e ListDataEvent
         *
         */
        public void intervalRemoved(ListDataEvent e) {
            firePropertyChange(AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                               Boolean.valueOf(false), Boolean.valueOf(true));
        }

        /**
         * List Data Listener contents changed method. Used to fire the visible data property change
         *
         * <p>
         *  列表数据监听器内容更改方法。用于触发可见数据属性更改
         * 
         * 
         * @param e ListDataEvent
         *
         */
         public void contentsChanged(ListDataEvent e) {
             firePropertyChange(AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                                Boolean.valueOf(false), Boolean.valueOf(true));
         }

    // AccessibleContext methods

        /**
         * Get the state set of this object.
         *
         * <p>
         *  获取此对象的状态集。
         * 
         * 
         * @return an instance of AccessibleState containing the current state
         * of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (selectionModel.getSelectionMode() !=
                ListSelectionModel.SINGLE_SELECTION) {
                states.add(AccessibleState.MULTISELECTABLE);
            }
            return states;
        }

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
            return AccessibleRole.LIST;
        }

        /**
         * Returns the <code>Accessible</code> child contained at
         * the local coordinate <code>Point</code>, if one exists.
         * Otherwise returns <code>null</code>.
         *
         * <p>
         *  返回包含在本地坐标<code> Point </code>(如果存在)中的<code> Accessible </code>子代。否则返回<code> null </code>。
         * 
         * 
         * @return the <code>Accessible</code> at the specified
         *    location, if it exists
         */
        public Accessible getAccessibleAt(Point p) {
            int i = locationToIndex(p);
            if (i >= 0) {
                return new AccessibleJListChild(JList.this, i);
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
            return getModel().getSize();
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
            if (i >= getModel().getSize()) {
                return null;
            } else {
                return new AccessibleJListChild(JList.this, i);
            }
        }

        /**
         * Get the AccessibleSelection associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleSelection interface on behalf of itself.
         *
         * <p>
         * 获取与此对象关联的AccessibleSelection。在为此类实现Java Accessibility API时,返回此对象,它负责代表自身实现AccessibleSelection接口。
         * 
         * 
         * @return this object
         */
        public AccessibleSelection getAccessibleSelection() {
            return this;
        }


    // AccessibleSelection methods

        /**
         * Returns the number of items currently selected.
         * If no items are selected, the return value will be 0.
         *
         * <p>
         *  返回当前选择的项目数。如果未选择任何项目,则返回值将为0。
         * 
         * 
         * @return the number of items currently selected.
         */
         public int getAccessibleSelectionCount() {
             return JList.this.getSelectedIndices().length;
         }

        /**
         * Returns an Accessible representing the specified selected item
         * in the object.  If there isn't a selection, or there are
         * fewer items selected than the integer passed in, the return
         * value will be <code>null</code>.
         *
         * <p>
         *  返回表示对象中指定的选定项目的Accessible。如果没有选择,或者选择的项目少于传递的整数,则返回值将为<code> null </code>。
         * 
         * 
         * @param i the zero-based index of selected items
         * @return an Accessible containing the selected item
         */
         public Accessible getAccessibleSelection(int i) {
             int len = getAccessibleSelectionCount();
             if (i < 0 || i >= len) {
                 return null;
             } else {
                 return getAccessibleChild(JList.this.getSelectedIndices()[i]);
             }
         }

        /**
         * Returns true if the current child of this object is selected.
         *
         * <p>
         *  如果选择此对象的当前子项,则返回true。
         * 
         * 
         * @param i the zero-based index of the child in this Accessible
         * object.
         * @see AccessibleContext#getAccessibleChild
         */
        public boolean isAccessibleChildSelected(int i) {
            return isSelectedIndex(i);
        }

        /**
         * Adds the specified selected item in the object to the object's
         * selection.  If the object supports multiple selections,
         * the specified item is added to any existing selection, otherwise
         * it replaces any existing selection in the object.  If the
         * specified item is already selected, this method has no effect.
         *
         * <p>
         *  将对象中指定的选定项目添加到对象的选择。如果对象支持多个选择,则将指定的项目添加到任何现有选择,否则将替换对象中的任何现有选择。如果已选择指定的项目,则此方法无效。
         * 
         * 
         * @param i the zero-based index of selectable items
         */
         public void addAccessibleSelection(int i) {
             JList.this.addSelectionInterval(i, i);
         }

        /**
         * Removes the specified selected item in the object from the object's
         * selection.  If the specified item isn't currently selected, this
         * method has no effect.
         *
         * <p>
         *  从对象的选择中删除对象中指定的选定项目。如果当前未选择指定的项目,则此方法无效。
         * 
         * 
         * @param i the zero-based index of selectable items
         */
         public void removeAccessibleSelection(int i) {
             JList.this.removeSelectionInterval(i, i);
         }

        /**
         * Clears the selection in the object, so that nothing in the
         * object is selected.
         * <p>
         *  清除对象中的选择,以便不选择对象中的任何内容。
         * 
         */
         public void clearAccessibleSelection() {
             JList.this.clearSelection();
         }

        /**
         * Causes every selected item in the object to be selected
         * if the object supports multiple selections.
         * <p>
         *  如果对象支持多个选择,则导致选择对象中的每个选定项目。
         * 
         */
         public void selectAllAccessibleSelection() {
             JList.this.addSelectionInterval(0, getAccessibleChildrenCount() -1);
         }

          /**
           * This class implements accessibility support appropriate
           * for list children.
           * <p>
           *  此类实现适用于列表孩子的辅助功能支持。
           * 
           */
        protected class AccessibleJListChild extends AccessibleContext
                implements Accessible, AccessibleComponent {
            private JList<E>     parent = null;
            private int       indexInParent;
            private Component component = null;
            private AccessibleContext accessibleContext = null;
            private ListModel<E> listModel;
            private ListCellRenderer<? super E> cellRenderer = null;

            public AccessibleJListChild(JList<E> parent, int indexInParent) {
                this.parent = parent;
                this.setAccessibleParent(parent);
                this.indexInParent = indexInParent;
                if (parent != null) {
                    listModel = parent.getModel();
                    cellRenderer = parent.getCellRenderer();
                }
            }

            private Component getCurrentComponent() {
                return getComponentAtIndex(indexInParent);
            }

            private AccessibleContext getCurrentAccessibleContext() {
                Component c = getComponentAtIndex(indexInParent);
                if (c instanceof Accessible) {
                    return c.getAccessibleContext();
                } else {
                    return null;
                }
            }

            private Component getComponentAtIndex(int index) {
                if (index < 0 || index >= listModel.getSize()) {
                    return null;
                }
                if ((parent != null)
                        && (listModel != null)
                        && cellRenderer != null) {
                    E value = listModel.getElementAt(index);
                    boolean isSelected = parent.isSelectedIndex(index);
                    boolean isFocussed = parent.isFocusOwner()
                            && (index == parent.getLeadSelectionIndex());
                    return cellRenderer.getListCellRendererComponent(
                            parent,
                            value,
                            index,
                            isSelected,
                            isFocussed);
                } else {
                    return null;
                }
            }


            // Accessible Methods
           /**
            * Get the AccessibleContext for this object. In the
            * implementation of the Java Accessibility API for this class,
            * returns this object, which is its own AccessibleContext.
            *
            * <p>
            * 获取此对象的AccessibleContext。在为该类实现Java Accessibility API时,返回此对象,这是它自己的AccessibleContext。
            * 
            * 
            * @return this object
            */
            public AccessibleContext getAccessibleContext() {
                return this;
            }


            // AccessibleContext methods

            public String getAccessibleName() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleName();
                } else {
                    return null;
                }
            }

            public void setAccessibleName(String s) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.setAccessibleName(s);
                }
            }

            public String getAccessibleDescription() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleDescription();
                } else {
                    return null;
                }
            }

            public void setAccessibleDescription(String s) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.setAccessibleDescription(s);
                }
            }

            public AccessibleRole getAccessibleRole() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleRole();
                } else {
                    return null;
                }
            }

            public AccessibleStateSet getAccessibleStateSet() {
                AccessibleContext ac = getCurrentAccessibleContext();
                AccessibleStateSet s;
                if (ac != null) {
                    s = ac.getAccessibleStateSet();
                } else {
                    s = new AccessibleStateSet();
                }

                s.add(AccessibleState.SELECTABLE);
                if (parent.isFocusOwner()
                    && (indexInParent == parent.getLeadSelectionIndex())) {
                    s.add(AccessibleState.ACTIVE);
                }
                if (parent.isSelectedIndex(indexInParent)) {
                    s.add(AccessibleState.SELECTED);
                }
                if (this.isShowing()) {
                    s.add(AccessibleState.SHOWING);
                } else if (s.contains(AccessibleState.SHOWING)) {
                    s.remove(AccessibleState.SHOWING);
                }
                if (this.isVisible()) {
                    s.add(AccessibleState.VISIBLE);
                } else if (s.contains(AccessibleState.VISIBLE)) {
                    s.remove(AccessibleState.VISIBLE);
                }
                s.add(AccessibleState.TRANSIENT); // cell-rendered
                return s;
            }

            public int getAccessibleIndexInParent() {
                return indexInParent;
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
                }
            }

            public void removePropertyChangeListener(PropertyChangeListener l) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    ac.removePropertyChangeListener(l);
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
            *  获取与此对象关联的AccessibleComponent。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleComponent接口。
            * 
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
                int fi = parent.getFirstVisibleIndex();
                int li = parent.getLastVisibleIndex();
                // The UI incorrectly returns a -1 for the last
                // visible index if the list is smaller than the
                // viewport size.
                if (li == -1) {
                    li = parent.getModel().getSize() - 1;
                }
                return ((indexInParent >= fi)
                        && (indexInParent <= li));
            }

            public void setVisible(boolean b) {
            }

            public boolean isShowing() {
                return (parent.isShowing() && isVisible());
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
                    Point listLocation = parent.getLocationOnScreen();
                    Point componentLocation = parent.indexToLocation(indexInParent);
                    if (componentLocation != null) {
                        componentLocation.translate(listLocation.x, listLocation.y);
                        return componentLocation;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }

            public Point getLocation() {
                if (parent != null) {
                    return parent.indexToLocation(indexInParent);
                } else {
                    return null;
                }
            }

            public void setLocation(Point p) {
                if ((parent != null)  && (parent.contains(p))) {
                    ensureIndexIsVisible(indexInParent);
                }
            }

            public Rectangle getBounds() {
                if (parent != null) {
                    return parent.getCellBounds(indexInParent,indexInParent);
                } else {
                    return null;
                }
            }

            public void setBounds(Rectangle r) {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac instanceof AccessibleComponent) {
                    ((AccessibleComponent) ac).setBounds(r);
                }
            }

            public Dimension getSize() {
                Rectangle cellBounds = this.getBounds();
                if (cellBounds != null) {
                    return cellBounds.getSize();
                } else {
                    return null;
                }
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

            // TIGER - 4733624
            /**
             * Returns the icon for the element renderer, as the only item
             * of an array of <code>AccessibleIcon</code>s or a <code>null</code> array
             * if the renderer component contains no icons.
             *
             * <p>
             *  如果渲染器组件不包含图标,则返回元素渲染器的图标作为<code> AccessibleIcon </code> s或<code> null </code>数组中唯一的数组。
             * 
             * @return an array containing the accessible icon
             *         or a <code>null</code> array if none
             * @since 1.3
             */
            public AccessibleIcon [] getAccessibleIcon() {
                AccessibleContext ac = getCurrentAccessibleContext();
                if (ac != null) {
                    return ac.getAccessibleIcon();
                } else {
                    return null;
                }
            }
        } // inner class AccessibleJListChild
    } // inner class AccessibleJList
}
