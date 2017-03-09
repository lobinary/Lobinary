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

/**
 * The <code>GridBagConstraints</code> class specifies constraints
 * for components that are laid out using the
 * <code>GridBagLayout</code> class.
 *
 * <p>
 *  <code> GridBagConstraints </code>类指定使用<code> GridBagLayout </code>类布置的组件的约束。
 * 
 * 
 * @author Doug Stein
 * @author Bill Spitzak (orignial NeWS &amp; OLIT implementation)
 * @see java.awt.GridBagLayout
 * @since JDK1.0
 */
public class GridBagConstraints implements Cloneable, java.io.Serializable {

    /**
     * Specifies that this component is the next-to-last component in its
     * column or row (<code>gridwidth</code>, <code>gridheight</code>),
     * or that this component be placed next to the previously added
     * component (<code>gridx</code>, <code>gridy</code>).
     * <p>
     *  指定此组件是其列或行(<code> gridwidth </code>,<code> gridheight </code>)中倒数第二个组件,或者此组件放置在先前添加的组件<code> gridx </code>
     * ,<code> gridy </code>)。
     * 
     * 
     * @see      java.awt.GridBagConstraints#gridwidth
     * @see      java.awt.GridBagConstraints#gridheight
     * @see      java.awt.GridBagConstraints#gridx
     * @see      java.awt.GridBagConstraints#gridy
     */
    public static final int RELATIVE = -1;

    /**
     * Specifies that this component is the
     * last component in its column or row.
     * <p>
     *  指定此组件是其列或行中的最后一个组件。
     * 
     */
    public static final int REMAINDER = 0;

    /**
     * Do not resize the component.
     * <p>
     *  不要调整组件的大小。
     * 
     */
    public static final int NONE = 0;

    /**
     * Resize the component both horizontally and vertically.
     * <p>
     *  水平和垂直调整组件大小。
     * 
     */
    public static final int BOTH = 1;

    /**
     * Resize the component horizontally but not vertically.
     * <p>
     *  水平但不垂直调整组件大小。
     * 
     */
    public static final int HORIZONTAL = 2;

    /**
     * Resize the component vertically but not horizontally.
     * <p>
     *  垂直但不水平调整组件大小。
     * 
     */
    public static final int VERTICAL = 3;

    /**
     * Put the component in the center of its display area.
     * <p>
     *  将组件放在其显示区域的中心。
     * 
     */
    public static final int CENTER = 10;

    /**
     * Put the component at the top of its display area,
     * centered horizontally.
     * <p>
     *  将组件放在其显示区域的顶部,水平居中。
     * 
     */
    public static final int NORTH = 11;

    /**
     * Put the component at the top-right corner of its display area.
     * <p>
     *  将组件放在其显示区域的右上角。
     * 
     */
    public static final int NORTHEAST = 12;

    /**
     * Put the component on the right side of its display area,
     * centered vertically.
     * <p>
     *  将组件放在显示区域的右侧,垂直居中。
     * 
     */
    public static final int EAST = 13;

    /**
     * Put the component at the bottom-right corner of its display area.
     * <p>
     *  将组件放在其显示区域的右下角。
     * 
     */
    public static final int SOUTHEAST = 14;

    /**
     * Put the component at the bottom of its display area, centered
     * horizontally.
     * <p>
     *  将组件放在显示区域的底部,水平居中。
     * 
     */
    public static final int SOUTH = 15;

    /**
     * Put the component at the bottom-left corner of its display area.
     * <p>
     *  将组件放在其显示区域的左下角。
     * 
     */
    public static final int SOUTHWEST = 16;

    /**
     * Put the component on the left side of its display area,
     * centered vertically.
     * <p>
     *  将组件放在其显示区域的左侧,垂直居中。
     * 
     */
    public static final int WEST = 17;

    /**
     * Put the component at the top-left corner of its display area.
     * <p>
     *  将组件放在其显示区域的左上角。
     * 
     */
    public static final int NORTHWEST = 18;

    /**
     * Place the component centered along the edge of its display area
     * associated with the start of a page for the current
     * {@code ComponentOrientation}.  Equal to NORTH for horizontal
     * orientations.
     * <p>
     * 将组件沿着与当前{@code ComponentOrientation}的页面开始关联的显示区域的边缘居中。等于水平方向的NORTH。
     * 
     */
    public static final int PAGE_START = 19;

