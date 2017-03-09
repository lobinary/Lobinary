/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import java.util.Vector;
import java.util.Locale;
import java.util.EventListener;
import java.awt.peer.ListPeer;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.accessibility.*;


/**
 * The <code>List</code> component presents the user with a
 * scrolling list of text items. The list can be set up so that
 * the user can choose either one item or multiple items.
 * <p>
 * For example, the code&nbsp;.&nbsp;.&nbsp;.
 *
 * <hr><blockquote><pre>
 * List lst = new List(4, false);
 * lst.add("Mercury");
 * lst.add("Venus");
 * lst.add("Earth");
 * lst.add("JavaSoft");
 * lst.add("Mars");
 * lst.add("Jupiter");
 * lst.add("Saturn");
 * lst.add("Uranus");
 * lst.add("Neptune");
 * lst.add("Pluto");
 * cnt.add(lst);
 * </pre></blockquote><hr>
 * <p>
 * where <code>cnt</code> is a container, produces the following
 * scrolling list:
 * <p>
 * <img src="doc-files/List-1.gif"
 * alt="Shows a list containing: Venus, Earth, JavaSoft, and Mars. Javasoft is selected." style="float:center; margin: 7px 10px;">
 * <p>
 * If the List allows multiple selections, then clicking on
 * an item that is already selected deselects it. In the preceding
 * example, only one item from the scrolling list can be selected
 * at a time, since the second argument when creating the new scrolling
 * list is <code>false</code>. If the List does not allow multiple
 * selections, selecting an item causes any other selected item
 * to be deselected.
 * <p>
 * Note that the list in the example shown was created with four visible
 * rows.  Once the list has been created, the number of visible rows
 * cannot be changed.  A default <code>List</code> is created with
 * four rows, so that <code>lst = new List()</code> is equivalent to
 * <code>list = new List(4, false)</code>.
 * <p>
 * Beginning with Java&nbsp;1.1, the Abstract Window Toolkit
 * sends the <code>List</code> object all mouse, keyboard, and focus events
 * that occur over it. (The old AWT event model is being maintained
 * only for backwards compatibility, and its use is discouraged.)
 * <p>
 * When an item is selected or deselected by the user, AWT sends an instance
 * of <code>ItemEvent</code> to the list.
 * When the user double-clicks on an item in a scrolling list,
 * AWT sends an instance of <code>ActionEvent</code> to the
 * list following the item event. AWT also generates an action event
 * when the user presses the return key while an item in the
 * list is selected.
 * <p>
 * If an application wants to perform some action based on an item
 * in this list being selected or activated by the user, it should implement
 * <code>ItemListener</code> or <code>ActionListener</code>
 * as appropriate and register the new listener to receive
 * events from this list.
 * <p>
 * For multiple-selection scrolling lists, it is considered a better
 * user interface to use an external gesture (such as clicking on a
 * button) to trigger the action.
 * <p>
 *  <code> List </code>组件向用户显示文本项的滚动列表。可以设置列表,以便用户可以选择一个项目或多个项目。
 * <p>
 *  例如,代码&nbsp;。&nbsp;。&nbsp;
 * 
 *  <hr> <blockquote> <pre> List lst = new List(4,false); lst.add("Mercury"); lst.add("Venus"); lst.add(
 * "Earth"); lst.add("JavaSoft"); lst.add("Mars"); lst.add("Jupiter"); lst.add("Saturn"); lst.add("天王星")
 * ; lst.add("Neptune"); lst.add("Pluto"); cnt.add(lst); </pre> </blockquote> <hr>。
 * <p>
 *  其中<code> cnt </code>是一个容器,生成以下滚动列表：
 * <p>
 *  <img src ="doc-files / List-1.gif"
 * alt="Shows a list containing: Venus, Earth, JavaSoft, and Mars. Javasoft is selected." style="float:center; margin: 7px 10px;">
 * <p>
 *  如果列表允许多个选择,则单击已选择的项目可取消选择它。在前面的示例中,每次只能选择来自滚动列表的一个项目,因为创建新滚动列表时的第二个参数是<code> false </code>。
 * 如果列表不允许多个选择,则选择项目会导致取消选择任何其他所选项目。
 * <p>
 *  请注意,所示示例中的列表是使用四个可见行创建的。创建列表后,将无法更改可见行的数量。
 * 使用四行创建默认<code> List </code>,使得<code> lst = new List()</code>等效于<code> list = new List(4,false)</code>
 *  。
 *  请注意,所示示例中的列表是使用四个可见行创建的。创建列表后,将无法更改可见行的数量。
 * <p>
 * 从Java 1.1开始,抽象窗口工具包向<code> List </code>对象发送所有鼠标,键盘和焦点事件。 (旧AWT事件模型仅用于向后兼容性,并且不建议使用它。)
 * <p>
 *  当用户选择或取消选择项目时,AWT会将<code> ItemEvent </code>的实例发送到列表。
 * 当用户双击滚动列表中的项目时,AWT会向item事件后面的列表发送一个<code> ActionEvent </code>实例。当用户在选择列表中的项目时按下返回键时,AWT也生成动作事件。
 * <p>
 *  如果应用程序想要基于由用户选择或激活的该列表中的项目来执行一些动作,则它应当适当地实现<code> ItemListener </code>或<code> ActionListener </code>
 * 并注册新的监听器从此列表接收事件。
 * <p>
 *  对于多选择滚动列表,它被认为是使用外部手势(诸如点击按钮)来触发动作的更好的用户界面。
 * 
 * 
 * @author      Sami Shaio
 * @see         java.awt.event.ItemEvent
 * @see         java.awt.event.ItemListener
 * @see         java.awt.event.ActionEvent
 * @see         java.awt.event.ActionListener
 * @since       JDK1.0
 */
public class List extends Component implements ItemSelectable, Accessible {
    /**
     * A vector created to contain items which will become
     * part of the List Component.
     *
     * <p>
     *  创建的向量包含将成为列表组件一部分的项目。
     * 
     * 
     * @serial
     * @see #addItem(String)
     * @see #getItem(int)
     */
    Vector<String>      items = new Vector<>();

    /**
     * This field will represent the number of visible rows in the
     * <code>List</code> Component.  It is specified only once, and
     * that is when the list component is actually
     * created.  It will never change.
     *
     * <p>
     *  此字段将表示<code> List </code>组件中可见行的数量。它只指定一次,即实际创建列表组件时。它永远不会改变。
     * 
     * 
     * @serial
     * @see #getRows()
     */
    int         rows = 0;

    /**
     * <code>multipleMode</code> is a variable that will
     * be set to <code>true</code> if a list component is to be set to
     * multiple selection mode, that is where the user can
     * select more than one item in a list at one time.
     * <code>multipleMode</code> will be set to false if the
     * list component is set to single selection, that is where
     * the user can only select one item on the list at any
     * one time.
     *
     * <p>
     * <code> multipleMode </code>是一个变量,如果要将列表组件设置为多选模式,则该变量将设置为<code> true </code>,即用户可以选择多个项目列表一次。
     * 如果列表组件设置为单选,那么<code> multipleMode </code>将设置为false,即用户在任何时间只能选择列表上的一个项目。
     * 
     * 
     * @serial
     * @see #isMultipleMode()
     * @see #setMultipleMode(boolean)
     */
    boolean     multipleMode = false;

    /**
     * <code>selected</code> is an array that will contain
     * the indices of items that have been selected.
     *
     * <p>
     *  <code> selected </code>是一个数组,将包含已选择的项目的索引。
     * 
     * 
     * @serial
     * @see #getSelectedIndexes()
     * @see #getSelectedIndex()
     */
    int         selected[] = new int[0];

