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

import java.awt.peer.ScrollbarPeer;
import java.awt.event.*;
import java.util.EventListener;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.accessibility.*;


/**
 * The <code>Scrollbar</code> class embodies a scroll bar, a
 * familiar user-interface object. A scroll bar provides a
 * convenient means for allowing a user to select from a
 * range of values. The following three vertical
 * scroll bars could be used as slider controls to pick
 * the red, green, and blue components of a color:
 * <p>
 * <img src="doc-files/Scrollbar-1.gif" alt="Image shows 3 vertical sliders, side-by-side."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * Each scroll bar in this example could be created with
 * code similar to the following:
 *
 * <hr><blockquote><pre>
 * redSlider=new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 255);
 * add(redSlider);
 * </pre></blockquote><hr>
 * <p>
 * Alternatively, a scroll bar can represent a range of values. For
 * example, if a scroll bar is used for scrolling through text, the
 * width of the "bubble" (also called the "thumb" or "scroll box")
 * can be used to represent the amount of text that is visible.
 * Here is an example of a scroll bar that represents a range:
 * <p>
 * <img src="doc-files/Scrollbar-2.gif"
 * alt="Image shows horizontal slider with starting range of 0 and ending range of 300. The slider thumb is labeled 60."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * The value range represented by the bubble in this example
 * is the <em>visible amount</em>. The horizontal scroll bar
 * in this example could be created with code like the following:
 *
 * <hr><blockquote><pre>
 * ranger = new Scrollbar(Scrollbar.HORIZONTAL, 0, 60, 0, 300);
 * add(ranger);
 * </pre></blockquote><hr>
 * <p>
 * Note that the actual maximum value of the scroll bar is the
 * <code>maximum</code> minus the <code>visible amount</code>.
 * In the previous example, because the <code>maximum</code> is
 * 300 and the <code>visible amount</code> is 60, the actual maximum
 * value is 240.  The range of the scrollbar track is 0 - 300.
 * The left side of the bubble indicates the value of the
 * scroll bar.
 * <p>
 * Normally, the user changes the value of the scroll bar by
 * making a gesture with the mouse. For example, the user can
 * drag the scroll bar's bubble up and down, or click in the
 * scroll bar's unit increment or block increment areas. Keyboard
 * gestures can also be mapped to the scroll bar. By convention,
 * the <b>Page&nbsp;Up</b> and <b>Page&nbsp;Down</b>
 * keys are equivalent to clicking in the scroll bar's block
 * increment and block decrement areas.
 * <p>
 * When the user changes the value of the scroll bar, the scroll bar
 * receives an instance of <code>AdjustmentEvent</code>.
 * The scroll bar processes this event, passing it along to
 * any registered listeners.
 * <p>
 * Any object that wishes to be notified of changes to the
 * scroll bar's value should implement
 * <code>AdjustmentListener</code>, an interface defined in
 * the package <code>java.awt.event</code>.
 * Listeners can be added and removed dynamically by calling
 * the methods <code>addAdjustmentListener</code> and
 * <code>removeAdjustmentListener</code>.
 * <p>
 * The <code>AdjustmentEvent</code> class defines five types
 * of adjustment event, listed here:
 *
 * <ul>
 * <li><code>AdjustmentEvent.TRACK</code> is sent out when the
 * user drags the scroll bar's bubble.
 * <li><code>AdjustmentEvent.UNIT_INCREMENT</code> is sent out
 * when the user clicks in the left arrow of a horizontal scroll
 * bar, or the top arrow of a vertical scroll bar, or makes the
 * equivalent gesture from the keyboard.
 * <li><code>AdjustmentEvent.UNIT_DECREMENT</code> is sent out
 * when the user clicks in the right arrow of a horizontal scroll
 * bar, or the bottom arrow of a vertical scroll bar, or makes the
 * equivalent gesture from the keyboard.
 * <li><code>AdjustmentEvent.BLOCK_INCREMENT</code> is sent out
 * when the user clicks in the track, to the left of the bubble
 * on a horizontal scroll bar, or above the bubble on a vertical
 * scroll bar. By convention, the <b>Page&nbsp;Up</b>
 * key is equivalent, if the user is using a keyboard that
 * defines a <b>Page&nbsp;Up</b> key.
 * <li><code>AdjustmentEvent.BLOCK_DECREMENT</code> is sent out
 * when the user clicks in the track, to the right of the bubble
 * on a horizontal scroll bar, or below the bubble on a vertical
 * scroll bar. By convention, the <b>Page&nbsp;Down</b>
 * key is equivalent, if the user is using a keyboard that
 * defines a <b>Page&nbsp;Down</b> key.
 * </ul>
 * <p>
 * The JDK&nbsp;1.0 event system is supported for backwards
 * compatibility, but its use with newer versions of the platform is
 * discouraged. The five types of adjustment events introduced
 * with JDK&nbsp;1.1 correspond to the five event types
 * that are associated with scroll bars in previous platform versions.
 * The following list gives the adjustment event type,
 * and the corresponding JDK&nbsp;1.0 event type it replaces.
 *
 * <ul>
 * <li><code>AdjustmentEvent.TRACK</code> replaces
 * <code>Event.SCROLL_ABSOLUTE</code>
 * <li><code>AdjustmentEvent.UNIT_INCREMENT</code> replaces
 * <code>Event.SCROLL_LINE_UP</code>
 * <li><code>AdjustmentEvent.UNIT_DECREMENT</code> replaces
 * <code>Event.SCROLL_LINE_DOWN</code>
 * <li><code>AdjustmentEvent.BLOCK_INCREMENT</code> replaces
 * <code>Event.SCROLL_PAGE_UP</code>
 * <li><code>AdjustmentEvent.BLOCK_DECREMENT</code> replaces
 * <code>Event.SCROLL_PAGE_DOWN</code>
 * </ul>
 * <p>
 * <b>Note</b>: We recommend using a <code>Scrollbar</code>
 * for value selection only.  If you want to implement
 * a scrollable component inside a container, we recommend you use
 * a {@link ScrollPane ScrollPane}. If you use a
 * <code>Scrollbar</code> for this purpose, you are likely to
 * encounter issues with painting, key handling, sizing and
 * positioning.
 *
 * <p>
 *  <code> Scrollbar </code>类包含一个滚动条,一个熟悉的用户界面对象滚动条提供了一种方便的方法,允许用户从一系列值中选择以下三个垂直滚动条可以用作滑块控制选择颜色的红色,绿色和蓝色
 * 分量：。
 * <p>
 *  <img src ="doc-files / Scrollbar-1gif"alt ="图像显示3个垂直滑块,并排"
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  本示例中的每个滚动条可以使用类似于以下代码创建：
 * 
 *  <hr> <blockquote> <pre> redSlider = new Scrollbar(ScrollbarVERTICAL,0,1,0,255); add(redSlider); </pre>
 *  </blockquote> <hr>。
 * <p>
 * 或者,滚动条可以表示值的范围。例如,如果滚动条用于滚动文本,则"泡"(也称为"拇指"或"滚动框")的宽度可以用于表示可见的文本量以下是表示范围的滚动条的示例：
 * <p>
 *  <img src ="doc-files / Scrollbar-2gif"alt ="图像显示水平滑块,起始范围为0,结束范围为300滑块缩略图标记为60"
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  在此示例中由气泡表示的值范围是<em>可见量</em>。在本示例中,可以使用如下代码创建水平滚动条：
 * 
 *  <hr> <blockquote> <pre> ranger = new Scrollbar(ScrollbarHORIZONTAL,0,60,0,300); add(ranger); </pre> 
 * </blockquote> <hr>。
 * <p>
 * 注意,滚动条的实际最大值是<code> maximum </code>减去<code> visible amount </code>。
 * 在前面的例子中,因为<code> maximum </code> <code> visible amount </code>为60,实际最大值为240滚动条轨道的范围为0  -  300气泡的左侧表示滚
 * 动条的值。
 * 注意,滚动条的实际最大值是<code> maximum </code>减去<code> visible amount </code>。
 * <p>
 * 通常,用户通过用鼠标进行手势来改变滚动条的值例如,用户可以上下拖动滚动条的气泡,或者在滚动条的单位增量或块增量区域中点击键盘手势也可以映射到滚动条按照惯例,<b>页面向上</b>和<b>页面向下</b>
 * 键等同于单击滚动条的块增量和块减量区域。
 * <p>
 *  当用户更改滚动条的值时,滚动条接收<code> AdjustmentEvent </code>的实例。滚动条处理此事件,将其传递给任何注册的侦听器
 * <p>
 * 任何希望被通知滚动条的值的变化的对象应该实现<code> AdjustmentListener </code>,它是包中定义的接口<code> javaawtevent </code>监听器可以通过调用
 * 方法动态添加和删除<code> addAdjustmentListener </code>和<code> removeAdjustmentListener </code>。
 * <p>
 *  <code> AdjustmentEvent </code>类定义了五种类型的调整事件,如下所示：
 * 
 * <ul>
 * <li>当用户点击水平滚动条的左箭头时,用户拖动滚动条的气泡</li> <code> AdjustmentEventUNIT_INCREMENT </code>或垂直滚动​​条的顶部箭头,或者当用户点击
 * 水平滚动条的右箭头或底部箭头时,发出来自键盘<li> <code> AdjustmentEventUNIT_DECREMENT </code>的等效手势或者使得当用户在轨道中点击时,在水平滚动条上的气泡
 * 的左边,或者在上面的水平滚动条上,发出来自键盘<li> <code> AdjustmentEventBLOCK_INCREMENT </code>的等效手势在一个垂直的滚动条的泡影按照惯例,如果用户使用
 * 定义<b>页面向上</b>键<li> <code> AdjustmentEventBLOCK_DECREMENT </code>的键盘,<b>页面向上键</b>当用户在轨道中点击时,在水平滚动条上的气泡
 * 右侧或在垂直滚动条上的气泡下方发送。
 * 按照惯例,<b> Page&nbsp;向下</b>键是等效的,如果用户正在使用定义了<b> Page&nbsp;向下</b>键的键盘。
 * </ul>
 * <p>
 * JDK&nbsp; 10事件系统支持向后兼容性,但不建议使用新版本的平台。
 * JDK 11引入的五种类型的调整事件对应于与以前平台版本中的滚动条相关联的五种事件类型以下列表提供了调整事件类型及其替换的相应JDK&nbsp; 10事件类型。
 * 
 * <ul>
 * <li> <code> AdjustmentEventTRACK </code>取代<code> EventSCROLL_LINE_UP </code> <li> <code> AdjustmentEv
 * entUNIT_DECREMENT </code>,调整EventEventTRACK </code>取代<code> EventSCROLL_ABSOLUTE </code> <li> <code> 
 * AdjustmentEventUNIT_INCREMENT </code > <code> EventSCROLL_PAGE_DOWN </code>替换<code> EventSCROLL_PAGE_
 * UP </code> </code>代替<code> EventSCROLL_PAGE_DOWN </code> <li> <code> AdjustmentEventBLOCK_DECREMENT </code>
 * 。
 * </ul>
 * <p>
 * <b>注意</b>：我们建议仅使用<code> Scrollbar </code>进行值选择如果要在容器中实现可滚动组件,我们建议您使用{@link ScrollPane ScrollPane}使用<code>
 *  Scrollbar </code>为此目的,你可能会遇到绘画,键处理,大小和位置的问题。
 * 
 * 
 * @author      Sami Shaio
 * @see         java.awt.event.AdjustmentEvent
 * @see         java.awt.event.AdjustmentListener
 * @since       JDK1.0
 */