    /**
     * Place the component centered along the edge of its display area
     * associated with the end of a page for the current
     * {@code ComponentOrientation}.  Equal to SOUTH for horizontal
     * orientations.
     * <p>
     *  将组件沿着与当前{@code ComponentOrientation}的页面末尾相关联的显示区域的边缘居中。等于SOUTH水平方向。
     * 
     */
    public static final int PAGE_END = 20;

    /**
     * Place the component centered along the edge of its display area where
     * lines of text would normally begin for the current
     * {@code ComponentOrientation}.  Equal to WEST for horizontal,
     * left-to-right orientations and EAST for horizontal, right-to-left
     * orientations.
     * <p>
     *  将组件沿着其显示区域的边缘居中,其中文本行通常开始用于当前{@code ComponentOrientation}。等于WEST用于水平,从左到右的方向,EAST用于水平,从右到左的方向。
     * 
     */
    public static final int LINE_START = 21;

    /**
     * Place the component centered along the edge of its display area where
     * lines of text would normally end for the current
     * {@code ComponentOrientation}.  Equal to EAST for horizontal,
     * left-to-right orientations and WEST for horizontal, right-to-left
     * orientations.
     * <p>
     *  将组件沿着其显示区域的边缘居中,其中文本行通常会结束当前{@code ComponentOrientation}。等于EAST用于水平,从左到右的方向,WEST用于水平,从右到左的方向。
     * 
     */
    public static final int LINE_END = 22;

    /**
     * Place the component in the corner of its display area where
     * the first line of text on a page would normally begin for the current
     * {@code ComponentOrientation}.  Equal to NORTHWEST for horizontal,
     * left-to-right orientations and NORTHEAST for horizontal, right-to-left
     * orientations.
     * <p>
     *  将组件放置在其显示区域的角落,页面上的第一行文本通常从当前{@code ComponentOrientation}开始。
     * 等于NORTHWEST为水平,从左到右的方向,NORTHEAST为水平,从右到左的方向。
     * 
     */
    public static final int FIRST_LINE_START = 23;

    /**
     * Place the component in the corner of its display area where
     * the first line of text on a page would normally end for the current
     * {@code ComponentOrientation}.  Equal to NORTHEAST for horizontal,
     * left-to-right orientations and NORTHWEST for horizontal, right-to-left
     * orientations.
     * <p>
     *  将组件放置在其显示区域的一角,其中页面上的第一行文本通常会结束当前的{@code ComponentOrientation}。
     * 等于NORTHEAST用于水平,从左到右的方向,NORTHWEST用于水平,从右到左的方向。
     * 
     */
    public static final int FIRST_LINE_END = 24;

    /**
     * Place the component in the corner of its display area where
     * the last line of text on a page would normally start for the current
     * {@code ComponentOrientation}.  Equal to SOUTHWEST for horizontal,
     * left-to-right orientations and SOUTHEAST for horizontal, right-to-left
     * orientations.
     * <p>
     * 将组件放置在其显示区域的角落,页面上最后一行文本通常从当前{@code ComponentOrientation}开始。
     * 等于SOUTHWEST的水平,从左到右的方向,SOUTHEAST为水平,从右到左的方向。
     * 
     */
    public static final int LAST_LINE_START = 25;

