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
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.io.PrintStream;

/**
 * A layout manager that allows multiple components to be laid out either
 * vertically or horizontally. The components will not wrap so, for
 * example, a vertical arrangement of components will stay vertically
 * arranged when the frame is resized.
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 *    <TR>
 *      <TD ALIGN="CENTER">
 *         <P STYLE="TEXT-ALIGN:CENTER"><IMG SRC="doc-files/BoxLayout-1.gif"
 *          alt="The following text describes this graphic."
 *          WIDTH="191" HEIGHT="201" STYLE="FLOAT:BOTTOM; BORDER:0">
 *      </TD>
 *    </TR>
 * </TABLE>
 * <p>
 * Nesting multiple panels with different combinations of horizontal and
 * vertical gives an effect similar to GridBagLayout, without the
 * complexity. The diagram shows two panels arranged horizontally, each
 * of which contains 3 components arranged vertically.
 *
 * <p> The BoxLayout manager is constructed with an axis parameter that
 * specifies the type of layout that will be done. There are four choices:
 *
 * <blockquote><b><tt>X_AXIS</tt></b> - Components are laid out horizontally
 * from left to right.</blockquote>
 *
 * <blockquote><b><tt>Y_AXIS</tt></b> - Components are laid out vertically
 * from top to bottom.</blockquote>
 *
 * <blockquote><b><tt>LINE_AXIS</tt></b> - Components are laid out the way
 * words are laid out in a line, based on the container's
 * <tt>ComponentOrientation</tt> property. If the container's
 * <tt>ComponentOrientation</tt> is horizontal then components are laid out
 * horizontally, otherwise they are laid out vertically.  For horizontal
 * orientations, if the container's <tt>ComponentOrientation</tt> is left to
 * right then components are laid out left to right, otherwise they are laid
 * out right to left. For vertical orientations components are always laid out
 * from top to bottom.</blockquote>
 *
 * <blockquote><b><tt>PAGE_AXIS</tt></b> - Components are laid out the way
 * text lines are laid out on a page, based on the container's
 * <tt>ComponentOrientation</tt> property. If the container's
 * <tt>ComponentOrientation</tt> is horizontal then components are laid out
 * vertically, otherwise they are laid out horizontally.  For horizontal
 * orientations, if the container's <tt>ComponentOrientation</tt> is left to
 * right then components are laid out left to right, otherwise they are laid
 * out right to left.&nbsp; For vertical orientations components are always
 * laid out from top to bottom.</blockquote>
 * <p>
 * For all directions, components are arranged in the same order as they were
 * added to the container.
 * <p>
 * BoxLayout attempts to arrange components
 * at their preferred widths (for horizontal layout)
 * or heights (for vertical layout).
 * For a horizontal layout,
 * if not all the components are the same height,
 * BoxLayout attempts to make all the components
 * as high as the highest component.
 * If that's not possible for a particular component,
 * then BoxLayout aligns that component vertically,
 * according to the component's Y alignment.
 * By default, a component has a Y alignment of 0.5,
 * which means that the vertical center of the component
 * should have the same Y coordinate as
 * the vertical centers of other components with 0.5 Y alignment.
 * <p>
 * Similarly, for a vertical layout,
 * BoxLayout attempts to make all components in the column
 * as wide as the widest component.
 * If that fails, it aligns them horizontally
 * according to their X alignments.  For <code>PAGE_AXIS</code> layout,
 * horizontal alignment is done based on the leading edge of the component.
 * In other words, an X alignment value of 0.0 means the left edge of a
 * component if the container's <code>ComponentOrientation</code> is left to
 * right and it means the right edge of the component otherwise.
 * <p>
 * Instead of using BoxLayout directly, many programs use the Box class.
 * The Box class is a lightweight container that uses a BoxLayout.
 * It also provides handy methods to help you use BoxLayout well.
 * Adding components to multiple nested boxes is a powerful way to get
 * the arrangement you want.
 * <p>
 * For further information and examples see
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
 *  布局管理器允许垂直或水平布置多个组件。组件不会缠绕,例如,当框架调整大小时,组件的垂直布置将保持垂直布置。
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 * <TR>
 * <TD ALIGN="CENTER">
 *  <P STYLE ="TEXT-ALIGN：CENTER"> <IMG SRC ="doc-files / BoxLayout-1.gif"alt ="以下文本描述此图形。
 * WIDTH="191" HEIGHT="201" STYLE="FLOAT:BOTTOM; BORDER:0">
 * </TD>
 * </TR>
 * </TABLE>
 * <p>
 *  使用水平和垂直的不同组合嵌套多个面板会产生类似于GridBagLayout的效果,没有复杂性。该图示出了水平布置的两个面板,每个面板包含垂直布置的3个部件。
 * 
 *  <p> BoxLayout管理器由一个轴参数构成,该参数指定将要执行的布局类型。有四个选择：
 * 
 *  <blockquote> <b> <tt> X_AXIS </tt> </b>  - 组件从左到右水平布局。</blockquote>
 * 
 *  <blockquote> <b> <tt> Y_AXIS </tt> </b>  - 组件从顶部到底部垂直布局。</blockquote>
 * 
 * <blockquote> <b> <tt> LINE_AXIS </tt> </b>  - 组件是根据容器的<tt> ComponentOrientation </tt>属性,将单词排列成一行的。
 * 如果容器的<tt> ComponentOrientation </tt>是水平的,那么组件是水平布局的,否则它们是垂直布局的。
 * 对于水平方向,如果容器的<tt> ComponentOrientation </tt>是从左到右,则组件从左到右排列,否则它们从右到左排列。对于垂直方向,组件始终从上到下排列。
 * </blockquote>。
 * 
 *  <blockquote> <b> <tt> PAGE_AXIS </tt> </b>  - 组件是根据容器的<tt> ComponentOrientation </tt>属性以文本行布局在页面上的方式
 * 布局的。
 * 如果容器的<tt> ComponentOrientation </tt>是水平的,那么组件是垂直布局的,否则它们是水平布局的。
 * 对于水平方向,如果容器的<tt> ComponentOrientation </tt>是从左到右,则组件从左到右排列,否则它们从右到左排列。对于垂直方向,组件始终从上到下排列。
 * </blockquote>。
 * <p>
 *  对于所有方向,组件以与添加到容器中的顺序相同的顺序排列。
 * <p>
 * BoxLayout尝试按其首选宽度(对于水平布局)或高度(对于垂直布局)排列组件。对于水平布局,如果并非所有组件都具有相同的高度,BoxLayout会尝试使所有组件与最高组件一样高。
 * 如果对于特定组件不可能,BoxLayout将根据组件的Y对齐将组件垂直对齐。默认情况下,组件的Y对齐为0.5,这意味着组件的垂直中心应具有与其他具有0.5 Y对齐的组件的垂直中心相同的Y坐标。
 * <p>
 *  类似地,对于垂直布局,BoxLayout会尝试使列中的所有组件与最宽组件一样宽。如果失败,它们将根据它们的X对齐水平对齐它们。
 * 对于<code> PAGE_AXIS </code>布局,水平对齐是基于组件的前沿完成的。
 * 换句话说,如果容器的<code> ComponentOrientation </code>从左到右,则X对齐值0.0表示组件的左边缘,否则表示组件的右边缘。
 * <p>
 *  而不是直接使用BoxLayout,许多程序使用Box类。 Box类是一个使用BoxLayout的轻量级容器。它还提供了方便的方法,以帮助您使用BoxLayout很好。
 * 将组件添加到多个嵌套的框是一个强大的方式来获得你想要的安排。
 * <p>
 * 有关详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html">如何使用BoxLayout 
 * </a>,<em>中的一节。
 *  Java Tutorial。</em>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see Box
 * @see java.awt.ComponentOrientation
 * @see JComponent#getAlignmentX
 * @see JComponent#getAlignmentY
 *
 * @author   Timothy Prinzing
 */
