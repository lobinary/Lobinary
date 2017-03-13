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
import java.util.Arrays;

/**
 * The <code>GridBagLayout</code> class is a flexible layout
 * manager that aligns components vertically, horizontally or along their
 * baseline without requiring that the components be of the same size.
 * Each <code>GridBagLayout</code> object maintains a dynamic,
 * rectangular grid of cells, with each component occupying
 * one or more cells, called its <em>display area</em>.
 * <p>
 * Each component managed by a <code>GridBagLayout</code> is associated with
 * an instance of {@link GridBagConstraints}.  The constraints object
 * specifies where a component's display area should be located on the grid
 * and how the component should be positioned within its display area.  In
 * addition to its constraints object, the <code>GridBagLayout</code> also
 * considers each component's minimum and preferred sizes in order to
 * determine a component's size.
 * <p>
 * The overall orientation of the grid depends on the container's
 * {@link ComponentOrientation} property.  For horizontal left-to-right
 * orientations, grid coordinate (0,0) is in the upper left corner of the
 * container with x increasing to the right and y increasing downward.  For
 * horizontal right-to-left orientations, grid coordinate (0,0) is in the upper
 * right corner of the container with x increasing to the left and y
 * increasing downward.
 * <p>
 * To use a grid bag layout effectively, you must customize one or more
 * of the <code>GridBagConstraints</code> objects that are associated
 * with its components. You customize a <code>GridBagConstraints</code>
 * object by setting one or more of its instance variables:
 *
 * <dl>
 * <dt>{@link GridBagConstraints#gridx},
 * {@link GridBagConstraints#gridy}
 * <dd>Specifies the cell containing the leading corner of the component's
 * display area, where the cell at the origin of the grid has address
 * <code>gridx&nbsp;=&nbsp;0</code>,
 * <code>gridy&nbsp;=&nbsp;0</code>.  For horizontal left-to-right layout,
 * a component's leading corner is its upper left.  For horizontal
 * right-to-left layout, a component's leading corner is its upper right.
 * Use <code>GridBagConstraints.RELATIVE</code> (the default value)
 * to specify that the component be placed immediately following
 * (along the x axis for <code>gridx</code> or the y axis for
 * <code>gridy</code>) the component that was added to the container
 * just before this component was added.
 * <dt>{@link GridBagConstraints#gridwidth},
 * {@link GridBagConstraints#gridheight}
 * <dd>Specifies the number of cells in a row (for <code>gridwidth</code>)
 * or column (for <code>gridheight</code>)
 * in the component's display area.
 * The default value is 1.
 * Use <code>GridBagConstraints.REMAINDER</code> to specify
 * that the component's display area will be from <code>gridx</code>
 * to the last cell in the row (for <code>gridwidth</code>)
 * or from <code>gridy</code> to the last cell in the column
 * (for <code>gridheight</code>).
 *
 * Use <code>GridBagConstraints.RELATIVE</code> to specify
 * that the component's display area will be from <code>gridx</code>
 * to the next to the last cell in its row (for <code>gridwidth</code>
 * or from <code>gridy</code> to the next to the last cell in its
 * column (for <code>gridheight</code>).
 *
 * <dt>{@link GridBagConstraints#fill}
 * <dd>Used when the component's display area
 * is larger than the component's requested size
 * to determine whether (and how) to resize the component.
 * Possible values are
 * <code>GridBagConstraints.NONE</code> (the default),
 * <code>GridBagConstraints.HORIZONTAL</code>
 * (make the component wide enough to fill its display area
 * horizontally, but don't change its height),
 * <code>GridBagConstraints.VERTICAL</code>
 * (make the component tall enough to fill its display area
 * vertically, but don't change its width), and
 * <code>GridBagConstraints.BOTH</code>
 * (make the component fill its display area entirely).
 * <dt>{@link GridBagConstraints#ipadx},
 * {@link GridBagConstraints#ipady}
 * <dd>Specifies the component's internal padding within the layout,
 * how much to add to the minimum size of the component.
 * The width of the component will be at least its minimum width
 * plus <code>ipadx</code> pixels. Similarly, the height of
 * the component will be at least the minimum height plus
 * <code>ipady</code> pixels.
 * <dt>{@link GridBagConstraints#insets}
 * <dd>Specifies the component's external padding, the minimum
 * amount of space between the component and the edges of its display area.
 * <dt>{@link GridBagConstraints#anchor}
 * <dd>Specifies where the component should be positioned in its display area.
 * There are three kinds of possible values: absolute, orientation-relative,
 * and baseline-relative
 * Orientation relative values are interpreted relative to the container's
 * <code>ComponentOrientation</code> property while absolute values
 * are not.  Baseline relative values are calculated relative to the
 * baseline.  Valid values are:
 *
 * <center><table BORDER=0 WIDTH=800
 *        SUMMARY="absolute, relative and baseline values as described above">
 * <tr>
 * <th><P style="text-align:left">Absolute Values</th>
 * <th><P style="text-align:left">Orientation Relative Values</th>
 * <th><P style="text-align:left">Baseline Relative Values</th>
 * </tr>
 * <tr>
 * <td>
 * <ul style="list-style-type:none">
 * <li><code>GridBagConstraints.NORTH</code></li>
 * <li><code>GridBagConstraints.SOUTH</code></li>
 * <li><code>GridBagConstraints.WEST</code></li>
 * <li><code>GridBagConstraints.EAST</code></li>
 * <li><code>GridBagConstraints.NORTHWEST</code></li>
 * <li><code>GridBagConstraints.NORTHEAST</code></li>
 * <li><code>GridBagConstraints.SOUTHWEST</code></li>
 * <li><code>GridBagConstraints.SOUTHEAST</code></li>
 * <li><code>GridBagConstraints.CENTER</code> (the default)</li>
 * </ul>
 * </td>
 * <td>
 * <ul style="list-style-type:none">
 * <li><code>GridBagConstraints.PAGE_START</code></li>
 * <li><code>GridBagConstraints.PAGE_END</code></li>
 * <li><code>GridBagConstraints.LINE_START</code></li>
 * <li><code>GridBagConstraints.LINE_END</code></li>
 * <li><code>GridBagConstraints.FIRST_LINE_START</code></li>
 * <li><code>GridBagConstraints.FIRST_LINE_END</code></li>
 * <li><code>GridBagConstraints.LAST_LINE_START</code></li>
 * <li><code>GridBagConstraints.LAST_LINE_END</code></li>
 * </ul>
 * </td>
 * <td>
 * <ul style="list-style-type:none">
 * <li><code>GridBagConstraints.BASELINE</code></li>
 * <li><code>GridBagConstraints.BASELINE_LEADING</code></li>
 * <li><code>GridBagConstraints.BASELINE_TRAILING</code></li>
 * <li><code>GridBagConstraints.ABOVE_BASELINE</code></li>
 * <li><code>GridBagConstraints.ABOVE_BASELINE_LEADING</code></li>
 * <li><code>GridBagConstraints.ABOVE_BASELINE_TRAILING</code></li>
 * <li><code>GridBagConstraints.BELOW_BASELINE</code></li>
 * <li><code>GridBagConstraints.BELOW_BASELINE_LEADING</code></li>
 * <li><code>GridBagConstraints.BELOW_BASELINE_TRAILING</code></li>
 * </ul>
 * </td>
 * </tr>
 * </table></center>
 * <dt>{@link GridBagConstraints#weightx},
 * {@link GridBagConstraints#weighty}
 * <dd>Used to determine how to distribute space, which is
 * important for specifying resizing behavior.
 * Unless you specify a weight for at least one component
 * in a row (<code>weightx</code>) and column (<code>weighty</code>),
 * all the components clump together in the center of their container.
 * This is because when the weight is zero (the default),
 * the <code>GridBagLayout</code> object puts any extra space
 * between its grid of cells and the edges of the container.
 * </dl>
 * <p>
 * Each row may have a baseline; the baseline is determined by the
 * components in that row that have a valid baseline and are aligned
 * along the baseline (the component's anchor value is one of {@code
 * BASELINE}, {@code BASELINE_LEADING} or {@code BASELINE_TRAILING}).
 * If none of the components in the row has a valid baseline, the row
 * does not have a baseline.
 * <p>
 * If a component spans rows it is aligned either to the baseline of
 * the start row (if the baseline-resize behavior is {@code
 * CONSTANT_ASCENT}) or the end row (if the baseline-resize behavior
 * is {@code CONSTANT_DESCENT}).  The row that the component is
 * aligned to is called the <em>prevailing row</em>.
 * <p>
 * The following figure shows a baseline layout and includes a
 * component that spans rows:
 * <center><table summary="Baseline Layout">
 * <tr ALIGN=CENTER>
 * <td>
 * <img src="doc-files/GridBagLayout-baseline.png"
 *  alt="The following text describes this graphic (Figure 1)." style="float:center">
 * </td>
 * </table></center>
 * This layout consists of three components:
 * <ul><li>A panel that starts in row 0 and ends in row 1.  The panel
 *   has a baseline-resize behavior of <code>CONSTANT_DESCENT</code> and has
 *   an anchor of <code>BASELINE</code>.  As the baseline-resize behavior
 *   is <code>CONSTANT_DESCENT</code> the prevailing row for the panel is
 *   row 1.
 * <li>Two buttons, each with a baseline-resize behavior of
 *   <code>CENTER_OFFSET</code> and an anchor of <code>BASELINE</code>.
 * </ul>
 * Because the second button and the panel share the same prevailing row,
 * they are both aligned along their baseline.
 * <p>
 * Components positioned using one of the baseline-relative values resize
 * differently than when positioned using an absolute or orientation-relative
 * value.  How components change is dictated by how the baseline of the
 * prevailing row changes.  The baseline is anchored to the
 * bottom of the display area if any components with the same prevailing row
 * have a baseline-resize behavior of <code>CONSTANT_DESCENT</code>,
 * otherwise the baseline is anchored to the top of the display area.
 * The following rules dictate the resize behavior:
 * <ul>
 * <li>Resizable components positioned above the baseline can only
 * grow as tall as the baseline.  For example, if the baseline is at 100
 * and anchored at the top, a resizable component positioned above the
 * baseline can never grow more than 100 units.
 * <li>Similarly, resizable components positioned below the baseline can
 * only grow as high as the difference between the display height and the
 * baseline.
 * <li>Resizable components positioned on the baseline with a
 * baseline-resize behavior of <code>OTHER</code> are only resized if
 * the baseline at the resized size fits within the display area.  If
 * the baseline is such that it does not fit within the display area
 * the component is not resized.
 * <li>Components positioned on the baseline that do not have a
 * baseline-resize behavior of <code>OTHER</code>
 * can only grow as tall as {@code display height - baseline + baseline of component}.
 * </ul>
 * If you position a component along the baseline, but the
 * component does not have a valid baseline, it will be vertically centered
 * in its space.  Similarly if you have positioned a component relative
 * to the baseline and none of the components in the row have a valid
 * baseline the component is vertically centered.
 * <p>
 * The following figures show ten components (all buttons)
 * managed by a grid bag layout.  Figure 2 shows the layout for a horizontal,
 * left-to-right container and Figure 3 shows the layout for a horizontal,
 * right-to-left container.
 *
 * <center><table WIDTH=600 summary="layout">
 * <tr ALIGN=CENTER>
 * <td>
 * <img src="doc-files/GridBagLayout-1.gif" alt="The preceding text describes this graphic (Figure 1)." style="float:center; margin: 7px 10px;">
 * </td>
 * <td>
 * <img src="doc-files/GridBagLayout-2.gif" alt="The preceding text describes this graphic (Figure 2)." style="float:center; margin: 7px 10px;">
 * </td>
 * <tr ALIGN=CENTER>
 * <td>Figure 2: Horizontal, Left-to-Right</td>
 * <td>Figure 3: Horizontal, Right-to-Left</td>
 * </tr>
 * </table></center>
 * <p>
 * Each of the ten components has the <code>fill</code> field
 * of its associated <code>GridBagConstraints</code> object
 * set to <code>GridBagConstraints.BOTH</code>.
 * In addition, the components have the following non-default constraints:
 *
 * <ul>
 * <li>Button1, Button2, Button3: <code>weightx&nbsp;=&nbsp;1.0</code>
 * <li>Button4: <code>weightx&nbsp;=&nbsp;1.0</code>,
 * <code>gridwidth&nbsp;=&nbsp;GridBagConstraints.REMAINDER</code>
 * <li>Button5: <code>gridwidth&nbsp;=&nbsp;GridBagConstraints.REMAINDER</code>
 * <li>Button6: <code>gridwidth&nbsp;=&nbsp;GridBagConstraints.RELATIVE</code>
 * <li>Button7: <code>gridwidth&nbsp;=&nbsp;GridBagConstraints.REMAINDER</code>
 * <li>Button8: <code>gridheight&nbsp;=&nbsp;2</code>,
 * <code>weighty&nbsp;=&nbsp;1.0</code>
 * <li>Button9, Button 10:
 * <code>gridwidth&nbsp;=&nbsp;GridBagConstraints.REMAINDER</code>
 * </ul>
 * <p>
 * Here is the code that implements the example shown above:
 *
 * <hr><blockquote><pre>
 * import java.awt.*;
 * import java.util.*;
 * import java.applet.Applet;
 *
 * public class GridBagEx1 extends Applet {
 *
 *     protected void makebutton(String name,
 *                               GridBagLayout gridbag,
 *                               GridBagConstraints c) {
 *         Button button = new Button(name);
 *         gridbag.setConstraints(button, c);
 *         add(button);
 *     }
 *
 *     public void init() {
 *         GridBagLayout gridbag = new GridBagLayout();
 *         GridBagConstraints c = new GridBagConstraints();
 *
 *         setFont(new Font("SansSerif", Font.PLAIN, 14));
 *         setLayout(gridbag);
 *
 *         c.fill = GridBagConstraints.BOTH;
 *         c.weightx = 1.0;
 *         makebutton("Button1", gridbag, c);
 *         makebutton("Button2", gridbag, c);
 *         makebutton("Button3", gridbag, c);
 *
 *         c.gridwidth = GridBagConstraints.REMAINDER; //end row
 *         makebutton("Button4", gridbag, c);
 *
 *         c.weightx = 0.0;                //reset to the default
 *         makebutton("Button5", gridbag, c); //another row
 *
 *         c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
 *         makebutton("Button6", gridbag, c);
 *
 *         c.gridwidth = GridBagConstraints.REMAINDER; //end row
 *         makebutton("Button7", gridbag, c);
 *
 *         c.gridwidth = 1;                //reset to the default
 *         c.gridheight = 2;
 *         c.weighty = 1.0;
 *         makebutton("Button8", gridbag, c);
 *
 *         c.weighty = 0.0;                //reset to the default
 *         c.gridwidth = GridBagConstraints.REMAINDER; //end row
 *         c.gridheight = 1;               //reset to the default
 *         makebutton("Button9", gridbag, c);
 *         makebutton("Button10", gridbag, c);
 *
 *         setSize(300, 100);
 *     }
 *
 *     public static void main(String args[]) {
 *         Frame f = new Frame("GridBag Layout Example");
 *         GridBagEx1 ex1 = new GridBagEx1();
 *
 *         ex1.init();
 *
 *         f.add("Center", ex1);
 *         f.pack();
 *         f.setSize(f.getPreferredSize());
 *         f.show();
 *     }
 * }
 * </pre></blockquote><hr>
 * <p>
 * <p>
 *  <code> GridBagLayout </code>类是一个灵活的布局管理器,可以垂直,水平或沿着它们的基线对齐组件,而不需要组件具有相同的大小每个<code> GridBagLayout </code>
 * 对象维护一个动态的矩形网格,其中每个组件占据一个或多个单元,称为其<em>显示区域</em>。
 * <p>
 * 由<code> GridBagLayout </code>管理的每个组件与{@link GridBagConstraints}的实例相关联。
 * 约束对象指定组件的显示区域应位于网格上以及组件应如何在其显示区域中定位除了约束对象之外,<code> GridBagLayout </code>还会考虑每个组件的最小和首选大小,以确定组件的大小。
 * <p>
 * 网格的整体方向取决于容器的{@link ComponentOrientation}属性对于水平从左到右的方向,网格坐标(0,0)在容器的左上角,x向右增加,y增加向下对于水平从右到左的方向,网格坐标(0
 * ,0)在容器的右上角,x向左增加,y向下增加。
 * <p>
 *  要有效地使用网格包布局,您必须自定义与其组件相关联的一个或多个<code> GridBagConstraints </code>对象您可以通过设置一个或多个网格包布局来自定义<code> GridBa
 * gConstraints </code>实例变量：。
 * 
 * <dl>
 * <dt> {@ link GridBagConstraints#gridy},{@link GridBagConstraints#gridy} <dd>指定包含组件显示区域前导角的单元格,其中网格原点处
 * 的单元格具有地址<code> gridx& =&nbsp; 0 </code>,<code> gridy&nbsp; =&nbsp; 0 </code>对于水平从左到右的布局,组件的前角是左上角对于水平
 * 从右到左布局,前导角是它的右上角使用<code> GridBagConstraintsRELATIVE </code>(默认值)来指定组件紧随其后放置(沿x轴为<code> gridx </code>或
 * y轴为<code > gridy </code>)在添加此组件之前添加到容器的组件<dt> {@ link GridBagConstraints#gridwidth},{@link GridBagConstraints#gridheight}
 *  <dd>指定行中的单元格数量(<code> gridwidth </code>)或列(<code> gridheight <代码>)在组件的显示区域中默认值为1使用<code> GridBagCons
 * traintsREMAINDER </code>指定组件的显示区域将从<code> gridx </code>到行中的最后一个单元格code> gridwidth </code>)或从<code> gr
 * idy </code>到列中的最后一个单元格(对于<code> gridheight </code>)。
 * 
 * 使用<code> GridBagConstraintsRELATIVE </code>来指定组件的显示区域将从<code> gridx </code>到其行中最后一个单元格的旁边(对于<code> gr
 * idwidth </code> code> gridy </code>到其列中最后一个单元格的旁边(<code> gridheight </code>)。
 * 
 * <dt> {@ link GridBagConstraints#fill} <dd>当组件的显示区域大于组件的请求大小以确定是否(以及如何调整组件大小时使用。
 * 可能的值为<code> GridBagConstraintsNONE </code>默认),<code> GridBagConstraintsHORIZONTAL </code>(使组件足够宽以水平填充
 * 其显示区域,但不改变其高度),<code> GridBagConstraintsEVTICAL </code>(使组件足够高,显示区域,但不改变其宽度),和<code> GridBagConstrain
 * tsBOTH </code>(使组件完全填充其显示区域)<dt> {@ link GridBagConstraints#ipadx},{@link GridBagConstraints#ipady} <dd>
 * 指定组件在布局中的内部填充,要添加到组件的最小大小中多少组件的宽度将至少为它的最小宽度加上<code> ipadx </code> pixels类似地,组件的高度将至少为最小高度加上<code> ipa
 * dy </code> pixels <dt> {@ link GridBagConstraints#insets}指定组件的外部填充,组件与其显示区域边缘之间的最小空间量<dt> {@ link GridBagConstraints#anchor}
 *  <dd>指定组件在其显示区域中的位置有三种可能的值：绝对,方向相对和基线相对方向相对值相对于容器的<code> ComponentOrientation </code>属性进行解释,而绝对值不是基线相
 * 对值是相对于基线计算的有效值为：。
 * <dt> {@ link GridBagConstraints#fill} <dd>当组件的显示区域大于组件的请求大小以确定是否(以及如何调整组件大小时使用。
 * 
 * <center> <table BORDER = 0 WIDTH = 800
 * SUMMARY="absolute, relative and baseline values as described above">
 * <tr>
 *  <th> <p style ="text-align：left">绝对值</th> <th> <p style ="text-align：left">方向相对值</th> <th> "text-ali
 * gn：left">基线相对值</th>。
 * </tr>
 * <tr>
 * <td>
 * <ul style="list-style-type:none">
 *  <li> <code> GridBagConstraintsNORTH </code> </li> <li> <code> GridBagConstraintsSOUTH </code> </li> 
 * <li> <code> GridBagConstraintsWEST </code> </li> <li> <code > GridBagConstraintsEAST </code> </li> <li>
 *  </li> <li> </li> <li> </li> <li> </li> > </li> </li> GridBagConstraintsSOUTHEAST </code> </li> <li> 
 * <code> GridBagConstraintsCENTER </code>。
 * </ul>
 * </td>
 * <td>
 * <ul style="list-style-type:none">
 * <li> <code> GridBagConstraintsPAGE_START </code> </li> <li> <code> GridBagConstraintsPAGE_END </code>
 *  </li> <li> <code> GridBagConstraintsPAGE_START </code> > GridBagConstraintsLINK_END </code> </li> </li>
 *  <li> </li> <li> </li> <li> > </li> <li> <code> GridBagConstraintsLAST_LINE_END </code> </li>。
 * </ul>
 * </td>
 * <td>
 * <ul style="list-style-type:none">
 * <li> <code> GridBagConstraintsBASELINE </code> </code> </li> <li> </li> </li> </li> > GridBagConstrai
 * ntsBOVE_BASELINE </code> </li> </li> <li> </li> </li> <li> </li> <li> </li> <li> </> </li> <li> </li>
 *  <li> </li> <li> </li>。
 * </ul>
 * </td>
 * </tr>
 * </table> </center> <dt> {@ link GridBagConstraints#weightx},{@link GridBagConstraints#weighty} <dd>用于
 * 确定如何分配空间,这对于指定调整大小行为很重要除非您所有的组件在它们的容器的中心聚集在一起(<code> weightx </code>)和列(<code> weighty </code>)中的至少一个
 * 组件这是因为当权重为零默认),<code> GridBagLayout </code>对象在其单元格和容器边缘之间添加任何额外的空格。
 * </dl>
 * <p>
 * 每行可以具有基线;基线由该行中具有有效基线并沿基线对齐的组件确定(组件的锚点值是{@code BASELINE},{@code BASELINE_LEADING}或{@code BASELINE_TRAILING}
 * 之一)如果没有该行中的组件具有有效的基线,该行没有基线。
 * <p>
 *  如果组件跨越行,它将对齐到开始行的基线(如果基线调整行为是{@code CONSTANT_ASCENT})或结束行(如果基线调整行为是{@code CONSTANT_DESCENT})的行。
 * 组件对齐到被称为<em>当前行</em>。
 * <p>
 *  下图显示了基线布局,并包括跨行的组件：<center> <table summary ="Baseline Layout">
 * <tr ALIGN=CENTER>
 * <td>
 * <img src ="doc-files / GridBagLayout-baselinepng"
 * alt="The following text describes this graphic (Figure 1)." style="float:center">
 * </td>
 *  </table> </center>此布局由三个组件组成：<ul> <li>从第0行开始并在第1行结束的面板此面板具有<code> CONSTANT_DESCENT </code>并且具有<code>
 *  BASELINE </code>的锚点。
 * 由于基线调整行为是<code> CONSTANT_DESCENT </code>,面板的主要行是第1行<li>两个按钮,每个按钮都具有基线调整行为的<code> CENTER_OFFSET </code>
 * 和一个<code> BASELINE </code>的锚点。
 * </ul>
 *  因为第二按钮和面板共享相同的主行,所以它们都沿着它们的基线对准
 * <p>
 * 使用基线相对值之一定位的组件与使用绝对或定向相对值定位时的组件大小不同如何组件更改由主行的基线如何更改决定如果有任何基线固定在显示区域的底部具有相同主要行的组件具有<code> CONSTANT_DES
 * CENT </code>的基线调整行为,否则基准被锚定到显示区域的顶部以下规则指示调整大小行为：。
 * <ul>
 * <li>定位在基线上方的可调整组件只能增长到与基线一样高例如,如果基线为100,并且锚定在顶部,则位于基线上方的可调整大小的组件永远不会增长超过100个单位<li>同样,位于基线下面的可调整大小的组件只
 * 能增长与显示高度和基线之间的差异。
 * <li>只有调整<code> OTHER </code>的基线调整行为时,位于基线上的可调整组件才会调整大小调整大小的大小的基线适合显示区域内。
 * 如果基线不适合显示区域,组件不调整大小<li>定位在基线上且没有<code> OTHER </code>的基线调整行为的组件只能增长到{@code display height  -  baseline + component of component}
 * 。
 * <li>只有调整<code> OTHER </code>的基线调整行为时,位于基线上的可调整组件才会调整大小调整大小的大小的基线适合显示区域内。
 * </ul>
 * 如果沿着基线定位组件,但该组件没有有效的基线,则它将在其空间中垂直居中。类似地,如果已经相对于基线放置了一个组件,并且该行中的所有组件都没有有效的基线组件垂直居中
 * <p>
 *  下图显示了由网格袋布局管理的十个组件(所有按钮)。图2显示了一个水平的,从左到右的容器的布局,图3显示了一个水平的,从右到左的容器
 * 
 *  <center> <table WIDTH = 600 summary ="layout">
 * <tr ALIGN=CENTER>
 * <td>
 * <img src="doc-files/GridBagLayout-1.gif" alt="The preceding text describes this graphic (Figure 1)." style="float:center; margin: 7px 10px;">
 * </td>
 * <td>
 * <img src="doc-files/GridBagLayout-2.gif" alt="The preceding text describes this graphic (Figure 2)." style="float:center; margin: 7px 10px;">
 * </td>
 * <tr ALIGN=CENTER>
 *  <td>图2：水平,从左到右</td> <td>图3：水平,从右到左</td>
 * </tr>
 *  </table> </center>
 * <p>
 * 十个组件中的每一个都具有其相关联的<code> GridBagConstraints </code>对象的<code> fill </code>字段设置为<code> GridBagConstraint
 * sBOTH </code>。
 * 此外,组件具有以下非缺省约束：。
 * 
 * <ul>
 * <li> Button1,Button2,Button3：<code> weightx&nbsp; =&nbsp; 10 </code> <li> Button4：<code> weightx&nbsp
 * ; =&nbsp; 10 </code>,<code> gridwidth&nbsp; = GridBagConstraintsREMAINDER </code> <li> Button5：<code>
 *  gridwidth&nbsp; =&nbsp; GridBagConstraintsREMAINDER </code> <li> Button6：<code> gridwidth&nbsp; =&nb
 * sp; GridBagConstraintsRELATIVE </code> <li> Button7： =&nbsp; GridBagConstraintsREMAINDER </code> <li>
 *  Button8：<code> gridheight&nbsp; =&nbsp; 2 </code>,<code> weighty&nbsp; =&nbsp; 10 </code> <li> Butto
 * n9,Button 10：<code > gridwidth&nbsp; =&nbsp; GridBagConstraintsREMAINDER </code>。
 * </ul>
 * <p>
 *  这里是实现上面示例的代码：
 * 
 *  <hr> <blockquote> <pre> import javaawt *; import javautil *; import javaappletApplet;
 * 
 *  public class GridBagEx1 extends Applet {
 * 
 * protected void makebutton(String name,GridBagLayout gridbag,GridBagConstraints c){Button button = new Button(name); gridbagsetConstraints(button,c);添加(按钮); }
 * }。
 * 
 *  public void init(){GridBagLayout gridbag = new GridBagLayout(); GridBagConstraints c = new GridBagConstraints();。
 * 
 *  setFont(new Font("SansSerif",FontPLAIN,14)); setLayout(gridbag)
 * 
 *  cfill = GridBagConstraintsBOTH; cweightx = 10; makebutton("Button1",gridbag,c); makebutton("Button2"
 * ,gridbag,c); makebutton("Button3",gridbag,c);。
 * 
 *  cgridwidth = GridBagConstraintsREMAINDER; // end row makebutton("Button4",gridbag,c);
 * 
 *  cweightx = 00; // reset to the default makebutton("Button5",gridbag,c); //另一行
 * 
 * cgridwidth = GridBagConstraintsRELATIVE; //在行中的倒数第二行makebutton("Button6",gridbag,c);
 * 
 *  cgridwidth = GridBagConstraintsREMAINDER; // end row makebutton("Button7",gridbag,c);
 * 
 *  cgridwidth = 1; // reset to the default cgridheight = 2; cweighty = 10; makebutton("Button8",gridbag
 * ,c);。
 * 
 *  cweighty = 00; // reset to the default cgridwidth = GridBagConstraintsREMAINDER; // end row cgridhei
 * ght = 1; // reset to the default makebutton("Button9",gridbag,c); makebutton("Button10",gridbag,c);。
 * 
 *  setSize(300,100); }}
 * 
 *  public static void main(String args []){Frame f = new Frame("GridBag Layout Example"); GridBagEx1 ex1 = new GridBagEx1();。
 * 
 *  ex1init();
 * 
 * fadd("Center",ex1); fpack(); fsetSize(fgetPreferredSize()); fshow(); }} </pre> </blockquote> <hr>
 * <p>
 * 
 * @author Doug Stein
 * @author Bill Spitzak (orignial NeWS &amp; OLIT implementation)
 * @see       java.awt.GridBagConstraints
 * @see       java.awt.GridBagLayoutInfo
 * @see       java.awt.ComponentOrientation
 * @since JDK1.0
 */
