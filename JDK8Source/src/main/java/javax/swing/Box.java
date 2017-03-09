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
import java.beans.ConstructorProperties;
import java.util.Locale;
import java.io.Serializable;
import javax.accessibility.*;

/**
 * A lightweight container
 * that uses a BoxLayout object as its layout manager.
 * Box provides several class methods
 * that are useful for containers using BoxLayout --
 * even non-Box containers.
 *
 * <p>
 * The <code>Box</code> class can create several kinds
 * of invisible components
 * that affect layout:
 * glue, struts, and rigid areas.
 * If all the components your <code>Box</code> contains
 * have a fixed size,
 * you might want to use a glue component
 * (returned by <code>createGlue</code>)
 * to control the components' positions.
 * If you need a fixed amount of space between two components,
 * try using a strut
 * (<code>createHorizontalStrut</code> or <code>createVerticalStrut</code>).
 * If you need an invisible component
 * that always takes up the same amount of space,
 * get it by invoking <code>createRigidArea</code>.
 * <p>
 * If you are implementing a <code>BoxLayout</code> you
 * can find further information and examples in
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html">How to Use BoxLayout</a>,
 * a section in <em>The Java Tutorial.</em>
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
 *  一个使用BoxLayout对象作为其布局管理器的轻量级容器。 Box提供了几个类方法,这些方法对使用BoxLayout(甚至非Box)容器的容器非常有用。
 * 
 * <p>
 *  <code> Box </code>类可以创建几种影响布局的不可见组件：粘连,struts和刚性区域。
 * 如果你的<code> Box </code>包含的所有组件都有固定的大小,你可能想使用一个粘合组件(由<code> createGlue </code>返回)来控制组件的位置。
 * 如果两个组件之间需要固定的空间量,请尝试使用strut(<code> createHorizo​​ntalStrut </code>或<code> createVerticalStrut </code>
 * )。
 * 如果你的<code> Box </code>包含的所有组件都有固定的大小,你可能想使用一个粘合组件(由<code> createGlue </code>返回)来控制组件的位置。
 * 如果您需要一个总是占用相同空间量的不可见组件,请通过调用<code> createRigidArea </code>获取它。
 * <p>
 *  如果您正在实施<code> BoxLayout </code>,您可以在<a href="https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html">
 * 方法中找到更多信息和示例使用BoxLayout </a>,<em> Java教程</em>中的一个部分。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see BoxLayout
 *
 * @author  Timothy Prinzing
 */
@SuppressWarnings("serial")
public class Box extends JComponent implements Accessible {

    /**
     * Creates a <code>Box</code> that displays its components
     * along the the specified axis.
     *
     * <p>
     *  创建一个<code> Box </code>,沿指定的轴显示其组件。
     * 
     * 
     * @param axis  can be {@link BoxLayout#X_AXIS},
     *              {@link BoxLayout#Y_AXIS},
     *              {@link BoxLayout#LINE_AXIS} or
     *              {@link BoxLayout#PAGE_AXIS}.
     * @throws AWTError if the <code>axis</code> is invalid
     * @see #createHorizontalBox
     * @see #createVerticalBox
     */
    public Box(int axis) {
        super();
        super.setLayout(new BoxLayout(this, axis));
    }

    /**
     * Creates a <code>Box</code> that displays its components
     * from left to right. If you want a <code>Box</code> that
     * respects the component orientation you should create the
     * <code>Box</code> using the constructor and pass in
     * <code>BoxLayout.LINE_AXIS</code>, eg:
     * <pre>
     *   Box lineBox = new Box(BoxLayout.LINE_AXIS);
     * </pre>
     *
     * <p>
     *  创建一个从左到右显示其组件的<code> Box </code>。
     * 如果您想要一个尊重组件方向的<code> Box </code>,您应该使用构造函数创建<code> Box </code>,并传入<code> BoxLayout.LINE_AXIS </code>,
     * 例如：。
     *  创建一个从左到右显示其组件的<code> Box </code>。
     * <pre>
     *  Box lineBox = new Box(BoxLayout.LINE_AXIS);
     * </pre>
     * 
     * 
     * @return the box
     */
    public static Box createHorizontalBox() {
        return new Box(BoxLayout.X_AXIS);
    }