    /**
     * Place the component in the corner of its display area where
     * the last line of text on a page would normally end for the current
     * {@code ComponentOrientation}.  Equal to SOUTHEAST for horizontal,
     * left-to-right orientations and SOUTHWEST for horizontal, right-to-left
     * orientations.
     * <p>
     *  将组件放置在显示区域的一角,其中页面上的最后一行文本通常会结束当前的{@code ComponentOrientation}。
     * 等于SOUTHEAST用于水平,从左到右的方向,SOUTHWEST用于水平,从右到左的方向。
     * 
     */
    public static final int LAST_LINE_END = 26;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally centered and
     * vertically aligned along the baseline of the prevailing row.
     * If the component does not have a baseline it will be vertically
     * centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应水平居中,并沿当前行的基线垂直对齐。如果组件没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BASELINE = 0x100;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * leading edge.  For components with a left-to-right orientation,
     * the leading edge is the left edge.  Vertically the component is
     * aligned along the baseline of the prevailing row.  If the
     * component does not have a baseline it will be vertically
     * centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应沿前沿水平放置。对于具有从左到右方向的组件,前边缘是左边缘。垂直地,组件沿着主行的基线对准。如果组件没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BASELINE_LEADING = 0x200;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * trailing edge.  For components with a left-to-right
     * orientation, the trailing edge is the right edge.  Vertically
     * the component is aligned along the baseline of the prevailing
     * row.  If the component does not have a baseline it will be
     * vertically centered.
     *
     * <p>
     * <code> anchor </code>字段的可能值。指定组件应沿着后缘水平放置。对于具有从左到右方向的组件,后缘是右边缘。垂直地,组件沿着主行的基线对准。如果组件没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BASELINE_TRAILING = 0x300;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally centered.  Vertically
     * the component is positioned so that its bottom edge touches
     * the baseline of the starting row.  If the starting row does not
     * have a baseline it will be vertically centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应水平居中。垂直地定位部件,使得其底部边缘接触起始行的基线。如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int ABOVE_BASELINE = 0x400;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * leading edge.  For components with a left-to-right orientation,
     * the leading edge is the left edge.  Vertically the component is
     * positioned so that its bottom edge touches the baseline of the
     * starting row.  If the starting row does not have a baseline it
     * will be vertically centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应沿前沿水平放置。对于具有从左到右方向的组件,前边缘是左边缘。垂直地定位部件,使得其底部边缘接触起始行的基线。
     * 如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int ABOVE_BASELINE_LEADING = 0x500;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * trailing edge.  For components with a left-to-right
     * orientation, the trailing edge is the right edge.  Vertically
     * the component is positioned so that its bottom edge touches
     * the baseline of the starting row.  If the starting row does not
     * have a baseline it will be vertically centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应沿着后缘水平放置。对于具有从左到右方向的组件,后缘是右边缘。垂直地定位部件,使得其底部边缘接触起始行的基线。
     * 如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int ABOVE_BASELINE_TRAILING = 0x600;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally centered.  Vertically
     * the component is positioned so that its top edge touches the
     * baseline of the starting row.  If the starting row does not
     * have a baseline it will be vertically centered.
     *
     * <p>
     * <code> anchor </code>字段的可能值。指定组件应水平居中。垂直地定位部件,使得其顶部边缘接触起始行的基线。如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BELOW_BASELINE = 0x700;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * leading edge.  For components with a left-to-right orientation,
     * the leading edge is the left edge.  Vertically the component is
     * positioned so that its top edge touches the baseline of the
     * starting row.  If the starting row does not have a baseline it
     * will be vertically centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应沿前沿水平放置。对于具有从左到右方向的组件,前边缘是左边缘。垂直地定位部件,使得其顶部边缘接触起始行的基线。
     * 如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BELOW_BASELINE_LEADING = 0x800;

    /**
     * Possible value for the <code>anchor</code> field.  Specifies
     * that the component should be horizontally placed along the
     * trailing edge.  For components with a left-to-right
     * orientation, the trailing edge is the right edge.  Vertically
     * the component is positioned so that its top edge touches the
     * baseline of the starting row.  If the starting row does not
     * have a baseline it will be vertically centered.
     *
     * <p>
     *  <code> anchor </code>字段的可能值。指定组件应沿着后缘水平放置。对于具有从左到右方向的组件,后缘是右边缘。垂直地定位部件,使得其顶部边缘接触起始行的基线。
     * 如果起始行没有基线,它将垂直居中。
     * 
     * 
     * @since 1.6
     */
    public static final int BELOW_BASELINE_TRAILING = 0x900;

    /**
     * Specifies the cell containing the leading edge of the component's
     * display area, where the first cell in a row has <code>gridx=0</code>.
     * The leading edge of a component's display area is its left edge for
     * a horizontal, left-to-right container and its right edge for a
     * horizontal, right-to-left container.
     * The value
     * <code>RELATIVE</code> specifies that the component be placed
     * immediately following the component that was added to the container
     * just before this component was added.
     * <p>
     * The default value is <code>RELATIVE</code>.
     * <code>gridx</code> should be a non-negative value.
     * <p>
     * 指定包含组件显示区域前缘的单元格,其中行中的第一个单元格具有<code> gridx = 0 </code>。
     * 组件的显示区域的前边缘是其从左到右水平的容器的左边缘,以及对于水平的,从右到左的容器的右边缘。值<code> RELATIVE </code>指定组件紧跟在添加此组件之前添加到容器的组件之后。
     * <p>
     *  默认值为<code> RELATIVE </code>。 <code> gridx </code>应为非负值。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#gridy
     * @see java.awt.ComponentOrientation
     */
    public int gridx;