    /**
     * This variable contains the value that will be used
     * when trying to make a particular list item visible.
     *
     * <p>
     *  此变量包含在尝试使特定列表项可见时使用的值。
     * 
     * 
     * @serial
     * @see #makeVisible(int)
     */
    int         visibleIndex = -1;

    transient ActionListener actionListener;
    transient ItemListener itemListener;

    private static final String base = "list";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -3304312411574666869L;

    /**
     * Creates a new scrolling list.
     * By default, there are four visible lines and multiple selections are
     * not allowed.  Note that this is a convenience method for
     * <code>List(0, false)</code>.  Also note that the number of visible
     * lines in the list cannot be changed after it has been created.
     * <p>
     *  创建新的滚动列表。默认情况下,有四个可见行,并且不允许多个选择。注意,这是<code> List(0,false)</code>的一个方便的方法。另请注意,列表中的可见行数在创建后无法更改。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public List() throws HeadlessException {
        this(0, false);
    }

    /**
     * Creates a new scrolling list initialized with the specified
     * number of visible lines. By default, multiple selections are
     * not allowed.  Note that this is a convenience method for
     * <code>List(rows, false)</code>.  Also note that the number
     * of visible rows in the list cannot be changed after it has
     * been created.
     * <p>
     *  创建使用指定数量的可见行初始化的新滚动列表。默认情况下,不允许多个选择。注意,这是一个方便的方法<code> List(rows,false)</code>。
     * 另请注意,列表中的可见行数在创建后无法更改。
     * 
     * 
     * @param       rows the number of items to show.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since       JDK1.1
     */
    public List(int rows) throws HeadlessException {
        this(rows, false);
    }

    /**
     * The default number of visible rows is 4.  A list with
     * zero rows is unusable and unsightly.
     * <p>
     *  可见行的默认数量为4.具有零行的列表不可用且不美观。
     * 
     */
    final static int    DEFAULT_VISIBLE_ROWS = 4;

    /**
     * Creates a new scrolling list initialized to display the specified
     * number of rows. Note that if zero rows are specified, then
     * the list will be created with a default of four rows.
     * Also note that the number of visible rows in the list cannot
     * be changed after it has been created.
     * If the value of <code>multipleMode</code> is
     * <code>true</code>, then the user can select multiple items from
     * the list. If it is <code>false</code>, only one item at a time
     * can be selected.
     * <p>
     * 创建一个新的滚动列表,初始化为显示指定的行数。请注意,如果指定了零行,那么将使用默认值四行创建列表。另请注意,列表中的可见行数在创建后无法更改。
     * 如果<code> multipleMode </code>的值为<code> true </code>,则用户可以从列表中选择多个项目。
     * 如果是<code> false </code>,则一次只能选择一个项目。
     * 
     * 
     * @param       rows   the number of items to show.
     * @param       multipleMode   if <code>true</code>,
     *                     then multiple selections are allowed;
     *                     otherwise, only one item can be selected at a time.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public List(int rows, boolean multipleMode) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        this.rows = (rows != 0) ? rows : DEFAULT_VISIBLE_ROWS;
        this.multipleMode = multipleMode;
    }

    /**
     * Construct a name for this component.  Called by
     * <code>getName</code> when the name is <code>null</code>.
     * <p>
     *  构造此组件的名称。当名称为<code> null </code>时,由<code> getName </code>调用。
     * 
     */
    String constructComponentName() {
        synchronized (List.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the peer for the list.  The peer allows us to modify the
     * list's appearance without changing its functionality.
     * <p>
     *  创建列表的对等体。对等体允许我们修改列表的外观而不改变其功能。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createList(this);
            super.addNotify();
        }
    }

    /**
     * Removes the peer for this list.  The peer allows us to modify the
     * list's appearance without changing its functionality.
     * <p>
     *  删除此列表的对等项。对等体允许我们修改列表的外观而不改变其功能。
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            ListPeer peer = (ListPeer)this.peer;
            if (peer != null) {
                selected = peer.getSelectedIndexes();
            }
            super.removeNotify();
        }
    }

    /**
     * Gets the number of items in the list.
     * <p>
     *  获取列表中的项目数。
     * 
     * 
     * @return     the number of items in the list
     * @see        #getItem
     * @since      JDK1.1
     */
    public int getItemCount() {
        return countItems();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getItemCount()</code>.
     */
    @Deprecated
    public int countItems() {
        return items.size();
    }

    /**
     * Gets the item associated with the specified index.
     * <p>
     *  获取与指定索引关联的项目。
     * 
     * 
     * @return       an item that is associated with
     *                    the specified index
     * @param        index the position of the item
     * @see          #getItemCount
     */
    public String getItem(int index) {
        return getItemImpl(index);
    }

    // NOTE: This method may be called by privileged threads.
    //       We implement this functionality in a package-private method
    //       to insure that it cannot be overridden by client subclasses.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    final String getItemImpl(int index) {
        return items.elementAt(index);
    }

    /**
     * Gets the items in the list.
     * <p>
     *  获取列表中的项目。
     * 
     * 
     * @return       a string array containing items of the list
     * @see          #select
     * @see          #deselect
     * @see          #isIndexSelected
     * @since        JDK1.1
     */
    public synchronized String[] getItems() {
        String itemCopies[] = new String[items.size()];
        items.copyInto(itemCopies);
        return itemCopies;
    }

    /**
     * Adds the specified item to the end of scrolling list.
     * <p>
     *  将指定的项目添加到滚动列表的末尾。
     * 
     * 
     * @param item the item to be added
     * @since JDK1.1
     */
    public void add(String item) {
        addItem(item);
    }

    /**
    /* <p>
    /* 
     * @deprecated      replaced by <code>add(String)</code>.
     */
    @Deprecated
    public void addItem(String item) {
        addItem(item, -1);
    }

    /**
     * Adds the specified item to the the scrolling list
     * at the position indicated by the index.  The index is
     * zero-based.  If the value of the index is less than zero,
     * or if the value of the index is greater than or equal to
     * the number of items in the list, then the item is added
     * to the end of the list.
     * <p>
     *  将指定的项目添加到由索引指示的位置的滚动列表。索引是从零开始的。如果索引的值小于零,或者索引的值大于或等于列表中的项目数,则将该项目添加到列表的末尾。
     * 
     * 
     * @param       item   the item to be added;
     *              if this parameter is <code>null</code> then the item is
     *              treated as an empty string, <code>""</code>
     * @param       index  the position at which to add the item
     * @since       JDK1.1
     */
    public void add(String item, int index) {
        addItem(item, index);
    }