    /**
     * Creates a <code>Box</code> that displays its components
     * from top to bottom. If you want a <code>Box</code> that
     * respects the component orientation you should create the
     * <code>Box</code> using the constructor and pass in
     * <code>BoxLayout.PAGE_AXIS</code>, eg:
     * <pre>
     *   Box lineBox = new Box(BoxLayout.PAGE_AXIS);
     * </pre>
     *
     * <p>
     *  创建一个从顶部到底部显示其组件的<code>框</code>。
     * 如果您想要一个尊重组件方向的<code> Box </code>,您应该使用构造函数创建<code> Box </code>,并传入<code> BoxLayout.PAGE_AXIS </code>,
     * 例如：。
     *  创建一个从顶部到底部显示其组件的<code>框</code>。
     * <pre>
     *  Box lineBox = new Box(BoxLayout.PAGE_AXIS);
     * </pre>
     * 
     * 
     * @return the box
     */
    public static Box createVerticalBox() {
        return new Box(BoxLayout.Y_AXIS);
    }

    /**
     * Creates an invisible component that's always the specified size.
     * <!-- WHEN WOULD YOU USE THIS AS OPPOSED TO A STRUT? -->
     *
     * <p>
     *  创建总是指定大小的不可见组件。
     * <!-- WHEN WOULD YOU USE THIS AS OPPOSED TO A STRUT? -->
     * 
     * 
     * @param d the dimensions of the invisible component
     * @return the component
     * @see #createGlue
     * @see #createHorizontalStrut
     * @see #createVerticalStrut
     */
    public static Component createRigidArea(Dimension d) {
        return new Filler(d, d, d);
    }

    /**
     * Creates an invisible, fixed-width component.
     * In a horizontal box,
     * you typically use this method
     * to force a certain amount of space between two components.
     * In a vertical box,
     * you might use this method
     * to force the box to be at least the specified width.
     * The invisible component has no height
     * unless excess space is available,
     * in which case it takes its share of available space,
     * just like any other component that has no maximum height.
     *
     * <p>
     * 创建不可见的固定宽度组件。在水平框中,通常使用此方法在两个组件之间强制使用一定量的空间。在垂直框中,您可以使用此方法强制框至少为指定的宽度。
     * 除了剩余空间可用之外,不可见组件没有高度,在这种情况下,它占用其可用空间的份额,就像任何其他没有最大高度的组件一样。
     * 
     * 
     * @param width the width of the invisible component, in pixels &gt;= 0
     * @return the component
     * @see #createVerticalStrut
     * @see #createGlue
     * @see #createRigidArea
     */
    public static Component createHorizontalStrut(int width) {
        return new Filler(new Dimension(width,0), new Dimension(width,0),
                          new Dimension(width, Short.MAX_VALUE));
    }

    /**
     * Creates an invisible, fixed-height component.
     * In a vertical box,
     * you typically use this method
     * to force a certain amount of space between two components.
     * In a horizontal box,
     * you might use this method
     * to force the box to be at least the specified height.
     * The invisible component has no width
     * unless excess space is available,
     * in which case it takes its share of available space,
     * just like any other component that has no maximum width.
     *
     * <p>
     *  创建不可见的固定高度组件。在垂直框中,通常使用此方法在两个组件之间强制一定量的空间。在水平框中,您可以使用此方法强制框至少为指定的高度。
     * 除了剩余空间可用之外,不可见组件没有宽度,在这种情况下,它占用其可用空间的份额,就像没有最大宽度的任何其他组件一样。
     * 
     * 
     * @param height the height of the invisible component, in pixels &gt;= 0
     * @return the component
     * @see #createHorizontalStrut
     * @see #createGlue
     * @see #createRigidArea
     */
    public static Component createVerticalStrut(int height) {
        return new Filler(new Dimension(0,height), new Dimension(0,height),
                          new Dimension(Short.MAX_VALUE, height));
    }

