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

import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.accessibility.*;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;

import java.io.ObjectOutputStream;
import java.io.IOException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;

/**
 * Provides a scrollable view of a lightweight component.
 * A <code>JScrollPane</code> manages a viewport, optional
 * vertical and horizontal scroll bars, and optional row and
 * column heading viewports.
 * You can find task-oriented documentation of <code>JScrollPane</code> in
 *  <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html">How to Use Scroll Panes</a>,
 * a section in <em>The Java Tutorial</em>.  Note that
 * <code>JScrollPane</code> does not support heavyweight components.
 *
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 *    <TR>
 *    <TD ALIGN="CENTER">
 *      <P STYLE="TEXT-ALIGN:CENTER"><IMG SRC="doc-files/JScrollPane-1.gif"
 *      alt="The following text describes this image."
 *      WIDTH="256" HEIGHT="248" STYLE="FLOAT:BOTTOM; BORDER:0px">
 *    </TD>
 *    </TR>
 * </TABLE>
 * The <code>JViewport</code> provides a window,
 * or &quot;viewport&quot; onto a data
 * source -- for example, a text file. That data source is the
 * &quot;scrollable client&quot; (aka data model) displayed by the
 * <code>JViewport</code> view.
 * A <code>JScrollPane</code> basically consists of <code>JScrollBar</code>s,
 * a <code>JViewport</code>, and the wiring between them,
 * as shown in the diagram at right.
 * <p>
 * In addition to the scroll bars and viewport,
 * a <code>JScrollPane</code> can have a
 * column header and a row header. Each of these is a
 * <code>JViewport</code> object that
 * you specify with <code>setRowHeaderView</code>,
 * and <code>setColumnHeaderView</code>.
 * The column header viewport automatically scrolls left and right, tracking
 * the left-right scrolling of the main viewport.
 * (It never scrolls vertically, however.)
 * The row header acts in a similar fashion.
 * <p>
 * Where two scroll bars meet, the row header meets the column header,
 * or a scroll bar meets one of the headers, both components stop short
 * of the corner, leaving a rectangular space which is, by default, empty.
 * These spaces can potentially exist in any number of the four corners.
 * In the previous diagram, the top right space is present and identified
 * by the label "corner component".
 * <p>
 * Any number of these empty spaces can be replaced by using the
 * <code>setCorner</code> method to add a component to a particular corner.
 * (Note: The same component cannot be added to multiple corners.)
 * This is useful if there's
 * some extra decoration or function you'd like to add to the scroll pane.
 * The size of each corner component is entirely determined by the size of the
 * headers and/or scroll bars that surround it.
 * <p>
 * A corner component will only be visible if there is an empty space in that
 * corner for it to exist in. For example, consider a component set into the
 * top right corner of a scroll pane with a column header. If the scroll pane's
 * vertical scrollbar is not present, perhaps because the view component hasn't
 * grown large enough to require it, then the corner component will not be
 * shown (since there is no empty space in that corner created by the meeting
 * of the header and vertical scroll bar). Forcing the scroll bar to always be
 * shown, using
 * <code>setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS)</code>,
 * will ensure that the space for the corner component always exists.
 * <p>
 * To add a border around the main viewport,
 * you can use <code>setViewportBorder</code>.
 * (Of course, you can also add a border around the whole scroll pane using
 * <code>setBorder</code>.)
 * <p>
 * A common operation to want to do is to set the background color that will
 * be used if the main viewport view is smaller than the viewport, or is
 * not opaque. This can be accomplished by setting the background color
 * of the viewport, via <code>scrollPane.getViewport().setBackground()</code>.
 * The reason for setting the color of the viewport and not the scrollpane
 * is that by default <code>JViewport</code> is opaque
 * which, among other things, means it will completely fill
 * in its background using its background color.  Therefore when
 * <code>JScrollPane</code> draws its background the viewport will
 * usually draw over it.
 * <p>
 * By default <code>JScrollPane</code> uses <code>ScrollPaneLayout</code>
 * to handle the layout of its child Components. <code>ScrollPaneLayout</code>
 * determines the size to make the viewport view in one of two ways:
 * <ol>
 *   <li>If the view implements <code>Scrollable</code>
 *       a combination of <code>getPreferredScrollableViewportSize</code>,
 *       <code>getScrollableTracksViewportWidth</code> and
 *       <code>getScrollableTracksViewportHeight</code>is used, otherwise
 *   <li><code>getPreferredSize</code> is used.
 * </ol>
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
 *  提供轻量级组件的可滚动视图。 <code> JScrollPane </code>管理视口,可选的垂直和水平滚动条以及可选的行和列标题视口。
 * 您可以在<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html">如何使用滚动窗格中找到<code>
 *  JScrollPane </code>的面向任务的文档</a>,<em> Java教程</em>中的一个部分。
 *  提供轻量级组件的可滚动视图。 <code> JScrollPane </code>管理视口,可选的垂直和水平滚动条以及可选的行和列标题视口。
 * 请注意,<code> JScrollPane </code>不支持重量级组件。
 * 
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 * <TR>
 * <TD ALIGN="CENTER">
 *  <P STYLE ="TEXT-ALIGN：CENTER"> <IMG SRC ="doc-files / JScrollPane-1.gif"alt ="以下文字描述此图片。
 * WIDTH="256" HEIGHT="248" STYLE="FLOAT:BOTTOM; BORDER:0px">
 * </TD>
 * </TR>
 * </TABLE>
 *  <code> JViewport </code>提供了一个窗口或"视口"到数据源 - 例如,文本文件。
 * 该数据源是"可滚动客户端" (aka数据模型)由<code> JViewport </code>视图显示。
 *  <code> JScrollPane </code>基本上由<code> JScrollBar </code>,<code> JViewport </code>和它们之间的接线组成,如右图所示。
 * <p>
 * 除了滚动条和视口之外,<code> JScrollPane </code>可以有一个列标题和一个行标题。
 * 每个都是一个<code> JViewport </code>对象,你用<code> setRowHeaderView </code>和<code> setColumnHeaderView </code>
 * 指定。
 * 除了滚动条和视口之外,<code> JScrollPane </code>可以有一个列标题和一个行标题。列标题视口自动向左和向右滚动,跟踪主视口的左右滚动。 (但它不会垂直滚动。
 * )行标题以类似的方式工作。
 * <p>
 *  在两个滚动条相遇处,行标题符合列标题,或者滚动条满足其中一个标题,两个组件都停止在拐角处,留下一个矩形空间,默认情况下为空。这些空间可以潜在地存在于任何数量的四个角。
 * 在上图中,右上角空间存在并由标签"角部件"标识。
 * <p>
 *  可以使用<code> setCorner </code>方法将某个组件添加到特定角落来替换任意数量的这些空格。 (注意：相同的组件不能添加到多个角。
 * )如果有一些额外的装饰或函数要添加到滚动窗格,这是有用的。每个角部件的尺寸完全由围绕其的头部和/或滚动条的尺寸确定。
 * <p>
 * 只有在角落中存在空的空间时,角组件才会可见。例如,将组件设置到具有列标题的滚动窗格的右上角。
 * 如果滚动窗格的垂直滚动条不存在,也许是因为视图组件没有增长得足够大以至于不需要它,则角组件将不会显示(因为在该角落的会议中没有创建空白空间和垂直滚动条)。
 * 强制滚动条始终显示,使用<code> setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_​​ALWAYS)</code>,将确保角组件的空间始终存在。
 * <p>
 *  要在主视口周围添加边框,可以使用<code> setViewportBorder </code>。 (当然,您也可以使用<code> setBorder </code>在整个滚动窗格周围添加边框。
 * )。
 * <p>
 *  要执行的常见操作是设置在主视口视图小于视口时使用的背景颜色,或不透明的背景颜色。这可以通过设置视口的背景颜色,通过<code> scrollPane.getViewport()。
 * setBackground()</code>来实现。
 * 设置视口的颜色而不是滚动条的原因是默认情况下<code> JViewport </code>是不透明的,除了别的以外,这意味着它将使用它的背景颜色完全填充它的背景。
 * 因此,当<code> JScrollPane </code>绘制其背景时,视口通常会在其上绘制。
 * <p>
 * 默认情况下,<code> JScrollPane </code>使用<code> ScrollPaneLayout </code>来处理其子组件的布局。
 *  <code> ScrollPaneLayout </code>以两种方式之一确定要创建视口视图的大小：。
 * <ol>
 *  <li>如果视图实现<code> Scrollable </code>,则使用<code> getPreferredScrollableViewportSize </code>,<code> getS
 * crollableTracksViewportWidth </code>和<code> getScrollableTracksViewportHeight </code> > <code> getPre
 * ferredSize </code>被使用。
 * </ol>
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JScrollBar
 * @see JViewport
 * @see ScrollPaneLayout
 * @see Scrollable
 * @see Component#getPreferredSize
 * @see #setViewportView
 * @see #setRowHeaderView
 * @see #setColumnHeaderView
 * @see #setCorner
 * @see #setViewportBorder
 *
 * @beaninfo
 *     attribute: isContainer true
 *     attribute: containerDelegate getViewport
 *   description: A specialized container that manages a viewport, optional scrollbars and headers
 *
 * @author Hans Muller
 */