public class Scrollbar extends Component implements Adjustable, Accessible {

    /**
     * A constant that indicates a horizontal scroll bar.
     * <p>
     *  指示水平滚动条的常数
     * 
     */
    public static final int     HORIZONTAL = 0;

    /**
     * A constant that indicates a vertical scroll bar.
     * <p>
     *  指示垂直滚动条的常数
     * 
     */
    public static final int     VERTICAL   = 1;

    /**
     * The value of the <code>Scrollbar</code>.
     * This property must be greater than or equal to <code>minimum</code>
     * and less than or equal to
     * <code>maximum - visibleAmount</code>
     *
     * <p>
     *  <code> Scrollbar </code>的值必须大于或等于<code> minimum </code>且小于或等于<code> maximum  -  visibleAmount </code>
     * 。
     * 
     * 
     * @serial
     * @see #getValue
     * @see #setValue
     */
    int value;

    /**
     * The maximum value of the <code>Scrollbar</code>.
     * This value must be greater than the <code>minimum</code>
     * value.<br>
     *
     * <p>
     *  <code> Scrollbar的最大值</code>此值必须大于<code>最小值</code>的值<br>
     * 
     * 
     * @serial
     * @see #getMaximum
     * @see #setMaximum
     */
    int maximum;

    /**
     * The minimum value of the <code>Scrollbar</code>.
     * This value must be less than the <code>maximum</code>
     * value.<br>
     *
     * <p>
     * <code> Scrollbar的最小值</code>此值必须小于<code>最大</code>值<br>
     * 
     * 
     * @serial
     * @see #getMinimum
     * @see #setMinimum
     */
    int minimum;

    /**
     * The size of the <code>Scrollbar</code>'s bubble.
     * When a scroll bar is used to select a range of values,
     * the visibleAmount represents the size of this range.
     * Depending on platform, this may be visually indicated
     * by the size of the bubble.
     *
     * <p>
     *  <code> Scrollbar </code>的气泡的大小当滚动条用于选择一个范围的值时,visibleAmount代表该范围的大小。根据平台,这可以通过泡沫
     * 
     * 
     * @serial
     * @see #getVisibleAmount
     * @see #setVisibleAmount
     */
    int visibleAmount;

