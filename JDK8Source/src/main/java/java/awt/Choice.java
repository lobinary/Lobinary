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

import java.util.*;
import java.awt.peer.ChoicePeer;
import java.awt.event.*;
import java.util.EventListener;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.accessibility.*;


/**
 * The <code>Choice</code> class presents a pop-up menu of choices.
 * The current choice is displayed as the title of the menu.
 * <p>
 * The following code example produces a pop-up menu:
 *
 * <hr><blockquote><pre>
 * Choice ColorChooser = new Choice();
 * ColorChooser.add("Green");
 * ColorChooser.add("Red");
 * ColorChooser.add("Blue");
 * </pre></blockquote><hr>
 * <p>
 * After this choice menu has been added to a panel,
 * it appears as follows in its normal state:
 * <p>
 * <img src="doc-files/Choice-1.gif" alt="The following text describes the graphic"
 * style="float:center; margin: 7px 10px;">
 * <p>
 * In the picture, <code>"Green"</code> is the current choice.
 * Pushing the mouse button down on the object causes a menu to
 * appear with the current choice highlighted.
 * <p>
 * Some native platforms do not support arbitrary resizing of
 * <code>Choice</code> components and the behavior of
 * <code>setSize()/getSize()</code> is bound by
 * such limitations.
 * Native GUI <code>Choice</code> components' size are often bound by such
 * attributes as font size and length of items contained within
 * the <code>Choice</code>.
 * <p>
 * <p>
 *  <code> Choice </code>类提供了一个弹出的选择菜单。当前选择显示为菜单的标题。
 * <p>
 *  以下代码示例生成一个弹出菜单：
 * 
 *  <hr> <blockquote> <pre> Choice ColorChooser = new Choice(); ColorChooser.add("Green"); ColorChooser.
 * add("Red"); ColorChooser.add("Blue"); </pre> </blockquote> <hr>。
 * <p>
 *  将此选择菜单添加到面板后,其正常状态下如下所示：
 * <p>
 *  <img src ="doc-files / Choice-1.gif"alt ="以下文本描述了图形"
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  在图片中,<code>"Green"</code>是当前的选择。在对象上按下鼠标按钮会出现一个菜单,突出显示当前选择。
 * <p>
 *  一些本机平台不支持对<code> Choice </code>组件的任意调整大小,并且<code> setSize()/ getSize()</code>的行为受到这些限制的约束。
 * 本地GUI <code>选择</code>组件的大小通常受诸如<code> Choice </code>中包含的项目的字体大小和长度等属性的约束。
 * <p>
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public class Choice extends Component implements ItemSelectable, Accessible {
    /**
     * The items for the <code>Choice</code>.
     * This can be a <code>null</code> value.
     * <p>
     *  <code>选择</code>的项目。这可以是<code> null </code>值。
     * 
     * 
     * @serial
     * @see #add(String)
     * @see #addItem(String)
     * @see #getItem(int)
     * @see #getItemCount()
     * @see #insert(String, int)
     * @see #remove(String)
     */
    Vector<String> pItems;

    /**
     * The index of the current choice for this <code>Choice</code>
     * or -1 if nothing is selected.
     * <p>
     *  这个<code> Choice </code>或-1(如果没有选择)的当前选择的索引。
     * 
     * 
     * @serial
     * @see #getSelectedItem()
     * @see #select(int)
     */
    int selectedIndex = -1;

    transient ItemListener itemListener;

    private static final String base = "choice";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4075310674757313071L;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        /* initialize JNI field and method ids */
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Creates a new choice menu. The menu initially has no items in it.
     * <p>
     * By default, the first item added to the choice menu becomes the
     * selected item, until a different selection is made by the user
     * by calling one of the <code>select</code> methods.
     * <p>
     *  创建新的选项菜单。菜单最初没有项目。
     * <p>
     * 默认情况下,添加到选择菜单的第一个项目变为所选项目,直到用户通过调用<code> select </code>方法之一进行不同的选择。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       #select(int)
     * @see       #select(java.lang.String)
     */
    public Choice() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        pItems = new Vector<>();
    }

    /**
     * Constructs a name for this component.  Called by
     * <code>getName</code> when the name is <code>null</code>.
     * <p>
     *  构造此组件的名称。当名称为<code> null </code>时,由<code> getName </code>调用。
     * 
     */
    String constructComponentName() {
        synchronized (Choice.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the <code>Choice</code>'s peer.  This peer allows us
     * to change the look
     * of the <code>Choice</code> without changing its functionality.
     * <p>
     *  创建<code> Choice </code>的对等体。这个对等体允许我们改变<code> Choice </code>的外观,而不改变它的功能。
     * 
     * 
     * @see     java.awt.Toolkit#createChoice(java.awt.Choice)
     * @see     java.awt.Component#getToolkit()
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createChoice(this);
            super.addNotify();
        }
    }

    /**
     * Returns the number of items in this <code>Choice</code> menu.
     * <p>
     *  返回此<code> Choice </code>菜单中的项目数。
     * 
     * 
     * @return the number of items in this <code>Choice</code> menu
     * @see     #getItem
     * @since   JDK1.1
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
        return pItems.size();
    }

    /**
     * Gets the string at the specified index in this
     * <code>Choice</code> menu.
     * <p>
     *  获取此<code> Choice </code>菜单中指定索引处的字符串。
     * 
     * 
     * @param      index the index at which to begin
     * @see        #getItemCount
     */
    public String getItem(int index) {
        return getItemImpl(index);
    }

    /*
     * This is called by the native code, so client code can't
     * be called on the toolkit thread.
     * <p>
     *  这是由本地代码调用的,所以客户端代码不能在工具包线程上调用。
     * 
     */
    final String getItemImpl(int index) {
        return pItems.elementAt(index);
    }

    /**
     * Adds an item to this <code>Choice</code> menu.
     * <p>
     *  向此<code>选择</code>菜单中添加项目。
     * 
     * 
     * @param      item    the item to be added
     * @exception  NullPointerException   if the item's value is
     *                  <code>null</code>
     * @since      JDK1.1
     */
    public void add(String item) {
        addItem(item);
    }

    /**
     * Obsolete as of Java 2 platform v1.1.  Please use the
     * <code>add</code> method instead.
     * <p>
     * Adds an item to this <code>Choice</code> menu.
     * <p>
     *  作为Java 2平台v1.1的已过时。请改用<code> add </code>方法。
     * <p>
     *  向此<code>选择</code>菜单中添加项目。
     * 
     * 
     * @param item the item to be added
     * @exception NullPointerException if the item's value is equal to
     *          <code>null</code>
     */
    public void addItem(String item) {
        synchronized (this) {
            insertNoInvalidate(item, pItems.size());
        }

        // This could change the preferred size of the Component.
        invalidateIfValid();
    }

    /**
     * Inserts an item to this <code>Choice</code>,
     * but does not invalidate the <code>Choice</code>.
     * Client methods must provide their own synchronization before
     * invoking this method.
     * <p>
     *  在<code> Choice </code>中插入项目,但不会使<code> Choice </code>无效。客户端方法必须在调用此方法之前提供自己的同步。
     * 
     * 
     * @param item the item to be added
     * @param index the new item position
     * @exception NullPointerException if the item's value is equal to
     *          <code>null</code>
     */
    private void insertNoInvalidate(String item, int index) {
        if (item == null) {
            throw new
                NullPointerException("cannot add null item to Choice");
        }
        pItems.insertElementAt(item, index);
        ChoicePeer peer = (ChoicePeer)this.peer;
        if (peer != null) {
            peer.add(item, index);
        }
        // no selection or selection shifted up
        if (selectedIndex < 0 || selectedIndex >= index) {
            select(0);
        }
    }


    /**
     * Inserts the item into this choice at the specified position.
     * Existing items at an index greater than or equal to
     * <code>index</code> are shifted up by one to accommodate
     * the new item.  If <code>index</code> is greater than or
     * equal to the number of items in this choice,
     * <code>item</code> is added to the end of this choice.
     * <p>
     * If the item is the first one being added to the choice,
     * then the item becomes selected.  Otherwise, if the
     * selected item was one of the items shifted, the first
     * item in the choice becomes the selected item.  If the
     * selected item was no among those shifted, it remains
     * the selected item.
     * <p>
     *  在指定位置将项目插入此选项。将大于或等于<code> index </code>的索引的现有项目向上移位1以容纳新项目。
     * 如果<code> index </code>大于或等于此选项中的项数,则<code> item </code>将添加到此选择的末尾。
     * <p>
     * 如果项目是添加到选择中的第一个项目,则该项目变为选择。否则,如果所选项目是移动的项目之一,则选项中的第一项目变为所选项目。如果所选项目在移动的项目中不是,则其保持为所选项目。
     * 
     * 
     * @param item the non-<code>null</code> item to be inserted
     * @param index the position at which the item should be inserted
     * @exception IllegalArgumentException if index is less than 0
     */
    public void insert(String item, int index) {
        synchronized (this) {
            if (index < 0) {
                throw new IllegalArgumentException("index less than zero.");
            }
            /* if the index greater than item count, add item to the end */
            index = Math.min(index, pItems.size());

            insertNoInvalidate(item, index);
        }

        // This could change the preferred size of the Component.
        invalidateIfValid();
    }

    /**
     * Removes the first occurrence of <code>item</code>
     * from the <code>Choice</code> menu.  If the item
     * being removed is the currently selected item,
     * then the first item in the choice becomes the
     * selected item.  Otherwise, the currently selected
     * item remains selected (and the selected index is
     * updated accordingly).
     * <p>
     *  从<code>选择</code>菜单中删除第一次出现的<code>项</code>。如果要删除的项目是当前选定的项目,则选项中的第一个项目将成为所选项目。
     * 否则,当前选择的项目保持选中状态(并且相应地更新所选索引)。
     * 
     * 
     * @param      item  the item to remove from this <code>Choice</code> menu
     * @exception  IllegalArgumentException  if the item doesn't
     *                     exist in the choice menu
     * @since      JDK1.1
     */
    public void remove(String item) {
        synchronized (this) {
            int index = pItems.indexOf(item);
            if (index < 0) {
                throw new IllegalArgumentException("item " + item +
                                                   " not found in choice");
            } else {
                removeNoInvalidate(index);
            }
        }

        // This could change the preferred size of the Component.
        invalidateIfValid();
    }

    /**
     * Removes an item from the choice menu
     * at the specified position.  If the item
     * being removed is the currently selected item,
     * then the first item in the choice becomes the
     * selected item.  Otherwise, the currently selected
     * item remains selected (and the selected index is
     * updated accordingly).
     * <p>
     *  从指定位置的选项菜单中删除项目。如果要删除的项目是当前选定的项目,则选项中的第一个项目将成为所选项目。否则,当前选择的项目保持选中状态(并且相应地更新所选索引)。
     * 
     * 
     * @param      position the position of the item
     * @throws IndexOutOfBoundsException if the specified
     *          position is out of bounds
     * @since      JDK1.1
     */
    public void remove(int position) {
        synchronized (this) {
            removeNoInvalidate(position);
        }

        // This could change the preferred size of the Component.
        invalidateIfValid();
    }

    /**
     * Removes an item from the <code>Choice</code> at the
     * specified position, but does not invalidate the <code>Choice</code>.
     * Client methods must provide their
     * own synchronization before invoking this method.
     * <p>
     *  从指定位置的<code> Choice </code>中移除项目,但不会使<code> Choice </code>无效。客户端方法必须在调用此方法之前提供自己的同步。
     * 
     * 
     * @param      position   the position of the item
     */
    private void removeNoInvalidate(int position) {
        pItems.removeElementAt(position);
        ChoicePeer peer = (ChoicePeer)this.peer;
        if (peer != null) {
            peer.remove(position);
        }
        /* Adjust selectedIndex if selected item was removed. */
        if (pItems.size() == 0) {
            selectedIndex = -1;
        } else if (selectedIndex == position) {
            select(0);
        } else if (selectedIndex > position) {
            select(selectedIndex-1);
        }
    }


    /**
     * Removes all items from the choice menu.
     * <p>
     *  从选项菜单中删除所有项目。
     * 
     * 
     * @see       #remove
     * @since     JDK1.1
     */
    public void removeAll() {
        synchronized (this) {
            if (peer != null) {
                ((ChoicePeer)peer).removeAll();
            }
            pItems.removeAllElements();
            selectedIndex = -1;
        }

        // This could change the preferred size of the Component.
        invalidateIfValid();
    }

    /**
     * Gets a representation of the current choice as a string.
     * <p>
     *  以字符串形式获取当前选择的表示。
     * 
     * 
     * @return    a string representation of the currently
     *                     selected item in this choice menu
     * @see       #getSelectedIndex
     */
    public synchronized String getSelectedItem() {
        return (selectedIndex >= 0) ? getItem(selectedIndex) : null;
    }

    /**
     * Returns an array (length 1) containing the currently selected
     * item.  If this choice has no items, returns <code>null</code>.
     * <p>
     *  返回包含当前选定项目的数组(长度1)。如果此选择没有项目,则返回<code> null </code>。
     * 
     * 
     * @see ItemSelectable
     */
    public synchronized Object[] getSelectedObjects() {
        if (selectedIndex >= 0) {
            Object[] items = new Object[1];
            items[0] = getItem(selectedIndex);
            return items;
        }
        return null;
    }

    /**
     * Returns the index of the currently selected item.
     * If nothing is selected, returns -1.
     *
     * <p>
     *  返回当前选定项目的索引。如果没有选择,返回-1。
     * 
     * 
     * @return the index of the currently selected item, or -1 if nothing
     *  is currently selected
     * @see #getSelectedItem
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Sets the selected item in this <code>Choice</code> menu to be the
     * item at the specified position.
     *
     * <p>Note that this method should be primarily used to
     * initially select an item in this component.
     * Programmatically calling this method will <i>not</i> trigger
     * an <code>ItemEvent</code>.  The only way to trigger an
     * <code>ItemEvent</code> is by user interaction.
     *
     * <p>
     * 将此<code> Choice </code>菜单中的选定项目设置为指定位置的项目。
     * 
     *  <p>请注意,此方法应主要用于初始选择此组件中的项目。以编程方式调用此方法将</i>不会触发<code> ItemEvent </code>。
     * 触发<code> ItemEvent </code>的唯一方法是通过用户交互。
     * 
     * 
     * @param      pos      the position of the selected item
     * @exception  IllegalArgumentException if the specified
     *                            position is greater than the
     *                            number of items or less than zero
     * @see        #getSelectedItem
     * @see        #getSelectedIndex
     */
    public synchronized void select(int pos) {
        if ((pos >= pItems.size()) || (pos < 0)) {
            throw new IllegalArgumentException("illegal Choice item position: " + pos);
        }
        if (pItems.size() > 0) {
            selectedIndex = pos;
            ChoicePeer peer = (ChoicePeer)this.peer;
            if (peer != null) {
                peer.select(pos);
            }
        }
    }

    /**
     * Sets the selected item in this <code>Choice</code> menu
     * to be the item whose name is equal to the specified string.
     * If more than one item matches (is equal to) the specified string,
     * the one with the smallest index is selected.
     *
     * <p>Note that this method should be primarily used to
     * initially select an item in this component.
     * Programmatically calling this method will <i>not</i> trigger
     * an <code>ItemEvent</code>.  The only way to trigger an
     * <code>ItemEvent</code> is by user interaction.
     *
     * <p>
     *  将此<code> Choice </code>菜单中的选定项目设置为名称等于指定字符串的项目。如果多个项目与指定的字符串匹配(等于),则选择索引最小的字符串。
     * 
     *  <p>请注意,此方法应主要用于初始选择此组件中的项目。以编程方式调用此方法将</i>不会触发<code> ItemEvent </code>。
     * 触发<code> ItemEvent </code>的唯一方法是通过用户交互。
     * 
     * 
     * @param       str     the specified string
     * @see         #getSelectedItem
     * @see         #getSelectedIndex
     */
    public synchronized void select(String str) {
        int index = pItems.indexOf(str);
        if (index >= 0) {
            select(index);
        }
    }

    /**
     * Adds the specified item listener to receive item events from
     * this <code>Choice</code> menu.  Item events are sent in response
     * to user input, but not in response to calls to <code>select</code>.
     * If l is <code>null</code>, no exception is thrown and no action
     * is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     * <p>
     *  添加指定的项目监听器以从此<code> Choice </code>菜单接收项目事件。项目事件是响应用户输入而发送的,但不响应对<code> select </code>的调用。
     * 如果l是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l    the item listener
     * @see           #removeItemListener
     * @see           #getItemListeners
     * @see           #select
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
     * Removes the specified item listener so that it no longer receives
     * item events from this <code>Choice</code> menu.
     * If l is <code>null</code>, no exception is thrown and no
     * action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     * <p>
     * 删除指定的项目侦听器,使其不再从此<code> Choice </code>菜单接收项目事件。如果l是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l    the item listener
     * @see           #addItemListener
     * @see           #getItemListeners
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since         JDK1.1
     */
    public synchronized void removeItemListener(ItemListener l) {
        if (l == null) {
            return;
        }
        itemListener = AWTEventMulticaster.remove(itemListener, l);
    }

    /**
     * Returns an array of all the item listeners
     * registered on this choice.
     *
     * <p>
     *  返回在此选择上注册的所有项目侦听器的数组。
     * 
     * 
     * @return all of this choice's <code>ItemListener</code>s
     *         or an empty array if no item
     *         listeners are currently registered
     *
     * @see           #addItemListener
     * @see           #removeItemListener
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since 1.4
     */
    public synchronized ItemListener[] getItemListeners() {
        return getListeners(ItemListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this <code>Choice</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>Choice</code> <code>c</code>
     * for its item listeners with the following code:
     *
     * <pre>ItemListener[] ils = (ItemListener[])(c.getListeners(ItemListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     *  返回当前在此<code> Choice </code>上注册为<code> <em> Foo </em>侦听器</code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * 
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询其项目侦听器的<code> Choice </code> <code> c </code>：。
     * 
     *  <pre> ItemListener [] ils =(ItemListener [])(c.getListeners(ItemListener.class)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组。
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this choice,
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
        if  (listenerType == ItemListener.class) {
            l = itemListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        if (e.id == ItemEvent.ITEM_STATE_CHANGED) {
            if ((eventMask & AWTEvent.ITEM_EVENT_MASK) != 0 ||
                itemListener != null) {
                return true;
            }
            return false;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this choice. If the event is an
     * instance of <code>ItemEvent</code>, it invokes the
     * <code>processItemEvent</code> method. Otherwise, it calls its
     * superclass's <code>processEvent</code> method.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  处理此选择上的事件。如果事件是<code> ItemEvent </code>的实例,它会调用<code> processItemEvent </code>方法。
     * 否则,它调用其超类的<code> processEvent </code>方法。 <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param      e the event
     * @see        java.awt.event.ItemEvent
     * @see        #processItemEvent
     * @since      JDK1.1
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof ItemEvent) {
            processItemEvent((ItemEvent)e);
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes item events occurring on this <code>Choice</code>
     * menu by dispatching them to any registered
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
     *  通过将它们分发到任何已注册的<code> ItemListener </code>对象来处理在此<code> Choice </code>菜单上发生的项目事件。
     * <p>
     * 除非为此组件启用项目事件,否则不会调用此方法。当发生以下情况之一时,将启用项目事件：
     * <ul>
     *  <li> <code> ItemListener </code>对象通过<code> addItemListener </code>注册。
     *  <li>项目事件通过<code> enableEvents </code>启用。
     * </ul>
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param       e the item event
     * @see         java.awt.event.ItemEvent
     * @see         java.awt.event.ItemListener
     * @see         #addItemListener(ItemListener)
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
     * Returns a string representing the state of this <code>Choice</code>
     * menu. This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回表示此<code> Choice </code>菜单的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return    the parameter string of this <code>Choice</code> menu
     */
    protected String paramString() {
        return super.paramString() + ",current=" + getSelectedItem();
    }


    /* Serialization support.
    /* <p>
     */

    /*
     * Choice Serial Data Version.
     * <p>
     *  选择串行数据版本。
     * 
     * 
     * @serial
     */
    private int choiceSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable <code>ItemListeners</code>
     * as optional data. The non-serializable
     * <code>ItemListeners</code> are detected and
     * no attempt is made to serialize them.
     *
     * <p>
     *  将缺省可序列化字段写入流。将可序列化的<code> ItemListeners </code>列表写为可选数据。
     * 检测到不可序列化的<code> ItemListeners </code>,并且不尝试将它们序列化。
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @serialData <code>null</code> terminated sequence of 0
     *   or more pairs; the pair consists of a <code>String</code>
     *   and an <code>Object</code>; the <code>String</code> indicates
     *   the type of object and is one of the following:
     *   <code>itemListenerK</code> indicating an
     *     <code>ItemListener</code> object
     *
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#itemListenerK
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s)
      throws java.io.IOException
    {
      s.defaultWriteObject();

      AWTEventMulticaster.save(s, itemListenerK, itemListener);
      s.writeObject(null);
    }

    /**
     * Reads the <code>ObjectInputStream</code> and if it
     * isn't <code>null</code> adds a listener to receive
     * item events fired by the <code>Choice</code> item.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     *  读取<code> ObjectInputStream </code>,如果不是<code> null </code>添加了一个侦听器来接收由<code> Choice </code>项目触发的项目事件
     * 。
     * 无法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @serial
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

        else // skip value for unrecognized key
          s.readObject();
      }
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

/////////////////
// Accessibility support
////////////////


    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>Choice</code>. For <code>Choice</code> components,
     * the <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleAWTChoice</code>. A new <code>AccessibleAWTChoice</code>
     * instance is created if necessary.
     *
     * <p>
     *  获取与此<code> Choice </code>关联的<code> AccessibleContext </code>。
     * 对于<code> Choice </code>组件,<code> AccessibleContext </code>采用<code> AccessibleAWTChoice </code>的形式。
     * 如有必要,将创建一个新的<code> AccessibleAWTChoice </code>实例。
     * 
     * 
     * @return an <code>AccessibleAWTChoice</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>Choice</code>
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTChoice();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Choice</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to choice user-interface elements.
     * <p>
     * 此类实现<code> Choice </code>类的辅助功能支持。它提供了适合于选择用户界面元素的Java可访问性API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTChoice extends AccessibleAWTComponent
        implements AccessibleAction
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 7175603582428509322L;

        public AccessibleAWTChoice() {
            super();
        }

        /**
         * Get the AccessibleAction associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleAction interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleAction。在为该类实现Java辅助功能API时,返回此对象,该对象负责代表自身实现AccessibleAction接口。
         * 
         * 
         * @return this object
         * @see AccessibleAction
         */
        public AccessibleAction getAccessibleAction() {
            return this;
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
            return AccessibleRole.COMBO_BOX;
        }

        /**
         * Returns the number of accessible actions available in this object
         * If there are more than one, the first one is considered the "default"
         * action of the object.
         *
         * <p>
         *  返回此对象中可用的可用操作数如果有多个,则第一个被视为对象的"默认"操作。
         * 
         * 
         * @return the zero-based number of Actions in this object
         */
        public int getAccessibleActionCount() {
            return 0;  //  To be fully implemented in a future release
        }

        /**
         * Returns a description of the specified action of the object.
         *
         * <p>
         *  返回对象的指定操作的描述。
         * 
         * 
         * @param i zero-based index of the actions
         * @return a String description of the action
         * @see #getAccessibleActionCount
         */
        public String getAccessibleActionDescription(int i) {
            return null;  //  To be fully implemented in a future release
        }

        /**
         * Perform the specified Action on the object
         *
         * <p>
         *  对对象执行指定的Action
         * 
         * @param i zero-based index of actions
         * @return true if the action was performed; otherwise false.
         * @see #getAccessibleActionCount
         */
        public boolean doAccessibleAction(int i) {
            return false;  //  To be fully implemented in a future release
        }

    } // inner class AccessibleAWTChoice

}
