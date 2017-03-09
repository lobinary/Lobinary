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
import java.beans.Transient;
import java.util.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.accessibility.*;
import sun.swing.SwingUtilities2;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A component that lets the user switch between a group of components by
 * clicking on a tab with a given title and/or icon.
 * For examples and information on using tabbed panes see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html">How to Use Tabbed Panes</a>,
 * a section in <em>The Java Tutorial</em>.
 * <p>
 * Tabs/components are added to a <code>TabbedPane</code> object by using the
 * <code>addTab</code> and <code>insertTab</code> methods.
 * A tab is represented by an index corresponding
 * to the position it was added in, where the first tab has an index equal to 0
 * and the last tab has an index equal to the tab count minus 1.
 * <p>
 * The <code>TabbedPane</code> uses a <code>SingleSelectionModel</code>
 * to represent the set
 * of tab indices and the currently selected index.  If the tab count
 * is greater than 0, then there will always be a selected index, which
 * by default will be initialized to the first tab.  If the tab count is
 * 0, then the selected index will be -1.
 * <p>
 * The tab title can be rendered by a <code>Component</code>.
 * For example, the following produce similar results:
 * <pre>
 * // In this case the look and feel renders the title for the tab.
 * tabbedPane.addTab("Tab", myComponent);
 * // In this case the custom component is responsible for rendering the
 * // title of the tab.
 * tabbedPane.addTab(null, myComponent);
 * tabbedPane.setTabComponentAt(0, new JLabel("Tab"));
 * </pre>
 * The latter is typically used when you want a more complex user interaction
 * that requires custom components on the tab.  For example, you could
 * provide a custom component that animates or one that has widgets for
 * closing the tab.
 * <p>
 * If you specify a component for a tab, the <code>JTabbedPane</code>
 * will not render any text or icon you have specified for the tab.
 * <p>
 * <strong>Note:</strong>
 * Do not use <code>setVisible</code> directly on a tab component to make it visible,
 * use <code>setSelectedComponent</code> or <code>setSelectedIndex</code> methods instead.
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
 *
 * @beaninfo
 *      attribute: isContainer true
 *    description: A component which provides a tab folder metaphor for
 *                 displaying one component from a set of components.
 *
 * <p>
 *  允许用户通过单击具有给定标题和/或图标的选项卡在一组组件之间切换的组件。
 * 有关使用标签式窗格的示例和信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html">
 * 如何使用标签式窗格</a>, <em> Java教程</em>。
 *  允许用户通过单击具有给定标题和/或图标的选项卡在一组组件之间切换的组件。
 * <p>
 *  通过使用<code> addTab </code>和<code> insertTab </code>方法将标签/组件添加到<code> TabbedPane </code>对象中。
 * 标签由与其被添加的位置相对应的索引表示,其中第一标签具有等于0的索引,并且最后一个标签具有等于标签计数减1的索引。
 * <p>
 *  <code> TabbedPane </code>使用<code> SingleSelectionModel </code>来表示选项卡索引集和当前选定的索引。
 * 如果标签计数大于0,那么将始终有一个选定的索引,默认情况下将被初始化为第一个选项卡。如果选项卡计数为0,则选定的索引将为-1。
 * <p>
 *  选项卡标题可以通过<code> Component </code>来呈现。例如,以下产生类似的结果：
 * <pre>
 * //在这种情况下,外观会呈现标签的标题。 tabbedPane.addTab("Tab",myComponent); //在这种情况下,自定义组件负责呈现标签的//标题。
 *  tabbedPane.addTab(null,myComponent); tabbedPane.setTabComponentAt(0,new JLabel("Tab"));。
 * </pre>
 *  后者通常用于需要更复杂的用户交互,需要选项卡上的自定义组件。例如,您可以提供一个动画的自定义组件或具有用于关闭该选项卡的小部件的自定义组件。
 * <p>
 *  如果为选项卡指定组件,<code> JTabbedPane </code>将不会呈现您为该选项卡指定的任何文本或图标。
 * <p>
 *  <strong>注意</strong>：不要直接在标签页组件上使用<code> setVisible </code>使其可见,而改用<code> setSelectedComponent </code>
 * 或<code> setSelectedIndex </code> 。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * @beaninfo属性：isContainer true description：提供一个tab文件夹隐喻的组件,用于显示一组组件中的一个组件。
 * 
 * 
 * @author Dave Moore
 * @author Philip Milne
 * @author Amy Fowler
 *
 * @see SingleSelectionModel
 */
