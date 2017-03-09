/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.*;
import static java.awt.Component.BaselineResizeBehavior;
import static javax.swing.LayoutStyle.ComponentPlacement;
import static javax.swing.SwingConstants.HORIZONTAL;
import static javax.swing.SwingConstants.VERTICAL;

/**
 * {@code GroupLayout} is a {@code LayoutManager} that hierarchically
 * groups components in order to position them in a {@code Container}.
 * {@code GroupLayout} is intended for use by builders, but may be
 * hand-coded as well.
 * Grouping is done by instances of the {@link Group Group} class. {@code
 * GroupLayout} supports two types of groups. A sequential group
 * positions its child elements sequentially, one after another. A
 * parallel group aligns its child elements in one of four ways.
 * <p>
 * Each group may contain any number of elements, where an element is
 * a {@code Group}, {@code Component}, or gap. A gap can be thought
 * of as an invisible component with a minimum, preferred and maximum
 * size. In addition {@code GroupLayout} supports a preferred gap,
 * whose value comes from {@code LayoutStyle}.
 * <p>
 * Elements are similar to a spring. Each element has a range as
 * specified by a minimum, preferred and maximum.  Gaps have either a
 * developer-specified range, or a range determined by {@code
 * LayoutStyle}. The range for {@code Component}s is determined from
 * the {@code Component}'s {@code getMinimumSize}, {@code
 * getPreferredSize} and {@code getMaximumSize} methods. In addition,
 * when adding {@code Component}s you may specify a particular range
 * to use instead of that from the component. The range for a {@code
 * Group} is determined by the type of group. A {@code ParallelGroup}'s
 * range is the maximum of the ranges of its elements. A {@code
 * SequentialGroup}'s range is the sum of the ranges of its elements.
 * <p>
 * {@code GroupLayout} treats each axis independently.  That is, there
 * is a group representing the horizontal axis, and a group
 * representing the vertical axis.  The horizontal group is
 * responsible for determining the minimum, preferred and maximum size
 * along the horizontal axis as well as setting the x and width of the
 * components contained in it. The vertical group is responsible for
 * determining the minimum, preferred and maximum size along the
 * vertical axis as well as setting the y and height of the
 * components contained in it. Each {@code Component} must exist in both
 * a horizontal and vertical group, otherwise an {@code IllegalStateException}
 * is thrown during layout, or when the minimum, preferred or
 * maximum size is requested.
 * <p>
 * The following diagram shows a sequential group along the horizontal
 * axis. The sequential group contains three components. A parallel group
 * was used along the vertical axis.
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.1.gif" alt="Sequential group along the horizontal axis in three components">
 * <p>
 * To reinforce that each axis is treated independently the diagram shows
 * the range of each group and element along each axis. The
 * range of each component has been projected onto the axes,
 * and the groups are rendered in blue (horizontal) and red (vertical).
 * For readability there is a gap between each of the elements in the
 * sequential group.
 * <p>
 * The sequential group along the horizontal axis is rendered as a solid
 * blue line. Notice the sequential group is the sum of the children elements
 * it contains.
 * <p>
 * Along the vertical axis the parallel group is the maximum of the height
 * of each of the components. As all three components have the same height,
 * the parallel group has the same height.
 * <p>
 * The following diagram shows the same three components, but with the
 * parallel group along the horizontal axis and the sequential group along
 * the vertical axis.
 *
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.2.gif" alt="Sequential group along the vertical axis in three components">
 * <p>
 * As {@code c1} is the largest of the three components, the parallel
 * group is sized to {@code c1}. As {@code c2} and {@code c3} are smaller
 * than {@code c1} they are aligned based on the alignment specified
 * for the component (if specified) or the default alignment of the
 * parallel group. In the diagram {@code c2} and {@code c3} were created
 * with an alignment of {@code LEADING}. If the component orientation were
 * right-to-left then {@code c2} and {@code c3} would be positioned on
 * the opposite side.
 * <p>
 * The following diagram shows a sequential group along both the horizontal
 * and vertical axis.
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.3.gif" alt="Sequential group along both the horizontal and vertical axis in three components">
 * <p>
 * {@code GroupLayout} provides the ability to insert gaps between
 * {@code Component}s. The size of the gap is determined by an
 * instance of {@code LayoutStyle}. This may be turned on using the
 * {@code setAutoCreateGaps} method.  Similarly, you may use
 * the {@code setAutoCreateContainerGaps} method to insert gaps
 * between components that touch the edge of the parent container and the
 * container.
 * <p>
 * The following builds a panel consisting of two labels in
 * one column, followed by two textfields in the next column:
 * <pre>
 *   JComponent panel = ...;
 *   GroupLayout layout = new GroupLayout(panel);
 *   panel.setLayout(layout);
 *
 *   // Turn on automatically adding gaps between components
 *   layout.setAutoCreateGaps(true);
 *
 *   // Turn on automatically creating gaps between components that touch
 *   // the edge of the container and the container.
 *   layout.setAutoCreateContainerGaps(true);
 *
 *   // Create a sequential group for the horizontal axis.
 *
 *   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
 *
 *   // The sequential group in turn contains two parallel groups.
 *   // One parallel group contains the labels, the other the text fields.
 *   // Putting the labels in a parallel group along the horizontal axis
 *   // positions them at the same x location.
 *   //
 *   // Variable indentation is used to reinforce the level of grouping.
 *   hGroup.addGroup(layout.createParallelGroup().
 *            addComponent(label1).addComponent(label2));
 *   hGroup.addGroup(layout.createParallelGroup().
 *            addComponent(tf1).addComponent(tf2));
 *   layout.setHorizontalGroup(hGroup);
 *
 *   // Create a sequential group for the vertical axis.
 *   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
 *
 *   // The sequential group contains two parallel groups that align
 *   // the contents along the baseline. The first parallel group contains
 *   // the first label and text field, and the second parallel group contains
 *   // the second label and text field. By using a sequential group
 *   // the labels and text fields are positioned vertically after one another.
 *   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
 *            addComponent(label1).addComponent(tf1));
 *   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
 *            addComponent(label2).addComponent(tf2));
 *   layout.setVerticalGroup(vGroup);
 * </pre>
 * <p>
 * When run the following is produced.
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.example.png" alt="Produced horizontal/vertical form">
 * <p>
 * This layout consists of the following.
 * <ul><li>The horizontal axis consists of a sequential group containing two
 *         parallel groups.  The first parallel group contains the labels,
 *         and the second parallel group contains the text fields.
 *     <li>The vertical axis consists of a sequential group
 *         containing two parallel groups.  The parallel groups are configured
 *         to align their components along the baseline. The first parallel
 *         group contains the first label and first text field, and
 *         the second group consists of the second label and second
 *         text field.
 * </ul>
 * There are a couple of things to notice in this code:
 * <ul>
 *   <li>You need not explicitly add the components to the container; this
 *       is indirectly done by using one of the {@code add} methods of
 *       {@code Group}.
 *   <li>The various {@code add} methods return
 *       the caller.  This allows for easy chaining of invocations.  For
 *       example, {@code group.addComponent(label1).addComponent(label2);} is
 *       equivalent to
 *       {@code group.addComponent(label1); group.addComponent(label2);}.
 *   <li>There are no public constructors for {@code Group}s; instead
 *       use the create methods of {@code GroupLayout}.
 * </ul>
 *
 * <p>
 *  {@code GroupLayout}是一个{@code LayoutManager},对分组进行分层分组,以便将它们放置在{@code Container}中。
 *  {@code GroupLayout}适用于构建者使用,但也可以手动编码。分组由{@link Group Group}类的实例完成。 {@code GroupLayout}支持两种类型的组。
 * 顺序组按顺序依次对其子元素进行定位。并行组以四种方式之一对齐其子元素。
 * <p>
 *  每个组可以包含任意数量的元素,其中元素是{@code Group},{@code Component}或gap。间隙可以被认为是具有最小,优选和最大尺寸的不可见组件。
 * 此外,{@code GroupLayout}支持首选间隔,其值来自{@code LayoutStyle}。
 * <p>
 * 元素类似于弹簧。每个元素具有由最小值,优选值和最大值指定的范围。间隙具有开发人员指定的范围,或由{@code LayoutStyle}确定的范围。
 *  {@code Component}的范围由{@code Component}的{@code getMinimumSize},{@code getPreferredSize}和{@code getMaximumSize}
 * 方法确定。
 * 元素类似于弹簧。每个元素具有由最小值,优选值和最大值指定的范围。间隙具有开发人员指定的范围,或由{@code LayoutStyle}确定的范围。
 * 此外,当添加{@code Component}时,您可以指定要使用的特定范围,而不是组件中的范围。 {@code Group}的范围由组的类型决定。
 *  {@code ParallelGroup}的范围是其元素范围的最大值。 {@code SequentialGroup}的范围是其元素范围的总和。
 * <p>
 *  {@code GroupLayout}单独处理每个轴。也就是说,存在表示水平轴的组和表示垂直轴的组。水平组负责确定沿水平轴的最小,优选和最大尺寸,以及设置其中包含的分量的x和宽度。
 * 垂直组负责确定沿着垂直轴的最小,优选和最大尺寸,以及设置其中包含的分量的y和高度。
 * 每个{@code Component}必须同时存在于水平和垂直组中,否则在布局期间或在请求最小,首选或最大大小时会抛出{@code IllegalStateException}。
 * <p>
 * 下图显示了沿水平轴的顺序组。顺序组包含三个组件。沿着垂直轴使用平行组。
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.1.gif" alt="Sequential group along the horizontal axis in three components">
 * <p>
 *  为了强化每个轴被独立地处理,该图示出了沿着每个轴的每个组和元素的范围。每个分量的范围已经投影到轴上,并且分组以蓝色(水平)和红色(垂直)呈现。为了可读性,在顺序组中的每个元素之间存在间隙。
 * <p>
 *  沿着水平轴的顺序组被呈现为纯蓝色线。请注意,序列组是其包含的子元素的总和。
 * <p>
 *  沿着垂直轴,平行组是每个部件的高度的最大值。由于所有三个部件具有相同的高度,平行组具有相同的高度。
 * <p>
 *  下图显示相同的三个组件,但并行组沿水平轴,顺序组沿垂直轴。
 * 
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.2.gif" alt="Sequential group along the vertical axis in three components">
 * <p>
 * 由于{@code c1}是三个组件中最大的,因此并行组的大小为{@code c1}。
 * 由于{@code c2}和{@code c3}小于{@code c1},因此会根据为组件指定的对齐方式(如果指定)或并行组的默认对齐方式进行对齐。
 * 在图表中,{@code c2}和{@code c3}的对齐方式为{@code LEADING}。如果组件方向是从右到左,则{@code c2}和{@code c3}将位于相反的一侧。
 * <p>
 *  下图显示了沿水平轴和垂直轴的顺序组。
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.3.gif" alt="Sequential group along both the horizontal and vertical axis in three components">
 * <p>
 *  {@code GroupLayout}提供了在{@code Component}之间插入间隙的能力。间隙的大小由{@code LayoutStyle}的实例确定。
 * 这可以使用{@code setAutoCreateGaps}方法打开。同样,您可以使用{@code setAutoCreateContainerGaps}方法在接触父容器和容器边缘的组件之间插入间隙。
 * <p>
 *  以下内容构建了一个面板,其中包含一列中的两个标签,后面是下一列中的两个文本字段：
 * <pre>
 *  JComponent panel = ...; GroupLayout layout = new GroupLayout(panel); panel.setLayout(layout);
 * 
 *  //打开自动添加组件之间的间隙layout.setAutoCreateGaps(true);
 * 
 *  //打开会自动在触摸容器边缘和容器的组件之间创建间隙。 layout.setAutoCreateContainerGaps(true);
 * 
 *  //为水平轴创建顺序组。
 * 
 * GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
 * 
 *  //顺序组又包含两个并行组。 //一个并行组包含标签,另一个并行组包含文本字段。 //将标签放在水平轴的平行组中//将它们定位在同一个x位置。 // //变量缩进用于加强分组的级别。
 *  hGroup.addGroup(layout.createParallelGroup()。
 * addComponent(label1).addComponent(label2)); hGroup.addGroup(layout.createParallelGroup()。
 * addComponent(tf1).addComponent(tf2)); layout.setHorizo​​ntalGroup(hGroup);。
 * 
 *  //为垂直轴创建顺序组。 GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
 * 
 *  //顺序组包含两个平行组,用于沿着基线对齐//内容。第一个并行组包含//第一个标签和文本字段,第二个并行组包含//第二个标签和文本字段。通过使用顺序组//标签和文本字段相互垂直放置。
 *  vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)。
 * addComponent(label1).addComponent(tf1)); vGroup.addGroup(layout.createParallelGroup(Alignment.BASELIN
 * E)。
 *  vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)。
 * addComponent(label2).addComponent(tf2)); layout.setVerticalGroup(vGroup);。
 * </pre>
 * <p>
 *  运行时生成以下内容。
 * <p style="text-align:center">
 * <img src="doc-files/groupLayout.example.png" alt="Produced horizontal/vertical form">
 * <p>
 * 此布局包括以下内容。 <ul> <li>横轴由包含两个平行组的顺序组组成。第一个并行组包含标签,第二个并行组包含文本字段。 <li>纵轴由包含两个平行组的顺序组组成。并行组配置为沿着基线对齐其组件。
 * 第一并行组包含第一标签和第一文本字段,并且第二组包括第二标签和第二文本字段。
 * </ul>
 *  在这段代码中有几个要注意的事情：
 * <ul>
 *  <li>您无需将组件显式添加到容器;这是通过使用{@code Group}的{@code add}方法之一间接完成的。 <li>各种{@code add}方法返回调用者。这允许容易地链接调用。
 * 例如,{@code group.addComponent(label1).addComponent(label2);}等价于{@code group.addComponent(label1); group.addComponent(label2);}
 * 。
 *  <li>您无需将组件显式添加到容器;这是通过使用{@code Group}的{@code add}方法之一间接完成的。 <li>各种{@code add}方法返回调用者。这允许容易地链接调用。
 *  <li> {@code Group}没有公共建构函式;而是使用{@code GroupLayout}的create方法。
 * </ul>
 * 
 * 
 * @author Tomas Pavek
 * @author Jan Stola
 * @author Scott Violet
 * @since 1.6
 */
public class GroupLayout implements LayoutManager2 {
    // Used in size calculations
    private static final int MIN_SIZE = 0;

    private static final int PREF_SIZE = 1;

    private static final int MAX_SIZE = 2;

    // Used by prepare, indicates min, pref or max isn't going to be used.
    private static final int SPECIFIC_SIZE = 3;

    private static final int UNSET = Integer.MIN_VALUE;

    /**
     * Indicates the size from the component or gap should be used for a
     * particular range value.
     *
     * <p>
     *  指示来自组件或间隙的大小应用于特定范围值。
     * 
     * 
     * @see Group
     */
    public static final int DEFAULT_SIZE = -1;

    /**
     * Indicates the preferred size from the component or gap should
     * be used for a particular range value.
     *
     * <p>
     *  指示来自组件或间隙的首选大小应用于特定范围值。
     * 
     * 
     * @see Group
     */
    public static final int PREFERRED_SIZE = -2;

    // Whether or not we automatically try and create the preferred
    // padding between components.
    private boolean autocreatePadding;

    // Whether or not we automatically try and create the preferred
    // padding between components the touch the edge of the container and
    // the container.
    private boolean autocreateContainerPadding;

    /**
     * Group responsible for layout along the horizontal axis.  This is NOT
     * the user specified group, use getHorizontalGroup to dig that out.
     * <p>
     *  负责沿水平轴布置的组。这不是用户指定的组,使用getHorizo​​ntalGroup来挖掘它。
     * 
     */
    private Group horizontalGroup;

    /**
     * Group responsible for layout along the vertical axis.  This is NOT
     * the user specified group, use getVerticalGroup to dig that out.
     * <p>
     * 负责沿垂直轴布置的组。这不是用户指定的组,请使用getVerticalGroup来挖掘它。
     * 
     */
    private Group verticalGroup;

    // Maps from Component to ComponentInfo.  This is used for tracking
    // information specific to a Component.
    private Map<Component,ComponentInfo> componentInfos;

    // Container we're doing layout for.
    private Container host;

    // Used by areParallelSiblings, cached to avoid excessive garbage.
    private Set<Spring> tmpParallelSet;

    // Indicates Springs have changed in some way since last change.
    private boolean springsChanged;

    // Indicates invalidateLayout has been invoked.
    private boolean isValid;

    // Whether or not any preferred padding (or container padding) springs
    // exist
    private boolean hasPreferredPaddingSprings;

    /**
     * The LayoutStyle instance to use, if null the sharedInstance is used.
     * <p>
     *  要使用的LayoutStyle实例,如果为null,则使用sharedInstance。
     * 
     */
    private LayoutStyle layoutStyle;

    /**
     * If true, components that are not visible are treated as though they
     * aren't there.
     * <p>
     *  如果为true,那么不可见的组件将被视为不存在。
     * 
     */
    private boolean honorsVisibility;


    /**
     * Enumeration of the possible ways {@code ParallelGroup} can align
     * its children.
     *
     * <p>
     *  枚举可能的方式{@code ParallelGroup}可以对齐它的孩子。
     * 
     * 
     * @see #createParallelGroup(Alignment)
     * @since 1.6
     */
    public enum Alignment {
        /**
         * Indicates the elements should be
         * aligned to the origin.  For the horizontal axis with a left to
         * right orientation this means aligned to the left edge. For the
         * vertical axis leading means aligned to the top edge.
         *
         * <p>
         *  表示元素应该与原点对齐。对于具有从左到右方向的水平轴,这意味着与左边缘对齐。对于垂直轴导向装置对准顶部边缘。
         * 
         * 
         * @see #createParallelGroup(Alignment)
         */
        LEADING,