@SuppressWarnings("serial")
public class BoxLayout implements LayoutManager2, Serializable {

    /**
     * Specifies that components should be laid out left to right.
     * <p>
     *  指定组件应从左到右布局。
     * 
     */
    public static final int X_AXIS = 0;

    /**
     * Specifies that components should be laid out top to bottom.
     * <p>
     *  指定组件应从上到下排列。
     * 
     */
    public static final int Y_AXIS = 1;

    /**
     * Specifies that components should be laid out in the direction of
     * a line of text as determined by the target container's
     * <code>ComponentOrientation</code> property.
     * <p>
     *  指定组件应按照目标容器的<code> ComponentOrientation </code>属性确定的文本行的方向布局。
     * 
     */
    public static final int LINE_AXIS = 2;

    /**
     * Specifies that components should be laid out in the direction that
     * lines flow across a page as determined by the target container's
     * <code>ComponentOrientation</code> property.
     * <p>
     *  指定组件应该按照目标容器的<code> ComponentOrientation </code>属性确定的在页面上流动的方向布局。
     * 
     */
    public static final int PAGE_AXIS = 3;

    /**
     * Creates a layout manager that will lay out components along the
     * given axis.
     *
     * <p>
     *  创建将沿给定轴布置组件的布局管理器。
     * 
     * 
     * @param target  the container that needs to be laid out
     * @param axis  the axis to lay out components along. Can be one of:
     *              <code>BoxLayout.X_AXIS</code>,
     *              <code>BoxLayout.Y_AXIS</code>,
     *              <code>BoxLayout.LINE_AXIS</code> or
     *              <code>BoxLayout.PAGE_AXIS</code>
     *
     * @exception AWTError  if the value of <code>axis</code> is invalid
     */
    @ConstructorProperties({"target", "axis"})
    public BoxLayout(Container target, int axis) {
        if (axis != X_AXIS && axis != Y_AXIS &&
            axis != LINE_AXIS && axis != PAGE_AXIS) {
            throw new AWTError("Invalid axis");
        }
        this.axis = axis;
        this.target = target;
    }