public class JTabbedPane extends JComponent
       implements Serializable, Accessible, SwingConstants {

   /**
    * The tab layout policy for wrapping tabs in multiple runs when all
    * tabs will not fit within a single run.
    * <p>
    *  当所有选项卡都不适合单个运行时,用于在多个运行中包装选项卡的选项卡布局策略。
    * 
    */
    public static final int WRAP_TAB_LAYOUT = 0;

   /**
    * Tab layout policy for providing a subset of available tabs when all
    * the tabs will not fit within a single run.  If all the tabs do
    * not fit within a single run the look and feel will provide a way
    * to navigate to hidden tabs.
    * <p>
    *  当所有选项卡都不适合单个运行时,用于提供可用选项卡子集的选项卡布局策略。如果所有选项卡不适合单个运行,外观和感觉将提供一种导航到隐藏选项卡的方法。
    * 
    */
    public static final int SCROLL_TAB_LAYOUT = 1;


    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "TabbedPaneUI";

    /**
     * Where the tabs are placed.
     * <p>
     *  放置选项卡的位置。
     * 
     * 
     * @see #setTabPlacement
     */
    protected int tabPlacement = TOP;

    private int tabLayoutPolicy;

    /** The default selection model */
    protected SingleSelectionModel model;

    private boolean haveRegistered;

    /**
     * The <code>changeListener</code> is the listener we add to the
     * model.
     * <p>
     *  <code> changeListener </code>是我们添加到模型的监听器。
     * 
     */
    protected ChangeListener changeListener = null;

    private final java.util.List<Page> pages;

    /* The component that is currently visible */
    private Component visComp = null;

    /**
     * Only one <code>ChangeEvent</code> is needed per <code>TabPane</code>
     * instance since the
     * event's only (read-only) state is the source property.  The source
     * of events generated here is always "this".
     * <p>
     *  由于事件的只读(只读)状态是源属性,因此每个<code> TabPane </code>实例只需要一个<code> ChangeEvent </code>。这里生成的事件源始终是"this"。
     * 
     */
    protected transient ChangeEvent changeEvent = null;

    /**
     * Creates an empty <code>TabbedPane</code> with a default
     * tab placement of <code>JTabbedPane.TOP</code>.
     * <p>
     *  使用<code> JTabbedPane.TOP </code>的默认标签位置创建一个空的<code> TabbedPane </code>。
     * 
     * 
     * @see #addTab
     */
    public JTabbedPane() {
        this(TOP, WRAP_TAB_LAYOUT);
    }

    /**
     * Creates an empty <code>TabbedPane</code> with the specified tab placement
     * of either: <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
     * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>.
     *
     * <p>
     *  使用指定的标签位置创建一个空的<code> TabbedPane </code>：<code> JTabbedPane.TOP </code>,<code> JTabbedPane.BOTTOM </code>
     * ,<code> JTabbedPane.LEFT </code >或<code> JTabbedPane.RIGHT </code>。
     * 
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     * @see #addTab
     */
    public JTabbedPane(int tabPlacement) {
        this(tabPlacement, WRAP_TAB_LAYOUT);
    }

    /**
     * Creates an empty <code>TabbedPane</code> with the specified tab placement
     * and tab layout policy.  Tab placement may be either:
     * <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
     * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>.
     * Tab layout policy may be either: <code>JTabbedPane.WRAP_TAB_LAYOUT</code>
     * or <code>JTabbedPane.SCROLL_TAB_LAYOUT</code>.
     *
     * <p>
     *  使用指定的标签布置和标签布局策略创建一个空的<code> TabbedPane </code>。
     * 标签位置可以是：<code> JTabbedPane.TOP </code>,<code> JTabbedPane.BOTTOM </code>,<code> JTabbedPane.LEFT </code>
     * 或<code> JTabbedPane.RIGHT </code> 。
     *  使用指定的标签布置和标签布局策略创建一个空的<code> TabbedPane </code>。
     * 制表符布局策略可以是：<code> JTabbedPane.WRAP_TAB_LAYOUT </code>或<code> JTabbedPane.SCROLL_TAB_LAYOUT </code>。
     * 
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
     * @exception IllegalArgumentException if tab placement or tab layout policy are not
     *            one of the above supported values
     * @see #addTab
     * @since 1.4
     */
    public JTabbedPane(int tabPlacement, int tabLayoutPolicy) {
        setTabPlacement(tabPlacement);
        setTabLayoutPolicy(tabLayoutPolicy);
        pages = new ArrayList<Page>(1);
        setModel(new DefaultSingleSelectionModel());
        updateUI();
    }

    /**
     * Returns the UI object which implements the L&amp;F for this component.
     *
     * <p>
     * 返回实现此组件的L&amp; F的UI对象。
     * 
     * 
     * @return a <code>TabbedPaneUI</code> object
     * @see #setUI
     */
    public TabbedPaneUI getUI() {
        return (TabbedPaneUI)ui;
    }

    /**
     * Sets the UI object which implements the L&amp;F for this component.
     *
     * <p>
     *  设置实现此组件的L&amp; F的UI对象。
     * 
     * 
     * @param ui the new UI object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the tabbedpane's LookAndFeel
     */
    public void setUI(TabbedPaneUI ui) {
        super.setUI(ui);
        // disabled icons are generated by LF so they should be unset here
        for (int i = 0; i < getTabCount(); i++) {
            Icon icon = pages.get(i).disabledIcon;
            if (icon instanceof UIResource) {
                setDisabledIconAt(i, null);
            }
        }
    }

    /**
     * Resets the UI property to a value from the current look and feel.
     *
     * <p>
     *  将UI属性重置为当前外观的值。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((TabbedPaneUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the UI class that implements the
     * L&amp;F for this component.
     *
     * <p>
     *  返回实现此组件的L&amp; F的UI类的名称。
     * 
     * 
     * @return the string "TabbedPaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * We pass <code>ModelChanged</code> events along to the listeners with
     * the tabbedpane (instead of the model itself) as the event source.
     * <p>
     *  我们将<code> ModelChanged </code>事件传递给具有选项卡式窗格(而不是模型本身)作为事件源的侦听器。
     * 
     */
    protected class ModelListener implements ChangeListener, Serializable {
        public void stateChanged(ChangeEvent e) {
            fireStateChanged();
        }
    }

    /**
     * Subclasses that want to handle <code>ChangeEvents</code> differently
     * can override this to return a subclass of <code>ModelListener</code> or
     * another <code>ChangeListener</code> implementation.
     *
     * <p>
     *  想要处理<code> ChangeEvents </code>的子类可以覆盖此类,以返回<code> ModelListener </code>或另一个<code> ChangeListener </code>
     * 实现的子类。
     * 
     * 
     * @see #fireStateChanged
     */
    protected ChangeListener createChangeListener() {
        return new ModelListener();
    }

    /**
     * Adds a <code>ChangeListener</code> to this tabbedpane.
     *
     * <p>
     *  在此标签栏中添加<code> ChangeListener </code>。
     * 
     * 
     * @param l the <code>ChangeListener</code> to add
     * @see #fireStateChanged
     * @see #removeChangeListener
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * Removes a <code>ChangeListener</code> from this tabbedpane.
     *
     * <p>
     *  从此选项卡式窗格中删除<code> ChangeListener </code>。
     * 
     * 
     * @param l the <code>ChangeListener</code> to remove
     * @see #fireStateChanged
     * @see #addChangeListener
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

   /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this <code>JTabbedPane</code> with <code>addChangeListener</code>.
     *
     * <p>
     *  返回使用<code> addChangeListener </code>添加到此<code> JTabbedPane </code>中的所有<code> ChangeListener </code>的
     * 数组。
     * 
     * 
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Sends a {@code ChangeEvent}, with this {@code JTabbedPane} as the source,
     * to each registered listener. This method is called each time there is
     * a change to either the selected index or the selected tab in the
     * {@code JTabbedPane}. Usually, the selected index and selected tab change
     * together. However, there are some cases, such as tab addition, where the
     * selected index changes and the same tab remains selected. There are other
     * cases, such as deleting the selected tab, where the index remains the
     * same, but a new tab moves to that index. Events are fired for all of
     * these cases.
     *
     * <p>
     *  使用此{@code JTabbedPane}作为源,向每个已注册的侦听器发送{@code ChangeEvent}。
     * 每当对所选索引或{@code JTabbedPane}中的所选选项卡进行更改时,将调用此方法。通常,所选索引和所选标签一起改变。
     * 但是,在某些情况下,如选项卡添加,选定的索引更改,同一选项卡仍保持选中状态。还有其他情况,例如删除所选标签,其中索引保持不变,但新标签移动到该索引。所有这些情况都会触发事件。
     * 
     * 
     * @see #addChangeListener
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        /* --- Begin code to deal with visibility --- */

        /* This code deals with changing the visibility of components to
         * hide and show the contents for the selected tab. It duplicates
         * logic already present in BasicTabbedPaneUI, logic that is
         * processed during the layout pass. This code exists to allow
         * developers to do things that are quite difficult to accomplish
         * with the previous model of waiting for the layout pass to process
         * visibility changes; such as requesting focus on the new visible
         * component.
         *
         * For the average code, using the typical JTabbedPane methods,
         * all visibility changes will now be processed here. However,
         * the code in BasicTabbedPaneUI still exists, for the purposes
         * of backward compatibility. Therefore, when making changes to
         * this code, ensure that the BasicTabbedPaneUI code is kept in
         * synch.
         * <p>
         * 隐藏和显示所选选项卡的内容。它复制已经存在于BasicTabbedPaneUI中的逻辑,在布局传递期间处理的逻辑。
         * 这个代码存在允许开发人员做以前的模型很难完成的等待布局传递来处理可见性更改的模型;例如请求关注新的可见组件。
         * 
         *  对于平均代码,使用典型的JTabbedPane方法,现在将处理所有可见性更改。但是,BasicTabbedPaneUI中的代码仍然存在,用于向后兼容性。
         * 因此,在更改此代码时,请确保BasicTabbedPaneUI代码保持同步。
         * 
         */

        int selIndex = getSelectedIndex();

        /* if the selection is now nothing */
        if (selIndex < 0) {
            /* if there was a previous visible component */
            if (visComp != null && visComp.isVisible()) {
                /* make it invisible */
                visComp.setVisible(false);
            }

            /* now there's no visible component */
            visComp = null;

        /* else - the selection is now something */
        } else {
            /* Fetch the component for the new selection */
            Component newComp = getComponentAt(selIndex);

            /* if the new component is non-null and different */
            if (newComp != null && newComp != visComp) {
                boolean shouldChangeFocus = false;

                /* Note: the following (clearing of the old visible component)
                 * is inside this if-statement for good reason: Tabbed pane
                 * should continue to show the previously visible component
                 * if there is no component for the chosen tab.
                 * <p>
                 *  是在这个if语句内有很好的理由：如果所选择的选项卡没有组件,选项卡式窗格应该继续显示以前可见的组件。
                 * 
                 */

                /* if there was a previous visible component */
                if (visComp != null) {
                    shouldChangeFocus =
                        (SwingUtilities.findFocusOwner(visComp) != null);

                    /* if it's still visible */
                    if (visComp.isVisible()) {
                        /* make it invisible */
                        visComp.setVisible(false);
                    }
                }

                if (!newComp.isVisible()) {
                    newComp.setVisible(true);
                }

                if (shouldChangeFocus) {
                    SwingUtilities2.tabbedPaneChangeFocusTo(newComp);
                }

                visComp = newComp;
            } /* else - the visible component shouldn't changed */
        }

        /* --- End code to deal with visibility --- */

        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

    /**
     * Returns the model associated with this tabbedpane.
     *
     * <p>
     *  返回与此标签页相关联的模型。
     * 
     * 
     * @see #setModel
     */
    public SingleSelectionModel getModel() {
        return model;
    }

    /**
     * Sets the model to be used with this tabbedpane.
     *
     * <p>
     *  设置要与此选项卡窗格一起使用的模型。
     * 
     * 
     * @param model the model to be used
     * @see #getModel
     * @beaninfo
     *       bound: true
     * description: The tabbedpane's SingleSelectionModel.
     */
    public void setModel(SingleSelectionModel model) {
        SingleSelectionModel oldModel = getModel();

        if (oldModel != null) {
            oldModel.removeChangeListener(changeListener);
            changeListener = null;
        }

        this.model = model;

        if (model != null) {
            changeListener = createChangeListener();
            model.addChangeListener(changeListener);
        }

        firePropertyChange("model", oldModel, model);
        repaint();
    }

    /**
     * Returns the placement of the tabs for this tabbedpane.
     * <p>
     *  返回此选项卡窗格的选项卡的位置。
     * 
     * 
     * @see #setTabPlacement
     */
    public int getTabPlacement() {
        return tabPlacement;
    }

    /**
     * Sets the tab placement for this tabbedpane.
     * Possible values are:<ul>
     * <li><code>JTabbedPane.TOP</code>
     * <li><code>JTabbedPane.BOTTOM</code>
     * <li><code>JTabbedPane.LEFT</code>
     * <li><code>JTabbedPane.RIGHT</code>
     * </ul>
     * The default value, if not set, is <code>SwingConstants.TOP</code>.
     *
     * <p>
     *  设置此选项卡窗格的选项卡位置。
     * 可能的值有：<ul> <li> <code> JTabbedPane.TFT </code> <li> <code> JTabbedPane.BOTTOM </code> <li> <code>代码> 
     * JTabbedPane.RIGHT </code>。
     *  设置此选项卡窗格的选项卡位置。
     * </ul>
     *  默认值(如果未设置)为<code> SwingConstants.TOP </code>。
     * 
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     * @exception IllegalArgumentException if tab placement value isn't one
     *                          of the above valid values
     *
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *         enum: TOP JTabbedPane.TOP
     *               LEFT JTabbedPane.LEFT
     *               BOTTOM JTabbedPane.BOTTOM
     *               RIGHT JTabbedPane.RIGHT
     *  description: The tabbedpane's tab placement.
     *
     */
    public void setTabPlacement(int tabPlacement) {
        if (tabPlacement != TOP && tabPlacement != LEFT &&
            tabPlacement != BOTTOM && tabPlacement != RIGHT) {
            throw new IllegalArgumentException("illegal tab placement: must be TOP, BOTTOM, LEFT, or RIGHT");
        }
        if (this.tabPlacement != tabPlacement) {
            int oldValue = this.tabPlacement;
            this.tabPlacement = tabPlacement;
            firePropertyChange("tabPlacement", oldValue, tabPlacement);
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the policy used by the tabbedpane to layout the tabs when all the
     * tabs will not fit within a single run.
     * <p>
     *  返回当所有选项卡不适合单个运行时,选项卡式窗格布局选项卡所使用的策略。
     * 
     * 
     * @see #setTabLayoutPolicy
     * @since 1.4
     */
    public int getTabLayoutPolicy() {
        return tabLayoutPolicy;
    }

   /**
     * Sets the policy which the tabbedpane will use in laying out the tabs
     * when all the tabs will not fit within a single run.
     * Possible values are:
     * <ul>
     * <li><code>JTabbedPane.WRAP_TAB_LAYOUT</code>
     * <li><code>JTabbedPane.SCROLL_TAB_LAYOUT</code>
     * </ul>
     *
     * The default value, if not set by the UI, is <code>JTabbedPane.WRAP_TAB_LAYOUT</code>.
     * <p>
     * Some look and feels might only support a subset of the possible
     * layout policies, in which case the value of this property may be
     * ignored.
     *
     * <p>
     * 设置当所有选项卡在单次运行中不合适时,选项卡式窗格在布局选项卡时将使用的策略。可能的值为：
     * <ul>
     *  <li> <code> JTabbedPane.WRAP_TAB_LAYOUT </code> <li> <code> JTabbedPane.SCROLL_TAB_LAYOUT </code>
     * </ul>
     * 
     *  如果UI未设置默认值,则为<code> JTabbedPane.WRAP_TAB_LAYOUT </code>。
     * <p>
     *  某些外观和感觉可能只支持可能的布局策略的一个子集,在这种情况下,此属性的值可能会被忽略。
     * 
     * 
     * @param tabLayoutPolicy the policy used to layout the tabs
     * @exception IllegalArgumentException if layoutPolicy value isn't one
     *                          of the above valid values
     * @see #getTabLayoutPolicy
     * @since 1.4
     *
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *         enum: WRAP_TAB_LAYOUT JTabbedPane.WRAP_TAB_LAYOUT
     *               SCROLL_TAB_LAYOUT JTabbedPane.SCROLL_TAB_LAYOUT
     *  description: The tabbedpane's policy for laying out the tabs
     *
     */
    public void setTabLayoutPolicy(int tabLayoutPolicy) {
        if (tabLayoutPolicy != WRAP_TAB_LAYOUT && tabLayoutPolicy != SCROLL_TAB_LAYOUT) {
            throw new IllegalArgumentException("illegal tab layout policy: must be WRAP_TAB_LAYOUT or SCROLL_TAB_LAYOUT");
        }
        if (this.tabLayoutPolicy != tabLayoutPolicy) {
            int oldValue = this.tabLayoutPolicy;
            this.tabLayoutPolicy = tabLayoutPolicy;
            firePropertyChange("tabLayoutPolicy", oldValue, tabLayoutPolicy);
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the currently selected index for this tabbedpane.
     * Returns -1 if there is no currently selected tab.
     *
     * <p>
     *  返回此选项卡式窗格的当前选定的索引。如果当前没有选择标签,则返回-1。
     * 
     * 
     * @return the index of the selected tab
     * @see #setSelectedIndex
     */
    @Transient
    public int getSelectedIndex() {
        return model.getSelectedIndex();
    }

    /**
     * Sets the selected index for this tabbedpane. The index must be
     * a valid tab index or -1, which indicates that no tab should be selected
     * (can also be used when there are no tabs in the tabbedpane).  If a -1
     * value is specified when the tabbedpane contains one or more tabs, then
     * the results will be implementation defined.
     *
     * <p>
     *  设置此选项卡式窗格的所选索引。索引必须是有效的索引索引或-1,表示不应选择选项卡(也可以在选项卡窗格中没有选项卡时使用)。如果在选项卡式窗格包含一个或多个选项卡时指定了-1值,则结果将由实现定义。
     * 
     * 
     * @param index  the index to be selected
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < -1 || index >= tab count)}
     *
     * @see #getSelectedIndex
     * @see SingleSelectionModel#setSelectedIndex
     * @beaninfo
     *   preferred: true
     * description: The tabbedpane's selected tab index.
     */
    public void setSelectedIndex(int index) {
        if (index != -1) {
            checkIndex(index);
        }
        setSelectedIndexImpl(index, true);
    }


    private void setSelectedIndexImpl(int index, boolean doAccessibleChanges) {
        int oldIndex = model.getSelectedIndex();
        Page oldPage = null, newPage = null;
        String oldName = null;

        doAccessibleChanges = doAccessibleChanges && (oldIndex != index);

        if (doAccessibleChanges) {
            if (accessibleContext != null) {
                oldName = accessibleContext.getAccessibleName();
            }

            if (oldIndex >= 0) {
                oldPage = pages.get(oldIndex);
            }

            if (index >= 0) {
                newPage = pages.get(index);
            }
        }

        model.setSelectedIndex(index);

        if (doAccessibleChanges) {
            changeAccessibleSelection(oldPage, oldName, newPage);
        }
    }

    private void changeAccessibleSelection(Page oldPage, String oldName, Page newPage) {
        if (accessibleContext == null) {
            return;
        }

        if (oldPage != null) {
            oldPage.firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                       AccessibleState.SELECTED, null);
        }

        if (newPage != null) {
            newPage.firePropertyChange(AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                                       null, AccessibleState.SELECTED);
        }

        accessibleContext.firePropertyChange(
            AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
            oldName,
            accessibleContext.getAccessibleName());
    }

    /**
     * Returns the currently selected component for this tabbedpane.
     * Returns <code>null</code> if there is no currently selected tab.
     *
     * <p>
     *  返回此选项卡式窗格的当前选定组件。如果当前没有选择标签,则返回<code> null </code>。
     * 
     * 
     * @return the component corresponding to the selected tab
     * @see #setSelectedComponent
     */
    @Transient
    public Component getSelectedComponent() {
        int index = getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return getComponentAt(index);
    }

    /**
     * Sets the selected component for this tabbedpane.  This
     * will automatically set the <code>selectedIndex</code> to the index
     * corresponding to the specified component.
     *
     * <p>
     *  设置此选项卡式窗格的所选组件。这将自动将<code> selectedIndex </code>设置为对应于指定组件的索引。
     * 
     * 
     * @exception IllegalArgumentException if component not found in tabbed
     *          pane
     * @see #getSelectedComponent
     * @beaninfo
     *   preferred: true
     * description: The tabbedpane's selected component.
     */
    public void setSelectedComponent(Component c) {
        int index = indexOfComponent(c);
        if (index != -1) {
            setSelectedIndex(index);
        } else {
            throw new IllegalArgumentException("component not found in tabbed pane");
        }
    }

    /**
     * Inserts a new tab for the given component, at the given index,
     * represented by the given title and/or icon, either of which may
     * be {@code null}.
     *
     * <p>
     *  在给定组件的给定索引处插入一个新的选项卡,由给定的标题和/或图标表示,其中任何一个都可以是{@code null}。
     * 
     * 
     * @param title the title to be displayed on the tab
     * @param icon the icon to be displayed on the tab
     * @param component the component to be displayed when this tab is clicked.
     * @param tip the tooltip to be displayed for this tab
     * @param index the position to insert this new tab
     *       ({@code > 0 and <= getTabCount()})
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code < 0 or > getTabCount()})
     *
     * @see #addTab
     * @see #removeTabAt
     */
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        int newIndex = index;

        // If component already exists, remove corresponding
        // tab so that new tab gets added correctly
        // Note: we are allowing component=null because of compatibility,
        // but we really should throw an exception because much of the
        // rest of the JTabbedPane implementation isn't designed to deal
        // with null components for tabs.
        int removeIndex = indexOfComponent(component);
        if (component != null && removeIndex != -1) {
            removeTabAt(removeIndex);
            if (newIndex > removeIndex) {
                newIndex--;
            }
        }

        int selectedIndex = getSelectedIndex();

        pages.add(
            newIndex,
            new Page(this, title != null? title : "", icon, null, component, tip));


        if (component != null) {
            addImpl(component, null, -1);
            component.setVisible(false);
        } else {
            firePropertyChange("indexForNullComponent", -1, index);
        }

        if (pages.size() == 1) {
            setSelectedIndex(0);
        }

        if (selectedIndex >= newIndex) {
            setSelectedIndexImpl(selectedIndex + 1, false);
        }

        if (!haveRegistered && tip != null) {
            ToolTipManager.sharedInstance().registerComponent(this);
            haveRegistered = true;
        }

        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                    null, component);
        }
        revalidate();
        repaint();
    }

    /**
     * Adds a <code>component</code> and <code>tip</code>
     * represented by a <code>title</code> and/or <code>icon</code>,
     * either of which can be <code>null</code>.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     * 添加由<code> title </code>和/或<code>图标</code>表示的<code>组件</code>和<code>提示</code>,其中可以是<code> null </code>。
     *  <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * @param tip the tooltip to be displayed for this tab
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public void addTab(String title, Icon icon, Component component, String tip) {
        insertTab(title, icon, component, tip, pages.size());
    }

    /**
     * Adds a <code>component</code> represented by a <code>title</code>
     * and/or <code>icon</code>, either of which can be <code>null</code>.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  添加由<code> title </code>和/或<code>图标</code>表示的<code>组件</code>,其中可以是<code> null </code>。
     *  <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public void addTab(String title, Icon icon, Component component) {
        insertTab(title, icon, component, null, pages.size());
    }

    /**
     * Adds a <code>component</code> represented by a <code>title</code>
     * and no icon.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  添加由<code> title </code>表示的<code>组件</code>,没有图标。 <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public void addTab(String title, Component component) {
        insertTab(title, null, component, null, pages.size());
    }

    /**
     * Adds a <code>component</code> with a tab title defaulting to
     * the name of the component which is the result of calling
     * <code>component.getName</code>.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  添加一个<code>组件</code>,其标签标题默认为调用<code> component.getName </code>的组件名称。 <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param component the component to be displayed when this tab is clicked
     * @return the component
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public Component add(Component component) {
        if (!(component instanceof UIResource)) {
            addTab(component.getName(), component);
        } else {
            super.add(component);
        }
        return component;
    }

    /**
     * Adds a <code>component</code> with the specified tab title.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  使用指定的标签名称添加<code>组件</code>。 <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * @return the component
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public Component add(String title, Component component) {
        if (!(component instanceof UIResource)) {
            addTab(title, component);
        } else {
            super.add(title, component);
        }
        return component;
    }

    /**
     * Adds a <code>component</code> at the specified tab index with a tab
     * title defaulting to the name of the component.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  在指定的标签索引处添加一个<code>组件</code>,标签标题默认为组件名称。 <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param component the component to be displayed when this tab is clicked
     * @param index the position to insert this new tab
     * @return the component
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public Component add(Component component, int index) {
        if (!(component instanceof UIResource)) {
            // Container.add() interprets -1 as "append", so convert
            // the index appropriately to be handled by the vector
            insertTab(component.getName(), null, component, null,
                      index == -1? getTabCount() : index);
        } else {
            super.add(component, index);
        }
        return component;
    }

    /**
     * Adds a <code>component</code> to the tabbed pane.
     * If <code>constraints</code> is a <code>String</code> or an
     * <code>Icon</code>, it will be used for the tab title,
     * otherwise the component's name will be used as the tab title.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  在标签窗格中添加<code>组件</code>。
     * 如果<code>约束</code>是<code> String </code>或<code> Icon </code>,它将用于选项卡标题,否则组件的名称将用作选项卡标题。
     *  <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param component the component to be displayed when this tab is clicked
     * @param constraints the object to be displayed in the tab
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public void add(Component component, Object constraints) {
        if (!(component instanceof UIResource)) {
            if (constraints instanceof String) {
                addTab((String)constraints, component);
            } else if (constraints instanceof Icon) {
                addTab(null, (Icon)constraints, component);
            } else {
                add(component);
            }
        } else {
            super.add(component, constraints);
        }
    }

    /**
     * Adds a <code>component</code> at the specified tab index.
     * If <code>constraints</code> is a <code>String</code> or an
     * <code>Icon</code>, it will be used for the tab title,
     * otherwise the component's name will be used as the tab title.
     * Cover method for <code>insertTab</code>.
     *
     * <p>
     *  在指定的标签索引处添加<code>组件</code>。
     * 如果<code>约束</code>是<code> String </code>或<code> Icon </code>,它将用于选项卡标题,否则组件的名称将用作选项卡标题。
     *  <code> insertTab </code>的覆盖方法。
     * 
     * 
     * @param component the component to be displayed when this tab is clicked
     * @param constraints the object to be displayed in the tab
     * @param index the position to insert this new tab
     *
     * @see #insertTab
     * @see #removeTabAt
     */
    public void add(Component component, Object constraints, int index) {
        if (!(component instanceof UIResource)) {

            Icon icon = constraints instanceof Icon? (Icon)constraints : null;
            String title = constraints instanceof String? (String)constraints : null;
            // Container.add() interprets -1 as "append", so convert
            // the index appropriately to be handled by the vector
            insertTab(title, icon, component, null, index == -1? getTabCount() : index);
        } else {
            super.add(component, constraints, index);
        }
    }

    /**
     * Removes the tab at <code>index</code>.
     * After the component associated with <code>index</code> is removed,
     * its visibility is reset to true to ensure it will be visible
     * if added to other containers.
     * <p>
     * 删除<code> index </code>上的选项卡。与<code> index </code>关联的组件被删除后,其可见性将重置为true,以确保它添加到其他容器时可见。
     * 
     * 
     * @param index the index of the tab to be removed
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #addTab
     * @see #insertTab
     */
    public void removeTabAt(int index) {
        checkIndex(index);

        Component component = getComponentAt(index);
        boolean shouldChangeFocus = false;
        int selected = getSelectedIndex();
        String oldName = null;

        /* if we're about to remove the visible component */
        if (component == visComp) {
            shouldChangeFocus = (SwingUtilities.findFocusOwner(visComp) != null);
            visComp = null;
        }

        if (accessibleContext != null) {
            /* if we're removing the selected page */
            if (index == selected) {
                /* fire an accessible notification that it's unselected */
                pages.get(index).firePropertyChange(
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    AccessibleState.SELECTED, null);

                oldName = accessibleContext.getAccessibleName();
            }

            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                    component, null);
        }

        // Force the tabComponent to be cleaned up.
        setTabComponentAt(index, null);
        pages.remove(index);

        // NOTE 4/15/2002 (joutwate):
        // This fix is implemented using client properties since there is
        // currently no IndexPropertyChangeEvent.  Once
        // IndexPropertyChangeEvents have been added this code should be
        // modified to use it.
        putClientProperty("__index_to_remove__", Integer.valueOf(index));

        /* if the selected tab is after the removal */
        if (selected > index) {
            setSelectedIndexImpl(selected - 1, false);

        /* if the selected tab is the last tab */
        } else if (selected >= getTabCount()) {
            setSelectedIndexImpl(selected - 1, false);
            Page newSelected = (selected != 0)
                ? pages.get(selected - 1)
                : null;

            changeAccessibleSelection(null, oldName, newSelected);

        /* selected index hasn't changed, but the associated tab has */
        } else if (index == selected) {
            fireStateChanged();
            changeAccessibleSelection(null, oldName, pages.get(index));
        }

        // We can't assume the tab indices correspond to the
        // container's children array indices, so make sure we
        // remove the correct child!
        if (component != null) {
            Component components[] = getComponents();
            for (int i = components.length; --i >= 0; ) {
                if (components[i] == component) {
                    super.remove(i);
                    component.setVisible(true);
                    break;
                }
            }
        }

        if (shouldChangeFocus) {
            SwingUtilities2.tabbedPaneChangeFocusTo(getSelectedComponent());
        }

        revalidate();
        repaint();
    }

    /**
     * Removes the specified <code>Component</code> from the
     * <code>JTabbedPane</code>. The method does nothing
     * if the <code>component</code> is null.
     *
     * <p>
     *  从<code> JTabbedPane </code>中删除指定的<code> Component </code>。如果<code>组件</code>为空,该方法不执行任何操作。
     * 
     * 
     * @param component the component to remove from the tabbedpane
     * @see #addTab
     * @see #removeTabAt
     */
    public void remove(Component component) {
        int index = indexOfComponent(component);
        if (index != -1) {
            removeTabAt(index);
        } else {
            // Container#remove(comp) invokes Container#remove(int)
            // so make sure JTabbedPane#remove(int) isn't called here
            Component children[] = getComponents();
            for (int i=0; i < children.length; i++) {
                if (component == children[i]) {
                    super.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Removes the tab and component which corresponds to the specified index.
     *
     * <p>
     *  删除与指定索引对应的选项卡和组件。
     * 
     * 
     * @param index the index of the component to remove from the
     *          <code>tabbedpane</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     * @see #addTab
     * @see #removeTabAt
     */
    public void remove(int index) {
        removeTabAt(index);
    }

    /**
     * Removes all the tabs and their corresponding components
     * from the <code>tabbedpane</code>.
     *
     * <p>
     *  从<code>选项卡式窗格</code>中删除所有选项卡及其对应的组件。
     * 
     * 
     * @see #addTab
     * @see #removeTabAt
     */
    public void removeAll() {
        setSelectedIndexImpl(-1, true);

        int tabCount = getTabCount();
        // We invoke removeTabAt for each tab, otherwise we may end up
        // removing Components added by the UI.
        while (tabCount-- > 0) {
            removeTabAt(tabCount);
        }
    }

    /**
     * Returns the number of tabs in this <code>tabbedpane</code>.
     *
     * <p>
     *  返回此<code>选项卡式窗格</code>中的标签数量。
     * 
     * 
     * @return an integer specifying the number of tabbed pages
     */
    public int getTabCount() {
        return pages.size();
    }

    /**
     * Returns the number of tab runs currently used to display
     * the tabs.
     * <p>
     *  返回当前用于显示选项卡的选项卡运行数。
     * 
     * 
     * @return an integer giving the number of rows if the
     *          <code>tabPlacement</code>
     *          is <code>TOP</code> or <code>BOTTOM</code>
     *          and the number of columns if
     *          <code>tabPlacement</code>
     *          is <code>LEFT</code> or <code>RIGHT</code>,
     *          or 0 if there is no UI set on this <code>tabbedpane</code>
     */
    public int getTabRunCount() {
        if (ui != null) {
            return ((TabbedPaneUI)ui).getTabRunCount(this);
        }
        return 0;
    }


// Getters for the Pages

    /**
     * Returns the tab title at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>的标签标题。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the title at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     * @see #setTitleAt
     */
    public String getTitleAt(int index) {
        return pages.get(index).title;
    }

    /**
     * Returns the tab icon at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>的标签图标。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the icon at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setIconAt
     */
    public Icon getIconAt(int index) {
        return pages.get(index).icon;
    }

    /**
     * Returns the tab disabled icon at <code>index</code>.
     * If the tab disabled icon doesn't exist at <code>index</code>
     * this will forward the call to the look and feel to construct
     * an appropriate disabled Icon from the corresponding enabled
     * Icon. Some look and feels might not render the disabled Icon,
     * in which case it won't be created.
     *
     * <p>
     *  返回<code> index </code>处的禁用选项卡图标。如果<code> index </code>处不存在选项卡禁用图标,则会将调用转发到外观和感觉,以从相应启用的图标构建适当的禁用图标。
     * 一些外观和感觉可能不会呈现禁用的图标,在这种情况下,它不会被创建。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the icon at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setDisabledIconAt
     */
    public Icon getDisabledIconAt(int index) {
        Page page = pages.get(index);
        if (page.disabledIcon == null) {
            page.disabledIcon = UIManager.getLookAndFeel().getDisabledIcon(this, page.icon);
        }
        return page.disabledIcon;
    }

    /**
     * Returns the tab tooltip text at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>处的制表符工具提示文本。
     * 
     * 
     * @param index  the index of the item being queried
     * @return a string containing the tool tip text at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setToolTipTextAt
     * @since 1.3
     */
    public String getToolTipTextAt(int index) {
        return pages.get(index).tip;
    }

    /**
     * Returns the tab background color at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>的标签背景颜色。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the <code>Color</code> of the tab background at
     *          <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setBackgroundAt
     */
    public Color getBackgroundAt(int index) {
        return pages.get(index).getBackground();
    }

    /**
     * Returns the tab foreground color at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>处的标签前景颜色。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the <code>Color</code> of the tab foreground at
     *          <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setForegroundAt
     */
    public Color getForegroundAt(int index) {
        return pages.get(index).getForeground();
    }

    /**
     * Returns whether or not the tab at <code>index</code> is
     * currently enabled.
     *
     * <p>
     *  返回<code> index </code>的标签是否当前已启用。
     * 
     * 
     * @param index  the index of the item being queried
     * @return true if the tab at <code>index</code> is enabled;
     *          false otherwise
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setEnabledAt
     */
    public boolean isEnabledAt(int index) {
        return pages.get(index).isEnabled();
    }

    /**
     * Returns the component at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>的组件。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the <code>Component</code> at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setComponentAt
     */
    public Component getComponentAt(int index) {
        return pages.get(index).component;
    }

    /**
     * Returns the keyboard mnemonic for accessing the specified tab.
     * The mnemonic is the key which when combined with the look and feel's
     * mouseless modifier (usually Alt) will activate the specified
     * tab.
     *
     * <p>
     * 返回用于访问指定标签的键盘助记符。助记符是当与外观和感觉的无调节修饰符(通常为Alt)组合时将激活指定的选项卡的键。
     * 
     * 
     * @since 1.4
     * @param tabIndex the index of the tab that the mnemonic refers to
     * @return the key code which represents the mnemonic;
     *         -1 if a mnemonic is not specified for the tab
     * @exception IndexOutOfBoundsException if index is out of range
     *            (<code>tabIndex</code> &lt; 0 ||
     *              <code>tabIndex</code> &gt;= tab count)
     * @see #setDisplayedMnemonicIndexAt(int,int)
     * @see #setMnemonicAt(int,int)
     */
    public int getMnemonicAt(int tabIndex) {
        checkIndex(tabIndex);

        Page page = pages.get(tabIndex);
        return page.getMnemonic();
    }

    /**
     * Returns the character, as an index, that the look and feel should
     * provide decoration for as representing the mnemonic character.
     *
     * <p>
     *  返回作为索引的字符,外观应该提供装饰作为代表助记符的字符。
     * 
     * 
     * @since 1.4
     * @param tabIndex the index of the tab that the mnemonic refers to
     * @return index representing mnemonic character if one exists;
     *    otherwise returns -1
     * @exception IndexOutOfBoundsException if index is out of range
     *            (<code>tabIndex</code> &lt; 0 ||
     *              <code>tabIndex</code> &gt;= tab count)
     * @see #setDisplayedMnemonicIndexAt(int,int)
     * @see #setMnemonicAt(int,int)
     */
    public int getDisplayedMnemonicIndexAt(int tabIndex) {
        checkIndex(tabIndex);

        Page page = pages.get(tabIndex);
        return page.getDisplayedMnemonicIndex();
    }

    /**
     * Returns the tab bounds at <code>index</code>.  If the tab at
     * this index is not currently visible in the UI, then returns
     * <code>null</code>.
     * If there is no UI set on this <code>tabbedpane</code>,
     * then returns <code>null</code>.
     *
     * <p>
     *  返回<code> index </code>处的制表符边界。如果此索引处的标签当前在UI中不可见,则返回<code> null </code>。
     * 如果在<code>标签栏</code>上没有设置UI,则返回<code> null </code>。
     * 
     * 
     * @param index the index to be queried
     * @return a <code>Rectangle</code> containing the tab bounds at
     *          <code>index</code>, or <code>null</code> if tab at
     *          <code>index</code> is not currently visible in the UI,
     *          or if there is no UI set on this <code>tabbedpane</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     */
    public Rectangle getBoundsAt(int index) {
        checkIndex(index);
        if (ui != null) {
            return ((TabbedPaneUI)ui).getTabBounds(this, index);
        }
        return null;
    }


// Setters for the Pages

    /**
     * Sets the title at <code>index</code> to <code>title</code> which
     * can be <code>null</code>.
     * The title is not shown if a tab component for this tab was specified.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     *  将<code> index </code>的标题设置为<code> title </code>,可以是<code> null </code>。如果指定了此选项卡的选项卡组件,则不会显示标题。
     * 如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index where the title should be set
     * @param title the title to be displayed in the tab
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getTitleAt
     * @see #setTabComponentAt
     * @beaninfo
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The title at the specified tab index.
     */
    public void setTitleAt(int index, String title) {
        Page page = pages.get(index);
        String oldTitle =page.title;
        page.title = title;

        if (oldTitle != title) {
            firePropertyChange("indexForTitle", -1, index);
        }
        page.updateDisplayedMnemonicIndex();
        if ((oldTitle != title) && (accessibleContext != null)) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                    oldTitle, title);
        }
        if (title == null || oldTitle == null ||
            !title.equals(oldTitle)) {
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the icon at <code>index</code> to <code>icon</code> which can be
     * <code>null</code>. This does not set disabled icon at <code>icon</code>.
     * If the new Icon is different than the current Icon and disabled icon
     * is not explicitly set, the LookAndFeel will be asked to generate a disabled
     * Icon. To explicitly set disabled icon, use <code>setDisableIconAt()</code>.
     * The icon is not shown if a tab component for this tab was specified.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     *  将<code> index </code>上的图标设置为<code>图标</code>,可以是<code> null </code>。这不会在<code>图标</code>上设置禁用图标。
     * 如果新图标与当前图标不同,并且未显式设置禁用图标,LookAndFeel将要求生成禁用图标。要显式设置禁用图标,请使用<code> setDisableIconAt()</code>。
     * 如果指定了此选项卡的选项卡组件,则不会显示该图标。如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index where the icon should be set
     * @param icon the icon to be displayed in the tab
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setDisabledIconAt
     * @see #getIconAt
     * @see #getDisabledIconAt
     * @see #setTabComponentAt
     * @beaninfo
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The icon at the specified tab index.
     */
    public void setIconAt(int index, Icon icon) {
        Page page = pages.get(index);
        Icon oldIcon = page.icon;
        if (icon != oldIcon) {
            page.icon = icon;

            /* If the default icon has really changed and we had
             * generated the disabled icon for this page, then
             * clear the disabledIcon field of the page.
             * <p>
             *  生成此页面的禁用图标,然后清除页面的disabledIcon字段。
             * 
             */
            if (page.disabledIcon instanceof UIResource) {
                page.disabledIcon = null;
            }

            // Fire the accessibility Visible data change
            if (accessibleContext != null) {
                accessibleContext.firePropertyChange(
                        AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                        oldIcon, icon);
            }
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the disabled icon at <code>index</code> to <code>icon</code>
     * which can be <code>null</code>.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     * 将<code> index </code>的禁用图标设置为<code>图标</code>,可以是<code> null </code>。如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index where the disabled icon should be set
     * @param disabledIcon the icon to be displayed in the tab when disabled
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getDisabledIconAt
     * @beaninfo
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The disabled icon at the specified tab index.
     */
    public void setDisabledIconAt(int index, Icon disabledIcon) {
        Icon oldIcon = pages.get(index).disabledIcon;
        pages.get(index).disabledIcon = disabledIcon;
        if (disabledIcon != oldIcon && !isEnabledAt(index)) {
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the tooltip text at <code>index</code> to <code>toolTipText</code>
     * which can be <code>null</code>.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     *  将<code> index </code>处的工具提示文本设置为<code> toolTipText </code>,可以是<code> null </code>。
     * 如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index where the tooltip text should be set
     * @param toolTipText the tooltip text to be displayed for the tab
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getToolTipTextAt
     * @beaninfo
     *    preferred: true
     *  description: The tooltip text at the specified tab index.
     * @since 1.3
     */
    public void setToolTipTextAt(int index, String toolTipText) {
        String oldToolTipText = pages.get(index).tip;
        pages.get(index).tip = toolTipText;

        if ((oldToolTipText != toolTipText) && (accessibleContext != null)) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                    oldToolTipText, toolTipText);
        }
        if (!haveRegistered && toolTipText != null) {
            ToolTipManager.sharedInstance().registerComponent(this);
            haveRegistered = true;
        }
    }

    /**
     * Sets the background color at <code>index</code> to
     * <code>background</code>
     * which can be <code>null</code>, in which case the tab's background color
     * will default to the background color of the <code>tabbedpane</code>.
     * An internal exception is raised if there is no tab at that index.
     * <p>
     * It is up to the look and feel to honor this property, some may
     * choose to ignore it.
     *
     * <p>
     *  将<code> index </code>的背景颜色设置为<code>背景</code>,可以是<code> null </code>,在这种情况下,标签的背景颜色将默认为<代码> tabbedpan
     * e </code>。
     * 如果该索引处没有制表符,则会引发内部异常。
     * <p>
     *  它是由外观和感觉来尊重这个属性,有些人可能会选择忽略它。
     * 
     * 
     * @param index the tab index where the background should be set
     * @param background the color to be displayed in the tab's background
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getBackgroundAt
     * @beaninfo
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The background color at the specified tab index.
     */
    public void setBackgroundAt(int index, Color background) {
        Color oldBg = pages.get(index).background;
        pages.get(index).setBackground(background);
        if (background == null || oldBg == null ||
            !background.equals(oldBg)) {
            Rectangle tabBounds = getBoundsAt(index);
            if (tabBounds != null) {
                repaint(tabBounds);
            }
        }
    }

    /**
     * Sets the foreground color at <code>index</code> to
     * <code>foreground</code> which can be
     * <code>null</code>, in which case the tab's foreground color
     * will default to the foreground color of this <code>tabbedpane</code>.
     * An internal exception is raised if there is no tab at that index.
     * <p>
     * It is up to the look and feel to honor this property, some may
     * choose to ignore it.
     *
     * <p>
     *  将<code> index </code>的前景颜色设置为<code> foreground </code>,可以是<code> null </code>,在这种情况下,标签的前景颜色将默认为<代码>
     *  tabbedpane </code>。
     * 如果该索引处没有制表符,则会引发内部异常。
     * <p>
     *  它是由外观和感觉来尊重这个属性,有些人可能会选择忽略它。
     * 
     * 
     * @param index the tab index where the foreground should be set
     * @param foreground the color to be displayed as the tab's foreground
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getForegroundAt
     * @beaninfo
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: The foreground color at the specified tab index.
     */
    public void setForegroundAt(int index, Color foreground) {
        Color oldFg = pages.get(index).foreground;
        pages.get(index).setForeground(foreground);
        if (foreground == null || oldFg == null ||
            !foreground.equals(oldFg)) {
            Rectangle tabBounds = getBoundsAt(index);
            if (tabBounds != null) {
                repaint(tabBounds);
            }
        }
    }

    /**
     * Sets whether or not the tab at <code>index</code> is enabled.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     *  设置是否启用<code> index </code>处的选项卡。如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index which should be enabled/disabled
     * @param enabled whether or not the tab should be enabled
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #isEnabledAt
     */
    public void setEnabledAt(int index, boolean enabled) {
        boolean oldEnabled = pages.get(index).isEnabled();
        pages.get(index).setEnabled(enabled);
        if (enabled != oldEnabled) {
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the component at <code>index</code> to <code>component</code>.
     * An internal exception is raised if there is no tab at that index.
     *
     * <p>
     *  将<code> index </code>处的组件设置为<code> component </code>。如果该索引处没有制表符,则会引发内部异常。
     * 
     * 
     * @param index the tab index where this component is being placed
     * @param component the component for the tab
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #getComponentAt
     * @beaninfo
     *    attribute: visualUpdate true
     *  description: The component at the specified tab index.
     */
    public void setComponentAt(int index, Component component) {
        Page page = pages.get(index);
        if (component != page.component) {
            boolean shouldChangeFocus = false;

            if (page.component != null) {
                shouldChangeFocus =
                    (SwingUtilities.findFocusOwner(page.component) != null);

                // REMIND(aim): this is really silly;
                // why not if (page.component.getParent() == this) remove(component)
                synchronized(getTreeLock()) {
                    int count = getComponentCount();
                    Component children[] = getComponents();
                    for (int i = 0; i < count; i++) {
                        if (children[i] == page.component) {
                            super.remove(i);
                        }
                    }
                }
            }

            page.component = component;
            boolean selectedPage = (getSelectedIndex() == index);

            if (selectedPage) {
                this.visComp = component;
            }

            if (component != null) {
                component.setVisible(selectedPage);
                addImpl(component, null, -1);

                if (shouldChangeFocus) {
                    SwingUtilities2.tabbedPaneChangeFocusTo(component);
                }
            } else {
                repaint();
            }

            revalidate();
        }
    }

    /**
     * Provides a hint to the look and feel as to which character in the
     * text should be decorated to represent the mnemonic. Not all look and
     * feels may support this. A value of -1 indicates either there is
     * no mnemonic for this tab, or you do not wish the mnemonic to be
     * displayed for this tab.
     * <p>
     * The value of this is updated as the properties relating to the
     * mnemonic change (such as the mnemonic itself, the text...).
     * You should only ever have to call this if
     * you do not wish the default character to be underlined. For example, if
     * the text at tab index 3 was 'Apple Price', with a mnemonic of 'p',
     * and you wanted the 'P'
     * to be decorated, as 'Apple <u>P</u>rice', you would have to invoke
     * <code>setDisplayedMnemonicIndex(3, 6)</code> after invoking
     * <code>setMnemonicAt(3, KeyEvent.VK_P)</code>.
     * <p>Note that it is the programmer's responsibility to ensure
     * that each tab has a unique mnemonic or unpredictable results may
     * occur.
     *
     * <p>
     * 提供关于文本中应当装饰哪个字符以表示助记符的外观和感觉的提示。不是所有的外观和感觉可能支持这一点。值-1表示此选项卡没有助记符,或者您不希望为此选项卡显示助记符。
     * <p>
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。
     * 例如,如果标签索引3处的文本是"Apple Price",助记符为"p",并且您希望将"P"装饰为"Apple <u> P </u>必须在调用<code> setMnemonicAt(3,KeyEven
     * t.VK_P)</code>之后调用<code> setDisplayedMnemonicIndex(3,6)</code>。
     *  此值将更新为与助记符更改相关的属性(例如助记符本身,文本...)。如果你不希望默认字符被加下划线,你应该只需要调用这个。 <p>请注意,程序员有责任确保每个标签都有唯一的助记符或不可预测的结果。
     * 
     * 
     * @since 1.4
     * @param tabIndex the index of the tab that the mnemonic refers to
     * @param mnemonicIndex index into the <code>String</code> to underline
     * @exception IndexOutOfBoundsException if <code>tabIndex</code> is
     *            out of range ({@code tabIndex < 0 || tabIndex >= tab
     *            count})
     * @exception IllegalArgumentException will be thrown if
     *            <code>mnemonicIndex</code> is &gt;= length of the tab
     *            title , or &lt; -1
     * @see #setMnemonicAt(int,int)
     * @see #getDisplayedMnemonicIndexAt(int)
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: the index into the String to draw the keyboard character
     *               mnemonic at
     */
    public void setDisplayedMnemonicIndexAt(int tabIndex, int mnemonicIndex) {
        checkIndex(tabIndex);

        Page page = pages.get(tabIndex);

        page.setDisplayedMnemonicIndex(mnemonicIndex);
    }

    /**
     * Sets the keyboard mnemonic for accessing the specified tab.
     * The mnemonic is the key which when combined with the look and feel's
     * mouseless modifier (usually Alt) will activate the specified
     * tab.
     * <p>
     * A mnemonic must correspond to a single key on the keyboard
     * and should be specified using one of the <code>VK_XXX</code>
     * keycodes defined in <code>java.awt.event.KeyEvent</code>
     * or one of the extended keycodes obtained through
     * <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code>.
     * Mnemonics are case-insensitive, therefore a key event
     * with the corresponding keycode would cause the button to be
     * activated whether or not the Shift modifier was pressed.
     * <p>
     * This will update the displayed mnemonic property for the specified
     * tab.
     *
     * <p>
     *  设置用于访问指定标签的键盘助记符。助记符是当与外观和感觉的无调节修饰符(通常为Alt)组合时将激活指定的选项卡的键。
     * <p>
     * 助记符必须对应于键盘上的单个键,并且应使用<code> java.awt.event.KeyEvent </code>中定义的<code> VK_XXX </code>键码之一或扩展键码之一通过<code>
     *  java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>获取。
     * 助记符不区分大小写,因此具有相应键码的键事件将导致按钮被激活,无论是否按下Shift键。
     * <p>
     *  这将更新指定选项卡的显示助记符属性。
     * 
     * 
     * @since 1.4
     * @param tabIndex the index of the tab that the mnemonic refers to
     * @param mnemonic the key code which represents the mnemonic
     * @exception IndexOutOfBoundsException if <code>tabIndex</code> is out
     *            of range ({@code tabIndex < 0 || tabIndex >= tab count})
     * @see #getMnemonicAt(int)
     * @see #setDisplayedMnemonicIndexAt(int,int)
     *
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The keyboard mnenmonic, as a KeyEvent VK constant,
     *               for the specified tab
     */
    public void setMnemonicAt(int tabIndex, int mnemonic) {
        checkIndex(tabIndex);

        Page page = pages.get(tabIndex);
        page.setMnemonic(mnemonic);

        firePropertyChange("mnemonicAt", null, null);
    }

// end of Page setters

    /**
     * Returns the first tab index with a given <code>title</code>,  or
     * -1 if no tab has this title.
     *
     * <p>
     *  返回带有给定<code> title </code>的第一个制表符索引,如果没有标题具有此标题,则返回-1。
     * 
     * 
     * @param title the title for the tab
     * @return the first tab index which matches <code>title</code>, or
     *          -1 if no tab has this title
     */
    public int indexOfTab(String title) {
        for(int i = 0; i < getTabCount(); i++) {
            if (getTitleAt(i).equals(title == null? "" : title)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the first tab index with a given <code>icon</code>,
     * or -1 if no tab has this icon.
     *
     * <p>
     *  返回带有给定<code>图标</code>的第一个标签索引,如果没有标签有此图标,则返回-1。
     * 
     * 
     * @param icon the icon for the tab
     * @return the first tab index which matches <code>icon</code>,
     *          or -1 if no tab has this icon
     */
    public int indexOfTab(Icon icon) {
        for(int i = 0; i < getTabCount(); i++) {
            Icon tabIcon = getIconAt(i);
            if ((tabIcon != null && tabIcon.equals(icon)) ||
                (tabIcon == null && tabIcon == icon)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the tab for the specified component.
     * Returns -1 if there is no tab for this component.
     *
     * <p>
     *  返回指定组件的选项卡的索引。如果此组件没有选项卡,则返回-1。
     * 
     * 
     * @param component the component for the tab
     * @return the first tab which matches this component, or -1
     *          if there is no tab for this component
     */
    public int indexOfComponent(Component component) {
        for(int i = 0; i < getTabCount(); i++) {
            Component c = getComponentAt(i);
            if ((c != null && c.equals(component)) ||
                (c == null && c == component)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the tab index corresponding to the tab whose bounds
     * intersect the specified location.  Returns -1 if no tab
     * intersects the location.
     *
     * <p>
     *  返回与其边界与指定位置相交的选项卡对应的选项卡索引。如果没有标签与位置相交,则返回-1。
     * 
     * 
     * @param x the x location relative to this tabbedpane
     * @param y the y location relative to this tabbedpane
     * @return the tab index which intersects the location, or
     *         -1 if no tab intersects the location
     * @since 1.4
     */
    public int indexAtLocation(int x, int y) {
        if (ui != null) {
            return ((TabbedPaneUI)ui).tabForCoordinate(this, x, y);
        }
        return -1;
    }


    /**
     * Returns the tooltip text for the component determined by the
     * mouse event location.
     *
     * <p>
     *  返回由鼠标事件位置确定的组件的工具提示文本。
     * 
     * 
     * @param event  the <code>MouseEvent</code> that tells where the
     *          cursor is lingering
     * @return the <code>String</code> containing the tooltip text
     */
    public String getToolTipText(MouseEvent event) {
        if (ui != null) {
            int index = ((TabbedPaneUI)ui).tabForCoordinate(this, event.getX(), event.getY());

            if (index != -1) {
                return pages.get(index).tip;
            }
        }
        return super.getToolTipText(event);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= pages.size()) {
            throw new IndexOutOfBoundsException("Index: "+index+", Tab count: "+pages.size());
        }
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
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }

    /* Called from the <code>JComponent</code>'s
     * <code>EnableSerializationFocusListener</code> to
     * do any Swing-specific pre-serialization configuration.
     * <p>
     *  <code> EnableSerializationFocusListener </code>可以执行任何Swing特定的预序列化配置。
     * 
     */
    void compWriteObjectNotify() {
        super.compWriteObjectNotify();
        // If ToolTipText != null, then the tooltip has already been
        // unregistered by JComponent.compWriteObjectNotify()
        if (getToolTipText() == null && haveRegistered) {
            ToolTipManager.sharedInstance().unregisterComponent(this);
        }
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
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        if ((ui != null) && (getUIClassID().equals(uiClassID))) {
            ui.installUI(this);
        }
        // If ToolTipText != null, then the tooltip has already been
        // registered by JComponent.readObject()
        if (getToolTipText() == null && haveRegistered) {
            ToolTipManager.sharedInstance().registerComponent(this);
        }
    }


    /**
     * Returns a string representation of this <code>JTabbedPane</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     * 返回此<code> JTabbedPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JTabbedPane.
     */
    protected String paramString() {
        String tabPlacementString;
        if (tabPlacement == TOP) {
            tabPlacementString = "TOP";
        } else if (tabPlacement == BOTTOM) {
            tabPlacementString = "BOTTOM";
        } else if (tabPlacement == LEFT) {
            tabPlacementString = "LEFT";
        } else if (tabPlacement == RIGHT) {
            tabPlacementString = "RIGHT";
        } else tabPlacementString = "";
        String haveRegisteredString = (haveRegistered ?
                                       "true" : "false");

        return super.paramString() +
        ",haveRegistered=" + haveRegisteredString +
        ",tabPlacement=" + tabPlacementString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JTabbedPane.
     * For tabbed panes, the AccessibleContext takes the form of an
     * AccessibleJTabbedPane.
     * A new AccessibleJTabbedPane instance is created if necessary.
     *
     * <p>
     *  获取与此JTabbedPane关联的AccessibleContext。对于选项卡式窗格,AccessibleContext采用AccessibleJTabbedPane的形式。
     * 如果需要,将创建一个新的AccessibleJTabbedPane实例。
     * 
     * 
     * @return an AccessibleJTabbedPane that serves as the
     *         AccessibleContext of this JTabbedPane
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJTabbedPane();

            // initialize AccessibleContext for the existing pages
            int count = getTabCount();
            for (int i = 0; i < count; i++) {
                pages.get(i).initAccessibleContext();
            }
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JTabbedPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to tabbed pane user-interface
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
     *  此类实现了对<code> JTabbedPane </code>类的辅助功能支持。它提供了适用于标签式窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJTabbedPane extends AccessibleJComponent
        implements AccessibleSelection, ChangeListener {

        /**
         * Returns the accessible name of this object, or {@code null} if
         * there is no accessible name.
         *
         * <p>
         *  返回此对象的可访问名称,如果没有可访问的名称,则返回{@code null}。
         * 
         * 
         * @return the accessible name of this object, nor {@code null}.
         * @since 1.6
         */
        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            }

            String cp = (String)getClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY);

            if (cp != null) {
                return cp;
            }

            int index = getSelectedIndex();

            if (index >= 0) {
                return pages.get(index).getAccessibleName();
            }

            return super.getAccessibleName();
        }

        /**
         *  Constructs an AccessibleJTabbedPane
         * <p>
         *  构造一个AccessableJTabbedPane
         * 
         */
        public AccessibleJTabbedPane() {
            super();
            JTabbedPane.this.model.addChangeListener(this);
        }

        public void stateChanged(ChangeEvent e) {
            Object o = e.getSource();
            firePropertyChange(AccessibleContext.ACCESSIBLE_SELECTION_PROPERTY,
                               null, o);
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of
         *          the object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PAGE_TAB_LIST;
        }

        /**
         * Returns the number of accessible children in the object.
         *
         * <p>
         *  返回对象中可访问的子项数。
         * 
         * 
         * @return the number of accessible children in the object.
         */
        public int getAccessibleChildrenCount() {
            return getTabCount();
        }

        /**
         * Return the specified Accessible child of the object.
         *
         * <p>
         *  返回对象的指定Accessible子项。
         * 
         * 
         * @param i zero-based index of child
         * @return the Accessible child of the object
         * @exception IllegalArgumentException if index is out of bounds
         */
        public Accessible getAccessibleChild(int i) {
            if (i < 0 || i >= getTabCount()) {
                return null;
            }
            return pages.get(i);
        }

        /**
         * Gets the <code>AccessibleSelection</code> associated with
         * this object.  In the implementation of the Java
         * Accessibility API for this class,
         * returns this object, which is responsible for implementing the
         * <code>AccessibleSelection</code> interface on behalf of itself.
         *
         * <p>
         * 获取与此对象关联的<code> AccessibleSelection </code>。
         * 在为该类实现Java辅助功能API时,返回此对象,该对象负责代表自身实现<code> AccessibleSelection </code>接口。
         * 
         * 
         * @return this object
         */
        public AccessibleSelection getAccessibleSelection() {
           return this;
        }

        /**
         * Returns the <code>Accessible</code> child contained at
         * the local coordinate <code>Point</code>, if one exists.
         * Otherwise returns the currently selected tab.
         *
         * <p>
         *  返回包含在本地坐标<code> Point </code>(如果存在)中的<code> Accessible </code>子代。否则返回当前选择的选项卡。
         * 
         * 
         * @return the <code>Accessible</code> at the specified
         *    location, if it exists
         */
        public Accessible getAccessibleAt(Point p) {
            int tab = ((TabbedPaneUI) ui).tabForCoordinate(JTabbedPane.this,
                                                           p.x, p.y);
            if (tab == -1) {
                tab = getSelectedIndex();
            }
            return getAccessibleChild(tab);
        }

        public int getAccessibleSelectionCount() {
            return 1;
        }

        public Accessible getAccessibleSelection(int i) {
            int index = getSelectedIndex();
            if (index == -1) {
                return null;
            }
            return pages.get(index);
        }

        public boolean isAccessibleChildSelected(int i) {
            return (i == getSelectedIndex());
        }

        public void addAccessibleSelection(int i) {
           setSelectedIndex(i);
        }

        public void removeAccessibleSelection(int i) {
           // can't do
        }

        public void clearAccessibleSelection() {
           // can't do
        }

        public void selectAllAccessibleSelection() {
           // can't do
        }
    }

    private class Page extends AccessibleContext
        implements Serializable, Accessible, AccessibleComponent {
        String title;
        Color background;
        Color foreground;
        Icon icon;
        Icon disabledIcon;
        JTabbedPane parent;
        Component component;
        String tip;
        boolean enabled = true;
        boolean needsUIUpdate;
        int mnemonic = -1;
        int mnemonicIndex = -1;
        Component tabComponent;

        Page(JTabbedPane parent,
             String title, Icon icon, Icon disabledIcon, Component component, String tip) {
            this.title = title;
            this.icon = icon;
            this.disabledIcon = disabledIcon;
            this.parent = parent;
            this.setAccessibleParent(parent);
            this.component = component;
            this.tip = tip;

            initAccessibleContext();
        }

        /*
         * initializes the AccessibleContext for the page
         * <p>
         *  初始化页面的AccessibleContext
         * 
         */
        void initAccessibleContext() {
            if (JTabbedPane.this.accessibleContext != null &&
                component instanceof Accessible) {
                /*
                 * Do initialization if the AccessibleJTabbedPane
                 * has been instantiated. We do not want to load
                 * Accessibility classes unnecessarily.
                 * <p>
                 *  如果AccessibleJTabbedPane已实例化,请进行初始化。我们不想不必要地加载可访问性类。
                 * 
                 */
                AccessibleContext ac;
                ac = component.getAccessibleContext();
                if (ac != null) {
                    ac.setAccessibleParent(this);
                }
            }
        }

        void setMnemonic(int mnemonic) {
            this.mnemonic = mnemonic;
            updateDisplayedMnemonicIndex();
        }

        int getMnemonic() {
            return mnemonic;
        }

        /*
         * Sets the page displayed mnemonic index
         * <p>
         *  设置页面显示助记索引
         * 
         */
        void setDisplayedMnemonicIndex(int mnemonicIndex) {
            if (this.mnemonicIndex != mnemonicIndex) {
                if (mnemonicIndex != -1 && (title == null ||
                        mnemonicIndex < 0 ||
                        mnemonicIndex >= title.length())) {
                    throw new IllegalArgumentException(
                                "Invalid mnemonic index: " + mnemonicIndex);
                }
                this.mnemonicIndex = mnemonicIndex;
                JTabbedPane.this.firePropertyChange("displayedMnemonicIndexAt",
                                                    null, null);
            }
        }

        /*
         * Returns the page displayed mnemonic index
         * <p>
         *  返回页面显示的助记符索引
         * 
         */
        int getDisplayedMnemonicIndex() {
            return this.mnemonicIndex;
        }

        void updateDisplayedMnemonicIndex() {
            setDisplayedMnemonicIndex(
                SwingUtilities.findDisplayedMnemonicIndex(title, mnemonic));
        }

        /////////////////
        // Accessibility support
        ////////////////

        public AccessibleContext getAccessibleContext() {
            return this;
        }


        // AccessibleContext methods

        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            } else if (title != null) {
                return title;
            }
            return null;
        }

        public String getAccessibleDescription() {
            if (accessibleDescription != null) {
                return accessibleDescription;
            } else if (tip != null) {
                return tip;
            }
            return null;
        }

        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PAGE_TAB;
        }

        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states;
            states = parent.getAccessibleContext().getAccessibleStateSet();
            states.add(AccessibleState.SELECTABLE);
            int i = parent.indexOfTab(title);
            if (i == parent.getSelectedIndex()) {
                states.add(AccessibleState.SELECTED);
            }
            return states;
        }

        public int getAccessibleIndexInParent() {
            return parent.indexOfTab(title);
        }

        public int getAccessibleChildrenCount() {
            if (component instanceof Accessible) {
                return 1;
            } else {
                return 0;
            }
        }

        public Accessible getAccessibleChild(int i) {
            if (component instanceof Accessible) {
                return (Accessible) component;
            } else {
                return null;
            }
        }

        public Locale getLocale() {
            return parent.getLocale();
        }

        public AccessibleComponent getAccessibleComponent() {
            return this;
        }


        // AccessibleComponent methods

        public Color getBackground() {
            return background != null? background : parent.getBackground();
        }

        public void setBackground(Color c) {
            background = c;
        }

        public Color getForeground() {
            return foreground != null? foreground : parent.getForeground();
        }

        public void setForeground(Color c) {
            foreground = c;
        }

        public Cursor getCursor() {
            return parent.getCursor();
        }

        public void setCursor(Cursor c) {
            parent.setCursor(c);
        }

        public Font getFont() {
            return parent.getFont();
        }

        public void setFont(Font f) {
            parent.setFont(f);
        }

        public FontMetrics getFontMetrics(Font f) {
            return parent.getFontMetrics(f);
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean b) {
            enabled = b;
        }

        public boolean isVisible() {
            return parent.isVisible();
        }

        public void setVisible(boolean b) {
            parent.setVisible(b);
        }

        public boolean isShowing() {
            return parent.isShowing();
        }

        public boolean contains(Point p) {
            Rectangle r = getBounds();
            return r.contains(p);
        }

        public Point getLocationOnScreen() {
             Point parentLocation = parent.getLocationOnScreen();
             Point componentLocation = getLocation();
             componentLocation.translate(parentLocation.x, parentLocation.y);
             return componentLocation;
        }

        public Point getLocation() {
             Rectangle r = getBounds();
             return new Point(r.x, r.y);
        }

        public void setLocation(Point p) {
            // do nothing
        }

        public Rectangle getBounds() {
            return parent.getUI().getTabBounds(parent,
                                               parent.indexOfTab(title));
        }

        public void setBounds(Rectangle r) {
            // do nothing
        }

        public Dimension getSize() {
            Rectangle r = getBounds();
            return new Dimension(r.width, r.height);
        }

        public void setSize(Dimension d) {
            // do nothing
        }

        public Accessible getAccessibleAt(Point p) {
            if (component instanceof Accessible) {
                return (Accessible) component;
            } else {
                return null;
            }
        }

        public boolean isFocusTraversable() {
            return false;
        }

        public void requestFocus() {
            // do nothing
        }

        public void addFocusListener(FocusListener l) {
            // do nothing
        }

        public void removeFocusListener(FocusListener l) {
            // do nothing
        }

        // TIGER - 4732339
        /**
         * Returns an AccessibleIcon
         *
         * <p>
         *  返回AccessibleIcon
         * 
         * 
         * @return the enabled icon if one exists and the page
         * is enabled. Otherwise, returns the disabled icon if
         * one exists and the page is disabled.  Otherwise, null
         * is returned.
         */
        public AccessibleIcon [] getAccessibleIcon() {
            AccessibleIcon accessibleIcon = null;
            if (enabled && icon instanceof ImageIcon) {
                AccessibleContext ac =
                    ((ImageIcon)icon).getAccessibleContext();
                accessibleIcon = (AccessibleIcon)ac;
            } else if (!enabled && disabledIcon instanceof ImageIcon) {
                AccessibleContext ac =
                    ((ImageIcon)disabledIcon).getAccessibleContext();
                accessibleIcon = (AccessibleIcon)ac;
            }
            if (accessibleIcon != null) {
                AccessibleIcon [] returnIcons = new AccessibleIcon[1];
                returnIcons[0] = accessibleIcon;
                return returnIcons;
            } else {
                return null;
            }
        }
    }

    /**
    * Sets the component that is responsible for rendering the
    * title for the specified tab.  A null value means
    * <code>JTabbedPane</code> will render the title and/or icon for
    * the specified tab.  A non-null value means the component will
    * render the title and <code>JTabbedPane</code> will not render
    * the title and/or icon.
    * <p>
    * Note: The component must not be one that the developer has
    *       already added to the tabbed pane.
    *
    * <p>
    *  设置负责呈现指定标签的标题的组件。空值意味着<code> JTabbedPane </code>将渲染指定标签的标题和/或图标。
    * 非空值意味着组件将渲染标题,并且<code> JTabbedPane </code>将不渲染标题和/或图标。
    * <p>
    *  注意：组件不能是开发人员已经添加到选项卡式窗格的组件。
    * 
    * 
    * @param index the tab index where the component should be set
    * @param component the component to render the title for the
    *                  specified tab
    * @exception IndexOutOfBoundsException if index is out of range
    *            {@code (index < 0 || index >= tab count)}
    * @exception IllegalArgumentException if component has already been
    *            added to this <code>JTabbedPane</code>
    *
    * @see #getTabComponentAt
    * @beaninfo
    *    preferred: true
    *    attribute: visualUpdate true
    *  description: The tab component at the specified tab index.
    * @since 1.6
    */
    public void setTabComponentAt(int index, Component component) {
        if (component != null && indexOfComponent(component) != -1) {
            throw new IllegalArgumentException("Component is already added to this JTabbedPane");
        }
        Component oldValue = getTabComponentAt(index);
        if (component != oldValue) {
            int tabComponentIndex = indexOfTabComponent(component);
            if (tabComponentIndex != -1) {
                setTabComponentAt(tabComponentIndex, null);
            }
            pages.get(index).tabComponent = component;
            firePropertyChange("indexForTabComponent", -1, index);
        }
    }

    /**
     * Returns the tab component at <code>index</code>.
     *
     * <p>
     *  返回<code> index </code>处的制表符组件。
     * 
     * 
     * @param index  the index of the item being queried
     * @return the tab component at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            {@code (index < 0 || index >= tab count)}
     *
     * @see #setTabComponentAt
     * @since 1.6
     */
    public Component getTabComponentAt(int index) {
        return pages.get(index).tabComponent;
    }

    /**
     * Returns the index of the tab for the specified tab component.
     * Returns -1 if there is no tab for this tab component.
     *
     * <p>
     *  返回指定Tab组件的选项卡的索引。如果此选项卡组件没有选项卡,则返回-1。
     * 
     * @param tabComponent the tab component for the tab
     * @return the first tab which matches this tab component, or -1
     *          if there is no tab for this tab component
     * @see #setTabComponentAt
     * @see #getTabComponentAt
     * @since 1.6
     */
     public int indexOfTabComponent(Component tabComponent) {
        for(int i = 0; i < getTabCount(); i++) {
            Component c = getTabComponentAt(i);
            if (c == tabComponent) {
                return i;
            }
        }
        return -1;
    }
}