        /**
         * Indicates the elements should be aligned to the end of the
         * region.  For the horizontal axis with a left to right
         * orientation this means aligned to the right edge. For the
         * vertical axis trailing means aligned to the bottom edge.
         *
         * <p>
         *  表示元素应该对齐到区域的末尾。对于具有从左到右方向的水平轴,这意味着与右边缘对齐。对于垂直轴拖尾装置与底边对齐。
         * 
         * 
         * @see #createParallelGroup(Alignment)
         */
        TRAILING,

        /**
         * Indicates the elements should be centered in
         * the region.
         *
         * <p>
         *  表示元素应在区域中居中。
         * 
         * 
         * @see #createParallelGroup(Alignment)
         */
        CENTER,

        /**
         * Indicates the elements should be aligned along
         * their baseline.
         *
         * <p>
         *  表示元素应沿其基线对齐。
         * 
         * 
         * @see #createParallelGroup(Alignment)
         * @see #createBaselineGroup(boolean,boolean)
         */
        BASELINE
    }


    private static void checkSize(int min, int pref, int max,
            boolean isComponentSpring) {
        checkResizeType(min, isComponentSpring);
        if (!isComponentSpring && pref < 0) {
            throw new IllegalArgumentException("Pref must be >= 0");
        } else if (isComponentSpring) {
            checkResizeType(pref, true);
        }
        checkResizeType(max, isComponentSpring);
        checkLessThan(min, pref);
        checkLessThan(pref, max);
    }

    private static void checkResizeType(int type, boolean isComponentSpring) {
        if (type < 0 && ((isComponentSpring && type != DEFAULT_SIZE &&
                type != PREFERRED_SIZE) ||
                (!isComponentSpring && type != PREFERRED_SIZE))) {
            throw new IllegalArgumentException("Invalid size");
        }
    }

    private static void checkLessThan(int min, int max) {
        if (min >= 0 && max >= 0 && min > max) {
            throw new IllegalArgumentException(
                    "Following is not met: min<=pref<=max");
        }
    }

    /**
     * Creates a {@code GroupLayout} for the specified {@code Container}.
     *
     * <p>
     *  为指定的{@code Container}创建{@code GroupLayout}。
     * 
     * 
     * @param host the {@code Container} the {@code GroupLayout} is
     *        the {@code LayoutManager} for
     * @throws IllegalArgumentException if host is {@code null}
     */
    public GroupLayout(Container host) {
        if (host == null) {
            throw new IllegalArgumentException("Container must be non-null");
        }
        honorsVisibility = true;
        this.host = host;
        setHorizontalGroup(createParallelGroup(Alignment.LEADING, true));
        setVerticalGroup(createParallelGroup(Alignment.LEADING, true));
        componentInfos = new HashMap<Component,ComponentInfo>();
        tmpParallelSet = new HashSet<Spring>();
    }

    /**
     * Sets whether component visibility is considered when sizing and
     * positioning components. A value of {@code true} indicates that
     * non-visible components should not be treated as part of the
     * layout. A value of {@code false} indicates that components should be
     * positioned and sized regardless of visibility.
     * <p>
     * A value of {@code false} is useful when the visibility of components
     * is dynamically adjusted and you don't want surrounding components and
     * the sizing to change.
     * <p>
     * The specified value is used for components that do not have an
     * explicit visibility specified.
     * <p>
     * The default is {@code true}.
     *
     * <p>
     *  设置在调整和定位组件时是否考虑组件可见性。值为{@code true}表示不可见的组件不应被视为布局的一部分。值为{@code false}表示无论可见性如何,组件应该被定位和调整大小。
     * <p>
     *  当组件的可见性被动态调整,并且您不想让周围组件和大小更改时,{@code false}的值很有用。
     * <p>
     * 指定的值用于没有指定显式可见性的组件。
     * <p>
     *  默认值为{@code true}。
     * 
     * 
     * @param honorsVisibility whether component visibility is considered when
     *                         sizing and positioning components
     * @see #setHonorsVisibility(Component,Boolean)
     */
    public void setHonorsVisibility(boolean honorsVisibility) {
        if (this.honorsVisibility != honorsVisibility) {
            this.honorsVisibility = honorsVisibility;
            springsChanged = true;
            isValid = false;
            invalidateHost();
        }
    }

    /**
     * Returns whether component visibility is considered when sizing and
     * positioning components.
     *
     * <p>
     *  返回在调整和定位组件时是否考虑组件可见性。
     * 
     * 
     * @return whether component visibility is considered when sizing and
     *         positioning components
     */
    public boolean getHonorsVisibility() {
        return honorsVisibility;
    }

    /**
     * Sets whether the component's visibility is considered for
     * sizing and positioning. A value of {@code Boolean.TRUE}
     * indicates that if {@code component} is not visible it should
     * not be treated as part of the layout. A value of {@code false}
     * indicates that {@code component} is positioned and sized
     * regardless of it's visibility.  A value of {@code null}
     * indicates the value specified by the single argument method {@code
     * setHonorsVisibility} should be used.
     * <p>
     * If {@code component} is not a child of the {@code Container} this
     * {@code GroupLayout} is managing, it will be added to the
     * {@code Container}.
     *
     * <p>
     *  设置是否考虑组件的可见性用于大小和定位。值为{@code Boolean.TRUE}表示如果{@code component}不可见,则不应将其视为布局的一部分。
     * 值为{@code false}表示{@code component}的位置和大小,无论其可见性如何。
     * 值为{@code null}表示应使用单一参数方法{@code setHonorsVisibility}指定的值。
     * <p>
     *  如果{@code Component}不是{@code GroupLayout}正在管理的{@code Container}的子级,则会将其添加到{@code Container}中。
     * 
     * 
     * @param component the component
     * @param honorsVisibility whether visibility of this {@code component} should be
     *              considered for sizing and positioning
     * @throws IllegalArgumentException if {@code component} is {@code null}
     * @see #setHonorsVisibility(Component,Boolean)
     */
    public void setHonorsVisibility(Component component,
            Boolean honorsVisibility) {
        if (component == null) {
            throw new IllegalArgumentException("Component must be non-null");
        }
        getComponentInfo(component).setHonorsVisibility(honorsVisibility);
        springsChanged = true;
        isValid = false;
        invalidateHost();
    }

    /**
     * Sets whether a gap between components should automatically be
     * created.  For example, if this is {@code true} and you add two
     * components to a {@code SequentialGroup} a gap between the
     * two components is automatically be created.  The default is
     * {@code false}.
     *
     * <p>
     *  设置是否应自动创建组件之间的间隙。例如,如果这是{@code true},并且向{@code SequentialGroup}添加两个组件,则会自动创建两个组件之间的间隙。
     * 默认值为{@code false}。
     * 
     * 
     * @param autoCreatePadding whether a gap between components is
     *        automatically created
     */
    public void setAutoCreateGaps(boolean autoCreatePadding) {
        if (this.autocreatePadding != autoCreatePadding) {
            this.autocreatePadding = autoCreatePadding;
            invalidateHost();
        }
    }

    /**
     * Returns {@code true} if gaps between components are automatically
     * created.
     *
     * <p>
     *  如果自动创建组件之间的间隙,则返回{@code true}。
     * 
     * 
     * @return {@code true} if gaps between components are automatically
     *         created
     */
    public boolean getAutoCreateGaps() {
        return autocreatePadding;
    }

    /**
     * Sets whether a gap between the container and components that
     * touch the border of the container should automatically be
     * created. The default is {@code false}.
     *
     * <p>
     *  设置是否应自动创建容器和触及容器边框的组件之间的间隙。默认值为{@code false}。
     * 
     * 
     * @param autoCreateContainerPadding whether a gap between the container and
     *        components that touch the border of the container should
     *        automatically be created
     */
    public void setAutoCreateContainerGaps(boolean autoCreateContainerPadding){
        if (this.autocreateContainerPadding != autoCreateContainerPadding) {
            this.autocreateContainerPadding = autoCreateContainerPadding;
            horizontalGroup = createTopLevelGroup(getHorizontalGroup());
            verticalGroup = createTopLevelGroup(getVerticalGroup());
            invalidateHost();
        }
    }

    /**
     * Returns {@code true} if gaps between the container and components that
     * border the container are automatically created.
     *
     * <p>
     *  如果容器和与该容器边界的组件之间的间隙自动创建,则返回{@code true}。
     * 
     * 
     * @return {@code true} if gaps between the container and components that
     *         border the container are automatically created
     */
    public boolean getAutoCreateContainerGaps() {
        return autocreateContainerPadding;
    }