    /**
     * Constructs a BoxLayout that
     * produces debugging messages.
     *
     * <p>
     *  构造一个生成调试消息的BoxLayout。
     * 
     * 
     * @param target  the container that needs to be laid out
     * @param axis  the axis to lay out components along. Can be one of:
     *              <code>BoxLayout.X_AXIS</code>,
     *              <code>BoxLayout.Y_AXIS</code>,
     *              <code>BoxLayout.LINE_AXIS</code> or
     *              <code>BoxLayout.PAGE_AXIS</code>
     *
     * @param dbg  the stream to which debugging messages should be sent,
     *   null if none
     */
    BoxLayout(Container target, int axis, PrintStream dbg) {
        this(target, axis);
        this.dbg = dbg;
    }

    /**
     * Returns the container that uses this layout manager.
     *
     * <p>
     *  返回使用此布局管理器的容器。
     * 
     * 
     * @return the container that uses this layout manager
     *
     * @since 1.6
     */
    public final Container getTarget() {
        return this.target;
    }

    /**
     * Returns the axis that was used to lay out components.
     * Returns one of:
     * <code>BoxLayout.X_AXIS</code>,
     * <code>BoxLayout.Y_AXIS</code>,
     * <code>BoxLayout.LINE_AXIS</code> or
     * <code>BoxLayout.PAGE_AXIS</code>
     *
     * <p>
     *  返回用于布置组件的轴。
     * 返回下列之一：<code> BoxLayout.X_AXIS </code>,<code> BoxLayout.Y_AXIS </code>,<code> BoxLayout.LINE_AXIS </code>
     * 或<code> BoxLayout.PAGE_AXIS </code>。
     *  返回用于布置组件的轴。
     * 
     * 
     * @return the axis that was used to lay out components
     *
     * @since 1.6
     */
    public final int getAxis() {
        return this.axis;
    }

