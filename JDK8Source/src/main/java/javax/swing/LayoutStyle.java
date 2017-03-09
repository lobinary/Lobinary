/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Container;
import javax.swing.plaf.ComponentUI;
import sun.awt.AppContext;

/**
 * <code>LayoutStyle</code> provides information about how to position
 * components.  This class is primarily useful for visual tools and
 * layout managers.  Most developers will not need to use this class.
 * <p>
 * You typically don't set or create a
 * <code>LayoutStyle</code>.  Instead use the static method
 * <code>getInstance</code> to obtain the current instance.
 *
 * <p>
 *  <code> LayoutStyle </code>提供有关如何定位组件的信息。这个类主要用于可视化工具和布局管理器。大多数开发人员不需要使用这个类。
 * <p>
 *  您通常不设置或创建<code> LayoutStyle </code>。而是使用静态方法<code> getInstance </code>获取当前实例。
 * 
 * 
 * @since 1.6
 */
public abstract class LayoutStyle {
    /**
     * Sets the shared instance of <code>LayoutStyle</code>.  Specifying
     * <code>null</code> results in using the <code>LayoutStyle</code> from
     * the current <code>LookAndFeel</code>.
     *
     * <p>
     *  设置<code> LayoutStyle </code>的共享实例。
     * 指定<code> null </code>会导致使用当前<code> LookAndFeel </code>中的<code> LayoutStyle </code>。
     * 
     * 
     * @param style the <code>LayoutStyle</code>, or <code>null</code>
     * @see #getInstance
     */
    public static void setInstance(LayoutStyle style) {
        synchronized(LayoutStyle.class) {
            if (style == null) {
                AppContext.getAppContext().remove(LayoutStyle.class);
            }
            else {
                AppContext.getAppContext().put(LayoutStyle.class, style);
            }
        }
    }

    /**
     * Returns the shared instance of <code>LayoutStyle</code>.  If an instance
     * has not been specified in <code>setInstance</code>, this will return
     * the <code>LayoutStyle</code> from the current <code>LookAndFeel</code>.
     *
     * <p>
     *  返回<code> LayoutStyle </code>的共享实例。
     * 如果在<code> setInstance </code>中没有指定实例,则将返回当前<code> LookAndFeel </code>中的<code> LayoutStyle </code>。
     * 
     * 
     * @see LookAndFeel#getLayoutStyle
     * @return the shared instance of <code>LayoutStyle</code>
     */
    public static LayoutStyle getInstance() {
        LayoutStyle style;
        synchronized(LayoutStyle.class) {
            style = (LayoutStyle)AppContext.getAppContext().
                    get(LayoutStyle.class);
        }
        if (style == null) {
            return UIManager.getLookAndFeel().getLayoutStyle();
        }
        return style;
    }


    /**
     * <code>ComponentPlacement</code> is an enumeration of the
     * possible ways two components can be placed relative to each
     * other.  <code>ComponentPlacement</code> is used by the
     * <code>LayoutStyle</code> method <code>getPreferredGap</code>.  Refer to
     * <code>LayoutStyle</code> for more information.
     *
     * <p>
     *  <code> ComponentPlacement </code>是两个组件相对于彼此放置的可能方式的枚举。
     *  <code> ComponentPlacement </code>由<code> LayoutStyle </code>方法<code> getPreferredGap </code>使用。
     * 有关详细信息,请参阅<code> LayoutStyle </code>。
     * 
     * 
     * @see LayoutStyle#getPreferredGap(JComponent,JComponent,
     *      ComponentPlacement,int,Container)
     * @since 1.6
     */
    public enum ComponentPlacement {
        /**
         * Enumeration value indicating the two components are
         * visually related and will be placed in the same parent.
         * For example, a <code>JLabel</code> providing a label for a
         * <code>JTextField</code> is typically visually associated
         * with the <code>JTextField</code>; the constant <code>RELATED</code>
         * is used for this.
         * <p>
         *  枚举值指示两个组件在视觉上相关,并将放置在同一个父代。
         * 例如,为<code> JTextField </code>提供标签的<code> JLabel </code>通常与<code> JTextField </code>在视觉上相关联;为此使用常量<code>
         *  RELATED </code>。
         *  枚举值指示两个组件在视觉上相关,并将放置在同一个父代。
         * 
         */
        RELATED,

        /**
         * Enumeration value indicating the two components are
         * visually unrelated and will be placed in the same parent.
         * For example, groupings of components are usually visually
         * separated; the constant <code>UNRELATED</code> is used for this.
         * <p>
         * 枚举值指示两个组件在视觉上不相关,并且将放置在同一父级中。例如,组件的分组通常在视觉上分离;使用常量<code> UNRELATED </code>。
         * 
         */
        UNRELATED,

        /**
         * Enumeration value indicating the distance to indent a component
         * is being requested.  For example, often times the children of
         * a label will be horizontally indented from the label.  To determine
         * the preferred distance for such a gap use the
         * <code>INDENT</code> type.
         * <p>
         * This value is typically only useful with a direction of
         * <code>EAST</code> or <code>WEST</code>.
         * <p>
         *  枚举值,指示正在请求组件的缩进距离。例如,通常标签的子元素将从标签水平缩进。要确定这种间隙的首选距离,请使用<code> INDENT </code>类型。
         * <p>
         *  此值通常仅适用于<code> EAST </code>或<code> WEST </code>的方向。
         * 
         */
        INDENT;
    }