    /**
     * The <code>Scrollbar</code>'s orientation--being either horizontal
     * or vertical.
     * This value should be specified when the scrollbar is created.<BR>
     * orientation can be either : <code>VERTICAL</code> or
     * <code>HORIZONTAL</code> only.
     *
     * <p>
     *  <code> Scrollbar </code>的方向 - 水平或垂直此值应在创建滚动条时指定<BR>方向可以是：<code> VERTICAL </code>或<code> HORIZONTAL </code>
     * 。
     * 
     * 
     * @serial
     * @see #getOrientation
     * @see #setOrientation
     */
    int orientation;

    /**
     * The amount by which the scrollbar value will change when going
     * up or down by a line.
     * This value must be greater than zero.
     *
     * <p>
     *  滚动条值在向上或向下移动一行时的变化量此值必须大于零
     * 
     * 
     * @serial
     * @see #getLineIncrement
     * @see #setLineIncrement
     */
    int lineIncrement = 1;

    /**
     * The amount by which the scrollbar value will change when going
     * up or down by a page.
     * This value must be greater than zero.
     *
     * <p>
     * 在向上或向下移动页面时滚动条值将改变的量此值必须大于零
     * 
     * 
     * @serial
     * @see #getPageIncrement
     * @see #setPageIncrement
     */
    int pageIncrement = 10;

    /**
     * The adjusting status of the <code>Scrollbar</code>.
     * True if the value is in the process of changing as a result of
     * actions being taken by the user.
     *
     * <p>
     *  <code> Scrollbar </code>的调整状态如果值正在由于用户执行的操作而发生更改的过程中
     * 
     * 
     * @see #getValueIsAdjusting
     * @see #setValueIsAdjusting
     * @since 1.4
     */
    transient boolean isAdjusting;

    transient AdjustmentListener adjustmentListener;

    private static final String base = "scrollbar";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 11 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 8451667562882310543L;

    /**
     * Initialize JNI field and method IDs.
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Constructs a new vertical scroll bar.
     * The default properties of the scroll bar are listed in
     * the following table:
     *
     * <table border=1 summary="Scrollbar default properties">
     * <tr>
     *   <th>Property</th>
     *   <th>Description</th>
     *   <th>Default Value</th>
     * </tr>
     * <tr>
     *   <td>orientation</td>
     *   <td>indicates whether the scroll bar is vertical
     *   <br>or horizontal</td>
     *   <td><code>Scrollbar.VERTICAL</code></td>
     * </tr>
     * <tr>
     *   <td>value</td>
     *   <td>value which controls the location
     *   <br>of the scroll bar's bubble</td>
     *   <td>0</td>
     * </tr>
     * <tr>
     *   <td>visible amount</td>
     *   <td>visible amount of the scroll bar's range,
     *   <br>typically represented by the size of the
     *   <br>scroll bar's bubble</td>
     *   <td>10</td>
     * </tr>
     * <tr>
     *   <td>minimum</td>
     *   <td>minimum value of the scroll bar</td>
     *   <td>0</td>
     * </tr>
     * <tr>
     *   <td>maximum</td>
     *   <td>maximum value of the scroll bar</td>
     *   <td>100</td>
     * </tr>
     * <tr>
     *   <td>unit increment</td>
     *   <td>amount the value changes when the
     *   <br>Line Up or Line Down key is pressed,
     *   <br>or when the end arrows of the scrollbar
     *   <br>are clicked </td>
     *   <td>1</td>
     * </tr>
     * <tr>
     *   <td>block increment</td>
     *   <td>amount the value changes when the
     *   <br>Page Up or Page Down key is pressed,
     *   <br>or when the scrollbar track is clicked
     *   <br>on either side of the bubble </td>
     *   <td>10</td>
     * </tr>
     * </table>
     *
     * <p>
     *  构造新的垂直滚动条滚动条的默认属性如下表所示：
     * 
     * <table border=1 summary="Scrollbar default properties">
     * <tr>
     *  <th>属性</th> <th>描述</th> <th>默认值</th>
     * </tr>
     * <tr>
     *  <td> orientation </td> <td>指示滚动条是垂直的<br>还是水平的</td> <td> <code> ScrollbarVERTICAL </code> </td>
     * </tr>
     * <tr>
     *  <td> value </td> <td>用于控制滚动条气泡的位置的值</td> <td> 0 </td>
     * </tr>
     * <tr>
     * <td>可见数量</td> <td>滚动条范围的可见数量,<br>通常由<br>滚动条的气泡大小表示</td> <td> 10 </td>
     * </tr>
     * <tr>
     *  <td>最小值</td> <td>滚动条的最小值</td> <td> 0 </td>
     * </tr>
     * <tr>
     *  <td>最大值</td> <td>滚动条的最大值</td> <td> 100 </td>
     * </tr>
     * <tr>
     *  </span> </span> </span> </span> </span> </span> </<td> 1 </td>
     * </tr>
     * <tr>
     *  </span> <td>块增量</td> <td>当按下<br> Page Up或Page Down键时,<br>或当滚动条轨迹被点击时, / td> <td> 10 </td>
     * </tr>
     * </table>
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Scrollbar() throws HeadlessException {
        this(VERTICAL, 0, 10, 0, 100);
    }

    /**
     * Constructs a new scroll bar with the specified orientation.
     * <p>
     * The <code>orientation</code> argument must take one of the two
     * values <code>Scrollbar.HORIZONTAL</code>,
     * or <code>Scrollbar.VERTICAL</code>,
     * indicating a horizontal or vertical scroll bar, respectively.
     *
     * <p>
     *  构造具有指定方向的新滚动条
     * <p>
     * <code> orientation </code>参数必须使用<code> ScrollbarHORIZONTAL </code>或<code> ScrollbarVERTICAL </code>两个
     * 值之一,分别表示水平或垂直滚动​​条。
     * 
     * 
     * @param       orientation   indicates the orientation of the scroll bar
     * @exception   IllegalArgumentException    when an illegal value for
     *                    the <code>orientation</code> argument is supplied
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Scrollbar(int orientation) throws HeadlessException {
        this(orientation, 0, 10, 0, 100);
    }

    /**
     * Constructs a new scroll bar with the specified orientation,
     * initial value, visible amount, and minimum and maximum values.
     * <p>
     * The <code>orientation</code> argument must take one of the two
     * values <code>Scrollbar.HORIZONTAL</code>,
     * or <code>Scrollbar.VERTICAL</code>,
     * indicating a horizontal or vertical scroll bar, respectively.
     * <p>
     * The parameters supplied to this constructor are subject to the
     * constraints described in {@link #setValues(int, int, int, int)}.
     *
     * <p>
     *  构造具有指定方向,初始值,可见量和最小值和最大值的新滚动条
     * <p>
     *  <code> orientation </code>参数必须使用<code> ScrollbarHORIZONTAL </code>或<code> ScrollbarVERTICAL </code>两
     * 个值之一,分别表示水平或垂直滚动​​条。
     * <p>
     *  提供给此构造函数的参数受{@link #setValues(int,int,int,int)}中描述的约束的约束
     * 
     * 
     * @param     orientation   indicates the orientation of the scroll bar.
     * @param     value     the initial value of the scroll bar
     * @param     visible   the visible amount of the scroll bar, typically
     *                      represented by the size of the bubble
     * @param     minimum   the minimum value of the scroll bar
     * @param     maximum   the maximum value of the scroll bar
     * @exception IllegalArgumentException    when an illegal value for
     *                    the <code>orientation</code> argument is supplied
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see #setValues
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Scrollbar(int orientation, int value, int visible, int minimum,
        int maximum) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        switch (orientation) {
          case HORIZONTAL:
          case VERTICAL:
            this.orientation = orientation;
            break;
          default:
            throw new IllegalArgumentException("illegal scrollbar orientation");
        }
        setValues(value, visible, minimum, maximum);
    }

    /**
     * Constructs a name for this component.  Called by <code>getName</code>
     * when the name is <code>null</code>.
     * <p>
     *  构造此组件的名称当名称为<code> null </code>时,由<code> getName </code>
     * 
     */
    String constructComponentName() {
        synchronized (Scrollbar.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the <code>Scrollbar</code>'s peer.  The peer allows you to modify
     * the appearance of the <code>Scrollbar</code> without changing any of its
     * functionality.
     * <p>
     * 创建<code> Scrollbar </code>的对等体对等体允许您修改<code> Scrollbar </code>的外观,而不改变其任何功能
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createScrollbar(this);
            super.addNotify();
        }
    }