public class GridBagLayout implements LayoutManager2,
java.io.Serializable {

    static final int EMPIRICMULTIPLIER = 2;
    /**
     * This field is no longer used to reserve arrays and kept for backward
     * compatibility. Previously, this was
     * the maximum number of grid positions (both horizontal and
     * vertical) that could be laid out by the grid bag layout.
     * Current implementation doesn't impose any limits
     * on the size of a grid.
     * <p>
     *  该字段不再用于保留数组并保持向后兼容以前,这是可以由网格布局布局的最大网格位置数(水平和垂直)当前实现不会对网格的大小
     * 
     */
    protected static final int MAXGRIDSIZE = 512;

    /**
     * The smallest grid that can be laid out by the grid bag layout.
     * <p>
     *  可以通过网格布局布局的最小网格
     * 
     */
    protected static final int MINSIZE = 1;
    /**
     * The preferred grid size that can be laid out by the grid bag layout.
     * <p>
     *  可以通过网格布局布局的首选网格大小
     * 
     */
    protected static final int PREFERREDSIZE = 2;

    /**
     * This hashtable maintains the association between
     * a component and its gridbag constraints.
     * The Keys in <code>comptable</code> are the components and the
     * values are the instances of <code>GridBagConstraints</code>.
     *
     * <p>
     *  这个散列表保持组件和它的网格包约束之间的关联。<code> comptable </code>中的Keys是组件,值是<code> GridBagConstraints </code>
     * 
     * 
     * @serial
     * @see java.awt.GridBagConstraints
     */
    protected Hashtable<Component,GridBagConstraints> comptable;

    /**
     * This field holds a gridbag constraints instance
     * containing the default values, so if a component
     * does not have gridbag constraints associated with
     * it, then the component will be assigned a
     * copy of the <code>defaultConstraints</code>.
     *
     * <p>
     * 此字段包含一个包含默认值的网格包约束实例,因此如果一个组件没有与其相关的网格包约束,则该组件将被分配一个<code> defaultConstraints </code>
     * 
     * 
     * @serial
     * @see #getConstraints(Component)
     * @see #setConstraints(Component, GridBagConstraints)
     * @see #lookupConstraints(Component)
     */
    protected GridBagConstraints defaultConstraints;

    /**
     * This field holds the layout information
     * for the gridbag.  The information in this field
     * is based on the most recent validation of the
     * gridbag.
     * If <code>layoutInfo</code> is <code>null</code>
     * this indicates that there are no components in
     * the gridbag or if there are components, they have
     * not yet been validated.
     *
     * <p>
     *  此字段保存gridbag的布局信息此字段中的信息基于gridbag的最近验证如果<code> layoutInfo </code>是<code> null </code>,则表示没有组件gridbag
     * 或者如果有组件,它们还没有被验证。
     * 
     * 
     * @serial
     * @see #getLayoutInfo(Container, int)
     */
    protected GridBagLayoutInfo layoutInfo;

    /**
     * This field holds the overrides to the column minimum
     * width.  If this field is non-<code>null</code> the values are
     * applied to the gridbag after all of the minimum columns
     * widths have been calculated.
     * If columnWidths has more elements than the number of
     * columns, columns are added to the gridbag to match
     * the number of elements in columnWidth.
     *
     * <p>
     * 此字段包含对列最小宽度的覆盖。如果此字段为非<code> null </code>,则在计算了所有最小列宽度之后,这些值将应用于网格包。
     * 如果columnWidths的元素数超过列,列添加到gridbag中以匹配columnWidth中的元素数。
     * 
     * 
     * @serial
     * @see #getLayoutDimensions()
     */
    public int columnWidths[];

    /**
     * This field holds the overrides to the row minimum
     * heights.  If this field is non-<code>null</code> the values are
     * applied to the gridbag after all of the minimum row
     * heights have been calculated.
     * If <code>rowHeights</code> has more elements than the number of
     * rows, rows are added to the gridbag to match
     * the number of elements in <code>rowHeights</code>.
     *
     * <p>
     *  此字段保存对行最小高度的覆盖如果此字段为非<code> null </code>,则在计算所有最小行高度之后将值应用于网格包如果<code> rowHeights </code>具有比行数更多的元素,
     * 行被添加到gridbag以匹配<code> rowHeights </code>中的元素数量,。
     * 
     * 
     * @serial
     * @see #getLayoutDimensions()
     */
    public int rowHeights[];

    /**
     * This field holds the overrides to the column weights.
     * If this field is non-<code>null</code> the values are
     * applied to the gridbag after all of the columns
     * weights have been calculated.
     * If <code>columnWeights[i]</code> &gt; weight for column i, then
     * column i is assigned the weight in <code>columnWeights[i]</code>.
     * If <code>columnWeights</code> has more elements than the number
     * of columns, the excess elements are ignored - they do
     * not cause more columns to be created.
     *
     * <p>
     * 此字段保存对列权重的覆盖如果此字段为非<code> null </code>,则在计算所有列权重后,将值应用于gridbag如果<code> columnWeights [i] </code >&gt;
     * 第i列的权重,则第i列被分配在<code> columnWeights [i] </code>中的权重。
     * 如果<code> columnWeights </code>有多于列数的元素,则忽略多余的元素 - 不会导致创建更多的列。
     * 
     * 
     * @serial
     */
    public double columnWeights[];

    /**
     * This field holds the overrides to the row weights.
     * If this field is non-<code>null</code> the values are
     * applied to the gridbag after all of the rows
     * weights have been calculated.
     * If <code>rowWeights[i]</code> &gt; weight for row i, then
     * row i is assigned the weight in <code>rowWeights[i]</code>.
     * If <code>rowWeights</code> has more elements than the number
     * of rows, the excess elements are ignored - they do
     * not cause more rows to be created.
     *
     * <p>
     * 此字段包含对行权重的覆盖如果此字段为非<code> null </code>,则在计算所有行权重后,将值应用于网格包。
     * 如果<code> rowWeights [i] </code >&gt;第i行的权重,则第i行被分配在<code> rowWeights [i] </code>中的权重。
     * 如果<code> rowWeights </code>有多于行数的元素,则忽略多余的元素 - 不会导致创建更多的行。
     * 
     * 
     * @serial
     */
    public double rowWeights[];

    /**
     * The component being positioned.  This is set before calling into
     * <code>adjustForGravity</code>.
     * <p>
     *  被定位的组件在调用<code> adjustForGravity </code>之前设置
     * 
     */
    private Component componentAdjusting;

    /**
     * Creates a grid bag layout manager.
     * <p>
     *  创建网格包布局管理器
     * 
     */
    public GridBagLayout () {
        comptable = new Hashtable<Component,GridBagConstraints>();
        defaultConstraints = new GridBagConstraints();
    }

    /**
     * Sets the constraints for the specified component in this layout.
     * <p>
     *  在此布局中设置指定组件的约束
     * 
     * 
     * @param       comp the component to be modified
     * @param       constraints the constraints to be applied
     */
    public void setConstraints(Component comp, GridBagConstraints constraints) {
        comptable.put(comp, (GridBagConstraints)constraints.clone());
    }

    /**
     * Gets the constraints for the specified component.  A copy of
     * the actual <code>GridBagConstraints</code> object is returned.
     * <p>
     *  获取指定组件的约束返回实际的<code> GridBagConstraints </code>对象的副本
     * 
     * 
     * @param       comp the component to be queried
     * @return      the constraint for the specified component in this
     *                  grid bag layout; a copy of the actual constraint
     *                  object is returned
     */
    public GridBagConstraints getConstraints(Component comp) {
        GridBagConstraints constraints = comptable.get(comp);
        if (constraints == null) {
            setConstraints(comp, defaultConstraints);
            constraints = comptable.get(comp);
        }
        return (GridBagConstraints)constraints.clone();
    }

    /**
     * Retrieves the constraints for the specified component.
     * The return value is not a copy, but is the actual
     * <code>GridBagConstraints</code> object used by the layout mechanism.
     * <p>
     * If <code>comp</code> is not in the <code>GridBagLayout</code>,
     * a set of default <code>GridBagConstraints</code> are returned.
     * A <code>comp</code> value of <code>null</code> is invalid
     * and returns <code>null</code>.
     *
     * <p>
     * 检索指定组件的约束返回值不是副本,而是布局机制使用的实际<code> GridBagConstraints </code>对象
     * <p>
     *  如果<code> comp </code>不在<code> GridBagLayout </code>中,则返回一组默认<code> GridBagConstraints </code> A <code>
     *  comp </code> null </code>无效并返回<code> null </code>。
     * 
     * 
     * @param       comp the component to be queried
     * @return      the constraints for the specified component
     */
    protected GridBagConstraints lookupConstraints(Component comp) {
        GridBagConstraints constraints = comptable.get(comp);
        if (constraints == null) {
            setConstraints(comp, defaultConstraints);
            constraints = comptable.get(comp);
        }
        return constraints;
    }

    /**
     * Removes the constraints for the specified component in this layout
     * <p>
     *  删除此布局中指定组件的约束
     * 
     * 
     * @param       comp the component to be modified
     */
    private void removeConstraints(Component comp) {
        comptable.remove(comp);
    }

    /**
     * Determines the origin of the layout area, in the graphics coordinate
     * space of the target container.  This value represents the pixel
     * coordinates of the top-left corner of the layout area regardless of
     * the <code>ComponentOrientation</code> value of the container.  This
     * is distinct from the grid origin given by the cell coordinates (0,0).
     * Most applications do not call this method directly.
     * <p>
     * 确定布局区域的原点,在目标容器的图形坐标空间中此值表示布局区域的左上角的像素坐标,而不考虑容器的<code> ComponentOrientation </code>值这是不同于由单元格坐标(0,0)给
     * 出的网格原点大多数应用程序不直接调用此方法。
     * 
     * 
     * @return     the graphics origin of the cell in the top-left
     *             corner of the layout grid
     * @see        java.awt.ComponentOrientation
     * @since      JDK1.1
     */
    public Point getLayoutOrigin () {
        Point origin = new Point(0,0);
        if (layoutInfo != null) {
            origin.x = layoutInfo.startx;
            origin.y = layoutInfo.starty;
        }
        return origin;
    }

    /**
     * Determines column widths and row heights for the layout grid.
     * <p>
     * Most applications do not call this method directly.
     * <p>
     *  确定布局网格的列宽和行高
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @return     an array of two arrays, containing the widths
     *                       of the layout columns and
     *                       the heights of the layout rows
     * @since      JDK1.1
     */
    public int [][] getLayoutDimensions () {
        if (layoutInfo == null)
            return new int[2][0];

        int dim[][] = new int [2][];
        dim[0] = new int[layoutInfo.width];
        dim[1] = new int[layoutInfo.height];

        System.arraycopy(layoutInfo.minWidth, 0, dim[0], 0, layoutInfo.width);
        System.arraycopy(layoutInfo.minHeight, 0, dim[1], 0, layoutInfo.height);

        return dim;
    }

    /**
     * Determines the weights of the layout grid's columns and rows.
     * Weights are used to calculate how much a given column or row
     * stretches beyond its preferred size, if the layout has extra
     * room to fill.
     * <p>
     * Most applications do not call this method directly.
     * <p>
     *  确定布局网格的列和行的权重权重用于计算给定列或行延伸超出其首选大小的多少,如果布局有额外的空间填充
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @return      an array of two arrays, representing the
     *                    horizontal weights of the layout columns
     *                    and the vertical weights of the layout rows
     * @since       JDK1.1
     */
    public double [][] getLayoutWeights () {
        if (layoutInfo == null)
            return new double[2][0];

        double weights[][] = new double [2][];
        weights[0] = new double[layoutInfo.width];
        weights[1] = new double[layoutInfo.height];

        System.arraycopy(layoutInfo.weightX, 0, weights[0], 0, layoutInfo.width);
        System.arraycopy(layoutInfo.weightY, 0, weights[1], 0, layoutInfo.height);

        return weights;
    }

    /**
     * Determines which cell in the layout grid contains the point
     * specified by <code>(x,&nbsp;y)</code>. Each cell is identified
     * by its column index (ranging from 0 to the number of columns
     * minus 1) and its row index (ranging from 0 to the number of
     * rows minus 1).
     * <p>
     * If the <code>(x,&nbsp;y)</code> point lies
     * outside the grid, the following rules are used.
     * The column index is returned as zero if <code>x</code> lies to the
     * left of the layout for a left-to-right container or to the right of
     * the layout for a right-to-left container.  The column index is returned
     * as the number of columns if <code>x</code> lies
     * to the right of the layout in a left-to-right container or to the left
     * in a right-to-left container.
     * The row index is returned as zero if <code>y</code> lies above the
     * layout, and as the number of rows if <code>y</code> lies
     * below the layout.  The orientation of a container is determined by its
     * <code>ComponentOrientation</code> property.
     * <p>
     * 确定布局网格中的哪个单元格包含由<code>(x,y)指定的点</code>每个单元格由其列索引(范围从0到列数减去1)及其行索引(范围从0到行数减1)
     * <p>
     * 如果<code>(x,&nbsp; y)</code>点在网格之外,则使用以下规则。
     * 如果<code> x </code>位于布局的左边,列索引返回为零对于从左到右的容器,或者对于从右到左容器的布局的右边,如果<code> x </code>位于布局的右边,则列索引返回为列数从左到右容器
     * 或在从右到左容器中的左边如果<code> y </code>位于布局上方,行索引返回为零,如果<code> y </code>位于布局下方容器的方向由<code> ComponentOrientatio
     * n </code>属性决定。
     * 如果<code>(x,&nbsp; y)</code>点在网格之外,则使用以下规则。
     * 
     * 
     * @param      x    the <i>x</i> coordinate of a point
     * @param      y    the <i>y</i> coordinate of a point
     * @return     an ordered pair of indexes that indicate which cell
     *             in the layout grid contains the point
     *             (<i>x</i>,&nbsp;<i>y</i>).
     * @see        java.awt.ComponentOrientation
     * @since      JDK1.1
     */
    public Point location(int x, int y) {
        Point loc = new Point(0,0);
        int i, d;

        if (layoutInfo == null)
            return loc;

        d = layoutInfo.startx;
        if (!rightToLeft) {
            for (i=0; i<layoutInfo.width; i++) {
                d += layoutInfo.minWidth[i];
                if (d > x)
                    break;
            }
        } else {
            for (i=layoutInfo.width-1; i>=0; i--) {
                if (d > x)
                    break;
                d += layoutInfo.minWidth[i];
            }
            i++;
        }
        loc.x = i;

        d = layoutInfo.starty;
        for (i=0; i<layoutInfo.height; i++) {
            d += layoutInfo.minHeight[i];
            if (d > y)
                break;
        }
        loc.y = i;

        return loc;
    }

    /**
     * Has no effect, since this layout manager does not use a per-component string.
     * <p>
     *  没有效果,因为这个布局管理器不使用每个组件的字符串
     * 
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Adds the specified component to the layout, using the specified
     * <code>constraints</code> object.  Note that constraints
     * are mutable and are, therefore, cloned when cached.
     *
     * <p>
     * 使用指定的<code>约束</code>对象,将指定的组件添加到布局中注意约束是可变的,因此在缓存时进行克隆
     * 
     * 
     * @param      comp         the component to be added
     * @param      constraints  an object that determines how
     *                          the component is added to the layout
     * @exception IllegalArgumentException if <code>constraints</code>
     *            is not a <code>GridBagConstraint</code>
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof GridBagConstraints) {
            setConstraints(comp, (GridBagConstraints)constraints);
        } else if (constraints != null) {
            throw new IllegalArgumentException("cannot add to layout: constraints must be a GridBagConstraint");
        }
    }

    /**
     * Removes the specified component from this layout.
     * <p>
     * Most applications do not call this method directly.
     * <p>
     *  从此布局中删除指定的组件
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @param    comp   the component to be removed.
     * @see      java.awt.Container#remove(java.awt.Component)
     * @see      java.awt.Container#removeAll()
     */
    public void removeLayoutComponent(Component comp) {
        removeConstraints(comp);
    }

    /**
     * Determines the preferred size of the <code>parent</code>
     * container using this grid bag layout.
     * <p>
     * Most applications do not call this method directly.
     *
     * <p>
     *  使用此网格包布局确定<code>父</code>容器的首选大小
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @param     parent   the container in which to do the layout
     * @see       java.awt.Container#getPreferredSize
     * @return the preferred size of the <code>parent</code>
     *  container
     */
    public Dimension preferredLayoutSize(Container parent) {
        GridBagLayoutInfo info = getLayoutInfo(parent, PREFERREDSIZE);
        return getMinSize(parent, info);
    }

    /**
     * Determines the minimum size of the <code>parent</code> container
     * using this grid bag layout.
     * <p>
     * Most applications do not call this method directly.
     * <p>
     *  使用此网格包布局确定<code> parent </code>容器的最小大小
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @param     parent   the container in which to do the layout
     * @see       java.awt.Container#doLayout
     * @return the minimum size of the <code>parent</code> container
     */
    public Dimension minimumLayoutSize(Container parent) {
        GridBagLayoutInfo info = getLayoutInfo(parent, MINSIZE);
        return getMinSize(parent, info);
    }

    /**
     * Returns the maximum dimensions for this layout given the components
     * in the specified target container.
     * <p>
     *  返回给定指定目标容器中的组件的此布局的最大尺寸
     * 
     * 
     * @param target the container which needs to be laid out
     * @see Container
     * @see #minimumLayoutSize(Container)
     * @see #preferredLayoutSize(Container)
     * @return the maximum dimensions for this layout
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
     * <p>
     * 返回沿x轴的对齐方式这指定了组件相对于其他组件的对齐方式。该值应为0和1之间的数字,其中0表示沿原点对齐,1对齐距离原点最远,05中心等
     * <p>
     * 
     * @return the value <code>0.5f</code> to indicate centered
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
     * <p>
     *  返回沿y轴的对齐这指定了组件相对于其他组件的对齐方式。值应该是0和1之间的数字,其中0表示沿原点对齐,1对齐距离原点最远,05中心等
     * <p>
     * 
     * @return the value <code>0.5f</code> to indicate centered
     */
    public float getLayoutAlignmentY(Container parent) {
        return 0.5f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * <p>
     *  使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃
     * 
     */
    public void invalidateLayout(Container target) {
    }

    /**
     * Lays out the specified container using this grid bag layout.
     * This method reshapes components in the specified container in
     * order to satisfy the constraints of this <code>GridBagLayout</code>
     * object.
     * <p>
     * Most applications do not call this method directly.
     * <p>
     * 使用此网格包布局放弃指定的容器此方法重新塑造指定容器中的组件,以满足此<code> GridBagLayout </code>对象的约束
     * <p>
     *  大多数应用程序不直接调用此方法
     * 
     * 
     * @param parent the container in which to do the layout
     * @see java.awt.Container
     * @see java.awt.Container#doLayout
     */
    public void layoutContainer(Container parent) {
        arrangeGrid(parent);
    }

    /**
     * Returns a string representation of this grid bag layout's values.
     * <p>
     *  返回此网格包布局的值的字符串表示形式
     * 
     * 
     * @return     a string representation of this grid bag layout.
     */
    public String toString() {
        return getClass().getName();
    }

    /**
     * Print the layout information.  Useful for debugging.
     * <p>
     *  打印布局信息有用于调试
     * 
     */

    /* DEBUG
     *
     *  protected void dumpLayoutInfo(GridBagLayoutInfo s) {
     *    int x;
     *
     *    System.out.println("Col\tWidth\tWeight");
     *    for (x=0; x<s.width; x++) {
     *      System.out.println(x + "\t" +
     *                   s.minWidth[x] + "\t" +
     *                   s.weightX[x]);
     *    }
     *    System.out.println("Row\tHeight\tWeight");
     *    for (x=0; x<s.height; x++) {
     *      System.out.println(x + "\t" +
     *                   s.minHeight[x] + "\t" +
     *                   s.weightY[x]);
     *    }
     *  }
     * <p>
     *  protected void dumpLayoutInfo(GridBagLayoutInfo s){int x;
     * 
     *  Systemoutprintln("Col \\ tWidth \\ tWeight"); for(x = 0; x <swidth; x ++){Systemoutprintln(x +"\\ t"+ sminWidth [x] +"\\ t"+ sweightX [x]); } Systemoutprintln("Row \\ tHeight \\ tWeight"); for(x = 0; x <sheight; x ++){Systemoutprintln(x +"\\ t"+ sminHeight [x] +"\\ t"+ sweightY [x] }}。
     * 
     */

    /**
     * Print the layout constraints.  Useful for debugging.
     * <p>
     *  打印布局约束有用于调试
     * 
     */

    /* DEBUG
     *
     *  protected void dumpConstraints(GridBagConstraints constraints) {
     *    System.out.println(
     *                 "wt " +
     *                 constraints.weightx +
     *                 " " +
     *                 constraints.weighty +
     *                 ", " +
     *
     *                 "box " +
     *                 constraints.gridx +
     *                 " " +
     *                 constraints.gridy +
     *                 " " +
     *                 constraints.gridwidth +
     *                 " " +
     *                 constraints.gridheight +
     *                 ", " +
     *
     *                 "min " +
     *                 constraints.minWidth +
     *                 " " +
     *                 constraints.minHeight +
     *                 ", " +
     *
     *                 "pad " +
     *                 constraints.insets.bottom +
     *                 " " +
     *                 constraints.insets.left +
     *                 " " +
     *                 constraints.insets.right +
     *                 " " +
     *                 constraints.insets.top +
     *                 " " +
     *                 constraints.ipadx +
     *                 " " +
     *                 constraints.ipady);
     *  }
     * <p>
     * protected void dumpConstraints(GridBagConstraints constraints){Systemoutprintln("wt"+ constraintsweightx +""+ constraintsweighty +","+。
     * 
     *  "box"+ constraintsgridx +""+ constraintsgridy +""+ constraintsgridwidth +""+ constraintsgridheight +
     * ","+。
     * 
     *  "min"+ constraintsminWidth +""+ constraintsminHeight +","+
     * 
     *  "pad"+ constraintsinsetsbottom +""+ constraintsinsetsleft +""+ constraintsinsetsright +""+ constrain
     * tsinsetstop +""+ constraintsipadx +""+ constraintsipady); }}。
     * 
     */

    /**
     * Fills in an instance of <code>GridBagLayoutInfo</code> for the
     * current set of managed children. This requires three passes through the
     * set of children:
     *
     * <ol>
     * <li>Figure out the dimensions of the layout grid.
     * <li>Determine which cells the components occupy.
     * <li>Distribute the weights and min sizes among the rows/columns.
     * </ol>
     *
     * This also caches the minsizes for all the children when they are
     * first encountered (so subsequent loops don't need to ask again).
     * <p>
     * This method should only be used internally by
     * <code>GridBagLayout</code>.
     *
     * <p>
     *  在当前集合的托管子项中填充<code> GridBagLayoutInfo </code>的实例这需要三次通过子集：
     * 
     * <ol>
     * <li>找出布局网格的尺寸<li>确定组件占用的单元格<li>在行/列之间分配权重和最小尺寸
     * </ol>
     * 
     *  这也缓存了所有孩子的minsizes,当他们第一次遇到(因此后续循环不需要再询问)
     * <p>
     *  此方法应仅由<code> GridBagLayout </code>在内部使用
     * 
     * 
     * @param parent  the layout container
     * @param sizeflag either <code>PREFERREDSIZE</code> or
     *   <code>MINSIZE</code>
     * @return the <code>GridBagLayoutInfo</code> for the set of children
     * @since 1.4
     */
    protected GridBagLayoutInfo getLayoutInfo(Container parent, int sizeflag) {
        return GetLayoutInfo(parent, sizeflag);
    }

    /*
     * Calculate maximum array sizes to allocate arrays without ensureCapacity
     * we may use preCalculated sizes in whole class because of upper estimation of
     * maximumArrayXIndex and maximumArrayYIndex.
     * <p>
     *  计算最大数组大小以分配没有ensureCapacity的数组,我们可以在整个类中使用preCalculated大小,因为上面估计的maximumArrayXIndex和maximumArrayYInd
     * ex。
     * 
     */

    private long[]  preInitMaximumArraySizes(Container parent){
        Component components[] = parent.getComponents();
        Component comp;
        GridBagConstraints constraints;
        int curX, curY;
        int curWidth, curHeight;
        int preMaximumArrayXIndex = 0;
        int preMaximumArrayYIndex = 0;
        long [] returnArray = new long[2];

        for (int compId = 0 ; compId < components.length ; compId++) {
            comp = components[compId];
            if (!comp.isVisible()) {
                continue;
            }

            constraints = lookupConstraints(comp);
            curX = constraints.gridx;
            curY = constraints.gridy;
            curWidth = constraints.gridwidth;
            curHeight = constraints.gridheight;

            // -1==RELATIVE, means that column|row equals to previously added component,
            // since each next Component with gridx|gridy == RELATIVE starts from
            // previous position, so we should start from previous component which
            // already used in maximumArray[X|Y]Index calculation. We could just increase
            // maximum by 1 to handle situation when component with gridx=-1 was added.
            if (curX < 0){
                curX = ++preMaximumArrayYIndex;
            }
            if (curY < 0){
                curY = ++preMaximumArrayXIndex;
            }
            // gridwidth|gridheight may be equal to RELATIVE (-1) or REMAINDER (0)
            // in any case using 1 instead of 0 or -1 should be sufficient to for
            // correct maximumArraySizes calculation
            if (curWidth <= 0){
                curWidth = 1;
            }
            if (curHeight <= 0){
                curHeight = 1;
            }

            preMaximumArrayXIndex = Math.max(curY + curHeight, preMaximumArrayXIndex);
            preMaximumArrayYIndex = Math.max(curX + curWidth, preMaximumArrayYIndex);
        } //for (components) loop
        // Must specify index++ to allocate well-working arrays.
        /* fix for 4623196.
         * now return long array instead of Point
         * <p>
         *  现在返回long数组而不是Point
         * 
         */
        returnArray[0] = preMaximumArrayXIndex;
        returnArray[1] = preMaximumArrayYIndex;
        return returnArray;
    } //PreInitMaximumSizes

    /**
     * This method is obsolete and supplied for backwards
     * compatibility only; new code should call {@link
     * #getLayoutInfo(java.awt.Container, int) getLayoutInfo} instead.
     * This method is the same as <code>getLayoutInfo</code>;
     * refer to <code>getLayoutInfo</code> for details on parameters
     * and return value.
     * <p>
     * 此方法已过时,仅供向后兼容;新代码应该调用{@link #getLayoutInfo(javaawtContainer,int)getLayoutInfo},而不是这个方法与<code> getLayo
     * utInfo </code>相同;有关参数和返回值的详细信息,请参阅<code> getLayoutInfo </code>。
     * 
     */
    protected GridBagLayoutInfo GetLayoutInfo(Container parent, int sizeflag) {
        synchronized (parent.getTreeLock()) {
            GridBagLayoutInfo r;
            Component comp;
            GridBagConstraints constraints;
            Dimension d;
            Component components[] = parent.getComponents();
            // Code below will address index curX+curWidth in the case of yMaxArray, weightY
            // ( respectively curY+curHeight for xMaxArray, weightX ) where
            //  curX in 0 to preInitMaximumArraySizes.y
            // Thus, the maximum index that could
            // be calculated in the following code is curX+curX.
            // EmpericMultier equals 2 because of this.

            int layoutWidth, layoutHeight;
            int []xMaxArray;
            int []yMaxArray;
            int compindex, i, k, px, py, pixels_diff, nextSize;
            int curX = 0; // constraints.gridx
            int curY = 0; // constraints.gridy
            int curWidth = 1;  // constraints.gridwidth
            int curHeight = 1;  // constraints.gridheight
            int curRow, curCol;
            double weight_diff, weight;
            int maximumArrayXIndex = 0;
            int maximumArrayYIndex = 0;
            int anchor;

            /*
             * Pass #1
             *
             * Figure out the dimensions of the layout grid (use a value of 1 for
             * zero or negative widths and heights).
             * <p>
             *  通过#1
             * 
             *  计算出布局网格的尺寸(对于零或负宽度和高度,使用值为1)
             * 
             */

            layoutWidth = layoutHeight = 0;
            curRow = curCol = -1;
            long [] arraySizes = preInitMaximumArraySizes(parent);

            /* fix for 4623196.
             * If user try to create a very big grid we can
             * get NegativeArraySizeException because of integer value
             * overflow (EMPIRICMULTIPLIER*gridSize might be more then Integer.MAX_VALUE).
             * We need to detect this situation and try to create a
             * grid with Integer.MAX_VALUE size instead.
             * <p>
             *  如果用户尝试创建一个非常大的网格,我们可以得到NegativeArraySizeException因为整数值溢出(EMPIRICMULTIPLIER * gridSize可能更多然后IntegerMA
             * X_VALUE)我们需要检测这种情况,并尝试创建一个具有IntegerMAX_VALUE大小的网格。
             * 
             */
            maximumArrayXIndex = (EMPIRICMULTIPLIER * arraySizes[0] > Integer.MAX_VALUE )? Integer.MAX_VALUE : EMPIRICMULTIPLIER*(int)arraySizes[0];
            maximumArrayYIndex = (EMPIRICMULTIPLIER * arraySizes[1] > Integer.MAX_VALUE )? Integer.MAX_VALUE : EMPIRICMULTIPLIER*(int)arraySizes[1];

            if (rowHeights != null){
                maximumArrayXIndex = Math.max(maximumArrayXIndex, rowHeights.length);
            }
            if (columnWidths != null){
                maximumArrayYIndex = Math.max(maximumArrayYIndex, columnWidths.length);
            }

            xMaxArray = new int[maximumArrayXIndex];
            yMaxArray = new int[maximumArrayYIndex];

            boolean hasBaseline = false;
            for (compindex = 0 ; compindex < components.length ; compindex++) {
                comp = components[compindex];
                if (!comp.isVisible())
                    continue;
                constraints = lookupConstraints(comp);

                curX = constraints.gridx;
                curY = constraints.gridy;
                curWidth = constraints.gridwidth;
                if (curWidth <= 0)
                    curWidth = 1;
                curHeight = constraints.gridheight;
                if (curHeight <= 0)
                    curHeight = 1;

                /* If x or y is negative, then use relative positioning: */
                if (curX < 0 && curY < 0) {
                    if (curRow >= 0)
                        curY = curRow;
                    else if (curCol >= 0)
                        curX = curCol;
                    else
                        curY = 0;
                }
                if (curX < 0) {
                    px = 0;
                    for (i = curY; i < (curY + curHeight); i++) {
                        px = Math.max(px, xMaxArray[i]);
                    }

                    curX = px - curX - 1;
                    if(curX < 0)
                        curX = 0;
                }
                else if (curY < 0) {
                    py = 0;
                    for (i = curX; i < (curX + curWidth); i++) {
                        py = Math.max(py, yMaxArray[i]);
                    }
                    curY = py - curY - 1;
                    if(curY < 0)
                        curY = 0;
                }

                /* Adjust the grid width and height
                 *  fix for 5005945: unneccessary loops removed
                 * <p>
                 *  修复5005945：删除了不必要的循环
                 * 
                 */
                px = curX + curWidth;
                if (layoutWidth < px) {
                    layoutWidth = px;
                }
                py = curY + curHeight;
                if (layoutHeight < py) {
                    layoutHeight = py;
                }

                /* Adjust xMaxArray and yMaxArray */
                for (i = curX; i < (curX + curWidth); i++) {
                    yMaxArray[i] =py;
                }
                for (i = curY; i < (curY + curHeight); i++) {
                    xMaxArray[i] = px;
                }


                /* Cache the current slave's size. */
                if (sizeflag == PREFERREDSIZE)
                    d = comp.getPreferredSize();
                else
                    d = comp.getMinimumSize();
                constraints.minWidth = d.width;
                constraints.minHeight = d.height;
                if (calculateBaseline(comp, constraints, d)) {
                    hasBaseline = true;
                }

                /* Zero width and height must mean that this is the last item (or
                /* <p>
                /* 
                 * else something is wrong). */
                if (constraints.gridheight == 0 && constraints.gridwidth == 0)
                    curRow = curCol = -1;

                /* Zero width starts a new row */
                if (constraints.gridheight == 0 && curRow < 0)
                    curCol = curX + curWidth;

                /* Zero height starts a new column */
                else if (constraints.gridwidth == 0 && curCol < 0)
                    curRow = curY + curHeight;
            } //for (components) loop


            /*
             * Apply minimum row/column dimensions
             * <p>
             *  应用最小行/列尺寸
             * 
             */
            if (columnWidths != null && layoutWidth < columnWidths.length)
                layoutWidth = columnWidths.length;
            if (rowHeights != null && layoutHeight < rowHeights.length)
                layoutHeight = rowHeights.length;

            r = new GridBagLayoutInfo(layoutWidth, layoutHeight);

            /*
             * Pass #2
             *
             * Negative values for gridX are filled in with the current x value.
             * Negative values for gridY are filled in with the current y value.
             * Negative or zero values for gridWidth and gridHeight end the current
             *  row or column, respectively.
             * <p>
             *  通过#2
             * 
             * gridX的负值用当前x值填充gridY的负值用当前y值填充gridWidth和gridHeight的负值或零值分别结束当前行或列
             * 
             */

            curRow = curCol = -1;

            Arrays.fill(xMaxArray, 0);
            Arrays.fill(yMaxArray, 0);

            int[] maxAscent = null;
            int[] maxDescent = null;
            short[] baselineType = null;

            if (hasBaseline) {
                r.maxAscent = maxAscent = new int[layoutHeight];
                r.maxDescent = maxDescent = new int[layoutHeight];
                r.baselineType = baselineType = new short[layoutHeight];
                r.hasBaseline = true;
            }


            for (compindex = 0 ; compindex < components.length ; compindex++) {
                comp = components[compindex];
                if (!comp.isVisible())
                    continue;
                constraints = lookupConstraints(comp);

                curX = constraints.gridx;
                curY = constraints.gridy;
                curWidth = constraints.gridwidth;
                curHeight = constraints.gridheight;

                /* If x or y is negative, then use relative positioning: */
                if (curX < 0 && curY < 0) {
                    if(curRow >= 0)
                        curY = curRow;
                    else if(curCol >= 0)
                        curX = curCol;
                    else
                        curY = 0;
                }

                if (curX < 0) {
                    if (curHeight <= 0) {
                        curHeight += r.height - curY;
                        if (curHeight < 1)
                            curHeight = 1;
                    }

                    px = 0;
                    for (i = curY; i < (curY + curHeight); i++)
                        px = Math.max(px, xMaxArray[i]);

                    curX = px - curX - 1;
                    if(curX < 0)
                        curX = 0;
                }
                else if (curY < 0) {
                    if (curWidth <= 0) {
                        curWidth += r.width - curX;
                        if (curWidth < 1)
                            curWidth = 1;
                    }

                    py = 0;
                    for (i = curX; i < (curX + curWidth); i++){
                        py = Math.max(py, yMaxArray[i]);
                    }

                    curY = py - curY - 1;
                    if(curY < 0)
                        curY = 0;
                }

                if (curWidth <= 0) {
                    curWidth += r.width - curX;
                    if (curWidth < 1)
                        curWidth = 1;
                }

                if (curHeight <= 0) {
                    curHeight += r.height - curY;
                    if (curHeight < 1)
                        curHeight = 1;
                }

                px = curX + curWidth;
                py = curY + curHeight;

                for (i = curX; i < (curX + curWidth); i++) { yMaxArray[i] = py; }
                for (i = curY; i < (curY + curHeight); i++) { xMaxArray[i] = px; }

                /* Make negative sizes start a new row/column */
                if (constraints.gridheight == 0 && constraints.gridwidth == 0)
                    curRow = curCol = -1;
                if (constraints.gridheight == 0 && curRow < 0)
                    curCol = curX + curWidth;
                else if (constraints.gridwidth == 0 && curCol < 0)
                    curRow = curY + curHeight;

                /* Assign the new values to the gridbag slave */
                constraints.tempX = curX;
                constraints.tempY = curY;
                constraints.tempWidth = curWidth;
                constraints.tempHeight = curHeight;

                anchor = constraints.anchor;
                if (hasBaseline) {
                    switch(anchor) {
                    case GridBagConstraints.BASELINE:
                    case GridBagConstraints.BASELINE_LEADING:
                    case GridBagConstraints.BASELINE_TRAILING:
                        if (constraints.ascent >= 0) {
                            if (curHeight == 1) {
                                maxAscent[curY] =
                                        Math.max(maxAscent[curY],
                                                 constraints.ascent);
                                maxDescent[curY] =
                                        Math.max(maxDescent[curY],
                                                 constraints.descent);
                            }
                            else {
                                if (constraints.baselineResizeBehavior ==
                                        Component.BaselineResizeBehavior.
                                        CONSTANT_DESCENT) {
                                    maxDescent[curY + curHeight - 1] =
                                        Math.max(maxDescent[curY + curHeight
                                                            - 1],
                                                 constraints.descent);
                                }
                                else {
                                    maxAscent[curY] = Math.max(maxAscent[curY],
                                                           constraints.ascent);
                                }
                            }
                            if (constraints.baselineResizeBehavior ==
                                    Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
                                baselineType[curY + curHeight - 1] |=
                                        (1 << constraints.
                                         baselineResizeBehavior.ordinal());
                            }
                            else {
                                baselineType[curY] |= (1 << constraints.
                                             baselineResizeBehavior.ordinal());
                            }
                        }
                        break;
                    case GridBagConstraints.ABOVE_BASELINE:
                    case GridBagConstraints.ABOVE_BASELINE_LEADING:
                    case GridBagConstraints.ABOVE_BASELINE_TRAILING:
                        // Component positioned above the baseline.
                        // To make the bottom edge of the component aligned
                        // with the baseline the bottom inset is
                        // added to the descent, the rest to the ascent.
                        pixels_diff = constraints.minHeight +
                                constraints.insets.top +
                                constraints.ipady;
                        maxAscent[curY] = Math.max(maxAscent[curY],
                                                   pixels_diff);
                        maxDescent[curY] = Math.max(maxDescent[curY],
                                                    constraints.insets.bottom);
                        break;
                    case GridBagConstraints.BELOW_BASELINE:
                    case GridBagConstraints.BELOW_BASELINE_LEADING:
                    case GridBagConstraints.BELOW_BASELINE_TRAILING:
                        // Component positioned below the baseline.
                        // To make the top edge of the component aligned
                        // with the baseline the top inset is
                        // added to the ascent, the rest to the descent.
                        pixels_diff = constraints.minHeight +
                                constraints.insets.bottom + constraints.ipady;
                        maxDescent[curY] = Math.max(maxDescent[curY],
                                                    pixels_diff);
                        maxAscent[curY] = Math.max(maxAscent[curY],
                                                   constraints.insets.top);
                        break;
                    }
                }
            }

            r.weightX = new double[maximumArrayYIndex];
            r.weightY = new double[maximumArrayXIndex];
            r.minWidth = new int[maximumArrayYIndex];
            r.minHeight = new int[maximumArrayXIndex];


            /*
             * Apply minimum row/column dimensions and weights
             * <p>
             *  应用最小行/列尺寸和重量
             * 
             */
            if (columnWidths != null)
                System.arraycopy(columnWidths, 0, r.minWidth, 0, columnWidths.length);
            if (rowHeights != null)
                System.arraycopy(rowHeights, 0, r.minHeight, 0, rowHeights.length);
            if (columnWeights != null)
                System.arraycopy(columnWeights, 0, r.weightX, 0,  Math.min(r.weightX.length, columnWeights.length));
            if (rowWeights != null)
                System.arraycopy(rowWeights, 0, r.weightY, 0,  Math.min(r.weightY.length, rowWeights.length));

            /*
             * Pass #3
             *
             * Distribute the minimun widths and weights:
             * <p>
             *  通过#3
             * 
             *  分发最小宽度和权重：
             * 
             */

            nextSize = Integer.MAX_VALUE;

            for (i = 1;
                 i != Integer.MAX_VALUE;
                 i = nextSize, nextSize = Integer.MAX_VALUE) {
                for (compindex = 0 ; compindex < components.length ; compindex++) {
                    comp = components[compindex];
                    if (!comp.isVisible())
                        continue;
                    constraints = lookupConstraints(comp);

                    if (constraints.tempWidth == i) {
                        px = constraints.tempX + constraints.tempWidth; /* right column */

                        /*
                         * Figure out if we should use this slave\'s weight.  If the weight
                         * is less than the total weight spanned by the width of the cell,
                         * then discard the weight.  Otherwise split the difference
                         * according to the existing weights.
                         * <p>
                         *  找出我们是否应该使用这个奴隶的权重如果权重小于由单元格宽度跨越的总权重,则丢弃权重否则根据现有权重分割差异
                         * 
                         */

                        weight_diff = constraints.weightx;
                        for (k = constraints.tempX; k < px; k++)
                            weight_diff -= r.weightX[k];
                        if (weight_diff > 0.0) {
                            weight = 0.0;
                            for (k = constraints.tempX; k < px; k++)
                                weight += r.weightX[k];
                            for (k = constraints.tempX; weight > 0.0 && k < px; k++) {
                                double wt = r.weightX[k];
                                double dx = (wt * weight_diff) / weight;
                                r.weightX[k] += dx;
                                weight_diff -= dx;
                                weight -= wt;
                            }
                            /* Assign the remainder to the rightmost cell */
                            r.weightX[px-1] += weight_diff;
                        }

                        /*
                         * Calculate the minWidth array values.
                         * First, figure out how wide the current slave needs to be.
                         * Then, see if it will fit within the current minWidth values.
                         * If it will not fit, add the difference according to the
                         * weightX array.
                         * <p>
                         *  计算minWidth数组值首先,弄清楚当前从节点需要多宽,然后,看它是否适合当前的minWidth值如果它不合适,根据weightX数组添加差值
                         * 
                         */

                        pixels_diff =
                            constraints.minWidth + constraints.ipadx +
                            constraints.insets.left + constraints.insets.right;

                        for (k = constraints.tempX; k < px; k++)
                            pixels_diff -= r.minWidth[k];
                        if (pixels_diff > 0) {
                            weight = 0.0;
                            for (k = constraints.tempX; k < px; k++)
                                weight += r.weightX[k];
                            for (k = constraints.tempX; weight > 0.0 && k < px; k++) {
                                double wt = r.weightX[k];
                                int dx = (int)((wt * ((double)pixels_diff)) / weight);
                                r.minWidth[k] += dx;
                                pixels_diff -= dx;
                                weight -= wt;
                            }
                            /* Any leftovers go into the rightmost cell */
                            r.minWidth[px-1] += pixels_diff;
                        }
                    }
                    else if (constraints.tempWidth > i && constraints.tempWidth < nextSize)
                        nextSize = constraints.tempWidth;


                    if (constraints.tempHeight == i) {
                        py = constraints.tempY + constraints.tempHeight; /* bottom row */

                        /*
                         * Figure out if we should use this slave's weight.  If the weight
                         * is less than the total weight spanned by the height of the cell,
                         * then discard the weight.  Otherwise split it the difference
                         * according to the existing weights.
                         * <p>
                         * 找出我们是否应该使用从属的权重如果权重小于由单元格的高度跨越的总权重,则丢弃权重否则根据现有权重拆分差异
                         * 
                         */

                        weight_diff = constraints.weighty;
                        for (k = constraints.tempY; k < py; k++)
                            weight_diff -= r.weightY[k];
                        if (weight_diff > 0.0) {
                            weight = 0.0;
                            for (k = constraints.tempY; k < py; k++)
                                weight += r.weightY[k];
                            for (k = constraints.tempY; weight > 0.0 && k < py; k++) {
                                double wt = r.weightY[k];
                                double dy = (wt * weight_diff) / weight;
                                r.weightY[k] += dy;
                                weight_diff -= dy;
                                weight -= wt;
                            }
                            /* Assign the remainder to the bottom cell */
                            r.weightY[py-1] += weight_diff;
                        }

                        /*
                         * Calculate the minHeight array values.
                         * First, figure out how tall the current slave needs to be.
                         * Then, see if it will fit within the current minHeight values.
                         * If it will not fit, add the difference according to the
                         * weightY array.
                         * <p>
                         *  计算minHeight数组值首先,弄清楚当前从节点需要多大,然后,看它是否适合当前的minHeight值如果它不合适,根据weightY数组添加差值
                         * 
                         */

                        pixels_diff = -1;
                        if (hasBaseline) {
                            switch(constraints.anchor) {
                            case GridBagConstraints.BASELINE:
                            case GridBagConstraints.BASELINE_LEADING:
                            case GridBagConstraints.BASELINE_TRAILING:
                                if (constraints.ascent >= 0) {
                                    if (constraints.tempHeight == 1) {
                                        pixels_diff =
                                            maxAscent[constraints.tempY] +
                                            maxDescent[constraints.tempY];
                                    }
                                    else if (constraints.baselineResizeBehavior !=
                                             Component.BaselineResizeBehavior.
                                             CONSTANT_DESCENT) {
                                        pixels_diff =
                                                maxAscent[constraints.tempY] +
                                                constraints.descent;
                                    }
                                    else {
                                        pixels_diff = constraints.ascent +
                                                maxDescent[constraints.tempY +
                                                   constraints.tempHeight - 1];
                                    }
                                }
                                break;
                            case GridBagConstraints.ABOVE_BASELINE:
                            case GridBagConstraints.ABOVE_BASELINE_LEADING:
                            case GridBagConstraints.ABOVE_BASELINE_TRAILING:
                                pixels_diff = constraints.insets.top +
                                        constraints.minHeight +
                                        constraints.ipady +
                                        maxDescent[constraints.tempY];
                                break;
                            case GridBagConstraints.BELOW_BASELINE:
                            case GridBagConstraints.BELOW_BASELINE_LEADING:
                            case GridBagConstraints.BELOW_BASELINE_TRAILING:
                                pixels_diff = maxAscent[constraints.tempY] +
                                        constraints.minHeight +
                                        constraints.insets.bottom +
                                        constraints.ipady;
                                break;
                            }
                        }
                        if (pixels_diff == -1) {
                            pixels_diff =
                                constraints.minHeight + constraints.ipady +
                                constraints.insets.top +
                                constraints.insets.bottom;
                        }
                        for (k = constraints.tempY; k < py; k++)
                            pixels_diff -= r.minHeight[k];
                        if (pixels_diff > 0) {
                            weight = 0.0;
                            for (k = constraints.tempY; k < py; k++)
                                weight += r.weightY[k];
                            for (k = constraints.tempY; weight > 0.0 && k < py; k++) {
                                double wt = r.weightY[k];
                                int dy = (int)((wt * ((double)pixels_diff)) / weight);
                                r.minHeight[k] += dy;
                                pixels_diff -= dy;
                                weight -= wt;
                            }
                            /* Any leftovers go into the bottom cell */
                            r.minHeight[py-1] += pixels_diff;
                        }
                    }
                    else if (constraints.tempHeight > i &&
                             constraints.tempHeight < nextSize)
                        nextSize = constraints.tempHeight;
                }
            }
            return r;
        }
    } //getLayoutInfo()

    /**
     * Calculate the baseline for the specified component.
     * If {@code c} is positioned along it's baseline, the baseline is
     * obtained and the {@code constraints} ascent, descent and
     * baseline resize behavior are set from the component; and true is
     * returned. Otherwise false is returned.
     * <p>
     *  计算指定组件的基线如果{@code c}沿着其基线放置,那么将获取基线,并从组件中设置{@code constraints}上升,下降和基线调整行为;并返回true返回否则返回false
     * 
     */
    private boolean calculateBaseline(Component c,
                                      GridBagConstraints constraints,
                                      Dimension size) {
        int anchor = constraints.anchor;
        if (anchor == GridBagConstraints.BASELINE ||
                anchor == GridBagConstraints.BASELINE_LEADING ||
                anchor == GridBagConstraints.BASELINE_TRAILING) {
            // Apply the padding to the component, then ask for the baseline.
            int w = size.width + constraints.ipadx;
            int h = size.height + constraints.ipady;
            constraints.ascent = c.getBaseline(w, h);
            if (constraints.ascent >= 0) {
                // Component has a baseline
                int baseline = constraints.ascent;
                // Adjust the ascent and descent to include the insets.
                constraints.descent = h - constraints.ascent +
                            constraints.insets.bottom;
                constraints.ascent += constraints.insets.top;
                constraints.baselineResizeBehavior =
                        c.getBaselineResizeBehavior();
                constraints.centerPadding = 0;
                if (constraints.baselineResizeBehavior == Component.
                        BaselineResizeBehavior.CENTER_OFFSET) {
                    // Component has a baseline resize behavior of
                    // CENTER_OFFSET, calculate centerPadding and
                    // centerOffset (see the description of
                    // CENTER_OFFSET in the enum for detais on this
                    // algorithm).
                    int nextBaseline = c.getBaseline(w, h + 1);
                    constraints.centerOffset = baseline - h / 2;
                    if (h % 2 == 0) {
                        if (baseline != nextBaseline) {
                            constraints.centerPadding = 1;
                        }
                    }
                    else if (baseline == nextBaseline){
                        constraints.centerOffset--;
                        constraints.centerPadding = 1;
                    }
                }
            }
            return true;
        }
        else {
            constraints.ascent = -1;
            return false;
        }
    }

    /**
     * Adjusts the x, y, width, and height fields to the correct
     * values depending on the constraint geometry and pads.
     * This method should only be used internally by
     * <code>GridBagLayout</code>.
     *
     * <p>
     * 根据约束几何和衬垫将x,y,width和height字段调整为正确的值此方法仅应由<code> GridBagLayout </code>内部使用。
     * 
     * 
     * @param constraints the constraints to be applied
     * @param r the <code>Rectangle</code> to be adjusted
     * @since 1.4
     */
    protected void adjustForGravity(GridBagConstraints constraints,
                                    Rectangle r) {
        AdjustForGravity(constraints, r);
    }

    /**
     * This method is obsolete and supplied for backwards
     * compatibility only; new code should call {@link
     * #adjustForGravity(java.awt.GridBagConstraints, java.awt.Rectangle)
     * adjustForGravity} instead.
     * This method is the same as <code>adjustForGravity</code>;
     * refer to <code>adjustForGravity</code> for details
     * on parameters.
     * <p>
     *  此方法已过时,仅供向后兼容;新代码应该调用{@link #adjustForGravity(javaawtGridBagConstraints,javaawtRectangle)adjustForGravity}
     * 而不是这个方法与<code> adjustForGravity </code>相同;有关参数的详细信息,请参阅<code> adjustForGravity </code>。
     * 
     */
    protected void AdjustForGravity(GridBagConstraints constraints,
                                    Rectangle r) {
        int diffx, diffy;
        int cellY = r.y;
        int cellHeight = r.height;

        if (!rightToLeft) {
            r.x += constraints.insets.left;
        } else {
            r.x -= r.width - constraints.insets.right;
        }
        r.width -= (constraints.insets.left + constraints.insets.right);
        r.y += constraints.insets.top;
        r.height -= (constraints.insets.top + constraints.insets.bottom);

        diffx = 0;
        if ((constraints.fill != GridBagConstraints.HORIZONTAL &&
             constraints.fill != GridBagConstraints.BOTH)
            && (r.width > (constraints.minWidth + constraints.ipadx))) {
            diffx = r.width - (constraints.minWidth + constraints.ipadx);
            r.width = constraints.minWidth + constraints.ipadx;
        }

        diffy = 0;
        if ((constraints.fill != GridBagConstraints.VERTICAL &&
             constraints.fill != GridBagConstraints.BOTH)
            && (r.height > (constraints.minHeight + constraints.ipady))) {
            diffy = r.height - (constraints.minHeight + constraints.ipady);
            r.height = constraints.minHeight + constraints.ipady;
        }

        switch (constraints.anchor) {
          case GridBagConstraints.BASELINE:
              r.x += diffx/2;
              alignOnBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.BASELINE_LEADING:
              if (rightToLeft) {
                  r.x += diffx;
              }
              alignOnBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.BASELINE_TRAILING:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              alignOnBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.ABOVE_BASELINE:
              r.x += diffx/2;
              alignAboveBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.ABOVE_BASELINE_LEADING:
              if (rightToLeft) {
                  r.x += diffx;
              }
              alignAboveBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.ABOVE_BASELINE_TRAILING:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              alignAboveBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.BELOW_BASELINE:
              r.x += diffx/2;
              alignBelowBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.BELOW_BASELINE_LEADING:
              if (rightToLeft) {
                  r.x += diffx;
              }
              alignBelowBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.BELOW_BASELINE_TRAILING:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              alignBelowBaseline(constraints, r, cellY, cellHeight);
              break;
          case GridBagConstraints.CENTER:
              r.x += diffx/2;
              r.y += diffy/2;
              break;
          case GridBagConstraints.PAGE_START:
          case GridBagConstraints.NORTH:
              r.x += diffx/2;
              break;
          case GridBagConstraints.NORTHEAST:
              r.x += diffx;
              break;
          case GridBagConstraints.EAST:
              r.x += diffx;
              r.y += diffy/2;
              break;
          case GridBagConstraints.SOUTHEAST:
              r.x += diffx;
              r.y += diffy;
              break;
          case GridBagConstraints.PAGE_END:
          case GridBagConstraints.SOUTH:
              r.x += diffx/2;
              r.y += diffy;
              break;
          case GridBagConstraints.SOUTHWEST:
              r.y += diffy;
              break;
          case GridBagConstraints.WEST:
              r.y += diffy/2;
              break;
          case GridBagConstraints.NORTHWEST:
              break;
          case GridBagConstraints.LINE_START:
              if (rightToLeft) {
                  r.x += diffx;
              }
              r.y += diffy/2;
              break;
          case GridBagConstraints.LINE_END:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              r.y += diffy/2;
              break;
          case GridBagConstraints.FIRST_LINE_START:
              if (rightToLeft) {
                  r.x += diffx;
              }
              break;
          case GridBagConstraints.FIRST_LINE_END:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              break;
          case GridBagConstraints.LAST_LINE_START:
              if (rightToLeft) {
                  r.x += diffx;
              }
              r.y += diffy;
              break;
          case GridBagConstraints.LAST_LINE_END:
              if (!rightToLeft) {
                  r.x += diffx;
              }
              r.y += diffy;
              break;
          default:
              throw new IllegalArgumentException("illegal anchor value");
        }
    }

    /**
     * Positions on the baseline.
     *
     * <p>
     *  基线上的位置
     * 
     * 
     * @param cellY the location of the row, does not include insets
     * @param cellHeight the height of the row, does not take into account
     *        insets
     * @param r available bounds for the component, is padded by insets and
     *        ipady
     */
    private void alignOnBaseline(GridBagConstraints cons, Rectangle r,
                                 int cellY, int cellHeight) {
        if (cons.ascent >= 0) {
            if (cons.baselineResizeBehavior == Component.
                    BaselineResizeBehavior.CONSTANT_DESCENT) {
                // Anchor to the bottom.
                // Baseline is at (cellY + cellHeight - maxDescent).
                // Bottom of component (maxY) is at baseline + descent
                // of component. We need to subtract the bottom inset here
                // as the descent in the constraints object includes the
                // bottom inset.
                int maxY = cellY + cellHeight -
                      layoutInfo.maxDescent[cons.tempY + cons.tempHeight - 1] +
                      cons.descent - cons.insets.bottom;
                if (!cons.isVerticallyResizable()) {
                    // Component not resizable, calculate y location
                    // from maxY - height.
                    r.y = maxY - cons.minHeight;
                    r.height = cons.minHeight;
                } else {
                    // Component is resizable. As brb is constant descent,
                    // can expand component to fill region above baseline.
                    // Subtract out the top inset so that components insets
                    // are honored.
                    r.height = maxY - cellY - cons.insets.top;
                }
            }
            else {
                // BRB is not constant_descent
                int baseline; // baseline for the row, relative to cellY
                // Component baseline, includes insets.top
                int ascent = cons.ascent;
                if (layoutInfo.hasConstantDescent(cons.tempY)) {
                    // Mixed ascent/descent in same row, calculate position
                    // off maxDescent
                    baseline = cellHeight - layoutInfo.maxDescent[cons.tempY];
                }
                else {
                    // Only ascents/unknown in this row, anchor to top
                    baseline = layoutInfo.maxAscent[cons.tempY];
                }
                if (cons.baselineResizeBehavior == Component.
                        BaselineResizeBehavior.OTHER) {
                    // BRB is other, which means we can only determine
                    // the baseline by asking for it again giving the
                    // size we plan on using for the component.
                    boolean fits = false;
                    ascent = componentAdjusting.getBaseline(r.width, r.height);
                    if (ascent >= 0) {
                        // Component has a baseline, pad with top inset
                        // (this follows from calculateBaseline which
                        // does the same).
                        ascent += cons.insets.top;
                    }
                    if (ascent >= 0 && ascent <= baseline) {
                        // Components baseline fits within rows baseline.
                        // Make sure the descent fits within the space as well.
                        if (baseline + (r.height - ascent - cons.insets.top) <=
                                cellHeight - cons.insets.bottom) {
                            // It fits, we're good.
                            fits = true;
                        }
                        else if (cons.isVerticallyResizable()) {
                            // Doesn't fit, but it's resizable.  Try
                            // again assuming we'll get ascent again.
                            int ascent2 = componentAdjusting.getBaseline(
                                    r.width, cellHeight - cons.insets.bottom -
                                    baseline + ascent);
                            if (ascent2 >= 0) {
                                ascent2 += cons.insets.top;
                            }
                            if (ascent2 >= 0 && ascent2 <= ascent) {
                                // It'll fit
                                r.height = cellHeight - cons.insets.bottom -
                                        baseline + ascent;
                                ascent = ascent2;
                                fits = true;
                            }
                        }
                    }
                    if (!fits) {
                        // Doesn't fit, use min size and original ascent
                        ascent = cons.ascent;
                        r.width = cons.minWidth;
                        r.height = cons.minHeight;
                    }
                }
                // Reset the components y location based on
                // components ascent and baseline for row. Because ascent
                // includes the baseline
                r.y = cellY + baseline - ascent + cons.insets.top;
                if (cons.isVerticallyResizable()) {
                    switch(cons.baselineResizeBehavior) {
                    case CONSTANT_ASCENT:
                        r.height = Math.max(cons.minHeight,cellY + cellHeight -
                                            r.y - cons.insets.bottom);
                        break;
                    case CENTER_OFFSET:
                        {
                            int upper = r.y - cellY - cons.insets.top;
                            int lower = cellY + cellHeight - r.y -
                                cons.minHeight - cons.insets.bottom;
                            int delta = Math.min(upper, lower);
                            delta += delta;
                            if (delta > 0 &&
                                (cons.minHeight + cons.centerPadding +
                                 delta) / 2 + cons.centerOffset != baseline) {
                                // Off by 1
                                delta--;
                            }
                            r.height = cons.minHeight + delta;
                            r.y = cellY + baseline -
                                (r.height + cons.centerPadding) / 2 -
                                cons.centerOffset;
                        }
                        break;
                    case OTHER:
                        // Handled above
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        else {
            centerVertically(cons, r, cellHeight);
        }
    }

    /**
     * Positions the specified component above the baseline. That is
     * the bottom edge of the component will be aligned along the baseline.
     * If the row does not have a baseline, this centers the component.
     * <p>
     *  将指定的组件定位在基线上方即组件的底部边缘将沿着基线对齐如果行没有基线,则将组件
     * 
     */
    private void alignAboveBaseline(GridBagConstraints cons, Rectangle r,
                                    int cellY, int cellHeight) {
        if (layoutInfo.hasBaseline(cons.tempY)) {
            int maxY; // Baseline for the row
            if (layoutInfo.hasConstantDescent(cons.tempY)) {
                // Prefer descent
                maxY = cellY + cellHeight - layoutInfo.maxDescent[cons.tempY];
            }
            else {
                // Prefer ascent
                maxY = cellY + layoutInfo.maxAscent[cons.tempY];
            }
            if (cons.isVerticallyResizable()) {
                // Component is resizable. Top edge is offset by top
                // inset, bottom edge on baseline.
                r.y = cellY + cons.insets.top;
                r.height = maxY - r.y;
            }
            else {
                // Not resizable.
                r.height = cons.minHeight + cons.ipady;
                r.y = maxY - r.height;
            }
        }
        else {
            centerVertically(cons, r, cellHeight);
        }
    }

    /**
     * Positions below the baseline.
     * <p>
     *  基线以下的位置
     * 
     */
    private void alignBelowBaseline(GridBagConstraints cons, Rectangle r,
                                    int cellY, int cellHeight) {
        if (layoutInfo.hasBaseline(cons.tempY)) {
            if (layoutInfo.hasConstantDescent(cons.tempY)) {
                // Prefer descent
                r.y = cellY + cellHeight - layoutInfo.maxDescent[cons.tempY];
            }
            else {
                // Prefer ascent
                r.y = cellY + layoutInfo.maxAscent[cons.tempY];
            }
            if (cons.isVerticallyResizable()) {
                r.height = cellY + cellHeight - r.y - cons.insets.bottom;
            }
        }
        else {
            centerVertically(cons, r, cellHeight);
        }
    }

    private void centerVertically(GridBagConstraints cons, Rectangle r,
                                  int cellHeight) {
        if (!cons.isVerticallyResizable()) {
            r.y += Math.max(0, (cellHeight - cons.insets.top -
                                cons.insets.bottom - cons.minHeight -
                                cons.ipady) / 2);
        }
    }

    /**
     * Figures out the minimum size of the
     * master based on the information from <code>getLayoutInfo</code>.
     * This method should only be used internally by
     * <code>GridBagLayout</code>.
     *
     * <p>
     * 基于来自<code> getLayoutInfo </code>的信息计算出主机的最小大小。此方法应该仅由<code> GridBagLayout </code>
     * 
     * 
     * @param parent the layout container
     * @param info the layout info for this parent
     * @return a <code>Dimension</code> object containing the
     *   minimum size
     * @since 1.4
     */
    protected Dimension getMinSize(Container parent, GridBagLayoutInfo info) {
        return GetMinSize(parent, info);
    }

    /**
     * This method is obsolete and supplied for backwards
     * compatibility only; new code should call {@link
     * #getMinSize(java.awt.Container, GridBagLayoutInfo) getMinSize} instead.
     * This method is the same as <code>getMinSize</code>;
     * refer to <code>getMinSize</code> for details on parameters
     * and return value.
     * <p>
     *  此方法已过时,仅供向后兼容;新代码应该调用{@link #getMinSize(javaawtContainer,GridBagLayoutInfo)getMinSize},而这个方法与<code> 
     * getMinSize </code>相同;有关参数和返回值的详细信息,请参阅<code> getMinSize </code>。
     * 
     */
    protected Dimension GetMinSize(Container parent, GridBagLayoutInfo info) {
        Dimension d = new Dimension();
        int i, t;
        Insets insets = parent.getInsets();

        t = 0;
        for(i = 0; i < info.width; i++)
            t += info.minWidth[i];
        d.width = t + insets.left + insets.right;

        t = 0;
        for(i = 0; i < info.height; i++)
            t += info.minHeight[i];
        d.height = t + insets.top + insets.bottom;

        return d;
    }

    transient boolean rightToLeft = false;

    /**
     * Lays out the grid.
     * This method should only be used internally by
     * <code>GridBagLayout</code>.
     *
     * <p>
     *  放出网格此方法应仅由<code> GridBagLayout </code>在内部使用
     * 
     * 
     * @param parent the layout container
     * @since 1.4
     */
    protected void arrangeGrid(Container parent) {
        ArrangeGrid(parent);
    }

    /**
     * This method is obsolete and supplied for backwards
     * compatibility only; new code should call {@link
     * #arrangeGrid(Container) arrangeGrid} instead.
     * This method is the same as <code>arrangeGrid</code>;
     * refer to <code>arrangeGrid</code> for details on the
     * parameter.
     * <p>
     * 此方法已过时,仅供向后兼容;新代码应该调用{@link #arrangeGrid(Container)arrangeGrid},而这个方法与<code> arrangeGrid </code>相同;有关
     * 参数的详细信息,请参阅<code> arrangeGrid </code>。
     * 
     */
    protected void ArrangeGrid(Container parent) {
        Component comp;
        int compindex;
        GridBagConstraints constraints;
        Insets insets = parent.getInsets();
        Component components[] = parent.getComponents();
        Dimension d;
        Rectangle r = new Rectangle();
        int i, diffw, diffh;
        double weight;
        GridBagLayoutInfo info;

        rightToLeft = !parent.getComponentOrientation().isLeftToRight();

        /*
         * If the parent has no slaves anymore, then don't do anything
         * at all:  just leave the parent's size as-is.
         * <p>
         *  如果父节点没有奴隶,那么不要做任何事情：只要保留父节点的大小
         * 
         */
        if (components.length == 0 &&
            (columnWidths == null || columnWidths.length == 0) &&
            (rowHeights == null || rowHeights.length == 0)) {
            return;
        }

        /*
         * Pass #1: scan all the slaves to figure out the total amount
         * of space needed.
         * <p>
         *  通过#1：扫描所有从机,找出所需的总空间量
         * 
         */

        info = getLayoutInfo(parent, PREFERREDSIZE);
        d = getMinSize(parent, info);

        if (parent.width < d.width || parent.height < d.height) {
            info = getLayoutInfo(parent, MINSIZE);
            d = getMinSize(parent, info);
        }

        layoutInfo = info;
        r.width = d.width;
        r.height = d.height;

        /*
         * DEBUG
         *
         * DumpLayoutInfo(info);
         * for (compindex = 0 ; compindex < components.length ; compindex++) {
         * comp = components[compindex];
         * if (!comp.isVisible())
         *      continue;
         * constraints = lookupConstraints(comp);
         * DumpConstraints(constraints);
         * }
         * System.out.println("minSize " + r.width + " " + r.height);
         * <p>
         *  调试
         * 
         *  DumpLayoutInfo(info); for(compindex = 0; compindex <componentslength; compindex ++){comp = components [compindex]; if(！compisVisible())continue; constraints = lookupConstraints(comp); DumpConstraints(约束); } Systemoutprintln("minSize"+ rwidth +""+ rheight);。
         * 
         */

        /*
         * If the current dimensions of the window don't match the desired
         * dimensions, then adjust the minWidth and minHeight arrays
         * according to the weights.
         * <p>
         * 如果窗口的当前尺寸与所需的尺寸不匹配,则根据权重调整minWidth和minHeight数组
         * 
         */

        diffw = parent.width - r.width;
        if (diffw != 0) {
            weight = 0.0;
            for (i = 0; i < info.width; i++)
                weight += info.weightX[i];
            if (weight > 0.0) {
                for (i = 0; i < info.width; i++) {
                    int dx = (int)(( ((double)diffw) * info.weightX[i]) / weight);
                    info.minWidth[i] += dx;
                    r.width += dx;
                    if (info.minWidth[i] < 0) {
                        r.width -= info.minWidth[i];
                        info.minWidth[i] = 0;
                    }
                }
            }
            diffw = parent.width - r.width;
        }

        else {
            diffw = 0;
        }

        diffh = parent.height - r.height;
        if (diffh != 0) {
            weight = 0.0;
            for (i = 0; i < info.height; i++)
                weight += info.weightY[i];
            if (weight > 0.0) {
                for (i = 0; i < info.height; i++) {
                    int dy = (int)(( ((double)diffh) * info.weightY[i]) / weight);
                    info.minHeight[i] += dy;
                    r.height += dy;
                    if (info.minHeight[i] < 0) {
                        r.height -= info.minHeight[i];
                        info.minHeight[i] = 0;
                    }
                }
            }
            diffh = parent.height - r.height;
        }

        else {
            diffh = 0;
        }

        /*
         * DEBUG
         *
         * System.out.println("Re-adjusted:");
         * DumpLayoutInfo(info);
         * <p>
         *  调试
         * 
         *  Systemoutprintln("重新调整："); DumpLayoutInfo(info);
         * 
         */

        /*
         * Now do the actual layout of the slaves using the layout information
         * that has been collected.
         * <p>
         *  现在使用收集的布局信息做从站的实际布局
         * 
         */

        info.startx = diffw/2 + insets.left;
        info.starty = diffh/2 + insets.top;

        for (compindex = 0 ; compindex < components.length ; compindex++) {
            comp = components[compindex];
            if (!comp.isVisible()){
                continue;
            }
            constraints = lookupConstraints(comp);

            if (!rightToLeft) {
                r.x = info.startx;
                for(i = 0; i < constraints.tempX; i++)
                    r.x += info.minWidth[i];
            } else {
                r.x = parent.width - (diffw/2 + insets.right);
                for(i = 0; i < constraints.tempX; i++)
                    r.x -= info.minWidth[i];
            }

            r.y = info.starty;
            for(i = 0; i < constraints.tempY; i++)
                r.y += info.minHeight[i];

            r.width = 0;
            for(i = constraints.tempX;
                i < (constraints.tempX + constraints.tempWidth);
                i++) {
                r.width += info.minWidth[i];
            }

            r.height = 0;
            for(i = constraints.tempY;
                i < (constraints.tempY + constraints.tempHeight);
                i++) {
                r.height += info.minHeight[i];
            }

            componentAdjusting = comp;
            adjustForGravity(constraints, r);

            /* fix for 4408108 - components were being created outside of the container */
            /* fix for 4969409 "-" replaced by "+"  */
            if (r.x < 0) {
                r.width += r.x;
                r.x = 0;
            }

            if (r.y < 0) {
                r.height += r.y;
                r.y = 0;
            }

            /*
             * If the window is too small to be interesting then
             * unmap it.  Otherwise configure it and then make sure
             * it's mapped.
             * <p>
             *  如果窗口太小,有趣,然后unmap它否则配置它,然后确保它映射
             */

            if ((r.width <= 0) || (r.height <= 0)) {
                comp.setBounds(0, 0, 0, 0);
            }
            else {
                if (comp.x != r.x || comp.y != r.y ||
                    comp.width != r.width || comp.height != r.height) {
                    comp.setBounds(r.x, r.y, r.width, r.height);
                }
            }
        }
    }

    // Added for serial backwards compatibility (4348425)
    static final long serialVersionUID = 8838754796412211005L;
}