    /**
     * Creates a new <code>LayoutStyle</code>.  You generally don't
     * create a <code>LayoutStyle</code>.  Instead use the method
     * <code>getInstance</code> to obtain the current
     * <code>LayoutStyle</code>.
     * <p>
     *  创建新的<code> LayoutStyle </code>。您通常不创建<code> LayoutStyle </code>。
     * 而是使用方法<code> getInstance </code>获取当前<code> LayoutStyle </code>。
     * 
     */
    public LayoutStyle() {
    }

    /**
     * Returns the amount of space to use between two components.
     * The return value indicates the distance to place
     * <code>component2</code> relative to <code>component1</code>.
     * For example, the following returns the amount of space to place
     * between <code>component2</code> and <code>component1</code>
     * when <code>component2</code> is placed vertically above
     * <code>component1</code>:
     * <pre>
     *   int gap = getPreferredGap(component1, component2,
     *                             ComponentPlacement.RELATED,
     *                             SwingConstants.NORTH, parent);
     * </pre>
     * The <code>type</code> parameter indicates the relation between
     * the two components.  If the two components will be contained in
     * the same parent and are showing similar logically related
     * items, use <code>RELATED</code>.  If the two components will be
     * contained in the same parent but show logically unrelated items
     * use <code>UNRELATED</code>.  Some look and feels may not
     * distinguish between the <code>RELATED</code> and
     * <code>UNRELATED</code> types.
     * <p>
     * The return value is not intended to take into account the
     * current size and position of <code>component2</code> or
     * <code>component1</code>.  The return value may take into
     * consideration various properties of the components.  For
     * example, the space may vary based on font size, or the preferred
     * size of the component.
     *
     * <p>
     *  返回在两个组件之间使用的空间量。返回值表示相对于<code> component1 </code>放置<code> component2 </code>的距离。
     * 例如,当<code> component2 </code>垂直放置在<code> component1 </code>之上时,以下内容返回<code> component2 </code>和<code>
     *  component1 </code>之间的空格大小>：。
     *  返回在两个组件之间使用的空间量。返回值表示相对于<code> component1 </code>放置<code> component2 </code>的距离。
     * <pre>
     *  int gap = getPreferredGap(component1,component2,ComponentPlacement.RELATED,SwingConstants.NORTH,pare
     * nt);。
     * </pre>
     * <code> type </code>参数指示两个组件之间的关系。如果两个组件将包含在相同的父项中并显示类似的逻辑相关项,请使用<code> RELATED </code>。
     * 
     * @param component1 the <code>JComponent</code>
     *               <code>component2</code> is being placed relative to
     * @param component2 the <code>JComponent</code> being placed
     * @param position the position <code>component2</code> is being placed
     *        relative to <code>component1</code>; one of
     *        <code>SwingConstants.NORTH</code>,
     *        <code>SwingConstants.SOUTH</code>,
     *        <code>SwingConstants.EAST</code> or
     *        <code>SwingConstants.WEST</code>
     * @param type how the two components are being placed
     * @param parent the parent of <code>component2</code>; this may differ
     *        from the actual parent and it may be <code>null</code>
     * @return the amount of space to place between the two components
     * @throws NullPointerException if <code>component1</code>,
     *         <code>component2</code> or <code>type</code> is
     *         <code>null</code>
     * @throws IllegalArgumentException if <code>position</code> is not
     *         one of <code>SwingConstants.NORTH</code>,
     *         <code>SwingConstants.SOUTH</code>,
     *         <code>SwingConstants.EAST</code> or
     *         <code>SwingConstants.WEST</code>
     * @see LookAndFeel#getLayoutStyle
     * @since 1.6
     */
    public abstract int getPreferredGap(JComponent component1,
                                        JComponent component2,
                                        ComponentPlacement type, int position,
                                        Container parent);

    /**
     * Returns the amount of space to place between the component and specified
     * edge of its parent.
     *
     * <p>
     * 如果两个组件将包含在同一父代,但显示逻辑上不相关的项目使用<code> UNRELATED </code>。
     * 一些外观和感觉可能不区分<code> RELATED </code>和<code> UNRELATED </code>类型。
     * <p>
     *  返回值不打算考虑<code> component2 </code>或<code> component1 </code>的当前大小和位置。返回值可以考虑组件的各种属性。
     * 例如,空间可以基于字体大小或组件的优选大小而变化。
     * 
     * @param component the <code>JComponent</code> being positioned
     * @param position the position <code>component</code> is being placed
     *        relative to its parent; one of
     *        <code>SwingConstants.NORTH</code>,
     *        <code>SwingConstants.SOUTH</code>,
     *        <code>SwingConstants.EAST</code> or
     *        <code>SwingConstants.WEST</code>
     * @param parent the parent of <code>component</code>; this may differ
     *        from the actual parent and may be <code>null</code>
     * @return the amount of space to place between the component and specified
     *         edge
     * @throws IllegalArgumentException if <code>position</code> is not
     *         one of <code>SwingConstants.NORTH</code>,
     *         <code>SwingConstants.SOUTH</code>,
     *         <code>SwingConstants.EAST</code> or
     *         <code>SwingConstants.WEST</code>
     */
    public abstract int getContainerGap(JComponent component, int position,
                                        Container parent);
}