    /**
     * Returns the orientation of this scroll bar.
     *
     * <p>
     *  返回此滚动条的方向
     * 
     * 
     * @return    the orientation of this scroll bar, either
     *               <code>Scrollbar.HORIZONTAL</code> or
     *               <code>Scrollbar.VERTICAL</code>
     * @see       java.awt.Scrollbar#setOrientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation for this scroll bar.
     *
     * <p>
     *  设置此滚动条的方向
     * 
     * 
     * @param orientation  the orientation of this scroll bar, either
     *               <code>Scrollbar.HORIZONTAL</code> or
     *               <code>Scrollbar.VERTICAL</code>
     * @see       java.awt.Scrollbar#getOrientation
     * @exception   IllegalArgumentException  if the value supplied
     *                   for <code>orientation</code> is not a
     *                   legal value
     * @since     JDK1.1
     */
    public void setOrientation(int orientation) {
        synchronized (getTreeLock()) {
            if (orientation == this.orientation) {
                return;
            }
            switch (orientation) {
                case HORIZONTAL:
                case VERTICAL:
                    this.orientation = orientation;
                    break;
                default:
                    throw new IllegalArgumentException("illegal scrollbar orientation");
            }
            /* Create a new peer with the specified orientation. */
            if (peer != null) {
                removeNotify();
                addNotify();
                invalidate();
            }
        }
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    ((orientation == VERTICAL)
                     ? AccessibleState.HORIZONTAL : AccessibleState.VERTICAL),
                    ((orientation == VERTICAL)
                     ? AccessibleState.VERTICAL : AccessibleState.HORIZONTAL));
        }
    }

    /**
     * Gets the current value of this scroll bar.
     *
     * <p>
     *  获取此滚动条的当前值
     * 
     * 
     * @return      the current value of this scroll bar
     * @see         java.awt.Scrollbar#getMinimum
     * @see         java.awt.Scrollbar#getMaximum
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of this scroll bar to the specified value.
     * <p>
     * If the value supplied is less than the current <code>minimum</code>
     * or greater than the current <code>maximum - visibleAmount</code>,
     * then either <code>minimum</code> or <code>maximum - visibleAmount</code>
     * is substituted, as appropriate.
     * <p>
     * Normally, a program should change a scroll bar's
     * value only by calling <code>setValues</code>.
     * The <code>setValues</code> method simultaneously
     * and synchronously sets the minimum, maximum, visible amount,
     * and value properties of a scroll bar, so that they are
     * mutually consistent.
     * <p>
     * Calling this method does not fire an
     * <code>AdjustmentEvent</code>.
     *
     * <p>
     *  将此滚动条的值设置为指定的值
     * <p>
     *  如果提供的值小于当前<code> minimum </code>或大于当前<code> maximum  -  visibleAmount </code>,则<code> minimum </code>
     * 或<code> maximum  -  visibleAmount </code>。
     * <p>
     * 通常,程序应该通过同时调用<code> setValues </code>方法来更改滚动条的值,同时设置滚动条的最小值,最大值,可见量和值属性,使它们相互一致
     * <p>
     *  调用此方法不会触发<code> AdjustmentEvent </code>
     * 
     * 
     * @param       newValue   the new value of the scroll bar
     * @see         java.awt.Scrollbar#setValues
     * @see         java.awt.Scrollbar#getValue
     * @see         java.awt.Scrollbar#getMinimum
     * @see         java.awt.Scrollbar#getMaximum
     */
    public void setValue(int newValue) {
        // Use setValues so that a consistent policy relating
        // minimum, maximum, visible amount, and value is enforced.
        setValues(newValue, visibleAmount, minimum, maximum);
    }

    /**
     * Gets the minimum value of this scroll bar.
     *
     * <p>
     *  获取此滚动条的最小值
     * 
     * 
     * @return      the minimum value of this scroll bar
     * @see         java.awt.Scrollbar#getValue
     * @see         java.awt.Scrollbar#getMaximum
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * Sets the minimum value of this scroll bar.
     * <p>
     * When <code>setMinimum</code> is called, the minimum value
     * is changed, and other values (including the maximum, the
     * visible amount, and the current scroll bar value)
     * are changed to be consistent with the new minimum.
     * <p>
     * Normally, a program should change a scroll bar's minimum
     * value only by calling <code>setValues</code>.
     * The <code>setValues</code> method simultaneously
     * and synchronously sets the minimum, maximum, visible amount,
     * and value properties of a scroll bar, so that they are
     * mutually consistent.
     * <p>
     * Note that setting the minimum value to <code>Integer.MAX_VALUE</code>
     * will result in the new minimum value being set to
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * <p>
     *  设置此滚动条的最小值
     * <p>
     *  当调用<code> setMinimum </code>时,将更改最小值,并更改其他值(包括最大值,可见量和当前滚动条值)以与新的最小值
     * <p>
     * 通常,程序只能通过同时调用<code> setValues </code>方法来更改滚动条的最小值,同时设置滚动条的最小值,最大值,可见量和值属性bar,使它们相互一致
     * <p>
     *  注意,将最小值设置为<code> IntegerMAX_VALUE </code>将导致新的最小值设置为<code> IntegerMAX_VALUE  -  1 </code>
     * 
     * 
     * @param       newMinimum   the new minimum value for this scroll bar
     * @see         java.awt.Scrollbar#setValues
     * @see         java.awt.Scrollbar#setMaximum
     * @since       JDK1.1
     */
    public void setMinimum(int newMinimum) {
        // No checks are necessary in this method since minimum is
        // the first variable checked in the setValues function.

        // Use setValues so that a consistent policy relating
        // minimum, maximum, visible amount, and value is enforced.
        setValues(value, visibleAmount, newMinimum, maximum);
    }

    /**
     * Gets the maximum value of this scroll bar.
     *
     * <p>
     *  获取此滚动条的最大值
     * 
     * 
     * @return      the maximum value of this scroll bar
     * @see         java.awt.Scrollbar#getValue
     * @see         java.awt.Scrollbar#getMinimum
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Sets the maximum value of this scroll bar.
     * <p>
     * When <code>setMaximum</code> is called, the maximum value
     * is changed, and other values (including the minimum, the
     * visible amount, and the current scroll bar value)
     * are changed to be consistent with the new maximum.
     * <p>
     * Normally, a program should change a scroll bar's maximum
     * value only by calling <code>setValues</code>.
     * The <code>setValues</code> method simultaneously
     * and synchronously sets the minimum, maximum, visible amount,
     * and value properties of a scroll bar, so that they are
     * mutually consistent.
     * <p>
     * Note that setting the maximum value to <code>Integer.MIN_VALUE</code>
     * will result in the new maximum value being set to
     * <code>Integer.MIN_VALUE + 1</code>.
     *
     * <p>
     *  设置此滚动条的最大值
     * <p>
     *  当调用<code> setMaximum </code>时,将更改最大值,并更改其他值(包括最小值,可见量和当前滚动条值)以与新的最大值
     * <p>
     * 通常,程序只能通过同时调用<code> setValues </code>方法来更改滚动条的最大值,同时设置滚动条的最小值,最大值,可见数量和值属性bar,使它们相互一致
     * <p>
     *  请注意,将最大值设置为<code> IntegerMIN_VALUE </code>会导致新的最大值设置为<code> IntegerMIN_VALUE + 1 </code>
     * 
     * 
     * @param       newMaximum   the new maximum value
     *                     for this scroll bar
     * @see         java.awt.Scrollbar#setValues
     * @see         java.awt.Scrollbar#setMinimum
     * @since       JDK1.1
     */
    public void setMaximum(int newMaximum) {
        // minimum is checked first in setValues, so we need to
        // enforce minimum and maximum checks here.
        if (newMaximum == Integer.MIN_VALUE) {
            newMaximum = Integer.MIN_VALUE + 1;
        }

        if (minimum >= newMaximum) {
            minimum = newMaximum - 1;
        }

        // Use setValues so that a consistent policy relating
        // minimum, maximum, visible amount, and value is enforced.
        setValues(value, visibleAmount, minimum, newMaximum);
    }

    /**
     * Gets the visible amount of this scroll bar.
     * <p>
     * When a scroll bar is used to select a range of values,
     * the visible amount is used to represent the range of values
     * that are currently visible.  The size of the scroll bar's
     * bubble (also called a thumb or scroll box), usually gives a
     * visual representation of the relationship of the visible
     * amount to the range of the scroll bar.
     * Note that depending on platform, the value of the visible amount property
     * may not be visually indicated by the size of the bubble.
     * <p>
     * The scroll bar's bubble may not be displayed when it is not
     * moveable (e.g. when it takes up the entire length of the
     * scroll bar's track, or when the scroll bar is disabled).
     * Whether the bubble is displayed or not will not affect
     * the value returned by <code>getVisibleAmount</code>.
     *
     * <p>
     *  获取此滚动条的可见数量
     * <p>
     * 当滚动条用于选择值范围时,可见量用于表示当前可见的值的范围。
     * 滚动条的气泡(也称为缩略图或滚动框)的大小通常给出可视表示可见量与滚动条的范围的关系注意,根据平台,可见量属性的值可能不能通过气泡的大小在视觉上指示。
     * <p>
     *  当滚动条不可移动时(例如,当它占据滚动条的轨道的整个长度或者当滚动条被禁用时),可能不显示滚动条的气泡。是否显示气泡不会影响返回的值<code> getVisibleAmount </code>
     * 
     * 
     * @return      the visible amount of this scroll bar
     * @see         java.awt.Scrollbar#setVisibleAmount
     * @since       JDK1.1
     */
    public int getVisibleAmount() {
        return getVisible();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getVisibleAmount()</code>.
     */
    @Deprecated
    public int getVisible() {
        return visibleAmount;
    }

    /**
     * Sets the visible amount of this scroll bar.
     * <p>
     * When a scroll bar is used to select a range of values,
     * the visible amount is used to represent the range of values
     * that are currently visible.  The size of the scroll bar's
     * bubble (also called a thumb or scroll box), usually gives a
     * visual representation of the relationship of the visible
     * amount to the range of the scroll bar.
     * Note that depending on platform, the value of the visible amount property
     * may not be visually indicated by the size of the bubble.
     * <p>
     * The scroll bar's bubble may not be displayed when it is not
     * moveable (e.g. when it takes up the entire length of the
     * scroll bar's track, or when the scroll bar is disabled).
     * Whether the bubble is displayed or not will not affect
     * the value returned by <code>getVisibleAmount</code>.
     * <p>
     * If the visible amount supplied is less than <code>one</code>
     * or greater than the current <code>maximum - minimum</code>,
     * then either <code>one</code> or <code>maximum - minimum</code>
     * is substituted, as appropriate.
     * <p>
     * Normally, a program should change a scroll bar's
     * value only by calling <code>setValues</code>.
     * The <code>setValues</code> method simultaneously
     * and synchronously sets the minimum, maximum, visible amount,
     * and value properties of a scroll bar, so that they are
     * mutually consistent.
     *
     * <p>
     *  设置此滚动条的可见量
     * <p>
     * 当滚动条用于选择值范围时,可见量用于表示当前可见的值的范围。
     * 滚动条的气泡(也称为缩略图或滚动框)的大小通常给出可视表示可见量与滚动条的范围的关系注意,根据平台,可见量属性的值可能不能通过气泡的大小在视觉上指示。
     * <p>
     *  当滚动条不可移动时(例如,当它占据滚动条的轨道的整个长度或者当滚动条被禁用时),可能不显示滚动条的气泡。是否显示气泡不会影响返回的值<code> getVisibleAmount </code>
     * <p>
     * 如果提供的可见数量小于<code>一个</code>或大于当前<code> maximum  -  minimum </code>,则<code>一个</code>或<code> / code>
     * <p>
     *  通常,程序应该通过同时调用<code> setValues </code>方法来更改滚动条的值,同时设置滚动条的最小值,最大值,可见量和值属性,使它们相互一致
     * 
     * 
     * @param       newAmount the new visible amount
     * @see         java.awt.Scrollbar#getVisibleAmount
     * @see         java.awt.Scrollbar#setValues
     * @since       JDK1.1
     */
    public void setVisibleAmount(int newAmount) {
        // Use setValues so that a consistent policy relating
        // minimum, maximum, visible amount, and value is enforced.
        setValues(value, newAmount, minimum, maximum);
    }

    /**
     * Sets the unit increment for this scroll bar.
     * <p>
     * The unit increment is the value that is added or subtracted
     * when the user activates the unit increment area of the
     * scroll bar, generally through a mouse or keyboard gesture
     * that the scroll bar receives as an adjustment event.
     * The unit increment must be greater than zero.
     * Attepts to set the unit increment to a value lower than 1
     * will result in a value of 1 being set.
     * <p>
     * In some operating systems, this property
     * can be ignored by the underlying controls.
     *
     * <p>
     *  设置此滚动条的单位增量
     * <p>
     * 单位增量是当用户激活滚动条的单位增量区域时添加或减去的值,通常通过滚动条作为调整事件接收的鼠标或键盘手势单位增量必须大于零攻击将单位增量设置为小于1的值将导致设置值1
     * <p>
     *  在某些操作系统中,底层控件可以忽略此属性
     * 
     * 
     * @param        v  the amount by which to increment or decrement
     *                         the scroll bar's value
     * @see          java.awt.Scrollbar#getUnitIncrement
     * @since        JDK1.1
     */
    public void setUnitIncrement(int v) {
        setLineIncrement(v);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setUnitIncrement(int)</code>.
     */
    @Deprecated
    public synchronized void setLineIncrement(int v) {
        int tmp = (v < 1) ? 1 : v;

        if (lineIncrement == tmp) {
            return;
        }
        lineIncrement = tmp;

        ScrollbarPeer peer = (ScrollbarPeer)this.peer;
        if (peer != null) {
            peer.setLineIncrement(lineIncrement);
        }
    }

    /**
     * Gets the unit increment for this scrollbar.
     * <p>
     * The unit increment is the value that is added or subtracted
     * when the user activates the unit increment area of the
     * scroll bar, generally through a mouse or keyboard gesture
     * that the scroll bar receives as an adjustment event.
     * The unit increment must be greater than zero.
     * <p>
     * In some operating systems, this property
     * can be ignored by the underlying controls.
     *
     * <p>
     *  获取此滚动条的单位增量
     * <p>
     *  单位增量是当用户激活滚动条的单位增量区域时添加或减去的值,通常通过滚动条作为调整事件接收的鼠标或键盘手势。单位增量必须大于零
     * <p>
     * 在某些操作系统中,底层控件可以忽略此属性
     * 
     * 
     * @return      the unit increment of this scroll bar
     * @see         java.awt.Scrollbar#setUnitIncrement
     * @since       JDK1.1
     */
    public int getUnitIncrement() {
        return getLineIncrement();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getUnitIncrement()</code>.
     */
    @Deprecated
    public int getLineIncrement() {
        return lineIncrement;
    }

    /**
     * Sets the block increment for this scroll bar.
     * <p>
     * The block increment is the value that is added or subtracted
     * when the user activates the block increment area of the
     * scroll bar, generally through a mouse or keyboard gesture
     * that the scroll bar receives as an adjustment event.
     * The block increment must be greater than zero.
     * Attepts to set the block increment to a value lower than 1
     * will result in a value of 1 being set.
     *
     * <p>
     *  设置此滚动条的块增量
     * <p>
     *  块增量是当用户激活滚动条的块增量区域时添加或减去的值,通常通过滚动条接收作为调整事件的鼠标或键盘手势块增量必须大于零攻击将块增量设置为小于1的值将导致设置值1
     * 
     * 
     * @param        v  the amount by which to increment or decrement
     *                         the scroll bar's value
     * @see          java.awt.Scrollbar#getBlockIncrement
     * @since        JDK1.1
     */
    public void setBlockIncrement(int v) {
        setPageIncrement(v);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setBlockIncrement()</code>.
     */
    @Deprecated
    public synchronized void setPageIncrement(int v) {
        int tmp = (v < 1) ? 1 : v;

        if (pageIncrement == tmp) {
            return;
        }
        pageIncrement = tmp;

        ScrollbarPeer peer = (ScrollbarPeer)this.peer;
        if (peer != null) {
            peer.setPageIncrement(pageIncrement);
        }
    }

    /**
     * Gets the block increment of this scroll bar.
     * <p>
     * The block increment is the value that is added or subtracted
     * when the user activates the block increment area of the
     * scroll bar, generally through a mouse or keyboard gesture
     * that the scroll bar receives as an adjustment event.
     * The block increment must be greater than zero.
     *
     * <p>
     *  获取此滚动条的块增量
     * <p>
     * 块增量是当用户激活滚动条的块增量区域时添加或减去的值,通常通过滚动条作为调整事件接收的鼠标或键盘手势。块增量必须大于零
     * 
     * 
     * @return      the block increment of this scroll bar
     * @see         java.awt.Scrollbar#setBlockIncrement
     * @since       JDK1.1
     */
    public int getBlockIncrement() {
        return getPageIncrement();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getBlockIncrement()</code>.
     */
    @Deprecated
    public int getPageIncrement() {
        return pageIncrement;
    }

    /**
     * Sets the values of four properties for this scroll bar:
     * <code>value</code>, <code>visibleAmount</code>,
     * <code>minimum</code>, and <code>maximum</code>.
     * If the values supplied for these properties are inconsistent
     * or incorrect, they will be changed to ensure consistency.
     * <p>
     * This method simultaneously and synchronously sets the values
     * of four scroll bar properties, assuring that the values of
     * these properties are mutually consistent. It enforces the
     * following constraints:
     * <code>maximum</code> must be greater than <code>minimum</code>,
     * <code>maximum - minimum</code> must not be greater
     *     than <code>Integer.MAX_VALUE</code>,
     * <code>visibleAmount</code> must be greater than zero.
     * <code>visibleAmount</code> must not be greater than
     *     <code>maximum - minimum</code>,
     * <code>value</code> must not be less than <code>minimum</code>,
     * and <code>value</code> must not be greater than
     *     <code>maximum - visibleAmount</code>
     * <p>
     * Calling this method does not fire an
     * <code>AdjustmentEvent</code>.
     *
     * <p>
     *  设置此滚动条的四个属性的值：<code> value </code>,<code> visibleAmount </code>,<code> minimum </code>和<code> maximu
     * m </code>为这些属性提供的不一致或不正确,它们将被更改以确保一致性。
     * <p>
     * 此方法同时并同步设置四个滚动条属性的值,确保这些属性的值相互一致。
     * 它强制以下约束：<code> maximum </code>必须大于<code> minimum </code> ,<code> maximum-minimum </code>必须不能大于<code> 
     * IntegerMAX_VALUE </code>,<code> visibleAmount </code>必须大于零<code> visibleAmount </code> <code> maximum
     *   -  minimum </code>不能小于<code> minimum </code>,且<code> value </code>不能大于<code > maximum  -  visibleAm
     * ount </code>。
     * 此方法同时并同步设置四个滚动条属性的值,确保这些属性的值相互一致。
     * <p>
     *  调用此方法不会触发<code> AdjustmentEvent </code>
     * 
     * 
     * @param      value is the position in the current window
     * @param      visible is the visible amount of the scroll bar
     * @param      minimum is the minimum value of the scroll bar
     * @param      maximum is the maximum value of the scroll bar
     * @see        #setMinimum
     * @see        #setMaximum
     * @see        #setVisibleAmount
     * @see        #setValue
     */
    public void setValues(int value, int visible, int minimum, int maximum) {
        int oldValue;
        synchronized (this) {
            if (minimum == Integer.MAX_VALUE) {
                minimum = Integer.MAX_VALUE - 1;
            }
            if (maximum <= minimum) {
                maximum = minimum + 1;
            }

            long maxMinusMin = (long) maximum - (long) minimum;
            if (maxMinusMin > Integer.MAX_VALUE) {
                maxMinusMin = Integer.MAX_VALUE;
                maximum = minimum + (int) maxMinusMin;
            }
            if (visible > (int) maxMinusMin) {
                visible = (int) maxMinusMin;
            }
            if (visible < 1) {
                visible = 1;
            }

            if (value < minimum) {
                value = minimum;
            }
            if (value > maximum - visible) {
                value = maximum - visible;
            }

            oldValue = this.value;
            this.value = value;
            this.visibleAmount = visible;
            this.minimum = minimum;
            this.maximum = maximum;
            ScrollbarPeer peer = (ScrollbarPeer)this.peer;
            if (peer != null) {
                peer.setValues(value, visibleAmount, minimum, maximum);
            }
        }

        if ((oldValue != value) && (accessibleContext != null))  {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VALUE_PROPERTY,
                    Integer.valueOf(oldValue),
                    Integer.valueOf(value));
        }
    }

    /**
     * Returns true if the value is in the process of changing as a
     * result of actions being taken by the user.
     *
     * <p>
     * 如果值处于由用户执行的操作的结果而发生更改的过程中,则返回true
     * 
     * 
     * @return the value of the <code>valueIsAdjusting</code> property
     * @see #setValueIsAdjusting
     * @since 1.4
     */
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    /**
     * Sets the <code>valueIsAdjusting</code> property.
     *
     * <p>
     *  设置<code> valueIsAdjusting </code>属性
     * 
     * 
     * @param b new adjustment-in-progress status
     * @see #getValueIsAdjusting
     * @since 1.4
     */
    public void setValueIsAdjusting(boolean b) {
        boolean oldValue;

        synchronized (this) {
            oldValue = isAdjusting;
            isAdjusting = b;
        }

        if ((oldValue != b) && (accessibleContext != null)) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                    ((oldValue) ? AccessibleState.BUSY : null),
                    ((b) ? AccessibleState.BUSY : null));
        }
    }



    /**
     * Adds the specified adjustment listener to receive instances of
     * <code>AdjustmentEvent</code> from this scroll bar.
     * If l is <code>null</code>, no exception is thrown and no
     * action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的调整侦听器以从此滚动条接收<code> AdjustmentEvent </code>的实例如果l是<code> null </code>,则不会抛出任何异常并且不执行任何操作<p>请参阅<a href ="doc-files / AWTThreadIssueshtml#ListenersThreads">
     *  AWT线程问题</a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param        l the adjustment listener
     * @see          #removeAdjustmentListener
     * @see          #getAdjustmentListeners
     * @see          java.awt.event.AdjustmentEvent
     * @see          java.awt.event.AdjustmentListener
     * @since        JDK1.1
     */
    public synchronized void addAdjustmentListener(AdjustmentListener l) {
        if (l == null) {
            return;
        }
        adjustmentListener = AWTEventMulticaster.add(adjustmentListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified adjustment listener so that it no longer
     * receives instances of <code>AdjustmentEvent</code> from this scroll bar.
     * If l is <code>null</code>, no exception is thrown and no action
     * is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 删除指定的调整侦听器,使其不再从此滚动条接收<code> AdjustmentEvent </code>的实例如果l是<code> null </code>,则不会抛出任何异常并且不执行任何操作<p>到
     * <a href=\"doc-files/AWTThreadIssueshtml#ListenersThreads\"> AWT线程问题</a>了解有关AWT线程模型的详细信息。
     * 
     * 
     * @param           l    the adjustment listener
     * @see             #addAdjustmentListener
     * @see             #getAdjustmentListeners
     * @see             java.awt.event.AdjustmentEvent
     * @see             java.awt.event.AdjustmentListener
     * @since           JDK1.1
     */
    public synchronized void removeAdjustmentListener(AdjustmentListener l) {
        if (l == null) {
            return;
        }
        adjustmentListener = AWTEventMulticaster.remove(adjustmentListener, l);
    }

    /**
     * Returns an array of all the adjustment listeners
     * registered on this scrollbar.
     *
     * <p>
     *  返回在此滚动条上注册的所有调整侦听器的数组
     * 
     * 
     * @return all of this scrollbar's <code>AdjustmentListener</code>s
     *         or an empty array if no adjustment
     *         listeners are currently registered
     * @see             #addAdjustmentListener
     * @see             #removeAdjustmentListener
     * @see             java.awt.event.AdjustmentEvent
     * @see             java.awt.event.AdjustmentListener
     * @since 1.4
     */
    public synchronized AdjustmentListener[] getAdjustmentListeners() {
        return getListeners(AdjustmentListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this <code>Scrollbar</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal,  such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>Scrollbar</code> <code>c</code>
     * for its mouse listeners with the following code:
     *
     * <pre>MouseListener[] mls = (MouseListener[])(c.getListeners(MouseListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     *  返回当前注册为<code> <em> </em>侦听器</code>的所有对象的数组</code> </em> / code>使用<code> add <em> </em>侦听器</code>方法注册
     * 。
     * <p>
     * 您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listenerclass </code>例如,您可以查询<code> S
     * crollbar </code > <code> c </code>为它的鼠标监听器使用以下代码：。
     * 
     *  <pre> MouseListener [] mls =(MouseListener [])(cgetListeners(MouseListenerclass)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this component,
     *          or an empty array if no such listeners have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if  (listenerType == AdjustmentListener.class) {
            l = adjustmentListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        if (e.id == AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED) {
            if ((eventMask & AWTEvent.ADJUSTMENT_EVENT_MASK) != 0 ||
                adjustmentListener != null) {
                return true;
            }
            return false;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this scroll bar. If the event is an
     * instance of <code>AdjustmentEvent</code>, it invokes the
     * <code>processAdjustmentEvent</code> method.
     * Otherwise, it invokes its superclass's
     * <code>processEvent</code> method.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  在此滚动条上处理事件如果事件是<code> AdjustmentEvent </code>的实例,它会调用<code> processAdjustmentEvent </code>方法。
     * 否则,它将调用其超类的<code> processEvent </code> p>请注意,如果事件参数是<code> null </code>,行为是未指定的,可能会导致异常。
     * 
     * 
     * @param        e the event
     * @see          java.awt.event.AdjustmentEvent
     * @see          java.awt.Scrollbar#processAdjustmentEvent
     * @since        JDK1.1
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof AdjustmentEvent) {
            processAdjustmentEvent((AdjustmentEvent)e);
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes adjustment events occurring on this
     * scrollbar by dispatching them to any registered
     * <code>AdjustmentListener</code> objects.
     * <p>
     * This method is not called unless adjustment events are
     * enabled for this component. Adjustment events are enabled
     * when one of the following occurs:
     * <ul>
     * <li>An <code>AdjustmentListener</code> object is registered
     * via <code>addAdjustmentListener</code>.
     * <li>Adjustment events are enabled via <code>enableEvents</code>.
     * </ul>
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     * 通过将调度事件分派到任何已注册的<code> AdjustmentListener </code>对象,来处理在此滚动条上发生的调整事件
     * <p>
     *  除非为此组件启用了调整事件,否则不会调用此方法调整事件在以下情况之一时启用：
     * <ul>
     *  <li>通过<code> addAdjustmentListener </code> <li>注册<code> AdjustmentListener </code>对象。
     * 调整事件通过<code> enableEvents </code>。
     * </ul>
     *  <p>请注意,如果事件参数是<code> null </code>,行为未指定,可能会导致异常
     * 
     * 
     * @param       e the adjustment event
     * @see         java.awt.event.AdjustmentEvent
     * @see         java.awt.event.AdjustmentListener
     * @see         java.awt.Scrollbar#addAdjustmentListener
     * @see         java.awt.Component#enableEvents
     * @since       JDK1.1
     */
    protected void processAdjustmentEvent(AdjustmentEvent e) {
        AdjustmentListener listener = adjustmentListener;
        if (listener != null) {
            listener.adjustmentValueChanged(e);
        }
    }

    /**
     * Returns a string representing the state of this <code>Scrollbar</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     * 返回表示此<code> Scrollbar的状态的字符串</code>此方法仅用于调试目的,返回的字符串的内容和格式可能会在实现之间变化返回的字符串可能为空但可能不<code> null </code>
     * 。
     * 
     * 
     * @return      the parameter string of this scroll bar
     */
    protected String paramString() {
        return super.paramString() +
            ",val=" + value +
            ",vis=" + visibleAmount +
            ",min=" + minimum +
            ",max=" + maximum +
            ((orientation == VERTICAL) ? ",vert" : ",horz") +
            ",isAdjusting=" + isAdjusting;
    }


    /* Serialization support.
    /* <p>
     */

    /**
     * The scroll bar's serialized Data Version.
     *
     * <p>
     *  滚动条的序列化数据版本
     * 
     * 
     * @serial
     */
    private int scrollbarSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable <code>AdjustmentListeners</code>
     * as optional data. The non-serializable listeners are
     * detected and no attempt is made to serialize them.
     *
     * <p>
     *  将默认可序列化字段写入流写入可序列化的列表<code> AdjustmentListeners </code>作为可选数据检测到非可序列化的侦听器,并且不尝试将它们序列化
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @serialData <code>null</code> terminated sequence of 0
     *   or more pairs; the pair consists of a <code>String</code>
     *   and an <code>Object</code>; the <code>String</code> indicates
     *   the type of object and is one of the following:
     *   <code>adjustmentListenerK</code> indicating an
     *     <code>AdjustmentListener</code> object
     *
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#adjustmentListenerK
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
      s.defaultWriteObject();

      AWTEventMulticaster.save(s, adjustmentListenerK, adjustmentListener);
      s.writeObject(null);
    }

    /**
     * Reads the <code>ObjectInputStream</code> and if
     * it isn't <code>null</code> adds a listener to
     * receive adjustment events fired by the
     * <code>Scrollbar</code>.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     *  读取<code> ObjectInputStream </code>,如果不是<code> null </code>添加一个监听器来接收由<code> Scrollbar触发的调整事件</code>无
     * 法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject(ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
      GraphicsEnvironment.checkHeadless();
      s.defaultReadObject();

      Object keyOrNull;
      while(null != (keyOrNull = s.readObject())) {
        String key = ((String)keyOrNull).intern();

        if (adjustmentListenerK == key)
          addAdjustmentListener((AdjustmentListener)(s.readObject()));

        else // skip value for unrecognized key
          s.readObject();
      }
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>Scrollbar</code>. For scrollbars, the
     * <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleAWTScrollBar</code>. A new
     * <code>AccessibleAWTScrollBar</code> instance is created if necessary.
     *
     * <p>
     * 获取与此<code> Scrollbar </code>关联的<code> AccessibleContext </code>对于滚动条,<code> AccessibleContext </code>
     * 采用<code> AccessibleAWTScrollBar </code> >如果需要,创建AccessibleAWTScrollBar </code>实例。
     * 
     * 
     * @return an <code>AccessibleAWTScrollBar</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>ScrollBar</code>
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTScrollBar();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Scrollbar</code> class.  It provides an implementation of
     * the Java Accessibility API appropriate to scrollbar
     * user-interface elements.
     * <p>
     *  此类实现<code> Scrollbar </code>类的辅助功能支持它提供了适用于滚动条用户界面元素的Java辅助功能API的实现
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTScrollBar extends AccessibleAWTComponent
        implements AccessibleValue
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 13 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -344337268523697807L;

        /**
         * Get the state set of this object.
         *
         * <p>
         *  获取此对象的状态集
         * 
         * 
         * @return an instance of <code>AccessibleState</code>
         *     containing the current state of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (getValueIsAdjusting()) {
                states.add(AccessibleState.BUSY);
            }
            if (getOrientation() == VERTICAL) {
                states.add(AccessibleState.VERTICAL);
            } else {
                states.add(AccessibleState.HORIZONTAL);
            }
            return states;
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用
         * 
         * 
         * @return an instance of <code>AccessibleRole</code>
         *     describing the role of the object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.SCROLL_BAR;
        }

        /**
         * Get the <code>AccessibleValue</code> associated with this
         * object.  In the implementation of the Java Accessibility
         * API for this class, return this object, which is
         * responsible for implementing the
         * <code>AccessibleValue</code> interface on behalf of itself.
         *
         * <p>
         * 获取与此对象关联的<code> AccessibleValue </code>在此类的Java辅助功能API的实现中,返回此对象,该对象负责代表自身实现<code> AccessibleValue </code>
         * 。
         * 
         * 
         * @return this object
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }

        /**
         * Get the accessible value of this object.
         *
         * <p>
         *  获取此对象的可访问值
         * 
         * 
         * @return The current value of this object.
         */
        public Number getCurrentAccessibleValue() {
            return Integer.valueOf(getValue());
        }

        /**
         * Set the value of this object as a Number.
         *
         * <p>
         *  将此对象的值设置为Number
         * 
         * 
         * @return True if the value was set.
         */
        public boolean setCurrentAccessibleValue(Number n) {
            if (n instanceof Integer) {
                setValue(n.intValue());
                return true;
            } else {
                return false;
            }
        }

        /**
         * Get the minimum accessible value of this object.
         *
         * <p>
         *  获取此对象的最小可访问值
         * 
         * 
         * @return The minimum value of this object.
         */
        public Number getMinimumAccessibleValue() {
            return Integer.valueOf(getMinimum());
        }

        /**
         * Get the maximum accessible value of this object.
         *
         * <p>
         *  获取此对象的最大可访问值
         * 
         * @return The maximum value of this object.
         */
        public Number getMaximumAccessibleValue() {
            return Integer.valueOf(getMaximum());
        }

    } // AccessibleAWTScrollBar

}