    /**
     * Specifies the cell at the top of the component's display area,
     * where the topmost cell has <code>gridy=0</code>. The value
     * <code>RELATIVE</code> specifies that the component be placed just
     * below the component that was added to the container just before
     * this component was added.
     * <p>
     * The default value is <code>RELATIVE</code>.
     * <code>gridy</code> should be a non-negative value.
     * <p>
     *  指定组件显示区域顶部的单元格,其中最上面的单元格具有<code> gridy = 0 </code>。
     * 值<code> RELATIVE </code>指定将组件放在恰好在添加此组件之前添加到容器的组件的正下方。
     * <p>
     *  默认值为<code> RELATIVE </code>。 <code> gridy </code>应为非负值。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#gridx
     */
    public int gridy;

    /**
     * Specifies the number of cells in a row for the component's
     * display area.
     * <p>
     * Use <code>REMAINDER</code> to specify that the component's
     * display area will be from <code>gridx</code> to the last
     * cell in the row.
     * Use <code>RELATIVE</code> to specify that the component's
     * display area will be from <code>gridx</code> to the next
     * to the last one in its row.
     * <p>
     * <code>gridwidth</code> should be non-negative and the default
     * value is 1.
     * <p>
     *  指定组件显示区域行中的单元格数。
     * <p>
     *  使用<code> REMAINDER </code>指定组件的显示区域将从<code> gridx </code>到该行中的最后一个单元格。
     * 使用<code> RELATIVE </code>指定组件的显示区域将从<code> gridx </code>到其行中最后一个的旁边。
     * <p>
     *  <code> gridwidth </code>应为非负数,默认值为1。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#gridheight
     */
    public int gridwidth;

    /**
     * Specifies the number of cells in a column for the component's
     * display area.
     * <p>
     * Use <code>REMAINDER</code> to specify that the component's
     * display area will be from <code>gridy</code> to the last
     * cell in the column.
     * Use <code>RELATIVE</code> to specify that the component's
     * display area will be from <code>gridy</code> to the next
     * to the last one in its column.
     * <p>
     * <code>gridheight</code> should be a non-negative value and the
     * default value is 1.
     * <p>
     *  指定组件的显示区域的列中的单元格数。
     * <p>
     * 使用<code> REMAINDER </code>指定组件的显示区域将从<code> gridy </code>到列中的最后一个单元格。
     * 使用<code> RELATIVE </code>指定组件的显示区域将从<code> gridy </code>到其列中最后一个的旁边。
     * <p>
     *  <code> gridheight </code>应为非负值,默认值为1。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#gridwidth
     */
    public int gridheight;

    /**
     * Specifies how to distribute extra horizontal space.
     * <p>
     * The grid bag layout manager calculates the weight of a column to
     * be the maximum <code>weightx</code> of all the components in a
     * column. If the resulting layout is smaller horizontally than the area
     * it needs to fill, the extra space is distributed to each column in
     * proportion to its weight. A column that has a weight of zero receives
     * no extra space.
     * <p>
     * If all the weights are zero, all the extra space appears between
     * the grids of the cell and the left and right edges.
     * <p>
     * The default value of this field is <code>0</code>.
     * <code>weightx</code> should be a non-negative value.
     * <p>
     *  指定如何分配额外的水平空间。
     * <p>
     *  网格包布局管理器将列的权重计算为列中所有组件的最大值<code> weightx </code>。如果结果布局在水平方向上小于其需要填充的区域,则额外空间与其重量成比例地分布到每列。
     * 权重为零的列不接收额外的空间。
     * <p>
     *  如果所有权重为零,则在单元的网格和左右边缘之间出现所有额外的空间。
     * <p>
     *  此字段的默认值为<code> 0 </code>。 <code> weightx </code>应为非负值。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#weighty
     */
    public double weightx;

