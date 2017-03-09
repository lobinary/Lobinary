/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.accessibility.Accessible;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;


/**
 * The base class for all UI delegate objects in the Swing pluggable
 * look and feel architecture.  The UI delegate object for a Swing
 * component is responsible for implementing the aspects of the
 * component that depend on the look and feel.
 * The <code>JComponent</code> class
 * invokes methods from this class in order to delegate operations
 * (painting, layout calculations, etc.) that may vary depending on the
 * look and feel installed.  <b>Client programs should not invoke methods
 * on this class directly.</b>
 *
 * <p>
 *  Swing中的所有UI委托对象的基类可插拔外观体系结构。 Swing组件的UI委托对象负责实现依赖于外观和感觉的组件的各个方面。
 *  <code> JComponent </code>类调用此类中的方法,以便委派操作(绘画,布局计算等),这些操作可能因安装的外观而有所不同。 <b>客户端程序不应直接调用此类的方法。</b>。
 * 
 * 
 * @see javax.swing.JComponent
 * @see javax.swing.UIManager
 *
 */
public abstract class ComponentUI {
    /**
     * Sole constructor. (For invocation by subclass constructors,
     * typically implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    public ComponentUI() {
    }

    /**
     * Configures the specified component appropriately for the look and feel.
     * This method is invoked when the <code>ComponentUI</code> instance is being installed
     * as the UI delegate on the specified component.  This method should
     * completely configure the component for the look and feel,
     * including the following:
     * <ol>
     * <li>Install default property values for color, fonts, borders,
     *     icons, opacity, etc. on the component.  Whenever possible,
     *     property values initialized by the client program should <i>not</i>
     *     be overridden.
     * <li>Install a <code>LayoutManager</code> on the component if necessary.
     * <li>Create/add any required sub-components to the component.
     * <li>Create/install event listeners on the component.
     * <li>Create/install a <code>PropertyChangeListener</code> on the component in order
     *     to detect and respond to component property changes appropriately.
     * <li>Install keyboard UI (mnemonics, traversal, etc.) on the component.
     * <li>Initialize any appropriate instance data.
     * </ol>
     * <p>
     *  适当地配置指定的组件的外观和感觉。当将<code> ComponentUI </code>实例作为UI委托安装在指定组件上时,将调用此方法。此方法应完全配置组件的外观和感觉,包括以下内容：
     * <ol>
     * <li>在组件上安装颜色,字体,边框,图标,不透明度等的默认属性值。只要可能,客户端程序初始化的属性值就不应被覆盖。
     * </i> <li>如有必要,在组件上安装<code> LayoutManager </code>。 <li>创建/添加任何所需的子组件到组件。 <li>在组件上创建/安装事件侦听器。
     *  <li>在组件上创建/安装<code> PropertyChangeListener </code>,以便适当地检测和响应组件属性更改。 <li>在组件上安装键盘UI(助记符,遍历等)。
     *  <li>初始化任何适当的实例数据。
     * </ol>
     * 
     * @param c the component where this UI delegate is being installed
     *
     * @see #uninstallUI
     * @see javax.swing.JComponent#setUI
     * @see javax.swing.JComponent#updateUI
     */
    public void installUI(JComponent c) {
    }