public class JScrollPane extends JComponent implements ScrollPaneConstants, Accessible
{
    private Border viewportBorder;

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ScrollPaneUI";

    /**
     * The display policy for the vertical scrollbar.
     * The default is
     * <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>.
     * <p>
     *  垂直滚动条的显示策略。默认值为<code> ScrollPaneConstants.VERTICAL_SCROLLBAR_​​AS_NEEDED </code>。
     * 
     * 
     * @see #setVerticalScrollBarPolicy
     */
    protected int verticalScrollBarPolicy = VERTICAL_SCROLLBAR_AS_NEEDED;


    /**
     * The display policy for the horizontal scrollbar.
     * The default is
     * <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code>.
     * <p>
     *  水平滚动条的显示策略。默认值为<code> ScrollPaneConstants.HORIZONTAL_SCROLLBAR_​​AS_NEEDED </code>。
     * 
     * 
     * @see #setHorizontalScrollBarPolicy
     */
    protected int horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED;


    /**
     * The scrollpane's viewport child.  Default is an empty
     * <code>JViewport</code>.
     * <p>
     *  滚动条的视口孩子。默认是一个空的<code> JViewport </code>。
     * 
     * 
     * @see #setViewport
     */
    protected JViewport viewport;


    /**
     * The scrollpane's vertical scrollbar child.
     * Default is a <code>JScrollBar</code>.
     * <p>
     *  滚动条的垂直滚动条子。默认是一个<code> JScrollBar </code>。
     * 
     * 
     * @see #setVerticalScrollBar
     */
    protected JScrollBar verticalScrollBar;


    /**
     * The scrollpane's horizontal scrollbar child.
     * Default is a <code>JScrollBar</code>.
     * <p>
     * 滚动条的水平滚动条子。默认是一个<code> JScrollBar </code>。
     * 
     * 
     * @see #setHorizontalScrollBar
     */
    protected JScrollBar horizontalScrollBar;


    /**
     * The row header child.  Default is <code>null</code>.
     * <p>
     *  行标题子。默认为<code> null </code>。
     * 
     * 
     * @see #setRowHeader
     */
    protected JViewport rowHeader;


    /**
     * The column header child.  Default is <code>null</code>.
     * <p>
     *  列标题子元素。默认为<code> null </code>。
     * 
     * 
     * @see #setColumnHeader
     */
    protected JViewport columnHeader;


    /**
     * The component to display in the lower left corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在左下角的组件。默认为<code> null </code>。
     * 
     * 
     * @see #setCorner
     */
    protected Component lowerLeft;


    /**
     * The component to display in the lower right corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在右下角的组件。默认为<code> null </code>。
     * 
     * 
     * @see #setCorner
     */
    protected Component lowerRight;


    /**
     * The component to display in the upper left corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在左上角的组件。默认为<code> null </code>。
     * 
     * 
     * @see #setCorner
     */
    protected Component upperLeft;


    /**
     * The component to display in the upper right corner.
     * Default is <code>null</code>.
     * <p>
     *  要在右上角显示的组件。默认为<code> null </code>。
     * 
     * 
     * @see #setCorner
     */
    protected Component upperRight;

    /*
     * State flag for mouse wheel scrolling
     * <p>
     *  鼠标滚轮滚动的状态标志
     * 
     */
    private boolean wheelScrollState = true;

