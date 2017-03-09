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



import java.beans.ConstructorProperties;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.awt.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;



/**
 * <code>JSplitPane</code> is used to divide two (and only two)
 * <code>Component</code>s. The two <code>Component</code>s
 * are graphically divided based on the look and feel
 * implementation, and the two <code>Component</code>s can then be
 * interactively resized by the user.
 * Information on using <code>JSplitPane</code> is in
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html">How to Use Split Panes</a> in
 * <em>The Java Tutorial</em>.
 * <p>
 * The two <code>Component</code>s in a split pane can be aligned
 * left to right using
 * <code>JSplitPane.HORIZONTAL_SPLIT</code>, or top to bottom using
 * <code>JSplitPane.VERTICAL_SPLIT</code>.
 * The preferred way to change the size of the <code>Component</code>s
 * is to invoke
 * <code>setDividerLocation</code> where <code>location</code> is either
 * the new x or y position, depending on the orientation of the
 * <code>JSplitPane</code>.
 * <p>
 * To resize the <code>Component</code>s to their preferred sizes invoke
 * <code>resetToPreferredSizes</code>.
 * <p>
 * When the user is resizing the <code>Component</code>s the minimum
 * size of the <code>Components</code> is used to determine the
 * maximum/minimum position the <code>Component</code>s
 * can be set to. If the minimum size of the two
 * components is greater than the size of the split pane the divider
 * will not allow you to resize it. To alter the minimum size of a
 * <code>JComponent</code>, see {@link JComponent#setMinimumSize}.
 * <p>
 * When the user resizes the split pane the new space is distributed between
 * the two components based on the <code>resizeWeight</code> property.
 * A value of 0,
 * the default, indicates the right/bottom component gets all the space,
 * where as a value of 1 indicates the left/top component gets all the space.
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
 * <p>
 *  <code> JSplitPane </code>用于分割两个(只有两个)<code> Component </code>。
 * 基于外观和感觉实现图形地划分两个<code> Component </code>,然后用户可以交互地调整两个<code> Component </code>。
 * 有关使用<code> JSplitPane </code>的信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html">
 * 如何使用拆分窗格</a>在Java教程</em>中。
 * 基于外观和感觉实现图形地划分两个<code> Component </code>,然后用户可以交互地调整两个<code> Component </code>。
 * <p>
 *  拆分窗格中的两个<code> Component </code>可以使用<code> JSplitPane.HORIZONTAL_SPLIT </code>从左到右排列,或使用<code> JSpli
 * tPane.VERTICAL_SPLIT </code>从上到下排列。
 * 更改<code> Component </code>的大小的首选方法是调用<code> setDividerLocation </code>,其中<code> location </code>是新的x或
 * y位置, <code> JSplitPane </code>的方向。
 * <p>
 *  要将<code> Component </code>调整为其首选大小,请调用<code> resetToPreferredSizes </code>。
 * <p>
 * 当用户调整<code> Component </code>的大小时,<code> Components </code>的最小大小用于确定<code> Component </code>可设置的最大/最小
 * 位置至。
 * 如果两个组件的最小大小大于拆分窗格的大小,则分隔符将不允许您调整其大小。
 * 要更改<code> JComponent </code>的最小大小,请参阅{@link JComponent#setMinimumSize}。
 * <p>
 *  当用户调整拆分窗格时,基于<code> resizeWeight </code>属性在两个组件之间分配新空间。
 * 值为0,默认值,表示right / bottom组件获取所有空间,其中值为1表示left / top组件获取所有空间。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see #setDividerLocation
 * @see #resetToPreferredSizes
 *
 * @author Scott Violet
 */