    /**
     * Sets the {@code Group} that positions and sizes
     * components along the horizontal axis.
     *
     * <p>
     * 沿水平轴设置位置和大小组件的{@code Group}。
     * 
     * 
     * @param group the {@code Group} that positions and sizes
     *        components along the horizontal axis
     * @throws IllegalArgumentException if group is {@code null}
     */
    public void setHorizontalGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Group must be non-null");
        }
        horizontalGroup = createTopLevelGroup(group);
        invalidateHost();
    }

    /**
     * Returns the {@code Group} that positions and sizes components
     * along the horizontal axis.
     *
     * <p>
     *  返回沿水平轴定位和缩放组件的{@code Group}。
     * 
     * 
     * @return the {@code Group} responsible for positioning and
     *         sizing component along the horizontal axis
     */
    private Group getHorizontalGroup() {
        int index = 0;
        if (horizontalGroup.springs.size() > 1) {
            index = 1;
        }
        return (Group)horizontalGroup.springs.get(index);
    }

    /**
     * Sets the {@code Group} that positions and sizes
     * components along the vertical axis.
     *
     * <p>
     *  沿垂直轴设置位置和大小组件的{@code Group}。
     * 
     * 
     * @param group the {@code Group} that positions and sizes
     *        components along the vertical axis
     * @throws IllegalArgumentException if group is {@code null}
     */
    public void setVerticalGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Group must be non-null");
        }
        verticalGroup = createTopLevelGroup(group);
        invalidateHost();
    }

    /**
     * Returns the {@code Group} that positions and sizes components
     * along the vertical axis.
     *
     * <p>
     *  返回沿纵轴的位置和大小组件的{@code Group}。
     * 
     * 
     * @return the {@code Group} responsible for positioning and
     *         sizing component along the vertical axis
     */
    private Group getVerticalGroup() {
        int index = 0;
        if (verticalGroup.springs.size() > 1) {
            index = 1;
        }
        return (Group)verticalGroup.springs.get(index);
    }

    /**
     * Wraps the user specified group in a sequential group.  If
     * container gaps should be generated the necessary springs are
     * added.
     * <p>
     *  将用户指定的组包含在顺序组中。如果应该产生容器间隙,则添加必要的弹簧。
     * 
     */
    private Group createTopLevelGroup(Group specifiedGroup) {
        SequentialGroup group = createSequentialGroup();
        if (getAutoCreateContainerGaps()) {
            group.addSpring(new ContainerAutoPreferredGapSpring());
            group.addGroup(specifiedGroup);
            group.addSpring(new ContainerAutoPreferredGapSpring());
        } else {
            group.addGroup(specifiedGroup);
        }
        return group;
    }

    /**
     * Creates and returns a {@code SequentialGroup}.
     *
     * <p>
     *  创建并返回{@code SequentialGroup}。
     * 
     * 
     * @return a new {@code SequentialGroup}
     */
    public SequentialGroup createSequentialGroup() {
        return new SequentialGroup();
    }

    /**
     * Creates and returns a {@code ParallelGroup} with an alignment of
     * {@code Alignment.LEADING}.  This is a cover method for the more
     * general {@code createParallelGroup(Alignment)} method.
     *
     * <p>
     *  创建并返回{@code ParallelGroup},对齐方式为{@code Alignment.LEADING}。
     * 这是一个更通用的{@code createParallelGroup(Alignment)}方法的覆盖方法。
     * 
     * 
     * @return a new {@code ParallelGroup}
     * @see #createParallelGroup(Alignment)
     */
    public ParallelGroup createParallelGroup() {
        return createParallelGroup(Alignment.LEADING);
    }

    /**
     * Creates and returns a {@code ParallelGroup} with the specified
     * alignment.  This is a cover method for the more general {@code
     * createParallelGroup(Alignment,boolean)} method with {@code true}
     * supplied for the second argument.
     *
     * <p>
     *  创建并返回具有指定对齐的{@code ParallelGroup}。
     * 这是一个更通用的{@code createParallelGroup(Alignment,boolean)}方法的覆盖方法,为第二个参数提供了{@code true}。
     * 
     * 
     * @param alignment the alignment for the elements of the group
     * @throws IllegalArgumentException if {@code alignment} is {@code null}
     * @return a new {@code ParallelGroup}
     * @see #createBaselineGroup
     * @see ParallelGroup
     */
    public ParallelGroup createParallelGroup(Alignment alignment) {
        return createParallelGroup(alignment, true);
    }

    /**
     * Creates and returns a {@code ParallelGroup} with the specified
     * alignment and resize behavior. The {@code
     * alignment} argument specifies how children elements are
     * positioned that do not fill the group. For example, if a {@code
     * ParallelGroup} with an alignment of {@code TRAILING} is given
     * 100 and a child only needs 50, the child is
     * positioned at the position 50 (with a component orientation of
     * left-to-right).
     * <p>
     * Baseline alignment is only useful when used along the vertical
     * axis. A {@code ParallelGroup} created with a baseline alignment
     * along the horizontal axis is treated as {@code LEADING}.
     * <p>
     * Refer to {@link GroupLayout.ParallelGroup ParallelGroup} for details on
     * the behavior of baseline groups.
     *
     * <p>
     *  创建并返回具有指定对齐和调整行为的{@code ParallelGroup}。 {@code alignment}参数指定如何定位不填充组的子元素。
     * 例如,如果{@code TRAILING}对齐的{@code ParallelGroup}给定为100,并且子级只需要50,则子级位于位置50(组件方向为从左到右)。
     * <p>
     * 基线对齐仅在沿垂直轴使用时有用。沿着水平轴以基线对齐创建的{@code ParallelGroup}被视为{@code LEADING}。
     * <p>
     *  有关基线组行为的详细信息,请参阅{@link GroupLayout.ParallelGroup ParallelGroup}。
     * 
     * 
     * @param alignment the alignment for the elements of the group
     * @param resizable {@code true} if the group is resizable; if the group
     *        is not resizable the preferred size is used for the
     *        minimum and maximum size of the group
     * @throws IllegalArgumentException if {@code alignment} is {@code null}
     * @return a new {@code ParallelGroup}
     * @see #createBaselineGroup
     * @see GroupLayout.ParallelGroup
     */
    public ParallelGroup createParallelGroup(Alignment alignment,
            boolean resizable){
        if (alignment == null) {
            throw new IllegalArgumentException("alignment must be non null");
        }

        if (alignment == Alignment.BASELINE) {
            return new BaselineGroup(resizable);
        }
        return new ParallelGroup(alignment, resizable);
    }

    /**
     * Creates and returns a {@code ParallelGroup} that aligns it's
     * elements along the baseline.
     *
     * <p>
     *  创建并返回一个{@code ParallelGroup},它沿着基线对齐它的元素。
     * 
     * 
     * @param resizable whether the group is resizable
     * @param anchorBaselineToTop whether the baseline is anchored to
     *        the top or bottom of the group
     * @see #createBaselineGroup
     * @see ParallelGroup
     */
    public ParallelGroup createBaselineGroup(boolean resizable,
            boolean anchorBaselineToTop) {
        return new BaselineGroup(resizable, anchorBaselineToTop);
    }

    /**
     * Forces the specified components to have the same size
     * regardless of their preferred, minimum or maximum sizes. Components that
     * are linked are given the maximum of the preferred size of each of
     * the linked components. For example, if you link two components with
     * a preferred width of 10 and 20, both components are given a width of 20.
     * <p>
     * This can be used multiple times to force any number of
     * components to share the same size.
     * <p>
     * Linked Components are not be resizable.
     *
     * <p>
     *  强制指定的组件具有相同的大小,而不考虑其首选,最小或最大大小。链接的组件给出每个链接组件的优选大小的最大值。例如,如果链接具有首选宽度10和20的两个组件,则两个组件的宽度为20。
     * <p>
     *  这可以多次使用以强制任何数量的组件共享相同的大小。
     * <p>
     *  链接的组件不可调整大小。
     * 
     * 
     * @param components the {@code Component}s that are to have the same size
     * @throws IllegalArgumentException if {@code components} is
     *         {@code null}, or contains {@code null}
     * @see #linkSize(int,Component[])
     */
    public void linkSize(Component... components) {
        linkSize(SwingConstants.HORIZONTAL, components);
        linkSize(SwingConstants.VERTICAL, components);
    }

    /**
     * Forces the specified components to have the same size along the
     * specified axis regardless of their preferred, minimum or
     * maximum sizes. Components that are linked are given the maximum
     * of the preferred size of each of the linked components. For
     * example, if you link two components along the horizontal axis
     * and the preferred width is 10 and 20, both components are given
     * a width of 20.
     * <p>
     * This can be used multiple times to force any number of
     * components to share the same size.
     * <p>
     * Linked {@code Component}s are not be resizable.
     *
     * <p>
     *  强制指定的组件沿指定轴具有相同的大小,而不考虑其首选,最小或最大大小。链接的组件给出每个链接组件的优选大小的最大值。例如,如果沿水平轴链接两个组件,首选宽度为10和20,则两个组件的宽度为20。
     * <p>
     *  这可以多次使用以强制任何数量的组件共享相同的大小。
     * <p>
     *  链接的{@code Component}不可调整大小。
     * 
     * 
     * @param components the {@code Component}s that are to have the same size
     * @param axis the axis to link the size along; one of
     *             {@code SwingConstants.HORIZONTAL} or
     *             {@code SwingConstans.VERTICAL}
     * @throws IllegalArgumentException if {@code components} is
     *         {@code null}, or contains {@code null}; or {@code axis}
     *          is not {@code SwingConstants.HORIZONTAL} or
     *          {@code SwingConstants.VERTICAL}
     */
    public void linkSize(int axis, Component... components) {
        if (components == null) {
            throw new IllegalArgumentException("Components must be non-null");
        }
        for (int counter = components.length - 1; counter >= 0; counter--) {
            Component c = components[counter];
            if (components[counter] == null) {
                throw new IllegalArgumentException(
                        "Components must be non-null");
            }
            // Force the component to be added
            getComponentInfo(c);
        }
        int glAxis;
        if (axis == SwingConstants.HORIZONTAL) {
            glAxis = HORIZONTAL;
        } else if (axis == SwingConstants.VERTICAL) {
            glAxis = VERTICAL;
        } else {
            throw new IllegalArgumentException("Axis must be one of " +
                    "SwingConstants.HORIZONTAL or SwingConstants.VERTICAL");
        }
        LinkInfo master = getComponentInfo(
                components[components.length - 1]).getLinkInfo(glAxis);
        for (int counter = components.length - 2; counter >= 0; counter--) {
            master.add(getComponentInfo(components[counter]));
        }
        invalidateHost();
    }

    /**
     * Replaces an existing component with a new one.
     *
     * <p>
     *  用新的组件替换现有组件。
     * 
     * 
     * @param existingComponent the component that should be removed
     *        and replaced with {@code newComponent}
     * @param newComponent the component to put in
     *        {@code existingComponent}'s place
     * @throws IllegalArgumentException if either of the components are
     *         {@code null} or {@code existingComponent} is not being managed
     *         by this layout manager
     */
    public void replace(Component existingComponent, Component newComponent) {
        if (existingComponent == null || newComponent == null) {
            throw new IllegalArgumentException("Components must be non-null");
        }
        // Make sure all the components have been registered, otherwise we may
        // not update the correct Springs.
        if (springsChanged) {
            registerComponents(horizontalGroup, HORIZONTAL);
            registerComponents(verticalGroup, VERTICAL);
        }
        ComponentInfo info = componentInfos.remove(existingComponent);
        if (info == null) {
            throw new IllegalArgumentException("Component must already exist");
        }
        host.remove(existingComponent);
        if (newComponent.getParent() != host) {
            host.add(newComponent);
        }
        info.setComponent(newComponent);
        componentInfos.put(newComponent, info);
        invalidateHost();
    }

    /**
     * Sets the {@code LayoutStyle} used to calculate the preferred
     * gaps between components. A value of {@code null} indicates the
     * shared instance of {@code LayoutStyle} should be used.
     *
     * <p>
     * 设置用于计算组件之间的首选间隙的{@code LayoutStyle}。值为{@code null}表示应使用{@code LayoutStyle}的共享实例。
     * 
     * 
     * @param layoutStyle the {@code LayoutStyle} to use
     * @see LayoutStyle
     */
    public void setLayoutStyle(LayoutStyle layoutStyle) {
        this.layoutStyle = layoutStyle;
        invalidateHost();
    }

    /**
     * Returns the {@code LayoutStyle} used for calculating the preferred
     * gap between components. This returns the value specified to
     * {@code setLayoutStyle}, which may be {@code null}.
     *
     * <p>
     *  返回用于计算组件之间首选间隙的{@code LayoutStyle}。这会返回指定给{@code setLayoutStyle}的值,它可能是{@code null}。
     * 
     * 
     * @return the {@code LayoutStyle} used for calculating the preferred
     *         gap between components
     */
    public LayoutStyle getLayoutStyle() {
        return layoutStyle;
    }

    private LayoutStyle getLayoutStyle0() {
        LayoutStyle layoutStyle = getLayoutStyle();
        if (layoutStyle == null) {
            layoutStyle = LayoutStyle.getInstance();
        }
        return layoutStyle;
    }

    private void invalidateHost() {
        if (host instanceof JComponent) {
            ((JComponent)host).revalidate();
        } else {
            host.invalidate();
        }
        host.repaint();
    }

    //
    // LayoutManager
    //
    /**
     * Notification that a {@code Component} has been added to
     * the parent container.  You should not invoke this method
     * directly, instead you should use one of the {@code Group}
     * methods to add a {@code Component}.
     *
     * <p>
     *  通知{@code Component}已添加到父容器。您不应该直接调用此方法,而应该使用{@code Group}方法之一添加{@code Component}。
     * 
     * 
     * @param name the string to be associated with the component
     * @param component the {@code Component} to be added
     */
    public void addLayoutComponent(String name, Component component) {
    }

    /**
     * Notification that a {@code Component} has been removed from
     * the parent container.  You should not invoke this method
     * directly, instead invoke {@code remove} on the parent
     * {@code Container}.
     *
     * <p>
     *  通知{@code Component}已从父容器中移除。您不应直接调用此方法,而是在父级{@code Container}上调用{@code remove}。
     * 
     * 
     * @param component the component to be removed
     * @see java.awt.Component#remove
     */
    public void removeLayoutComponent(Component component) {
        ComponentInfo info = componentInfos.remove(component);
        if (info != null) {
            info.dispose();
            springsChanged = true;
            isValid = false;
        }
    }

    /**
     * Returns the preferred size for the specified container.
     *
     * <p>
     *  返回指定容器的首选大小。
     * 
     * 
     * @param parent the container to return the preferred size for
     * @return the preferred size for {@code parent}
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} this was created with
     * @throws IllegalStateException if any of the components added to
     *         this layout are not in both a horizontal and vertical group
     * @see java.awt.Container#getPreferredSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        checkParent(parent);
        prepare(PREF_SIZE);
        return adjustSize(horizontalGroup.getPreferredSize(HORIZONTAL),
                verticalGroup.getPreferredSize(VERTICAL));
    }

    /**
     * Returns the minimum size for the specified container.
     *
     * <p>
     *  返回指定容器的最小大小。
     * 
     * 
     * @param parent the container to return the size for
     * @return the minimum size for {@code parent}
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} that this was created with
     * @throws IllegalStateException if any of the components added to
     *         this layout are not in both a horizontal and vertical group
     * @see java.awt.Container#getMinimumSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        checkParent(parent);
        prepare(MIN_SIZE);
        return adjustSize(horizontalGroup.getMinimumSize(HORIZONTAL),
                verticalGroup.getMinimumSize(VERTICAL));
    }

    /**
     * Lays out the specified container.
     *
     * <p>
     *  退出指定的容器。
     * 
     * 
     * @param parent the container to be laid out
     * @throws IllegalStateException if any of the components added to
     *         this layout are not in both a horizontal and vertical group
     */
    public void layoutContainer(Container parent) {
        // Step 1: Prepare for layout.
        prepare(SPECIFIC_SIZE);
        Insets insets = parent.getInsets();
        int width = parent.getWidth() - insets.left - insets.right;
        int height = parent.getHeight() - insets.top - insets.bottom;
        boolean ltr = isLeftToRight();
        if (getAutoCreateGaps() || getAutoCreateContainerGaps() ||
                hasPreferredPaddingSprings) {
            // Step 2: Calculate autopadding springs
            calculateAutopadding(horizontalGroup, HORIZONTAL, SPECIFIC_SIZE, 0,
                    width);
            calculateAutopadding(verticalGroup, VERTICAL, SPECIFIC_SIZE, 0,
                    height);
        }
        // Step 3: set the size of the groups.
        horizontalGroup.setSize(HORIZONTAL, 0, width);
        verticalGroup.setSize(VERTICAL, 0, height);
        // Step 4: apply the size to the components.
        for (ComponentInfo info : componentInfos.values()) {
            info.setBounds(insets, width, ltr);
        }
    }

    //
    // LayoutManager2
    //
    /**
     * Notification that a {@code Component} has been added to
     * the parent container.  You should not invoke this method
     * directly, instead you should use one of the {@code Group}
     * methods to add a {@code Component}.
     *
     * <p>
     *  通知{@code Component}已添加到父容器。您不应该直接调用此方法,而应该使用{@code Group}方法之一添加{@code Component}。
     * 
     * 
     * @param component the component added
     * @param constraints description of where to place the component
     */
    public void addLayoutComponent(Component component, Object constraints) {
    }

    /**
     * Returns the maximum size for the specified container.
     *
     * <p>
     *  返回指定容器的最大大小。
     * 
     * 
     * @param parent the container to return the size for
     * @return the maximum size for {@code parent}
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} that this was created with
     * @throws IllegalStateException if any of the components added to
     *         this layout are not in both a horizontal and vertical group
     * @see java.awt.Container#getMaximumSize
     */
    public Dimension maximumLayoutSize(Container parent) {
        checkParent(parent);
        prepare(MAX_SIZE);
        return adjustSize(horizontalGroup.getMaximumSize(HORIZONTAL),
                verticalGroup.getMaximumSize(VERTICAL));
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * <p>
     *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     * 
     * @param parent the {@code Container} hosting this {@code LayoutManager}
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} that this was created with
     * @return the alignment; this implementation returns {@code .5}
     */
    public float getLayoutAlignmentX(Container parent) {
        checkParent(parent);
        return .5f;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * <p>
     * 返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     * 
     * @param parent the {@code Container} hosting this {@code LayoutManager}
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} that this was created with
     * @return alignment; this implementation returns {@code .5}
     */
    public float getLayoutAlignmentY(Container parent) {
        checkParent(parent);
        return .5f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     *
     * <p>
     *  使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃。
     * 
     * 
     * @param parent the {@code Container} hosting this LayoutManager
     * @throws IllegalArgumentException if {@code parent} is not
     *         the same {@code Container} that this was created with
     */
    public void invalidateLayout(Container parent) {
        checkParent(parent);
        // invalidateLayout is called from Container.invalidate, which
        // does NOT grab the treelock.  All other methods do.  To make sure
        // there aren't any possible threading problems we grab the tree lock
        // here.
        synchronized(parent.getTreeLock()) {
            isValid = false;
        }
    }

    private void prepare(int sizeType) {
        boolean visChanged = false;
        // Step 1: If not-valid, clear springs and update visibility.
        if (!isValid) {
            isValid = true;
            horizontalGroup.setSize(HORIZONTAL, UNSET, UNSET);
            verticalGroup.setSize(VERTICAL, UNSET, UNSET);
            for (ComponentInfo ci : componentInfos.values()) {
                if (ci.updateVisibility()) {
                    visChanged = true;
                }
                ci.clearCachedSize();
            }
        }
        // Step 2: Make sure components are bound to ComponentInfos
        if (springsChanged) {
            registerComponents(horizontalGroup, HORIZONTAL);
            registerComponents(verticalGroup, VERTICAL);
        }
        // Step 3: Adjust the autopadding. This removes existing
        // autopadding, then recalculates where it should go.
        if (springsChanged || visChanged) {
            checkComponents();
            horizontalGroup.removeAutopadding();
            verticalGroup.removeAutopadding();
            if (getAutoCreateGaps()) {
                insertAutopadding(true);
            } else if (hasPreferredPaddingSprings ||
                    getAutoCreateContainerGaps()) {
                insertAutopadding(false);
            }
            springsChanged = false;
        }
        // Step 4: (for min/pref/max size calculations only) calculate the
        // autopadding. This invokes for unsetting the calculated values, then
        // recalculating them.
        // If sizeType == SPECIFIC_SIZE, it indicates we're doing layout, this
        // step will be done later on.
        if (sizeType != SPECIFIC_SIZE && (getAutoCreateGaps() ||
                getAutoCreateContainerGaps() || hasPreferredPaddingSprings)) {
            calculateAutopadding(horizontalGroup, HORIZONTAL, sizeType, 0, 0);
            calculateAutopadding(verticalGroup, VERTICAL, sizeType, 0, 0);
        }
    }

    private void calculateAutopadding(Group group, int axis, int sizeType,
            int origin, int size) {
        group.unsetAutopadding();
        switch(sizeType) {
            case MIN_SIZE:
                size = group.getMinimumSize(axis);
                break;
            case PREF_SIZE:
                size = group.getPreferredSize(axis);
                break;
            case MAX_SIZE:
                size = group.getMaximumSize(axis);
                break;
            default:
                break;
        }
        group.setSize(axis, origin, size);
        group.calculateAutopadding(axis);
    }

    private void checkComponents() {
        for (ComponentInfo info : componentInfos.values()) {
            if (info.horizontalSpring == null) {
                throw new IllegalStateException(info.component +
                        " is not attached to a horizontal group");
            }
            if (info.verticalSpring == null) {
                throw new IllegalStateException(info.component +
                        " is not attached to a vertical group");
            }
        }
    }

    private void registerComponents(Group group, int axis) {
        List<Spring> springs = group.springs;
        for (int counter = springs.size() - 1; counter >= 0; counter--) {
            Spring spring = springs.get(counter);
            if (spring instanceof ComponentSpring) {
                ((ComponentSpring)spring).installIfNecessary(axis);
            } else if (spring instanceof Group) {
                registerComponents((Group)spring, axis);
            }
        }
    }

    private Dimension adjustSize(int width, int height) {
        Insets insets = host.getInsets();
        return new Dimension(width + insets.left + insets.right,
                height + insets.top + insets.bottom);
    }

    private void checkParent(Container parent) {
        if (parent != host) {
            throw new IllegalArgumentException(
                    "GroupLayout can only be used with one Container at a time");
        }
    }

    /**
     * Returns the {@code ComponentInfo} for the specified Component,
     * creating one if necessary.
     * <p>
     *  返回指定组件的{@code ComponentInfo},如果需要,创建一个。
     * 
     */
    private ComponentInfo getComponentInfo(Component component) {
        ComponentInfo info = componentInfos.get(component);
        if (info == null) {
            info = new ComponentInfo(component);
            componentInfos.put(component, info);
            if (component.getParent() != host) {
                host.add(component);
            }
        }
        return info;
    }

    /**
     * Adjusts the autopadding springs for the horizontal and vertical
     * groups.  If {@code insert} is {@code true} this will insert auto padding
     * springs, otherwise this will only adjust the springs that
     * comprise auto preferred padding springs.
     * <p>
     *  调整水平和垂直组的自动装载弹簧。如果{@code insert}是{@code true},这将插入自动填充弹簧,否则只会调整包含自动首选填充弹簧的弹簧。
     * 
     */
    private void insertAutopadding(boolean insert) {
        horizontalGroup.insertAutopadding(HORIZONTAL,
                new ArrayList<AutoPreferredGapSpring>(1),
                new ArrayList<AutoPreferredGapSpring>(1),
                new ArrayList<ComponentSpring>(1),
                new ArrayList<ComponentSpring>(1), insert);
        verticalGroup.insertAutopadding(VERTICAL,
                new ArrayList<AutoPreferredGapSpring>(1),
                new ArrayList<AutoPreferredGapSpring>(1),
                new ArrayList<ComponentSpring>(1),
                new ArrayList<ComponentSpring>(1), insert);
    }

    /**
     * Returns {@code true} if the two Components have a common ParallelGroup
     * ancestor along the particular axis.
     * <p>
     *  如果两个组件具有沿特定轴的公共ParallelGroup祖先,则返回{@code true}。
     * 
     */
    private boolean areParallelSiblings(Component source, Component target,
            int axis) {
        ComponentInfo sourceInfo = getComponentInfo(source);
        ComponentInfo targetInfo = getComponentInfo(target);
        Spring sourceSpring;
        Spring targetSpring;
        if (axis == HORIZONTAL) {
            sourceSpring = sourceInfo.horizontalSpring;
            targetSpring = targetInfo.horizontalSpring;
        } else {
            sourceSpring = sourceInfo.verticalSpring;
            targetSpring = targetInfo.verticalSpring;
        }
        Set<Spring> sourcePath = tmpParallelSet;
        sourcePath.clear();
        Spring spring = sourceSpring.getParent();
        while (spring != null) {
            sourcePath.add(spring);
            spring = spring.getParent();
        }
        spring = targetSpring.getParent();
        while (spring != null) {
            if (sourcePath.contains(spring)) {
                sourcePath.clear();
                while (spring != null) {
                    if (spring instanceof ParallelGroup) {
                        return true;
                    }
                    spring = spring.getParent();
                }
                return false;
            }
            spring = spring.getParent();
        }
        sourcePath.clear();
        return false;
    }

    private boolean isLeftToRight() {
        return host.getComponentOrientation().isLeftToRight();
    }

    /**
     * Returns a string representation of this {@code GroupLayout}.
     * This method is intended to be used for debugging purposes,
     * and the content and format of the returned string may vary
     * between implementations.
     *
     * <p>
     *  返回此{@code GroupLayout}的字符串表示形式。此方法旨在用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 
     * 
     * @return a string representation of this {@code GroupLayout}
     **/
    public String toString() {
        if (springsChanged) {
            registerComponents(horizontalGroup, HORIZONTAL);
            registerComponents(verticalGroup, VERTICAL);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("HORIZONTAL\n");
        createSpringDescription(buffer, horizontalGroup, "  ", HORIZONTAL);
        buffer.append("\nVERTICAL\n");
        createSpringDescription(buffer, verticalGroup, "  ", VERTICAL);
        return buffer.toString();
    }

    private void createSpringDescription(StringBuffer buffer, Spring spring,
            String indent, int axis) {
        String origin = "";
        String padding = "";
        if (spring instanceof ComponentSpring) {
            ComponentSpring cSpring = (ComponentSpring)spring;
            origin = Integer.toString(cSpring.getOrigin()) + " ";
            String name = cSpring.getComponent().getName();
            if (name != null) {
                origin = "name=" + name + ", ";
            }
        }
        if (spring instanceof AutoPreferredGapSpring) {
            AutoPreferredGapSpring paddingSpring =
                    (AutoPreferredGapSpring)spring;
            padding = ", userCreated=" + paddingSpring.getUserCreated() +
                    ", matches=" + paddingSpring.getMatchDescription();
        }
        buffer.append(indent + spring.getClass().getName() + " " +
                Integer.toHexString(spring.hashCode()) + " " +
                origin +
                ", size=" + spring.getSize() +
                ", alignment=" + spring.getAlignment() +
                " prefs=[" + spring.getMinimumSize(axis) +
                " " + spring.getPreferredSize(axis) +
                " " + spring.getMaximumSize(axis) +
                padding + "]\n");
        if (spring instanceof Group) {
            List<Spring> springs = ((Group)spring).springs;
            indent += "  ";
            for (int counter = 0; counter < springs.size(); counter++) {
                createSpringDescription(buffer, springs.get(counter), indent,
                        axis);
            }
        }
    }


    /**
     * Spring consists of a range: min, pref and max, a value some where in
     * the middle of that, and a location. Spring caches the
     * min/max/pref.  If the min/pref/max has internally changes, or needs
     * to be updated you must invoke clear.
     * <p>
     *  Spring由一个范围：min,pref和max,一个值,在那里的中间,和一个位置。 Spring缓存min / max / pref。
     * 如果min / pref / max有内部改变,或者需要更新,你必须调用clear。
     * 
     */
    private abstract class Spring {
        private int size;
        private int min;
        private int max;
        private int pref;
        private Spring parent;

        private Alignment alignment;

        Spring() {
            min = pref = max = UNSET;
        }

        /**
         * Calculates and returns the minimum size.
         *
         * <p>
         *  计算并返回最小大小。
         * 
         * 
         * @param axis the axis of layout; one of HORIZONTAL or VERTICAL
         * @return the minimum size
         */
        abstract int calculateMinimumSize(int axis);

        /**
         * Calculates and returns the preferred size.
         *
         * <p>
         *  计算并返回首选大小。
         * 
         * 
         * @param axis the axis of layout; one of HORIZONTAL or VERTICAL
         * @return the preferred size
         */
        abstract int calculatePreferredSize(int axis);

        /**
         * Calculates and returns the minimum size.
         *
         * <p>
         *  计算并返回最小大小。
         * 
         * 
         * @param axis the axis of layout; one of HORIZONTAL or VERTICAL
         * @return the minimum size
         */
        abstract int calculateMaximumSize(int axis);

        /**
         * Sets the parent of this Spring.
         * <p>
         *  设置此Spring的父级。
         * 
         */
        void setParent(Spring parent) {
            this.parent = parent;
        }

        /**
         * Returns the parent of this spring.
         * <p>
         *  返回此弹簧的父级。
         * 
         */
        Spring getParent() {
            return parent;
        }

        // This is here purely as a convenience for ParallelGroup to avoid
        // having to track alignment separately.
        void setAlignment(Alignment alignment) {
            this.alignment = alignment;
        }

        /**
         * Alignment for this Spring, this may be null.
         * <p>
         * 对于这个Spring,这可能为null。
         * 
         */
        Alignment getAlignment() {
            return alignment;
        }

        /**
         * Returns the minimum size.
         * <p>
         *  返回最小大小。
         * 
         */
        final int getMinimumSize(int axis) {
            if (min == UNSET) {
                min = constrain(calculateMinimumSize(axis));
            }
            return min;
        }

        /**
         * Returns the preferred size.
         * <p>
         *  返回首选大小。
         * 
         */
        final int getPreferredSize(int axis) {
            if (pref == UNSET) {
                pref = constrain(calculatePreferredSize(axis));
            }
            return pref;
        }

        /**
         * Returns the maximum size.
         * <p>
         *  返回最大大小。
         * 
         */
        final int getMaximumSize(int axis) {
            if (max == UNSET) {
                max = constrain(calculateMaximumSize(axis));
            }
            return max;
        }

        /**
         * Sets the value and location of the spring.  Subclasses
         * will want to invoke super, then do any additional sizing.
         *
         * <p>
         *  设置弹簧的值和位置。子类将要调用super,然后做任何额外的大小调整。
         * 
         * 
         * @param axis HORIZONTAL or VERTICAL
         * @param origin of this Spring
         * @param size of the Spring.  If size is UNSET, this invokes
         *        clear.
         */
        void setSize(int axis, int origin, int size) {
            this.size = size;
            if (size == UNSET) {
                unset();
            }
        }

        /**
         * Resets the cached min/max/pref.
         * <p>
         *  重置缓存的最小/最大/预置。
         * 
         */
        void unset() {
            size = min = pref = max = UNSET;
        }

        /**
         * Returns the current size.
         * <p>
         *  返回当前大小。
         * 
         */
        int getSize() {
            return size;
        }

        int constrain(int value) {
            return Math.min(value, Short.MAX_VALUE);
        }

        int getBaseline() {
            return -1;
        }

        BaselineResizeBehavior getBaselineResizeBehavior() {
            return BaselineResizeBehavior.OTHER;
        }

        final boolean isResizable(int axis) {
            int min = getMinimumSize(axis);
            int pref = getPreferredSize(axis);
            return (min != pref || pref != getMaximumSize(axis));
        }

        /**
         * Returns {@code true} if this spring will ALWAYS have a zero
         * size. This should NOT check the current size, rather it's
         * meant to quickly test if this Spring will always have a
         * zero size.
         *
         * <p>
         *  返回{@code true}如果这个spring总是为零大小。这不应该检查当前的大小,而是它的意思是快速测试,如果这个Spring将总是一个零大小。
         * 
         * 
         * @param treatAutopaddingAsZeroSized if {@code true}, auto padding
         *        springs should be treated as having a size of {@code 0}
         * @return {@code true} if this spring will have a zero size,
         *         {@code false} otherwise
         */
        abstract boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized);
    }

    /**
     * {@code Group} provides the basis for the two types of
     * operations supported by {@code GroupLayout}: laying out
     * components one after another ({@link SequentialGroup SequentialGroup})
     * or aligned ({@link ParallelGroup ParallelGroup}). {@code Group} and
     * its subclasses have no public constructor; to create one use
     * one of {@code createSequentialGroup} or
     * {@code createParallelGroup}. Additionally, taking a {@code Group}
     * created from one {@code GroupLayout} and using it with another
     * will produce undefined results.
     * <p>
     * Various methods in {@code Group} and its subclasses allow you
     * to explicitly specify the range. The arguments to these methods
     * can take two forms, either a value greater than or equal to 0,
     * or one of {@code DEFAULT_SIZE} or {@code PREFERRED_SIZE}. A
     * value greater than or equal to {@code 0} indicates a specific
     * size. {@code DEFAULT_SIZE} indicates the corresponding size
     * from the component should be used.  For example, if {@code
     * DEFAULT_SIZE} is passed as the minimum size argument, the
     * minimum size is obtained from invoking {@code getMinimumSize}
     * on the component. Likewise, {@code PREFERRED_SIZE} indicates
     * the value from {@code getPreferredSize} should be used.
     * The following example adds {@code myComponent} to {@code group}
     * with specific values for the range. That is, the minimum is
     * explicitly specified as 100, preferred as 200, and maximum as
     * 300.
     * <pre>
     *   group.addComponent(myComponent, 100, 200, 300);
     * </pre>
     * The following example adds {@code myComponent} to {@code group} using
     * a combination of the forms. The minimum size is forced to be the
     * same as the preferred size, the preferred size is determined by
     * using {@code myComponent.getPreferredSize} and the maximum is
     * determined by invoking {@code getMaximumSize} on the component.
     * <pre>
     *   group.addComponent(myComponent, GroupLayout.PREFERRED_SIZE,
     *             GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE);
     * </pre>
     * <p>
     * Unless otherwise specified all the methods of {@code Group} and
     * its subclasses that allow you to specify a range throw an
     * {@code IllegalArgumentException} if passed an invalid range. An
     * invalid range is one in which any of the values are &lt; 0 and
     * not one of {@code PREFERRED_SIZE} or {@code DEFAULT_SIZE}, or
     * the following is not met (for specific values): {@code min}
     * &lt;= {@code pref} &lt;= {@code max}.
     * <p>
     * Similarly any methods that take a {@code Component} throw a
     * {@code IllegalArgumentException} if passed {@code null} and any methods
     * that take a {@code Group} throw an {@code NullPointerException} if
     * passed {@code null}.
     *
     * <p>
     *  {@code Group}为{@code GroupLayout}支持的两种类型的操作提供了基础：一个接一个地布置组件({@link SequentialGroup SequentialGroup})
     * 或对齐({@link ParallelGroup ParallelGroup})。
     *  {@code Group}及其子类没有公共构造函数;创建一个使用{@code createSequentialGroup}或{@code createParallelGroup}之一。
     * 此外,从一个{@code GroupLayout}创建的{@code Group}并与另一个{@code GroupLayout}一起使用会产生未定义的结果。
     * <p>
     * {@code Group}及其子类中的各种方法允许您显式指定范围。
     * 这些方法的参数可以采用两种形式,要么是大于等于0的值,要么是{@code DEFAULT_SIZE}或{@code PREFERRED_SIZE}之一。大于或等于{@code 0}的值表示特定大小。
     *  {@code DEFAULT_SIZE}表示应使用组件对应的大小。
     * 例如,如果{@code DEFAULT_SIZE}作为最小大小参数传递,则最小大小通过调用组件上的{@code getMinimumSize}获得。
     * 同样,{@code PREFERRED_SIZE}表示应使用{@code getPreferredSize}的值。
     * 以下示例将{@code myComponent}添加到具有该范围的特定值的{@code group}。也就是说,最小值被明确指定为100,优选为200,最大值为300。
     * <pre>
     *  group.addComponent(myComponent,100,200,300);
     * </pre>
     *  以下示例使用表单的组合将{@code myComponent}添加到{@code group}。
     * 最小大小被强制为与首选大小相同,首选大小通过使用{@code myComponent.getPreferredSize}确定,最大大小由组件上调用{@code getMaximumSize}确定。
     * <pre>
     *  group.addComponent(myComponent,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.DEF
     * AULT_SIZE);。
     * </pre>
     * <p>
     * 除非另有说明,{@code Group}及其子类的所有允许指定范围的方法如果传递的范围无效,都会抛出{@code IllegalArgumentException}。
     * 无效范围是其中任何值< 0,而不是{@code PREFERRED_SIZE}或{@code DEFAULT_SIZE}中的一个,或者不满足以下条件(针对特定值)：{@code min} <= {@code pref}&lt; = {@code max} 。
     * 除非另有说明,{@code Group}及其子类的所有允许指定范围的方法如果传递的范围无效,都会抛出{@code IllegalArgumentException}。
     * <p>
     *  类似的任何方法,{@code组件}抛出一个{@code IllegalArgumentException}如果传递{@code null}和任何方法{@code组}抛出一个{@code NullPointerException}
     * 如果传递{@code null} 。
     * 
     * 
     * @see #createSequentialGroup
     * @see #createParallelGroup
     * @since 1.6
     */
    public abstract class Group extends Spring {
        // private int origin;
        // private int size;
        List<Spring> springs;

        Group() {
            springs = new ArrayList<Spring>();
        }

        /**
         * Adds a {@code Group} to this {@code Group}.
         *
         * <p>
         *  在此{@code Group}中添加{@code Group}。
         * 
         * 
         * @param group the {@code Group} to add
         * @return this {@code Group}
         */
        public Group addGroup(Group group) {
            return addSpring(group);
        }

        /**
         * Adds a {@code Component} to this {@code Group}.
         *
         * <p>
         *  在此{@code Group}中添加{@code Component}。
         * 
         * 
         * @param component the {@code Component} to add
         * @return this {@code Group}
         */
        public Group addComponent(Component component) {
            return addComponent(component, DEFAULT_SIZE, DEFAULT_SIZE,
                    DEFAULT_SIZE);
        }

        /**
         * Adds a {@code Component} to this {@code Group}
         * with the specified size.
         *
         * <p>
         *  在指定大小的{@code Group}中添加{@code Component}。
         * 
         * 
         * @param component the {@code Component} to add
         * @param min the minimum size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @param pref the preferred size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @param max the maximum size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @return this {@code Group}
         */
        public Group addComponent(Component component, int min, int pref,
                int max) {
            return addSpring(new ComponentSpring(component, min, pref, max));
        }

        /**
         * Adds a rigid gap to this {@code Group}.
         *
         * <p>
         *  在此{@code Group}中添加一个严格的间隙。
         * 
         * 
         * @param size the size of the gap
         * @return this {@code Group}
         * @throws IllegalArgumentException if {@code size} is less than
         *         {@code 0}
         */
        public Group addGap(int size) {
            return addGap(size, size, size);
        }

        /**
         * Adds a gap to this {@code Group} with the specified size.
         *
         * <p>
         *  向此{@code Group}的指定大小添加一个空格。
         * 
         * 
         * @param min the minimum size of the gap
         * @param pref the preferred size of the gap
         * @param max the maximum size of the gap
         * @throws IllegalArgumentException if any of the values are
         *         less than {@code 0}
         * @return this {@code Group}
         */
        public Group addGap(int min, int pref, int max) {
            return addSpring(new GapSpring(min, pref, max));
        }

        Spring getSpring(int index) {
            return springs.get(index);
        }

        int indexOf(Spring spring) {
            return springs.indexOf(spring);
        }

        /**
         * Adds the Spring to the list of {@code Spring}s and returns
         * the receiver.
         * <p>
         *  将Spring添加到{@code Spring}的列表中并返回接收器。
         * 
         */
        Group addSpring(Spring spring) {
            springs.add(spring);
            spring.setParent(this);
            if (!(spring instanceof AutoPreferredGapSpring) ||
                    !((AutoPreferredGapSpring)spring).getUserCreated()) {
                springsChanged = true;
            }
            return this;
        }

        //
        // Spring methods
        //

        void setSize(int axis, int origin, int size) {
            super.setSize(axis, origin, size);
            if (size == UNSET) {
                for (int counter = springs.size() - 1; counter >= 0;
                counter--) {
                    getSpring(counter).setSize(axis, origin, size);
                }
            } else {
                setValidSize(axis, origin, size);
            }
        }

        /**
         * This is invoked from {@code setSize} if passed a value
         * other than UNSET.
         * <p>
         *  如果传递的值不是UNSET,则从{@code setSize}调用此方法。
         * 
         */
        abstract void setValidSize(int axis, int origin, int size);

        int calculateMinimumSize(int axis) {
            return calculateSize(axis, MIN_SIZE);
        }

        int calculatePreferredSize(int axis) {
            return calculateSize(axis, PREF_SIZE);
        }

        int calculateMaximumSize(int axis) {
            return calculateSize(axis, MAX_SIZE);
        }

        /**
         * Calculates the specified size.  This is called from
         * one of the {@code getMinimumSize0},
         * {@code getPreferredSize0} or
         * {@code getMaximumSize0} methods.  This will invoke
         * to {@code operator} to combine the values.
         * <p>
         *  计算指定的大小。这是从{@code getMinimumSize0},{@code getPreferredSize0}或{@code getMaximumSize0}方法之一调用的。
         * 这将调用{@code operator}来组合这些值。
         * 
         */
        int calculateSize(int axis, int type) {
            int count = springs.size();
            if (count == 0) {
                return 0;
            }
            if (count == 1) {
                return getSpringSize(getSpring(0), axis, type);
            }
            int size = constrain(operator(getSpringSize(getSpring(0), axis,
                    type), getSpringSize(getSpring(1), axis, type)));
            for (int counter = 2; counter < count; counter++) {
                size = constrain(operator(size, getSpringSize(
                        getSpring(counter), axis, type)));
            }
            return size;
        }

        int getSpringSize(Spring spring, int axis, int type) {
            switch(type) {
                case MIN_SIZE:
                    return spring.getMinimumSize(axis);
                case PREF_SIZE:
                    return spring.getPreferredSize(axis);
                case MAX_SIZE:
                    return spring.getMaximumSize(axis);
            }
            assert false;
            return 0;
        }

        /**
         * Used to compute how the two values representing two springs
         * will be combined.  For example, a group that layed things out
         * one after the next would return {@code a + b}.
         * <p>
         *  用于计算表示两个弹簧的两个值的组合方式。例如,一个接一个地放置一个东西的组将返回{@code a + b}。
         * 
         */
        abstract int operator(int a, int b);

        //
        // Padding
        //

        /**
         * Adjusts the autopadding springs in this group and its children.
         * If {@code insert} is true this will insert auto padding
         * springs, otherwise this will only adjust the springs that
         * comprise auto preferred padding springs.
         *
         * <p>
         * 调整此组中的自动装载弹簧及其子装置。如果{@code insert}为true,这将插入自动填充弹簧,否则这将只调整包含自动首选填充弹簧的弹簧。
         * 
         * 
         * @param axis the axis of the springs; HORIZONTAL or VERTICAL
         * @param leadingPadding List of AutopaddingSprings that occur before
         *                       this Group
         * @param trailingPadding any trailing autopadding springs are added
         *                        to this on exit
         * @param leading List of ComponentSprings that occur before this Group
         * @param trailing any trailing ComponentSpring are added to this
         *                 List
         * @param insert Whether or not to insert AutopaddingSprings or just
         *               adjust any existing AutopaddingSprings.
         */
        abstract void insertAutopadding(int axis,
                List<AutoPreferredGapSpring> leadingPadding,
                List<AutoPreferredGapSpring> trailingPadding,
                List<ComponentSpring> leading, List<ComponentSpring> trailing,
                boolean insert);

        /**
         * Removes any AutopaddingSprings for this Group and its children.
         * <p>
         *  删除此组及其子组的任何AutopaddingSprings。
         * 
         */
        void removeAutopadding() {
            unset();
            for (int counter = springs.size() - 1; counter >= 0; counter--) {
                Spring spring = springs.get(counter);
                if (spring instanceof AutoPreferredGapSpring) {
                    if (((AutoPreferredGapSpring)spring).getUserCreated()) {
                        ((AutoPreferredGapSpring)spring).reset();
                    } else {
                        springs.remove(counter);
                    }
                } else if (spring instanceof Group) {
                    ((Group)spring).removeAutopadding();
                }
            }
        }

        void unsetAutopadding() {
            // Clear cached pref/min/max.
            unset();
            for (int counter = springs.size() - 1; counter >= 0; counter--) {
                Spring spring = springs.get(counter);
                if (spring instanceof AutoPreferredGapSpring) {
                    spring.unset();
                } else if (spring instanceof Group) {
                    ((Group)spring).unsetAutopadding();
                }
            }
        }

        void calculateAutopadding(int axis) {
            for (int counter = springs.size() - 1; counter >= 0; counter--) {
                Spring spring = springs.get(counter);
                if (spring instanceof AutoPreferredGapSpring) {
                    // Force size to be reset.
                    spring.unset();
                    ((AutoPreferredGapSpring)spring).calculatePadding(axis);
                } else if (spring instanceof Group) {
                    ((Group)spring).calculateAutopadding(axis);
                }
            }
            // Clear cached pref/min/max.
            unset();
        }

        @Override
        boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized) {
            for (int i = springs.size() - 1; i >= 0; i--) {
                Spring spring = springs.get(i);
                if (!spring.willHaveZeroSize(treatAutopaddingAsZeroSized)) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * A {@code Group} that positions and sizes its elements
     * sequentially, one after another.  This class has no public
     * constructor, use the {@code createSequentialGroup} method
     * to create one.
     * <p>
     * In order to align a {@code SequentialGroup} along the baseline
     * of a baseline aligned {@code ParallelGroup} you need to specify
     * which of the elements of the {@code SequentialGroup} is used to
     * determine the baseline.  The element used to calculate the
     * baseline is specified using one of the {@code add} methods that
     * take a {@code boolean}. The last element added with a value of
     * {@code true} for {@code useAsBaseline} is used to calculate the
     * baseline.
     *
     * <p>
     *  一个{@code Group},按顺序依次定位和调整其元素的大小。这个类没有公共构造函数,使用{@code createSequentialGroup}方法创建一个。
     * <p>
     *  为了沿着基线对齐{@code ParallelGroup}的基线对齐{@code SequentialGroup},您需要指定{@code SequentialGroup}的哪些元素用于确定基线。
     * 用于计算基线的元素使用采用{@code boolean}的{@code add}方法之一指定。
     * 对于{@code useAsBaseline},添加了值为{@code true}的最后一个元素用于计算基线。
     * 
     * 
     * @see #createSequentialGroup
     * @since 1.6
     */
    public class SequentialGroup extends Group {
        private Spring baselineSpring;

        SequentialGroup() {
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public SequentialGroup addGroup(Group group) {
            return (SequentialGroup)super.addGroup(group);
        }

        /**
         * Adds a {@code Group} to this {@code Group}.
         *
         * <p>
         *  在此{@code Group}中添加{@code Group}。
         * 
         * 
         * @param group the {@code Group} to add
         * @param useAsBaseline whether the specified {@code Group} should
         *        be used to calculate the baseline for this {@code Group}
         * @return this {@code Group}
         */
        public SequentialGroup addGroup(boolean useAsBaseline, Group group) {
            super.addGroup(group);
            if (useAsBaseline) {
                baselineSpring = group;
            }
            return this;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public SequentialGroup addComponent(Component component) {
            return (SequentialGroup)super.addComponent(component);
        }

        /**
         * Adds a {@code Component} to this {@code Group}.
         *
         * <p>
         *  在此{@code Group}中添加{@code Component}。
         * 
         * 
         * @param useAsBaseline whether the specified {@code Component} should
         *        be used to calculate the baseline for this {@code Group}
         * @param component the {@code Component} to add
         * @return this {@code Group}
         */
        public SequentialGroup addComponent(boolean useAsBaseline,
                Component component) {
            super.addComponent(component);
            if (useAsBaseline) {
                baselineSpring = springs.get(springs.size() - 1);
            }
            return this;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public SequentialGroup addComponent(Component component, int min,
                int pref, int max) {
            return (SequentialGroup)super.addComponent(
                    component, min, pref, max);
        }

        /**
         * Adds a {@code Component} to this {@code Group}
         * with the specified size.
         *
         * <p>
         *  在指定大小的{@code Group}中添加{@code Component}。
         * 
         * 
         * @param useAsBaseline whether the specified {@code Component} should
         *        be used to calculate the baseline for this {@code Group}
         * @param component the {@code Component} to add
         * @param min the minimum size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @param pref the preferred size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @param max the maximum size or one of {@code DEFAULT_SIZE} or
         *            {@code PREFERRED_SIZE}
         * @return this {@code Group}
         */
        public SequentialGroup addComponent(boolean useAsBaseline,
                Component component, int min, int pref, int max) {
            super.addComponent(component, min, pref, max);
            if (useAsBaseline) {
                baselineSpring = springs.get(springs.size() - 1);
            }
            return this;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public SequentialGroup addGap(int size) {
            return (SequentialGroup)super.addGap(size);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public SequentialGroup addGap(int min, int pref, int max) {
            return (SequentialGroup)super.addGap(min, pref, max);
        }

        /**
         * Adds an element representing the preferred gap between two
         * components. The element created to represent the gap is not
         * resizable.
         *
         * <p>
         *  添加表示两个组件之间的首选间隙的元素。创建用于表示间隙的元素不可调整大小。
         * 
         * 
         * @param comp1 the first component
         * @param comp2 the second component
         * @param type the type of gap; one of the constants defined by
         *        {@code LayoutStyle}
         * @return this {@code SequentialGroup}
         * @throws IllegalArgumentException if {@code type}, {@code comp1} or
         *         {@code comp2} is {@code null}
         * @see LayoutStyle
         */
        public SequentialGroup addPreferredGap(JComponent comp1,
                JComponent comp2, ComponentPlacement type) {
            return addPreferredGap(comp1, comp2, type, DEFAULT_SIZE,
                    PREFERRED_SIZE);
        }

        /**
         * Adds an element representing the preferred gap between two
         * components.
         *
         * <p>
         *  添加表示两个组件之间的首选间隙的元素。
         * 
         * 
         * @param comp1 the first component
         * @param comp2 the second component
         * @param type the type of gap
         * @param pref the preferred size of the grap; one of
         *        {@code DEFAULT_SIZE} or a value &gt;= 0
         * @param max the maximum size of the gap; one of
         *        {@code DEFAULT_SIZE}, {@code PREFERRED_SIZE}
         *        or a value &gt;= 0
         * @return this {@code SequentialGroup}
         * @throws IllegalArgumentException if {@code type}, {@code comp1} or
         *         {@code comp2} is {@code null}
         * @see LayoutStyle
         */
        public SequentialGroup addPreferredGap(JComponent comp1,
                JComponent comp2, ComponentPlacement type, int pref,
                int max) {
            if (type == null) {
                throw new IllegalArgumentException("Type must be non-null");
            }
            if (comp1 == null || comp2 == null) {
                throw new IllegalArgumentException(
                        "Components must be non-null");
            }
            checkPreferredGapValues(pref, max);
            return (SequentialGroup)addSpring(new PreferredGapSpring(
                    comp1, comp2, type, pref, max));
        }

        /**
         * Adds an element representing the preferred gap between the
         * nearest components.  During layout, neighboring
         * components are found, and the size of the added gap is set
         * based on the preferred gap between the components.  If no
         * neighboring components are found the gap has a size of {@code 0}.
         * <p>
         * The element created to represent the gap is not
         * resizable.
         *
         * <p>
         * 添加表示最近组件之间的首选间隙的元素。在布局期间,找到相邻的部件,并且基于部件之间的优选间隙设置添加的间隙的尺寸。如果没有找到相邻组件,则间隙具有{@code 0}的大小。
         * <p>
         *  创建用于表示间隙的元素不可调整大小。
         * 
         * 
         * @param type the type of gap; one of
         *        {@code LayoutStyle.ComponentPlacement.RELATED} or
         *        {@code LayoutStyle.ComponentPlacement.UNRELATED}
         * @return this {@code SequentialGroup}
         * @see LayoutStyle
         * @throws IllegalArgumentException if {@code type} is not one of
         *         {@code LayoutStyle.ComponentPlacement.RELATED} or
         *         {@code LayoutStyle.ComponentPlacement.UNRELATED}
         */
        public SequentialGroup addPreferredGap(ComponentPlacement type) {
            return addPreferredGap(type, DEFAULT_SIZE, DEFAULT_SIZE);
        }

        /**
         * Adds an element representing the preferred gap between the
         * nearest components.  During layout, neighboring
         * components are found, and the minimum of this
         * gap is set based on the size of the preferred gap between the
         * neighboring components.  If no neighboring components are found the
         * minimum size is set to 0.
         *
         * <p>
         *  添加表示最近组件之间的首选间隙的元素。在布局期间,找到相邻分量,并且基于相邻分量之间的优选间隙的大小来设置该间隙的最小值。如果没有找到相邻组件,则最小大小被设置为0。
         * 
         * 
         * @param type the type of gap; one of
         *        {@code LayoutStyle.ComponentPlacement.RELATED} or
         *        {@code LayoutStyle.ComponentPlacement.UNRELATED}
         * @param pref the preferred size of the grap; one of
         *        {@code DEFAULT_SIZE} or a value &gt;= 0
         * @param max the maximum size of the gap; one of
         *        {@code DEFAULT_SIZE}, {@code PREFERRED_SIZE}
         *        or a value &gt;= 0
         * @return this {@code SequentialGroup}
         * @throws IllegalArgumentException if {@code type} is not one of
         *         {@code LayoutStyle.ComponentPlacement.RELATED} or
         *         {@code LayoutStyle.ComponentPlacement.UNRELATED}
         * @see LayoutStyle
         */
        public SequentialGroup addPreferredGap(ComponentPlacement type,
                int pref, int max) {
            if (type != ComponentPlacement.RELATED &&
                    type != ComponentPlacement.UNRELATED) {
                throw new IllegalArgumentException(
                        "Type must be one of " +
                        "LayoutStyle.ComponentPlacement.RELATED or " +
                        "LayoutStyle.ComponentPlacement.UNRELATED");
            }
            checkPreferredGapValues(pref, max);
            hasPreferredPaddingSprings = true;
            return (SequentialGroup)addSpring(new AutoPreferredGapSpring(
                    type, pref, max));
        }

        /**
         * Adds an element representing the preferred gap between an edge
         * the container and components that touch the border of the
         * container. This has no effect if the added gap does not
         * touch an edge of the parent container.
         * <p>
         * The element created to represent the gap is not
         * resizable.
         *
         * <p>
         *  添加表示容器和触及容器边框的组件之间的首选间隙的元素。如果添加的间隙不接触父容器的边缘,则这没有效果。
         * <p>
         *  创建用于表示间隙的元素不可调整大小。
         * 
         * 
         * @return this {@code SequentialGroup}
         */
        public SequentialGroup addContainerGap() {
            return addContainerGap(DEFAULT_SIZE, DEFAULT_SIZE);
        }

        /**
         * Adds an element representing the preferred gap between one
         * edge of the container and the next or previous {@code
         * Component} with the specified size. This has no
         * effect if the next or previous element is not a {@code
         * Component} and does not touch one edge of the parent
         * container.
         *
         * <p>
         *  添加表示容器的一个边缘和具有指定大小的下一个或上一个{@code Component}之间的首选间隙的元素。
         * 如果下一个或上一个元素不是{@code Component},并且不接触父容器的一个边缘,则这没有效果。
         * 
         * 
         * @param pref the preferred size; one of {@code DEFAULT_SIZE} or a
         *              value &gt;= 0
         * @param max the maximum size; one of {@code DEFAULT_SIZE},
         *        {@code PREFERRED_SIZE} or a value &gt;= 0
         * @return this {@code SequentialGroup}
         */
        public SequentialGroup addContainerGap(int pref, int max) {
            if ((pref < 0 && pref != DEFAULT_SIZE) ||
                    (max < 0 && max != DEFAULT_SIZE && max != PREFERRED_SIZE)||
                    (pref >= 0 && max >= 0 && pref > max)) {
                throw new IllegalArgumentException(
                        "Pref and max must be either DEFAULT_VALUE " +
                        "or >= 0 and pref <= max");
            }
            hasPreferredPaddingSprings = true;
            return (SequentialGroup)addSpring(
                    new ContainerAutoPreferredGapSpring(pref, max));
        }

        int operator(int a, int b) {
            return constrain(a) + constrain(b);
        }

        void setValidSize(int axis, int origin, int size) {
            int pref = getPreferredSize(axis);
            if (size == pref) {
                // Layout at preferred size
                for (Spring spring : springs) {
                    int springPref = spring.getPreferredSize(axis);
                    spring.setSize(axis, origin, springPref);
                    origin += springPref;
                }
            } else if (springs.size() == 1) {
                Spring spring = getSpring(0);
                spring.setSize(axis, origin, Math.min(
                        Math.max(size, spring.getMinimumSize(axis)),
                        spring.getMaximumSize(axis)));
            } else if (springs.size() > 1) {
                // Adjust between min/pref
                setValidSizeNotPreferred(axis, origin, size);
            }
        }

        private void setValidSizeNotPreferred(int axis, int origin, int size) {
            int delta = size - getPreferredSize(axis);
            assert delta != 0;
            boolean useMin = (delta < 0);
            int springCount = springs.size();
            if (useMin) {
                delta *= -1;
            }

            // The following algorithm if used for resizing springs:
            // 1. Calculate the resizability of each spring (pref - min or
            //    max - pref) into a list.
            // 2. Sort the list in ascending order
            // 3. Iterate through each of the resizable Springs, attempting
            //    to give them (pref - size) / resizeCount
            // 4. For any Springs that can not accommodate that much space
            //    add the remainder back to the amount to distribute and
            //    recalculate how must space the remaining springs will get.
            // 5. Set the size of the springs.

            // First pass, sort the resizable springs into the List resizable
            List<SpringDelta> resizable = buildResizableList(axis, useMin);
            int resizableCount = resizable.size();

            if (resizableCount > 0) {
                // How much we would like to give each Spring.
                int sDelta = delta / resizableCount;
                // Remaining space.
                int slop = delta - sDelta * resizableCount;
                int[] sizes = new int[springCount];
                int sign = useMin ? -1 : 1;
                // Second pass, accumulate the resulting deltas (relative to
                // preferred) into sizes.
                for (int counter = 0; counter < resizableCount; counter++) {
                    SpringDelta springDelta = resizable.get(counter);
                    if ((counter + 1) == resizableCount) {
                        sDelta += slop;
                    }
                    springDelta.delta = Math.min(sDelta, springDelta.delta);
                    delta -= springDelta.delta;
                    if (springDelta.delta != sDelta && counter + 1 <
                            resizableCount) {
                        // Spring didn't take all the space, reset how much
                        // each spring will get.
                        sDelta = delta / (resizableCount - counter - 1);
                        slop = delta - sDelta * (resizableCount - counter - 1);
                    }
                    sizes[springDelta.index] = sign * springDelta.delta;
                }

                // And finally set the size of each spring
                for (int counter = 0; counter < springCount; counter++) {
                    Spring spring = getSpring(counter);
                    int sSize = spring.getPreferredSize(axis) + sizes[counter];
                    spring.setSize(axis, origin, sSize);
                    origin += sSize;
                }
            } else {
                // Nothing resizable, use the min or max of each of the
                // springs.
                for (int counter = 0; counter < springCount; counter++) {
                    Spring spring = getSpring(counter);
                    int sSize;
                    if (useMin) {
                        sSize = spring.getMinimumSize(axis);
                    } else {
                        sSize = spring.getMaximumSize(axis);
                    }
                    spring.setSize(axis, origin, sSize);
                    origin += sSize;
                }
            }
        }

        /**
         * Returns the sorted list of SpringDelta's for the current set of
         * Springs. The list is ordered based on the amount of flexibility of
         * the springs.
         * <p>
         *  返回当前Springs集合的SpringDelta的排序列表。该列表基于弹簧的柔性量来排序。
         * 
         */
        private List<SpringDelta> buildResizableList(int axis,
                boolean useMin) {
            // First pass, figure out what is resizable
            int size = springs.size();
            List<SpringDelta> sorted = new ArrayList<SpringDelta>(size);
            for (int counter = 0; counter < size; counter++) {
                Spring spring = getSpring(counter);
                int sDelta;
                if (useMin) {
                    sDelta = spring.getPreferredSize(axis) -
                            spring.getMinimumSize(axis);
                } else {
                    sDelta = spring.getMaximumSize(axis) -
                            spring.getPreferredSize(axis);
                }
                if (sDelta > 0) {
                    sorted.add(new SpringDelta(counter, sDelta));
                }
            }
            Collections.sort(sorted);
            return sorted;
        }

        private int indexOfNextNonZeroSpring(
                int index, boolean treatAutopaddingAsZeroSized) {
            while (index < springs.size()) {
                Spring spring = springs.get(index);
                if (!spring.willHaveZeroSize(treatAutopaddingAsZeroSized)) {
                    return index;
                }
                index++;
            }
            return index;
        }

        @Override
        void insertAutopadding(int axis,
                List<AutoPreferredGapSpring> leadingPadding,
                List<AutoPreferredGapSpring> trailingPadding,
                List<ComponentSpring> leading, List<ComponentSpring> trailing,
                boolean insert) {
            List<AutoPreferredGapSpring> newLeadingPadding =
                    new ArrayList<AutoPreferredGapSpring>(leadingPadding);
            List<AutoPreferredGapSpring> newTrailingPadding =
                    new ArrayList<AutoPreferredGapSpring>(1);
            List<ComponentSpring> newLeading =
                    new ArrayList<ComponentSpring>(leading);
            List<ComponentSpring> newTrailing = null;
            int counter = 0;
            // Warning, this must use springs.size, as it may change during the
            // loop.
            while (counter < springs.size()) {
                Spring spring = getSpring(counter);
                if (spring instanceof AutoPreferredGapSpring) {
                    if (newLeadingPadding.size() == 0) {
                        // Autopadding spring. Set the sources of the
                        // autopadding spring based on newLeading.
                        AutoPreferredGapSpring padding =
                            (AutoPreferredGapSpring)spring;
                        padding.setSources(newLeading);
                        newLeading.clear();
                        counter = indexOfNextNonZeroSpring(counter + 1, true);
                        if (counter == springs.size()) {
                            // Last spring in the list, add it to
                            // trailingPadding.
                            if (!(padding instanceof
                                  ContainerAutoPreferredGapSpring)) {
                                trailingPadding.add(padding);
                            }
                        } else {
                            newLeadingPadding.clear();
                            newLeadingPadding.add(padding);
                        }
                    } else {
                        counter = indexOfNextNonZeroSpring(counter + 1, true);
                    }
                } else {
                    // Not a padding spring
                    if (newLeading.size() > 0 && insert) {
                        // There's leading ComponentSprings, create an
                        // autopadding spring.
                        AutoPreferredGapSpring padding =
                                new AutoPreferredGapSpring();
                        // Force the newly created spring to be considered
                        // by NOT incrementing counter
                        springs.add(counter, padding);
                        continue;
                    }
                    if (spring instanceof ComponentSpring) {
                        // Spring is a Component, make it the target of any
                        // leading AutopaddingSpring.
                        ComponentSpring cSpring = (ComponentSpring)spring;
                        if (!cSpring.isVisible()) {
                            counter++;
                            continue;
                        }
                        for (AutoPreferredGapSpring gapSpring : newLeadingPadding) {
                            gapSpring.addTarget(cSpring, axis);
                        }
                        newLeading.clear();
                        newLeadingPadding.clear();
                        counter = indexOfNextNonZeroSpring(counter + 1, false);
                        if (counter == springs.size()) {
                            // Last Spring, add it to trailing
                            trailing.add(cSpring);
                        } else {
                            // Not that last Spring, add it to leading
                            newLeading.add(cSpring);
                        }
                    } else if (spring instanceof Group) {
                        // Forward call to child Group
                        if (newTrailing == null) {
                            newTrailing = new ArrayList<ComponentSpring>(1);
                        } else {
                            newTrailing.clear();
                        }
                        newTrailingPadding.clear();
                        ((Group)spring).insertAutopadding(axis,
                                newLeadingPadding, newTrailingPadding,
                                newLeading, newTrailing, insert);
                        newLeading.clear();
                        newLeadingPadding.clear();
                        counter = indexOfNextNonZeroSpring(
                                    counter + 1, (newTrailing.size() == 0));
                        if (counter == springs.size()) {
                            trailing.addAll(newTrailing);
                            trailingPadding.addAll(newTrailingPadding);
                        } else {
                            newLeading.addAll(newTrailing);
                            newLeadingPadding.addAll(newTrailingPadding);
                        }
                    } else {
                        // Gap
                        newLeadingPadding.clear();
                        newLeading.clear();
                        counter++;
                    }
                }
            }
        }

        int getBaseline() {
            if (baselineSpring != null) {
                int baseline = baselineSpring.getBaseline();
                if (baseline >= 0) {
                    int size = 0;
                    for (Spring spring : springs) {
                        if (spring == baselineSpring) {
                            return size + baseline;
                        } else {
                            size += spring.getPreferredSize(VERTICAL);
                        }
                    }
                }
            }
            return -1;
        }

        BaselineResizeBehavior getBaselineResizeBehavior() {
            if (isResizable(VERTICAL)) {
                if (!baselineSpring.isResizable(VERTICAL)) {
                    // Spring to use for baseline isn't resizable. In this case
                    // baseline resize behavior can be determined based on how
                    // preceding springs resize.
                    boolean leadingResizable = false;
                    for (Spring spring : springs) {
                        if (spring == baselineSpring) {
                            break;
                        } else if (spring.isResizable(VERTICAL)) {
                            leadingResizable = true;
                            break;
                        }
                    }
                    boolean trailingResizable = false;
                    for (int i = springs.size() - 1; i >= 0; i--) {
                        Spring spring = springs.get(i);
                        if (spring == baselineSpring) {
                            break;
                        }
                        if (spring.isResizable(VERTICAL)) {
                            trailingResizable = true;
                            break;
                        }
                    }
                    if (leadingResizable && !trailingResizable) {
                        return BaselineResizeBehavior.CONSTANT_DESCENT;
                    } else if (!leadingResizable && trailingResizable) {
                        return BaselineResizeBehavior.CONSTANT_ASCENT;
                    }
                    // If we get here, both leading and trailing springs are
                    // resizable. Fall through to OTHER.
                } else {
                    BaselineResizeBehavior brb = baselineSpring.getBaselineResizeBehavior();
                    if (brb == BaselineResizeBehavior.CONSTANT_ASCENT) {
                        for (Spring spring : springs) {
                            if (spring == baselineSpring) {
                                return BaselineResizeBehavior.CONSTANT_ASCENT;
                            }
                            if (spring.isResizable(VERTICAL)) {
                                return BaselineResizeBehavior.OTHER;
                            }
                        }
                    } else if (brb == BaselineResizeBehavior.CONSTANT_DESCENT) {
                        for (int i = springs.size() - 1; i >= 0; i--) {
                            Spring spring = springs.get(i);
                            if (spring == baselineSpring) {
                                return BaselineResizeBehavior.CONSTANT_DESCENT;
                            }
                            if (spring.isResizable(VERTICAL)) {
                                return BaselineResizeBehavior.OTHER;
                            }
                        }
                    }
                }
                return BaselineResizeBehavior.OTHER;
            }
            // Not resizable, treat as constant_ascent
            return BaselineResizeBehavior.CONSTANT_ASCENT;
        }

        private void checkPreferredGapValues(int pref, int max) {
            if ((pref < 0 && pref != DEFAULT_SIZE && pref != PREFERRED_SIZE) ||
                    (max < 0 && max != DEFAULT_SIZE && max != PREFERRED_SIZE)||
                    (pref >= 0 && max >= 0 && pref > max)) {
                throw new IllegalArgumentException(
                        "Pref and max must be either DEFAULT_SIZE, " +
                        "PREFERRED_SIZE, or >= 0 and pref <= max");
            }
        }
    }


    /**
     * Used by SequentialGroup in calculating resizability of springs.
     * <p>
     *  由SequentialGroup用于计算弹簧的可重新调整。
     * 
     */
    private static final class SpringDelta implements Comparable<SpringDelta> {
        // Original index.
        public final int index;
        // Delta, one of pref - min or max - pref.
        public int delta;

        public SpringDelta(int index, int delta) {
            this.index = index;
            this.delta = delta;
        }

        public int compareTo(SpringDelta o) {
            return delta - o.delta;
        }

        public String toString() {
            return super.toString() + "[index=" + index + ", delta=" +
                    delta + "]";
        }
    }


    /**
     * A {@code Group} that aligns and sizes it's children.
     * {@code ParallelGroup} aligns it's children in
     * four possible ways: along the baseline, centered, anchored to the
     * leading edge, or anchored to the trailing edge.
     * <h3>Baseline</h3>
     * A {@code ParallelGroup} that aligns it's children along the
     * baseline must first decide where the baseline is
     * anchored. The baseline can either be anchored to the top, or
     * anchored to the bottom of the group. That is, the distance between the
     * baseline and the beginning of the group can be a constant
     * distance, or the distance between the end of the group and the
     * baseline can be a constant distance. The possible choices
     * correspond to the {@code BaselineResizeBehavior} constants
     * {@link
     * java.awt.Component.BaselineResizeBehavior#CONSTANT_ASCENT CONSTANT_ASCENT} and
     * {@link
     * java.awt.Component.BaselineResizeBehavior#CONSTANT_DESCENT CONSTANT_DESCENT}.
     * <p>
     * The baseline anchor may be explicitly specified by the
     * {@code createBaselineGroup} method, or determined based on the elements.
     * If not explicitly specified, the baseline will be anchored to
     * the bottom if all the elements with a baseline, and that are
     * aligned to the baseline, have a baseline resize behavior of
     * {@code CONSTANT_DESCENT}; otherwise the baseline is anchored to the top
     * of the group.
     * <p>
     * Elements aligned to the baseline are resizable if they have have
     * a baseline resize behavior of {@code CONSTANT_ASCENT} or
     * {@code CONSTANT_DESCENT}. Elements with a baseline resize
     * behavior of {@code OTHER} or {@code CENTER_OFFSET} are not resizable.
     * <p>
     * The baseline is calculated based on the preferred height of each
     * of the elements that have a baseline. The baseline is
     * calculated using the following algorithm:
     * {@code max(maxNonBaselineHeight, maxAscent + maxDescent)}, where the
     * {@code maxNonBaselineHeight} is the maximum height of all elements
     * that do not have a baseline, or are not aligned along the baseline.
     * {@code maxAscent} is the maximum ascent (baseline) of all elements that
     * have a baseline and are aligned along the baseline.
     * {@code maxDescent} is the maximum descent (preferred height - baseline)
     * of all elements that have a baseline and are aligned along the baseline.
     * <p>
     * A {@code ParallelGroup} that aligns it's elements along the baseline
     * is only useful along the vertical axis. If you create a
     * baseline group and use it along the horizontal axis an
     * {@code IllegalStateException} is thrown when you ask
     * {@code GroupLayout} for the minimum, preferred or maximum size or
     * attempt to layout the components.
     * <p>
     * Elements that are not aligned to the baseline and smaller than the size
     * of the {@code ParallelGroup} are positioned in one of three
     * ways: centered, anchored to the leading edge, or anchored to the
     * trailing edge.
     *
     * <h3>Non-baseline {@code ParallelGroup}</h3>
     * {@code ParallelGroup}s created with an alignment other than
     * {@code BASELINE} align elements that are smaller than the size
     * of the group in one of three ways: centered, anchored to the
     * leading edge, or anchored to the trailing edge.
     * <p>
     * The leading edge is based on the axis and {@code
     * ComponentOrientation}.  For the vertical axis the top edge is
     * always the leading edge, and the bottom edge is always the
     * trailing edge. When the {@code ComponentOrientation} is {@code
     * LEFT_TO_RIGHT}, the leading edge is the left edge and the
     * trailing edge the right edge. A {@code ComponentOrientation} of
     * {@code RIGHT_TO_LEFT} flips the left and right edges. Child
     * elements are aligned based on the specified alignment the
     * element was added with. If you do not specify an alignment, the
     * alignment specified for the {@code ParallelGroup} is used.
     * <p>
     * To align elements along the baseline you {@code createBaselineGroup},
     * or {@code createParallelGroup} with an alignment of {@code BASELINE}.
     * If the group was not created with a baseline alignment, and you attempt
     * to add an element specifying a baseline alignment, an
     * {@code IllegalArgumentException} is thrown.
     *
     * <p>
     * {@code Group},用于对齐和缩放它的子项。 {@code ParallelGroup}以四种可能的方式对齐它的孩子：沿着基线,居中,锚定到前缘或锚定到后缘。
     *  <h3>基线</h3>沿着基线对齐子节点的{@code ParallelGroup}必须首先决定基线的锚定位置。基线可以锚定到顶部,或锚定到组的底部。
     * 也就是说,基线与组的开始之间的距离可以是恒定距离,或者组的末端和基线之间的距离可以是恒定距离。
     * 可能的选项对应于{@code BaselineResizeBehavior}常数{@link java.awt.Component.BaselineResizeBehavior#CONSTANT_ASCENT CONSTANT_ASCENT}
     * 和{@link java.awt.Component.BaselineResizeBehavior#CONSTANT_DESCENT CONSTANT_DESCENT}。
     * 也就是说,基线与组的开始之间的距离可以是恒定距离,或者组的末端和基线之间的距离可以是恒定距离。
     * <p>
     *  基线锚可以由{@code createBaselineGroup}方法显式指定,或者基于元素确定。
     * 如果没有明确指定,如果所有具有基线并且与基线对齐的元素都具有{@code CONSTANT_DESCENT}的基线调整大小行为,则基线将锚定到底部;否则基线会锚定到组的顶部。
     * <p>
     * 与基线对齐的元素如果具有{@code CONSTANT_ASCENT}或{@code CONSTANT_DESCENT}的基线调整大小行为,则可调整大小。
     * 具有{@code OTHER}或{@code CENTER_OFFSET}的基线调整大小行为的元素无法调整大小。
     * <p>
     *  基线是基于具有基线的每个元素的优选高度计算的。
     * 使用以下算法计算基线：{@code max(maxNonBaselineHeight,maxAscent + maxDescent)},其中{@code maxNonBaselineHeight}是没有
     * 基线或未沿基线对齐的所有元素的最大高度。
     *  基线是基于具有基线的每个元素的优选高度计算的。 {@code maxAscent}是具有基线并沿基线对齐的所有元素的最大上升(基线)。
     *  {@code maxDescent}是所有具有基线并沿基线对齐的元素的最大下降(首选高度 - 基线)。
     * <p>
     *  沿着基线对齐其元素的{@code ParallelGroup}仅在垂直轴上有用。
     * 如果创建基准组并沿水平轴使用它,则当您询问{@code GroupLayout}的最小,首选或最大大小或尝试布局组件时,将抛出{@code IllegalStateException}。
     * <p>
     *  未与基线对齐且小于{@code ParallelGroup}的大小的元素以三种方式定位：居中,锚定到前边缘或锚定到后边缘。
     * 
     * 使用非{@code BASELINE}的对齐方式创建的{@code ParallelGroup} {@code ParallelGroup}非基线{@code ParallelGroup}对齐元素小于组
     * 大小的三种方式之一：居中,锚定到前缘,或锚定到后缘。
     * <p>
     *  前沿基于轴和{@code ComponentOrientation}。对于垂直轴,上边缘始终是前边缘,并且下边缘始终是后边缘。
     * 当{@code ComponentOrientation}为{@code LEFT_TO_RIGHT}时,前边缘是左边缘,后边缘是右边缘。
     *  {@code RIGHT_TO_LEFT}的{@code ComponentOrientation}会翻转左右边缘。子元素根据元素添加的指定对齐方式进行对齐。
     * 如果不指定对齐,则使用为{@code ParallelGroup}指定的对齐。
     * <p>
     *  要沿着基线({@code createBaselineGroup}或{@code createParallelGroup})对齐{@code BASELINE}的对齐方式对齐元素。
     * 如果未使用基准对齐创建组,并且尝试添加指定基准对齐的元素,则会抛出{@code IllegalArgumentException}。
     * 
     * 
     * @see #createParallelGroup()
     * @see #createBaselineGroup(boolean,boolean)
     * @since 1.6
     */
    public class ParallelGroup extends Group {
        // How children are layed out.
        private final Alignment childAlignment;
        // Whether or not we're resizable.
        private final boolean resizable;

        ParallelGroup(Alignment childAlignment, boolean resizable) {
            this.childAlignment = childAlignment;
            this.resizable = resizable;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public ParallelGroup addGroup(Group group) {
            return (ParallelGroup)super.addGroup(group);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public ParallelGroup addComponent(Component component) {
            return (ParallelGroup)super.addComponent(component);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public ParallelGroup addComponent(Component component, int min, int pref,
                int max) {
            return (ParallelGroup)super.addComponent(component, min, pref, max);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public ParallelGroup addGap(int pref) {
            return (ParallelGroup)super.addGap(pref);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         */
        public ParallelGroup addGap(int min, int pref, int max) {
            return (ParallelGroup)super.addGap(min, pref, max);
        }

        /**
         * Adds a {@code Group} to this {@code ParallelGroup} with the
         * specified alignment. If the child is smaller than the
         * {@code Group} it is aligned based on the specified
         * alignment.
         *
         * <p>
         *  使用指定的对齐方式将{@code Group}添加到此{@code ParallelGroup}。如果子元素小于{@code Group},它将根据指定的对齐方式对齐。
         * 
         * 
         * @param alignment the alignment
         * @param group the {@code Group} to add
         * @return this {@code ParallelGroup}
         * @throws IllegalArgumentException if {@code alignment} is
         *         {@code null}
         */
        public ParallelGroup addGroup(Alignment alignment, Group group) {
            checkChildAlignment(alignment);
            group.setAlignment(alignment);
            return (ParallelGroup)addSpring(group);
        }

        /**
         * Adds a {@code Component} to this {@code ParallelGroup} with
         * the specified alignment.
         *
         * <p>
         * 使用指定的对齐向此{@code ParallelGroup}添加一个{@code Component}。
         * 
         * 
         * @param alignment the alignment
         * @param component the {@code Component} to add
         * @return this {@code Group}
         * @throws IllegalArgumentException if {@code alignment} is
         *         {@code null}
         */
        public ParallelGroup addComponent(Component component,
                Alignment alignment) {
            return addComponent(component, alignment, DEFAULT_SIZE, DEFAULT_SIZE,
                    DEFAULT_SIZE);
        }

        /**
         * Adds a {@code Component} to this {@code ParallelGroup} with the
         * specified alignment and size.
         *
         * <p>
         *  将{@code Component}添加到具有指定对齐和大小的此{@code ParallelGroup}。
         * 
         * 
         * @param alignment the alignment
         * @param component the {@code Component} to add
         * @param min the minimum size
         * @param pref the preferred size
         * @param max the maximum size
         * @throws IllegalArgumentException if {@code alignment} is
         *         {@code null}
         * @return this {@code Group}
         */
        public ParallelGroup addComponent(Component component,
                Alignment alignment, int min, int pref, int max) {
            checkChildAlignment(alignment);
            ComponentSpring spring = new ComponentSpring(component,
                    min, pref, max);
            spring.setAlignment(alignment);
            return (ParallelGroup)addSpring(spring);
        }

        boolean isResizable() {
            return resizable;
        }

        int operator(int a, int b) {
            return Math.max(a, b);
        }

        int calculateMinimumSize(int axis) {
            if (!isResizable()) {
                return getPreferredSize(axis);
            }
            return super.calculateMinimumSize(axis);
        }

        int calculateMaximumSize(int axis) {
            if (!isResizable()) {
                return getPreferredSize(axis);
            }
            return super.calculateMaximumSize(axis);
        }

        void setValidSize(int axis, int origin, int size) {
            for (Spring spring : springs) {
                setChildSize(spring, axis, origin, size);
            }
        }

        void setChildSize(Spring spring, int axis, int origin, int size) {
            Alignment alignment = spring.getAlignment();
            int springSize = Math.min(
                    Math.max(spring.getMinimumSize(axis), size),
                    spring.getMaximumSize(axis));
            if (alignment == null) {
                alignment = childAlignment;
            }
            switch (alignment) {
                case TRAILING:
                    spring.setSize(axis, origin + size - springSize,
                            springSize);
                    break;
                case CENTER:
                    spring.setSize(axis, origin +
                            (size - springSize) / 2,springSize);
                    break;
                default: // LEADING, or BASELINE
                    spring.setSize(axis, origin, springSize);
                    break;
            }
        }

        @Override
        void insertAutopadding(int axis,
                List<AutoPreferredGapSpring> leadingPadding,
                List<AutoPreferredGapSpring> trailingPadding,
                List<ComponentSpring> leading, List<ComponentSpring> trailing,
                boolean insert) {
            for (Spring spring : springs) {
                if (spring instanceof ComponentSpring) {
                    if (((ComponentSpring)spring).isVisible()) {
                        for (AutoPreferredGapSpring gapSpring :
                                 leadingPadding) {
                            gapSpring.addTarget((ComponentSpring)spring, axis);
                        }
                        trailing.add((ComponentSpring)spring);
                    }
                } else if (spring instanceof Group) {
                    ((Group)spring).insertAutopadding(axis, leadingPadding,
                            trailingPadding, leading, trailing, insert);
                } else if (spring instanceof AutoPreferredGapSpring) {
                    ((AutoPreferredGapSpring)spring).setSources(leading);
                    trailingPadding.add((AutoPreferredGapSpring)spring);
                }
            }
        }

        private void checkChildAlignment(Alignment alignment) {
            checkChildAlignment(alignment, (this instanceof BaselineGroup));
        }

        private void checkChildAlignment(Alignment alignment,
                boolean allowsBaseline) {
            if (alignment == null) {
                throw new IllegalArgumentException("Alignment must be non-null");
            }
            if (!allowsBaseline && alignment == Alignment.BASELINE) {
                throw new IllegalArgumentException("Alignment must be one of:" +
                        "LEADING, TRAILING or CENTER");
            }
        }
    }


    /**
     * An extension of {@code ParallelGroup} that aligns its
     * constituent {@code Spring}s along the baseline.
     * <p>
     *  {@code ParallelGroup}的扩展,它沿着基线对齐其组成部分{@code Spring}。
     * 
     */
    private class BaselineGroup extends ParallelGroup {
        // Whether or not all child springs have a baseline
        private boolean allSpringsHaveBaseline;

        // max(spring.getBaseline()) of all springs aligned along the baseline
        // that have a baseline
        private int prefAscent;

        // max(spring.getPreferredSize().height - spring.getBaseline()) of all
        // springs aligned along the baseline that have a baseline
        private int prefDescent;

        // Whether baselineAnchoredToTop was explicitly set
        private boolean baselineAnchorSet;

        // Whether the baseline is anchored to the top or the bottom.
        // If anchored to the top the baseline is always at prefAscent,
        // otherwise the baseline is at (height - prefDescent)
        private boolean baselineAnchoredToTop;

        // Whether or not the baseline has been calculated.
        private boolean calcedBaseline;

        BaselineGroup(boolean resizable) {
            super(Alignment.LEADING, resizable);
            prefAscent = prefDescent = -1;
            calcedBaseline = false;
        }

        BaselineGroup(boolean resizable, boolean baselineAnchoredToTop) {
            this(resizable);
            this.baselineAnchoredToTop = baselineAnchoredToTop;
            baselineAnchorSet = true;
        }

        void unset() {
            super.unset();
            prefAscent = prefDescent = -1;
            calcedBaseline = false;
        }

        void setValidSize(int axis, int origin, int size) {
            checkAxis(axis);
            if (prefAscent == -1) {
                super.setValidSize(axis, origin, size);
            } else {
                // do baseline layout
                baselineLayout(origin, size);
            }
        }

        int calculateSize(int axis, int type) {
            checkAxis(axis);
            if (!calcedBaseline) {
                calculateBaselineAndResizeBehavior();
            }
            if (type == MIN_SIZE) {
                return calculateMinSize();
            }
            if (type == MAX_SIZE) {
                return calculateMaxSize();
            }
            if (allSpringsHaveBaseline) {
                return prefAscent + prefDescent;
            }
            return Math.max(prefAscent + prefDescent,
                    super.calculateSize(axis, type));
        }

        private void calculateBaselineAndResizeBehavior() {
            // calculate baseline
            prefAscent = 0;
            prefDescent = 0;
            int baselineSpringCount = 0;
            BaselineResizeBehavior resizeBehavior = null;
            for (Spring spring : springs) {
                if (spring.getAlignment() == null ||
                        spring.getAlignment() == Alignment.BASELINE) {
                    int baseline = spring.getBaseline();
                    if (baseline >= 0) {
                        if (spring.isResizable(VERTICAL)) {
                            BaselineResizeBehavior brb = spring.
                                    getBaselineResizeBehavior();
                            if (resizeBehavior == null) {
                                resizeBehavior = brb;
                            } else if (brb != resizeBehavior) {
                                resizeBehavior = BaselineResizeBehavior.
                                        CONSTANT_ASCENT;
                            }
                        }
                        prefAscent = Math.max(prefAscent, baseline);
                        prefDescent = Math.max(prefDescent, spring.
                                getPreferredSize(VERTICAL) - baseline);
                        baselineSpringCount++;
                    }
                }
            }
            if (!baselineAnchorSet) {
                if (resizeBehavior == BaselineResizeBehavior.CONSTANT_DESCENT){
                    this.baselineAnchoredToTop = false;
                } else {
                    this.baselineAnchoredToTop = true;
                }
            }
            allSpringsHaveBaseline = (baselineSpringCount == springs.size());
            calcedBaseline = true;
        }

        private int calculateMaxSize() {
            int maxAscent = prefAscent;
            int maxDescent = prefDescent;
            int nonBaselineMax = 0;
            for (Spring spring : springs) {
                int baseline;
                int springMax = spring.getMaximumSize(VERTICAL);
                if ((spring.getAlignment() == null ||
                        spring.getAlignment() == Alignment.BASELINE) &&
                        (baseline = spring.getBaseline()) >= 0) {
                    int springPref = spring.getPreferredSize(VERTICAL);
                    if (springPref != springMax) {
                        switch (spring.getBaselineResizeBehavior()) {
                            case CONSTANT_ASCENT:
                                if (baselineAnchoredToTop) {
                                    maxDescent = Math.max(maxDescent,
                                            springMax - baseline);
                                }
                                break;
                            case CONSTANT_DESCENT:
                                if (!baselineAnchoredToTop) {
                                    maxAscent = Math.max(maxAscent,
                                            springMax - springPref + baseline);
                                }
                                break;
                            default: // CENTER_OFFSET and OTHER, not resizable
                                break;
                        }
                    }
                } else {
                    // Not aligned along the baseline, or no baseline.
                    nonBaselineMax = Math.max(nonBaselineMax, springMax);
                }
            }
            return Math.max(nonBaselineMax, maxAscent + maxDescent);
        }

        private int calculateMinSize() {
            int minAscent = 0;
            int minDescent = 0;
            int nonBaselineMin = 0;
            if (baselineAnchoredToTop) {
                minAscent = prefAscent;
            } else {
                minDescent = prefDescent;
            }
            for (Spring spring : springs) {
                int springMin = spring.getMinimumSize(VERTICAL);
                int baseline;
                if ((spring.getAlignment() == null ||
                        spring.getAlignment() == Alignment.BASELINE) &&
                        (baseline = spring.getBaseline()) >= 0) {
                    int springPref = spring.getPreferredSize(VERTICAL);
                    BaselineResizeBehavior brb = spring.
                            getBaselineResizeBehavior();
                    switch (brb) {
                        case CONSTANT_ASCENT:
                            if (baselineAnchoredToTop) {
                                minDescent = Math.max(springMin - baseline,
                                        minDescent);
                            } else {
                                minAscent = Math.max(baseline, minAscent);
                            }
                            break;
                        case CONSTANT_DESCENT:
                            if (!baselineAnchoredToTop) {
                                minAscent = Math.max(
                                        baseline - (springPref - springMin),
                                        minAscent);
                            } else {
                                minDescent = Math.max(springPref - baseline,
                                        minDescent);
                            }
                            break;
                        default:
                            // CENTER_OFFSET and OTHER are !resizable, use
                            // the preferred size.
                            minAscent = Math.max(baseline, minAscent);
                            minDescent = Math.max(springPref - baseline,
                                    minDescent);
                            break;
                    }
                } else {
                    // Not aligned along the baseline, or no baseline.
                    nonBaselineMin = Math.max(nonBaselineMin, springMin);
                }
            }
            return Math.max(nonBaselineMin, minAscent + minDescent);
        }

        /**
         * Lays out springs that have a baseline along the baseline.  All
         * others are centered.
         * <p>
         *  放出沿基线具有基线的弹簧。所有其他人都居中。
         * 
         */
        private void baselineLayout(int origin, int size) {
            int ascent;
            int descent;
            if (baselineAnchoredToTop) {
                ascent = prefAscent;
                descent = size - ascent;
            } else {
                ascent = size - prefDescent;
                descent = prefDescent;
            }
            for (Spring spring : springs) {
                Alignment alignment = spring.getAlignment();
                if (alignment == null || alignment == Alignment.BASELINE) {
                    int baseline = spring.getBaseline();
                    if (baseline >= 0) {
                        int springMax = spring.getMaximumSize(VERTICAL);
                        int springPref = spring.getPreferredSize(VERTICAL);
                        int height = springPref;
                        int y;
                        switch(spring.getBaselineResizeBehavior()) {
                            case CONSTANT_ASCENT:
                                y = origin + ascent - baseline;
                                height = Math.min(descent, springMax -
                                        baseline) + baseline;
                                break;
                            case CONSTANT_DESCENT:
                                height = Math.min(ascent, springMax -
                                        springPref + baseline) +
                                        (springPref - baseline);
                                y = origin + ascent +
                                        (springPref - baseline) - height;
                                break;
                            default: // CENTER_OFFSET & OTHER, not resizable
                                y = origin + ascent - baseline;
                                break;
                        }
                        spring.setSize(VERTICAL, y, height);
                    } else {
                        setChildSize(spring, VERTICAL, origin, size);
                    }
                } else {
                    setChildSize(spring, VERTICAL, origin, size);
                }
            }
        }

        int getBaseline() {
            if (springs.size() > 1) {
                // Force the baseline to be calculated
                getPreferredSize(VERTICAL);
                return prefAscent;
            } else if (springs.size() == 1) {
                return springs.get(0).getBaseline();
            }
            return -1;
        }

        BaselineResizeBehavior getBaselineResizeBehavior() {
            if (springs.size() == 1) {
                return springs.get(0).getBaselineResizeBehavior();
            }
            if (baselineAnchoredToTop) {
                return BaselineResizeBehavior.CONSTANT_ASCENT;
            }
            return BaselineResizeBehavior.CONSTANT_DESCENT;
        }

        // If the axis is VERTICAL, throws an IllegalStateException
        private void checkAxis(int axis) {
            if (axis == HORIZONTAL) {
                throw new IllegalStateException(
                        "Baseline must be used along vertical axis");
            }
        }
    }


    private final class ComponentSpring extends Spring {
        private Component component;
        private int origin;

        // min/pref/max are either a value >= 0 or one of
        // DEFAULT_SIZE or PREFERRED_SIZE
        private final int min;
        private final int pref;
        private final int max;

        // Baseline for the component, computed as necessary.
        private int baseline = -1;

        // Whether or not the size has been requested yet.
        private boolean installed;

        private ComponentSpring(Component component, int min, int pref,
                int max) {
            this.component = component;
            if (component == null) {
                throw new IllegalArgumentException(
                        "Component must be non-null");
            }

            checkSize(min, pref, max, true);

            this.min = min;
            this.max = max;
            this.pref = pref;

            // getComponentInfo makes sure component is a child of the
            // Container GroupLayout is the LayoutManager for.
            getComponentInfo(component);
        }

        int calculateMinimumSize(int axis) {
            if (isLinked(axis)) {
                return getLinkSize(axis, MIN_SIZE);
            }
            return calculateNonlinkedMinimumSize(axis);
        }

        int calculatePreferredSize(int axis) {
            if (isLinked(axis)) {
                return getLinkSize(axis, PREF_SIZE);
            }
            int min = getMinimumSize(axis);
            int pref = calculateNonlinkedPreferredSize(axis);
            int max = getMaximumSize(axis);
            return Math.min(max, Math.max(min, pref));
        }

        int calculateMaximumSize(int axis) {
            if (isLinked(axis)) {
                return getLinkSize(axis, MAX_SIZE);
            }
            return Math.max(getMinimumSize(axis),
                    calculateNonlinkedMaximumSize(axis));
        }

        boolean isVisible() {
            return getComponentInfo(getComponent()).isVisible();
        }

        int calculateNonlinkedMinimumSize(int axis) {
            if (!isVisible()) {
                return 0;
            }
            if (min >= 0) {
                return min;
            }
            if (min == PREFERRED_SIZE) {
                return calculateNonlinkedPreferredSize(axis);
            }
            assert (min == DEFAULT_SIZE);
            return getSizeAlongAxis(axis, component.getMinimumSize());
        }

        int calculateNonlinkedPreferredSize(int axis) {
            if (!isVisible()) {
                return 0;
            }
            if (pref >= 0) {
                return pref;
            }
            assert (pref == DEFAULT_SIZE || pref == PREFERRED_SIZE);
            return getSizeAlongAxis(axis, component.getPreferredSize());
        }

        int calculateNonlinkedMaximumSize(int axis) {
            if (!isVisible()) {
                return 0;
            }
            if (max >= 0) {
                return max;
            }
            if (max == PREFERRED_SIZE) {
                return calculateNonlinkedPreferredSize(axis);
            }
            assert (max == DEFAULT_SIZE);
            return getSizeAlongAxis(axis, component.getMaximumSize());
        }

        private int getSizeAlongAxis(int axis, Dimension size) {
            return (axis == HORIZONTAL) ? size.width : size.height;
        }

        private int getLinkSize(int axis, int type) {
            if (!isVisible()) {
                return 0;
            }
            ComponentInfo ci = getComponentInfo(component);
            return ci.getLinkSize(axis, type);
        }

        void setSize(int axis, int origin, int size) {
            super.setSize(axis, origin, size);
            this.origin = origin;
            if (size == UNSET) {
                baseline = -1;
            }
        }

        int getOrigin() {
            return origin;
        }

        void setComponent(Component component) {
            this.component = component;
        }

        Component getComponent() {
            return component;
        }

        int getBaseline() {
            if (baseline == -1) {
                Spring horizontalSpring = getComponentInfo(component).
                        horizontalSpring;
                int width = horizontalSpring.getPreferredSize(HORIZONTAL);
                int height = getPreferredSize(VERTICAL);
                if (width > 0 && height > 0) {
                    baseline = component.getBaseline(width, height);
                }
            }
            return baseline;
        }

        BaselineResizeBehavior getBaselineResizeBehavior() {
            return getComponent().getBaselineResizeBehavior();
        }

        private boolean isLinked(int axis) {
            return getComponentInfo(component).isLinked(axis);
        }

        void installIfNecessary(int axis) {
            if (!installed) {
                installed = true;
                if (axis == HORIZONTAL) {
                    getComponentInfo(component).horizontalSpring = this;
                } else {
                    getComponentInfo(component).verticalSpring = this;
                }
            }
        }

        @Override
        boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized) {
            return !isVisible();
        }
    }


    /**
     * Spring representing the preferred distance between two components.
     * <p>
     *  弹簧表示两个组件之间的优选距离。
     * 
     */
    private class PreferredGapSpring extends Spring {
        private final JComponent source;
        private final JComponent target;
        private final ComponentPlacement type;
        private final int pref;
        private final int max;

        PreferredGapSpring(JComponent source, JComponent target,
                ComponentPlacement type, int pref, int max) {
            this.source = source;
            this.target = target;
            this.type = type;
            this.pref = pref;
            this.max = max;
        }

        int calculateMinimumSize(int axis) {
            return getPadding(axis);
        }

        int calculatePreferredSize(int axis) {
            if (pref == DEFAULT_SIZE || pref == PREFERRED_SIZE) {
                return getMinimumSize(axis);
            }
            int min = getMinimumSize(axis);
            int max = getMaximumSize(axis);
            return Math.min(max, Math.max(min, pref));
        }

        int calculateMaximumSize(int axis) {
            if (max == PREFERRED_SIZE || max == DEFAULT_SIZE) {
                return getPadding(axis);
            }
            return Math.max(getMinimumSize(axis), max);
        }

        private int getPadding(int axis) {
            int position;
            if (axis == HORIZONTAL) {
                position = SwingConstants.EAST;
            } else {
                position = SwingConstants.SOUTH;
            }
            return getLayoutStyle0().getPreferredGap(source,
                    target, type, position, host);
        }

        @Override
        boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized) {
            return false;
        }
    }


    /**
     * Spring represented a certain amount of space.
     * <p>
     *  Spring代表了一定的空间。
     * 
     */
    private class GapSpring extends Spring {
        private final int min;
        private final int pref;
        private final int max;

        GapSpring(int min, int pref, int max) {
            checkSize(min, pref, max, false);
            this.min = min;
            this.pref = pref;
            this.max = max;
        }

        int calculateMinimumSize(int axis) {
            if (min == PREFERRED_SIZE) {
                return getPreferredSize(axis);
            }
            return min;
        }

        int calculatePreferredSize(int axis) {
            return pref;
        }

        int calculateMaximumSize(int axis) {
            if (max == PREFERRED_SIZE) {
                return getPreferredSize(axis);
            }
            return max;
        }

        @Override
        boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized) {
            return false;
        }
    }


    /**
     * Spring reprensenting the distance between any number of sources and
     * targets.  The targets and sources are computed during layout.  An
     * instance of this can either be dynamically created when
     * autocreatePadding is true, or explicitly created by the developer.
     * <p>
     *  春天代表任何数量的源和目标之间的距离。在布局期间计算目标和源。这个实例可以在autocreatePadding为true时动态创建,也可以由开发人员明确创建。
     * 
     */
    private class AutoPreferredGapSpring extends Spring {
        List<ComponentSpring> sources;
        ComponentSpring source;
        private List<AutoPreferredGapMatch> matches;
        int size;
        int lastSize;
        private final int pref;
        private final int max;
        // Type of gap
        private ComponentPlacement type;
        private boolean userCreated;

        private AutoPreferredGapSpring() {
            this.pref = PREFERRED_SIZE;
            this.max = PREFERRED_SIZE;
            this.type = ComponentPlacement.RELATED;
        }

        AutoPreferredGapSpring(int pref, int max) {
            this.pref = pref;
            this.max = max;
        }

        AutoPreferredGapSpring(ComponentPlacement type, int pref, int max) {
            this.type = type;
            this.pref = pref;
            this.max = max;
            this.userCreated = true;
        }

        public void setSource(ComponentSpring source) {
            this.source = source;
        }

        public void setSources(List<ComponentSpring> sources) {
            this.sources = new ArrayList<ComponentSpring>(sources);
        }

        public void setUserCreated(boolean userCreated) {
            this.userCreated = userCreated;
        }

        public boolean getUserCreated() {
            return userCreated;
        }

        void unset() {
            lastSize = getSize();
            super.unset();
            size = 0;
        }

        public void reset() {
            size = 0;
            sources = null;
            source = null;
            matches = null;
        }

        public void calculatePadding(int axis) {
            size = UNSET;
            int maxPadding = UNSET;
            if (matches != null) {
                LayoutStyle p = getLayoutStyle0();
                int position;
                if (axis == HORIZONTAL) {
                    if (isLeftToRight()) {
                        position = SwingConstants.EAST;
                    } else {
                        position = SwingConstants.WEST;
                    }
                } else {
                    position = SwingConstants.SOUTH;
                }
                for (int i = matches.size() - 1; i >= 0; i--) {
                    AutoPreferredGapMatch match = matches.get(i);
                    maxPadding = Math.max(maxPadding,
                            calculatePadding(p, position, match.source,
                            match.target));
                }
            }
            if (size == UNSET) {
                size = 0;
            }
            if (maxPadding == UNSET) {
                maxPadding = 0;
            }
            if (lastSize != UNSET) {
                size += Math.min(maxPadding, lastSize);
            }
        }

        private int calculatePadding(LayoutStyle p, int position,
                ComponentSpring source,
                ComponentSpring target) {
            int delta = target.getOrigin() - (source.getOrigin() +
                    source.getSize());
            if (delta >= 0) {
                int padding;
                if ((source.getComponent() instanceof JComponent) &&
                        (target.getComponent() instanceof JComponent)) {
                    padding = p.getPreferredGap(
                            (JComponent)source.getComponent(),
                            (JComponent)target.getComponent(), type, position,
                            host);
                } else {
                    padding = 10;
                }
                if (padding > delta) {
                    size = Math.max(size, padding - delta);
                }
                return padding;
            }
            return 0;
        }

        public void addTarget(ComponentSpring spring, int axis) {
            int oAxis = (axis == HORIZONTAL) ? VERTICAL : HORIZONTAL;
            if (source != null) {
                if (areParallelSiblings(source.getComponent(),
                        spring.getComponent(), oAxis)) {
                    addValidTarget(source, spring);
                }
            } else {
                Component component = spring.getComponent();
                for (int counter = sources.size() - 1; counter >= 0;
                         counter--){
                    ComponentSpring source = sources.get(counter);
                    if (areParallelSiblings(source.getComponent(),
                            component, oAxis)) {
                        addValidTarget(source, spring);
                    }
                }
            }
        }

        private void addValidTarget(ComponentSpring source,
                ComponentSpring target) {
            if (matches == null) {
                matches = new ArrayList<AutoPreferredGapMatch>(1);
            }
            matches.add(new AutoPreferredGapMatch(source, target));
        }

        int calculateMinimumSize(int axis) {
            return size;
        }

        int calculatePreferredSize(int axis) {
            if (pref == PREFERRED_SIZE || pref == DEFAULT_SIZE) {
                return size;
            }
            return Math.max(size, pref);
        }

        int calculateMaximumSize(int axis) {
            if (max >= 0) {
                return Math.max(getPreferredSize(axis), max);
            }
            return size;
        }

        String getMatchDescription() {
            return (matches == null) ? "" : matches.toString();
        }

        public String toString() {
            return super.toString() + getMatchDescription();
        }

        @Override
        boolean willHaveZeroSize(boolean treatAutopaddingAsZeroSized) {
            return treatAutopaddingAsZeroSized;
        }
    }


    /**
     * Represents two springs that should have autopadding inserted between
     * them.
     * <p>
     *  代表应该在它们之间插入自动填充的两个弹簧。
     * 
     */
    private final static class AutoPreferredGapMatch {
        public final ComponentSpring source;
        public final ComponentSpring target;

        AutoPreferredGapMatch(ComponentSpring source, ComponentSpring target) {
            this.source = source;
            this.target = target;
        }

        private String toString(ComponentSpring spring) {
            return spring.getComponent().getName();
        }

        public String toString() {
            return "[" + toString(source) + "-" + toString(target) + "]";
        }
    }


    /**
     * An extension of AutopaddingSpring used for container level padding.
     * <p>
     *  用于容器级填充的AutopaddingSpring的扩展。
     * 
     */
    private class ContainerAutoPreferredGapSpring extends
            AutoPreferredGapSpring {
        private List<ComponentSpring> targets;

        ContainerAutoPreferredGapSpring() {
            super();
            setUserCreated(true);
        }

        ContainerAutoPreferredGapSpring(int pref, int max) {
            super(pref, max);
            setUserCreated(true);
        }

        public void addTarget(ComponentSpring spring, int axis) {
            if (targets == null) {
                targets = new ArrayList<ComponentSpring>(1);
            }
            targets.add(spring);
        }

        public void calculatePadding(int axis) {
            LayoutStyle p = getLayoutStyle0();
            int maxPadding = 0;
            int position;
            size = 0;
            if (targets != null) {
                // Leading
                if (axis == HORIZONTAL) {
                    if (isLeftToRight()) {
                        position = SwingConstants.WEST;
                    } else {
                        position = SwingConstants.EAST;
                    }
                } else {
                    position = SwingConstants.SOUTH;
                }
                for (int i = targets.size() - 1; i >= 0; i--) {
                    ComponentSpring targetSpring = targets.get(i);
                    int padding = 10;
                    if (targetSpring.getComponent() instanceof JComponent) {
                        padding = p.getContainerGap(
                                (JComponent)targetSpring.getComponent(),
                                position, host);
                        maxPadding = Math.max(padding, maxPadding);
                        padding -= targetSpring.getOrigin();
                    } else {
                        maxPadding = Math.max(padding, maxPadding);
                    }
                    size = Math.max(size, padding);
                }
            } else {
                // Trailing
                if (axis == HORIZONTAL) {
                    if (isLeftToRight()) {
                        position = SwingConstants.EAST;
                    } else {
                        position = SwingConstants.WEST;
                    }
                } else {
                    position = SwingConstants.SOUTH;
                }
                if (sources != null) {
                    for (int i = sources.size() - 1; i >= 0; i--) {
                        ComponentSpring sourceSpring = sources.get(i);
                        maxPadding = Math.max(maxPadding,
                                updateSize(p, sourceSpring, position));
                    }
                } else if (source != null) {
                    maxPadding = updateSize(p, source, position);
                }
            }
            if (lastSize != UNSET) {
                size += Math.min(maxPadding, lastSize);
            }
        }

        private int updateSize(LayoutStyle p, ComponentSpring sourceSpring,
                int position) {
            int padding = 10;
            if (sourceSpring.getComponent() instanceof JComponent) {
                padding = p.getContainerGap(
                        (JComponent)sourceSpring.getComponent(), position,
                        host);
            }
            int delta = Math.max(0, getParent().getSize() -
                    sourceSpring.getSize() - sourceSpring.getOrigin());
            size = Math.max(size, padding - delta);
            return padding;
        }

        String getMatchDescription() {
            if (targets != null) {
                return "leading: " + targets.toString();
            }
            if (sources != null) {
                return "trailing: " + sources.toString();
            }
            return "--";
        }
    }


    // LinkInfo contains the set of ComponentInfosthat are linked along a
    // particular axis.
    private static class LinkInfo {
        private final int axis;
        private final List<ComponentInfo> linked;
        private int size;

        LinkInfo(int axis) {
            linked = new ArrayList<ComponentInfo>();
            size = UNSET;
            this.axis = axis;
        }

        public void add(ComponentInfo child) {
            LinkInfo childMaster = child.getLinkInfo(axis, false);
            if (childMaster == null) {
                linked.add(child);
                child.setLinkInfo(axis, this);
            } else if (childMaster != this) {
                linked.addAll(childMaster.linked);
                for (ComponentInfo childInfo : childMaster.linked) {
                    childInfo.setLinkInfo(axis, this);
                }
            }
            clearCachedSize();
        }

        public void remove(ComponentInfo info) {
            linked.remove(info);
            info.setLinkInfo(axis, null);
            if (linked.size() == 1) {
                linked.get(0).setLinkInfo(axis, null);
            }
            clearCachedSize();
        }

        public void clearCachedSize() {
            size = UNSET;
        }

        public int getSize(int axis) {
            if (size == UNSET) {
                size = calculateLinkedSize(axis);
            }
            return size;
        }

        private int calculateLinkedSize(int axis) {
            int size = 0;
            for (ComponentInfo info : linked) {
                ComponentSpring spring;
                if (axis == HORIZONTAL) {
                    spring = info.horizontalSpring;
                } else {
                    assert (axis == VERTICAL);
                    spring = info.verticalSpring;
                }
                size = Math.max(size,
                        spring.calculateNonlinkedPreferredSize(axis));
            }
            return size;
        }
    }

    /**
     * Tracks the horizontal/vertical Springs for a Component.
     * This class is also used to handle Springs that have their sizes
     * linked.
     * <p>
     *  跟踪组件的水平/垂直弹簧。这个类也用于处理其大小被链接的Springs。
     * 
     */
    private class ComponentInfo {
        // Component being layed out
        private Component component;

        ComponentSpring horizontalSpring;
        ComponentSpring verticalSpring;

        // If the component's size is linked to other components, the
        // horizontalMaster and/or verticalMaster reference the group of
        // linked components.
        private LinkInfo horizontalMaster;
        private LinkInfo verticalMaster;

        private boolean visible;
        private Boolean honorsVisibility;

        ComponentInfo(Component component) {
            this.component = component;
            updateVisibility();
        }

        public void dispose() {
            // Remove horizontal/vertical springs
            removeSpring(horizontalSpring);
            horizontalSpring = null;
            removeSpring(verticalSpring);
            verticalSpring = null;
            // Clean up links
            if (horizontalMaster != null) {
                horizontalMaster.remove(this);
            }
            if (verticalMaster != null) {
                verticalMaster.remove(this);
            }
        }

        void setHonorsVisibility(Boolean honorsVisibility) {
            this.honorsVisibility = honorsVisibility;
        }

        private void removeSpring(Spring spring) {
            if (spring != null) {
                ((Group)spring.getParent()).springs.remove(spring);
            }
        }

        public boolean isVisible() {
            return visible;
        }

        /**
         * Updates the cached visibility.
         *
         * <p>
         *  更新缓存的可见性。
         * 
         * 
         * @return true if the visibility changed
         */
        boolean updateVisibility() {
            boolean honorsVisibility;
            if (this.honorsVisibility == null) {
                honorsVisibility = GroupLayout.this.getHonorsVisibility();
            } else {
                honorsVisibility = this.honorsVisibility;
            }
            boolean newVisible = (honorsVisibility) ?
                component.isVisible() : true;
            if (visible != newVisible) {
                visible = newVisible;
                return true;
            }
            return false;
        }

        public void setBounds(Insets insets, int parentWidth, boolean ltr) {
            int x = horizontalSpring.getOrigin();
            int w = horizontalSpring.getSize();
            int y = verticalSpring.getOrigin();
            int h = verticalSpring.getSize();

            if (!ltr) {
                x = parentWidth - x - w;
            }
            component.setBounds(x + insets.left, y + insets.top, w, h);
        }

        public void setComponent(Component component) {
            this.component = component;
            if (horizontalSpring != null) {
                horizontalSpring.setComponent(component);
            }
            if (verticalSpring != null) {
                verticalSpring.setComponent(component);
            }
        }

        public Component getComponent() {
            return component;
        }

        /**
         * Returns true if this component has its size linked to
         * other components.
         * <p>
         *  如果此组件的大小链接到其他组件,则返回true。
         */
        public boolean isLinked(int axis) {
            if (axis == HORIZONTAL) {
                return horizontalMaster != null;
            }
            assert (axis == VERTICAL);
            return (verticalMaster != null);
        }

        private void setLinkInfo(int axis, LinkInfo linkInfo) {
            if (axis == HORIZONTAL) {
                horizontalMaster = linkInfo;
            } else {
                assert (axis == VERTICAL);
                verticalMaster = linkInfo;
            }
        }

        public LinkInfo getLinkInfo(int axis) {
            return getLinkInfo(axis, true);
        }

        private LinkInfo getLinkInfo(int axis, boolean create) {
            if (axis == HORIZONTAL) {
                if (horizontalMaster == null && create) {
                    // horizontalMaster field is directly set by adding
                    // us to the LinkInfo.
                    new LinkInfo(HORIZONTAL).add(this);
                }
                return horizontalMaster;
            } else {
                assert (axis == VERTICAL);
                if (verticalMaster == null && create) {
                    // verticalMaster field is directly set by adding
                    // us to the LinkInfo.
                    new LinkInfo(VERTICAL).add(this);
                }
                return verticalMaster;
            }
        }

        public void clearCachedSize() {
            if (horizontalMaster != null) {
                horizontalMaster.clearCachedSize();
            }
            if (verticalMaster != null) {
                verticalMaster.clearCachedSize();
            }
        }

        int getLinkSize(int axis, int type) {
            if (axis == HORIZONTAL) {
                return horizontalMaster.getSize(axis);
            } else {
                assert (axis == VERTICAL);
                return verticalMaster.getSize(axis);
            }
        }

    }
}