    /**
    /* <p>
    /* 
     * @deprecated      replaced by <code>add(String, int)</code>.
     */
    @Deprecated
    public synchronized void addItem(String item, int index) {
        if (index < -1 || index >= items.size()) {
            index = -1;
        }

        if (item == null) {
            item = "";
        }

        if (index == -1) {
            items.addElement(item);
        } else {
            items.insertElementAt(item, index);
        }

        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            peer.add(item, index);
        }
    }

    /**
     * Replaces the item at the specified index in the scrolling list
     * with the new string.
     * <p>
     *  使用新字符串替换滚动列表中指定索引处的项目。
     * 
     * 
     * @param       newValue   a new string to replace an existing item
     * @param       index      the position of the item to replace
     * @exception ArrayIndexOutOfBoundsException if <code>index</code>
     *          is out of range
     */
    public synchronized void replaceItem(String newValue, int index) {
        remove(index);
        add(newValue, index);
    }

    /**
     * Removes all items from this list.
     * <p>
     *  从此列表中删除所有项目。
     * 
     * 
     * @see #remove
     * @see #delItems
     * @since JDK1.1
     */
    public void removeAll() {
        clear();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>removeAll()</code>.
     */
    @Deprecated
    public synchronized void clear() {
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            peer.removeAll();
        }
        items = new Vector<>();
        selected = new int[0];
    }

    /**
     * Removes the first occurrence of an item from the list.
     * If the specified item is selected, and is the only selected
     * item in the list, the list is set to have no selection.
     * <p>
     * 从列表中删除项目的第一次出现。如果选择了指定的项目,并且是列表中唯一选定的项目,则列表将设置为无选择。
     * 
     * 
     * @param        item  the item to remove from the list
     * @exception    IllegalArgumentException
     *                     if the item doesn't exist in the list
     * @since        JDK1.1
     */
    public synchronized void remove(String item) {
        int index = items.indexOf(item);
        if (index < 0) {
            throw new IllegalArgumentException("item " + item +
                                               " not found in list");
        } else {
            remove(index);
        }
    }

    /**
     * Removes the item at the specified position
     * from this scrolling list.
     * If the item with the specified position is selected, and is the
     * only selected item in the list, the list is set to have no selection.
     * <p>
     *  从此滚动列表中删除指定位置的项目。如果选择了具有指定位置的项目,并且是列表中唯一选定的项目,则列表将设置为无选择。
     * 
     * 
     * @param      position   the index of the item to delete
     * @see        #add(String, int)
     * @since      JDK1.1
     * @exception    ArrayIndexOutOfBoundsException
     *               if the <code>position</code> is less than 0 or
     *               greater than <code>getItemCount()-1</code>
     */
    public void remove(int position) {
        delItem(position);
    }

    /**
    /* <p>
    /* 
     * @deprecated     replaced by <code>remove(String)</code>
     *                         and <code>remove(int)</code>.
     */
    @Deprecated
    public void delItem(int position) {
        delItems(position, position);
    }

    /**
     * Gets the index of the selected item on the list,
     *
     * <p>
     *  获取列表中所选项目的索引,
     * 
     * 
     * @return        the index of the selected item;
     *                if no item is selected, or if multiple items are
     *                selected, <code>-1</code> is returned.
     * @see           #select
     * @see           #deselect
     * @see           #isIndexSelected
     */
    public synchronized int getSelectedIndex() {
        int sel[] = getSelectedIndexes();
        return (sel.length == 1) ? sel[0] : -1;
    }

    /**
     * Gets the selected indexes on the list.
     *
     * <p>
     *  获取列表上的所选索引。
     * 
     * 
     * @return        an array of the selected indexes on this scrolling list;
     *                if no item is selected, a zero-length array is returned.
     * @see           #select
     * @see           #deselect
     * @see           #isIndexSelected
     */
    public synchronized int[] getSelectedIndexes() {
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            selected = peer.getSelectedIndexes();
        }
        return selected.clone();
    }

    /**
     * Gets the selected item on this scrolling list.
     *
     * <p>
     *  获取此滚动列表上的所选项目。
     * 
     * 
     * @return        the selected item on the list;
     *                if no item is selected, or if multiple items are
     *                selected, <code>null</code> is returned.
     * @see           #select
     * @see           #deselect
     * @see           #isIndexSelected
     */
    public synchronized String getSelectedItem() {
        int index = getSelectedIndex();
        return (index < 0) ? null : getItem(index);
    }

    /**
     * Gets the selected items on this scrolling list.
     *
     * <p>
     *  获取此滚动列表上的所选项目。
     * 
     * 
     * @return        an array of the selected items on this scrolling list;
     *                if no item is selected, a zero-length array is returned.
     * @see           #select
     * @see           #deselect
     * @see           #isIndexSelected
     */
    public synchronized String[] getSelectedItems() {
        int sel[] = getSelectedIndexes();
        String str[] = new String[sel.length];
        for (int i = 0 ; i < sel.length ; i++) {
            str[i] = getItem(sel[i]);
        }
        return str;
    }

    /**
     * Gets the selected items on this scrolling list in an array of Objects.
     * <p>
     *  在对象数组中获取此滚动列表上的所选项目。
     * 
     * 
     * @return        an array of <code>Object</code>s representing the
     *                selected items on this scrolling list;
     *                if no item is selected, a zero-length array is returned.
     * @see #getSelectedItems
     * @see ItemSelectable
     */
    public Object[] getSelectedObjects() {
        return getSelectedItems();
    }

    /**
     * Selects the item at the specified index in the scrolling list.
     *<p>
     * Note that passing out of range parameters is invalid,
     * and will result in unspecified behavior.
     *
     * <p>Note that this method should be primarily used to
     * initially select an item in this component.
     * Programmatically calling this method will <i>not</i> trigger
     * an <code>ItemEvent</code>.  The only way to trigger an
     * <code>ItemEvent</code> is by user interaction.
     *
     * <p>
     *  选择滚动列表中指定索引处的项目。
     * p>
     *  注意,超出范围参数是无效的,并且将导致未指定的行为。
     * 
     *  <p>请注意,此方法应主要用于初始选择此组件中的项目。以编程方式调用此方法将</i>不会触发<code> ItemEvent </code>。
     * 触发<code> ItemEvent </code>的唯一方法是通过用户交互。
     * 
     * 
     * @param        index the position of the item to select
     * @see          #getSelectedItem
     * @see          #deselect
     * @see          #isIndexSelected
     */
    public void select(int index) {
        // Bug #4059614: select can't be synchronized while calling the peer,
        // because it is called from the Window Thread.  It is sufficient to
        // synchronize the code that manipulates 'selected' except for the
        // case where the peer changes.  To handle this case, we simply
        // repeat the selection process.

        ListPeer peer;
        do {
            peer = (ListPeer)this.peer;
            if (peer != null) {
                peer.select(index);
                return;
            }

            synchronized(this)
            {
                boolean alreadySelected = false;

                for (int i = 0 ; i < selected.length ; i++) {
                    if (selected[i] == index) {
                        alreadySelected = true;
                        break;
                    }
                }

                if (!alreadySelected) {
                    if (!multipleMode) {
                        selected = new int[1];
                        selected[0] = index;
                    } else {
                        int newsel[] = new int[selected.length + 1];
                        System.arraycopy(selected, 0, newsel, 0,
                                         selected.length);
                        newsel[selected.length] = index;
                        selected = newsel;
                    }
                }
            }
        } while (peer != this.peer);
    }

    /**
     * Deselects the item at the specified index.
     * <p>
     * Note that passing out of range parameters is invalid,
     * and will result in unspecified behavior.
     * <p>
     * If the item at the specified index is not selected,
     * then the operation is ignored.
     * <p>
     *  取消选择指定索引处的项目。
     * <p>
     *  注意,超出范围参数是无效的,并且将导致未指定的行为。
     * <p>
     *  如果未选择指定索引处的项目,则忽略该操作。
     * 
     * 
     * @param        index the position of the item to deselect
     * @see          #select
     * @see          #getSelectedItem
     * @see          #isIndexSelected
     */
    public synchronized void deselect(int index) {
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            if (isMultipleMode() || (getSelectedIndex() == index)) {
                peer.deselect(index);
            }
        }

        for (int i = 0 ; i < selected.length ; i++) {
            if (selected[i] == index) {
                int newsel[] = new int[selected.length - 1];
                System.arraycopy(selected, 0, newsel, 0, i);
                System.arraycopy(selected, i+1, newsel, i, selected.length - (i+1));
                selected = newsel;
                return;
            }
        }
    }

    /**
     * Determines if the specified item in this scrolling list is
     * selected.
     * <p>
     *  确定是否选择此滚动列表中的指定项目。
     * 
     * 
     * @param      index   the item to be checked
     * @return     <code>true</code> if the specified item has been
     *                       selected; <code>false</code> otherwise
     * @see        #select
     * @see        #deselect
     * @since      JDK1.1
     */
    public boolean isIndexSelected(int index) {
        return isSelected(index);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>isIndexSelected(int)</code>.
     */
    @Deprecated
    public boolean isSelected(int index) {
        int sel[] = getSelectedIndexes();
        for (int i = 0 ; i < sel.length ; i++) {
            if (sel[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the number of visible lines in this list.  Note that
     * once the <code>List</code> has been created, this number
     * will never change.
     * <p>
     *  获取此列表中可见行的数量。注意,一旦创建了<code> List </code>,这个数字将永远不会改变。
     * 
     * 
     * @return     the number of visible lines in this scrolling list
     */
    public int getRows() {
        return rows;
    }

    /**
     * Determines whether this list allows multiple selections.
     * <p>
     * 确定此列表是否允许多个选择。
     * 
     * 
     * @return     <code>true</code> if this list allows multiple
     *                 selections; otherwise, <code>false</code>
     * @see        #setMultipleMode
     * @since      JDK1.1
     */
    public boolean isMultipleMode() {
        return allowsMultipleSelections();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>isMultipleMode()</code>.
     */
    @Deprecated
    public boolean allowsMultipleSelections() {
        return multipleMode;
    }

    /**
     * Sets the flag that determines whether this list
     * allows multiple selections.
     * When the selection mode is changed from multiple-selection to
     * single-selection, the selected items change as follows:
     * If a selected item has the location cursor, only that
     * item will remain selected.  If no selected item has the
     * location cursor, all items will be deselected.
     * <p>
     *  设置确定此列表是否允许多个选择的标志。当选择模式从多重选择更改为单选时,所选项目更改如下：如果所选项目具有位置光标,则只有该项目将保持选中状态。如果所选项目没有位置光标,则所有项目将被取消选择。
     * 
     * 
     * @param       b   if <code>true</code> then multiple selections
     *                      are allowed; otherwise, only one item from
     *                      the list can be selected at once
     * @see         #isMultipleMode
     * @since       JDK1.1
     */
    public void setMultipleMode(boolean b) {
        setMultipleSelections(b);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setMultipleMode(boolean)</code>.
     */
    @Deprecated
    public synchronized void setMultipleSelections(boolean b) {
        if (b != multipleMode) {
            multipleMode = b;
            ListPeer peer = (ListPeer)this.peer;
            if (peer != null) {
                peer.setMultipleMode(b);
            }
        }
    }

    /**
     * Gets the index of the item that was last made visible by
     * the method <code>makeVisible</code>.
     * <p>
     *  获取最后由方法<code> makeVisible </code>可见的项目的索引。
     * 
     * 
     * @return      the index of the item that was last made visible
     * @see         #makeVisible
     */
    public int getVisibleIndex() {
        return visibleIndex;
    }

    /**
     * Makes the item at the specified index visible.
     * <p>
     *  使指定索引处的项目可见。
     * 
     * 
     * @param       index    the position of the item
     * @see         #getVisibleIndex
     */
    public synchronized void makeVisible(int index) {
        visibleIndex = index;
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            peer.makeVisible(index);
        }
    }

    /**
     * Gets the preferred dimensions for a list with the specified
     * number of rows.
     * <p>
     *  获取具有指定行数的列表的首选维度。
     * 
     * 
     * @param      rows    number of rows in the list
     * @return     the preferred dimensions for displaying this scrolling list
     *             given that the specified number of rows must be visible
     * @see        java.awt.Component#getPreferredSize
     * @since      JDK1.1
     */
    public Dimension getPreferredSize(int rows) {
        return preferredSize(rows);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getPreferredSize(int)</code>.
     */
    @Deprecated
    public Dimension preferredSize(int rows) {
        synchronized (getTreeLock()) {
            ListPeer peer = (ListPeer)this.peer;
            return (peer != null) ?
                       peer.getPreferredSize(rows) :
                       super.preferredSize();
        }
    }

    /**
     * Gets the preferred size of this scrolling list.
     * <p>
     *  获取此滚动列表的首选大小。
     * 
     * 
     * @return     the preferred dimensions for displaying this scrolling list
     * @see        java.awt.Component#getPreferredSize
     * @since      JDK1.1
     */
    public Dimension getPreferredSize() {
        return preferredSize();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getPreferredSize()</code>.
     */
    @Deprecated
    public Dimension preferredSize() {
        synchronized (getTreeLock()) {
            return (rows > 0) ?
                       preferredSize(rows) :
                       super.preferredSize();
        }
    }

    /**
     * Gets the minimum dimensions for a list with the specified
     * number of rows.
     * <p>
     *  获取具有指定行数的列表的最小维度。
     * 
     * 
     * @param      rows    number of rows in the list
     * @return     the minimum dimensions for displaying this scrolling list
     *             given that the specified number of rows must be visible
     * @see        java.awt.Component#getMinimumSize
     * @since      JDK1.1
     */
    public Dimension getMinimumSize(int rows) {
        return minimumSize(rows);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getMinimumSize(int)</code>.
     */
    @Deprecated
    public Dimension minimumSize(int rows) {
        synchronized (getTreeLock()) {
            ListPeer peer = (ListPeer)this.peer;
            return (peer != null) ?
                       peer.getMinimumSize(rows) :
                       super.minimumSize();
        }
    }

    /**
     * Determines the minimum size of this scrolling list.
     * <p>
     *  确定此滚动列表的最小大小。
     * 
     * 
     * @return       the minimum dimensions needed
     *                        to display this scrolling list
     * @see          java.awt.Component#getMinimumSize()
     * @since        JDK1.1
     */
    public Dimension getMinimumSize() {
        return minimumSize();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getMinimumSize()</code>.
     */
    @Deprecated
    public Dimension minimumSize() {
        synchronized (getTreeLock()) {
            return (rows > 0) ? minimumSize(rows) : super.minimumSize();
        }
    }

    /**
     * Adds the specified item listener to receive item events from
     * this list.  Item events are sent in response to user input, but not
     * in response to calls to <code>select</code> or <code>deselect</code>.
     * If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的项目侦听器以从此列表接收项目事件。项目事件是响应用户输入而发送的,但不响应对<code> select </code>或<code>取消选择</code>的调用。
     * 如果侦听器<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l the item listener
     * @see           #removeItemListener
     * @see           #getItemListeners
     * @see           #select
     * @see           #deselect
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since         JDK1.1
     */
    public synchronized void addItemListener(ItemListener l) {
        if (l == null) {
            return;
        }
        itemListener = AWTEventMulticaster.add(itemListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified item listener so that it no longer
     * receives item events from this list.
     * If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 删除指定的项目侦听器,以使其不再从此列表接收项目事件。如果侦听器<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param           l the item listener
     * @see             #addItemListener
     * @see             #getItemListeners
     * @see             java.awt.event.ItemEvent
     * @see             java.awt.event.ItemListener
     * @since           JDK1.1
     */
    public synchronized void removeItemListener(ItemListener l) {
        if (l == null) {
            return;
        }
        itemListener = AWTEventMulticaster.remove(itemListener, l);
    }

    /**
     * Returns an array of all the item listeners
     * registered on this list.
     *
     * <p>
     *  返回在此列表上注册的所有项目侦听器的数组。
     * 
     * 
     * @return all of this list's <code>ItemListener</code>s
     *         or an empty array if no item
     *         listeners are currently registered
     *
     * @see             #addItemListener
     * @see             #removeItemListener
     * @see             java.awt.event.ItemEvent
     * @see             java.awt.event.ItemListener
     * @since 1.4
     */
    public synchronized ItemListener[] getItemListeners() {
        return getListeners(ItemListener.class);
    }

    /**
     * Adds the specified action listener to receive action events from
     * this list. Action events occur when a user double-clicks
     * on a list item or types Enter when the list has the keyboard
     * focus.
     * <p>
     * If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的操作侦听器以从此列表接收操作事件。当用户双击列表项时发生操作事件,或者当列表具有键盘焦点时键入Enter。
     * <p>
     *  如果侦听器<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l the action listener
     * @see           #removeActionListener
     * @see           #getActionListeners
     * @see           java.awt.event.ActionEvent
     * @see           java.awt.event.ActionListener
     * @since         JDK1.1
     */
    public synchronized void addActionListener(ActionListener l) {
        if (l == null) {
            return;
        }
        actionListener = AWTEventMulticaster.add(actionListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified action listener so that it no longer
     * receives action events from this list. Action events
     * occur when a user double-clicks on a list item.
     * If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的操作侦听器,以使其不再从此列表接收操作事件。当用户双击列表项时发生操作事件。
     * 如果侦听器<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param           l     the action listener
     * @see             #addActionListener
     * @see             #getActionListeners
     * @see             java.awt.event.ActionEvent
     * @see             java.awt.event.ActionListener
     * @since           JDK1.1
     */
    public synchronized void removeActionListener(ActionListener l) {
        if (l == null) {
            return;
        }
        actionListener = AWTEventMulticaster.remove(actionListener, l);
    }

    /**
     * Returns an array of all the action listeners
     * registered on this list.
     *
     * <p>
     *  返回在此列表上注册的所有操作侦听器的数组。
     * 
     * 
     * @return all of this list's <code>ActionListener</code>s
     *         or an empty array if no action
     *         listeners are currently registered
     *
     * @see             #addActionListener
     * @see             #removeActionListener
     * @see             java.awt.event.ActionEvent
     * @see             java.awt.event.ActionListener
     * @since 1.4
     */
    public synchronized ActionListener[] getActionListeners() {
        return getListeners(ActionListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this <code>List</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>List</code> <code>l</code>
     * for its item listeners with the following code:
     *
     * <pre>ItemListener[] ils = (ItemListener[])(l.getListeners(ItemListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     * 返回当前在此<code> List </code>上注册为<code> <em> Foo </em>侦听器</code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * 
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询其项目侦听器的<code> List </code> <code> l </code>：。
     * 
     *  <pre> ItemListener [] ils =(ItemListener [])(l.getListeners(ItemListener.class)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组。
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this list,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getItemListeners
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if  (listenerType == ActionListener.class) {
            l = actionListener;
        } else if  (listenerType == ItemListener.class) {
            l = itemListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        switch(e.id) {
          case ActionEvent.ACTION_PERFORMED:
            if ((eventMask & AWTEvent.ACTION_EVENT_MASK) != 0 ||
                actionListener != null) {
                return true;
            }
            return false;
          case ItemEvent.ITEM_STATE_CHANGED:
            if ((eventMask & AWTEvent.ITEM_EVENT_MASK) != 0 ||
                itemListener != null) {
                return true;
            }
            return false;
          default:
            break;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this scrolling list. If an event is
     * an instance of <code>ItemEvent</code>, it invokes the
     * <code>processItemEvent</code> method. Else, if the
     * event is an instance of <code>ActionEvent</code>,
     * it invokes <code>processActionEvent</code>.
     * If the event is not an item event or an action event,
     * it invokes <code>processEvent</code> on the superclass.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  在此滚动列表上处理事件。如果事件是<code> ItemEvent </code>的实例,它会调用<code> processItemEvent </code>方法。
     * 否则,如果事件是<code> ActionEvent </code>的一个实例,它会调用<code> processActionEvent </code>。
     * 如果事件不是项目事件或动作事件,它会调用超类上的<code> processEvent </code>。
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param        e the event
     * @see          java.awt.event.ActionEvent
     * @see          java.awt.event.ItemEvent
     * @see          #processActionEvent
     * @see          #processItemEvent
     * @since        JDK1.1
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof ItemEvent) {
            processItemEvent((ItemEvent)e);
            return;
        } else if (e instanceof ActionEvent) {
            processActionEvent((ActionEvent)e);
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes item events occurring on this list by
     * dispatching them to any registered
     * <code>ItemListener</code> objects.
     * <p>
     * This method is not called unless item events are
     * enabled for this component. Item events are enabled
     * when one of the following occurs:
     * <ul>
     * <li>An <code>ItemListener</code> object is registered
     * via <code>addItemListener</code>.
     * <li>Item events are enabled via <code>enableEvents</code>.
     * </ul>
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将项目事件分派到任何已注册的<code> ItemListener </code>对象,来处理在此列表上发生的项目事件。
     * <p>
     *  除非为此组件启用项目事件,否则不会调用此方法。当发生以下情况之一时,将启用项目事件：
     * <ul>
     * <li> <code> ItemListener </code>对象通过<code> addItemListener </code>注册。
     *  <li>项目事件通过<code> enableEvents </code>启用。
     * </ul>
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param       e the item event
     * @see         java.awt.event.ItemEvent
     * @see         java.awt.event.ItemListener
     * @see         #addItemListener
     * @see         java.awt.Component#enableEvents
     * @since       JDK1.1
     */
    protected void processItemEvent(ItemEvent e) {
        ItemListener listener = itemListener;
        if (listener != null) {
            listener.itemStateChanged(e);
        }
    }

    /**
     * Processes action events occurring on this component
     * by dispatching them to any registered
     * <code>ActionListener</code> objects.
     * <p>
     * This method is not called unless action events are
     * enabled for this component. Action events are enabled
     * when one of the following occurs:
     * <ul>
     * <li>An <code>ActionListener</code> object is registered
     * via <code>addActionListener</code>.
     * <li>Action events are enabled via <code>enableEvents</code>.
     * </ul>
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将它们分发到任何注册的<code> ActionListener </code>对象来处理在此组件上发生的操作事件。
     * <p>
     *  除非为此组件启用了操作事件,否则不会调用此方法。当发生以下情况之一时,将启用操作事件：
     * <ul>
     *  <li> <code> ActionListener </code>对象通过<code> addActionListener </code>注册。
     *  <li>操作事件通过<code> enableEvents </code>启用。
     * </ul>
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param       e the action event
     * @see         java.awt.event.ActionEvent
     * @see         java.awt.event.ActionListener
     * @see         #addActionListener
     * @see         java.awt.Component#enableEvents
     * @since       JDK1.1
     */
    protected void processActionEvent(ActionEvent e) {
        ActionListener listener = actionListener;
        if (listener != null) {
            listener.actionPerformed(e);
        }
    }

    /**
     * Returns the parameter string representing the state of this
     * scrolling list. This string is useful for debugging.
     * <p>
     *  返回表示此滚动列表的状态的参数字符串。这个字符串对于调试很有用。
     * 
     * 
     * @return    the parameter string of this scrolling list
     */
    protected String paramString() {
        return super.paramString() + ",selected=" + getSelectedItem();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * Not for public use in the future.
     * This method is expected to be retained only as a package
     * private method.
     */
    @Deprecated
    public synchronized void delItems(int start, int end) {
        for (int i = end; i >= start; i--) {
            items.removeElementAt(i);
        }
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
            peer.delItems(start, end);
        }
    }

    /*
     * Serialization support.  Since the value of the selected
     * field isn't necessarily up to date, we sync it up with the
     * peer before serializing.
     * <p>
     *  序列化支持。由于所选字段的值不一定是最新的,因此我们在序列化之前将其与对等体同步。
     * 
     */

    /**
     * The <code>List</code> component's
     * Serialized Data Version.
     *
     * <p>
     *  <code> List </code>组件的序列化数据版本。
     * 
     * 
     * @serial
     */
    private int listSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable <code>ItemListeners</code>
     * and <code>ActionListeners</code> as optional data.
     * The non-serializable listeners are detected and
     * no attempt is made to serialize them.
     *
     * <p>
     *  将缺省可序列化字段写入流。将可序列化的<code> ItemListeners </code>和<code> ActionListeners </code>列表写为可选数据。
     * 检测到不可序列化的侦听器,并且不尝试将它们串行化。
     * 
     * 
     * @serialData <code>null</code> terminated sequence of 0
     *  or more pairs; the pair consists of a <code>String</code>
     *  and an <code>Object</code>; the <code>String</code>
     *  indicates the type of object and is one of the
     *  following:
     *  <code>itemListenerK</code> indicating an
     *    <code>ItemListener</code> object;
     *  <code>actionListenerK</code> indicating an
     *    <code>ActionListener</code> object
     *
     * @param s the <code>ObjectOutputStream</code> to write
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#itemListenerK
     * @see java.awt.Component#actionListenerK
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
      synchronized (this) {
        ListPeer peer = (ListPeer)this.peer;
        if (peer != null) {
          selected = peer.getSelectedIndexes();
        }
      }
      s.defaultWriteObject();

      AWTEventMulticaster.save(s, itemListenerK, itemListener);
      AWTEventMulticaster.save(s, actionListenerK, actionListener);
      s.writeObject(null);
    }

    /**
     * Reads the <code>ObjectInputStream</code> and if it
     * isn't <code>null</code> adds a listener to receive
     * both item events and action events (as specified
     * by the key stored in the stream) fired by the
     * <code>List</code>.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     * 读取<code> ObjectInputStream </code>,如果它不是<code> null </code>添加一个监听器接收项目事件和动作事件(由存储在流中的密钥指定)代码>列表</code>
     * 。
     * 无法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to write
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @see #removeItemListener(ItemListener)
     * @see #addItemListener(ItemListener)
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject(ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
      GraphicsEnvironment.checkHeadless();
      s.defaultReadObject();

      Object keyOrNull;
      while(null != (keyOrNull = s.readObject())) {
        String key = ((String)keyOrNull).intern();

        if (itemListenerK == key)
          addItemListener((ItemListener)(s.readObject()));

        else if (actionListenerK == key)
          addActionListener((ActionListener)(s.readObject()));

        else // skip value for unrecognized key
          s.readObject();
      }
    }


/////////////////
// Accessibility support
////////////////


    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>List</code>. For lists, the <code>AccessibleContext</code>
     * takes the form of an <code>AccessibleAWTList</code>.
     * A new <code>AccessibleAWTList</code> instance is created, if necessary.
     *
     * <p>
     *  获取与此<code> List </code>关联的<code> AccessibleContext </code>。
     * 对于列表,<code> AccessibleContext </code>采用<code> AccessibleAWTList </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleAWTList </code>实例。
     * 
     * 
     * @return an <code>AccessibleAWTList</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>List</code>
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTList();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>List</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to list user-interface elements.
     * <p>
     *  此类实现<code> List </code>类的辅助功能支持。它提供了适用于列出用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTList extends AccessibleAWTComponent
        implements AccessibleSelection, ItemListener, ActionListener
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 7924617370136012829L;

        public AccessibleAWTList() {
            super();
            List.this.addActionListener(this);
            List.this.addItemListener(this);
        }

        public void actionPerformed(ActionEvent event)  {
        }

        public void itemStateChanged(ItemEvent event)  {
        }

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
            if (List.this.isMultipleMode())  {
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
         * Returns the Accessible child contained at the local coordinate
         * Point, if one exists.
         *
         * <p>
         *  返回包含在本地坐标Point(如果存在)的Accessible子项。
         * 
         * 
         * @return the Accessible at the specified location, if it exists
         */
        public Accessible getAccessibleAt(Point p) {
            return null; // fredxFIXME Not implemented yet
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
            return List.this.getItemCount();
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
            synchronized(List.this)  {
                if (i >= List.this.getItemCount()) {
                    return null;
                } else {
                    return new AccessibleAWTListChild(List.this, i);
                }
            }
        }

        /**
         * Get the AccessibleSelection associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleSelection interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleSelection。在为此类实现Java Accessibility API时,返回此对象,它负责代表自身实现AccessibleSelection接口。
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
             return List.this.getSelectedIndexes().length;
         }

        /**
         * Returns an Accessible representing the specified selected item
         * in the object.  If there isn't a selection, or there are
         * fewer items selected than the integer passed in, the return
         * value will be null.
         *
         * <p>
         * 返回表示对象中指定的选定项目的Accessible。如果没有选择,或者选择的项目少于传递的整数,则返回值将为null。
         * 
         * 
         * @param i the zero-based index of selected items
         * @return an Accessible containing the selected item
         */
         public Accessible getAccessibleSelection(int i) {
             synchronized(List.this)  {
                 int len = getAccessibleSelectionCount();
                 if (i < 0 || i >= len) {
                     return null;
                 } else {
                     return getAccessibleChild(List.this.getSelectedIndexes()[i]);
                 }
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
            return List.this.isIndexSelected(i);
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
             List.this.select(i);
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
             List.this.deselect(i);
         }

        /**
         * Clears the selection in the object, so that nothing in the
         * object is selected.
         * <p>
         *  清除对象中的选择,以便不选择对象中的任何内容。
         * 
         */
         public void clearAccessibleSelection() {
             synchronized(List.this)  {
                 int selectedIndexes[] = List.this.getSelectedIndexes();
                 if (selectedIndexes == null)
                     return;
                 for (int i = selectedIndexes.length - 1; i >= 0; i--) {
                     List.this.deselect(selectedIndexes[i]);
                 }
             }
         }

        /**
         * Causes every selected item in the object to be selected
         * if the object supports multiple selections.
         * <p>
         *  如果对象支持多个选择,则导致选择对象中的每个选定项目。
         * 
         */
         public void selectAllAccessibleSelection() {
             synchronized(List.this)  {
                 for (int i = List.this.getItemCount() - 1; i >= 0; i--) {
                     List.this.select(i);
                 }
             }
         }

       /**
        * This class implements accessibility support for
        * List children.  It provides an implementation of the
        * Java Accessibility API appropriate to list children
        * user-interface elements.
        * <p>
        *  此类实现List子项的辅助功能支持。它提供了适用于列出子用户界面元素的Java辅助功能API的实现。
        * 
        * 
        * @since 1.3
        */
        protected class AccessibleAWTListChild extends AccessibleAWTComponent
            implements Accessible
        {
            /*
             * JDK 1.3 serialVersionUID
             * <p>
             *  JDK 1.3 serialVersionUID
             * 
             */
            private static final long serialVersionUID = 4412022926028300317L;

        // [[[FIXME]]] need to finish implementing this!!!

            private List parent;
            private int  indexInParent;

            public AccessibleAWTListChild(List parent, int indexInParent)  {
                this.parent = parent;
                this.setAccessibleParent(parent);
                this.indexInParent = indexInParent;
            }

            //
            // required Accessible methods
            //
          /**
           * Gets the AccessibleContext for this object.  In the
           * implementation of the Java Accessibility API for this class,
           * return this object, which acts as its own AccessibleContext.
           *
           * <p>
           *  获取此对象的AccessibleContext。在为该类实现Java辅助功能API时,返回此对象,该对象充当其自己的AccessibleContext。
           * 
           * 
           * @return this object
           */
            public AccessibleContext getAccessibleContext() {
                return this;
            }

            //
            // required AccessibleContext methods
            //

            /**
             * Get the role of this object.
             *
             * <p>
             *  获取此对象的作用。
             * 
             * 
             * @return an instance of AccessibleRole describing the role of
             * the object
             * @see AccessibleRole
             */
            public AccessibleRole getAccessibleRole() {
                return AccessibleRole.LIST_ITEM;
            }

            /**
             * Get the state set of this object.  The AccessibleStateSet of an
             * object is composed of a set of unique AccessibleState's.  A
             * change in the AccessibleStateSet of an object will cause a
             * PropertyChangeEvent to be fired for the
             * ACCESSIBLE_STATE_PROPERTY property.
             *
             * <p>
             * 获取此对象的状态集。对象的AccessibleStateSet由一组唯一的AccessibleState组成。
             * 对象的AccessibleStateSet中的更改将导致针对ACCESSIBLE_STATE_PROPERTY属性触发PropertyChangeEvent。
             * 
             * 
             * @return an instance of AccessibleStateSet containing the
             * current state set of the object
             * @see AccessibleStateSet
             * @see AccessibleState
             * @see #addPropertyChangeListener
             */
            public AccessibleStateSet getAccessibleStateSet() {
                AccessibleStateSet states = super.getAccessibleStateSet();
                if (parent.isIndexSelected(indexInParent)) {
                    states.add(AccessibleState.SELECTED);
                }
                return states;
            }

            /**
             * Gets the locale of the component. If the component does not
             * have a locale, then the locale of its parent is returned.
             *
             * <p>
             *  获取组件的语言环境。如果组件没有语言环境,那么将返回其父组件的语言环境。
             * 
             * 
             * @return This component's locale.  If this component does not have
             * a locale, the locale of its parent is returned.
             *
             * @exception IllegalComponentStateException
             * If the Component does not have its own locale and has not yet
             * been added to a containment hierarchy such that the locale can
             * be determined from the containing parent.
             */
            public Locale getLocale() {
                return parent.getLocale();
            }

            /**
             * Get the 0-based index of this object in its accessible parent.
             *
             * <p>
             *  在其可访问的父代中获取此对象的基于0的索引。
             * 
             * 
             * @return the 0-based index of this object in its parent; -1 if
             * this object does not have an accessible parent.
             *
             * @see #getAccessibleParent
             * @see #getAccessibleChildrenCount
             * @see #getAccessibleChild
             */
            public int getAccessibleIndexInParent() {
                return indexInParent;
            }

            /**
             * Returns the number of accessible children of the object.
             *
             * <p>
             *  返回对象的可访问子项数。
             * 
             * 
             * @return the number of accessible children of the object.
             */
            public int getAccessibleChildrenCount() {
                return 0;       // list elements can't have children
            }

            /**
             * Return the specified Accessible child of the object.  The
             * Accessible children of an Accessible object are zero-based,
             * so the first child of an Accessible child is at index 0, the
             * second child is at index 1, and so on.
             *
             * <p>
             *  返回对象的指定Accessible子项。可访问对象的可访问子对象是基于零的,因此可访问子对象的第一个子对象位于索引0,第二个子对象位于索引1,依此类推。
             * 
             * 
             * @param i zero-based index of child
             * @return the Accessible child of the object
             * @see #getAccessibleChildrenCount
             */
            public Accessible getAccessibleChild(int i) {
                return null;    // list elements can't have children
            }


            //
            // AccessibleComponent delegatation to parent List
            //

            /**
             * Get the background color of this object.
             *
             * <p>
             *  获取此对象的背景颜色。
             * 
             * 
             * @return the background color, if supported, of the object;
             * otherwise, null
             * @see #setBackground
             */
            public Color getBackground() {
                return parent.getBackground();
            }

            /**
             * Set the background color of this object.
             *
             * <p>
             *  设置此对象的背景颜色。
             * 
             * 
             * @param c the new Color for the background
             * @see #setBackground
             */
            public void setBackground(Color c) {
                parent.setBackground(c);
            }

            /**
             * Get the foreground color of this object.
             *
             * <p>
             *  获取此对象的前景色。
             * 
             * 
             * @return the foreground color, if supported, of the object;
             * otherwise, null
             * @see #setForeground
             */
            public Color getForeground() {
                return parent.getForeground();
            }

            /**
             * Set the foreground color of this object.
             *
             * <p>
             *  设置此对象的前景颜色。
             * 
             * 
             * @param c the new Color for the foreground
             * @see #getForeground
             */
            public void setForeground(Color c) {
                parent.setForeground(c);
            }

            /**
             * Get the Cursor of this object.
             *
             * <p>
             *  获取此对象的Cursor。
             * 
             * 
             * @return the Cursor, if supported, of the object; otherwise, null
             * @see #setCursor
             */
            public Cursor getCursor() {
                return parent.getCursor();
            }

            /**
             * Set the Cursor of this object.
             * <p>
             * The method may have no visual effect if the Java platform
             * implementation and/or the native system do not support
             * changing the mouse cursor shape.
             * <p>
             *  设置此对象的Cursor。
             * <p>
             *  如果Java平台实现和/或本地系统不支持改变鼠标光标形状,则该方法可以没有视觉效果。
             * 
             * 
             * @param cursor the new Cursor for the object
             * @see #getCursor
             */
            public void setCursor(Cursor cursor) {
                parent.setCursor(cursor);
            }

            /**
             * Get the Font of this object.
             *
             * <p>
             *  获得此对象的字体。
             * 
             * 
             * @return the Font,if supported, for the object; otherwise, null
             * @see #setFont
             */
            public Font getFont() {
                return parent.getFont();
            }

            /**
             * Set the Font of this object.
             *
             * <p>
             *  设置此对象的字体。
             * 
             * 
             * @param f the new Font for the object
             * @see #getFont
             */
            public void setFont(Font f) {
                parent.setFont(f);
            }

            /**
             * Get the FontMetrics of this object.
             *
             * <p>
             *  获取此对象的FontMetrics。
             * 
             * 
             * @param f the Font
             * @return the FontMetrics, if supported, the object; otherwise, null
             * @see #getFont
             */
            public FontMetrics getFontMetrics(Font f) {
                return parent.getFontMetrics(f);
            }

            /**
             * Determine if the object is enabled.  Objects that are enabled
             * will also have the AccessibleState.ENABLED state set in their
             * AccessibleStateSet.
             *
             * <p>
             *  确定对象是否已启用。启用的对象也将在其AccessibleStateSet中设置AccessibleState.ENABLED状态。
             * 
             * 
             * @return true if object is enabled; otherwise, false
             * @see #setEnabled
             * @see AccessibleContext#getAccessibleStateSet
             * @see AccessibleState#ENABLED
             * @see AccessibleStateSet
             */
            public boolean isEnabled() {
                return parent.isEnabled();
            }

            /**
             * Set the enabled state of the object.
             *
             * <p>
             *  设置对象的启用状态。
             * 
             * 
             * @param b if true, enables this object; otherwise, disables it
             * @see #isEnabled
             */
            public void setEnabled(boolean b) {
                parent.setEnabled(b);
            }

            /**
             * Determine if the object is visible.  Note: this means that the
             * object intends to be visible; however, it may not be
             * showing on the screen because one of the objects that this object
             * is contained by is currently not visible.  To determine if an
             * object is showing on the screen, use isShowing().
             * <p>Objects that are visible will also have the
             * AccessibleState.VISIBLE state set in their AccessibleStateSet.
             *
             * <p>
             * 确定对象是否可见。注意：这意味着对象是可见的;但是,它可能不会显示在屏幕上,因为该对象包含的对象之一当前不可见。要确定对象是否显示在屏幕上,请使用isShowing()。
             *  <p>可见的对象也将在其AccessibleStateSet中设置AccessibleState.VISIBLE状态。
             * 
             * 
             * @return true if object is visible; otherwise, false
             * @see #setVisible
             * @see AccessibleContext#getAccessibleStateSet
             * @see AccessibleState#VISIBLE
             * @see AccessibleStateSet
             */
            public boolean isVisible() {
                // [[[FIXME]]] needs to work like isShowing() below
                return false;
                // return parent.isVisible();
            }

            /**
             * Set the visible state of the object.
             *
             * <p>
             *  设置对象的可见状态。
             * 
             * 
             * @param b if true, shows this object; otherwise, hides it
             * @see #isVisible
             */
            public void setVisible(boolean b) {
                // [[[FIXME]]] should scroll to item to make it show!
                parent.setVisible(b);
            }

            /**
             * Determine if the object is showing.  This is determined by
             * checking the visibility of the object and visibility of the
             * object ancestors.
             * Note: this will return true even if the object is obscured
             * by another (for example, it to object is underneath a menu
             * that was pulled down).
             *
             * <p>
             *  确定对象是否正在显示。这通过检查对象的可见性和对象祖先的可见性来确定。注意：即使对象被另一个对象遮盖,这将返回true(例如,对象在下拉的菜单下)。
             * 
             * 
             * @return true if object is showing; otherwise, false
             */
            public boolean isShowing() {
                // [[[FIXME]]] only if it's showing!!!
                return false;
                // return parent.isShowing();
            }

            /**
             * Checks whether the specified point is within this object's
             * bounds, where the point's x and y coordinates are defined to
             * be relative to the coordinate system of the object.
             *
             * <p>
             *  检查指定点是否在此对象的边界内,其中点的x和y坐标被定义为相对于对象的坐标系。
             * 
             * 
             * @param p the Point relative to the coordinate system of the
             * object
             * @return true if object contains Point; otherwise false
             * @see #getBounds
             */
            public boolean contains(Point p) {
                // [[[FIXME]]] - only if p is within the list element!!!
                return false;
                // return parent.contains(p);
            }

            /**
             * Returns the location of the object on the screen.
             *
             * <p>
             *  返回对象在屏幕上的位置。
             * 
             * 
             * @return location of object on screen; null if this object
             * is not on the screen
             * @see #getBounds
             * @see #getLocation
             */
            public Point getLocationOnScreen() {
                // [[[FIXME]]] sigh
                return null;
            }

            /**
             * Gets the location of the object relative to the parent in the
             * form of a point specifying the object's top-left corner in the
             * screen's coordinate space.
             *
             * <p>
             *  以指定对象在屏幕坐标空间中左上角的点的形式获取对象相对于父对象的位置。
             * 
             * 
             * @return An instance of Point representing the top-left corner of
             * the objects's bounds in the coordinate space of the screen; null
             * if this object or its parent are not on the screen
             * @see #getBounds
             * @see #getLocationOnScreen
             */
            public Point getLocation() {
                // [[[FIXME]]]
                return null;
            }

            /**
             * Sets the location of the object relative to the parent.
             * <p>
             *  设置对象相对于父对象的位置。
             * 
             * 
             * @param p the new position for the top-left corner
             * @see #getLocation
             */
            public void setLocation(Point p) {
                // [[[FIXME]]] maybe - can simply return as no-op
            }

            /**
             * Gets the bounds of this object in the form of a Rectangle object.
             * The bounds specify this object's width, height, and location
             * relative to its parent.
             *
             * <p>
             *  以Rectangle对象的形式获取此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
             * 
             * 
             * @return A rectangle indicating this component's bounds; null if
             * this object is not on the screen.
             * @see #contains
             */
            public Rectangle getBounds() {
                // [[[FIXME]]]
                return null;
            }

            /**
             * Sets the bounds of this object in the form of a Rectangle
             * object.  The bounds specify this object's width, height, and
             * location relative to its parent.
             *
             * <p>
             *  以Rectangle对象的形式设置此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
             * 
             * 
             * @param r rectangle indicating this component's bounds
             * @see #getBounds
             */
            public void setBounds(Rectangle r) {
                // no-op; not supported
            }

            /**
             * Returns the size of this object in the form of a Dimension
             * object.  The height field of the Dimension object contains this
             * objects's height, and the width field of the Dimension object
             * contains this object's width.
             *
             * <p>
             * 以Dimension对象的形式返回此对象的大小。 Dimension对象的height字段包含此对象的高度,Dimension对象的width字段包含此对象的宽度。
             * 
             * 
             * @return A Dimension object that indicates the size of this
             * component; null if this object is not on the screen
             * @see #setSize
             */
            public Dimension getSize() {
                // [[[FIXME]]]
                return null;
            }

            /**
             * Resizes this object so that it has width and height.
             *
             * <p>
             *  调整此对象的大小,使其具有宽度和高度。
             * 
             * 
             * @param d - The dimension specifying the new size of the object.
             * @see #getSize
             */
            public void setSize(Dimension d) {
                // not supported; no-op
            }

            /**
             * Returns the <code>Accessible</code> child, if one exists,
             * contained at the local coordinate <code>Point</code>.
             *
             * <p>
             *  返回包含在本地坐标<code> Point </code>处的<code> Accessible </code>子代(如果存在)。
             * 
             * 
             * @param p the point relative to the coordinate system of this
             *     object
             * @return the <code>Accessible</code>, if it exists,
             *     at the specified location; otherwise <code>null</code>
             */
            public Accessible getAccessibleAt(Point p) {
                return null;    // object cannot have children!
            }

            /**
             * Returns whether this object can accept focus or not.   Objects
             * that can accept focus will also have the
             * <code>AccessibleState.FOCUSABLE</code> state set in their
             * <code>AccessibleStateSet</code>.
             *
             * <p>
             *  返回此对象是否可以接受焦点。
             * 可以接受焦点的对象也将在其<code> AccessibleStateSet </code>中设置<code> AccessibleState.FOCUSABLE </code>状态。
             * 
             * 
             * @return true if object can accept focus; otherwise false
             * @see AccessibleContext#getAccessibleStateSet
             * @see AccessibleState#FOCUSABLE
             * @see AccessibleState#FOCUSED
             * @see AccessibleStateSet
             */
            public boolean isFocusTraversable() {
                return false;   // list element cannot receive focus!
            }

            /**
             * Requests focus for this object.  If this object cannot accept
             * focus, nothing will happen.  Otherwise, the object will attempt
             * to take focus.
             * <p>
             *  此对象的请求焦点。如果这个对象不能接受焦点,什么也不会发生。否则,对象将尝试获取焦点。
             * 
             * 
             * @see #isFocusTraversable
             */
            public void requestFocus() {
                // nothing to do; a no-op
            }

            /**
             * Adds the specified focus listener to receive focus events from
             * this component.
             *
             * <p>
             *  添加指定的焦点侦听器以从此组件接收焦点事件。
             * 
             * 
             * @param l the focus listener
             * @see #removeFocusListener
             */
            public void addFocusListener(FocusListener l) {
                // nothing to do; a no-op
            }

            /**
             * Removes the specified focus listener so it no longer receives
             * focus events from this component.
             *
             * <p>
             *  删除指定的焦点侦听器,使其不再从此组件接收焦点事件。
             * 
             * @param l the focus listener
             * @see #addFocusListener
             */
            public void removeFocusListener(FocusListener l) {
                // nothing to do; a no-op
            }



        } // inner class AccessibleAWTListChild

    } // inner class AccessibleAWTList

}