    /**
     * Creates a <code>JScrollPane</code> that displays the view
     * component in a viewport
     * whose view position can be controlled with a pair of scrollbars.
     * The scrollbar policies specify when the scrollbars are displayed,
     * For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code>
     * then the vertical scrollbar only appears if the view doesn't fit
     * vertically. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * <p>
     *  创建在视口中显示视图组件的<code> JScrollPane </code>,该视口的视图位置可以使用一对滚动条进行控制。滚动条策略指定何时显示滚动条。
     * 例如,如果<code> vsbPolicy </code> </code>是<code> VERTICAL_SCROLLBAR_​​AS_NEEDED </code>,则只有当视图不垂直时才会出现垂直滚
     * 动条。
     *  创建在视口中显示视图组件的<code> JScrollPane </code>,该视口的视图位置可以使用一对滚动条进行控制。滚动条策略指定何时显示滚动条。
     * 可用的政策设置列在{@link #setVerticalScrollBarPolicy}和{@link #setHorizo​​ntalScrollBarPolicy}。
     * 
     * 
     * @see #setViewportView
     *
     * @param view the component to display in the scrollpanes viewport
     * @param vsbPolicy an integer that specifies the vertical
     *          scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *          scrollbar policy
     */
    public JScrollPane(Component view, int vsbPolicy, int hsbPolicy)
    {
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(vsbPolicy);
        setHorizontalScrollBarPolicy(hsbPolicy);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (view != null) {
            setViewportView(view);
        }
        setUIProperty("opaque",true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }


    /**
     * Creates a <code>JScrollPane</code> that displays the
     * contents of the specified
     * component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     *
     * <p>
     *  创建一个显示指定组件内容的<code> JScrollPane </code>,当组件的内容大于视图时,水平和垂直滚动条都会显示。
     * 
     * 
     * @see #setViewportView
     * @param view the component to display in the scrollpane's viewport
     */
    public JScrollPane(Component view) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * with specified
     * scrollbar policies. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     *
     * <p>
     *  使用指定的滚动条策略创建一个空(无视口视图)<code> JScrollPane </code>。
     * 可用的政策设置列在{@link #setVerticalScrollBarPolicy}和{@link #setHorizo​​ntalScrollBarPolicy}。
     * 
     * 
     * @see #setViewportView
     *
     * @param vsbPolicy an integer that specifies the vertical
     *          scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal
     *          scrollbar policy
     */
    public JScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }


    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code>
     * where both horizontal and vertical scrollbars appear when needed.
     * <p>
     * 创建一个空(无视口视图)<code> JScrollPane </code>其中水平和垂直滚动条在需要时显示。
     * 
     */
    public JScrollPane() {
        this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    /**
     * Returns the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @return the <code>ScrollPaneUI</code> object that renders this
     *                          component
     * @see #setUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public ScrollPaneUI getUI() {
        return (ScrollPaneUI)ui;
    }


    /**
     * Sets the <code>ScrollPaneUI</code> object that provides the
     * look and feel (L&amp;F) for this component.
     *
     * <p>
     *  设置为此组件提供外观和感觉(L&amp; F)的<code> ScrollPaneUI </code>对象。
     * 
     * 
     * @param ui the <code>ScrollPaneUI</code> L&amp;F object
     * @see #getUI
     */
    public void setUI(ScrollPaneUI ui) {
        super.setUI(ui);
    }


    /**
     * Replaces the current <code>ScrollPaneUI</code> object with a version
     * from the current default look and feel.
     * To be called when the default look and feel changes.
     *
     * <p>
     *  将当前的<code> ScrollPaneUI </code>对象替换为当前默认外观的版本。当默认的外观和感觉改变时被调用。
     * 
     * 
     * @see JComponent#updateUI
     * @see UIManager#getUI
     */
    public void updateUI() {
        setUI((ScrollPaneUI)UIManager.getUI(this));
    }


    /**
     * Returns the suffix used to construct the name of the L&amp;F class used to
     * render this component.
     *
     * <p>
     *  返回用于构造用于渲染此组件的L&amp; F类的名称的后缀。
     * 
     * 
     * @return the string "ScrollPaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     *
     * @beaninfo
     *    hidden: true
     */
    public String getUIClassID() {
        return uiClassID;
    }



    /**
     * Sets the layout manager for this <code>JScrollPane</code>.
     * This method overrides <code>setLayout</code> in
     * <code>java.awt.Container</code> to ensure that only
     * <code>LayoutManager</code>s which
     * are subclasses of <code>ScrollPaneLayout</code> can be used in a
     * <code>JScrollPane</code>. If <code>layout</code> is non-null, this
     * will invoke <code>syncWithScrollPane</code> on it.
     *
     * <p>
     *  为此<code> JScrollPane </code>设置布局管理器。
     * 此方法覆盖<code> java.awt.Container </code>中的<code> setLayout </code>,以确保只有<code> ScrollPaneLayout </code>
     * 的子类的<code> LayoutManager </code>用在<code> JScrollPane </code>中。
     *  为此<code> JScrollPane </code>设置布局管理器。
     * 如果<code> layout </code>是非空的,这将调用<code> syncWithScrollPane </code>就可以了。
     * 
     * 
     * @param layout the specified layout manager
     * @exception ClassCastException if layout is not a
     *                  <code>ScrollPaneLayout</code>
     * @see java.awt.Container#getLayout
     * @see java.awt.Container#setLayout
     *
     * @beaninfo
     *    hidden: true
     */
    public void setLayout(LayoutManager layout) {
        if (layout instanceof ScrollPaneLayout) {
            super.setLayout(layout);
            ((ScrollPaneLayout)layout).syncWithScrollPane(this);
        }
        else if (layout == null) {
            super.setLayout(layout);
        }
        else {
            String s = "layout of JScrollPane must be a ScrollPaneLayout";
            throw new ClassCastException(s);
        }
    }

    /**
     * Overridden to return true so that any calls to <code>revalidate</code>
     * on any descendants of this <code>JScrollPane</code> will cause the
     * entire tree beginning with this <code>JScrollPane</code> to be
     * validated.
     *
     * <p>
     *  重写为返回true,以便对此<code> JScrollPane </code>的任何后代进行<code> revalidate </code>的任何调用将导致以此代码<code> JScrollPa
     * ne </code>开始的整个树被验证。
     * 
     * 
     * @return true
     * @see java.awt.Container#validate
     * @see JComponent#revalidate
     * @see JComponent#isValidateRoot
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
     * Returns the vertical scroll bar policy value.
     * <p>
     *  返回垂直滚动条策略值。
     * 
     * 
     * @return the <code>verticalScrollBarPolicy</code> property
     * @see #setVerticalScrollBarPolicy
     */
    public int getVerticalScrollBarPolicy() {
        return verticalScrollBarPolicy;
    }


    /**
     * Determines when the vertical scrollbar appears in the scrollpane.
     * Legal values are:
     * <ul>
     * <li><code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>
     * <li><code>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER</code>
     * <li><code>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS</code>
     * </ul>
     *
     * <p>
     *  确定垂直滚动条何时显示在滚动条中。法律价值包括：
     * <ul>
     *  <li> <code> ScrollPaneConstants.VERTICAL_SCROLLBAR_​​ALWAYS </code> ScrollPaneConstants.VERTICAL_SCR
     * OLLBAR_​​ALWAYS </code> ScrollPaneConstants.VERTICAL_SCROLLBAR_​​NEVER </code>。
     * </ul>
     * 
     * 
     * @param policy one of the three values listed above
     * @exception IllegalArgumentException if <code>policy</code>
     *                          is not one of the legal values shown above
     * @see #getVerticalScrollBarPolicy
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The scrollpane vertical scrollbar policy
     *        enum: VERTICAL_SCROLLBAR_AS_NEEDED ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
     *              VERTICAL_SCROLLBAR_NEVER ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
     *              VERTICAL_SCROLLBAR_ALWAYS ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
     */
    public void setVerticalScrollBarPolicy(int policy) {
        switch (policy) {
        case VERTICAL_SCROLLBAR_AS_NEEDED:
        case VERTICAL_SCROLLBAR_NEVER:
        case VERTICAL_SCROLLBAR_ALWAYS:
                break;
        default:
            throw new IllegalArgumentException("invalid verticalScrollBarPolicy");
        }
        int old = verticalScrollBarPolicy;
        verticalScrollBarPolicy = policy;
        firePropertyChange("verticalScrollBarPolicy", old, policy);
        revalidate();
        repaint();
    }


    /**
     * Returns the horizontal scroll bar policy value.
     * <p>
     * 返回水平滚动条策略值。
     * 
     * 
     * @return the <code>horizontalScrollBarPolicy</code> property
     * @see #setHorizontalScrollBarPolicy
     */
    public int getHorizontalScrollBarPolicy() {
        return horizontalScrollBarPolicy;
    }


    /**
     * Determines when the horizontal scrollbar appears in the scrollpane.
     * The options are:<ul>
     * <li><code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code>
     * <li><code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER</code>
     * <li><code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS</code>
     * </ul>
     *
     * <p>
     *  确定水平滚动条何时出现在滚动窗格中。
     * 选项为：<ul> <li> <code> ScrollPaneConstants.HORIZONTAL_SCROLLBAR_​​ALWAYS </code> ScrollPaneConstants.HO
     * RIZONTAL_SCROLLBAR_​​ALWAYS </code> ScrollPaneConstants.HORIZONTAL_SCROLLBAR_​​NEVER </code>。
     *  确定水平滚动条何时出现在滚动窗格中。
     * </ul>
     * 
     * 
     * @param policy one of the three values listed above
     * @exception IllegalArgumentException if <code>policy</code>
     *                          is not one of the legal values shown above
     * @see #getHorizontalScrollBarPolicy
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The scrollpane scrollbar policy
     *        enum: HORIZONTAL_SCROLLBAR_AS_NEEDED ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
     *              HORIZONTAL_SCROLLBAR_NEVER ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
     *              HORIZONTAL_SCROLLBAR_ALWAYS ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
     */
    public void setHorizontalScrollBarPolicy(int policy) {
        switch (policy) {
        case HORIZONTAL_SCROLLBAR_AS_NEEDED:
        case HORIZONTAL_SCROLLBAR_NEVER:
        case HORIZONTAL_SCROLLBAR_ALWAYS:
                break;
        default:
            throw new IllegalArgumentException("invalid horizontalScrollBarPolicy");
        }
        int old = horizontalScrollBarPolicy;
        horizontalScrollBarPolicy = policy;
        firePropertyChange("horizontalScrollBarPolicy", old, policy);
        revalidate();
        repaint();
    }


    /**
     * Returns the <code>Border</code> object that surrounds the viewport.
     *
     * <p>
     *  返回视口周围的<code> Border </code>对象。
     * 
     * 
     * @return the <code>viewportBorder</code> property
     * @see #setViewportBorder
     */
    public Border getViewportBorder() {
        return viewportBorder;
    }


    /**
     * Adds a border around the viewport.  Note that the border isn't
     * set on the viewport directly, <code>JViewport</code> doesn't support
     * the <code>JComponent</code> border property.
     * Similarly setting the <code>JScrollPane</code>s
     * viewport doesn't affect the <code>viewportBorder</code> property.
     * <p>
     * The default value of this property is computed by the look
     * and feel implementation.
     *
     * <p>
     *  在视口周围添加边框。请注意,边框不是直接在视口上设置的,<code> JViewport </code>不支持<code> JComponent </code> border属性。
     * 类似地,设置<code> JScrollPane </code>的视口不会影响<code> viewportBorder </code>属性。
     * <p>
     *  此属性的默认值由外观实现计算。
     * 
     * 
     * @param viewportBorder the border to be added
     * @see #getViewportBorder
     * @see #setViewport
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: The border around the viewport.
     */
    public void setViewportBorder(Border viewportBorder) {
        Border oldValue = this.viewportBorder;
        this.viewportBorder = viewportBorder;
        firePropertyChange("viewportBorder", oldValue, viewportBorder);
    }


    /**
     * Returns the bounds of the viewport's border.
     *
     * <p>
     *  返回视口边框的边界。
     * 
     * 
     * @return a <code>Rectangle</code> object specifying the viewport border
     */
    public Rectangle getViewportBorderBounds()
    {
        Rectangle borderR = new Rectangle(getSize());

        Insets insets = getInsets();
        borderR.x = insets.left;
        borderR.y = insets.top;
        borderR.width -= insets.left + insets.right;
        borderR.height -= insets.top + insets.bottom;

        boolean leftToRight = SwingUtilities.isLeftToRight(this);

        /* If there's a visible column header remove the space it
         * needs from the top of borderR.
         * <p>
         *  需要从borderR的顶部。
         * 
         */

        JViewport colHead = getColumnHeader();
        if ((colHead != null) && (colHead.isVisible())) {
            int colHeadHeight = colHead.getHeight();
            borderR.y += colHeadHeight;
            borderR.height -= colHeadHeight;
        }

        /* If there's a visible row header remove the space it needs
         * from the left of borderR.
         * <p>
         *  从borderR的左边。
         * 
         */

        JViewport rowHead = getRowHeader();
        if ((rowHead != null) && (rowHead.isVisible())) {
            int rowHeadWidth = rowHead.getWidth();
            if ( leftToRight ) {
                borderR.x += rowHeadWidth;
            }
            borderR.width -= rowHeadWidth;
        }

        /* If there's a visible vertical scrollbar remove the space it needs
         * from the width of borderR.
         * <p>
         *  从borderR的宽度。
         * 
         */
        JScrollBar vsb = getVerticalScrollBar();
        if ((vsb != null) && (vsb.isVisible())) {
            int vsbWidth = vsb.getWidth();
            if ( !leftToRight ) {
                borderR.x += vsbWidth;
            }
            borderR.width -= vsbWidth;
        }

        /* If there's a visible horizontal scrollbar remove the space it needs
         * from the height of borderR.
         * <p>
         *  从borderR的高度。
         * 
         */
        JScrollBar hsb = getHorizontalScrollBar();
        if ((hsb != null) && (hsb.isVisible())) {
            borderR.height -= hsb.getHeight();
        }

        return borderR;
    }


    /**
     * By default <code>JScrollPane</code> creates scrollbars
     * that are instances
     * of this class.  <code>Scrollbar</code> overrides the
     * <code>getUnitIncrement</code> and <code>getBlockIncrement</code>
     * methods so that, if the viewport's view is a <code>Scrollable</code>,
     * the view is asked to compute these values. Unless
     * the unit/block increment have been explicitly set.
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
     *  默认情况下,<code> JScrollPane </code>创建作为此类的实例的滚动条。
     *  <code> Scrollbar </code>覆盖<code> getUnitIncrement </code>和<code> getBlockIncrement </code>方法,以便如果视口的
     * 视图是<code> Scrollable </code>来计算这些值。
     *  默认情况下,<code> JScrollPane </code>创建作为此类的实例的滚动条。除非单位/块增量已明确设置。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     * 
     * @see Scrollable
     * @see JScrollPane#createVerticalScrollBar
     * @see JScrollPane#createHorizontalScrollBar
     */
    protected class ScrollBar extends JScrollBar implements UIResource
    {
        /**
         * Set to true when the unit increment has been explicitly set.
         * If this is false the viewport's view is obtained and if it
         * is an instance of <code>Scrollable</code> the unit increment
         * from it is used.
         * <p>
         *  当单位增量已明确设置时设置为true。如果这是假的,则获得视口的视图,并且如果它是<code> Scrollable </code>的实例,则使用其单位增量。
         * 
         */
        private boolean unitIncrementSet;
        /**
         * Set to true when the block increment has been explicitly set.
         * If this is false the viewport's view is obtained and if it
         * is an instance of <code>Scrollable</code> the block increment
         * from it is used.
         * <p>
         *  当块增量已明确设置时设置为true。如果这是假的,则获得视口的视图,并且如果它是<code> Scrollable </code>的实例,则使用来自它的块增量。
         * 
         */
        private boolean blockIncrementSet;

        /**
         * Creates a scrollbar with the specified orientation.
         * The options are:
         * <ul>
         * <li><code>ScrollPaneConstants.VERTICAL</code>
         * <li><code>ScrollPaneConstants.HORIZONTAL</code>
         * </ul>
         *
         * <p>
         *  创建具有指定方向的滚动条。选项是：
         * <ul>
         *  <li> <code> ScrollPaneConstants.VERTICAL </code> <li> <code> ScrollPaneConstants.HORIZONTAL </code>。
         * </ul>
         * 
         * 
         * @param orientation  an integer specifying one of the legal
         *      orientation values shown above
         * @since 1.4
         */
        public ScrollBar(int orientation) {
            super(orientation);
            this.putClientProperty("JScrollBar.fastWheelScrolling",
                                   Boolean.TRUE);
        }

        /**
         * Messages super to set the value, and resets the
         * <code>unitIncrementSet</code> instance variable to true.
         *
         * <p>
         *  消息超级设置值,并将<code> unitIncrementSet </code>实例变量重置为true。
         * 
         * 
         * @param unitIncrement the new unit increment value, in pixels
         */
        public void setUnitIncrement(int unitIncrement) {
            unitIncrementSet = true;
            this.putClientProperty("JScrollBar.fastWheelScrolling", null);
            super.setUnitIncrement(unitIncrement);
        }

        /**
         * Computes the unit increment for scrolling if the viewport's
         * view is a <code>Scrollable</code> object.
         * Otherwise return <code>super.getUnitIncrement</code>.
         *
         * <p>
         *  如果视口的视图是一个<code> Scrollable </code>对象,则计算滚动的单位增量。否则返回<code> super.getUnitIncrement </code>。
         * 
         * 
         * @param direction less than zero to scroll up/left,
         *      greater than zero for down/right
         * @return an integer, in pixels, containing the unit increment
         * @see Scrollable#getScrollableUnitIncrement
         */
        public int getUnitIncrement(int direction) {
            JViewport vp = getViewport();
            if (!unitIncrementSet && (vp != null) &&
                (vp.getView() instanceof Scrollable)) {
                Scrollable view = (Scrollable)(vp.getView());
                Rectangle vr = vp.getViewRect();
                return view.getScrollableUnitIncrement(vr, getOrientation(), direction);
            }
            else {
                return super.getUnitIncrement(direction);
            }
        }

        /**
         * Messages super to set the value, and resets the
         * <code>blockIncrementSet</code> instance variable to true.
         *
         * <p>
         *  消息超级设置值,并将<code> blockIncrementSet </code>实例变量重置为true。
         * 
         * 
         * @param blockIncrement the new block increment value, in pixels
         */
        public void setBlockIncrement(int blockIncrement) {
            blockIncrementSet = true;
            this.putClientProperty("JScrollBar.fastWheelScrolling", null);
            super.setBlockIncrement(blockIncrement);
        }

        /**
         * Computes the block increment for scrolling if the viewport's
         * view is a <code>Scrollable</code> object.  Otherwise
         * the <code>blockIncrement</code> equals the viewport's width
         * or height.  If there's no viewport return
         * <code>super.getBlockIncrement</code>.
         *
         * <p>
         * 如果视口的视图是一个<code> Scrollable </code>对象,则计算用于滚动的块增量。否则<code> blockIncrement </code>等于视口的宽度或高度。
         * 如果没有视口返回<code> super.getBlockIncrement </code>。
         * 
         * 
         * @param direction less than zero to scroll up/left,
         *      greater than zero for down/right
         * @return an integer, in pixels, containing the block increment
         * @see Scrollable#getScrollableBlockIncrement
         */
        public int getBlockIncrement(int direction) {
            JViewport vp = getViewport();
            if (blockIncrementSet || vp == null) {
                return super.getBlockIncrement(direction);
            }
            else if (vp.getView() instanceof Scrollable) {
                Scrollable view = (Scrollable)(vp.getView());
                Rectangle vr = vp.getViewRect();
                return view.getScrollableBlockIncrement(vr, getOrientation(), direction);
            }
            else if (getOrientation() == VERTICAL) {
                return vp.getExtentSize().height;
            }
            else {
                return vp.getExtentSize().width;
            }
        }

    }


    /**
     * Returns a <code>JScrollPane.ScrollBar</code> by default.
     * Subclasses may override this method to force <code>ScrollPaneUI</code>
     * implementations to use a <code>JScrollBar</code> subclass.
     * Used by <code>ScrollPaneUI</code> implementations to
     * create the horizontal scrollbar.
     *
     * <p>
     *  默认情况下返回<code> JScrollPane.ScrollBar </code>。
     * 子类可以覆盖此方法以强制<code> ScrollPaneUI </code>实现使用<code> JScrollBar </code>子类。
     * 用于<code> ScrollPaneUI </code>实现来创建水平滚动条。
     * 
     * 
     * @return a <code>JScrollBar</code> with a horizontal orientation
     * @see JScrollBar
     */
    public JScrollBar createHorizontalScrollBar() {
        return new ScrollBar(JScrollBar.HORIZONTAL);
    }


    /**
     * Returns the horizontal scroll bar that controls the viewport's
     * horizontal view position.
     *
     * <p>
     *  返回控制视口水平视图位置的水平滚动条。
     * 
     * 
     * @return the <code>horizontalScrollBar</code> property
     * @see #setHorizontalScrollBar
     */
    @Transient
    public JScrollBar getHorizontalScrollBar() {
        return horizontalScrollBar;
    }


    /**
     * Adds the scrollbar that controls the viewport's horizontal view
     * position to the scrollpane.
     * This is usually unnecessary, as <code>JScrollPane</code> creates
     * horizontal and vertical scrollbars by default.
     *
     * <p>
     *  将控制视口水平视图位置的滚动条添加到滚动窗格。这通常是不必要的,因为<code> JScrollPane </code>默认创建水平和垂直滚动条。
     * 
     * 
     * @param horizontalScrollBar the horizontal scrollbar to be added
     * @see #createHorizontalScrollBar
     * @see #getHorizontalScrollBar
     *
     * @beaninfo
     *        expert: true
     *         bound: true
     *   description: The horizontal scrollbar.
     */
    public void setHorizontalScrollBar(JScrollBar horizontalScrollBar) {
        JScrollBar old = getHorizontalScrollBar();
        this.horizontalScrollBar = horizontalScrollBar;
        if (horizontalScrollBar != null) {
            add(horizontalScrollBar, HORIZONTAL_SCROLLBAR);
        }
        else if (old != null) {
            remove(old);
        }
        firePropertyChange("horizontalScrollBar", old, horizontalScrollBar);

        revalidate();
        repaint();
    }


    /**
     * Returns a <code>JScrollPane.ScrollBar</code> by default.  Subclasses
     * may override this method to force <code>ScrollPaneUI</code>
     * implementations to use a <code>JScrollBar</code> subclass.
     * Used by <code>ScrollPaneUI</code> implementations to create the
     * vertical scrollbar.
     *
     * <p>
     *  默认情况下返回<code> JScrollPane.ScrollBar </code>。
     * 子类可以覆盖此方法以强制<code> ScrollPaneUI </code>实现使用<code> JScrollBar </code>子类。
     * 用于<code> ScrollPaneUI </code>实现来创建垂直滚动条。
     * 
     * 
     * @return a <code>JScrollBar</code> with a vertical orientation
     * @see JScrollBar
     */
    public JScrollBar createVerticalScrollBar() {
        return new ScrollBar(JScrollBar.VERTICAL);
    }


    /**
     * Returns the vertical scroll bar that controls the viewports
     * vertical view position.
     *
     * <p>
     *  返回控制视口垂直视图位置的垂直滚动条。
     * 
     * 
     * @return the <code>verticalScrollBar</code> property
     * @see #setVerticalScrollBar
     */
    @Transient
    public JScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }


    /**
     * Adds the scrollbar that controls the viewports vertical view position
     * to the scrollpane.  This is usually unnecessary,
     * as <code>JScrollPane</code> creates vertical and
     * horizontal scrollbars by default.
     *
     * <p>
     *  将控制视口垂直视图位置的滚动条添加到滚动条。这通常是不必要的,因为默认情况下<code> JScrollPane </code>创建垂直和水平滚动条。
     * 
     * 
     * @param verticalScrollBar the new vertical scrollbar to be added
     * @see #createVerticalScrollBar
     * @see #getVerticalScrollBar
     *
     * @beaninfo
     *        expert: true
     *         bound: true
     *   description: The vertical scrollbar.
     */
    public void setVerticalScrollBar(JScrollBar verticalScrollBar) {
        JScrollBar old = getVerticalScrollBar();
        this.verticalScrollBar = verticalScrollBar;
        add(verticalScrollBar, VERTICAL_SCROLLBAR);
        firePropertyChange("verticalScrollBar", old, verticalScrollBar);

        revalidate();
        repaint();
    }


    /**
     * Returns a new <code>JViewport</code> by default.
     * Used to create the
     * viewport (as needed) in <code>setViewportView</code>,
     * <code>setRowHeaderView</code>, and <code>setColumnHeaderView</code>.
     * Subclasses may override this method to return a subclass of
     * <code>JViewport</code>.
     *
     * <p>
     * 默认情况下返回一个新的<code> JViewport </code>。
     * 用于在<code> setViewportView </code>,<code> setRowHeaderView </code>和<code> setColumnHeaderView </code>中
     * 创建视口(根据需要)。
     * 默认情况下返回一个新的<code> JViewport </code>。子类可以覆盖此方法以返回<code> JViewport </code>的子类。
     * 
     * 
     * @return a new <code>JViewport</code>
     */
    protected JViewport createViewport() {
        return new JViewport();
    }


    /**
     * Returns the current <code>JViewport</code>.
     *
     * <p>
     *  返回当前<code> JViewport </code>。
     * 
     * 
     * @see #setViewport
     * @return the <code>viewport</code> property
     */
    public JViewport getViewport() {
        return viewport;
    }


    /**
     * Removes the old viewport (if there is one); forces the
     * viewPosition of the new viewport to be in the +x,+y quadrant;
     * syncs up the row and column headers (if there are any) with the
     * new viewport; and finally syncs the scrollbars and
     * headers with the new viewport.
     * <p>
     * Most applications will find it more convenient to use
     * <code>setViewportView</code>
     * to add a viewport and a view to the scrollpane.
     *
     * <p>
     *  删除旧的视口(如果有);强制新视口的viewPosition位于+ x,+ y象限中;使用新的视口同步行和列标题(如果有的话);最后使滚动条和标题与新的视口同步。
     * <p>
     *  大多数应用程序会发现使用<code> setViewportView </code>向滚动窗口添加视口和视图更加方便。
     * 
     * 
     * @param viewport the new viewport to be used; if viewport is
     *          <code>null</code>, the old viewport is still removed
     *          and the new viewport is set to <code>null</code>
     * @see #createViewport
     * @see #getViewport
     * @see #setViewportView
     *
     * @beaninfo
     *       expert: true
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The viewport child for this scrollpane
     *
     */
    public void setViewport(JViewport viewport) {
        JViewport old = getViewport();
        this.viewport = viewport;
        if (viewport != null) {
            add(viewport, VIEWPORT);
        }
        else if (old != null) {
            remove(old);
        }
        firePropertyChange("viewport", old, viewport);

        if (accessibleContext != null) {
            ((AccessibleJScrollPane)accessibleContext).resetViewPort();
        }

        revalidate();
        repaint();
    }


    /**
     * Creates a viewport if necessary and then sets its view.  Applications
     * that don't provide the view directly to the <code>JScrollPane</code>
     * constructor
     * should use this method to specify the scrollable child that's going
     * to be displayed in the scrollpane. For example:
     * <pre>
     * JScrollPane scrollpane = new JScrollPane();
     * scrollpane.setViewportView(myBigComponentToScroll);
     * </pre>
     * Applications should not add children directly to the scrollpane.
     *
     * <p>
     *  如果需要,创建视口,然后设置其视图。不直接向<code> JScrollPane </code>构造函数提供视图的应用程序应该使用此方法来指定要在滚动窗格中显示的可滚动子项。例如：
     * <pre>
     *  JScrollPane scrollpane = new JScrollPane(); scrollpane.setViewportView(myBigComponentToScroll);
     * </pre>
     *  应用程序不应将子项直接添加到滚动窗格。
     * 
     * 
     * @param view the component to add to the viewport
     * @see #setViewport
     * @see JViewport#setView
     */
    public void setViewportView(Component view) {
        if (getViewport() == null) {
            setViewport(createViewport());
        }
        getViewport().setView(view);
    }



    /**
     * Returns the row header.
     * <p>
     *  返回行标题。
     * 
     * 
     * @return the <code>rowHeader</code> property
     * @see #setRowHeader
     */
    @Transient
    public JViewport getRowHeader() {
        return rowHeader;
    }


    /**
     * Removes the old rowHeader, if it exists; if the new rowHeader
     * isn't <code>null</code>, syncs the y coordinate of its
     * viewPosition with
     * the viewport (if there is one) and then adds it to the scroll pane.
     * <p>
     * Most applications will find it more convenient to use
     * <code>setRowHeaderView</code>
     * to add a row header component and its viewport to the scroll pane.
     *
     * <p>
     *  删除旧的rowHeader,如果存在;如果新的rowHeader不是<code> null </code>,同步其viewPosition的y坐标与视口(如果有),然后将其添加到滚动窗格。
     * <p>
     * 大多数应用程序会发现使用<code> setRowHeaderView </code>更方便地将行标题组件及其视口添加到滚动窗格。
     * 
     * 
     * @param rowHeader the new row header to be used; if <code>null</code>
     *          the old row header is still removed and the new rowHeader
     *          is set to <code>null</code>
     * @see #getRowHeader
     * @see #setRowHeaderView
     *
     * @beaninfo
     *        bound: true
     *       expert: true
     *  description: The row header child for this scrollpane
     */
    public void setRowHeader(JViewport rowHeader) {
        JViewport old = getRowHeader();
        this.rowHeader = rowHeader;
        if (rowHeader != null) {
            add(rowHeader, ROW_HEADER);
        }
        else if (old != null) {
            remove(old);
        }
        firePropertyChange("rowHeader", old, rowHeader);
        revalidate();
        repaint();
    }


    /**
     * Creates a row-header viewport if necessary, sets
     * its view and then adds the row-header viewport
     * to the scrollpane.  For example:
     * <pre>
     * JScrollPane scrollpane = new JScrollPane();
     * scrollpane.setViewportView(myBigComponentToScroll);
     * scrollpane.setRowHeaderView(myBigComponentsRowHeader);
     * </pre>
     *
     * <p>
     *  如果需要,创建行标头视口,设置其视图,然后将行标头视口添加到滚动窗格。例如：
     * <pre>
     *  JScrollPane scrollpane = new JScrollPane(); scrollpane.setViewportView(myBigComponentToScroll); scro
     * llpane.setRowHeaderView(myBigComponentsRowHeader);。
     * </pre>
     * 
     * 
     * @see #setRowHeader
     * @see JViewport#setView
     * @param view the component to display as the row header
     */
    public void setRowHeaderView(Component view) {
        if (getRowHeader() == null) {
            setRowHeader(createViewport());
        }
        getRowHeader().setView(view);
    }



    /**
     * Returns the column header.
     * <p>
     *  返回列标题。
     * 
     * 
     * @return the <code>columnHeader</code> property
     * @see #setColumnHeader
     */
    @Transient
    public JViewport getColumnHeader() {
        return columnHeader;
    }


    /**
     * Removes the old columnHeader, if it exists; if the new columnHeader
     * isn't <code>null</code>, syncs the x coordinate of its viewPosition
     * with the viewport (if there is one) and then adds it to the scroll pane.
     * <p>
     * Most applications will find it more convenient to use
     * <code>setColumnHeaderView</code>
     * to add a column header component and its viewport to the scroll pane.
     *
     * <p>
     *  删除旧的columnHeader,如果存在;如果新的columnHeader不是<code> null </code>,将其viewPosition的x坐标与视口(如果有)同步,然后将其添加到滚动窗格
     * 。
     * <p>
     *  大多数应用程序会发现使用<code> setColumnHeaderView </code>更方便地将列标题组件及其视口添加到滚动窗格。
     * 
     * 
     * @see #getColumnHeader
     * @see #setColumnHeaderView
     *
     * @beaninfo
     *        bound: true
     *  description: The column header child for this scrollpane
     *    attribute: visualUpdate true
     */
    public void setColumnHeader(JViewport columnHeader) {
        JViewport old = getColumnHeader();
        this.columnHeader = columnHeader;
        if (columnHeader != null) {
            add(columnHeader, COLUMN_HEADER);
        }
        else if (old != null) {
            remove(old);
        }
        firePropertyChange("columnHeader", old, columnHeader);

        revalidate();
        repaint();
    }



    /**
     * Creates a column-header viewport if necessary, sets
     * its view, and then adds the column-header viewport
     * to the scrollpane.  For example:
     * <pre>
     * JScrollPane scrollpane = new JScrollPane();
     * scrollpane.setViewportView(myBigComponentToScroll);
     * scrollpane.setColumnHeaderView(myBigComponentsColumnHeader);
     * </pre>
     *
     * <p>
     *  如果需要,创建列标题视口,设置其视图,然后将列标题视口添加到滚动窗格。例如：
     * <pre>
     *  JScrollPane scrollpane = new JScrollPane(); scrollpane.setViewportView(myBigComponentToScroll); scro
     * llpane.setColumnHeaderView(myBigComponentsColumnHeader);。
     * </pre>
     * 
     * 
     * @see #setColumnHeader
     * @see JViewport#setView
     *
     * @param view the component to display as the column header
     */
    public void setColumnHeaderView(Component view) {
        if (getColumnHeader() == null) {
            setColumnHeader(createViewport());
        }
        getColumnHeader().setView(view);
    }


    /**
     * Returns the component at the specified corner. The
     * <code>key</code> value specifying the corner is one of:
     * <ul>
     * <li>ScrollPaneConstants.LOWER_LEFT_CORNER
     * <li>ScrollPaneConstants.LOWER_RIGHT_CORNER
     * <li>ScrollPaneConstants.UPPER_LEFT_CORNER
     * <li>ScrollPaneConstants.UPPER_RIGHT_CORNER
     * <li>ScrollPaneConstants.LOWER_LEADING_CORNER
     * <li>ScrollPaneConstants.LOWER_TRAILING_CORNER
     * <li>ScrollPaneConstants.UPPER_LEADING_CORNER
     * <li>ScrollPaneConstants.UPPER_TRAILING_CORNER
     * </ul>
     *
     * <p>
     *  返回指定角落的组件。指定角的<code>键</code>值为以下之一：
     * <ul>
     * <LI> ScrollPaneConstants.LOWER_LEFT_CORNER <LI> ScrollPaneConstants.LOWER_RIGHT_CORNER <LI> ScrollPan
     * eConstants.UPPER_LEFT_CORNER <LI> ScrollPaneConstants.UPPER_RIGHT_CORNER <LI> ScrollPaneConstants.LOW
     * ER_LEADING_CORNER <LI> ScrollPaneConstants.LOWER_TRAILING_CORNER <LI> ScrollPaneConstants.UPPER_LEADI
     * NG_CORNER <LI> ScrollPaneConstants.UPPER_TRAILING_CORNER。
     * </ul>
     * 
     * 
     * @param key one of the values as shown above
     * @return the corner component (which may be <code>null</code>)
     *         identified by the given key, or <code>null</code>
     *         if the key is invalid
     * @see #setCorner
     */
    public Component getCorner(String key) {
        boolean isLeftToRight = getComponentOrientation().isLeftToRight();
        if (key.equals(LOWER_LEADING_CORNER)) {
            key = isLeftToRight ? LOWER_LEFT_CORNER : LOWER_RIGHT_CORNER;
        } else if (key.equals(LOWER_TRAILING_CORNER)) {
            key = isLeftToRight ? LOWER_RIGHT_CORNER : LOWER_LEFT_CORNER;
        } else if (key.equals(UPPER_LEADING_CORNER)) {
            key = isLeftToRight ? UPPER_LEFT_CORNER : UPPER_RIGHT_CORNER;
        } else if (key.equals(UPPER_TRAILING_CORNER)) {
            key = isLeftToRight ? UPPER_RIGHT_CORNER : UPPER_LEFT_CORNER;
        }
        if (key.equals(LOWER_LEFT_CORNER)) {
            return lowerLeft;
        }
        else if (key.equals(LOWER_RIGHT_CORNER)) {
            return lowerRight;
        }
        else if (key.equals(UPPER_LEFT_CORNER)) {
            return upperLeft;
        }
        else if (key.equals(UPPER_RIGHT_CORNER)) {
            return upperRight;
        }
        else {
            return null;
        }
    }


    /**
     * Adds a child that will appear in one of the scroll panes
     * corners, if there's room.   For example with both scrollbars
     * showing (on the right and bottom edges of the scrollpane)
     * the lower left corner component will be shown in the space
     * between ends of the two scrollbars. Legal values for
     * the <b>key</b> are:
     * <ul>
     * <li>ScrollPaneConstants.LOWER_LEFT_CORNER
     * <li>ScrollPaneConstants.LOWER_RIGHT_CORNER
     * <li>ScrollPaneConstants.UPPER_LEFT_CORNER
     * <li>ScrollPaneConstants.UPPER_RIGHT_CORNER
     * <li>ScrollPaneConstants.LOWER_LEADING_CORNER
     * <li>ScrollPaneConstants.LOWER_TRAILING_CORNER
     * <li>ScrollPaneConstants.UPPER_LEADING_CORNER
     * <li>ScrollPaneConstants.UPPER_TRAILING_CORNER
     * </ul>
     * <p>
     * Although "corner" doesn't match any beans property
     * signature, <code>PropertyChange</code> events are generated with the
     * property name set to the corner key.
     *
     * <p>
     *  添加将出现在其中一个滚动窗口角落(如果有空间)的子级。例如,对于两个滚动条显示(在滚动条的右边缘和底部边缘),左下角组件将显示在两个滚动条的端部之间的空间中。 <b>键</b>的有效值为：
     * <ul>
     *  <LI> ScrollPaneConstants.LOWER_LEFT_CORNER <LI> ScrollPaneConstants.LOWER_RIGHT_CORNER <LI> ScrollPa
     * neConstants.UPPER_LEFT_CORNER <LI> ScrollPaneConstants.UPPER_RIGHT_CORNER <LI> ScrollPaneConstants.LO
     * WER_LEADING_CORNER <LI> ScrollPaneConstants.LOWER_TRAILING_CORNER <LI> ScrollPaneConstants.UPPER_LEAD
     * ING_CORNER <LI> ScrollPaneConstants.UPPER_TRAILING_CORNER。
     * </ul>
     * <p>
     *  虽然"corner"与任何bean属性签名不匹配,但会生成属性名称设置为角键的<code> PropertyChange </code>事件。
     * 
     * 
     * @param key identifies which corner the component will appear in
     * @param corner one of the following components:
     * <ul>
     * <li>lowerLeft
     * <li>lowerRight
     * <li>upperLeft
     * <li>upperRight
     * </ul>
     * @exception IllegalArgumentException if corner key is invalid
     */
    public void setCorner(String key, Component corner)
    {
        Component old;
        boolean isLeftToRight = getComponentOrientation().isLeftToRight();
        if (key.equals(LOWER_LEADING_CORNER)) {
            key = isLeftToRight ? LOWER_LEFT_CORNER : LOWER_RIGHT_CORNER;
        } else if (key.equals(LOWER_TRAILING_CORNER)) {
            key = isLeftToRight ? LOWER_RIGHT_CORNER : LOWER_LEFT_CORNER;
        } else if (key.equals(UPPER_LEADING_CORNER)) {
            key = isLeftToRight ? UPPER_LEFT_CORNER : UPPER_RIGHT_CORNER;
        } else if (key.equals(UPPER_TRAILING_CORNER)) {
            key = isLeftToRight ? UPPER_RIGHT_CORNER : UPPER_LEFT_CORNER;
        }
        if (key.equals(LOWER_LEFT_CORNER)) {
            old = lowerLeft;
            lowerLeft = corner;
        }
        else if (key.equals(LOWER_RIGHT_CORNER)) {
            old = lowerRight;
            lowerRight = corner;
        }
        else if (key.equals(UPPER_LEFT_CORNER)) {
            old = upperLeft;
            upperLeft = corner;
        }
        else if (key.equals(UPPER_RIGHT_CORNER)) {
            old = upperRight;
            upperRight = corner;
        }
        else {
            throw new IllegalArgumentException("invalid corner key");
        }
        if (old != null) {
            remove(old);
        }
        if (corner != null) {
            add(corner, key);
        }
        firePropertyChange(key, old, corner);
        revalidate();
        repaint();
    }

    /**
     * Sets the orientation for the vertical and horizontal
     * scrollbars as determined by the
     * <code>ComponentOrientation</code> argument.
     *
     * <p>
     *  设置由<code> ComponentOrientation </code>参数确定的垂直和水平滚动条的方向。
     * 
     * 
     * @param  co one of the following values:
     * <ul>
     * <li>java.awt.ComponentOrientation.LEFT_TO_RIGHT
     * <li>java.awt.ComponentOrientation.RIGHT_TO_LEFT
     * <li>java.awt.ComponentOrientation.UNKNOWN
     * </ul>
     * @see java.awt.ComponentOrientation
     */
    public void setComponentOrientation( ComponentOrientation co ) {
        super.setComponentOrientation( co );
        if( verticalScrollBar != null )
            verticalScrollBar.setComponentOrientation( co );
        if( horizontalScrollBar != null )
            horizontalScrollBar.setComponentOrientation( co );
    }

    /**
     * Indicates whether or not scrolling will take place in response to the
     * mouse wheel.  Wheel scrolling is enabled by default.
     *
     * <p>
     *  指示是否响应鼠标滚轮进行滚动。默认情况下启用滚轮滚动。
     * 
     * 
     * @see #setWheelScrollingEnabled
     * @since 1.4
     * @beaninfo
     *       bound: true
     * description: Flag for enabling/disabling mouse wheel scrolling
     */
    public boolean isWheelScrollingEnabled() {return wheelScrollState;}

    /**
     * Enables/disables scrolling in response to movement of the mouse wheel.
     * Wheel scrolling is enabled by default.
     *
     * <p>
     * 启用/禁用响应鼠标滚轮的移动进行滚动。默认情况下启用滚轮滚动。
     * 
     * 
     * @param handleWheel   <code>true</code> if scrolling should be done
     *                      automatically for a MouseWheelEvent,
     *                      <code>false</code> otherwise.
     * @see #isWheelScrollingEnabled
     * @see java.awt.event.MouseWheelEvent
     * @see java.awt.event.MouseWheelListener
     * @since 1.4
     * @beaninfo
     *       bound: true
     * description: Flag for enabling/disabling mouse wheel scrolling
     */
    public void setWheelScrollingEnabled(boolean handleWheel) {
        boolean old = wheelScrollState;
        wheelScrollState = handleWheel;
        firePropertyChange("wheelScrollingEnabled", old, handleWheel);
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


    /**
     * Returns a string representation of this <code>JScrollPane</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JScrollPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JScrollPane</code>.
     */
    protected String paramString() {
        String viewportBorderString = (viewportBorder != null ?
                                       viewportBorder.toString() : "");
        String viewportString = (viewport != null ?
                                 viewport.toString() : "");
        String verticalScrollBarPolicyString;
        if (verticalScrollBarPolicy == VERTICAL_SCROLLBAR_AS_NEEDED) {
            verticalScrollBarPolicyString = "VERTICAL_SCROLLBAR_AS_NEEDED";
        } else if (verticalScrollBarPolicy == VERTICAL_SCROLLBAR_NEVER) {
            verticalScrollBarPolicyString = "VERTICAL_SCROLLBAR_NEVER";
        } else if (verticalScrollBarPolicy == VERTICAL_SCROLLBAR_ALWAYS) {
            verticalScrollBarPolicyString = "VERTICAL_SCROLLBAR_ALWAYS";
        } else verticalScrollBarPolicyString = "";
        String horizontalScrollBarPolicyString;
        if (horizontalScrollBarPolicy == HORIZONTAL_SCROLLBAR_AS_NEEDED) {
            horizontalScrollBarPolicyString = "HORIZONTAL_SCROLLBAR_AS_NEEDED";
        } else if (horizontalScrollBarPolicy == HORIZONTAL_SCROLLBAR_NEVER) {
            horizontalScrollBarPolicyString = "HORIZONTAL_SCROLLBAR_NEVER";
        } else if (horizontalScrollBarPolicy == HORIZONTAL_SCROLLBAR_ALWAYS) {
            horizontalScrollBarPolicyString = "HORIZONTAL_SCROLLBAR_ALWAYS";
        } else horizontalScrollBarPolicyString = "";
        String horizontalScrollBarString = (horizontalScrollBar != null ?
                                            horizontalScrollBar.toString()
                                            : "");
        String verticalScrollBarString = (verticalScrollBar != null ?
                                          verticalScrollBar.toString() : "");
        String columnHeaderString = (columnHeader != null ?
                                     columnHeader.toString() : "");
        String rowHeaderString = (rowHeader != null ?
                                  rowHeader.toString() : "");
        String lowerLeftString = (lowerLeft != null ?
                                  lowerLeft.toString() : "");
        String lowerRightString = (lowerRight != null ?
                                  lowerRight.toString() : "");
        String upperLeftString = (upperLeft != null ?
                                  upperLeft.toString() : "");
        String upperRightString = (upperRight != null ?
                                  upperRight.toString() : "");

        return super.paramString() +
        ",columnHeader=" + columnHeaderString +
        ",horizontalScrollBar=" + horizontalScrollBarString +
        ",horizontalScrollBarPolicy=" + horizontalScrollBarPolicyString +
        ",lowerLeft=" + lowerLeftString +
        ",lowerRight=" + lowerRightString +
        ",rowHeader=" + rowHeaderString +
        ",upperLeft=" + upperLeftString +
        ",upperRight=" + upperRightString +
        ",verticalScrollBar=" + verticalScrollBarString +
        ",verticalScrollBarPolicy=" + verticalScrollBarPolicyString +
        ",viewport=" + viewportString +
        ",viewportBorder=" + viewportBorderString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JScrollPane.
     * For scroll panes, the AccessibleContext takes the form of an
     * AccessibleJScrollPane.
     * A new AccessibleJScrollPane instance is created if necessary.
     *
     * <p>
     *  获取与此JScrollPane相关联的AccessibleContext。对于滚动窗格,AccessibleContext采用AccessibleJScrollPane的形式。
     * 如有必要,将创建一个新的AccessibleJScrollPane实例。
     * 
     * 
     * @return an AccessibleJScrollPane that serves as the
     *         AccessibleContext of this JScrollPane
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJScrollPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JScrollPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to scroll pane user-interface
     * elements.
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
     *  此类实现了对<code> JScrollPane </code>类的辅助功能支持。它提供了适用于滚动窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJScrollPane extends AccessibleJComponent
        implements ChangeListener, PropertyChangeListener {

        protected JViewport viewPort = null;

        /*
         * Resets the viewport ChangeListener and PropertyChangeListener
         * <p>
         *  重置视口ChangeListener和PropertyChangeListener
         * 
         */
        public void resetViewPort() {
            if (viewPort != null) {
                viewPort.removeChangeListener(this);
                viewPort.removePropertyChangeListener(this);
            }
            viewPort = JScrollPane.this.getViewport();
            if (viewPort != null) {
                viewPort.addChangeListener(this);
                viewPort.addPropertyChangeListener(this);
            }
        }

        /**
         * AccessibleJScrollPane constructor
         * <p>
         *  AccessibleJScrollPane构造函数
         * 
         */
        public AccessibleJScrollPane() {
            super();

            resetViewPort();

            // initialize the AccessibleRelationSets for the JScrollPane
            // and JScrollBar(s)
            JScrollBar scrollBar = getHorizontalScrollBar();
            if (scrollBar != null) {
                setScrollBarRelations(scrollBar);
            }
            scrollBar = getVerticalScrollBar();
            if (scrollBar != null) {
                setScrollBarRelations(scrollBar);
            }
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.SCROLL_PANE;
        }

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * <p>
         * 当侦听器的目标已更改其状态时调用。
         * 
         * 
         * @param e  a <code>ChangeEvent</code> object. Must not be null.
         *
         * @throws NullPointerException if the parameter is null.
         */
        public void stateChanged(ChangeEvent e) {
            if (e == null) {
                throw new NullPointerException();
            }
            firePropertyChange(ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                               Boolean.valueOf(false),
                               Boolean.valueOf(true));
        }

        /**
         * This method gets called when a bound property is changed.
         * <p>
         *  当绑定属性更改时,将调用此方法。
         * 
         * 
         * @param e A <code>PropertyChangeEvent</code> object describing
         * the event source and the property that has changed. Must not be null.
         *
         * @throws NullPointerException if the parameter is null.
         * @since 1.5
         */
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName == "horizontalScrollBar" ||
                propertyName == "verticalScrollBar") {

                if (e.getNewValue() instanceof JScrollBar) {
                    setScrollBarRelations((JScrollBar)e.getNewValue());
                }
            }
        }


        /*
         * Sets the CONTROLLER_FOR and CONTROLLED_BY AccessibleRelations for
         * the JScrollPane and JScrollBar. JScrollBar must not be null.
         * <p>
         *  为JScrollPane和JScrollBar设置CONTROLLER_FOR和CONTROLLED_BY AccessibleRelations。 JScrollBar不能为空。
         * 
         */
        void setScrollBarRelations(JScrollBar scrollBar) {
            /*
             * The JScrollBar is a CONTROLLER_FOR the JScrollPane.
             * The JScrollPane is CONTROLLED_BY the JScrollBar.
             * <p>
             *  JScrollBar是JScrollPane的CONTROLLER_FOR。 JScrollPane是CONTROLLED_BY的JScrollBar。
             */
            AccessibleRelation controlledBy =
                new AccessibleRelation(AccessibleRelation.CONTROLLED_BY,
                                       scrollBar);
            AccessibleRelation controllerFor =
                new AccessibleRelation(AccessibleRelation.CONTROLLER_FOR,
                                       JScrollPane.this);

            // set the relation set for the scroll bar
            AccessibleContext ac = scrollBar.getAccessibleContext();
            ac.getAccessibleRelationSet().add(controllerFor);

            // set the relation set for the scroll pane
            getAccessibleRelationSet().add(controlledBy);
        }
    }
}