    /**
     * Specifies how to distribute extra vertical space.
     * <p>
     * The grid bag layout manager calculates the weight of a row to be
     * the maximum <code>weighty</code> of all the components in a row.
     * If the resulting layout is smaller vertically than the area it
     * needs to fill, the extra space is distributed to each row in
     * proportion to its weight. A row that has a weight of zero receives no
     * extra space.
     * <p>
     * If all the weights are zero, all the extra space appears between
     * the grids of the cell and the top and bottom edges.
     * <p>
     * The default value of this field is <code>0</code>.
     * <code>weighty</code> should be a non-negative value.
     * <p>
     *  指定如何分配额外的垂直空间。
     * <p>
     *  网格包布局管理器将行的权重计算为行中所有组件的最大值<code> weighty </code>。如果结果布局在垂直方向小于需要填充的区域,则额外空间按照其重量成比例地分配给每行。
     * 权重为零的行不会收到额外的空间。
     * <p>
     * 如果所有权重为零,则在单元的网格与顶部和底部边缘之间出现所有额外的空间。
     * <p>
     *  此字段的默认值为<code> 0 </code>。 <code> weighty </code>应为非负值。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#weightx
     */
    public double weighty;

    /**
     * This field is used when the component is smaller than its
     * display area. It determines where, within the display area, to
     * place the component.
     * <p> There are three kinds of possible values: orientation
     * relative, baseline relative and absolute.  Orientation relative
     * values are interpreted relative to the container's component
     * orientation property, baseline relative values are interpreted
     * relative to the baseline and absolute values are not.  The
     * absolute values are:
     * <code>CENTER</code>, <code>NORTH</code>, <code>NORTHEAST</code>,
     * <code>EAST</code>, <code>SOUTHEAST</code>, <code>SOUTH</code>,
     * <code>SOUTHWEST</code>, <code>WEST</code>, and <code>NORTHWEST</code>.
     * The orientation relative values are: <code>PAGE_START</code>,
     * <code>PAGE_END</code>,
     * <code>LINE_START</code>, <code>LINE_END</code>,
     * <code>FIRST_LINE_START</code>, <code>FIRST_LINE_END</code>,
     * <code>LAST_LINE_START</code> and <code>LAST_LINE_END</code>.  The
     * baseline relative values are:
     * <code>BASELINE</code>, <code>BASELINE_LEADING</code>,
     * <code>BASELINE_TRAILING</code>,
     * <code>ABOVE_BASELINE</code>, <code>ABOVE_BASELINE_LEADING</code>,
     * <code>ABOVE_BASELINE_TRAILING</code>,
     * <code>BELOW_BASELINE</code>, <code>BELOW_BASELINE_LEADING</code>,
     * and <code>BELOW_BASELINE_TRAILING</code>.
     * The default value is <code>CENTER</code>.
     * <p>
     * 当组件小于其显示区域时,使用此字段。它确定在显示区域内放置组件的位置。 <p>有三种可能的值：方向相对,基线相对和绝对。
     * 方向相对值相对于容器的组件方向属性来解释,基线相对值相对于基线被解释,而绝对值不被解释。
     * 绝对值为：<code> CENTER </code>,<code> NORTH </code>,<code> NORTHEAST </code>,<code> EAST </code>,<code> S
     * OUTHEAST </code> <code> SOUTH </code>,<code> SOUTHWEST </code>,<code> WEST </code>和<code> NORTHWEST </code>
     * 。
     * 方向相对值相对于容器的组件方向属性来解释,基线相对值相对于基线被解释,而绝对值不被解释。
     * 方向相对值为：<code> PAGE_START </code>,<code> PAGE_END </code>,<code> LINE_START </code>,<code> LINE_END </code>
     * ,<code> FIRST_LINE_START </code> ,<code> FIRST_LINE_END </code>,<code> LAST_LINE_START </code>和<code>
     *  LAST_LINE_END </code>。
     * 方向相对值相对于容器的组件方向属性来解释,基线相对值相对于基线被解释,而绝对值不被解释。
     * 基线相对值为：<code> BASELINE </code>,<code> BASELINE_LEADING </code>,<code> BASELINE_TRAILING </code>,<code>
     *  ABOVE_BASELINE </code>,<code> ABOVE_BASELINE_LEADING </code> ,<code> ABOVE_BASELINE_TRAILING </code>
     * ,<code> BELOW_BASELINE </code>,<code> BELOW_BASELINE_LEADING </code>和<code> BELOW_BASELINE_TRAILING </code>
     * 。
     * 方向相对值相对于容器的组件方向属性来解释,基线相对值相对于基线被解释,而绝对值不被解释。默认值为<code> CENTER </code>。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.ComponentOrientation
     */
    public int anchor;