    /**
     * Reverses configuration which was done on the specified component during
     * <code>installUI</code>.  This method is invoked when this
     * <code>UIComponent</code> instance is being removed as the UI delegate
     * for the specified component.  This method should undo the
     * configuration performed in <code>installUI</code>, being careful to
     * leave the <code>JComponent</code> instance in a clean state (no
     * extraneous listeners, look-and-feel-specific property objects, etc.).
     * This should include the following:
     * <ol>
     * <li>Remove any UI-set borders from the component.
     * <li>Remove any UI-set layout managers on the component.
     * <li>Remove any UI-added sub-components from the component.
     * <li>Remove any UI-added event/property listeners from the component.
     * <li>Remove any UI-installed keyboard UI from the component.
     * <li>Nullify any allocated instance data objects to allow for GC.
     * </ol>
     * <p>
     *  在<code> installUI </code>期间在指定的组件上完成的反转配置。当这个<code> UIComponent </code>实例作为指定组件的UI委托删除时,会调用此方法。
     * 这个方法应该撤消在<code> installUI </code>中执行的配置,小心的保持<code> JComponent </code>实例在一个干净的状态(没有无关的侦听器,等等。)。
     * 这应包括以下内容：。
     * <ol>
     * <li>从组件中删除任何UI设置边框。 <li>移除组件上的任何UI设置布局管理器。 <li>从组件中删除任何添加UI的子组件。 <li>从组件中删除任何添加UI的事件/属性侦听器。
     *  <li>从组件中删除任何已安装UI的键盘UI。 <li>清除任何已分配的实例数据对象以允许GC。
     * </ol>
     * 
     * @param c the component from which this UI delegate is being removed;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see #installUI
     * @see javax.swing.JComponent#updateUI
     */
    public void uninstallUI(JComponent c) {
    }

    /**
     * Paints the specified component appropriately for the look and feel.
     * This method is invoked from the <code>ComponentUI.update</code> method when
     * the specified component is being painted.  Subclasses should override
     * this method and use the specified <code>Graphics</code> object to
     * render the content of the component.
     *
     * <p>
     *  适当地为指定的组件绘制外观和感觉。当绘制指定的组件时,从<code> ComponentUI.update </code>方法调用此方法。
     * 子类应该重写这个方法,并使用指定的<code> Graphics </code>对象来渲染组件的内容。
     * 
     * 
     * @param g the <code>Graphics</code> context in which to paint
     * @param c the component being painted;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see #update
     */
    public void paint(Graphics g, JComponent c) {
    }

