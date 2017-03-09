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

import java.util.Hashtable;

/**
 * A border layout lays out a container, arranging and resizing
 * its components to fit in five regions:
 * north, south, east, west, and center.
 * Each region may contain no more than one component, and
 * is identified by a corresponding constant:
 * <code>NORTH</code>, <code>SOUTH</code>, <code>EAST</code>,
 * <code>WEST</code>, and <code>CENTER</code>.  When adding a
 * component to a container with a border layout, use one of these
 * five constants, for example:
 * <pre>
 *    Panel p = new Panel();
 *    p.setLayout(new BorderLayout());
 *    p.add(new Button("Okay"), BorderLayout.SOUTH);
 * </pre>
 * As a convenience, <code>BorderLayout</code> interprets the
 * absence of a string specification the same as the constant
 * <code>CENTER</code>:
 * <pre>
 *    Panel p2 = new Panel();
 *    p2.setLayout(new BorderLayout());
 *    p2.add(new TextArea());  // Same as p.add(new TextArea(), BorderLayout.CENTER);
 * </pre>
 * <p>
 * In addition, <code>BorderLayout</code> supports the relative
 * positioning constants, <code>PAGE_START</code>, <code>PAGE_END</code>,
 * <code>LINE_START</code>, and <code>LINE_END</code>.
 * In a container whose <code>ComponentOrientation</code> is set to
 * <code>ComponentOrientation.LEFT_TO_RIGHT</code>, these constants map to
 * <code>NORTH</code>, <code>SOUTH</code>, <code>WEST</code>, and
 * <code>EAST</code>, respectively.
 * <p>
 * For compatibility with previous releases, <code>BorderLayout</code>
 * also includes the relative positioning constants <code>BEFORE_FIRST_LINE</code>,
 * <code>AFTER_LAST_LINE</code>, <code>BEFORE_LINE_BEGINS</code> and
 * <code>AFTER_LINE_ENDS</code>.  These are equivalent to
 * <code>PAGE_START</code>, <code>PAGE_END</code>, <code>LINE_START</code>
 * and <code>LINE_END</code> respectively.  For
 * consistency with the relative positioning constants used by other
 * components, the latter constants are preferred.
 * <p>
 * Mixing both absolute and relative positioning constants can lead to
 * unpredictable results.  If
 * you use both types, the relative constants will take precedence.
 * For example, if you add components using both the <code>NORTH</code>
 * and <code>PAGE_START</code> constants in a container whose
 * orientation is <code>LEFT_TO_RIGHT</code>, only the
 * <code>PAGE_START</code> will be layed out.
 * <p>
 * NOTE: Currently (in the Java 2 platform v1.2),
 * <code>BorderLayout</code> does not support vertical
 * orientations.  The <code>isVertical</code> setting on the container's
 * <code>ComponentOrientation</code> is not respected.
 * <p>
 * The components are laid out according to their
 * preferred sizes and the constraints of the container's size.
 * The <code>NORTH</code> and <code>SOUTH</code> components may
 * be stretched horizontally; the <code>EAST</code> and
 * <code>WEST</code> components may be stretched vertically;
 * the <code>CENTER</code> component may stretch both horizontally
 * and vertically to fill any space left over.
 * <p>
 * Here is an example of five buttons in an applet laid out using
 * the <code>BorderLayout</code> layout manager:
 * <p>
 * <img src="doc-files/BorderLayout-1.gif"
 * alt="Diagram of an applet demonstrating BorderLayout.
 *      Each section of the BorderLayout contains a Button corresponding to its position in the layout, one of:
 *      North, West, Center, East, or South."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * The code for this applet is as follows:
 *
 * <hr><blockquote><pre>
 * import java.awt.*;
 * import java.applet.Applet;
 *
 * public class buttonDir extends Applet {
 *   public void init() {
 *     setLayout(new BorderLayout());
 *     add(new Button("North"), BorderLayout.NORTH);
 *     add(new Button("South"), BorderLayout.SOUTH);
 *     add(new Button("East"), BorderLayout.EAST);
 *     add(new Button("West"), BorderLayout.WEST);
 *     add(new Button("Center"), BorderLayout.CENTER);
 *   }
 * }
 * </pre></blockquote><hr>
 * <p>
 * <p>
 *  边界布局布置一个容器,安排和调整其组件的大小以适应五个区域：北,南,东,西和中心。
 * 每个区域可以包含不多于一个组件,并且由相应的常量来标识：<code> NORTH </code>,<code> SOUTH </code>,<code> EAST </code> / code>和<code>
 *  CENTER </code>。
 *  边界布局布置一个容器,安排和调整其组件的大小以适应五个区域：北,南,东,西和中心。当向带有边框布局的容器添加组件时,请使用这五个常量之一,例如：。
 * <pre>
 *  面板p =新面板(); p.setLayout(new BorderLayout()); p.add(new Button("Okay"),BorderLayout.SOUTH);
 * </pre>
 *  为方便起见,<code> BorderLayout </code>解释不存在与常量<code> CENTER </code>相同的字符串规范：
 * <pre>
 *  面板p2 =新面板(); p2.setLayout(new BorderLayout()); p2.add(new TextArea()); //与p.add相同(new TextArea(),Bor
 * derLayout.CENTER);。
 * </pre>
 * <p>
 *  此外,<code> BorderLayout </code>支持相对定位常数<code> PAGE_START </code>,<code> PAGE_END </code>,<code> LINE_
 * START </code>和<code> LINE_END < / code>。
 * 在<code> ComponentOrientation </code>设置为<code> ComponentOrientation.LEFT_TO_RIGHT </code>的容器中,这些常量映射到<code>
 *  NORTH </code>,<code> SOUTH </code> > WEST </code>和<code> EAST </code>。
 * <p>
 * 为了与先前版本兼容,<code> BorderLayout </code>还包括相对定位常量<code> BEFORE_FIRST_LINE </code>,<code> AFTER_LAST_LINE
 *  </code>,<code> BEFORE_LINE_BEGINS </code> AFTER_LINE_ENDS </code>。
 * 分别等同于<code> PAGE_START </code>,<code> PAGE_END </code>,<code> LINE_START </code>和<code> LINE_END </code>
 * 。
 * 为了与其它组分使用的相对定位常数一致,优选后者常数。
 * <p>
 *  混合绝对和相对定位常数可能导致不可预测的结果。如果使用这两种类型,相对常数将优先。
 * 例如,如果在定向为<code> LEFT_TO_RIGHT </code>的容器中同时使用<code> NORTH </code>和<code> PAGE_START </code>常量添加组件,则只有
 * <code> PAGE_START < / code>将被布局。
 *  混合绝对和相对定位常数可能导致不可预测的结果。如果使用这两种类型,相对常数将优先。
 * <p>
 *  注意：目前(在Java 2平台v1.2中),<code> BorderLayout </code>不支持垂直方向。
 * 不会尊重容器的<code> ComponentOrientation </code>上的<code> isVertical </code>设置。
 * <p>
 *  根据它们的优选尺寸和容器尺寸的约束来布置组件。
 *  <code> NORTH </code>和<code> SOUTH </code>组件可以水平拉伸; <code> EAST </code>和<code> WEST </code>组件可以垂直拉伸; 
 * <code> CENTER </code>组件可以水平和垂直伸展以填充剩余的任何空间。
 *  根据它们的优选尺寸和容器尺寸的约束来布置组件。
 * <p>
 * 下面是使用<code> BorderLayout </code>布局管理器布局的applet中的五个按钮的示例：
 * <p>
 *  <img src ="doc-files / BorderLayout-1.gif"alt ="展示BorderLayout的小程序图.BlackLayout的每个部分包含一个Button,它对应于它在布局中的位置,包括：North,West,东或南"。
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  此小程序的代码如下：
 * 
 *  <hr> <blockquote> <pre> import java.awt。*; import java.applet.Applet;
 * 
 *  public class buttonDir extends Applet {public void init(){setLayout(new BorderLayout()); add(new Button("North"),BorderLayout.NORTH); add(new Button("South"),BorderLayout.SOUTH); add(new Button("East"),BorderLayout.EAST); add(new Button("West"),BorderLayout.WEST); add(new Button("Center"),BorderLayout.CENTER); }
 * } </pre> </blockquote> <hr>。
 * <p>
 * 
 * @author      Arthur van Hoff
 * @see         java.awt.Container#add(String, Component)
 * @see         java.awt.ComponentOrientation
 * @since       JDK1.0
 */