    /**
     * This field is used when the component's display area is larger
     * than the component's requested size. It determines whether to
     * resize the component, and if so, how.
     * <p>
     * The following values are valid for <code>fill</code>:
     *
     * <ul>
     * <li>
     * <code>NONE</code>: Do not resize the component.
     * <li>
     * <code>HORIZONTAL</code>: Make the component wide enough to fill
     *         its display area horizontally, but do not change its height.
     * <li>
     * <code>VERTICAL</code>: Make the component tall enough to fill its
     *         display area vertically, but do not change its width.
     * <li>
     * <code>BOTH</code>: Make the component fill its display area
     *         entirely.
     * </ul>
     * <p>
     * The default value is <code>NONE</code>.
     * <p>
     *  当组件的显示区域大于组件的请求大小时,将使用此字段。它确定是否调整组件大小,如果是,如何调整。
     * <p>
     * 以下值对<code> fill </code>有效：
     * 
     * <ul>
     * <li>
     *  <code> NONE </code>：不要调整组件的大小。
     * <li>
     *  <code> HORIZONTAL </code>：使组件足够宽以水平填充其显示区域,但不要更改其高度。
     * <li>
     *  <code> VERTICAL </code>：使组件高度足以垂直填充其显示区域,但不要更改其宽度。
     * <li>
     *  <code> BOTH </code>：使组件完全填满其显示区域。
     * </ul>
     * <p>
     *  默认值为<code> NONE </code>。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public int fill;

    /**
     * This field specifies the external padding of the component, the
     * minimum amount of space between the component and the edges of its
     * display area.
     * <p>
     * The default value is <code>new Insets(0, 0, 0, 0)</code>.
     * <p>
     *  此字段指定组件的外部填充,组件与其显示区域边缘之间的最小空间量。
     * <p>
     *  默认值为<code> new Insets(0,0,0,0)</code>。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public Insets insets;

    /**
     * This field specifies the internal padding of the component, how much
     * space to add to the minimum width of the component. The width of
     * the component is at least its minimum width plus
     * <code>ipadx</code> pixels.
     * <p>
     * The default value is <code>0</code>.
     * <p>
     *  此字段指定组件的内部填充,要添加到组件的最小宽度的空间大小。组件的宽度至少为其最小宽度加上<code> ipadx </code>像素。
     * <p>
     *  默认值为<code> 0 </code>。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#ipady
     */
    public int ipadx;

    /**
     * This field specifies the internal padding, that is, how much
     * space to add to the minimum height of the component. The height of
     * the component is at least its minimum height plus
     * <code>ipady</code> pixels.
     * <p>
     * The default value is 0.
     * <p>
     *  此字段指定内部填充,即要添加到组件的最小高度的空间。组件的高度至少为其最小高度加上<code> ipady </code>像素。
     * <p>
     *  默认值为0。
     * 
     * 
     * @serial
     * @see #clone()
     * @see java.awt.GridBagConstraints#ipadx
     */
    public int ipady;

    /**
     * Temporary place holder for the x coordinate.
     * <p>
     *  x坐标的临时占位符。
     * 
     * 
     * @serial
     */
    int tempX;
    /**
     * Temporary place holder for the y coordinate.
     * <p>
     *  y坐标的临时占位符。
     * 
     * 
     * @serial
     */
    int tempY;
    /**
     * Temporary place holder for the Width of the component.
     * <p>
     *  组件宽度的临时占位符。
     * 
     * 
     * @serial
     */
    int tempWidth;
    /**
     * Temporary place holder for the Height of the component.
     * <p>
     *  组件高度的临时占位符。
     * 
     * 
     * @serial
     */
    int tempHeight;
    /**
     * The minimum width of the component.  It is used to calculate
     * <code>ipady</code>, where the default will be 0.
     * <p>
     *  组件的最小宽度。它用于计算<code> ipady </code>,其中默认值为0。
     * 
     * 
     * @serial
     * @see #ipady
     */
    int minWidth;
    /**
     * The minimum height of the component. It is used to calculate
     * <code>ipadx</code>, where the default will be 0.
     * <p>
     * 组件的最小高度。它用于计算<code> ipadx </code>,其中默认值为0。
     * 
     * 
     * @serial
     * @see #ipadx
     */
    int minHeight;