    /**
     * Notifies this UI delegate that it is time to paint the specified
     * component.  This method is invoked by <code>JComponent</code>
     * when the specified component is being painted.
     *
     * <p>By default this method fills the specified component with
     * its background color if its {@code opaque} property is {@code true},
     * and then immediately calls {@code paint}. In general this method need
     * not be overridden by subclasses; all look-and-feel rendering code should
     * reside in the {@code paint} method.
     *
     * <p>
     *  通知此UI委托,是时候绘制指定的组件。当绘制指定的组件时,此方法由<code> JComponent </code>调用。
     * 
     *  <p>默认情况下,如果指定的组件的{@code opaque}属性是{@code true},它会以背景颜色填充,然后立即调用{@code paint}。
     * 一般来说,这个方法不需要被子类覆盖;所有的外观渲染代码应该驻留在{@code paint}方法中。
     * 
     * 
     * @param g the <code>Graphics</code> context in which to paint
     * @param c the component being painted;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see #paint
     * @see javax.swing.JComponent#paintComponent
     */
    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(),c.getHeight());
        }
        paint(g, c);
    }

    /**
     * Returns the specified component's preferred size appropriate for
     * the look and feel.  If <code>null</code> is returned, the preferred
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method returns <code>null</code>.
     *
     * <p>
     * 返回指定组件适合外观和感觉的首选大小。如果返回<code> null </code>,则首选大小将由组件的布局管理器计算(这是安装了特定布局管理器的任何组件的首选方法)。
     * 此方法的默认实现返回<code> null </code>。
     * 
     * 
     * @param c the component whose preferred size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @see javax.swing.JComponent#getPreferredSize
     * @see java.awt.LayoutManager#preferredLayoutSize
     */
    public Dimension getPreferredSize(JComponent c) {
        return null;
    }

    /**
     * Returns the specified component's minimum size appropriate for
     * the look and feel.  If <code>null</code> is returned, the minimum
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method invokes <code>getPreferredSize</code> and returns that value.
     *
     * <p>
     *  返回指定组件适合外观和感觉的最小大小。如果返回<code> null </code>,则最小大小将由组件的布局管理器计算(这是安装了特定布局管理器的任何组件的首选方法)。
     * 此方法的默认实现调用<code> getPreferredSize </code>并返回该值。
     * 
     * 
     * @param c the component whose minimum size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     * @return a <code>Dimension</code> object or <code>null</code>
     *
     * @see javax.swing.JComponent#getMinimumSize
     * @see java.awt.LayoutManager#minimumLayoutSize
     * @see #getPreferredSize
     */
    public Dimension getMinimumSize(JComponent c) {
        return getPreferredSize(c);
    }

    /**
     * Returns the specified component's maximum size appropriate for
     * the look and feel.  If <code>null</code> is returned, the maximum
     * size will be calculated by the component's layout manager instead
     * (this is the preferred approach for any component with a specific
     * layout manager installed).  The default implementation of this
     * method invokes <code>getPreferredSize</code> and returns that value.
     *
     * <p>
     *  返回指定组件的最大大小,适合外观和感觉。如果返回<code> null </code>,则最大大小将由组件的布局管理器计算(这是安装了特定布局管理器的任何组件的首选方法)。
     * 此方法的默认实现调用<code> getPreferredSize </code>并返回该值。
     * 
     * 
     * @param c the component whose maximum size is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     * @return a <code>Dimension</code> object or <code>null</code>
     *
     * @see javax.swing.JComponent#getMaximumSize
     * @see java.awt.LayoutManager2#maximumLayoutSize
     */
    public Dimension getMaximumSize(JComponent c) {
        return getPreferredSize(c);
    }

    /**
     * Returns <code>true</code> if the specified <i>x,y</i> location is
     * contained within the look and feel's defined shape of the specified
     * component. <code>x</code> and <code>y</code> are defined to be relative
     * to the coordinate system of the specified component.  Although
     * a component's <code>bounds</code> is constrained to a rectangle,
     * this method provides the means for defining a non-rectangular
     * shape within those bounds for the purpose of hit detection.
     *
     * <p>
     * 如果指定的<i> x,y </i>位置包含在指定组件的外观和定义的定义形状内,则返回<code> true </code>。
     *  <code> x </code>和<code> y </code>定义为相对于指定组件的坐标系。
     * 虽然组件的<code> bounds </code>被约束为矩形,但是该方法提供了在该边界内定义非矩形形状以用于命中检测的方法。
     * 
     * 
     * @param c the component where the <i>x,y</i> location is being queried;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     * @param x the <i>x</i> coordinate of the point
     * @param y the <i>y</i> coordinate of the point
     *
     * @see javax.swing.JComponent#contains
     * @see java.awt.Component#contains
     */
    @SuppressWarnings("deprecation")
    public boolean contains(JComponent c, int x, int y) {
        return c.inside(x, y);
    }

    /**
     * Returns an instance of the UI delegate for the specified component.
     * Each subclass must provide its own static <code>createUI</code>
     * method that returns an instance of that UI delegate subclass.
     * If the UI delegate subclass is stateless, it may return an instance
     * that is shared by multiple components.  If the UI delegate is
     * stateful, then it should return a new instance per component.
     * The default implementation of this method throws an error, as it
     * should never be invoked.
     * <p>
     *  返回指定组件的UI代理的实例。每个子类必须提供自己的静态<code> createUI </code>方法,返回该UI委托子类的实例。如果UI委托子类是无状态的,它可能返回由多个组件共享的实例。
     * 如果UI委托是有状态的,那么它应该为每个组件返回一个新的实例。此方法的默认实现会抛出错误,因为它不应该被调用。
     * 
     */
    public static ComponentUI createUI(JComponent c) {
        throw new Error("ComponentUI.createUI not implemented.");
    }

    /**
     * Returns the baseline.  The baseline is measured from the top of
     * the component.  This method is primarily meant for
     * <code>LayoutManager</code>s to align components along their
     * baseline.  A return value less than 0 indicates this component
     * does not have a reasonable baseline and that
     * <code>LayoutManager</code>s should not align this component on
     * its baseline.
     * <p>
     * This method returns -1.  Subclasses that have a meaningful baseline
     * should override appropriately.
     *
     * <p>
     *  返回基线。基线从组件的顶部测量。此方法主要用于<code> LayoutManager </code>以沿其基线对齐组件。
     * 小于0的返回值表示此组件没有合理的基线,并且<code> LayoutManager </code>不应将此组件与其基线对齐。
     * <p>
     *  此方法返回-1。具有有意义基线的子类应适当覆盖。
     * 
     * 
     * @param c <code>JComponent</code> baseline is being requested for
     * @param width the width to get the baseline for
     * @param height the height to get the baseline for
     * @throws NullPointerException if <code>c</code> is <code>null</code>
     * @throws IllegalArgumentException if width or height is &lt; 0
     * @return baseline or a value &lt; 0 indicating there is no reasonable
     *                  baseline
     * @see javax.swing.JComponent#getBaseline(int,int)
     * @since 1.6
     */
    public int getBaseline(JComponent c, int width, int height) {
        if (c == null) {
            throw new NullPointerException("Component must be non-null");
        }
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException(
                    "Width and height must be >= 0");
        }
        return -1;
    }

    /**
     * Returns an enum indicating how the baseline of he component
     * changes as the size changes.  This method is primarily meant for
     * layout managers and GUI builders.
     * <p>
     * This method returns <code>BaselineResizeBehavior.OTHER</code>.
     * Subclasses that support a baseline should override appropriately.
     *
     * <p>
     * 返回一个枚举,指示组件的基线如何随着大小变化而变化。此方法主要用于布局管理器和GUI构建器。
     * <p>
     *  此方法返回<code> BaselineResizeBehavior.OTHER </code>。支持基线的子类应适当覆盖。
     * 
     * 
     * @param c <code>JComponent</code> to return baseline resize behavior for
     * @return an enum indicating how the baseline changes as the component
     *         size changes
     * @throws NullPointerException if <code>c</code> is <code>null</code>
     * @see javax.swing.JComponent#getBaseline(int, int)
     * @since 1.6
     */
    public Component.BaselineResizeBehavior getBaselineResizeBehavior(
            JComponent c) {
        if (c == null) {
            throw new NullPointerException("Component must be non-null");
        }
        return Component.BaselineResizeBehavior.OTHER;
    }

    /**
     * Returns the number of accessible children in the object.  If all
     * of the children of this object implement <code>Accessible</code>,
     * this
     * method should return the number of children of this object.
     * UIs might wish to override this if they present areas on the
     * screen that can be viewed as components, but actual components
     * are not used for presenting those areas.
     *
     * Note: As of v1.3, it is recommended that developers call
     * <code>Component.AccessibleAWTComponent.getAccessibleChildrenCount()</code> instead
     * of this method.
     *
     * <p>
     *  返回对象中可访问的子项数。如果此对象的所有子实现<code> Accessible </code>,此方法应返回此对象的子项数。
     *  UI可能希望覆盖这一点,如果它们在屏幕上呈现可以被视为组件的区域,但实际组件不用于呈现这些区域。
     * 
     *  注意：自v1.3起,建议开发人员调用<code> Component.AccessibleAWTComponent.getAccessibleChildrenCount()</code>而不是此方法。
     * 
     * 
     * @see #getAccessibleChild
     * @return the number of accessible children in the object
     */
    public int getAccessibleChildrenCount(JComponent c) {
        return SwingUtilities.getAccessibleChildrenCount(c);
    }

    /**
     * Returns the <code>i</code>th <code>Accessible</code> child of the object.
     * UIs might need to override this if they present areas on the
     * screen that can be viewed as components, but actual components
     * are not used for presenting those areas.
     *
     * <p>
     *
     * Note: As of v1.3, it is recommended that developers call
     * <code>Component.AccessibleAWTComponent.getAccessibleChild()</code> instead of
     * this method.
     *
     * <p>
     *  返回对象的<code> i </code> th <code> Accessible </code>子对象。
     * 如果用户界面在屏幕上显示可以视为组件的区域,则UI可能需要覆盖,但实际组件不用于显示这些区域。
     * 
     * <p>
     * 
     * @see #getAccessibleChildrenCount
     * @param i zero-based index of child
     * @return the <code>i</code>th <code>Accessible</code> child of the object
     */
    public Accessible getAccessibleChild(JComponent c, int i) {
        return SwingUtilities.getAccessibleChild(c, i);
    }
}