public class JSplitPane extends JComponent implements Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "SplitPaneUI";

    /**
     * Vertical split indicates the <code>Component</code>s are
     * split along the y axis.  For example the two
     * <code>Component</code>s will be split one on top of the other.
     * <p>
     *  垂直拆分表示<code>组件</code>沿y轴分割。例如,两个<code> Component </code>将被分割在另一个之上。
     * 
     */
    public final static int VERTICAL_SPLIT = 0;

    /**
     * Horizontal split indicates the <code>Component</code>s are
     * split along the x axis.  For example the two
     * <code>Component</code>s will be split one to the left of the
     * other.
     * <p>
     * 水平拆分表示<code> Component </code>沿x轴分割。例如,两个<code> Component </code>将被分割到另一个的左边。
     * 
     */
    public final static int HORIZONTAL_SPLIT = 1;

    /**
     * Used to add a <code>Component</code> to the left of the other
     * <code>Component</code>.
     * <p>
     *  用于在另一个<code> Component </code>的左侧添加<code> Component </code>。
     * 
     */
    public final static String LEFT = "left";

    /**
     * Used to add a <code>Component</code> to the right of the other
     * <code>Component</code>.
     * <p>
     *  用于在另一个<code> Component </code>的右侧添加<code> Component </code>。
     * 
     */
    public final static String RIGHT = "right";

    /**
     * Used to add a <code>Component</code> above the other
     * <code>Component</code>.
     * <p>
     *  用于在另一个<code> Component </code>之上添加<code> Component </code>。
     * 
     */
    public final static String TOP = "top";

    /**
     * Used to add a <code>Component</code> below the other
     * <code>Component</code>.
     * <p>
     *  用于在另一个<code> Component </code>下添加<code> Component </code>。
     * 
     */
    public final static String BOTTOM = "bottom";

    /**
     * Used to add a <code>Component</code> that will represent the divider.
     * <p>
     *  用于添加代表分隔符的<code> Component </code>。
     * 
     */
    public final static String DIVIDER = "divider";

    /**
     * Bound property name for orientation (horizontal or vertical).
     * <p>
     *  定向的绑定属性名称(水平或垂直)。
     * 
     */
    public final static String ORIENTATION_PROPERTY = "orientation";

    /**
     * Bound property name for continuousLayout.
     * <p>
     *  continuousLayout的绑定属性名。
     * 
     */
    public final static String CONTINUOUS_LAYOUT_PROPERTY = "continuousLayout";

    /**
     * Bound property name for border.
     * <p>
     *  边框的边界属性名称。
     * 
     */
    public final static String DIVIDER_SIZE_PROPERTY = "dividerSize";

    /**
     * Bound property for oneTouchExpandable.
     * <p>
     *  oneTouchExpandable的绑定属性。
     * 
     */
    public final static String ONE_TOUCH_EXPANDABLE_PROPERTY =
                               "oneTouchExpandable";

    /**
     * Bound property for lastLocation.
     * <p>
     *  lastLocation的绑定属性。
     * 
     */
    public final static String LAST_DIVIDER_LOCATION_PROPERTY =
                               "lastDividerLocation";

    /**
     * Bound property for the dividerLocation.
     * <p>
     *  dividerRocation的绑定属性。
     * 
     * 
     * @since 1.3
     */
    public final static String DIVIDER_LOCATION_PROPERTY = "dividerLocation";

    /**
     * Bound property for weight.
     * <p>
     *  重量的绑定属性。
     * 
     * 
     * @since 1.3
     */
    public final static String RESIZE_WEIGHT_PROPERTY = "resizeWeight";

    /**
     * How the views are split.
     * <p>
     *  视图如何拆分。
     * 
     */
    protected int orientation;

    /**
     * Whether or not the views are continuously redisplayed while
     * resizing.
     * <p>
     *  在调整大小时是否连续重新显示视图。
     * 
     */
    protected boolean continuousLayout;

    /**
     * The left or top component.
     * <p>
     *  左或顶部组件。
     * 
     */
    protected Component leftComponent;

    /**
     * The right or bottom component.
     * <p>
     *  右侧或底部组件。
     * 
     */
    protected Component rightComponent;

    /**
     * Size of the divider.
     * <p>
     *  分隔器的尺寸。
     * 
     */
    protected int dividerSize;
    private boolean dividerSizeSet = false;

    /**
     * Is a little widget provided to quickly expand/collapse the
     * split pane?
     * <p>
     *  是否提供了一个小部件来快速展开/折叠拆分窗格?
     * 
     */
    protected boolean oneTouchExpandable;
    private boolean oneTouchExpandableSet;

    /**
     * Previous location of the split pane.
     * <p>
     *  拆分窗格的上一个位置。
     * 
     */
    protected int lastDividerLocation;

    /**
     * How to distribute extra space.
     * <p>
     *  如何分配额外的空间。
     * 
     */
    private double resizeWeight;

    /**
     * Location of the divider, at least the value that was set, the UI may
     * have a different value.
     * <p>
     *  分隔符的位置,至少是设置的值,UI可能有不同的值。
     * 
     */
    private int dividerLocation;


    /**
     * Creates a new <code>JSplitPane</code> configured to arrange the child
     * components side-by-side horizontally, using two buttons for the components.
     * <p>
     *  创建一个新的<code> JSplitPane </code>,用于使用两个组件按钮来水平排列子组件。
     * 
     */
    public JSplitPane() {
        this(JSplitPane.HORIZONTAL_SPLIT,
                UIManager.getBoolean("SplitPane.continuousLayout"),
                new JButton(UIManager.getString("SplitPane.leftButtonText")),
                new JButton(UIManager.getString("SplitPane.rightButtonText")));
    }


    /**
     * Creates a new <code>JSplitPane</code> configured with the
     * specified orientation.
     *
     * <p>
     *  创建使用指定方向配置的新<code> JSplitPane </code>。
     * 
     * 
     * @param newOrientation  <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @exception IllegalArgumentException if <code>orientation</code>
     *          is not one of HORIZONTAL_SPLIT or VERTICAL_SPLIT.
     */
    @ConstructorProperties({"orientation"})
    public JSplitPane(int newOrientation) {
        this(newOrientation,
                UIManager.getBoolean("SplitPane.continuousLayout"));
    }


    /**
     * Creates a new <code>JSplitPane</code> with the specified
     * orientation and redrawing style.
     *
     * <p>
     * 使用指定的方向和重绘样式创建新的<code> JSplitPane </code>。
     * 
     * 
     * @param newOrientation  <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param newContinuousLayout  a boolean, true for the components to
     *        redraw continuously as the divider changes position, false
     *        to wait until the divider position stops changing to redraw
     * @exception IllegalArgumentException if <code>orientation</code>
     *          is not one of HORIZONTAL_SPLIT or VERTICAL_SPLIT
     */
    public JSplitPane(int newOrientation,
                      boolean newContinuousLayout) {
        this(newOrientation, newContinuousLayout, null, null);
    }


    /**
     * Creates a new <code>JSplitPane</code> with the specified
     * orientation and the specified components.
     *
     * <p>
     *  使用指定的方向和指定的组件创建新的<code> JSplitPane </code>。
     * 
     * 
     * @param newOrientation  <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param newLeftComponent the <code>Component</code> that will
     *          appear on the left
     *          of a horizontally-split pane, or at the top of a
     *          vertically-split pane
     * @param newRightComponent the <code>Component</code> that will
     *          appear on the right
     *          of a horizontally-split pane, or at the bottom of a
     *          vertically-split pane
     * @exception IllegalArgumentException if <code>orientation</code>
     *          is not one of: HORIZONTAL_SPLIT or VERTICAL_SPLIT
     */
    public JSplitPane(int newOrientation,
                      Component newLeftComponent,
                      Component newRightComponent){
        this(newOrientation,
                UIManager.getBoolean("SplitPane.continuousLayout"),
                newLeftComponent, newRightComponent);
    }


    /**
     * Creates a new <code>JSplitPane</code> with the specified
     * orientation and
     * redrawing style, and with the specified components.
     *
     * <p>
     *  使用指定的方向和重绘样式以及指定的组件创建新的<code> JSplitPane </code>。
     * 
     * 
     * @param newOrientation  <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param newContinuousLayout  a boolean, true for the components to
     *        redraw continuously as the divider changes position, false
     *        to wait until the divider position stops changing to redraw
     * @param newLeftComponent the <code>Component</code> that will
     *          appear on the left
     *          of a horizontally-split pane, or at the top of a
     *          vertically-split pane
     * @param newRightComponent the <code>Component</code> that will
     *          appear on the right
     *          of a horizontally-split pane, or at the bottom of a
     *          vertically-split pane
     * @exception IllegalArgumentException if <code>orientation</code>
     *          is not one of HORIZONTAL_SPLIT or VERTICAL_SPLIT
     */
    public JSplitPane(int newOrientation,
                      boolean newContinuousLayout,
                      Component newLeftComponent,
                      Component newRightComponent){
        super();

        dividerLocation = -1;
        setLayout(null);
        setUIProperty("opaque", Boolean.TRUE);
        orientation = newOrientation;
        if (orientation != HORIZONTAL_SPLIT && orientation != VERTICAL_SPLIT)
            throw new IllegalArgumentException("cannot create JSplitPane, " +
                                               "orientation must be one of " +
                                               "JSplitPane.HORIZONTAL_SPLIT " +
                                               "or JSplitPane.VERTICAL_SPLIT");
        continuousLayout = newContinuousLayout;
        if (newLeftComponent != null)
            setLeftComponent(newLeftComponent);
        if (newRightComponent != null)
            setRightComponent(newRightComponent);
        updateUI();

    }


    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the <code>SplitPaneUI</code> L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(SplitPaneUI ui) {
        if ((SplitPaneUI)this.ui != ui) {
            super.setUI(ui);
            revalidate();
        }
    }


    /**
     * Returns the <code>SplitPaneUI</code> that is providing the
     * current look and feel.
     *
     * <p>
     *  返回提供当前外观的<code> SplitPaneUI </code>。
     * 
     * 
     * @return the <code>SplitPaneUI</code> object that renders this component
     * @beaninfo
     *       expert: true
     *  description: The L&amp;F object that renders this component.
     */
    public SplitPaneUI getUI() {
        return (SplitPaneUI)ui;
    }


    /**
     * Notification from the <code>UIManager</code> that the L&amp;F has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * <p>
     *  来自<code> UIManager </code>的通知表示L&amp; F已更改。使用<code> UIManager </code>中的最新版本替换当前的UI对象。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((SplitPaneUI)UIManager.getUI(this));
        revalidate();
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "SplitPaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *       expert: true
     *  description: A string that specifies the name of the L&amp;F class.
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Sets the size of the divider.
     *
     * <p>
     *  设置分隔符的大小。
     * 
     * 
     * @param newSize an integer giving the size of the divider in pixels
     * @beaninfo
     *        bound: true
     *  description: The size of the divider.
     */
    public void setDividerSize(int newSize) {
        int           oldSize = dividerSize;

        dividerSizeSet = true;
        if (oldSize != newSize) {
            dividerSize = newSize;
            firePropertyChange(DIVIDER_SIZE_PROPERTY, oldSize, newSize);
        }
    }


    /**
     * Returns the size of the divider.
     *
     * <p>
     *  返回分隔符的大小。
     * 
     * 
     * @return an integer giving the size of the divider in pixels
     */
    public int getDividerSize() {
        return dividerSize;
    }


    /**
     * Sets the component to the left (or above) the divider.
     *
     * <p>
     *  将组件设置为分隔符的左侧(或上方)。
     * 
     * 
     * @param comp the <code>Component</code> to display in that position
     */
    public void setLeftComponent(Component comp) {
        if (comp == null) {
            if (leftComponent != null) {
                remove(leftComponent);
                leftComponent = null;
            }
        } else {
            add(comp, JSplitPane.LEFT);
        }
    }


    /**
     * Returns the component to the left (or above) the divider.
     *
     * <p>
     *  将组件返回到分隔符的左侧(或上方)。
     * 
     * 
     * @return the <code>Component</code> displayed in that position
     * @beaninfo
     *    preferred: true
     *  description: The component to the left (or above) the divider.
     */
    public Component getLeftComponent() {
        return leftComponent;
    }


    /**
     * Sets the component above, or to the left of the divider.
     *
     * <p>
     *  设置上面的组件,或者在分频器的左边。
     * 
     * 
     * @param comp the <code>Component</code> to display in that position
     * @beaninfo
     *  description: The component above, or to the left of the divider.
     */
    public void setTopComponent(Component comp) {
        setLeftComponent(comp);
    }


    /**
     * Returns the component above, or to the left of the divider.
     *
     * <p>
     *  返回上面的组件或分隔符的左侧。
     * 
     * 
     * @return the <code>Component</code> displayed in that position
     */
    public Component getTopComponent() {
        return leftComponent;
    }


    /**
     * Sets the component to the right (or below) the divider.
     *
     * <p>
     *  将组件设置为分隔符的右侧(或下侧)。
     * 
     * 
     * @param comp the <code>Component</code> to display in that position
     * @beaninfo
     *    preferred: true
     *  description: The component to the right (or below) the divider.
     */
    public void setRightComponent(Component comp) {
        if (comp == null) {
            if (rightComponent != null) {
                remove(rightComponent);
                rightComponent = null;
            }
        } else {
            add(comp, JSplitPane.RIGHT);
        }
    }


    /**
     * Returns the component to the right (or below) the divider.
     *
     * <p>
     *  将组件返回到右侧(或下方)的分隔符。
     * 
     * 
     * @return the <code>Component</code> displayed in that position
     */
    public Component getRightComponent() {
        return rightComponent;
    }


    /**
     * Sets the component below, or to the right of the divider.
     *
     * <p>
     *  将组件设置在下方或分频器的右侧。
     * 
     * 
     * @param comp the <code>Component</code> to display in that position
     * @beaninfo
     *  description: The component below, or to the right of the divider.
     */
    public void setBottomComponent(Component comp) {
        setRightComponent(comp);
    }


    /**
     * Returns the component below, or to the right of the divider.
     *
     * <p>
     *  返回下面或分隔符右侧的组件。
     * 
     * 
     * @return the <code>Component</code> displayed in that position
     */
    public Component getBottomComponent() {
        return rightComponent;
    }


    /**
     * Sets the value of the <code>oneTouchExpandable</code> property,
     * which must be <code>true</code> for the
     * <code>JSplitPane</code> to provide a UI widget
     * on the divider to quickly expand/collapse the divider.
     * The default value of this property is <code>false</code>.
     * Some look and feels might not support one-touch expanding;
     * they will ignore this property.
     *
     * <p>
     * 设置<code> oneTouchExpandable </code>属性的值,对于<code> JSplitPane </code>必须是<code> true </code>才能在分隔符上提供一个U
     * I窗口小部件以快速展开/分频器。
     * 此属性的默认值为<code> false </code>。一些外观和感觉可能不支持一触式扩展;他们将忽略此属性。
     * 
     * 
     * @param newValue <code>true</code> to specify that the split pane should provide a
     *        collapse/expand widget
     * @beaninfo
     *        bound: true
     *  description: UI widget on the divider to quickly
     *               expand/collapse the divider.
     *
     * @see #isOneTouchExpandable
     */
    public void setOneTouchExpandable(boolean newValue) {
        boolean           oldValue = oneTouchExpandable;

        oneTouchExpandable = newValue;
        oneTouchExpandableSet = true;
        firePropertyChange(ONE_TOUCH_EXPANDABLE_PROPERTY, oldValue, newValue);
        repaint();
    }


    /**
     * Gets the <code>oneTouchExpandable</code> property.
     *
     * <p>
     *  获取<code> oneTouchExpandable </code>属性。
     * 
     * 
     * @return the value of the <code>oneTouchExpandable</code> property
     * @see #setOneTouchExpandable
     */
    public boolean isOneTouchExpandable() {
        return oneTouchExpandable;
    }


    /**
     * Sets the last location the divider was at to
     * <code>newLastLocation</code>.
     *
     * <p>
     *  设置分隔符到<code> newLastLocation </code>的最后一个位置。
     * 
     * 
     * @param newLastLocation an integer specifying the last divider location
     *        in pixels, from the left (or upper) edge of the pane to the
     *        left (or upper) edge of the divider
     * @beaninfo
     *        bound: true
     *  description: The last location the divider was at.
     */
    public void setLastDividerLocation(int newLastLocation) {
        int               oldLocation = lastDividerLocation;

        lastDividerLocation = newLastLocation;
        firePropertyChange(LAST_DIVIDER_LOCATION_PROPERTY, oldLocation,
                           newLastLocation);
    }


    /**
     * Returns the last location the divider was at.
     *
     * <p>
     *  返回分隔符的最后一个位置。
     * 
     * 
     * @return an integer specifying the last divider location as a count
     *       of pixels from the left (or upper) edge of the pane to the
     *       left (or upper) edge of the divider
     */
    public int getLastDividerLocation() {
        return lastDividerLocation;
    }


    /**
     * Sets the orientation, or how the splitter is divided. The options
     * are:<ul>
     * <li>JSplitPane.VERTICAL_SPLIT  (above/below orientation of components)
     * <li>JSplitPane.HORIZONTAL_SPLIT  (left/right orientation of components)
     * </ul>
     *
     * <p>
     *  设置方向,或如何分割分割。
     * 选项为：<ul> <li> JSplitPane.VERTICAL_SPLIT(组件方向上方/下方)<li> JSplitPane.HORIZONTAL_SPLIT(组件的左/右方向)。
     * </ul>
     * 
     * 
     * @param orientation an integer specifying the orientation
     * @exception IllegalArgumentException if orientation is not one of:
     *        HORIZONTAL_SPLIT or VERTICAL_SPLIT.
     * @beaninfo
     *        bound: true
     *  description: The orientation, or how the splitter is divided.
     *         enum: HORIZONTAL_SPLIT JSplitPane.HORIZONTAL_SPLIT
     *               VERTICAL_SPLIT   JSplitPane.VERTICAL_SPLIT
     */
    public void setOrientation(int orientation) {
        if ((orientation != VERTICAL_SPLIT) &&
            (orientation != HORIZONTAL_SPLIT)) {
           throw new IllegalArgumentException("JSplitPane: orientation must " +
                                              "be one of " +
                                              "JSplitPane.VERTICAL_SPLIT or " +
                                              "JSplitPane.HORIZONTAL_SPLIT");
        }

        int           oldOrientation = this.orientation;

        this.orientation = orientation;
        firePropertyChange(ORIENTATION_PROPERTY, oldOrientation, orientation);
    }


    /**
     * Returns the orientation.
     *
     * <p>
     *  返回方向。
     * 
     * 
     * @return an integer giving the orientation
     * @see #setOrientation
     */
    public int getOrientation() {
        return orientation;
    }


    /**
     * Sets the value of the <code>continuousLayout</code> property,
     * which must be <code>true</code> for the child components
     * to be continuously
     * redisplayed and laid out during user intervention.
     * The default value of this property is look and feel dependent.
     * Some look and feels might not support continuous layout;
     * they will ignore this property.
     *
     * <p>
     *  设置<code> continuousLayout </code>属性的值,必须<code> true </code>才能在用户干预期间连续重新显示和布置子组件。此属性的默认值与外观相关。
     * 一些外观和感觉可能不支持连续布局;他们将忽略此属性。
     * 
     * 
     * @param newContinuousLayout  <code>true</code> if the components
     *        should continuously be redrawn as the divider changes position
     * @beaninfo
     *        bound: true
     *  description: Whether the child components are
     *               continuously redisplayed and laid out during
     *               user intervention.
     * @see #isContinuousLayout
     */
    public void setContinuousLayout(boolean newContinuousLayout) {
        boolean           oldCD = continuousLayout;

        continuousLayout = newContinuousLayout;
        firePropertyChange(CONTINUOUS_LAYOUT_PROPERTY, oldCD,
                           newContinuousLayout);
    }


    /**
     * Gets the <code>continuousLayout</code> property.
     *
     * <p>
     *  获取<code> continuousLayout </code>属性。
     * 
     * 
     * @return the value of the <code>continuousLayout</code> property
     * @see #setContinuousLayout
     */
    public boolean isContinuousLayout() {
        return continuousLayout;
    }

    /**
     * Specifies how to distribute extra space when the size of the split pane
     * changes. A value of 0, the default,
     * indicates the right/bottom component gets all the extra space (the
     * left/top component acts fixed), where as a value of 1 specifies the
     * left/top component gets all the extra space (the right/bottom component
     * acts fixed). Specifically, the left/top component gets (weight * diff)
     * extra space and the right/bottom component gets (1 - weight) * diff
     * extra space.
     *
     * <p>
     * 指定当拆分窗格的大小更改时如何分配额外的空间。值为0,默认值表示右/底组件获得所有额外的空间(左/顶组件行为固定),其中值1指定左/顶组件获得所有额外的空间(右/底部组件行为固定)。
     * 具体来说,左/顶分量获得(weight * diff)额外空间,右/底分量获得(1-重量)* diff额外空间。
     * 
     * 
     * @param value as described above
     * @exception IllegalArgumentException if <code>value</code> is &lt; 0 or &gt; 1
     * @since 1.3
     * @beaninfo
     *        bound: true
     *  description: Specifies how to distribute extra space when the split pane
     *               resizes.
     */
    public void setResizeWeight(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("JSplitPane weight must be between 0 and 1");
        }
        double         oldWeight = resizeWeight;

        resizeWeight = value;
        firePropertyChange(RESIZE_WEIGHT_PROPERTY, oldWeight, value);
    }

    /**
     * Returns the number that determines how extra space is distributed.
     * <p>
     *  返回确定如何分配额外空间的数字。
     * 
     * 
     * @return how extra space is to be distributed on a resize of the
     *         split pane
     * @since 1.3
     */
    public double getResizeWeight() {
        return resizeWeight;
    }

    /**
     * Lays out the <code>JSplitPane</code> layout based on the preferred size
     * of the children components. This will likely result in changing
     * the divider location.
     * <p>
     *  根据子组件的首选大小放出<code> JSplitPane </code>布局。这可能会导致更改分隔符位置。
     * 
     */
    public void resetToPreferredSizes() {
        SplitPaneUI         ui = getUI();

        if (ui != null) {
            ui.resetToPreferredSizes(this);
        }
    }


    /**
     * Sets the divider location as a percentage of the
     * <code>JSplitPane</code>'s size.
     * <p>
     * This method is implemented in terms of
     * <code>setDividerLocation(int)</code>.
     * This method immediately changes the size of the split pane based on
     * its current size. If the split pane is not correctly realized and on
     * screen, this method will have no effect (new divider location will
     * become (current size * proportionalLocation) which is 0).
     *
     * <p>
     *  将分隔符位置设置为<code> JSplitPane </code>大小的百分比。
     * <p>
     *  这个方法是根据<code> setDividerLocation(int)</code>来实现的。此方法根据当前大小立即更改拆分窗格的大小。
     * 如果分割窗格没有正确实现和在屏幕上,这种方法将没有效果(新的分隔符位置将变为(当前大小* proportionalLocation)为0)。
     * 
     * 
     * @param proportionalLocation  a double-precision floating point value
     *        that specifies a percentage, from zero (top/left) to 1.0
     *        (bottom/right)
     * @exception IllegalArgumentException if the specified location is &lt; 0
     *            or &gt; 1.0
     * @beaninfo
     *  description: The location of the divider.
     */
    public void setDividerLocation(double proportionalLocation) {
        if (proportionalLocation < 0.0 ||
           proportionalLocation > 1.0) {
            throw new IllegalArgumentException("proportional location must " +
                                               "be between 0.0 and 1.0.");
        }
        if (getOrientation() == VERTICAL_SPLIT) {
            setDividerLocation((int)((double)(getHeight() - getDividerSize()) *
                                     proportionalLocation));
        } else {
            setDividerLocation((int)((double)(getWidth() - getDividerSize()) *
                                     proportionalLocation));
        }
    }


    /**
     * Sets the location of the divider. This is passed off to the
     * look and feel implementation, and then listeners are notified. A value
     * less than 0 implies the divider should be reset to a value that
     * attempts to honor the preferred size of the left/top component.
     * After notifying the listeners, the last divider location is updated,
     * via <code>setLastDividerLocation</code>.
     *
     * <p>
     *  设置分隔符的位置。这被传递给外观和感觉实现,然后通知侦听器。小于0的值意味着分频器应该重置为尝试满足左/顶分量的优选大小的值。
     * 在通知监听器之后,通过<code> setLastDividerLocation </code>更新最后的分频器位置。
     * 
     * 
     * @param location an int specifying a UI-specific value (typically a
     *        pixel count)
     * @beaninfo
     *        bound: true
     *  description: The location of the divider.
     */
    public void setDividerLocation(int location) {
        int                 oldValue = dividerLocation;

        dividerLocation = location;

        // Notify UI.
        SplitPaneUI         ui = getUI();

        if (ui != null) {
            ui.setDividerLocation(this, location);
        }

        // Then listeners
        firePropertyChange(DIVIDER_LOCATION_PROPERTY, oldValue, location);

        // And update the last divider location.
        setLastDividerLocation(oldValue);
    }


    /**
     * Returns the last value passed to <code>setDividerLocation</code>.
     * The value returned from this method may differ from the actual
     * divider location (if <code>setDividerLocation</code> was passed a
     * value bigger than the current size).
     *
     * <p>
     * 返回传递给<code> setDividerLocation </code>的最后一个值。
     * 从此方法返回的值可能与实际分隔符位置不同(如果<code> setDividerLocation </code>传递的值大于当前大小)。
     * 
     * 
     * @return an integer specifying the location of the divider
     */
    public int getDividerLocation() {
        return dividerLocation;
    }


    /**
     * Returns the minimum location of the divider from the look and feel
     * implementation.
     *
     * <p>
     *  从外观实现返回分隔符的最小位置。
     * 
     * 
     * @return an integer specifying a UI-specific value for the minimum
     *          location (typically a pixel count); or -1 if the UI is
     *          <code>null</code>
     * @beaninfo
     *  description: The minimum location of the divider from the L&amp;F.
     */
    public int getMinimumDividerLocation() {
        SplitPaneUI         ui = getUI();

        if (ui != null) {
            return ui.getMinimumDividerLocation(this);
        }
        return -1;
    }


    /**
     * Returns the maximum location of the divider from the look and feel
     * implementation.
     *
     * <p>
     *  返回从外观和感觉实现的分隔符的最大位置。
     * 
     * 
     * @return an integer specifying a UI-specific value for the maximum
     *          location (typically a pixel count); or -1 if the  UI is
     *          <code>null</code>
     */
    public int getMaximumDividerLocation() {
        SplitPaneUI         ui = getUI();

        if (ui != null) {
            return ui.getMaximumDividerLocation(this);
        }
        return -1;
    }


    /**
     * Removes the child component, <code>component</code> from the
     * pane. Resets the <code>leftComponent</code> or
     * <code>rightComponent</code> instance variable, as necessary.
     *
     * <p>
     *  从窗格中删除子组件<code>组件</code>。根据需要重置<code> leftComponent </code>或<code> rightComponent </code>实例变量。
     * 
     * 
     * @param component the <code>Component</code> to remove
     */
    public void remove(Component component) {
        if (component == leftComponent) {
            leftComponent = null;
        } else if (component == rightComponent) {
            rightComponent = null;
        }
        super.remove(component);

        // Update the JSplitPane on the screen
        revalidate();
        repaint();
    }


    /**
     * Removes the <code>Component</code> at the specified index.
     * Updates the <code>leftComponent</code> and <code>rightComponent</code>
     * instance variables as necessary, and then messages super.
     *
     * <p>
     *  删除指定索引处的<code> Component </code>。
     * 根据需要更新<code> leftComponent </code>和<code> rightComponent </code>实例变量,然后更新消息super。
     * 
     * 
     * @param index an integer specifying the component to remove, where
     *        1 specifies the left/top component and 2 specifies the
     *        bottom/right component
     */
    public void remove(int index) {
        Component    comp = getComponent(index);

        if (comp == leftComponent) {
            leftComponent = null;
        } else if (comp == rightComponent) {
            rightComponent = null;
        }
        super.remove(index);

        // Update the JSplitPane on the screen
        revalidate();
        repaint();
    }


    /**
     * Removes all the child components from the split pane. Resets the
     * <code>leftComonent</code> and <code>rightComponent</code>
     * instance variables.
     * <p>
     *  从拆分窗格中删除所有子组件。重置<code> leftComonent </code>和<code> rightComponent </code>实例变量。
     * 
     */
    public void removeAll() {
        leftComponent = rightComponent = null;
        super.removeAll();

        // Update the JSplitPane on the screen
        revalidate();
        repaint();
    }


    /**
     * Returns true, so that calls to <code>revalidate</code>
     * on any descendant of this <code>JSplitPane</code>
     * will cause a request to be queued that
     * will validate the <code>JSplitPane</code> and all its descendants.
     *
     * <p>
     *  返回true,因此对<code> JSplitPane </code>的任何后代调用<code> revalidate </code>会导致请求排队,将验证<code> JSplitPane </code>
     * 后人。
     * 
     * 
     * @return true
     * @see JComponent#revalidate
     * @see java.awt.Container#isValidateRoot
     *
     * @beaninfo
     *    hidden: true
     */
    @Override
    public boolean isValidateRoot() {
        return true;
    }


    /**
     * Adds the specified component to this split pane.
     * If <code>constraints</code> identifies the left/top or
     * right/bottom child component, and a component with that identifier
     * was previously added, it will be removed and then <code>comp</code>
     * will be added in its place. If <code>constraints</code> is not
     * one of the known identifiers the layout manager may throw an
     * <code>IllegalArgumentException</code>.
     * <p>
     * The possible constraints objects (Strings) are:
     * <ul>
     * <li>JSplitPane.TOP
     * <li>JSplitPane.LEFT
     * <li>JSplitPane.BOTTOM
     * <li>JSplitPane.RIGHT
     * </ul>
     * If the <code>constraints</code> object is <code>null</code>,
     * the component is added in the
     * first available position (left/top if open, else right/bottom).
     *
     * <p>
     * 将指定的组件添加到此拆分窗格。如果<code>约束</code>标识左/上或右/下子组件,并且之前添加了具​​有该标识符的组件,则它将被删除,然后<code> comp </code>地点。
     * 如果<code>约束</code>不是已知标识符之一,布局管理器可能会抛出一个<code> IllegalArgumentException </code>。
     * <p>
     *  可能的约束对象(Strings)是：
     * <ul>
     *  <li> JSplitPane.TOP <li> JSplitPane.LEFT <li> JSplitPane.BOTTOM <li> JSplitPane.RIGHT
     * </ul>
     *  如果<code>约束</code>对象是<code> null </code>,则组件将添加到第一个可用位置(如果打开,则为left / top,否则为right / bottom)。
     * 
     * 
     * @param comp        the component to add
     * @param constraints an <code>Object</code> specifying the
     *                    layout constraints
     *                    (position) for this component
     * @param index       an integer specifying the index in the container's
     *                    list.
     * @exception IllegalArgumentException  if the <code>constraints</code>
     *          object does not match an existing component
     * @see java.awt.Container#addImpl(Component, Object, int)
     */
    protected void addImpl(Component comp, Object constraints, int index)
    {
        Component             toRemove;

        if (constraints != null && !(constraints instanceof String)) {
            throw new IllegalArgumentException("cannot add to layout: " +
                                               "constraint must be a string " +
                                               "(or null)");
        }

        /* If the constraints are null and the left/right component is
        /* <p>
        /* 
           invalid, add it at the left/right component. */
        if (constraints == null) {
            if (getLeftComponent() == null) {
                constraints = JSplitPane.LEFT;
            } else if (getRightComponent() == null) {
                constraints = JSplitPane.RIGHT;
            }
        }

        /* Find the Component that already exists and remove it. */
        if (constraints != null && (constraints.equals(JSplitPane.LEFT) ||
                                   constraints.equals(JSplitPane.TOP))) {
            toRemove = getLeftComponent();
            if (toRemove != null) {
                remove(toRemove);
            }
            leftComponent = comp;
            index = -1;
        } else if (constraints != null &&
                   (constraints.equals(JSplitPane.RIGHT) ||
                    constraints.equals(JSplitPane.BOTTOM))) {
            toRemove = getRightComponent();
            if (toRemove != null) {
                remove(toRemove);
            }
            rightComponent = comp;
            index = -1;
        } else if (constraints != null &&
                constraints.equals(JSplitPane.DIVIDER)) {
            index = -1;
        }
        /* LayoutManager should raise for else condition here. */

        super.addImpl(comp, constraints, index);

        // Update the JSplitPane on the screen
        revalidate();
        repaint();
    }


    /**
     * Subclassed to message the UI with <code>finishedPaintingChildren</code>
     * after super has been messaged, as well as painting the border.
     *
     * <p>
     *  子类化为在超级消息发送之后用<code> finishedPaintingChildren </code>消息UI,以及绘制边框。
     * 
     * 
     * @param g the <code>Graphics</code> context within which to paint
     */
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        SplitPaneUI        ui = getUI();

        if (ui != null) {
            Graphics           tempG = g.create();
            ui.finishedPaintingChildren(this, tempG);
            tempG.dispose();
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

    void setUIProperty(String propertyName, Object value) {
        if (propertyName == "dividerSize") {
            if (!dividerSizeSet) {
                setDividerSize(((Number)value).intValue());
                dividerSizeSet = false;
            }
        } else if (propertyName == "oneTouchExpandable") {
            if (!oneTouchExpandableSet) {
                setOneTouchExpandable(((Boolean)value).booleanValue());
                oneTouchExpandableSet = false;
            }
        } else {
            super.setUIProperty(propertyName, value);
        }
    }


    /**
     * Returns a string representation of this <code>JSplitPane</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JSplitPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JSplitPane</code>.
     */
    protected String paramString() {
        String orientationString = (orientation == HORIZONTAL_SPLIT ?
                                    "HORIZONTAL_SPLIT" : "VERTICAL_SPLIT");
        String continuousLayoutString = (continuousLayout ?
                                         "true" : "false");
        String oneTouchExpandableString = (oneTouchExpandable ?
                                           "true" : "false");

        return super.paramString() +
        ",continuousLayout=" + continuousLayoutString +
        ",dividerSize=" + dividerSize +
        ",lastDividerLocation=" + lastDividerLocation +
        ",oneTouchExpandable=" + oneTouchExpandableString +
        ",orientation=" + orientationString;
    }



    ///////////////////////////
    // Accessibility support //
    ///////////////////////////


    /**
     * Gets the AccessibleContext associated with this JSplitPane.
     * For split panes, the AccessibleContext takes the form of an
     * AccessibleJSplitPane.
     * A new AccessibleJSplitPane instance is created if necessary.
     *
     * <p>
     *  获取与此JSplitPane相关联的AccessibleContext。对于分割窗格,AccessibleContext采用AccessibleJSplitPane的形式。
     * 如果需要,将创建一个新的AccessibleJSplitPane实例。
     * 
     * 
     * @return an AccessibleJSplitPane that serves as the
     *         AccessibleContext of this JSplitPane
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this SplitPane.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJSplitPane();
        }
        return accessibleContext;
    }


    /**
     * This class implements accessibility support for the
     * <code>JSplitPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to split pane user-interface elements.
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
     * 此类实现<code> JSplitPane </code>类的辅助功能支持。它提供了适用于拆分窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJSplitPane extends AccessibleJComponent
        implements AccessibleValue {
        /**
         * Gets the state set of this object.
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
            // FIXME: [[[WDW - Should also add BUSY if this implements
            // Adjustable at some point.  If this happens, we probably
            // should also add actions.]]]
            if (getOrientation() == VERTICAL_SPLIT) {
                states.add(AccessibleState.VERTICAL);
            } else {
                states.add(AccessibleState.HORIZONTAL);
            }
            return states;
        }


        /**
         * Get the AccessibleValue associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleValue interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleValue。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleValue接口。
         * 
         * 
         * @return this object
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }


        /**
         * Gets the accessible value of this object.
         *
         * <p>
         *  获取此对象的可访问值。
         * 
         * 
         * @return a localized String describing the value of this object
         */
        public Number getCurrentAccessibleValue() {
            return Integer.valueOf(getDividerLocation());
        }


        /**
         * Sets the value of this object as a Number.
         *
         * <p>
         *  将此对象的值设置为Number。
         * 
         * 
         * @return True if the value was set.
         */
        public boolean setCurrentAccessibleValue(Number n) {
            // TIGER - 4422535
            if (n == null) {
                return false;
            }
            setDividerLocation(n.intValue());
            return true;
        }


        /**
         * Gets the minimum accessible value of this object.
         *
         * <p>
         *  获取此对象的最小可访问值。
         * 
         * 
         * @return The minimum value of this object.
         */
        public Number getMinimumAccessibleValue() {
            return Integer.valueOf(getUI().getMinimumDividerLocation(
                                                        JSplitPane.this));
        }


        /**
         * Gets the maximum accessible value of this object.
         *
         * <p>
         *  获取此对象的最大可访问值。
         * 
         * 
         * @return The maximum value of this object.
         */
        public Number getMaximumAccessibleValue() {
            return Integer.valueOf(getUI().getMaximumDividerLocation(
                                                        JSplitPane.this));
        }


        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of
         * the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.SPLIT_PANE;
        }
    } // inner class AccessibleJSplitPane
}
