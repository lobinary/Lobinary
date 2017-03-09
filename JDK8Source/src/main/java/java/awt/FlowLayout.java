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

import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A flow layout arranges components in a directional flow, much
 * like lines of text in a paragraph. The flow direction is
 * determined by the container's <code>componentOrientation</code>
 * property and may be one of two values:
 * <ul>
 * <li><code>ComponentOrientation.LEFT_TO_RIGHT</code>
 * <li><code>ComponentOrientation.RIGHT_TO_LEFT</code>
 * </ul>
 * Flow layouts are typically used
 * to arrange buttons in a panel. It arranges buttons
 * horizontally until no more buttons fit on the same line.
 * The line alignment is determined by the <code>align</code>
 * property. The possible values are:
 * <ul>
 * <li>{@link #LEFT LEFT}
 * <li>{@link #RIGHT RIGHT}
 * <li>{@link #CENTER CENTER}
 * <li>{@link #LEADING LEADING}
 * <li>{@link #TRAILING TRAILING}
 * </ul>
 * <p>
 * For example, the following picture shows an applet using the flow
 * layout manager (its default layout manager) to position three buttons:
 * <p>
 * <img src="doc-files/FlowLayout-1.gif"
 * ALT="Graphic of Layout for Three Buttons"
 * style="float:center; margin: 7px 10px;">
 * <p>
 * Here is the code for this applet:
 *
 * <hr><blockquote><pre>
 * import java.awt.*;
 * import java.applet.Applet;
 *
 * public class myButtons extends Applet {
 *     Button button1, button2, button3;
 *     public void init() {
 *         button1 = new Button("Ok");
 *         button2 = new Button("Open");
 *         button3 = new Button("Close");
 *         add(button1);
 *         add(button2);
 *         add(button3);
 *     }
 * }
 * </pre></blockquote><hr>
 * <p>
 * A flow layout lets each component assume its natural (preferred) size.
 *
 * <p>
 *  流布局在定向流中排列组件,很像段落中的文本行。流向由容器的<code> componentOrientation </code>属性决定,可以是以下两个值之一：
 * <ul>
 *  <li> <code> ComponentOrientation.LEFT_TO_RIGHT </code> <li> <code> ComponentOrientation.RIGHT_TO_LEF
 * T </code>。
 * </ul>
 *  流布局通常用于布置面板中的按钮。它水平排列按钮,直到没有更多的按钮适合在同一行。线对齐由<code> align </code>属性决定。可能的值为：
 * <ul>
 *  <li> {@ link #LEFT LEFT} <li> {@ link #RIGHT RIGHT} <li> {@ link #CENTER CENTER} <li> {@ link #LEADING LEADING}
 *  <li> {@ link #TRAILING TRAILING}。
 * </ul>
 * <p>
 *  例如,下图显示了一个使用流布局管理器(其默认布局管理器)定位三个按钮的小程序：
 * <p>
 *  <img src ="doc-files / FlowLayout-1.gif"ALT ="三个按钮的布局图形"
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  这里是这个applet的代码：
 * 
 *  <hr> <blockquote> <pre> import java.awt。*; import java.applet.Applet;
 * 
 *  public class myButtons extends Applet {Button button1,button2,button3; public void init(){button1 = new Button("Ok"); button2 = new Button("Open"); button3 = new Button("Close"); add(button1); add(button2); add(button3); }
 * } </pre> </blockquote> <hr>。
 * <p>
 *  流布局允许每个组件采用其自然(首选)大小。
 * 
 * 
 * @author      Arthur van Hoff
 * @author      Sami Shaio
 * @since       JDK1.0
 * @see ComponentOrientation
 */
public class FlowLayout implements LayoutManager, java.io.Serializable {

    /**
     * This value indicates that each row of components
     * should be left-justified.
     * <p>
     * 此值指示每行组件应左对齐。
     * 
     */
    public static final int LEFT        = 0;

    /**
     * This value indicates that each row of components
     * should be centered.
     * <p>
     *  此值指示组件的每一行应居中。
     * 
     */
    public static final int CENTER      = 1;

    /**
     * This value indicates that each row of components
     * should be right-justified.
     * <p>
     *  此值表示每行组件应右对齐。
     * 
     */
    public static final int RIGHT       = 2;

    /**
     * This value indicates that each row of components
     * should be justified to the leading edge of the container's
     * orientation, for example, to the left in left-to-right orientations.
     *
     * <p>
     *  此值指示每个组件的行应该对齐到容器的方向的前边缘,例如,以从左到右的方向向左。
     * 
     * 
     * @see     java.awt.Component#getComponentOrientation
     * @see     java.awt.ComponentOrientation
     * @since   1.2
     */
    public static final int LEADING     = 3;

    /**
     * This value indicates that each row of components
     * should be justified to the trailing edge of the container's
     * orientation, for example, to the right in left-to-right orientations.
     *
     * <p>
     *  此值指示每个组件行应该对齐到容器方向的后缘,例如,从左到右的方向向右。
     * 
     * 
     * @see     java.awt.Component#getComponentOrientation
     * @see     java.awt.ComponentOrientation
     * @since   1.2
     */
    public static final int TRAILING = 4;

    /**
     * <code>align</code> is the property that determines
     * how each row distributes empty space.
     * It can be one of the following values:
     * <ul>
     * <li><code>LEFT</code>
     * <li><code>RIGHT</code>
     * <li><code>CENTER</code>
     * </ul>
     *
     * <p>
     *  <code> align </code>是确定每行如何分配空白空间的属性。它可以是以下值之一：
     * <ul>
     *  <li> <code> LEFT </code> <li> <code> RIGHT </code> <li> <code> CENTER </code>
     * </ul>
     * 
     * 
     * @serial
     * @see #getAlignment
     * @see #setAlignment
     */
    int align;          // This is for 1.1 serialization compatibility

    /**
     * <code>newAlign</code> is the property that determines
     * how each row distributes empty space for the Java 2 platform,
     * v1.2 and greater.
     * It can be one of the following three values:
     * <ul>
     * <li><code>LEFT</code>
     * <li><code>RIGHT</code>
     * <li><code>CENTER</code>
     * <li><code>LEADING</code>
     * <li><code>TRAILING</code>
     * </ul>
     *
     * <p>
     *  <code> newAlign </code>是确定每个行如何分配Java 2平台(v1.2及更高版本)的空白空间的属性。它可以是以下三个值之一：
     * <ul>
     *  <li> <code> LEFT </code> <li> <code> RIGHT </code> <li> <code> CENTER </code> <li> <code> LEADING </code>
     *  <li> <code> TRAILING </code>。
     * </ul>
     * 
     * 
     * @serial
     * @since 1.2
     * @see #getAlignment
     * @see #setAlignment
     */
    int newAlign;       // This is the one we actually use

    /**
     * The flow layout manager allows a seperation of
     * components with gaps.  The horizontal gap will
     * specify the space between components and between
     * the components and the borders of the
     * <code>Container</code>.
     *
     * <p>
     *  流布局管理器允许具有间隙的组件的分隔。水平间隙将指定组件之间以及组件和<code> Container </code>的边框之间的空间。
     * 
     * 
     * @serial
     * @see #getHgap()
     * @see #setHgap(int)
     */
    int hgap;

    /**
     * The flow layout manager allows a seperation of
     * components with gaps.  The vertical gap will
     * specify the space between rows and between the
     * the rows and the borders of the <code>Container</code>.
     *
     * <p>
     *  流布局管理器允许具有间隙的组件的分隔。垂直间隙将指定行之间以及行和<code> Container </code>之间的边界之间的间距。
     * 
     * 
     * @serial
     * @see #getHgap()
     * @see #setHgap(int)
     */
    int vgap;

    /**
     * If true, components will be aligned on their baseline.
     * <p>
     * 如果为true,组件将在其基线上对齐。
     * 
     */
    private boolean alignOnBaseline;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -7262534875583282631L;

    /**
     * Constructs a new <code>FlowLayout</code> with a centered alignment and a
     * default 5-unit horizontal and vertical gap.
     * <p>
     *  构造一个新的<code> FlowLayout </code>,其中心对齐方式和默认的5单元水平和垂直间隙。
     * 
     */
    public FlowLayout() {
        this(CENTER, 5, 5);
    }

    /**
     * Constructs a new <code>FlowLayout</code> with the specified
     * alignment and a default 5-unit horizontal and vertical gap.
     * The value of the alignment argument must be one of
     * <code>FlowLayout.LEFT</code>, <code>FlowLayout.RIGHT</code>,
     * <code>FlowLayout.CENTER</code>, <code>FlowLayout.LEADING</code>,
     * or <code>FlowLayout.TRAILING</code>.
     * <p>
     *  使用指定的对齐方式和默认的5单位水平和垂直间隙构造新的<code> FlowLayout </code>。
     *  align参数的值必须是<code> FlowLayout.LEFT </code>,<code> FlowLayout.RIGHT </code>,<code> FlowLayout.CENTER 
     * </code>,<code> FlowLayout.LEADING < / code>或<code> FlowLayout.TRAILING </code>。
     *  使用指定的对齐方式和默认的5单位水平和垂直间隙构造新的<code> FlowLayout </code>。
     * 
     * 
     * @param align the alignment value
     */
    public FlowLayout(int align) {
        this(align, 5, 5);
    }

    /**
     * Creates a new flow layout manager with the indicated alignment
     * and the indicated horizontal and vertical gaps.
     * <p>
     * The value of the alignment argument must be one of
     * <code>FlowLayout.LEFT</code>, <code>FlowLayout.RIGHT</code>,
     * <code>FlowLayout.CENTER</code>, <code>FlowLayout.LEADING</code>,
     * or <code>FlowLayout.TRAILING</code>.
     * <p>
     *  创建具有指示的对齐和指示的水平和垂直间隙的新流布局管理器。
     * <p>
     *  align参数的值必须是<code> FlowLayout.LEFT </code>,<code> FlowLayout.RIGHT </code>,<code> FlowLayout.CENTER 
     * </code>,<code> FlowLayout.LEADING < / code>或<code> FlowLayout.TRAILING </code>。
     * 
     * 
     * @param      align   the alignment value
     * @param      hgap    the horizontal gap between components
     *                     and between the components and the
     *                     borders of the <code>Container</code>
     * @param      vgap    the vertical gap between components
     *                     and between the components and the
     *                     borders of the <code>Container</code>
     */
    public FlowLayout(int align, int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
        setAlignment(align);
    }

    /**
     * Gets the alignment for this layout.
     * Possible values are <code>FlowLayout.LEFT</code>,
     * <code>FlowLayout.RIGHT</code>, <code>FlowLayout.CENTER</code>,
     * <code>FlowLayout.LEADING</code>,
     * or <code>FlowLayout.TRAILING</code>.
     * <p>
     *  获取此布局的对齐方式。
     * 可能的值为<code> FlowLayout.LEFT </code>,<code> FlowLayout.RIGHT </code>,<code> FlowLayout.CENTER </code>,
     * <code> FlowLayout.LEADING </code> > FlowLayout.TRAILING </code>。
     *  获取此布局的对齐方式。
     * 
     * 
     * @return     the alignment value for this layout
     * @see        java.awt.FlowLayout#setAlignment
     * @since      JDK1.1
     */
    public int getAlignment() {
        return newAlign;
    }

    /**
     * Sets the alignment for this layout.
     * Possible values are
     * <ul>
     * <li><code>FlowLayout.LEFT</code>
     * <li><code>FlowLayout.RIGHT</code>
     * <li><code>FlowLayout.CENTER</code>
     * <li><code>FlowLayout.LEADING</code>
     * <li><code>FlowLayout.TRAILING</code>
     * </ul>
     * <p>
     *  设置此布局的对齐方式。可能的值为
     * <ul>
     *  <li> <code> FlowLayout.LEFT </code> <li> <code> FlowLayout.RIGHT </code> <li> <code> FlowLayout.CENT
     * ER </code>代码> <li> <code> FlowLayout.TRAILING </code>。
     * </ul>
     * 
     * @param      align one of the alignment values shown above
     * @see        #getAlignment()
     * @since      JDK1.1
     */
    public void setAlignment(int align) {
        this.newAlign = align;

        // this.align is used only for serialization compatibility,
        // so set it to a value compatible with the 1.1 version
        // of the class

        switch (align) {
        case LEADING:
            this.align = LEFT;
            break;
        case TRAILING:
            this.align = RIGHT;
            break;
        default:
            this.align = align;
            break;
        }
    }

    /**
     * Gets the horizontal gap between components
     * and between the components and the borders
     * of the <code>Container</code>
     *
     * <p>
     *  获取组件之间以及组件和<code> Container </code>的边框之间的水平间距
     * 
     * 
     * @return     the horizontal gap between components
     *             and between the components and the borders
     *             of the <code>Container</code>
     * @see        java.awt.FlowLayout#setHgap
     * @since      JDK1.1
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * Sets the horizontal gap between components and
     * between the components and the borders of the
     * <code>Container</code>.
     *
     * <p>
     * 设置组件之间以及组件和<code> Container </code>的边框之间的水平间距。
     * 
     * 
     * @param hgap the horizontal gap between components
     *             and between the components and the borders
     *             of the <code>Container</code>
     * @see        java.awt.FlowLayout#getHgap
     * @since      JDK1.1
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * Gets the vertical gap between components and
     * between the components and the borders of the
     * <code>Container</code>.
     *
     * <p>
     *  获取组件之间以及组件和<code> Container </code>的边框之间的垂直间隙。
     * 
     * 
     * @return     the vertical gap between components
     *             and between the components and the borders
     *             of the <code>Container</code>
     * @see        java.awt.FlowLayout#setVgap
     * @since      JDK1.1
     */
    public int getVgap() {
        return vgap;
    }

    /**
     * Sets the vertical gap between components and between
     * the components and the borders of the <code>Container</code>.
     *
     * <p>
     *  设置组件之间以及组件和<code> Container </code>的边框之间的垂直间距。
     * 
     * 
     * @param vgap the vertical gap between components
     *             and between the components and the borders
     *             of the <code>Container</code>
     * @see        java.awt.FlowLayout#getVgap
     * @since      JDK1.1
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**
     * Sets whether or not components should be vertically aligned along their
     * baseline.  Components that do not have a baseline will be centered.
     * The default is false.
     *
     * <p>
     *  设置组件是否应沿其基线垂直对齐。没有基线的组件将居中。默认值为false。
     * 
     * 
     * @param alignOnBaseline whether or not components should be
     *                        vertically aligned on their baseline
     * @since 1.6
     */
    public void setAlignOnBaseline(boolean alignOnBaseline) {
        this.alignOnBaseline = alignOnBaseline;
    }

    /**
     * Returns true if components are to be vertically aligned along
     * their baseline.  The default is false.
     *
     * <p>
     *  如果组件沿其基线垂直对齐,则返回true。默认值为false。
     * 
     * 
     * @return true if components are to be vertically aligned along
     *              their baseline
     * @since 1.6
     */
    public boolean getAlignOnBaseline() {
        return alignOnBaseline;
    }

    /**
     * Adds the specified component to the layout.
     * Not used by this class.
     * <p>
     *  将指定的组件添加到布局。不被这个类使用。
     * 
     * 
     * @param name the name of the component
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Removes the specified component from the layout.
     * Not used by this class.
     * <p>
     *  从布局中删除指定的组件。不被这个类使用。
     * 
     * 
     * @param comp the component to remove
     * @see       java.awt.Container#removeAll
     */
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Returns the preferred dimensions for this layout given the
     * <i>visible</i> components in the specified target container.
     *
     * <p>
     *  给定指定目标容器中的<i>可见</i>组件时,返回此布局的首选维度。
     * 
     * 
     * @param target the container that needs to be laid out
     * @return    the preferred dimensions to lay out the
     *            subcomponents of the specified container
     * @see Container
     * @see #minimumLayoutSize
     * @see       java.awt.Container#getPreferredSize
     */
    public Dimension preferredLayoutSize(Container target) {
      synchronized (target.getTreeLock()) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        boolean firstVisibleComponent = true;
        boolean useBaseline = getAlignOnBaseline();
        int maxAscent = 0;
        int maxDescent = 0;

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                dim.height = Math.max(dim.height, d.height);
                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.width += hgap;
                }
                dim.width += d.width;
                if (useBaseline) {
                    int baseline = m.getBaseline(d.width, d.height);
                    if (baseline >= 0) {
                        maxAscent = Math.max(maxAscent, baseline);
                        maxDescent = Math.max(maxDescent, d.height - baseline);
                    }
                }
            }
        }
        if (useBaseline) {
            dim.height = Math.max(maxAscent + maxDescent, dim.height);
        }
        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + hgap*2;
        dim.height += insets.top + insets.bottom + vgap*2;
        return dim;
      }
    }

    /**
     * Returns the minimum dimensions needed to layout the <i>visible</i>
     * components contained in the specified target container.
     * <p>
     *  返回布局包含在指定目标容器中的<i>可见</i>组件所需的最小尺寸。
     * 
     * 
     * @param target the container that needs to be laid out
     * @return    the minimum dimensions to lay out the
     *            subcomponents of the specified container
     * @see #preferredLayoutSize
     * @see       java.awt.Container
     * @see       java.awt.Container#doLayout
     */
    public Dimension minimumLayoutSize(Container target) {
      synchronized (target.getTreeLock()) {
        boolean useBaseline = getAlignOnBaseline();
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        int maxAscent = 0;
        int maxDescent = 0;
        boolean firstVisibleComponent = true;

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.visible) {
                Dimension d = m.getMinimumSize();
                dim.height = Math.max(dim.height, d.height);
                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.width += hgap;
                }
                dim.width += d.width;
                if (useBaseline) {
                    int baseline = m.getBaseline(d.width, d.height);
                    if (baseline >= 0) {
                        maxAscent = Math.max(maxAscent, baseline);
                        maxDescent = Math.max(maxDescent,
                                              dim.height - baseline);
                    }
                }
}
}

        if (useBaseline) {
            dim.height = Math.max(maxAscent + maxDescent, dim.height);
        }

        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + hgap*2;
        dim.height += insets.top + insets.bottom + vgap*2;
        return dim;





      }
    }

    /**
     * Centers the elements in the specified row, if there is any slack.
     * <p>
     *  将元素居中在指定行中,如果有任何松弛。
     * 
     * 
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param rowStart the beginning of the row
     * @param rowEnd the the ending of the row
     * @param useBaseline Whether or not to align on baseline.
     * @param ascent Ascent for the components. This is only valid if
     *               useBaseline is true.
     * @param descent Ascent for the components. This is only valid if
     *               useBaseline is true.
     * @return actual row height
     */
    private int moveComponents(Container target, int x, int y, int width, int height,
                                int rowStart, int rowEnd, boolean ltr,
                                boolean useBaseline, int[] ascent,
                                int[] descent) {
        switch (newAlign) {
        case LEFT:
            x += ltr ? 0 : width;
            break;
        case CENTER:
            x += width / 2;
            break;
        case RIGHT:
            x += ltr ? width : 0;
            break;
        case LEADING:
            break;
        case TRAILING:
            x += width;
            break;
        }
        int maxAscent = 0;
        int nonbaselineHeight = 0;
        int baselineOffset = 0;
        if (useBaseline) {
            int maxDescent = 0;
            for (int i = rowStart ; i < rowEnd ; i++) {
                Component m = target.getComponent(i);
                if (m.visible) {
                    if (ascent[i] >= 0) {
                        maxAscent = Math.max(maxAscent, ascent[i]);
                        maxDescent = Math.max(maxDescent, descent[i]);
                    }
                    else {
                        nonbaselineHeight = Math.max(m.getHeight(),
                                                     nonbaselineHeight);
                    }
                }
            }
            height = Math.max(maxAscent + maxDescent, nonbaselineHeight);
            baselineOffset = (height - maxAscent - maxDescent) / 2;
        }
        for (int i = rowStart ; i < rowEnd ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                int cy;
                if (useBaseline && ascent[i] >= 0) {
                    cy = y + baselineOffset + maxAscent - ascent[i];
                }
                else {
                    cy = y + (height - m.height) / 2;
                }
                if (ltr) {
                    m.setLocation(x, cy);
                } else {
                    m.setLocation(target.width - x - m.width, cy);
                }
                x += m.width + hgap;
            }
        }
        return height;
    }

    /**
     * Lays out the container. This method lets each
     * <i>visible</i> component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the alignment of
     * this <code>FlowLayout</code> object.
     *
     * <p>
     *  放出容器。该方法通过重新塑造目标容器中的组件,以便满足此<code> FlowLayout </code>对象的对齐,使每个<i>可见</i>组件取其首选大小。
     * 
     * 
     * @param target the specified component being laid out
     * @see Container
     * @see       java.awt.Container#doLayout
     */
    public void layoutContainer(Container target) {
      synchronized (target.getTreeLock()) {
        Insets insets = target.getInsets();
        int maxwidth = target.width - (insets.left + insets.right + hgap*2);
        int nmembers = target.getComponentCount();
        int x = 0, y = insets.top + vgap;
        int rowh = 0, start = 0;

        boolean ltr = target.getComponentOrientation().isLeftToRight();

        boolean useBaseline = getAlignOnBaseline();
        int[] ascent = null;
        int[] descent = null;

        if (useBaseline) {
            ascent = new int[nmembers];
            descent = new int[nmembers];
        }

        for (int i = 0 ; i < nmembers ; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                m.setSize(d.width, d.height);

                if (useBaseline) {
                    int baseline = m.getBaseline(d.width, d.height);
                    if (baseline >= 0) {
                        ascent[i] = baseline;
                        descent[i] = d.height - baseline;
                    }
                    else {
                        ascent[i] = -1;
                    }
                }
                if ((x == 0) || ((x + d.width) <= maxwidth)) {
                    if (x > 0) {
                        x += hgap;
                    }
                    x += d.width;
                    rowh = Math.max(rowh, d.height);
                } else {
                    rowh = moveComponents(target, insets.left + hgap, y,
                                   maxwidth - x, rowh, start, i, ltr,
                                   useBaseline, ascent, descent);
                    x = d.width;
                    y += vgap + rowh;
                    rowh = d.height;
                    start = i;
                }
            }
        }
        moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh,
                       start, nmembers, ltr, useBaseline, ascent, descent);
      }
    }

    //
    // the internal serial version which says which version was written
    // - 0 (default) for versions before the Java 2 platform, v1.2
    // - 1 for version >= Java 2 platform v1.2, which includes "newAlign" field
    //
    private static final int currentSerialVersion = 1;
    /**
     * This represent the <code>currentSerialVersion</code>
     * which is bein used.  It will be one of two values :
     * <code>0</code> versions before Java 2 platform v1.2..
     * <code>1</code> versions after  Java 2 platform v1.2..
     *
     * <p>
     * 这代表正在使用的<code> currentSerialVersion </code>。它将是以下两个值之一：Java 2平台v1.2之前的<code> 0 </code>版本。
     * 在Java 2平台v1.2之后的<code> 1 </code>版本。
     * 
     * 
     * @serial
     * @since 1.2
     */
    private int serialVersionOnStream = currentSerialVersion;

    /**
     * Reads this object out of a serialization stream, handling
     * objects written by older versions of the class that didn't contain all
     * of the fields we use now..
     * <p>
     *  从序列化流中读取此对象,处理由不包含我们现在使用的所有字段的较旧版本的类编写的对象。
     * 
     */
    private void readObject(ObjectInputStream stream)
         throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        if (serialVersionOnStream < 1) {
            // "newAlign" field wasn't present, so use the old "align" field.
            setAlignment(this.align);
        }
        serialVersionOnStream = currentSerialVersion;
    }

    /**
     * Returns a string representation of this <code>FlowLayout</code>
     * object and its values.
     * <p>
     *  返回此<> FlowLayout </code>对象及其值的字符串表示形式。
     * 
     * @return     a string representation of this layout
     */
    public String toString() {
        String str = "";
        switch (align) {
          case LEFT:        str = ",align=left"; break;
          case CENTER:      str = ",align=center"; break;
          case RIGHT:       str = ",align=right"; break;
          case LEADING:     str = ",align=leading"; break;
          case TRAILING:    str = ",align=trailing"; break;
        }
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + str + "]";
    }


}