    /**
     * Indicates that a child has changed its layout related information,
     * and thus any cached calculations should be flushed.
     * <p>
     * This method is called by AWT when the invalidate method is called
     * on the Container.  Since the invalidate method may be called
     * asynchronously to the event thread, this method may be called
     * asynchronously.
     *
     * <p>
     * 表示子项已更改其布局相关信息,因此应刷新任何缓存的计算。
     * <p>
     *  当在Container上调用invalidate方法时,此方法由AWT调用。由于invalidate方法可以异步地调用到事件线程,因此可以异步调用此方法。
     * 
     * 
     * @param target  the affected container
     *
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized void invalidateLayout(Container target) {
        checkContainer(target);
        xChildren = null;
        yChildren = null;
        xTotal = null;
        yTotal = null;
    }

    /**
     * Not used by this class.
     *
     * <p>
     *  不被这个类使用。
     * 
     * 
     * @param name the name of the component
     * @param comp the component
     */
    public void addLayoutComponent(String name, Component comp) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Not used by this class.
     *
     * <p>
     *  不被这个类使用。
     * 
     * 
     * @param comp the component
     */
    public void removeLayoutComponent(Component comp) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Not used by this class.
     *
     * <p>
     *  不被这个类使用。
     * 
     * 
     * @param comp the component
     * @param constraints constraints
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Returns the preferred dimensions for this layout, given the components
     * in the specified target container.
     *
     * <p>
     *  返回此布局的首选维度,给定指定目标容器中的组件。
     * 
     * 
     * @param target  the container that needs to be laid out
     * @return the dimensions &gt;= 0 &amp;&amp; &lt;= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see Container
     * @see #minimumLayoutSize
     * @see #maximumLayoutSize
     */
    public Dimension preferredLayoutSize(Container target) {
        Dimension size;
        synchronized(this) {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.preferred, yTotal.preferred);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the minimum dimensions needed to lay out the components
     * contained in the specified target container.
     *
     * <p>
     *  返回布局包含在指定目标容器中的组件所需的最小维度。
     * 
     * 
     * @param target  the container that needs to be laid out
     * @return the dimensions &gt;= 0 &amp;&amp; &lt;= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see #preferredLayoutSize
     * @see #maximumLayoutSize
     */
    public Dimension minimumLayoutSize(Container target) {
        Dimension size;
        synchronized(this) {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.minimum, yTotal.minimum);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the maximum dimensions the target container can use
     * to lay out the components it contains.
     *
     * <p>
     *  返回目标容器可用于布局其包含的组件的最大尺寸。
     * 
     * 
     * @param target  the container that needs to be laid out
     * @return the dimensions &gt;= 0 &amp;&amp; &lt;= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see #preferredLayoutSize
     * @see #minimumLayoutSize
     */
    public Dimension maximumLayoutSize(Container target) {
        Dimension size;
        synchronized(this) {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.maximum, yTotal.maximum);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the alignment along the X axis for the container.
     * If the box is horizontal, the default
     * alignment will be returned. Otherwise, the alignment needed
     * to place the children along the X axis will be returned.
     *
     * <p>
     *  返回容器沿X轴的对齐方式。如果框是水平的,将返回默认对齐方式。否则,将返回沿X轴放置孩子所需的对齐。
     * 
     * 
     * @param target  the container
     * @return the alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized float getLayoutAlignmentX(Container target) {
        checkContainer(target);
        checkRequests();
        return xTotal.alignment;
    }

    /**
     * Returns the alignment along the Y axis for the container.
     * If the box is vertical, the default
     * alignment will be returned. Otherwise, the alignment needed
     * to place the children along the Y axis will be returned.
     *
     * <p>
     *  返回容器沿Y轴的对齐方式。如果框是垂直的,将返回默认对齐方式。否则,将返回沿Y轴放置孩子所需的对齐。
     * 
     * 
     * @param target  the container
     * @return the alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized float getLayoutAlignmentY(Container target) {
        checkContainer(target);
        checkRequests();
        return yTotal.alignment;
    }

    /**
     * Called by the AWT <!-- XXX CHECK! --> when the specified container
     * needs to be laid out.
     *
     * <p>
     *  由AWT召唤<！ -  XXX CHECK！ - >当指定的容器需要布局时。
     * 
     * 
     * @param target  the container to lay out
     *
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public void layoutContainer(Container target) {
        checkContainer(target);
        int nChildren = target.getComponentCount();
        int[] xOffsets = new int[nChildren];
        int[] xSpans = new int[nChildren];
        int[] yOffsets = new int[nChildren];
        int[] ySpans = new int[nChildren];

        Dimension alloc = target.getSize();
        Insets in = target.getInsets();
        alloc.width -= in.left + in.right;
        alloc.height -= in.top + in.bottom;

        // Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
        ComponentOrientation o = target.getComponentOrientation();
        int absoluteAxis = resolveAxis( axis, o );
        boolean ltr = (absoluteAxis != axis) ? o.isLeftToRight() : true;


        // determine the child placements
        synchronized(this) {
            checkRequests();

            if (absoluteAxis == X_AXIS) {
                SizeRequirements.calculateTiledPositions(alloc.width, xTotal,
                                                         xChildren, xOffsets,
                                                         xSpans, ltr);
                SizeRequirements.calculateAlignedPositions(alloc.height, yTotal,
                                                           yChildren, yOffsets,
                                                           ySpans);
            } else {
                SizeRequirements.calculateAlignedPositions(alloc.width, xTotal,
                                                           xChildren, xOffsets,
                                                           xSpans, ltr);
                SizeRequirements.calculateTiledPositions(alloc.height, yTotal,
                                                         yChildren, yOffsets,
                                                         ySpans);
            }
        }

        // flush changes to the container
        for (int i = 0; i < nChildren; i++) {
            Component c = target.getComponent(i);
            c.setBounds((int) Math.min((long) in.left + (long) xOffsets[i], Integer.MAX_VALUE),
                        (int) Math.min((long) in.top + (long) yOffsets[i], Integer.MAX_VALUE),
                        xSpans[i], ySpans[i]);

        }
        if (dbg != null) {
            for (int i = 0; i < nChildren; i++) {
                Component c = target.getComponent(i);
                dbg.println(c.toString());
                dbg.println("X: " + xChildren[i]);
                dbg.println("Y: " + yChildren[i]);
            }
        }

    }

    void checkContainer(Container target) {
        if (this.target != target) {
            throw new AWTError("BoxLayout can't be shared");
        }
    }

    void checkRequests() {
        if (xChildren == null || yChildren == null) {
            // The requests have been invalidated... recalculate
            // the request information.
            int n = target.getComponentCount();
            xChildren = new SizeRequirements[n];
            yChildren = new SizeRequirements[n];
            for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                if (!c.isVisible()) {
                    xChildren[i] = new SizeRequirements(0,0,0, c.getAlignmentX());
                    yChildren[i] = new SizeRequirements(0,0,0, c.getAlignmentY());
                    continue;
                }
                Dimension min = c.getMinimumSize();
                Dimension typ = c.getPreferredSize();
                Dimension max = c.getMaximumSize();
                xChildren[i] = new SizeRequirements(min.width, typ.width,
                                                    max.width,
                                                    c.getAlignmentX());
                yChildren[i] = new SizeRequirements(min.height, typ.height,
                                                    max.height,
                                                    c.getAlignmentY());
            }

            // Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
            int absoluteAxis = resolveAxis(axis,target.getComponentOrientation());

            if (absoluteAxis == X_AXIS) {
                xTotal = SizeRequirements.getTiledSizeRequirements(xChildren);
                yTotal = SizeRequirements.getAlignedSizeRequirements(yChildren);
            } else {
                xTotal = SizeRequirements.getAlignedSizeRequirements(xChildren);
                yTotal = SizeRequirements.getTiledSizeRequirements(yChildren);
            }
        }
    }

    /**
     * Given one of the 4 axis values, resolve it to an absolute axis.
     * The relative axis values, PAGE_AXIS and LINE_AXIS are converted
     * to their absolute couterpart given the target's ComponentOrientation
     * value.  The absolute axes, X_AXIS and Y_AXIS are returned unmodified.
     *
     * <p>
     * 给定4个轴值中的一个,将其解析为绝对轴。相对轴值PAGE_AXIS和LINE_AXIS被转换为给定目标的ComponentOrientation值的绝对couterpart。
     * 绝对轴X_AXIS和Y_AXIS未修改地返回。
     * 
     * @param axis the axis to resolve
     * @param o the ComponentOrientation to resolve against
     * @return the resolved axis
     */
    private int resolveAxis( int axis, ComponentOrientation o ) {
        int absoluteAxis;
        if( axis == LINE_AXIS ) {
            absoluteAxis = o.isHorizontal() ? X_AXIS : Y_AXIS;
        } else if( axis == PAGE_AXIS ) {
            absoluteAxis = o.isHorizontal() ? Y_AXIS : X_AXIS;
        } else {
            absoluteAxis = axis;
        }
        return absoluteAxis;
   }


    private int axis;
    private Container target;

    private transient SizeRequirements[] xChildren;
    private transient SizeRequirements[] yChildren;
    private transient SizeRequirements xTotal;
    private transient SizeRequirements yTotal;

    private transient PrintStream dbg;
}