    /**
     * Creates an invisible "glue" component
     * that can be useful in a Box
     * whose visible components have a maximum width
     * (for a horizontal box)
     * or height (for a vertical box).
     * You can think of the glue component
     * as being a gooey substance
     * that expands as much as necessary
     * to fill the space between its neighboring components.
     *
     * <p>
     *
     * For example, suppose you have
     * a horizontal box that contains two fixed-size components.
     * If the box gets extra space,
     * the fixed-size components won't become larger,
    * so where does the extra space go?
     * Without glue,
     * the extra space goes to the right of the second component.
     * If you put glue between the fixed-size components,
     * then the extra space goes there.
     * If you put glue before the first fixed-size component,
     * the extra space goes there,
     * and the fixed-size components are shoved against the right
     * edge of the box.
     * If you put glue before the first fixed-size component
     * and after the second fixed-size component,
     * the fixed-size components are centered in the box.
     *
     * <p>
     *
     * To use glue,
     * call <code>Box.createGlue</code>
     * and add the returned component to a container.
     * The glue component has no minimum or preferred size,
     * so it takes no space unless excess space is available.
     * If excess space is available,
     * then the glue component takes its share of available
     * horizontal or vertical space,
     * just like any other component that has no maximum width or height.
     *
     * <p>
     *  创建一个不可见的"胶水"组件,在可见组件具有最大宽度(对于水平框)或高度(对于垂直框)的框中可以是有用的。你可以认为胶水组件是一种粘性物质,它可以根据需要进行扩展,以填充相邻组件之间的空间。
     * 
     * <p>
     * 
     * 例如,假设有一个水平框包含两个固定大小的组件。如果框获得额外的空间,固定大小的组件不会变大,所以额外的空间在哪里?没有胶水,额外的空间就在第二个组件的右边。
     * 如果你在固定大小的组件之间放置胶水,那么额外的空间就到那里。如果你在第一个固定大小的组件之前放置胶水,额外的空间就会到达那里,固定大小的组件被推到盒子的右边缘。
     * 如果将胶水放在第一个固定大小的组件之前和第二个固定大小的组件之后,则固定大小的组件在框中居中。
     * 
     * <p>
     * 
     *  要使用glue,调用<code> Box.createGlue </code>并将返回的组件添加到容器中。粘合组件没有最小或优选的大小,因此除非有可用的空间,否则不需要空间。
     * 如果剩余空间可用,则粘合组件获得其可用水平或垂直空间的份额,就像没有最大宽度或高度的任何其他组件一样。
     * 
     * 
     * @return the component
     */
    public static Component createGlue() {
        return new Filler(new Dimension(0,0), new Dimension(0,0),
                          new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
    }

    /**
     * Creates a horizontal glue component.
     *
     * <p>
     *  创建水平粘贴组件。
     * 
     * 
     * @return the component
     */
    public static Component createHorizontalGlue() {
        return new Filler(new Dimension(0,0), new Dimension(0,0),
                          new Dimension(Short.MAX_VALUE, 0));
    }

    /**
     * Creates a vertical glue component.
     *
     * <p>
     *  创建垂直粘贴组件。
     * 
     * 
     * @return the component
     */
    public static Component createVerticalGlue() {
        return new Filler(new Dimension(0,0), new Dimension(0,0),
                          new Dimension(0, Short.MAX_VALUE));
    }

    /**
     * Throws an AWTError, since a Box can use only a BoxLayout.
     *
     * <p>
     *  抛出一个AWTError,因为一个Box可以只使用一个BoxLayout。
     * 
     * 
     * @param l the layout manager to use
     */
    public void setLayout(LayoutManager l) {
        throw new AWTError("Illegal request");
    }

    /**
     * Paints this <code>Box</code>.  If this <code>Box</code> has a UI this
     * method invokes super's implementation, otherwise if this
     * <code>Box</code> is opaque the <code>Graphics</code> is filled
     * using the background.
     *
     * <p>
     *  画这个<code>框</code>。
     * 如果这个<code> Box </code>有一个UI,这个方法调用super的实现,否则如果<code> Box </code>是不透明的,则使用背景填充<code> Graphics </code>
     * 。
     *  画这个<code>框</code>。
     * 
     * 
     * @param g the <code>Graphics</code> to paint to
     * @throws NullPointerException if <code>g</code> is null
     * @since 1.6
     */
    protected void paintComponent(Graphics g) {
        if (ui != null) {
            // On the off chance some one created a UI, honor it
            super.paintComponent(g);
        } else if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    /**
     * An implementation of a lightweight component that participates in
     * layout but has no view.
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
     *  参与布局但没有视图的轻量组件的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    public static class Filler extends JComponent implements Accessible {

        /**
         * Constructor to create shape with the given size ranges.
         *
         * <p>
         *  用于创建具有给定大小范围的形状的构造函数。
         * 
         * 
         * @param min   Minimum size
         * @param pref  Preferred size
         * @param max   Maximum size
         */
        @ConstructorProperties({"minimumSize", "preferredSize", "maximumSize"})
        public Filler(Dimension min, Dimension pref, Dimension max) {
            setMinimumSize(min);
            setPreferredSize(pref);
            setMaximumSize(max);
        }

        /**
         * Change the size requests for this shape.  An invalidate() is
         * propagated upward as a result so that layout will eventually
         * happen with using the new sizes.
         *
         * <p>
         *  更改此形状的大小请求。一个invalidate()作为结果向上传播,因此布局最终会发生与使用新的大小。
         * 
         * 
         * @param min   Value to return for getMinimumSize
         * @param pref  Value to return for getPreferredSize
         * @param max   Value to return for getMaximumSize
         */
        public void changeShape(Dimension min, Dimension pref, Dimension max) {
            setMinimumSize(min);
            setPreferredSize(pref);
            setMaximumSize(max);
            revalidate();
        }

        // ---- Component methods ------------------------------------------

        /**
         * Paints this <code>Filler</code>.  If this
         * <code>Filler</code> has a UI this method invokes super's
         * implementation, otherwise if this <code>Filler</code> is
         * opaque the <code>Graphics</code> is filled using the
         * background.
         *
         * <p>
         *  画这个<code> Filler </code>。
         * 如果这个<code> Filler </code>有一个UI,这个方法调用super的实现,否则如果<code> Filler </code>是不透明的,那么<code> Graphics </code>
         * 。
         *  画这个<code> Filler </code>。
         * 
         * 
         * @param g the <code>Graphics</code> to paint to
         * @throws NullPointerException if <code>g</code> is null
         * @since 1.6
         */
        protected void paintComponent(Graphics g) {
            if (ui != null) {
                // On the off chance some one created a UI, honor it
                super.paintComponent(g);
            } else if (isOpaque()) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }

/////////////////
// Accessibility support for Box$Filler
////////////////

        /**
         * Gets the AccessibleContext associated with this Box.Filler.
         * For box fillers, the AccessibleContext takes the form of an
         * AccessibleBoxFiller.
         * A new AccessibleAWTBoxFiller instance is created if necessary.
         *
         * <p>
         *  获取与此Box.Filler相关联的AccessibleContext。对于盒式填充程序,AccessibleContext采用AccessibleBoxFiller的形式。
         * 如果需要,将创建一个新的AccessibleAWTBoxFiller实例。
         * 
         * 
         * @return an AccessibleBoxFiller that serves as the
         *         AccessibleContext of this Box.Filler.
         */
        public AccessibleContext getAccessibleContext() {
            if (accessibleContext == null) {
                accessibleContext = new AccessibleBoxFiller();
            }
            return accessibleContext;
        }

        /**
         * This class implements accessibility support for the
         * <code>Box.Filler</code> class.
         * <p>
         *  此类实现<code> Box.Filler </code>类的辅助功能支持。
         * 
         */
        @SuppressWarnings("serial")
        protected class AccessibleBoxFiller extends AccessibleAWTComponent {
            // AccessibleContext methods
            //
            /**
             * Gets the role of this object.
             *
             * <p>
             *  获取此对象的作用。
             * 
             * 
             * @return an instance of AccessibleRole describing the role of
             *   the object (AccessibleRole.FILLER)
             * @see AccessibleRole
             */
            public AccessibleRole getAccessibleRole() {
                return AccessibleRole.FILLER;
            }
        }
    }

/////////////////
// Accessibility support for Box
////////////////

    /**
     * Gets the AccessibleContext associated with this Box.
     * For boxes, the AccessibleContext takes the form of an
     * AccessibleBox.
     * A new AccessibleAWTBox instance is created if necessary.
     *
     * <p>
     *  获取与此Box相关联的AccessibleContext。对于框,AccessibleContext采用AccessibleBox的形式。
     * 如果需要,将创建一个新的AccessibleAWTBox实例。
     * 
     * 
     * @return an AccessibleBox that serves as the
     *         AccessibleContext of this Box
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleBox();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Box</code> class.
     * <p>
     *  此类实现<code> Box </code>类的辅助功能支持。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleBox extends AccessibleAWTContainer {
        // AccessibleContext methods
        //
        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         *   object (AccessibleRole.FILLER)
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.FILLER;
        }
    } // inner class AccessibleBox
}