    // The following fields are only used if the anchor is
    // one of BASELINE, BASELINE_LEADING or BASELINE_TRAILING.
    // ascent and descent include the insets and ipady values.
    transient int ascent;
    transient int descent;
    transient Component.BaselineResizeBehavior baselineResizeBehavior;
    // The folllowing two fields are used if the baseline type is
    // CENTER_OFFSET.
    // centerPadding is either 0 or 1 and indicates if
    // the height needs to be padded by one when calculating where the
    // baseline lands
    transient int centerPadding;
    // Where the baseline lands relative to the center of the component.
    transient int centerOffset;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -1000070633030801713L;

    /**
     * Creates a <code>GridBagConstraint</code> object with
     * all of its fields set to their default value.
     * <p>
     *  创建一个<code> GridBagConstraint </code>对象,其所有字段都设置为其默认值。
     * 
     */
    public GridBagConstraints () {
        gridx = RELATIVE;
        gridy = RELATIVE;
        gridwidth = 1;
        gridheight = 1;

        weightx = 0;
        weighty = 0;
        anchor = CENTER;
        fill = NONE;

        insets = new Insets(0, 0, 0, 0);
        ipadx = 0;
        ipady = 0;
    }

    /**
     * Creates a <code>GridBagConstraints</code> object with
     * all of its fields set to the passed-in arguments.
     *
     * Note: Because the use of this constructor hinders readability
     * of source code, this constructor should only be used by
     * automatic source code generation tools.
     *
     * <p>
     *  创建一个<code> GridBagConstraints </code>对象,其所有字段都设置为传入的参数。
     * 
     *  注意：因为使用这个构造函数阻碍了源代码的可读性,所以这个构造函数只应该由自动源代码生成工具使用。
     * 
     * 
     * @param gridx     The initial gridx value.
     * @param gridy     The initial gridy value.
     * @param gridwidth The initial gridwidth value.
     * @param gridheight        The initial gridheight value.
     * @param weightx   The initial weightx value.
     * @param weighty   The initial weighty value.
     * @param anchor    The initial anchor value.
     * @param fill      The initial fill value.
     * @param insets    The initial insets value.
     * @param ipadx     The initial ipadx value.
     * @param ipady     The initial ipady value.
     *
     * @see java.awt.GridBagConstraints#gridx
     * @see java.awt.GridBagConstraints#gridy
     * @see java.awt.GridBagConstraints#gridwidth
     * @see java.awt.GridBagConstraints#gridheight
     * @see java.awt.GridBagConstraints#weightx
     * @see java.awt.GridBagConstraints#weighty
     * @see java.awt.GridBagConstraints#anchor
     * @see java.awt.GridBagConstraints#fill
     * @see java.awt.GridBagConstraints#insets
     * @see java.awt.GridBagConstraints#ipadx
     * @see java.awt.GridBagConstraints#ipady
     *
     * @since 1.2
     */
    public GridBagConstraints(int gridx, int gridy,
                              int gridwidth, int gridheight,
                              double weightx, double weighty,
                              int anchor, int fill,
                              Insets insets, int ipadx, int ipady) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        this.fill = fill;
        this.ipadx = ipadx;
        this.ipady = ipady;
        this.insets = insets;
        this.anchor  = anchor;
        this.weightx = weightx;
        this.weighty = weighty;
    }

    /**
     * Creates a copy of this grid bag constraint.
     * <p>
     * 
     * @return     a copy of this grid bag constraint
     */
    public Object clone () {
        try {
            GridBagConstraints c = (GridBagConstraints)super.clone();
            c.insets = (Insets)insets.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    boolean isVerticallyResizable() {
        return (fill == BOTH || fill == VERTICAL);
    }
}