public class BorderLayout implements LayoutManager2,
                                     java.io.Serializable {
    /**
     * Constructs a border layout with the horizontal gaps
     * between components.
     * The horizontal gap is specified by <code>hgap</code>.
     *
     * <p>
     *  构造具有组件之间的水平间隙的边框布局。水平间隙由<code> hgap </code>指定。
     * 
     * 
     * @see #getHgap()
     * @see #setHgap(int)
     *
     * @serial
     */
        int hgap;

    /**
     * Constructs a border layout with the vertical gaps
     * between components.
     * The vertical gap is specified by <code>vgap</code>.
     *
     * <p>
     *  构造具有组件之间的垂直间隙的边框布局。垂直间隙由<code> vgap </code>指定。
     * 
     * 
     * @see #getVgap()
     * @see #setVgap(int)
     * @serial
     */
        int vgap;

    /**
     * Constant to specify components location to be the
     *      north portion of the border layout.
     * <p>
     *  常数指定组件位置为边框布局的北部。
     * 
     * 
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
        Component north;
     /**
     * Constant to specify components location to be the
     *      west portion of the border layout.
     * <p>
     *  常量指定组件位置为边界布局的西部。
     * 
     * 
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
        Component west;
    /**
     * Constant to specify components location to be the
     *      east portion of the border layout.
     * <p>
     *  常量指定组件位置为边框布局的东部。
     * 
     * 
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
        Component east;
    /**
     * Constant to specify components location to be the
     *      south portion of the border layout.
     * <p>
     *  常量指定组件位置为边框布局的南部。
     * 
     * 
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    Component south;
    /**
     * Constant to specify components location to be the
     *      center portion of the border layout.
     * <p>
     *  常量指定要作为边框布局的中心部分的组件位置。
     * 
     * 
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
        Component center;

    /**
     *
     * A relative positioning constant, that can be used instead of
     * north, south, east, west or center.
     * mixing the two types of constants can lead to unpredictable results.  If
     * you use both types, the relative constants will take precedence.
     * For example, if you add components using both the <code>NORTH</code>
     * and <code>BEFORE_FIRST_LINE</code> constants in a container whose
     * orientation is <code>LEFT_TO_RIGHT</code>, only the
     * <code>BEFORE_FIRST_LINE</code> will be layed out.
     * This will be the same for lastLine, firstItem, lastItem.
     * <p>
     * 相对定位常数,可用于代替北,南,东,西或中心。混合两种类型的常量可能导致不可预测的结果。如果使用这两种类型,相对常数将优先。
     * 例如,如果在定向为<code> LEFT_TO_RIGHT </code>的容器中同时使用<code> NORTH </code>和<code> BEFORE_FIRST_LINE </code>常量添
     * 加组件,则只有<code> BEFORE_FIRST_LINE < / code>将被布局。
     * 相对定位常数,可用于代替北,南,东,西或中心。混合两种类型的常量可能导致不可预测的结果。如果使用这两种类型,相对常数将优先。这将是lastLine,firstItem,lastItem相同。
     * 
     * 
     * @serial
     */
    Component firstLine;
     /**
     * A relative positioning constant, that can be used instead of
     * north, south, east, west or center.
     * Please read Description for firstLine.
     * <p>
     *  相对定位常数,可用于代替北,南,东,西或中心。请阅读firstLine的说明。
     * 
     * 
     * @serial
     */
        Component lastLine;
     /**
     * A relative positioning constant, that can be used instead of
     * north, south, east, west or center.
     * Please read Description for firstLine.
     * <p>
     *  相对定位常数,可用于代替北,南,东,西或中心。请阅读firstLine的说明。
     * 
     * 
     * @serial
     */
        Component firstItem;
    /**
     * A relative positioning constant, that can be used instead of
     * north, south, east, west or center.
     * Please read Description for firstLine.
     * <p>
     *  相对定位常数,可用于代替北,南,东,西或中心。请阅读firstLine的说明。
     * 
     * 
     * @serial
     */
        Component lastItem;

    /**
     * The north layout constraint (top of container).
     * <p>
     *  北布局约束(容器顶部)。
     * 
     */
    public static final String NORTH  = "North";

    /**
     * The south layout constraint (bottom of container).
     * <p>
     *  南布局约束(容器底部)。
     * 
     */
    public static final String SOUTH  = "South";

    /**
     * The east layout constraint (right side of container).
     * <p>
     *  东布局约束(容器右侧)。
     * 
     */
    public static final String EAST   = "East";

    /**
     * The west layout constraint (left side of container).
     * <p>
     *  西布局约束(容器的左侧)。
     * 
     */
    public static final String WEST   = "West";

    /**
     * The center layout constraint (middle of container).
     * <p>
     *  中心布局约束(容器中间)。
     * 
     */
    public static final String CENTER = "Center";

    /**
     * Synonym for PAGE_START.  Exists for compatibility with previous
     * versions.  PAGE_START is preferred.
     *
     * <p>
     *  PAGE_START的同义词。存在与以前版本的兼容性。 PAGE_START是首选。
     * 
     * 
     * @see #PAGE_START
     * @since 1.2
     */
    public static final String BEFORE_FIRST_LINE = "First";

    /**
     * Synonym for PAGE_END.  Exists for compatibility with previous
     * versions.  PAGE_END is preferred.
     *
     * <p>
     *  PAGE_END的同义词。存在与以前版本的兼容性。 PAGE_END是首选。
     * 
     * 
     * @see #PAGE_END
     * @since 1.2
     */
    public static final String AFTER_LAST_LINE = "Last";

    /**
     * Synonym for LINE_START.  Exists for compatibility with previous
     * versions.  LINE_START is preferred.
     *
     * <p>
     * LINE_START的同义词。存在与以前版本的兼容性。 LINE_START是首选。
     * 
     * 
     * @see #LINE_START
     * @since 1.2
     */
    public static final String BEFORE_LINE_BEGINS = "Before";

    /**
     * Synonym for LINE_END.  Exists for compatibility with previous
     * versions.  LINE_END is preferred.
     *
     * <p>
     *  LINE_END的同义词。存在与以前版本的兼容性。 LINE_END是首选。
     * 
     * 
     * @see #LINE_END
     * @since 1.2
     */
    public static final String AFTER_LINE_ENDS = "After";

    /**
     * The component comes before the first line of the layout's content.
     * For Western, left-to-right and top-to-bottom orientations, this is
     * equivalent to NORTH.
     *
     * <p>
     *  组件位于布局内容的第一行之前。对于西,从左到右和从上到下的方向,这相当于NORTH。
     * 
     * 
     * @see java.awt.Component#getComponentOrientation
     * @since 1.4
     */
    public static final String PAGE_START = BEFORE_FIRST_LINE;

    /**
     * The component comes after the last line of the layout's content.
     * For Western, left-to-right and top-to-bottom orientations, this is
     * equivalent to SOUTH.
     *
     * <p>
     *  组件位于布局内容的最后一行之后。对于西,从左到右和从上到下的方向,这相当于SOUTH。
     * 
     * 
     * @see java.awt.Component#getComponentOrientation
     * @since 1.4
     */
    public static final String PAGE_END = AFTER_LAST_LINE;

    /**
     * The component goes at the beginning of the line direction for the
     * layout. For Western, left-to-right and top-to-bottom orientations,
     * this is equivalent to WEST.
     *
     * <p>
     *  组件在布局的线方向的开始处。对于西方,从左到右和从上到下的方向,这相当于西方。
     * 
     * 
     * @see java.awt.Component#getComponentOrientation
     * @since 1.4
     */
    public static final String LINE_START = BEFORE_LINE_BEGINS;

    /**
     * The component goes at the end of the line direction for the
     * layout. For Western, left-to-right and top-to-bottom orientations,
     * this is equivalent to EAST.
     *
     * <p>
     *  组件在布局的线方向的末尾。对于西,从左到右和从上到下的方向,这相当于EAST。
     * 
     * 
     * @see java.awt.Component#getComponentOrientation
     * @since 1.4
     */
    public static final String LINE_END = AFTER_LINE_ENDS;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -8658291919501921765L;

    /**
     * Constructs a new border layout with
     * no gaps between components.
     * <p>
     *  构造新的边框布局,组件之间没有间隙。
     * 
     */
    public BorderLayout() {
        this(0, 0);
    }

    /**
     * Constructs a border layout with the specified gaps
     * between components.
     * The horizontal gap is specified by <code>hgap</code>
     * and the vertical gap is specified by <code>vgap</code>.
     * <p>
     *  构造具有组件之间指定间隙的边框布局。水平间隙由<code> hgap </code>指定,垂直间隙由<code> vgap </code>指定。
     * 
     * 
     * @param   hgap   the horizontal gap.
     * @param   vgap   the vertical gap.
     */
    public BorderLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    /**
     * Returns the horizontal gap between components.
     * <p>
     *  返回组件之间的水平间距。
     * 
     * 
     * @since   JDK1.1
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * Sets the horizontal gap between components.
     * <p>
     *  设置组件之间的水平间距。
     * 
     * 
     * @param hgap the horizontal gap between components
     * @since   JDK1.1
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * Returns the vertical gap between components.
     * <p>
     *  返回组件之间的垂直间隙。
     * 
     * 
     * @since   JDK1.1
     */
    public int getVgap() {
        return vgap;
    }

    /**
     * Sets the vertical gap between components.
     * <p>
     *  设置组件之间的垂直间距。
     * 
     * 
     * @param vgap the vertical gap between components
     * @since   JDK1.1
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.  For border layouts, the constraint must be
     * one of the following constants:  <code>NORTH</code>,
     * <code>SOUTH</code>, <code>EAST</code>,
     * <code>WEST</code>, or <code>CENTER</code>.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a component is added to a container using the
     * <code>Container.add</code> method with the same argument types.
     * <p>
     * 使用指定的约束对象将指定的组件添加到布局。
     * 对于边框布局,约束必须是以下常量之一：<code> NORTH </code>,<code> SOUTH </code>,<code> EAST </code>,<code> WEST </code>或
     * <code> CENTER </code>。
     * 使用指定的约束对象将指定的组件添加到布局。
     * <p>
     *  大多数应用程序不直接调用此方法。当使用具有相同参数类型的<code> Container.add </code>方法将组件添加到容器时,将调用此方法。
     * 
     * 
     * @param   comp         the component to be added.
     * @param   constraints  an object that specifies how and where
     *                       the component is added to the layout.
     * @see     java.awt.Container#add(java.awt.Component, java.lang.Object)
     * @exception   IllegalArgumentException  if the constraint object is not
     *                 a string, or if it not one of the five specified
         *              constants.
     * @since   JDK1.1
     */
    public void addLayoutComponent(Component comp, Object constraints) {
      synchronized (comp.getTreeLock()) {
        if ((constraints == null) || (constraints instanceof String)) {
            addLayoutComponent((String)constraints, comp);
        } else {
            throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
        }
      }
    }

    /**
    /* <p>
    /* 
     * @deprecated  replaced by <code>addLayoutComponent(Component, Object)</code>.
     */
    @Deprecated
    public void addLayoutComponent(String name, Component comp) {
      synchronized (comp.getTreeLock()) {
        /* Special case:  treat null the same as "Center". */
        if (name == null) {
            name = "Center";
        }

        /* Assign the component to one of the known regions of the layout.
        /* <p>
         */
        if ("Center".equals(name)) {
            center = comp;
        } else if ("North".equals(name)) {
            north = comp;
        } else if ("South".equals(name)) {
            south = comp;
        } else if ("East".equals(name)) {
            east = comp;
        } else if ("West".equals(name)) {
            west = comp;
        } else if (BEFORE_FIRST_LINE.equals(name)) {
            firstLine = comp;
        } else if (AFTER_LAST_LINE.equals(name)) {
            lastLine = comp;
        } else if (BEFORE_LINE_BEGINS.equals(name)) {
            firstItem = comp;
        } else if (AFTER_LINE_ENDS.equals(name)) {
            lastItem = comp;
        } else {
            throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
        }
      }
    }

    /**
     * Removes the specified component from this border layout. This
     * method is called when a container calls its <code>remove</code> or
     * <code>removeAll</code> methods. Most applications do not call this
     * method directly.
     * <p>
     *  从此边框布局中删除指定的组件。当容器调用其<code> remove </code>或<code> removeAll </code>方法时,将调用此方法。大多数应用程序不直接调用此方法。
     * 
     * 
     * @param   comp   the component to be removed.
     * @see     java.awt.Container#remove(java.awt.Component)
     * @see     java.awt.Container#removeAll()
     */
    public void removeLayoutComponent(Component comp) {
      synchronized (comp.getTreeLock()) {
        if (comp == center) {
            center = null;
        } else if (comp == north) {
            north = null;
        } else if (comp == south) {
            south = null;
        } else if (comp == east) {
            east = null;
        } else if (comp == west) {
            west = null;
        }
        if (comp == firstLine) {
            firstLine = null;
        } else if (comp == lastLine) {
            lastLine = null;
        } else if (comp == firstItem) {
            firstItem = null;
        } else if (comp == lastItem) {
            lastItem = null;
        }
      }
    }

    /**
     * Gets the component that was added using the given constraint
     *
     * <p>
     *  获取使用给定约束添加的组件
     * 
     * 
     * @param   constraints  the desired constraint, one of <code>CENTER</code>,
     *                       <code>NORTH</code>, <code>SOUTH</code>,
     *                       <code>WEST</code>, <code>EAST</code>,
     *                       <code>PAGE_START</code>, <code>PAGE_END</code>,
     *                       <code>LINE_START</code>, <code>LINE_END</code>
     * @return  the component at the given location, or <code>null</code> if
     *          the location is empty
     * @exception   IllegalArgumentException  if the constraint object is
     *              not one of the nine specified constants
     * @see     #addLayoutComponent(java.awt.Component, java.lang.Object)
     * @since 1.5
     */
    public Component getLayoutComponent(Object constraints) {
        if (CENTER.equals(constraints)) {
            return center;
        } else if (NORTH.equals(constraints)) {
            return north;
        } else if (SOUTH.equals(constraints)) {
            return south;
        } else if (WEST.equals(constraints)) {
            return west;
        } else if (EAST.equals(constraints)) {
            return east;
        } else if (PAGE_START.equals(constraints)) {
            return firstLine;
        } else if (PAGE_END.equals(constraints)) {
            return lastLine;
        } else if (LINE_START.equals(constraints)) {
            return firstItem;
        } else if (LINE_END.equals(constraints)) {
            return lastItem;
        } else {
            throw new IllegalArgumentException("cannot get component: unknown constraint: " + constraints);
        }
    }


    /**
     * Returns the component that corresponds to the given constraint location
     * based on the target <code>Container</code>'s component orientation.
     * Components added with the relative constraints <code>PAGE_START</code>,
     * <code>PAGE_END</code>, <code>LINE_START</code>, and <code>LINE_END</code>
     * take precedence over components added with the explicit constraints
     * <code>NORTH</code>, <code>SOUTH</code>, <code>WEST</code>, and <code>EAST</code>.
     * The <code>Container</code>'s component orientation is used to determine the location of components
     * added with <code>LINE_START</code> and <code>LINE_END</code>.
     *
     * <p>
     *  根据目标<code> Container </code>的组件方向,返回与给定约束位置对应的组件。
     * 使用相对限制条件<code> PAGE_START </code>,<code> PAGE_END </code>,<code> LINE_START </code>和<code> LINE_END </code>
     * 添加的组件优先于使用显式约束<code> NORTH </code>,<code> SOUTH </code>,<code> WEST </code>和<code> EAST </code>。
     *  根据目标<code> Container </code>的组件方向,返回与给定约束位置对应的组件。
     *  <code> Container </code>的组件方向用于确定用<code> LINE_START </code>和<code> LINE_END </code>添加的组件的位置。
     * 
     * 
     * @param   constraints     the desired absolute position, one of <code>CENTER</code>,
     *                          <code>NORTH</code>, <code>SOUTH</code>,
     *                          <code>EAST</code>, <code>WEST</code>
     * @param   target     the {@code Container} used to obtain
     *                     the constraint location based on the target
     *                     {@code Container}'s component orientation.
     * @return  the component at the given location, or <code>null</code> if
     *          the location is empty
     * @exception   IllegalArgumentException  if the constraint object is
     *              not one of the five specified constants
     * @exception   NullPointerException  if the target parameter is null
     * @see     #addLayoutComponent(java.awt.Component, java.lang.Object)
     * @since 1.5
     */
    public Component getLayoutComponent(Container target, Object constraints) {
        boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component result = null;

        if (NORTH.equals(constraints)) {
            result = (firstLine != null) ? firstLine : north;
        } else if (SOUTH.equals(constraints)) {
            result = (lastLine != null) ? lastLine : south;
        } else if (WEST.equals(constraints)) {
            result = ltr ? firstItem : lastItem;
            if (result == null) {
                result = west;
            }
        } else if (EAST.equals(constraints)) {
            result = ltr ? lastItem : firstItem;
            if (result == null) {
                result = east;
            }
        } else if (CENTER.equals(constraints)) {
            result = center;
        } else {
            throw new IllegalArgumentException("cannot get component: invalid constraint: " + constraints);
        }

        return result;
    }


    /**
     * Gets the constraints for the specified component
     *
     * <p>
     *  获取指定组件的约束
     * 
     * 
     * @param   comp the component to be queried
     * @return  the constraint for the specified component,
     *          or null if component is null or is not present
     *          in this layout
     * @see #addLayoutComponent(java.awt.Component, java.lang.Object)
     * @since 1.5
     */
    public Object getConstraints(Component comp) {
        //fix for 6242148 : API method java.awt.BorderLayout.getConstraints(null) should return null
        if (comp == null){
            return null;
        }
        if (comp == center) {
            return CENTER;
        } else if (comp == north) {
            return NORTH;
        } else if (comp == south) {
            return SOUTH;
        } else if (comp == west) {
            return WEST;
        } else if (comp == east) {
            return EAST;
        } else if (comp == firstLine) {
            return PAGE_START;
        } else if (comp == lastLine) {
            return PAGE_END;
        } else if (comp == firstItem) {
            return LINE_START;
        } else if (comp == lastItem) {
            return LINE_END;
        }
        return null;
    }

    /**
     * Determines the minimum size of the <code>target</code> container
     * using this layout manager.
     * <p>
     * This method is called when a container calls its
     * <code>getMinimumSize</code> method. Most applications do not call
     * this method directly.
     * <p>
     *  使用此布局管理器确定<code>目标</code>容器的最小大小。
     * <p>
     * 当容器调用其<code> getMinimumSize </code>方法时,将调用此方法。大多数应用程序不直接调用此方法。
     * 
     * 
     * @param   target   the container in which to do the layout.
     * @return  the minimum dimensions needed to lay out the subcomponents
     *          of the specified container.
     * @see     java.awt.Container
     * @see     java.awt.BorderLayout#preferredLayoutSize
     * @see     java.awt.Container#getMinimumSize()
     */
    public Dimension minimumLayoutSize(Container target) {
      synchronized (target.getTreeLock()) {
        Dimension dim = new Dimension(0, 0);

        boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component c = null;

        if ((c=getChild(EAST,ltr)) != null) {
            Dimension d = c.getMinimumSize();
            dim.width += d.width + hgap;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(WEST,ltr)) != null) {
            Dimension d = c.getMinimumSize();
            dim.width += d.width + hgap;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(CENTER,ltr)) != null) {
            Dimension d = c.getMinimumSize();
            dim.width += d.width;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(NORTH,ltr)) != null) {
            Dimension d = c.getMinimumSize();
            dim.width = Math.max(d.width, dim.width);
            dim.height += d.height + vgap;
        }
        if ((c=getChild(SOUTH,ltr)) != null) {
            Dimension d = c.getMinimumSize();
            dim.width = Math.max(d.width, dim.width);
            dim.height += d.height + vgap;
        }

        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right;
        dim.height += insets.top + insets.bottom;

        return dim;
      }
    }

    /**
     * Determines the preferred size of the <code>target</code>
     * container using this layout manager, based on the components
     * in the container.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a container calls its <code>getPreferredSize</code>
     * method.
     * <p>
     *  使用此布局管理器,基于容器中的组件,确定<code> target </code>容器的首选大小。
     * <p>
     *  大多数应用程序不直接调用此方法。当容器调用其<code> getPreferredSize </code>方法时,将调用此方法。
     * 
     * 
     * @param   target   the container in which to do the layout.
     * @return  the preferred dimensions to lay out the subcomponents
     *          of the specified container.
     * @see     java.awt.Container
     * @see     java.awt.BorderLayout#minimumLayoutSize
     * @see     java.awt.Container#getPreferredSize()
     */
    public Dimension preferredLayoutSize(Container target) {
      synchronized (target.getTreeLock()) {
        Dimension dim = new Dimension(0, 0);

        boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component c = null;

        if ((c=getChild(EAST,ltr)) != null) {
            Dimension d = c.getPreferredSize();
            dim.width += d.width + hgap;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(WEST,ltr)) != null) {
            Dimension d = c.getPreferredSize();
            dim.width += d.width + hgap;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(CENTER,ltr)) != null) {
            Dimension d = c.getPreferredSize();
            dim.width += d.width;
            dim.height = Math.max(d.height, dim.height);
        }
        if ((c=getChild(NORTH,ltr)) != null) {
            Dimension d = c.getPreferredSize();
            dim.width = Math.max(d.width, dim.width);
            dim.height += d.height + vgap;
        }
        if ((c=getChild(SOUTH,ltr)) != null) {
            Dimension d = c.getPreferredSize();
            dim.width = Math.max(d.width, dim.width);
            dim.height += d.height + vgap;
        }

        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right;
        dim.height += insets.top + insets.bottom;

        return dim;
      }
    }

    /**
     * Returns the maximum dimensions for this layout given the components
     * in the specified target container.
     * <p>
     *  返回给定指定目标容器中的组件的此布局的最大尺寸。
     * 
     * 
     * @param target the component which needs to be laid out
     * @see Container
     * @see #minimumLayoutSize
     * @see #preferredLayoutSize
     */
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentX(Container parent) {
        return 0.5f;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     *  返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentY(Container parent) {
        return 0.5f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * <p>
     *  使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃。
     * 
     */
    public void invalidateLayout(Container target) {
    }

    /**
     * Lays out the container argument using this border layout.
     * <p>
     * This method actually reshapes the components in the specified
     * container in order to satisfy the constraints of this
     * <code>BorderLayout</code> object. The <code>NORTH</code>
     * and <code>SOUTH</code> components, if any, are placed at
     * the top and bottom of the container, respectively. The
     * <code>WEST</code> and <code>EAST</code> components are
     * then placed on the left and right, respectively. Finally,
     * the <code>CENTER</code> object is placed in any remaining
     * space in the middle.
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a container calls its <code>doLayout</code> method.
     * <p>
     *  使用此边框布局放出容器参数。
     * <p>
     * 这个方法实际上重塑了指定容器中的组件,以满足这个<code> BorderLayout </code>对象的约束。
     *  <code> NORTH </code>和<code> SOUTH </code>组件(如果有)分别放置在容器的顶部和底部。
     * 然后将<code> WEST </code>和<code> EAST </code>组件分别放置在左侧和右侧。最后,<code> CENTER </code>对象被放置在中间的任何剩余空间中。
     * <p>
     *  大多数应用程序不直接调用此方法。当容器调用其<code> doLayout </code>方法时,将调用此方法。
     * 
     * @param   target   the container in which to do the layout.
     * @see     java.awt.Container
     * @see     java.awt.Container#doLayout()
     */
    public void layoutContainer(Container target) {
      synchronized (target.getTreeLock()) {
        Insets insets = target.getInsets();
        int top = insets.top;
        int bottom = target.height - insets.bottom;
        int left = insets.left;
        int right = target.width - insets.right;

        boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component c = null;

        if ((c=getChild(NORTH,ltr)) != null) {
            c.setSize(right - left, c.height);
            Dimension d = c.getPreferredSize();
            c.setBounds(left, top, right - left, d.height);
            top += d.height + vgap;
        }
        if ((c=getChild(SOUTH,ltr)) != null) {
            c.setSize(right - left, c.height);
            Dimension d = c.getPreferredSize();
            c.setBounds(left, bottom - d.height, right - left, d.height);
            bottom -= d.height + vgap;
        }
        if ((c=getChild(EAST,ltr)) != null) {
            c.setSize(c.width, bottom - top);
            Dimension d = c.getPreferredSize();
            c.setBounds(right - d.width, top, d.width, bottom - top);
            right -= d.width + hgap;
        }
        if ((c=getChild(WEST,ltr)) != null) {
            c.setSize(c.width, bottom - top);
            Dimension d = c.getPreferredSize();
            c.setBounds(left, top, d.width, bottom - top);
            left += d.width + hgap;
        }
        if ((c=getChild(CENTER,ltr)) != null) {
            c.setBounds(left, top, right - left, bottom - top);
        }
      }
    }

    /**
     * Get the component that corresponds to the given constraint location
     *
     * <p>
     * 
     * 
     * @param   key     The desired absolute position,
     *                  either NORTH, SOUTH, EAST, or WEST.
     * @param   ltr     Is the component line direction left-to-right?
     */
    private Component getChild(String key, boolean ltr) {
        Component result = null;

        if (key == NORTH) {
            result = (firstLine != null) ? firstLine : north;
        }
        else if (key == SOUTH) {
            result = (lastLine != null) ? lastLine : south;
        }
        else if (key == WEST) {
            result = ltr ? firstItem : lastItem;
            if (result == null) {
                result = west;
            }
        }
        else if (key == EAST) {
            result = ltr ? lastItem : firstItem;
            if (result == null) {
                result = east;
            }
        }
        else if (key == CENTER) {
            result = center;
        }
        if (result != null && !result.visible) {
            result = null;
        }
        return result;
    }

    /**
     * Returns a string representation of the state of this border layout.
     * <p>
     *  获取与给定约束位置对应的组件
     * 
     * 
     * @return    a string representation of this border layout.
     */
    public String toString() {
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }
}
